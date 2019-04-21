package me.rabrg.skywars.game;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import me.rabrg.skywars.SkyWars;
import me.rabrg.skywars.config.PluginConfig;
import me.rabrg.skywars.controllers.GameController;
import me.rabrg.skywars.controllers.IconMenuController;
import me.rabrg.skywars.controllers.KitController;
import me.rabrg.skywars.controllers.PlayerController;
import me.rabrg.skywars.controllers.SchematicController;
import me.rabrg.skywars.controllers.WorldController;
import me.rabrg.skywars.player.GamePlayer;
import me.rabrg.skywars.utilities.CraftBukkitUtil;
import me.rabrg.skywars.utilities.Messaging;
import me.rabrg.skywars.utilities.PlayerUtil;
import me.rabrg.skywars.utilities.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sk89q.worldedit.CuboidClipboard;

public class Game {

	private GameState gameState;
	private final Map<Integer, GamePlayer> idPlayerMap = Maps.newLinkedHashMap();
	private final Map<GamePlayer, Integer> playerIdMap = Maps.newHashMap();
	private int playerCount = 0;
	private final int slots;
	private final Map<Integer, Location> spawnPlaces = Maps.newHashMap();
	private int timer;
	private Scoreboard scoreboard;
	private Objective objective;
	private boolean built;

	private final CuboidClipboard schematic;
	private final World world;
	private int[] islandCoordinates;
	private final List<Location> chestList = Lists.newArrayList();

	public Game(final CuboidClipboard schematic) {
		this.schematic = schematic;
		world = WorldController.get().create(this, schematic);
		slots = spawnPlaces.size();
		gameState = GameState.WAITING;

		for (int iii = 0; iii < slots; iii++) {
			idPlayerMap.put(iii, null);
		}
	}

	public boolean isBuilt() {
		return built;
	}

	public void setBuilt(final boolean built) {
		this.built = built;
	}

	public void setTimer(final int timer) {
		this.timer = timer;
	}

	public void addChest(final Location location) {
		chestList.add(location);
	}

	public void removeChest(final Location location) {
		chestList.remove(location);
	}

	public boolean isChest(final Location location) {
		return chestList.contains(location);
	}

	public boolean isReady() {
		return slots >= 2;
	}

	public World getWorld() {
		return world;
	}

	public void setIslandCoordinates(final int[] coordinates) {
		islandCoordinates = coordinates;
	}

	public int[] getIslandCoordinates() {
		return islandCoordinates;
	}

	public void onPlayerJoin(final GamePlayer gamePlayer) {
		final Player player = gamePlayer.getBukkitPlayer();

		final int id = getFistEmpty();
		playerCount++;
		idPlayerMap.put(getFistEmpty(), gamePlayer);
		playerIdMap.put(gamePlayer, id);

		sendMessage(new Messaging.MessageFormatter().withPrefix().setVariable("player", player.getDisplayName()).setVariable("amount", String.valueOf(getPlayerCount()))
				.setVariable("slots", String.valueOf(slots)).format("game.join"));

		if (getMinimumPlayers() - playerCount != 0) {
			sendMessage(new Messaging.MessageFormatter().withPrefix().setVariable("amount", String.valueOf(getMinimumPlayers() - playerCount)).format("game.required"));
		}

		PlayerUtil.refreshPlayer(player);

		if (PluginConfig.saveInventory()) {
			gamePlayer.saveCurrentState();
		}

		if (PluginConfig.clearInventory()) {
			PlayerUtil.clearInventory(player);
		}

		if (player.getGameMode() != GameMode.SURVIVAL) {
			player.setGameMode(GameMode.SURVIVAL);
		}

		gamePlayer.setGame(this);
		gamePlayer.setChosenKit(false);
		gamePlayer.setSkipFallDamage(true);
		player.teleport(getSpawn(id).clone().add(0.5, 0.5, 0.5));

		KitController.get().openKitMenu(gamePlayer);

		if (!PluginConfig.buildSchematic()) {
			timer = getTimer();
		}
	}

	public void onPlayerLeave(final GamePlayer gamePlayer) {
		onPlayerLeave(gamePlayer, true, true, true);
	}

