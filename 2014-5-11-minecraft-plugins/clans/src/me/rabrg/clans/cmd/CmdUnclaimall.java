package me.rabrg.clans.cmd;

import org.bukkit.Bukkit;

import me.rabrg.clans.Board;
import me.rabrg.clans.Conf;
import me.rabrg.clans.P;
import me.rabrg.clans.event.LandUnclaimAllEvent;
import me.rabrg.clans.integration.Econ;
import me.rabrg.clans.integration.SpoutFeatures;
import me.rabrg.clans.struct.Permission;

public class CmdUnclaimall extends FCommand {
	public CmdUnclaimall() {
		this.aliases.add("unclaimall");
		this.aliases.add("declaimall");

		// this.requiredArgs.add("");
		// this.optionalArgs.put("", "");

		this.permission = Permission.UNCLAIM_ALL.node;
		this.disableOnLock = true;

		senderMustBePlayer = true;
		senderMustBeMember = false;
		senderMustBeModerator = true;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		if (Econ.shouldBeUsed()) {
			final double refund = Econ.calculateTotalLandRefund(myClan.getLandRounded());
			if (Conf.bankEnabled && Conf.bankClanPaysLandCosts) {
				if (!Econ.modifyMoney(myClan, refund, "to unclaim all clan land", "for unclaiming all clan land")) {
					return;
				}
			} else {
				if (!Econ.modifyMoney(fme, refund, "to unclaim all clan land", "for unclaiming all clan land")) {
					return;
				}
			}
		}

		final LandUnclaimAllEvent unclaimAllEvent = new LandUnclaimAllEvent(myClan, fme);
		Bukkit.getServer().getPluginManager().callEvent(unclaimAllEvent);
		// this event cannot be cancelled

		Board.unclaimAll(myClan.getId());
		myClan.msg("%s<i> unclaimed ALL of your clan's land.", fme.describeTo(myClan, true));
		SpoutFeatures.updateTerritoryDisplayLoc(null);

		if (Conf.logLandUnclaims) {
			P.p.log(fme.getName() + " unclaimed everything for the clan: " + myClan.getTag());
		}
	}

}
