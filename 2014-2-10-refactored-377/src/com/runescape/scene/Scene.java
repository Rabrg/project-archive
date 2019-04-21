package com.runescape.scene;

import com.runescape.collection.LinkedList;
import com.runescape.media.Rasterizer;
import com.runescape.media.Rasterizer3D;
import com.runescape.media.VertexNormal;
import com.runescape.media.renderable.Model;
import com.runescape.media.renderable.Renderable;
import com.runescape.scene.tile.ComplexTile;
import com.runescape.scene.tile.FloorDecoration;
import com.runescape.scene.tile.GenericTile;
import com.runescape.scene.tile.SceneTile;
import com.runescape.scene.tile.Wall;
import com.runescape.scene.tile.WallDecoration;

public class Scene {
	
	public static boolean aBoolean439;
	public int anInt440;
	public boolean aBoolean441;
	public boolean aBoolean442;
	public int anInt443;
	public static int anInt444;
	public int anInt445;
	public int anInt446;
	public boolean aBoolean447;
	public boolean aBoolean448;
	public boolean aBoolean449;
	public int anInt450;
	public static boolean aBoolean451 = true;
	public int anInt452;
	public int anInt453;
	public int anInt454;
	public int anIntArrayArrayArray455[][][];
	public SceneTile aClass50_Sub3ArrayArrayArray456[][][];
	public int anInt457;
	public int anInt458;
	public SceneSpawnRequest aClass5Array459[];
	public int anIntArrayArrayArray460[][][];
	public static int anInt461;
	public static int anInt462;
	public static int anInt463;
	public static int anInt464;
	public static int anInt465;
	public static int anInt466;
	public static int anInt467;
	public static int anInt468;
	public static int anInt469;
	public static int anInt470;
	public static int anInt471;
	public static int anInt472;
	public static int anInt473;
	public static int anInt474;
	public static int anInt475;
	public static int anInt476;
	public static SceneSpawnRequest aClass5Array477[] = new SceneSpawnRequest[100];
	public static final int anIntArray478[] = { 53, -53, -53, 53 };
	public static final int anIntArray479[] = { -53, -53, 53, 53 };
	public static final int anIntArray480[] = { -45, 45, 45, -45 };
	public static final int anIntArray481[] = { 45, 45, -45, -45 };
	public static boolean aBoolean482;
	public static int anInt483;
	public static int anInt484;
	public static int anInt485 = -1;
	public static int anInt486 = -1;
	public static int anInt487;
	public static int anIntArray488[];
	public static SceneCluster aClass39ArrayArray489[][];
	public static int anInt490;
	public static SceneCluster aClass39Array491[] = new SceneCluster[500];
	public static LinkedList aClass6_492 = new LinkedList(true);
	public static final int anIntArray493[] = { 19, 55, 38, 155, 255, 110, 137, 205, 76 };
	public static final int anIntArray494[] = { 160, 192, 80, 96, 0, 144, 80, 48, 160 };
	public static final int anIntArray495[] = { 76, 8, 137, 4, 0, 1, 38, 2, 19 };
	public static final int anIntArray496[] = { 0, 0, 2, 0, 0, 2, 1, 1, 0 };
	public static final int anIntArray497[] = { 2, 0, 0, 2, 0, 0, 0, 4, 4 };
	public static final int anIntArray498[] = { 0, 4, 4, 8, 0, 0, 8, 0, 0 };
	public static final int anIntArray499[] = { 1, 1, 0, 0, 0, 8, 0, 0, 8 };
	public static final int anIntArray500[] = { 41, 39248, 41, 4643, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 43086,
			41, 41, 41, 41, 41, 41, 41, 8602, 41, 28992, 41, 41, 41, 41, 41, 5056, 41, 41, 41, 7079, 41, 41, 41, 41,
			41, 41, 41, 41, 41, 41, 3131, 41, 41, 41 };
	public int anIntArray501[];
	public int anIntArray502[];
	public int anInt503;
	public int anIntArrayArray504[][] = { new int[16], { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1 }, { 1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 },
			{ 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1 }, { 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1 }, { 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0 }, { 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1 } };
	public int anIntArrayArray505[][] = { { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 },
			{ 12, 8, 4, 0, 13, 9, 5, 1, 14, 10, 6, 2, 15, 11, 7, 3 },
			{ 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 },
			{ 3, 7, 11, 15, 2, 6, 10, 14, 1, 5, 9, 13, 0, 4, 8, 12 } };
	public static boolean aBooleanArrayArrayArrayArray506[][][][] = new boolean[8][32][51][51];
	public static boolean aBooleanArrayArray507[][];
	public static int anInt508;
	public static int anInt509;
	public static int anInt510;
	public static int anInt511;
	public static int anInt512;
	public static int anInt513;
	
	public Scene(int ai[][][], int i, int j, int k, byte byte0) {
		anInt440 = -203;
		aBoolean441 = true;
		aBoolean442 = false;
		anInt445 = 2;
		anInt446 = -766;
		aBoolean447 = true;
		aBoolean448 = true;
		aBoolean449 = false;
		anInt450 = -68;
		aClass5Array459 = new SceneSpawnRequest[5000];
		anIntArray501 = new int[10000];
		anIntArray502 = new int[10000];
		anInt452 = j;
		anInt453 = k;
		anInt454 = i;
		aClass50_Sub3ArrayArrayArray456 = new SceneTile[j][k][i];
		anIntArrayArrayArray460 = new int[j][k + 1][i + 1];
		anIntArrayArrayArray455 = ai;
		if (byte0 == 5) {
			byte0 = 0;
		} else {
			anInt446 = 272;
		}
		method241((byte) 7);
	}

	public static void method240(boolean flag) {
		Scene.aClass5Array477 = null;
		Scene.anIntArray488 = null;
		Scene.aClass39ArrayArray489 = null;
		Scene.aClass6_492 = null;
		Scene.aBooleanArrayArrayArrayArray506 = null;
		if (flag) {
			return;
		} else {
			Scene.aBooleanArrayArray507 = null;
			return;
		}
	}

	public void method241(byte byte0) {
		for (int i = 0; i < anInt452; i++) {
			for (int j = 0; j < anInt453; j++) {
				for (int i1 = 0; i1 < anInt454; i1++) {
					aClass50_Sub3ArrayArrayArray456[i][j][i1] = null;
				}

			}

		}

		if (byte0 != 7) {
			for (int k = 1; k > 0; k++) {
				;
			}
		}
		for (int l = 0; l < Scene.anInt487; l++) {
			for (int j1 = 0; j1 < Scene.anIntArray488[l]; j1++) {
				Scene.aClass39ArrayArray489[l][j1] = null;
			}

			Scene.anIntArray488[l] = 0;
		}

		for (int k1 = 0; k1 < anInt458; k1++) {
			aClass5Array459[k1] = null;
		}

		anInt458 = 0;
		for (int l1 = 0; l1 < Scene.aClass5Array477.length; l1++) {
			Scene.aClass5Array477[l1] = null;
		}

	}

	public void method242(int i, boolean flag) {
		if (!flag) {
			aBoolean448 = !aBoolean448;
		}
		anInt457 = i;
		for (int j = 0; j < anInt453; j++) {
			for (int k = 0; k < anInt454; k++) {
				if (aClass50_Sub3ArrayArrayArray456[i][j][k] == null) {
					aClass50_Sub3ArrayArrayArray456[i][j][k] = new SceneTile(i, j, k);
				}
			}

		}

	}

	public void method243(boolean flag, int i, int j) {
		SceneTile class50_sub3 = aClass50_Sub3ArrayArrayArray456[0][i][j];
		for (int k = 0; k < 3; k++) {
			SceneTile class50_sub3_1 = aClass50_Sub3ArrayArrayArray456[k][i][j] = aClass50_Sub3ArrayArrayArray456[k + 1][i][j];
			if (class50_sub3_1 != null) {
				class50_sub3_1.anInt1397--;
				for (int i1 = 0; i1 < class50_sub3_1.anInt1407; i1++) {
					SceneSpawnRequest class5 = class50_sub3_1.aClass5Array1408[i1];
					if ((class5.anInt125 >> 29 & 3) == 2 && class5.anInt119 == i && class5.anInt121 == j) {
						class5.anInt113--;
					}
				}

			}
		}

		if (aClass50_Sub3ArrayArrayArray456[0][i][j] == null) {
			aClass50_Sub3ArrayArrayArray456[0][i][j] = new SceneTile(0, i, j);
		}
		aClass50_Sub3ArrayArrayArray456[0][i][j].aClass50_Sub3_1419 = class50_sub3;
		if (!flag) {
			for (int l = 1; l > 0; l++) {
				;
			}
		}
		aClass50_Sub3ArrayArrayArray456[3][i][j] = null;
	}

	public static void method244(int i, int j, int k, int l, int i1, int j1, int k1, int l1, int i2) {
		SceneCluster class39 = new SceneCluster();
		if (i != -8967) {
			for (int j2 = 1; j2 > 0; j2++) {
				;
			}
		}
		class39.anInt675 = j / 128;
		class39.anInt676 = l / 128;
		class39.anInt677 = k1 / 128;
		class39.anInt678 = i1 / 128;
		class39.anInt679 = i2;
		class39.anInt680 = j;
		class39.anInt681 = l;
		class39.anInt682 = k1;
		class39.anInt683 = i1;
		class39.anInt684 = l1;
		class39.anInt685 = k;
		Scene.aClass39ArrayArray489[j1][Scene.anIntArray488[j1]++] = class39;
	}

	public void method245(int i, int j, int k, int l) {
		SceneTile class50_sub3 = aClass50_Sub3ArrayArrayArray456[i][j][k];
		if (class50_sub3 == null) {
			return;
		} else {
			aClass50_Sub3ArrayArrayArray456[i][j][k].anInt1411 = l;
			return;
		}
	}

	public void method246(int i, int j, int k, int l, int i1, int j1, int k1, int l1, int i2, int j2, int k2, int l2,
			int i3, int j3, int k3, int l3, int i4, int j4, int k4, int l4) {
		if (l == 0) {
			GenericTile class3 = new GenericTile(k2, l2, i3, j3, -1, k4, false);
			for (int i5 = i; i5 >= 0; i5--) {
				if (aClass50_Sub3ArrayArrayArray456[i5][j][k] == null) {
					aClass50_Sub3ArrayArrayArray456[i5][j][k] = new SceneTile(i5, j, k);
				}
			}

			aClass50_Sub3ArrayArrayArray456[i][j][k].aClass3_1401 = class3;
			return;
		}
		if (l == 1) {
			GenericTile class3_1 = new GenericTile(k3, l3, i4, j4, j1, l4, k1 == l1 && k1 == i2 && k1 == j2);
			for (int j5 = i; j5 >= 0; j5--) {
				if (aClass50_Sub3ArrayArrayArray456[j5][j][k] == null) {
					aClass50_Sub3ArrayArrayArray456[j5][j][k] = new SceneTile(j5, j, k);
				}
			}

			aClass50_Sub3ArrayArrayArray456[i][j][k].aClass3_1401 = class3_1;
			return;
		}
		ComplexTile class20 = new ComplexTile(j2, k3, i2, k1, j, i3, j3, l4, l2, i4, 0, k2, l, l1, j4, j1, k4, l3, k,
				i1);
		for (int k5 = i; k5 >= 0; k5--) {
			if (aClass50_Sub3ArrayArrayArray456[k5][j][k] == null) {
				aClass50_Sub3ArrayArrayArray456[k5][j][k] = new SceneTile(k5, j, k);
			}
		}

		aClass50_Sub3ArrayArrayArray456[i][j][k].aClass20_1402 = class20;
	}

	public void method247(int i, int j, int k, byte byte0, int l, int i1, int j1, Renderable class50_sub1_sub4) {
		if (k <= 0) {
			return;
		}
		if (class50_sub1_sub4 == null) {
			return;
		}
		FloorDecoration class28 = new FloorDecoration();
		class28.aClass50_Sub1_Sub4_570 = class50_sub1_sub4;
		class28.anInt568 = i * 128 + 64;
		class28.anInt569 = j * 128 + 64;
		class28.anInt567 = i1;
		class28.anInt571 = l;
		class28.aByte572 = byte0;
		if (aClass50_Sub3ArrayArrayArray456[j1][i][j] == null) {
			aClass50_Sub3ArrayArrayArray456[j1][i][j] = new SceneTile(j1, i, j);
		}
		aClass50_Sub3ArrayArrayArray456[j1][i][j].aClass28_1405 = class28;
	}

