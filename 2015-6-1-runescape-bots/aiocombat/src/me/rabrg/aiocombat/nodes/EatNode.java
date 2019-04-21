package me.rabrg.aiocombat.nodes;

import me.rabrg.aiocombat.AIOCombat;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.methods.tabs.Tab;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.Item;

public final class EatNode extends TaskNode {

    private final AIOCombat script;

    private int eatHitpoints = -1;

    public EatNode(final AIOCombat script) {
        this.script = script;
    }

    @Override
    public boolean accept() {
        if (eatHitpoints == -1)
            resetEatHitpoints();
        return getInventory().contains(script.getFoodName())
                && getSkills().getBoostedLevels(Skill.HITPOINTS) <= eatHitpoints;
    }

    @Override
    public int execute() {
        final Item food = getInventory().getRandom(script.getFoodName());
        if (food != null && food.interact("Eat")) {
            if (getBank().isOpen())
                getBank().close();
            if (!getTabs().isOpen(Tab.INVENTORY))
                getTabs().open(Tab.INVENTORY);
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getSkills().getBoostedLevels(Skill.HITPOINTS) > eatHitpoints;
                }
            }, Calculations.random(1200, 1800));
            resetEatHitpoints();
        }
        return Calculations.random(0, 300);
    }

    private void resetEatHitpoints() {
        eatHitpoints = Calculations.random(script.getMinEat(), script.getMaxEat());
    }

}
