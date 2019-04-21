package me.rabrg.clans.cmd;

import me.rabrg.clans.CPlayer;
import me.rabrg.clans.CPlayers;
import me.rabrg.clans.Clan;
import me.rabrg.clans.integration.SpoutFeatures;
import me.rabrg.clans.struct.Permission;

public class CmdPeaceful extends FCommand {

	public CmdPeaceful() {
		super();
		this.aliases.add("peaceful");

		this.requiredArgs.add("clan tag");
		// this.optionalArgs.put("", "");

		this.permission = Permission.SET_PEACEFUL.node;
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
		if (clan.isPeaceful()) {
			change = "removed peaceful status from";
			clan.setPeaceful(false);
		} else {
			change = "granted peaceful status to";
			clan.setPeaceful(true);
		}

		// Inform all players
		for (final CPlayer fplayer : CPlayers.i.getOnline()) {
			if (fplayer.getClan() == clan) {
				fplayer.msg((fme == null ? "A server admin" : fme.describeTo(fplayer, true)) + "<i> has " + change + " your clan.");
			} else {
				fplayer.msg((fme == null ? "A server admin" : fme.describeTo(fplayer, true)) + "<i> has " + change + " the clan \"" + clan.getTag(fplayer)
						+ "<i>\".");
			}
		}

		SpoutFeatures.updateAppearances(clan);
	}

}
