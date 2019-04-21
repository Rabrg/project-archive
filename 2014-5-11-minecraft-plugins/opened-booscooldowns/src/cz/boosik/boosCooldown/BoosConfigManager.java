package cz.boosik.boosCooldown;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 * T¯Ìda zajiöùujÌcÌ veökerÈ metody, kterÈ se starajÌ o konfiguraci pluginu a o
 * datab·zi.
 * 
 * @author Jakub Kol·¯
 * 
 */
public class BoosConfigManager {

	private static YamlConfiguration conf;
	private static YamlConfiguration confusers;
	private static File confFile;
	private static File confusersFile;

	/**
	 * Metoda zajiöùujÌcÌ smaz·nÌ veöker˝ch aktivnÌch cooldown a warmup ËasovaË˘
	 * vöech hr·Ë˘.
	 */
	static void clear() {
		ConfigurationSection userSection = confusers
				.getConfigurationSection("users");
		if (userSection == null)
			return;
		for (String user : userSection.getKeys(false)) {
			// clear cooldown
			ConfigurationSection cooldown = confusers
					.getConfigurationSection("users." + user + ".cooldown");
			if (cooldown != null) {
				for (String key : cooldown.getKeys(false)) {
					confusers.set("users." + user + ".cooldown." + key, null);
				}
			}
			confusers.set("users." + user + ".cooldown", null);

			// clear warmup
			ConfigurationSection warmup = confusers
					.getConfigurationSection("users." + user + ".warmup");
			if (warmup != null) {
				for (String key : warmup.getKeys(false)) {
					confusers.set("users." + user + ".warmup." + key, null);
				}
			}
			confusers.set("users." + user + ".warmup", null);

			confusers.set("users." + user, null);
		}
		saveConfusers();
		loadConfusers();
	}

	/**
	 * Metoda vymaûe veökerÈ hodnoty specifickÈho hr·Ëe ve specifickÈ sekci
	 * datab·ze.
	 * 
	 * @param co
	 *            sekce datab·ze (warmup, cooldown, uses)
	 * @param player
	 *            jmÈno hr·Ëe pro kretÈho se m· vymazat Ë·st datab·ze
	 */
	public static void clearSomething(String co, String player) {
		ConfigurationSection userSection = confusers
				.getConfigurationSection("users."
						+ player.toLowerCase().hashCode() + "." + co);
		if (userSection == null)
			return;
		confusers.set("users." + player.toLowerCase().hashCode() + "." + co,
				null);
		saveConfusers();
		loadConfusers();
	}

	/**
	 * Metoda vymaûe hodnoty specifickÈho p¯Ìkazu, specifickÈho hr·Ëe ve
	 * specifickÈ sekci datab·ze.
	 * 
	 * @param co
	 *            sekce datab·ze (warmup, cooldown, uses)
	 * @param player
	 *            jmÈno hr·Ëe pro kretÈho se m· vymazat Ë·st datab·ze
	 * @param command
	 *            p¯Ìkaz pro kter˝ se majÌ vymazat hodnoty
	 */
	static void clearSomething(String co, String player, String command) {
		int pre2 = command.toLowerCase().hashCode();
		confusers.set("users." + player.toLowerCase().hashCode() + "." + co
				+ "." + pre2, 0);
		saveConfusers();
		loadConfusers();
	}

	/**
	 * @param message
	 * @return
	 */
	static String getAlias(String message) {
		return conf.getString("commands.aliases." + message);
	}

	/**
	 * @return
	 */
	static Set<String> getAliases() {
		Set<String> aliases = conf.getConfigurationSection("commands.aliases")
				.getKeys(false);
		return aliases;
	}

	/**
	 * @return
	 */
	static boolean getBlockInteractDuringWarmup() {
		return conf.getBoolean("options.options.block_interact_during_warmup",
				false);
	}

	/**
	 * @return
	 */
	public static String getCancelWarmupByGameModeChangeMessage() {
		return conf.getString(
				"options.messages.warmup_cancelled_by_gamemode_change",
				"&6Warm-ups have been cancelled due to changing gamemode.&f");
	}

	/**
	 * @return
	 */
	static boolean getCancelWarmUpOnDamage() {
		return conf
				.getBoolean("options.options.cancel_warmup_on_damage", false);
	}