	public void method248(int i, int j, Renderable class50_sub1_sub4, Renderable class50_sub1_sub4_1, int k,
			Renderable class50_sub1_sub4_2, int l, int i1, int j1) {
		CameraAngle class10 = new CameraAngle();
		class10.aClass50_Sub1_Sub4_176 = class50_sub1_sub4;
		class10.anInt174 = j1 * 128 + 64;
		class10.anInt175 = i1 * 128 + 64;
		class10.anInt173 = i;
		class10.anInt179 = k;
		class10.aClass50_Sub1_Sub4_177 = class50_sub1_sub4_1;
		class10.aClass50_Sub1_Sub4_178 = class50_sub1_sub4_2;
		if (l < 2 || l > 2) {
			aBoolean447 = !aBoolean447;
		}
		int k1 = 0;
		SceneTile class50_sub3 = aClass50_Sub3ArrayArrayArray456[j][j1][i1];
		if (class50_sub3 != null) {
			for (int l1 = 0; l1 < class50_sub3.anInt1407; l1++) {
				if (class50_sub3.aClass5Array1408[l1].aClass50_Sub1_Sub4_117 instanceof Model) {
					int i2 = ((Model) class50_sub3.aClass5Array1408[l1].aClass50_Sub1_Sub4_117).anInt1675;
					if (i2 > k1) {
						k1 = i2;
					}
				}
			}

		}
		class10.anInt180 = k1;
		if (aClass50_Sub3ArrayArrayArray456[j][j1][i1] == null) {
			aClass50_Sub3ArrayArrayArray456[j][j1][i1] = new SceneTile(j, j1, i1);
		}
		aClass50_Sub3ArrayArrayArray456[j][j1][i1].aClass10_1406 = class10;
	}

	public void method249(int i, int j, int k, int l, Renderable class50_sub1_sub4, int i1, int j1, byte byte0, int k1,
			Renderable class50_sub1_sub4_1, int l1) {
		if (class50_sub1_sub4_1 == null && class50_sub1_sub4 == null) {
			return;
		}
		Wall class44 = new Wall();
		class44.anInt726 = j1;
		class44.aByte727 = byte0;
		class44.anInt720 = i1 * 128 + 64;
		class44.anInt721 = k1 * 128 + 64;
		class44.anInt719 = i;
		if (j != 49878) {
			for (int i2 = 1; i2 > 0; i2++) {
				;
			}
		}
		class44.aClass50_Sub1_Sub4_724 = class50_sub1_sub4_1;
		class44.aClass50_Sub1_Sub4_725 = class50_sub1_sub4;
		class44.anInt722 = l;
		class44.anInt723 = k;
		for (int j2 = l1; j2 >= 0; j2--) {
			if (aClass50_Sub3ArrayArrayArray456[j2][i1][k1] == null) {
				aClass50_Sub3ArrayArrayArray456[j2][i1][k1] = new SceneTile(j2, i1, k1);
			}
		}

		aClass50_Sub3ArrayArrayArray456[l1][i1][k1].aClass44_1403 = class44;
	}

	public void method250(int i, int j, int k, int l, byte byte0, int i1, int j1, int k1, int l1, int i2,
			Renderable class50_sub1_sub4, int j2) {
		if (class50_sub1_sub4 == null) {
			return;
		}
		WallDecoration class35 = new WallDecoration();
		class35.anInt609 = l;
		class35.aByte610 = byte0;
		class35.anInt604 = i1 * 128 + 64 + l1;
		class35.anInt605 = k1 * 128 + 64 + j1;
		if (j2 >= 0) {
			anInt446 = 308;
		}
		class35.anInt603 = i2;
		class35.aClass50_Sub1_Sub4_608 = class50_sub1_sub4;
		class35.anInt606 = j;
		class35.anInt607 = k;
		for (int k2 = i; k2 >= 0; k2--) {
			if (aClass50_Sub3ArrayArrayArray456[k2][i1][k1] == null) {
				aClass50_Sub3ArrayArrayArray456[k2][i1][k1] = new SceneTile(k2, i1, k1);
			}
		}

		aClass50_Sub3ArrayArrayArray456[i][i1][k1].aClass35_1404 = class35;
	}

	public boolean method251(int i, int j, int k, Renderable class50_sub1_sub4, byte byte0, int l, int i1, int j1,
			int k1, int l1, int i2) {
		while (j1 >= 0) {
			throw new NullPointerException();
		}
		if (class50_sub1_sub4 == null) {
			return true;
		} else {
			int j2 = i1 * 128 + 64 * j;
			int k2 = k * 128 + 64 * k1;
			return method254(i, i1, k, j, k1, j2, k2, l1, class50_sub1_sub4, l, false, i2, byte0);
		}
	}

	public boolean method252(int i, Renderable class50_sub1_sub4, int j, int k, boolean flag, int l, int i1, int j1,
			int k1, int l1) {
		if (class50_sub1_sub4 == null) {
			return true;
		}
		int i2 = j - j1;
		int j2 = k1 - j1;
		int k2 = j + j1;
		int l2 = k1 + j1;
		if (flag) {
			if (l1 > 640 && l1 < 1408) {
				l2 += 128;
			}
			if (l1 > 1152 && l1 < 1920) {
				k2 += 128;
			}
			if (l1 > 1664 || l1 < 384) {
				j2 -= 128;
			}
			if (l1 > 128 && l1 < 896) {
				i2 -= 128;
			}
		}
		i2 /= 128;
		if (l != 0) {
			anInt450 = 368;
		}
		j2 /= 128;
		k2 /= 128;
		l2 /= 128;
		return method254(i1, i2, j2, (k2 - i2) + 1, (l2 - j2) + 1, j, k1, k, class50_sub1_sub4, l1, true, i, (byte) 0);
	}

	public boolean method253(int i, int j, int k, int l, Renderable class50_sub1_sub4, int i1, int j1, int k1, int l1,
			int i2, int j2, int k2, int l2) {
		if (l < 7 || l > 7) {
			aBoolean449 = !aBoolean449;
		}
		if (class50_sub1_sub4 == null) {
			return true;
		} else {
			return method254(k2, i1, j, (j2 - i1) + 1, (k1 - j) + 1, l1, j1, i, class50_sub1_sub4, i2, true, l2,
					(byte) 0);
		}
	}

	public boolean method254(int i, int j, int k, int l, int i1, int j1, int k1, int l1, Renderable class50_sub1_sub4,
			int i2, boolean flag, int j2, byte byte0) {
		for (int k2 = j; k2 < j + l; k2++) {
			for (int l2 = k; l2 < k + i1; l2++) {
				if (k2 < 0 || l2 < 0 || k2 >= anInt453 || l2 >= anInt454) {
					return false;
				}
				SceneTile class50_sub3 = aClass50_Sub3ArrayArrayArray456[i][k2][l2];
				if (class50_sub3 != null && class50_sub3.anInt1407 >= 5) {
					return false;
				}
			}

		}

		SceneSpawnRequest class5 = new SceneSpawnRequest();
		class5.anInt125 = j2;
		class5.aByte126 = byte0;
		class5.anInt113 = i;
		class5.anInt115 = j1;
		class5.anInt116 = k1;
		class5.anInt114 = l1;
		class5.aClass50_Sub1_Sub4_117 = class50_sub1_sub4;
		class5.anInt118 = i2;
		class5.anInt119 = j;
		class5.anInt121 = k;
		class5.anInt120 = (j + l) - 1;
		class5.anInt122 = (k + i1) - 1;
		for (int i3 = j; i3 < j + l; i3++) {
			for (int j3 = k; j3 < k + i1; j3++) {
				int k3 = 0;
				if (i3 > j) {
					k3++;
				}
				if (i3 < (j + l) - 1) {
					k3 += 4;
				}
				if (j3 > k) {
					k3 += 8;
				}
				if (j3 < (k + i1) - 1) {
					k3 += 2;
				}
				for (int l3 = i; l3 >= 0; l3--) {
					if (aClass50_Sub3ArrayArrayArray456[l3][i3][j3] == null) {
						aClass50_Sub3ArrayArrayArray456[l3][i3][j3] = new SceneTile(l3, i3, j3);
					}
				}

				SceneTile class50_sub3_1 = aClass50_Sub3ArrayArrayArray456[i][i3][j3];
				class50_sub3_1.aClass5Array1408[class50_sub3_1.anInt1407] = class5;
				class50_sub3_1.anIntArray1409[class50_sub3_1.anInt1407] = k3;
				class50_sub3_1.anInt1410 |= k3;
				class50_sub3_1.anInt1407++;
			}

		}

		if (flag) {
			aClass5Array459[anInt458++] = class5;
		}
		return true;
	}

	public void method255(int i) {
		i = 16 / i;
		for (int j = 0; j < anInt458; j++) {
			SceneSpawnRequest class5 = aClass5Array459[j];
			method256(class5, 0);
			aClass5Array459[j] = null;
		}

		anInt458 = 0;
	}

	public void method256(SceneSpawnRequest class5, int i) {
		for (int j = class5.anInt119; j <= class5.anInt120; j++) {
			for (int k = class5.anInt121; k <= class5.anInt122; k++) {
				SceneTile class50_sub3 = aClass50_Sub3ArrayArrayArray456[class5.anInt113][j][k];
				if (class50_sub3 != null) {
					for (int l = 0; l < class50_sub3.anInt1407; l++) {
						if (class50_sub3.aClass5Array1408[l] != class5) {
							continue;
						}
						class50_sub3.anInt1407--;
						for (int i1 = l; i1 < class50_sub3.anInt1407; i1++) {
							class50_sub3.aClass5Array1408[i1] = class50_sub3.aClass5Array1408[i1 + 1];
							class50_sub3.anIntArray1409[i1] = class50_sub3.anIntArray1409[i1 + 1];
						}

						class50_sub3.aClass5Array1408[class50_sub3.anInt1407] = null;
						break;
					}

					class50_sub3.anInt1410 = 0;
					for (int j1 = 0; j1 < class50_sub3.anInt1407; j1++) {
						class50_sub3.anInt1410 |= class50_sub3.anIntArray1409[j1];
					}

				}
			}

		}

		if (i != 0) {
			anInt443 = -317;
		}
	}

	public void method257(int i, int j, int k, int l, int i1) {
		SceneTile class50_sub3 = aClass50_Sub3ArrayArrayArray456[k][l][i];
		if (class50_sub3 == null) {
			return;
		}
		WallDecoration class35 = class50_sub3.aClass35_1404;
		if (class35 == null) {
			return;
		}
		int j1 = l * 128 + 64;
		int k1 = i * 128 + 64;
		if (i1 != 0) {
			return;
		} else {
			class35.anInt604 = j1 + ((class35.anInt604 - j1) * j) / 16;
			class35.anInt605 = k1 + ((class35.anInt605 - k1) * j) / 16;
			return;
		}
	}

	public void method258(int i, int j, int k, boolean flag) {
		SceneTile class50_sub3 = aClass50_Sub3ArrayArrayArray456[j][k][i];
		if (class50_sub3 == null) {
			return;
		}
		class50_sub3.aClass44_1403 = null;
		if (!flag) {
			anInt440 = -232;
		}
	}

	public void method259(boolean flag, int i, int j, int k) {
		SceneTile class50_sub3 = aClass50_Sub3ArrayArrayArray456[k][i][j];
		if (flag) {
			return;
		}
		if (class50_sub3 == null) {
			return;
		} else {
			class50_sub3.aClass35_1404 = null;
			return;
		}
	}

	public void method260(int i, int j, int k, int l) {
		if (k >= 0) {
			return;
		}
		SceneTile class50_sub3 = aClass50_Sub3ArrayArrayArray456[j][l][i];
		if (class50_sub3 == null) {
			return;
		}
		for (int i1 = 0; i1 < class50_sub3.anInt1407; i1++) {
			SceneSpawnRequest class5 = class50_sub3.aClass5Array1408[i1];
			if ((class5.anInt125 >> 29 & 3) == 2 && class5.anInt119 == l && class5.anInt121 == i) {
				method256(class5, 0);
				return;
			}
		}

	}

	public void method261(int i, int j, boolean flag, int k) {
		SceneTile class50_sub3 = aClass50_Sub3ArrayArrayArray456[k][i][j];
		if (class50_sub3 == null) {
			return;
		}
		class50_sub3.aClass28_1405 = null;
		if (!flag) {
			for (int l = 1; l > 0; l++) {
				;
			}
		}
	}

	public void method262(int i, int j, int k) {
		SceneTile class50_sub3 = aClass50_Sub3ArrayArrayArray456[i][j][k];
		if (class50_sub3 == null) {
			return;
		} else {
			class50_sub3.aClass10_1406 = null;
			return;
		}
	}

	public Wall method263(int i, int j, int k, int l) {
		SceneTile class50_sub3 = aClass50_Sub3ArrayArrayArray456[i][k][l];
		if (j != 17734) {
			throw new NullPointerException();
		}
		if (class50_sub3 == null) {
			return null;
		} else {
			return class50_sub3.aClass44_1403;
		}
	}

	public WallDecoration method264(int i, int j, int k, boolean flag) {
		SceneTile class50_sub3 = aClass50_Sub3ArrayArrayArray456[i][k][j];
		if (flag) {
			throw new NullPointerException();
		}
		if (class50_sub3 == null) {
			return null;
		} else {
			return class50_sub3.aClass35_1404;
		}
	}

	public SceneSpawnRequest method265(int i, byte byte0, int j, int k) {
		if (byte0 != 32) {
			for (int l = 1; l > 0; l++) {
				;
			}
		}
		SceneTile class50_sub3 = aClass50_Sub3ArrayArrayArray456[k][i][j];
		if (class50_sub3 == null) {
			return null;
		}
		for (int i1 = 0; i1 < class50_sub3.anInt1407; i1++) {
			SceneSpawnRequest class5 = class50_sub3.aClass5Array1408[i1];
			if ((class5.anInt125 >> 29 & 3) == 2 && class5.anInt119 == i && class5.anInt121 == j) {
				return class5;
			}
		}

		return null;
	}

