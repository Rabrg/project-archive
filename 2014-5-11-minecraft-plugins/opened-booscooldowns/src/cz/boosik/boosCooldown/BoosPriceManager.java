package cz.boosik.boosCooldown;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import util.boosChat;

/**
 * Tøída obsahuje veškeré metody potøebné k øízení poplatkù pomocí vìcí za pøíkazy.
 * 
 * @author Jakub Koláø
 * 
 */
public class BoosPriceManager {
	private static Economy economy = BoosCoolDown.getEconomy();
	private static String msg = "";

	/**
	 * Metoda zajišuje funkci platby za pøíkaz. Vrací hodnotu v závislosti na
	 * úspìšnosti platby.
	 * 
	 * @param player
	 *            specifikovaný hráè
	 * @param regexCommand
	 *            pøíkaz z konfigurace vyhovující originálnímu pøíkazu
	 * @param originalCommand
	 *            originální pøíkaz použitý hráèem
	 * @param price
	 *            cena použití pøíkazu
	 * @param name
	 *            jméno specifického hráèe
	 * @return true pokud byl úspìšnì zaplacen poplatek, nebo pokud nebyl
	 *         nalezen ekonomický plugin; false pokud došlo k chybì nebo hráè
	 *         nemìl dostatek financí
	 */
	static boolean payForCommand(Player player, String regexCommand,
			String originalCommand, double price, String name) {
		if (economy == null) {
			return true;
		}
		EconomyResponse r = economy.withdrawPlayer(name, price);
		if (r.transactionSuccess()) {
			msg = String.format(BoosConfigManager.getPaidForCommandMessage(),
					economy.format(r.amount), economy.format(r.balance));
			msg = msg.replaceAll("&command&", originalCommand);
			boosChat.sendMessageToPlayer(player, msg);
			return true;
		} else {
			if (r.errorMessage.equals("Insufficient funds")) {
				String unit;
				if (price == 1) {
					unit = economy.currencyNameSingular();
				} else {
					unit = economy.currencyNamePlural();
				}
				msg = String.format(
						BoosConfigManager.getInsufficientFundsMessage(), (price
								+ " " + unit), economy.format(r.balance));
				msg = msg.replaceAll("&command&", originalCommand);
			} else {
				msg = String.format(BoosConfigManager.getPaidErrorMessage(),
						r.errorMessage);
			}
			boosChat.sendMessageToPlayer(player, msg);
			return false;
		}
	}

	/**
	 * Metoda ukonèuje/neukonèuje událost použití pøíkazu v závislosti na tom,
	 * jakou hodnotu vrátila metoda payForCommand(Player player, String
	 * regexCommand, String originalCommand, double price, String name);.
	 * 
	 * @param event
	 *            událost PlayerCommandPreprocessEvent
	 * @param player
	 *            specifický hráè
	 * @param regexCommand
	 *            pøíkaz z konfigurace vyhovující originálnímu pøíkazu
	 * @param originalCommand
	 *            originální pøíkaz použitý hráèem
	 * @param price
	 *            cena použití pøíkazu
	 */
	static void payForCommand(PlayerCommandPreprocessEvent event,
			Player player, String regexCommand, String originalCommand,
			double price) {
		String name = player.getName();
		if (price > 0) {
			if (!player.hasPermission("booscooldowns.noprice")
					&& !player.hasPermission("booscooldowns.noprice."
							+ originalCommand)) {
				if (payForCommand(player, regexCommand, originalCommand, price,
						name)) {
					return;
				} else {
					BoosCoolDownManager.cancelCooldown(player, regexCommand);
					event.setCancelled(true);
					return;
				}
			}
		}
	}
}
