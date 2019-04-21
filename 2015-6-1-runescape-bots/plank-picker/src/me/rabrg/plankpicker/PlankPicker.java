package me.rabrg.plankpicker;

import me.rabrg.plankpicker.node.BankPlankTaskNode;
import me.rabrg.plankpicker.node.GatherPlankTaskNode;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.impl.TaskScript;
import org.dreambot.api.script.listener.PaintListener;

import java.awt.*;

@ScriptManifest(category = Category.MONEYMAKING, name = "Plank Picker", author = "Rabrg", version = 0.1)
public class PlankPicker extends TaskScript implements PaintListener {

    public static final Area PLANK_AREA = new Area(2548, 3573, 2556, 3578);
    public static final Area BANK_AREA = new Area(2531, 3570, 2537, 3577);

    public static int planks;

    @Override
    public void onStart() {
        addNodes(new GatherPlankTaskNode(), new BankPlankTaskNode());
    }

    @Override
    public void onPaint(final Graphics g) {
        g.drawString(getLastTaskNode().getClass().getSimpleName(), 15, 50);
        g.drawString("Planks\t: " + planks, 15, 65);
        g.drawString("Planks/h\t: " + getPerHour(planks), 15, 80);
        g.drawString("Profit\t: " + planks * 428, 15, 95);
        g.drawString("Profit/h\t: " + getPerHour(planks * 428), 15, 110);
    }

    private long startTime = System.currentTimeMillis();

    public long getElapsed() {
        return System.currentTimeMillis() - startTime;
    }

    private int getPerHour(double value) {
        if (getElapsed() > 0) {
            return (int) (value * 3600000d / getElapsed());
        } else {
            return 0;
        }
    }
}
