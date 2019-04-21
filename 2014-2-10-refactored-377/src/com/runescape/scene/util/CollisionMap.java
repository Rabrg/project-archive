package com.runescape.scene.util;

public class CollisionMap {
	
	public boolean aBoolean748;
	public int anInt749;
	public boolean aBoolean750;
	public int anInt751;
	public boolean aBoolean752;
	public int anInt753;
	public int anInt754;
	public int anInt755;
	public int anInt756;
	public int anIntArrayArray757[][];
	
	public CollisionMap(int i, int j, int k) {
		aBoolean748 = false;
		anInt749 = -766;
		aBoolean750 = true;
		anInt751 = 3;
		aBoolean752 = true;
		anInt753 = 0;
		if (j != 0) {
			throw new NullPointerException();
		} else {
			anInt754 = 0;
			anInt755 = k;
			anInt756 = i;
			anIntArrayArray757 = new int[anInt755][anInt756];
			method411();
			return;
		}
	}

	public void method411() {
		for (int i = 0; i < anInt755; i++) {
			for (int j = 0; j < anInt756; j++) {
				if (i == 0 || j == 0 || i == anInt755 - 1 || j == anInt756 - 1) {
					anIntArrayArray757[i][j] = 0xffffff;
				} else {
					anIntArrayArray757[i][j] = 0x1000000;
				}
			}

		}

	}

	public void method412(int i, int j, boolean flag, int k, int l, int i1) {
		l -= anInt753;
		if (j != 37679) {
			return;
		}
		i1 -= anInt754;
		if (k == 0) {
			if (i == 0) {
				method415(l, i1, 128);
				method415(l - 1, i1, 8);
			}
			if (i == 1) {
				method415(l, i1, 2);
				method415(l, i1 + 1, 32);
			}
			if (i == 2) {
				method415(l, i1, 8);
				method415(l + 1, i1, 128);
			}
			if (i == 3) {
				method415(l, i1, 32);
				method415(l, i1 - 1, 2);
			}
		}
		if (k == 1 || k == 3) {
			if (i == 0) {
				method415(l, i1, 1);
				method415(l - 1, i1 + 1, 16);
			}
			if (i == 1) {
				method415(l, i1, 4);
				method415(l + 1, i1 + 1, 64);
			}
			if (i == 2) {
				method415(l, i1, 16);
				method415(l + 1, i1 - 1, 1);
			}
			if (i == 3) {
				method415(l, i1, 64);
				method415(l - 1, i1 - 1, 4);
			}
		}
		if (k == 2) {
			if (i == 0) {
				method415(l, i1, 130);
				method415(l - 1, i1, 8);
				method415(l, i1 + 1, 32);
			}
			if (i == 1) {
				method415(l, i1, 10);
				method415(l, i1 + 1, 32);
				method415(l + 1, i1, 128);
			}
			if (i == 2) {
				method415(l, i1, 40);
				method415(l + 1, i1, 128);
				method415(l, i1 - 1, 2);
			}
			if (i == 3) {
				method415(l, i1, 160);
				method415(l, i1 - 1, 2);
				method415(l - 1, i1, 8);
			}
		}
		if (flag) {
			if (k == 0) {
				if (i == 0) {
					method415(l, i1, 0x10000);
					method415(l - 1, i1, 4096);
				}
				if (i == 1) {
					method415(l, i1, 1024);
					method415(l, i1 + 1, 16384);
				}
				if (i == 2) {
					method415(l, i1, 4096);
					method415(l + 1, i1, 0x10000);
				}
				if (i == 3) {
					method415(l, i1, 16384);
					method415(l, i1 - 1, 1024);
				}
			}
			if (k == 1 || k == 3) {
				if (i == 0) {
					method415(l, i1, 512);
					method415(l - 1, i1 + 1, 8192);
				}
				if (i == 1) {
					method415(l, i1, 2048);
					method415(l + 1, i1 + 1, 32768);
				}
				if (i == 2) {
					method415(l, i1, 8192);
					method415(l + 1, i1 - 1, 512);
				}
				if (i == 3) {
					method415(l, i1, 32768);
					method415(l - 1, i1 - 1, 2048);
				}
			}
			if (k == 2) {
				if (i == 0) {
					method415(l, i1, 0x10400);
					method415(l - 1, i1, 4096);
					method415(l, i1 + 1, 16384);
				}
				if (i == 1) {
					method415(l, i1, 5120);
					method415(l, i1 + 1, 16384);
					method415(l + 1, i1, 0x10000);
				}
				if (i == 2) {
					method415(l, i1, 20480);
					method415(l + 1, i1, 0x10000);
					method415(l, i1 - 1, 1024);
				}
				if (i == 3) {
					method415(l, i1, 0x14000);
					method415(l, i1 - 1, 1024);
					method415(l - 1, i1, 4096);
				}
			}
		}
	}

