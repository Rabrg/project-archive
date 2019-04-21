package com.runescape.collection;

public class Node {
	
	public long aLong833;
	public Node aClass50_834;
	public Node aClass50_835;
	public static boolean aBoolean836;
	
	public void method442() {
		if (aClass50_835 == null) {
			return;
		} else {
			aClass50_835.aClass50_834 = aClass50_834;
			aClass50_834.aClass50_835 = aClass50_835;
			aClass50_834 = null;
			aClass50_835 = null;
			return;
		}
	}
}
