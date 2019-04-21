package com.github.pfacheris.BukkitDuel;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.github.pfacheris.BukkitDuel.Objects.Arena;
import com.github.pfacheris.BukkitDuel.Objects.Duel;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Polygonal2DSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;

public class DuelCommandExecutor implements CommandExecutor {
	private final BukkitDuel plugin;

	public DuelCommandExecutor(final BukkitDuel plugin) {
		this.plugin = plugin;

	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if (sender instanceof Player) {
			if (args.length < 1) {
				performHelpCommand(sender, args);
			}
			if (args.length >= 1) {
				if (args[0].equals("challenge")) {
					performChallengeCommand(sender, args);
				}
				if (args[0].equals("requests")) {
					performRequestCommand(sender, args);
				}
				if (args[0].equals("accept")) {
					performAcceptCommand(sender, args);
				}
				if (args[0].equals("reject")) {
					performRejectCommand(sender, args);
				}
				if (args[0].equals("quit")) {
					performQuitCommand(sender, args);
				}
				if (args[0].equals("arenas")) {
					performArenaCommand(sender, args);
				}
				if (args[0].equals("stake")) {
					performStakesCommand(sender, args);
				}
				if (args[0].equals("reload")) {
					plugin.reloadConfig();
					sender.sendMessage("BukkitDuel config reloaded.");
				}
				if (args[0].equals("help")) {
					performHelpCommand(sender, args);
				}
			}

		}
		sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Incorrect usage:");
		sender.sendMessage(ChatColor.DARK_RED + "Correct syntax is '/duel <help|challenge|accept|reject|stake|arenas|requests|reload>'");
		return false;
	}

