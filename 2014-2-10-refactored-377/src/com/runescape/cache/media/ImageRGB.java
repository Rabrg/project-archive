package com.runescape.cache.media;

import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.PixelGrabber;

import com.runescape.cache.Archive;
import com.runescape.media.Rasterizer;
import com.runescape.net.Buffer;

public class ImageRGB extends Rasterizer {
	
	public int anInt1477;
	public boolean aBoolean1478;
	public byte aByte1479;
	public int anInt1480;
	public int anInt1481;
	public int anInt1482;
	public int anInt1483;
	public boolean aBoolean1484;
	public boolean aBoolean1485;
	public boolean aBoolean1486;
	public boolean aBoolean1487;
	public int anInt1488;
	public int anIntArray1489[];
	public int anInt1490;
	public int anInt1491;
	public int anInt1492;
	public int anInt1493;
	public int anInt1494;
	public int anInt1495;
	
	public ImageRGB(int i, int j) {
		anInt1477 = -235;
		aBoolean1478 = true;
		aByte1479 = 5;
		anInt1480 = -3539;
		anInt1481 = -766;
		anInt1482 = -766;
		anInt1483 = 1;
		aBoolean1484 = true;
		aBoolean1485 = false;
		aBoolean1486 = false;
		aBoolean1487 = true;
		anInt1488 = 3600;
		anIntArray1489 = new int[i * j];
		anInt1490 = anInt1494 = i;
		anInt1491 = anInt1495 = j;
		anInt1492 = anInt1493 = 0;
	}

	public ImageRGB(byte abyte0[], Component component) {
		anInt1477 = -235;
		aBoolean1478 = true;
		aByte1479 = 5;
		anInt1480 = -3539;
		anInt1481 = -766;
		anInt1482 = -766;
		anInt1483 = 1;
		aBoolean1484 = true;
		aBoolean1485 = false;
		aBoolean1486 = false;
		aBoolean1487 = true;
		anInt1488 = 3600;
		try {
			Image image = Toolkit.getDefaultToolkit().createImage(abyte0);
			MediaTracker mediatracker = new MediaTracker(component);
			mediatracker.addImage(image, 0);
			mediatracker.waitForAll();
			anInt1490 = image.getWidth(component);
			anInt1491 = image.getHeight(component);
			anInt1494 = anInt1490;
			anInt1495 = anInt1491;
			anInt1492 = 0;
			anInt1493 = 0;
			anIntArray1489 = new int[anInt1490 * anInt1491];
			PixelGrabber pixelgrabber = new PixelGrabber(image, 0, 0, anInt1490, anInt1491, anIntArray1489, 0,
					anInt1490);
			pixelgrabber.grabPixels();
			return;
		} catch (Exception _ex) {
			System.out.println("Error converting jpg");
		}
	}

