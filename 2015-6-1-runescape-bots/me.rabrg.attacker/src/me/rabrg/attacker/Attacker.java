package me.rabrg.attacker;

import java.awt.Graphics;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.world.World;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.wrappers.items.GroundItem;
import org.dreambot.api.wrappers.widgets.message.Message;
import org.dreambot.api.wrappers.widgets.message.MessageType;

import me.rabrg.attacker.node.AttackNode;
import me.rabrg.attacker.node.EatNode;
import me.rabrg.attacker.node.Node;

@ScriptManifest(author="Rabrg", category= Category.COMBAT, name="Attacker", version=0.1, description="Kills things")
public final class Attacker extends AbstractScript {

	private final Node[] nodes = { new EatNode(this), new AttackNode(this) };

	private Node currentNode;

	private GroundItem championScroll;

	@Override
	public void onStart() {
		getSkillTracker().start();
	}

	@Override
	public int onLoop() {
		if ((championScroll = getGroundItems().closest(new Filter<GroundItem>() {
			@Override
			public boolean match(final GroundItem item) {
				return item.getName().toLowerCase().contains("champion") || item.getName().toLowerCase().contains("defender");
			}
		})) != null && championScroll.interact("Take") && !getInventory().isFull()) {
			sleep(600, 1200);
		}
		for (final Player player : getPlayers().all()) {
			if (!player.getName().equals(getLocalPlayer().getName()) && player.distance() < 10) {
				getWorldHopper().quickHop(getWorlds().getRandomWorld(new Filter<World>() {
					@Override
					public boolean match(final World world) {
						return world.isMembers() && !world.isPVP();
					}
				}).getID());
				return Calculations.random(600, 1200);
			}
		}
		if (this.getDialogues().canContinue())
			getDialogues().continueDialogue();
		for (final Node node : nodes)
			if ((currentNode = node).validate())
				return node.execute();
		return Calculations.random(5, 25);
	}

	@Override
	public void onMessage(final Message message) {
		if (message.getType() == MessageType.GAME) {
			if (message.getMessage().contains("I'm already under attack.")) {
				sleep(1200, 1600);
			}
		}
	}

	@Override
	public void onPaint(final Graphics g){
		g.drawString("Node: " + (currentNode == null ? "null" : currentNode.getName()), 5, 45);
		g.drawString("Attack (" + getSkillTracker().getStartLevel(Skill.ATTACK) + "+" + getSkillTracker().getGainedLevels(Skill.ATTACK) + ") xp: " + getSkillTracker().getGainedExperience(Skill.ATTACK) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.ATTACK), 5, 60);
		g.drawString("Strength (" + getSkillTracker().getStartLevel(Skill.STRENGTH) + "+" + getSkillTracker().getGainedLevels(Skill.STRENGTH) + ") xp: " + getSkillTracker().getGainedExperience(Skill.STRENGTH) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.STRENGTH), 5, 75);
		g.drawString("Defence (" + getSkillTracker().getStartLevel(Skill.DEFENCE) + "+" + getSkillTracker().getGainedLevels(Skill.DEFENCE) + ") xp: " + getSkillTracker().getGainedExperience(Skill.DEFENCE) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.DEFENCE), 5, 90);
		g.drawString("Magic (" + getSkillTracker().getStartLevel(Skill.MAGIC) + "+" + getSkillTracker().getGainedLevels(Skill.MAGIC) + ") xp: " + getSkillTracker().getGainedExperience(Skill.MAGIC) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.MAGIC), 5, 105);
		g.drawString("Hitpoints (" + getSkillTracker().getStartLevel(Skill.HITPOINTS) + "+" + getSkillTracker().getGainedLevels(Skill.HITPOINTS) + ") xp: " + getSkillTracker().getGainedExperience(Skill.HITPOINTS) + ", xp/h: " + getSkillTracker().getGainedExperiencePerHour(Skill.HITPOINTS), 5, 120);
	}
}
