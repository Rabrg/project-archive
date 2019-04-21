package me.rabrg.unknown.util;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public final class PlayerUtility {

	private PlayerUtility() {
		
	}

	public static void removeAndAddPotionEffects(final Player player, final PotionEffect[] newPotionEffects) {
		for (final PotionEffect potionEffect : player.getActivePotionEffects()) {
			player.removePotionEffect(potionEffect.getType());
		}
		
		if (newPotionEffects != null) {
			for (final PotionEffect potionEffect : newPotionEffects) {
				player.addPotionEffect(potionEffect, false);
			}
		}
	}
}
