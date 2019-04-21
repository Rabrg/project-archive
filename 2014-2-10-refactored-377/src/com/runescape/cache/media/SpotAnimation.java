package com.runescape.cache.media;

import com.runescape.cache.Archive;
import com.runescape.collection.Cache;
import com.runescape.media.renderable.Model;
import com.runescape.net.Buffer;

public class SpotAnimation {
	
	public static byte aByte550 = 6;
	public static boolean aBoolean551 = true;
	public int anInt552;
	public static int anInt553;
	public static SpotAnimation aClass27Array554[];
	public int anInt555;
	public int anInt556;
	public int anInt557;
	public AnimationSequence aClass14_558;
	public int anIntArray559[];
	public int anIntArray560[];
	public int anInt561;
	public int anInt562;
	public int anInt563;
	public int anInt564;
	public int anInt565;
	public static Cache aClass33_566 = new Cache(30, -572);
	
	public static void method305(Archive class2, int i) {
		Buffer class50_sub1_sub2 = new Buffer(true, class2.getFile("spotanim.dat", null));
		SpotAnimation.anInt553 = class50_sub1_sub2.method523();
		if (i != 36135) {
			SpotAnimation.aBoolean551 = !SpotAnimation.aBoolean551;
		}
		if (SpotAnimation.aClass27Array554 == null) {
			SpotAnimation.aClass27Array554 = new SpotAnimation[SpotAnimation.anInt553];
		}
		for (int j = 0; j < SpotAnimation.anInt553; j++) {
			if (SpotAnimation.aClass27Array554[j] == null) {
				SpotAnimation.aClass27Array554[j] = new SpotAnimation();
			}
			SpotAnimation.aClass27Array554[j].anInt555 = j;
			SpotAnimation.aClass27Array554[j].method306(SpotAnimation.aByte550, class50_sub1_sub2);
		}

	}

	public void method306(byte byte0, Buffer class50_sub1_sub2) {
		if (byte0 == 6) {
			byte0 = 0;
		} else {
			anInt552 = 458;
		}
		do {
			int i = class50_sub1_sub2.method521();
			if (i == 0) {
				return;
			}
			if (i == 1) {
				anInt556 = class50_sub1_sub2.method523();
			} else if (i == 2) {
				anInt557 = class50_sub1_sub2.method523();
				if (AnimationSequence.aClass14Array293 != null) {
					aClass14_558 = AnimationSequence.aClass14Array293[anInt557];
				}
			} else if (i == 4) {
				anInt561 = class50_sub1_sub2.method523();
			} else if (i == 5) {
				anInt562 = class50_sub1_sub2.method523();
			} else if (i == 6) {
				anInt563 = class50_sub1_sub2.method523();
			} else if (i == 7) {
				anInt564 = class50_sub1_sub2.method521();
			} else if (i == 8) {
				anInt565 = class50_sub1_sub2.method521();
			} else if (i >= 40 && i < 50) {
				anIntArray559[i - 40] = class50_sub1_sub2.method523();
			} else if (i >= 50 && i < 60) {
				anIntArray560[i - 50] = class50_sub1_sub2.method523();
			} else {
				System.out.println("Error unrecognised spotanim config code: " + i);
			}
		} while (true);
	}

	public Model method307() {
		Model class50_sub1_sub4_sub4 = (Model) SpotAnimation.aClass33_566.method345(anInt555);
		if (class50_sub1_sub4_sub4 != null) {
			return class50_sub1_sub4_sub4;
		}
		class50_sub1_sub4_sub4 = Model.method577(anInt556);
		if (class50_sub1_sub4_sub4 == null) {
			return null;
		}
		for (int i = 0; i < 6; i++) {
			if (anIntArray559[0] != 0) {
				class50_sub1_sub4_sub4.method591(anIntArray559[i], anIntArray560[i]);
			}
		}

		SpotAnimation.aClass33_566.method346(class50_sub1_sub4_sub4, anInt555, 5);
		return class50_sub1_sub4_sub4;
	}

	public SpotAnimation() {
		anInt552 = -214;
		anInt557 = -1;
		anIntArray559 = new int[6];
		anIntArray560 = new int[6];
		anInt561 = 128;
		anInt562 = 128;
	}
}
