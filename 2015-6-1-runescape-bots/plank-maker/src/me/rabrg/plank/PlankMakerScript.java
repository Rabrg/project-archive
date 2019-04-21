package me.rabrg.plank;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

@ScriptManifest(name = "PlankMaker", author = "Rabrg", version = 1.0, info = "", logo = "")
public final class PlankMakerScript extends Script {

	private final Position[] bankToPlankMakerPartOne =new Position[] { new Position(3264, 3428, 0), new Position(3278, 3428, 0), new Position(3286, 3440, 0), new Position(3288, 3455, 0), new Position(3295, 3467, 0), new Position(3301, 3481, 0), new Position(3302, 3490, 0) };
	private final int oakLogId = 1521;
	private final int sawmillOperatorId = 5422;
	@Override
	public int onLoop() throws InterruptedException {
		interfaces.getOpenInterface().getChild(94).interact("Buy All");
		return random(100, 250);
	}

}
