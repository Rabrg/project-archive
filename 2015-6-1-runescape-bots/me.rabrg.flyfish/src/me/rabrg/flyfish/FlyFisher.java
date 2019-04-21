package me.rabrg.flyfish;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.wrappers.widgets.message.Message;
import org.dreambot.api.wrappers.widgets.message.MessageType;

@ScriptManifest(author="Rabrg", category= Category.FISHING, name="Fly Fisher", version=0.1, description="Fishes fly")
public final class FlyFisher extends AbstractScript {

	private NPC fish;
	private Tile fishTile;

	@Override
	public void onStart() {
		getSkillTracker().start(Skill.FISHING);
	}

	@Override
	public int onLoop() {
		if (!getInventory().contains("Feather")) {
			stop();
		}
		for (final Player player : getPlayers().all()) {
			if (!player.getName().equals(getLocalPlayer().getName())) {
				getWorldHopper().quickHop(getWorlds().getRandomWorld(new Filter<World>() {
					@Override
					public boolean match(final World world) {
						return world.isMembers() && !world.isPVP();
					}
				}).getID());
				sleep(4200, 8400);
				break;
			}
		}
		fish = getNpcs().closest(new Filter<NPC>() {
			@Override
			public boolean match(final NPC npc) {
				return npc.getName().equals("Fishing spot") && npc.hasAction("Lure") && npc.getX() < 2387;
			}
		});
		if (!getInventory().isFull() && (getLocalPlayer().getAnimation() < 1 || !fish.getTile().equals(fishTile) || getDialogues().continueDialogue())) {
			if (fish.interact("Lure")) {
				fishTile = fish.getTile();
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return getLocalPlayer().getAnimation() > 1;
					}
				}, Calculations.random(2048, 8192));
				if (Calculations.random(0.0, 1.0) > .985) {
					getCamera().rotateTo(Calculations.random(0, 360), Calculations.random(0, 360));
				} else if (Calculations.random(0.0, 1.0) > .985) {
					getSkills().hoverSkill(Skill.FISHING);
					sleep(1200, 2400);
				} else if (Calculations.random(0.0, 1.0) > .985) {
					getMouse().moveMouseOutsideScreen();
					sleep(1200, 2400);
				}
			}
		} else if (getInventory().isFull()) {
			if (!getTabs().isOpen(Tab.INVENTORY)) {
				getTabs().open(Tab.INVENTORY);
			}
			getInventory().dropAllExcept("Fly fishing rod", "Feather");
		}
		return Calculations.random(0, 1200);
	}

	@Override
	public void onMessage(final Message message) {
		if (message.getType() == MessageType.PLAYER) {
			final String msg = message.getMessage().toLowerCase();
			String[] replies = null;
			if (msg.contains("fish lvl") || msg.contains("fish level") || msg.contains("fishing lvl") || msg.contains("fishing level")) {
				replies = new String[] { ("" + getSkills().getRealLevel(Skill.FISHING)) };
			} else if (msg.contains("bot") || msg.contains("reported")) {
				replies = new String[] { "reported", "noob", "fag", "nooob" };
			}
			if (replies != null) {
				sleep(1200, 3600);
				getKeyboard().type(replies[Calculations.random(replies.length)], true, true);
				sleep(600, 2400);
			}
		}
	}

	@Override
	public void onPaint(final Graphics g) {
		g.drawString("Fishing (" + getSkillTracker().getStartLevel(Skill.FISHING) + "+" + getSkillTracker().getGainedLevels(Skill.FISHING) + ") xp: " + getSkillTracker().getGainedExperience(Skill.FISHING) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.FISHING), 5, 60);
	}
}
