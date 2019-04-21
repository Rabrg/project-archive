package me.rabrg.ares.kit.impl;

import org.bukkit.inventory.PlayerInventory;
import org.bukkit.permissions.Permission;
import org.bukkit.potion.PotionEffect;

import me.rabrg.ares.kit.Kit;
import me.rabrg.ares.kit.KitEffect;

public final class MeleeKit implements Kit {

	@Override
	public String getName() {
		return "Melee";
	}

	@Override
	public Permission getPermission() {
		return new Permission("rabrg.ares.kit.melee");
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
