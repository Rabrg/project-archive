package me.rabrg.miner.task;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.Script;

import me.rabrg.miner.MinerTaskScript;
import me.rabrg.script.task.Task;

public final class WalkToMineTask implements Task {

	private final MinerTaskScript script;

	private final Position[] bankToMinePath = new Position[] {new Position(3278,3177,0), new Position(3282,3189,0), new Position(3284,3199,0), new Position(3288,3210,0), new Position(3292,3219,0), new Position(3294,3232,0), new Position(3293,3245,0), new Position(3295,3257,0), new Position(3295,3268,0), new Position(3298,3277,0), new Position(3302,3284,0)};

	public WalkToMineTask(final MinerTaskScript script) {
		this.script = script;
	}

	@Override
	public boolean validate() {
		return !script.inventory.isFull() && !script.miningArea.contains(script.myPlayer());
	}

	@Override
	public int execute() throws InterruptedException {
		script.localWalker.walkPath(bankToMinePath);
		return Script.random(5, 25);
	}

}
