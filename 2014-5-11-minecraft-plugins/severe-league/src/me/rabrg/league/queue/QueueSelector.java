package me.rabrg.league.queue;

import me.rabrg.league.League;

import org.bukkit.scheduler.BukkitRunnable;

public final class QueueSelector extends BukkitRunnable {

	private final League league;

	public QueueSelector(final League league) {
		this.league = league;
	}

	@Override
	public void run() {
		league.getQueue().select();
		league.getServer().broadcastMessage("The next league queue will be selected in one minute.");
	}

}
