package me.rabrg.clans;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.rabrg.clans.event.CPlayerLeaveEvent;
import me.rabrg.clans.event.LandClaimEvent;
import me.rabrg.clans.iface.EconomyParticipator;
import me.rabrg.clans.iface.RelationParticipator;
import me.rabrg.clans.integration.Econ;
import me.rabrg.clans.integration.LWCFeatures;
import me.rabrg.clans.integration.SpoutFeatures;
import me.rabrg.clans.integration.Worldguard;
import me.rabrg.clans.struct.ChatMode;
import me.rabrg.clans.struct.Permission;
import me.rabrg.clans.struct.Relation;
import me.rabrg.clans.struct.Role;
import me.rabrg.clans.util.RelationUtil;
import me.rabrg.clans.zcore.persist.PlayerEntity;

/**
 * Logged in players always have exactly one CPlayer instance. Logged out
 * players may or may not have an CPlayer instance. They will always have one if
 * they are part of a clan. This is because only players with a clan are saved
 * to disk (in order to not waste disk space).
 * 
 * The CPlayer is linked to a minecraft player using the player name.
 * 
 * The same instance is always returned for the same player. This means you can
 * use the == operator. No .equals method necessary.
 */

public class CPlayer extends PlayerEntity implements EconomyParticipator {
	// private transient String playerName;
	private transient CLocation lastStoodAt = new CLocation(); // Where did this
																// player stand
																// the last time
																// we checked?

	// FIELD: clanId
	private String clanId;

	public Clan getClan() {
		if (this.clanId == null) {
			return null;
		}
		return Clans.i.get(this.clanId);
	}

	public String getClanId() {
		return this.clanId;
	}

	public boolean hasClan() {
		return !clanId.equals("0");
	}

	public void setClan(final Clan clan) {
		final Clan oldClan = this.getClan();
		if (oldClan != null) {
			oldClan.removeCPlayer(this);
		}
		clan.addCPlayer(this);
		this.clanId = clan.getId();
		SpoutFeatures.updateAppearances(this.getPlayer());
	}

	// FIELD: role
	private Role role;

	public Role getRole() {
		return this.role;
	}

	public void setRole(final Role role) {
		this.role = role;
		SpoutFeatures.updateAppearances(this.getPlayer());
	}

	// FIELD: title
	private String title;

	// FIELD: power
	private double power;

	// FIELD: powerBoost
	// special increase/decrease to min and max power for this player
	private double powerBoost;

	public double getPowerBoost() {
		return this.powerBoost;
	}

	public void setPowerBoost(final double powerBoost) {
		this.powerBoost = powerBoost;
	}

	// FIELD: lastPowerUpdateTime
	private long lastPowerUpdateTime;

	// FIELD: lastLoginTime
	private long lastLoginTime;

	// FIELD: mapAutoUpdating
	private transient boolean mapAutoUpdating;

	// FIELD: autoClaimEnabled
	private transient Clan autoClaimFor;

	public Clan getAutoClaimFor() {
		return autoClaimFor;
	}

	public void setAutoClaimFor(final Clan clan) {
		this.autoClaimFor = clan;
		if (this.autoClaimFor != null) {
			// TODO: merge these into same autoclaim
			this.autoSafeZoneEnabled = false;
			this.autoWarZoneEnabled = false;
		}
	}

	// FIELD: autoSafeZoneEnabled
	private transient boolean autoSafeZoneEnabled;

	public boolean isAutoSafeClaimEnabled() {
		return autoSafeZoneEnabled;
	}

	public void setIsAutoSafeClaimEnabled(final boolean enabled) {
		this.autoSafeZoneEnabled = enabled;
		if (enabled) {
			this.autoClaimFor = null;
			this.autoWarZoneEnabled = false;
		}
	}

	// FIELD: autoWarZoneEnabled
	private transient boolean autoWarZoneEnabled;

	public boolean isAutoWarClaimEnabled() {
		return autoWarZoneEnabled;
	}

	public void setIsAutoWarClaimEnabled(final boolean enabled) {
		this.autoWarZoneEnabled = enabled;
		if (enabled) {
			this.autoClaimFor = null;
			this.autoSafeZoneEnabled = false;
		}
	}

	private transient boolean isAdminBypassing = false;

	public boolean isAdminBypassing() {
		return this.isAdminBypassing;
	}

