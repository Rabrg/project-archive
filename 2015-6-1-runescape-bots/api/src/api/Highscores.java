package api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * A wrapper for the RuneScape old school highscores API.
 * @author Ryan Greene
 *
 */
public final class Highscores {

	/**
	 * A private constructor to prevent initialization.
	 */
	private Highscores() {
		
	}

	/**
	 * The base URL of the API request.
	 */
	private static final String BASE_URL = "http://services.runescape.com/m=hiscore_oldschool/index_lite.ws?player=";

	/**
	 * The amount of skills in old school RuneScape.
	 */
	private static final int SKILL_COUNT = 24;

	/**
	 * The amount of recorded activities in old school RuneScape.
	 */
	private static final int ACTIVITY_COUNT = 3;

	/**
	 * Gets all of the specified player's skills.
	 * @param playerName The name of the player to get the skills of.
	 * @return The skills of the player.
	 * @throws IllegalArgumentException if the player name isn't a valid one.
	 */
	public static Skill[] getSkills(final String playerName) {
		try {
			final Skill[] skills = new Skill[SKILL_COUNT];
			try (final BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(new StringBuffer(BASE_URL).append(playerName).toString()).openStream()))) {
				for (int skill = 0; skill < SKILL_COUNT; skill++) {
					final String[] skillEncodedSplit = reader.readLine().split(",");
					skills[skill] = new Skill(Integer.parseInt(skillEncodedSplit[0]), Integer.parseInt(skillEncodedSplit[1]), Integer.parseInt(skillEncodedSplit[2]));
				}
			}
			return skills;
		} catch (final IOException e) {
			throw new IllegalArgumentException("Username not valid");
		}
	}

	/**
	 * Gets the specified skill of the specified player.
	 * @param playerName The name of the player to get the skill of.
	 * @param skillId The id of the skill to get.
	 * @return The skill of the player.
	 */
	public static Skill getSkill(final String playerName, int skillId) {	
		return getSkills(playerName)[skillId];
	}

	/**
	 * Gets all of the specified player's activities.
	 * @param playerName The name of the player to get the activities of.
	 * @return The activities of the player.
	 * @throws IllegalArgumentException if the player name isn't a valid one.
	 */
	public static Activity[] getActivities(final String playerName) {
		try {
			final Activity[] activities = new Activity[ACTIVITY_COUNT];
			try (final BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(new StringBuffer(BASE_URL).append(playerName).toString()).openStream()))) {
				for (int skill = 0; skill < SKILL_COUNT; skill++) {
					reader.readLine();
				}
				for (int activity = 0; activity < ACTIVITY_COUNT; activity++) {
					final String[] activityEncodedSplit = reader.readLine().split(",");
					activities[activity] = new Activity(Integer.parseInt(activityEncodedSplit[0]), Integer.parseInt(activityEncodedSplit[1]));
				}
			}
			return activities;
		} catch (final IOException e) {
			throw new IllegalArgumentException("Username not valid");
		}
	}

	/**
	 * Gets the specified activity of the specified player.
	 * @param playerName The name of the player to get the activity of.
	 * @param activityId The id of the activity to get.
	 * @return The activity of the player.
	 */
	public static Activity getActivity(final String playerName, int activityId) {	
		return getActivities(playerName)[activityId];
	}

	/**
	 * Represents a single skill.
	 * @author Ryan Greene
	 *
	 */
	public static class Skill {

		/**
		 * The rank of the skill.
		 */
		private final int rank;

		/**
		 * The level of the skill.
		 */
		private final int level;

		/**
		 * The xp of the skill.
		 */
		private final int xp;

		/**
		 * Constructs a new skill with the specified rank, level, and xp.
		 * @param rank The rank of the skill.
		 * @param level The level of the skill.
		 * @param xp The xp of the skill.
		 */
		private Skill(final int rank, final int level, final int xp) {
			this.rank = rank;
			this.level = level;
			this.xp = xp;
		}

		/**
		 * Gets the rank of the skill.
		 * @return The rank of the skill.
		 */
		public int getRank() {
			return rank;
		}

		/**
		 * Gets the level of the skill.
		 * @return The level of the skill.
		 */
		public int getLevel() {
			return level;
		}

		/**
		 * Gets the xp of the skill.
		 * @return The xp of the skill.
		 */
		public int getXp() {
			return xp;
		}
	}

	/**
	 * Represents a single activity.
	 * @author Ryan Greene
	 *
	 */
	public static class Activity {

		/**
		 * The rank of the activity.
		 */
		private final int rank;

		/**
		 * The score of the activity.
		 */
		private final int score;

		/**
		 * Constructs a new activity with the specified rank and score.
		 * @param rank The rank of the activity.
		 * @param score The score of the activity.
		 */
		private Activity(final int rank, final int score) {
			this.rank = rank;
			this.score = score;
		}

		/**
		 * Gets the rank of the activity.
		 * @return The rank of the activity.
		 */
		public int getRank() {
			return rank;
		}

		/**
		 * Gets the score of the activity.
		 * @return The score of the activity.
		 */
		public int getScore() {
			return score;
		}
	}
}
