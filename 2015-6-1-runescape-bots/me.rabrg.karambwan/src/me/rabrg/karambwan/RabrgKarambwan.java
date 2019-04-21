package me.rabrg.karambwan;

import java.awt.Graphics;

import org.tbot.internal.AbstractScript;
import org.tbot.internal.Manifest;
import org.tbot.internal.ScriptCategory;
import org.tbot.internal.event.listeners.PaintListener;
import org.tbot.methods.Random;

import me.rabrg.karambwan.node.Node;
import me.rabrg.karambwan.node.NodeController;
import me.rabrg.karambwan.node.bank.BankNode;
import me.rabrg.karambwan.node.bank.CloseBankNode;
import me.rabrg.karambwan.node.bank.TeleportBankNode;
import me.rabrg.karambwan.node.house.EnterHousePortalNode;
import me.rabrg.karambwan.node.house.TeleportHouseNode;
import me.rabrg.karambwan.node.misc.FailSafeNode;
import me.rabrg.karambwan.node.misc.ToggleRunNode;
import me.rabrg.karambwan.node.shop.OpenShopDoorNode;
import me.rabrg.karambwan.node.shop.ShopNode;
import me.rabrg.karambwan.node.shop.WalkToShopNode;

@Manifest(authors = "Rabrg", name = "RabrgKarambwan", category = ScriptCategory.MONEY_MAKING)
public class RabrgKarambwan extends AbstractScript implements PaintListener {

	private final long startTime = System.currentTimeMillis();

	public long getElapsed() {
		return System.currentTimeMillis() - startTime;
	}

	private static final int KARAMBWAN_PRICE = 701;
	public static int karambwans;

	private final NodeController nodeController = new NodeController(new ToggleRunNode(), new FailSafeNode(),
			new BankNode(), new CloseBankNode(), new TeleportHouseNode(), new EnterHousePortalNode(),
			new WalkToShopNode(), new ShopNode(), new TeleportBankNode(), new OpenShopDoorNode());

	private Node currentNode;

	@Override
	public int loop() {
		if ((currentNode = nodeController.get()) != null) {
			return currentNode.execute();
		}
		return Random.nextInt(25, 125);
	}

	public int getPerHour(final int value) {
		if (getElapsed() > 0) {
			return (int) (value * 3600000d / getElapsed());
		} else {
			return 0;
		}
	}

	@Override
	public void onRepaint(final Graphics g) {
		g.drawString(currentNode != null ? "Node: " + currentNode.getName() : "Node: null", 360, 20);
		g.drawString("Time elapsed: " + (int) (getElapsed() / (1000 * 60 * 60)) + ":"
				+ (int) (getElapsed() / (1000 * 60) % 60) + ":" + (int) (getElapsed() / 1000) % 60, 360, 35);
		g.drawString("Karambwans: " + karambwans, 360, 50);
		g.drawString("Karambwans per hour: " + getPerHour(karambwans), 360, 65);
		g.drawString("Coins spent: " + 120 * karambwans, 360, 80);
		g.drawString("Profit: " + (karambwans * KARAMBWAN_PRICE - 120 * karambwans), 360, 95);
		g.drawString("Profit per hour: " + getPerHour(karambwans * KARAMBWAN_PRICE - 118 * karambwans), 360, 110);
		g.fillRect(10, 463, 65, 12);
	}

}
