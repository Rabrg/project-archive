package me.rabrg.constructor.nodes;

import me.rabrg.constructor.enums.ConstructionType;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.widget.Widget;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.Item;

public final class BuildTaskNode extends TaskNode {

    private static final String NAILS = " nails";
    private static final String REMOVE = "Remove";
    private static final String BUILD = "Build";
    private static final String YES = "Yes";

    private static final Filter<Item> NAIL_FILTER = new Filter<Item>() {
        @Override
        public boolean match(final Item item) {
            return item != null && item.getName() != null && item.getName().contains(NAILS);
        }
    };

    private final ConstructionType type;

    private GameObject spaceObject;
    private GameObject builtObject;
    private Widget widget;

    public BuildTaskNode(final ConstructionType type) {
        this.type = type;
    }

    @Override
    public boolean accept() {
        return spaceObject != null && spaceObject.exists()  // Remove space object
                || getInventory().count(type.getPlank()) >= type.getPlankAmount() // Has required planks
                && getInventory().count(NAIL_FILTER) >= type.getNailAmount() // Has required nails
                && (spaceObject = getGameObjects().closest(type.getSpaceObjectName())) != null; // Space is open
    }

    @Override
    public int execute() {
        if (builtObject != null && builtObject.exists() && builtObject.interact(REMOVE)) { // Remove space object
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return getDialogues().chooseOption(YES);
                }
            }, Calculations.random(4800, 7200));
            if (builtObject != null && !builtObject.exists())
                builtObject = null;
        } else if (widget != null && widget.isVisible()) {
            if (getMouse().click(type.getClickRectangle())) {
                sleepUntil(new Condition() {
                    @Override
                    public boolean verify() {
                        return (builtObject = getGameObjects().getTopObjectOnTile(spaceObject.getTile())) != null
                                && builtObject.equals(spaceObject);
                    }
                }, Calculations.random(14400, 28800));
                if (builtObject != null && !builtObject.hasAction(REMOVE))
                    builtObject = null;
            }
        } else if (spaceObject != null && spaceObject.interact(BUILD)) { // Build object
            sleepUntil(new Condition() {
                @Override
                public boolean verify() {
                    return (widget = getWidgets().getWidget(type.getWidgetId())) != null && widget.isVisible();
                }
            }, Calculations.random(4800, 7200));
        }
        return Calculations.random(150, 1200);
    }
}