	public void method413(int i, int j, int k, int l, boolean flag, int i1, byte byte0) {
		if (byte0 != 52) {
			anInt749 = -314;
		}
		int j1 = 256;
		if (flag) {
			j1 += 0x20000;
		}
		i1 -= anInt753;
		i -= anInt754;
		if (j == 1 || j == 3) {
			int k1 = l;
			l = k;
			k = k1;
		}
		for (int l1 = i1; l1 < i1 + l; l1++) {
			if (l1 >= 0 && l1 < anInt755) {
				for (int i2 = i; i2 < i + k; i2++) {
					if (i2 >= 0 && i2 < anInt756) {
						method415(l1, i2, j1);
					}
				}

			}
		}

	}

	public void method414(int i, int j, int k) {
		k -= anInt753;
		j -= anInt754;
		if (i < 8 || i > 8) {
			anInt751 = 84;
		}
		anIntArrayArray757[k][j] |= 0x200000;
	}

	public void method415(int i, int j, int k) {
		anIntArrayArray757[i][j] |= k;
	}

	public void method416(int i, int j, int k, int l, int i1, boolean flag) {
		if (k != 0) {
			aBoolean750 = !aBoolean750;
		}
		j -= anInt753;
		l -= anInt754;
		if (i1 == 0) {
			if (i == 0) {
				method418(j, 128, (byte) 17, l);
				method418(j - 1, 8, (byte) 17, l);
			}
			if (i == 1) {
				method418(j, 2, (byte) 17, l);
				method418(j, 32, (byte) 17, l + 1);
			}
			if (i == 2) {
				method418(j, 8, (byte) 17, l);
				method418(j + 1, 128, (byte) 17, l);
			}
			if (i == 3) {
				method418(j, 32, (byte) 17, l);
				method418(j, 2, (byte) 17, l - 1);
			}
		}
		if (i1 == 1 || i1 == 3) {
			if (i == 0) {
				method418(j, 1, (byte) 17, l);
				method418(j - 1, 16, (byte) 17, l + 1);
			}
			if (i == 1) {
				method418(j, 4, (byte) 17, l);
				method418(j + 1, 64, (byte) 17, l + 1);
			}
			if (i == 2) {
				method418(j, 16, (byte) 17, l);
				method418(j + 1, 1, (byte) 17, l - 1);
			}
			if (i == 3) {
				method418(j, 64, (byte) 17, l);
				method418(j - 1, 4, (byte) 17, l - 1);
			}
		}
		if (i1 == 2) {
			if (i == 0) {
				method418(j, 130, (byte) 17, l);
				method418(j - 1, 8, (byte) 17, l);
				method418(j, 32, (byte) 17, l + 1);
			}
			if (i == 1) {
				method418(j, 10, (byte) 17, l);
				method418(j, 32, (byte) 17, l + 1);
				method418(j + 1, 128, (byte) 17, l);
			}
			if (i == 2) {
				method418(j, 40, (byte) 17, l);
				method418(j + 1, 128, (byte) 17, l);
				method418(j, 2, (byte) 17, l - 1);
			}
			if (i == 3) {
				method418(j, 160, (byte) 17, l);
				method418(j, 2, (byte) 17, l - 1);
				method418(j - 1, 8, (byte) 17, l);
			}
		}
		if (flag) {
			if (i1 == 0) {
				if (i == 0) {
					method418(j, 0x10000, (byte) 17, l);
					method418(j - 1, 4096, (byte) 17, l);
				}
				if (i == 1) {
					method418(j, 1024, (byte) 17, l);
					method418(j, 16384, (byte) 17, l + 1);
				}
				if (i == 2) {
					method418(j, 4096, (byte) 17, l);
					method418(j + 1, 0x10000, (byte) 17, l);
				}
				if (i == 3) {
					method418(j, 16384, (byte) 17, l);
					method418(j, 1024, (byte) 17, l - 1);
				}
			}
			if (i1 == 1 || i1 == 3) {
				if (i == 0) {
					method418(j, 512, (byte) 17, l);
					method418(j - 1, 8192, (byte) 17, l + 1);
				}
				if (i == 1) {
					method418(j, 2048, (byte) 17, l);
					method418(j + 1, 32768, (byte) 17, l + 1);
				}
				if (i == 2) {
					method418(j, 8192, (byte) 17, l);
					method418(j + 1, 512, (byte) 17, l - 1);
				}
				if (i == 3) {
					method418(j, 32768, (byte) 17, l);
					method418(j - 1, 2048, (byte) 17, l - 1);
				}
			}
			if (i1 == 2) {
				if (i == 0) {
					method418(j, 0x10400, (byte) 17, l);
					method418(j - 1, 4096, (byte) 17, l);
					method418(j, 16384, (byte) 17, l + 1);
				}
				if (i == 1) {
					method418(j, 5120, (byte) 17, l);
					method418(j, 16384, (byte) 17, l + 1);
					method418(j + 1, 0x10000, (byte) 17, l);
				}
				if (i == 2) {
					method418(j, 20480, (byte) 17, l);
					method418(j + 1, 0x10000, (byte) 17, l);
					method418(j, 1024, (byte) 17, l - 1);
				}
				if (i == 3) {
					method418(j, 0x14000, (byte) 17, l);
					method418(j, 1024, (byte) 17, l - 1);
					method418(j - 1, 4096, (byte) 17, l);
				}
			}
		}
	}

