package me.rabrg.miner.task;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;

import me.rabrg.miner.MinerTaskScript;
import me.rabrg.script.task.Task;

public final class MineTask implements Task {

	private final MinerTaskScript script;

	private final int[] rockIds = new int[] { 13710, 13711 };

	private RS2Object currentRock;

	public MineTask(final MinerTaskScript script) {
		this.script = script;
	}

	@Override
	public boolean validate() {
		return script.miningArea.contains(script.myPlayer()) && !script.inventory.isFull();
	}

	@Override
	public int execute() {
		currentRock = script.objects.closest(script.miningArea, rockIds);
		if (currentRock != null) {
			currentRock.interact("Mine");
		}
		return Script.random(175, 250);
	}

}
