package me.rabrg.clans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.rabrg.clans.iface.EconomyParticipator;
import me.rabrg.clans.iface.RelationParticipator;
import me.rabrg.clans.integration.Econ;
import me.rabrg.clans.integration.LWCFeatures;
import me.rabrg.clans.struct.Permission;
import me.rabrg.clans.struct.Relation;
import me.rabrg.clans.struct.Role;
import me.rabrg.clans.util.LazyLocation;
import me.rabrg.clans.util.MiscUtil;
import me.rabrg.clans.util.RelationUtil;
import me.rabrg.clans.zcore.persist.Entity;

public class Clan extends Entity implements EconomyParticipator {
	// FIELD: relationWish
	private final Map<String, Relation> relationWish;

	// FIELD: claimOwnership
	private final Map<CLocation, Set<String>> claimOwnership = new ConcurrentHashMap<CLocation, Set<String>>();

	// FIELD: fplayers
	// speedy lookup of players in clan
	private transient Set<CPlayer> fplayers = new HashSet<CPlayer>();

	// FIELD: invites
	// Where string is a lowercase player name
	private final Set<String> invites;

	public void invite(final CPlayer fplayer) {
		this.invites.add(fplayer.getName().toLowerCase());
	}

	public void deinvite(final CPlayer fplayer) {
		this.invites.remove(fplayer.getName().toLowerCase());
	}

	public boolean isInvited(final CPlayer fplayer) {
		return this.invites.contains(fplayer.getName().toLowerCase());
	}

	// FIELD: open
	private boolean open;

	public boolean getOpen() {
		return open;
	}

	public void setOpen(final boolean isOpen) {
		open = isOpen;
	}

	// FIELD: peaceful
	// "peaceful" status can only be set by server admins/moderators/ops, and
	// prevents PvP and land capture to/from the clan
	private boolean peaceful;

	public boolean isPeaceful() {
		return this.peaceful;
	}

	public void setPeaceful(final boolean isPeaceful) {
		this.peaceful = isPeaceful;
	}

	// FIELD: peacefulExplosionsEnabled
	private boolean peacefulExplosionsEnabled;

	public void setPeacefulExplosionsEnabled(final boolean val) {
		peacefulExplosionsEnabled = val;
	}

	public boolean getPeacefulExplosionsEnabled() {
		return this.peacefulExplosionsEnabled;
	}

	public boolean noExplosionsInTerritory() {
		return this.peaceful && !peacefulExplosionsEnabled;
	}

	// FIELD: permanent
	// "permanent" status can only be set by server admins/moderators/ops, and
	// allows the clan to remain even with 0 members
	private boolean permanent;

	public boolean isPermanent() {
		return permanent || !this.isNormal();
	}

	public void setPermanent(final boolean isPermanent) {
		permanent = isPermanent;
	}

	// FIELD: tag
	private String tag;

	public String getTag() {
		return this.tag;
	}

	public String getTag(final String prefix) {
		return prefix + this.tag;
	}

	public String getTag(final Clan otherClan) {
		if (otherClan == null) {
			return getTag();
		}
		return this.getTag(this.getColorTo(otherClan).toString());
	}

	public String getTag(final CPlayer otherFplayer) {
		if (otherFplayer == null) {
			return getTag();
		}
		return this.getTag(this.getColorTo(otherFplayer).toString());
	}

	public void setTag(String str) {
		if (Conf.clanTagForceUpperCase) {
			str = str.toUpperCase();
		}
		this.tag = str;
	}

	public String getComparisonTag() {
		return MiscUtil.getComparisonString(this.tag);
	}

