package me.rabrg.unknown;

import me.rabrg.unknown.kit.command.KitCommand;
import me.rabrg.unknown.kit.command.ListKitsCommand;

import org.bukkit.plugin.java.JavaPlugin;

public final class Unknown extends JavaPlugin {

	@Override
	public void onEnable() {
		registerCommands();
	}

	private void registerCommands() {
		getCommand("listkits").setExecutor(new ListKitsCommand());
		getCommand("kit").setExecutor(new KitCommand());
	}
}