	public void method417(int i, int j, int k, int l, int i1, boolean flag, int j1) {
		int k1 = 256;
		if (flag) {
			k1 += 0x20000;
		}
		k -= anInt753;
		if (i != 2) {
			aBoolean750 = !aBoolean750;
		}
		j -= anInt754;
		if (l == 1 || l == 3) {
			int l1 = j1;
			j1 = i1;
			i1 = l1;
		}
		for (int i2 = k; i2 < k + j1; i2++) {
			if (i2 >= 0 && i2 < anInt755) {
				for (int j2 = j; j2 < j + i1; j2++) {
					if (j2 >= 0 && j2 < anInt756) {
						method418(i2, k1, (byte) 17, j2);
					}
				}

			}
		}

	}

	public void method418(int i, int j, byte byte0, int k) {
		anIntArrayArray757[i][k] &= 0xffffff - j;
		if (byte0 == 17) {
			;
		}
	}

	public void method419(int i, byte byte0, int j) {
		if (byte0 != -122) {
			return;
		} else {
			i -= anInt753;
			j -= anInt754;
			anIntArrayArray757[i][j] &= 0xdfffff;
			return;
		}
	}

	public boolean method420(int i, int j, int k, int l, int i1, int j1, int k1) {
		if (i1 == i && j1 == k) {
			return true;
		}
		i1 -= anInt753;
		if (j != 0) {
			aBoolean752 = !aBoolean752;
		}
		j1 -= anInt754;
		i -= anInt753;
		k -= anInt754;
		if (l == 0) {
			if (k1 == 0) {
				if (i1 == i - 1 && j1 == k) {
					return true;
				}
				if (i1 == i && j1 == k + 1 && (anIntArrayArray757[i1][j1] & 0x1280120) == 0) {
					return true;
				}
				if (i1 == i && j1 == k - 1 && (anIntArrayArray757[i1][j1] & 0x1280102) == 0) {
					return true;
				}
			} else if (k1 == 1) {
				if (i1 == i && j1 == k + 1) {
					return true;
				}
				if (i1 == i - 1 && j1 == k && (anIntArrayArray757[i1][j1] & 0x1280108) == 0) {
					return true;
				}
				if (i1 == i + 1 && j1 == k && (anIntArrayArray757[i1][j1] & 0x1280180) == 0) {
					return true;
				}
			} else if (k1 == 2) {
				if (i1 == i + 1 && j1 == k) {
					return true;
				}
				if (i1 == i && j1 == k + 1 && (anIntArrayArray757[i1][j1] & 0x1280120) == 0) {
					return true;
				}
				if (i1 == i && j1 == k - 1 && (anIntArrayArray757[i1][j1] & 0x1280102) == 0) {
					return true;
				}
			} else if (k1 == 3) {
				if (i1 == i && j1 == k - 1) {
					return true;
				}
				if (i1 == i - 1 && j1 == k && (anIntArrayArray757[i1][j1] & 0x1280108) == 0) {
					return true;
				}
				if (i1 == i + 1 && j1 == k && (anIntArrayArray757[i1][j1] & 0x1280180) == 0) {
					return true;
				}
			}
		}
		if (l == 2) {
			if (k1 == 0) {
				if (i1 == i - 1 && j1 == k) {
					return true;
				}
				if (i1 == i && j1 == k + 1) {
					return true;
				}
				if (i1 == i + 1 && j1 == k && (anIntArrayArray757[i1][j1] & 0x1280180) == 0) {
					return true;
				}
				if (i1 == i && j1 == k - 1 && (anIntArrayArray757[i1][j1] & 0x1280102) == 0) {
					return true;
				}
			} else if (k1 == 1) {
				if (i1 == i - 1 && j1 == k && (anIntArrayArray757[i1][j1] & 0x1280108) == 0) {
					return true;
				}
				if (i1 == i && j1 == k + 1) {
					return true;
				}
				if (i1 == i + 1 && j1 == k) {
					return true;
				}
				if (i1 == i && j1 == k - 1 && (anIntArrayArray757[i1][j1] & 0x1280102) == 0) {
					return true;
				}
			} else if (k1 == 2) {
				if (i1 == i - 1 && j1 == k && (anIntArrayArray757[i1][j1] & 0x1280108) == 0) {
					return true;
				}
				if (i1 == i && j1 == k + 1 && (anIntArrayArray757[i1][j1] & 0x1280120) == 0) {
					return true;
				}
				if (i1 == i + 1 && j1 == k) {
					return true;
				}
				if (i1 == i && j1 == k - 1) {
					return true;
				}
			} else if (k1 == 3) {
				if (i1 == i - 1 && j1 == k) {
					return true;
				}
				if (i1 == i && j1 == k + 1 && (anIntArrayArray757[i1][j1] & 0x1280120) == 0) {
					return true;
				}
				if (i1 == i + 1 && j1 == k && (anIntArrayArray757[i1][j1] & 0x1280180) == 0) {
					return true;
				}
				if (i1 == i && j1 == k - 1) {
					return true;
				}
			}
		}
		if (l == 9) {
			if (i1 == i && j1 == k + 1 && (anIntArrayArray757[i1][j1] & 0x20) == 0) {
				return true;
			}
			if (i1 == i && j1 == k - 1 && (anIntArrayArray757[i1][j1] & 2) == 0) {
				return true;
			}
			if (i1 == i - 1 && j1 == k && (anIntArrayArray757[i1][j1] & 8) == 0) {
				return true;
			}
			if (i1 == i + 1 && j1 == k && (anIntArrayArray757[i1][j1] & 0x80) == 0) {
				return true;
			}
		}
		return false;
	}

