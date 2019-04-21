package me.rabrg.fireorb;

import java.awt.Graphics;

import org.tbot.internal.AbstractScript;
import org.tbot.internal.Manifest;
import org.tbot.internal.event.events.InventoryEvent;
import org.tbot.internal.event.listeners.InventoryListener;
import org.tbot.internal.event.listeners.PaintListener;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Equipment;
import org.tbot.methods.walking.Walking;
import org.tbot.wrappers.Item;

import me.rabrg.fireorb.node.BankNode;
import me.rabrg.fireorb.node.ChargeOrbNode;
import me.rabrg.fireorb.node.ClanWarsNode;
import me.rabrg.fireorb.node.ClimbLadderNode;
import me.rabrg.fireorb.node.Node;
import me.rabrg.fireorb.node.StrangeFloorNode;

@Manifest(name = "Rabrg Fire Orb", authors = "Rabrg", version = 0.02)
public final class RabrgFireOrb extends AbstractScript implements PaintListener, InventoryListener {

	public static boolean hop;

	private static final int PROFIT = 1200;

	private final Node[] nodes = { new ClimbLadderNode(), new StrangeFloorNode(), new ChargeOrbNode(), new BankNode(), new ClanWarsNode() };

	private Node currentNode;

	private int fireOrbs;

	private long startTime = System.currentTimeMillis();

	private long getElapsed() {
		return System.currentTimeMillis() - startTime;
	}

	private int getPerHour(final double value) {
		if (getElapsed() > 0) {
			return (int) (value * 3600000d / getElapsed());
		} else {
			return 0;
		}
	}

	private long lastMove = System.currentTimeMillis();

	@Override
	public int loop() {
		if (Walking.getRunEnergy() > 15 && !Walking.isRunEnabled())
			Walking.setRun(true);
		
		if (Walking.isMoving() || Players.getLocal().getAnimation() > 0)
			lastMove = System.currentTimeMillis();
		
		if (System.currentTimeMillis() - lastMove > 60000 && !ChargeOrbNode.OBELISK_AREA.contains(Players.getLocal())) {
			lastMove = System.currentTimeMillis();
			final Item ring = Equipment.getItemInSlot(Equipment.SLOTS_RING);
			if (ring != null && ring.interact("Clan Wars")) {
				Time.sleep(5400, 60000);
				return 0;
			}
		}
		for (final Node node : nodes) {
			if (node.validate()) {
				currentNode = node;
				currentNode.execute();
				break;
			}
		}
		return Random.nextInt(0, 512);
	}

	@Override
	public void onRepaint(final Graphics g) {
		g.drawString("Time elapsed: " + (int)(getElapsed() / (1000*60*60)) + ":" + (int)((getElapsed() / (1000*60)) % 60) + ":" + (int)(getElapsed() / 1000) % 60, 15, 30);
		g.drawString("Fire orbs: " + fireOrbs, 15, 45);
		g.drawString("Gold: " + fireOrbs * PROFIT, 15, 60);
		g.drawString("Gold/h: " + getPerHour(fireOrbs * PROFIT), 15, 75);
	}

	@Override
	public void itemsAdded(final InventoryEvent event) {
		if (event.getItem().getName().equals("Fire orb")) {
			fireOrbs++;
		}
	}

	@Override
	public void itemsRemoved(final InventoryEvent event) {
		
	}

}
