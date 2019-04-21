package com.runescape.cache.media;

import java.util.Random;

import com.runescape.cache.Archive;
import com.runescape.media.Rasterizer;
import com.runescape.net.Buffer;

public class TypeFace extends Rasterizer {
	
	public boolean aBoolean1496;
	public boolean aBoolean1497;
	public int anInt1498;
	public int anInt1499;
	public byte aByteArrayArray1500[][];
	public int anIntArray1501[];
	public int anIntArray1502[];
	public int anIntArray1503[];
	public int anIntArray1504[];
	public int anIntArray1505[];
	public int anInt1506;
	public Random aRandom1507;
	public boolean aBoolean1508;
	
	public TypeFace(boolean flag, Archive class2, int i, String s) {
		aBoolean1496 = true;
		aBoolean1497 = true;
		anInt1498 = 3;
		anInt1499 = 3;
		aByteArrayArray1500 = new byte[256][];
		anIntArray1501 = new int[256];
		anIntArray1502 = new int[256];
		anIntArray1503 = new int[256];
		anIntArray1504 = new int[256];
		anIntArray1505 = new int[256];
		aRandom1507 = new Random();
		aBoolean1508 = false;
		Buffer class50_sub1_sub2 = new Buffer(true, class2.getFile(s + ".dat", null));
		Buffer class50_sub1_sub2_1 = new Buffer(true, class2.getFile("index.dat", null));
		class50_sub1_sub2_1.position = class50_sub1_sub2.method523() + 4;
		while (i >= 0) {
			aBoolean1496 = !aBoolean1496;
		}
		int k = class50_sub1_sub2_1.method521();
		if (k > 0) {
			class50_sub1_sub2_1.position += 3 * (k - 1);
		}
		for (int l = 0; l < 256; l++) {
			anIntArray1503[l] = class50_sub1_sub2_1.method521();
			anIntArray1504[l] = class50_sub1_sub2_1.method521();
			int i1 = anIntArray1501[l] = class50_sub1_sub2_1.method523();
			int j1 = anIntArray1502[l] = class50_sub1_sub2_1.method523();
			int k1 = class50_sub1_sub2_1.method521();
			int l1 = i1 * j1;
			aByteArrayArray1500[l] = new byte[l1];
			if (k1 == 0) {
				for (int i2 = 0; i2 < l1; i2++) {
					aByteArrayArray1500[l][i2] = class50_sub1_sub2.method522();
				}

			} else if (k1 == 1) {
				for (int j2 = 0; j2 < i1; j2++) {
					for (int l2 = 0; l2 < j1; l2++) {
						aByteArrayArray1500[l][j2 + l2 * i1] = class50_sub1_sub2.method522();
					}

				}

			}
			if (j1 > anInt1506 && l < 128) {
				anInt1506 = j1;
			}
			anIntArray1503[l] = 1;
			anIntArray1505[l] = i1 + 2;
			int k2 = 0;
			for (int i3 = j1 / 7; i3 < j1; i3++) {
				k2 += aByteArrayArray1500[l][i3 * i1];
			}

			if (k2 <= j1 / 7) {
				anIntArray1505[l]--;
				anIntArray1503[l] = 0;
			}
			k2 = 0;
			for (int j3 = j1 / 7; j3 < j1; j3++) {
				k2 += aByteArrayArray1500[l][(i1 - 1) + j3 * i1];
			}

			if (k2 <= j1 / 7) {
				anIntArray1505[l]--;
			}
		}

		if (flag) {
			anIntArray1505[32] = anIntArray1505[73];
			return;
		} else {
			anIntArray1505[32] = anIntArray1505[105];
			return;
		}
	}

	public void method469(boolean flag, String s, int i, int j, int k) {
		method474(2245, j - method473(s, (byte) -53), i, k, s);
		if (flag) {
			;
		}
	}

	public void method470(int i, int j, int k, int l, String s) {
		method474(2245, i - method473(s, (byte) -53) / 2, l, k, s);
		j = 21 / j;
	}

	public void method471(boolean flag, int i, int j, int k, int l, String s) {
		if (i < anInt1498 || i > anInt1498) {
			return;
		} else {
			method478(j, l - method472((byte) 35, s) / 2, k, flag, s, -39629);
			return;
		}
	}

