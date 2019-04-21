package me.rabrg.lumbyrange;

import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.items.Item;
import org.dreambot.api.wrappers.widgets.WidgetChild;

@ScriptManifest(author = "Rabrg", category = Category.COOKING, name = "Lumby Range", version = 0.1)
public final class LumbyRange extends AbstractScript {

	private WidgetChild widget;

	@Override
	public int onLoop() {
		if (!getInventory().contains(RAW_ITEM_FILTER) && getLocalPlayer().getY() < 9000) {
			final GameObject ladder = getGameObjects().closest("Trapdoor");
			if (ladder.interact("Climb-down")) {
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return getLocalPlayer().getY() > 9000;
					}
				}, Calculations.random(7500, 15000));
			} else {
				getCamera().rotateTo(Calculations.random(0, 360), Calculations.random(0, 360));
			}
		} else if ((!getInventory().contains(RAW_ITEM_FILTER) && getLocalPlayer().getY() > 9000) || getBank().isOpen()) {
			if (!getBank().isOpen()) {
				final GameObject chest = getGameObjects().closest("Chest");
				if (chest.interact("Bank")) {
					sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return getBank().isOpen();
						}
					}, Calculations.random(7500, 15000));
				}
			} else if (!getInventory().isEmpty() && !getInventory().contains(RAW_ITEM_FILTER)) {
				if (getBank().depositAllItems()) {
					sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return getInventory().isEmpty();
						}
					}, Calculations.random(1200, 2400));
				}
			} else if (getInventory().isEmpty()) {
				if (getBank().withdrawAll(RAW_ITEM_FILTER)) {
					sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return !getInventory().isEmpty();
						}
					}, Calculations.random(1200, 2400));
				}
			} else {
				if (getBank().close()) {
					sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return !getBank().isOpen();
						}
					}, Calculations.random(1200, 2400));
				}
			}
		} else if (getInventory().contains(RAW_ITEM_FILTER) && getLocalPlayer().getY() > 9000) {
			final GameObject ladder = getGameObjects().closest("Ladder");
			if (ladder.interact("Climb-up")) {
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return getLocalPlayer().getY() < 9000;
					}
				}, Calculations.random(7500, 15000));
			}
		} else if (getLocalPlayer().getAnimation() < 1 && ((widget = getWidgets().getChildWidget(307, 6)) == null || !widget.isVisible())) {
			final GameObject range = getGameObjects().closest("Cooking range");
			final Item raw = getInventory().get(RAW_ITEM_FILTER);
			if (raw.interact("Use")) {
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return getInventory().isItemSelected();
					}
				}, Calculations.random(7500, 15000));
				if (range.interact("Use")) {
					sleepUntil(new Condition() {
						@Override
						public boolean verify() {
							return (widget = getWidgets().getChildWidget(307, 6)) != null && widget.isVisible();
						}
					}, Calculations.random(7500, 15000));
				}
			}
		} else if ((widget = getWidgets().getChildWidget(307, 6)) != null && widget.isVisible()) {
			if (widget.interact("Cook All")) {
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return getLocalPlayer().getAnimation() > 1;
					}
				}, Calculations.random(7500, 15000));
				sleepUntil(new Condition() {
					@Override
					public boolean verify() {
						return getLocalPlayer().getAnimation() > 1 || getDialogues().inDialogue();
					}
				}, Calculations.random(7500, 15000));
			}
		}
		return Calculations.random(0, 300);
	}

	private static final Filter<Item> RAW_ITEM_FILTER = new Filter<Item>() {
		@Override
		public boolean match(final Item item) {
			return item != null && item.getName() != null && item.getName().toLowerCase().startsWith("raw");
		}
	};
}
