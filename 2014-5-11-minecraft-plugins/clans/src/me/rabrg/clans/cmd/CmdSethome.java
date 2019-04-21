package me.rabrg.clans.cmd;

import me.rabrg.clans.Board;
import me.rabrg.clans.CLocation;
import me.rabrg.clans.Clan;
import me.rabrg.clans.Conf;
import me.rabrg.clans.struct.Permission;
import me.rabrg.clans.struct.Role;

public class CmdSethome extends FCommand {
	public CmdSethome() {
		this.aliases.add("sethome");

		// this.requiredArgs.add("");
		this.optionalArgs.put("clan tag", "mine");

		this.permission = Permission.SETHOME.node;
		this.disableOnLock = true;

		senderMustBePlayer = true;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		if (!Conf.homesEnabled) {
			fme.msg("<b>Sorry, Clan homes are disabled on this server.");
			return;
		}

		final Clan clan = this.argAsClan(0, myClan);
		if (clan == null) {
			return;
		}

		// Can the player set the home for this clan?
		if (clan == myClan) {
			if (!Permission.SETHOME_ANY.has(sender) && !assertMinRole(Role.MODERATOR)) {
				return;
			}
		} else {
			if (!Permission.SETHOME_ANY.has(sender, true)) {
				return;
			}
		}

		// Can the player set the clan home HERE?
		if (!Permission.BYPASS.has(me) && Conf.homesMustBeInClaimedTerritory && Board.getClanAt(new CLocation(me)) != clan) {
			fme.msg("<b>Sorry, your clan home can only be set inside your own claimed territory.");
			return;
		}

		// if economy is enabled, they're not on the bypass list, and this
		// command has a cost set, make 'em pay
		if (!payForCommand(Conf.econCostSethome, "to set the clan home", "for setting the clan home")) {
			return;
		}

		clan.setHome(me.getLocation());

		clan.msg("%s<i> set the home for your clan. You can now use:", fme.describeTo(myClan, true));
		clan.sendMessage(p.cmdBase.cmdHome.getUseageTemplate());
		if (clan != myClan) {
			fme.msg("<b>You have set the home for the " + clan.getTag(fme) + "<i> clan.");
		}
	}

}
