package cz.boosik.boosCooldown.Listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import util.boosChat;
import cz.boosik.boosCooldown.BoosConfigManager;
import cz.boosik.boosCooldown.BoosWarmUpManager;

/**
 * Posluchaè naslouchající události, která se spouští v okamiku kdy hráè
 * provádí interakce s herními bloky. Pokud na pøíkazech hráèe je aktivní
 * èasovaè warmup a blok se kterım hráè interaguje je kontejner, pak tato tøída
 * poté ukonèuje tuto událost a blokuje hráèi pøístup do kontejnerù. Pokud hráè
 * disponuje oprávnìním „booscooldowns.dontblock.interact“, jeho pøístup do
 * kontejnerù není blokován.
 * 
 * @author Jakub Koláø
 * 
 */
public class BoosPlayerInteractListener implements Listener {
	/**
	 * Metoda zjišuje jestli je entita která spustila tuto událost hráè. Pokud
	 * je entita hráè, hráè není null a nedisponuje oprávnìním
	 * booscooldowns.dontblock.interact a pokud tento hráè disponuje aktivními
	 * warmup èasovaèi a blok se kterım interaguje je kontejner, pak je hráèi
	 * odeslána zpráva, která ho informuje o tom, e do kontejneru není moné
	 * pøistupovat a tato událost je následnì ukonèena. To zablokuje hráèi
	 * pøístup do kontejneru.
	 * 
	 * @param event
	 *            událost PlayerInteractEvent
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerInteract(PlayerInteractEvent event) {
		if (event.isCancelled())
			return;

		Entity entity = event.getPlayer();
		if (entity != null && entity instanceof Player) {
			Player player = (Player) entity;
			if (player != null
					&& !player
							.hasPermission("booscooldowns.dontblock.interact")) {
				if (BoosWarmUpManager.hasWarmUps(player)) {
					if (event.getClickedBlock().getType().name()
							.equals("CHEST")
							|| event.getClickedBlock().getType().name()
									.equals("FURNACE")
							|| event.getClickedBlock().getType().name()
									.equals("BURNING_FURNACE")
							|| event.getClickedBlock().getType().name()
									.equals("WORKBENCH")
							|| event.getClickedBlock().getType().name()
									.equals("DISPENSER")
							|| event.getClickedBlock().getType().name()
									.equals("JUKEBOX")
							|| event.getClickedBlock().getType().name()
									.equals("LOCKED_CHEST")
							|| event.getClickedBlock().getType().name()
									.equals("ENCHANTMENT_TABLE")
							|| event.getClickedBlock().getType().name()
									.equals("BREWING_STAND")
							|| event.getClickedBlock().getType().name()
									.equals("CAULDRON")
							|| event.getClickedBlock().getType().name()
									.equals("STORAGE_MINECART")
							|| event.getClickedBlock().getType().name()
									.equals("TRAPPED_CHEST")
							|| event.getClickedBlock().getType().name()
									.equals("DROPPER")
							|| event.getClickedBlock().getType().name()
									.equals("HOPPER")) {
						event.setCancelled(true);
						boosChat.sendMessageToPlayer(player,
								BoosConfigManager.getInteractBlockedMessage());
					}
				}

			}
		}
	}
}
