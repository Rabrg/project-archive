package net.thewavemc.onevsone.command;

import java.util.List;

import net.thewavemc.onevsone.Configuration;
import net.thewavemc.onevsone.OneVsOne;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandHandler implements CommandExecutor {
	OneVsOne plugin;

	public CommandHandler(final OneVsOne plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (cmd.getName().equalsIgnoreCase("ovo") && sender instanceof Player) {
			final Player player = (Player) sender;
			if (args.length < 1) {
				player.sendMessage(this.plugin.TAG + ChatColor.RED + "Commands:");
				player.sendMessage(ChatColor.GREEN + "/ovo enter");
				player.sendMessage(ChatColor.GREEN + "/ovo leave");
				player.sendMessage(ChatColor.GREEN + "/ovo invite [player]");
				player.sendMessage(ChatColor.GREEN + "/ovo accept [player]");
				player.sendMessage(ChatColor.GREEN + "/ovo config [option]");
				player.sendMessage(ChatColor.GREEN + "/ovo info");
				return false;
			}
			String str1;
			switch ((str1 = args[0].toLowerCase()).hashCode()) {
			case -1423461112:
				if (str1.equals("accept")) {
				}
				break;
			case -1354792126:
				if (str1.equals("config")) {
				}
				break;
			case -1183699191:
				if (str1.equals("invite")) {
				}
				break;
			case 3198785:
				if (str1.equals("help")) {
				}
				break;
			case 3237038:
				if (str1.equals("info")) {
				}
				break;
			case 3267882:
				if (str1.equals("join")) {
				}
				break;
			case 3482191:
				if (str1.equals("quit")) {
				}
				break;
			case 96667352:
				if (str1.equals("enter")) {
					break;
				}
				break;
			case 102846135:
				if (!str1.equals("leave")) {
					break label4015;
					if (player.hasPermission("ovo.use")) {
						if (!Configuration.isConfigured) {
							player.sendMessage(this.plugin.TAG + ChatColor.RED + Configuration.checkConfigured());
							break label4067;
						}
						if (!this.plugin.getDuelManager().isOneVsOne(player)) {
							this.plugin.getDuelManager().enter(player);
							break label4067;
						}
						player.sendMessage(this.plugin.TAG + ChatColor.GREEN + "You are already in the One Vs One arena!");
						break label4067;
					}
					this.plugin.noPerms(player);
					break label4067;
				} else if (player.hasPermission("ovo.use")) {
					if (this.plugin.getDuelManager().isOneVsOne(player)) {
						if (!this.plugin.getDuelManager().isInDuel(player)) {
							this.plugin.getDuelManager().leave(player, true);
						} else {
							player.sendMessage(this.plugin.TAG + ChatColor.RED + "Don't leave the One Vs One arena while dueling!");
						}
					} else {
						player.sendMessage(this.plugin.TAG + ChatColor.RED + "You have to be in the One Vs One arena to leave it, \"/ovo enter\" to enter One Vs One!");
					}
				} else {
					this.plugin.noPerms(player);
					break label4067;
					if (args.length == 2) {
						if (this.plugin.getDuelManager().isOneVsOne(player)) {
							if (!this.plugin.getDuelManager().isInDuel(player)) {
								final String invite = args[1];
								final Player target = Bukkit.getPlayer(invite);
								if (target != null) {
									if (this.plugin.getDuelManager().isOneVsOne(target)) {
										if (!this.plugin.getDuelManager().isInDuel(target)) {
											this.plugin.getDuelManager().inviteDuel(player, target);
										} else {
											player.sendMessage(this.plugin.TAG + ChatColor.RED + target.getName() + " is already in a duel!");
										}
									} else {
										player.sendMessage(this.plugin.TAG + ChatColor.RED + target.getName() + " is not playing One Vs One!");
									}
								} else {
									player.sendMessage(this.plugin.TAG + ChatColor.RED + invite + " is not online!");
								}
							} else {
								player.sendMessage(this.plugin.TAG + ChatColor.RED + "You can't invite players while dueling!");
							}
						} else {
							player.sendMessage(this.plugin.TAG + ChatColor.RED + "You are not in the One Vs One arena, \"/ovo enter\" to enter One Vs One!");
							break label4067;
							if (args.length == 1) {
								if (this.plugin.getDuelManager().isOneVsOne(player)) {
									if (!this.plugin.getDuelManager().acceptDuel(player)) {
										player.sendMessage(this.plugin.TAG + ChatColor.RED + "No pending invites!");
									}
								} else {
									player.sendMessage(this.plugin.TAG + ChatColor.RED + "You are not in the One Vs One arena, \"/ovo enter\" to enter One Vs One!");
								}
							} else if (args.length == 2) {
								String who = args[1];
								if (this.plugin.getDuelManager().isOneVsOne(player)) {
									if (!this.plugin.getDuelManager().acceptDuel(player, who)) {
										player.sendMessage(this.plugin.TAG + ChatColor.RED + "No pending invites!");
									}
								} else {
									player.sendMessage(this.plugin.TAG + ChatColor.RED + "You are not in the One Vs One arena, \"/ovo enter\" to enter One Vs One!");
									break label4067;
									if (args.length < 2) {
										player.sendMessage(this.plugin.TAG + ChatColor.RED + "Configure Commands:");
										player.sendMessage(ChatColor.GREEN + "/ovo config spawn");
										player.sendMessage(ChatColor.GREEN + "/ovo config spotone");
										player.sendMessage(ChatColor.GREEN + "/ovo config spottwo");
										player.sendMessage(ChatColor.GREEN + "/ovo config exit");
										player.sendMessage(ChatColor.GREEN + "/ovo config inviteitem");
										player.sendMessage(ChatColor.GREEN + "/ovo config inventory");
										player.sendMessage(ChatColor.GREEN + "/ovo config disabledcommands");
										return false;
									}
									this.plugin.saveConfig();
									switch ((who = args[1].toLowerCase()).hashCode()) {
									case -2020599460:
										if (who.equals("inventory")) {
										}
										break;
									case -1998717404:
										if (who.equals("spotone")) {
										}
										break;
									case -1998712310:
										if (who.equals("spottwo")) {
										}
										break;
									case -1199044348:
										if (who.equals("disabledcommands")) {
										}
										break;
									case 3127582:
										if (who.equals("exit")) {
										}
										break;
									case 109638523:
										if (who.equals("spawn")) {
											break;
										}
										break;
									case 1198718364:
										if (!who.equals("inviteitem")) {
											break label3377;
											if (player.hasPermission("ovo.configure.spawn")) {
												Configuration.spawn = player.getLocation();
												Configuration.saveLocationToConfig(this.plugin, "arena.spawn", player.getLocation());
												player.sendMessage(this.plugin.TAG + ChatColor.GREEN + "Succesfully set the spawn of the arena as your current location!");
												break label4067;
											}
											this.plugin.noPerms(player);
											break label4067;
											if (player.hasPermission("ovo.configure.spotone")) {
												Configuration.spotOne = player.getLocation();
												Configuration.saveLocationToConfig(this.plugin, "arena.spotone", player.getLocation());
												player.sendMessage(this.plugin.TAG + ChatColor.GREEN + "Succesfully set spot one as your current location!");
												break label4067;
											}
											this.plugin.noPerms(player);
											break label4067;
											if (player.hasPermission("ovo.configure.spottwo")) {
												Configuration.spotTwo = player.getLocation();
												Configuration.saveLocationToConfig(this.plugin, "arena.spottwo", player.getLocation());
												player.sendMessage(this.plugin.TAG + ChatColor.GREEN + "Succesfully set spot two as your current location!");
												break label4067;
											}
											this.plugin.noPerms(player);
											break label4067;
											if (player.hasPermission("ovo.configure.exit")) {
												Configuration.exit = player.getLocation();
												Configuration.saveLocationToConfig(this.plugin, "exit", player.getLocation());
												player.sendMessage(this.plugin.TAG + ChatColor.GREEN + "Succesfully set the exit of the arena as your current location!");
												break label4067;
											}
											this.plugin.noPerms(player);
											break label4067;
										} else {
											ItemStack i;
											if (player.hasPermission("ovo.configure.inviteitem")) {
												i = player.getItemInHand();
												if (!i.getType().equals(Material.AIR)) {
													Configuration.inviteItem = Configuration.getInviteItem(i.getType());
													this.plugin.getConfig().set("arena.invite-item", i.getType().toString());
													player.sendMessage(this.plugin.TAG + ChatColor.GREEN + "Succesfully set the inviteitem as the item you are currently holding!");
												} else {
													player.sendMessage(this.plugin.TAG + ChatColor.RED + "Invite item cannot be AIR!");
												}
											} else {
												this.plugin.noPerms(player);
												break label4067;
												if (player.hasPermission("ovo.configure.inventory")) {
													Configuration.inventoryContents = player.getInventory().getContents();
													Configuration.armorContents = player.getInventory().getArmorContents();
													this.plugin.getConfig().set("arena.inventory.items", player.getInventory().getContents());
													this.plugin.getConfig().set("arena.inventory.armor", player.getInventory().getArmorContents());
													player.sendMessage(this.plugin.TAG + ChatColor.GREEN + "Succesfully set the duel inventory as your current inventory!");
												} else {
													this.plugin.noPerms(player);
													break label4067;
													if (player.hasPermission("ovo.configure.disabledcommands")) {
														if (args.length < 3) {
															player.sendMessage(this.plugin.TAG + ChatColor.RED + "Disabled-Commands Commands:");
															player.sendMessage(ChatColor.GREEN + "/ovo config disabledcommands add [command]");
															player.sendMessage(ChatColor.GREEN + "/ovo config disabledcommands remove [command]");
															player.sendMessage(ChatColor.GREEN + "/ovo config disabledcommands list");
															return false;
														}
														switch (args[2].toLowerCase().hashCode()) {
														case -934610812:
															if (i.equals("remove")) {
															}
															break;
														case 96417:
															if (i.equals("add")) {
																break;
															}
															break;
														case 3322014:
															if (!i.equals("list")) {
																break label3310;
																if (args.length == 4) {
																	final String commandToAdd = args[3];
																	if (commandToAdd.contains("/")) {
																		commandToAdd.replace("/", "");
																	}
																	final List<String> dcListAdd = this.plugin.getConfig().getStringList("arena.disabled-commands");
																	if (!dcListAdd.contains(commandToAdd)) {
																		dcListAdd.add(commandToAdd);
																		player.sendMessage(this.plugin.TAG + ChatColor.GREEN + "Succesfully added /" + commandToAdd
																				+ " to the disabled commands");
																	} else {
																		player.sendMessage(this.plugin.TAG + ChatColor.RED + "The disabled commands list already contains /"
																				+ commandToAdd);
																		return true;
																	}
																	Configuration.disabledCommands = dcListAdd;
																	this.plugin.getConfig().set("arena.disabled-commands", dcListAdd);
																	break label4067;
																}
																player.sendMessage(this.plugin.TAG + ChatColor.RED + "Usage: /ovo config disabledcommands add [command]");
																break label4067;
																if (args.length == 4) {
																	final String commandToRemove = args[3];
																	if (commandToRemove.contains("/")) {
																		commandToRemove.replace("/", "");
																	}
																	final List<String> dcListRemove = this.plugin.getConfig().getStringList("arena.disabled-commands");
																	if (dcListRemove.contains(commandToRemove)) {
																		dcListRemove.remove(commandToRemove);
																		player.sendMessage(this.plugin.TAG + ChatColor.GREEN + "Succesfully remove /" + commandToRemove
																				+ " from the disabled commands");
																	} else {
																		player.sendMessage(this.plugin.TAG + ChatColor.RED + "The disabled commands list doesn't contain /"
																				+ commandToRemove);
																		return true;
																	}
																	Configuration.disabledCommands = dcListRemove;
																	this.plugin.getConfig().set("arena.disabled-commands", dcListRemove);
																	break label4067;
																}
																player.sendMessage(this.plugin.TAG + ChatColor.RED + "Usage: /ovo config disabledcommands remove [command]");
																break label4067;
															} else {
																String message = "";
																int index = 0;
																for (final String s : Configuration.disabledCommands) {
																	if (index == 0) {
																		message = message + "/" + s;
																	} else {
																		message = message + ", " + "/" + s;
																	}
																	index++;
																}
																player.sendMessage(this.plugin.TAG + ChatColor.GREEN + "Disabled Commands(" + index + "): " + message);
															}
															break;
														}
														label3310: player.sendMessage(this.plugin.TAG + ChatColor.RED + "\"/ovo config disabledcommands " + args[2]
																+ "\" was not recognized!");
													} else {
														this.plugin.noPerms(player);
													}
												}
											}
										}
										break;
									}
									label3377: player.sendMessage(this.plugin.TAG + ChatColor.RED + "\"/ovo config " + args[1] + "\" was not recognized!");
									break label4067;
									player.sendMessage(this.plugin.TAG + ChatColor.RED + "Commands:");
									player.sendMessage(ChatColor.GREEN + "/ovo enter");
									player.sendMessage(ChatColor.GREEN + "/ovo leave");
									player.sendMessage(ChatColor.GREEN + "/ovo invite [player]");
									player.sendMessage(ChatColor.GREEN + "/ovo accept [player]");
									player.sendMessage(ChatColor.GREEN + "/ovo config [option]");
									player.sendMessage(ChatColor.GREEN + "/ovo info");
									break label4067;
									player.sendMessage(this.plugin.TAG + ChatColor.GREEN + "Author: " + ChatColor.DARK_RED + "werter318" + ChatColor.GREEN + ", Version: "
											+ this.plugin.getDescription().getVersion());
									break label4067;
									if (player.hasPermission("ovo.use")) {
										if (!Configuration.isConfigured) {
											player.sendMessage(this.plugin.TAG + ChatColor.RED + Configuration.checkConfigured());
										} else if (!this.plugin.getDuelManager().isOneVsOne(player)) {
											this.plugin.getDuelManager().enter(player);
										} else {
											player.sendMessage(this.plugin.TAG + ChatColor.GREEN + "You are already in the One Vs One arena!");
										}
									} else {
										this.plugin.noPerms(player);
										break label4067;
										if (player.hasPermission("ovo.use")) {
											if (this.plugin.getDuelManager().isOneVsOne(player)) {
												if (!this.plugin.getDuelManager().isInDuel(player)) {
													this.plugin.getDuelManager().leave(player, true);
												} else {
													player.sendMessage(this.plugin.TAG + ChatColor.RED + "Don't leave the One Vs One arena while dueling!");
												}
											} else {
												player.sendMessage(this.plugin.TAG + ChatColor.RED
														+ "You have to be in the One Vs One arena to leave it, \"/ovo enter\" to enter One Vs One!");
											}
										} else {
											this.plugin.noPerms(player);
										}
									}
								}
							}
						}
					}
				}
				break;
			}
			label4015: player.sendMessage(this.plugin.TAG + ChatColor.RED + "\"/ovo " + args[0] + "\" was not recognized!");
		}
		label4067: return false;
	}
}