	public void setIsAdminBypassing(final boolean val) {
		this.isAdminBypassing = val;
	}

	// FIELD: loginPvpDisabled
	private transient boolean loginPvpDisabled;

	// FIELD: deleteMe
	private transient boolean deleteMe;

	// FIELD: chatMode
	private ChatMode chatMode;

	public void setChatMode(final ChatMode chatMode) {
		this.chatMode = chatMode;
	}

	public ChatMode getChatMode() {
		if (this.clanId.equals("0") || !Conf.clanOnlyChat) {
			this.chatMode = ChatMode.PUBLIC;
		}
		return chatMode;
	}

	// FIELD: chatSpy
	private transient boolean spyingChat = false;

	public void setSpyingChat(final boolean chatSpying) {
		this.spyingChat = chatSpying;
	}

	public boolean isSpyingChat() {
		return spyingChat;
	}

	// FIELD: account
	@Override
	public String getAccountId() {
		return this.getId();
	}

	// -------------------------------------------- //
	// Construct
	// -------------------------------------------- //

	// GSON need this noarg constructor.
	public CPlayer() {
		this.resetClanData(false);
		this.power = Conf.powerPlayerStarting;
		this.lastPowerUpdateTime = System.currentTimeMillis();
		this.lastLoginTime = System.currentTimeMillis();
		this.mapAutoUpdating = false;
		this.autoClaimFor = null;
		this.autoSafeZoneEnabled = false;
		this.autoWarZoneEnabled = false;
		this.loginPvpDisabled = (Conf.noPVPDamageToOthersForXSecondsAfterLogin > 0) ? true : false;
		this.deleteMe = false;
		this.powerBoost = 0.0;

		if (!Conf.newPlayerStartingClanID.equals("0") && Clans.i.exists(Conf.newPlayerStartingClanID)) {
			this.clanId = Conf.newPlayerStartingClanID;
		}
	}

	public final void resetClanData(final boolean doSpoutUpdate) {
		// clean up any territory ownership in old clan, if there is one
		if (Clans.i.exists(this.getClanId())) {
			final Clan currentClan = this.getClan();
			currentClan.removeCPlayer(this);
			if (currentClan.isNormal()) {
				currentClan.clearClaimOwnership(this.getId());
			}
		}

		this.clanId = "0"; // The default neutral clan
		this.chatMode = ChatMode.PUBLIC;
		this.role = Role.NORMAL;
		this.title = "";
		this.autoClaimFor = null;

		if (doSpoutUpdate) {
			SpoutFeatures.updateAppearances(this.getPlayer());
		}
	}

	public void resetClanData() {
		this.resetClanData(true);
	}

	// -------------------------------------------- //
	// Getters And Setters
	// -------------------------------------------- //

	public long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(final long lastLoginTime) {
		losePowerFromBeingOffline();
		this.lastLoginTime = lastLoginTime;
		this.lastPowerUpdateTime = lastLoginTime;
		if (Conf.noPVPDamageToOthersForXSecondsAfterLogin > 0) {
			this.loginPvpDisabled = true;
		}
	}

	public boolean isMapAutoUpdating() {
		return mapAutoUpdating;
	}

	public void setMapAutoUpdating(final boolean mapAutoUpdating) {
		this.mapAutoUpdating = mapAutoUpdating;
	}

	public boolean hasLoginPvpDisabled() {
		if (!loginPvpDisabled) {
			return false;
		}
		if (this.lastLoginTime + (Conf.noPVPDamageToOthersForXSecondsAfterLogin * 1000) < System.currentTimeMillis()) {
			this.loginPvpDisabled = false;
			return false;
		}
		return true;
	}

	public CLocation getLastStoodAt() {
		return this.lastStoodAt;
	}

	public void setLastStoodAt(final CLocation flocation) {
		this.lastStoodAt = flocation;
	}

	public void markForDeletion(final boolean delete) {
		deleteMe = delete;
	}

	// ----------------------------------------------//
	// Title, Name, Clan Tag and Chat
	// ----------------------------------------------//

	// Base:

	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getName() {
		return this.getId(); // TODO: ... display name or remove completeley
	}

	public String getTag() {
		if (!this.hasClan()) {
			return "";
		}
		return this.getClan().getTag();
	}

	// Base concatenations:

	public String getNameAndSomething(final String something) {
		String ret = this.role.getPrefix();
		if (something.length() > 0) {
			ret += something + " ";
		}
		ret += this.getName();
		return ret;
	}

