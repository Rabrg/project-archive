package me.rabrg.skywars.utilities;

import java.util.List;

public class StringUtils {

	public static String toString(final String[] args, final char color1, final char color2) {
		final StringBuilder stringBuilder = new StringBuilder();

		if (args.length > 1) {
			for (int iii = 0; iii < args.length - 1; iii++) {
				stringBuilder.append("\247");
				stringBuilder.append(color1);
				stringBuilder.append(args[iii]);
				stringBuilder.append("\247");
				stringBuilder.append(color2);
				stringBuilder.append(", ");
			}
		}

		stringBuilder.append("\247");
		stringBuilder.append(color1);
		stringBuilder.append(args[args.length - 1]);

		return stringBuilder.toString();
	}

	public static String toString(final List<String> args, final char color1, final char color2) {
		return toString(args.toArray(new String[args.size()]), color1, color2);
	}

	public static String formatScore(final int score) {
		return formatScore(score, "");
	}

	public static String formatScore(final int score, final String note) {
		char color = '7';

		if (score > 0) {
			color = 'a';
		} else if (score < 0) {
			color = 'c';
		}

		return "\247" + color + "(" + (score > 0 ? "+" : "") + score + note + ")";
	}
}
