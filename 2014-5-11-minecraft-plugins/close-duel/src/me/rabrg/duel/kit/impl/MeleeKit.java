package me.rabrg.duel.kit.impl;

import me.rabrg.duel.kit.Kit;
import me.rabrg.duel.kit.KitEffect;

import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

public final class MeleeKit implements Kit {

	@Override
	public String getName() {
		return "Melee";
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
		return null;
	}

}
