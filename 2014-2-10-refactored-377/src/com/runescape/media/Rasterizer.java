package com.runescape.media;

import com.runescape.collection.CacheableNode;

public class Rasterizer extends CacheableNode {
	
	public static int anInt1420;
	public static boolean aBoolean1421;
	public static boolean aBoolean1422 = true;
	public static int anInt1423 = -291;
	public static int anIntArray1424[];
	public static int anInt1425;
	public static int anInt1426;
	public static int anInt1427;
	public static int anInt1428;
	public static int anInt1429;
	public static int anInt1430;
	public static int anInt1431;
	public static int anInt1432;
	public static int anInt1433;
	public static boolean aBoolean1434;
	
	public static void method444(boolean flag, int i, int j, int ai[]) {
		if (!flag) {
			Rasterizer.anInt1423 = -394;
		}
		Rasterizer.anIntArray1424 = ai;
		Rasterizer.anInt1425 = i;
		Rasterizer.anInt1426 = j;
		Rasterizer.method446(0, 0, j, i, true);
	}

	public static void method445(byte byte0) {
		Rasterizer.anInt1429 = 0;
		Rasterizer.anInt1427 = 0;
		Rasterizer.anInt1430 = Rasterizer.anInt1425;
		Rasterizer.anInt1428 = Rasterizer.anInt1426;
		Rasterizer.anInt1431 = Rasterizer.anInt1430 - 1;
		if (byte0 != 82) {
			Rasterizer.anInt1423 = -258;
		}
		Rasterizer.anInt1432 = Rasterizer.anInt1430 / 2;
	}

	public static void method446(int i, int j, int k, int l, boolean flag) {
		if (j < 0) {
			j = 0;
		}
		if (i < 0) {
			i = 0;
		}
		if (l > Rasterizer.anInt1425) {
			l = Rasterizer.anInt1425;
		}
		if (k > Rasterizer.anInt1426) {
			k = Rasterizer.anInt1426;
		}
		Rasterizer.anInt1429 = j;
		Rasterizer.anInt1427 = i;
		Rasterizer.anInt1430 = l;
		Rasterizer.anInt1428 = k;
		if (!flag) {
			return;
		} else {
			Rasterizer.anInt1431 = Rasterizer.anInt1430 - 1;
			Rasterizer.anInt1432 = Rasterizer.anInt1430 / 2;
			Rasterizer.anInt1433 = Rasterizer.anInt1428 / 2;
			return;
		}
	}

	public static void method447(int i) {
		int j = Rasterizer.anInt1425 * Rasterizer.anInt1426;
		if (i != 4) {
			Rasterizer.aBoolean1421 = !Rasterizer.aBoolean1421;
		}
		for (int k = 0; k < j; k++) {
			Rasterizer.anIntArray1424[k] = 0;
		}

	}

	public static void method448(boolean flag, int i, int j, int k, int l, int i1, int j1) {
		if (j1 < Rasterizer.anInt1429) {
			k -= Rasterizer.anInt1429 - j1;
			j1 = Rasterizer.anInt1429;
		}
		if (j < Rasterizer.anInt1427) {
			l -= Rasterizer.anInt1427 - j;
			j = Rasterizer.anInt1427;
		}
		if (j1 + k > Rasterizer.anInt1430) {
			k = Rasterizer.anInt1430 - j1;
		}
		if (j + l > Rasterizer.anInt1428) {
			l = Rasterizer.anInt1428 - j;
		}
		int k1 = 256 - i1;
		int l1 = (i >> 16 & 0xff) * i1;
		int i2 = (i >> 8 & 0xff) * i1;
		int j2 = (i & 0xff) * i1;
		if (flag) {
			Rasterizer.aBoolean1421 = !Rasterizer.aBoolean1421;
		}
		int j3 = Rasterizer.anInt1425 - k;
		int k3 = j1 + j * Rasterizer.anInt1425;
		for (int l3 = 0; l3 < l; l3++) {
			for (int i4 = -k; i4 < 0; i4++) {
				int k2 = (Rasterizer.anIntArray1424[k3] >> 16 & 0xff) * k1;
				int l2 = (Rasterizer.anIntArray1424[k3] >> 8 & 0xff) * k1;
				int i3 = (Rasterizer.anIntArray1424[k3] & 0xff) * k1;
				int j4 = ((l1 + k2 >> 8) << 16) + ((i2 + l2 >> 8) << 8) + (j2 + i3 >> 8);
				Rasterizer.anIntArray1424[k3++] = j4;
			}

			k3 += j3;
		}

	}

	public static void method449(int i, int j, int k, byte byte0, int l, int i1) {
		if (i1 < Rasterizer.anInt1429) {
			l -= Rasterizer.anInt1429 - i1;
			i1 = Rasterizer.anInt1429;
		}
		if (j < Rasterizer.anInt1427) {
			i -= Rasterizer.anInt1427 - j;
			j = Rasterizer.anInt1427;
		}
		if (i1 + l > Rasterizer.anInt1430) {
			l = Rasterizer.anInt1430 - i1;
		}
		if (j + i > Rasterizer.anInt1428) {
			i = Rasterizer.anInt1428 - j;
		}
		int j1 = Rasterizer.anInt1425 - l;
		int k1 = i1 + j * Rasterizer.anInt1425;
		for (int l1 = -i; l1 < 0; l1++) {
			for (int i2 = -l; i2 < 0; i2++) {
				Rasterizer.anIntArray1424[k1++] = k;
			}

			k1 += j1;
		}

		if (byte0 == -24) {
			;
		}
	}

