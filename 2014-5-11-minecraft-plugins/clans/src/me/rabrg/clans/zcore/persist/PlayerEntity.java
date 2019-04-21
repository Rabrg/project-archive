package me.rabrg.clans.zcore.persist;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerEntity extends Entity {
	public Player getPlayer() {
		return Bukkit.getPlayerExact(this.getId());
	}

	public boolean isOnline() {
		return this.getPlayer() != null;
	}

	// make sure target player should be able to detect that this player is
	// online
	public boolean isOnlineAndVisibleTo(final Player player) {
		final Player target = this.getPlayer();
		return target != null && player.canSee(target);
	}

	public boolean isOffline() {
		return !isOnline();
	}

	// -------------------------------------------- //
	// Message Sending Helpers
	// -------------------------------------------- //

	public void sendMessage(final String msg) {
		final Player player = this.getPlayer();
		if (player == null) {
			return;
		}
		player.sendMessage(msg);
	}

	public void sendMessage(final List<String> msgs) {
		for (final String msg : msgs) {
			this.sendMessage(msg);
		}
	}

}