	public int method472(byte byte0, String s) {
		if (s == null) {
			return 0;
		}
		int i = 0;
		for (int j = 0; j < s.length(); j++) {
			if (s.charAt(j) == '@' && j + 4 < s.length() && s.charAt(j + 4) == '@') {
				j += 4;
			} else {
				i += anIntArray1505[s.charAt(j)];
			}
		}

		if (byte0 != 35) {
			for (int k = 1; k > 0; k++) {
				;
			}
		}
		return i;
	}

	public int method473(String s, byte byte0) {
		if (s == null) {
			return 0;
		}
		int i = 0;
		if (byte0 != -53) {
			for (int j = 1; j > 0; j++) {
				;
			}
		}
		for (int k = 0; k < s.length(); k++) {
			i += anIntArray1505[s.charAt(k)];
		}

		return i;
	}

	public void method474(int i, int j, int k, int l, String s) {
		if (i != 2245) {
			for (int i1 = 1; i1 > 0; i1++) {
				;
			}
		}
		if (s == null) {
			return;
		}
		l -= anInt1506;
		for (int j1 = 0; j1 < s.length(); j1++) {
			char c = s.charAt(j1);
			if (c != ' ') {
				method481(aByteArrayArray1500[c], j + anIntArray1503[c], l + anIntArray1504[c], anIntArray1501[c],
						anIntArray1502[c], k);
			}
			j += anIntArray1505[c];
		}

	}

	public void method475(int i, byte byte0, int j, String s, int k, int l) {
		if (s == null) {
			return;
		}
		k -= method473(s, (byte) -53) / 2;
		if (byte0 == 4) {
			byte0 = 0;
		} else {
			aBoolean1497 = !aBoolean1497;
		}
		i -= anInt1506;
		for (int i1 = 0; i1 < s.length(); i1++) {
			char c = s.charAt(i1);
			if (c != ' ') {
				method481(aByteArrayArray1500[c], k + anIntArray1503[c],
						i + anIntArray1504[c] + (int) (Math.sin(i1 / 2D + j / 5D) * 5D), anIntArray1501[c],
						anIntArray1502[c], l);
			}
			k += anIntArray1505[c];
		}

	}

	public void method476(int i, int j, byte byte0, String s, int k, int l) {
		if (s == null) {
			return;
		}
		k -= method473(s, (byte) -53) / 2;
		if (byte0 != 1) {
			for (int i1 = 1; i1 > 0; i1++) {
				;
			}
		}
		i -= anInt1506;
		for (int j1 = 0; j1 < s.length(); j1++) {
			char c = s.charAt(j1);
			if (c != ' ') {
				method481(aByteArrayArray1500[c], k + anIntArray1503[c] + (int) (Math.sin(j1 / 5D + l / 5D) * 5D), i
						+ anIntArray1504[c] + (int) (Math.sin(j1 / 3D + l / 5D) * 5D), anIntArray1501[c],
						anIntArray1502[c], j);
			}
			k += anIntArray1505[c];
		}

	}

	public void method477(int i, String s, int j, int k, int l, int i1, int j1) {
		if (s == null) {
			return;
		}
		double d = 7D - i1 / 8D;
		while (i >= 0) {
			for (int k1 = 1; k1 > 0; k1++) {
				;
			}
		}
		if (d < 0.0D) {
			d = 0.0D;
		}
		k -= method473(s, (byte) -53) / 2;
		l -= anInt1506;
		for (int l1 = 0; l1 < s.length(); l1++) {
			char c = s.charAt(l1);
			if (c != ' ') {
				method481(aByteArrayArray1500[c], k + anIntArray1503[c],
						l + anIntArray1504[c] + (int) (Math.sin(l1 / 1.5D + j1) * d), anIntArray1501[c],
						anIntArray1502[c], j);
			}
			k += anIntArray1505[c];
		}

	}

