package me.rabrg.abyss.node;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.equipment.EquipmentSlot;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.widget.Widget;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.Item;

import me.rabrg.abyss.RabrgAbyssPro;

public final class WildernessDitchNode extends Node {

	private static final Area WILDERNESS_DITCH_LEFT = new Area(3085, 3521, 3092, 3522);
	private static final Area WILDERNESS_DITCH_RIGHT = new Area(3101, 3521, 3108, 3522);

	private Item amulet;

	private Tile wildernessDitchTile;
	private GameObject wildernessDitch;
	private Widget warningWidget;

	public WildernessDitchNode(final RabrgAbyssPro script) {
		super(script);
	}

	@Override
	public boolean validate() {
		return EDGEVILLE_AREA.contains(script.getLocalPlayer()) && (!script.getInventory().contains(script.FOOD_NAME) && !script.getInventory().contains(STAMINA_POTION_FILTER) && (amulet = script.getEquipment().getItemInSlot(EquipmentSlot.AMULET.getSlot())) != null && !amulet.getName().equals("Amulet of glory")) && script.getInventory().contains("Pure essence") && script.getInventory().isFull() && ((!script.getSmallPouch().isUsing() || script.getSmallPouch().isFull()) && (!script.getMediumPouch().isUsing() || script.getMediumPouch().isFull()) && (!script.getLargePouch().isUsing() || script.getLargePouch().isFull()) && (!script.getGiantPouch().isUsing() || script.getGiantPouch().isFull()));
	}

	@Override
	public void execute() {
		if (wildernessDitch == null) {
			resetDitch();
		} else {
			if ((warningWidget = script.getWidgets().getWidget(382)) != null && warningWidget.isVisible() && warningWidget.getChild(27).interact("Off/On") && warningWidget.getChild(24).interact("Ok")) {
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						sleep(5, 50);
						return (warningWidget = script.getWidgets().getWidget(382)) == null || !warningWidget.isVisible();
					}
				}, Calculations.random(1200, 1800));
			} else if (wildernessDitch.distance() > 10 && script.getWalking().walk(wildernessDitch)) {
				sleep(600, 1800);
			} else if (wildernessDitch.interact("Cross")) {
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						sleep(5, 50);
						return !EDGEVILLE_AREA.contains(script.getLocalPlayer()) || ((warningWidget = script.getWidgets().getWidget(382)) != null && warningWidget.isVisible());
					}
				}, Calculations.random(4800, 5600));
				if (!EDGEVILLE_AREA.contains(script.getLocalPlayer())) {
					wildernessDitch = null;
				}
			} else {
				resetDitch();
			}
		}
	}

	private void resetDitch() {
		wildernessDitchTile = Calculations.getRandom().nextBoolean() ? WILDERNESS_DITCH_LEFT.getRandomTile() : WILDERNESS_DITCH_RIGHT.getRandomTile();
		wildernessDitch = script.getGameObjects().closest(WILDERNESS_DITCH_FILTER);
	}
	@Override
	public String toString() {
		return "Wilderness ditch";
	}

	private final Filter<GameObject> WILDERNESS_DITCH_FILTER = new Filter<GameObject>() {
		@Override
		public boolean match(final GameObject object) {
			return object != null && object.getTile().equals(wildernessDitchTile) && "Wilderness Ditch".equals(object.getName());
		}
	};
}
