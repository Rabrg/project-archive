package cz.boosik.boosCooldown;

import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import util.boosChat;

/**
 * HlavnÌ posluchaË, kter˝ naslouch· ud·losti pouûitÌ p¯Ìkazu hr·Ëem.
 * Kontroluje, jestli jsou pro p¯Ìkaz nastaveny omezenÌ a na z·kladÏ tohoto
 * spouötÌ ËasovaËe a vol· metody spojenÈ s poplatky a limity.
 * 
 * @author Jakub Kol·¯
 * 
 */
public class BoosCoolDownListener implements Listener {
	private static BoosCoolDown plugin;

	/**
	 * @param instance
	 */
	public BoosCoolDownListener(BoosCoolDown instance) {
		plugin = instance;
	}

	/**
	 * Metoda zkontroluje pomocÌ vol·nÌ dalöÌch metod, jestli p¯ikaz kter˝ hr·Ë
	 * pouûil je nÏjak˝m zp˘sobem omezen˝ a na z·kladÏ toho je buÔ ud·lost
	 * pouûitÌ p¯Ìkazu stornov·na, nebo ne.
	 * 
	 * @param event
	 *            ud·lost PlayerCommandPreprocessEvent
	 * @param player
	 *            hr·Ë kter˝ spustil tuto ud·lost
	 * @param regexCommad
	 *            p¯Ìkaz z konfiguraËnÌho souboru, kter˝ vyhovuje origin·lnÌmu
	 *            p¯Ìkazu
	 * @param originalCommand
	 *            origin·lnÌ p¯Ìkaz kter˝ hr·Ë pouûil
	 * @param warmupTime
	 *            warmup doba nastaven· pro regexCommand
	 * @param cooldownTime
	 *            cooldown doba nastaven· pro regexCommand
	 * @param price
	 *            cena nastaven· pro regexCommand
	 * @param limit
	 *            limit nastaven˝ pro regexCommand
	 */
	private void checkRestrictions(PlayerCommandPreprocessEvent event,
			Player player, String regexCommad, String originalCommand,
			int warmupTime, int cooldownTime, double price, String item, int count, int limit) {
		boolean blocked = BoosLimitManager.blocked(player, regexCommad,
				originalCommand, limit);
		if (!blocked) {
			if (warmupTime > 0) {
				if (!player.hasPermission("booscooldowns.nowarmup")
						&& !player.hasPermission("booscooldowns.nowarmup."
								+ originalCommand)) {
					start(event, player, regexCommad, originalCommand,
							warmupTime, cooldownTime);
				}
			} else {
				if (BoosCoolDownManager.coolDown(player, regexCommad,
						originalCommand, cooldownTime)) {
					event.setCancelled(true);
				}
			}
			if (!event.isCancelled()) {
				BoosPriceManager.payForCommand(event, player, regexCommad,
						originalCommand, price);
			}
			if (!event.isCancelled()) {
				BoosItemCostManager.payItemForCommand(event, player, regexCommad,
						originalCommand, item, count);
			}
			if (!event.isCancelled()) {
				String msg = String.format(BoosConfigManager.getMessage(regexCommad, player));
				if (!msg.equals("")) {
					boosChat.sendMessageToPlayer(player, msg);
				}
			}
		} else {
			event.setCancelled(true);
			String msg = String.format(BoosConfigManager
					.getCommandBlockedMessage());
			boosChat.sendMessageToPlayer(player, msg);
		}
		if (!event.isCancelled()) {
			BoosLimitManager.setUses(player, regexCommad, originalCommand);
			if (BoosConfigManager.getCommandLogging()) {
				BoosCoolDown.commandLogger(player.getName(), originalCommand);
			}
		}
	}