	public FloorDecoration method266(int i, int j, int k, int l) {
		if (k != 0) {
			throw new NullPointerException();
		}
		SceneTile class50_sub3 = aClass50_Sub3ArrayArrayArray456[i][l][j];
		if (class50_sub3 == null || class50_sub3.aClass28_1405 == null) {
			return null;
		} else {
			return class50_sub3.aClass28_1405;
		}
	}

	public int method267(int i, int j, int k) {
		SceneTile class50_sub3 = aClass50_Sub3ArrayArrayArray456[i][j][k];
		if (class50_sub3 == null || class50_sub3.aClass44_1403 == null) {
			return 0;
		} else {
			return class50_sub3.aClass44_1403.anInt726;
		}
	}

	public int method268(int i, byte byte0, int j, int k) {
		if (byte0 != 4) {
			aBoolean449 = !aBoolean449;
		}
		SceneTile class50_sub3 = aClass50_Sub3ArrayArrayArray456[j][i][k];
		if (class50_sub3 == null || class50_sub3.aClass35_1404 == null) {
			return 0;
		} else {
			return class50_sub3.aClass35_1404.anInt609;
		}
	}

	public int method269(int i, int j, int k) {
		SceneTile class50_sub3 = aClass50_Sub3ArrayArrayArray456[i][j][k];
		if (class50_sub3 == null) {
			return 0;
		}
		for (int l = 0; l < class50_sub3.anInt1407; l++) {
			SceneSpawnRequest class5 = class50_sub3.aClass5Array1408[l];
			if ((class5.anInt125 >> 29 & 3) == 2 && class5.anInt119 == j && class5.anInt121 == k) {
				return class5.anInt125;
			}
		}

		return 0;
	}

	public int method270(int i, int j, int k) {
		SceneTile class50_sub3 = aClass50_Sub3ArrayArrayArray456[i][j][k];
		if (class50_sub3 == null || class50_sub3.aClass28_1405 == null) {
			return 0;
		} else {
			return class50_sub3.aClass28_1405.anInt571;
		}
	}

	public int method271(int i, int j, int k, int l) {
		SceneTile class50_sub3 = aClass50_Sub3ArrayArrayArray456[i][j][k];
		if (class50_sub3 == null) {
			return -1;
		}
		if (class50_sub3.aClass44_1403 != null && class50_sub3.aClass44_1403.anInt726 == l) {
			return class50_sub3.aClass44_1403.aByte727 & 0xff;
		}
		if (class50_sub3.aClass35_1404 != null && class50_sub3.aClass35_1404.anInt609 == l) {
			return class50_sub3.aClass35_1404.aByte610 & 0xff;
		}
		if (class50_sub3.aClass28_1405 != null && class50_sub3.aClass28_1405.anInt571 == l) {
			return class50_sub3.aClass28_1405.aByte572 & 0xff;
		}
		for (int i1 = 0; i1 < class50_sub3.anInt1407; i1++) {
			if (class50_sub3.aClass5Array1408[i1].anInt125 == l) {
				return class50_sub3.aClass5Array1408[i1].aByte126 & 0xff;
			}
		}

		return -1;
	}

	public void method272(byte byte0, int i, int j, int k) {
		for (int l = 0; l < anInt452; l++) {
			for (int i1 = 0; i1 < anInt453; i1++) {
				for (int j1 = 0; j1 < anInt454; j1++) {
					SceneTile class50_sub3 = aClass50_Sub3ArrayArrayArray456[l][i1][j1];
					if (class50_sub3 != null) {
						Wall class44 = class50_sub3.aClass44_1403;
						if (class44 != null && class44.aClass50_Sub1_Sub4_724 != null
								&& class44.aClass50_Sub1_Sub4_724.aClass40Array1474 != null) {
							method274(j1, l, 0, 1, (Model) class44.aClass50_Sub1_Sub4_724, i1, 1);
							if (class44.aClass50_Sub1_Sub4_725 != null
									&& class44.aClass50_Sub1_Sub4_725.aClass40Array1474 != null) {
								method274(j1, l, 0, 1, (Model) class44.aClass50_Sub1_Sub4_725, i1, 1);
								method275((Model) class44.aClass50_Sub1_Sub4_724,
										(Model) class44.aClass50_Sub1_Sub4_725, 0, 0, 0, false);
								((Model) class44.aClass50_Sub1_Sub4_725).method595(i, j, 0, k);
							}
							((Model) class44.aClass50_Sub1_Sub4_724).method595(i, j, 0, k);
						}
						for (int k1 = 0; k1 < class50_sub3.anInt1407; k1++) {
							SceneSpawnRequest class5 = class50_sub3.aClass5Array1408[k1];
							if (class5 != null && class5.aClass50_Sub1_Sub4_117 != null
									&& class5.aClass50_Sub1_Sub4_117.aClass40Array1474 != null) {
								method274(j1, l, 0, (class5.anInt120 - class5.anInt119) + 1,
										(Model) class5.aClass50_Sub1_Sub4_117, i1,
										(class5.anInt122 - class5.anInt121) + 1);
								((Model) class5.aClass50_Sub1_Sub4_117).method595(i, j, 0, k);
							}
						}

						FloorDecoration class28 = class50_sub3.aClass28_1405;
						if (class28 != null && class28.aClass50_Sub1_Sub4_570.aClass40Array1474 != null) {
							method273(i1, (Model) class28.aClass50_Sub1_Sub4_570, j1, l, 0);
							((Model) class28.aClass50_Sub1_Sub4_570).method595(i, j, 0, k);
						}
					}
				}

			}

		}

		if (byte0 == 2) {
			byte0 = 0;
		}
	}

	public void method273(int i, Model class50_sub1_sub4_sub4, int j, int k, int l) {
		if (l != 0) {
			return;
		}
		if (i < anInt453) {
			SceneTile class50_sub3 = aClass50_Sub3ArrayArrayArray456[k][i + 1][j];
			if (class50_sub3 != null && class50_sub3.aClass28_1405 != null
					&& class50_sub3.aClass28_1405.aClass50_Sub1_Sub4_570.aClass40Array1474 != null) {
				method275(class50_sub1_sub4_sub4, (Model) class50_sub3.aClass28_1405.aClass50_Sub1_Sub4_570, 128, 0, 0,
						true);
			}
		}
		if (j < anInt453) {
			SceneTile class50_sub3_1 = aClass50_Sub3ArrayArrayArray456[k][i][j + 1];
			if (class50_sub3_1 != null && class50_sub3_1.aClass28_1405 != null
					&& class50_sub3_1.aClass28_1405.aClass50_Sub1_Sub4_570.aClass40Array1474 != null) {
				method275(class50_sub1_sub4_sub4, (Model) class50_sub3_1.aClass28_1405.aClass50_Sub1_Sub4_570, 0, 0,
						128, true);
			}
		}
		if (i < anInt453 && j < anInt454) {
			SceneTile class50_sub3_2 = aClass50_Sub3ArrayArrayArray456[k][i + 1][j + 1];
			if (class50_sub3_2 != null && class50_sub3_2.aClass28_1405 != null
					&& class50_sub3_2.aClass28_1405.aClass50_Sub1_Sub4_570.aClass40Array1474 != null) {
				method275(class50_sub1_sub4_sub4, (Model) class50_sub3_2.aClass28_1405.aClass50_Sub1_Sub4_570, 128, 0,
						128, true);
			}
		}
		if (i < anInt453 && j > 0) {
			SceneTile class50_sub3_3 = aClass50_Sub3ArrayArrayArray456[k][i + 1][j - 1];
			if (class50_sub3_3 != null && class50_sub3_3.aClass28_1405 != null
					&& class50_sub3_3.aClass28_1405.aClass50_Sub1_Sub4_570.aClass40Array1474 != null) {
				method275(class50_sub1_sub4_sub4, (Model) class50_sub3_3.aClass28_1405.aClass50_Sub1_Sub4_570, 128, 0,
						-128, true);
			}
		}
	}

	public void method274(int i, int j, int k, int l, Model class50_sub1_sub4_sub4, int i1, int j1) {
		boolean flag = true;
		int k1 = i1;
		int l1 = i1 + l;
		int i2 = i - 1;
		int j2 = i + j1;
		for (int k2 = j; k2 <= j + 1; k2++) {
			if (k2 != anInt452) {
				for (int l2 = k1; l2 <= l1; l2++) {
					if (l2 >= 0 && l2 < anInt453) {
						for (int i3 = i2; i3 <= j2; i3++) {
							if (i3 >= 0 && i3 < anInt454 && (!flag || l2 >= l1 || i3 >= j2 || i3 < i && l2 != i1)) {
								SceneTile class50_sub3 = aClass50_Sub3ArrayArrayArray456[k2][l2][i3];
								if (class50_sub3 != null) {
									int j3 = (anIntArrayArrayArray455[k2][l2][i3]
											+ anIntArrayArrayArray455[k2][l2 + 1][i3]
											+ anIntArrayArrayArray455[k2][l2][i3 + 1] + anIntArrayArrayArray455[k2][l2 + 1][i3 + 1])
											/ 4
											- (anIntArrayArrayArray455[j][i1][i]
													+ anIntArrayArrayArray455[j][i1 + 1][i]
													+ anIntArrayArrayArray455[j][i1][i + 1] + anIntArrayArrayArray455[j][i1 + 1][i + 1])
											/ 4;
									Wall class44 = class50_sub3.aClass44_1403;
									if (class44 != null && class44.aClass50_Sub1_Sub4_724 != null
											&& class44.aClass50_Sub1_Sub4_724.aClass40Array1474 != null) {
										method275(class50_sub1_sub4_sub4, (Model) class44.aClass50_Sub1_Sub4_724,
												(l2 - i1) * 128 + (1 - l) * 64, j3, (i3 - i) * 128 + (1 - j1) * 64,
												flag);
									}
									if (class44 != null && class44.aClass50_Sub1_Sub4_725 != null
											&& class44.aClass50_Sub1_Sub4_725.aClass40Array1474 != null) {
										method275(class50_sub1_sub4_sub4, (Model) class44.aClass50_Sub1_Sub4_725,
												(l2 - i1) * 128 + (1 - l) * 64, j3, (i3 - i) * 128 + (1 - j1) * 64,
												flag);
									}
									for (int k3 = 0; k3 < class50_sub3.anInt1407; k3++) {
										SceneSpawnRequest class5 = class50_sub3.aClass5Array1408[k3];
										if (class5 != null && class5.aClass50_Sub1_Sub4_117 != null
												&& class5.aClass50_Sub1_Sub4_117.aClass40Array1474 != null) {
											int l3 = (class5.anInt120 - class5.anInt119) + 1;
											int i4 = (class5.anInt122 - class5.anInt121) + 1;
											method275(class50_sub1_sub4_sub4, (Model) class5.aClass50_Sub1_Sub4_117,
													(class5.anInt119 - i1) * 128 + (l3 - l) * 64, j3,
													(class5.anInt121 - i) * 128 + (i4 - j1) * 64, flag);
										}
									}

								}
							}
						}

					}
				}

				k1--;
				flag = false;
			}
		}

		if (k == 0) {
			;
		}
	}

