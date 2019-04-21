package me.rabrg.willowlog;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.widgets.message.Message;
import org.dreambot.api.wrappers.widgets.message.MessageType;

import me.rabrg.willowlog.node.Banker;
import me.rabrg.willowlog.node.Cutter;
import me.rabrg.willowlog.node.Node;

@ScriptManifest(author="Rabrg", category= Category.WOODCUTTING, name="Willow log", version=0.1, description="Cuts willow logs")
public final class WillowLog extends AbstractScript {

	private final Node[] nodes = { new Banker(this), new Cutter(this) };

	@Override
	public void onStart() {
		getSkillTracker().start(Skill.WOODCUTTING);
	}

	@Override
	public int onLoop() {
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
			} else if (msg.contains("wc lvl") || msg.contains("wcing level") || msg.contains("woodcutting lvl") || msg.contains("woodcutting level")) {
				replies = new String[] { ("" + getSkills().getRealLevel(Skill.MINING)) };
			}
			if (replies != null) {
				sleep(1000, 5000);
				getKeyboard().type(replies[Calculations.random(replies.length)], true, true);
				sleep(600, 2400);
			}
		}
	}

	@Override
	public void onPaint(final Graphics g){
		g.drawString("Woodcutting (" + getSkillTracker().getStartLevel(Skill.WOODCUTTING) + "+" + getSkillTracker().getGainedLevels(Skill.WOODCUTTING) + ") xp: " + getSkillTracker().getGainedExperience(Skill.WOODCUTTING) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.WOODCUTTING), 5, 30);
	}
}
