package me.rabrg.pickpocket;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.widgets.message.Message;

import me.rabrg.pickpocket.node.BankNode;
import me.rabrg.pickpocket.node.EatNode;
import me.rabrg.pickpocket.node.KnockOutNode;
import me.rabrg.pickpocket.node.LadderNode;
import me.rabrg.pickpocket.node.Node;
import me.rabrg.pickpocket.node.PickpocketNode;

@ScriptManifest(author="Rabrg", category= Category.THIEVING, name="Rabrg Pickpocket", version=0.1, description="Pickpockets master farmers")
public final class RabrgPickpocket extends AbstractScript {

	private final Node[] nodes = { new BankNode(this), new EatNode(this), new LadderNode(this), new PickpocketNode(this), new KnockOutNode(this) };

	@Override
	public void onStart() {
		getSkillTracker().start(Skill.THIEVING);
	}

	@Override
	public int onLoop() {
		for (final Node node : nodes) {
			if (node.validate())
				return node.execute();
		}
		return Calculations.random(5, 25);
	}

	@Override
	public void onMessage(final Message message) {
		if (message.getMessage().contains("stunned"))
			sleep(2400, 3200);
		else if (message.getMessage().contains("can't reach that"))
			sleep(999999);
		else if (message.getMessage().contains("during combat"))
			LadderNode.climb = true;
	}

	@Override
	public void onPaint(final Graphics g) {
		g.drawString("Thieving xp: " + getSkillTracker().getGainedExperience(Skill.THIEVING), 5, 90);
		g.drawString("Thieving xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.THIEVING), 5, 105);
	}
}
