package me.rabrg.forcefield;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.packetwrapper.WrapperPlayServerEntityTeleport;
import com.comphenix.packetwrapper.WrapperPlayServerNamedEntitySpawn;
import com.comphenix.protocol.Packets;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;

@SuppressWarnings("deprecation")
public class Plugin extends JavaPlugin implements Listener {

	private final Plugin instance;

	private final List<Player> players;

	private final Map<Integer, Long> timers;

	private final List<Integer> indices;

	private int index;

	private int static_count = 8;

	public Plugin() {
		instance = this;
		players = new ArrayList<Player>();
		timers = new HashMap<Integer, Long>();
		indices = new ArrayList<Integer>();
		index = 123456789;
	}

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ConnectionSide.CLIENT_SIDE, Packets.Client.USE_ENTITY) {
			@Override
			public void onPacketReceiving(final PacketEvent event) {
				final int id = event.getPacket().getIntegers().getValues().get(0);
				if (indices.contains(id)) {
					if (id > 123456789 + static_count) {
						if (timers.get(id) < System.currentTimeMillis()) {
							for (final Player player : players) {
								move(player, new Location(player.getWorld(), 0, 0, 0), id);
							}
						} else {
							move(event.getPlayer(), event.getPlayer().getLocation(), id);
							timers.put(id, System.currentTimeMillis() + 1000);
						}
					}

					if (!players.contains(event.getPlayer())) {
						players.add(event.getPlayer());
					}if (event.getPlayer().hasMetadata("forcefield")) {
						event.getPlayer().setMetadata("forcefield", new FixedMetadataValue(instance, event.getPlayer().getMetadata("forcefield").get(0).asInt() + 1));
						
						if (event.getPlayer().getMetadata("forcefield").get(0).asInt() > 100) {
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tempban " + event.getPlayer().getName() + " 1d automated hack  reason: forcefield length: 1d info: " + event.getPlayer().getMetadata("forcefield").get(0).asInt());
							getServer().broadcastMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "SevereAnti" + ChatColor.RED + "Hack" + ChatColor.DARK_RED + "] " + ChatColor.RED + event.getPlayer().getName() + ChatColor.YELLOW + " has been SNIPED by Rabrg. reason: forcefield length: 1d info: " + event.getPlayer().getMetadata("forcefield").get(0).asInt());
							getServer().broadcastMessage(ChatColor.DARK_RED + "[" + ChatColor.AQUA + "SevereAnti" + ChatColor.RED + "Hack" + ChatColor.DARK_RED + "] " + ChatColor.YELLOW + "Has sniped: "  + ChatColor.RED +  "a lot of" + ChatColor.YELLOW + " players.");
						}
					} else {
						event.getPlayer().setMetadata("forcefield", new FixedMetadataValue(instance, 1));
					}
				}
			}
		});
	}

	@Override
	public void onDisable() {
		
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if (args[0].equalsIgnoreCase("test") || args[0].equalsIgnoreCase("t")) {
			if (args == null || args.length != 2) {
				sender.sendMessage("Correct usage: /forcefield test <name>");
				return true;
			}
			final Player player = getServer().getPlayer(args[1]);
			if (player == null) {
				sender.sendMessage("That player couldn't be found.");
				return true;
			}
			spawn(player, player.getLocation(), false);
		} else if (args[0].equalsIgnoreCase("testinvis") || args[0].equalsIgnoreCase("ti")) {
			if (args == null || args.length != 2) {
				sender.sendMessage("Correct usage: /forcefield test <name>");
				return true;
			}
			final Player player = getServer().getPlayer(args[1]);
			if (player == null) {
				sender.sendMessage("That player couldn't be found.");
				return true;
			}
			spawn(player, player.getLocation(), true);
		} else if (args[0].equalsIgnoreCase("count") || args[0].equalsIgnoreCase("c")) {
			if (players.size() == 0) {
				sender.sendMessage("There are currently no players being counted.");
			} else {
				for (final Player player : players) {
					sender.sendMessage(player.getName() + " attacked a fake player " + player.getMetadata("forcefield").get(0).asInt() + " times.");
				}
			}
		} else if (args[0].equalsIgnoreCase("clear")) {
			for (final Player player : players) {
				if (player.hasMetadata("forcefield")) {
					player.removeMetadata("forcefield", instance);
				}
			}
			sender.sendMessage("You have cleared " + players.size() + " players from the counter.");
			players.clear();
		}
		return true;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoinEvent(final PlayerJoinEvent event) {
		spawn(event.getPlayer(), new Location(getServer().getWorld("world"), 223.5, 75, 286.5), true);
		spawn(event.getPlayer(), new Location(getServer().getWorld("world"), 219.5, 75, 295.5), true);
		spawn(event.getPlayer(), new Location(getServer().getWorld("world"), 285.5, 76, 301.5), true);
		spawn(event.getPlayer(), new Location(getServer().getWorld("world"), 285.5, 76, 281.5), true);
		spawn(event.getPlayer(), new Location(getServer().getWorld("world"), 293.5, 77, 301.5), true);
		spawn(event.getPlayer(), new Location(getServer().getWorld("world"), 393.5, 77, 281.5), true);
		spawn(event.getPlayer(), new Location(getServer().getWorld("world"), 312.5, 73, 282.5), true);
		spawn(event.getPlayer(), new Location(getServer().getWorld("world"), 320.5, 71, 304), true);
		spawn(event.getPlayer(), new Location(getServer().getWorld("world"), 337.5, 74, 332.5), true);
	}

	private void spawn(final Player player, final Location location, final boolean invis) {
		final ProtocolManager manager = ProtocolLibrary.getProtocolManager();
		final WrapperPlayServerNamedEntitySpawn spawned = new WrapperPlayServerNamedEntitySpawn();
		
		spawned.setPlayerUUID("" + index);
		spawned.setEntityID(index);
		spawned.setPosition(location.toVector());
		spawned.setY(location.getY() - 1);
		spawned.setPlayerName("Rabrg_" + index);
		
		spawned.setYaw(0);
		spawned.setPitch(-45);
		
		final WrappedDataWatcher watcher = new WrappedDataWatcher();
		watcher.setObject(0, (byte) (invis ? 0x20 : 0));
		watcher.setObject(1, (short) 300);
		watcher.setObject(8, (byte) 0);
		spawned.setMetadata(watcher);
		
		indices.add(index);
		timers.put(index, System.currentTimeMillis() + 1000);
		index++;
		
		try {
			manager.sendServerPacket(player, spawned.getHandle());
		} catch (final InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public void move(final Player player, final Location location, final int id) {
		final ProtocolManager manager = ProtocolLibrary.getProtocolManager();
		final WrapperPlayServerEntityTeleport spawned = new WrapperPlayServerEntityTeleport();
		
		spawned.setEntityID(id);
		spawned.setX(location.getX());
		spawned.setY(location.toVector().getY() - 1.25);
		spawned.setZ(location.toVector().getZ());
		
		try {
			manager.sendServerPacket(player, spawned.getHandle());
		} catch (final InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}