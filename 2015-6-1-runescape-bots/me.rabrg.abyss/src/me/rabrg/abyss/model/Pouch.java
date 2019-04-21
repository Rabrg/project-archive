package me.rabrg.abyss.model;

import me.rabrg.abyss.RabrgAbyssPro;

public final class Pouch {

	private final RabrgAbyssPro script;

	private final int capacity;
	private final boolean using;

	public Pouch(final RabrgAbyssPro script, final int capacity, final boolean using) {
		this.script = script;
		
		this.capacity = capacity;
		this.using = using;
	}

	public int getCapacity() {
		return capacity;
	}

	public boolean isUsing() {
		return using;
	}

	public boolean isFull() {
		final int config = script.getPlayerSettings().getConfig(720);
		switch (capacity) {
		case 3:
			return (config & 0x2) == 0x2;
		case 6:
			return (config & 0x08) == 0x8;
		case 9:
			return (config & 0x20) == 0x20;
		case 12:
			return (config & 0x80) == 0x80;
		}
		return false;
	}

}
