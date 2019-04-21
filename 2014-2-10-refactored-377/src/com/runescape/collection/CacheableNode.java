package com.runescape.collection;

public class CacheableNode extends Node {
	
	public CacheableNode aClass50_Sub1_1381;
	public CacheableNode aClass50_Sub1_1382;
	public static int anInt1383;
	
	public void method443() {
		if (aClass50_Sub1_1382 == null) {
			return;
		} else {
			aClass50_Sub1_1382.aClass50_Sub1_1381 = aClass50_Sub1_1381;
			aClass50_Sub1_1381.aClass50_Sub1_1382 = aClass50_Sub1_1382;
			aClass50_Sub1_1381 = null;
			aClass50_Sub1_1382 = null;
			return;
		}
	}
}