	public void method275(Model class50_sub1_sub4_sub4, Model class50_sub1_sub4_sub4_1, int i, int j, int k,
			boolean flag) {
		anInt503++;
		int l = 0;
		int ai[] = class50_sub1_sub4_sub4_1.anIntArray1649;
		int i1 = class50_sub1_sub4_sub4_1.anInt1648;
		int j1 = class50_sub1_sub4_sub4_1.anInt1669 >> 16;
		int k1 = (class50_sub1_sub4_sub4_1.anInt1669 << 16) >> 16;
		int l1 = class50_sub1_sub4_sub4_1.anInt1670 >> 16;
		int i2 = (class50_sub1_sub4_sub4_1.anInt1670 << 16) >> 16;
		for (int j2 = 0; j2 < class50_sub1_sub4_sub4.anInt1648; j2++) {
			VertexNormal class40 = ((Renderable) (class50_sub1_sub4_sub4)).aClass40Array1474[j2];
			VertexNormal class40_1 = class50_sub1_sub4_sub4.aClass40Array1681[j2];
			if (class40_1.anInt696 != 0) {
				int i3 = class50_sub1_sub4_sub4.anIntArray1650[j2] - j;
				if (i3 <= class50_sub1_sub4_sub4_1.anInt1672) {
					int j3 = class50_sub1_sub4_sub4.anIntArray1649[j2] - i;
					if (j3 >= j1 && j3 <= k1) {
						int k3 = class50_sub1_sub4_sub4.anIntArray1651[j2] - k;
						if (k3 >= i2 && k3 <= l1) {
							for (int l3 = 0; l3 < i1; l3++) {
								VertexNormal class40_2 = ((Renderable) (class50_sub1_sub4_sub4_1)).aClass40Array1474[l3];
								VertexNormal class40_3 = class50_sub1_sub4_sub4_1.aClass40Array1681[l3];
								if (j3 == ai[l3] && k3 == class50_sub1_sub4_sub4_1.anIntArray1651[l3]
										&& i3 == class50_sub1_sub4_sub4_1.anIntArray1650[l3] && class40_3.anInt696 != 0) {
									class40.anInt693 += class40_3.anInt693;
									class40.anInt694 += class40_3.anInt694;
									class40.anInt695 += class40_3.anInt695;
									class40.anInt696 += class40_3.anInt696;
									class40_2.anInt693 += class40_1.anInt693;
									class40_2.anInt694 += class40_1.anInt694;
									class40_2.anInt695 += class40_1.anInt695;
									class40_2.anInt696 += class40_1.anInt696;
									l++;
									anIntArray501[j2] = anInt503;
									anIntArray502[l3] = anInt503;
								}
							}

						}
					}
				}
			}
		}

		if (l < 3 || !flag) {
			return;
		}
		for (int k2 = 0; k2 < class50_sub1_sub4_sub4.anInt1652; k2++) {
			if (anIntArray501[class50_sub1_sub4_sub4.anIntArray1653[k2]] == anInt503
					&& anIntArray501[class50_sub1_sub4_sub4.anIntArray1654[k2]] == anInt503
					&& anIntArray501[class50_sub1_sub4_sub4.anIntArray1655[k2]] == anInt503) {
				class50_sub1_sub4_sub4.anIntArray1659[k2] = -1;
			}
		}

		for (int l2 = 0; l2 < class50_sub1_sub4_sub4_1.anInt1652; l2++) {
			if (anIntArray502[class50_sub1_sub4_sub4_1.anIntArray1653[l2]] == anInt503
					&& anIntArray502[class50_sub1_sub4_sub4_1.anIntArray1654[l2]] == anInt503
					&& anIntArray502[class50_sub1_sub4_sub4_1.anIntArray1655[l2]] == anInt503) {
				class50_sub1_sub4_sub4_1.anIntArray1659[l2] = -1;
			}
		}

	}

	public void method276(int ai[], int i, int j, int k, int l, int i1) {
		SceneTile class50_sub3 = aClass50_Sub3ArrayArrayArray456[k][l][i1];
		if (class50_sub3 == null) {
			return;
		}
		GenericTile class3 = class50_sub3.aClass3_1401;
		if (class3 != null) {
			int j1 = class3.anInt101;
			if (j1 == 0) {
				return;
			}
			for (int k1 = 0; k1 < 4; k1++) {
				ai[i] = j1;
				ai[i + 1] = j1;
				ai[i + 2] = j1;
				ai[i + 3] = j1;
				i += j;
			}

			return;
		}
		ComplexTile class20 = class50_sub3.aClass20_1402;
		if (class20 == null) {
			return;
		}
		int l1 = class20.anInt414;
		int i2 = class20.anInt415;
		int j2 = class20.anInt416;
		int k2 = class20.anInt417;
		int ai1[] = anIntArrayArray504[l1];
		int ai2[] = anIntArrayArray505[i2];
		int l2 = 0;
		if (j2 != 0) {
			for (int i3 = 0; i3 < 4; i3++) {
				ai[i] = ai1[ai2[l2++]] != 0 ? k2 : j2;
				ai[i + 1] = ai1[ai2[l2++]] != 0 ? k2 : j2;
				ai[i + 2] = ai1[ai2[l2++]] != 0 ? k2 : j2;
				ai[i + 3] = ai1[ai2[l2++]] != 0 ? k2 : j2;
				i += j;
			}

			return;
		}
		for (int j3 = 0; j3 < 4; j3++) {
			if (ai1[ai2[l2++]] != 0) {
				ai[i] = k2;
			}
			if (ai1[ai2[l2++]] != 0) {
				ai[i + 1] = k2;
			}
			if (ai1[ai2[l2++]] != 0) {
				ai[i + 2] = k2;
			}
			if (ai1[ai2[l2++]] != 0) {
				ai[i + 3] = k2;
			}
			i += j;
		}

	}

	public static void method277(int i, int j, int ai[], int k, int l, int i1) {
		Scene.anInt510 = 0;
		Scene.anInt511 = 0;
		Scene.anInt512 = i1;
		Scene.anInt513 = i;
		Scene.anInt508 = i1 / 2;
		Scene.anInt509 = i / 2;
		boolean aflag[][][][] = new boolean[9][32][53][53];
		for (int j1 = 128; j1 <= 384; j1 += 32) {
			for (int k1 = 0; k1 < 2048; k1 += 64) {
				Scene.anInt473 = Model.anIntArray1710[j1];
				Scene.anInt474 = Model.anIntArray1711[j1];
				Scene.anInt475 = Model.anIntArray1710[k1];
				Scene.anInt476 = Model.anIntArray1711[k1];
				int i2 = (j1 - 128) / 32;
				int k2 = k1 / 64;
				for (int i3 = -26; i3 <= 26; i3++) {
					for (int k3 = -26; k3 <= 26; k3++) {
						int l3 = i3 * 128;
						int j4 = k3 * 128;
						boolean flag1 = false;
						for (int l4 = -l; l4 <= k; l4 += 128) {
							if (!Scene.method278(j4, l3, Scene.anInt444, ai[i2] + l4)) {
								continue;
							}
							flag1 = true;
							break;
						}

						aflag[i2][k2][i3 + 25 + 1][k3 + 25 + 1] = flag1;
					}

				}

			}

		}

		for (int l1 = 0; l1 < 8; l1++) {
			for (int j2 = 0; j2 < 32; j2++) {
				for (int l2 = -25; l2 < 25; l2++) {
					for (int j3 = -25; j3 < 25; j3++) {
						boolean flag = false;
						label0: for (int i4 = -1; i4 <= 1; i4++) {
							for (int k4 = -1; k4 <= 1; k4++) {
								if (aflag[l1][j2][l2 + i4 + 25 + 1][j3 + k4 + 25 + 1]) {
									flag = true;
								} else if (aflag[l1][(j2 + 1) % 31][l2 + i4 + 25 + 1][j3 + k4 + 25 + 1]) {
									flag = true;
								} else if (aflag[l1 + 1][j2][l2 + i4 + 25 + 1][j3 + k4 + 25 + 1]) {
									flag = true;
								} else {
									if (!aflag[l1 + 1][(j2 + 1) % 31][l2 + i4 + 25 + 1][j3 + k4 + 25 + 1]) {
										continue;
									}
									flag = true;
								}
								break label0;
							}

						}

						Scene.aBooleanArrayArrayArrayArray506[l1][j2][l2 + 25][j3 + 25] = flag;
					}

				}

			}

		}

		if (j == 22845) {
			;
		}
	}

	public static boolean method278(int i, int j, int k, int l) {
		int i1 = i * Scene.anInt475 + j * Scene.anInt476 >> 16;
		int j1 = i * Scene.anInt476 - j * Scene.anInt475 >> 16;
		if (k != 0) {
			Scene.aBoolean439 = !Scene.aBoolean439;
		}
		int k1 = l * Scene.anInt473 + j1 * Scene.anInt474 >> 16;
		int l1 = l * Scene.anInt474 - j1 * Scene.anInt473 >> 16;
		if (k1 < 50 || k1 > 3500) {
			return false;
		}
		int i2 = Scene.anInt508 + (i1 << 9) / k1;
		int j2 = Scene.anInt509 + (l1 << 9) / k1;
		return i2 >= Scene.anInt510 && i2 <= Scene.anInt512 && j2 >= Scene.anInt511 && j2 <= Scene.anInt513;
	}

	public void method279(int i, int j, int k) {
		Scene.aBoolean482 = true;
		Scene.anInt483 = j;
		Scene.anInt484 = k;
		Scene.anInt485 = -1;
		if (i != 0) {
			return;
		} else {
			Scene.anInt486 = -1;
			return;
		}
	}

	public void method280(int i, int j, int k, int l, int i1, int j1, int k1) {
		if (i < 0) {
			i = 0;
		} else if (i >= anInt453 * 128) {
			i = anInt453 * 128 - 1;
		}
		if (i1 < 0) {
			i1 = 0;
		} else if (i1 >= anInt454 * 128) {
			i1 = anInt454 * 128 - 1;
		}
		Scene.anInt463++;
		Scene.anInt473 = Model.anIntArray1710[k1];
		Scene.anInt474 = Model.anIntArray1711[k1];
		Scene.anInt475 = Model.anIntArray1710[j1];
		Scene.anInt476 = Model.anIntArray1711[j1];
		Scene.aBooleanArrayArray507 = Scene.aBooleanArrayArrayArrayArray506[(k1 - 128) / 32][j1 / 64];
		Scene.anInt470 = i;
		Scene.anInt471 = l;
		Scene.anInt472 = i1;
		Scene.anInt468 = i / 128;
		Scene.anInt469 = i1 / 128;
		Scene.anInt462 = j;
		Scene.anInt464 = Scene.anInt468 - 25;
		if (k != 0) {
			return;
		}
		if (Scene.anInt464 < 0) {
			Scene.anInt464 = 0;
		}
		Scene.anInt466 = Scene.anInt469 - 25;
		if (Scene.anInt466 < 0) {
			Scene.anInt466 = 0;
		}
		Scene.anInt465 = Scene.anInt468 + 25;
		if (Scene.anInt465 > anInt453) {
			Scene.anInt465 = anInt453;
		}
		Scene.anInt467 = Scene.anInt469 + 25;
		if (Scene.anInt467 > anInt454) {
			Scene.anInt467 = anInt454;
		}
		method286(anInt445);
		Scene.anInt461 = 0;
		for (int l1 = anInt457; l1 < anInt452; l1++) {
			SceneTile aclass50_sub3[][] = aClass50_Sub3ArrayArrayArray456[l1];
			for (int j2 = Scene.anInt464; j2 < Scene.anInt465; j2++) {
				for (int l2 = Scene.anInt466; l2 < Scene.anInt467; l2++) {
					SceneTile class50_sub3 = aclass50_sub3[j2][l2];
					if (class50_sub3 != null) {
						if (class50_sub3.anInt1411 > j
								|| !Scene.aBooleanArrayArray507[(j2 - Scene.anInt468) + 25][(l2 - Scene.anInt469) + 25]
								&& anIntArrayArrayArray455[l1][j2][l2] - l < 2000) {
							class50_sub3.aBoolean1412 = false;
							class50_sub3.aBoolean1413 = false;
							class50_sub3.anInt1415 = 0;
						} else {
							class50_sub3.aBoolean1412 = true;
							class50_sub3.aBoolean1413 = true;
							if (class50_sub3.anInt1407 > 0) {
								class50_sub3.aBoolean1414 = true;
							} else {
								class50_sub3.aBoolean1414 = false;
							}
							Scene.anInt461++;
						}
					}
				}

			}

		}

		for (int i2 = anInt457; i2 < anInt452; i2++) {
			SceneTile aclass50_sub3_1[][] = aClass50_Sub3ArrayArrayArray456[i2];
			for (int i3 = -25; i3 <= 0; i3++) {
				int j3 = Scene.anInt468 + i3;
				int l3 = Scene.anInt468 - i3;
				if (j3 >= Scene.anInt464 || l3 < Scene.anInt465) {
					for (int j4 = -25; j4 <= 0; j4++) {
						int l4 = Scene.anInt469 + j4;
						int j5 = Scene.anInt469 - j4;
						if (j3 >= Scene.anInt464) {
							if (l4 >= Scene.anInt466) {
								SceneTile class50_sub3_1 = aclass50_sub3_1[j3][l4];
								if (class50_sub3_1 != null && class50_sub3_1.aBoolean1412) {
									method281(class50_sub3_1, true);
								}
							}
							if (j5 < Scene.anInt467) {
								SceneTile class50_sub3_2 = aclass50_sub3_1[j3][j5];
								if (class50_sub3_2 != null && class50_sub3_2.aBoolean1412) {
									method281(class50_sub3_2, true);
								}
							}
						}
						if (l3 < Scene.anInt465) {
							if (l4 >= Scene.anInt466) {
								SceneTile class50_sub3_3 = aclass50_sub3_1[l3][l4];
								if (class50_sub3_3 != null && class50_sub3_3.aBoolean1412) {
									method281(class50_sub3_3, true);
								}
							}
							if (j5 < Scene.anInt467) {
								SceneTile class50_sub3_4 = aclass50_sub3_1[l3][j5];
								if (class50_sub3_4 != null && class50_sub3_4.aBoolean1412) {
									method281(class50_sub3_4, true);
								}
							}
						}
						if (Scene.anInt461 == 0) {
							Scene.aBoolean482 = false;
							return;
						}
					}

				}
			}

		}

		for (int k2 = anInt457; k2 < anInt452; k2++) {
			SceneTile aclass50_sub3_2[][] = aClass50_Sub3ArrayArrayArray456[k2];
			for (int k3 = -25; k3 <= 0; k3++) {
				int i4 = Scene.anInt468 + k3;
				int k4 = Scene.anInt468 - k3;
				if (i4 >= Scene.anInt464 || k4 < Scene.anInt465) {
					for (int i5 = -25; i5 <= 0; i5++) {
						int k5 = Scene.anInt469 + i5;
						int l5 = Scene.anInt469 - i5;
						if (i4 >= Scene.anInt464) {
							if (k5 >= Scene.anInt466) {
								SceneTile class50_sub3_5 = aclass50_sub3_2[i4][k5];
								if (class50_sub3_5 != null && class50_sub3_5.aBoolean1412) {
									method281(class50_sub3_5, false);
								}
							}
							if (l5 < Scene.anInt467) {
								SceneTile class50_sub3_6 = aclass50_sub3_2[i4][l5];
								if (class50_sub3_6 != null && class50_sub3_6.aBoolean1412) {
									method281(class50_sub3_6, false);
								}
							}
						}
						if (k4 < Scene.anInt465) {
							if (k5 >= Scene.anInt466) {
								SceneTile class50_sub3_7 = aclass50_sub3_2[k4][k5];
								if (class50_sub3_7 != null && class50_sub3_7.aBoolean1412) {
									method281(class50_sub3_7, false);
								}
							}
							if (l5 < Scene.anInt467) {
								SceneTile class50_sub3_8 = aclass50_sub3_2[k4][l5];
								if (class50_sub3_8 != null && class50_sub3_8.aBoolean1412) {
									method281(class50_sub3_8, false);
								}
							}
						}
						if (Scene.anInt461 == 0) {
							Scene.aBoolean482 = false;
							return;
						}
					}

				}
			}

		}

		Scene.aBoolean482 = false;
	}

