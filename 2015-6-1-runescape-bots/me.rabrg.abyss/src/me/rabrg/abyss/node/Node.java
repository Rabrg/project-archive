package me.rabrg.abyss.node;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.filter.Filter;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.utilities.impl.Condition;
import org.dreambot.api.wrappers.items.Item;

import me.rabrg.abyss.RabrgAbyssPro;

public abstract class Node {

	static final Area EDGEVILLE_AREA = new Area(3077, 3483, 3123, 3520);
	static final Area WILDERNESS_AREA = new Area(3065, 3521, 3120, 3575);
	static final Area ABYSS_AREA = new Area(2950, 4750, 3100, 4900);
	static final Area ALTAR_AREA = new Area(2370, 4820, 2415, 4860);

	static final Filter<Item> STAMINA_POTION_FILTER = new Filter<Item>() {
		@Override
		public boolean match(final Item item) {
			return item != null && item.getName().startsWith("Stamina potion");
		}
	};

	final RabrgAbyssPro script;

	public Node(final RabrgAbyssPro script) {
		this.script = script;
	}

	public abstract boolean validate();

	public abstract void execute();

	public abstract String toString();

	public static void sleep(final int min, final int max) {
		MethodProvider.sleep(min, max);
	}

	public static void sleepUntil(final Condition condition, final int timeout) {
		MethodProvider.sleepUntil(condition, timeout);
	}

	public static void log(final Object msg) {
		MethodProvider.log(msg.toString());
	}
}
