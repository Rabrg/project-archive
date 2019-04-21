package me.rabrg.abyss.node;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;

import me.rabrg.abyss.RabrgAbyssPro;

public final class AbyssNode extends Node {

	public static final Tile ABYSS_CENTER_TILE = new Tile(3039, 4835);

	private final String[] obstacleNames;

	public static Area obstacleArea;

	private GameObject obstacle;
	private NPC darkMage;

	public AbyssNode(final RabrgAbyssPro script) {
		super(script);
		final List<String> list = new ArrayList<String>();
		list.add("Passage");
		if (script.AGILITY_OBSTACLE) {
			list.add("Gap");
		}
		if (script.THIEVING_OBSTACLE) {
			list.add("Eyes");
		}
		if (script.MINING_OBSTACLE) {
			list.add("Rock");
		}
		obstacleNames = list.toArray(new String[list.size()]);
	}

	@Override
	public boolean validate() {
		return ABYSS_AREA.contains(script.getLocalPlayer());
	}

	@Override
	public void execute() {
		if (!script.getMap().canReach(ABYSS_CENTER_TILE) && (obstacle = script.getGameObjects().closest(obstacleNames)) != null && !script.obstacle) {
			log("5");
			if (obstacle.distance() > 5 && script.getWalking().walk(obstacle)) {
				log("6");
				if (!obstacle.isOnScreen()) {
					log("7");
					script.getCamera().rotateToEntity(obstacle);
				}
			} else if (obstacle.interact(obstacle.getActions()[0])) {
				log("8");
				sleep(600, 900);
			} else {
				log("9");
				script.getWalking().walk(obstacle);
			}
		} else if (script.getMap().canReach(ABYSS_CENTER_TILE) && script.getInventory().contains(5515, 5513, 5511) && (darkMage = script.getNpcs().closest("Dark mage")) != null) {
			if (darkMage.interact("Repairs")) {
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						sleep(25, 150);
						return !script.getInventory().contains(5515, 5513, 5511);
					}
				}, Calculations.random(15000, 30000));
			} else {
				script.getWalking().walk(darkMage);
				script.getCamera().rotateToEntity(darkMage);
			}
		} else if (script.getMap().canReach(ABYSS_CENTER_TILE) && (obstacle = script.getGameObjects().closest(script.RUNE + " rift")) != null) {
			obstacleArea = script.getClient().getViewportTools().getModelArea(obstacle);
			log("1");
			if (obstacle.distance() > 8) {
				log("2");
				script.getWalking().walk(obstacle);
				sleep(300, 900);
			} else if (obstacle.interact("Exit-through")) {
				log("3");
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						sleep(25, 150);
						return !ABYSS_AREA.contains(script.getLocalPlayer());
					}
				}, Calculations.random(5400, 7200));
			} else {
				log("4");
				script.getWalking().walk(obstacle);
				script.getCamera().rotateToEntity(obstacle);
				sleep(300, 900);
			}
		}
	}

	@Override
	public String toString() {
		return "Abyss";
	}

}
