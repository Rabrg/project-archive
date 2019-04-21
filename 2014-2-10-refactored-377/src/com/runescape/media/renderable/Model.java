package com.runescape.media.renderable;

import com.runescape.media.Animation;
import com.runescape.media.Rasterizer;
import com.runescape.media.Rasterizer3D;
import com.runescape.media.Skins;
import com.runescape.media.VertexNormal;
import com.runescape.net.Buffer;
import com.runescape.net.requester.Requester;

public class Model extends Renderable {
	
	public int anInt1636;
	public int anInt1637;
	public boolean aBoolean1638;
	public boolean aBoolean1639;
	public int anInt1640;
	public boolean aBoolean1641;
	public static int anInt1642;
	public static Model aClass50_Sub1_Sub4_Sub4_1643 = new Model(852);
	public static int anIntArray1644[] = new int[2000];
	public static int anIntArray1645[] = new int[2000];
	public static int anIntArray1646[] = new int[2000];
	public static int anIntArray1647[] = new int[2000];
	public int anInt1648;
	public int anIntArray1649[];
	public int anIntArray1650[];
	public int anIntArray1651[];
	public int anInt1652;
	public int anIntArray1653[];
	public int anIntArray1654[];
	public int anIntArray1655[];
	public int anIntArray1656[];
	public int anIntArray1657[];
	public int anIntArray1658[];
	public int anIntArray1659[];
	public int anIntArray1660[];
	public int anIntArray1661[];
	public int anIntArray1662[];
	public int anInt1663;
	public int anInt1664;
	public int anIntArray1665[];
	public int anIntArray1666[];
	public int anIntArray1667[];
	public int anInt1668;
	public int anInt1669;
	public int anInt1670;
	public int anInt1671;
	public int anInt1672;
	public int anInt1673;
	public int anInt1674;
	public int anInt1675;
	public int anIntArray1676[];
	public int anIntArray1677[];
	public int anIntArrayArray1678[][];
	public int anIntArrayArray1679[][];
	public boolean aBoolean1680;
	public VertexNormal aClass40Array1681[];
	public static ModelHeader aClass26Array1682[];
	public static Requester aClass32_1683;
	public static boolean aBooleanArray1684[] = new boolean[4096];
	public static boolean aBooleanArray1685[] = new boolean[4096];
	public static int anIntArray1686[] = new int[4096];
	public static int anIntArray1687[] = new int[4096];
	public static int anIntArray1688[] = new int[4096];
	public static int anIntArray1689[] = new int[4096];
	public static int anIntArray1690[] = new int[4096];
	public static int anIntArray1691[] = new int[4096];
	public static int anIntArray1692[] = new int[1500];
	public static int anIntArrayArray1693[][] = new int[1500][512];
	public static int anIntArray1694[] = new int[12];
	public static int anIntArrayArray1695[][] = new int[12][2000];
	public static int anIntArray1696[] = new int[2000];
	public static int anIntArray1697[] = new int[2000];
	public static int anIntArray1698[] = new int[12];
	public static int anIntArray1699[] = new int[10];
	public static int anIntArray1700[] = new int[10];
	public static int anIntArray1701[] = new int[10];
	public static int anInt1702;
	public static int anInt1703;
	public static int anInt1704;
	public static boolean aBoolean1705;
	public static int anInt1706;
	public static int anInt1707;
	public static int anInt1708;
	public static int anIntArray1709[] = new int[1000];
	public static int anIntArray1710[];
	public static int anIntArray1711[];
	public static int anIntArray1712[];
	public static int anIntArray1713[];
	
	public static void method573(boolean flag) {
		Model.aClass26Array1682 = null;
		Model.aBooleanArray1684 = null;
		Model.aBooleanArray1685 = null;
		Model.anIntArray1686 = null;
		Model.anIntArray1687 = null;
		Model.anIntArray1688 = null;
		Model.anIntArray1689 = null;
		Model.anIntArray1690 = null;
		Model.anIntArray1691 = null;
		Model.anIntArray1692 = null;
		Model.anIntArrayArray1693 = null;
		Model.anIntArray1694 = null;
		Model.anIntArrayArray1695 = null;
		Model.anIntArray1696 = null;
		Model.anIntArray1697 = null;
		Model.anIntArray1698 = null;
		Model.anIntArray1710 = null;
		Model.anIntArray1711 = null;
		Model.anIntArray1712 = null;
		if (flag) {
			return;
		} else {
			Model.anIntArray1713 = null;
			return;
		}
	}

	public static void method574(int i, Requester class32) {
		Model.aClass26Array1682 = new ModelHeader[i];
		Model.aClass32_1683 = class32;
	}

	public static void method575(byte abyte0[], int i, byte byte0) {
		if (byte0 != 7) {
			return;
		}
		if (abyte0 == null) {
			ModelHeader class26 = Model.aClass26Array1682[i] = new ModelHeader();
			class26.anInt534 = 0;
			class26.anInt535 = 0;
			class26.anInt536 = 0;
			return;
		}
		Buffer class50_sub1_sub2 = new Buffer(true, abyte0);
		class50_sub1_sub2.position = abyte0.length - 18;
		ModelHeader class26_1 = Model.aClass26Array1682[i] = new ModelHeader();
		class26_1.aByteArray533 = abyte0;
		class26_1.anInt534 = class50_sub1_sub2.method523();
		class26_1.anInt535 = class50_sub1_sub2.method523();
		class26_1.anInt536 = class50_sub1_sub2.method521();
		int j = class50_sub1_sub2.method521();
		int k = class50_sub1_sub2.method521();
		int l = class50_sub1_sub2.method521();
		int i1 = class50_sub1_sub2.method521();
		int j1 = class50_sub1_sub2.method521();
		int k1 = class50_sub1_sub2.method523();
		int l1 = class50_sub1_sub2.method523();
		int i2 = class50_sub1_sub2.method523();
		int j2 = class50_sub1_sub2.method523();
		int k2 = 0;
		class26_1.anInt537 = k2;
		k2 += class26_1.anInt534;
		class26_1.anInt543 = k2;
		k2 += class26_1.anInt535;
		class26_1.anInt546 = k2;
		if (k == 255) {
			k2 += class26_1.anInt535;
		} else {
			class26_1.anInt546 = -k - 1;
		}
		class26_1.anInt548 = k2;
		if (i1 == 1) {
			k2 += class26_1.anInt535;
		} else {
			class26_1.anInt548 = -1;
		}
		class26_1.anInt545 = k2;
		if (j == 1) {
			k2 += class26_1.anInt535;
		} else {
			class26_1.anInt545 = -1;
		}
		class26_1.anInt541 = k2;
		if (j1 == 1) {
			k2 += class26_1.anInt534;
		} else {
			class26_1.anInt541 = -1;
		}
		class26_1.anInt547 = k2;
		if (l == 1) {
			k2 += class26_1.anInt535;
		} else {
			class26_1.anInt547 = -1;
		}
		class26_1.anInt542 = k2;
		k2 += j2;
		class26_1.anInt544 = k2;
		k2 += class26_1.anInt535 * 2;
		class26_1.anInt549 = k2;
		k2 += class26_1.anInt536 * 6;
		class26_1.anInt538 = k2;
		k2 += k1;
		class26_1.anInt539 = k2;
		k2 += l1;
		class26_1.anInt540 = k2;
		k2 += i2;
	}

	public static void method576(int i, int j) {
		if (j != 1) {
			return;
		} else {
			Model.aClass26Array1682[i] = null;
			return;
		}
	}

	public static Model method577(int i) {
		if (Model.aClass26Array1682 == null) {
			return null;
		}
		ModelHeader class26 = Model.aClass26Array1682[i];
		if (class26 == null) {
			Model.aClass32_1683.request(i);
			return null;
		} else {
			return new Model(i, -478);
		}
	}

	public static boolean method578(int i) {
		if (Model.aClass26Array1682 == null) {
			return false;
		}
		ModelHeader class26 = Model.aClass26Array1682[i];
		if (class26 == null) {
			Model.aClass32_1683.request(i);
			return false;
		} else {
			return true;
		}
	}

	public Model(int i) {
		anInt1636 = 932;
		anInt1637 = 426;
		aBoolean1638 = false;
		aBoolean1639 = true;
		anInt1640 = -252;
		aBoolean1641 = false;
		aBoolean1680 = false;
		if (i <= 0) {
			anInt1640 = -110;
		}
	}