	public String getNameAndTitle() {
		return this.getNameAndSomething(this.getTitle());
	}

	public String getNameAndTag() {
		return this.getNameAndSomething(this.getTag());
	}

	// Colored concatenations:
	// These are used in information messages

	public String getNameAndTitle(final Clan clan) {
		return this.getColorTo(clan) + this.getNameAndTitle();
	}

	public String getNameAndTitle(final CPlayer fplayer) {
		return this.getColorTo(fplayer) + this.getNameAndTitle();
	}

	/*
	 * public String getNameAndTag(Clan clan) { return
	 * this.getRelationColor(clan)+this.getNameAndTag(); } public String
	 * getNameAndTag(CPlayer fplayer) { return
	 * this.getRelationColor(fplayer)+this.getNameAndTag(); }
	 */

	// TODO: REmovded for refactoring.

	/*
	 * public String getNameAndRelevant(Clan clan) { // Which relation? Relation
	 * rel = this.getRelationTo(clan);
	 * 
	 * // For member we show title if (rel == Relation.MEMBER) { return
	 * rel.getColor() + this.getNameAndTitle(); }
	 * 
	 * // For non members we show tag return rel.getColor() +
	 * this.getNameAndTag(); } public String getNameAndRelevant(CPlayer fplayer)
	 * { return getNameAndRelevant(fplayer.getClan()); }
	 */

	// Chat Tag:
	// These are injected into the format of global chat messages.

	public String getChatTag() {
		if (!this.hasClan()) {
			return "";
		}

		return String.format(Conf.chatTagFormat, this.role.getPrefix() + this.getTag());
	}

	// Colored Chat Tag
	public String getChatTag(final Clan clan) {
		if (!this.hasClan()) {
			return "";
		}

		return this.getRelationTo(clan).getColor() + getChatTag();
	}

	public String getChatTag(final CPlayer fplayer) {
		if (!this.hasClan()) {
			return "";
		}

		return this.getColorTo(fplayer) + getChatTag();
	}

	// -------------------------------
	// Relation and relation colors
	// -------------------------------

	@Override
	public String describeTo(final RelationParticipator that, final boolean ucfirst) {
		return RelationUtil.describeThatToMe(this, that, ucfirst);
	}

	@Override
	public String describeTo(final RelationParticipator that) {
		return RelationUtil.describeThatToMe(this, that);
	}

	@Override
	public Relation getRelationTo(final RelationParticipator rp) {
		return RelationUtil.getRelationTo(this, rp);
	}

	@Override
	public Relation getRelationTo(final RelationParticipator rp, final boolean ignorePeaceful) {
		return RelationUtil.getRelationTo(this, rp, ignorePeaceful);
	}

	public Relation getRelationToLocation() {
		return Board.getClanAt(new CLocation(this)).getRelationTo(this);
	}

	@Override
	public ChatColor getColorTo(final RelationParticipator rp) {
		return RelationUtil.getColorOfThatToMe(this, rp);
	}

	// ----------------------------------------------//
	// Health
	// ----------------------------------------------//
	public void heal(final int amnt) {
		final Player player = this.getPlayer();
		if (player == null) {
			return;
		}
		player.setHealth(player.getHealth() + amnt);
	}

	// ----------------------------------------------//
	// Power
	// ----------------------------------------------//
	public double getPower() {
		this.updatePower();
		return this.power;
	}

	protected void alterPower(final double delta) {
		this.power += delta;
		if (this.power > this.getPowerMax()) {
			this.power = this.getPowerMax();
		} else if (this.power < this.getPowerMin()) {
			this.power = this.getPowerMin();
		}
	}

	public double getPowerMax() {
		return Conf.powerPlayerMax + this.powerBoost;
	}

	public double getPowerMin() {
		return Conf.powerPlayerMin + this.powerBoost;
	}

	public int getPowerRounded() {
		return (int) Math.round(this.getPower());
	}

	public int getPowerMaxRounded() {
		return (int) Math.round(this.getPowerMax());
	}

	public int getPowerMinRounded() {
		return (int) Math.round(this.getPowerMin());
	}

