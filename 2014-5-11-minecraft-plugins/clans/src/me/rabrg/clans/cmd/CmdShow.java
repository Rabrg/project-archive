package me.rabrg.clans.cmd;

import java.util.Collection;

import me.rabrg.clans.CPlayer;
import me.rabrg.clans.Clan;
import me.rabrg.clans.Clans;
import me.rabrg.clans.Conf;
import me.rabrg.clans.integration.Econ;
import me.rabrg.clans.struct.Permission;
import me.rabrg.clans.struct.Relation;
import me.rabrg.clans.struct.Role;

public class CmdShow extends FCommand {

	public CmdShow() {
		this.aliases.add("show");
		this.aliases.add("who");

		// this.requiredArgs.add("");
		this.optionalArgs.put("clan tag", "yours");

		this.permission = Permission.SHOW.node;
		this.disableOnLock = false;

		senderMustBePlayer = true;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		Clan clan = myClan;
		if (this.argIsSet(0)) {
			clan = this.argAsClan(0);
			if (clan == null) {
				return;
			}
		}

		// if economy is enabled, they're not on the bypass list, and this
		// command has a cost set, make 'em pay
		if (!payForCommand(Conf.econCostShow, "to show clan information", "for showing clan information")) {
			return;
		}

		final Collection<CPlayer> admins = clan.getCPlayersWhereRole(Role.ADMIN);
		final Collection<CPlayer> mods = clan.getCPlayersWhereRole(Role.MODERATOR);
		final Collection<CPlayer> normals = clan.getCPlayersWhereRole(Role.NORMAL);

		msg(p.txt.titleize(clan.getTag(fme)));
		msg("<a>Description: <i>%s", clan.getDescription());
		if (!clan.isNormal()) {
			return;
		}

		String peaceStatus = "";
		if (clan.isPeaceful()) {
			peaceStatus = "     " + Conf.colorNeutral + "This clan is Peaceful";
		}

		msg("<a>Joining: <i>" + (clan.getOpen() ? "no invitation is needed" : "invitation is required") + peaceStatus);

		final double powerBoost = clan.getPowerBoost();
		final String boost = (powerBoost == 0.0) ? "" : (powerBoost > 0.0 ? " (bonus: " : " (penalty: ") + powerBoost + ")";
		msg("<a>Land / Power / Maxpower: <i> %d/%d/%d %s", clan.getLandRounded(), clan.getPowerRounded(), clan.getPowerMaxRounded(), boost);

		if (clan.isPermanent()) {
			msg("<a>This clan is permanent, remaining even with no members.");
		}

		// show the land value
		if (Econ.shouldBeUsed()) {
			final double value = Econ.calculateTotalLandValue(clan.getLandRounded());
			final double refund = value * Conf.econClaimRefundMultiplier;
			if (value > 0) {
				final String stringValue = Econ.moneyString(value);
				final String stringRefund = (refund > 0.0) ? (" (" + Econ.moneyString(refund) + " depreciated)") : "";
				msg("<a>Total land value: <i>" + stringValue + stringRefund);
			}

			// Show bank contents
			if (Conf.bankEnabled) {
				msg("<a>Bank contains: <i>" + Econ.moneyString(Econ.getBalance(clan.getAccountId())));
			}
		}

		String listpart;

		// List relation
		String allyList = p.txt.parse("<a>Allies: ");
		String enemyList = p.txt.parse("<a>Enemies: ");
		for (final Clan otherClan : Clans.i.get()) {
			if (otherClan == clan) {
				continue;
			}

			final Relation rel = otherClan.getRelationTo(clan);
			if (!rel.isAlly() && !rel.isEnemy()) {
				continue; // if not ally or enemy, drop out now so we're not
							// wasting time on it; good performance boost
			}

			listpart = otherClan.getTag(fme) + p.txt.parse("<i>") + ", ";
			if (rel.isAlly()) {
				allyList += listpart;
			} else if (rel.isEnemy()) {
				enemyList += listpart;
			}
		}
		if (allyList.endsWith(", ")) {
			allyList = allyList.substring(0, allyList.length() - 2);
		}
		if (enemyList.endsWith(", ")) {
			enemyList = enemyList.substring(0, enemyList.length() - 2);
		}

		sendMessage(allyList);
		sendMessage(enemyList);

		// List the members...
		String onlineList = p.txt.parse("<a>") + "Members online: ";
		String offlineList = p.txt.parse("<a>") + "Members offline: ";
		for (final CPlayer follower : admins) {
			listpart = follower.getNameAndTitle(fme) + p.txt.parse("<i>") + ", ";
			if (follower.isOnlineAndVisibleTo(me)) {
				onlineList += listpart;
			} else {
				offlineList += listpart;
			}
		}
		for (final CPlayer follower : mods) {
			listpart = follower.getNameAndTitle(fme) + p.txt.parse("<i>") + ", ";
			if (follower.isOnlineAndVisibleTo(me)) {
				onlineList += listpart;
			} else {
				offlineList += listpart;
			}
		}
		for (final CPlayer follower : normals) {
			listpart = follower.getNameAndTitle(fme) + p.txt.parse("<i>") + ", ";
			if (follower.isOnlineAndVisibleTo(me)) {
				onlineList += listpart;
			} else {
				offlineList += listpart;
			}
		}

		if (onlineList.endsWith(", ")) {
			onlineList = onlineList.substring(0, onlineList.length() - 2);
		}
		if (offlineList.endsWith(", ")) {
			offlineList = offlineList.substring(0, offlineList.length() - 2);
		}

		sendMessage(onlineList);
		sendMessage(offlineList);
	}

}
