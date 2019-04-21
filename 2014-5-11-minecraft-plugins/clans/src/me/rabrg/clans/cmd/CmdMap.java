package me.rabrg.clans.cmd;

import me.rabrg.clans.Board;
import me.rabrg.clans.CLocation;
import me.rabrg.clans.Conf;
import me.rabrg.clans.struct.Permission;

public class CmdMap extends FCommand {
	public CmdMap() {
		super();
		this.aliases.add("map");

		// this.requiredArgs.add("");
		this.optionalArgs.put("on/off", "once");

		this.permission = Permission.MAP.node;
		this.disableOnLock = false;

		senderMustBePlayer = true;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		if (this.argIsSet(0)) {
			if (this.argAsBool(0, !fme.isMapAutoUpdating())) {
				// Turn on

				// if economy is enabled, they're not on the bypass list, and
				// this command has a cost set, make 'em pay
				if (!payForCommand(Conf.econCostMap, "to show the map", "for showing the map")) {
					return;
				}

				fme.setMapAutoUpdating(true);
				msg("<i>Map auto update <green>ENABLED.");

				// And show the map once
				showMap();
			} else {
				// Turn off
				fme.setMapAutoUpdating(false);
				msg("<i>Map auto update <red>DISABLED.");
			}
		} else {
			// if economy is enabled, they're not on the bypass list, and this
			// command has a cost set, make 'em pay
			if (!payForCommand(Conf.econCostMap, "to show the map", "for showing the map")) {
				return;
			}

			showMap();
		}
	}

	public void showMap() {
		sendMessage(Board.getMap(myClan, new CLocation(fme), fme.getPlayer().getLocation().getYaw()));
	}

}