	private boolean performChallengeCommand(final CommandSender sender, final String[] args) {
		if (args.length == 1) {
			sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "No player specified:");
			sender.sendMessage(ChatColor.DARK_RED + "Use /duel challenge <player> [arena] to issue a duel challenge.");

			return false;
		}
		if (args.length > 1) {
			final Player challengee = plugin.getServer().getPlayer(args[1]);
			if (challengee != null && challengee.isOnline()) {
				if (challengee.getName().equals(sender.getName())) {
					sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "You can't duel yourself.");
					return false;
				}
				if (args.length == 2) {
					if (BukkitDuel.duelManager.getDuelByTwoParticipants((Player) sender, challengee) == null) {

						Arena tempArena = null;
						try {
							tempArena = BukkitDuel.arenaManager.getArenas().get(0);
						} catch (final Exception e) {

						}
						if (tempArena != null) {
							final Duel tempDuel = new Duel((Player) sender, challengee, tempArena);
							BukkitDuel.duelManager.save(tempDuel);
							sender.sendMessage(ChatColor.GREEN + "Challenge sent.");
							challengee.sendMessage(ChatColor.GREEN + "You have a new duel request from " + sender.getName());
							return true;
						}
						sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "There are no arenas defined.");
						sender.sendMessage(ChatColor.DARK_RED + "Please have an admin define an arena.");
						return false;
					}
					sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Duel request not sent:");
					sender.sendMessage(ChatColor.DARK_RED + "A request to this player already exists.");
					return false;
				} else if (args.length == 3) {
					if (BukkitDuel.duelManager.getDuelByTwoParticipants(challengee, (Player) sender) != null) {
						final Arena tempArena = BukkitDuel.arenaManager.getArenaByName(args[2]);
						if (tempArena != null) {
							tempArena.setInUse(true);
							final Duel tempDuel = new Duel((Player) sender, challengee, tempArena);
							BukkitDuel.duelManager.save(tempDuel);
							sender.sendMessage("Challenge sent.");
							return true;
						}
						sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Arena not found:");
						sender.sendMessage(ChatColor.DARK_RED + "Use /duel arenas to view arenas.");
						return false;
					}
					sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Duel request not sent:");
					sender.sendMessage(ChatColor.DARK_RED + "A request to this player already exists.");
					return false;
				}
				sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Incorrect Syntax:");
				sender.sendMessage(ChatColor.DARK_RED + "Use '/duel challenge <player> [(optional)arena]'");
				return false;
			}
			sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Duel Request not sent:");
			sender.sendMessage(ChatColor.DARK_RED + "Player is not online.");
			return false;
		}
		return false;
	}

	private boolean performAcceptCommand(final CommandSender sender, final String[] args) {
		if (args.length == 1) {
			if (BukkitDuel.duelManager.getDuelByChallengee((Player) sender).size() > 1) {
				sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Please specify a duel request to accept:");
				sender.sendMessage(ChatColor.DARK_RED + "Use /duel requests to view your requests.");
				sender.sendMessage(ChatColor.DARK_RED + "Use /duel accept <opponent> to accept a request.");
				return false;
			}
			if (BukkitDuel.duelManager.getDuelByChallengee((Player) sender).size() < 1) {
				sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "You have no pending duel requests:");
				sender.sendMessage(ChatColor.DARK_RED + "Use /duel challenge <player> [arena] to issue a request to another player.");
				return false;
			}
			if (BukkitDuel.duelManager.getDuelByChallengee((Player) sender).size() == 1) {
				final Duel tempDuel = BukkitDuel.duelManager.getDuelByChallengee((Player) sender).get(0);
				if (!BukkitDuel.duelManager.isPlayerInActiveDuel(tempDuel.getChallengee()) && !BukkitDuel.duelManager.isPlayerInActiveDuel(tempDuel.getInitiator())) {
					tempDuel.getChallengee().sendMessage(
							ChatColor.GREEN + "Duel with " + tempDuel.getInitiator().getName() + " accepted.\nYou will be teleported to the arena in 10 seconds.");
					tempDuel.getInitiator().sendMessage(
							ChatColor.GREEN + "Duel with " + tempDuel.getChallengee().getName() + " accepted.\nYou will be teleported to the arena in 10 seconds.");

					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

						@Override
						public void run() {
							tempDuel.startDuel(plugin);
						}
					}, 200L);

					return true;
				}
				sender.sendMessage("One of the duelists is currently in a duel, please wait for them to finish.");
				return false;
			}
		}

		if (args.length == 2) {
			final Player initiator = plugin.getServer().getPlayer(args[1]);
			if (initiator.isOnline() && initiator != null) {
				if (BukkitDuel.duelManager.getDuelByTwoParticipants(initiator, (Player) sender) != null) {
					final Duel tempDuel = BukkitDuel.duelManager.getDuelByTwoParticipants(initiator, (Player) sender);
					if (((Player) sender).equals(tempDuel.getChallengee())) {
						if (!BukkitDuel.duelManager.isPlayerInActiveDuel(tempDuel.getChallengee()) && !BukkitDuel.duelManager.isPlayerInActiveDuel(tempDuel.getInitiator())) {
							if (!tempDuel.getArena().getInUse()) {
								tempDuel.getChallengee().sendMessage(
										ChatColor.GREEN + "Duel with " + tempDuel.getInitiator().getName() + " accepted.\nYou will be teleported to the arena in 10 seconds.");
								tempDuel.getInitiator().sendMessage(
										ChatColor.GREEN + "Duel with " + tempDuel.getChallengee().getName() + " accepted.\nYou will be teleported to the arena in 10 seconds.");

								plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

									@Override
									public void run() {
										tempDuel.startDuel(plugin);
									}
								}, 200L);

								return true;
							}
							tempDuel.getInitiator().sendMessage(ChatColor.DARK_RED + "The arena your duel is set to use is currently in use.");
							sender.sendMessage(ChatColor.DARK_RED + "The arena your duel is set to use is currently in use.");
							tempDuel.getInitiator().sendMessage("Searching for a free arena...");
							sender.sendMessage("Searching for a free arena...");

							if (BukkitDuel.arenaManager.getArenasByUsed(false).size() >= 1) {
								tempDuel.setArena(BukkitDuel.arenaManager.getArenasByUsed(false).get(0));
								tempDuel.getInitiator().sendMessage(ChatColor.GREEN + "Arena found! Duel beginning in 10 seconds.");
								sender.sendMessage(ChatColor.GREEN + "Arena found! Duel beginning in 10 seconds.");

								plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

									@Override
									public void run() {
										tempDuel.startDuel(plugin);
									}
								}, 200L);

								return true;
							}

							sender.sendMessage(ChatColor.DARK_RED + "No open arenas found. Please wait several minutes or have an admin define a new arena.");
							return false;
						}
						sender.sendMessage(ChatColor.DARK_RED + "One of the duelists is currently in a duel, please wait for them to finish.");
						return false;
					}
				}
			}
			sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Duel request from " + args[1] + " not found:");
			sender.sendMessage(ChatColor.DARK_RED + "Check your duel requests to make sure you are accepting a valid request.");
			return false;
		}

		return false;
	}

	private boolean performRejectCommand(final CommandSender sender, final String[] args) {
		if (args.length == 1) {
			if (BukkitDuel.duelManager.getDuelByChallengee((Player) sender).size() > 1) {
				sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Please specify a duel request to reject:");
				sender.sendMessage(ChatColor.DARK_RED + "Use /duel requests to view your requests.");
				sender.sendMessage(ChatColor.DARK_RED + "Use /duel reject <opponent> to reject a request.");
				return false;
			}
			if (BukkitDuel.duelManager.getDuelByChallengee((Player) sender).size() < 1) {
				sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "You have no pending duel requests to reject.");
				return false;
			}
			if (BukkitDuel.duelManager.getDuelByChallengee((Player) sender).size() == 1) {
				final Duel tempDuel = BukkitDuel.duelManager.getDuelByChallengee((Player) sender).get(0);
				BukkitDuel.duelManager.cancelDuel(tempDuel);
				sender.sendMessage("[BukkitDuel] Request from " + tempDuel.getInitiator().getName() + " rejected.");
				tempDuel.getInitiator().sendMessage("[BukkitDuel] Request to " + sender.getName() + " rejected.");
				return true;
			}
		}

		if (args.length == 2) {
			final Player initiator = plugin.getServer().getPlayer(args[1]);
			if (initiator != null) {
				if (BukkitDuel.duelManager.getDuelByTwoParticipants(initiator, (Player) sender) != null) {
					final Duel tempDuel = BukkitDuel.duelManager.getDuelByTwoParticipants(initiator, (Player) sender);
					BukkitDuel.duelManager.cancelDuel(tempDuel);
					sender.sendMessage("[BukkitDuel] Request from " + tempDuel.getInitiator().getName() + " rejected.");
					tempDuel.getInitiator().sendMessage("[BukkitDuel] Request to " + sender.getName() + " rejected.");
					return true;
				}
			}
			sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Duel request from " + args[1] + " not found:");
			sender.sendMessage(ChatColor.DARK_RED + "Check your duel requests to make sure you are rejecting a valid request.");
			return false;
		}

		return false;
	}

	private boolean performRequestCommand(final CommandSender sender, final String[] args) {
		sender.sendMessage(ChatColor.GREEN + "===========" + ChatColor.BOLD + "[Requests To Me]" + ChatColor.GREEN + "===========");
		int counter = 0;
		for (final Duel d : BukkitDuel.duelManager.getDuelByChallengee((Player) sender)) {
			sender.sendMessage(ChatColor.BOLD + "Duel with " + ChatColor.GREEN + d.getInitiator().getName() + ",  " + ChatColor.BOLD + "Request ID: " + ChatColor.GREEN + counter);
			counter++;
		}

		sender.sendMessage(ChatColor.GREEN + "==========" + ChatColor.BOLD + "[Requests From Me]" + ChatColor.GREEN + "==========");
		for (final Duel d : BukkitDuel.duelManager.getDuelByInitiator((Player) sender)) {
			sender.sendMessage(ChatColor.BOLD + "Duel with " + ChatColor.GREEN + d.getChallengee().getName());
		}

		return true;
	}

	private boolean performArenaCommand(final CommandSender sender, final String[] args) {
		if (args.length == 1) {
			sender.sendMessage(ChatColor.GREEN + "============" + ChatColor.BOLD + "[Vacant Arenas]" + ChatColor.GREEN + "===========");
			for (final Arena a : BukkitDuel.arenaManager.getArenasByUsed(false)) {
				sender.sendMessage(ChatColor.BOLD + "Arena Name: " + ChatColor.GREEN + a.getName());
			}

			sender.sendMessage(ChatColor.GREEN + "============" + ChatColor.BOLD + "[In Use Arenas]" + ChatColor.GREEN + "===========");
			for (final Arena a : BukkitDuel.arenaManager.getArenasByUsed(true)) {
				sender.sendMessage(ChatColor.BOLD + "Arena Name: " + ChatColor.GREEN + a.getName());
			}

			return true;
		}
		if (args.length > 1) {
			if (args[1].equalsIgnoreCase("set")) {
				if (BukkitDuel.duelManager.getDuelByInitiator((Player) sender).size() == 1) {
					if (args.length > 2) {
						final Arena newArena = BukkitDuel.arenaManager.getArenaByName(args[2]);
						if (newArena != null) {
							BukkitDuel.duelManager.getDuelByInitiator((Player) sender).get(0).setArena(newArena);
							return true;
						}

						sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Arena not found:");
						sender.sendMessage(ChatColor.DARK_RED + "Use '/duel arenas' to list available arenas.");
						return false;
					}
					sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Arena not found:");
					sender.sendMessage(ChatColor.DARK_RED + "Please specificy an arena to modify.");
					return false;
				}
				if (BukkitDuel.duelManager.getDuelByInitiator((Player) sender).size() > 1) {
					if (args[3] != null) {
						final Arena newArena = BukkitDuel.arenaManager.getArenaByName(args[2]);
						if (newArena != null) {
							final Duel tempDuel = BukkitDuel.duelManager.getDuelByTwoParticipants((Player) sender, plugin.getServer().getPlayer(args[3]));
							if (tempDuel != null) {
								tempDuel.setArena(newArena);
								sender.sendMessage("Arena set to " + newArena.getName());
								return true;
							}
							sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Duel request from " + args[1] + " not found:");
							sender.sendMessage(ChatColor.DARK_RED + "Check your duel requests to make sure you are accepting a valid request.");
							return false;
						}

						sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Arena not found:");
						sender.sendMessage(ChatColor.DARK_RED + "Use '/duel arenas' to list available arenas.");
						return false;
					}
					sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "You have multiple duel requests out:");
					sender.sendMessage(ChatColor.DARK_RED + "Please specify the duel to set the arena on with '/duel arenas set <arena> <opponent>");
					return false;
				}

				sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "You currently have no challenges to set the arena on:");
				sender.sendMessage(ChatColor.DARK_RED + "Issue duel requests with '/duel challenge <player> [arena]' commmand.");
				return false;

			}
			if (args[1].equalsIgnoreCase("add")) {
				if (((Player) sender).hasPermission("BukkitDuel.admin")) {
					WorldEditPlugin worldEditPlugin = null;
					worldEditPlugin = (WorldEditPlugin) plugin.getServer().getPluginManager().getPlugin("WorldEdit");
					final Selection sel = worldEditPlugin.getSelection((Player) sender);

					if (sel instanceof CuboidSelection) {
						final CuboidSelection polySel = (CuboidSelection) sel;
						final int minY = polySel.getNativeMinimumPoint().getBlockY();
						final int maxY = polySel.getNativeMaximumPoint().getBlockY();
						final int minX = polySel.getNativeMinimumPoint().getBlockX();
						final int maxX = polySel.getNativeMaximumPoint().getBlockX();
						final int minZ = polySel.getNativeMinimumPoint().getBlockZ();
						final int maxZ = polySel.getNativeMaximumPoint().getBlockZ();

						if (args.length >= 3 && args[2] != null && args[2].length() != 0) {
							BukkitDuel.arenaManager.save(args[2], plugin.getServer().getWorld(sel.getWorld().getName()), minX, minY, minZ, maxX, maxY, maxZ);
							sender.sendMessage(ChatColor.GREEN + "Arena created. Define spawn points with '/duel arenas edit <spawn1/spawn2>'");
							return true;
						}
						sender.sendMessage(ChatColor.DARK_RED + "Please choose an arena name.");
						return false;
					}
					sender.sendMessage(ChatColor.DARK_RED + "Please use a valid WorldEdit selection.");
					return false;
				}

				sender.sendMessage(ChatColor.DARK_RED + "You don't have permission to create/modify arenas.");
				return false;
			}
			if (args[1].equalsIgnoreCase("edit")) {
				if (((Player) sender).hasPermission("BukkitDuel.admin")) {
					if (args.length > 3) {
						final Arena tempArena = BukkitDuel.arenaManager.getArenaByName(args[2]);
						if (tempArena != null) {
							if (args[3].equalsIgnoreCase("bounds")) {
								WorldEditPlugin worldEditPlugin = null;
								worldEditPlugin = (WorldEditPlugin) plugin.getServer().getPluginManager().getPlugin("WorldEdit");
								final Selection sel = worldEditPlugin.getSelection((Player) sender);

								if (sel instanceof Polygonal2DSelection) {
									final Polygonal2DSelection polySel = (Polygonal2DSelection) sel;
									final int minY = polySel.getNativeMinimumPoint().getBlockY();
									final int maxY = polySel.getNativeMaximumPoint().getBlockY();
									final int minX = polySel.getNativeMinimumPoint().getBlockX();
									final int maxX = polySel.getNativeMaximumPoint().getBlockX();
									final int minZ = polySel.getNativeMinimumPoint().getBlockZ();
									final int maxZ = polySel.getNativeMaximumPoint().getBlockZ();

									tempArena.setMin(minX, minY, minZ);
									tempArena.setMin(maxX, maxY, maxZ);
									sender.sendMessage(ChatColor.GREEN + "Arena bounds modified.");
									return true;
								}
								sender.sendMessage(ChatColor.DARK_RED + "Please use a valid WorldEdit selection.");
								return false;
							}

							if (args[3].equalsIgnoreCase("spawn1")) {
								tempArena.setSpawn1(((Player) sender).getLocation());
								sender.sendMessage(ChatColor.GREEN + "Spawn 1 set at current position.");
								return true;
							}

							if (args[3].equalsIgnoreCase("spawn2")) {
								tempArena.setSpawn2(((Player) sender).getLocation());
								sender.sendMessage(ChatColor.GREEN + "Spawn 2 set at current position.");
								return true;
							}
						}
						sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Arena not found:");
						sender.sendMessage(ChatColor.DARK_RED + "Use '/duel arenas' to list available arenas.");
						return false;
					}
					if (args.length == 3) {
						sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Property to edit not specified:");
						sender.sendMessage(ChatColor.DARK_RED + "Valid properties are: spawn1, spawn2, bounds");
						return false;
					}
					sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Incorrect usage:");
					sender.sendMessage(ChatColor.DARK_RED + "Correct syntax is '/duel arenas edit <arena> <property>'");
					return false;
				}
				sender.sendMessage(ChatColor.DARK_RED + "You don't have permission to modify arenas.");
				return false;
			}
			if (args[1].equalsIgnoreCase("remove")) {
				if (((Player) sender).hasPermission("BukkitDuel.admin")) {
					final Arena tempArena = BukkitDuel.arenaManager.getArenaByName(args[2]);
					if (tempArena != null) {
						BukkitDuel.arenaManager.remove(tempArena);
						sender.sendMessage(ChatColor.GREEN + "Arena deleted.");
					}
					sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Arena not found:");
					sender.sendMessage(ChatColor.DARK_RED + "Use '/duel arenas' to list available arenas.");
					return false;
				}
				sender.sendMessage(ChatColor.DARK_RED + "You don't have permission to delete arenas.");
				return false;
			}
		}
		sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Incorrect usage:");
		sender.sendMessage(ChatColor.DARK_RED + "Correct syntax is '/duel arena <add|remove|edit|set>'");
		return false;
	}

	private boolean performQuitCommand(final CommandSender sender, final String[] args) {
		if (BukkitDuel.duelManager.isPlayerInActiveDuel((Player) sender)) {
			final Duel tempDuel = BukkitDuel.duelManager.getActiveDuelByPlayer((Player) sender);
			if (((Player) sender).equals(tempDuel.getInitiator())) {
				BukkitDuel.duelManager.endDuel(tempDuel, false);
			} else {
				BukkitDuel.duelManager.endDuel(tempDuel, true);
			}
			return true;
		}
		sender.sendMessage("[BukkitDuel] You are not currently in a duel.");
		return false;
	}

	private boolean performStakesCommand(final CommandSender sender, final String[] args) {
		if (args.length >= 2) {
			String playerName = args[1];
			if (playerName.equalsIgnoreCase("view")) {
				if (args.length >= 3) {
					playerName = args[2];
					if (BukkitDuel.duelManager.getDuelByTwoParticipants((Player) sender, plugin.getServer().getPlayer(playerName)) == null) {
						sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Duel request from " + args[1] + " not found:");
						sender.sendMessage(ChatColor.DARK_RED + "Check your duel requests to make sure you are viewing the wager of a valid request.");
						return false;
					}
					final Duel tempDuel = BukkitDuel.duelManager.getDuelByTwoParticipants((Player) sender, plugin.getServer().getPlayer(playerName));
					sender.sendMessage(ChatColor.BOLD + "Duel with " + playerName + ":");
					sender.sendMessage(ChatColor.GREEN + "===========" + ChatColor.BOLD + "[" + tempDuel.getInitiator().getName() + "'s Wager]" + ChatColor.GREEN + "===========");
					sender.sendMessage("Money: " + tempDuel.stakesInitiator.getWagerMoneyAmount() + " Dollars");
					sender.sendMessage("Experience: " + tempDuel.stakesInitiator.getWagerExperienceAmount());
					sender.sendMessage("Power: " + tempDuel.stakesInitiator.getWagerPowerAmount() + " Faction Power");
					if (tempDuel.getInitiator().equals(sender)) {
						sender.sendMessage("Items: Can be modified and viewed using '/duel stake <opponent> item'");
					} else {
						for (final ItemStack item : tempDuel.stakesInitiator.getWagerItems().getContents()) {
							sender.sendMessage("Item: " + item.getType().name() + " x" + item.getAmount());
						}

					}

					sender.sendMessage(ChatColor.GREEN + "===========" + ChatColor.BOLD + "[" + tempDuel.getChallengee().getName() + "'s Wager]" + ChatColor.GREEN + "===========");
					sender.sendMessage("Money: " + tempDuel.stakesChallengee.getWagerMoneyAmount() + " Dollars");
					sender.sendMessage("Experience: " + tempDuel.stakesChallengee.getWagerExperienceAmount());
					sender.sendMessage("Power: " + tempDuel.stakesChallengee.getWagerPowerAmount() + " Faction Power");
					if (tempDuel.getChallengee().equals(sender)) {
						sender.sendMessage("Items: Can be modified and viewed using '/duel stake <opponent> item'");
					} else {
						for (final ItemStack item : tempDuel.stakesInitiator.getWagerItems().getContents()) {
							sender.sendMessage("Item: " + item.getType().name() + " x" + item.getAmount());
						}
					}

					return true;

				}
			}
			if (args.length >= 3) {
				final String id = args[2];
				if (BukkitDuel.duelManager.getDuelByTwoParticipants((Player) sender, plugin.getServer().getPlayer(playerName)) == null) {
					sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Duel request from " + args[1] + " not found:");
					sender.sendMessage(ChatColor.DARK_RED + "Check your duel requests to make sure you are wagering with a valid request.");
					return false;
				}

				final Duel tempDuel = BukkitDuel.duelManager.getDuelByTwoParticipants((Player) sender, plugin.getServer().getPlayer(playerName));
				if (id.equalsIgnoreCase("item")) {
					sender.sendMessage("[BukkitDuel] Place items in the popup window that you want to wager.");
					if (tempDuel.getInitiator().equals(sender)) {
						((Player) sender).openInventory(tempDuel.stakesInitiator.getWagerItems());
						tempDuel.getChallengee().sendMessage(
								"[BukkitDuel] " + sender.getName() + " has altered their wager items.\nUse '/duel stake view <player>' to check the wager.");

					}
					if (tempDuel.getChallengee().equals(sender)) {
						((Player) sender).openInventory(tempDuel.stakesChallengee.getWagerItems());
						tempDuel.getInitiator().sendMessage(
								"[BukkitDuel] " + sender.getName() + " has altered their wager items.\nUse '/duel stake view <player>' to check the wager.");
					}
					return true;
				}
				if (args.length >= 4) {
					final String amount = args[3];
					if (tempDuel.getInitiator().equals(sender)) {
						if (id.equalsIgnoreCase("money")) {
							if (BukkitDuel.economy.isEnabled()) {
								if (BukkitDuel.economy.bankBalance(sender.getName()).amount <= Integer.parseInt(amount)) {
									tempDuel.stakesInitiator.setWagerMoneyAmount(Integer.parseInt(amount));
									sender.sendMessage("Wager amount set to " + amount + "dollars.");
									tempDuel.getChallengee().sendMessage("[BukkitDuel]" + sender.getName() + " is wagering " + amount + "dollars.");
									tempDuel.getChallengee().sendMessage("Accept the duel request with '/duel accept <player>', or reject with '/duel reject <player>'");
									return true;
								}
								sender.sendMessage("You can't wager more money than you have.");
								return false;
							}
							sender.sendMessage("No economy plugin active, money wagering disabled.");
							return false;
						}
						if (id.equalsIgnoreCase("exp") || id.equalsIgnoreCase("experience")) {
							if (((Player) sender).getExp() <= Integer.parseInt(amount)) {
								tempDuel.stakesInitiator.setWagerExperienceAmount(Integer.parseInt(amount));
								sender.sendMessage("Experience wager amount set to " + amount + ".");
								tempDuel.getChallengee().sendMessage("[BukkitDuel] " + sender.getName() + " is wagering " + amount + "experience.");
								tempDuel.getChallengee().sendMessage("[BukkitDuel] Accept the duel request with '/duel accept <player>', or reject with '/duel reject <player>'");
								return true;
							}
							sender.sendMessage("You can't wager more experience than you have.");
							return false;
						}

						if (id.equalsIgnoreCase("power")) {
							// do faction power stuff
						}
					}
					if (tempDuel.getChallengee().equals(sender)) {
						if (id.equalsIgnoreCase("money")) {
							if (BukkitDuel.economy.isEnabled()) {
								if (BukkitDuel.economy.bankBalance(sender.getName()).amount <= Integer.parseInt(amount)) {
									tempDuel.stakesInitiator.setWagerMoneyAmount(Integer.parseInt(amount));
									sender.sendMessage("Wager amount set to " + amount + "dollars.");
									tempDuel.getInitiator().sendMessage("[BukkitDuel] " + sender.getName() + " is wagering " + amount + "dollars.");
									tempDuel.getInitiator().sendMessage("[BukkitDuel] " + sender.getName() + " must accept the duel to begin.");
									return true;
								}
								sender.sendMessage("You can't wager more money than you have.");
								return false;
							}
							sender.sendMessage("No economy plugin active, money wagering disabled.");
							return false;
						}
						if (id.equalsIgnoreCase("exp") || id.equalsIgnoreCase("experience")) {
							tempDuel.stakesInitiator.setWagerExperienceAmount(Integer.parseInt(amount));
							if (((Player) sender).getExp() <= Integer.parseInt(amount)) {
								sender.sendMessage("Experience wager amount set to " + amount + ".");
								tempDuel.getInitiator().sendMessage("[BukkitDuel] " + sender.getName() + " is wagering " + amount + "experience.");
								tempDuel.getInitiator().sendMessage("[BukkitDuel] " + sender.getName() + " must accept the duel to begin.");
								return true;
							}
							sender.sendMessage("You can't wager more experience than you have.");
							return false;
						}
						if (id.equalsIgnoreCase("power")) {
							// do faction power stuff
						}

					}
				}
			}
		}
		sender.sendMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "Incorrect usage:");
		sender.sendMessage(ChatColor.DARK_RED + "Correct syntax is '/duel stake <player> <money:exp:item:power> [amount]'");
		return false;
	}

	private boolean performHelpCommand(final CommandSender sender, final String[] args) {
		sender.sendMessage(ChatColor.GREEN + "===========" + ChatColor.BOLD + "[BukkitDuel Help (1/1)]" + ChatColor.GREEN + "===========");
		sender.sendMessage(ChatColor.GREEN + "Use /duel [command] ? for more information.");
		sender.sendMessage(ChatColor.BOLD + "challenge: " + ChatColor.GREEN + "send duel requests");
		sender.sendMessage(ChatColor.BOLD + "requests: " + ChatColor.GREEN + "list duel requests");
		sender.sendMessage(ChatColor.BOLD + "accept: " + ChatColor.GREEN + "accept duel requests");
		sender.sendMessage(ChatColor.BOLD + "arenas: " + ChatColor.GREEN + "arena list and create/modify");
		sender.sendMessage(ChatColor.BOLD + "stake: " + ChatColor.GREEN + "alter wager for active duel");

		return true;
	}

	public static boolean isInteger(final String str) {
		if (str == null) {
			return false;
		}
		final int length = str.length();
		if (length == 0) {
			return false;
		}
		int i = 0;
		if (str.charAt(0) == '-') {
			if (length == 1) {
				return false;
			}
			i = 1;
		}
		for (; i < length; i++) {
			final char c = str.charAt(i);
			if (c <= '/' || c >= ':') {
				return false;
			}
		}
		return true;
	}
}