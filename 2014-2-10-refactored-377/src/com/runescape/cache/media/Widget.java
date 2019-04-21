package com.runescape.cache.media;

import com.runescape.Game;
import com.runescape.cache.Archive;
import com.runescape.cache.def.ActorDefinition;
import com.runescape.cache.def.ItemDefinition;
import com.runescape.collection.Cache;
import com.runescape.media.Animation;
import com.runescape.media.renderable.Model;
import com.runescape.net.Buffer;
import com.runescape.util.TextUtilities;

public class Widget {
	
	public static int anInt210;
	public String aString211;
	public ImageRGB aClass50_Sub1_Sub1_Sub1_212;
	public int anIntArray213[];
	public static Archive aClass2_214;
	public int anInt215;
	public static Widget aClass13Array216[];
	public boolean aBoolean217;
	public int anInt218;
	public boolean aBoolean219;
	public byte aByte220;
	public int anIntArray221[];
	public int anInt222;
	public static TypeFace aClass50_Sub1_Sub1_Sub2Array223[];
	public int anIntArray224[];
	public int anInt225;
	public int anInt226;
	public int anInt227;
	public int anInt228;
	public boolean aBoolean229;
	public String aString230;
	public int anInt231;
	public int anIntArray232[];
	public boolean aBoolean233;
	public int anIntArrayArray234[][];
	public int anInt235;
	public int anInt236;
	public TypeFace aClass50_Sub1_Sub1_Sub2_237;
	public int anInt238;
	public boolean aBoolean239;
	public int anInt240;
	public int anInt241;
	public int anInt242;
	public static int anInt243;
	public int anInt244;
	public ImageRGB aClass50_Sub1_Sub1_Sub1_245;
	public static int anInt246 = -1;
	public boolean aBoolean247;
	public int anInt248;
	public String aString249;
	public static Cache aClass33_250;
	public int anInt251;
	public int anInt252;
	public int anInt253;
	public int anInt254;
	public static int anInt255 = -1;
	public int anIntArray256[];
	public static boolean aBoolean257;
	public int anIntArray258[];
	public int anInt259;
	public int anInt260;
	public int anInt261;
	public String aStringArray262[];
	public int anInt263;
	public static Cache aClass33_264 = new Cache(30, -572);
	public ImageRGB aClass50_Sub1_Sub1_Sub1Array265[];
	public int anInt266;
	public int anInt267;
	public String aString268;
	public int anIntArray269[];
	public int anInt270;
	public boolean aBoolean271;
	public boolean aBoolean272;
	public int anIntArray273[];
	public boolean aBoolean274;
	public static int anInt275 = -291;
	public int anIntArray276[];
	public static int anInt277 = -1;
	public static boolean aBoolean278 = true;
	public static int anInt279 = 373;
	public static int anInt280;
	public String aString281;
	public static byte aByteArrayArray282[][];
	public int anInt283;
	public int anInt284;
	public int anInt285;
	public int anInt286;
	public int anInt287;
	public boolean aBoolean288;
	public int anInt289;
	
	public static ImageRGB method194(int i, String s, int j) {
		long l = (TextUtilities.method301(183, s) << 8) + i;
		if (j <= 0) {
			Widget.anInt275 = -317;
		}
		ImageRGB class50_sub1_sub1_sub1 = (ImageRGB) Widget.aClass33_250.method345(l);
		if (class50_sub1_sub1_sub1 != null) {
			return class50_sub1_sub1_sub1;
		}
		if (Widget.aClass2_214 == null) {
			return null;
		}
		try {
			class50_sub1_sub1_sub1 = new ImageRGB(Widget.aClass2_214, s, i);
			Widget.aClass33_250.method346(class50_sub1_sub1_sub1, l, 5);
		} catch (Exception _ex) {
			return null;
		}
		return class50_sub1_sub1_sub1;
	}

	public static Widget method195(int i) {
		if (Widget.aClass13Array216[i] == null) {
			Buffer class50_sub1_sub2 = new Buffer(true, Widget.aByteArrayArray282[i]);
			int j = class50_sub1_sub2.method523();
			Widget.aClass13Array216[i] = Widget.method198(j, class50_sub1_sub2, 10896, i);
		}
		return Widget.aClass13Array216[i];
	}

