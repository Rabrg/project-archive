package cz.boosik.boosCooldown;

import org.bukkit.entity.Player;

import util.boosChat;

/**
 * Tøída obsahuje veškeré metody potøebné k øízení limitù.
 * 
 * @author Jakub Koláø
 * 
 */
public class BoosLimitManager {
	/**
	 * Metoda kontroluje zda je moné pouít pøíkaz, nebo zda je pøíkaz ji
	 * zablokovanı.
	 * 
	 * @param player
	 *            specifická hráè
	 * @param regexCommand
	 *            pøíkaz z konfiguraèního souboru, kterı vyhovuje originálnímu
	 *            pøíkazu
	 * @param originalCommand
	 *            originální pøíkaz pouitı hráèem
	 * @param limit
	 *            limit nastavenı pro regexCommand
	 * @return false pokud pøíkaz je moné pouít, true pokud pøíkaz není moné
	 *         pouít
	 */
	static boolean blocked(Player player, String regexCommand,
			String originalCommand, int limit) {
		int uses = getUses(player, regexCommand);
		if (player.hasPermission("booscooldowns.nolimit")
				|| player.hasPermission("booscooldowns.nolimit."
						+ originalCommand)) {
		} else {
			if (limit == -1) {
				return false;
			} else if (limit <= uses) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Metoda vrací hodnotu, která je urèena tím, kolikrát ji hráè pouil
	 * specifikovanı pøíkaz.
	 * 
	 * @param player
	 *            specifická hráè
	 * @param regexCommand
	 *            pøíkaz z konfiguraèního souboru, kterı vyhovuje originálnímu
	 *            pøíkazu
	 * @return hodnota pøedstavujíci poèet pouití specifikovaného pøíkazu
	 */
	static int getUses(Player player, String regexCommand) {
		int regexCommand2 = regexCommand.toLowerCase().hashCode();
		int uses = 0;
		uses = BoosConfigManager.getConfusers().getInt(
				"users." + player.getName().toLowerCase().hashCode() + ".uses."
						+ regexCommand2, uses);
		return uses;
	}

	/**
	 * Metoda nastavuje poèet pouití pøíkazu o jedna vìtší po kadém pouití
	 * pøíkazu hráèem. Nastevení hodnoty probíhá jen pro pøíkazy, které jsou
	 * definovány v konfiguraci.
	 * 
	 * @param player
	 *            specifická hráè
	 * @param regexCommand
	 *            pøíkaz z konfiguraèního souboru, kterı vyhovuje originálnímu
	 *            pøíkazu
	 * @param originalCommand
	 *            originální pøíkaz pouitı hráèem
	 */
	static void setUses(Player player, String regexCommand,
			String originalCommand) {
		if (BoosConfigManager.getLimitsEnabled()) {
			if (BoosConfigManager.getCommands(player).contains(regexCommand)) {
				int regexCommand2 = regexCommand.toLowerCase().hashCode();
				int uses = getUses(player, regexCommand);
				uses = uses + 1;
				try {
					BoosConfigManager.getConfusers().set(
							"users."
									+ player.getName().toLowerCase().hashCode()
									+ ".uses." + regexCommand2, uses);
				} catch (IllegalArgumentException e) {
					BoosCoolDown
							.getLog()
							.warning(
									"Player "
											+ player.getName()
											+ " used empty command and caused this error!");
				}
			} else {
				return;
			}
		}
	}

	/**
	 * Metoda odesílá hráèi zprávu o limitovaném pøíkazu, hodnotu tohoto limitu
	 * a kolikrát je ještì moné limitovanı pøíkaz pouít.
	 * 
	 * @param send
	 *            hráè kterému bude odeslán seznam
	 * @param comm
	 *            pøíkaz o kterém si hráè vyádal informace
	 * @param lim
	 *            hodnota limitu na pøíkazu
	 */
	static void getLimitListMessages(Player send, String comm, int lim) {
		if (lim != -1) {
			int uses = getUses(send, comm);
			String message = BoosConfigManager.getLimitListMessage();
			int num = lim - uses;
			if (num < 0) {
				num = 0;
			}
			message = BoosConfigManager.getLimitListMessage();
			message = message.replaceAll("&command&", comm);
			message = message.replaceAll("&limit&", String.valueOf(lim));
			message = message.replaceAll("&times&", String.valueOf(num));
			boosChat.sendMessageToPlayer(send, message);
		}
	}

}
