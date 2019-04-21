package me.rabrg.clans.cmd;

import java.util.ArrayList;

import org.bukkit.Bukkit;

import me.rabrg.clans.Clan;
import me.rabrg.clans.Clans;
import me.rabrg.clans.Conf;
import me.rabrg.clans.event.ClanRenameEvent;
import me.rabrg.clans.integration.SpoutFeatures;
import me.rabrg.clans.struct.Permission;
import me.rabrg.clans.util.MiscUtil;

public class CmdTag extends FCommand {

	public CmdTag() {
		this.aliases.add("tag");

		this.requiredArgs.add("clan tag");
		// this.optionalArgs.put("", "");

		this.permission = Permission.TAG.node;
		this.disableOnLock = true;

		senderMustBePlayer = true;
		senderMustBeMember = false;
		senderMustBeModerator = true;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		final String tag = this.argAsString(0);

		// TODO does not first test cover selfcase?
		if (Clans.i.isTagTaken(tag) && !MiscUtil.getComparisonString(tag).equals(myClan.getComparisonTag())) {
			msg("<b>That tag is already taken");
			return;
		}

		final ArrayList<String> errors = new ArrayList<String>();
		errors.addAll(Clans.validateTag(tag));
		if (errors.size() > 0) {
			sendMessage(errors);
			return;
		}

		// if economy is enabled, they're not on the bypass list, and this
		// command has a cost set, make sure they can pay
		if (!canAffordCommand(Conf.econCostTag, "to change the clan tag")) {
			return;
		}

		// trigger the clan rename event (cancellable)
		final ClanRenameEvent renameEvent = new ClanRenameEvent(fme, tag);
		Bukkit.getServer().getPluginManager().callEvent(renameEvent);
		if (renameEvent.isCancelled()) {
			return;
		}

		// then make 'em pay (if applicable)
		if (!payForCommand(Conf.econCostTag, "to change the clan tag", "for changing the clan tag")) {
			return;
		}

		final String oldtag = myClan.getTag();
		myClan.setTag(tag);

		// Inform
		myClan.msg("%s<i> changed your clan tag to %s", fme.describeTo(myClan, true), myClan.getTag(myClan));
		for (final Clan clan : Clans.i.get()) {
			if (clan == myClan) {
				continue;
			}
			clan.msg("<i>The clan %s<i> changed their name to %s.", fme.getColorTo(clan) + oldtag, myClan.getTag(clan));
		}

		if (Conf.spoutClanTagsOverNames) {
			SpoutFeatures.updateAppearances(myClan);
		}
	}

}
