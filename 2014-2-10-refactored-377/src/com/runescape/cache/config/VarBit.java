package com.runescape.cache.config;

import com.runescape.cache.Archive;
import com.runescape.net.Buffer;

public class VarBit {
	
	public int anInt822;
	public static int anInt823;
	public static VarBit aClass49Array824[];
	public String aString825;
	public int anInt826;
	public int anInt827;
	public int anInt828;
	public boolean aBoolean829;
	public int anInt830;
	public int anInt831;
	public boolean aBoolean832;
	
	public static void method440(Archive class2, int i) {
		Buffer class50_sub1_sub2 = new Buffer(true, class2.getFile("varbit.dat", null));
		if (i != 36135) {
			return;
		}
		VarBit.anInt823 = class50_sub1_sub2.method523();
		if (VarBit.aClass49Array824 == null) {
			VarBit.aClass49Array824 = new VarBit[VarBit.anInt823];
		}
		for (int j = 0; j < VarBit.anInt823; j++) {
			if (VarBit.aClass49Array824[j] == null) {
				VarBit.aClass49Array824[j] = new VarBit();
			}
			VarBit.aClass49Array824[j].method441(-954, j, class50_sub1_sub2);
			if (VarBit.aClass49Array824[j].aBoolean829) {
				Varp.aClass43Array704[VarBit.aClass49Array824[j].anInt826].aBoolean716 = true;
			}
		}

		if (class50_sub1_sub2.position != class50_sub1_sub2.buffer.length) {
			System.out.println("varbit load mismatch");
		}
	}

	public void method441(int i, int j, Buffer class50_sub1_sub2) {
		if (i >= 0) {
			anInt822 = -151;
		}
		do {
			int k = class50_sub1_sub2.method521();
			if (k == 0) {
				return;
			}
			if (k == 1) {
				anInt826 = class50_sub1_sub2.method523();
				anInt827 = class50_sub1_sub2.method521();
				anInt828 = class50_sub1_sub2.method521();
			} else if (k == 10) {
				aString825 = class50_sub1_sub2.method528();
			} else if (k == 2) {
				aBoolean829 = true;
			} else if (k == 3) {
				anInt830 = class50_sub1_sub2.method526();
			} else if (k == 4) {
				anInt831 = class50_sub1_sub2.method526();
			} else if (k == 5) {
				aBoolean832 = false;
			} else {
				System.out.println("Error unrecognised config code: " + k);
			}
		} while (true);
	}
}