	public Model(int i, int j) {
		anInt1636 = 932;
		anInt1637 = 426;
		aBoolean1638 = false;
		aBoolean1639 = true;
		anInt1640 = -252;
		aBoolean1641 = false;
		aBoolean1680 = false;
		Model.anInt1642++;
		ModelHeader class26 = Model.aClass26Array1682[i];
		anInt1648 = class26.anInt534;
		anInt1652 = class26.anInt535;
		anInt1664 = class26.anInt536;
		anIntArray1649 = new int[anInt1648];
		anIntArray1650 = new int[anInt1648];
		anIntArray1651 = new int[anInt1648];
		anIntArray1653 = new int[anInt1652];
		anIntArray1654 = new int[anInt1652];
		anIntArray1655 = new int[anInt1652];
		anIntArray1665 = new int[anInt1664];
		anIntArray1666 = new int[anInt1664];
		anIntArray1667 = new int[anInt1664];
		if (class26.anInt541 >= 0) {
			anIntArray1676 = new int[anInt1648];
		}
		if (class26.anInt545 >= 0) {
			anIntArray1659 = new int[anInt1652];
		}
		if (class26.anInt546 >= 0) {
			anIntArray1660 = new int[anInt1652];
		} else {
			anInt1663 = -class26.anInt546 - 1;
		}
		if (class26.anInt547 >= 0) {
			anIntArray1661 = new int[anInt1652];
		}
		if (class26.anInt548 >= 0) {
			anIntArray1677 = new int[anInt1652];
		}
		anIntArray1662 = new int[anInt1652];
		Buffer class50_sub1_sub2 = new Buffer(true, class26.aByteArray533);
		class50_sub1_sub2.position = class26.anInt537;
		Buffer class50_sub1_sub2_1 = new Buffer(true, class26.aByteArray533);
		class50_sub1_sub2_1.position = class26.anInt538;
		Buffer class50_sub1_sub2_2 = new Buffer(true, class26.aByteArray533);
		class50_sub1_sub2_2.position = class26.anInt539;
		if (j >= 0) {
			aBoolean1641 = !aBoolean1641;
		}
		Buffer class50_sub1_sub2_3 = new Buffer(true, class26.aByteArray533);
		class50_sub1_sub2_3.position = class26.anInt540;
		Buffer class50_sub1_sub2_4 = new Buffer(true, class26.aByteArray533);
		class50_sub1_sub2_4.position = class26.anInt541;
		int k = 0;
		int l = 0;
		int i1 = 0;
		for (int j1 = 0; j1 < anInt1648; j1++) {
			int k1 = class50_sub1_sub2.method521();
			int i2 = 0;
			if ((k1 & 1) != 0) {
				i2 = class50_sub1_sub2_1.method534();
			}
			int k2 = 0;
			if ((k1 & 2) != 0) {
				k2 = class50_sub1_sub2_2.method534();
			}
			int i3 = 0;
			if ((k1 & 4) != 0) {
				i3 = class50_sub1_sub2_3.method534();
			}
			anIntArray1649[j1] = k + i2;
			anIntArray1650[j1] = l + k2;
			anIntArray1651[j1] = i1 + i3;
			k = anIntArray1649[j1];
			l = anIntArray1650[j1];
			i1 = anIntArray1651[j1];
			if (anIntArray1676 != null) {
				anIntArray1676[j1] = class50_sub1_sub2_4.method521();
			}
		}

		class50_sub1_sub2.position = class26.anInt544;
		class50_sub1_sub2_1.position = class26.anInt545;
		class50_sub1_sub2_2.position = class26.anInt546;
		class50_sub1_sub2_3.position = class26.anInt547;
		class50_sub1_sub2_4.position = class26.anInt548;
		for (int l1 = 0; l1 < anInt1652; l1++) {
			anIntArray1662[l1] = class50_sub1_sub2.method523();
			if (anIntArray1659 != null) {
				anIntArray1659[l1] = class50_sub1_sub2_1.method521();
			}
			if (anIntArray1660 != null) {
				anIntArray1660[l1] = class50_sub1_sub2_2.method521();
			}
			if (anIntArray1661 != null) {
				anIntArray1661[l1] = class50_sub1_sub2_3.method521();
			}
			if (anIntArray1677 != null) {
				anIntArray1677[l1] = class50_sub1_sub2_4.method521();
			}
		}

		class50_sub1_sub2.position = class26.anInt542;
		class50_sub1_sub2_1.position = class26.anInt543;
		int j2 = 0;
		int l2 = 0;
		int j3 = 0;
		int k3 = 0;
		for (int l3 = 0; l3 < anInt1652; l3++) {
			int i4 = class50_sub1_sub2_1.method521();
			if (i4 == 1) {
				j2 = class50_sub1_sub2.method534() + k3;
				k3 = j2;
				l2 = class50_sub1_sub2.method534() + k3;
				k3 = l2;
				j3 = class50_sub1_sub2.method534() + k3;
				k3 = j3;
				anIntArray1653[l3] = j2;
				anIntArray1654[l3] = l2;
				anIntArray1655[l3] = j3;
			}
			if (i4 == 2) {
				l2 = j3;
				j3 = class50_sub1_sub2.method534() + k3;
				k3 = j3;
				anIntArray1653[l3] = j2;
				anIntArray1654[l3] = l2;
				anIntArray1655[l3] = j3;
			}
			if (i4 == 3) {
				j2 = j3;
				j3 = class50_sub1_sub2.method534() + k3;
				k3 = j3;
				anIntArray1653[l3] = j2;
				anIntArray1654[l3] = l2;
				anIntArray1655[l3] = j3;
			}
			if (i4 == 4) {
				int k4 = j2;
				j2 = l2;
				l2 = k4;
				j3 = class50_sub1_sub2.method534() + k3;
				k3 = j3;
				anIntArray1653[l3] = j2;
				anIntArray1654[l3] = l2;
				anIntArray1655[l3] = j3;
			}
		}

		class50_sub1_sub2.position = class26.anInt549;
		for (int j4 = 0; j4 < anInt1664; j4++) {
			anIntArray1665[j4] = class50_sub1_sub2.method523();
			anIntArray1666[j4] = class50_sub1_sub2.method523();
			anIntArray1667[j4] = class50_sub1_sub2.method523();
		}

	}

	public Model(int i, Model aclass50_sub1_sub4_sub4[], byte byte0) {
		anInt1636 = 932;
		anInt1637 = 426;
		aBoolean1638 = false;
		aBoolean1639 = true;
		anInt1640 = -252;
		aBoolean1641 = false;
		aBoolean1680 = false;
		Model.anInt1642++;
		boolean flag = false;
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		anInt1648 = 0;
		anInt1652 = 0;
		anInt1664 = 0;
		anInt1663 = -1;
		for (int j = 0; j < i; j++) {
			Model class50_sub1_sub4_sub4 = aclass50_sub1_sub4_sub4[j];
			if (class50_sub1_sub4_sub4 != null) {
				anInt1648 += class50_sub1_sub4_sub4.anInt1648;
				anInt1652 += class50_sub1_sub4_sub4.anInt1652;
				anInt1664 += class50_sub1_sub4_sub4.anInt1664;
				flag |= class50_sub1_sub4_sub4.anIntArray1659 != null;
				if (class50_sub1_sub4_sub4.anIntArray1660 != null) {
					flag1 = true;
				} else {
					if (anInt1663 == -1) {
						anInt1663 = class50_sub1_sub4_sub4.anInt1663;
					}
					if (anInt1663 != class50_sub1_sub4_sub4.anInt1663) {
						flag1 = true;
					}
				}
				flag2 |= class50_sub1_sub4_sub4.anIntArray1661 != null;
				flag3 |= class50_sub1_sub4_sub4.anIntArray1677 != null;
			}
		}

		anIntArray1649 = new int[anInt1648];
		anIntArray1650 = new int[anInt1648];
		anIntArray1651 = new int[anInt1648];
		if (byte0 != -89) {
			throw new NullPointerException();
		}
		anIntArray1676 = new int[anInt1648];
		anIntArray1653 = new int[anInt1652];
		anIntArray1654 = new int[anInt1652];
		anIntArray1655 = new int[anInt1652];
		anIntArray1665 = new int[anInt1664];
		anIntArray1666 = new int[anInt1664];
		anIntArray1667 = new int[anInt1664];
		if (flag) {
			anIntArray1659 = new int[anInt1652];
		}
		if (flag1) {
			anIntArray1660 = new int[anInt1652];
		}
		if (flag2) {
			anIntArray1661 = new int[anInt1652];
		}
		if (flag3) {
			anIntArray1677 = new int[anInt1652];
		}
		anIntArray1662 = new int[anInt1652];
		anInt1648 = 0;
		anInt1652 = 0;
		anInt1664 = 0;
		int k = 0;
		for (int l = 0; l < i; l++) {
			Model class50_sub1_sub4_sub4_1 = aclass50_sub1_sub4_sub4[l];
			if (class50_sub1_sub4_sub4_1 != null) {
				for (int i1 = 0; i1 < class50_sub1_sub4_sub4_1.anInt1652; i1++) {
					if (flag) {
						if (class50_sub1_sub4_sub4_1.anIntArray1659 == null) {
							anIntArray1659[anInt1652] = 0;
						} else {
							int j1 = class50_sub1_sub4_sub4_1.anIntArray1659[i1];
							if ((j1 & 2) == 2) {
								j1 += k << 2;
							}
							anIntArray1659[anInt1652] = j1;
						}
					}
					if (flag1) {
						if (class50_sub1_sub4_sub4_1.anIntArray1660 == null) {
							anIntArray1660[anInt1652] = class50_sub1_sub4_sub4_1.anInt1663;
						} else {
							anIntArray1660[anInt1652] = class50_sub1_sub4_sub4_1.anIntArray1660[i1];
						}
					}
					if (flag2) {
						if (class50_sub1_sub4_sub4_1.anIntArray1661 == null) {
							anIntArray1661[anInt1652] = 0;
						} else {
							anIntArray1661[anInt1652] = class50_sub1_sub4_sub4_1.anIntArray1661[i1];
						}
					}
					if (flag3 && class50_sub1_sub4_sub4_1.anIntArray1677 != null) {
						anIntArray1677[anInt1652] = class50_sub1_sub4_sub4_1.anIntArray1677[i1];
					}
					anIntArray1662[anInt1652] = class50_sub1_sub4_sub4_1.anIntArray1662[i1];
					anIntArray1653[anInt1652] = method580(class50_sub1_sub4_sub4_1,
							class50_sub1_sub4_sub4_1.anIntArray1653[i1]);
					anIntArray1654[anInt1652] = method580(class50_sub1_sub4_sub4_1,
							class50_sub1_sub4_sub4_1.anIntArray1654[i1]);
					anIntArray1655[anInt1652] = method580(class50_sub1_sub4_sub4_1,
							class50_sub1_sub4_sub4_1.anIntArray1655[i1]);
					anInt1652++;
				}

				for (int k1 = 0; k1 < class50_sub1_sub4_sub4_1.anInt1664; k1++) {
					anIntArray1665[anInt1664] = method580(class50_sub1_sub4_sub4_1,
							class50_sub1_sub4_sub4_1.anIntArray1665[k1]);
					anIntArray1666[anInt1664] = method580(class50_sub1_sub4_sub4_1,
							class50_sub1_sub4_sub4_1.anIntArray1666[k1]);
					anIntArray1667[anInt1664] = method580(class50_sub1_sub4_sub4_1,
							class50_sub1_sub4_sub4_1.anIntArray1667[k1]);
					anInt1664++;
				}

				k += class50_sub1_sub4_sub4_1.anInt1664;
			}
		}

	}

