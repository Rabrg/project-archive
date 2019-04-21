package me.rabrg.fireorb.node;

import org.tbot.internal.handlers.LogHandler;
import org.tbot.methods.Bank;
import org.tbot.methods.Game;
import org.tbot.methods.GameObjects;
import org.tbot.methods.Players;
import org.tbot.methods.Random;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Equipment;
import org.tbot.methods.tabs.Inventory;
import org.tbot.methods.walking.Path;
import org.tbot.methods.walking.Walking;
import org.tbot.util.Condition;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.Item;
import org.tbot.wrappers.Player;
import org.tbot.wrappers.Tile;

import me.rabrg.fireorb.RabrgFireOrb;

public final class BankNode extends Node {

	private static final Tile BANK_TILE = new Tile(3369, 3170);

	private static final String BANK_CHEST = "Bank chest";

	private Path path;
	private GameObject bank;
	private Item ring;

	@Override
	public boolean validate() {
		return Inventory.contains(FIRE_ORB) || Inventory.contains(RING_OF_DUELING_MAX) || (Inventory.getFirst(ANTIPOISON_FILTER) == null && Inventory.getFirst(VIAL) != null) || !Inventory.isFull() || Bank.isOpen();
	}

	@Override
	public void execute() {
		LogHandler.log("1");
		if (!Bank.isOpen() && BANK_TILE.distance() < 50 && BANK_TILE.distance() > 6) {
			LogHandler.log("2");
			if (path == null) {
				path = Walking.findLocalPath(BANK_TILE);
			}
			if (path != null && path.traverse()) {
				Time.sleep(0, 1800);
			} else {
				path = Walking.findLocalPath(BANK_TILE);
			}
		} else if (!Bank.isOpen() && !Inventory.contains(RING_OF_DUELING_MAX) && BANK_TILE.distance() <= 6 && (bank = GameObjects.getNearest(BANK_CHEST)) != null && bank.interact("Use")) {
			LogHandler.log("3");
			path = null;
			Time.sleepUntil(new Condition() {
				@Override
				public boolean check() {
					return Bank.isOpen();
				}
			}, Random.nextInt(4800, 6000));
		} else if (Bank.isOpen()) {
			LogHandler.log("4");
			if (Inventory.contains(VIAL) && Bank.depositAll(VIAL)) {
				Time.sleepUntil(new Condition() {
					@Override
					public boolean check() {
						return !Inventory.contains(VIAL);
					}
				}, Random.nextInt(600, 1200));
			} else if (Inventory.contains(FIRE_ORB) && Bank.depositAll(FIRE_ORB)) {
				Time.sleepUntil(new Condition() {
					@Override
					public boolean check() {
						return !Inventory.contains(FIRE_ORB);
					}
				}, Random.nextInt(600, 1200));
			} else if (Inventory.getFirst(ANTIPOISON_FILTER) == null && Bank.withdraw(ANTIPOISON_MAX, 1)) {
				Time.sleepUntil(new Condition() {
					@Override
					public boolean check() {
						return Inventory.contains(ANTIPOISON_MAX);
					}
				}, Random.nextInt(600, 1200));
			} else if (Equipment.getItemInSlot(Equipment.SLOTS_RING) == null && !Inventory.contains(RING_OF_DUELING_MAX) && Bank.withdraw(RING_OF_DUELING_MAX, 1)) {
				Time.sleepUntil(new Condition() {
					@Override
					public boolean check() {
						return Inventory.contains(RING_OF_DUELING_MAX);
					}
				}, Random.nextInt(2400, 3600));
			} else if (Inventory.getEmptySlots() > 0 && Bank.withdrawAll(UNPOWERED_ORB)) {
				Time.sleepUntil(new Condition() {
					@Override
					public boolean check() {
						return Inventory.contains(UNPOWERED_ORB);
					}
				}, Random.nextInt(600, 1200));
			} else {
				if (RabrgFireOrb.hop == true) {
					RabrgFireOrb.hop = false;
					Game.instaHopRandomP2P();
				} else {
					Bank.close();
				}
			}
		} else if ((ring = Inventory.getFirst(RING_OF_DUELING_MAX)) != null && ring.interact("Wear")) {
			LogHandler.log("5");
			Time.sleepUntil(new Condition() {
				@Override
				public boolean check() {
					return !Inventory.contains(RING_OF_DUELING_MAX);
				}
			}, Random.nextInt(600, 1200));
		} else if (BANK_TILE.distance() > 50 && (ring = Equipment.getItemInSlot(Equipment.SLOTS_RING)) != null && ring.interact("Clan Wars")) {
			for (final Player player : Players.getLoaded()) {
				if (!player.getName().equals(Players.getLocal().getName()) && player.distance() < 25) {
					RabrgFireOrb.hop = true;
					break;
				}
			}
			LogHandler.log("6");
			Time.sleepUntil(new Condition() {
				@Override
				public boolean check() {
					return (bank = GameObjects.getNearest(BANK_CHEST)) != null;
				}
			}, Random.nextInt(5400, 6000));
			Time.sleep(300, 900);
		}
	}

	@Override
	public String toString() {
		return "Banking";
	}

}