	// FIELD: description
	private String description;

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String value) {
		this.description = value;
	}

	// FIELD: home
	private LazyLocation home;

	public void setHome(final Location home) {
		this.home = new LazyLocation(home);
	}

	public boolean hasHome() {
		return this.getHome() != null;
	}

	public Location getHome() {
		confirmValidHome();
		return (this.home != null) ? this.home.getLocation() : null;
	}

	public void confirmValidHome() {
		if (!Conf.homesMustBeInClaimedTerritory || this.home == null
				|| (this.home.getLocation() != null && Board.getClanAt(new CLocation(this.home.getLocation())) == this)) {
			return;
		}

		msg("<b>Your clan home has been un-set since it is no longer in your territory.");
		this.home = null;
	}

	// FIELD: lastPlayerLoggedOffTime
	private transient long lastPlayerLoggedOffTime;

	// FIELD: account (fake field)
	// Bank functions
	public double money;

	@Override
	public String getAccountId() {
		final String aid = "clan-" + this.getId();

		// We need to override the default money given to players.
		if (!Econ.hasAccount(aid)) {
			Econ.setBalance(aid, 0);
		}

		return aid;
	}

	// FIELD: permanentPower
	private Integer permanentPower;

	public Integer getPermanentPower() {
		return this.permanentPower;
	}

	public void setPermanentPower(final Integer permanentPower) {
		this.permanentPower = permanentPower;
	}

	public boolean hasPermanentPower() {
		return this.permanentPower != null;
	}

	// FIELD: powerBoost
	// special increase/decrease to default and max power for this clan
	private double powerBoost;

	public double getPowerBoost() {
		return this.powerBoost;
	}

	public void setPowerBoost(final double powerBoost) {
		this.powerBoost = powerBoost;
	}

	// -------------------------------------------- //
	// Construct
	// -------------------------------------------- //

	public Clan() {
		this.relationWish = new HashMap<String, Relation>();
		this.invites = new HashSet<String>();
		this.open = Conf.newClansDefaultOpen;
		this.tag = "???";
		this.description = "Default clan description :(";
		this.lastPlayerLoggedOffTime = 0;
		this.peaceful = false;
		this.peacefulExplosionsEnabled = false;
		this.permanent = false;
		this.money = 0.0;
		this.powerBoost = 0.0;
	}

	// -------------------------------------------- //
	// Extra Getters And Setters
	// -------------------------------------------- //

	public boolean noPvPInTerritory() {
		return isSafeZone() || (peaceful && Conf.peacefulTerritoryDisablePVP);
	}

	public boolean noMonstersInTerritory() {
		return isSafeZone() || (peaceful && Conf.peacefulTerritoryDisableMonsters);
	}

	// -------------------------------
	// Understand the types
	// -------------------------------

	public boolean isNormal() {
		return !(this.isNone() || this.isSafeZone() || this.isWarZone());
	}

	public boolean isNone() {
		return this.getId().equals("0");
	}

	public boolean isSafeZone() {
		return this.getId().equals("-1");
	}

	public boolean isWarZone() {
		return this.getId().equals("-2");
	}

	public boolean isPlayerFreeType() {
		return this.isSafeZone() || this.isWarZone();
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

	@Override
	public ChatColor getColorTo(final RelationParticipator rp) {
		return RelationUtil.getColorOfThatToMe(this, rp);
	}

	public Relation getRelationWish(final Clan otherClan) {
		if (this.relationWish.containsKey(otherClan.getId())) {
			return this.relationWish.get(otherClan.getId());
		}
		return Relation.NEUTRAL;
	}

	public void setRelationWish(final Clan otherClan, final Relation relation) {
		if (this.relationWish.containsKey(otherClan.getId()) && relation.equals(Relation.NEUTRAL)) {
			this.relationWish.remove(otherClan.getId());
		} else {
			this.relationWish.put(otherClan.getId(), relation);
		}
	}

	// ----------------------------------------------//
	// Power
	// ----------------------------------------------//
	public double getPower() {
		if (this.hasPermanentPower()) {
			return this.getPermanentPower();
		}

		double ret = 0;
		for (final CPlayer fplayer : fplayers) {
			ret += fplayer.getPower();
		}
		if (Conf.powerClanMax > 0 && ret > Conf.powerClanMax) {
			ret = Conf.powerClanMax;
		}
		return ret + this.powerBoost;
	}

	public double getPowerMax() {
		if (this.hasPermanentPower()) {
			return this.getPermanentPower();
		}

		double ret = 0;
		for (final CPlayer fplayer : fplayers) {
			ret += fplayer.getPowerMax();
		}
		if (Conf.powerClanMax > 0 && ret > Conf.powerClanMax) {
			ret = Conf.powerClanMax;
		}
		return ret + this.powerBoost;
	}

	public int getPowerRounded() {
		return (int) Math.round(this.getPower());
	}

	public int getPowerMaxRounded() {
		return (int) Math.round(this.getPowerMax());
	}

	public int getLandRounded() {
		return Board.getClanCoordCount(this);
	}

	public int getLandRoundedInWorld(final String worldName) {
		return Board.getClanCoordCountInWorld(this, worldName);
	}

	public boolean hasLandInflation() {
		return this.getLandRounded() > this.getPowerRounded();
	}

	// -------------------------------
	// CPlayers
	// -------------------------------

	// maintain the reference list of CPlayers in this clan
	public void refreshCPlayers() {
		fplayers.clear();
		if (this.isPlayerFreeType()) {
			return;
		}

		for (final CPlayer fplayer : CPlayers.i.get()) {
			if (fplayer.getClan() == this) {
				fplayers.add(fplayer);
			}
		}
	}

	protected boolean addCPlayer(final CPlayer fplayer) {
		if (this.isPlayerFreeType()) {
			return false;
		}

		return fplayers.add(fplayer);
	}

	protected boolean removeCPlayer(final CPlayer fplayer) {
		if (this.isPlayerFreeType()) {
			return false;
		}

		return fplayers.remove(fplayer);
	}

	public Set<CPlayer> getCPlayers() {
		// return a shallow copy of the CPlayer list, to prevent tampering and
		// concurrency issues
		final Set<CPlayer> ret = new HashSet<CPlayer>(fplayers);
		return ret;
	}

	public Set<CPlayer> getCPlayersWhereOnline(final boolean online) {
		final Set<CPlayer> ret = new HashSet<CPlayer>();

		for (final CPlayer fplayer : fplayers) {
			if (fplayer.isOnline() == online) {
				ret.add(fplayer);
			}
		}

		return ret;
	}

	public CPlayer getCPlayerAdmin() {
		if (!this.isNormal()) {
			return null;
		}

		for (final CPlayer fplayer : fplayers) {
			if (fplayer.getRole() == Role.ADMIN) {
				return fplayer;
			}
		}
		return null;
	}

	public ArrayList<CPlayer> getCPlayersWhereRole(final Role role) {
		final ArrayList<CPlayer> ret = new ArrayList<CPlayer>();
		if (!this.isNormal()) {
			return ret;
		}

		for (final CPlayer fplayer : fplayers) {
			if (fplayer.getRole() == role) {
				ret.add(fplayer);
			}
		}

		return ret;
	}

	public ArrayList<Player> getOnlinePlayers() {
		final ArrayList<Player> ret = new ArrayList<Player>();
		if (this.isPlayerFreeType()) {
			return ret;
		}

		for (final Player player : P.p.getServer().getOnlinePlayers()) {
			final CPlayer fplayer = CPlayers.i.get(player);
			if (fplayer.getClan() == this) {
				ret.add(player);
			}
		}

		return ret;
	}

	// slightly faster check than getOnlinePlayers() if you just want to see if
	// there are any players online
	public boolean hasPlayersOnline() {
		// only real clans can have players online, not safe zone / war zone
		if (this.isPlayerFreeType()) {
			return false;
		}

		for (final Player player : P.p.getServer().getOnlinePlayers()) {
			final CPlayer fplayer = CPlayers.i.get(player);
			if (fplayer.getClan() == this) {
				return true;
			}
		}

		// even if all players are technically logged off, maybe someone was on
		// recently enough to not consider them officially offline yet
		if (Conf.considerClansReallyOfflineAfterXMinutes > 0
				&& System.currentTimeMillis() < lastPlayerLoggedOffTime + (Conf.considerClansReallyOfflineAfterXMinutes * 60000)) {
			return true;
		}
		return false;
	}

	public void memberLoggedOff() {
		if (this.isNormal()) {
			lastPlayerLoggedOffTime = System.currentTimeMillis();
		}
	}

	// used when current leader is about to be removed from the clan; promotes
	// new leader, or disbands clan if no other members left
	public void promoteNewLeader() {
		if (!this.isNormal()) {
			return;
		}
		if (this.isPermanent() && Conf.permanentClansDisableLeaderPromotion) {
			return;
		}

		final CPlayer oldLeader = this.getCPlayerAdmin();

		// get list of moderators, or list of normal members if there are no
		// moderators
		ArrayList<CPlayer> replacements = this.getCPlayersWhereRole(Role.MODERATOR);
		if (replacements == null || replacements.isEmpty()) {
			replacements = this.getCPlayersWhereRole(Role.NORMAL);
		}

		if (replacements == null || replacements.isEmpty()) { // clan admin is
																// the only
																// member;
																// one-man clan
			if (this.isPermanent()) {
				if (oldLeader != null) {
					oldLeader.setRole(Role.NORMAL);
				}
				return;
			}

			// no members left and clan isn't permanent, so disband it
			if (Conf.logClanDisband) {
				P.p.log("The clan " + this.getTag() + " (" + this.getId() + ") has been disbanded since it has no members left.");
			}

			for (final CPlayer fplayer : CPlayers.i.getOnline()) {
				fplayer.msg("The clan %s<i> was disbanded.", this.getTag(fplayer));
			}

			this.detach();
		} else { // promote new clan admin
			if (oldLeader != null) {
				oldLeader.setRole(Role.NORMAL);
			}
			replacements.get(0).setRole(Role.ADMIN);
			this.msg("<i>Clan admin <h>%s<i> has been removed. %s<i> has been promoted as the new clan admin.", oldLeader == null ? "" : oldLeader.getName(),
					replacements.get(0).getName());
			P.p.log("Clan " + this.getTag() + " (" + this.getId() + ") admin was removed. Replacement admin: " + replacements.get(0).getName());
		}
	}

	// ----------------------------------------------//
	// Messages
	// ----------------------------------------------//
	@Override
	public void msg(String message, final Object... args) {
		message = P.p.txt.parse(message, args);

		for (final CPlayer fplayer : this.getCPlayersWhereOnline(true)) {
			fplayer.sendMessage(message);
		}
	}

	public void sendMessage(final String message) {
		for (final CPlayer fplayer : this.getCPlayersWhereOnline(true)) {
			fplayer.sendMessage(message);
		}
	}

	public void sendMessage(final List<String> messages) {
		for (final CPlayer fplayer : this.getCPlayersWhereOnline(true)) {
			fplayer.sendMessage(messages);
		}
	}

	// ----------------------------------------------//
	// Ownership of specific claims
	// ----------------------------------------------//

	public void clearAllClaimOwnership() {
		claimOwnership.clear();
	}

	public void clearClaimOwnership(final CLocation loc) {
		if (Conf.onUnclaimResetLwcLocks && LWCFeatures.getEnabled()) {
			LWCFeatures.clearAllChests(loc);
		}
		claimOwnership.remove(loc);
	}

	public void clearClaimOwnership(final String playerName) {
		if (playerName == null || playerName.isEmpty()) {
			return;
		}

		Set<String> ownerData;
		final String player = playerName.toLowerCase();

		for (final Entry<CLocation, Set<String>> entry : claimOwnership.entrySet()) {
			ownerData = entry.getValue();

			if (ownerData == null) {
				continue;
			}

			final Iterator<String> iter = ownerData.iterator();
			while (iter.hasNext()) {
				if (iter.next().equals(player)) {
					iter.remove();
				}
			}

			if (ownerData.isEmpty()) {
				if (Conf.onUnclaimResetLwcLocks && LWCFeatures.getEnabled()) {
					LWCFeatures.clearAllChests(entry.getKey());
				}
				claimOwnership.remove(entry.getKey());
			}
		}
	}

	public int getCountOfClaimsWithOwners() {
		return claimOwnership.isEmpty() ? 0 : claimOwnership.size();
	}

	public boolean doesLocationHaveOwnersSet(final CLocation loc) {
		if (claimOwnership.isEmpty() || !claimOwnership.containsKey(loc)) {
			return false;
		}

		final Set<String> ownerData = claimOwnership.get(loc);
		return ownerData != null && !ownerData.isEmpty();
	}

	public boolean isPlayerInOwnerList(final String playerName, final CLocation loc) {
		if (claimOwnership.isEmpty()) {
			return false;
		}
		final Set<String> ownerData = claimOwnership.get(loc);
		if (ownerData == null) {
			return false;
		}
		if (ownerData.contains(playerName.toLowerCase())) {
			return true;
		}

		return false;
	}

	public void setPlayerAsOwner(final String playerName, final CLocation loc) {
		Set<String> ownerData = claimOwnership.get(loc);
		if (ownerData == null) {
			ownerData = new HashSet<String>();
		}
		ownerData.add(playerName.toLowerCase());
		claimOwnership.put(loc, ownerData);
	}

	public void removePlayerAsOwner(final String playerName, final CLocation loc) {
		final Set<String> ownerData = claimOwnership.get(loc);
		if (ownerData == null) {
			return;
		}
		ownerData.remove(playerName.toLowerCase());
		claimOwnership.put(loc, ownerData);
	}

	public Set<String> getOwnerList(final CLocation loc) {
		return claimOwnership.get(loc);
	}

	public String getOwnerListString(final CLocation loc) {
		final Set<String> ownerData = claimOwnership.get(loc);
		if (ownerData == null || ownerData.isEmpty()) {
			return "";
		}

		String ownerList = "";

		final Iterator<String> iter = ownerData.iterator();
		while (iter.hasNext()) {
			if (!ownerList.isEmpty()) {
				ownerList += ", ";
			}
			ownerList += iter.next();
		}
		return ownerList;
	}

	public boolean playerHasOwnershipRights(final CPlayer fplayer, final CLocation loc) {
		// in own clan, with sufficient role or permission to bypass ownership?
		if (fplayer.getClan() == this
				&& (fplayer.getRole().isAtLeast(Conf.ownedAreaModeratorsBypass ? Role.MODERATOR : Role.ADMIN) || Permission.OWNERSHIP_BYPASS.has(fplayer
						.getPlayer()))) {
			return true;
		}

		// make sure claimOwnership is initialized
		if (claimOwnership.isEmpty()) {
			return true;
		}

		// need to check the ownership list, then
		final Set<String> ownerData = claimOwnership.get(loc);

		// if no owner list, owner list is empty, or player is in owner list,
		// they're allowed
		if (ownerData == null || ownerData.isEmpty() || ownerData.contains(fplayer.getName().toLowerCase())) {
			return true;
		}

		return false;
	}

	// ----------------------------------------------//
	// Persistance and entity management
	// ----------------------------------------------//

	@Override
	public void postDetach() {
		if (Econ.shouldBeUsed()) {
			Econ.setBalance(getAccountId(), 0);
		}

		// Clean the board
		Board.clean();

		// Clean the fplayers
		CPlayers.i.clean();
	}
}