	public void method478(int i, int j, int k, boolean flag, String s, int l) {
		aBoolean1508 = false;
		if (l != -39629) {
			return;
		}
		int i1 = j;
		if (s == null) {
			return;
		}
		k -= anInt1506;
		for (int j1 = 0; j1 < s.length(); j1++) {
			if (s.charAt(j1) == '@' && j1 + 4 < s.length() && s.charAt(j1 + 4) == '@') {
				int k1 = method480(anInt1499, s.substring(j1 + 1, j1 + 4));
				if (k1 != -1) {
					i = k1;
				}
				j1 += 4;
			} else {
				char c = s.charAt(j1);
				if (c != ' ') {
					if (flag) {
						method481(aByteArrayArray1500[c], j + anIntArray1503[c] + 1, k + anIntArray1504[c] + 1,
								anIntArray1501[c], anIntArray1502[c], 0);
					}
					method481(aByteArrayArray1500[c], j + anIntArray1503[c], k + anIntArray1504[c], anIntArray1501[c],
							anIntArray1502[c], i);
				}
				j += anIntArray1505[c];
			}
		}

		if (aBoolean1508) {
			Rasterizer.method452(i1, 0x800000, k + (int) (anInt1506 * 0.69999999999999996D), j - i1, true);
		}
	}

	public void method479(boolean flag, int i, int j, int k, int l, String s, int i1) {
		if (s == null) {
			return;
		}
		aRandom1507.setSeed(i);
		int j1 = 192 + (aRandom1507.nextInt() & 0x1f);
		l -= anInt1506;
		if (i1 != 0) {
			anInt1499 = 489;
		}
		for (int k1 = 0; k1 < s.length(); k1++) {
			if (s.charAt(k1) == '@' && k1 + 4 < s.length() && s.charAt(k1 + 4) == '@') {
				int l1 = method480(anInt1499, s.substring(k1 + 1, k1 + 4));
				if (l1 != -1) {
					k = l1;
				}
				k1 += 4;
			} else {
				char c = s.charAt(k1);
				if (c != ' ') {
					if (flag) {
						method483(j + anIntArray1503[c] + 1, true, 0, aByteArrayArray1500[c],
								l + anIntArray1504[c] + 1, anIntArray1502[c], anIntArray1501[c], 192);
					}
					method483(j + anIntArray1503[c], true, k, aByteArrayArray1500[c], l + anIntArray1504[c],
							anIntArray1502[c], anIntArray1501[c], j1);
				}
				j += anIntArray1505[c];
				if ((aRandom1507.nextInt() & 3) == 0) {
					j++;
				}
			}
		}

	}

	public int method480(int i, String s) {
		if (i != anInt1499) {
			for (int j = 1; j > 0; j++) {
				;
			}
		}
		if (s.equals("red")) {
			return 0xff0000;
		}
		if (s.equals("gre")) {
			return 65280;
		}
		if (s.equals("blu")) {
			return 255;
		}
		if (s.equals("yel")) {
			return 0xffff00;
		}
		if (s.equals("cya")) {
			return 65535;
		}
		if (s.equals("mag")) {
			return 0xff00ff;
		}
		if (s.equals("whi")) {
			return 0xffffff;
		}
		if (s.equals("bla")) {
			return 0;
		}
		if (s.equals("lre")) {
			return 0xff9040;
		}
		if (s.equals("dre")) {
			return 0x800000;
		}
		if (s.equals("dbl")) {
			return 128;
		}
		if (s.equals("or1")) {
			return 0xffb000;
		}
		if (s.equals("or2")) {
			return 0xff7000;
		}
		if (s.equals("or3")) {
			return 0xff3000;
		}
		if (s.equals("gr1")) {
			return 0xc0ff00;
		}
		if (s.equals("gr2")) {
			return 0x80ff00;
		}
		if (s.equals("gr3")) {
			return 0x40ff00;
		}
		if (s.equals("str")) {
			aBoolean1508 = true;
		}
		if (s.equals("end")) {
			aBoolean1508 = false;
		}
		return -1;
	}

