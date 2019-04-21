package com.runescape.cache.def;

import com.runescape.cache.Archive;
import com.runescape.cache.media.ImageRGB;
import com.runescape.collection.Cache;
import com.runescape.media.Rasterizer;
import com.runescape.media.Rasterizer3D;
import com.runescape.media.renderable.Model;
import com.runescape.media.renderable.Renderable;
import com.runescape.net.Buffer;

public class ItemDefinition {
	
	public int anInt326;
	public int anInt327;
	public byte aByteArray328[];
	public String aString329;
	public byte aByte330;
	public int anInt331;
	public int anInt332;
	public int anInt333;
	public int anInt334;
	public static int anInt335;
	public static ItemDefinition aClass16Array336[];
	public static Cache aClass33_337 = new Cache(50, -572);
	public String aStringArray338[];
	public int anInt339;
	public int anInt340;
	public int anIntArray341[];
	public static int anIntArray342[];
	public int anInt343;
	public static boolean aBoolean344 = true;
	public int anInt345;
	public static Cache aClass33_346 = new Cache(100, -572);
	public static byte aByte347 = 6;
	public String aStringArray348[];
	public boolean aBoolean349;
	public static boolean aBoolean350 = true;
	public int anInt351;
	public static int anInt352;
	public int anInt353;
	public int anInt354;
	public int anInt355;
	public int anInt356;
	public int anInt357;
	public int anInt358;
	public int anInt359;
	public int anInt360;
	public int anInt361;
	public int anInt362;
	public int anInt363;
	public int anIntArray364[];
	public int anIntArray365[];
	public int anInt366;
	public int anInt367;
	public int anInt368;
	public int anInt369;
	public int anInt370;
	public boolean aBoolean371;
	public int anInt372;
	public static Buffer aClass50_Sub1_Sub2_373;
	public boolean aBoolean374;
	public int anInt375;
	public int anIntArray376[];
	public boolean aBoolean377;
	public byte aByte378;
	
	public boolean method211(int i, int j) {
		int k = anInt334;
		int l = anInt361;
		if (j != 0) {
			aBoolean349 = !aBoolean349;
		}
		if (i == 1) {
			k = anInt375;
			l = anInt362;
		}
		if (k == -1) {
			return true;
		}
		boolean flag = true;
		if (!Model.method578(k)) {
			flag = false;
		}
		if (l != -1 && !Model.method578(l)) {
			flag = false;
		}
		return flag;
	}

	public static ItemDefinition method212(int i) {
		for (int j = 0; j < 10; j++) {
			if (ItemDefinition.aClass16Array336[j].anInt363 == i) {
				return ItemDefinition.aClass16Array336[j];
			}
		}

		ItemDefinition.anInt352 = (ItemDefinition.anInt352 + 1) % 10;
		ItemDefinition class16 = ItemDefinition.aClass16Array336[ItemDefinition.anInt352];
		ItemDefinition.aClass50_Sub1_Sub2_373.position = ItemDefinition.anIntArray342[i];
		class16.anInt363 = i;
		class16.method223();
		class16.method218(ItemDefinition.aByte347, ItemDefinition.aClass50_Sub1_Sub2_373);
		if (class16.anInt343 != -1) {
			class16.method215(0);
		}
		if (!ItemDefinition.aBoolean344 && class16.aBoolean377) {
			class16.aString329 = "Members Object";
			class16.aByteArray328 = "Login to a members' server to use this object.".getBytes();
			class16.aStringArray338 = null;
			class16.aStringArray348 = null;
			class16.anInt332 = 0;
		}
		return class16;
	}

