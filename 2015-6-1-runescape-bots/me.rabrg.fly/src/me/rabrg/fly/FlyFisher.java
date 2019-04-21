package me.rabrg.fly;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.widgets.message.Message;
import org.dreambot.api.wrappers.widgets.message.MessageType;

import me.rabrg.fly.node.ContinueNode;
import me.rabrg.fly.node.DropNode;
import me.rabrg.fly.node.FishNode;
import me.rabrg.fly.node.FishingNode;
import me.rabrg.fly.node.Node;

@ScriptManifest(author="Rabrg", category= Category.FISHING, name="Fly Fisher", version=0.1, description="Fishes shrimp")
public final class FlyFisher extends AbstractScript {

	private final Node[] nodes = { new DropNode(this), new ContinueNode(this), new FishNode(this), new FishingNode(this) };

	private Node currentNode;

	@Override
	public void onStart() {
		getSkillTracker().start(Skill.FISHING);
	}

	@Override
	public int onLoop() {
		for (final Node node : nodes) {
			if ((currentNode = node).validate())
				return node.execute();
		}
		return Calculations.random(600, 1800);
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
		g.drawString("Node: " + (currentNode == null ? "null" : currentNode.getName()), 5, 45);
		g.drawString("Fishing (" + getSkillTracker().getStartLevel(Skill.FISHING) + "+" + getSkillTracker().getGainedLevels(Skill.FISHING) + ") xp: " + getSkillTracker().getGainedExperience(Skill.FISHING) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.FISHING), 5, 60);
	}
}
