package me.rabrg.nickname;

import me.rabrg.nickname.command.NameCommandExecutor;

import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

	@Override
	public void onEnable() {
		getCommand("displayname").setExecutor(new NameCommandExecutor(this));
	}
}
