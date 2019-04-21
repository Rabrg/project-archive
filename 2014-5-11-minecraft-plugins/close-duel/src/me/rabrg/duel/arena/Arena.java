package me.rabrg.duel.arena;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.rabrg.duel.player.DuelPlayer;

public final class Arena {

	private final Location redPlayerSpawn;

	private final Location bluePlayerSpawn;

	private DuelPlayer redPlayer;

	private DuelPlayer bluePlayer;

	public Arena(final Location redPlayerSpawn, final Location bluePlayerSpawn) {
		this.redPlayerSpawn = redPlayerSpawn;
		this.bluePlayerSpawn = bluePlayerSpawn;
	}

	public Location getRedPlayerSpawn() {
		return redPlayerSpawn;
	}

	public Location getBluePlayerSpawn() {
		return bluePlayerSpawn;
	}

	public void startDuel(final DuelPlayer redPlayer, final DuelPlayer bluePlayer) {
		this.redPlayer = redPlayer;
		this.bluePlayer = bluePlayer;
		
		redPlayer.setDueling(bluePlayer);
		redPlayer.setChallenged(null);
		redPlayer.setChallengedWith(null);
		redPlayer.setChallengedBy(null);
		redPlayer.setChallengedByWith(null);
		
		bluePlayer.setDueling(redPlayer);
		bluePlayer.setChallenged(null);
		bluePlayer.setChallengedWith(null);
		bluePlayer.setChallengedBy(null);
		bluePlayer.setChallengedByWith(null);
		
		redPlayer.getPlayer().teleport(redPlayerSpawn);
		bluePlayer.getPlayer().teleport(bluePlayerSpawn);
		
		Bukkit.broadcastMessage("[Duel] " + redPlayer.getPlayer().getName() + " is now dueling " + bluePlayer.getPlayer().getName() + "!");
	}

	public void endDuel(final DuelPlayer winner, final DuelPlayer loser) {
		redPlayer.getPlayer().setHealth(20);
		redPlayer.getPlayer().getInventory().clear();
		redPlayer.getPlayer().getInventory().setArmorContents(null);
		redPlayer.getPlayer().getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
		redPlayer.getPlayer().teleport(ArenaManager.ARENA_SPAWN);
		redPlayer = null;
		
		bluePlayer.getPlayer().setHealth(20);
		bluePlayer.getPlayer().getInventory().clear();
		bluePlayer.getPlayer().getInventory().setArmorContents(null);
		bluePlayer.getPlayer().getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
		bluePlayer.getPlayer().teleport(ArenaManager.ARENA_SPAWN);
		bluePlayer = null;
		
		Bukkit.broadcastMessage("[Duel] " + winner.getPlayer().getName() + " has won a duel against " + loser.getPlayer().getName() + "!");
	}

	public boolean isVacant() {
		return redPlayer == null;
	}

	public DuelPlayer getRedPlayer() {
		return redPlayer;
	}

	public DuelPlayer getBluePlayer() {
		return bluePlayer;
	}
}