	public void method196(int i, int j, int k) {
		int l = anIntArray269[k];
		anIntArray269[k] = anIntArray269[i];
		anIntArray269[i] = l;
		l = anIntArray224[k];
		anIntArray224[k] = anIntArray224[i];
		if (j >= 0) {
			return;
		} else {
			anIntArray224[i] = l;
			return;
		}
	}

	public Model method197(int i, int j) {
		ItemDefinition class16 = null;
		if (i == 4) {
			class16 = ItemDefinition.method212(j);
			Widget.anInt280 += class16.anInt354;
			Widget.anInt243 += class16.anInt358;
		}
		Model class50_sub1_sub4_sub4 = (Model) Widget.aClass33_264.method345((i << 16) + j);
		if (class50_sub1_sub4_sub4 != null) {
			return class50_sub1_sub4_sub4;
		}
		if (i == 1) {
			class50_sub1_sub4_sub4 = Model.method577(j);
		}
		if (i == 2) {
			class50_sub1_sub4_sub4 = ActorDefinition.method364(j).method359(858);
		}
		if (i == 3) {
			class50_sub1_sub4_sub4 = Game.aClass50_Sub1_Sub4_Sub3_Sub2_1167.method570(true);
		}
		if (i == 4) {
			class50_sub1_sub4_sub4 = class16.method217(anInt270, 50);
		}
		if (i == 5) {
			class50_sub1_sub4_sub4 = null;
		}
		if (class50_sub1_sub4_sub4 != null) {
			Widget.aClass33_264.method346(class50_sub1_sub4_sub4, (i << 16) + j, 5);
		}
		return class50_sub1_sub4_sub4;
	}

