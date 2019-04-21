package me.happypikachu.BattleTags.listeners;

import me.happypikachu.BattleTags.BattleTags;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.TagAPI;

import com.massivecraft.factions.event.FPlayerJoinEvent;
import com.massivecraft.factions.event.FPlayerLeaveEvent;
import com.massivecraft.factions.event.FactionDisbandEvent;
import com.massivecraft.factions.event.FactionRelationEvent;

public class BattleTagsFactionsListener implements Listener {
	private BattleTags plugin;
    public BattleTagsFactionsListener(BattleTags plugin) {
            this.plugin = plugin;
    }
	
    @EventHandler
	public void onFPlayerJoin (FPlayerJoinEvent event) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (plugin.getConfig().getBoolean("Factions." + p.getWorld().getName())) {
				TagAPI.refreshPlayer(p);
			}
		}
	}
	
	@EventHandler
	public void onFPlayerLeave (FPlayerLeaveEvent event) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (plugin.getConfig().getBoolean("Factions." + p.getWorld().getName())) {
				TagAPI.refreshPlayer(p);
			}
		}
	}
	
    @EventHandler
	public void onFactionDisband (FactionDisbandEvent event) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (plugin.getConfig().getBoolean("Factions." + p.getWorld().getName())) {
				TagAPI.refreshPlayer(p);
			}
		}
	}
	
    @EventHandler
	public void onFactionRelation (FactionRelationEvent event) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (plugin.getConfig().getBoolean("Factions." + p.getWorld().getName())) {
				TagAPI.refreshPlayer(p);
			}
		}
	}
}