package me.rabrg.agility;

import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

@ScriptManifest(name = "Barbarian assault agility", author = "Rabrg", version = 0.1, info = "", logo = "")
public final class BarbarianAssaultAgilityScript extends Script {

	private int part = 0;

	private Filter<RS2Object> climbingWallFilter = new Filter<RS2Object>() {
		@Override
		public boolean match(RS2Object object) {
			if (object.getName().equals("Crumbling wall")) {
				switch(part) {
				case 5:
					return object.getPosition().getX() == 2536;
				case 6:
					return object.getPosition().getX() == 2539;
				case 7:
					return object.getPosition().getX() == 2542;
				}
			}
			return false;
		}
	};

	@SuppressWarnings("unchecked")
	@Override
	public int onLoop() throws InterruptedException {
		switch(part) {
		case 0: // rope swing
			log("rope swing");
			objects.closest(23131).interact("Swing-on");
			part = 1;
			return random(4000, 5000);
		case 1: // log balance
			log("log balance");
			objects.closest("Log balance").interact("Walk-across");
			part = 2;
			return random(3000, 4000);
		case 2: // obstacle net
			log("obstacle net");
			objects.closest("Obstacle net").interact("Climb-over");
			part++;
			return random(3000, 4000);
		case 3: // balancing ledge
			log("balancing ledge");
			objects.closest("Balancing ledge").interact("Walk-across");
			part++;
			return random(3000, 4000);
		case 4: // ladder
			log("ladder");
			if (myPosition().getZ() == 1) {
				objects.closest("Ladder").interact("Climb-down");
			} else {
				part++;
			}
			return random(3000, 4000);
		case 5: // crumbling wall
		case 6:
		case 7:
			log("climbing wall");
			objects.closest(climbingWallFilter).interact("Climb-over");
			part++;
			return random(1200, 1800);
		case 8:
			log("reset");
			part = 0;
			break;
		}
		return random(150, 300);
	}

	@Override
	public void onMessage(final Message message) {
		switch(message.getMessage()) {
		case "You skillfully swing across.": // rope swing
		case "...You make it safely to the other side.": // log balance
		case "You climb the netting...": // obstacle net
		case "You skillfully edge across the gap.": // balancing ledge
		case "You climb the low wall...": // crumbling wall
			log("incremented");
			part++;
			break;
		}
	}
}
