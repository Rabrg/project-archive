package me.rabrg.clans.cmd;

import me.rabrg.clans.CPlayer;
import me.rabrg.clans.CPlayers;
import me.rabrg.clans.Clan;
import me.rabrg.clans.P;
import me.rabrg.clans.struct.Permission;

public class CmdPermanent extends FCommand {
	public CmdPermanent() {
		super();
		this.aliases.add("permanent");

		this.requiredArgs.add("clan tag");
		// this.optionalArgs.put("", "");

		this.permission = Permission.SET_PERMANENT.node;
		this.disableOnLock = true;

		senderMustBePlayer = false;
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

		String change;
		if (clan.isPermanent()) {
			change = "removed permanent status from";
			clan.setPermanent(false);
		} else {
			change = "added permanent status to";
			clan.setPermanent(true);
		}

		P.p.log((fme == null ? "A server admin" : fme.getName()) + " " + change + " the clan \"" + clan.getTag() + "\".");

		// Inform all players
		for (final CPlayer fplayer : CPlayers.i.getOnline()) {
			if (fplayer.getClan() == clan) {
				fplayer.msg((fme == null ? "A server admin" : fme.describeTo(fplayer, true)) + "<i> " + change + " your clan.");
			} else {
				fplayer.msg((fme == null ? "A server admin" : fme.describeTo(fplayer, true)) + "<i> " + change + " the clan \"" + clan.getTag(fplayer) + "\".");
			}
		}
	}
}
