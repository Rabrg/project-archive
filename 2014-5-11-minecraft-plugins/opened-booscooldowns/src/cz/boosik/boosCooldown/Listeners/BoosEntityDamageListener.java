package cz.boosik.boosCooldown.Listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import util.boosChat;
import cz.boosik.boosCooldown.BoosConfigManager;
import cz.boosik.boosCooldown.BoosWarmUpManager;

/**
 * Posluchaè naslouchající události, která se spouští v okamiku kdy dojde ke
 * zranìní jakékoli entity ve svìtì. Pokud je touto entitou hráè a na jeho
 * pøíkazech je aktivní èasovaè warmup, ve chvíli spuštìní této události jsou
 * všechny jeho warmup èasovaèe stornovány a hráèi je odeslána zpráva, která ho
 * o této skuteènosti informuje. Pokud hráè disponuje oprávnìním
 * „booscooldowns.nocancel.damage“, jeho warmup èasovaèe stornovány nejsou.
 * 
 * @author Jakub Koláø
 * 
 */
public class BoosEntityDamageListener implements Listener {
	/**
	 * Metoda zjišuje jestli je entita která spustila tuto událost hráè. Pokud
	 * je entita hráè, hráè není null a nedisponuje oprávnìním
	 * booscooldowns.nocancel.damage a pokud tento hráè disponuje aktivními
	 * warmup èasovaèi, pak je hráèi odeslána zpráva, která ho informuje o
	 * ukonèení všech warmup èasovaèù a následnì tyto èasovaèe ukonèuje pomocí
	 * metody cancelWarmUps();.
	 * 
	 * @param event
	 *            událost EntityDamageEvent
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	private void onEntityDamage(EntityDamageEvent event) {
		if (event.isCancelled())
			return;

		Entity entity = event.getEntity();
		if (entity != null && entity instanceof Player) {
			Player player = (Player) entity;
			if (player != null
					&& !player.hasPermission("booscooldowns.nocancel.damage")) {
				if (BoosWarmUpManager.hasWarmUps(player)) {
					boosChat.sendMessageToPlayer(player, BoosConfigManager
							.getWarmUpCancelledByDamageMessage());
					BoosWarmUpManager.cancelWarmUps(player);
				}

			}
		}
	}
}
