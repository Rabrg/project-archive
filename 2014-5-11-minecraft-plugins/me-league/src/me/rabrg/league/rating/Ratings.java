package me.rabrg.league.rating;

import java.io.File;

import me.rabrg.league.League;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public final class Ratings {

	private final File file;

	private final FileConfiguration ratings;

	public Ratings(final League league) {
		this.file = new File(league.getDataFolder() + "/ratings.yml");
		this.ratings = YamlConfiguration.loadConfiguration(file);
	}

	public int getRating(final Player player) {
		return getRating(player.getName());
	}

	public int getRating(final String name) {
		return ratings.getInt(name);
	}

	public void adjustRatings(final Player winner, final Player loser) {
		ratings.set(winner.getName(), RatingCalculator.calculateNewRating(getRating(winner), getRating(loser), true));
		ratings.set(loser.getName(), RatingCalculator.calculateNewRating(getRating(winner), getRating(loser), false));
	}
}