	public Model(int i, boolean flag, int j, Model aclass50_sub1_sub4_sub4[]) {
		anInt1636 = 932;
		anInt1637 = 426;
		aBoolean1638 = false;
		aBoolean1639 = true;
		anInt1640 = -252;
		aBoolean1641 = false;
		aBoolean1680 = false;
		Model.anInt1642++;
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		boolean flag4 = false;
		anInt1648 = 0;
		anInt1652 = 0;
		anInt1664 = 0;
		anInt1663 = -1;
		for (int k = 0; k < i; k++) {
			Model class50_sub1_sub4_sub4 = aclass50_sub1_sub4_sub4[k];
			if (class50_sub1_sub4_sub4 != null) {
				anInt1648 += class50_sub1_sub4_sub4.anInt1648;
				anInt1652 += class50_sub1_sub4_sub4.anInt1652;
				anInt1664 += class50_sub1_sub4_sub4.anInt1664;
				flag1 |= class50_sub1_sub4_sub4.anIntArray1659 != null;
				if (class50_sub1_sub4_sub4.anIntArray1660 != null) {
					flag2 = true;
				} else {
					if (anInt1663 == -1) {
						anInt1663 = class50_sub1_sub4_sub4.anInt1663;
					}
					if (anInt1663 != class50_sub1_sub4_sub4.anInt1663) {
						flag2 = true;
					}
				}
				flag3 |= class50_sub1_sub4_sub4.anIntArray1661 != null;
				flag4 |= class50_sub1_sub4_sub4.anIntArray1662 != null;
			}
		}

		anIntArray1649 = new int[anInt1648];
		anIntArray1650 = new int[anInt1648];
		anIntArray1651 = new int[anInt1648];
		anIntArray1653 = new int[anInt1652];
		anIntArray1654 = new int[anInt1652];
		anIntArray1655 = new int[anInt1652];
		anIntArray1656 = new int[anInt1652];
		anIntArray1657 = new int[anInt1652];
		anIntArray1658 = new int[anInt1652];
		anIntArray1665 = new int[anInt1664];
		anIntArray1666 = new int[anInt1664];
		anIntArray1667 = new int[anInt1664];
		if (flag1) {
			anIntArray1659 = new int[anInt1652];
		}
		if (flag2) {
			anIntArray1660 = new int[anInt1652];
		}
		if (flag3) {
			anIntArray1661 = new int[anInt1652];
		}
		if (flag4) {
			anIntArray1662 = new int[anInt1652];
		}
		anInt1648 = 0;
		if (j != 0) {
			throw new NullPointerException();
		}
		anInt1652 = 0;
		anInt1664 = 0;
		int l = 0;
		for (int i1 = 0; i1 < i; i1++) {
			Model class50_sub1_sub4_sub4_1 = aclass50_sub1_sub4_sub4[i1];
			if (class50_sub1_sub4_sub4_1 != null) {
				int j1 = anInt1648;
				for (int k1 = 0; k1 < class50_sub1_sub4_sub4_1.anInt1648; k1++) {
					anIntArray1649[anInt1648] = class50_sub1_sub4_sub4_1.anIntArray1649[k1];
					anIntArray1650[anInt1648] = class50_sub1_sub4_sub4_1.anIntArray1650[k1];
					anIntArray1651[anInt1648] = class50_sub1_sub4_sub4_1.anIntArray1651[k1];
					anInt1648++;
				}

				for (int l1 = 0; l1 < class50_sub1_sub4_sub4_1.anInt1652; l1++) {
					anIntArray1653[anInt1652] = class50_sub1_sub4_sub4_1.anIntArray1653[l1] + j1;
					anIntArray1654[anInt1652] = class50_sub1_sub4_sub4_1.anIntArray1654[l1] + j1;
					anIntArray1655[anInt1652] = class50_sub1_sub4_sub4_1.anIntArray1655[l1] + j1;
					anIntArray1656[anInt1652] = class50_sub1_sub4_sub4_1.anIntArray1656[l1];
					anIntArray1657[anInt1652] = class50_sub1_sub4_sub4_1.anIntArray1657[l1];
					anIntArray1658[anInt1652] = class50_sub1_sub4_sub4_1.anIntArray1658[l1];
					if (flag1) {
						if (class50_sub1_sub4_sub4_1.anIntArray1659 == null) {
							anIntArray1659[anInt1652] = 0;
						} else {
							int i2 = class50_sub1_sub4_sub4_1.anIntArray1659[l1];
							if ((i2 & 2) == 2) {
								i2 += l << 2;
							}
							anIntArray1659[anInt1652] = i2;
						}
					}
					if (flag2) {
						if (class50_sub1_sub4_sub4_1.anIntArray1660 == null) {
							anIntArray1660[anInt1652] = class50_sub1_sub4_sub4_1.anInt1663;
						} else {
							anIntArray1660[anInt1652] = class50_sub1_sub4_sub4_1.anIntArray1660[l1];
						}
					}
					if (flag3) {
						if (class50_sub1_sub4_sub4_1.anIntArray1661 == null) {
							anIntArray1661[anInt1652] = 0;
						} else {
							anIntArray1661[anInt1652] = class50_sub1_sub4_sub4_1.anIntArray1661[l1];
						}
					}
					if (flag4 && class50_sub1_sub4_sub4_1.anIntArray1662 != null) {
						anIntArray1662[anInt1652] = class50_sub1_sub4_sub4_1.anIntArray1662[l1];
					}
					anInt1652++;
				}

				for (int j2 = 0; j2 < class50_sub1_sub4_sub4_1.anInt1664; j2++) {
					anIntArray1665[anInt1664] = class50_sub1_sub4_sub4_1.anIntArray1665[j2] + j1;
					anIntArray1666[anInt1664] = class50_sub1_sub4_sub4_1.anIntArray1666[j2] + j1;
					anIntArray1667[anInt1664] = class50_sub1_sub4_sub4_1.anIntArray1667[j2] + j1;
					anInt1664++;
				}

				l += class50_sub1_sub4_sub4_1.anInt1664;
			}
		}

		method581(anInt1636);
	}

	public Model(boolean flag, boolean flag1, boolean flag2, Model class50_sub1_sub4_sub4, boolean flag3) {
		anInt1636 = 932;
		anInt1637 = 426;
		aBoolean1638 = false;
		aBoolean1639 = true;
		anInt1640 = -252;
		aBoolean1641 = false;
		aBoolean1680 = false;
		Model.anInt1642++;
		anInt1648 = class50_sub1_sub4_sub4.anInt1648;
		anInt1652 = class50_sub1_sub4_sub4.anInt1652;
		anInt1664 = class50_sub1_sub4_sub4.anInt1664;
		if (flag1) {
			anInt1640 = 498;
		}
		if (flag) {
			anIntArray1649 = class50_sub1_sub4_sub4.anIntArray1649;
			anIntArray1650 = class50_sub1_sub4_sub4.anIntArray1650;
			anIntArray1651 = class50_sub1_sub4_sub4.anIntArray1651;
		} else {
			anIntArray1649 = new int[anInt1648];
			anIntArray1650 = new int[anInt1648];
			anIntArray1651 = new int[anInt1648];
			for (int i = 0; i < anInt1648; i++) {
				anIntArray1649[i] = class50_sub1_sub4_sub4.anIntArray1649[i];
				anIntArray1650[i] = class50_sub1_sub4_sub4.anIntArray1650[i];
				anIntArray1651[i] = class50_sub1_sub4_sub4.anIntArray1651[i];
			}

		}
		if (flag2) {
			anIntArray1662 = class50_sub1_sub4_sub4.anIntArray1662;
		} else {
			anIntArray1662 = new int[anInt1652];
			for (int j = 0; j < anInt1652; j++) {
				anIntArray1662[j] = class50_sub1_sub4_sub4.anIntArray1662[j];
			}

		}
		if (flag3) {
			anIntArray1661 = class50_sub1_sub4_sub4.anIntArray1661;
		} else {
			anIntArray1661 = new int[anInt1652];
			if (class50_sub1_sub4_sub4.anIntArray1661 == null) {
				for (int k = 0; k < anInt1652; k++) {
					anIntArray1661[k] = 0;
				}

			} else {
				for (int l = 0; l < anInt1652; l++) {
					anIntArray1661[l] = class50_sub1_sub4_sub4.anIntArray1661[l];
				}

			}
		}
		anIntArray1676 = class50_sub1_sub4_sub4.anIntArray1676;
		anIntArray1677 = class50_sub1_sub4_sub4.anIntArray1677;
		anIntArray1659 = class50_sub1_sub4_sub4.anIntArray1659;
		anIntArray1653 = class50_sub1_sub4_sub4.anIntArray1653;
		anIntArray1654 = class50_sub1_sub4_sub4.anIntArray1654;
		anIntArray1655 = class50_sub1_sub4_sub4.anIntArray1655;
		anIntArray1660 = class50_sub1_sub4_sub4.anIntArray1660;
		anInt1663 = class50_sub1_sub4_sub4.anInt1663;
		anIntArray1665 = class50_sub1_sub4_sub4.anIntArray1665;
		anIntArray1666 = class50_sub1_sub4_sub4.anIntArray1666;
		anIntArray1667 = class50_sub1_sub4_sub4.anIntArray1667;
	}

	public Model(boolean flag, boolean flag1, int i, Model class50_sub1_sub4_sub4) {
		anInt1636 = 932;
		anInt1637 = 426;
		aBoolean1638 = false;
		aBoolean1639 = true;
		anInt1640 = -252;
		aBoolean1641 = false;
		aBoolean1680 = false;
		Model.anInt1642++;
		anInt1648 = class50_sub1_sub4_sub4.anInt1648;
		anInt1652 = class50_sub1_sub4_sub4.anInt1652;
		anInt1664 = class50_sub1_sub4_sub4.anInt1664;
		if (flag) {
			anIntArray1650 = new int[anInt1648];
			for (int j = 0; j < anInt1648; j++) {
				anIntArray1650[j] = class50_sub1_sub4_sub4.anIntArray1650[j];
			}

		} else {
			anIntArray1650 = class50_sub1_sub4_sub4.anIntArray1650;
		}
		if (flag1) {
			anIntArray1656 = new int[anInt1652];
			anIntArray1657 = new int[anInt1652];
			anIntArray1658 = new int[anInt1652];
			for (int k = 0; k < anInt1652; k++) {
				anIntArray1656[k] = class50_sub1_sub4_sub4.anIntArray1656[k];
				anIntArray1657[k] = class50_sub1_sub4_sub4.anIntArray1657[k];
				anIntArray1658[k] = class50_sub1_sub4_sub4.anIntArray1658[k];
			}

			anIntArray1659 = new int[anInt1652];
			if (class50_sub1_sub4_sub4.anIntArray1659 == null) {
				for (int l = 0; l < anInt1652; l++) {
					anIntArray1659[l] = 0;
				}

			} else {
				for (int i1 = 0; i1 < anInt1652; i1++) {
					anIntArray1659[i1] = class50_sub1_sub4_sub4.anIntArray1659[i1];
				}

			}
			super.aClass40Array1474 = new VertexNormal[anInt1648];
			for (int j1 = 0; j1 < anInt1648; j1++) {
				VertexNormal class40 = super.aClass40Array1474[j1] = new VertexNormal();
				VertexNormal class40_1 = ((Renderable) (class50_sub1_sub4_sub4)).aClass40Array1474[j1];
				class40.anInt693 = class40_1.anInt693;
				class40.anInt694 = class40_1.anInt694;
				class40.anInt695 = class40_1.anInt695;
				class40.anInt696 = class40_1.anInt696;
			}

			aClass40Array1681 = class50_sub1_sub4_sub4.aClass40Array1681;
		} else {
			anIntArray1656 = class50_sub1_sub4_sub4.anIntArray1656;
			anIntArray1657 = class50_sub1_sub4_sub4.anIntArray1657;
			anIntArray1658 = class50_sub1_sub4_sub4.anIntArray1658;
			anIntArray1659 = class50_sub1_sub4_sub4.anIntArray1659;
		}
		anIntArray1649 = class50_sub1_sub4_sub4.anIntArray1649;
		anIntArray1651 = class50_sub1_sub4_sub4.anIntArray1651;
		if (i != 0) {
			aBoolean1638 = !aBoolean1638;
		}
		anIntArray1662 = class50_sub1_sub4_sub4.anIntArray1662;
		anIntArray1661 = class50_sub1_sub4_sub4.anIntArray1661;
		anIntArray1660 = class50_sub1_sub4_sub4.anIntArray1660;
		anInt1663 = class50_sub1_sub4_sub4.anInt1663;
		anIntArray1653 = class50_sub1_sub4_sub4.anIntArray1653;
		anIntArray1654 = class50_sub1_sub4_sub4.anIntArray1654;
		anIntArray1655 = class50_sub1_sub4_sub4.anIntArray1655;
		anIntArray1665 = class50_sub1_sub4_sub4.anIntArray1665;
		anIntArray1666 = class50_sub1_sub4_sub4.anIntArray1666;
		anIntArray1667 = class50_sub1_sub4_sub4.anIntArray1667;
		super.anInt1475 = ((Renderable) (class50_sub1_sub4_sub4)).anInt1475;
		anInt1672 = class50_sub1_sub4_sub4.anInt1672;
		anInt1671 = class50_sub1_sub4_sub4.anInt1671;
		anInt1674 = class50_sub1_sub4_sub4.anInt1674;
		anInt1673 = class50_sub1_sub4_sub4.anInt1673;
		anInt1669 = class50_sub1_sub4_sub4.anInt1669;
		anInt1670 = class50_sub1_sub4_sub4.anInt1670;
		anInt1668 = class50_sub1_sub4_sub4.anInt1668;
	}

