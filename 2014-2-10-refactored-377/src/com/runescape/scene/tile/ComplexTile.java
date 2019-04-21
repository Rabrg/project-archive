package com.runescape.scene.tile;

public class ComplexTile {
	
	public int anInt402;
	public int anIntArray403[];
	public int anIntArray404[];
	public int anIntArray405[];
	public int anIntArray406[];
	public int anIntArray407[];
	public int anIntArray408[];
	public int anIntArray409[];
	public int anIntArray410[];
	public int anIntArray411[];
	public int anIntArray412[];
	public boolean aBoolean413;
	public int anInt414;
	public int anInt415;
	public int anInt416;
	public int anInt417;
	public static int anIntArray418[] = new int[6];
	public static int anIntArray419[] = new int[6];
	public static int anIntArray420[] = new int[6];
	public static int anIntArray421[] = new int[6];
	public static int anIntArray422[] = new int[6];
	public static int anIntArray423[] = { 1, 0 };
	public static int anIntArray424[] = { 2, 1 };
	public static int anIntArray425[] = { 3, 3 };
	public static final int anIntArrayArray426[][] = { { 1, 3, 5, 7 }, { 1, 3, 5, 7 }, { 1, 3, 5, 7 },
			{ 1, 3, 5, 7, 6 }, { 1, 3, 5, 7, 6 }, { 1, 3, 5, 7, 6 }, { 1, 3, 5, 7, 6 }, { 1, 3, 5, 7, 2, 6 },
			{ 1, 3, 5, 7, 2, 8 }, { 1, 3, 5, 7, 2, 8 }, { 1, 3, 5, 7, 11, 12 }, { 1, 3, 5, 7, 11, 12 },
			{ 1, 3, 5, 7, 13, 14 } };
	public static final int anIntArrayArray427[][] = { { 0, 1, 2, 3, 0, 0, 1, 3 }, { 1, 1, 2, 3, 1, 0, 1, 3 },
			{ 0, 1, 2, 3, 1, 0, 1, 3 }, { 0, 0, 1, 2, 0, 0, 2, 4, 1, 0, 4, 3 }, { 0, 0, 1, 4, 0, 0, 4, 3, 1, 1, 2, 4 },
			{ 0, 0, 4, 3, 1, 0, 1, 2, 1, 0, 2, 4 }, { 0, 1, 2, 4, 1, 0, 1, 4, 1, 0, 4, 3 },
			{ 0, 4, 1, 2, 0, 4, 2, 5, 1, 0, 4, 5, 1, 0, 5, 3 }, { 0, 4, 1, 2, 0, 4, 2, 3, 0, 4, 3, 5, 1, 0, 4, 5 },
			{ 0, 0, 4, 5, 1, 4, 1, 2, 1, 4, 2, 3, 1, 4, 3, 5 },
			{ 0, 0, 1, 5, 0, 1, 4, 5, 0, 1, 2, 4, 1, 0, 5, 3, 1, 5, 4, 3, 1, 4, 2, 3 },
			{ 1, 0, 1, 5, 1, 1, 4, 5, 1, 1, 2, 4, 0, 0, 5, 3, 0, 5, 4, 3, 0, 4, 2, 3 },
			{ 1, 0, 5, 4, 1, 0, 1, 5, 0, 0, 4, 3, 0, 4, 5, 3, 0, 5, 2, 3, 0, 1, 2, 5 } };
	
