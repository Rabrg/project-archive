package me.rabrg.alkarid;

import java.util.ArrayList;
import java.util.List;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.GroundItem;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.api.ui.Message;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import org.osbot.rs07.utility.Area;

@ScriptManifest(name = "AlkaridGuardKiller", author = "Rabrg", version = 1.0, info = "", logo = "")
public final class AlkaridGuardKiller extends Script {

	private final Position bankPosition = new Position(3270, 3167, 0);
	private final Position midpoint = new Position(3278, 3177, 0);
	private final Position guardPosition = new Position(3293, 3177, 0);

	private final Position[] bankToGuardPath = new Position[] { midpoint, guardPosition };
	private final Position[] guardToBankPath = new Position[] { guardPosition, midpoint, bankPosition };

	private final Area guardArea = new Area(3282, 3167, 3303, 3177);

	private final int foodId = 371;
	private final int foodAmount = 1;
	private final int minHp = 50;

	private final int guardId = 3103;

	private final List<Integer> dropIds = new ArrayList<Integer>();

	@Override
	public void onStart() {
		dropIds.add(199);
		dropIds.add(201);
		dropIds.add(203);
		dropIds.add(205);
		dropIds.add(207);
		dropIds.add(209);
		dropIds.add(211);
		dropIds.add(213);
		dropIds.add(215);
		dropIds.add(217);
		dropIds.add(219);
		dropIds.add(2485);
		dropIds.add(3049);
	}

	@Override
	public int onLoop() throws InterruptedException {
		/*if (skills.getDynamic(Skill.HITPOINTS) <= minHp) {
			if (!inventory.interact("Eat", foodId)) {
				guardsToBank();
			}
		} else if (inventory.isFull()) {
			guardsToBank();
		} else if (!guardArea.contains(myPlayer())) {
			bankToGuards();
		} else {*/
		if (inventory.isFull()) {
			stop(false);
		}
		if (!myPlayer().isUnderAttack()) {
			final NPC guard = npcs.closest(guardArea, guardId);
			if (guard != null) {
				guard.interact("Attack");
			}
		}
			for (final GroundItem item : groundItems.getAll()) {
				if (dropIds.contains(item.getId())) {
					item.interact("Take");
					return random(1000, 1250);
				}
			}
		// }
		return random(250, 750);
	}

	@Override
	public void onMessage(final Message message) {
		if (message.getMessage().contains("reach that")) {
			final RS2Object door = objects.closest("Large door");
			if (door != null) {
				door.interact("Open");
			} else {
				stop(false);
			}
		}
	}

	private void guardsToBank() throws InterruptedException {
		localWalker.walkPath(guardToBankPath);
		bank.open();
		bank.depositAll();
		bank.withdraw(foodId, foodAmount);
	}

	private void bankToGuards() {
		localWalker.walkPath(bankToGuardPath);
	}

	private Position randomizePosition(final Position position) {
		final Position position_ = new Position(position.getX() + random(-1, 1), position.getY() + random(-1, 1), 0);
		log(position_);
		return position_;
	}
}