	public void method579(boolean flag, Model class50_sub1_sub4_sub4, int i) {
		anInt1648 = class50_sub1_sub4_sub4.anInt1648;
		anInt1652 = class50_sub1_sub4_sub4.anInt1652;
		anInt1664 = class50_sub1_sub4_sub4.anInt1664;
		if (Model.anIntArray1644.length < anInt1648) {
			Model.anIntArray1644 = new int[anInt1648 + 100];
			Model.anIntArray1645 = new int[anInt1648 + 100];
			Model.anIntArray1646 = new int[anInt1648 + 100];
		}
		anIntArray1649 = Model.anIntArray1644;
		anIntArray1650 = Model.anIntArray1645;
		if (i != 1244) {
			return;
		}
		anIntArray1651 = Model.anIntArray1646;
		for (int j = 0; j < anInt1648; j++) {
			anIntArray1649[j] = class50_sub1_sub4_sub4.anIntArray1649[j];
			anIntArray1650[j] = class50_sub1_sub4_sub4.anIntArray1650[j];
			anIntArray1651[j] = class50_sub1_sub4_sub4.anIntArray1651[j];
		}

		if (flag) {
			anIntArray1661 = class50_sub1_sub4_sub4.anIntArray1661;
		} else {
			if (Model.anIntArray1647.length < anInt1652) {
				Model.anIntArray1647 = new int[anInt1652 + 100];
			}
			anIntArray1661 = Model.anIntArray1647;
			if (class50_sub1_sub4_sub4.anIntArray1661 == null) {
				for (int k = 0; k < anInt1652; k++) {
					anIntArray1661[k] = 0;
				}

			} else {
				for (int l = 0; l < anInt1652; l++) {
					anIntArray1661[l] = class50_sub1_sub4_sub4.anIntArray1661[l];
				}

			}
		}
		anIntArray1659 = class50_sub1_sub4_sub4.anIntArray1659;
		anIntArray1662 = class50_sub1_sub4_sub4.anIntArray1662;
		anIntArray1660 = class50_sub1_sub4_sub4.anIntArray1660;
		anInt1663 = class50_sub1_sub4_sub4.anInt1663;
		anIntArrayArray1679 = class50_sub1_sub4_sub4.anIntArrayArray1679;
		anIntArrayArray1678 = class50_sub1_sub4_sub4.anIntArrayArray1678;
		anIntArray1653 = class50_sub1_sub4_sub4.anIntArray1653;
		anIntArray1654 = class50_sub1_sub4_sub4.anIntArray1654;
		anIntArray1655 = class50_sub1_sub4_sub4.anIntArray1655;
		anIntArray1656 = class50_sub1_sub4_sub4.anIntArray1656;
		anIntArray1657 = class50_sub1_sub4_sub4.anIntArray1657;
		anIntArray1658 = class50_sub1_sub4_sub4.anIntArray1658;
		anIntArray1665 = class50_sub1_sub4_sub4.anIntArray1665;
		anIntArray1666 = class50_sub1_sub4_sub4.anIntArray1666;
		anIntArray1667 = class50_sub1_sub4_sub4.anIntArray1667;
	}

	public int method580(Model class50_sub1_sub4_sub4, int i) {
		int j = -1;
		int k = class50_sub1_sub4_sub4.anIntArray1649[i];
		int l = class50_sub1_sub4_sub4.anIntArray1650[i];
		int i1 = class50_sub1_sub4_sub4.anIntArray1651[i];
		for (int j1 = 0; j1 < anInt1648; j1++) {
			if (k != anIntArray1649[j1] || l != anIntArray1650[j1] || i1 != anIntArray1651[j1]) {
				continue;
			}
			j = j1;
			break;
		}

		if (j == -1) {
			anIntArray1649[anInt1648] = k;
			anIntArray1650[anInt1648] = l;
			anIntArray1651[anInt1648] = i1;
			if (class50_sub1_sub4_sub4.anIntArray1676 != null) {
				anIntArray1676[anInt1648] = class50_sub1_sub4_sub4.anIntArray1676[i];
			}
			j = anInt1648++;
		}
		return j;
	}

	public void method581(int i) {
		super.anInt1475 = 0;
		anInt1671 = 0;
		anInt1672 = 0;
		for (int j = 0; j < anInt1648; j++) {
			int k = anIntArray1649[j];
			int l = anIntArray1650[j];
			int i1 = anIntArray1651[j];
			if (-l > super.anInt1475) {
				super.anInt1475 = -l;
			}
			if (l > anInt1672) {
				anInt1672 = l;
			}
			int j1 = k * k + i1 * i1;
			if (j1 > anInt1671) {
				anInt1671 = j1;
			}
		}

		anInt1671 = (int) (Math.sqrt(anInt1671) + 0.98999999999999999D);
		anInt1674 = (int) (Math.sqrt(anInt1671 * anInt1671 + super.anInt1475 * super.anInt1475) + 0.98999999999999999D);
		i = 64 / i;
		anInt1673 = anInt1674 + (int) (Math.sqrt(anInt1671 * anInt1671 + anInt1672 * anInt1672) + 0.98999999999999999D);
	}

	public void method582(int i) {
		super.anInt1475 = 0;
		if (i != 6) {
			return;
		}
		anInt1672 = 0;
		for (int j = 0; j < anInt1648; j++) {
			int k = anIntArray1650[j];
			if (-k > super.anInt1475) {
				super.anInt1475 = -k;
			}
			if (k > anInt1672) {
				anInt1672 = k;
			}
		}

		anInt1674 = (int) (Math.sqrt(anInt1671 * anInt1671 + super.anInt1475 * super.anInt1475) + 0.98999999999999999D);
		anInt1673 = anInt1674 + (int) (Math.sqrt(anInt1671 * anInt1671 + anInt1672 * anInt1672) + 0.98999999999999999D);
	}

	public void method583(int i) {
		super.anInt1475 = 0;
		anInt1671 = 0;
		anInt1672 = 0;
		int j = 32767;
		int k = -32767;
		int l = -32767;
		int i1 = 32767;
		for (int j1 = 0; j1 < anInt1648; j1++) {
			int k1 = anIntArray1649[j1];
			int l1 = anIntArray1650[j1];
			int i2 = anIntArray1651[j1];
			if (k1 < j) {
				j = k1;
			}
			if (k1 > k) {
				k = k1;
			}
			if (i2 < i1) {
				i1 = i2;
			}
			if (i2 > l) {
				l = i2;
			}
			if (-l1 > super.anInt1475) {
				super.anInt1475 = -l1;
			}
			if (l1 > anInt1672) {
				anInt1672 = l1;
			}
			int j2 = k1 * k1 + i2 * i2;
			if (j2 > anInt1671) {
				anInt1671 = j2;
			}
		}

		anInt1671 = (int) Math.sqrt(anInt1671);
		anInt1674 = (int) Math.sqrt(anInt1671 * anInt1671 + super.anInt1475 * super.anInt1475);
		anInt1673 = anInt1674 + (int) Math.sqrt(anInt1671 * anInt1671 + anInt1672 * anInt1672);
		anInt1669 = (j << 16) + (k & 0xffff);
		if (i <= 0) {
			anInt1637 = 50;
		}
		anInt1670 = (l << 16) + (i1 & 0xffff);
	}

	public void method584(int i) {
		if (i != 7) {
			return;
		}
		if (anIntArray1676 != null) {
			int ai[] = new int[256];
			int j = 0;
			for (int l = 0; l < anInt1648; l++) {
				int j1 = anIntArray1676[l];
				ai[j1]++;
				if (j1 > j) {
					j = j1;
				}
			}

			anIntArrayArray1678 = new int[j + 1][];
			for (int k1 = 0; k1 <= j; k1++) {
				anIntArrayArray1678[k1] = new int[ai[k1]];
				ai[k1] = 0;
			}

			for (int j2 = 0; j2 < anInt1648; j2++) {
				int l2 = anIntArray1676[j2];
				anIntArrayArray1678[l2][ai[l2]++] = j2;
			}

			anIntArray1676 = null;
		}
		if (anIntArray1677 != null) {
			int ai1[] = new int[256];
			int k = 0;
			for (int i1 = 0; i1 < anInt1652; i1++) {
				int l1 = anIntArray1677[i1];
				ai1[l1]++;
				if (l1 > k) {
					k = l1;
				}
			}

			anIntArrayArray1679 = new int[k + 1][];
			for (int i2 = 0; i2 <= k; i2++) {
				anIntArrayArray1679[i2] = new int[ai1[i2]];
				ai1[i2] = 0;
			}

			for (int k2 = 0; k2 < anInt1652; k2++) {
				int i3 = anIntArray1677[k2];
				anIntArrayArray1679[i3][ai1[i3]++] = k2;
			}

			anIntArray1677 = null;
		}
	}

	public void method585(int i, byte byte0) {
		if (anIntArrayArray1678 == null) {
			return;
		}
		if (i == -1) {
			return;
		}
		Animation class21 = Animation.method238(i);
		if (class21 == null) {
			return;
		}
		Skins class41 = class21.aClass41_432;
		if (byte0 == 6) {
			byte0 = 0;
		} else {
			return;
		}
		Model.anInt1702 = 0;
		Model.anInt1703 = 0;
		Model.anInt1704 = 0;
		for (int j = 0; j < class21.anInt433; j++) {
			int k = class21.anIntArray434[j];
			method587(class41.anIntArray698[k], class41.anIntArrayArray699[k], class21.anIntArray435[j],
					class21.anIntArray436[j], class21.anIntArray437[j]);
		}

	}