	public static Widget method198(int i, Buffer class50_sub1_sub2, int j, int k) {
		Widget class13 = new Widget();
		class13.anInt215 = k;
		class13.anInt248 = i;
		class13.anInt236 = class50_sub1_sub2.method521();
		class13.anInt289 = class50_sub1_sub2.method521();
		class13.anInt242 = class50_sub1_sub2.method523();
		class13.anInt241 = class50_sub1_sub2.method523();
		class13.anInt238 = class50_sub1_sub2.method523();
		if (j != 10896) {
			Widget.aBoolean278 = !Widget.aBoolean278;
		}
		class13.aByte220 = (byte) class50_sub1_sub2.method521();
		class13.anInt254 = class50_sub1_sub2.method521();
		if (class13.anInt254 != 0) {
			class13.anInt254 = (class13.anInt254 - 1 << 8) + class50_sub1_sub2.method521();
		} else {
			class13.anInt254 = -1;
		}
		if (class13.anInt242 == 600) {
			Widget.anInt246 = i;
		}
		if (class13.anInt242 == 650) {
			Widget.anInt255 = i;
		}
		if (class13.anInt242 == 655) {
			Widget.anInt277 = i;
		}
		int l = class50_sub1_sub2.method521();
		if (l > 0) {
			class13.anIntArray273 = new int[l];
			class13.anIntArray256 = new int[l];
			for (int i1 = 0; i1 < l; i1++) {
				class13.anIntArray273[i1] = class50_sub1_sub2.method521();
				class13.anIntArray256[i1] = class50_sub1_sub2.method523();
			}

		}
		int j1 = class50_sub1_sub2.method521();
		if (j1 > 0) {
			class13.anIntArrayArray234 = new int[j1][];
			for (int k1 = 0; k1 < j1; k1++) {
				int l2 = class50_sub1_sub2.method523();
				class13.anIntArrayArray234[k1] = new int[l2];
				for (int k4 = 0; k4 < l2; k4++) {
					class13.anIntArrayArray234[k1][k4] = class50_sub1_sub2.method523();
				}

			}

		}
		if (class13.anInt236 == 0) {
			class13.anInt285 = class50_sub1_sub2.method523();
			class13.aBoolean219 = class50_sub1_sub2.method521() == 1;
			int l1 = class50_sub1_sub2.method523();
			class13.anIntArray258 = new int[l1];
			class13.anIntArray232 = new int[l1];
			class13.anIntArray276 = new int[l1];
			for (int i3 = 0; i3 < l1; i3++) {
				class13.anIntArray258[i3] = class50_sub1_sub2.method523();
				class13.anIntArray232[i3] = class50_sub1_sub2.method524();
				class13.anIntArray276[i3] = class50_sub1_sub2.method524();
			}

		}
		if (class13.anInt236 == 1) {
			class13.anInt225 = class50_sub1_sub2.method523();
			class13.aBoolean233 = class50_sub1_sub2.method521() == 1;
		}
		if (class13.anInt236 == 2) {
			class13.anIntArray269 = new int[class13.anInt241 * class13.anInt238];
			class13.anIntArray224 = new int[class13.anInt241 * class13.anInt238];
			class13.aBoolean274 = class50_sub1_sub2.method521() == 1;
			class13.aBoolean229 = class50_sub1_sub2.method521() == 1;
			class13.aBoolean288 = class50_sub1_sub2.method521() == 1;
			class13.aBoolean217 = class50_sub1_sub2.method521() == 1;
			class13.anInt263 = class50_sub1_sub2.method521();
			class13.anInt244 = class50_sub1_sub2.method521();
			class13.anIntArray221 = new int[20];
			class13.anIntArray213 = new int[20];
			class13.aClass50_Sub1_Sub1_Sub1Array265 = new ImageRGB[20];
			for (int i2 = 0; i2 < 20; i2++) {
				int j3 = class50_sub1_sub2.method521();
				if (j3 == 1) {
					class13.anIntArray221[i2] = class50_sub1_sub2.method524();
					class13.anIntArray213[i2] = class50_sub1_sub2.method524();
					String s1 = class50_sub1_sub2.method528();
					if (s1.length() > 0) {
						int l4 = s1.lastIndexOf(",");
						class13.aClass50_Sub1_Sub1_Sub1Array265[i2] = Widget.method194(
								Integer.parseInt(s1.substring(l4 + 1)), s1.substring(0, l4), 373);
					}
				}
			}

			class13.aStringArray262 = new String[5];
			for (int k3 = 0; k3 < 5; k3++) {
				class13.aStringArray262[k3] = class50_sub1_sub2.method528();
				if (class13.aStringArray262[k3].length() == 0) {
					class13.aStringArray262[k3] = null;
				}
			}

		}
		if (class13.anInt236 == 3) {
			class13.aBoolean239 = class50_sub1_sub2.method521() == 1;
		}
		if (class13.anInt236 == 4 || class13.anInt236 == 1) {
			class13.aBoolean272 = class50_sub1_sub2.method521() == 1;
			int j2 = class50_sub1_sub2.method521();
			if (Widget.aClass50_Sub1_Sub1_Sub2Array223 != null) {
				class13.aClass50_Sub1_Sub1_Sub2_237 = Widget.aClass50_Sub1_Sub1_Sub2Array223[j2];
			}
			class13.aBoolean247 = class50_sub1_sub2.method521() == 1;
		}
		if (class13.anInt236 == 4) {
			class13.aString230 = class50_sub1_sub2.method528();
			class13.aString249 = class50_sub1_sub2.method528();
		}
		if (class13.anInt236 == 1 || class13.anInt236 == 3 || class13.anInt236 == 4) {
			class13.anInt240 = class50_sub1_sub2.method526();
		}
		if (class13.anInt236 == 3 || class13.anInt236 == 4) {
			class13.anInt260 = class50_sub1_sub2.method526();
			class13.anInt261 = class50_sub1_sub2.method526();
			class13.anInt226 = class50_sub1_sub2.method526();
		}
		if (class13.anInt236 == 5) {
			String s = class50_sub1_sub2.method528();
			if (s.length() > 0) {
				int l3 = s.lastIndexOf(",");
				class13.aClass50_Sub1_Sub1_Sub1_212 = Widget.method194(Integer.parseInt(s.substring(l3 + 1)),
						s.substring(0, l3), 373);
			}
			s = class50_sub1_sub2.method528();
			if (s.length() > 0) {
				int i4 = s.lastIndexOf(",");
				class13.aClass50_Sub1_Sub1_Sub1_245 = Widget.method194(Integer.parseInt(s.substring(i4 + 1)),
						s.substring(0, i4), 373);
			}
		}
		if (class13.anInt236 == 6) {
			k = class50_sub1_sub2.method521();
			if (k != 0) {
				class13.anInt283 = 1;
				class13.anInt284 = (k - 1 << 8) + class50_sub1_sub2.method521();
			}
			k = class50_sub1_sub2.method521();
			if (k != 0) {
				class13.anInt266 = 1;
				class13.anInt267 = (k - 1 << 8) + class50_sub1_sub2.method521();
			}
			k = class50_sub1_sub2.method521();
			if (k != 0) {
				class13.anInt286 = (k - 1 << 8) + class50_sub1_sub2.method521();
			} else {
				class13.anInt286 = -1;
			}
			k = class50_sub1_sub2.method521();
			if (k != 0) {
				class13.anInt287 = (k - 1 << 8) + class50_sub1_sub2.method521();
			} else {
				class13.anInt287 = -1;
			}
			class13.anInt251 = class50_sub1_sub2.method523();
			class13.anInt252 = class50_sub1_sub2.method523();
			class13.anInt253 = class50_sub1_sub2.method523();
		}
		if (class13.anInt236 == 7) {
			class13.anIntArray269 = new int[class13.anInt241 * class13.anInt238];
			class13.anIntArray224 = new int[class13.anInt241 * class13.anInt238];
			class13.aBoolean272 = class50_sub1_sub2.method521() == 1;
			int k2 = class50_sub1_sub2.method521();
			if (Widget.aClass50_Sub1_Sub1_Sub2Array223 != null) {
				class13.aClass50_Sub1_Sub1_Sub2_237 = Widget.aClass50_Sub1_Sub1_Sub2Array223[k2];
			}
			class13.aBoolean247 = class50_sub1_sub2.method521() == 1;
			class13.anInt240 = class50_sub1_sub2.method526();
			class13.anInt263 = class50_sub1_sub2.method524();
			class13.anInt244 = class50_sub1_sub2.method524();
			class13.aBoolean229 = class50_sub1_sub2.method521() == 1;
			class13.aStringArray262 = new String[5];
			for (int j4 = 0; j4 < 5; j4++) {
				class13.aStringArray262[j4] = class50_sub1_sub2.method528();
				if (class13.aStringArray262[j4].length() == 0) {
					class13.aStringArray262[j4] = null;
				}
			}

		}
		if (class13.anInt236 == 8) {
			class13.aString230 = class50_sub1_sub2.method528();
		}
		if (class13.anInt289 == 2 || class13.anInt236 == 2) {
			class13.aString281 = class50_sub1_sub2.method528();
			class13.aString211 = class50_sub1_sub2.method528();
			class13.anInt222 = class50_sub1_sub2.method523();
		}
		if (class13.anInt289 == 1 || class13.anInt289 == 4 || class13.anInt289 == 5 || class13.anInt289 == 6) {
			class13.aString268 = class50_sub1_sub2.method528();
			if (class13.aString268.length() == 0) {
				if (class13.anInt289 == 1) {
					class13.aString268 = "Ok";
				}
				if (class13.anInt289 == 4) {
					class13.aString268 = "Select";
				}
				if (class13.anInt289 == 5) {
					class13.aString268 = "Select";
				}
				if (class13.anInt289 == 6) {
					class13.aString268 = "Continue";
				}
			}
		}
		return class13;
	}

