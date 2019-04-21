package me.rabrg.kitpvp.listener;

import me.rabrg.kitpvp.KitPvP;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public final class KitSignListener implements Listener {

	@SuppressWarnings("unused")
	private final KitPvP kitPvP;

	public KitSignListener(final KitPvP kitPvP) {
		this.kitPvP = kitPvP;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onSignChangeEvent(final SignChangeEvent event) {
		
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteractEvent(final PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			final Block block = event.getClickedBlock();
			if(block.getType() == Material.SIGN || block.getType() == Material.SIGN_POST) { 
				@SuppressWarnings("unused")
				final Sign sign = (Sign) block;
			}
		}
	}
}