	public void method481(byte abyte0[], int i, int j, int k, int l, int i1) {
		int j1 = i + j * Rasterizer.anInt1425;
		int k1 = Rasterizer.anInt1425 - k;
		int l1 = 0;
		int i2 = 0;
		if (j < Rasterizer.anInt1427) {
			int j2 = Rasterizer.anInt1427 - j;
			l -= j2;
			j = Rasterizer.anInt1427;
			i2 += j2 * k;
			j1 += j2 * Rasterizer.anInt1425;
		}
		if (j + l >= Rasterizer.anInt1428) {
			l -= ((j + l) - Rasterizer.anInt1428) + 1;
		}
		if (i < Rasterizer.anInt1429) {
			int k2 = Rasterizer.anInt1429 - i;
			k -= k2;
			i = Rasterizer.anInt1429;
			i2 += k2;
			j1 += k2;
			l1 += k2;
			k1 += k2;
		}
		if (i + k >= Rasterizer.anInt1430) {
			int l2 = ((i + k) - Rasterizer.anInt1430) + 1;
			k -= l2;
			l1 += l2;
			k1 += l2;
		}
		if (k <= 0 || l <= 0) {
			return;
		} else {
			method482(Rasterizer.anIntArray1424, abyte0, i1, i2, j1, k, l, k1, l1);
			return;
		}
	}

	public void method482(int ai[], byte abyte0[], int i, int j, int k, int l, int i1, int j1, int k1) {
		int l1 = -(l >> 2);
		l = -(l & 3);
		for (int i2 = -i1; i2 < 0; i2++) {
			for (int j2 = l1; j2 < 0; j2++) {
				if (abyte0[j++] != 0) {
					ai[k++] = i;
				} else {
					k++;
				}
				if (abyte0[j++] != 0) {
					ai[k++] = i;
				} else {
					k++;
				}
				if (abyte0[j++] != 0) {
					ai[k++] = i;
				} else {
					k++;
				}
				if (abyte0[j++] != 0) {
					ai[k++] = i;
				} else {
					k++;
				}
			}

			for (int k2 = l; k2 < 0; k2++) {
				if (abyte0[j++] != 0) {
					ai[k++] = i;
				} else {
					k++;
				}
			}

			k += j1;
			j += k1;
		}

	}

	public void method483(int i, boolean flag, int j, byte abyte0[], int k, int l, int i1, int j1) {
		int k1 = i + k * Rasterizer.anInt1425;
		int l1 = Rasterizer.anInt1425 - i1;
		int i2 = 0;
		int j2 = 0;
		if (!flag) {
			return;
		}
		if (k < Rasterizer.anInt1427) {
			int k2 = Rasterizer.anInt1427 - k;
			l -= k2;
			k = Rasterizer.anInt1427;
			j2 += k2 * i1;
			k1 += k2 * Rasterizer.anInt1425;
		}
		if (k + l >= Rasterizer.anInt1428) {
			l -= ((k + l) - Rasterizer.anInt1428) + 1;
		}
		if (i < Rasterizer.anInt1429) {
			int l2 = Rasterizer.anInt1429 - i;
			i1 -= l2;
			i = Rasterizer.anInt1429;
			j2 += l2;
			k1 += l2;
			i2 += l2;
			l1 += l2;
		}
		if (i + i1 >= Rasterizer.anInt1430) {
			int i3 = ((i + i1) - Rasterizer.anInt1430) + 1;
			i1 -= i3;
			i2 += i3;
			l1 += i3;
		}
		if (i1 <= 0 || l <= 0) {
			return;
		} else {
			method484(j2, l1, i2, k1, j1, Rasterizer.anIntArray1424, j, 2, l, i1, abyte0);
			return;
		}
	}

	public void method484(int i, int j, int k, int l, int i1, int ai[], int j1, int k1, int l1, int i2, byte abyte0[]) {
		if (k1 < 2 || k1 > 2) {
			aBoolean1496 = !aBoolean1496;
		}
		j1 = ((j1 & 0xff00ff) * i1 & 0xff00ff00) + ((j1 & 0xff00) * i1 & 0xff0000) >> 8;
		i1 = 256 - i1;
		for (int j2 = -l1; j2 < 0; j2++) {
			for (int k2 = -i2; k2 < 0; k2++) {
				if (abyte0[i++] != 0) {
					int l2 = ai[l];
					ai[l++] = (((l2 & 0xff00ff) * i1 & 0xff00ff00) + ((l2 & 0xff00) * i1 & 0xff0000) >> 8) + j1;
				} else {
					l++;
				}
			}

			l += j;
			i += k;
		}

	}
}
