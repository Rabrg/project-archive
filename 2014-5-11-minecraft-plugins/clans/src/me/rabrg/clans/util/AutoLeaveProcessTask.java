package me.rabrg.clans.util;

import java.util.ArrayList;
import java.util.ListIterator;

import org.bukkit.scheduler.BukkitRunnable;

import me.rabrg.clans.CPlayer;
import me.rabrg.clans.CPlayers;
import me.rabrg.clans.Clan;
import me.rabrg.clans.Conf;
import me.rabrg.clans.P;
import me.rabrg.clans.struct.Role;

public class AutoLeaveProcessTask extends BukkitRunnable {
	private transient boolean readyToGo = false;
	private transient boolean finished = false;
	private transient ArrayList<CPlayer> fplayers;
	private transient ListIterator<CPlayer> iterator;
	private transient double toleranceMillis;

	public AutoLeaveProcessTask() {
		fplayers = new ArrayList<CPlayer>(CPlayers.i.get());
		this.iterator = fplayers.listIterator();
		this.toleranceMillis = Conf.autoLeaveAfterDaysOfInactivity * 24 * 60 * 60 * 1000;
		this.readyToGo = true;
		this.finished = false;
	}

	@Override
	public void run() {
		if (Conf.autoLeaveAfterDaysOfInactivity <= 0.0 || Conf.autoLeaveRoutineMaxMillisecondsPerTick <= 0.0) {
			this.stop();
			return;
		}

		if (!readyToGo) {
			return;
		}
		// this is set so it only does one iteration at a time, no matter how
		// frequently the timer fires
		readyToGo = false;
		// and this is tracked to keep one iteration from dragging on too long
		// and possibly choking the system if there are a very large number of
		// players to go through
		final long loopStartTime = System.currentTimeMillis();

		while (iterator.hasNext()) {
			final long now = System.currentTimeMillis();

			// if this iteration has been running for maximum time, stop to take
			// a breather until next tick
			if (now > loopStartTime + Conf.autoLeaveRoutineMaxMillisecondsPerTick) {
				readyToGo = true;
				return;
			}

			final CPlayer fplayer = iterator.next();
			if (fplayer.isOffline() && now - fplayer.getLastLoginTime() > toleranceMillis) {
				if (Conf.logClanLeave || Conf.logClanKick) {
					P.p.log("Player " + fplayer.getName() + " was auto-removed due to inactivity.");
				}

				// if player is clan admin, sort out the clan since he's going
				// away
				if (fplayer.getRole() == Role.ADMIN) {
					final Clan clan = fplayer.getClan();
					if (clan != null) {
						fplayer.getClan().promoteNewLeader();
					}
				}

				fplayer.leave(false);
				iterator.remove(); // go ahead and remove this list's link to
									// the CPlayer object
				fplayer.detach();
			}
		}

		// looks like we've finished
		this.stop();
	}

	// we're done, shut down
	public void stop() {
		readyToGo = false;
		finished = true;

		this.cancel();
	}

	public boolean isFinished() {
		return finished;
	}
}
