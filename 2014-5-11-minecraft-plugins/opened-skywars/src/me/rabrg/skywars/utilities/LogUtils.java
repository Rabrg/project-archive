package me.rabrg.skywars.utilities;

import java.util.logging.Level;

import me.rabrg.skywars.SkyWars;

public class LogUtils {

	public static void log(final Level level, String message, final Object... args) {
		if (args.length > 0) {
			message = String.format(message, args);
		}

		SkyWars.get().getLogger().log(level, message);
	}

	public static void log(final Level level, final Class<?> clazz, final String message, final Object... args) {
		log(level, String.format("[%s] %s", clazz.getSimpleName(), message), args);
	}
}