	public void method586(int i, int j, int k, int ai[]) {
		if (k == -1) {
			return;
		}
		if (ai == null || i == -1) {
			method585(k, (byte) 6);
			return;
		}
		Animation class21 = Animation.method238(k);
		if (class21 == null) {
			return;
		}
		Animation class21_1 = Animation.method238(i);
		if (class21_1 == null) {
			method585(k, (byte) 6);
			return;
		}
		Skins class41 = class21.aClass41_432;
		Model.anInt1702 = 0;
		if (j != 0) {
			aBoolean1641 = !aBoolean1641;
		}
		Model.anInt1703 = 0;
		Model.anInt1704 = 0;
		int l = 0;
		int i1 = ai[l++];
		for (int j1 = 0; j1 < class21.anInt433; j1++) {
			int k1;
			for (k1 = class21.anIntArray434[j1]; k1 > i1; i1 = ai[l++]) {
				;
			}
			if (k1 != i1 || class41.anIntArray698[k1] == 0) {
				method587(class41.anIntArray698[k1], class41.anIntArrayArray699[k1], class21.anIntArray435[j1],
						class21.anIntArray436[j1], class21.anIntArray437[j1]);
			}
		}

		Model.anInt1702 = 0;
		Model.anInt1703 = 0;
		Model.anInt1704 = 0;
		l = 0;
		i1 = ai[l++];
		for (int l1 = 0; l1 < class21_1.anInt433; l1++) {
			int i2;
			for (i2 = class21_1.anIntArray434[l1]; i2 > i1; i1 = ai[l++]) {
				;
			}
			if (i2 == i1 || class41.anIntArray698[i2] == 0) {
				method587(class41.anIntArray698[i2], class41.anIntArrayArray699[i2], class21_1.anIntArray435[l1],
						class21_1.anIntArray436[l1], class21_1.anIntArray437[l1]);
			}
		}

	}

	public void method587(int i, int ai[], int j, int k, int l) {
		int i1 = ai.length;
		if (i == 0) {
			int j1 = 0;
			Model.anInt1702 = 0;
			Model.anInt1703 = 0;
			Model.anInt1704 = 0;
			for (int k2 = 0; k2 < i1; k2++) {
				int l3 = ai[k2];
				if (l3 < anIntArrayArray1678.length) {
					int ai5[] = anIntArrayArray1678[l3];
					for (int i5 = 0; i5 < ai5.length; i5++) {
						int j6 = ai5[i5];
						Model.anInt1702 += anIntArray1649[j6];
						Model.anInt1703 += anIntArray1650[j6];
						Model.anInt1704 += anIntArray1651[j6];
						j1++;
					}

				}
			}

			if (j1 > 0) {
				Model.anInt1702 = Model.anInt1702 / j1 + j;
				Model.anInt1703 = Model.anInt1703 / j1 + k;
				Model.anInt1704 = Model.anInt1704 / j1 + l;
				return;
			} else {
				Model.anInt1702 = j;
				Model.anInt1703 = k;
				Model.anInt1704 = l;
				return;
			}
		}
		if (i == 1) {
			for (int k1 = 0; k1 < i1; k1++) {
				int l2 = ai[k1];
				if (l2 < anIntArrayArray1678.length) {
					int ai1[] = anIntArrayArray1678[l2];
					for (int i4 = 0; i4 < ai1.length; i4++) {
						int j5 = ai1[i4];
						anIntArray1649[j5] += j;
						anIntArray1650[j5] += k;
						anIntArray1651[j5] += l;
					}

				}
			}

			return;
		}
		if (i == 2) {
			for (int l1 = 0; l1 < i1; l1++) {
				int i3 = ai[l1];
				if (i3 < anIntArrayArray1678.length) {
					int ai2[] = anIntArrayArray1678[i3];
					for (int j4 = 0; j4 < ai2.length; j4++) {
						int k5 = ai2[j4];
						anIntArray1649[k5] -= Model.anInt1702;
						anIntArray1650[k5] -= Model.anInt1703;
						anIntArray1651[k5] -= Model.anInt1704;
						int k6 = (j & 0xff) * 8;
						int l6 = (k & 0xff) * 8;
						int i7 = (l & 0xff) * 8;
						if (i7 != 0) {
							int j7 = Model.anIntArray1710[i7];
							int i8 = Model.anIntArray1711[i7];
							int l8 = anIntArray1650[k5] * j7 + anIntArray1649[k5] * i8 >> 16;
							anIntArray1650[k5] = anIntArray1650[k5] * i8 - anIntArray1649[k5] * j7 >> 16;
							anIntArray1649[k5] = l8;
						}
						if (k6 != 0) {
							int k7 = Model.anIntArray1710[k6];
							int j8 = Model.anIntArray1711[k6];
							int i9 = anIntArray1650[k5] * j8 - anIntArray1651[k5] * k7 >> 16;
							anIntArray1651[k5] = anIntArray1650[k5] * k7 + anIntArray1651[k5] * j8 >> 16;
							anIntArray1650[k5] = i9;
						}
						if (l6 != 0) {
							int l7 = Model.anIntArray1710[l6];
							int k8 = Model.anIntArray1711[l6];
							int j9 = anIntArray1651[k5] * l7 + anIntArray1649[k5] * k8 >> 16;
							anIntArray1651[k5] = anIntArray1651[k5] * k8 - anIntArray1649[k5] * l7 >> 16;
							anIntArray1649[k5] = j9;
						}
						anIntArray1649[k5] += Model.anInt1702;
						anIntArray1650[k5] += Model.anInt1703;
						anIntArray1651[k5] += Model.anInt1704;
					}

				}
			}

			return;
		}
		if (i == 3) {
			for (int i2 = 0; i2 < i1; i2++) {
				int j3 = ai[i2];
				if (j3 < anIntArrayArray1678.length) {
					int ai3[] = anIntArrayArray1678[j3];
					for (int k4 = 0; k4 < ai3.length; k4++) {
						int l5 = ai3[k4];
						anIntArray1649[l5] -= Model.anInt1702;
						anIntArray1650[l5] -= Model.anInt1703;
						anIntArray1651[l5] -= Model.anInt1704;
						anIntArray1649[l5] = (anIntArray1649[l5] * j) / 128;
						anIntArray1650[l5] = (anIntArray1650[l5] * k) / 128;
						anIntArray1651[l5] = (anIntArray1651[l5] * l) / 128;
						anIntArray1649[l5] += Model.anInt1702;
						anIntArray1650[l5] += Model.anInt1703;
						anIntArray1651[l5] += Model.anInt1704;
					}

				}
			}

			return;
		}
		if (i == 5 && anIntArrayArray1679 != null && anIntArray1661 != null) {
			for (int j2 = 0; j2 < i1; j2++) {
				int k3 = ai[j2];
				if (k3 < anIntArrayArray1679.length) {
					int ai4[] = anIntArrayArray1679[k3];
					for (int l4 = 0; l4 < ai4.length; l4++) {
						int i6 = ai4[l4];
						anIntArray1661[i6] += j * 8;
						if (anIntArray1661[i6] < 0) {
							anIntArray1661[i6] = 0;
						}
						if (anIntArray1661[i6] > 255) {
							anIntArray1661[i6] = 255;
						}
					}

				}
			}

		}
	}

	public void method588(boolean flag) {
		if (!flag) {
			return;
		}
		for (int i = 0; i < anInt1648; i++) {
			int j = anIntArray1649[i];
			anIntArray1649[i] = anIntArray1651[i];
			anIntArray1651[i] = -j;
		}

	}

	public void method589(int i, int j) {
		int k = Model.anIntArray1710[i];
		int l = Model.anIntArray1711[i];
		for (int i1 = 0; i1 < anInt1648; i1++) {
			int j1 = anIntArray1650[i1] * l - anIntArray1651[i1] * k >> 16;
			anIntArray1651[i1] = anIntArray1650[i1] * k + anIntArray1651[i1] * l >> 16;
			anIntArray1650[i1] = j1;
		}

		j = 61 / j;
	}

	public void method590(int i, int j, boolean flag, int k) {
		if (flag) {
			anInt1636 = -310;
		}
		for (int l = 0; l < anInt1648; l++) {
			anIntArray1649[l] += i;
			anIntArray1650[l] += k;
			anIntArray1651[l] += j;
		}

	}

	public void method591(int i, int j) {
		for (int k = 0; k < anInt1652; k++) {
			if (anIntArray1662[k] == i) {
				anIntArray1662[k] = j;
			}
		}

	}

	public void method592(int i) {
		if (i != 0) {
			for (int j = 1; j > 0; j++) {
				;
			}
		}
		for (int k = 0; k < anInt1648; k++) {
			anIntArray1651[k] = -anIntArray1651[k];
		}

		for (int l = 0; l < anInt1652; l++) {
			int i1 = anIntArray1653[l];
			anIntArray1653[l] = anIntArray1655[l];
			anIntArray1655[l] = i1;
		}

	}

	public void method593(int i, int j, int k, int l) {
		for (int i1 = 0; i1 < anInt1648; i1++) {
			anIntArray1649[i1] = (anIntArray1649[i1] * l) / 128;
			anIntArray1650[i1] = (anIntArray1650[i1] * i) / 128;
			anIntArray1651[i1] = (anIntArray1651[i1] * j) / 128;
		}

		if (k != 9) {
			anInt1636 = 322;
		}
	}

	public void method594(int i, int j, int k, int l, int i1, boolean flag) {
		int j1 = (int) Math.sqrt(k * k + l * l + i1 * i1);
		int k1 = j * j1 >> 8;
		if (anIntArray1656 == null) {
			anIntArray1656 = new int[anInt1652];
			anIntArray1657 = new int[anInt1652];
			anIntArray1658 = new int[anInt1652];
		}
		if (super.aClass40Array1474 == null) {
			super.aClass40Array1474 = new VertexNormal[anInt1648];
			for (int l1 = 0; l1 < anInt1648; l1++) {
				super.aClass40Array1474[l1] = new VertexNormal();
			}

		}
		for (int i2 = 0; i2 < anInt1652; i2++) {
			int j2 = anIntArray1653[i2];
			int l2 = anIntArray1654[i2];
			int i3 = anIntArray1655[i2];
			int j3 = anIntArray1649[l2] - anIntArray1649[j2];
			int k3 = anIntArray1650[l2] - anIntArray1650[j2];
			int l3 = anIntArray1651[l2] - anIntArray1651[j2];
			int i4 = anIntArray1649[i3] - anIntArray1649[j2];
			int j4 = anIntArray1650[i3] - anIntArray1650[j2];
			int k4 = anIntArray1651[i3] - anIntArray1651[j2];
			int l4 = k3 * k4 - j4 * l3;
			int i5 = l3 * i4 - k4 * j3;
			int j5;
			for (j5 = j3 * j4 - i4 * k3; l4 > 8192 || i5 > 8192 || j5 > 8192 || l4 < -8192 || i5 < -8192 || j5 < -8192; j5 >>= 1) {
				l4 >>= 1;
				i5 >>= 1;
			}

			int k5 = (int) Math.sqrt(l4 * l4 + i5 * i5 + j5 * j5);
			if (k5 <= 0) {
				k5 = 1;
			}
			l4 = (l4 * 256) / k5;
			i5 = (i5 * 256) / k5;
			j5 = (j5 * 256) / k5;
			if (anIntArray1659 == null || (anIntArray1659[i2] & 1) == 0) {
				VertexNormal class40_2 = super.aClass40Array1474[j2];
				class40_2.anInt693 += l4;
				class40_2.anInt694 += i5;
				class40_2.anInt695 += j5;
				class40_2.anInt696++;
				class40_2 = super.aClass40Array1474[l2];
				class40_2.anInt693 += l4;
				class40_2.anInt694 += i5;
				class40_2.anInt695 += j5;
				class40_2.anInt696++;
				class40_2 = super.aClass40Array1474[i3];
				class40_2.anInt693 += l4;
				class40_2.anInt694 += i5;
				class40_2.anInt695 += j5;
				class40_2.anInt696++;
			} else {
				int l5 = i + (k * l4 + l * i5 + i1 * j5) / (k1 + k1 / 2);
				anIntArray1656[i2] = Model.method597(anIntArray1662[i2], l5, anIntArray1659[i2]);
			}
		}

		if (flag) {
			method596(i, k1, k, l, i1);
		} else {
			aClass40Array1681 = new VertexNormal[anInt1648];
			for (int k2 = 0; k2 < anInt1648; k2++) {
				VertexNormal class40 = super.aClass40Array1474[k2];
				VertexNormal class40_1 = aClass40Array1681[k2] = new VertexNormal();
				class40_1.anInt693 = class40.anInt693;
				class40_1.anInt694 = class40.anInt694;
				class40_1.anInt695 = class40.anInt695;
				class40_1.anInt696 = class40.anInt696;
			}

			anInt1668 = (i << 16) + (k1 & 0xffff);
		}
		if (flag) {
			method581(anInt1636);
			return;
		} else {
			method583(426);
			return;
		}
	}

