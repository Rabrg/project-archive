package me.rabrg.kitpvp.command;

import me.rabrg.kitpvp.KitPvP;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class KitsCommandExecutor implements CommandExecutor {

	private final KitPvP kitPvP;

	public KitsCommandExecutor(final KitPvP kitPvP) {
		this.kitPvP = kitPvP;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if(sender instanceof Player) {
			sender.sendMessage(kitPvP.getKitManager().getKits((Player) sender));
		}
		return true;
	}

}
