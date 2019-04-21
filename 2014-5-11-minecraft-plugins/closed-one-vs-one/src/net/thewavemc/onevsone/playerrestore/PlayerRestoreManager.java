package net.thewavemc.onevsone.playerrestore;

import java.io.File;
import java.util.List;

import net.thewavemc.onevsone.Configuration;
import net.thewavemc.onevsone.OneVsOne;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerRestoreManager {
	OneVsOne plugin;

	public PlayerRestoreManager(final OneVsOne plugin) {
		this.plugin = plugin;

		final File file = new File(plugin.getDataFolder() + File.separator + "restore");
		if (!file.isDirectory()) {
			file.mkdir();
		}
		for (final Player p : Bukkit.getOnlinePlayers()) {
			if (needsRestore(p)) {
				p.sendMessage(plugin.TAG + ChatColor.GREEN + "Server is reloading so your data is getting restored!");
				try {
					restorePlayer(p);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void restorePlayer(final Player p1) throws Exception {
		final File file = new File(this.plugin.getDataFolder() + File.separator + "restore", p1.getName() + ".yml");
		if (Configuration.restore) {
			if (file.exists()) {
				final YamlConfiguration yc = YamlConfiguration.loadConfiguration(file);
				yc.load(file);

				p1.teleport(new Location(Bukkit.getWorld(yc.getString("location.world")), yc.getDouble("location.x"), yc.getDouble("location.y"), yc.getDouble("location.z"),
						(float) yc.getDouble("location.yaw"), (float) yc.getDouble("location.pitch")));

				p1.setVelocity(yc.getVector("player.data.velocity"));

				final List<ItemStack> itemsList = (List) yc.get("inventory.content.items");
				final ItemStack[] items = new ItemStack[36];
				for (int i = 0; i < itemsList.size(); i++) {
					items[i] = itemsList.get(i);
				}
				p1.getInventory().setContents(items);

				final List<ItemStack> armorList = (List) yc.get("inventory.content.armor");
				final ItemStack[] armor = new ItemStack[4];
				for (int i = 0; i < armorList.size(); i++) {
					armor[i] = armorList.get(i);
				}
				p1.getInventory().setArmorContents(armor);

				p1.setFallDistance((float) yc.getDouble("player.data.falldistance"));

				p1.setHealth(yc.getDouble("player.data.health"));
				p1.setFoodLevel(yc.getInt("player.data.hunger"));

				file.delete();
				p1.sendMessage(this.plugin.TAG + ChatColor.GREEN + "Your data has been restored!");
			} else {
				p1.sendMessage(this.plugin.TAG + ChatColor.RED + "An error accured while restoring your items, contact an admin!");
			}
		} else if (Configuration.exit != null) {
			this.plugin.clearPlayer(p1);
			p1.teleport(Configuration.exit);
			file.delete();
			p1.sendMessage(this.plugin.TAG + ChatColor.GREEN + "You have been teleported to the exit of the One Vs One arena!");
		} else {
			Bukkit.broadcastMessage(this.plugin.TAG + ChatColor.RED + "ERROR: Exit is null!");
		}
	}

	public void storePlayer(final Player p1, final PlayerRestore pr) throws Exception {
		if (Configuration.restore) {
			final File file = new File(this.plugin.getDataFolder() + File.separator + "restore", p1.getName() + ".yml");
			file.createNewFile();

			final YamlConfiguration yc = YamlConfiguration.loadConfiguration(file);
			yc.load(file);

			yc.set("location.world", pr.loc.getWorld().getName());
			yc.set("location.x", Double.valueOf(pr.loc.getX()));
			yc.set("location.y", Double.valueOf(pr.loc.getY()));
			yc.set("location.z", Double.valueOf(pr.loc.getZ()));
			yc.set("location.yaw", Float.valueOf(pr.loc.getYaw()));
			yc.set("location.pitch", Float.valueOf(pr.loc.getPitch()));

			yc.set("player.data.velocity", pr.velocity);

			yc.set("inventory.content.items", pr.invcontent);

			yc.set("inventory.content.armor", pr.armcontent);

			yc.set("player.data.falldistance", Float.valueOf(pr.fallDistance));

			yc.set("player.data.health", Double.valueOf(pr.health));
			yc.set("player.data.hunger", Integer.valueOf(pr.hunger));

			yc.save(file);
		} else {
			final File file = new File(this.plugin.getDataFolder() + File.separator + "restore", p1.getName() + ".yml");
			file.createNewFile();
		}
	}

	public boolean needsRestore(final Player p1) {
		final File file = new File(this.plugin.getDataFolder() + File.separator + "restore", p1.getName() + ".yml");
		if (file.exists()) {
			return true;
		}
		return false;
	}
}
