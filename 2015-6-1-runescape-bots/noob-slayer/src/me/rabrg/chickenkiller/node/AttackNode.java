package me.rabrg.chickenkiller.node;

import me.rabrg.chickenkiller.util.CharacterDistanceComparator;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.wrappers.interactive.NPC;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AttackNode extends TaskNode {

    private static final List<String> NAMES = Arrays.asList("Cow calf", "Cow", "Chicken", "Seagull", "Barbarian",
            "Gunthor the brave", "Big frog", "Giant frog");

    private final CharacterDistanceComparator characterDistanceComparator = new CharacterDistanceComparator();

    private List<NPC> targets;

    @Override
    public boolean accept() {
        return (!getLocalPlayer().isInCombat() && !getLocalPlayer().isMoving() || getDialogues().continueDialogue())
                && (targets = getNpcs().all(npc -> npc.getName() != null && NAMES.contains(npc.getName())
                && npc.hasAction("Attack")
                && (!npc.isInCombat() || npc.getInteractingCharacter().getName().equals(getLocalPlayer().getName()))
                && npc.getHealth() > 0 && getMap().canReach(npc)))
                != null && targets.size() > 0;
    }

    @Override
    public int execute() {
        Collections.sort(targets, characterDistanceComparator);
        final NPC target = targets.get(0);
//        NPC target;
//        if (targets.size() <= 3)
//            target = targets.get(Calculations.random(targets.size()));
//        else {
//            Collections.sort(targets, characterDistanceComparator);
//            target = targets.get(Calculations.random(3));
//        }
        if (target.interact("Attack"))
            sleepUntil(() -> getLocalPlayer().isInCombat(), Calculations.random(1800) + Calculations.random(600));
        return Calculations.random(900) + Calculations.random(600);
    }
}
