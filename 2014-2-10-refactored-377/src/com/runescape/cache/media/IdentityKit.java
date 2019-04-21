package com.runescape.cache.media;

import com.runescape.cache.Archive;
import com.runescape.media.renderable.Model;
import com.runescape.net.Buffer;

public class IdentityKit {
	
	public static byte aByte811 = 6;
	public int anInt812;
	public int anInt813;
	public static int anInt814;
	public static IdentityKit aClass48Array815[];
	public int anInt816;
	public int anIntArray817[];
	public int anIntArray818[];
	public int anIntArray819[];
	public int anIntArray820[] = { -1, -1, -1, -1, -1 };
	public boolean aBoolean821;
	
	public static void method434(Archive archive, int i) {
		Buffer buffer = new Buffer(true, archive.getFile("idk.dat", null));
		IdentityKit.anInt814 = buffer.method523();
		if (IdentityKit.aClass48Array815 == null) {
			IdentityKit.aClass48Array815 = new IdentityKit[IdentityKit.anInt814];
		}
		for (int j = 0; j < IdentityKit.anInt814; j++) {
			if (IdentityKit.aClass48Array815[j] == null) {
				IdentityKit.aClass48Array815[j] = new IdentityKit();
			}
			IdentityKit.aClass48Array815[j].loadDefinition(IdentityKit.aByte811, buffer);
		}

		if (i == 36135) {
			;
		}
	}

	public void loadDefinition(byte byte0, Buffer class50_sub1_sub2) {
		if (byte0 == 6) {
			byte0 = 0;
		} else {
			throw new NullPointerException();
		}
		do {
			int i = class50_sub1_sub2.method521();
			if (i == 0) {
				return;
			}
			if (i == 1) {
				anInt816 = class50_sub1_sub2.method521();
			} else if (i == 2) {
				int j = class50_sub1_sub2.method521();
				anIntArray817 = new int[j];
				for (int k = 0; k < j; k++) {
					anIntArray817[k] = class50_sub1_sub2.method523();
				}

			} else if (i == 3) {
				aBoolean821 = true;
			} else if (i >= 40 && i < 50) {
				anIntArray818[i - 40] = class50_sub1_sub2.method523();
			} else if (i >= 50 && i < 60) {
				anIntArray819[i - 50] = class50_sub1_sub2.method523();
			} else if (i >= 60 && i < 70) {
				anIntArray820[i - 60] = class50_sub1_sub2.method523();
			} else {
				System.out.println("Error unrecognised config code: " + i);
			}
		} while (true);
	}

	public boolean method436(int i) {
		if (anIntArray817 == null) {
			return true;
		}
		boolean flag = true;
		i = 89 / i;
		for (int j = 0; j < anIntArray817.length; j++) {
			if (!Model.method578(anIntArray817[j])) {
				flag = false;
			}
		}

		return flag;
	}

	public Model method437(byte byte0) {
		if (anIntArray817 == null) {
			return null;
		}
		Model aclass50_sub1_sub4_sub4[] = new Model[anIntArray817.length];
		for (int i = 0; i < anIntArray817.length; i++) {
			aclass50_sub1_sub4_sub4[i] = Model.method577(anIntArray817[i]);
		}

		Model class50_sub1_sub4_sub4;
		if (aclass50_sub1_sub4_sub4.length == 1) {
			class50_sub1_sub4_sub4 = aclass50_sub1_sub4_sub4[0];
		} else {
			class50_sub1_sub4_sub4 = new Model(aclass50_sub1_sub4_sub4.length, aclass50_sub1_sub4_sub4, (byte) -89);
		}
		for (int j = 0; j < 6; j++) {
			if (anIntArray818[j] == 0) {
				break;
			}
			class50_sub1_sub4_sub4.method591(anIntArray818[j], anIntArray819[j]);
		}

		if (byte0 != 2) {
			throw new NullPointerException();
		} else {
			return class50_sub1_sub4_sub4;
		}
	}

	public boolean method438(int i) {
		if (i != -10584) {
			throw new NullPointerException();
		}
		boolean flag = true;
		for (int j = 0; j < 5; j++) {
			if (anIntArray820[j] != -1 && !Model.method578(anIntArray820[j])) {
				flag = false;
			}
		}

		return flag;
	}

	public Model method439(boolean flag) {
		Model aclass50_sub1_sub4_sub4[] = new Model[5];
		if (flag) {
			anInt812 = -298;
		}
		int i = 0;
		for (int j = 0; j < 5; j++) {
			if (anIntArray820[j] != -1) {
				aclass50_sub1_sub4_sub4[i++] = Model.method577(anIntArray820[j]);
			}
		}

		Model class50_sub1_sub4_sub4 = new Model(i, aclass50_sub1_sub4_sub4, (byte) -89);
		for (int k = 0; k < 6; k++) {
			if (anIntArray818[k] == 0) {
				break;
			}
			class50_sub1_sub4_sub4.method591(anIntArray818[k], anIntArray819[k]);
		}

		return class50_sub1_sub4_sub4;
	}

	public IdentityKit() {
		anInt812 = -766;
		anInt813 = 256;
		anInt816 = -1;
		anIntArray818 = new int[6];
		anIntArray819 = new int[6];
		aBoolean821 = false;
	}
}
