package me.rabrg.saltpetre;

import me.rabrg.saltpetre.node.BankTaskNode;
import me.rabrg.saltpetre.node.MineTaskNode;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.impl.TaskScript;
import org.dreambot.api.script.listener.InventoryListener;
import org.dreambot.api.script.listener.PaintListener;
import org.dreambot.api.wrappers.items.Item;

import java.awt.*;

@ScriptManifest(category = Category.MONEYMAKING, name = "Saltpetre", author = "Rabrg", version = 1.0)
public class Saltpetre extends TaskScript implements InventoryListener, PaintListener {

    private int count;

    @Override
    public void onStart() {
        addNodes(new BankTaskNode(), new MineTaskNode());
    }

    @Override
    public void onItemChange(final Item[] items) {
        for (final Item item : items) {
            if (item != null && "Saltpetre".equals(item.getName()) && item.getAmount() > 0) {
                count += item.getAmount();
            }
        }
    }

    @Override
    public void onPaint(final Graphics g) {
        g.drawString(getLastTaskNode().getClass().getSimpleName(), 15, 50);
        g.drawString("Planks\t: " + count, 15, 65);
        g.drawString("Planks/h\t: " + getPerHour(count), 15, 80);
        g.drawString("Profit\t: " + count * 400, 15, 95);
        g.drawString("Profit/h\t: " + getPerHour(count * 400), 15, 110);
    }

    private long startTime = System.currentTimeMillis();

    private long getElapsed() {
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
