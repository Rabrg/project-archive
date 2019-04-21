package com.runescape.media.renderable;

import com.runescape.cache.media.SpotAnimation;
import com.runescape.media.Animation;

public class Projectile extends Renderable {
	
	public SpotAnimation aClass27_1553;
	public int anInt1554;
	public double aDouble1555;
	public double aDouble1556;
	public double aDouble1557;
	public int anInt1558;
	public int anInt1559;
	public int anInt1560;
	public boolean aBoolean1561;
	public int anInt1562;
	public int anInt1563;
	public byte aByte1564;
	public int anInt1565;
	public int anInt1566;
	public int anInt1567;
	public int anInt1568;
	public double aDouble1569;
	public double aDouble1570;
	public double aDouble1571;
	public double aDouble1572;
	public boolean aBoolean1573;
	public double aDouble1574;
	public boolean aBoolean1575;
	public int anInt1576;
	public int anInt1577;
	public int anInt1578;
	public int anInt1579;
	
	public void method562(int i, int j, int k, int l, int i1) {
		if (!aBoolean1575) {
			double d = i - anInt1576;
			double d2 = j - anInt1577;
			double d3 = Math.sqrt(d * d + d2 * d2);
			aDouble1555 = anInt1576 + (d * anInt1559) / d3;
			aDouble1556 = anInt1577 + (d2 * anInt1559) / d3;
			aDouble1557 = anInt1578;
		}
		double d1 = (anInt1566 + 1) - l;
		aDouble1569 = (i - aDouble1555) / d1;
		aDouble1570 = (j - aDouble1556) / d1;
		if (i1 != 0) {
			return;
		}
		aDouble1571 = Math.sqrt(aDouble1569 * aDouble1569 + aDouble1570 * aDouble1570);
		if (!aBoolean1575) {
			aDouble1572 = -aDouble1571 * Math.tan(anInt1558 * 0.02454369D);
		}
		aDouble1574 = (2D * (k - aDouble1557 - aDouble1572 * d1)) / (d1 * d1);
	}

	public void method563(int i, boolean flag) {
		aBoolean1575 = true;
		aDouble1555 += aDouble1569 * i;
		if (flag) {
			for (int j = 1; j > 0; j++) {
				;
			}
		}
		aDouble1556 += aDouble1570 * i;
		aDouble1557 += aDouble1572 * i + 0.5D * aDouble1574 * i * i;
		aDouble1572 += aDouble1574 * i;
		anInt1562 = (int) (Math.atan2(aDouble1569, aDouble1570) * 325.94900000000001D) + 1024 & 0x7ff;
		anInt1563 = (int) (Math.atan2(aDouble1572, aDouble1571) * 325.94900000000001D) & 0x7ff;
		if (aClass27_1553.aClass14_558 != null) {
			for (anInt1568 += i; anInt1568 > aClass27_1553.aClass14_558.method205(0, anInt1567);) {
				anInt1568 -= aClass27_1553.aClass14_558.method205(0, anInt1567);
				anInt1567++;
				if (anInt1567 >= aClass27_1553.aClass14_558.anInt294) {
					anInt1567 = 0;
				}
			}
		}

	}

	@Override
	public Model method561(byte byte0) {
		Model class50_sub1_sub4_sub4 = aClass27_1553.method307();
		if (class50_sub1_sub4_sub4 == null) {
			return null;
		}
		int i = -1;
		if (aClass27_1553.aClass14_558 != null) {
			i = aClass27_1553.aClass14_558.anIntArray295[anInt1567];
		}
		Model class50_sub1_sub4_sub4_1 = new Model(false, false, true, class50_sub1_sub4_sub4, Animation.method239(
				aBoolean1573, i));
		if (i != -1) {
			class50_sub1_sub4_sub4_1.method584(7);
			class50_sub1_sub4_sub4_1.method585(i, (byte) 6);
			class50_sub1_sub4_sub4_1.anIntArrayArray1679 = null;
			class50_sub1_sub4_sub4_1.anIntArrayArray1678 = null;
		}
		if (aClass27_1553.anInt561 != 128 || aClass27_1553.anInt562 != 128) {
			class50_sub1_sub4_sub4_1.method593(aClass27_1553.anInt562, aClass27_1553.anInt561, 9,
					aClass27_1553.anInt561);
		}
		class50_sub1_sub4_sub4_1.method589(anInt1563, 341);
		class50_sub1_sub4_sub4_1.method594(64 + aClass27_1553.anInt564, 850 + aClass27_1553.anInt565, -30, -50, -30,
				true);
		if (byte0 == 3) {
			byte0 = 0;
		} else {
			aBoolean1561 = !aBoolean1561;
		}
		return class50_sub1_sub4_sub4_1;
	}

	public Projectile(int i, int j, int k, int l, int i1, int j1, int k1, int l1, byte byte0, int i2, int j2, int k2) {
		aBoolean1561 = false;
		aByte1564 = -41;
		aBoolean1573 = true;
		aBoolean1575 = false;
		aClass27_1553 = SpotAnimation.aClass27Array554[i1];
		anInt1554 = i;
		anInt1576 = j2;
		anInt1577 = l;
		anInt1578 = i2;
		anInt1565 = k2;
		anInt1566 = j1;
		if (byte0 != aByte1564) {
			throw new NullPointerException();
		} else {
			anInt1558 = k1;
			anInt1559 = k;
			anInt1560 = l1;
			anInt1579 = j;
			aBoolean1575 = false;
			return;
		}
	}
}
