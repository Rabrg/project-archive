package me.rabrg.miner;

import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.Area;

import me.rabrg.miner.task.BankTask;
import me.rabrg.miner.task.MineTask;
import me.rabrg.miner.task.WalkToBankTask;
import me.rabrg.miner.task.WalkToMineTask;
import me.rabrg.script.TaskScript;

@ScriptManifest(name = "MinerAdv", author = "Rabrg", version = 1.0, info = "", logo = "")
public final class MinerTaskScript extends TaskScript {

	public final Area bankArea = new Area(3269, 3161, 3272, 3173);
	public final Area miningArea = new Area(3301, 3283, 3304, 3286);

	public MinerTaskScript() {
		setTasks(new WalkToMineTask(this), new WalkToBankTask(this), new BankTask(this), new MineTask(this));
	}

	@Override
	public int getLoopDelay() {
		return random(25, 125);
	}

}
