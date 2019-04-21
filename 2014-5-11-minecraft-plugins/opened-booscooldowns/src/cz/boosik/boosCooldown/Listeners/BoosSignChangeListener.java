package cz.boosik.boosCooldown.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import util.boosChat;
import cz.boosik.boosCooldown.BoosConfigManager;

/**
 * @author Jakub Koláø
 *
 */
public class BoosSignChangeListener implements Listener {
	/**
	 * @param event
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	private void onSignChange(SignChangeEvent event) {
		if (event.isCancelled())
			return;

		Player player = event.getPlayer();
		String line1 = event.getLine(0);
		String line2 = event.getLine(1);
		if (line1.equals("[boosCooldowns]")) {
			if (line2.equals("player")
					&& !player
							.hasPermission("booscooldowns.signs.player.place")) {
				boosChat.sendMessageToPlayer(player,
						BoosConfigManager.getCannotCreateSignMessage());
				event.getBlock().breakNaturally();
				event.setCancelled(true);
				return;
			}
			if (line2.equals("server")
					&& !player
							.hasPermission("booscooldowns.signs.server.place")) {
				boosChat.sendMessageToPlayer(player,
						BoosConfigManager.getCannotCreateSignMessage());
				event.getBlock().breakNaturally();
				event.setCancelled(true);
				return;
			}
		}
	}
}
