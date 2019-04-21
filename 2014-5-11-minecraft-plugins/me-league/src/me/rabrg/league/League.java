package me.rabrg.league;

import me.rabrg.league.rating.RatingComparator;
import me.rabrg.league.rating.Ratings;

import org.bukkit.plugin.java.JavaPlugin;

public final class League extends JavaPlugin {

	private Ratings ratings;

	private RatingComparator ratingComparator;

	@Override
	public void onEnable() {
		ratings = new Ratings(this);
		ratingComparator = new RatingComparator(this);
	}

	public Ratings getRatings() {
		return ratings;
	}

	public RatingComparator getRatingComparator() {
		return ratingComparator;
	}
}
