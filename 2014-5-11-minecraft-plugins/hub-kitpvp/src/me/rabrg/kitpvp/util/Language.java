package me.rabrg.kitpvp.util;

import org.bukkit.ChatColor;

public enum Language {
	TITLE("title-name", "&d[&fKitPvP&d]:&f"),
	KILLS_COMMAND_NONE("kills-command-none", "%s doesn't seem to have any kills."),
	KILLS_COMMAND_ONE("kills-command-one", "%s has %s kill."),
	KILLS_COMMAND_MULTIPLE("kills-command-multiple", "%s has %s kills."),
	KILLSTREAK_COMMAND_NONE("killstreak-command-none", "%s doesn't seem to have a killstreak."),
	KILLSTREAK_COMMAND_ONE("killstreak-command-one", "%s has a killstreak of %s."),
	KILLSTREAK_COMMAND_MULTIPLE("killstreak-command-multiple", "%s has a killstreak of %s."),
	DEATHS_COMMAND_NONE("kills-command-none", "%s doesn't seem to have any deaths."),
	DEATHS_COMMAND_ONE("kills-command-one", "%s has %s death."),
	DEATHS_COMMAND_MULTIPLE("kills-command-multiple", "%s has %s deaths."),
	HUNDREDTH_KILL_BROADCAST("hundredth-kill-broadcast", "%s has gotten their %sth kill!"),
	MULTIPLE_FIVE_KILLSTREAK_BROADCAST("multiple-five-killstreak-broadcast", "%s has gotten a killstreak of %s!");

	private String path;
	private String def;

	Language(final String path, final String start) {
		this.path = path;
		this.def = start;
	}

	@Override
	public String toString() {
		if (this == TITLE) {
			return ChatColor.translateAlternateColorCodes('&', def) + " ";
		}
		return Language.TITLE.toString() + " " + ChatColor.translateAlternateColorCodes('&', def);
	}

	public String toString(Object... args) {
		return Language.TITLE.toString() + String.format(def, args);
	}

	public String getDefault() {
		return this.def;
	}

	public String getPath() {
		return this.path;
	}
}