package me.rabrg.clans.cmd;

import me.rabrg.clans.CPlayer;
import me.rabrg.clans.CPlayers;
import me.rabrg.clans.Conf;
import me.rabrg.clans.struct.Permission;
import me.rabrg.clans.zcore.util.TextUtil;

public class CmdDescription extends FCommand {
	public CmdDescription() {
		super();
		this.aliases.add("desc");

		this.requiredArgs.add("desc");
		this.errorOnToManyArgs = false;
		// this.optionalArgs

		this.permission = Permission.DESCRIPTION.node;
		this.disableOnLock = true;

		senderMustBePlayer = true;
		senderMustBeMember = false;
		senderMustBeModerator = true;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		// if economy is enabled, they're not on the bypass list, and this
		// command has a cost set, make 'em pay
		if (!payForCommand(Conf.econCostDesc, "to change clan description", "for changing clan description")) {
			return;
		}

		myClan.setDescription(TextUtil.implode(args, " ").replaceAll("(&([a-f0-9]))", "& $2")); // since
																								// "&"
																								// color
																								// tags
																								// seem
																								// to
																								// work
																								// even
																								// through
																								// plain
																								// old
																								// CPlayer.sendMessage()
																								// for
																								// some
																								// reason,
																								// we
																								// need
																								// to
																								// break
																								// those
																								// up

		if (!Conf.broadcastDescriptionChanges) {
			fme.msg("You have changed the description for <h>%s<i> to:", myClan.describeTo(fme));
			fme.sendMessage(myClan.getDescription());
			return;
		}

		// Broadcast the description to everyone
		for (final CPlayer fplayer : CPlayers.i.getOnline()) {
			fplayer.msg("<i>The clan %s<i> changed their description to:", myClan.describeTo(fplayer));
			fplayer.sendMessage(myClan.getDescription()); // players can inject
															// "&" or "`" or
															// "<i>" or whatever
															// in their
															// description; &k
															// is particularly
															// interesting
															// looking
		}
	}

}
