package com.runescape.collection;

public class Queue {
	
	public boolean aBoolean170;
	public CacheableNode aClass50_Sub1_171;
	public CacheableNode aClass50_Sub1_172;
	
	public Queue(boolean flag) {
		aBoolean170 = true;
		aClass50_Sub1_171 = new CacheableNode();
		aClass50_Sub1_171.aClass50_Sub1_1381 = aClass50_Sub1_171;
		aClass50_Sub1_171.aClass50_Sub1_1382 = aClass50_Sub1_171;
		if (!flag) {
			for (int i = 1; i > 0; i++) {
				;
			}
		}
	}

	public void method185(CacheableNode class50_sub1) {
		if (class50_sub1.aClass50_Sub1_1382 != null) {
			class50_sub1.method443();
		}
		class50_sub1.aClass50_Sub1_1382 = aClass50_Sub1_171.aClass50_Sub1_1382;
		class50_sub1.aClass50_Sub1_1381 = aClass50_Sub1_171;
		class50_sub1.aClass50_Sub1_1382.aClass50_Sub1_1381 = class50_sub1;
		class50_sub1.aClass50_Sub1_1381.aClass50_Sub1_1382 = class50_sub1;
	}

	public CacheableNode method186() {
		CacheableNode class50_sub1 = aClass50_Sub1_171.aClass50_Sub1_1381;
		if (class50_sub1 == aClass50_Sub1_171) {
			return null;
		} else {
			class50_sub1.method443();
			return class50_sub1;
		}
	}

	public CacheableNode method187() {
		CacheableNode class50_sub1 = aClass50_Sub1_171.aClass50_Sub1_1381;
		if (class50_sub1 == aClass50_Sub1_171) {
			aClass50_Sub1_172 = null;
			return null;
		} else {
			aClass50_Sub1_172 = class50_sub1.aClass50_Sub1_1381;
			return class50_sub1;
		}
	}

	public CacheableNode method188(int i) {
		CacheableNode class50_sub1 = aClass50_Sub1_172;
		if (class50_sub1 == aClass50_Sub1_171) {
			aClass50_Sub1_172 = null;
			return null;
		}
		aClass50_Sub1_172 = class50_sub1.aClass50_Sub1_1381;
		if (i < 1 || i > 1) {
			aBoolean170 = !aBoolean170;
		}
		return class50_sub1;
	}

	public int method189() {
		int i = 0;
		for (CacheableNode class50_sub1 = aClass50_Sub1_171.aClass50_Sub1_1381; class50_sub1 != aClass50_Sub1_171; class50_sub1 = class50_sub1.aClass50_Sub1_1381) {
			i++;
		}

		return i;
	}
}
