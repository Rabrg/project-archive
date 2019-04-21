package me.rabrg.clans.cmd;

import org.bukkit.Bukkit;

import me.rabrg.clans.Board;
import me.rabrg.clans.CLocation;
import me.rabrg.clans.Clan;
import me.rabrg.clans.Conf;
import me.rabrg.clans.P;
import me.rabrg.clans.event.LandUnclaimEvent;
import me.rabrg.clans.integration.Econ;
import me.rabrg.clans.integration.SpoutFeatures;
import me.rabrg.clans.struct.Permission;
import me.rabrg.clans.struct.Role;

public class CmdUnclaim extends FCommand {
	public CmdUnclaim() {
		this.aliases.add("unclaim");
		this.aliases.add("declaim");

		// this.requiredArgs.add("");
		// this.optionalArgs.put("", "");

		this.permission = Permission.UNCLAIM.node;
		this.disableOnLock = true;

		senderMustBePlayer = true;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		final CLocation flocation = new CLocation(fme);
		final Clan otherClan = Board.getClanAt(flocation);

		if (otherClan.isSafeZone()) {
			if (Permission.MANAGE_SAFE_ZONE.has(sender)) {
				Board.removeAt(flocation);
				SpoutFeatures.updateTerritoryDisplayLoc(flocation);
				msg("<i>Safe zone was unclaimed.");

				if (Conf.logLandUnclaims) {
					P.p.log(fme.getName() + " unclaimed land at (" + flocation.getCoordString() + ") from the clan: " + otherClan.getTag());
				}
			} else {
				msg("<b>This is a safe zone. You lack permissions to unclaim.");
			}
			return;
		} else if (otherClan.isWarZone()) {
			if (Permission.MANAGE_WAR_ZONE.has(sender)) {
				Board.removeAt(flocation);
				SpoutFeatures.updateTerritoryDisplayLoc(flocation);
				msg("<i>War zone was unclaimed.");

				if (Conf.logLandUnclaims) {
					P.p.log(fme.getName() + " unclaimed land at (" + flocation.getCoordString() + ") from the clan: " + otherClan.getTag());
				}
			} else {
				msg("<b>This is a war zone. You lack permissions to unclaim.");
			}
			return;
		}

		if (fme.isAdminBypassing()) {
			Board.removeAt(flocation);
			SpoutFeatures.updateTerritoryDisplayLoc(flocation);

			otherClan.msg("%s<i> unclaimed some of your land.", fme.describeTo(otherClan, true));
			msg("<i>You unclaimed this land.");

			if (Conf.logLandUnclaims) {
				P.p.log(fme.getName() + " unclaimed land at (" + flocation.getCoordString() + ") from the clan: " + otherClan.getTag());
			}

			return;
		}

		if (!assertHasClan()) {
			return;
		}

		if (!assertMinRole(Role.MODERATOR)) {
			return;
		}

		if (myClan != otherClan) {
			msg("<b>You don't own this land.");
			return;
		}

		final LandUnclaimEvent unclaimEvent = new LandUnclaimEvent(flocation, otherClan, fme);
		Bukkit.getServer().getPluginManager().callEvent(unclaimEvent);
		if (unclaimEvent.isCancelled()) {
			return;
		}

		if (Econ.shouldBeUsed()) {
			final double refund = Econ.calculateClaimRefund(myClan.getLandRounded());

			if (Conf.bankEnabled && Conf.bankClanPaysLandCosts) {
				if (!Econ.modifyMoney(myClan, refund, "to unclaim this land", "for unclaiming this land")) {
					return;
				}
			} else {
				if (!Econ.modifyMoney(fme, refund, "to unclaim this land", "for unclaiming this land")) {
					return;
				}
			}
		}

		Board.removeAt(flocation);
		SpoutFeatures.updateTerritoryDisplayLoc(flocation);
		myClan.msg("%s<i> unclaimed some land.", fme.describeTo(myClan, true));

		if (Conf.logLandUnclaims) {
			P.p.log(fme.getName() + " unclaimed land at (" + flocation.getCoordString() + ") from the clan: " + otherClan.getTag());
		}
	}

}
