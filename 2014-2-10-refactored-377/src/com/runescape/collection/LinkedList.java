package com.runescape.collection;

public class LinkedList {
	
	public int anInt127;
	public int anInt128;
	public Node aClass50_129;
	public Node aClass50_130;
	
	public LinkedList(boolean flag) {
		anInt127 = -48545;
		aClass50_129 = new Node();
		if (!flag) {
			for (int i = 1; i > 0; i++) {
				;
			}
		}
		aClass50_129.aClass50_834 = aClass50_129;
		aClass50_129.aClass50_835 = aClass50_129;
	}

	public void method155(Node class50) {
		if (class50.aClass50_835 != null) {
			class50.method442();
		}
		class50.aClass50_835 = aClass50_129.aClass50_835;
		class50.aClass50_834 = aClass50_129;
		class50.aClass50_835.aClass50_834 = class50;
		class50.aClass50_834.aClass50_835 = class50;
	}

	public void method156(byte byte0, Node class50) {
		if (byte0 != -57) {
			anInt128 = -23;
		}
		if (class50.aClass50_835 != null) {
			class50.method442();
		}
		class50.aClass50_835 = aClass50_129;
		class50.aClass50_834 = aClass50_129.aClass50_834;
		class50.aClass50_835.aClass50_834 = class50;
		class50.aClass50_834.aClass50_835 = class50;
	}

	public Node method157() {
		Node class50 = aClass50_129.aClass50_834;
		if (class50 == aClass50_129) {
			return null;
		} else {
			class50.method442();
			return class50;
		}
	}

	public Node method158() {
		Node class50 = aClass50_129.aClass50_834;
		if (class50 == aClass50_129) {
			aClass50_130 = null;
			return null;
		} else {
			aClass50_130 = class50.aClass50_834;
			return class50;
		}
	}

	public Node method159(boolean flag) {
		Node class50 = aClass50_129.aClass50_835;
		if (flag) {
			for (int i = 1; i > 0; i++) {
				;
			}
		}
		if (class50 == aClass50_129) {
			aClass50_130 = null;
			return null;
		} else {
			aClass50_130 = class50.aClass50_835;
			return class50;
		}
	}

	public Node method160(int i) {
		Node class50 = aClass50_130;
		if (class50 == aClass50_129) {
			aClass50_130 = null;
			return null;
		}
		aClass50_130 = class50.aClass50_834;
		if (i != 1) {
			throw new NullPointerException();
		} else {
			return class50;
		}
	}

	public Node method161(int i) {
		Node class50 = aClass50_130;
		if (i <= 0) {
			throw new NullPointerException();
		}
		if (class50 == aClass50_129) {
			aClass50_130 = null;
			return null;
		} else {
			aClass50_130 = class50.aClass50_835;
			return class50;
		}
	}

	public void method162() {
		if (aClass50_129.aClass50_834 == aClass50_129) {
			return;
		}
		do {
			Node class50 = aClass50_129.aClass50_834;
			if (class50 == aClass50_129) {
				return;
			}
			class50.method442();
		} while (true);
	}
}
