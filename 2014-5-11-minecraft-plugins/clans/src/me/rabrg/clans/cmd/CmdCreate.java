package me.rabrg.clans.cmd;

import java.util.ArrayList;

import org.bukkit.Bukkit;

import me.rabrg.clans.CPlayer;
import me.rabrg.clans.CPlayers;
import me.rabrg.clans.Clan;
import me.rabrg.clans.Clans;
import me.rabrg.clans.Conf;
import me.rabrg.clans.P;
import me.rabrg.clans.event.CPlayerJoinEvent;
import me.rabrg.clans.event.ClanCreateEvent;
import me.rabrg.clans.struct.Permission;
import me.rabrg.clans.struct.Role;

public class CmdCreate extends FCommand {
	public CmdCreate() {
		super();
		this.aliases.add("create");

		this.requiredArgs.add("clan tag");
		// this.optionalArgs.put("", "");

		this.permission = Permission.CREATE.node;
		this.disableOnLock = true;

		senderMustBePlayer = true;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		final String tag = this.argAsString(0);

		if (fme.hasClan()) {
			msg("<b>You must leave your current clan first.");
			return;
		}

		if (Clans.i.isTagTaken(tag)) {
			msg("<b>That tag is already in use.");
			return;
		}

		final ArrayList<String> tagValidationErrors = Clans.validateTag(tag);
		if (tagValidationErrors.size() > 0) {
			sendMessage(tagValidationErrors);
			return;
		}

		// if economy is enabled, they're not on the bypass list, and this
		// command has a cost set, make sure they can pay
		if (!canAffordCommand(Conf.econCostCreate, "to create a new clan")) {
			return;
		}

		// trigger the clan creation event (cancellable)
		final ClanCreateEvent createEvent = new ClanCreateEvent(me, tag);
		Bukkit.getServer().getPluginManager().callEvent(createEvent);
		if (createEvent.isCancelled()) {
			return;
		}

		// then make 'em pay (if applicable)
		if (!payForCommand(Conf.econCostCreate, "to create a new clan", "for creating a new clan")) {
			return;
		}

		final Clan clan = Clans.i.create();

		// TODO: Why would this even happen??? Auto increment clash??
		if (clan == null) {
			msg("<b>There was an internal error while trying to create your clan. Please try again.");
			return;
		}

		// finish setting up the Clan
		clan.setTag(tag);

		// trigger the clan join event for the creator
		final CPlayerJoinEvent joinEvent = new CPlayerJoinEvent(CPlayers.i.get(me), clan, CPlayerJoinEvent.PlayerJoinReason.CREATE);
		Bukkit.getServer().getPluginManager().callEvent(joinEvent);
		// join event cannot be cancelled or you'll have an empty clan

		// finish setting up the CPlayer
		fme.setRole(Role.ADMIN);
		fme.setClan(clan);

		for (final CPlayer follower : CPlayers.i.getOnline()) {
			follower.msg("%s<i> created a new clan %s", fme.describeTo(follower, true), clan.getTag(follower));
		}

		msg("<i>You should now: %s", p.cmdBase.cmdDescription.getUseageTemplate());

		if (Conf.logClanCreate) {
			P.p.log(fme.getName() + " created a new clan: " + tag);
		}
	}

}