	/**
	 * @return
	 */
	static boolean getCancelWarmUpOnGameModeChange() {
		return conf.getBoolean(
				"options.options.cancel_warmup_on_gamemode_change", false);
	}

	/**
	 * @return
	 */
	static boolean getCancelWarmupOnMove() {
		return conf.getBoolean("options.options.cancel_warmup_on_move", false);
	}

	/**
	 * @return
	 */
	static boolean getCancelWarmupOnSneak() {
		return conf.getBoolean("options.options.cancel_warmup_on_sneak", false);
	}

	/**
	 * @return
	 */
	public static String getCancelWarmupOnSneakMessage() {
		return conf.getString("options.messages.warmup_cancelled_by_sneak",
				"&6Warm-ups have been cancelled due to sneaking.&f");
	}

	/**
	 * @return
	 */
	static boolean getCancelWarmupOnSprint() {
		return conf
				.getBoolean("options.options.cancel_warmup_on_sprint", false);
	}

	/**
	 * @return
	 */
	public static String getCancelWarmupOnSprintMessage() {
		return conf.getString("options.messages.warmup_cancelled_by_sprint",
				"&6Warm-ups have been cancelled due to sprinting.&f");
	}

	/**
	 * @return
	 */
	public static String getCannotCreateSignMessage() {
		return conf.getString("options.messages.cannot_create_sign",
				"&6You are not allowed to create this kind of signs!&f");
	}

	/**
	 * @return
	 */
	public static String getCannotUseSignMessage() {
		return conf.getString("options.messages.cannot_use_sign",
				"&6You are not allowed to use this sign!&f");
	}

	/**
	 * @return
	 */
	public static boolean getCleanCooldownsOnDeath() {
		return conf.getBoolean("options.options.clear_cooldowns_on_death",
				false);
	}

	/**
	 * @return
	 */
	public static boolean getCleanUsesOnDeath() {
		return conf.getBoolean("options.options.clear_uses_on_death", false);
	}

	/**
	 * @return
	 */
	static boolean getClearOnRestart() {
		return conf.getBoolean("options.options.clear_on_restart", false);
	}

	/**
	 * @return
	 */
	static String getCommandBlockedMessage() {
		return conf.getString("options.messages.limit_achieved",
				"&6You cannot use this command anymore!&f");
	}

	/**
	 * @param player
	 * @return
	 */
	static String getCommandGroup(Player player) {
		String cmdGroup = "default";
		for (String group : getCommandGroups()) {
			if (player.hasPermission("booscooldowns." + group)) {
				cmdGroup = group;
			}
		}
		return cmdGroup;
	}

	/**
	 * @return
	 */
	static Set<String> getCommandGroups() {
		Set<String> groups = conf.getConfigurationSection("commands.groups")
				.getKeys(false);
		return groups;
	}

	/**
	 * @return
	 */
	static boolean getCommandLogging() {
		return conf.getBoolean("options.options.command_logging", false);
	}

	/**
	 * @param player
	 * @return
	 */
	static Set<String> getCommands(Player player) {
		String group = getCommandGroup(player);
		Set<String> commands = conf.getConfigurationSection(
				"commands.groups." + group).getKeys(false);
		return commands;
	}

	/**
	 * @param regexCommand
	 * @param player
	 * @return
	 */
//	static String[] getCommandValues(String regexCommand, Player player) {
//		String[] values;
//		String line = "";
//		String group = getCommandGroup(player);
//		line = conf.getString("commands.groups." + group + "." + regexCommand,
//				line);
//		values = line.split(",");
//		return values;
//	}

	/**
	 * @return
	 */
	static YamlConfiguration getConfusers() {
		return confusers;
	}

	/**
	 * @param regexCommand
	 * @param player
	 * @return
	 */
	static int getCoolDown(String regexCommand, Player player) {
		int coolDown;
		String group = getCommandGroup(player);
		coolDown = conf.getInt("commands.groups." + group + "." + regexCommand + ".cooldown", 0);
		return coolDown;
	}

	/**
	 * @return
	 */
	static boolean getCooldownEnabled() {
		return conf.getBoolean("options.options.cooldowns_enabled", true);
	}

	/**
	 * @return
	 */
	static String getCoolDownMessage() {
		return conf
				.getString(
						"options.messages.cooling_down",
						"&6Wait&e &seconds& seconds&6 before you can use command&e &command& &6again.&f");
	}

