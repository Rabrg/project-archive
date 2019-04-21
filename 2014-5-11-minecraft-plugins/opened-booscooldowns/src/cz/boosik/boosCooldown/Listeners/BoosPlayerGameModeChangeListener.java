package cz.boosik.boosCooldown.Listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import util.boosChat;
import cz.boosik.boosCooldown.BoosConfigManager;
import cz.boosik.boosCooldown.BoosWarmUpManager;

/**
 * Posluchaè naslouchající události, která se spouští v okamiku kdy hráè zmìní
 * herní mód. Pokud na pøíkazech hráèe je aktivní èasovaè warmup, ve chvíli
 * spuštìní této události jsou všechny jeho warmup èasovaèe stornovány a hráèi
 * je odeslána zpráva, která ho o této skuteènosti informuje. Pokud hráè
 * disponuje oprávnìním „booscooldowns.nocancel.gamemodechange“, jeho warmup
 * èasovaèe stornovány nejsou.
 * 
 * @author Jakub Koláø
 * 
 */
public class BoosPlayerGameModeChangeListener implements Listener {
	/**
	 * Metoda zjišuje jestli je entita která spustila tuto událost hráè. Pokud
	 * je entita hráè, hráè není null a nedisponuje oprávnìním
	 * booscooldowns.nocancel.gamemodechange a pokud tento hráè disponuje aktivními
	 * warmup èasovaèi, pak je hráèi odeslána zpráva, která ho informuje o
	 * ukonèení všech warmup èasovaèù a následnì tyto èasovaèe ukonèuje pomocí
	 * metody cancelWarmUps();.
	 * 
	 * @param event
	 *            událost PlayerGameModeChangeEvent
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
		if (event.isCancelled())
			return;

		Entity entity = event.getPlayer();
		if (entity != null && entity instanceof Player) {
			Player player = (Player) entity;
			if (player != null
					&& !player
							.hasPermission("booscooldowns.nocancel.gamemodechange")) {
				if (BoosWarmUpManager.hasWarmUps(player)) {
					boosChat.sendMessageToPlayer(player, BoosConfigManager
							.getCancelWarmupByGameModeChangeMessage());
					BoosWarmUpManager.cancelWarmUps(player);
				}

			}
		}
	}
}
