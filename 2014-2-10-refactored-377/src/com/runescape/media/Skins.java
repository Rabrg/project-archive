package com.runescape.media;

import com.runescape.net.Buffer;

public class Skins {
	
	public int anInt697;
	public int anIntArray698[];
	public int anIntArrayArray699[][];
	
	public Skins(Buffer class50_sub1_sub2, int i) {
		anInt697 = class50_sub1_sub2.method521();
		if (i != 0) {
			throw new NullPointerException();
		}
		anIntArray698 = new int[anInt697];
		anIntArrayArray699 = new int[anInt697][];
		for (int j = 0; j < anInt697; j++) {
			anIntArray698[j] = class50_sub1_sub2.method521();
		}

		for (int k = 0; k < anInt697; k++) {
			int l = class50_sub1_sub2.method521();
			anIntArrayArray699[k] = new int[l];
			for (int i1 = 0; i1 < l; i1++) {
				anIntArrayArray699[k][i1] = class50_sub1_sub2.method521();
			}

		}

	}
}