	public void method281(SceneTile class50_sub3, boolean flag) {
		Scene.aClass6_492.method155(class50_sub3);
		do {
			SceneTile class50_sub3_1;
			do {
				class50_sub3_1 = (SceneTile) Scene.aClass6_492.method157();
				if (class50_sub3_1 == null) {
					return;
				}
			} while (!class50_sub3_1.aBoolean1413);
			int i = class50_sub3_1.anInt1398;
			int j = class50_sub3_1.anInt1399;
			int k = class50_sub3_1.anInt1397;
			int l = class50_sub3_1.anInt1400;
			SceneTile aclass50_sub3[][] = aClass50_Sub3ArrayArrayArray456[k];
			if (class50_sub3_1.aBoolean1412) {
				if (flag) {
					if (k > 0) {
						SceneTile class50_sub3_2 = aClass50_Sub3ArrayArrayArray456[k - 1][i][j];
						if (class50_sub3_2 != null && class50_sub3_2.aBoolean1413) {
							continue;
						}
					}
					if (i <= Scene.anInt468 && i > Scene.anInt464) {
						SceneTile class50_sub3_3 = aclass50_sub3[i - 1][j];
						if (class50_sub3_3 != null && class50_sub3_3.aBoolean1413
								&& (class50_sub3_3.aBoolean1412 || (class50_sub3_1.anInt1410 & 1) == 0)) {
							continue;
						}
					}
					if (i >= Scene.anInt468 && i < Scene.anInt465 - 1) {
						SceneTile class50_sub3_4 = aclass50_sub3[i + 1][j];
						if (class50_sub3_4 != null && class50_sub3_4.aBoolean1413
								&& (class50_sub3_4.aBoolean1412 || (class50_sub3_1.anInt1410 & 4) == 0)) {
							continue;
						}
					}
					if (j <= Scene.anInt469 && j > Scene.anInt466) {
						SceneTile class50_sub3_5 = aclass50_sub3[i][j - 1];
						if (class50_sub3_5 != null && class50_sub3_5.aBoolean1413
								&& (class50_sub3_5.aBoolean1412 || (class50_sub3_1.anInt1410 & 8) == 0)) {
							continue;
						}
					}
					if (j >= Scene.anInt469 && j < Scene.anInt467 - 1) {
						SceneTile class50_sub3_6 = aclass50_sub3[i][j + 1];
						if (class50_sub3_6 != null && class50_sub3_6.aBoolean1413
								&& (class50_sub3_6.aBoolean1412 || (class50_sub3_1.anInt1410 & 2) == 0)) {
							continue;
						}
					}
				} else {
					flag = true;
				}
				class50_sub3_1.aBoolean1412 = false;
				if (class50_sub3_1.aClass50_Sub3_1419 != null) {
					SceneTile class50_sub3_7 = class50_sub3_1.aClass50_Sub3_1419;
					if (class50_sub3_7.aClass3_1401 != null) {
						if (!method287(0, i, j)) {
							method282(class50_sub3_7.aClass3_1401, 0, Scene.anInt473, Scene.anInt474, Scene.anInt475,
									Scene.anInt476, i, j);
						}
					} else if (class50_sub3_7.aClass20_1402 != null && !method287(0, i, j)) {
						method283(Scene.anInt474, Scene.anInt476, class50_sub3_7.aClass20_1402, Scene.anInt473, j, i,
								Scene.anInt475, (byte) 3);
					}
					Wall class44 = class50_sub3_7.aClass44_1403;
					if (class44 != null) {
						class44.aClass50_Sub1_Sub4_724.method560(0, Scene.anInt473, Scene.anInt474, Scene.anInt475,
								Scene.anInt476, class44.anInt720 - Scene.anInt470, class44.anInt719 - Scene.anInt471,
								class44.anInt721 - Scene.anInt472, class44.anInt726);
					}
					for (int i2 = 0; i2 < class50_sub3_7.anInt1407; i2++) {
						SceneSpawnRequest class5 = class50_sub3_7.aClass5Array1408[i2];
						if (class5 != null) {
							class5.aClass50_Sub1_Sub4_117.method560(class5.anInt118, Scene.anInt473, Scene.anInt474,
									Scene.anInt475, Scene.anInt476, class5.anInt115 - Scene.anInt470, class5.anInt114
											- Scene.anInt471, class5.anInt116 - Scene.anInt472, class5.anInt125);
						}
					}

				}
				boolean flag1 = false;
				if (class50_sub3_1.aClass3_1401 != null) {
					if (!method287(l, i, j)) {
						flag1 = true;
						method282(class50_sub3_1.aClass3_1401, l, Scene.anInt473, Scene.anInt474, Scene.anInt475,
								Scene.anInt476, i, j);
					}
				} else if (class50_sub3_1.aClass20_1402 != null && !method287(l, i, j)) {
					flag1 = true;
					method283(Scene.anInt474, Scene.anInt476, class50_sub3_1.aClass20_1402, Scene.anInt473, j, i,
							Scene.anInt475, (byte) 3);
				}
				int j1 = 0;
				int j2 = 0;
				Wall class44_3 = class50_sub3_1.aClass44_1403;
				WallDecoration class35_1 = class50_sub3_1.aClass35_1404;
				if (class44_3 != null || class35_1 != null) {
					if (Scene.anInt468 == i) {
						j1++;
					} else if (Scene.anInt468 < i) {
						j1 += 2;
					}
					if (Scene.anInt469 == j) {
						j1 += 3;
					} else if (Scene.anInt469 > j) {
						j1 += 6;
					}
					j2 = Scene.anIntArray493[j1];
					class50_sub3_1.anInt1418 = Scene.anIntArray495[j1];
				}
				if (class44_3 != null) {
					if ((class44_3.anInt722 & Scene.anIntArray494[j1]) != 0) {
						if (class44_3.anInt722 == 16) {
							class50_sub3_1.anInt1415 = 3;
							class50_sub3_1.anInt1416 = Scene.anIntArray496[j1];
							class50_sub3_1.anInt1417 = 3 - class50_sub3_1.anInt1416;
						} else if (class44_3.anInt722 == 32) {
							class50_sub3_1.anInt1415 = 6;
							class50_sub3_1.anInt1416 = Scene.anIntArray497[j1];
							class50_sub3_1.anInt1417 = 6 - class50_sub3_1.anInt1416;
						} else if (class44_3.anInt722 == 64) {
							class50_sub3_1.anInt1415 = 12;
							class50_sub3_1.anInt1416 = Scene.anIntArray498[j1];
							class50_sub3_1.anInt1417 = 12 - class50_sub3_1.anInt1416;
						} else {
							class50_sub3_1.anInt1415 = 9;
							class50_sub3_1.anInt1416 = Scene.anIntArray499[j1];
							class50_sub3_1.anInt1417 = 9 - class50_sub3_1.anInt1416;
						}
					} else {
						class50_sub3_1.anInt1415 = 0;
					}
					if ((class44_3.anInt722 & j2) != 0 && !method288(l, i, j, class44_3.anInt722)) {
						class44_3.aClass50_Sub1_Sub4_724.method560(0, Scene.anInt473, Scene.anInt474, Scene.anInt475,
								Scene.anInt476, class44_3.anInt720 - Scene.anInt470, class44_3.anInt719
										- Scene.anInt471, class44_3.anInt721 - Scene.anInt472, class44_3.anInt726);
					}
					if ((class44_3.anInt723 & j2) != 0 && !method288(l, i, j, class44_3.anInt723)) {
						class44_3.aClass50_Sub1_Sub4_725.method560(0, Scene.anInt473, Scene.anInt474, Scene.anInt475,
								Scene.anInt476, class44_3.anInt720 - Scene.anInt470, class44_3.anInt719
										- Scene.anInt471, class44_3.anInt721 - Scene.anInt472, class44_3.anInt726);
					}
				}
				if (class35_1 != null && !method289(l, i, j, class35_1.aClass50_Sub1_Sub4_608.anInt1475)) {
					if ((class35_1.anInt606 & j2) != 0) {
						class35_1.aClass50_Sub1_Sub4_608.method560(class35_1.anInt607, Scene.anInt473, Scene.anInt474,
								Scene.anInt475, Scene.anInt476, class35_1.anInt604 - Scene.anInt470, class35_1.anInt603
										- Scene.anInt471, class35_1.anInt605 - Scene.anInt472, class35_1.anInt609);
					} else if ((class35_1.anInt606 & 0x300) != 0) {
						int j4 = class35_1.anInt604 - Scene.anInt470;
						int l5 = class35_1.anInt603 - Scene.anInt471;
						int k6 = class35_1.anInt605 - Scene.anInt472;
						int i8 = class35_1.anInt607;
						int k9;
						if (i8 == 1 || i8 == 2) {
							k9 = -j4;
						} else {
							k9 = j4;
						}
						int k10;
						if (i8 == 2 || i8 == 3) {
							k10 = -k6;
						} else {
							k10 = k6;
						}
						if ((class35_1.anInt606 & 0x100) != 0 && k10 < k9) {
							int i11 = j4 + Scene.anIntArray478[i8];
							int k11 = k6 + Scene.anIntArray479[i8];
							class35_1.aClass50_Sub1_Sub4_608.method560(i8 * 512 + 256, Scene.anInt473, Scene.anInt474,
									Scene.anInt475, Scene.anInt476, i11, l5, k11, class35_1.anInt609);
						}
						if ((class35_1.anInt606 & 0x200) != 0 && k10 > k9) {
							int j11 = j4 + Scene.anIntArray480[i8];
							int l11 = k6 + Scene.anIntArray481[i8];
							class35_1.aClass50_Sub1_Sub4_608.method560(i8 * 512 + 1280 & 0x7ff, Scene.anInt473,
									Scene.anInt474, Scene.anInt475, Scene.anInt476, j11, l5, l11, class35_1.anInt609);
						}
					}
				}
				if (flag1) {
					FloorDecoration class28 = class50_sub3_1.aClass28_1405;
					if (class28 != null) {
						class28.aClass50_Sub1_Sub4_570.method560(0, Scene.anInt473, Scene.anInt474, Scene.anInt475,
								Scene.anInt476, class28.anInt568 - Scene.anInt470, class28.anInt567 - Scene.anInt471,
								class28.anInt569 - Scene.anInt472, class28.anInt571);
					}
					CameraAngle class10_1 = class50_sub3_1.aClass10_1406;
					if (class10_1 != null && class10_1.anInt180 == 0) {
						if (class10_1.aClass50_Sub1_Sub4_177 != null) {
							class10_1.aClass50_Sub1_Sub4_177.method560(0, Scene.anInt473, Scene.anInt474,
									Scene.anInt475, Scene.anInt476, class10_1.anInt174 - Scene.anInt470,
									class10_1.anInt173 - Scene.anInt471, class10_1.anInt175 - Scene.anInt472,
									class10_1.anInt179);
						}
						if (class10_1.aClass50_Sub1_Sub4_178 != null) {
							class10_1.aClass50_Sub1_Sub4_178.method560(0, Scene.anInt473, Scene.anInt474,
									Scene.anInt475, Scene.anInt476, class10_1.anInt174 - Scene.anInt470,
									class10_1.anInt173 - Scene.anInt471, class10_1.anInt175 - Scene.anInt472,
									class10_1.anInt179);
						}
						if (class10_1.aClass50_Sub1_Sub4_176 != null) {
							class10_1.aClass50_Sub1_Sub4_176.method560(0, Scene.anInt473, Scene.anInt474,
									Scene.anInt475, Scene.anInt476, class10_1.anInt174 - Scene.anInt470,
									class10_1.anInt173 - Scene.anInt471, class10_1.anInt175 - Scene.anInt472,
									class10_1.anInt179);
						}
					}
				}
				int k4 = class50_sub3_1.anInt1410;
				if (k4 != 0) {
					if (i < Scene.anInt468 && (k4 & 4) != 0) {
						SceneTile class50_sub3_17 = aclass50_sub3[i + 1][j];
						if (class50_sub3_17 != null && class50_sub3_17.aBoolean1413) {
							Scene.aClass6_492.method155(class50_sub3_17);
						}
					}
					if (j < Scene.anInt469 && (k4 & 2) != 0) {
						SceneTile class50_sub3_18 = aclass50_sub3[i][j + 1];
						if (class50_sub3_18 != null && class50_sub3_18.aBoolean1413) {
							Scene.aClass6_492.method155(class50_sub3_18);
						}
					}
					if (i > Scene.anInt468 && (k4 & 1) != 0) {
						SceneTile class50_sub3_19 = aclass50_sub3[i - 1][j];
						if (class50_sub3_19 != null && class50_sub3_19.aBoolean1413) {
							Scene.aClass6_492.method155(class50_sub3_19);
						}
					}
					if (j > Scene.anInt469 && (k4 & 8) != 0) {
						SceneTile class50_sub3_20 = aclass50_sub3[i][j - 1];
						if (class50_sub3_20 != null && class50_sub3_20.aBoolean1413) {
							Scene.aClass6_492.method155(class50_sub3_20);
						}
					}
				}
			}
			if (class50_sub3_1.anInt1415 != 0) {
				boolean flag2 = true;
				for (int k1 = 0; k1 < class50_sub3_1.anInt1407; k1++) {
					if (class50_sub3_1.aClass5Array1408[k1].anInt124 == Scene.anInt463
							|| (class50_sub3_1.anIntArray1409[k1] & class50_sub3_1.anInt1415) != class50_sub3_1.anInt1416) {
						continue;
					}
					flag2 = false;
					break;
				}

				if (flag2) {
					Wall class44_1 = class50_sub3_1.aClass44_1403;
					if (!method288(l, i, j, class44_1.anInt722)) {
						class44_1.aClass50_Sub1_Sub4_724.method560(0, Scene.anInt473, Scene.anInt474, Scene.anInt475,
								Scene.anInt476, class44_1.anInt720 - Scene.anInt470, class44_1.anInt719
										- Scene.anInt471, class44_1.anInt721 - Scene.anInt472, class44_1.anInt726);
					}
					class50_sub3_1.anInt1415 = 0;
				}
			}
			if (class50_sub3_1.aBoolean1414) {
				try {
					int i1 = class50_sub3_1.anInt1407;
					class50_sub3_1.aBoolean1414 = false;
					int l1 = 0;
					label0: for (int k2 = 0; k2 < i1; k2++) {
						SceneSpawnRequest class5_1 = class50_sub3_1.aClass5Array1408[k2];
						if (class5_1.anInt124 == Scene.anInt463) {
							continue;
						}
						for (int k3 = class5_1.anInt119; k3 <= class5_1.anInt120; k3++) {
							for (int l4 = class5_1.anInt121; l4 <= class5_1.anInt122; l4++) {
								SceneTile class50_sub3_21 = aclass50_sub3[k3][l4];
								if (class50_sub3_21.aBoolean1412) {
									class50_sub3_1.aBoolean1414 = true;
								} else {
									if (class50_sub3_21.anInt1415 == 0) {
										continue;
									}
									int l6 = 0;
									if (k3 > class5_1.anInt119) {
										l6++;
									}
									if (k3 < class5_1.anInt120) {
										l6 += 4;
									}
									if (l4 > class5_1.anInt121) {
										l6 += 8;
									}
									if (l4 < class5_1.anInt122) {
										l6 += 2;
									}
									if ((l6 & class50_sub3_21.anInt1415) != class50_sub3_1.anInt1417) {
										continue;
									}
									class50_sub3_1.aBoolean1414 = true;
								}
								continue label0;
							}

						}

						Scene.aClass5Array477[l1++] = class5_1;
						int i5 = Scene.anInt468 - class5_1.anInt119;
						int i6 = class5_1.anInt120 - Scene.anInt468;
						if (i6 > i5) {
							i5 = i6;
						}
						int i7 = Scene.anInt469 - class5_1.anInt121;
						int j8 = class5_1.anInt122 - Scene.anInt469;
						if (j8 > i7) {
							class5_1.anInt123 = i5 + j8;
						} else {
							class5_1.anInt123 = i5 + i7;
						}
					}

					while (l1 > 0) {
						int i3 = -50;
						int l3 = -1;
						for (int j5 = 0; j5 < l1; j5++) {
							SceneSpawnRequest class5_2 = Scene.aClass5Array477[j5];
							if (class5_2.anInt124 != Scene.anInt463) {
								if (class5_2.anInt123 > i3) {
									i3 = class5_2.anInt123;
									l3 = j5;
								} else if (class5_2.anInt123 == i3) {
									int j7 = class5_2.anInt115 - Scene.anInt470;
									int k8 = class5_2.anInt116 - Scene.anInt472;
									int l9 = Scene.aClass5Array477[l3].anInt115 - Scene.anInt470;
									int l10 = Scene.aClass5Array477[l3].anInt116 - Scene.anInt472;
									if (j7 * j7 + k8 * k8 > l9 * l9 + l10 * l10) {
										l3 = j5;
									}
								}
							}
						}

						if (l3 == -1) {
							break;
						}
						SceneSpawnRequest class5_3 = Scene.aClass5Array477[l3];
						class5_3.anInt124 = Scene.anInt463;
						if (!method290(l, class5_3.anInt119, class5_3.anInt120, class5_3.anInt121, class5_3.anInt122,
								class5_3.aClass50_Sub1_Sub4_117.anInt1475)) {
							class5_3.aClass50_Sub1_Sub4_117.method560(class5_3.anInt118, Scene.anInt473,
									Scene.anInt474, Scene.anInt475, Scene.anInt476, class5_3.anInt115 - Scene.anInt470,
									class5_3.anInt114 - Scene.anInt471, class5_3.anInt116 - Scene.anInt472,
									class5_3.anInt125);
						}
						for (int k7 = class5_3.anInt119; k7 <= class5_3.anInt120; k7++) {
							for (int l8 = class5_3.anInt121; l8 <= class5_3.anInt122; l8++) {
								SceneTile class50_sub3_22 = aclass50_sub3[k7][l8];
								if (class50_sub3_22.anInt1415 != 0) {
									Scene.aClass6_492.method155(class50_sub3_22);
								} else if ((k7 != i || l8 != j) && class50_sub3_22.aBoolean1413) {
									Scene.aClass6_492.method155(class50_sub3_22);
								}
							}

						}

					}
					if (class50_sub3_1.aBoolean1414) {
						continue;
					}
				} catch (Exception _ex) {
					class50_sub3_1.aBoolean1414 = false;
				}
			}
			if (!class50_sub3_1.aBoolean1413 || class50_sub3_1.anInt1415 != 0) {
				continue;
			}
			if (i <= Scene.anInt468 && i > Scene.anInt464) {
				SceneTile class50_sub3_8 = aclass50_sub3[i - 1][j];
				if (class50_sub3_8 != null && class50_sub3_8.aBoolean1413) {
					continue;
				}
			}
			if (i >= Scene.anInt468 && i < Scene.anInt465 - 1) {
				SceneTile class50_sub3_9 = aclass50_sub3[i + 1][j];
				if (class50_sub3_9 != null && class50_sub3_9.aBoolean1413) {
					continue;
				}
			}
			if (j <= Scene.anInt469 && j > Scene.anInt466) {
				SceneTile class50_sub3_10 = aclass50_sub3[i][j - 1];
				if (class50_sub3_10 != null && class50_sub3_10.aBoolean1413) {
					continue;
				}
			}
			if (j >= Scene.anInt469 && j < Scene.anInt467 - 1) {
				SceneTile class50_sub3_11 = aclass50_sub3[i][j + 1];
				if (class50_sub3_11 != null && class50_sub3_11.aBoolean1413) {
					continue;
				}
			}
			class50_sub3_1.aBoolean1413 = false;
			Scene.anInt461--;
			CameraAngle class10 = class50_sub3_1.aClass10_1406;
			if (class10 != null && class10.anInt180 != 0) {
				if (class10.aClass50_Sub1_Sub4_177 != null) {
					class10.aClass50_Sub1_Sub4_177.method560(0, Scene.anInt473, Scene.anInt474, Scene.anInt475,
							Scene.anInt476, class10.anInt174 - Scene.anInt470, class10.anInt173 - Scene.anInt471
									- class10.anInt180, class10.anInt175 - Scene.anInt472, class10.anInt179);
				}
				if (class10.aClass50_Sub1_Sub4_178 != null) {
					class10.aClass50_Sub1_Sub4_178.method560(0, Scene.anInt473, Scene.anInt474, Scene.anInt475,
							Scene.anInt476, class10.anInt174 - Scene.anInt470, class10.anInt173 - Scene.anInt471
									- class10.anInt180, class10.anInt175 - Scene.anInt472, class10.anInt179);
				}
				if (class10.aClass50_Sub1_Sub4_176 != null) {
					class10.aClass50_Sub1_Sub4_176.method560(0, Scene.anInt473, Scene.anInt474, Scene.anInt475,
							Scene.anInt476, class10.anInt174 - Scene.anInt470, class10.anInt173 - Scene.anInt471
									- class10.anInt180, class10.anInt175 - Scene.anInt472, class10.anInt179);
				}
			}
			if (class50_sub3_1.anInt1418 != 0) {
				WallDecoration class35 = class50_sub3_1.aClass35_1404;
				if (class35 != null && !method289(l, i, j, class35.aClass50_Sub1_Sub4_608.anInt1475)) {
					if ((class35.anInt606 & class50_sub3_1.anInt1418) != 0) {
						class35.aClass50_Sub1_Sub4_608.method560(class35.anInt607, Scene.anInt473, Scene.anInt474,
								Scene.anInt475, Scene.anInt476, class35.anInt604 - Scene.anInt470, class35.anInt603
										- Scene.anInt471, class35.anInt605 - Scene.anInt472, class35.anInt609);
					} else if ((class35.anInt606 & 0x300) != 0) {
						int l2 = class35.anInt604 - Scene.anInt470;
						int j3 = class35.anInt603 - Scene.anInt471;
						int i4 = class35.anInt605 - Scene.anInt472;
						int k5 = class35.anInt607;
						int j6;
						if (k5 == 1 || k5 == 2) {
							j6 = -l2;
						} else {
							j6 = l2;
						}
						int l7;
						if (k5 == 2 || k5 == 3) {
							l7 = -i4;
						} else {
							l7 = i4;
						}
						if ((class35.anInt606 & 0x100) != 0 && l7 >= j6) {
							int i9 = l2 + Scene.anIntArray478[k5];
							int i10 = i4 + Scene.anIntArray479[k5];
							class35.aClass50_Sub1_Sub4_608.method560(k5 * 512 + 256, Scene.anInt473, Scene.anInt474,
									Scene.anInt475, Scene.anInt476, i9, j3, i10, class35.anInt609);
						}
						if ((class35.anInt606 & 0x200) != 0 && l7 <= j6) {
							int j9 = l2 + Scene.anIntArray480[k5];
							int j10 = i4 + Scene.anIntArray481[k5];
							class35.aClass50_Sub1_Sub4_608.method560(k5 * 512 + 1280 & 0x7ff, Scene.anInt473,
									Scene.anInt474, Scene.anInt475, Scene.anInt476, j9, j3, j10, class35.anInt609);
						}
					}
				}
				Wall class44_2 = class50_sub3_1.aClass44_1403;
				if (class44_2 != null) {
					if ((class44_2.anInt723 & class50_sub3_1.anInt1418) != 0 && !method288(l, i, j, class44_2.anInt723)) {
						class44_2.aClass50_Sub1_Sub4_725.method560(0, Scene.anInt473, Scene.anInt474, Scene.anInt475,
								Scene.anInt476, class44_2.anInt720 - Scene.anInt470, class44_2.anInt719
										- Scene.anInt471, class44_2.anInt721 - Scene.anInt472, class44_2.anInt726);
					}
					if ((class44_2.anInt722 & class50_sub3_1.anInt1418) != 0 && !method288(l, i, j, class44_2.anInt722)) {
						class44_2.aClass50_Sub1_Sub4_724.method560(0, Scene.anInt473, Scene.anInt474, Scene.anInt475,
								Scene.anInt476, class44_2.anInt720 - Scene.anInt470, class44_2.anInt719
										- Scene.anInt471, class44_2.anInt721 - Scene.anInt472, class44_2.anInt726);
					}
				}
			}
			if (k < anInt452 - 1) {
				SceneTile class50_sub3_12 = aClass50_Sub3ArrayArrayArray456[k + 1][i][j];
				if (class50_sub3_12 != null && class50_sub3_12.aBoolean1413) {
					Scene.aClass6_492.method155(class50_sub3_12);
				}
			}
			if (i < Scene.anInt468) {
				SceneTile class50_sub3_13 = aclass50_sub3[i + 1][j];
				if (class50_sub3_13 != null && class50_sub3_13.aBoolean1413) {
					Scene.aClass6_492.method155(class50_sub3_13);
				}
			}
			if (j < Scene.anInt469) {
				SceneTile class50_sub3_14 = aclass50_sub3[i][j + 1];
				if (class50_sub3_14 != null && class50_sub3_14.aBoolean1413) {
					Scene.aClass6_492.method155(class50_sub3_14);
				}
			}
			if (i > Scene.anInt468) {
				SceneTile class50_sub3_15 = aclass50_sub3[i - 1][j];
				if (class50_sub3_15 != null && class50_sub3_15.aBoolean1413) {
					Scene.aClass6_492.method155(class50_sub3_15);
				}
			}
			if (j > Scene.anInt469) {
				SceneTile class50_sub3_16 = aclass50_sub3[i][j - 1];
				if (class50_sub3_16 != null && class50_sub3_16.aBoolean1413) {
					Scene.aClass6_492.method155(class50_sub3_16);
				}
			}
		} while (true);
	}