	public ImageRGB(Archive class2, String s, int i) {
		anInt1477 = -235;
		aBoolean1478 = true;
		aByte1479 = 5;
		anInt1480 = -3539;
		anInt1481 = -766;
		anInt1482 = -766;
		anInt1483 = 1;
		aBoolean1484 = true;
		aBoolean1485 = false;
		aBoolean1486 = false;
		aBoolean1487 = true;
		anInt1488 = 3600;
		Buffer class50_sub1_sub2 = new Buffer(true, class2.getFile(s + ".dat", null));
		Buffer class50_sub1_sub2_1 = new Buffer(true, class2.getFile("index.dat", null));
		class50_sub1_sub2_1.position = class50_sub1_sub2.method523();
		anInt1494 = class50_sub1_sub2_1.method523();
		anInt1495 = class50_sub1_sub2_1.method523();
		int j = class50_sub1_sub2_1.method521();
		int ai[] = new int[j];
		for (int k = 0; k < j - 1; k++) {
			ai[k + 1] = class50_sub1_sub2_1.method525();
			if (ai[k + 1] == 0) {
				ai[k + 1] = 1;
			}
		}

		for (int l = 0; l < i; l++) {
			class50_sub1_sub2_1.position += 2;
			class50_sub1_sub2.position += class50_sub1_sub2_1.method523() * class50_sub1_sub2_1.method523();
			class50_sub1_sub2_1.position++;
		}

		anInt1492 = class50_sub1_sub2_1.method521();
		anInt1493 = class50_sub1_sub2_1.method521();
		anInt1490 = class50_sub1_sub2_1.method523();
		anInt1491 = class50_sub1_sub2_1.method523();
		int i1 = class50_sub1_sub2_1.method521();
		int j1 = anInt1490 * anInt1491;
		anIntArray1489 = new int[j1];
		if (i1 == 0) {
			for (int k1 = 0; k1 < j1; k1++) {
				anIntArray1489[k1] = ai[class50_sub1_sub2.method521()];
			}

			return;
		}
		if (i1 == 1) {
			for (int l1 = 0; l1 < anInt1490; l1++) {
				for (int i2 = 0; i2 < anInt1491; i2++) {
					anIntArray1489[l1 + i2 * anInt1490] = ai[class50_sub1_sub2.method521()];
				}

			}

		}
	}

	public void method456(boolean flag) {
		if (flag) {
			return;
		} else {
			Rasterizer.method444(aBoolean1487, anInt1490, anInt1491, anIntArray1489);
			return;
		}
	}

	public void method457(int i, int j, int k, int l) {
		for (int i1 = 0; i1 < anIntArray1489.length; i1++) {
			int j1 = anIntArray1489[i1];
			if (j1 != 0) {
				int k1 = j1 >> 16 & 0xff;
				k1 += k;
				if (k1 < 1) {
					k1 = 1;
				} else if (k1 > 255) {
					k1 = 255;
				}
				int l1 = j1 >> 8 & 0xff;
				l1 += j;
				if (l1 < 1) {
					l1 = 1;
				} else if (l1 > 255) {
					l1 = 255;
				}
				int i2 = j1 & 0xff;
				i2 += i;
				if (i2 < 1) {
					i2 = 1;
				} else if (i2 > 255) {
					i2 = 255;
				}
				anIntArray1489[i1] = (k1 << 16) + (l1 << 8) + i2;
			}
		}

		if (l != anInt1477) {
			aBoolean1487 = !aBoolean1487;
		}
	}

	public void method458(int i) {
		int ai[] = new int[anInt1494 * anInt1495];
		for (int j = 0; j < anInt1491; j++) {
			for (int k = 0; k < anInt1490; k++) {
				ai[(j + anInt1493) * anInt1494 + (k + anInt1492)] = anIntArray1489[j * anInt1490 + k];
			}

		}

		anIntArray1489 = ai;
		anInt1490 = anInt1494;
		anInt1491 = anInt1495;
		anInt1492 = 0;
		anInt1493 = 0;
		if (i == 1790) {
			;
		}
	}

	public void method459(int i, int j, int k) {
		k += anInt1492;
		i += anInt1493;
		int l = k + i * Rasterizer.anInt1425;
		int i1 = 0;
		int j1 = anInt1491;
		int k1 = anInt1490;
		int l1 = Rasterizer.anInt1425 - k1;
		int i2 = 0;
		if (i < Rasterizer.anInt1427) {
			int j2 = Rasterizer.anInt1427 - i;
			j1 -= j2;
			i = Rasterizer.anInt1427;
			i1 += j2 * k1;
			l += j2 * Rasterizer.anInt1425;
		}
		if (i + j1 > Rasterizer.anInt1428) {
			j1 -= (i + j1) - Rasterizer.anInt1428;
		}
		if (k < Rasterizer.anInt1429) {
			int k2 = Rasterizer.anInt1429 - k;
			k1 -= k2;
			k = Rasterizer.anInt1429;
			i1 += k2;
			l += k2;
			i2 += k2;
			l1 += k2;
		}
		if (k + k1 > Rasterizer.anInt1430) {
			int l2 = (k + k1) - Rasterizer.anInt1430;
			k1 -= l2;
			i2 += l2;
			l1 += l2;
		}
		if (k1 <= 0 || j1 <= 0) {
			return;
		}
		method460(k1, l1, j1, anIntArray1489, i1, i2, l, (byte) -39, Rasterizer.anIntArray1424);
		if (j >= 0) {
			aBoolean1487 = !aBoolean1487;
		}
	}

