package me.rabrg.league.match;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class MatchArena {

	private final Location redSpawn;

	private final Location blueSpawn;

	private Player red;

	private Player blue;

	private boolean occupied;

	public MatchArena(final Location redSpawn, final Location blueSpawn) {
		this.redSpawn = redSpawn;
		this.blueSpawn = blueSpawn;
	}

	public void startMatch(final Player red, final Player blue) {
		this.red = red;
		this.blue = blue;
		red.teleport(redSpawn);
		blue.teleport(blueSpawn);
		startPlayer(red);
		red.sendMessage("You have been matched up against " + blue.getName() + "!");
		startPlayer(blue);
		blue.sendMessage("You have been matched up against " + red.getName() + "!");
		occupied = true;
	}

	public Player getRed() {
		return red;
	}

	public Player getBlue() {
		return blue;
	}

	public boolean isOccupied() {
		return occupied;
	}

	private static void startPlayer(final Player player) {
		player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
		player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
		player.getInventory().setItem(0, new ItemStack(Material.DIAMOND_SWORD));
		
		for (int slot = 1; slot < 9; slot++) {
			player.getInventory().setItem(slot, new ItemStack(Material.MUSHROOM_SOUP));
		}
	}
}