	public void method282(GenericTile class3, int i, int j, int k, int l, int i1, int j1, int k1) {
		int l1;
		int i2 = l1 = (j1 << 7) - Scene.anInt470;
		int j2;
		int k2 = j2 = (k1 << 7) - Scene.anInt472;
		int l2;
		int i3 = l2 = i2 + 128;
		int j3;
		int k3 = j3 = k2 + 128;
		int l3 = anIntArrayArrayArray455[i][j1][k1] - Scene.anInt471;
		int i4 = anIntArrayArrayArray455[i][j1 + 1][k1] - Scene.anInt471;
		int j4 = anIntArrayArrayArray455[i][j1 + 1][k1 + 1] - Scene.anInt471;
		int k4 = anIntArrayArrayArray455[i][j1][k1 + 1] - Scene.anInt471;
		int l4 = k2 * l + i2 * i1 >> 16;
		k2 = k2 * i1 - i2 * l >> 16;
		i2 = l4;
		l4 = l3 * k - k2 * j >> 16;
		k2 = l3 * j + k2 * k >> 16;
		l3 = l4;
		if (k2 < 50) {
			return;
		}
		l4 = j2 * l + i3 * i1 >> 16;
		j2 = j2 * i1 - i3 * l >> 16;
		i3 = l4;
		l4 = i4 * k - j2 * j >> 16;
		j2 = i4 * j + j2 * k >> 16;
		i4 = l4;
		if (j2 < 50) {
			return;
		}
		l4 = k3 * l + l2 * i1 >> 16;
		k3 = k3 * i1 - l2 * l >> 16;
		l2 = l4;
		l4 = j4 * k - k3 * j >> 16;
		k3 = j4 * j + k3 * k >> 16;
		j4 = l4;
		if (k3 < 50) {
			return;
		}
		l4 = j3 * l + l1 * i1 >> 16;
		j3 = j3 * i1 - l1 * l >> 16;
		l1 = l4;
		l4 = k4 * k - j3 * j >> 16;
		j3 = k4 * j + j3 * k >> 16;
		k4 = l4;
		if (j3 < 50) {
			return;
		}
		int i5 = Rasterizer3D.anInt1532 + (i2 << 9) / k2;
		int j5 = Rasterizer3D.anInt1533 + (l3 << 9) / k2;
		int k5 = Rasterizer3D.anInt1532 + (i3 << 9) / j2;
		int l5 = Rasterizer3D.anInt1533 + (i4 << 9) / j2;
		int i6 = Rasterizer3D.anInt1532 + (l2 << 9) / k3;
		int j6 = Rasterizer3D.anInt1533 + (j4 << 9) / k3;
		int k6 = Rasterizer3D.anInt1532 + (l1 << 9) / j3;
		int l6 = Rasterizer3D.anInt1533 + (k4 << 9) / j3;
		Rasterizer3D.anInt1531 = 0;
		if ((i6 - k6) * (l5 - l6) - (j6 - l6) * (k5 - k6) > 0) {
			Rasterizer3D.aBoolean1528 = false;
			if (i6 < 0 || k6 < 0 || k5 < 0 || i6 > Rasterizer.anInt1431 || k6 > Rasterizer.anInt1431
					|| k5 > Rasterizer.anInt1431) {
				Rasterizer3D.aBoolean1528 = true;
			}
			if (Scene.aBoolean482 && method285(Scene.anInt483, Scene.anInt484, j6, l6, l5, i6, k6, k5)) {
				Scene.anInt485 = j1;
				Scene.anInt486 = k1;
			}
			if (class3.anInt99 == -1) {
				if (class3.anInt97 != 0xbc614e) {
					Rasterizer3D.method503(j6, l6, l5, i6, k6, k5, class3.anInt97, class3.anInt98, class3.anInt96);
				}
			} else if (!Scene.aBoolean451) {
				if (class3.aBoolean100) {
					Rasterizer3D.method507(j6, l6, l5, i6, k6, k5, class3.anInt97, class3.anInt98, class3.anInt96, i2,
							i3, l1, l3, i4, k4, k2, j2, j3, class3.anInt99);
				} else {
					Rasterizer3D.method507(j6, l6, l5, i6, k6, k5, class3.anInt97, class3.anInt98, class3.anInt96, l2,
							l1, i3, j4, k4, i4, k3, j3, j2, class3.anInt99);
				}
			} else {
				int i7 = Scene.anIntArray500[class3.anInt99];
				Rasterizer3D.method503(j6, l6, l5, i6, k6, k5, method284(class3.anInt97, i7, 0),
						method284(class3.anInt98, i7, 0), method284(class3.anInt96, i7, 0));
			}
		}
		if ((i5 - k5) * (l6 - l5) - (j5 - l5) * (k6 - k5) > 0) {
			Rasterizer3D.aBoolean1528 = false;
			if (i5 < 0 || k5 < 0 || k6 < 0 || i5 > Rasterizer.anInt1431 || k5 > Rasterizer.anInt1431
					|| k6 > Rasterizer.anInt1431) {
				Rasterizer3D.aBoolean1528 = true;
			}
			if (Scene.aBoolean482 && method285(Scene.anInt483, Scene.anInt484, j5, l5, l6, i5, k5, k6)) {
				Scene.anInt485 = j1;
				Scene.anInt486 = k1;
			}
			if (class3.anInt99 == -1) {
				if (class3.anInt95 != 0xbc614e) {
					Rasterizer3D.method503(j5, l5, l6, i5, k5, k6, class3.anInt95, class3.anInt96, class3.anInt98);
					return;
				}
			} else {
				if (!Scene.aBoolean451) {
					Rasterizer3D.method507(j5, l5, l6, i5, k5, k6, class3.anInt95, class3.anInt96, class3.anInt98, i2,
							i3, l1, l3, i4, k4, k2, j2, j3, class3.anInt99);
					return;
				}
				int j7 = Scene.anIntArray500[class3.anInt99];
				Rasterizer3D.method503(j5, l5, l6, i5, k5, k6, method284(class3.anInt95, j7, 0),
						method284(class3.anInt96, j7, 0), method284(class3.anInt98, j7, 0));
			}
		}
	}

