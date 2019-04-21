package me.rabrg.chickenkiller.node;

import me.rabrg.chickenkiller.NoobSlayer;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.wrappers.items.Item;

public class EatNode extends TaskNode {

    private static final String[] NAMES = {"Salmon" };

    private final NoobSlayer noobSlayer;

    public EatNode(NoobSlayer noobSlayer) {
        this.noobSlayer = noobSlayer;
    }

    private Item food;

    @Override
    public boolean accept() {
        return getSkills().getBoostedLevels(Skill.HITPOINTS) < 20;
    }

    @Override
    public int execute() {
        getTabs().open(Tab.INVENTORY);
        food = getInventory().get(NAMES);
        if (food != null)
            food.interact("Eat");
        else if (!getLocalPlayer().isInCombat()) {
            getTabs().logout();
            noobSlayer.stop();
        }
        return 0;
    }
}