	/**
	 * @param player
	 * @return
	 */
	static Set<String> getCooldowns(Player player) {
		String cool = getCommandGroup(player);
		Set<String> cooldowns = conf.getConfigurationSection(
				"commands.groups." + cool).getKeys(false);
		return cooldowns;
	}

	/**
	 * @return
	 */
	static String getInsufficientFundsMessage() {
		return conf
				.getString(
						"options.messages.insufficient_funds",
						"&6You have insufficient funds!&e &command& &6costs &e%s &6but you only have &e%s");
	}

	/**
	 * @return
	 */
	public static String getInteractBlockedMessage() {
		return conf.getString(
				"options.messages.interact_blocked_during_warmup",
				"&6You can't do this when command is warming-up!&f");
	}
	
	/**
	 * @param regexCommand
	 * @param player
	 * @return
	 */
	static String getItemCostItem(String regexCommand, Player player) {
		String item = "";
		String temp;
		String[] command;
		String group = getCommandGroup(player);
		temp = conf.getString("commands.groups." + group + "." + regexCommand + ".itemcost", "");
		command = temp.split(",");
		if(command.length==2){
			item = command[0];
		}
		return item;
	}

	/**
	 * @param regexCommand
	 * @param player
	 * @return
	 */
	static int getItemCostCount(String regexCommand, Player player) {
		int count = 0;
		String temp;
		String[] command;
		String group = getCommandGroup(player);
		temp = conf.getString("commands.groups." + group + "." + regexCommand + ".itemcost", "");
		command = temp.split(",");
		if(command.length==2){
			count = Integer.valueOf(command[1]);
		}
		return count;
	}

	/**
	 * @param regexCommand
	 * @param player
	 * @return
	 */
	static int getLimit(String regexCommand, Player player) {
		int limit;
		String group = getCommandGroup(player);
		limit = conf.getInt("commands.groups." + group + "." + regexCommand + ".limit", -1);
		return limit;
	}

	/**
	 * @return
	 */
	static boolean getLimitEnabled() {
		return conf.getBoolean("options.options.limits_enabled", true);
	}

	/**
	 * @return
	 */
	static String getLimitListMessage() {
		return conf
				.getString(
						"options.messages.limit_list",
						"&6Limit for command &e&command&&6 is &e&limit&&6. You can still use it &e&times&&6 times.&f");
	}

	/**
	 * @return
	 */
	static boolean getLimitsEnabled() {
		return conf.getBoolean("options.options.limits_enabled", true);
	}

	/**
	 * @param pre
	 * @return
	 */
	static String getLink(String pre) {
		String link = null;
		pre = pre.toLowerCase();
		link = conf.getString("commands.links.link." + pre, link);
		return link;
	}

	/**
	 * @param link
	 * @return
	 */
	static List<String> getLinkList(String link) {
		List<String> linkGroup;
		link = link.toLowerCase();
		linkGroup = conf.getStringList("commands.links.linkGroups." + link);
		return linkGroup;
	}
	
	/**
	 * @param regexCommand
	 * @param player
	 * @return
	 */
	static String getMessage(String regexCommand, Player player) {
		String message = "";
		String group = getCommandGroup(player);
		message = conf.getString("commands.groups." + group + "." + regexCommand + ".message", "");
		return message;
	}

	/**
	 * @return
	 */
	static String getPaidErrorMessage() {
		return conf.getString("options.messages.paid_error",
				"An error has occured: %s");
	}

	/**
	 * @return
	 */
	static String getPaidForCommandMessage() {
		return conf.getString("options.messages.paid_for_command",
				"Price of &command& was %s and you now have %s");
	}

	/**
	 * @param regexCommand
	 * @param player
	 * @return
	 */
	static String getPotionEffect(String regexCommand, Player player) {
		String effect = "";
		String temp;
		String[] command;
		String group = getCommandGroup(player);
		temp = conf.getString("commands.groups." + group + "." + regexCommand + ".limit", "");
		command = temp.split(",");
		if(command.length==2){
			effect = command[0];
		}
		return effect;
	}

	/**
	 * @param regexCommand
	 * @param player
	 * @return
	 */
	static int getPotionEffectStrength(String regexCommand, Player player) {
		int effect = 0;
		String temp;
		String[] command;
		String group = getCommandGroup(player);
		temp = conf.getString("commands.groups." + group + "." + regexCommand + ".limit", "");
		command = temp.split(",");
		if(command.length==2){
			effect = Integer.valueOf(command[1]);
		}
		return effect;
	}