	public static void method199(int i, TypeFace aclass50_sub1_sub1_sub2[], Archive class2, Archive class2_1) {
		Widget.aClass33_250 = new Cache(50000, -572);
		Widget.aClass2_214 = class2_1;
		Widget.aClass50_Sub1_Sub1_Sub2Array223 = aclass50_sub1_sub1_sub2;
		int j = -1;
		Buffer class50_sub1_sub2 = new Buffer(true, class2.getFile("data", null));
		int k = class50_sub1_sub2.method523();
		Widget.aClass13Array216 = new Widget[k];
		Widget.aByteArrayArray282 = new byte[k][];
		while (class50_sub1_sub2.position < class50_sub1_sub2.buffer.length) {
			int l = class50_sub1_sub2.method523();
			if (l == 65535) {
				j = class50_sub1_sub2.method523();
				l = class50_sub1_sub2.method523();
			}
			int i1 = class50_sub1_sub2.position;
			Widget class13 = Widget.method198(j, class50_sub1_sub2, 10896, l);
			byte abyte0[] = Widget.aByteArrayArray282[class13.anInt215] = new byte[(class50_sub1_sub2.position - i1) + 2];
			for (int j1 = i1; j1 < class50_sub1_sub2.position; j1++) {
				abyte0[(j1 - i1) + 2] = class50_sub1_sub2.buffer[j1];
			}

			abyte0[0] = (byte) (j >> 8);
			abyte0[1] = (byte) j;
		}
		Widget.aClass2_214 = null;
		if (i >= 0) {
			Widget.anInt210 = 391;
		}
	}