	/**
	 * PosluchaË, kter˝ naslouch· ud·losti pouûitÌ p¯Ìkazu a spouötÌ se jeötÏ
	 * p¯ed tÌm, neû je vykon·n efekt tohto p¯Ìkazu. Metoda zjiöùuje, jestli
	 * p¯Ìkaz nenÌ alias jinÈho p¯Ìkazu a takÈ jestli se p¯Ìkaz kter˝ hr·Ë
	 * pouûil shoduje s p¯Ìkazem nastaven˝m v konfiguraci. Pokud se shoduje, pak
	 * jsou naËteny informace o warmup dobÏ, cooldown dobÏ, poplatku a limitu.
	 * Tyto hodnoty jsou potÈ p¯ed·ny metodÏ checkRestrictions();.
	 * 
	 * @param event
	 *            ud·lost PlayerCommandPreprocessEvent
	 */
	@EventHandler(priority = EventPriority.LOWEST)
	private void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Player player = event.getPlayer();
		String originalCommand = event.getMessage().replace("\\", "\\\\");
		originalCommand = originalCommand.trim().replaceAll(" +", " ").toLowerCase();
		String regexCommad = "";
		Set<String> aliases = BoosConfigManager.getAliases();
		Set<String> commands = BoosConfigManager.getCommands(player);
		boolean on = true;
		String item = "";
		int count = 0;
		int warmupTime = 0;
		double price = 0;
		int limit = -1;
		int cooldownTime = 0;
		on = BoosCoolDown.isPluginOnForPlayer(player);
		try {
			if (aliases.contains(originalCommand)) {
				originalCommand = BoosConfigManager.getAlias(originalCommand);
				if (originalCommand.contains("$player")) {
					originalCommand.replaceAll("$player", player.getName());
				}
				if (originalCommand.contains("$world")) {
					originalCommand.replaceAll("$world", player.getWorld()
							.getName());
				}
				event.setMessage(originalCommand);
			}
		} catch (NullPointerException e) {
			BoosCoolDown
					.getLog()
					.warning(
							"Aliases section in config.yml is missing! Please delete your config.yml, restart server and set it again!");
		}
		if (on) {
			for (String group : commands) {
				String group2 = group.replace("*", ".+");
				if (originalCommand.matches(group2)) {
					regexCommad = group;
					if (BoosConfigManager.getWarmupEnabled()) {
						warmupTime = BoosConfigManager.getWarmUp(regexCommad,
								player);
					}
					if (BoosConfigManager.getCooldownEnabled()) {
						cooldownTime = BoosConfigManager.getCoolDown(
								regexCommad, player);
					}
					if (BoosConfigManager.getPriceEnabled()) {
						price = BoosConfigManager.getPrice(regexCommad, player);
					}
					if (BoosConfigManager.getItemCostEnabled()) {
						item = BoosConfigManager.getItemCostItem(regexCommad, player);
						count = BoosConfigManager.getItemCostCount(regexCommad, player);
					}
					if (BoosConfigManager.getLimitEnabled()) {
						limit = BoosConfigManager.getLimit(regexCommad, player);
					}
					break;
				}
			}
			this.checkRestrictions(event, player, regexCommad, originalCommand,
					warmupTime, cooldownTime, price, item, count, limit);
		}
	}

	/**
	 * Metoda spouötÌ warmup a cooldown ËasovaËe, p¯ÌpadnÏ je ukonËuje, pokud
	 * jiû tyto ËasovaËe skonËili.
	 * 
	 * @param event
	 *            ud·lost PlayerCommandPreprocessEvent
	 * @param player
	 *            hr·Ë kter˝ spustil tuto ud·lost
	 * @param regexCommad
	 *            p¯Ìkaz z konfiguraËnÌho souboru, kter˝ vyhovuje origin·lnÌmu
	 *            p¯Ìkazu
	 * @param originalCommand
	 *            origin·lnÌ p¯Ìkaz kter˝ hr·Ë pouûil
	 * @param warmupTime
	 *            warmup doba nastaven· pro regexCommand
	 * @param cooldownTime
	 *            cooldown doba nastaven· pro regexCommand
	 */
	private void start(PlayerCommandPreprocessEvent event, Player player,
			String regexCommad, String originalCommand, int warmupTime,
			int cooldownTime) {
		if (!BoosWarmUpManager.checkWarmUpOK(player, regexCommad)) {
			if (BoosCoolDownManager.checkCoolDownOK(player, regexCommad,
					originalCommand, cooldownTime)) {
				BoosWarmUpManager.startWarmUp(plugin, player, regexCommad,
						originalCommand, warmupTime);
				event.setCancelled(true);
				return;
			} else {
				event.setCancelled(true);
				return;
			}
		} else {
			if (BoosCoolDownManager.coolDown(player, regexCommad,
					originalCommand, cooldownTime)) {
				event.setCancelled(true);
				return;
			} else {
				BoosWarmUpManager.removeWarmUpOK(player, regexCommad);
				return;
			}
		}
	}
}