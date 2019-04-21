package com.runescape.collection;

import com.runescape.util.SignLink;

public class HashTable {
	
	public boolean aBoolean398;
	public boolean aBoolean399;
	public int anInt400;
	public Node aClass50Array401[];
	
	public HashTable(byte byte0, int i) {
		aBoolean398 = true;
		aBoolean399 = true;
		anInt400 = i;
		aClass50Array401 = new Node[i];
		for (int j = 0; j < i; j++) {
			Node class50 = aClass50Array401[j] = new Node();
			class50.aClass50_834 = class50;
			class50.aClass50_835 = class50;
		}

		if (byte0 != 0) {
			for (int k = 1; k > 0; k++) {
				;
			}
		}
	}

	public Node method233(long l) {
		Node class50 = aClass50Array401[(int) (l & anInt400 - 1)];
		for (Node class50_1 = class50.aClass50_834; class50_1 != class50; class50_1 = class50_1.aClass50_834) {
			if (class50_1.aLong833 == l) {
				return class50_1;
			}
		}

		return null;
	}

	public void method234(int i, Node class50, long l) {
		try {
			if (class50.aClass50_835 != null) {
				class50.method442();
			}
			Node class50_1 = aClass50Array401[(int) (l & anInt400 - 1)];
			if (i != 6) {
				return;
			} else {
				class50.aClass50_835 = class50_1.aClass50_835;
				class50.aClass50_834 = class50_1;
				class50.aClass50_835.aClass50_834 = class50;
				class50.aClass50_834.aClass50_835 = class50;
				class50.aLong833 = l;
				return;
			}
		} catch (RuntimeException runtimeexception) {
			SignLink.reporterror("24638, " + i + ", " + class50 + ", " + l + ", " + runtimeexception.toString());
		}
		throw new RuntimeException();
	}
}
