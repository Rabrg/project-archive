package me.rabrg.home.command;

import java.util.List;

import me.rabrg.home.Home;
import me.rabrg.home.RabrgHomePlugin;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.earth2me.essentials.Essentials;

public class GoHomeCommand implements CommandExecutor {
	private final RabrgHomePlugin plugin;
	private final Essentials essentials;

	public GoHomeCommand(final RabrgHomePlugin plugin) {
		this.plugin = plugin;
		essentials = (Essentials) plugin.getServer().getPluginManager().getPlugin("Essentials");
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		final OfflinePlayer player = RabrgHomePlugin.getPlayer(sender, args, 1);
		if (player == null) {
			return true;
		}
		if (player != sender && !sender.isOp()) {
			sender.sendMessage(ChatColor.AQUA + "You don't have permission to go to other players homes");
			return true;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.AQUA + "I don't know how to move you!");
			return true;
		}
		Home home = null;
		final List<Home> homes = plugin.getDatabase().find(Home.class).where().ieq("playerName", player.getName()).findList();
		if (homes.size() == 1) {
			home = homes.get(0);
		} else {
			if (args.length >= 1) {
				for (final Home home_ : homes) {
					if (home_.getName().equals(args[0])) {
						home = home_;
					}
				}
			}
		}
		if (home == null) {
			if (player != sender) {
				sender.getServer().dispatchCommand(sender, "listhomes " + player.getName());
			} else {
				sender.getServer().dispatchCommand(sender, "listhomes");
			}
		} else {
			try {
				essentials.getUser((Player) sender).getTeleport().teleport(home.getLocation(), null, TeleportCause.COMMAND);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}
