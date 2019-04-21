package cz.boosik.boosCooldown;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;

import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.mcstats.MetricsLite;

import util.boosChat;
import cz.boosik.boosCooldown.Listeners.BoosEntityDamageListener;
import cz.boosik.boosCooldown.Listeners.BoosPlayerDeathListener;
import cz.boosik.boosCooldown.Listeners.BoosPlayerGameModeChangeListener;
import cz.boosik.boosCooldown.Listeners.BoosPlayerInteractListener;
import cz.boosik.boosCooldown.Listeners.BoosPlayerMoveListener;
import cz.boosik.boosCooldown.Listeners.BoosPlayerToggleSneakListener;
import cz.boosik.boosCooldown.Listeners.BoosPlayerToggleSprintListener;
import cz.boosik.boosCooldown.Listeners.BoosSignChangeListener;
import cz.boosik.boosCooldown.Listeners.BoosSignInteractListener;

/**
 * HlavnÌ t¯Ìda pluginu. T¯Ìda je potomkem JavaPlugin a implementuje Runnable.
 * Tato t¯Ìda se star· o veökerÈ inicializace p¯i spuötÏnÌ a ukonËenÌ pluginu.
 * Toto zahrnuje zjiötÏnÌ, zda je k dispozici plugin Vault a ekonomick˝ plugin,
 * registraci posluchaË˘ a takÈ se star· o funkce vöech kontrolnÌch a
 * konfiguraËnÌch p¯Ìkaz˘. Periodicky takÈ ukl·d· soubor datab·ze v intervalu
 * nastavenÈm v konfiguraci.
 * 
 * @author Jakub Kol·¯
 * 
 */
public class BoosCoolDown extends JavaPlugin implements Runnable {
	private static final Logger log = Logger.getLogger("Minecraft");
	private static PluginDescriptionFile pdfFile;
	private static Economy economy = null;
	private static boolean usingVault = false;
	private PluginManager pm;

	/**
	 * Metoda odesÌl· zpr·vy o pouûitÌ p¯Ìkaz˘ do konzole serveru.
	 * 
	 * @param player
	 *            jmÈno hr·Ëe kter˝ vykonal p¯Ìkaz
	 * @param command
	 *            vykonan˝ p¯Ìkaz
	 */
	public static void commandLogger(String player, String command) {
		log.info("[" + "boosLogger" + "] " + player + " used command "
				+ command);
	}

	/**
	 * @return
	 */
	public static Economy getEconomy() {
		return economy;
	}

	/**
	 * @return
	 */
	public static Logger getLog() {
		return log;
	}

	/**
	 * Metoda zjiöùuje na z·kladÏ opr·vnÏnÌ jestli je plugin aktivnÌ pro danÈho
	 * hr·Ëe nebo ne.
	 * 
	 * @param player
	 *            specifikovan˝ hr·Ë
	 * @return
	 */
	static boolean isPluginOnForPlayer(Player player) {
		boolean on;
		if (player.hasPermission("booscooldowns.exception")) {
			on = false;
		} else if (player.isOp()) {
			on = false;
		} else {
			on = true;
		}
		return on;
	}

