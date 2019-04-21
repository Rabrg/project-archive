package cz.boosik.boosCooldown.Listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import cz.boosik.boosCooldown.BoosConfigManager;
import cz.boosik.boosCooldown.BoosCoolDownManager;

/**
 * Posluchaè naslouchající události, která se spouští v okamiku kdy hráè zemøe.
 * V závislosti na konfiguraci pluginu a oprávnìních hráèe mohou nastat tøi
 * rùzné aktivity. Cooldown èasovaèe mohou bıt po smrti vymazány, nebo naopak
 * mohou bıt znovu spuštìny veškeré cooldown èasovaèe pro veškeré nastavené
 * pøíkazy. Také mohou bıt vymazány záznamy o pouitích pøíkazu a hráè bude opìt
 * schopen pouívat limitované pøíkazy a po hodnotu limitu.
 * 
 * @author Jakub Koláø
 * 
 */
public class BoosPlayerDeathListener implements Listener {
	/**
	 * Metoda zjišuje jestli je entita která spustila tuto událost hráè a
	 * jestli není null. Na základì toho spouští další metody.
	 * 
	 * @param event
	 *            událost PlayerDeathEvent
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	private void onPlayerDeath(PlayerDeathEvent event) {
		Entity entity = event.getEntity();
		if (entity != null && entity instanceof Player) {
			Player player = (Player) entity;
			clearCooldownsOnDeath(player);
			clearUsesOnDeath(player);
			startCooldownsOnDeath(player);
		}
	}

	/**
	 * Na základì konfigurace metoda spouští všechny cooldown èasovaèe
	 * specifikovaného hráèe tím e spustí medotu startAllCooldowns();.
	 * 
	 * @param player
	 *            hráè, kterı spustil událost PlayerDeathEvent
	 */
	private void startCooldownsOnDeath(Player player) {
		if (player != null) {
			if (BoosConfigManager.getStartCooldownsOnDeath()) {
				BoosCoolDownManager.startAllCooldowns(player, "");
			}
		}
	}

	/**
	 * Na základì konfigurace a toho jestli hráè disponuje oprávnìním
	 * booscooldowns.clear.uses.death metoda vymae všechny záznamy o spuštìní
	 * všech pøíkazù specifikovaného hráèe tím e spustí metodu
	 * clearSomething();.
	 * 
	 * @param player
	 *            hráè, kterı spustil událost PlayerDeathEvent
	 */
	private void clearUsesOnDeath(Player player) {
		if (player != null
				&& player.hasPermission("booscooldowns.clear.uses.death")) {
			if (BoosConfigManager.getCleanUsesOnDeath()) {
				BoosConfigManager.clearSomething("uses", player.getName()
						.toLowerCase());
			}
		}
	}

	/**
	 * Na základì konfigurace a toho jestli hráè disponuje oprávnìním
	 * booscooldowns.clear.cooldowns.death metoda vymae všechny cooldown
	 * èasovaèe všech pøíkazù specifikovaného hráèe tím e spustí metodu
	 * clearSomething();.
	 * 
	 * @param player
	 *            hráè, kterı spustil událost PlayerDeathEvent
	 */
	private void clearCooldownsOnDeath(Player player) {
		if (player != null
				&& player.hasPermission("booscooldowns.clear.cooldowns.death")) {
			if (BoosConfigManager.getCleanCooldownsOnDeath()) {
				BoosConfigManager.clearSomething("cooldown", player.getName()
						.toLowerCase());
			}
		}
	}
}
