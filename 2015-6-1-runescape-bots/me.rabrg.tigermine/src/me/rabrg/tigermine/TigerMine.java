package me.rabrg.tigermine;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.message.Message;
import org.dreambot.api.wrappers.widgets.message.MessageType;

@ScriptManifest(author = "Rabrg", category = Category.MINING, name = "Tiger Mine", version = 0.1)
public final class TigerMine extends AbstractScript {

	@Override
	public void onStart() {
		getSkillTracker().start(Skill.MINING);
	}

	@Override
	public int onLoop() {
		if (Calculations.random(0.0, 1.0) > .95) {
			getCamera().rotateToEvent(Calculations.random(0, 360), Calculations.random(0, 360)).run();
		} else if (getInventory().isFull()) {
			getInventory().dropAllExcept(new Filter<Item>() {
				@Override
				public boolean match(final Item item) {
					return item.getName().contains("pickaxe");
				}
			});
		} else if (getLocalPlayer().getAnimation() < 1) {
			final GameObject rocks = getGameObjects().closest(21702, 21703, 21704);
			if (rocks != null) {
				if (rocks.interactForceLeft("Mine")) {
					sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							switch (getGameObjects().getTopObjectOnTile(rocks.getTile()).getID()) {
							case 21702:
							case 21703:
							case 21704:
							case 21705:
								return false;
							default:
								return true;
							}
						}
					}, Calculations.random(750, 2400));
				}
			}
		}
		return Calculations.random(0, 1200);
	}

	@Override
	public void onMessage(final Message message) {
		if (message.getType() == MessageType.PLAYER) {
			final String msg = message.getMessage().toLowerCase();
			String[] replies = null;
			if (msg.contains("bot") || msg.contains("reported")) {
				replies = new String[] { "reported", "noob", "fag", "nooob" };
			} else if (msg.contains("where") || msg.contains("how") || msg.contains("are") || msg.contains("?")) {
				replies = new String[] { "nope", "no", "idk" };
			} else if (msg.contains("mining lvl") || msg.contains("mining level") || msg.contains("mine lvl") || msg.contains("mine level")) {
				replies = new String[] { ("" + getSkills().getRealLevel(Skill.MINING)) };
			}
			if (replies != null) {
				getKeyboard().type(replies[Calculations.random(replies.length)], true, true);
				sleep(600, 3600);
			}
		}
	}

	@Override
	public void onPaint(final Graphics g){
		g.drawString("Mining (" + getSkillTracker().getStartLevel(Skill.MINING) + "+" + getSkillTracker().getGainedLevels(Skill.MINING) + ") xp: " + getSkillTracker().getGainedExperience(Skill.MINING) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.MINING), 5, 30);
	}
}