	public void method283(int i, int j, ComplexTile class20, int k, int l, int i1, int j1, byte byte0) {
		int k1 = class20.anIntArray403.length;
		for (int l1 = 0; l1 < k1; l1++) {
			int i2 = class20.anIntArray403[l1] - Scene.anInt470;
			int k2 = class20.anIntArray404[l1] - Scene.anInt471;
			int i3 = class20.anIntArray405[l1] - Scene.anInt472;
			int k3 = i3 * j1 + i2 * j >> 16;
			i3 = i3 * j - i2 * j1 >> 16;
			i2 = k3;
			k3 = k2 * i - i3 * k >> 16;
			i3 = k2 * k + i3 * i >> 16;
			k2 = k3;
			if (i3 < 50) {
				return;
			}
			if (class20.anIntArray412 != null) {
				ComplexTile.anIntArray420[l1] = i2;
				ComplexTile.anIntArray421[l1] = k2;
				ComplexTile.anIntArray422[l1] = i3;
			}
			ComplexTile.anIntArray418[l1] = Rasterizer3D.anInt1532 + (i2 << 9) / i3;
			ComplexTile.anIntArray419[l1] = Rasterizer3D.anInt1533 + (k2 << 9) / i3;
		}

		Rasterizer3D.anInt1531 = 0;
		k1 = class20.anIntArray409.length;
		if (byte0 != 3) {
			return;
		}
		for (int j2 = 0; j2 < k1; j2++) {
			int l2 = class20.anIntArray409[j2];
			int j3 = class20.anIntArray410[j2];
			int l3 = class20.anIntArray411[j2];
			int i4 = ComplexTile.anIntArray418[l2];
			int j4 = ComplexTile.anIntArray418[j3];
			int k4 = ComplexTile.anIntArray418[l3];
			int l4 = ComplexTile.anIntArray419[l2];
			int i5 = ComplexTile.anIntArray419[j3];
			int j5 = ComplexTile.anIntArray419[l3];
			if ((i4 - j4) * (j5 - i5) - (l4 - i5) * (k4 - j4) > 0) {
				Rasterizer3D.aBoolean1528 = false;
				if (i4 < 0 || j4 < 0 || k4 < 0 || i4 > Rasterizer.anInt1431 || j4 > Rasterizer.anInt1431
						|| k4 > Rasterizer.anInt1431) {
					Rasterizer3D.aBoolean1528 = true;
				}
				if (Scene.aBoolean482 && method285(Scene.anInt483, Scene.anInt484, l4, i5, j5, i4, j4, k4)) {
					Scene.anInt485 = i1;
					Scene.anInt486 = l;
				}
				if (class20.anIntArray412 == null || class20.anIntArray412[j2] == -1) {
					if (class20.anIntArray406[j2] != 0xbc614e) {
						Rasterizer3D.method503(l4, i5, j5, i4, j4, k4, class20.anIntArray406[j2],
								class20.anIntArray407[j2], class20.anIntArray408[j2]);
					}
				} else if (!Scene.aBoolean451) {
					if (class20.aBoolean413) {
						Rasterizer3D.method507(l4, i5, j5, i4, j4, k4, class20.anIntArray406[j2],
								class20.anIntArray407[j2], class20.anIntArray408[j2], ComplexTile.anIntArray420[0],
								ComplexTile.anIntArray420[1], ComplexTile.anIntArray420[3],
								ComplexTile.anIntArray421[0], ComplexTile.anIntArray421[1],
								ComplexTile.anIntArray421[3], ComplexTile.anIntArray422[0],
								ComplexTile.anIntArray422[1], ComplexTile.anIntArray422[3], class20.anIntArray412[j2]);
					} else {
						Rasterizer3D
								.method507(l4, i5, j5, i4, j4, k4, class20.anIntArray406[j2],
										class20.anIntArray407[j2], class20.anIntArray408[j2],
										ComplexTile.anIntArray420[l2], ComplexTile.anIntArray420[j3],
										ComplexTile.anIntArray420[l3], ComplexTile.anIntArray421[l2],
										ComplexTile.anIntArray421[j3], ComplexTile.anIntArray421[l3],
										ComplexTile.anIntArray422[l2], ComplexTile.anIntArray422[j3],
										ComplexTile.anIntArray422[l3], class20.anIntArray412[j2]);
					}
				} else {
					int k5 = Scene.anIntArray500[class20.anIntArray412[j2]];
					Rasterizer3D.method503(l4, i5, j5, i4, j4, k4, method284(class20.anIntArray406[j2], k5, 0),
							method284(class20.anIntArray407[j2], k5, 0), method284(class20.anIntArray408[j2], k5, 0));
				}
			}
		}

	}

	public int method284(int i, int j, int k) {
		i = 127 - i;
		if (k != 0) {
			return anInt443;
		}
		i = (i * (j & 0x7f)) / 160;
		if (i < 2) {
			i = 2;
		} else if (i > 126) {
			i = 126;
		}
		return (j & 0xff80) + i;
	}

	public boolean method285(int i, int j, int k, int l, int i1, int j1, int k1, int l1) {
		if (j < k && j < l && j < i1) {
			return false;
		}
		if (j > k && j > l && j > i1) {
			return false;
		}
		if (i < j1 && i < k1 && i < l1) {
			return false;
		}
		if (i > j1 && i > k1 && i > l1) {
			return false;
		}
		int i2 = (j - k) * (k1 - j1) - (i - j1) * (l - k);
		int j2 = (j - i1) * (j1 - l1) - (i - l1) * (k - i1);
		int k2 = (j - l) * (l1 - k1) - (i - k1) * (i1 - l);
		return i2 * k2 > 0 && k2 * j2 > 0;
	}

