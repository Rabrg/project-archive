package me.rabrg.home;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import me.rabrg.home.command.DeleteHomeCommand;
import me.rabrg.home.command.GoHomeCommand;
import me.rabrg.home.command.ListHomesCommand;
import me.rabrg.home.command.SetHomeCommand;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class RabrgHomePlugin extends JavaPlugin {
	@Override
	public void onEnable() {
		getCommand("listhomes").setExecutor(new ListHomesCommand(this));
		getCommand("sethome").setExecutor(new SetHomeCommand(this));
		getCommand("gohome").setExecutor(new GoHomeCommand(this));
		getCommand("deletehome").setExecutor(new DeleteHomeCommand(this));

		saveDefaultConfig();
		setupDatabase();
	}

	@Override
	public void onDisable() {
		saveConfig();
	}

	private void setupDatabase() {
		try {
			getDatabase().find(Home.class).findRowCount();
		} catch (final PersistenceException ex) {
			System.out.println("Installing database for " + getDescription().getName() + " due to first time usage");
			installDDL();
		}
	}

	@Override
	public List<Class<?>> getDatabaseClasses() {
		final List<Class<?>> list = new ArrayList<>();
		list.add(Home.class);
		return list;
	}

	public static boolean anonymousCheck(final CommandSender sender) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Cannot execute that command, I don't know who you are!");
			return true;
		}
		return false;
	}

	public static OfflinePlayer getPlayer(final CommandSender sender, final String[] args, final int index) {
		if (args.length > index) {
			@SuppressWarnings("deprecation")
			final OfflinePlayer player = sender.getServer().getOfflinePlayer(args[index]);
			if (player == null) {
				sender.sendMessage("I don't know who '" + args[index] + "' is!");
				return null;
			}
			return player;
		}
		if (anonymousCheck(sender)) {
			return null;
		}
		return (Player) sender;
	}
}
