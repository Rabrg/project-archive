package cz.boosik.boosCooldown.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import util.boosChat;
import cz.boosik.boosCooldown.BoosConfigManager;
import cz.boosik.boosCooldown.BoosWarmUpManager;

/**
 * Posluchaè naslouchající události, která se spouští v okamžiku kdy hráè zapne
 * plížení (defaultnì pomocí klávesy control). Pokud na pøíkazech hráèe je
 * aktivní èasovaè warmup, ve chvíli spuštìní této události jsou všechny jeho
 * warmup èasovaèe stornovány a hráèi je odeslána zpráva, která ho o této
 * skuteènosti informuje. Pokud hráè disponuje oprávnìním
 * „booscooldowns.nocancel.sneak“, jeho warmup èasovaèe stornovány nejsou.
 * 
 * @author Jakub Koláø
 * 
 */
public class BoosPlayerToggleSneakListener implements Listener {
	/**
	 * Pokud hráè není null a nedisponuje oprávnìním
	 * booscooldowns.nocancel.sneak a pokud tento hráè disponuje aktivními
	 * warmup èasovaèi, pak je hráèi odeslána zpráva, která ho informuje o
	 * ukonèení všech warmup èasovaèù a následnì tyto èasovaèe ukonèuje pomocí
	 * metody cancelWarmUps();.
	 * 
	 * @param event
	 *            událost PlayerToggleSneakEvent
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
		if (event.isCancelled())
			return;

		Player player = event.getPlayer();
		if (player != null
				&& !player.hasPermission("booscooldowns.nocancel.sneak")) {
			if (BoosWarmUpManager.hasWarmUps(player)) {
				boosChat.sendMessageToPlayer(player,
						BoosConfigManager.getCancelWarmupOnSneakMessage());
				BoosWarmUpManager.cancelWarmUps(player);
			}

		}
	}
}