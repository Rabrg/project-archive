package me.rabrg.clans.cmd;

import me.rabrg.clans.CPlayer;
import me.rabrg.clans.Clan;
import me.rabrg.clans.struct.Permission;

public class CmdPermanentPower extends FCommand {
	public CmdPermanentPower() {
		super();
		this.aliases.add("permanentpower");

		this.requiredArgs.add("clan");
		this.optionalArgs.put("power", "reset");

		this.permission = Permission.SET_PERMANENTPOWER.node;
		this.disableOnLock = true;

		senderMustBePlayer = false;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		final Clan targetClan = this.argAsClan(0);
		if (targetClan == null) {
			return;
		}

		final Integer targetPower = this.argAsInt(1);

		targetClan.setPermanentPower(targetPower);

		String change = "removed permanentpower status from";
		if (targetClan.hasPermanentPower()) {
			change = "added permanentpower status to";
		}

		msg("<i>You %s <h>%s<i>.", change, targetClan.describeTo(fme));

		// Inform all players
		for (final CPlayer fplayer : targetClan.getCPlayersWhereOnline(true)) {
			fplayer.msg((fme == null ? "A server admin" : fme.describeTo(fplayer, true)) + "<i> " + change + " your clan.");
		}
	}
}