	public void method595(int i, int j, int k, int l) {
		int i1 = anInt1668 >> 16;
		int j1 = (anInt1668 << 16) >> 16;
		if (k != 0) {
			for (int k1 = 1; k1 > 0; k1++) {
				;
			}
		}
		method596(i1, j1, l, i, j);
	}

	public void method596(int i, int j, int k, int l, int i1) {
		for (int j1 = 0; j1 < anInt1652; j1++) {
			int k1 = anIntArray1653[j1];
			int i2 = anIntArray1654[j1];
			int j2 = anIntArray1655[j1];
			if (anIntArray1659 == null) {
				int i3 = anIntArray1662[j1];
				VertexNormal class40 = super.aClass40Array1474[k1];
				int k2 = i + (k * class40.anInt693 + l * class40.anInt694 + i1 * class40.anInt695)
						/ (j * class40.anInt696);
				anIntArray1656[j1] = Model.method597(i3, k2, 0);
				class40 = super.aClass40Array1474[i2];
				k2 = i + (k * class40.anInt693 + l * class40.anInt694 + i1 * class40.anInt695) / (j * class40.anInt696);
				anIntArray1657[j1] = Model.method597(i3, k2, 0);
				class40 = super.aClass40Array1474[j2];
				k2 = i + (k * class40.anInt693 + l * class40.anInt694 + i1 * class40.anInt695) / (j * class40.anInt696);
				anIntArray1658[j1] = Model.method597(i3, k2, 0);
			} else if ((anIntArray1659[j1] & 1) == 0) {
				int j3 = anIntArray1662[j1];
				int k3 = anIntArray1659[j1];
				VertexNormal class40_1 = super.aClass40Array1474[k1];
				int l2 = i + (k * class40_1.anInt693 + l * class40_1.anInt694 + i1 * class40_1.anInt695)
						/ (j * class40_1.anInt696);
				anIntArray1656[j1] = Model.method597(j3, l2, k3);
				class40_1 = super.aClass40Array1474[i2];
				l2 = i + (k * class40_1.anInt693 + l * class40_1.anInt694 + i1 * class40_1.anInt695)
						/ (j * class40_1.anInt696);
				anIntArray1657[j1] = Model.method597(j3, l2, k3);
				class40_1 = super.aClass40Array1474[j2];
				l2 = i + (k * class40_1.anInt693 + l * class40_1.anInt694 + i1 * class40_1.anInt695)
						/ (j * class40_1.anInt696);
				anIntArray1658[j1] = Model.method597(j3, l2, k3);
			}
		}

		super.aClass40Array1474 = null;
		aClass40Array1681 = null;
		anIntArray1676 = null;
		anIntArray1677 = null;
		if (anIntArray1659 != null) {
			for (int l1 = 0; l1 < anInt1652; l1++) {
				if ((anIntArray1659[l1] & 2) == 2) {
					return;
				}
			}

		}
		anIntArray1662 = null;
	}

	public static int method597(int i, int j, int k) {
		if ((k & 2) == 2) {
			if (j < 0) {
				j = 0;
			} else if (j > 127) {
				j = 127;
			}
			j = 127 - j;
			return j;
		}
		j = j * (i & 0x7f) >> 7;
		if (j < 2) {
			j = 2;
		} else if (j > 126) {
			j = 126;
		}
		return (i & 0xff80) + j;
	}

	public void method598(int i, int j, int k, int l, int i1, int j1, int k1) {
		int l1 = Rasterizer3D.anInt1532;
		int i2 = Rasterizer3D.anInt1533;
		int j2 = Model.anIntArray1710[i];
		int k2 = Model.anIntArray1711[i];
		int l2 = Model.anIntArray1710[j];
		int i3 = Model.anIntArray1711[j];
		int j3 = Model.anIntArray1710[k];
		int k3 = Model.anIntArray1711[k];
		int l3 = Model.anIntArray1710[l];
		int i4 = Model.anIntArray1711[l];
		int j4 = j1 * l3 + k1 * i4 >> 16;
		for (int k4 = 0; k4 < anInt1648; k4++) {
			int l4 = anIntArray1649[k4];
			int i5 = anIntArray1650[k4];
			int j5 = anIntArray1651[k4];
			if (k != 0) {
				int k5 = i5 * j3 + l4 * k3 >> 16;
				i5 = i5 * k3 - l4 * j3 >> 16;
				l4 = k5;
			}
			if (i != 0) {
				int l5 = i5 * k2 - j5 * j2 >> 16;
				j5 = i5 * j2 + j5 * k2 >> 16;
				i5 = l5;
			}
			if (j != 0) {
				int i6 = j5 * l2 + l4 * i3 >> 16;
				j5 = j5 * i3 - l4 * l2 >> 16;
				l4 = i6;
			}
			l4 += i1;
			i5 += j1;
			j5 += k1;
			int j6 = i5 * i4 - j5 * l3 >> 16;
			j5 = i5 * l3 + j5 * i4 >> 16;
			i5 = j6;
			Model.anIntArray1688[k4] = j5 - j4;
			Model.anIntArray1686[k4] = l1 + (l4 << 9) / j5;
			Model.anIntArray1687[k4] = i2 + (i5 << 9) / j5;
			if (anInt1664 > 0) {
				Model.anIntArray1689[k4] = l4;
				Model.anIntArray1690[k4] = i5;
				Model.anIntArray1691[k4] = j5;
			}
		}

		try {
			method599(false, false, 0);
			return;
		} catch (Exception _ex) {
			return;
		}
	}

	@Override
	public void method560(int i, int j, int k, int l, int i1, int j1, int k1, int l1, int i2) {
		int j2 = l1 * i1 - j1 * l >> 16;
		int k2 = k1 * j + j2 * k >> 16;
		int l2 = anInt1671 * k >> 16;
		int i3 = k2 + l2;
		if (i3 <= 50 || k2 >= 3500) {
			return;
		}
		int j3 = l1 * l + j1 * i1 >> 16;
		int k3 = j3 - anInt1671 << 9;
		if (k3 / i3 >= Rasterizer.anInt1432) {
			return;
		}
		int l3 = j3 + anInt1671 << 9;
		if (l3 / i3 <= -Rasterizer.anInt1432) {
			return;
		}
		int i4 = k1 * k - j2 * j >> 16;
		int j4 = anInt1671 * j >> 16;
		int k4 = i4 + j4 << 9;
		if (k4 / i3 <= -Rasterizer.anInt1433) {
			return;
		}
		int l4 = j4 + (super.anInt1475 * k >> 16);
		int i5 = i4 - l4 << 9;
		if (i5 / i3 >= Rasterizer.anInt1433) {
			return;
		}
		int j5 = l2 + (super.anInt1475 * j >> 16);
		boolean flag = false;
		if (k2 - j5 <= 50) {
			flag = true;
		}
		boolean flag1 = false;
		if (i2 > 0 && Model.aBoolean1705) {
			int k5 = k2 - l2;
			if (k5 <= 50) {
				k5 = 50;
			}
			if (j3 > 0) {
				k3 /= i3;
				l3 /= k5;
			} else {
				l3 /= i3;
				k3 /= k5;
			}
			if (i4 > 0) {
				i5 /= i3;
				k4 /= k5;
			} else {
				k4 /= i3;
				i5 /= k5;
			}
			int i6 = Model.anInt1706 - Rasterizer3D.anInt1532;
			int k6 = Model.anInt1707 - Rasterizer3D.anInt1533;
			if (i6 > k3 && i6 < l3 && k6 > i5 && k6 < k4) {
				if (aBoolean1680) {
					Model.anIntArray1709[Model.anInt1708++] = i2;
				} else {
					flag1 = true;
				}
			}
		}
		int l5 = Rasterizer3D.anInt1532;
		int j6 = Rasterizer3D.anInt1533;
		int l6 = 0;
		int i7 = 0;
		if (i != 0) {
			l6 = Model.anIntArray1710[i];
			i7 = Model.anIntArray1711[i];
		}
		for (int j7 = 0; j7 < anInt1648; j7++) {
			int k7 = anIntArray1649[j7];
			int l7 = anIntArray1650[j7];
			int i8 = anIntArray1651[j7];
			if (i != 0) {
				int j8 = i8 * l6 + k7 * i7 >> 16;
				i8 = i8 * i7 - k7 * l6 >> 16;
				k7 = j8;
			}
			k7 += j1;
			l7 += k1;
			i8 += l1;
			int k8 = i8 * l + k7 * i1 >> 16;
			i8 = i8 * i1 - k7 * l >> 16;
			k7 = k8;
			k8 = l7 * k - i8 * j >> 16;
			i8 = l7 * j + i8 * k >> 16;
			l7 = k8;
			Model.anIntArray1688[j7] = i8 - k2;
			if (i8 >= 50) {
				Model.anIntArray1686[j7] = l5 + (k7 << 9) / i8;
				Model.anIntArray1687[j7] = j6 + (l7 << 9) / i8;
			} else {
				Model.anIntArray1686[j7] = -5000;
				flag = true;
			}
			if (flag || anInt1664 > 0) {
				Model.anIntArray1689[j7] = k7;
				Model.anIntArray1690[j7] = l7;
				Model.anIntArray1691[j7] = i8;
			}
		}

		try {
			method599(flag, flag1, i2);
			return;
		} catch (Exception _ex) {
			return;
		}
	}

