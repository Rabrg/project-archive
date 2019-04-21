package me.rabrg.construction.node;

import me.rabrg.construction.Construction;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.widget.Widget;
import org.dreambot.api.script.TaskNode;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;

import java.awt.*;

public class BuildNode extends TaskNode {

    private static final Rectangle CHAIR_RECTANGLE = new Rectangle(37, 69, 28, 26);
    private static final Rectangle LARDER_RECTANGLE = new Rectangle(37, 176, 28, 30);

    private final Construction construction;

    private GameObject chairSpace;

    public BuildNode(final Construction construction) {
        this.construction = construction;
    }

    @Override
    public boolean accept() {
        return getInventory().count(construction.isOak() ? 8778 : 960)  >= (construction.isOak() ? 8 : 2)
                && (construction.isOak() || getInventory().count("Iron nails") >= 2)
                && ((chairSpace = getGameObjects().closest(construction.isOak() ? "Larder space" : "Chair space")) != null || construction.isOak() && (chairSpace = getGameObjects().closest("Larder")) != null);
    }

    @Override
    public int execute() {
        final Widget widget = getWidgets().getWidget(construction.isOak() ? 394 : 396);
        final GameObject chair = getGameObjects().closest(construction.isOak() ? "Larder" : "Chair");
        if (widget != null && widget.isVisible()) {
            if (getMouse().click(construction.isOak() ? LARDER_RECTANGLE : CHAIR_RECTANGLE)) {
                sleep(1600, 2400);
                construction.setDoneBuilding(false);
            }
        } else if (chair != null) {
            if (chair.interact("Remove")) {
                sleepUntil(new Condition() {
                    @Override
                    public boolean verify() {
                        sleep(150, 1200);
                        return getDialogues().chooseOption("Yes");
                    }
                }, Calculations.random(2400, 4800));
            }
        } else {
            if (chairSpace.interact("Build")) {
                sleepUntil(new Condition() {
                    @Override
                    public boolean verify() {
                        final Widget widget2 = getWidgets().getWidget(construction.isOak() ? 394 : 396);
                        return widget2 != null && widget2.isVisible();
                    }
                }, Calculations.random(2400, 4800));
            }
        }
        return Calculations.random(300, 1200);
    }
}
