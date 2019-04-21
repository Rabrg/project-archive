package com.runescape.cache.media;

import com.runescape.cache.Archive;
import com.runescape.media.Animation;
import com.runescape.net.Buffer;

public class AnimationSequence {
	
	public static byte aByte290 = 6;
	public boolean aBoolean291;
	public static int anInt292;
	public static AnimationSequence aClass14Array293[];
	public int anInt294;
	public int anIntArray295[];
	public int anIntArray296[];
	public int anIntArray297[];
	public int anInt298;
	public int anIntArray299[];
	public boolean aBoolean300;
	public int anInt301;
	public int anInt302;
	public int anInt303;
	public int anInt304;
	public int anInt305;
	public int anInt306;
	public int anInt307;
	public int anInt308;
	public static int anInt309;
	
	public static void method204(Archive class2, int i) {
		Buffer class50_sub1_sub2 = new Buffer(true, class2.getFile("seq.dat", null));
		AnimationSequence.anInt292 = class50_sub1_sub2.method523();
		if (AnimationSequence.aClass14Array293 == null) {
			AnimationSequence.aClass14Array293 = new AnimationSequence[AnimationSequence.anInt292];
		}
		for (int j = 0; j < AnimationSequence.anInt292; j++) {
			if (AnimationSequence.aClass14Array293[j] == null) {
				AnimationSequence.aClass14Array293[j] = new AnimationSequence();
			}
			AnimationSequence.aClass14Array293[j].method206(AnimationSequence.aByte290, class50_sub1_sub2);
		}

		if (i == 36135) {
			;
		}
	}

	public int method205(int i, int j) {
		int k = anIntArray297[j];
		if (i != 0) {
			return 1;
		}
		if (k == 0) {
			Animation class21 = Animation.method238(anIntArray295[j]);
			if (class21 != null) {
				k = anIntArray297[j] = class21.anInt431;
			}
		}
		if (k == 0) {
			k = 1;
		}
		return k;
	}

	public void method206(byte byte0, Buffer class50_sub1_sub2) {
		if (byte0 == 6) {
			byte0 = 0;
		} else {
			aBoolean291 = !aBoolean291;
		}
		do {
			int i = class50_sub1_sub2.method521();
			if (i == 0) {
				break;
			}
			if (i == 1) {
				anInt294 = class50_sub1_sub2.method521();
				anIntArray295 = new int[anInt294];
				anIntArray296 = new int[anInt294];
				anIntArray297 = new int[anInt294];
				for (int j = 0; j < anInt294; j++) {
					anIntArray295[j] = class50_sub1_sub2.method523();
					anIntArray296[j] = class50_sub1_sub2.method523();
					if (anIntArray296[j] == 65535) {
						anIntArray296[j] = -1;
					}
					anIntArray297[j] = class50_sub1_sub2.method523();
				}

			} else if (i == 2) {
				anInt298 = class50_sub1_sub2.method523();
			} else if (i == 3) {
				int k = class50_sub1_sub2.method521();
				anIntArray299 = new int[k + 1];
				for (int l = 0; l < k; l++) {
					anIntArray299[l] = class50_sub1_sub2.method521();
				}

				anIntArray299[k] = 0x98967f;
			} else if (i == 4) {
				aBoolean300 = true;
			} else if (i == 5) {
				anInt301 = class50_sub1_sub2.method521();
			} else if (i == 6) {
				anInt302 = class50_sub1_sub2.method523();
			} else if (i == 7) {
				anInt303 = class50_sub1_sub2.method523();
			} else if (i == 8) {
				anInt304 = class50_sub1_sub2.method521();
			} else if (i == 9) {
				anInt305 = class50_sub1_sub2.method521();
			} else if (i == 10) {
				anInt306 = class50_sub1_sub2.method521();
			} else if (i == 11) {
				anInt307 = class50_sub1_sub2.method521();
			} else if (i == 12) {
				anInt308 = class50_sub1_sub2.method526();
			} else {
				System.out.println("Error unrecognised seq config code: " + i);
			}
		} while (true);
		if (anInt294 == 0) {
			anInt294 = 1;
			anIntArray295 = new int[1];
			anIntArray295[0] = -1;
			anIntArray296 = new int[1];
			anIntArray296[0] = -1;
			anIntArray297 = new int[1];
			anIntArray297[0] = -1;
		}
		if (anInt305 == -1) {
			if (anIntArray299 != null) {
				anInt305 = 2;
			} else {
				anInt305 = 0;
			}
		}
		if (anInt306 == -1) {
			if (anIntArray299 != null) {
				anInt306 = 2;
				return;
			}
			anInt306 = 0;
		}
	}

	public AnimationSequence() {
		aBoolean291 = false;
		anInt298 = -1;
		aBoolean300 = false;
		anInt301 = 5;
		anInt302 = -1;
		anInt303 = -1;
		anInt304 = 99;
		anInt305 = -1;
		anInt306 = -1;
		anInt307 = 2;
	}
}