	public void method599(boolean flag, boolean flag1, int i) {
		for (int j = 0; j < anInt1673; j++) {
			Model.anIntArray1692[j] = 0;
		}

		for (int k = 0; k < anInt1652; k++) {
			if (anIntArray1659 == null || anIntArray1659[k] != -1) {
				int l = anIntArray1653[k];
				int k1 = anIntArray1654[k];
				int j2 = anIntArray1655[k];
				int i3 = Model.anIntArray1686[l];
				int l3 = Model.anIntArray1686[k1];
				int k4 = Model.anIntArray1686[j2];
				if (flag && (i3 == -5000 || l3 == -5000 || k4 == -5000)) {
					Model.aBooleanArray1685[k] = true;
					int j5 = (Model.anIntArray1688[l] + Model.anIntArray1688[k1] + Model.anIntArray1688[j2]) / 3
							+ anInt1674;
					Model.anIntArrayArray1693[j5][Model.anIntArray1692[j5]++] = k;
				} else {
					if (flag1
							&& method602(Model.anInt1706, Model.anInt1707, Model.anIntArray1687[l],
									Model.anIntArray1687[k1], Model.anIntArray1687[j2], i3, l3, k4)) {
						Model.anIntArray1709[Model.anInt1708++] = i;
						flag1 = false;
					}
					if ((i3 - l3) * (Model.anIntArray1687[j2] - Model.anIntArray1687[k1])
							- (Model.anIntArray1687[l] - Model.anIntArray1687[k1]) * (k4 - l3) > 0) {
						Model.aBooleanArray1685[k] = false;
						if (i3 < 0 || l3 < 0 || k4 < 0 || i3 > Rasterizer.anInt1431 || l3 > Rasterizer.anInt1431
								|| k4 > Rasterizer.anInt1431) {
							Model.aBooleanArray1684[k] = true;
						} else {
							Model.aBooleanArray1684[k] = false;
						}
						int k5 = (Model.anIntArray1688[l] + Model.anIntArray1688[k1] + Model.anIntArray1688[j2]) / 3
								+ anInt1674;
						Model.anIntArrayArray1693[k5][Model.anIntArray1692[k5]++] = k;
					}
				}
			}
		}

		if (anIntArray1660 == null) {
			for (int i1 = anInt1673 - 1; i1 >= 0; i1--) {
				int l1 = Model.anIntArray1692[i1];
				if (l1 > 0) {
					int ai[] = Model.anIntArrayArray1693[i1];
					for (int j3 = 0; j3 < l1; j3++) {
						method600(ai[j3]);
					}

				}
			}

			return;
		}
		for (int j1 = 0; j1 < 12; j1++) {
			Model.anIntArray1694[j1] = 0;
			Model.anIntArray1698[j1] = 0;
		}

		for (int i2 = anInt1673 - 1; i2 >= 0; i2--) {
			int k2 = Model.anIntArray1692[i2];
			if (k2 > 0) {
				int ai1[] = Model.anIntArrayArray1693[i2];
				for (int i4 = 0; i4 < k2; i4++) {
					int l4 = ai1[i4];
					int l5 = anIntArray1660[l4];
					int j6 = Model.anIntArray1694[l5]++;
					Model.anIntArrayArray1695[l5][j6] = l4;
					if (l5 < 10) {
						Model.anIntArray1698[l5] += i2;
					} else if (l5 == 10) {
						Model.anIntArray1696[j6] = i2;
					} else {
						Model.anIntArray1697[j6] = i2;
					}
				}

			}
		}

		int l2 = 0;
		if (Model.anIntArray1694[1] > 0 || Model.anIntArray1694[2] > 0) {
			l2 = (Model.anIntArray1698[1] + Model.anIntArray1698[2])
					/ (Model.anIntArray1694[1] + Model.anIntArray1694[2]);
		}
		int k3 = 0;
		if (Model.anIntArray1694[3] > 0 || Model.anIntArray1694[4] > 0) {
			k3 = (Model.anIntArray1698[3] + Model.anIntArray1698[4])
					/ (Model.anIntArray1694[3] + Model.anIntArray1694[4]);
		}
		int j4 = 0;
		if (Model.anIntArray1694[6] > 0 || Model.anIntArray1694[8] > 0) {
			j4 = (Model.anIntArray1698[6] + Model.anIntArray1698[8])
					/ (Model.anIntArray1694[6] + Model.anIntArray1694[8]);
		}
		int i6 = 0;
		int k6 = Model.anIntArray1694[10];
		int ai2[] = Model.anIntArrayArray1695[10];
		int ai3[] = Model.anIntArray1696;
		if (i6 == k6) {
			i6 = 0;
			k6 = Model.anIntArray1694[11];
			ai2 = Model.anIntArrayArray1695[11];
			ai3 = Model.anIntArray1697;
		}
		int i5;
		if (i6 < k6) {
			i5 = ai3[i6];
		} else {
			i5 = -1000;
		}
		for (int l6 = 0; l6 < 10; l6++) {
			while (l6 == 0 && i5 > l2) {
				method600(ai2[i6++]);
				if (i6 == k6 && ai2 != Model.anIntArrayArray1695[11]) {
					i6 = 0;
					k6 = Model.anIntArray1694[11];
					ai2 = Model.anIntArrayArray1695[11];
					ai3 = Model.anIntArray1697;
				}
				if (i6 < k6) {
					i5 = ai3[i6];
				} else {
					i5 = -1000;
				}
			}
			while (l6 == 3 && i5 > k3) {
				method600(ai2[i6++]);
				if (i6 == k6 && ai2 != Model.anIntArrayArray1695[11]) {
					i6 = 0;
					k6 = Model.anIntArray1694[11];
					ai2 = Model.anIntArrayArray1695[11];
					ai3 = Model.anIntArray1697;
				}
				if (i6 < k6) {
					i5 = ai3[i6];
				} else {
					i5 = -1000;
				}
			}
			while (l6 == 5 && i5 > j4) {
				method600(ai2[i6++]);
				if (i6 == k6 && ai2 != Model.anIntArrayArray1695[11]) {
					i6 = 0;
					k6 = Model.anIntArray1694[11];
					ai2 = Model.anIntArrayArray1695[11];
					ai3 = Model.anIntArray1697;
				}
				if (i6 < k6) {
					i5 = ai3[i6];
				} else {
					i5 = -1000;
				}
			}
			int i7 = Model.anIntArray1694[l6];
			int ai4[] = Model.anIntArrayArray1695[l6];
			for (int j7 = 0; j7 < i7; j7++) {
				method600(ai4[j7]);
			}

		}

		while (i5 != -1000) {
			method600(ai2[i6++]);
			if (i6 == k6 && ai2 != Model.anIntArrayArray1695[11]) {
				i6 = 0;
				ai2 = Model.anIntArrayArray1695[11];
				k6 = Model.anIntArray1694[11];
				ai3 = Model.anIntArray1697;
			}
			if (i6 < k6) {
				i5 = ai3[i6];
			} else {
				i5 = -1000;
			}
		}
	}

	public void method600(int i) {
		if (Model.aBooleanArray1685[i]) {
			method601(i);
			return;
		}
		int j = anIntArray1653[i];
		int k = anIntArray1654[i];
		int l = anIntArray1655[i];
		Rasterizer3D.aBoolean1528 = Model.aBooleanArray1684[i];
		if (anIntArray1661 == null) {
			Rasterizer3D.anInt1531 = 0;
		} else {
			Rasterizer3D.anInt1531 = anIntArray1661[i];
		}
		int i1;
		if (anIntArray1659 == null) {
			i1 = 0;
		} else {
			i1 = anIntArray1659[i] & 3;
		}
		if (i1 == 0) {
			Rasterizer3D.method503(Model.anIntArray1687[j], Model.anIntArray1687[k], Model.anIntArray1687[l],
					Model.anIntArray1686[j], Model.anIntArray1686[k], Model.anIntArray1686[l], anIntArray1656[i],
					anIntArray1657[i], anIntArray1658[i]);
			return;
		}
		if (i1 == 1) {
			Rasterizer3D.method505(Model.anIntArray1687[j], Model.anIntArray1687[k], Model.anIntArray1687[l],
					Model.anIntArray1686[j], Model.anIntArray1686[k], Model.anIntArray1686[l],
					Model.anIntArray1712[anIntArray1656[i]]);
			return;
		}
		if (i1 == 2) {
			int j1 = anIntArray1659[i] >> 2;
			int l1 = anIntArray1665[j1];
			int j2 = anIntArray1666[j1];
			int l2 = anIntArray1667[j1];
			Rasterizer3D.method507(Model.anIntArray1687[j], Model.anIntArray1687[k], Model.anIntArray1687[l],
					Model.anIntArray1686[j], Model.anIntArray1686[k], Model.anIntArray1686[l], anIntArray1656[i],
					anIntArray1657[i], anIntArray1658[i], Model.anIntArray1689[l1], Model.anIntArray1689[j2],
					Model.anIntArray1689[l2], Model.anIntArray1690[l1], Model.anIntArray1690[j2],
					Model.anIntArray1690[l2], Model.anIntArray1691[l1], Model.anIntArray1691[j2],
					Model.anIntArray1691[l2], anIntArray1662[i]);
			return;
		}
		if (i1 == 3) {
			int k1 = anIntArray1659[i] >> 2;
			int i2 = anIntArray1665[k1];
			int k2 = anIntArray1666[k1];
			int i3 = anIntArray1667[k1];
			Rasterizer3D.method507(Model.anIntArray1687[j], Model.anIntArray1687[k], Model.anIntArray1687[l],
					Model.anIntArray1686[j], Model.anIntArray1686[k], Model.anIntArray1686[l], anIntArray1656[i],
					anIntArray1656[i], anIntArray1656[i], Model.anIntArray1689[i2], Model.anIntArray1689[k2],
					Model.anIntArray1689[i3], Model.anIntArray1690[i2], Model.anIntArray1690[k2],
					Model.anIntArray1690[i3], Model.anIntArray1691[i2], Model.anIntArray1691[k2],
					Model.anIntArray1691[i3], anIntArray1662[i]);
		}
	}