	public Model method213(byte byte0, int i) {
		int j = anInt353;
		int k = anInt331;
		int l = anInt370;
		if (i == 1) {
			j = anInt326;
			k = anInt355;
			l = anInt367;
		}
		if (j == -1) {
			return null;
		}
		Model class50_sub1_sub4_sub4 = Model.method577(j);
		if (byte0 != -98) {
			throw new NullPointerException();
		}
		if (k != -1) {
			if (l != -1) {
				Model class50_sub1_sub4_sub4_1 = Model.method577(k);
				Model class50_sub1_sub4_sub4_3 = Model.method577(l);
				Model aclass50_sub1_sub4_sub4_1[] = { class50_sub1_sub4_sub4, class50_sub1_sub4_sub4_1,
						class50_sub1_sub4_sub4_3 };
				class50_sub1_sub4_sub4 = new Model(3, aclass50_sub1_sub4_sub4_1, (byte) -89);
			} else {
				Model class50_sub1_sub4_sub4_2 = Model.method577(k);
				Model aclass50_sub1_sub4_sub4[] = { class50_sub1_sub4_sub4, class50_sub1_sub4_sub4_2 };
				class50_sub1_sub4_sub4 = new Model(2, aclass50_sub1_sub4_sub4, (byte) -89);
			}
		}
		if (i == 0 && aByte378 != 0) {
			class50_sub1_sub4_sub4.method590(0, 0, false, aByte378);
		}
		if (i == 1 && aByte330 != 0) {
			class50_sub1_sub4_sub4.method590(0, 0, false, aByte330);
		}
		if (anIntArray364 != null) {
			for (int i1 = 0; i1 < anIntArray364.length; i1++) {
				class50_sub1_sub4_sub4.method591(anIntArray364[i1], anIntArray341[i1]);
			}

		}
		return class50_sub1_sub4_sub4;
	}

	public static void method214(Archive class2) {
		ItemDefinition.aClass50_Sub1_Sub2_373 = new Buffer(true, class2.getFile("obj.dat", null));
		Buffer class50_sub1_sub2 = new Buffer(true, class2.getFile("obj.idx", null));
		ItemDefinition.anInt335 = class50_sub1_sub2.method523();
		ItemDefinition.anIntArray342 = new int[ItemDefinition.anInt335];
		int i = 2;
		for (int j = 0; j < ItemDefinition.anInt335; j++) {
			ItemDefinition.anIntArray342[j] = i;
			i += class50_sub1_sub2.method523();
		}

		ItemDefinition.aClass16Array336 = new ItemDefinition[10];
		for (int k = 0; k < 10; k++) {
			ItemDefinition.aClass16Array336[k] = new ItemDefinition();
		}

	}

