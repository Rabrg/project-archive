package me.rabrg.hardcore.listener;

import me.rabrg.hardcore.Hardcore;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public final class DeathBanListener implements Listener {

	private final Hardcore hardcore;

	public DeathBanListener(final Hardcore hardcore) {
		this.hardcore = hardcore;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerDeathEvent(final PlayerDeathEvent event) {
		if (hardcore.getConfiguration().killedByPlayerOnlyBan() && event.getEntity().getKiller() == null) {
			return;
		}
		// ban
	}
}