	protected void updatePower() {
		if (this.isOffline()) {
			losePowerFromBeingOffline();
			if (!Conf.powerRegenOffline) {
				return;
			}
		}
		final long now = System.currentTimeMillis();
		final long millisPassed = now - this.lastPowerUpdateTime;
		this.lastPowerUpdateTime = now;

		final Player thisPlayer = this.getPlayer();
		if (thisPlayer != null && thisPlayer.isDead()) {
			return; // don't let dead players regain power until they respawn
		}

		final int millisPerMinute = 60 * 1000;
		this.alterPower(millisPassed * Conf.powerPerMinute / millisPerMinute);
	}

	protected void losePowerFromBeingOffline() {
		if (Conf.powerOfflineLossPerDay > 0.0 && this.power > Conf.powerOfflineLossLimit) {
			final long now = System.currentTimeMillis();
			final long millisPassed = now - this.lastPowerUpdateTime;
			this.lastPowerUpdateTime = now;

			double loss = millisPassed * Conf.powerOfflineLossPerDay / (24 * 60 * 60 * 1000);
			if (this.power - loss < Conf.powerOfflineLossLimit) {
				loss = this.power;
			}
			this.alterPower(-loss);
		}
	}

	public void onDeath() {
		this.updatePower();
		this.alterPower(-Conf.powerPerDeath);
	}

	// ----------------------------------------------//
	// Territory
	// ----------------------------------------------//
	public boolean isInOwnTerritory() {
		return Board.getClanAt(new CLocation(this)) == this.getClan();
	}

	public boolean isInOthersTerritory() {
		final Clan clanHere = Board.getClanAt(new CLocation(this));
		return clanHere != null && clanHere.isNormal() && clanHere != this.getClan();
	}

	public boolean isInAllyTerritory() {
		return Board.getClanAt(new CLocation(this)).getRelationTo(this).isAlly();
	}

	public boolean isInNeutralTerritory() {
		return Board.getClanAt(new CLocation(this)).getRelationTo(this).isNeutral();
	}

	public boolean isInEnemyTerritory() {
		return Board.getClanAt(new CLocation(this)).getRelationTo(this).isEnemy();
	}

	public void sendClanHereMessage() {
		if (SpoutFeatures.updateTerritoryDisplay(this)) {
			return;
		}
		final Clan clanHere = Board.getClanAt(this.getLastStoodAt());
		String msg = P.p.txt.parse("<i>") + " ~ " + clanHere.getTag(this);
		if (clanHere.getDescription().length() > 0) {
			msg += " - " + clanHere.getDescription();
		}
		this.sendMessage(msg);
	}

	// -------------------------------
	// Actions
	// -------------------------------

	public void leave(boolean makePay) {
		final Clan myClan = this.getClan();
		makePay = makePay && Econ.shouldBeUsed() && !this.isAdminBypassing();

		if (myClan == null) {
			resetClanData();
			return;
		}

		final boolean perm = myClan.isPermanent();

		if (!perm && this.getRole() == Role.ADMIN && myClan.getCPlayers().size() > 1) {
			msg("<b>You must give the admin role to someone else first.");
			return;
		}

		if (!Conf.canLeaveWithNegativePower && this.getPower() < 0) {
			msg("<b>You cannot leave until your power is positive.");
			return;
		}

		// if economy is enabled and they're not on the bypass list, make sure
		// they can pay
		if (makePay && !Econ.hasAtLeast(this, Conf.econCostLeave, "to leave your clan.")) {
			return;
		}

		final CPlayerLeaveEvent leaveEvent = new CPlayerLeaveEvent(this, myClan, CPlayerLeaveEvent.PlayerLeaveReason.LEAVE);
		Bukkit.getServer().getPluginManager().callEvent(leaveEvent);
		if (leaveEvent.isCancelled()) {
			return;
		}

		// then make 'em pay (if applicable)
		if (makePay && !Econ.modifyMoney(this, -Conf.econCostLeave, "to leave your clan.", "for leaving your clan.")) {
			return;
		}

		// Am I the last one in the clan?
		if (myClan.getCPlayers().size() == 1) {
			// Transfer all money
			if (Econ.shouldBeUsed()) {
				Econ.transferMoney(this, myClan, this, Econ.getBalance(myClan.getAccountId()));
			}
		}

		if (myClan.isNormal()) {
			for (final CPlayer fplayer : myClan.getCPlayersWhereOnline(true)) {
				fplayer.msg("%s<i> left %s<i>.", this.describeTo(fplayer, true), myClan.describeTo(fplayer));
			}

			if (Conf.logClanLeave) {
				P.p.log(this.getName() + " left the clan: " + myClan.getTag());
			}
		}

		this.resetClanData();

		if (myClan.isNormal() && !perm && myClan.getCPlayers().isEmpty()) {
			// Remove this clan
			for (final CPlayer fplayer : CPlayers.i.getOnline()) {
				fplayer.msg("<i>%s<i> was disbanded.", myClan.describeTo(fplayer, true));
			}

			myClan.detach();
			if (Conf.logClanDisband) {
				P.p.log("The clan " + myClan.getTag() + " (" + myClan.getId() + ") was disbanded due to the last player (" + this.getName() + ") leaving.");
			}
		}
	}