	public void method215(int i) {
		ItemDefinition class16 = ItemDefinition.method212(anInt343);
		anInt360 = class16.anInt360;
		anInt369 = class16.anInt369;
		if (i != 0) {
			for (int j = 1; j > 0; j++) {
				;
			}
		}
		anInt359 = class16.anInt359;
		anInt356 = class16.anInt356;
		anInt339 = class16.anInt339;
		anInt327 = class16.anInt327;
		anInt340 = class16.anInt340;
		anIntArray364 = class16.anIntArray364;
		anIntArray341 = class16.anIntArray341;
		ItemDefinition class16_1 = ItemDefinition.method212(anInt333);
		aString329 = class16_1.aString329;
		aBoolean377 = class16_1.aBoolean377;
		anInt345 = class16_1.anInt345;
		String s = "a";
		char c = class16_1.aString329.charAt(0);
		if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
			s = "an";
		}
		aByteArray328 = ("Swap this note at any bank for " + s + " " + class16_1.aString329 + ".").getBytes();
		aBoolean371 = true;
	}

	public boolean method216(int i, int j) {
		while (i >= 0) {
			ItemDefinition.aBoolean350 = !ItemDefinition.aBoolean350;
		}
		int k = anInt353;
		int l = anInt331;
		int i1 = anInt370;
		if (j == 1) {
			k = anInt326;
			l = anInt355;
			i1 = anInt367;
		}
		if (k == -1) {
			return true;
		}
		boolean flag = true;
		if (!Model.method578(k)) {
			flag = false;
		}
		if (l != -1 && !Model.method578(l)) {
			flag = false;
		}
		if (i1 != -1 && !Model.method578(i1)) {
			flag = false;
		}
		return flag;
	}

	public Model method217(int i, int j) {
		if (anIntArray365 != null && j > 1) {
			int k = -1;
			for (int l = 0; l < 10; l++) {
				if (j >= anIntArray376[l] && anIntArray376[l] != 0) {
					k = anIntArray365[l];
				}
			}

			if (k != -1) {
				return ItemDefinition.method212(k).method217(anInt351, 1);
			}
		}
		Model class50_sub1_sub4_sub4 = Model.method577(anInt360);
		while (i >= 0) {
			aBoolean349 = !aBoolean349;
		}
		if (class50_sub1_sub4_sub4 == null) {
			return null;
		}
		if (anIntArray364 != null) {
			for (int i1 = 0; i1 < anIntArray364.length; i1++) {
				class50_sub1_sub4_sub4.method591(anIntArray364[i1], anIntArray341[i1]);
			}

		}
		return class50_sub1_sub4_sub4;
	}

	public void method218(byte byte0, Buffer class50_sub1_sub2) {
		if (byte0 != 6) {
			aBoolean349 = !aBoolean349;
		}
		do {
			int i = class50_sub1_sub2.method521();
			if (i == 0) {
				return;
			}
			if (i == 1) {
				anInt360 = class50_sub1_sub2.method523();
			} else if (i == 2) {
				aString329 = class50_sub1_sub2.method528();
			} else if (i == 3) {
				aByteArray328 = class50_sub1_sub2.method529(621);
			} else if (i == 4) {
				anInt369 = class50_sub1_sub2.method523();
			} else if (i == 5) {
				anInt359 = class50_sub1_sub2.method523();
			} else if (i == 6) {
				anInt356 = class50_sub1_sub2.method523();
			} else if (i == 7) {
				anInt327 = class50_sub1_sub2.method523();
				if (anInt327 > 32767) {
					anInt327 -= 0x10000;
				}
			} else if (i == 8) {
				anInt340 = class50_sub1_sub2.method523();
				if (anInt340 > 32767) {
					anInt340 -= 0x10000;
				}
			} else if (i == 10) {
				anInt372 = class50_sub1_sub2.method523();
			} else if (i == 11) {
				aBoolean371 = true;
			} else if (i == 12) {
				anInt345 = class50_sub1_sub2.method526();
			} else if (i == 16) {
				aBoolean377 = true;
			} else if (i == 23) {
				anInt353 = class50_sub1_sub2.method523();
				aByte378 = class50_sub1_sub2.method522();
			} else if (i == 24) {
				anInt331 = class50_sub1_sub2.method523();
			} else if (i == 25) {
				anInt326 = class50_sub1_sub2.method523();
				aByte330 = class50_sub1_sub2.method522();
			} else if (i == 26) {
				anInt355 = class50_sub1_sub2.method523();
			} else if (i >= 30 && i < 35) {
				if (aStringArray338 == null) {
					aStringArray338 = new String[5];
				}
				aStringArray338[i - 30] = class50_sub1_sub2.method528();
				if (aStringArray338[i - 30].equalsIgnoreCase("hidden")) {
					aStringArray338[i - 30] = null;
				}
			} else if (i >= 35 && i < 40) {
				if (aStringArray348 == null) {
					aStringArray348 = new String[5];
				}
				aStringArray348[i - 35] = class50_sub1_sub2.method528();
			} else if (i == 40) {
				int j = class50_sub1_sub2.method521();
				anIntArray364 = new int[j];
				anIntArray341 = new int[j];
				for (int k = 0; k < j; k++) {
					anIntArray364[k] = class50_sub1_sub2.method523();
					anIntArray341[k] = class50_sub1_sub2.method523();
				}

			} else if (i == 78) {
				anInt370 = class50_sub1_sub2.method523();
			} else if (i == 79) {
				anInt367 = class50_sub1_sub2.method523();
			} else if (i == 90) {
				anInt334 = class50_sub1_sub2.method523();
			} else if (i == 91) {
				anInt375 = class50_sub1_sub2.method523();
			} else if (i == 92) {
				anInt361 = class50_sub1_sub2.method523();
			} else if (i == 93) {
				anInt362 = class50_sub1_sub2.method523();
			} else if (i == 95) {
				anInt339 = class50_sub1_sub2.method523();
			} else if (i == 97) {
				anInt333 = class50_sub1_sub2.method523();
			} else if (i == 98) {
				anInt343 = class50_sub1_sub2.method523();
			} else if (i >= 100 && i < 110) {
				if (anIntArray365 == null) {
					anIntArray365 = new int[10];
					anIntArray376 = new int[10];
				}
				anIntArray365[i - 100] = class50_sub1_sub2.method523();
				anIntArray376[i - 100] = class50_sub1_sub2.method523();
			} else if (i == 110) {
				anInt366 = class50_sub1_sub2.method523();
			} else if (i == 111) {
				anInt357 = class50_sub1_sub2.method523();
			} else if (i == 112) {
				anInt368 = class50_sub1_sub2.method523();
			} else if (i == 113) {
				anInt354 = class50_sub1_sub2.method522();
			} else if (i == 114) {
				anInt358 = class50_sub1_sub2.method522() * 5;
			} else if (i == 115) {
				anInt332 = class50_sub1_sub2.method521();
			}
		} while (true);
	}

	public Model method219(boolean flag, int i) {
		if (!flag) {
			aBoolean374 = !aBoolean374;
		}
		int j = anInt334;
		int k = anInt361;
		if (i == 1) {
			j = anInt375;
			k = anInt362;
		}
		if (j == -1) {
			return null;
		}
		Model class50_sub1_sub4_sub4 = Model.method577(j);
		if (k != -1) {
			Model class50_sub1_sub4_sub4_1 = Model.method577(k);
			Model aclass50_sub1_sub4_sub4[] = { class50_sub1_sub4_sub4, class50_sub1_sub4_sub4_1 };
			class50_sub1_sub4_sub4 = new Model(2, aclass50_sub1_sub4_sub4, (byte) -89);
		}
		if (anIntArray364 != null) {
			for (int l = 0; l < anIntArray364.length; l++) {
				class50_sub1_sub4_sub4.method591(anIntArray364[l], anIntArray341[l]);
			}

		}
		return class50_sub1_sub4_sub4;
	}

	public Model method220(int i) {
		if (anIntArray365 != null && i > 1) {
			int j = -1;
			for (int k = 0; k < 10; k++) {
				if (i >= anIntArray376[k] && anIntArray376[k] != 0) {
					j = anIntArray365[k];
				}
			}

			if (j != -1) {
				return ItemDefinition.method212(j).method220(1);
			}
		}
		Model class50_sub1_sub4_sub4 = (Model) ItemDefinition.aClass33_337.method345(anInt363);
		if (class50_sub1_sub4_sub4 != null) {
			return class50_sub1_sub4_sub4;
		}
		class50_sub1_sub4_sub4 = Model.method577(anInt360);
		if (class50_sub1_sub4_sub4 == null) {
			return null;
		}
		if (anInt366 != 128 || anInt357 != 128 || anInt368 != 128) {
			class50_sub1_sub4_sub4.method593(anInt357, anInt368, 9, anInt366);
		}
		if (anIntArray364 != null) {
			for (int l = 0; l < anIntArray364.length; l++) {
				class50_sub1_sub4_sub4.method591(anIntArray364[l], anIntArray341[l]);
			}

		}
		class50_sub1_sub4_sub4.method594(64 + anInt354, 768 + anInt358, -50, -10, -50, true);
		class50_sub1_sub4_sub4.aBoolean1680 = true;
		ItemDefinition.aClass33_337.method346(class50_sub1_sub4_sub4, anInt363, 5);
		return class50_sub1_sub4_sub4;
	}

	public static ImageRGB method221(byte byte0, int i, int j, int k) {
		if (i == 0) {
			ImageRGB class50_sub1_sub1_sub1 = (ImageRGB) ItemDefinition.aClass33_346.method345(k);
			if (class50_sub1_sub1_sub1 != null && class50_sub1_sub1_sub1.anInt1495 != j
					&& class50_sub1_sub1_sub1.anInt1495 != -1) {
				class50_sub1_sub1_sub1.method442();
				class50_sub1_sub1_sub1 = null;
			}
			if (class50_sub1_sub1_sub1 != null) {
				return class50_sub1_sub1_sub1;
			}
		}
		ItemDefinition class16 = ItemDefinition.method212(k);
		if (class16.anIntArray365 == null) {
			j = -1;
		}
		if (j > 1) {
			int l = -1;
			for (int i1 = 0; i1 < 10; i1++) {
				if (j >= class16.anIntArray376[i1] && class16.anIntArray376[i1] != 0) {
					l = class16.anIntArray365[i1];
				}
			}

			if (l != -1) {
				class16 = ItemDefinition.method212(l);
			}
		}
		Model class50_sub1_sub4_sub4 = class16.method220(1);
		if (class50_sub1_sub4_sub4 == null) {
			return null;
		}
		ImageRGB class50_sub1_sub1_sub1_2 = null;
		if (class16.anInt343 != -1) {
			class50_sub1_sub1_sub1_2 = ItemDefinition.method221((byte) -33, -1, 10, class16.anInt333);
			if (class50_sub1_sub1_sub1_2 == null) {
				return null;
			}
		}
		ImageRGB class50_sub1_sub1_sub1_1 = new ImageRGB(32, 32);
		int j1 = Rasterizer3D.anInt1532;
		int k1 = Rasterizer3D.anInt1533;
		int ai[] = Rasterizer3D.anIntArray1538;
		int ai1[] = Rasterizer.anIntArray1424;
		int l1 = Rasterizer.anInt1425;
		int i2 = Rasterizer.anInt1426;
		int j2 = Rasterizer.anInt1429;
		int k2 = Rasterizer.anInt1430;
		int l2 = Rasterizer.anInt1427;
		int i3 = Rasterizer.anInt1428;
		Rasterizer3D.aBoolean1530 = false;
		Rasterizer.method444(ItemDefinition.aBoolean350, 32, 32, class50_sub1_sub1_sub1_1.anIntArray1489);
		Rasterizer.method449(32, 0, 0, (byte) -24, 32, 0);
		Rasterizer3D.method493(568);
		int j3 = class16.anInt369;
		if (i == -1) {
			j3 = (int) (j3 * 1.5D);
		}
		if (i > 0) {
			j3 = (int) (j3 * 1.04D);
		}
		int k3 = Rasterizer3D.anIntArray1536[class16.anInt359] * j3 >> 16;
		int l3 = Rasterizer3D.anIntArray1537[class16.anInt359] * j3 >> 16;
		class50_sub1_sub4_sub4.method598(0, class16.anInt356, class16.anInt339, class16.anInt359, class16.anInt327, k3
				+ ((Renderable) (class50_sub1_sub4_sub4)).anInt1475 / 2 + class16.anInt340, l3 + class16.anInt340);
		for (int l4 = 31; l4 >= 0; l4--) {
			for (int i4 = 31; i4 >= 0; i4--) {
				if (class50_sub1_sub1_sub1_1.anIntArray1489[l4 + i4 * 32] == 0) {
					if (l4 > 0 && class50_sub1_sub1_sub1_1.anIntArray1489[(l4 - 1) + i4 * 32] > 1) {
						class50_sub1_sub1_sub1_1.anIntArray1489[l4 + i4 * 32] = 1;
					} else if (i4 > 0 && class50_sub1_sub1_sub1_1.anIntArray1489[l4 + (i4 - 1) * 32] > 1) {
						class50_sub1_sub1_sub1_1.anIntArray1489[l4 + i4 * 32] = 1;
					} else if (l4 < 31 && class50_sub1_sub1_sub1_1.anIntArray1489[l4 + 1 + i4 * 32] > 1) {
						class50_sub1_sub1_sub1_1.anIntArray1489[l4 + i4 * 32] = 1;
					} else if (i4 < 31 && class50_sub1_sub1_sub1_1.anIntArray1489[l4 + (i4 + 1) * 32] > 1) {
						class50_sub1_sub1_sub1_1.anIntArray1489[l4 + i4 * 32] = 1;
					}
				}
			}

		}

		if (i > 0) {
			for (int i5 = 31; i5 >= 0; i5--) {
				for (int j4 = 31; j4 >= 0; j4--) {
					if (class50_sub1_sub1_sub1_1.anIntArray1489[i5 + j4 * 32] == 0) {
						if (i5 > 0 && class50_sub1_sub1_sub1_1.anIntArray1489[(i5 - 1) + j4 * 32] == 1) {
							class50_sub1_sub1_sub1_1.anIntArray1489[i5 + j4 * 32] = i;
						} else if (j4 > 0 && class50_sub1_sub1_sub1_1.anIntArray1489[i5 + (j4 - 1) * 32] == 1) {
							class50_sub1_sub1_sub1_1.anIntArray1489[i5 + j4 * 32] = i;
						} else if (i5 < 31 && class50_sub1_sub1_sub1_1.anIntArray1489[i5 + 1 + j4 * 32] == 1) {
							class50_sub1_sub1_sub1_1.anIntArray1489[i5 + j4 * 32] = i;
						} else if (j4 < 31 && class50_sub1_sub1_sub1_1.anIntArray1489[i5 + (j4 + 1) * 32] == 1) {
							class50_sub1_sub1_sub1_1.anIntArray1489[i5 + j4 * 32] = i;
						}
					}
				}

			}

		} else if (i == 0) {
			for (int j5 = 31; j5 >= 0; j5--) {
				for (int k4 = 31; k4 >= 0; k4--) {
					if (class50_sub1_sub1_sub1_1.anIntArray1489[j5 + k4 * 32] == 0 && j5 > 0 && k4 > 0
							&& class50_sub1_sub1_sub1_1.anIntArray1489[(j5 - 1) + (k4 - 1) * 32] > 0) {
						class50_sub1_sub1_sub1_1.anIntArray1489[j5 + k4 * 32] = 0x302020;
					}
				}

			}

		}
		if (class16.anInt343 != -1) {
			int k5 = class50_sub1_sub1_sub1_2.anInt1494;
			int l5 = class50_sub1_sub1_sub1_2.anInt1495;
			class50_sub1_sub1_sub1_2.anInt1494 = 32;
			class50_sub1_sub1_sub1_2.anInt1495 = 32;
			class50_sub1_sub1_sub1_2.method461(0, 0, -488);
			class50_sub1_sub1_sub1_2.anInt1494 = k5;
			class50_sub1_sub1_sub1_2.anInt1495 = l5;
		}
		if (i == 0) {
			ItemDefinition.aClass33_346.method346(class50_sub1_sub1_sub1_1, k, 5);
		}
		Rasterizer.method444(ItemDefinition.aBoolean350, l1, i2, ai1);
		Rasterizer.method446(l2, j2, i3, k2, true);
		Rasterizer3D.anInt1532 = j1;
		Rasterizer3D.anInt1533 = k1;
		Rasterizer3D.anIntArray1538 = ai;
		Rasterizer3D.aBoolean1530 = true;
		if (class16.aBoolean371) {
			class50_sub1_sub1_sub1_1.anInt1494 = 33;
		} else {
			class50_sub1_sub1_sub1_1.anInt1494 = 32;
		}
		class50_sub1_sub1_sub1_1.anInt1495 = j;
		if (byte0 != -33) {
			throw new NullPointerException();
		} else {
			return class50_sub1_sub1_sub1_1;
		}
	}

	public static void method222(boolean flag) {
		ItemDefinition.aClass33_337 = null;
		if (flag) {
			for (int i = 1; i > 0; i++) {
				;
			}
		}
		ItemDefinition.aClass33_346 = null;
		ItemDefinition.anIntArray342 = null;
		ItemDefinition.aClass16Array336 = null;
		ItemDefinition.aClass50_Sub1_Sub2_373 = null;
	}

	public void method223() {
		anInt360 = 0;
		aString329 = null;
		aByteArray328 = null;
		anIntArray364 = null;
		anIntArray341 = null;
		anInt369 = 2000;
		anInt359 = 0;
		anInt356 = 0;
		anInt339 = 0;
		anInt327 = 0;
		anInt340 = 0;
		anInt372 = -1;
		aBoolean371 = false;
		anInt345 = 1;
		aBoolean377 = false;
		aStringArray338 = null;
		aStringArray348 = null;
		anInt353 = -1;
		anInt331 = -1;
		aByte378 = 0;
		anInt326 = -1;
		anInt355 = -1;
		aByte330 = 0;
		anInt370 = -1;
		anInt367 = -1;
		anInt334 = -1;
		anInt361 = -1;
		anInt375 = -1;
		anInt362 = -1;
		anIntArray365 = null;
		anIntArray376 = null;
		anInt333 = -1;
		anInt343 = -1;
		anInt366 = 128;
		anInt357 = 128;
		anInt368 = 128;
		anInt354 = 0;
		anInt358 = 0;
		anInt332 = 0;
	}

	public ItemDefinition() {
		aBoolean349 = true;
		anInt351 = -68;
		anInt363 = -1;
		aBoolean374 = true;
	}
}
