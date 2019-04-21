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

package net.gravitydevelopment.anticheat.command.executors;

import java.util.ArrayList;
import java.util.List;

import net.gravitydevelopment.anticheat.AntiCheat;
import net.gravitydevelopment.anticheat.check.CheckType;
import net.gravitydevelopment.anticheat.command.CommandBase;
import net.gravitydevelopment.anticheat.manage.CheckManager;
import net.gravitydevelopment.anticheat.util.Group;
import net.gravitydevelopment.anticheat.util.Permission;
import net.gravitydevelopment.anticheat.util.User;
import net.gravitydevelopment.anticheat.util.Utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandReport extends CommandBase {

	private static final String NAME = "AntiCheat Reports";
	private static final String COMMAND = "report";
	private static final String USAGE = "anticheat report [group/user]";
	private static final Permission PERMISSION = Permission.SYSTEM_REPORT;
	private static final String[] HELP = {
		GRAY + "Use: " + AQUA + "/anticheat report [group]" + GRAY + " to see all users in a given group",
		GRAY + "Use: " + AQUA + "/anticheat report [user]" + GRAY + " to see a single user's report",
		GRAY + "Use: " + AQUA + "/anticheat report [user/group] [num]" + GRAY + " to see pages of a report",
	};

	public CommandReport() {
		super(NAME, COMMAND, USAGE, HELP, PERMISSION);
	}

	@Override
	protected void execute(final CommandSender cs, final String[] args) {
		if (args.length >= 1) {
			int page = 1;
			if (args.length == 2) {
				if (Utilities.isInt(args[1])) {
					page = Integer.parseInt(args[1]);
				} else {
					cs.sendMessage(RED + "Not a valid page number: " + WHITE + args[1]);
				}
			}

			if ("low".equalsIgnoreCase(args[0])) {
				groupReport(cs, null, page);
			}
			else if ("all".equalsIgnoreCase(args[0])) {
				cs.sendMessage(GREEN + "Low: " + WHITE + USER_MANAGER.getUsersInGroup(null).size() + " players");
				for (final Group g : CONFIG.getGroups().getGroups()) {
					final int numPlayers = USER_MANAGER.getUsersInGroup(g).size();
					cs.sendMessage(g.getColor() + g.getName() + WHITE + ": " + numPlayers + " players");
				}
				cs.sendMessage(GRAY + "Use " + AQUA + "/anticheat report [group]" + GRAY + " for a list of players in each group.");
			} else {
				// Test groups
				for (final Group group : CONFIG.getGroups().getGroups()) {
					if (group.getName().equalsIgnoreCase(args[0]) || "low".equalsIgnoreCase(args[0]) || "all".equalsIgnoreCase(args[0])) {
						groupReport(cs, group, page);
						return;
					}
				}

				// Test users
				for (final Player player : Bukkit.getOnlinePlayers()) {
					if (player.getName().equalsIgnoreCase(args[0])) {
						final User user = AntiCheat.getManager().getUserManager().getUser(args[0]);
						playerReport(cs, user, page);
						return;
					}
				}

				// Neither of these
				cs.sendMessage(RED + "Not a valid group or user: " + WHITE + args[0]);
			}
		} else {
			sendHelp(cs);
		}
	}

	private void groupReport(final CommandSender cs, final Group group, final int page) {
		final List<User> users = USER_MANAGER.getUsersInGroup(group);
		final ChatColor color = group == null ? GREEN : group.getColor();
		final String groupName = group == null ? "Low" : group.getName();
		final int pages = (int) Math.ceil((float) users.size() / 7);
		if (page <= pages && page > 0) {
			cs.sendMessage("--------------------[" + GREEN + "REPORT[" + page + "/" + pages + "]" + WHITE + "]---------------------");
			cs.sendMessage(GRAY + "Group: " + color + groupName);
			for (int x = 0; x < 7; x++) {
				final int index = (page - 1) * 6 + x + (page - 1) * 1;
				if (index < users.size()) {
					final String player = users.get(index).getName();
					cs.sendMessage(GRAY + player);
				}
			}
			cs.sendMessage(MENU_END);
		} else {
			if (pages == 0) {
				cs.sendMessage("--------------------[" + GREEN + "REPORT[1/1]" + WHITE + "]---------------------");
				cs.sendMessage(GRAY + "Group: " + color + groupName);
				cs.sendMessage(GRAY + "There are no users in this group.");
				cs.sendMessage(MENU_END);
			} else {
				cs.sendMessage(RED + "Page not found. Requested " + WHITE + page + RED + ", Max " + WHITE + pages);
			}
		}
	}

	private void playerReport(final CommandSender cs, final User user, final int page) {
		final List<CheckType> types = new ArrayList<CheckType>();
		for (final CheckType type : CheckType.values()) {
			if (type.getUses(user.getName()) > 0) {
				types.add(type);
			}
		}

		final String name = user.getName();
		final int pages = (int) Math.ceil((float) types.size() / 6);

		final Group group = user.getGroup();
		String groupString = GREEN + "Low";

		if (group != null) {
			groupString = group.getColor() + group.getName();
		}
		groupString += " (" + user.getLevel() + ")";

		if (page <= pages && page > 0) {
			cs.sendMessage("--------------------[" + GREEN + "REPORT[" + page + "/" + pages + "]" + WHITE + "]---------------------");
			cs.sendMessage(GRAY + "Player: " + WHITE + name);
			cs.sendMessage(GRAY + "Group: " + groupString);
			for (int x = 0; x < 6; x++) {
				final int index = (page - 1) * 5 + x + page - 1;
				if (index < types.size()) {
					final CheckType type = types.get(index);
					final int use = type.getUses(name);
					ChatColor color = WHITE;
					if (use >= 20) {
						color = YELLOW;
					} else if (use > 50) {
						color = RED;
					}
					cs.sendMessage(GRAY + CheckType.getName(type) + ": " + color + use);
				}
			}
			cs.sendMessage(MENU_END);
		} else {
			if (pages == 0 && page == 1) {
				cs.sendMessage("--------------------[" + GREEN + "REPORT[1/1]" + WHITE + "]---------------------");
				cs.sendMessage(GRAY + "Player: " + WHITE + name);
				cs.sendMessage(GRAY + "Group: " + groupString);
				cs.sendMessage(GRAY + "This user has not failed any checks.");
				cs.sendMessage(MENU_END);
			} else {
				cs.sendMessage(RED + "Page not found. Requested " + WHITE + page + RED + ", Max " + WHITE + pages + 1);
			}
		}

		if (AntiCheat.developerMode()) {
			int permission = 0;
			int check = 0;
			for (final Permission perm : Permission.values()) {
				if (perm.get(user.getPlayer())) {
					permission++;
				}
			}
			final CheckManager manager = AntiCheat.getManager().getCheckManager();
			for (final CheckType type : CheckType.values()) {
				if (manager.willCheck(user.getPlayer(), type)) {
					check++;
				}
			}
			cs.sendMessage(ChatColor.GOLD + "User has " + permission + "/" + Permission.values().length + " permissions");
			cs.sendMessage(ChatColor.GOLD + "User will be checked for " + check + "/" + CheckType.values().length + " checks");
		}
	}
}
