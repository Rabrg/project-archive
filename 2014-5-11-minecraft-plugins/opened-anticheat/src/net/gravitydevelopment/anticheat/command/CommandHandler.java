/*
 * AntiCheat for Bukkit.
 * Copyright (C) 2012-2014 AntiCheat Team | http://gravitydevelopment.net
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package net.gravitydevelopment.anticheat.command;

import java.util.ArrayList;
import java.util.List;

import net.gravitydevelopment.anticheat.command.executors.CommandCalibrate;
import net.gravitydevelopment.anticheat.command.executors.CommandCheck;
import net.gravitydevelopment.anticheat.command.executors.CommandDebug;
import net.gravitydevelopment.anticheat.command.executors.CommandDeveloper;
import net.gravitydevelopment.anticheat.command.executors.CommandHelp;
import net.gravitydevelopment.anticheat.command.executors.CommandLog;
import net.gravitydevelopment.anticheat.command.executors.CommandReload;
import net.gravitydevelopment.anticheat.command.executors.CommandReport;
import net.gravitydevelopment.anticheat.command.executors.CommandReset;
import net.gravitydevelopment.anticheat.command.executors.CommandSpy;
import net.gravitydevelopment.anticheat.command.executors.CommandUpdate;
import net.gravitydevelopment.anticheat.command.executors.CommandXray;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHandler implements CommandExecutor {

	private final List<CommandBase> commands = new ArrayList<CommandBase>();

	public CommandHandler() {
		commands.add(new CommandHelp());
		commands.add(new CommandCalibrate());
		commands.add(new CommandCheck());
		commands.add(new CommandDebug());
		commands.add(new CommandDeveloper());
		commands.add(new CommandLog());
		commands.add(new CommandReload());
		commands.add(new CommandReport());
		commands.add(new CommandReset());
		commands.add(new CommandSpy());
		commands.add(new CommandUpdate());
		commands.add(new CommandXray());
	}

	@Override
	public boolean onCommand(final CommandSender cs, final Command cmd, final String alias, final String[] args) {
		if (args.length >= 1) {
			final String command = args[0];
			// Shift args down
			final String[] newArgs = new String[args.length-1];
			for (int i=1;i<args.length;i++) {
				newArgs[i-1] = args[i];
			}
			for (final CommandBase base : commands) {
				if (base.getCommand().equalsIgnoreCase(command)) {
					base.run(cs, newArgs);
					break;
				}
			}
			// Command not found, send help
			commands.get(0).run(cs, null);
		} else {
			// Send help
			commands.get(0).run(cs, null);
		}
		return true;
	}
}
