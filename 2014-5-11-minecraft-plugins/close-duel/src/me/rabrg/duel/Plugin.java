package me.rabrg.duel;

import me.rabrg.duel.arena.ArenaManager;
import me.rabrg.duel.listener.ChallengeListener;
import me.rabrg.duel.listener.DuelListener;
import me.rabrg.duel.listener.LobbyListener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

	@Override
	public void onEnable() {
		final PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(new LobbyListener(this), this);
		pluginManager.registerEvents(new ChallengeListener(this), this);
		pluginManager.registerEvents(new DuelListener(this), this);
		ArenaManager.initiate();
	}
}