	public void method460(int i, int j, int k, int ai[], int l, int i1, int j1, byte byte0, int ai1[]) {
		int k1 = -(i >> 2);
		if (byte0 != -39) {
			anInt1488 = 201;
		}
		i = -(i & 3);
		for (int l1 = -k; l1 < 0; l1++) {
			for (int i2 = k1; i2 < 0; i2++) {
				ai1[j1++] = ai[l++];
				ai1[j1++] = ai[l++];
				ai1[j1++] = ai[l++];
				ai1[j1++] = ai[l++];
			}

			for (int j2 = i; j2 < 0; j2++) {
				ai1[j1++] = ai[l++];
			}

			j1 += j;
			l += i1;
		}

	}

	public void method461(int i, int j, int k) {
		j += anInt1492;
		if (k >= 0) {
			return;
		}
		i += anInt1493;
		int l = j + i * Rasterizer.anInt1425;
		int i1 = 0;
		int j1 = anInt1491;
		int k1 = anInt1490;
		int l1 = Rasterizer.anInt1425 - k1;
		int i2 = 0;
		if (i < Rasterizer.anInt1427) {
			int j2 = Rasterizer.anInt1427 - i;
			j1 -= j2;
			i = Rasterizer.anInt1427;
			i1 += j2 * k1;
			l += j2 * Rasterizer.anInt1425;
		}
		if (i + j1 > Rasterizer.anInt1428) {
			j1 -= (i + j1) - Rasterizer.anInt1428;
		}
		if (j < Rasterizer.anInt1429) {
			int k2 = Rasterizer.anInt1429 - j;
			k1 -= k2;
			j = Rasterizer.anInt1429;
			i1 += k2;
			l += k2;
			i2 += k2;
			l1 += k2;
		}
		if (j + k1 > Rasterizer.anInt1430) {
			int l2 = (j + k1) - Rasterizer.anInt1430;
			k1 -= l2;
			i2 += l2;
			l1 += l2;
		}
		if (k1 <= 0 || j1 <= 0) {
			return;
		} else {
			method462(Rasterizer.anIntArray1424, anIntArray1489, 0, i1, l, k1, j1, l1, i2);
			return;
		}
	}

	public void method462(int ai[], int ai1[], int i, int j, int k, int l, int i1, int j1, int k1) {
		int l1 = -(l >> 2);
		l = -(l & 3);
		for (int i2 = -i1; i2 < 0; i2++) {
			for (int j2 = l1; j2 < 0; j2++) {
				i = ai1[j++];
				if (i != 0) {
					ai[k++] = i;
				} else {
					k++;
				}
				i = ai1[j++];
				if (i != 0) {
					ai[k++] = i;
				} else {
					k++;
				}
				i = ai1[j++];
				if (i != 0) {
					ai[k++] = i;
				} else {
					k++;
				}
				i = ai1[j++];
				if (i != 0) {
					ai[k++] = i;
				} else {
					k++;
				}
			}

			for (int k2 = l; k2 < 0; k2++) {
				i = ai1[j++];
				if (i != 0) {
					ai[k++] = i;
				} else {
					k++;
				}
			}

			k += j1;
			j += k1;
		}

	}