	/**
	 * @param regexCommand
	 * @param player
	 * @return
	 */
	static double getPrice(String regexCommand, Player player) {
		double price;
		String group = getCommandGroup(player);
		price = conf.getDouble("commands.groups." + group + "." + regexCommand + ".price", 0.0);
		return price;
	}

	/**
	 * @return
	 */
	static boolean getPriceEnabled() {
		return conf.getBoolean("options.options.prices_enabled", true);
	}

	/**
	 * @return
	 */
	static int getSaveInterval() {
		return conf.getInt("options.options.save_interval_in_minutes", 15);
	}

	/**
	 * @return
	 */
	static boolean getSignCommands() {
		return conf.getBoolean("options.options.command_signs", false);
	}

	/**
	 * @return
	 */
	public static boolean getStartCooldownsOnDeath() {
		return conf.getBoolean("options.options.start_cooldowns_on_death",
				false);
	}

	/**
	 * @return
	 */
	static String getUnitHoursMessage() {
		return conf.getString("options.units.hours", "hours");
	}

	/**
	 * @return
	 */
	static String getUnitMinutesMessage() {
		return conf.getString("options.units.minutes", "minutes");
	}

	/**
	 * @return
	 */
	static String getUnitSecondsMessage() {
		return conf.getString("options.units.seconds", "seconds");
	}

	/**
	 * @param regexCommand
	 * @param player
	 * @return
	 */
	static int getWarmUp(String regexCommand, Player player) {
		int warmUp;
		String group = getCommandGroup(player);
		warmUp = conf.getInt("commands.groups." + group + "." + regexCommand + ".warmup", 0);
		return warmUp;
	}

	/**
	 * @return
	 */
	static String getWarmUpAlreadyStartedMessage() {
		return conf.getString("options.messages.warmup_already_started",
				"&6Warm-Up process for&e &command& &6has already started.&f");
	}

	/**
	 * @return
	 */
	public static String getWarmUpCancelledByDamageMessage() {
		return conf.getString("options.messages.warmup_cancelled_by_damage",
				"&6Warm-ups have been cancelled due to receiving damage.&f");
	}

	/**
	 * @return
	 */
	public static String getWarmUpCancelledByMoveMessage() {
		return conf.getString("options.messages.warmup_cancelled_by_move",
				"&6Warm-ups have been cancelled due to moving.&f");
	}

	/**
	 * @return
	 */
	static boolean getWarmupEnabled() {
		return conf.getBoolean("options.options.warmups_enabled", true);
	}

	/**
	 * @return
	 */
	static String getWarmUpMessage() {
		return conf
				.getString("options.messages.warming_up",
						"&6Wait&e &seconds& seconds&6 before command&e &command& &6has warmed up.&f");
	}

