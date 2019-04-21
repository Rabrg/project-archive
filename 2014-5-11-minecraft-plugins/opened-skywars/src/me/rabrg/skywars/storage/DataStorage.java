package me.rabrg.skywars.storage;

import javax.annotation.Nonnull;

import me.rabrg.skywars.player.GamePlayer;

public abstract class DataStorage {

	public abstract void loadPlayer(@Nonnull GamePlayer gamePlayer);

	public abstract void savePlayer(@Nonnull GamePlayer gamePlayer);

	public enum DataStorageType {
		FILE, SQL
	}

	private static DataStorage instance;

	public static void setInstance(final DataStorageType dataStorageType) {
		switch (dataStorageType) {
		case FILE:
			instance = new FlatFileStorage();
			break;
		case SQL:
			instance = new SQLStorage();
			break;
		}
	}

	public static DataStorage get() {
		if (instance == null) {
			instance = new FlatFileStorage();
		}

		return instance;
	}
}