	public boolean method421(int i, int j, int k, int l, int i1, int j1, int k1) {
		if (l == k && j == k1) {
			return true;
		}
		l -= anInt753;
		j -= anInt754;
		k -= anInt753;
		if (i >= 0) {
			throw new NullPointerException();
		}
		k1 -= anInt754;
		if (j1 == 6 || j1 == 7) {
			if (j1 == 7) {
				i1 = i1 + 2 & 3;
			}
			if (i1 == 0) {
				if (l == k + 1 && j == k1 && (anIntArrayArray757[l][j] & 0x80) == 0) {
					return true;
				}
				if (l == k && j == k1 - 1 && (anIntArrayArray757[l][j] & 2) == 0) {
					return true;
				}
			} else if (i1 == 1) {
				if (l == k - 1 && j == k1 && (anIntArrayArray757[l][j] & 8) == 0) {
					return true;
				}
				if (l == k && j == k1 - 1 && (anIntArrayArray757[l][j] & 2) == 0) {
					return true;
				}
			} else if (i1 == 2) {
				if (l == k - 1 && j == k1 && (anIntArrayArray757[l][j] & 8) == 0) {
					return true;
				}
				if (l == k && j == k1 + 1 && (anIntArrayArray757[l][j] & 0x20) == 0) {
					return true;
				}
			} else if (i1 == 3) {
				if (l == k + 1 && j == k1 && (anIntArrayArray757[l][j] & 0x80) == 0) {
					return true;
				}
				if (l == k && j == k1 + 1 && (anIntArrayArray757[l][j] & 0x20) == 0) {
					return true;
				}
			}
		}
		if (j1 == 8) {
			if (l == k && j == k1 + 1 && (anIntArrayArray757[l][j] & 0x20) == 0) {
				return true;
			}
			if (l == k && j == k1 - 1 && (anIntArrayArray757[l][j] & 2) == 0) {
				return true;
			}
			if (l == k - 1 && j == k1 && (anIntArrayArray757[l][j] & 8) == 0) {
				return true;
			}
			if (l == k + 1 && j == k1 && (anIntArrayArray757[l][j] & 0x80) == 0) {
				return true;
			}
		}
		return false;
	}

	public boolean method422(int i, int j, boolean flag, int k, int l, int i1, int j1, int k1) {
		int l1 = (k + i) - 1;
		int i2 = (j1 + i1) - 1;
		if (!flag) {
			anInt749 = 238;
		}
		if (j >= k && j <= l1 && k1 >= j1 && k1 <= i2) {
			return true;
		}
		if (j == k - 1 && k1 >= j1 && k1 <= i2 && (anIntArrayArray757[j - anInt753][k1 - anInt754] & 8) == 0
				&& (l & 8) == 0) {
			return true;
		}
		if (j == l1 + 1 && k1 >= j1 && k1 <= i2 && (anIntArrayArray757[j - anInt753][k1 - anInt754] & 0x80) == 0
				&& (l & 2) == 0) {
			return true;
		}
		if (k1 == j1 - 1 && j >= k && j <= l1 && (anIntArrayArray757[j - anInt753][k1 - anInt754] & 2) == 0
				&& (l & 4) == 0) {
			return true;
		}
		return k1 == i2 + 1 && j >= k && j <= l1 && (anIntArrayArray757[j - anInt753][k1 - anInt754] & 0x20) == 0
				&& (l & 1) == 0;
	}
}
