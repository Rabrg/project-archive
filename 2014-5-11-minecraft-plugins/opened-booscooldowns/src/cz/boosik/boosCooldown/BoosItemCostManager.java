package cz.boosik.boosCooldown;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import util.boosChat;

/**
 * Tøída obsahuje veškeré metody potøebné k øízení poplatkù za pøíkazy.
 * 
 * @author Jakub Koláø
 * 
 */
public class BoosItemCostManager {
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
	 * @param item 
	 * @param price
	 *            cena použití pøíkazu
	 * @param name
	 *            jméno specifického hráèe
	 * @return true pokud byl úspìšnì zaplacen poplatek, nebo pokud nebyl
	 *         nalezen ekonomický plugin; false pokud došlo k chybì nebo hráè
	 *         nemìl dostatek financí
	 */
	static boolean payItemForCommand(Player player, String regexCommand,
			String originalCommand, String item, int count, String name) {
		Material material = Material.getMaterial(item);
		Inventory inventory = player.getInventory();
		Boolean trans = false;
		if(inventory.contains(material, count)){
			ItemStack itemstack = new ItemStack(material, count);
			inventory.removeItem(itemstack);
			trans = true;
		}
		if (trans) {
			msg = String.format(BoosConfigManager.getPaidItemsForCommandMessage(),
					count+" "+ item);
			msg = msg.replaceAll("&command&", originalCommand);
			boosChat.sendMessageToPlayer(player, msg);
			return true;
		} else {
				msg = String.format(
						BoosConfigManager.getInsufficientItemsMessage(), (count
								+ " " + item));
				msg = msg.replaceAll("&command&", originalCommand);
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
	 * @param item 
	 * @param price
	 *            cena použití pøíkazu
	 */
	static void payItemForCommand(PlayerCommandPreprocessEvent event,
			Player player, String regexCommand, String originalCommand,
			String item, int count) {
		String name = player.getName();
		if (count > 0) {
			if (!player.hasPermission("booscooldowns.noitemcost")
					&& !player.hasPermission("booscooldowns.noitemcost."
							+ originalCommand)) {
				if (payItemForCommand(player, regexCommand, originalCommand, item, count,
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
