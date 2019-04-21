package me.rabrg.clans.cmd;

import org.bukkit.Bukkit;

import me.rabrg.clans.CPlayer;
import me.rabrg.clans.CPlayers;
import me.rabrg.clans.Clan;
import me.rabrg.clans.Conf;
import me.rabrg.clans.P;
import me.rabrg.clans.event.CPlayerLeaveEvent;
import me.rabrg.clans.event.ClanDisbandEvent;
import me.rabrg.clans.integration.Econ;
import me.rabrg.clans.integration.SpoutFeatures;
import me.rabrg.clans.struct.Permission;
import me.rabrg.clans.struct.Role;

public class CmdDisband extends FCommand {
	public CmdDisband() {
		super();
		this.aliases.add("disband");

		// this.requiredArgs.add("");
		this.optionalArgs.put("clan tag", "yours");

		this.permission = Permission.DISBAND.node;
		this.disableOnLock = true;

		senderMustBePlayer = false;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		// The clan, default to your own.. but null if console sender.
		final Clan clan = this.argAsClan(0, fme == null ? null : myClan);
		if (clan == null) {
			return;
		}

		final boolean isMyClan = fme == null ? false : clan == myClan;

		if (isMyClan) {
			if (!assertMinRole(Role.ADMIN)) {
				return;
			}
		} else {
			if (!Permission.DISBAND_ANY.has(sender, true)) {
				return;
			}
		}

		if (!clan.isNormal()) {
			msg("<i>You cannot disband the Wilderness, SafeZone, or WarZone.");
			return;
		}
		if (clan.isPermanent()) {
			msg("<i>This clan is designated as permanent, so you cannot disband it.");
			return;
		}

		final ClanDisbandEvent disbandEvent = new ClanDisbandEvent(me, clan.getId());
		Bukkit.getServer().getPluginManager().callEvent(disbandEvent);
		if (disbandEvent.isCancelled()) {
			return;
		}

		// Send CPlayerLeaveEvent for each player in the clan
		for (final CPlayer fplayer : clan.getCPlayers()) {
			Bukkit.getServer().getPluginManager().callEvent(new CPlayerLeaveEvent(fplayer, clan, CPlayerLeaveEvent.PlayerLeaveReason.DISBAND));
		}

		// Inform all players
		for (final CPlayer fplayer : CPlayers.i.getOnline()) {
			final String who = senderIsConsole ? "A server admin" : fme.describeTo(fplayer);
			if (fplayer.getClan() == clan) {
				fplayer.msg("<h>%s<i> disbanded your clan.", who);
			} else {
				fplayer.msg("<h>%s<i> disbanded the clan %s.", who, clan.getTag(fplayer));
			}
		}
		if (Conf.logClanDisband) {
			P.p.log("The clan " + clan.getTag() + " (" + clan.getId() + ") was disbanded by " + (senderIsConsole ? "console command" : fme.getName()) + ".");
		}

		if (Econ.shouldBeUsed() && !senderIsConsole) {
			// Give all the clan's money to the disbander
			final double amount = Econ.getBalance(clan.getAccountId());
			Econ.transferMoney(fme, clan, fme, amount, false);

			if (amount > 0.0) {
				final String amountString = Econ.moneyString(amount);
				msg("<i>You have been given the disbanded clan's bank, totaling %s.", amountString);
				P.p.log(fme.getName() + " has been given bank holdings of " + amountString + " from disbanding " + clan.getTag() + ".");
			}
		}

		clan.detach();

		SpoutFeatures.updateAppearances();
	}
}
