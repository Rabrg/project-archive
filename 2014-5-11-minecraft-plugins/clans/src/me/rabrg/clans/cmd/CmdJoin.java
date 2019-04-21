package me.rabrg.clans.cmd;

import org.bukkit.Bukkit;

import me.rabrg.clans.CPlayer;
import me.rabrg.clans.CPlayers;
import me.rabrg.clans.Clan;
import me.rabrg.clans.Conf;
import me.rabrg.clans.P;
import me.rabrg.clans.event.CPlayerJoinEvent;
import me.rabrg.clans.struct.Permission;

public class CmdJoin extends FCommand {
	public CmdJoin() {
		super();
		this.aliases.add("join");

		this.requiredArgs.add("clan name");
		this.optionalArgs.put("player", "you");

		this.permission = Permission.JOIN.node;
		this.disableOnLock = true;

		senderMustBePlayer = true;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		final Clan clan = this.argAsClan(0);
		if (clan == null) {
			return;
		}

		final CPlayer fplayer = this.argAsBestCPlayerMatch(1, fme, false);
		final boolean samePlayer = fplayer == fme;

		if (!samePlayer && !Permission.JOIN_OTHERS.has(sender, false)) {
			msg("<b>You do not have permission to move other players into a clan.");
			return;
		}

		if (!clan.isNormal()) {
			msg("<b>Players may only join normal clans. This is a system clan.");
			return;
		}

		if (clan == fplayer.getClan()) {
			msg("<b>%s %s already a member of %s", fplayer.describeTo(fme, true), (samePlayer ? "are" : "is"), clan.getTag(fme));
			return;
		}

		if (Conf.clanMemberLimit > 0 && clan.getCPlayers().size() >= Conf.clanMemberLimit) {
			msg(" <b>!<white> The clan %s is at the limit of %d members, so %s cannot currently join.", clan.getTag(fme), Conf.clanMemberLimit,
					fplayer.describeTo(fme, false));
			return;
		}

		if (fplayer.hasClan()) {
			msg("<b>%s must leave %s current clan first.", fplayer.describeTo(fme, true), (samePlayer ? "your" : "their"));
			return;
		}

		if (!Conf.canLeaveWithNegativePower && fplayer.getPower() < 0) {
			msg("<b>%s cannot join a clan with a negative power level.", fplayer.describeTo(fme, true));
			return;
		}

		if (!(clan.getOpen() || clan.isInvited(fplayer) || fme.isAdminBypassing() || Permission.JOIN_ANY.has(sender, false))) {
			msg("<i>This clan requires invitation.");
			if (samePlayer) {
				clan.msg("%s<i> tried to join your clan.", fplayer.describeTo(clan, true));
			}
			return;
		}

		// if economy is enabled, they're not on the bypass list, and this
		// command has a cost set, make sure they can pay
		if (samePlayer && !canAffordCommand(Conf.econCostJoin, "to join a clan")) {
			return;
		}

		// trigger the join event (cancellable)
		final CPlayerJoinEvent joinEvent = new CPlayerJoinEvent(CPlayers.i.get(me), clan, CPlayerJoinEvent.PlayerJoinReason.COMMAND);
		Bukkit.getServer().getPluginManager().callEvent(joinEvent);
		if (joinEvent.isCancelled()) {
			return;
		}

		// then make 'em pay (if applicable)
		if (samePlayer && !payForCommand(Conf.econCostJoin, "to join a clan", "for joining a clan")) {
			return;
		}

		fme.msg("<i>%s successfully joined %s.", fplayer.describeTo(fme, true), clan.getTag(fme));

		if (!samePlayer) {
			fplayer.msg("<i>%s moved you into the clan %s.", fme.describeTo(fplayer, true), clan.getTag(fplayer));
		}
		clan.msg("<i>%s joined your clan.", fplayer.describeTo(clan, true));

		fplayer.resetClanData();
		fplayer.setClan(clan);
		clan.deinvite(fplayer);

		if (Conf.logClanJoin) {
			if (samePlayer) {
				P.p.log("%s joined the clan %s.", fplayer.getName(), clan.getTag());
			} else {
				P.p.log("%s moved the player %s into the clan %s.", fme.getName(), fplayer.getName(), clan.getTag());
			}
		}
	}
}
