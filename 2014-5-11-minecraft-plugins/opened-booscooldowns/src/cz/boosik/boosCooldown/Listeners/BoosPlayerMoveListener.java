package cz.boosik.boosCooldown.Listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import util.boosChat;
import cz.boosik.boosCooldown.BoosConfigManager;
import cz.boosik.boosCooldown.BoosWarmUpManager;

/**
 * Posluchaè naslouchající události, která se spouští v okamiku kdy se hráè
 * pohybuje. Pokud na pøíkazech hráèe je aktivní èasovaè warmup, ve chvíli
 * spuštìní této události jsou všechny jeho warmup èasovaèe stornovány a hráèi
 * je odeslána zpráva, která ho o této skuteènosti informuje. Pøi pohybech hráèe
 * hra tuto událost spouští 20 krát kadou vteøinu. To mùe bıt na velkıch
 * serverech velmi neoptimální a proto se funkce spouští jen jednou za vteøinu,
 * pomocí jednoduchého cyklu. Pokud hráè disponuje oprávnìním
 * „booscooldowns.nocancel.move“, jeho warmup èasovaèe stornovány nejsou.
 * 
 * @author Jakub Koláø
 * 
 */
public class BoosPlayerMoveListener implements Listener {
	/**
	 * Metoda zjišující, jestli hráè zmìnil pozico ve svìtì, nebo pøípadnì svìt
	 * od doby kdy pouil pøíkaz a do doby volání této metody.
	 * 
	 * @param player
	 *            hráè kterı se pohybuje
	 * @return true pokud hráè zmìnil svou pozici, nebo pokud pøešel do jiného
	 *         svìta; jinak vrací false
	 */
	private static boolean hasMoved(Player player) {
		String curworld = player.getWorld().getName();
		String cmdworld = BoosWarmUpManager.getPlayerworld().get(player);
		Location curloc = player.getLocation();
		Location cmdloc = BoosWarmUpManager.getPlayerloc().get(player);
		if (!curworld.equals(cmdworld)) {
			return true;
		} else if (cmdloc.distanceSquared(curloc) > 2) {
			return true;
		}

		return false;
	}

	private int tempTimer = 0;

	/**
	 * Pokud hráè není null a nedisponuje oprávnìním
	 * booscooldowns.nocancel.move a pokud tento hráè disponuje aktivními
	 * warmup èasovaèi, pak je hráèi odeslána zpráva, která ho informuje o
	 * ukonèení všech warmup èasovaèù a následnì tyto èasovaèe ukonèuje pomocí
	 * metody cancelWarmUps();. Metoda obsahuje jednoduchı èasovaè, kterı
	 * zajišuje, e funkce nebudou provádìny pøi kadém volání této metody (20x
	 * za vteøinu).
	 * 
	 * @param event
	 *            událost PlayerMoveEvent
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerMove(PlayerMoveEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (tempTimer < 20) {
			tempTimer = tempTimer + 1;
			return;
		} else {
			Player player = event.getPlayer();
			if (player != null
					&& !player.hasPermission("booscooldowns.nocancel.move")) {
				if (BoosWarmUpManager.hasWarmUps(player) && hasMoved(player)) {
					BoosWarmUpManager.clearLocWorld(player);
					boosChat.sendMessageToPlayer(player,
							BoosConfigManager.getWarmUpCancelledByMoveMessage());
					BoosWarmUpManager.cancelWarmUps(player);
				}
			}
			tempTimer = 0;
		}
	}
}