package me.rabrg.kits.command;

import java.util.ArrayList;
import java.util.Arrays;

import me.rabrg.kits.Kit;
import me.rabrg.kits.RabrgKitsPlugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public final class SaveKitCommandExecutor implements CommandExecutor {

	private final RabrgKitsPlugin plugin;

	public SaveKitCommandExecutor(final RabrgKitsPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if (args.length < 2) {
			return false;
		}
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "I can't save your kit!");
		}
		
		final Kit kit = new Kit(args[0]);
		
		final StringBuffer description = new StringBuffer();
		for (int i = 1; i < args.length; i++) {
			description.append(args[i]).append(' ');
		}
		kit.setDescription(description.substring(0, description.length() - 1));
		
		final Player player = (Player) sender;
		
		kit.setHelmet(player.getInventory().getHelmet());
		kit.setChestplate(player.getInventory().getChestplate());
		kit.setLeggings(player.getInventory().getLeggings());
		kit.setBoots(player.getInventory().getBoots());
		
		kit.setInventory(Arrays.asList(player.getInventory().getContents()));
		
		kit.setPotionEffects(new ArrayList<PotionEffect>(player.getActivePotionEffects()));
		
		plugin.putKit(args[0], kit);
		
		sender.sendMessage(ChatColor.AQUA + "You have saved your current kit as kit " + ChatColor.RED + args[0] + ChatColor.AQUA + "!");
		return true;
	}

}
