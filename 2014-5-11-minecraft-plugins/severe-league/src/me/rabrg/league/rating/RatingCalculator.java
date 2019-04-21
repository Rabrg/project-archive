package me.rabrg.league.rating;

public final class RatingCalculator {

	private RatingCalculator() {

	}

	public static int getNewRating(final int rating, final int opponentRating, final boolean won) {
		double expectedScore = getExpectedScore(rating, opponentRating);
		int newRating = calculateNewRating(rating, won ? 1 : -1, expectedScore, getKFactor(rating));
		return newRating;
	}

	private static double getExpectedScore(final int rating, final int opponentRating) {
		return 1.0 / (1.0 + Math.pow(10.0, ((double) (opponentRating - rating) / 400.0)));
	}

	private static int calculateNewRating(final int oldRating, double score, double expectedScore, double kFactor) {
		return oldRating + (int) (kFactor * (score - expectedScore));
	}

	private static int getKFactor(final int rating) {
		if (rating < 2100) {
			return 32;
		} else if (rating > 2100 && rating < 2400) {
			return 24;
		} else {
			return 16;
		}
	}
}
