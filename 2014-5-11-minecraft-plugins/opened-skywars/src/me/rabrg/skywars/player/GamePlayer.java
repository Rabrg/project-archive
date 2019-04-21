package me.rabrg.skywars.player;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.rabrg.skywars.SkyWars;
import me.rabrg.skywars.config.PluginConfig;
import me.rabrg.skywars.game.Game;
import me.rabrg.skywars.storage.DataStorage;

public class GamePlayer {

	private final Player bukkitPlayer;
	private final String playerName;
	private Game game;
	private boolean chosenKit;
	private int score;
	private int gamesPlayed;
	private int gamesWon;
	private int kills;
	private int deaths;
	private boolean skipFallDamage;
	private ItemStack[] savedInventoryContents = null;
	private ItemStack[] savedArmorContents = null;

	public GamePlayer(final Player bukkitPlayer) {
		this.bukkitPlayer = bukkitPlayer;
		this.playerName = bukkitPlayer.getName();

		DataStorage.get().loadPlayer(this);
	}

	public void save() {
		DataStorage.get().savePlayer(this);
	}

	public Player getBukkitPlayer() {
		return bukkitPlayer;
	}

	public boolean isPlaying() {
		return game != null;
	}

	public void setGame(final Game game) {
		this.game = game;
	}

	public Game getGame() {
		return game;
	}

	public boolean hasChosenKit() {
		return chosenKit;
	}

	public void setChosenKit(final boolean yes) {
		chosenKit = yes;
	}

	public int getScore() {
		if (PluginConfig.useEconomy()) {
			return (int) SkyWars.getEconomy().getBalance(playerName);
		}

		return score;
	}

	public void setScore(final int score) {
		if (PluginConfig.useEconomy()) {
			final double balance = SkyWars.getEconomy().getBalance(playerName);
			if (balance < 0) {
				SkyWars.getEconomy().depositPlayer(playerName, -balance);
			} else {
				SkyWars.getEconomy().withdrawPlayer(playerName, balance);
			}
			addScore(score);

		} else {
			this.score = score;
		}
	}

	public void addScore(final int score) {
		if (PluginConfig.useEconomy()) {
			if (score < 0) {
				SkyWars.getEconomy().withdrawPlayer(playerName, -score);
			} else {
				SkyWars.getEconomy().depositPlayer(playerName, score);
			}

		} else {
			this.score += score;
		}
	}

	@Override
	public String toString() {
		return playerName;
	}

	public String getName() {
		return playerName;
	}

	public int getGamesPlayed() {
		return gamesPlayed;
	}

	public void setGamesPlayed(final int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}

	public int getGamesWon() {
		return gamesWon;
	}

	public void setGamesWon(final int gamesWon) {
		this.gamesWon = gamesWon;
	}

	public int getKills() {
		return kills;
	}

	public void setKills(final int kills) {
		this.kills = kills;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(final int deaths) {
		this.deaths = deaths;
	}

	public void setSkipFallDamage(final boolean skipFallDamage) {
		this.skipFallDamage = skipFallDamage;
	}

	public boolean shouldSkipFallDamage() {
		return skipFallDamage;
	}

	public void saveCurrentState() {
		savedArmorContents = bukkitPlayer.getInventory().getArmorContents().clone();
		savedInventoryContents = bukkitPlayer.getInventory().getContents().clone();
	}

	@SuppressWarnings("deprecation")
	public void restoreState() {
		boolean shouldUpdateInventory = false;

		if (savedArmorContents != null) {
			bukkitPlayer.getInventory().setArmorContents(savedArmorContents);
			savedArmorContents = null;
			shouldUpdateInventory = true;
		}

		if (savedInventoryContents != null) {
			bukkitPlayer.getInventory().setContents(savedInventoryContents);
			savedInventoryContents = null;
			shouldUpdateInventory = true;
		}

		if (shouldUpdateInventory) {
			bukkitPlayer.updateInventory();
		}
	}
}
