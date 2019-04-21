package me.rabrg.nature;

import java.awt.Graphics;

import org.tbot.internal.AbstractScript;
import org.tbot.internal.Manifest;
import org.tbot.internal.event.events.MessageEvent;
import org.tbot.internal.event.listeners.MessageListener;
import org.tbot.internal.event.listeners.PaintListener;
import org.tbot.methods.Players;
import org.tbot.methods.Widgets;
import org.tbot.methods.tabs.Inventory;
import org.tbot.methods.walking.Walking;
import org.tbot.wrappers.Widget;

import me.rabrg.nature.node.Node;
import me.rabrg.nature.node.NodeController;
import me.rabrg.nature.node.abyss.EnterEntranceNode;
import me.rabrg.nature.node.abyss.EnterRiftNode;
import me.rabrg.nature.node.abyss.RepairDarkMageNode;
import me.rabrg.nature.node.abyss.WalkDarkMageNode;
import me.rabrg.nature.node.abyss.WalkEntranceNode;
import me.rabrg.nature.node.abyss.WalkRiftNode;
import me.rabrg.nature.node.altar.CraftRuneNode;
import me.rabrg.nature.node.altar.TeleportBankNode;
import me.rabrg.nature.node.bank.BankNode;
import me.rabrg.nature.node.mage.JumpWildernessDitchNode;
import me.rabrg.nature.node.mage.TeleportMageNode;
import me.rabrg.nature.node.mage.WalkMageNode;
import me.rabrg.nature.node.mage.WalkWildernessDitchNode;
import me.rabrg.nature.node.misc.DeadNode;
import me.rabrg.nature.node.misc.FailSafeNode;
import me.rabrg.nature.node.misc.UnstuckNode;
import me.rabrg.nature.util.TextSender;
import me.rabrg.nature.node.misc.ToggleRunNode;

@Manifest(name = "Rabrg Nature")
public final class RabrgNature extends AbstractScript implements PaintListener, MessageListener {

	public static int hoppedWorlds = 0;

	public static final TextSender SENDER = new TextSender("rabrgtextalert@gmail.com", "RabrgScripts");

	private static final int NAT_COST = 250;
	public static int crafted = 0;
	public static int sips = 0;

	private long startTime = System.currentTimeMillis();
	
	boolean teleblocked;
	
	public long getElapsed() {
		return System.currentTimeMillis() - startTime;
	}

	private final NodeController nodeController = new NodeController(new DeadNode(), new UnstuckNode(), new FailSafeNode(), new ToggleRunNode(), new BankNode(), new WalkWildernessDitchNode(), new JumpWildernessDitchNode(), new WalkMageNode(),new TeleportMageNode(), new EnterEntranceNode(), new WalkEntranceNode(), new RepairDarkMageNode(), new WalkDarkMageNode(),
			new WalkRiftNode(), new EnterRiftNode(), new CraftRuneNode(), new TeleportBankNode());

	private Node node;

	@Override
	public boolean onStart() {
		Node.giantPouch = Inventory.contains("Giant pouch");
		Node.largePouch = Inventory.contains("Large pouch");
		Node.mediumPouch = Inventory.contains("Medium pouch");
		Node.smallPouch = Inventory.contains("Small pouch");
		return true;
	}

	private Widget widget;

	@Override
	public int loop() {
		if ((widget = Widgets.getWidget(310)) != null && widget.isValid()) {
			Walking.walkTileMM(Players.getLocal().getLocation());
			return 1500;
		}
		if ((node = nodeController.get()) != null) {
			log(node != null ? "Node: " + node.getName() : "Node: null");
			return node.execute();
		}
		return 75;
	}

	public int getPerHour(double value) {
		if (getElapsed() > 0) {
			return (int) (value * 3600000d / getElapsed());
		} else {
			return 0;
		}
	}

	@Override
	public void onRepaint(final Graphics g) {
		g.drawString(node != null ? "Node: " + node.getName() : "Node: null", 360, 20);
		g.drawString("Time elapsed: " + (int)(getElapsed() / (1000*60*60)) + ":" + (int)((getElapsed() / (1000*60)) % 60) + ":" + (int)(getElapsed() / 1000) % 60, 360, 35);
		g.drawString("Profit: " + ((crafted * NAT_COST) - (sips * 1500)), 360, 50);
		g.drawString("Profit per hour: " + getPerHour(((crafted * NAT_COST) - (sips * 1500))), 360, 65);
		g.drawString("Runes crafted: " + crafted, 360, 80);
		g.drawString("Runes crafted per hour: " + getPerHour(crafted), 360, 95);
		g.drawString("Hopped worlds: " + hoppedWorlds, 360, 110);
	}

	@Override
	public void messageReceived(final MessageEvent event) {
		if ((event.getMessage().getType() == MessageEvent.CLIENT || event.getMessage().getType() == MessageEvent.SERVER) && event.getMessage().getText().contains("teleblock")) {
			teleblocked = true;
		}
	}
}