	/**
	 * Metoda naËte konfiguraËnÌ soubor z disku do pamÏti.
	 */
	static void load() {
		try {
			conf.load(confFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			BoosCoolDown.getLog().severe("[boosCooldowns] Configuration file not found!");
		} catch (IOException e) {
			e.printStackTrace();
			BoosCoolDown.getLog().severe("[boosCooldowns] Could not read configuration file!");
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
			BoosCoolDown.getLog().severe("[boosCooldowns] Configuration file is invalid!");
		}
	}

	/**
	 * Metoda naËte soubor datab·ze z disku do pamÏti.
	 */
	static void loadConfusers() {
		try {
			confusers.load(confusersFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			BoosCoolDown.getLog().severe("[boosCooldowns] Storage file not found!");
		} catch (IOException e) {
			e.printStackTrace();
			BoosCoolDown.getLog().severe("[boosCooldowns] Could not read storage file!");
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
			BoosCoolDown.getLog().severe("[boosCooldowns] Storage file is invalid!");
		}
	}

	/**
	 * Metoda znovu naËte konfiguraËnÌ soubor z disku do pamÏti.
	 */
	static void reload() {
		conf = new YamlConfiguration();
		load();
	}

	/**
	 * Metoda uloûÌ soubor datab·ze z pamÏti na disk.
	 */
	static void saveConfusers() {
		try {
			confFile.createNewFile();
			confusers.save(confusersFile);
		} catch (IOException e) {
			e.printStackTrace();
			BoosCoolDown.getLog().severe("[boosCooldowns] Could not save storage file!");	
		}
	}

	/**
	 * Metoda p¯id·v· z·znamy do konfiguraËnÌho souboru, kter˝ potÈ uloûÌ na
	 * disk a znovu jej naËte z disku do pamÏti.
	 * 
	 * @param coSetnout
	 *            n·zev skupiny pro kterou se m· p¯idat z·znam do konfiguraËnÌho
	 *            souboru
	 * @param co
	 *            p¯Ìkaz, pro kter˝ se m· p¯idat hodnota do konfiguraËnÌho
	 *            souboru
	 * @param hodnota
	 *            hodnota kter· se m· p¯idat pro specifikovan˝ p¯Ìkaz
	 */
	static void setAddToConfigFile(String coSetnout, String co, String hodnota) {
		co = co.toLowerCase();
		coSetnout = coSetnout.toLowerCase();
		reload();
		conf.set("commands.groups." + coSetnout + "." + co, hodnota);
		try {
			conf.save(confFile);
		} catch (IOException e) {
			e.printStackTrace();
			BoosCoolDown.getLog().severe("[boosCooldowns] Could not save configuration file!");
			
		}
		reload();
	}

	/**
	 * Metoda vytv·¯ejÌci konfiguraËnÌ a datab·zov˝ sobour, pokud tyto soubory
	 * jiû neexistujÌ. Pokud soubory jiû existujÌ, jsou naËteny z disku do
	 * pamÏti.
	 * 
	 * @param boosCoolDown
	 */
	@SuppressWarnings("static-access")
	BoosConfigManager(BoosCoolDown boosCoolDown) {
		confFile = new File(boosCoolDown.getDataFolder(), "config.yml");
		if (confFile.exists()) {
			conf = new YamlConfiguration();
			load();
		} else {
			this.confFile = new File(boosCoolDown.getDataFolder(), "config.yml");
			this.conf = new YamlConfiguration();
			conf.options().copyDefaults(true);
			conf.addDefault("options.options.warmups_enabled", true);
			conf.addDefault("options.options.cooldowns_enabled", true);
			conf.addDefault("options.options.prices_enabled", true);
			conf.addDefault("options.options.item_cost_enabled", true);
			conf.addDefault("options.options.limits_enabled", true);
			conf.addDefault("options.options.auto_save_enabled_CAN_CAUSE_BIG_LAGS", false);
			conf.addDefault("options.options.save_interval_in_minutes", 15);
			conf.addDefault("options.options.cancel_warmup_on_damage", false);
			conf.addDefault("options.options.cancel_warmup_on_move", false);
			conf.addDefault("options.options.cancel_warmup_on_sneak", false);
			conf.addDefault("options.options.cancel_warmup_on_sprint", false);
			conf.addDefault("options.options.cancel_warmup_on_gamemode_change",
					false);
			conf.addDefault("options.options.block_interact_during_warmup",
					false);
			conf.addDefault("options.options.clear_on_restart", false);
			conf.addDefault("options.options.clear_uses_on_death", false);
			conf.addDefault("options.options.clear_cooldowns_on_death", false);
			conf.addDefault("options.options.start_cooldowns_on_death", false);
			conf.addDefault("options.options.command_logging", false);
			conf.addDefault("options.options.command_signs", false);
			conf.addDefault("options.units.seconds", "seconds");
			conf.addDefault("options.units.minutes", "minutes");
			conf.addDefault("options.units.hours", "hours");
			conf.addDefault("options.messages.warmup_cancelled_by_damage",
					"&6Warm-ups have been cancelled due to receiving damage.&f");
			conf.addDefault("options.messages.warmup_cancelled_by_move",
					"&6Warm-ups have been cancelled due to moving.&f");
			conf.addDefault("options.messages.warmup_cancelled_by_sprint",
					"&6Warm-ups have been cancelled due to sprinting.&f");
			conf.addDefault("options.messages.warmup_cancelled_by_sneak",
					"&6Warm-ups have been cancelled due to sneaking.&f");
			conf.addDefault(
					"options.messages.warmup_cancelled_by_gamemode_change",
					"&6Warm-ups have been cancelled due to changing gamemode.&f");
			conf.addDefault("options.messages.cooling_down",
					"&6Wait&e &seconds& &unit&&6 before you can use command&e &command& &6again.&f");
			conf.addDefault("options.messages.warming_up",
					"&6Wait&e &seconds& &unit&&6 before command&e &command& &6has warmed up.&f");
			conf.addDefault("options.messages.warmup_already_started",
					"&6Warm-Up process for&e &command& &6has already started.&f");
			conf.addDefault("options.messages.paid_error",
					"&6An error has occured:&e %s");
			conf.addDefault(
					"options.messages.insufficient_funds",
					"&6You have insufficient funds!&e &command& &6costs &e%s &6but you only have &e%s");
			conf.addDefault("options.messages.paid_for_command",
					"&6Price of&e &command& &6was&e %s &6and you now have&e %s");
			conf.addDefault("options.messages.paid_items_for_command",
					"&6Price of&e &command& &6was &e%s");
			conf
			.addDefault(
					"options.messages.insufficient_items",
					"&6You have not enough items!&e &command& &6needs &e%s");
			conf.addDefault("options.messages.limit_achieved",
					"&6You cannot use this command anymore!&f");
			conf.addDefault(
					"options.messages.limit_list",
					"&6Limit for command &e&command&&6 is &e&limit&&6. You can still use it &e&times&&6 times.&f");
			conf.addDefault("options.messages.interact_blocked_during_warmup",
					"&6You can't do this when command is warming-up!&f");
			conf.addDefault("options.messages.cannot_create_sign",
					"&6You are not allowed to create this kind of signs!&f");
			conf.addDefault("options.messages.cannot_use_sign",
					"&6You are not allowed to use this sign!&f");
		}
		if (confFile.exists()) {
			load();
		}
		try {
			conf.addDefault("commands.groups.default./command parameter.cooldown",
					2);
			conf.addDefault("commands.groups.default./commandwithparameters *.cooldown",
					5);
			conf.addDefault("commands.groups.default./anothercommand.cooldown",
					2);
			conf.addDefault("commands.groups.default./yetanothercommand.warmup",
					5);
			conf.addDefault("commands.groups.default./yetanothercommand.price",
					10.0);
			conf.addDefault("commands.groups.default./yetanothercommand.limit",
					5);
			conf.addDefault("commands.groups.default./yetanothercommand.potion",
					"WEAKNESS,3");
			conf.addDefault("commands.groups.default./test.message", "You just used /test!");
			conf.addDefault("commands.groups.default./test.itemcost", "STONE,10");
			conf.addDefault("commands.groups.default.*.warmup", 1);
			conf.addDefault("commands.groups.default.*.cooldown", 1);
			conf.addDefault("commands.groups.default.*.price", 0.0);
			conf.addDefault("commands.groups.default.*.limit", -1);
			conf.addDefault("commands.groups.vip./command *.warmup", 5);
			conf.addDefault("commands.links.link./lol", "default");
			conf.addDefault("commands.links.link./example", "default");
			conf.addDefault("commands.links.link./command", "default");
			conf.addDefault("commands.links.link./yourCommandHere",
					"yourNameHere");
			String[] def = { "/lol", "/example" };
			conf.addDefault("commands.links.linkGroups.default",
					Arrays.asList(def));
			String[] def2 = { "/yourCommandHere", "/someCommand",
					"/otherCommand" };
			conf.addDefault("commands.links.linkGroups.yourNameHere",
					Arrays.asList(def2));
			conf.addDefault("commands.aliases./newcommand", "/originalcommand");
			conf.addDefault("commands.aliases./new spawn command",
					"/original spawn command");
			conf.save(confFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		confusersFile = new File(boosCoolDown.getDataFolder(), "users.yml");
		confusers = new YamlConfiguration();
		if (confusersFile.exists()) {
			loadConfusers();
		} else {
			try {
				confusersFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				BoosCoolDown.getLog().severe("[boosCooldowns] Could not save storage file!");
			}
		}
	}

	static boolean getAutoSave() {
		return conf.getBoolean("options.options.auto_save_enabled_CAN_CAUSE_BIG_LAGS", false);
	}

	public static String getPaidItemsForCommandMessage() {
		return conf.getString("options.messages.paid_items_for_command",
				"&6Price of&e &command& &6was &e%s");
	}

	public static String getInsufficientItemsMessage() {
		return conf
				.getString(
						"options.messages.insufficient_items",
						"&6You have not enough items!&e &command& &6needs &e%s");
	}

	public static boolean getItemCostEnabled() {
		return conf.getBoolean("options.options.item_cost_enabled", true);
	}
}