	public static void method200(boolean flag, int i) {
		if (!flag) {
			Widget.aBoolean257 = !Widget.aBoolean257;
		}
		if (i == -1) {
			return;
		}
		for (int j = 0; j < Widget.aClass13Array216.length; j++) {
			if (Widget.aClass13Array216[j] != null && Widget.aClass13Array216[j].anInt248 == i
					&& Widget.aClass13Array216[j].anInt236 != 2) {
				Widget.aClass13Array216[j] = null;
			}
		}

	}

	public static void method201(int i, Model class50_sub1_sub4_sub4, int j, int k) {
		Widget.aClass33_264.method347();
		if (k != 6) {
			for (int l = 1; l > 0; l++) {
				;
			}
		}
		if (class50_sub1_sub4_sub4 != null && i != 4) {
			Widget.aClass33_264.method346(class50_sub1_sub4_sub4, (i << 16) + j, 5);
		}
	}

	public static void method202(boolean flag) {
		Widget.aClass13Array216 = null;
		Widget.aClass2_214 = null;
		if (flag) {
			Widget.aBoolean257 = !Widget.aBoolean257;
		}
		Widget.aClass33_250 = null;
		Widget.aClass50_Sub1_Sub1_Sub2Array223 = null;
		Widget.aByteArrayArray282 = null;
	}

	public Model method203(int i, int j, int k, boolean flag) {
		Widget.anInt280 = 64;
		Widget.anInt243 = 768;
		Model class50_sub1_sub4_sub4;
		if (flag) {
			class50_sub1_sub4_sub4 = method197(anInt266, anInt267);
		} else {
			class50_sub1_sub4_sub4 = method197(anInt283, anInt284);
		}
		if (class50_sub1_sub4_sub4 == null) {
			return null;
		}
		if (i == -1 && j == -1 && class50_sub1_sub4_sub4.anIntArray1662 == null) {
			return class50_sub1_sub4_sub4;
		}
		Model class50_sub1_sub4_sub4_1 = new Model(false, false, true, class50_sub1_sub4_sub4, Animation.method239(
				aBoolean271, i) & Animation.method239(aBoolean271, j));
		if (k != 0) {
			aBoolean271 = !aBoolean271;
		}
		if (i != -1 || j != -1) {
			class50_sub1_sub4_sub4_1.method584(7);
		}
		if (i != -1) {
			class50_sub1_sub4_sub4_1.method585(i, (byte) 6);
		}
		if (j != -1) {
			class50_sub1_sub4_sub4_1.method585(j, (byte) 6);
		}
		class50_sub1_sub4_sub4_1.method594(Widget.anInt280, Widget.anInt243, -50, -10, -50, true);
		return class50_sub1_sub4_sub4_1;
	}

	public Widget() {
		anInt270 = -68;
		aBoolean271 = true;
	}
}
