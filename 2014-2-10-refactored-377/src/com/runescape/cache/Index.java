package com.runescape.cache;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Index {
	
	public int anInt514;
	public boolean aBoolean515;
	public int anInt516;
	public static byte aByteArray517[] = new byte[520];
	public RandomAccessFile aRandomAccessFile518;
	public RandomAccessFile aRandomAccessFile519;
	public int anInt520;
	public int anInt521;

	public Index(int i, int j, RandomAccessFile randomaccessfile, RandomAccessFile randomaccessfile1, int k) {
		aBoolean515 = false;
		anInt516 = 3;
		anInt521 = 65000;
		anInt520 = i;
		if (k < 4 || k > 4) {
			anInt516 = 148;
		}
		aRandomAccessFile518 = randomaccessfile;
		aRandomAccessFile519 = randomaccessfile1;
		anInt521 = j;
	}

	public synchronized byte[] method292(byte byte0, int i) {
		if (byte0 == 6) {
			byte0 = 0;
		} else {
			aBoolean515 = !aBoolean515;
		}
		try {
			method295(true, i * 6, aRandomAccessFile519);
			int l;
			for (int j = 0; j < 6; j += l) {
				l = aRandomAccessFile519.read(Index.aByteArray517, j, 6 - j);
				if (l == -1) {
					return null;
				}
			}

			int i1 = ((Index.aByteArray517[0] & 0xff) << 16) + ((Index.aByteArray517[1] & 0xff) << 8)
					+ (Index.aByteArray517[2] & 0xff);
			int j1 = ((Index.aByteArray517[3] & 0xff) << 16) + ((Index.aByteArray517[4] & 0xff) << 8)
					+ (Index.aByteArray517[5] & 0xff);
			if (i1 < 0 || i1 > anInt521) {
				return null;
			}
			if (j1 <= 0 || j1 > aRandomAccessFile518.length() / 520L) {
				return null;
			}
			byte abyte0[] = new byte[i1];
			int k1 = 0;
			for (int l1 = 0; k1 < i1; l1++) {
				if (j1 == 0) {
					return null;
				}
				method295(true, j1 * 520, aRandomAccessFile518);
				int k = 0;
				int i2 = i1 - k1;
				if (i2 > 512) {
					i2 = 512;
				}
				int j2;
				for (; k < i2 + 8; k += j2) {
					j2 = aRandomAccessFile518.read(Index.aByteArray517, k, (i2 + 8) - k);
					if (j2 == -1) {
						return null;
					}
				}

				int k2 = ((Index.aByteArray517[0] & 0xff) << 8) + (Index.aByteArray517[1] & 0xff);
				int l2 = ((Index.aByteArray517[2] & 0xff) << 8) + (Index.aByteArray517[3] & 0xff);
				int i3 = ((Index.aByteArray517[4] & 0xff) << 16) + ((Index.aByteArray517[5] & 0xff) << 8)
						+ (Index.aByteArray517[6] & 0xff);
				int j3 = Index.aByteArray517[7] & 0xff;
				if (k2 != i || l2 != l1 || j3 != anInt520) {
					return null;
				}
				if (i3 < 0 || i3 > aRandomAccessFile518.length() / 520L) {
					return null;
				}
				for (int k3 = 0; k3 < i2; k3++) {
					abyte0[k1++] = Index.aByteArray517[k3 + 8];
				}

				j1 = i3;
			}

			return abyte0;
		} catch (IOException _ex) {
			return null;
		}
	}

	public synchronized boolean method293(int i, boolean flag, byte abyte0[], int j) {
		if (!flag) {
			for (int k = 1; k > 0; k++) {
				;
			}
		}
		boolean flag1 = method294(abyte0, anInt514, j, true, i);
		if (!flag1) {
			flag1 = method294(abyte0, anInt514, j, false, i);
		}
		return flag1;
	}

	public synchronized boolean method294(byte abyte0[], int i, int j, boolean flag, int k) {
		if (i != 0) {
			throw new NullPointerException();
		}
		try {
			int l;
			if (flag) {
				method295(true, j * 6, aRandomAccessFile519);
				int k1;
				for (int i1 = 0; i1 < 6; i1 += k1) {
					k1 = aRandomAccessFile519.read(Index.aByteArray517, i1, 6 - i1);
					if (k1 == -1) {
						return false;
					}
				}

				l = ((Index.aByteArray517[3] & 0xff) << 16) + ((Index.aByteArray517[4] & 0xff) << 8)
						+ (Index.aByteArray517[5] & 0xff);
				if (l <= 0 || l > aRandomAccessFile518.length() / 520L) {
					return false;
				}
			} else {
				l = (int) ((aRandomAccessFile518.length() + 519L) / 520L);
				if (l == 0) {
					l = 1;
				}
			}
			Index.aByteArray517[0] = (byte) (k >> 16);
			Index.aByteArray517[1] = (byte) (k >> 8);
			Index.aByteArray517[2] = (byte) k;
			Index.aByteArray517[3] = (byte) (l >> 16);
			Index.aByteArray517[4] = (byte) (l >> 8);
			Index.aByteArray517[5] = (byte) l;
			method295(true, j * 6, aRandomAccessFile519);
			aRandomAccessFile519.write(Index.aByteArray517, 0, 6);
			int j1 = 0;
			for (int l1 = 0; j1 < k; l1++) {
				int i2 = 0;
				if (flag) {
					method295(true, l * 520, aRandomAccessFile518);
					int j2;
					int l2;
					for (j2 = 0; j2 < 8; j2 += l2) {
						l2 = aRandomAccessFile518.read(Index.aByteArray517, j2, 8 - j2);
						if (l2 == -1) {
							break;
						}
					}

					if (j2 == 8) {
						int i3 = ((Index.aByteArray517[0] & 0xff) << 8) + (Index.aByteArray517[1] & 0xff);
						int j3 = ((Index.aByteArray517[2] & 0xff) << 8) + (Index.aByteArray517[3] & 0xff);
						i2 = ((Index.aByteArray517[4] & 0xff) << 16) + ((Index.aByteArray517[5] & 0xff) << 8)
								+ (Index.aByteArray517[6] & 0xff);
						int k3 = Index.aByteArray517[7] & 0xff;
						if (i3 != j || j3 != l1 || k3 != anInt520) {
							return false;
						}
						if (i2 < 0 || i2 > aRandomAccessFile518.length() / 520L) {
							return false;
						}
					}
				}
				if (i2 == 0) {
					flag = false;
					i2 = (int) ((aRandomAccessFile518.length() + 519L) / 520L);
					if (i2 == 0) {
						i2++;
					}
					if (i2 == l) {
						i2++;
					}
				}
				if (k - j1 <= 512) {
					i2 = 0;
				}
				Index.aByteArray517[0] = (byte) (j >> 8);
				Index.aByteArray517[1] = (byte) j;
				Index.aByteArray517[2] = (byte) (l1 >> 8);
				Index.aByteArray517[3] = (byte) l1;
				Index.aByteArray517[4] = (byte) (i2 >> 16);
				Index.aByteArray517[5] = (byte) (i2 >> 8);
				Index.aByteArray517[6] = (byte) i2;
				Index.aByteArray517[7] = (byte) anInt520;
				method295(true, l * 520, aRandomAccessFile518);
				aRandomAccessFile518.write(Index.aByteArray517, 0, 8);
				int k2 = k - j1;
				if (k2 > 512) {
					k2 = 512;
				}
				aRandomAccessFile518.write(abyte0, j1, k2);
				j1 += k2;
				l = i2;
			}

			return true;
		} catch (IOException _ex) {
			return false;
		}
	}

	public synchronized void method295(boolean flag, int i, RandomAccessFile randomaccessfile) throws IOException {
		if (!flag) {
			return;
		}
		if (i < 0 || i > 0x3c00000) {
			System.out.println("Badseek - pos:" + i + " len:" + randomaccessfile.length());
			i = 0x3c00000;
			try {
				Thread.sleep(1000L);
			} catch (Exception _ex) {
			}
		}
		randomaccessfile.seek(i);
	}
}
