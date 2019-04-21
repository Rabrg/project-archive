package me.rabrg.ares.kit.impl;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.permissions.Permission;
import org.bukkit.potion.PotionEffect;

import me.rabrg.ares.kit.Kit;
import me.rabrg.ares.kit.KitEffect;
import me.rabrg.ares.player.AresPlayerManager;

public final class FishermanKit implements Kit, Listener {

	@Override
	public String getName() {
		return "Fisherman";
	}

	@Override
	public Permission getPermission() {
		return new Permission("rabrg.ares.kit.fisherman");
	}

	@Override
	public PlayerInventory getPlayerInventory(PlayerInventory inventory) {
		return inventory;
	}

	@Override
	public PotionEffect[] getPotionEffects() {
		return null;
	}

	@Override
	public KitEffect[] getKitEffects() {
		return new KitEffect[] { KitEffect.FISHING_ROD_PULL };
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerFishEvent(final PlayerFishEvent e) {
		final Player player = e.getPlayer();
		
		if (AresPlayerManager.getAresPlayer(player).hasKitEffect(KitEffect.FISHING_ROD_PULL)) { 
			if (e.getCaught() instanceof Player) {
				final Player caught = (Player) e.getCaught();
				caught.teleport(player.getLocation());
			}
		}
	}
}