	public boolean canClaimForClan(final Clan forClan) {
		if (forClan.isNone()) {
			return false;
		}

		if (this.isAdminBypassing() || (forClan == this.getClan() && this.getRole().isAtLeast(Role.MODERATOR))
				|| (forClan.isSafeZone() && Permission.MANAGE_SAFE_ZONE.has(getPlayer()))
				|| (forClan.isWarZone() && Permission.MANAGE_WAR_ZONE.has(getPlayer()))) {
			return true;
		}

		return false;
	}

	public boolean canClaimForClanAtLocation(final Clan forClan, final Location location, final boolean notifyFailure) {
		String error = null;
		final CLocation flocation = new CLocation(location);
		final Clan myClan = getClan();
		final Clan currentClan = Board.getClanAt(flocation);
		final int ownedLand = forClan.getLandRounded();

		if (Conf.worldGuardChecking && Worldguard.checkForRegionsInChunk(location)) {
			// Checks for WorldGuard regions in the chunk attempting to be
			// claimed
			error = P.p.txt.parse("<b>This land is protected");
		} else if (Conf.worldsNoClaiming.contains(flocation.getWorldName())) {
			error = P.p.txt.parse("<b>Sorry, this world has land claiming disabled.");
		} else if (this.isAdminBypassing()) {
			return true;
		} else if (forClan.isSafeZone() && Permission.MANAGE_SAFE_ZONE.has(getPlayer())) {
			return true;
		} else if (forClan.isWarZone() && Permission.MANAGE_WAR_ZONE.has(getPlayer())) {
			return true;
		} else if (myClan != forClan) {
			error = P.p.txt.parse("<b>You can't claim land for <h>%s<b>.", forClan.describeTo(this));
		} else if (forClan == currentClan) {
			error = P.p.txt.parse("%s<i> already own this land.", forClan.describeTo(this, true));
		} else if (this.getRole().value < Role.MODERATOR.value) {
			error = P.p.txt.parse("<b>You must be <h>%s<b> to claim land.", Role.MODERATOR.toString());
		} else if (forClan.getCPlayers().size() < Conf.claimsRequireMinClanMembers) {
			error = P.p.txt.parse("Clans must have at least <h>%s<b> members to claim land.", Conf.claimsRequireMinClanMembers);
		} else if (currentClan.isSafeZone()) {
			error = P.p.txt.parse("<b>You can not claim a Safe Zone.");
		} else if (currentClan.isWarZone()) {
			error = P.p.txt.parse("<b>You can not claim a War Zone.");
		} else if (ownedLand >= forClan.getPowerRounded()) {
			error = P.p.txt.parse("<b>You can't claim more land! You need more power!");
		} else if (Conf.claimedLandsMax != 0 && ownedLand >= Conf.claimedLandsMax && forClan.isNormal()) {
			error = P.p.txt.parse("<b>Limit reached. You can't claim more land!");
		} else if (currentClan.getRelationTo(forClan) == Relation.ALLY) {
			error = P.p.txt.parse("<b>You can't claim the land of your allies.");
		} else if (Conf.claimsMustBeConnected && !this.isAdminBypassing() && myClan.getLandRoundedInWorld(flocation.getWorldName()) > 0
				&& !Board.isConnectedLocation(flocation, myClan) && (!Conf.claimsCanBeUnconnectedIfOwnedByOtherClan || !currentClan.isNormal())) {
			if (Conf.claimsCanBeUnconnectedIfOwnedByOtherClan) {
				error = P.p.txt.parse("<b>You can only claim additional land which is connected to your first claim or controlled by another clan!");
			} else {
				error = P.p.txt.parse("<b>You can only claim additional land which is connected to your first claim!");
			}
		} else if (currentClan.isNormal()) {
			if (myClan.isPeaceful()) {
				error = P.p.txt.parse("%s<i> owns this land. Your clan is peaceful, so you cannot claim land from other clans.", currentClan.getTag(this));
			} else if (currentClan.isPeaceful()) {
				error = P.p.txt.parse("%s<i> owns this land, and is a peaceful clan. You cannot claim land from them.", currentClan.getTag(this));
			} else if (!currentClan.hasLandInflation()) {
				// TODO more messages WARN current clan most importantly
				error = P.p.txt.parse("%s<i> owns this land and is strong enough to keep it.", currentClan.getTag(this));
			} else if (!Board.isBorderLocation(flocation)) {
				error = P.p.txt.parse("<b>You must start claiming land at the border of the territory.");
			}
		}

		if (notifyFailure && error != null) {
			msg(error);
		}
		return error == null;
	}

