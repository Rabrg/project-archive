package me.rabrg.roguecooker.task;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.WidgetChild;

public class CookTask extends TaskNode {

    private long lastAnimation = 0;
    private Item rawFood;
    private WidgetChild cookWidget;

    @Override
    public boolean accept() {
        if (getLocalPlayer().isAnimating())
            lastAnimation = System.currentTimeMillis();
        return (System.currentTimeMillis() - lastAnimation > 2400 || getDialogues().canContinue())
                && (rawFood = getInventory().getRandom(new Filter<Item>() {
            @Override
            public boolean match(Item item) {
                return item.getName() != null && item.getName().startsWith("Raw ");
            }
        })) != null;
    }

    @Override
    public int execute() {
        final GameObject fire = getGameObjects().closest("Fire");
        if (fire != null && rawFood.useOn(fire)) {
            sleep(2400, 3600);
            if ((cookWidget = getWidgets().getChildWidget(307, 4)) != null && cookWidget.isVisible())
            cookWidget.interact();
        }
        cookWidget = null;
        return Calculations.random(1200);
    }
}
