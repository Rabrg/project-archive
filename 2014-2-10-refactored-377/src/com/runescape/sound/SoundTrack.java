package com.runescape.sound;

import com.runescape.net.Buffer;

public class SoundTrack {
	
	public static byte aByte664 = 6;
	public int anInt665;
	public int anInt666;
	public static boolean aBoolean667 = true;
	public static SoundTrack aClass38Array668[] = new SoundTrack[5000];
	public static int anIntArray669[] = new int[5000];
	public static byte aByteArray670[];
	public static Buffer aClass50_Sub1_Sub2_671;
	public SoundTrackInstrument aClass11Array672[];
	public int anInt673;
	public int anInt674;
	
	public SoundTrack(int i) {
		anInt665 = -573;
		anInt666 = -252;
		for (aClass11Array672 = new SoundTrackInstrument[10]; i >= 0;) {
			throw new NullPointerException();
		}

	}

	public static void method365(Buffer class50_sub1_sub2, int i) {
		if (i != 36135) {
			return;
		}
		SoundTrack.aByteArray670 = new byte[0x6baa8];
		SoundTrack.aClass50_Sub1_Sub2_671 = new Buffer(true, SoundTrack.aByteArray670);
		SoundTrackInstrument.method190();
		do {
			int j = class50_sub1_sub2.method523();
			if (j == 65535) {
				return;
			}
			SoundTrack.aClass38Array668[j] = new SoundTrack(-524);
			SoundTrack.aClass38Array668[j].method367(SoundTrack.aByte664, class50_sub1_sub2);
			SoundTrack.anIntArray669[j] = SoundTrack.aClass38Array668[j].method368(0);
		} while (true);
	}

	public static Buffer method366(int i, byte byte0, int j) {
		if (byte0 != 6) {
			SoundTrack.aBoolean667 = !SoundTrack.aBoolean667;
		}
		if (SoundTrack.aClass38Array668[j] != null) {
			SoundTrack class38 = SoundTrack.aClass38Array668[j];
			return class38.method369(-573, i);
		} else {
			return null;
		}
	}

	public void method367(byte byte0, Buffer class50_sub1_sub2) {
		for (int i = 0; i < 10; i++) {
			int j = class50_sub1_sub2.method521();
			if (j != 0) {
				class50_sub1_sub2.position--;
				aClass11Array672[i] = new SoundTrackInstrument();
				aClass11Array672[i].method193(SoundTrack.aByte664, class50_sub1_sub2);
			}
		}

		anInt673 = class50_sub1_sub2.method523();
		anInt674 = class50_sub1_sub2.method523();
		if (byte0 == 6) {
			byte0 = 0;
			return;
		} else {
			anInt666 = 64;
			return;
		}
	}

	public int method368(int i) {
		int j = 0x98967f;
		for (int k = 0; k < 10; k++) {
			if (aClass11Array672[k] != null && aClass11Array672[k].anInt199 / 20 < j) {
				j = aClass11Array672[k].anInt199 / 20;
			}
		}

		if (anInt673 < anInt674 && anInt673 / 20 < j) {
			j = anInt673 / 20;
		}
		if (j == 0x98967f || j == 0) {
			return 0;
		}
		for (int l = 0; l < 10; l++) {
			if (aClass11Array672[l] != null) {
				aClass11Array672[l].anInt199 -= j * 20;
			}
		}

		if (i != 0) {
			SoundTrack.aBoolean667 = !SoundTrack.aBoolean667;
		}
		if (anInt673 < anInt674) {
			anInt673 -= j * 20;
			anInt674 -= j * 20;
		}
		return j;
	}

	public Buffer method369(int i, int j) {
		int k = method370(j);
		SoundTrack.aClass50_Sub1_Sub2_671.position = 0;
		SoundTrack.aClass50_Sub1_Sub2_671.method515(0x52494646);
		SoundTrack.aClass50_Sub1_Sub2_671.method516(36 + k);
		SoundTrack.aClass50_Sub1_Sub2_671.method515(0x57415645);
		SoundTrack.aClass50_Sub1_Sub2_671.method515(0x666d7420);
		if (i >= 0) {
			throw new NullPointerException();
		} else {
			SoundTrack.aClass50_Sub1_Sub2_671.method516(16);
			SoundTrack.aClass50_Sub1_Sub2_671.method513(1, false);
			SoundTrack.aClass50_Sub1_Sub2_671.method513(1, false);
			SoundTrack.aClass50_Sub1_Sub2_671.method516(22050);
			SoundTrack.aClass50_Sub1_Sub2_671.method516(22050);
			SoundTrack.aClass50_Sub1_Sub2_671.method513(1, false);
			SoundTrack.aClass50_Sub1_Sub2_671.method513(8, false);
			SoundTrack.aClass50_Sub1_Sub2_671.method515(0x64617461);
			SoundTrack.aClass50_Sub1_Sub2_671.method516(k);
			SoundTrack.aClass50_Sub1_Sub2_671.position += k;
			return SoundTrack.aClass50_Sub1_Sub2_671;
		}
	}

	public int method370(int i) {
		int j = 0;
		for (int k = 0; k < 10; k++) {
			if (aClass11Array672[k] != null && aClass11Array672[k].anInt198 + aClass11Array672[k].anInt199 > j) {
				j = aClass11Array672[k].anInt198 + aClass11Array672[k].anInt199;
			}
		}

		if (j == 0) {
			return 0;
		}
		int l = (22050 * j) / 1000;
		int i1 = (22050 * anInt673) / 1000;
		int j1 = (22050 * anInt674) / 1000;
		if (i1 < 0 || i1 > l || j1 < 0 || j1 > l || i1 >= j1) {
			i = 0;
		}
		int k1 = l + (j1 - i1) * (i - 1);
		for (int l1 = 44; l1 < k1 + 44; l1++) {
			SoundTrack.aByteArray670[l1] = -128;
		}

		for (int i2 = 0; i2 < 10; i2++) {
			if (aClass11Array672[i2] != null) {
				int j2 = (aClass11Array672[i2].anInt198 * 22050) / 1000;
				int i3 = (aClass11Array672[i2].anInt199 * 22050) / 1000;
				int ai[] = aClass11Array672[i2].method191(j2, aClass11Array672[i2].anInt198);
				for (int l3 = 0; l3 < j2; l3++) {
					int j4 = (SoundTrack.aByteArray670[l3 + i3 + 44] & 0xff) + (ai[l3] >> 8);
					if ((j4 & 0xffffff00) != 0) {
						j4 = ~(j4 >> 31);
					}
					SoundTrack.aByteArray670[l3 + i3 + 44] = (byte) j4;
				}

			}
		}

		if (i > 1) {
			i1 += 44;
			j1 += 44;
			l += 44;
			int k2 = (k1 += 44) - l;
			for (int j3 = l - 1; j3 >= j1; j3--) {
				SoundTrack.aByteArray670[j3 + k2] = SoundTrack.aByteArray670[j3];
			}

			for (int k3 = 1; k3 < i; k3++) {
				int l2 = (j1 - i1) * k3;
				for (int i4 = i1; i4 < j1; i4++) {
					SoundTrack.aByteArray670[i4 + l2] = SoundTrack.aByteArray670[i4];
				}

			}

			k1 -= 44;
		}
		return k1;
	}
}