	public void method463(int i, int j, int k, int l) {
		j += anInt1492;
		k += anInt1493;
		int i1 = j + k * Rasterizer.anInt1425;
		int j1 = 0;
		if (i != 0) {
			return;
		}
		int k1 = anInt1491;
		int l1 = anInt1490;
		int i2 = Rasterizer.anInt1425 - l1;
		int j2 = 0;
		if (k < Rasterizer.anInt1427) {
			int k2 = Rasterizer.anInt1427 - k;
			k1 -= k2;
			k = Rasterizer.anInt1427;
			j1 += k2 * l1;
			i1 += k2 * Rasterizer.anInt1425;
		}
		if (k + k1 > Rasterizer.anInt1428) {
			k1 -= (k + k1) - Rasterizer.anInt1428;
		}
		if (j < Rasterizer.anInt1429) {
			int l2 = Rasterizer.anInt1429 - j;
			l1 -= l2;
			j = Rasterizer.anInt1429;
			j1 += l2;
			i1 += l2;
			j2 += l2;
			i2 += l2;
		}
		if (j + l1 > Rasterizer.anInt1430) {
			int i3 = (j + l1) - Rasterizer.anInt1430;
			l1 -= i3;
			j2 += i3;
			i2 += i3;
		}
		if (l1 <= 0 || k1 <= 0) {
			return;
		} else {
			method464(l1, j2, 0, i2, j1, anInt1481, l, i1, k1, Rasterizer.anIntArray1424, anIntArray1489);
			return;
		}
	}

	public void method464(int i, int j, int k, int l, int i1, int j1, int k1, int l1, int i2, int ai[], int ai1[]) {
		int j2 = 256 - k1;
		for (int k2 = -i2; k2 < 0; k2++) {
			for (int l2 = -i; l2 < 0; l2++) {
				k = ai1[i1++];
				if (k != 0) {
					int i3 = ai[l1];
					ai[l1++] = ((k & 0xff00ff) * k1 + (i3 & 0xff00ff) * j2 & 0xff00ff00)
							+ ((k & 0xff00) * k1 + (i3 & 0xff00) * j2 & 0xff0000) >> 8;
				} else {
					l1++;
				}
			}

			l1 += l;
			i1 += j;
		}

		if (j1 >= 0) {
			aBoolean1478 = !aBoolean1478;
		}
	}

	public void method465(int i, int j, int k, int l, int i1, int ai[], int j1, int k1, int l1, int ai1[], int i2) {
		j = 36 / j;
		try {
			int j2 = -i1 / 2;
			int k2 = -k / 2;
			int l2 = (int) (Math.sin(k1 / 326.11000000000001D) * 65536D);
			int i3 = (int) (Math.cos(k1 / 326.11000000000001D) * 65536D);
			l2 = l2 * l1 >> 8;
			i3 = i3 * l1 >> 8;
			int j3 = (l << 16) + (k2 * l2 + j2 * i3);
			int k3 = (i2 << 16) + (k2 * i3 - j2 * l2);
			int l3 = j1 + i * Rasterizer.anInt1425;
			for (i = 0; i < k; i++) {
				int i4 = ai1[i];
				int j4 = l3 + i4;
				int k4 = j3 + i3 * i4;
				int l4 = k3 - l2 * i4;
				for (j1 = -ai[i]; j1 < 0; j1++) {
					Rasterizer.anIntArray1424[j4++] = anIntArray1489[(k4 >> 16) + (l4 >> 16) * anInt1490];
					k4 += i3;
					l4 -= l2;
				}

				j3 += l2;
				k3 += i3;
				l3 += Rasterizer.anInt1425;
			}

			return;
		} catch (Exception _ex) {
			return;
		}
	}

