package me.rabrg.position;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

@ScriptManifest(name = "Path Printer", author = "Rabrg", version = 0.2, info = "", logo = "")
public final class PathPrinterScript extends Script {

	private Position lastPosition;
	private String path;

	@Override
	public void onStart() {
		bot.setHumanInputEnabled(true);
	}

	@Override
	public void onExit() {
		log(path.substring(0, path.length() - 2));
	}

	@Override
	public int onLoop() throws InterruptedException {
		if (!myPosition().equals(lastPosition) && !myPlayer().isMoving() && !myPlayer().isAnimating()) {
			lastPosition = myPosition();
			path += "new Position(" + lastPosition.getX() + ", " + lastPosition.getY() + ", " + lastPosition.getZ() + "), ";
		}
		return random(50, 150);
	}

}
