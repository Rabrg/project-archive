package net.thewavemc.onevsone;

import net.thewavemc.onevsone.command.CommandHandler;
import net.thewavemc.onevsone.duel.DuelManager;
import net.thewavemc.onevsone.listeners.ArenaInteractEvent;
import net.thewavemc.onevsone.listeners.ArenaRestrictedEvents;
import net.thewavemc.onevsone.listeners.CommandPreproccesEvent;
import net.thewavemc.onevsone.listeners.PlayerJoinQuitKickEvents;
import net.thewavemc.onevsone.listeners.PlayerLifeEvents;
import net.thewavemc.onevsone.playerrestore.PlayerRestoreManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

public class OneVsOne extends JavaPlugin {
	private DuelManager duelManager;
	private PlayerRestoreManager playerRestoreManager;
	public final String TAG = ChatColor.DARK_RED + "[" + ChatColor.GOLD + "O" + ChatColor.GOLD + "v" + ChatColor.GOLD + "O" + ChatColor.DARK_RED + "] " + ChatColor.RESET;

	@Override
	public void onEnable() {
		Configuration.loadConfig(this);

		this.duelManager = new DuelManager(this);
		this.playerRestoreManager = new PlayerRestoreManager(this);

		getCommand("ovo").setExecutor(new CommandHandler(this));

		Bukkit.getPluginManager().registerEvents(new ArenaRestrictedEvents(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerLifeEvents(this), this);
		Bukkit.getPluginManager().registerEvents(new ArenaInteractEvent(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoinQuitKickEvents(this), this);
		Bukkit.getPluginManager().registerEvents(new CommandPreproccesEvent(this), this);
	}

	@Override
	public void onDisable() {
		saveConfig();
	}

	public DuelManager getDuelManager() {
		return this.duelManager;
	}

	public PlayerRestoreManager getPlayerRestoreManager() {
		return this.playerRestoreManager;
	}

	public void clearPlayer(final Player p1) {
		p1.getInventory().clear();
		p1.getInventory().setArmorContents(null);
		for (final PotionEffect pe : p1.getActivePotionEffects()) {
			p1.removePotionEffect(pe.getType());
		}
	}

	public void noPerms(final Player p1) {
		p1.sendMessage(this.TAG + ChatColor.translateAlternateColorCodes('&', Configuration.noperms));
	}
}
