package me.rabrg.wyvern;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import me.rabrg.wyvern.node.AttackWyvernNode;
import me.rabrg.wyvern.node.BankNode;
import me.rabrg.wyvern.node.DrinkSuperCombatNode;
import me.rabrg.wyvern.node.EatNode;
import me.rabrg.wyvern.node.EnterCaveNode;
import me.rabrg.wyvern.node.EnterTrapdoorNode;
import me.rabrg.wyvern.node.LootNode;
import me.rabrg.wyvern.node.Node;
import me.rabrg.wyvern.node.NodeController;
import me.rabrg.wyvern.node.TeleportHouseNode;
import me.rabrg.wyvern.node.TeleportVarrockNode;
import me.rabrg.wyvern.node.ToggleRunNode;
import me.rabrg.wyvern.node.WaitDeadNode;
import me.rabrg.wyvern.node.WaitingNode;
import me.rabrg.wyvern.node.WalkToBankNode;
import me.rabrg.wyvern.node.WalkToCaveNode;
import me.rabrg.wyvern.node.WalkToTrapdoorNode;

import org.tbot.internal.AbstractScript;
import org.tbot.internal.Manifest;
import org.tbot.internal.ScriptCategory;
import org.tbot.internal.event.listeners.PaintListener;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.wrappers.Area;
import org.tbot.wrappers.Tile;

@Manifest(authors = "Rabrg", name = "Wyvern", category = ScriptCategory.OTHER)
public final class WyvernScript extends AbstractScript implements PaintListener {

	private static WyvernScript instance;
	
	public static long lastKill = System.currentTimeMillis();
	
	public WyvernScript() {
		instance = this;
	}
	
	public static WyvernScript get() {
		return instance;
	}
	
	public static final Area TRAPDOOR_AREA = new Area(3005, 3144, 3013, 3154);
	public static final Area RIMMINGTON_AREA = new Area(2943, 3140, 3025, 3240);
	public static final Area CAVE_AREA = new Area(3048, 9560, 3063, 9567);
	public static final Area WYVERN_AREA = new Area(3023, 9530, 3075, 9560);
	public static final Area VARROCK_SQUARE_AREA = new Area(3174, 3402, 3229, 3441);
	public static final Area BANK_AREA = new Area(3250, 3419, 3257, 3423);

	public static final Tile TRAPDOOR_TILE = new Tile(3008, 3150);
	public static final Tile CAVE_AREA_TILE = new Tile(3055, 9565);
	public static final Tile CAVE_TILE = new Tile(3056, 9560);
	public static final Tile BANK_TILE = new Tile(3253, 3421);
	
	public static String[] LOOT;
	
	private final NodeController controller = new NodeController(new ToggleRunNode(), new WaitingNode(), new DrinkSuperCombatNode(), new EatNode(), new LootNode(), new TeleportVarrockNode(), new WalkToTrapdoorNode(), new EnterTrapdoorNode(), new WalkToCaveNode(), new EnterCaveNode(), new AttackWyvernNode(), new WalkToBankNode(), new BankNode(), new TeleportHouseNode(), new WaitDeadNode());
	
	private Node lastNode;
	private Node currentNode;
	
	@Override
	public boolean onStart() {
		final List<String> list = new ArrayList<String>();
		list.add("Rune battleaxe");
		list.add("Earth battlestaff");
		list.add("Rune warhammer");
		list.add("Rune kiteshield");
		list.add("Rune full helm");
		list.add("Rune axe");
		list.add("Dragon platelegs");
		list.add("Dragon plateskirt");
		list.add("Granite legs");
		list.add("Pure essence");
		list.add("Chaos rune");
		list.add("Law rune");
		list.add("Death rune");
		list.add("Blood rune");
		list.add("Soul rune");
		list.add("Adamant bolts");
		list.add("Runite bolts");
		list.add("Rune arrow");
		list.add("Ranarr seed");
		list.add("Snapdragon seed");
		list.add("Magic logs");
		list.add("Adamantite bar");
		list.add("Battlestaff");
		list.add("Unpowered orb");
		list.add("Iron ore");
		list.add("Uncut ruby");
		list.add("Uncut diamond");
		list.add("Runite crossbow (u)");
		list.add("Clue scroll (elite)");
		list.add("Draconic visage");
		list.add("Loop half of key");
		list.add("Tooth half of key");
		list.add("Nature talisman");
		list.add("Chaos talisman");
		list.add("Nature rune");
		list.add("Runite bar");
		list.add("Rune spear");
		list.add("Rune 2h sword");
		list.add("Silver ore");
		list.add("Rune sq shield");
		list.add("Dragonstone");
		list.add("Rune kiteshield");
		list.add("Dragon med helm");
		list.add("Shield left half");
		list.add("Dragon spear");
		list.add("Prayer potion(4)");
		list.add("Wyvern bones");
		LOOT = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			LOOT[i] = list.get(i);
		}
		return true;
	}
	
	@Override
	public int loop() {
		if (Players.getLocal() == null)
			log("PLAYER IS NULL");
		else {
			lastNode = currentNode;
			currentNode = controller.getCurrentNode();
			if (currentNode != null)
				return currentNode.execute();
		}
		return Random.nextInt(20, 75);
	}

	@Override
	public void onRepaint(final Graphics g) {
		g.drawString(currentNode != null ? currentNode.getName() : "null", 7, 75);
		if (!lastNode.getName().equals(currentNode.getName()))
			log(currentNode.getName());
	}

}
