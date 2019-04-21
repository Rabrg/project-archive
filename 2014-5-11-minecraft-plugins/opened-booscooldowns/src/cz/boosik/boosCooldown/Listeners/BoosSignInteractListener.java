package cz.boosik.boosCooldown.Listeners;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import util.boosChat;
import cz.boosik.boosCooldown.BoosConfigManager;
import cz.boosik.boosCooldown.BoosCoolDown;

/**
 * @author Jakub
 *
 */
public class BoosSignInteractListener implements Listener {
	private final BoosCoolDown plugin;

	/**
	 * @param instance
	 */
	public BoosSignInteractListener(BoosCoolDown instance) {
		plugin = instance;
	}

	/**
	 * @param event
	 */
	@EventHandler(priority = EventPriority.NORMAL)
	private void onSignInteract(PlayerInteractEvent event) {
		String msg;
		if (event.isCancelled())
			return;

		if (event.hasBlock() && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (event.getClickedBlock().getType() == Material.SIGN_POST
					|| event.getClickedBlock().getType() == Material.WALL_SIGN) {
				Sign s = (Sign) event.getClickedBlock().getState();
				String line1 = s.getLine(0);
				String line2 = s.getLine(1);
				String line3 = s.getLine(2);
				String line4 = s.getLine(3);
				Player player = event.getPlayer();
				if (line1.equals("[boosCooldowns]")) {
					if (line2.equals("player")
							&& player
									.hasPermission("booscooldowns.signs.player.use")) {
						msg = line3;
						if (line3.endsWith("+") || !line4.isEmpty()) {
							msg = line3.substring(0, line3.length() - 1) + " "
									+ line4;
						}
						event.getPlayer().chat(msg);
					} else if (line2.equals("server")
							&& player
									.hasPermission("booscooldowns.signs.server.use")) {
						msg = line3;
						if (line3.endsWith("+") || !line4.isEmpty()) {
							msg = line3.substring(0, line3.length() - 1) + " "
									+ line4;
						}
						plugin.getServer().dispatchCommand(
								plugin.getServer().getConsoleSender(), msg);
					} else {
						boosChat.sendMessageToPlayer(player,
								BoosConfigManager.getCannotUseSignMessage());
					}
				}
			}
		}
	}
}
