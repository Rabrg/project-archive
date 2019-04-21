package cz.boosik.boosCooldown.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSprintEvent;

import util.boosChat;
import cz.boosik.boosCooldown.BoosConfigManager;
import cz.boosik.boosCooldown.BoosWarmUpManager;

/**
 * Posluchaè naslouchající události, která se spouští v okamžiku kdy hráè zapne
 * sprintování (pomocí dvojitého stisknutí klávesy pro pohyb vpøed). Pokud na
 * pøíkazech hráèe je aktivní èasovaè warmup, ve chvíli spuštìní této události
 * jsou všechny jeho warmup èasovaèe stornovány a hráèi je odeslána zpráva,
 * která ho o této skuteènosti informuje. Pokud hráè disponuje oprávnìním
 * „booscooldowns.nocancel.sprint“, jeho warmup èasovaèe stornovány nejsou.
 * 
 * @author Jakub Koláø
 * 
 */
public class BoosPlayerToggleSprintListener implements Listener {
	/**
	 * Pokud hráè není null a nedisponuje oprávnìním
	 * booscooldowns.nocancel.sprint a pokud tento hráè disponuje aktivními
	 * warmup èasovaèi, pak je hráèi odeslána zpráva, která ho informuje o
	 * ukonèení všech warmup èasovaèù a následnì tyto èasovaèe ukonèuje pomocí
	 * metody cancelWarmUps();.
	 * 
	 * @param event
	 *            událost PlayerToggleSprintEvent
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerToggleSprint(PlayerToggleSprintEvent event) {
		if (event.isCancelled())
			return;

		Player player = event.getPlayer();
		if (player != null
				&& !player.hasPermission("booscooldowns.nocancel.sprint")) {
			if (BoosWarmUpManager.hasWarmUps(player)) {
				boosChat.sendMessageToPlayer(player,
						BoosConfigManager.getCancelWarmupOnSprintMessage());
				BoosWarmUpManager.cancelWarmUps(player);
			}

		}
	}
}