	public static void method450(int i, int j, int k, int l, int i1, int j1) {
		Rasterizer.method452(i1, l, j, j1, true);
		Rasterizer.method452(i1, l, (j + k) - 1, j1, true);
		if (i != 0) {
			Rasterizer.anInt1420 = -278;
		}
		Rasterizer.method454(i1, l, k, false, j);
		Rasterizer.method454((i1 + j1) - 1, l, k, false, j);
	}

	public static void method451(int i, int j, int k, int l, int i1, int j1, byte byte0) {
		if (byte0 != -113) {
			return;
		}
		Rasterizer.method453(i1, i, j, 1388, j1, k);
		Rasterizer.method453((i1 + l) - 1, i, j, 1388, j1, k);
		if (l >= 3) {
			Rasterizer.method455(0, i1 + 1, i, k, l - 2, j1);
			Rasterizer.method455(0, i1 + 1, (i + j) - 1, k, l - 2, j1);
		}
	}

	public static void method452(int i, int j, int k, int l, boolean flag) {
		if (k < Rasterizer.anInt1427 || k >= Rasterizer.anInt1428) {
			return;
		}
		if (i < Rasterizer.anInt1429) {
			l -= Rasterizer.anInt1429 - i;
			i = Rasterizer.anInt1429;
		}
		if (i + l > Rasterizer.anInt1430) {
			l = Rasterizer.anInt1430 - i;
		}
		int i1 = i + k * Rasterizer.anInt1425;
		if (!flag) {
			for (int j1 = 1; j1 > 0; j1++) {
				;
			}
		}
		for (int k1 = 0; k1 < l; k1++) {
			Rasterizer.anIntArray1424[i1 + k1] = j;
		}

	}

	public static void method453(int i, int j, int k, int l, int i1, int j1) {
		if (i < Rasterizer.anInt1427 || i >= Rasterizer.anInt1428) {
			return;
		}
		if (j < Rasterizer.anInt1429) {
			k -= Rasterizer.anInt1429 - j;
			j = Rasterizer.anInt1429;
		}
		if (j + k > Rasterizer.anInt1430) {
			k = Rasterizer.anInt1430 - j;
		}
		int k1 = 256 - i1;
		int l1 = (j1 >> 16 & 0xff) * i1;
		int i2 = (j1 >> 8 & 0xff) * i1;
		int j2 = (j1 & 0xff) * i1;
		int j3 = j + i * Rasterizer.anInt1425;
		for (int k3 = 0; k3 < k; k3++) {
			int k2 = (Rasterizer.anIntArray1424[j3] >> 16 & 0xff) * k1;
			int l2 = (Rasterizer.anIntArray1424[j3] >> 8 & 0xff) * k1;
			int i3 = (Rasterizer.anIntArray1424[j3] & 0xff) * k1;
			int l3 = ((l1 + k2 >> 8) << 16) + ((i2 + l2 >> 8) << 8) + (j2 + i3 >> 8);
			Rasterizer.anIntArray1424[j3++] = l3;
		}

		if (l != 1388) {
			Rasterizer.anInt1420 = -36;
		}
	}

	public static void method454(int i, int j, int k, boolean flag, int l) {
		if (flag) {
			return;
		}
		if (i < Rasterizer.anInt1429 || i >= Rasterizer.anInt1430) {
			return;
		}
		if (l < Rasterizer.anInt1427) {
			k -= Rasterizer.anInt1427 - l;
			l = Rasterizer.anInt1427;
		}
		if (l + k > Rasterizer.anInt1428) {
			k = Rasterizer.anInt1428 - l;
		}
		int i1 = i + l * Rasterizer.anInt1425;
		for (int j1 = 0; j1 < k; j1++) {
			Rasterizer.anIntArray1424[i1 + j1 * Rasterizer.anInt1425] = j;
		}

	}

	public static void method455(int i, int j, int k, int l, int i1, int j1) {
		if (k < Rasterizer.anInt1429 || k >= Rasterizer.anInt1430) {
			return;
		}
		if (j < Rasterizer.anInt1427) {
			i1 -= Rasterizer.anInt1427 - j;
			j = Rasterizer.anInt1427;
		}
		if (j + i1 > Rasterizer.anInt1428) {
			i1 = Rasterizer.anInt1428 - j;
		}
		int k1 = 256 - j1;
		int l1 = (l >> 16 & 0xff) * j1;
		int i2 = (l >> 8 & 0xff) * j1;
		int j2 = (l & 0xff) * j1;
		if (i != 0) {
			for (int j3 = 1; j3 > 0; j3++) {
				;
			}
		}
		int k3 = k + j * Rasterizer.anInt1425;
		for (int l3 = 0; l3 < i1; l3++) {
			int k2 = (Rasterizer.anIntArray1424[k3] >> 16 & 0xff) * k1;
			int l2 = (Rasterizer.anIntArray1424[k3] >> 8 & 0xff) * k1;
			int i3 = (Rasterizer.anIntArray1424[k3] & 0xff) * k1;
			int i4 = ((l1 + k2 >> 8) << 16) + ((i2 + l2 >> 8) << 8) + (j2 + i3 >> 8);
			Rasterizer.anIntArray1424[k3] = i4;
			k3 += Rasterizer.anInt1425;
		}

	}
}