	public void onPlayerLeave(final GamePlayer gamePlayer, final boolean displayText, final boolean process, final boolean left) {
		final Player player = gamePlayer.getBukkitPlayer();

		IconMenuController.get().destroy(player);

		if (displayText) {
			if (left && gameState == GameState.PLAYING) {
				final int scorePerLeave = PluginConfig.getScorePerLeave(player);
				gamePlayer.addScore(scorePerLeave);

				sendMessage(new Messaging.MessageFormatter().withPrefix().setVariable("player", player.getDisplayName())
						.setVariable("score", StringUtils.formatScore(scorePerLeave, Messaging.getInstance().getMessage("score.naming"))).format("game.quit.playing"));

			} else {
				sendMessage(new Messaging.MessageFormatter().withPrefix().setVariable("player", player.getDisplayName()).setVariable("total", String.valueOf(getPlayerCount()))
						.setVariable("slots", String.valueOf(slots)).format("game.quit.other"));
			}
		}

		if (scoreboard != null) {
			objective.getScore(player).setScore(-playerCount);
			try {
				player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
			} catch (final IllegalStateException ignored) {

			}
		}

		if (player.isDead()) {
			CraftBukkitUtil.forceRespawn(player);
		} else {
			PlayerUtil.refreshPlayer(player);
			PlayerUtil.clearInventory(player);

			player.teleport(PluginConfig.getLobbySpawn());

			if (PluginConfig.saveInventory()) {
				gamePlayer.restoreState();
			}
		}

		playerCount--;
		idPlayerMap.put(playerIdMap.remove(gamePlayer), null);
		gamePlayer.setGame(null);
		gamePlayer.setChosenKit(false);

		if (process && gameState == GameState.PLAYING && playerCount == 1) {
			onGameEnd(getWinner());
		}
	}

	public void onPlayerDeath(final GamePlayer gamePlayer, final PlayerDeathEvent event) {
		final Player player = gamePlayer.getBukkitPlayer();
		final Player killer = player.getKiller();

		final int scorePerDeath = PluginConfig.getScorePerDeath(player);
		gamePlayer.addScore(scorePerDeath);
		gamePlayer.setDeaths(gamePlayer.getDeaths() + 1);

		if (killer != null) {
			final GamePlayer gameKiller = PlayerController.get().get(killer);

			final int scorePerKill = PluginConfig.getScorePerKill(killer);
			gameKiller.addScore(scorePerKill);
			gameKiller.setKills(gameKiller.getKills() + 1);

			sendMessage(new Messaging.MessageFormatter().withPrefix().setVariable("player", player.getDisplayName()).setVariable("killer", killer.getDisplayName())
					.setVariable("player_score", StringUtils.formatScore(scorePerDeath, Messaging.getInstance().getMessage("score.naming")))
					.setVariable("killer_score", StringUtils.formatScore(scorePerKill, Messaging.getInstance().getMessage("score.naming"))).format("game.kill"));

		} else {
			sendMessage(new Messaging.MessageFormatter().withPrefix().setVariable("player", player.getDisplayName())
					.setVariable("score", StringUtils.formatScore(scorePerDeath, Messaging.getInstance().getMessage("score.naming"))).format("game.death"));
		}

		sendMessage(new Messaging.MessageFormatter().withPrefix().setVariable("remaining", String.valueOf(playerCount - 1)).format("game.remaining"));

		for (final GamePlayer gp : getPlayers()) {
			if (gp.equals(gamePlayer)) {
				gp.getBukkitPlayer().sendMessage(new Messaging.MessageFormatter().withPrefix().format("game.eliminated.self"));

			} else {
				gp.getBukkitPlayer().sendMessage(new Messaging.MessageFormatter().withPrefix().setVariable("player", player.getDisplayName()).format("game.eliminated.others"));
			}
		}

		if (event != null) {
			final Location location = player.getLocation().clone();
			final World world = location.getWorld();

			for (final ItemStack itemStack : event.getDrops()) {
				world.dropItemNaturally(location, itemStack);
			}

			world.spawn(location, ExperienceOrb.class).setExperience(event.getDroppedExp());

			event.setDeathMessage(null);
			event.getDrops().clear();
			event.setDroppedExp(0);

			onPlayerLeave(gamePlayer, false, true, false);

		} else {
			onPlayerLeave(gamePlayer, false, true, false);
		}
	}