	/**
	 * Metoda zjiöùuje jestli je dostupn˝ plugin Vault a na z·kladÏ toho vol·
	 * metodu pro nastavenÌ ekonomickÈho pluginu a informuje o tom uûivatele
	 * pomocÌ konzole serveru.
	 */
	private void initializeVault() {
		Plugin x = pm.getPlugin("Vault");
		if (x != null & x instanceof Vault) {
			log.info("[" + pdfFile.getName() + "]"
					+ " found [Vault] searching for economy plugin.");
			usingVault = true;
			if (setupEconomy()) {
				log.info("[" + pdfFile.getName() + "]" + " found ["
						+ economy.getName()
						+ "] plugin, enabling prices support.");
			} else {
				log.info("["
						+ pdfFile.getName()
						+ "]"
						+ " economy plugin not found, disabling prices support.");
			}
		} else {
			log.info("[" + pdfFile.getName() + "]"
					+ " [Vault] not found disabling economy support.");
			usingVault = false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.bukkit.plugin.java.JavaPlugin#onCommand(org.bukkit.command.CommandSender
	 * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command c,
			String commandLabel, String[] args) {
		String command = c.getName().toLowerCase();
		if (command.equalsIgnoreCase("booscooldowns")) {
			if (args.length == 1) {
				if (sender.hasPermission("booscooldowns.reload")
						&& args[0].equalsIgnoreCase("reload")) {
					reload();
					boosChat.sendMessageToCommandSender(sender,
							"&6[" + pdfFile.getName() + "]&e"
									+ " config reloaded");
					return true;
				}
				if (sender.hasPermission("booscooldowns.list.limits")
						&& args[0].equalsIgnoreCase("limits")) {
					try {
						Player send = (Player) sender;
						Set<String> commands = BoosConfigManager
								.getCommands(send);
						for (String comm : commands) {
							int lim = BoosConfigManager.getLimit(comm, send);
							BoosLimitManager.getLimitListMessages(send, comm,
									lim);
						}
					} catch (ClassCastException e) {
						log.warning("You cannot use this command from console!");
					}
					return true;
				}
			}
			if (args.length == 2) {
				String jmeno = args[1];
				if (sender.hasPermission("booscooldowns.clearcooldowns")
						&& args[0].equalsIgnoreCase("clearcooldowns")) {
					String co = "cooldown";
					BoosConfigManager.clearSomething(co, jmeno);
					boosChat.sendMessageToCommandSender(sender,
							"&6[" + pdfFile.getName() + "]&e"
									+ " cooldowns of player " + jmeno
									+ " cleared");
					return true;
				} else if (sender.hasPermission("booscooldowns.clearuses")
						&& command.equalsIgnoreCase("booscooldowns")
						&& args[0].equalsIgnoreCase("clearuses")) {
					String co = "uses";
					BoosConfigManager.clearSomething(co, jmeno);
					boosChat.sendMessageToCommandSender(sender,
							"&6[" + pdfFile.getName() + "]&e"
									+ " uses of player " + jmeno + " cleared");
					return true;
				} else if (sender.hasPermission("booscooldowns.clearwarmups")
						&& command.equalsIgnoreCase("booscooldowns")
						&& args[0].equalsIgnoreCase("clearwarmups")) {
					String co = "warmup";
					BoosConfigManager.clearSomething(co, jmeno);
					boosChat.sendMessageToCommandSender(sender,
							"&6[" + pdfFile.getName() + "]&e"
									+ " warmups of player " + jmeno
									+ " cleared");
					return true;
				}
			}
			if (args.length == 3) {
				String jmeno = args[1];
				String command2 = args[2].trim();
				if (sender.hasPermission("booscooldowns.clearcooldowns")
						&& args[0].equalsIgnoreCase("clearcooldowns")) {
					String co = "cooldown";
					BoosConfigManager.clearSomething(co, jmeno, command2);
					boosChat.sendMessageToCommandSender(sender,
							"&6[" + pdfFile.getName() + "]&e"
									+ " cooldown for command " + command2
									+ " of player " + jmeno + " cleared");
					return true;
				} else if (sender.hasPermission("booscooldowns.clearuses")
						&& args[0].equalsIgnoreCase("clearuses")) {
					String co = "uses";
					BoosConfigManager.clearSomething(co, jmeno, command2);
					boosChat.sendMessageToCommandSender(sender,
							"&6[" + pdfFile.getName() + "]&e"
									+ " uses for command " + command2
									+ " of player " + jmeno + " cleared");
					return true;
				} else if (sender.hasPermission("booscooldowns.clearwarmups")
						&& args[0].equalsIgnoreCase("clearwarmups")) {
					String co = "warmup";
					BoosConfigManager.clearSomething(co, jmeno, command2);
					boosChat.sendMessageToCommandSender(sender,
							"&6[" + pdfFile.getName() + "]&e"
									+ " warmups for command " + command2
									+ " of player " + jmeno + " cleared");
					return true;

				}
			}
			if (args.length == 4) {
				if (sender.hasPermission("booscooldowns.set")
						&& args[0].equalsIgnoreCase("set")) {
					String coSetnout = args[1];
					String co = args[2];
					String hodnota = args[3];
					if (co.startsWith("/") || co.equals("*")) {
						if (co.contains("_")) {
							co = co.replace("_", " ");
						}
						BoosConfigManager.setAddToConfigFile(coSetnout, co,
								hodnota);
						boosChat.sendMessageToCommandSender(sender, "&6["
								+ pdfFile.getName() + "]&e" + " " + co
								+ " in group " + coSetnout + " is now set to "
								+ hodnota);
						return true;
					} else {
						boosChat.sendMessageToCommandSender(sender, "&6["
								+ pdfFile.getName() + "]&e"
								+ " Command has to start with \"/\".");
						return true;
					}
				}

			} else {
				boosChat.sendMessageToCommandSender(
						sender,
						"&6["
								+ pdfFile.getName()
								+ "]&e"
								+ " Invalid command or access denied!");
			}
		}
		return false;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
	 */
	@Override
	public void onDisable() {
		if (BoosConfigManager.getClearOnRestart() == true) {
			BoosConfigManager.clear();
			log.info("[" + pdfFile.getName() + "]" + " cooldowns cleared!");
		} else {
			BoosConfigManager.saveConfusers();
			log.info("[" + pdfFile.getName() + "]" + " cooldowns saved!");
		}
		log.info("[" + pdfFile.getName() + "]" + " version "
				+ pdfFile.getVersion() + " disabled!");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
	 */
	@Override
	public void onEnable() {
		pdfFile = this.getDescription();
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info("[" + pdfFile.getName() + "]" + " version "
				+ pdfFile.getVersion() + " by " + pdfFile.getAuthors()
				+ " is enabled!");
		new BoosConfigManager(this);
		BoosConfigManager.load();
		BoosConfigManager.loadConfusers();
		pm = getServer().getPluginManager();
		registerListeners();
		initializeVault();
		BukkitScheduler scheduler = this.getServer().getScheduler();
		if (BoosConfigManager.getAutoSave()){
		scheduler.scheduleSyncRepeatingTask(this, this,
				BoosConfigManager.getSaveInterval() * 1200,
				BoosConfigManager.getSaveInterval() * 1200);
		}
		if (BoosConfigManager.getClearOnRestart()) {
			BoosConfigManager.clear();
		}
		try {
			MetricsLite metrics = new MetricsLite(this);
			metrics.start();
		} catch (IOException e) {
			// Failed to submit the stats :-(
		}

	}

	/**
	 * Metoda registruje posluchaËe v PluginManageru na z·kladÏ konfigurace
	 * pluginu. RegistrojÌ se vûdy jen nezbytnÌ posluchaËi. PosluchaËi pro
	 * vypnutÈ funkce nejsou registrov·ni.
	 */
	private void registerListeners() {
		HandlerList.unregisterAll(this);
		pm.registerEvents(new BoosCoolDownListener(this), this);
		if (BoosConfigManager.getCancelWarmUpOnDamage()) {
			pm.registerEvents(new BoosEntityDamageListener(), this);
		}
		if (BoosConfigManager.getCleanCooldownsOnDeath()
				|| BoosConfigManager.getCleanUsesOnDeath()
				|| BoosConfigManager.getStartCooldownsOnDeath()) {
			pm.registerEvents(new BoosPlayerDeathListener(), this);
		}
		if (BoosConfigManager.getCancelWarmUpOnGameModeChange()) {
			pm.registerEvents(new BoosPlayerGameModeChangeListener(), this);
		}
		if (BoosConfigManager.getBlockInteractDuringWarmup()) {
			pm.registerEvents(new BoosPlayerInteractListener(), this);
		}
		if (BoosConfigManager.getCancelWarmupOnMove()) {
			pm.registerEvents(new BoosPlayerMoveListener(), this);
		}
		if (BoosConfigManager.getCancelWarmupOnSneak()) {
			pm.registerEvents(new BoosPlayerToggleSneakListener(), this);
		}
		if (BoosConfigManager.getCancelWarmupOnSprint()) {
			pm.registerEvents(new BoosPlayerToggleSprintListener(), this);
		}
		if (BoosConfigManager.getSignCommands()) {
			pm.registerEvents(new BoosSignChangeListener(), this);
			pm.registerEvents(new BoosSignInteractListener(this), this);
		}
	}

	/**
	 * Metoda vol· metodu pro znovunaËtenÌ konfiguraËnÌho souboru a metodu pro
	 * registraci posluchaË˘.
	 */
	private void reload() {
		BoosConfigManager.reload();
		registerListeners();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		BoosConfigManager.saveConfusers();
		BoosConfigManager.loadConfusers();
		log.info("[boosCooldowns] Config saved!");
	}

	/**
	 * Metoda vracÌ hodnotu true, pokud je na serveru dostupn˝ Ekonomick˝ plugin
	 * kompatibilnÌ s pluginem Vault.
	 * 
	 * @return true pokud je dostupn˝ ekonomick˝ plugin kompatibilnÌ s pluginem
	 *         Vault, jinak vracÌ hodnotu false
	 */
	private boolean setupEconomy() {
		if (usingVault) {
			RegisteredServiceProvider<Economy> economyProvider = getServer()
					.getServicesManager().getRegistration(
							net.milkbowl.vault.economy.Economy.class);
			if (economyProvider != null) {
				economy = economyProvider.getProvider();
			}
			return (economy != null);
		}
		return false;
	}
}
