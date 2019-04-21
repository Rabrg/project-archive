package me.rabrg.script;

import me.rabrg.script.task.Task;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.input.mouse.MiniMapTileDestination;
import org.osbot.rs07.script.Script;

public abstract class TaskScript extends Script {

	private Task[] tasks;

	public void setTasks(final Task... tasks) {
		this.tasks = tasks;
	}

	@Override
	public final int onLoop() throws InterruptedException {
		for (final Task task : tasks) {
			if (task.validate()) {
				sleep(task.execute());
			}
		}
		return getLoopDelay();
	}

	
	public abstract int getLoopDelay();

	public void traversePath(Position[] path, boolean reversed) throws InterruptedException {
		if (!reversed) {
			for (int i = 1; i < path.length; i++)
				if (!walkTile(path[i]))
					i--;
		} else {
			for (int i = path.length-2; i > 0; i--)
				if (!walkTile(path[i]))
					i++;
		}
	}

	private boolean walkTile(Position p) throws InterruptedException {
		mouse.click(new MiniMapTileDestination(bot, p), false);
		int failsafe = 0;
		while (failsafe < 10 && myPlayer().getPosition().distance(p) > 2) {
			sleep(200);
			failsafe++;
			if (myPlayer().isMoving())
				failsafe = 0;
		}
		if (failsafe == 10)
			return false;
		return true;
	}
}