	public void onGameStart() {
		registerScoreboard();
		gameState = GameState.PLAYING;

		for (final Map.Entry<Integer, GamePlayer> playerEntry : idPlayerMap.entrySet()) {
			final GamePlayer gamePlayer = playerEntry.getValue();

			if (gamePlayer != null) {
				objective.getScore(gamePlayer.getBukkitPlayer()).setScore(0);
				IconMenuController.get().destroy(gamePlayer.getBukkitPlayer());
				getSpawn(playerEntry.getKey()).clone().add(0, -1D, 0).getBlock().setType(Material.AIR);
				;
				gamePlayer.setGamesPlayed(gamePlayer.getGamesPlayed() + 1);
			}
		}

		for (final GamePlayer gamePlayer : getPlayers()) {
			gamePlayer.getBukkitPlayer().setHealth(20D);
			gamePlayer.getBukkitPlayer().setFoodLevel(20);

			gamePlayer.getBukkitPlayer().setScoreboard(scoreboard);
			gamePlayer.getBukkitPlayer().sendMessage(new Messaging.MessageFormatter().withPrefix().format("game.start"));
		}
	}

	public void onGameEnd() {
		onGameEnd(null);
	}

	public void onGameEnd(final GamePlayer gamePlayer) {
		if (gamePlayer != null) {
			final Player player = gamePlayer.getBukkitPlayer();
			final int score = PluginConfig.getScorePerWin(player);
			gamePlayer.addScore(score);
			gamePlayer.setGamesWon(gamePlayer.getGamesWon() + 1);

			Bukkit.broadcastMessage(new Messaging.MessageFormatter().withPrefix().setVariable("player", player.getDisplayName()).setVariable("score", String.valueOf(score))
					.setVariable("map", SchematicController.get().getName(schematic)).format("game.win"));
		}

		for (final GamePlayer player : getPlayers()) {
			onPlayerLeave(player, false, false, false);
		}

		gameState = GameState.ENDING;
		unregisterScoreboard();

		WorldController.get().unload(this);
		GameController.get().remove(this);
	}

	public void onTick() {
		if (timer <= 0 || gameState == GameState.WAITING && !hasReachedMinimumPlayers()) {
			return;
		}

		timer--;

		switch (gameState) {
		case WAITING:
			if (timer == 0) {
				onGameStart();
			} else if (timer % 10 == 0 || timer <= 5) {
				sendMessage(new Messaging.MessageFormatter().withPrefix().setVariable("timer", String.valueOf(timer)).format("game.countdown"));
			}
			break;

		case PLAYING:
			break;
		case ENDING:
			break;
		}
	}

	public GameState getState() {
		return gameState;
	}

	public boolean isFull() {
		return getPlayerCount() == slots;
	}

	public int getMinimumPlayers() {
		final FileConfiguration config = SkyWars.get().getConfig();
		final String schematicName = SchematicController.get().getName(schematic);

		return config.getInt("schematics." + schematicName + ".min-players", slots);
	}

	private int getTimer() {
		final FileConfiguration config = SkyWars.get().getConfig();
		final String schematicName = SchematicController.get().getName(schematic);

		return config.getInt("schematics." + schematicName + ".timer", 11);
	}

	public boolean hasReachedMinimumPlayers() {
		return getPlayerCount() >= getMinimumPlayers();
	}

	public void sendMessage(final String message) {
		for (final GamePlayer gamePlayer : getPlayers()) {
			gamePlayer.getBukkitPlayer().sendMessage(message);
		}
	}

	private GamePlayer getWinner() {
		for (final GamePlayer gamePlayer : idPlayerMap.values()) {
			if (gamePlayer == null) {
				continue;
			}

			return gamePlayer;
		}

		return null;
	}

	private int getFistEmpty() {
		for (final Map.Entry<Integer, GamePlayer> playerEntry : idPlayerMap.entrySet()) {
			if (playerEntry.getValue() == null) {
				return playerEntry.getKey();
			}
		}

		return -1;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public Collection<GamePlayer> getPlayers() {
		final List<GamePlayer> playerList = Lists.newArrayList();

		for (final GamePlayer gamePlayer : idPlayerMap.values()) {
			if (gamePlayer != null) {
				playerList.add(gamePlayer);
			}
		}

		return playerList;
	}

	private Location getSpawn(final int id) {
		return spawnPlaces.get(id);
	}

	public void addSpawn(final int id, final Location location) {
		spawnPlaces.put(id, location);
	}

	private void registerScoreboard() {
		if (scoreboard != null) {
			unregisterScoreboard();
		}

		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		objective = scoreboard.registerNewObjective("info", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName("\247c\247lLeaderBoard");
	}

	private void unregisterScoreboard() {
		if (objective != null) {
			objective.unregister();
		}

		if (scoreboard != null) {
			scoreboard = null;
		}
	}
}
