package denniss17.playerHealth;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.kitteh.tag.TagAPI;

public class PlayerHealth extends JavaPlugin {
	public static VersionChecker versionChecker;

	public static final String OBJECTIVE_NAME = "playerhealth";
	public static final int PROJECT_ID = 55658;

	public Map<Player, Set<String>> tags;

	@Override
	public void onEnable() {
		tags = new HashMap<Player, Set<String>>();

		for (final Player player : getServer().getOnlinePlayers()) {
			final HashSet<String> set = new HashSet<String>();
			set.add(player.getName());
			tags.put(player, set);
		}

		// Register PlayerListener
		this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
		if (getServer().getPluginManager().getPlugin("TagAPI") != null) {
			this.getServer().getPluginManager().registerEvents(new TagAPIListener(this), this);
			for (final Player player : getServer().getOnlinePlayers()) {
				// Resent tags for TagAPIListener to update tags
				TagAPI.refreshPlayer(player);
			}
			getLogger().info("TagAPI found, hooked into it...");
		} else {
			getLogger().info("TagAPI not found, continuing without...");
		}

		if (getConfig().getBoolean("check_updates")) {
			versionChecker = new VersionChecker(this, PROJECT_ID);
			versionChecker.activate(getConfig().getInt("check_updates_interval") * 60 * 20);
		}

		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	// Makes the health of other players visible for this player
	public void showHealth(final Player player) {
		Objective objective;
		try {
			objective = player.getScoreboard().registerNewObjective(OBJECTIVE_NAME, "dummy");
		} catch (final Exception e) {
			objective = player.getScoreboard().getObjective(OBJECTIVE_NAME);
		}
		objective.setDisplayName(getConfig().getString("display_name"));
		objective.setDisplaySlot(DisplaySlot.BELOW_NAME);

		// Send initial healths
		for (final Player other : getServer().getOnlinePlayers()) {
			for (final String tag : tags.get(other)) {
				objective.getScore(getServer().getOfflinePlayer(tag)).setScore((int) other.getHealth());
			}
		}
	}

	// Makes the health of other players invisible for this player
	public void removeHealth(final Player player) {
		final Objective objective = player.getScoreboard().getObjective(OBJECTIVE_NAME);
		if (objective != null) {
			objective.unregister();
		}
	}

	public void sendHealthOfPlayer(final Player player, final int health) {
		final Set<String> playerTags = tags.get(player);
		Objective objective;
		for (final String tag : playerTags) {
			for (final Player other : getServer().getOnlinePlayers()) {
				objective = other.getScoreboard().getObjective(OBJECTIVE_NAME);
				if (objective != null) {
					objective.getScore(getServer().getOfflinePlayer(tag)).setScore(health);
				}
			}
		}
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		if (cmd.getName().equals("playerhealth")) {
			if (args.length > 0) {
				if (args[0].equals("reload")) {
					// Reload
					if (sender.hasPermission("playerhealth.reload")) {
						reloadConfig();
						for (final Player player : getServer().getOnlinePlayers()) {
							if (player.getScoreboard().getObjective("playerhealth") != null) {
								player.getScoreboard().getObjective("playerhealth").setDisplayName(getConfig().getString("display_name"));
								if (!player.hasPermission("playerhealth.show")) {
									removeHealth(player);
								}
							} else {
								if (player.hasPermission("playerhealth.show")) {
									showHealth(player);
								}
							}
						}
					} else {
						sender.sendMessage(ChatColor.RED + "You don't have permission to do this!");
					}
					return true;
				}
				return false;
			} else {
				sender.sendMessage(ChatColor.YELLOW + "PlayerHealth" + ChatColor.WHITE + " version " + ChatColor.YELLOW + getDescription().getVersion());

				sender.sendMessage(getDescription().getDescription());
				sender.sendMessage("Website: " + ChatColor.YELLOW + getDescription().getWebsite());
				if (sender.hasPermission("playerhealth.reload")) {
					sender.sendMessage("Use " + ChatColor.YELLOW + "/playerhealth reload" + ChatColor.WHITE + " to reload the config and displays");
				}
				return true;
			}
		}
		return false;
	}
}