	public void method466(int i, int j, int k, int l, int i1, int j1, int k1, double d, int l1) {
		if (j1 != -30658) {
			return;
		}
		try {
			int i2 = -k1 / 2;
			int j2 = -i1 / 2;
			int k2 = (int) (Math.sin(d) * 65536D);
			int l2 = (int) (Math.cos(d) * 65536D);
			k2 = k2 * i >> 8;
			l2 = l2 * i >> 8;
			int i3 = (j << 16) + (j2 * k2 + i2 * l2);
			int j3 = (l << 16) + (j2 * l2 - i2 * k2);
			int k3 = k + l1 * Rasterizer.anInt1425;
			for (l1 = 0; l1 < i1; l1++) {
				int l3 = k3;
				int i4 = i3;
				int j4 = j3;
				for (k = -k1; k < 0; k++) {
					int k4 = anIntArray1489[(i4 >> 16) + (j4 >> 16) * anInt1490];
					if (k4 != 0) {
						Rasterizer.anIntArray1424[l3++] = k4;
					} else {
						l3++;
					}
					i4 += l2;
					j4 -= k2;
				}

				i3 += k2;
				j3 += l2;
				k3 += Rasterizer.anInt1425;
			}

			return;
		} catch (Exception _ex) {
			return;
		}
	}

	public void method467(IndexedImage class50_sub1_sub1_sub3, int i, int j, int k) {
		if (j != -49993) {
			return;
		}
		k += anInt1492;
		i += anInt1493;
		int l = k + i * Rasterizer.anInt1425;
		int i1 = 0;
		int j1 = anInt1491;
		int k1 = anInt1490;
		int l1 = Rasterizer.anInt1425 - k1;
		int i2 = 0;
		if (i < Rasterizer.anInt1427) {
			int j2 = Rasterizer.anInt1427 - i;
			j1 -= j2;
			i = Rasterizer.anInt1427;
			i1 += j2 * k1;
			l += j2 * Rasterizer.anInt1425;
		}
		if (i + j1 > Rasterizer.anInt1428) {
			j1 -= (i + j1) - Rasterizer.anInt1428;
		}
		if (k < Rasterizer.anInt1429) {
			int k2 = Rasterizer.anInt1429 - k;
			k1 -= k2;
			k = Rasterizer.anInt1429;
			i1 += k2;
			l += k2;
			i2 += k2;
			l1 += k2;
		}
		if (k + k1 > Rasterizer.anInt1430) {
			int l2 = (k + k1) - Rasterizer.anInt1430;
			k1 -= l2;
			i2 += l2;
			l1 += l2;
		}
		if (k1 <= 0 || j1 <= 0) {
			return;
		} else {
			method468(l, l1, anIntArray1489, k1, Rasterizer.anIntArray1424, class50_sub1_sub1_sub3.aByteArray1516,
					40303, j1, i1, 0, i2);
			return;
		}
	}

	public void method468(int i, int j, int ai[], int k, int ai1[], byte abyte0[], int l, int i1, int j1, int k1, int l1) {
		int i2 = -(k >> 2);
		if (l != 40303) {
			aBoolean1486 = !aBoolean1486;
		}
		k = -(k & 3);
		for (int j2 = -i1; j2 < 0; j2++) {
			for (int k2 = i2; k2 < 0; k2++) {
				k1 = ai[j1++];
				if (k1 != 0 && abyte0[i] == 0) {
					ai1[i++] = k1;
				} else {
					i++;
				}
				k1 = ai[j1++];
				if (k1 != 0 && abyte0[i] == 0) {
					ai1[i++] = k1;
				} else {
					i++;
				}
				k1 = ai[j1++];
				if (k1 != 0 && abyte0[i] == 0) {
					ai1[i++] = k1;
				} else {
					i++;
				}
				k1 = ai[j1++];
				if (k1 != 0 && abyte0[i] == 0) {
					ai1[i++] = k1;
				} else {
					i++;
				}
			}

			for (int l2 = k; l2 < 0; l2++) {
				k1 = ai[j1++];
				if (k1 != 0 && abyte0[i] == 0) {
					ai1[i++] = k1;
				} else {
					i++;
				}
			}

			i += j;
			j1 += l1;
		}

	}
}
