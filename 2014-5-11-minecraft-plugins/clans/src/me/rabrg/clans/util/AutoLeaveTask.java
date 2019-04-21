package me.rabrg.clans.util;

import me.rabrg.clans.Conf;
import me.rabrg.clans.P;

public class AutoLeaveTask implements Runnable {
	private static AutoLeaveProcessTask task;
	double rate;

	public AutoLeaveTask() {
		this.rate = Conf.autoLeaveRoutineRunsEveryXMinutes;
	}

	@Override
	public synchronized void run() {
		if (task != null && !task.isFinished()) {
			return;
		}

		task = new AutoLeaveProcessTask();
		task.runTaskTimer(P.p, 1, 1);

		// maybe setting has been changed? if so, restart this task at new rate
		if (this.rate != Conf.autoLeaveRoutineRunsEveryXMinutes) {
			P.p.startAutoLeaveTask(true);
		}
	}
}
