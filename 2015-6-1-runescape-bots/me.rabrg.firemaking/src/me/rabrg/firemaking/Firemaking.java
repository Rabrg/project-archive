package me.rabrg.firemaking;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.widgets.message.Message;
import org.dreambot.api.wrappers.widgets.message.MessageType;

import me.rabrg.firemaking.node.Banker;
import me.rabrg.firemaking.node.Lighter;
import me.rabrg.firemaking.node.Node;

@ScriptManifest(author="Rabrg", category= Category.FIREMAKING, name="Friemaking", version=0.1, description="Lights fires")
public final class Firemaking extends AbstractScript {

	private final Node[] nodes = { new Banker(this), new Lighter(this) };

	@Override
	public void onStart() {
		getSkillTracker().reset(Skill.FIREMAKING);
		getSkillTracker().start(Skill.FIREMAKING);
	}

	@Override
	public int onLoop() {
		getDialogues().continueDialogue();
		for (final Node node : nodes) {
			if (node.validate()) {
				return node.execute();
			}
		}
		return Calculations.random(0, 255);
	}

	@Override
	public void onMessage(final Message message) {
		if (message.getType() == MessageType.PLAYER) {
			final String msg = message.getMessage().toLowerCase();
			String[] replies = null;
			if (msg.contains("bot") || msg.contains("reported")) {
				replies = new String[] { "rekt", "noob" };
			} else if (msg.contains(getLocalPlayer().getName().toLowerCase())) {
				replies = new String[] { "fag", "choob", "noob" };
			} else if (msg.contains("fm lvl") || msg.contains("fming level") || msg.contains("firemaking lvl") || msg.contains("firemaking level")) {
				replies = new String[] { ("" + getSkills().getRealLevel(Skill.MINING)) };
			}
			if (replies != null) {
				sleep(1000, 5000);
				getKeyboard().type(replies[Calculations.random(replies.length)], true, true);
				sleep(600, 2400);
			}
		} else if (message.getMessage().contains("can't light a fire here")) {
			getWalking().walkExact(new Tile(getLocalPlayer().getX(), getLocalPlayer().getY() - 1));
			sleep(1200, 2400);
		}
	}

	@Override
	public void onPaint(final Graphics g){
		g.drawString("Firemaking (" + getSkillTracker().getStartLevel(Skill.FIREMAKING) + "+" + getSkillTracker().getGainedLevels(Skill.FIREMAKING) + ") xp: " + getSkillTracker().getGainedExperience(Skill.FIREMAKING) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.FIREMAKING), 5, 30);
	}
}
