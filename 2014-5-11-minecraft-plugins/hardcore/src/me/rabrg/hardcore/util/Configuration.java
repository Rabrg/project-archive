package me.rabrg.hardcore.util;

import me.rabrg.hardcore.Hardcore;

public final class Configuration {

	private final Hardcore hardcore;

	public Configuration(final Hardcore hardcore) {
		this.hardcore = hardcore;
	}

	public boolean killedByPlayerOnlyBan() {
		return hardcore.getConfig().getBoolean("death-ban.killed-by-player-only", false);
	}
}