	public void method286(int i) {
		int j = Scene.anIntArray488[Scene.anInt462];
		SceneCluster aclass39[] = Scene.aClass39ArrayArray489[Scene.anInt462];
		if (i < 2 || i > 2) {
			aBoolean441 = !aBoolean441;
		}
		Scene.anInt490 = 0;
		for (int k = 0; k < j; k++) {
			SceneCluster class39 = aclass39[k];
			if (class39.anInt679 == 1) {
				int l = (class39.anInt675 - Scene.anInt468) + 25;
				if (l < 0 || l > 50) {
					continue;
				}
				int k1 = (class39.anInt677 - Scene.anInt469) + 25;
				if (k1 < 0) {
					k1 = 0;
				}
				int j2 = (class39.anInt678 - Scene.anInt469) + 25;
				if (j2 > 50) {
					j2 = 50;
				}
				boolean flag = false;
				while (k1 <= j2) {
					if (Scene.aBooleanArrayArray507[l][k1++]) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					continue;
				}
				int j3 = Scene.anInt470 - class39.anInt680;
				if (j3 > 32) {
					class39.anInt686 = 1;
				} else {
					if (j3 >= -32) {
						continue;
					}
					class39.anInt686 = 2;
					j3 = -j3;
				}
				class39.anInt689 = (class39.anInt682 - Scene.anInt472 << 8) / j3;
				class39.anInt690 = (class39.anInt683 - Scene.anInt472 << 8) / j3;
				class39.anInt691 = (class39.anInt684 - Scene.anInt471 << 8) / j3;
				class39.anInt692 = (class39.anInt685 - Scene.anInt471 << 8) / j3;
				Scene.aClass39Array491[Scene.anInt490++] = class39;
				continue;
			}
			if (class39.anInt679 == 2) {
				int i1 = (class39.anInt677 - Scene.anInt469) + 25;
				if (i1 < 0 || i1 > 50) {
					continue;
				}
				int l1 = (class39.anInt675 - Scene.anInt468) + 25;
				if (l1 < 0) {
					l1 = 0;
				}
				int k2 = (class39.anInt676 - Scene.anInt468) + 25;
				if (k2 > 50) {
					k2 = 50;
				}
				boolean flag1 = false;
				while (l1 <= k2) {
					if (Scene.aBooleanArrayArray507[l1++][i1]) {
						flag1 = true;
						break;
					}
				}
				if (!flag1) {
					continue;
				}
				int k3 = Scene.anInt472 - class39.anInt682;
				if (k3 > 32) {
					class39.anInt686 = 3;
				} else {
					if (k3 >= -32) {
						continue;
					}
					class39.anInt686 = 4;
					k3 = -k3;
				}
				class39.anInt687 = (class39.anInt680 - Scene.anInt470 << 8) / k3;
				class39.anInt688 = (class39.anInt681 - Scene.anInt470 << 8) / k3;
				class39.anInt691 = (class39.anInt684 - Scene.anInt471 << 8) / k3;
				class39.anInt692 = (class39.anInt685 - Scene.anInt471 << 8) / k3;
				Scene.aClass39Array491[Scene.anInt490++] = class39;
			} else if (class39.anInt679 == 4) {
				int j1 = class39.anInt684 - Scene.anInt471;
				if (j1 > 128) {
					int i2 = (class39.anInt677 - Scene.anInt469) + 25;
					if (i2 < 0) {
						i2 = 0;
					}
					int l2 = (class39.anInt678 - Scene.anInt469) + 25;
					if (l2 > 50) {
						l2 = 50;
					}
					if (i2 <= l2) {
						int i3 = (class39.anInt675 - Scene.anInt468) + 25;
						if (i3 < 0) {
							i3 = 0;
						}
						int l3 = (class39.anInt676 - Scene.anInt468) + 25;
						if (l3 > 50) {
							l3 = 50;
						}
						boolean flag2 = false;
						label0: for (int i4 = i3; i4 <= l3; i4++) {
							for (int j4 = i2; j4 <= l2; j4++) {
								if (!Scene.aBooleanArrayArray507[i4][j4]) {
									continue;
								}
								flag2 = true;
								break label0;
							}

						}

						if (flag2) {
							class39.anInt686 = 5;
							class39.anInt687 = (class39.anInt680 - Scene.anInt470 << 8) / j1;
							class39.anInt688 = (class39.anInt681 - Scene.anInt470 << 8) / j1;
							class39.anInt689 = (class39.anInt682 - Scene.anInt472 << 8) / j1;
							class39.anInt690 = (class39.anInt683 - Scene.anInt472 << 8) / j1;
							Scene.aClass39Array491[Scene.anInt490++] = class39;
						}
					}
				}
			}
		}

	}

	public boolean method287(int i, int j, int k) {
		int l = anIntArrayArrayArray460[i][j][k];
		if (l == -Scene.anInt463) {
			return false;
		}
		if (l == Scene.anInt463) {
			return true;
		}
		int i1 = j << 7;
		int j1 = k << 7;
		if (method291(i1 + 1, anIntArrayArrayArray455[i][j][k], j1 + 1)
				&& method291((i1 + 128) - 1, anIntArrayArrayArray455[i][j + 1][k], j1 + 1)
				&& method291((i1 + 128) - 1, anIntArrayArrayArray455[i][j + 1][k + 1], (j1 + 128) - 1)
				&& method291(i1 + 1, anIntArrayArrayArray455[i][j][k + 1], (j1 + 128) - 1)) {
			anIntArrayArrayArray460[i][j][k] = Scene.anInt463;
			return true;
		} else {
			anIntArrayArrayArray460[i][j][k] = -Scene.anInt463;
			return false;
		}
	}

	public boolean method288(int i, int j, int k, int l) {
		if (!method287(i, j, k)) {
			return false;
		}
		int i1 = j << 7;
		int j1 = k << 7;
		int k1 = anIntArrayArrayArray455[i][j][k] - 1;
		int l1 = k1 - 120;
		int i2 = k1 - 230;
		int j2 = k1 - 238;
		if (l < 16) {
			if (l == 1) {
				if (i1 > Scene.anInt470) {
					if (!method291(i1, k1, j1)) {
						return false;
					}
					if (!method291(i1, k1, j1 + 128)) {
						return false;
					}
				}
				if (i > 0) {
					if (!method291(i1, l1, j1)) {
						return false;
					}
					if (!method291(i1, l1, j1 + 128)) {
						return false;
					}
				}
				if (!method291(i1, i2, j1)) {
					return false;
				}
				return method291(i1, i2, j1 + 128);
			}
			if (l == 2) {
				if (j1 < Scene.anInt472) {
					if (!method291(i1, k1, j1 + 128)) {
						return false;
					}
					if (!method291(i1 + 128, k1, j1 + 128)) {
						return false;
					}
				}
				if (i > 0) {
					if (!method291(i1, l1, j1 + 128)) {
						return false;
					}
					if (!method291(i1 + 128, l1, j1 + 128)) {
						return false;
					}
				}
				if (!method291(i1, i2, j1 + 128)) {
					return false;
				}
				return method291(i1 + 128, i2, j1 + 128);
			}
			if (l == 4) {
				if (i1 < Scene.anInt470) {
					if (!method291(i1 + 128, k1, j1)) {
						return false;
					}
					if (!method291(i1 + 128, k1, j1 + 128)) {
						return false;
					}
				}
				if (i > 0) {
					if (!method291(i1 + 128, l1, j1)) {
						return false;
					}
					if (!method291(i1 + 128, l1, j1 + 128)) {
						return false;
					}
				}
				if (!method291(i1 + 128, i2, j1)) {
					return false;
				}
				return method291(i1 + 128, i2, j1 + 128);
			}
			if (l == 8) {
				if (j1 > Scene.anInt472) {
					if (!method291(i1, k1, j1)) {
						return false;
					}
					if (!method291(i1 + 128, k1, j1)) {
						return false;
					}
				}
				if (i > 0) {
					if (!method291(i1, l1, j1)) {
						return false;
					}
					if (!method291(i1 + 128, l1, j1)) {
						return false;
					}
				}
				if (!method291(i1, i2, j1)) {
					return false;
				}
				return method291(i1 + 128, i2, j1);
			}
		}
		if (!method291(i1 + 64, j2, j1 + 64)) {
			return false;
		}
		if (l == 16) {
			return method291(i1, i2, j1 + 128);
		}
		if (l == 32) {
			return method291(i1 + 128, i2, j1 + 128);
		}
		if (l == 64) {
			return method291(i1 + 128, i2, j1);
		}
		if (l == 128) {
			return method291(i1, i2, j1);
		} else {
			System.out.println("Warning unsupported wall type");
			return true;
		}
	}

	public boolean method289(int i, int j, int k, int l) {
		if (!method287(i, j, k)) {
			return false;
		}
		int i1 = j << 7;
		int j1 = k << 7;
		return method291(i1 + 1, anIntArrayArrayArray455[i][j][k] - l, j1 + 1)
				&& method291((i1 + 128) - 1, anIntArrayArrayArray455[i][j + 1][k] - l, j1 + 1)
				&& method291((i1 + 128) - 1, anIntArrayArrayArray455[i][j + 1][k + 1] - l, (j1 + 128) - 1)
				&& method291(i1 + 1, anIntArrayArrayArray455[i][j][k + 1] - l, (j1 + 128) - 1);
	}

	public boolean method290(int i, int j, int k, int l, int i1, int j1) {
		if (j == k && l == i1) {
			if (!method287(i, j, l)) {
				return false;
			}
			int k1 = j << 7;
			int i2 = l << 7;
			return method291(k1 + 1, anIntArrayArrayArray455[i][j][l] - j1, i2 + 1)
					&& method291((k1 + 128) - 1, anIntArrayArrayArray455[i][j + 1][l] - j1, i2 + 1)
					&& method291((k1 + 128) - 1, anIntArrayArrayArray455[i][j + 1][l + 1] - j1, (i2 + 128) - 1)
					&& method291(k1 + 1, anIntArrayArrayArray455[i][j][l + 1] - j1, (i2 + 128) - 1);
		}
		for (int l1 = j; l1 <= k; l1++) {
			for (int j2 = l; j2 <= i1; j2++) {
				if (anIntArrayArrayArray460[i][l1][j2] == -Scene.anInt463) {
					return false;
				}
			}

		}

		int k2 = (j << 7) + 1;
		int l2 = (l << 7) + 2;
		int i3 = anIntArrayArrayArray455[i][j][l] - j1;
		if (!method291(k2, i3, l2)) {
			return false;
		}
		int j3 = (k << 7) - 1;
		if (!method291(j3, i3, l2)) {
			return false;
		}
		int k3 = (i1 << 7) - 1;
		if (!method291(k2, i3, k3)) {
			return false;
		}
		return method291(j3, i3, k3);
	}

	public boolean method291(int i, int j, int k) {
		for (int l = 0; l < Scene.anInt490; l++) {
			SceneCluster class39 = Scene.aClass39Array491[l];
			if (class39.anInt686 == 1) {
				int i1 = class39.anInt680 - i;
				if (i1 > 0) {
					int j2 = class39.anInt682 + (class39.anInt689 * i1 >> 8);
					int k3 = class39.anInt683 + (class39.anInt690 * i1 >> 8);
					int l4 = class39.anInt684 + (class39.anInt691 * i1 >> 8);
					int i6 = class39.anInt685 + (class39.anInt692 * i1 >> 8);
					if (k >= j2 && k <= k3 && j >= l4 && j <= i6) {
						return true;
					}
				}
			} else if (class39.anInt686 == 2) {
				int j1 = i - class39.anInt680;
				if (j1 > 0) {
					int k2 = class39.anInt682 + (class39.anInt689 * j1 >> 8);
					int l3 = class39.anInt683 + (class39.anInt690 * j1 >> 8);
					int i5 = class39.anInt684 + (class39.anInt691 * j1 >> 8);
					int j6 = class39.anInt685 + (class39.anInt692 * j1 >> 8);
					if (k >= k2 && k <= l3 && j >= i5 && j <= j6) {
						return true;
					}
				}
			} else if (class39.anInt686 == 3) {
				int k1 = class39.anInt682 - k;
				if (k1 > 0) {
					int l2 = class39.anInt680 + (class39.anInt687 * k1 >> 8);
					int i4 = class39.anInt681 + (class39.anInt688 * k1 >> 8);
					int j5 = class39.anInt684 + (class39.anInt691 * k1 >> 8);
					int k6 = class39.anInt685 + (class39.anInt692 * k1 >> 8);
					if (i >= l2 && i <= i4 && j >= j5 && j <= k6) {
						return true;
					}
				}
			} else if (class39.anInt686 == 4) {
				int l1 = k - class39.anInt682;
				if (l1 > 0) {
					int i3 = class39.anInt680 + (class39.anInt687 * l1 >> 8);
					int j4 = class39.anInt681 + (class39.anInt688 * l1 >> 8);
					int k5 = class39.anInt684 + (class39.anInt691 * l1 >> 8);
					int l6 = class39.anInt685 + (class39.anInt692 * l1 >> 8);
					if (i >= i3 && i <= j4 && j >= k5 && j <= l6) {
						return true;
					}
				}
			} else if (class39.anInt686 == 5) {
				int i2 = j - class39.anInt684;
				if (i2 > 0) {
					int j3 = class39.anInt680 + (class39.anInt687 * i2 >> 8);
					int k4 = class39.anInt681 + (class39.anInt688 * i2 >> 8);
					int l5 = class39.anInt682 + (class39.anInt689 * i2 >> 8);
					int i7 = class39.anInt683 + (class39.anInt690 * i2 >> 8);
					if (i >= j3 && i <= k4 && k >= l5 && k <= i7) {
						return true;
					}
				}
			}
		}

		return false;
	}

	static {
		Scene.anInt487 = 4;
		Scene.anIntArray488 = new int[Scene.anInt487];
		Scene.aClass39ArrayArray489 = new SceneCluster[Scene.anInt487][500];
	}
}
