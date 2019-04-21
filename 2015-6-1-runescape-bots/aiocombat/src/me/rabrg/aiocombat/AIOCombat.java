package me.rabrg.aiocombat;

import me.rabrg.aiocombat.nodes.*;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.impl.TaskScript;
import org.dreambot.api.script.listener.MessageListener;
import org.dreambot.api.script.listener.PaintListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.wrappers.widgets.message.Message;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@ScriptManifest(category = Category.COMBAT, name = "Rabrg AIO Combat",
        description = "Remember to message Rabrg for more private scripts :)", author = "Rabrg", version = 1.0)
public final class AIOCombat extends TaskScript implements MessageListener, PaintListener {

    private AIOCombatGUI gui;

    final List<String> targets = new ArrayList<>();
    final List<String> drops = new ArrayList<>();
    String foodName;
    int foodAmount;
    int minEat;
    int maxEat;

    private Tile attackTile;

    private long start;
    @Override
    public void onStart() {
        final AIOCombat script = this;
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                gui = new AIOCombatGUI(script);
                gui.setVisible(true);
            }
        });
        while (gui == null || gui.isVisible()) {
            sleep(200, 250);
        }

        addNodes(new EatNode(this), new DropNode(this), new RunNode(), new BankNode(this), new AttackNode(this));

        attackTile = getLocalPlayer().getTile();

        getSkillTracker().start(true);
        start = System.currentTimeMillis();
    }

    @Override
    public void onPaint(final Graphics g) {
        g.drawString("Runtime: " + Timer.formatTime(System.currentTimeMillis() - start), 15, 45);
        g.drawString("Attack level (gained): " + getSkills().getRealLevel(Skill.ATTACK) + " (" + getSkillTracker().getGainedLevels(Skill.ATTACK) + ")", 15, 60);
        g.drawString("Attack experience gained (per hour): " + getSkillTracker().getGainedExperience(Skill.ATTACK) + " (" + getSkillTracker().getGainedExperiencePerHour(Skill.ATTACK) + ")", 15, 75);
        g.drawString("Strength level (gained): " + getSkills().getRealLevel(Skill.STRENGTH) + " (" + getSkillTracker().getGainedLevels(Skill.STRENGTH) + ")", 15, 90);
        g.drawString("Strength experience gained (per hour): " + getSkillTracker().getGainedExperience(Skill.STRENGTH) + " (" + getSkillTracker().getGainedExperiencePerHour(Skill.STRENGTH) + ")", 15, 105);
        g.drawString("Defence level (gained): " + getSkills().getRealLevel(Skill.DEFENCE) + " (" + getSkillTracker().getGainedLevels(Skill.DEFENCE) + ")", 15, 120);
        g.drawString("Defence experience gained (per hour): " + getSkillTracker().getGainedExperience(Skill.DEFENCE) + " (" + getSkillTracker().getGainedExperiencePerHour(Skill.DEFENCE) + ")", 15, 135);
        g.drawString("Hitpoints level (gained): " + getSkills().getRealLevel(Skill.HITPOINTS) + " (" + getSkillTracker().getGainedLevels(Skill.HITPOINTS) + ")", 15, 150);
        g.drawString("Hitpoints experience gained (per hour): " + getSkillTracker().getGainedExperience(Skill.HITPOINTS) + " (" + getSkillTracker().getGainedExperiencePerHour(Skill.HITPOINTS) + ")", 15, 165);
    }

    @Override
    public void onGameMessage(Message message) {

    }

    @Override
    public void onPlayerMessage(final Message message) {
        final Player player = getPlayers().closest(message.getUsername());
        if (player != null && player.distance() < 7 && Calculations.random(3) == 2)
            getKeyboard().type("fuck you");
    }

    @Override
    public void onTradeMessage(Message message) {

    }

    @Override
    public void onPrivateInMessage(Message message) {

    }

    @Override
    public void onPrivateOutMessage(Message message) {

    }

    public List<String> getTargets() {
        return targets;
    }

    public List<String> getDrops() {
        return drops;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getFoodAmount() {
        return foodAmount;
    }

    public int getMinEat() {
        return minEat;
    }

    public int getMaxEat() {
        return maxEat;
    }

    public Tile getAttackTile() {
        return attackTile;
    }
}