	public void method601(int i) {
		int j = Rasterizer3D.anInt1532;
		int k = Rasterizer3D.anInt1533;
		int l = 0;
		int i1 = anIntArray1653[i];
		int j1 = anIntArray1654[i];
		int k1 = anIntArray1655[i];
		int l1 = Model.anIntArray1691[i1];
		int i2 = Model.anIntArray1691[j1];
		int j2 = Model.anIntArray1691[k1];
		if (l1 >= 50) {
			Model.anIntArray1699[l] = Model.anIntArray1686[i1];
			Model.anIntArray1700[l] = Model.anIntArray1687[i1];
			Model.anIntArray1701[l++] = anIntArray1656[i];
		} else {
			int k2 = Model.anIntArray1689[i1];
			int k3 = Model.anIntArray1690[i1];
			int k4 = anIntArray1656[i];
			if (j2 >= 50) {
				int k5 = (50 - l1) * Model.anIntArray1713[j2 - l1];
				Model.anIntArray1699[l] = j + (k2 + ((Model.anIntArray1689[k1] - k2) * k5 >> 16) << 9) / 50;
				Model.anIntArray1700[l] = k + (k3 + ((Model.anIntArray1690[k1] - k3) * k5 >> 16) << 9) / 50;
				Model.anIntArray1701[l++] = k4 + ((anIntArray1658[i] - k4) * k5 >> 16);
			}
			if (i2 >= 50) {
				int l5 = (50 - l1) * Model.anIntArray1713[i2 - l1];
				Model.anIntArray1699[l] = j + (k2 + ((Model.anIntArray1689[j1] - k2) * l5 >> 16) << 9) / 50;
				Model.anIntArray1700[l] = k + (k3 + ((Model.anIntArray1690[j1] - k3) * l5 >> 16) << 9) / 50;
				Model.anIntArray1701[l++] = k4 + ((anIntArray1657[i] - k4) * l5 >> 16);
			}
		}
		if (i2 >= 50) {
			Model.anIntArray1699[l] = Model.anIntArray1686[j1];
			Model.anIntArray1700[l] = Model.anIntArray1687[j1];
			Model.anIntArray1701[l++] = anIntArray1657[i];
		} else {
			int l2 = Model.anIntArray1689[j1];
			int l3 = Model.anIntArray1690[j1];
			int l4 = anIntArray1657[i];
			if (l1 >= 50) {
				int i6 = (50 - i2) * Model.anIntArray1713[l1 - i2];
				Model.anIntArray1699[l] = j + (l2 + ((Model.anIntArray1689[i1] - l2) * i6 >> 16) << 9) / 50;
				Model.anIntArray1700[l] = k + (l3 + ((Model.anIntArray1690[i1] - l3) * i6 >> 16) << 9) / 50;
				Model.anIntArray1701[l++] = l4 + ((anIntArray1656[i] - l4) * i6 >> 16);
			}
			if (j2 >= 50) {
				int j6 = (50 - i2) * Model.anIntArray1713[j2 - i2];
				Model.anIntArray1699[l] = j + (l2 + ((Model.anIntArray1689[k1] - l2) * j6 >> 16) << 9) / 50;
				Model.anIntArray1700[l] = k + (l3 + ((Model.anIntArray1690[k1] - l3) * j6 >> 16) << 9) / 50;
				Model.anIntArray1701[l++] = l4 + ((anIntArray1658[i] - l4) * j6 >> 16);
			}
		}
		if (j2 >= 50) {
			Model.anIntArray1699[l] = Model.anIntArray1686[k1];
			Model.anIntArray1700[l] = Model.anIntArray1687[k1];
			Model.anIntArray1701[l++] = anIntArray1658[i];
		} else {
			int i3 = Model.anIntArray1689[k1];
			int i4 = Model.anIntArray1690[k1];
			int i5 = anIntArray1658[i];
			if (i2 >= 50) {
				int k6 = (50 - j2) * Model.anIntArray1713[i2 - j2];
				Model.anIntArray1699[l] = j + (i3 + ((Model.anIntArray1689[j1] - i3) * k6 >> 16) << 9) / 50;
				Model.anIntArray1700[l] = k + (i4 + ((Model.anIntArray1690[j1] - i4) * k6 >> 16) << 9) / 50;
				Model.anIntArray1701[l++] = i5 + ((anIntArray1657[i] - i5) * k6 >> 16);
			}
			if (l1 >= 50) {
				int l6 = (50 - j2) * Model.anIntArray1713[l1 - j2];
				Model.anIntArray1699[l] = j + (i3 + ((Model.anIntArray1689[i1] - i3) * l6 >> 16) << 9) / 50;
				Model.anIntArray1700[l] = k + (i4 + ((Model.anIntArray1690[i1] - i4) * l6 >> 16) << 9) / 50;
				Model.anIntArray1701[l++] = i5 + ((anIntArray1656[i] - i5) * l6 >> 16);
			}
		}
		int j3 = Model.anIntArray1699[0];
		int j4 = Model.anIntArray1699[1];
		int j5 = Model.anIntArray1699[2];
		int i7 = Model.anIntArray1700[0];
		int j7 = Model.anIntArray1700[1];
		int k7 = Model.anIntArray1700[2];
		if ((j3 - j4) * (k7 - j7) - (i7 - j7) * (j5 - j4) > 0) {
			Rasterizer3D.aBoolean1528 = false;
			if (l == 3) {
				if (j3 < 0 || j4 < 0 || j5 < 0 || j3 > Rasterizer.anInt1431 || j4 > Rasterizer.anInt1431
						|| j5 > Rasterizer.anInt1431) {
					Rasterizer3D.aBoolean1528 = true;
				}
				int l7;
				if (anIntArray1659 == null) {
					l7 = 0;
				} else {
					l7 = anIntArray1659[i] & 3;
				}
				if (l7 == 0) {
					Rasterizer3D.method503(i7, j7, k7, j3, j4, j5, Model.anIntArray1701[0], Model.anIntArray1701[1],
							Model.anIntArray1701[2]);
				} else if (l7 == 1) {
					Rasterizer3D.method505(i7, j7, k7, j3, j4, j5, Model.anIntArray1712[anIntArray1656[i]]);
				} else if (l7 == 2) {
					int j8 = anIntArray1659[i] >> 2;
					int k9 = anIntArray1665[j8];
					int k10 = anIntArray1666[j8];
					int k11 = anIntArray1667[j8];
					Rasterizer3D.method507(i7, j7, k7, j3, j4, j5, Model.anIntArray1701[0], Model.anIntArray1701[1],
							Model.anIntArray1701[2], Model.anIntArray1689[k9], Model.anIntArray1689[k10],
							Model.anIntArray1689[k11], Model.anIntArray1690[k9], Model.anIntArray1690[k10],
							Model.anIntArray1690[k11], Model.anIntArray1691[k9], Model.anIntArray1691[k10],
							Model.anIntArray1691[k11], anIntArray1662[i]);
				} else if (l7 == 3) {
					int k8 = anIntArray1659[i] >> 2;
					int l9 = anIntArray1665[k8];
					int l10 = anIntArray1666[k8];
					int l11 = anIntArray1667[k8];
					Rasterizer3D.method507(i7, j7, k7, j3, j4, j5, anIntArray1656[i], anIntArray1656[i],
							anIntArray1656[i], Model.anIntArray1689[l9], Model.anIntArray1689[l10],
							Model.anIntArray1689[l11], Model.anIntArray1690[l9], Model.anIntArray1690[l10],
							Model.anIntArray1690[l11], Model.anIntArray1691[l9], Model.anIntArray1691[l10],
							Model.anIntArray1691[l11], anIntArray1662[i]);
				}
			}
			if (l == 4) {
				if (j3 < 0 || j4 < 0 || j5 < 0 || j3 > Rasterizer.anInt1431 || j4 > Rasterizer.anInt1431
						|| j5 > Rasterizer.anInt1431 || Model.anIntArray1699[3] < 0
						|| Model.anIntArray1699[3] > Rasterizer.anInt1431) {
					Rasterizer3D.aBoolean1528 = true;
				}
				int i8;
				if (anIntArray1659 == null) {
					i8 = 0;
				} else {
					i8 = anIntArray1659[i] & 3;
				}
				if (i8 == 0) {
					Rasterizer3D.method503(i7, j7, k7, j3, j4, j5, Model.anIntArray1701[0], Model.anIntArray1701[1],
							Model.anIntArray1701[2]);
					Rasterizer3D.method503(i7, k7, Model.anIntArray1700[3], j3, j5, Model.anIntArray1699[3],
							Model.anIntArray1701[0], Model.anIntArray1701[2], Model.anIntArray1701[3]);
					return;
				}
				if (i8 == 1) {
					int l8 = Model.anIntArray1712[anIntArray1656[i]];
					Rasterizer3D.method505(i7, j7, k7, j3, j4, j5, l8);
					Rasterizer3D.method505(i7, k7, Model.anIntArray1700[3], j3, j5, Model.anIntArray1699[3], l8);
					return;
				}
				if (i8 == 2) {
					int i9 = anIntArray1659[i] >> 2;
					int i10 = anIntArray1665[i9];
					int i11 = anIntArray1666[i9];
					int i12 = anIntArray1667[i9];
					Rasterizer3D.method507(i7, j7, k7, j3, j4, j5, Model.anIntArray1701[0], Model.anIntArray1701[1],
							Model.anIntArray1701[2], Model.anIntArray1689[i10], Model.anIntArray1689[i11],
							Model.anIntArray1689[i12], Model.anIntArray1690[i10], Model.anIntArray1690[i11],
							Model.anIntArray1690[i12], Model.anIntArray1691[i10], Model.anIntArray1691[i11],
							Model.anIntArray1691[i12], anIntArray1662[i]);
					Rasterizer3D.method507(i7, k7, Model.anIntArray1700[3], j3, j5, Model.anIntArray1699[3],
							Model.anIntArray1701[0], Model.anIntArray1701[2], Model.anIntArray1701[3],
							Model.anIntArray1689[i10], Model.anIntArray1689[i11], Model.anIntArray1689[i12],
							Model.anIntArray1690[i10], Model.anIntArray1690[i11], Model.anIntArray1690[i12],
							Model.anIntArray1691[i10], Model.anIntArray1691[i11], Model.anIntArray1691[i12],
							anIntArray1662[i]);
					return;
				}
				if (i8 == 3) {
					int j9 = anIntArray1659[i] >> 2;
					int j10 = anIntArray1665[j9];
					int j11 = anIntArray1666[j9];
					int j12 = anIntArray1667[j9];
					Rasterizer3D.method507(i7, j7, k7, j3, j4, j5, anIntArray1656[i], anIntArray1656[i],
							anIntArray1656[i], Model.anIntArray1689[j10], Model.anIntArray1689[j11],
							Model.anIntArray1689[j12], Model.anIntArray1690[j10], Model.anIntArray1690[j11],
							Model.anIntArray1690[j12], Model.anIntArray1691[j10], Model.anIntArray1691[j11],
							Model.anIntArray1691[j12], anIntArray1662[i]);
					Rasterizer3D.method507(i7, k7, Model.anIntArray1700[3], j3, j5, Model.anIntArray1699[3],
							anIntArray1656[i], anIntArray1656[i], anIntArray1656[i], Model.anIntArray1689[j10],
							Model.anIntArray1689[j11], Model.anIntArray1689[j12], Model.anIntArray1690[j10],
							Model.anIntArray1690[j11], Model.anIntArray1690[j12], Model.anIntArray1691[j10],
							Model.anIntArray1691[j11], Model.anIntArray1691[j12], anIntArray1662[i]);
				}
			}
		}
	}

	public boolean method602(int i, int j, int k, int l, int i1, int j1, int k1, int l1) {
		if (j < k && j < l && j < i1) {
			return false;
		}
		if (j > k && j > l && j > i1) {
			return false;
		}
		if (i < j1 && i < k1 && i < l1) {
			return false;
		}
		return i <= j1 || i <= k1 || i <= l1;
	}

	static {
		Model.anIntArray1710 = Rasterizer3D.anIntArray1536;
		Model.anIntArray1711 = Rasterizer3D.anIntArray1537;
		Model.anIntArray1712 = Rasterizer3D.anIntArray1548;
		Model.anIntArray1713 = Rasterizer3D.anIntArray1535;
	}
}
