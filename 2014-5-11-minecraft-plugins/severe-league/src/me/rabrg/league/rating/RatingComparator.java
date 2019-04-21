package me.rabrg.league.rating;

import java.util.Comparator;

import me.rabrg.league.League;

import org.bukkit.entity.Player;

public final class RatingComparator implements Comparator<Player> {

	private final League league;

	public RatingComparator(final League league) {
		this.league = league;
	}

	@Override
	public int compare(final Player player1, final Player player2) {
		final int player1Rating = league.getRatings().getRating(player1);
		final int player2Rating = league.getRatings().getRating(player2);

		if (player1Rating > player2Rating) {
			return 1;
		} else if (player1Rating < player2Rating) {
			return -1;
		}
		return 0;
	}

}