	public ComplexTile(int i, int j, int k, int l, int i1, int j1, int k1, int l1, int i2, int j2, int k2, int l2,
			int i3, int j3, int k3, int l3, int i4, int j4, int k4, int l4) {
		aBoolean413 = true;
		if (l != j3 || l != k || l != i) {
			aBoolean413 = false;
		}
		anInt414 = i3;
		anInt415 = l4;
		anInt416 = i4;
		anInt417 = l1;
		char c = '\200';
		int i5 = c / 2;
		int j5 = c / 4;
		int k5 = (c * 3) / 4;
		if (k2 < anInt402 || k2 > anInt402) {
			throw new NullPointerException();
		}
		int ai[] = ComplexTile.anIntArrayArray426[i3];
		int l5 = ai.length;
		anIntArray403 = new int[l5];
		anIntArray404 = new int[l5];
		anIntArray405 = new int[l5];
		int ai1[] = new int[l5];
		int ai2[] = new int[l5];
		int i6 = i1 * c;
		int j6 = k4 * c;
		for (int k6 = 0; k6 < l5; k6++) {
			int l6 = ai[k6];
			if ((l6 & 1) == 0 && l6 <= 8) {
				l6 = (l6 - l4 - l4 - 1 & 7) + 1;
			}
			if (l6 > 8 && l6 <= 12) {
				l6 = (l6 - 9 - l4 & 3) + 9;
			}
			if (l6 > 12 && l6 <= 16) {
				l6 = (l6 - 13 - l4 & 3) + 13;
			}
			int i7;
			int k7;
			int i8;
			int k8;
			int j9;
			if (l6 == 1) {
				i7 = i6;
				k7 = j6;
				i8 = l;
				k8 = l2;
				j9 = j;
			} else if (l6 == 2) {
				i7 = i6 + i5;
				k7 = j6;
				i8 = l + j3 >> 1;
				k8 = l2 + i2 >> 1;
				j9 = j + j4 >> 1;
			} else if (l6 == 3) {
				i7 = i6 + c;
				k7 = j6;
				i8 = j3;
				k8 = i2;
				j9 = j4;
			} else if (l6 == 4) {
				i7 = i6 + c;
				k7 = j6 + i5;
				i8 = j3 + k >> 1;
				k8 = i2 + j1 >> 1;
				j9 = j4 + j2 >> 1;
			} else if (l6 == 5) {
				i7 = i6 + c;
				k7 = j6 + c;
				i8 = k;
				k8 = j1;
				j9 = j2;
			} else if (l6 == 6) {
				i7 = i6 + i5;
				k7 = j6 + c;
				i8 = k + i >> 1;
				k8 = j1 + k1 >> 1;
				j9 = j2 + k3 >> 1;
			} else if (l6 == 7) {
				i7 = i6;
				k7 = j6 + c;
				i8 = i;
				k8 = k1;
				j9 = k3;
			} else if (l6 == 8) {
				i7 = i6;
				k7 = j6 + i5;
				i8 = i + l >> 1;
				k8 = k1 + l2 >> 1;
				j9 = k3 + j >> 1;
			} else if (l6 == 9) {
				i7 = i6 + i5;
				k7 = j6 + j5;
				i8 = l + j3 >> 1;
				k8 = l2 + i2 >> 1;
				j9 = j + j4 >> 1;
			} else if (l6 == 10) {
				i7 = i6 + k5;
				k7 = j6 + i5;
				i8 = j3 + k >> 1;
				k8 = i2 + j1 >> 1;
				j9 = j4 + j2 >> 1;
			} else if (l6 == 11) {
				i7 = i6 + i5;
				k7 = j6 + k5;
				i8 = k + i >> 1;
				k8 = j1 + k1 >> 1;
				j9 = j2 + k3 >> 1;
			} else if (l6 == 12) {
				i7 = i6 + j5;
				k7 = j6 + i5;
				i8 = i + l >> 1;
				k8 = k1 + l2 >> 1;
				j9 = k3 + j >> 1;
			} else if (l6 == 13) {
				i7 = i6 + j5;
				k7 = j6 + j5;
				i8 = l;
				k8 = l2;
				j9 = j;
			} else if (l6 == 14) {
				i7 = i6 + k5;
				k7 = j6 + j5;
				i8 = j3;
				k8 = i2;
				j9 = j4;
			} else if (l6 == 15) {
				i7 = i6 + k5;
				k7 = j6 + k5;
				i8 = k;
				k8 = j1;
				j9 = j2;
			} else {
				i7 = i6 + j5;
				k7 = j6 + k5;
				i8 = i;
				k8 = k1;
				j9 = k3;
			}
			anIntArray403[k6] = i7;
			anIntArray404[k6] = i8;
			anIntArray405[k6] = k7;
			ai1[k6] = k8;
			ai2[k6] = j9;
		}

		int ai3[] = ComplexTile.anIntArrayArray427[i3];
		int j7 = ai3.length / 4;
		anIntArray409 = new int[j7];
		anIntArray410 = new int[j7];
		anIntArray411 = new int[j7];
		anIntArray406 = new int[j7];
		anIntArray407 = new int[j7];
		anIntArray408 = new int[j7];
		if (l3 != -1) {
			anIntArray412 = new int[j7];
		}
		int l7 = 0;
		for (int j8 = 0; j8 < j7; j8++) {
			int l8 = ai3[l7];
			int k9 = ai3[l7 + 1];
			int i10 = ai3[l7 + 2];
			int j10 = ai3[l7 + 3];
			l7 += 4;
			if (k9 < 4) {
				k9 = k9 - l4 & 3;
			}
			if (i10 < 4) {
				i10 = i10 - l4 & 3;
			}
			if (j10 < 4) {
				j10 = j10 - l4 & 3;
			}
			anIntArray409[j8] = k9;
			anIntArray410[j8] = i10;
			anIntArray411[j8] = j10;
			if (l8 == 0) {
				anIntArray406[j8] = ai1[k9];
				anIntArray407[j8] = ai1[i10];
				anIntArray408[j8] = ai1[j10];
				if (anIntArray412 != null) {
					anIntArray412[j8] = -1;
				}
			} else {
				anIntArray406[j8] = ai2[k9];
				anIntArray407[j8] = ai2[i10];
				anIntArray408[j8] = ai2[j10];
				if (anIntArray412 != null) {
					anIntArray412[j8] = l3;
				}
			}
		}

		int i9 = l;
		int l9 = j3;
		if (j3 < i9) {
			i9 = j3;
		}
		if (j3 > l9) {
			l9 = j3;
		}
		if (k < i9) {
			i9 = k;
		}
		if (k > l9) {
			l9 = k;
		}
		if (i < i9) {
			i9 = i;
		}
		if (i > l9) {
			l9 = i;
		}
		i9 /= 14;
		l9 /= 14;
	}

}
