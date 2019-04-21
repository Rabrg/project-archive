package com.runescape.collection;

import com.runescape.util.SignLink;

public class Cache {
	
	public int anInt592;
	public int anInt593;
	public int anInt594;
	public CacheableNode aClass50_Sub1_595;
	public int anInt596;
	public int anInt597;
	public HashTable aClass19_598;
	public Queue aClass9_599;
	
	public Cache(int i, int j) {
		anInt592 = 256;
		aClass50_Sub1_595 = new CacheableNode();
		aClass9_599 = new Queue(true);
		anInt596 = i;
		if (j >= 0) {
			anInt592 = 433;
		}
		anInt597 = i;
		aClass19_598 = new HashTable((byte) 0, 1024);
	}

	public CacheableNode method345(long l) {
		CacheableNode class50_sub1 = (CacheableNode) aClass19_598.method233(l);
		if (class50_sub1 != null) {
			aClass9_599.method185(class50_sub1);
			anInt594++;
		} else {
			anInt593++;
		}
		return class50_sub1;
	}

	public void method346(CacheableNode class50_sub1, long l, int i) {
		try {
			if (i != 5) {
				anInt592 = 150;
			}
			if (anInt597 == 0) {
				CacheableNode class50_sub1_1 = aClass9_599.method186();
				class50_sub1_1.method442();
				class50_sub1_1.method443();
				if (class50_sub1_1 == aClass50_Sub1_595) {
					CacheableNode class50_sub1_2 = aClass9_599.method186();
					class50_sub1_2.method442();
					class50_sub1_2.method443();
				}
			} else {
				anInt597--;
			}
			aClass19_598.method234(6, class50_sub1, l);
			aClass9_599.method185(class50_sub1);
			return;
		} catch (RuntimeException runtimeexception) {
			SignLink.reporterror("76413, " + class50_sub1 + ", " + l + ", " + i + ", " + runtimeexception.toString());
		}
		throw new RuntimeException();
	}

	public void method347() {
		do {
			CacheableNode class50_sub1 = aClass9_599.method186();
			if (class50_sub1 != null) {
				class50_sub1.method442();
				class50_sub1.method443();
			} else {
				anInt597 = anInt596;
				return;
			}
		} while (true);
	}
}