	public boolean attemptClaim(final Clan forClan, final Location location, final boolean notifyFailure) {
		// notifyFailure is false if called by auto-claim; no need to notify on
		// every failure for it
		// return value is false on failure, true on success

		final CLocation flocation = new CLocation(location);
		final Clan currentClan = Board.getClanAt(flocation);

		final int ownedLand = forClan.getLandRounded();

		if (!this.canClaimForClanAtLocation(forClan, location, notifyFailure)) {
			return false;
		}

		// if economy is enabled and they're not on the bypass list, make sure
		// they can pay
		final boolean mustPay = Econ.shouldBeUsed() && !this.isAdminBypassing() && !forClan.isSafeZone() && !forClan.isWarZone();
		double cost = 0.0;
		EconomyParticipator payee = null;
		if (mustPay) {
			cost = Econ.calculateClaimCost(ownedLand, currentClan.isNormal());

			if (Conf.econClaimUnconnectedFee != 0.0 && forClan.getLandRoundedInWorld(flocation.getWorldName()) > 0
					&& !Board.isConnectedLocation(flocation, forClan)) {
				cost += Conf.econClaimUnconnectedFee;
			}

			if (Conf.bankEnabled && Conf.bankClanPaysLandCosts && this.hasClan()) {
				payee = this.getClan();
			} else {
				payee = this;
			}

			if (!Econ.hasAtLeast(payee, cost, "to claim this land")) {
				return false;
			}
		}

		final LandClaimEvent claimEvent = new LandClaimEvent(flocation, forClan, this);
		Bukkit.getServer().getPluginManager().callEvent(claimEvent);
		if (claimEvent.isCancelled()) {
			return false;
		}

		// then make 'em pay (if applicable)
		if (mustPay && !Econ.modifyMoney(payee, -cost, "to claim this land", "for claiming this land")) {
			return false;
		}

		if (LWCFeatures.getEnabled() && forClan.isNormal() && Conf.onCaptureResetLwcLocks) {
			LWCFeatures.clearOtherChests(flocation, this.getClan());
		}

		// announce success
		final Set<CPlayer> informTheseCPlayers = new HashSet<CPlayer>();
		informTheseCPlayers.add(this);
		informTheseCPlayers.addAll(forClan.getCPlayersWhereOnline(true));
		for (final CPlayer fp : informTheseCPlayers) {
			fp.msg("<h>%s<i> claimed land for <h>%s<i> from <h>%s<i>.", this.describeTo(fp, true), forClan.describeTo(fp), currentClan.describeTo(fp));
		}

		Board.setClanAt(forClan, flocation);
		SpoutFeatures.updateTerritoryDisplayLoc(flocation);

		if (Conf.logLandClaims) {
			P.p.log(this.getName() + " claimed land at (" + flocation.getCoordString() + ") for the clan: " + forClan.getTag());
		}

		return true;
	}

	// -------------------------------------------- //
	// Persistance
	// -------------------------------------------- //

	@Override
	public boolean shouldBeSaved() {
		if (!this.hasClan() && (this.getPowerRounded() == this.getPowerMaxRounded() || this.getPowerRounded() == (int) Math.round(Conf.powerPlayerStarting))) {
			return false;
		}
		return !this.deleteMe;
	}

	@Override
	public void msg(final String str, final Object... args) {
		this.sendMessage(P.p.txt.parse(str, args));
	}
}