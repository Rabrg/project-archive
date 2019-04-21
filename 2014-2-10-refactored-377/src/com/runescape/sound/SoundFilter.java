package com.runescape.sound;

import com.runescape.net.Buffer;

public class SoundFilter {
	
	public int anInt611;
	public boolean aBoolean612;
	public int anIntArray613[];
	public int anIntArrayArrayArray614[][][];
	public int anIntArrayArrayArray615[][][];
	public int anIntArray616[];
	public static float aFloatArrayArray617[][] = new float[2][8];
	public static int anIntArrayArray618[][] = new int[2][8];
	public static float aFloat619;
	public static int anInt620;
	
	public float method352(int i, int j, float f, int k) {
		float f1 = anIntArrayArrayArray615[i][0][k] + f
				* (anIntArrayArrayArray615[i][1][k] - anIntArrayArrayArray615[i][0][k]);
		f1 *= 0.001525879F;
		j = 34 / j;
		return 1.0F - (float) Math.pow(10D, -f1 / 20F);
	}

	public float method353(float f, int i) {
		float f1 = 32.7032F * (float) Math.pow(2D, f);
		if (i >= 0) {
			aBoolean612 = !aBoolean612;
		}
		return (f1 * 3.141593F) / 11025F;
	}

	public float method354(int i, int j, int k, float f) {
		if (j != 0) {
			anInt611 = -370;
		}
		float f1 = anIntArrayArrayArray614[k][0][i] + f
				* (anIntArrayArrayArray614[k][1][i] - anIntArrayArrayArray614[k][0][i]);
		f1 *= 0.0001220703F;
		return method353(f1, -335);
	}

	public int method355(int i, boolean flag, float f) {
		if (!flag) {
			for (int j = 1; j > 0; j++) {
				;
			}
		}
		if (i == 0) {
			float f1 = anIntArray616[0] + (anIntArray616[1] - anIntArray616[0]) * f;
			f1 *= 0.003051758F;
			SoundFilter.aFloat619 = (float) Math.pow(0.10000000000000001D, f1 / 20F);
			SoundFilter.anInt620 = (int) (SoundFilter.aFloat619 * 65536F);
		}
		if (anIntArray613[i] == 0) {
			return 0;
		}
		float f2 = method352(i, 849, f, 0);
		SoundFilter.aFloatArrayArray617[i][0] = -2F * f2 * (float) Math.cos(method354(0, 0, i, f));
		SoundFilter.aFloatArrayArray617[i][1] = f2 * f2;
		for (int k = 1; k < anIntArray613[i]; k++) {
			float f3 = method352(i, 849, f, k);
			float f4 = -2F * f3 * (float) Math.cos(method354(k, 0, i, f));
			float f5 = f3 * f3;
			SoundFilter.aFloatArrayArray617[i][k * 2 + 1] = SoundFilter.aFloatArrayArray617[i][k * 2 - 1] * f5;
			SoundFilter.aFloatArrayArray617[i][k * 2] = SoundFilter.aFloatArrayArray617[i][k * 2 - 1] * f4
					+ SoundFilter.aFloatArrayArray617[i][k * 2 - 2] * f5;
			for (int j1 = k * 2 - 1; j1 >= 2; j1--) {
				SoundFilter.aFloatArrayArray617[i][j1] += SoundFilter.aFloatArrayArray617[i][j1 - 1] * f4
						+ SoundFilter.aFloatArrayArray617[i][j1 - 2] * f5;
			}

			SoundFilter.aFloatArrayArray617[i][1] += SoundFilter.aFloatArrayArray617[i][0] * f4 + f5;
			SoundFilter.aFloatArrayArray617[i][0] += f4;
		}

		if (i == 0) {
			for (int l = 0; l < anIntArray613[0] * 2; l++) {
				SoundFilter.aFloatArrayArray617[0][l] *= SoundFilter.aFloat619;
			}

		}
		for (int i1 = 0; i1 < anIntArray613[i] * 2; i1++) {
			SoundFilter.anIntArrayArray618[i][i1] = (int) (SoundFilter.aFloatArrayArray617[i][i1] * 65536F);
		}

		return anIntArray613[i] * 2;
	}

	public void method356(int i, SoundTrackEnvelope class29, Buffer class50_sub1_sub2) {
		int j = class50_sub1_sub2.method521();
		anIntArray613[0] = j >> 4;
		while (i >= 0) {
			for (int k = 1; k > 0; k++) {
				;
			}
		}
		anIntArray613[1] = j & 0xf;
		if (j != 0) {
			anIntArray616[0] = class50_sub1_sub2.method523();
			anIntArray616[1] = class50_sub1_sub2.method523();
			int l = class50_sub1_sub2.method521();
			for (int i1 = 0; i1 < 2; i1++) {
				for (int j1 = 0; j1 < anIntArray613[i1]; j1++) {
					anIntArrayArrayArray614[i1][0][j1] = class50_sub1_sub2.method523();
					anIntArrayArrayArray615[i1][0][j1] = class50_sub1_sub2.method523();
				}

			}

			for (int k1 = 0; k1 < 2; k1++) {
				for (int l1 = 0; l1 < anIntArray613[k1]; l1++) {
					if ((l & 1 << k1 * 4 << l1) != 0) {
						anIntArrayArrayArray614[k1][1][l1] = class50_sub1_sub2.method523();
						anIntArrayArrayArray615[k1][1][l1] = class50_sub1_sub2.method523();
					} else {
						anIntArrayArrayArray614[k1][1][l1] = anIntArrayArrayArray614[k1][0][l1];
						anIntArrayArrayArray615[k1][1][l1] = anIntArrayArrayArray615[k1][0][l1];
					}
				}

			}

			if (l != 0 || anIntArray616[1] != anIntArray616[0]) {
				class29.method309(class50_sub1_sub2, 0);
			}
			return;
		} else {
			anIntArray616[0] = anIntArray616[1] = 0;
			return;
		}
	}

	public SoundFilter() {
		aBoolean612 = true;
		anIntArray613 = new int[2];
		anIntArrayArrayArray614 = new int[2][2][4];
		anIntArrayArrayArray615 = new int[2][2][4];
		anIntArray616 = new int[2];
	}
}
