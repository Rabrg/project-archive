package com.runescape.sound;

import com.runescape.net.Buffer;

public class SoundTrackEnvelope {
	
	public boolean aBoolean573;
	public int anInt574;
	public int anIntArray575[];
	public int anIntArray576[];
	public int anInt577;
	public int anInt578;
	public int anInt579;
	public int anInt580;
	public int anInt581;
	public int anInt582;
	public int anInt583;
	public int anInt584;
	public static int anInt585;
	
	public void method308(byte byte0, Buffer class50_sub1_sub2) {
		anInt579 = class50_sub1_sub2.method521();
		if (byte0 == 6) {
			byte0 = 0;
		} else {
			throw new NullPointerException();
		}
		anInt577 = class50_sub1_sub2.method526();
		anInt578 = class50_sub1_sub2.method526();
		method309(class50_sub1_sub2, 0);
	}

	public void method309(Buffer class50_sub1_sub2, int i) {
		anInt574 = class50_sub1_sub2.method521();
		anIntArray575 = new int[anInt574];
		anIntArray576 = new int[anInt574];
		if (i != 0) {
			return;
		}
		for (int j = 0; j < anInt574; j++) {
			anIntArray575[j] = class50_sub1_sub2.method523();
			anIntArray576[j] = class50_sub1_sub2.method523();
		}

	}

	public void method310(boolean flag) {
		anInt580 = 0;
		anInt581 = 0;
		if (!flag) {
			return;
		} else {
			anInt582 = 0;
			anInt583 = 0;
			anInt584 = 0;
			return;
		}
	}

	public int method311(int i, int j) {
		if (i != 0) {
			for (int k = 1; k > 0; k++) {
				;
			}
		}
		if (anInt584 >= anInt580) {
			anInt583 = anIntArray576[anInt581++] << 15;
			if (anInt581 >= anInt574) {
				anInt581 = anInt574 - 1;
			}
			anInt580 = (int) ((anIntArray575[anInt581] / 65536D) * j);
			if (anInt580 > anInt584) {
				anInt582 = ((anIntArray576[anInt581] << 15) - anInt583) / (anInt580 - anInt584);
			}
		}
		anInt583 += anInt582;
		anInt584++;
		return anInt583 - anInt582 >> 15;
	}

	public SoundTrackEnvelope() {
		aBoolean573 = true;
	}
}
