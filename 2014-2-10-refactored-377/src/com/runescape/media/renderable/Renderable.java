package com.runescape.media.renderable;

import com.runescape.collection.CacheableNode;
import com.runescape.media.VertexNormal;

public class Renderable extends CacheableNode {
	
	public static byte aByte1472 = 3;
	public boolean aBoolean1473;
	public VertexNormal aClass40Array1474[];
	public int anInt1475;
	public static boolean aBoolean1476;
	
	public void method560(int i, int j, int k, int l, int i1, int j1, int k1, int l1, int i2) {
		Model class50_sub1_sub4_sub4 = method561(Renderable.aByte1472);
		if (class50_sub1_sub4_sub4 != null) {
			anInt1475 = ((Renderable) (class50_sub1_sub4_sub4)).anInt1475;
			class50_sub1_sub4_sub4.method560(i, j, k, l, i1, j1, k1, l1, i2);
		}
	}

	public Model method561(byte byte0) {
		if (byte0 != 3) {
			aBoolean1473 = !aBoolean1473;
		}
		return null;
	}

	public Renderable() {
		aBoolean1473 = true;
		anInt1475 = 1000;
	}
}
