package com.runescape.media.renderable;

import com.runescape.cache.def.ItemDefinition;

public class Item extends Renderable {
	
	public int anInt1550;
	public int anInt1551;
	public int anInt1552;
	
	@Override
	public Model method561(byte byte0) {
		if (byte0 != 3) {
			anInt1551 = -358;
		}
		ItemDefinition class16 = ItemDefinition.method212(anInt1550);
		return class16.method220(anInt1552);
	}
}
