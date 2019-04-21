package com.runescape.scene.util;

public class TileUtilities {
	
	public static int anInt600;
	public static boolean aBoolean601 = true;
	public static boolean aBoolean602 = true;
	
	public static int method348(byte byte0, int i, int j, int k) {
		if (byte0 == 7) {
			byte0 = 0;
		} else {
			TileUtilities.anInt600 = 486;
		}
		i &= 3;
		if (i == 0) {
			return j;
		}
		if (i == 1) {
			return k;
		}
		if (i == 2) {
			return 7 - j;
		} else {
			return 7 - k;
		}
	}

	public static int method349(int i, int j, int k, byte byte0) {
		if (byte0 == 5) {
			byte0 = 0;
		} else {
			TileUtilities.aBoolean601 = !TileUtilities.aBoolean601;
		}
		k &= 3;
		if (k == 0) {
			return i;
		}
		if (k == 1) {
			return 7 - j;
		}
		if (k == 2) {
			return 7 - i;
		} else {
			return j;
		}
	}

	public static int method350(int i, int j, int k, int l, byte byte0, int i1, int j1) {
		if (byte0 != -117) {
			TileUtilities.aBoolean602 = !TileUtilities.aBoolean602;
		}
		if ((k & 1) == 1) {
			int k1 = i1;
			i1 = j;
			j = k1;
		}
		i &= 3;
		if (i == 0) {
			return l;
		}
		if (i == 1) {
			return j1;
		}
		if (i == 2) {
			return 7 - l - (i1 - 1);
		} else {
			return 7 - j1 - (j - 1);
		}
	}

	public static int method351(int i, int j, int k, int l, int i1, int j1, int k1) {
		if (k <= 0) {
			TileUtilities.anInt600 = -50;
		}
		if ((k1 & 1) == 1) {
			int l1 = i;
			i = j1;
			j1 = l1;
		}
		j &= 3;
		if (j == 0) {
			return i1;
		}
		if (j == 1) {
			return 7 - l - (i - 1);
		}
		if (j == 2) {
			return 7 - i1 - (j1 - 1);
		} else {
			return l;
		}
	}
}
