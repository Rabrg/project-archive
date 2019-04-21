package com.runescape.scene;

import com.runescape.cache.def.FloorDefinition;
import com.runescape.cache.def.GameObjectDefinition;
import com.runescape.media.Rasterizer3D;
import com.runescape.media.renderable.GameObject;
import com.runescape.media.renderable.Model;
import com.runescape.media.renderable.Renderable;
import com.runescape.net.Buffer;
import com.runescape.net.requester.OnDemandRequester;
import com.runescape.scene.util.CollisionMap;
import com.runescape.scene.util.TileUtilities;

public class Region {
	
	public byte[][][] aByteArrayArrayArray138;
	public byte aByte139 = 0;
	public boolean aBoolean140 = true;
	public static int anInt141 = (int) (Math.random() * 17.0) - 8;
	public byte[][][] aByteArrayArrayArray142;
	public static final int[] anIntArray143 = { 0, -1, 0, 1 };
	public int[] anIntArray144;
	public int[] anIntArray145;
	public int[] anIntArray146;
	public int[] anIntArray147;
	public int[] anIntArray148;
	public int[][][] anIntArrayArrayArray149;
	public static int anInt150 = 99;
	public int anInt151;
	public int anInt152;
	public byte[][][] aByteArrayArrayArray153;
	public static byte aByte154 = -80;
	public byte[][][] aByteArrayArrayArray155;
	public byte aByte156 = 0;
	public boolean aBoolean157 = true;
	public static final int[] anIntArray158 = { 1, 2, 4, 8 };
	public byte[][][] aByteArrayArrayArray159;
	public int anInt160 = 20411;
	public static final int[] anIntArray161 = { 1, 0, -1, 0 };
	public static int anInt162;
	public static int anInt163 = (int) (Math.random() * 33.0) - 16;
	public byte[][][] aByteArrayArrayArray164;
	public int[][] anIntArrayArray165;
	public int anInt166 = 69;
	public static final int[] anIntArray167 = { 16, 32, 64, 128 };
	public int[][][] anIntArrayArrayArray168;
	public static boolean aBoolean169 = true;

	public static int method163(int i, int i_0_, int i_1_) {
		int i_2_ = i / i_1_;
		int i_3_ = i & i_1_ - 1;
		int i_4_ = i_0_ / i_1_;
		int i_5_ = i_0_ & i_1_ - 1;
		int i_6_ = Region.method178(i_2_, i_4_);
		int i_7_ = Region.method178(i_2_ + 1, i_4_);
		int i_8_ = Region.method178(i_2_, i_4_ + 1);
		int i_9_ = Region.method178(i_2_ + 1, i_4_ + 1);
		int i_10_ = Region.method176(i_6_, i_7_, i_3_, i_1_);
		int i_11_ = Region.method176(i_8_, i_9_, i_3_, i_1_);
		return Region.method176(i_10_, i_11_, i_5_, i_1_);
	}

	public int method164(int i, int i_12_, int i_13_, byte i_14_) {
		if (i_14_ == aByte156) {
		} else {
			return 2;
		}
		if ((aByteArrayArrayArray138[i_12_][i_13_][i] & 0x8) != 0) {
			return 0;
		}
		if (i_12_ > 0 && (aByteArrayArrayArray138[1][i_13_][i] & 0x2) != 0) {
			return i_12_ - 1;
		}
		return i_12_;
	}

	public static void method165(int i, int i_15_, int i_16_, int i_17_, CollisionMap class46, int i_18_, int i_19_,
			int i_20_, int i_21_, Scene class22, int[][][] is) {
		int i_22_ = is[i_15_][i_19_][i_17_];
		int i_23_ = is[i_15_][i_19_ + 1][i_17_];
		int i_24_ = is[i_15_][i_19_ + 1][i_17_ + 1];
		int i_25_ = is[i_15_][i_19_][i_17_ + 1];
		int i_26_ = i_22_ + i_23_ + i_24_ + i_25_ >> 2;
		GameObjectDefinition class47 = GameObjectDefinition.method423(i);
		if (i_20_ == 0) {
			int i_27_ = i_19_ + (i_17_ << 7) + (i << 14) + 1073741824;
			if (!class47.aBoolean759) {
				i_27_ += -2147483648;
			}
			byte i_28_ = (byte) ((i_18_ << 6) + i_16_);
			if (i_16_ == 22) {
				Renderable class50_sub1_sub4;
				if (class47.anInt803 == -1 && class47.anIntArray805 == null) {
					class50_sub1_sub4 = class47.method431(22, i_18_, i_22_, i_23_, i_24_, i_25_, -1);
				} else {
					class50_sub1_sub4 = new GameObject(class47.anInt803, i_24_, i_25_, i_23_, 22, (byte) 3, i, true,
							i_22_, i_18_);
				}
				class22.method247(i_19_, i_17_, 669, i_28_, i_27_, i_26_, i_21_, class50_sub1_sub4);
				if (class47.aBoolean810 && class47.aBoolean759) {
					class46.method414(8, i_17_, i_19_);
				}
			} else if (i_16_ == 10 || i_16_ == 11) {
				Renderable class50_sub1_sub4;
				if (class47.anInt803 == -1 && class47.anIntArray805 == null) {
					class50_sub1_sub4 = class47.method431(10, i_18_, i_22_, i_23_, i_24_, i_25_, -1);
				} else {
					class50_sub1_sub4 = new GameObject(class47.anInt803, i_24_, i_25_, i_23_, 10, (byte) 3, i, true,
							i_22_, i_18_);
				}
				if (class50_sub1_sub4 != null) {
					int i_29_ = 0;
					if (i_16_ == 11) {
						i_29_ += 256;
					}
					int i_30_;
					int i_31_;
					if (i_18_ == 1 || i_18_ == 3) {
						i_30_ = class47.anInt775;
						i_31_ = class47.anInt801;
					} else {
						i_30_ = class47.anInt801;
						i_31_ = class47.anInt775;
					}
					class22.method251(i_21_, i_30_, i_17_, class50_sub1_sub4, i_28_, i_29_, i_19_, -896, i_31_, i_26_,
							i_27_);
				}
				if (class47.aBoolean810) {
					class46.method413(i_17_, i_18_, class47.anInt775, class47.anInt801, class47.aBoolean809, i_19_,
							(byte) 52);
				}
			} else if (i_16_ >= 12) {
				Renderable class50_sub1_sub4;
				if (class47.anInt803 == -1 && class47.anIntArray805 == null) {
					class50_sub1_sub4 = class47.method431(i_16_, i_18_, i_22_, i_23_, i_24_, i_25_, -1);
				} else {
					class50_sub1_sub4 = new GameObject(class47.anInt803, i_24_, i_25_, i_23_, i_16_, (byte) 3, i, true,
							i_22_, i_18_);
				}
				class22.method251(i_21_, 1, i_17_, class50_sub1_sub4, i_28_, 0, i_19_, -896, 1, i_26_, i_27_);
				if (class47.aBoolean810) {
					class46.method413(i_17_, i_18_, class47.anInt775, class47.anInt801, class47.aBoolean809, i_19_,
							(byte) 52);
				}
			} else if (i_16_ == 0) {
				Renderable class50_sub1_sub4;
				if (class47.anInt803 == -1 && class47.anIntArray805 == null) {
					class50_sub1_sub4 = class47.method431(0, i_18_, i_22_, i_23_, i_24_, i_25_, -1);
				} else {
					class50_sub1_sub4 = new GameObject(class47.anInt803, i_24_, i_25_, i_23_, 0, (byte) 3, i, true,
							i_22_, i_18_);
				}
				class22.method249(i_26_, 49878, 0, Region.anIntArray158[i_18_], null, i_19_, i_27_, i_28_, i_17_,
						class50_sub1_sub4, i_21_);
				if (class47.aBoolean810) {
					class46.method412(i_18_, 37679, class47.aBoolean809, i_16_, i_19_, i_17_);
				}
			} else if (i_16_ == 1) {
				Renderable class50_sub1_sub4;
				if (class47.anInt803 == -1 && class47.anIntArray805 == null) {
					class50_sub1_sub4 = class47.method431(1, i_18_, i_22_, i_23_, i_24_, i_25_, -1);
				} else {
					class50_sub1_sub4 = new GameObject(class47.anInt803, i_24_, i_25_, i_23_, 1, (byte) 3, i, true,
							i_22_, i_18_);
				}
				class22.method249(i_26_, 49878, 0, Region.anIntArray167[i_18_], null, i_19_, i_27_, i_28_, i_17_,
						class50_sub1_sub4, i_21_);
				if (class47.aBoolean810) {
					class46.method412(i_18_, 37679, class47.aBoolean809, i_16_, i_19_, i_17_);
				}
			} else if (i_16_ == 2) {
				int i_32_ = i_18_ + 1 & 0x3;
				Renderable class50_sub1_sub4;
				Renderable class50_sub1_sub4_33_;
				if (class47.anInt803 == -1 && class47.anIntArray805 == null) {
					class50_sub1_sub4 = class47.method431(2, 4 + i_18_, i_22_, i_23_, i_24_, i_25_, -1);
					class50_sub1_sub4_33_ = class47.method431(2, i_32_, i_22_, i_23_, i_24_, i_25_, -1);
				} else {
					class50_sub1_sub4 = new GameObject(class47.anInt803, i_24_, i_25_, i_23_, 2, (byte) 3, i, true,
							i_22_, 4 + i_18_);
					class50_sub1_sub4_33_ = new GameObject(class47.anInt803, i_24_, i_25_, i_23_, 2, (byte) 3, i, true,
							i_22_, i_32_);
				}
				class22.method249(i_26_, 49878, Region.anIntArray158[i_32_], Region.anIntArray158[i_18_],
						class50_sub1_sub4_33_, i_19_, i_27_, i_28_, i_17_, class50_sub1_sub4, i_21_);
				if (class47.aBoolean810) {
					class46.method412(i_18_, 37679, class47.aBoolean809, i_16_, i_19_, i_17_);
				}
			} else if (i_16_ == 3) {
				Renderable class50_sub1_sub4;
				if (class47.anInt803 == -1 && class47.anIntArray805 == null) {
					class50_sub1_sub4 = class47.method431(3, i_18_, i_22_, i_23_, i_24_, i_25_, -1);
				} else {
					class50_sub1_sub4 = new GameObject(class47.anInt803, i_24_, i_25_, i_23_, 3, (byte) 3, i, true,
							i_22_, i_18_);
				}
				class22.method249(i_26_, 49878, 0, Region.anIntArray167[i_18_], null, i_19_, i_27_, i_28_, i_17_,
						class50_sub1_sub4, i_21_);
				if (class47.aBoolean810) {
					class46.method412(i_18_, 37679, class47.aBoolean809, i_16_, i_19_, i_17_);
				}
			} else if (i_16_ == 9) {
				Renderable class50_sub1_sub4;
				if (class47.anInt803 == -1 && class47.anIntArray805 == null) {
					class50_sub1_sub4 = class47.method431(i_16_, i_18_, i_22_, i_23_, i_24_, i_25_, -1);
				} else {
					class50_sub1_sub4 = new GameObject(class47.anInt803, i_24_, i_25_, i_23_, i_16_, (byte) 3, i, true,
							i_22_, i_18_);
				}
				class22.method251(i_21_, 1, i_17_, class50_sub1_sub4, i_28_, 0, i_19_, -896, 1, i_26_, i_27_);
				if (class47.aBoolean810) {
					class46.method413(i_17_, i_18_, class47.anInt775, class47.anInt801, class47.aBoolean809, i_19_,
							(byte) 52);
				}
			} else {
				if (class47.aBoolean769) {
					if (i_18_ == 1) {
						int i_34_ = i_25_;
						i_25_ = i_24_;
						i_24_ = i_23_;
						i_23_ = i_22_;
						i_22_ = i_34_;
					} else if (i_18_ == 2) {
						int i_35_ = i_25_;
						i_25_ = i_23_;
						i_23_ = i_35_;
						i_35_ = i_24_;
						i_24_ = i_22_;
						i_22_ = i_35_;
					} else if (i_18_ == 3) {
						int i_36_ = i_25_;
						i_25_ = i_22_;
						i_22_ = i_23_;
						i_23_ = i_24_;
						i_24_ = i_36_;
					}
				}
				if (i_16_ == 4) {
					Renderable class50_sub1_sub4;
					if (class47.anInt803 == -1 && class47.anIntArray805 == null) {
						class50_sub1_sub4 = class47.method431(4, 0, i_22_, i_23_, i_24_, i_25_, -1);
					} else {
						class50_sub1_sub4 = new GameObject(class47.anInt803, i_24_, i_25_, i_23_, 4, (byte) 3, i, true,
								i_22_, 0);
					}
					class22.method250(i_21_, Region.anIntArray158[i_18_], i_18_ * 512, i_27_, i_28_, i_19_, 0, i_17_,
							0, i_26_, class50_sub1_sub4, -930);
				} else if (i_16_ == 5) {
					int i_37_ = 16;
					int i_38_ = class22.method267(i_21_, i_19_, i_17_);
					if (i_38_ > 0) {
						i_37_ = GameObjectDefinition.method423(i_38_ >> 14 & 0x7fff).anInt802;
					}
					Renderable class50_sub1_sub4;
					if (class47.anInt803 == -1 && class47.anIntArray805 == null) {
						class50_sub1_sub4 = class47.method431(4, 0, i_22_, i_23_, i_24_, i_25_, -1);
					} else {
						class50_sub1_sub4 = new GameObject(class47.anInt803, i_24_, i_25_, i_23_, 4, (byte) 3, i, true,
								i_22_, 0);
					}
					class22.method250(i_21_, Region.anIntArray158[i_18_], i_18_ * 512, i_27_, i_28_, i_19_,
							Region.anIntArray143[i_18_] * i_37_, i_17_, Region.anIntArray161[i_18_] * i_37_, i_26_,
							class50_sub1_sub4, -930);
				} else if (i_16_ == 6) {
					Renderable class50_sub1_sub4;
					if (class47.anInt803 == -1 && class47.anIntArray805 == null) {
						class50_sub1_sub4 = class47.method431(4, 0, i_22_, i_23_, i_24_, i_25_, -1);
					} else {
						class50_sub1_sub4 = new GameObject(class47.anInt803, i_24_, i_25_, i_23_, 4, (byte) 3, i, true,
								i_22_, 0);
					}
					class22.method250(i_21_, 256, i_18_, i_27_, i_28_, i_19_, 0, i_17_, 0, i_26_, class50_sub1_sub4,
							-930);
				} else if (i_16_ == 7) {
					Renderable class50_sub1_sub4;
					if (class47.anInt803 == -1 && class47.anIntArray805 == null) {
						class50_sub1_sub4 = class47.method431(4, 0, i_22_, i_23_, i_24_, i_25_, -1);
					} else {
						class50_sub1_sub4 = new GameObject(class47.anInt803, i_24_, i_25_, i_23_, 4, (byte) 3, i, true,
								i_22_, 0);
					}
					class22.method250(i_21_, 512, i_18_, i_27_, i_28_, i_19_, 0, i_17_, 0, i_26_, class50_sub1_sub4,
							-930);
				} else if (i_16_ == 8) {
					Renderable class50_sub1_sub4;
					if (class47.anInt803 == -1 && class47.anIntArray805 == null) {
						class50_sub1_sub4 = class47.method431(4, 0, i_22_, i_23_, i_24_, i_25_, -1);
					} else {
						class50_sub1_sub4 = new GameObject(class47.anInt803, i_24_, i_25_, i_23_, 4, (byte) 3, i, true,
								i_22_, 0);
					}
					class22.method250(i_21_, 768, i_18_, i_27_, i_28_, i_19_, 0, i_17_, 0, i_26_, class50_sub1_sub4,
							-930);
				}
			}
		}
	}

	public void method166(int i, int i_39_, int i_40_, int i_41_) {
		if (i == anInt160) {
			for (int i_42_ = 0; i_42_ < 8; i_42_++) {
				for (int i_43_ = 0; i_43_ < 8; i_43_++) {
					anIntArrayArrayArray149[i_39_][i_41_ + i_42_][i_40_ + i_43_] = 0;
				}
			}
			if (i_41_ > 0) {
				for (int i_44_ = 1; i_44_ < 8; i_44_++) {
					anIntArrayArrayArray149[i_39_][i_41_][i_40_ + i_44_] = anIntArrayArrayArray149[i_39_][i_41_ - 1][i_40_
							+ i_44_];
				}
			}
			if (i_40_ > 0) {
				for (int i_45_ = 1; i_45_ < 8; i_45_++) {
					anIntArrayArrayArray149[i_39_][i_41_ + i_45_][i_40_] = anIntArrayArrayArray149[i_39_][i_41_ + i_45_][i_40_ - 1];
				}
			}
			if (i_41_ > 0 && anIntArrayArrayArray149[i_39_][i_41_ - 1][i_40_] != 0) {
				anIntArrayArrayArray149[i_39_][i_41_][i_40_] = anIntArrayArrayArray149[i_39_][i_41_ - 1][i_40_];
			} else if (i_40_ > 0 && anIntArrayArrayArray149[i_39_][i_41_][i_40_ - 1] != 0) {
				anIntArrayArrayArray149[i_39_][i_41_][i_40_] = anIntArrayArrayArray149[i_39_][i_41_][i_40_ - 1];
			} else if (i_41_ > 0 && i_40_ > 0 && anIntArrayArrayArray149[i_39_][i_41_ - 1][i_40_ - 1] != 0) {
				anIntArrayArrayArray149[i_39_][i_41_][i_40_] = anIntArrayArrayArray149[i_39_][i_41_ - 1][i_40_ - 1];
			}
		}
	}

	public void method167(CollisionMap[] class46s, int i, Scene class22) {
		for (int i_46_ = 0; i_46_ < 4; i_46_++) {
			for (int i_47_ = 0; i_47_ < 104; i_47_++) {
				for (int i_48_ = 0; i_48_ < 104; i_48_++) {
					if ((aByteArrayArrayArray138[i_46_][i_47_][i_48_] & 0x1) == 1) {
						int i_49_ = i_46_;
						if ((aByteArrayArrayArray138[1][i_47_][i_48_] & 0x2) == 2) {
							i_49_--;
						}
						if (i_49_ >= 0) {
							class46s[i_49_].method414(8, i_48_, i_47_);
						}
					}
				}
			}
		}
		Region.anInt141 += (int) (Math.random() * 5.0) - 2;
		if (Region.anInt141 < -8) {
			Region.anInt141 = -8;
		}
		if (Region.anInt141 > 8) {
			Region.anInt141 = 8;
		}
		Region.anInt163 += (int) (Math.random() * 5.0) - 2;
		if (Region.anInt163 < -16) {
			Region.anInt163 = -16;
		}
		if (Region.anInt163 > 16) {
			Region.anInt163 = 16;
		}
		for (int i_50_ = 0; i_50_ < 4; i_50_++) {
			byte[][] is = aByteArrayArrayArray164[i_50_];
			int i_51_ = 96;
			int i_52_ = 768;
			int i_53_ = -50;
			int i_54_ = -10;
			int i_55_ = -50;
			int i_56_ = (int) Math.sqrt(i_53_ * i_53_ + i_54_ * i_54_ + i_55_ * i_55_);
			int i_57_ = i_52_ * i_56_ >> 8;
			for (int i_58_ = 1; i_58_ < anInt152 - 1; i_58_++) {
				for (int i_59_ = 1; i_59_ < anInt151 - 1; i_59_++) {
					int i_60_ = (anIntArrayArrayArray149[i_50_][i_59_ + 1][i_58_] - anIntArrayArrayArray149[i_50_][i_59_ - 1][i_58_]);
					int i_61_ = (anIntArrayArrayArray149[i_50_][i_59_][i_58_ + 1] - anIntArrayArrayArray149[i_50_][i_59_][i_58_ - 1]);
					int i_62_ = (int) Math.sqrt(i_60_ * i_60_ + 65536 + i_61_ * i_61_);
					int i_63_ = (i_60_ << 8) / i_62_;
					int i_64_ = 65536 / i_62_;
					int i_65_ = (i_61_ << 8) / i_62_;
					int i_66_ = i_51_ + (i_53_ * i_63_ + i_54_ * i_64_ + i_55_ * i_65_) / i_57_;
					int i_67_ = ((is[i_59_ - 1][i_58_] >> 2) + (is[i_59_ + 1][i_58_] >> 3)
							+ (is[i_59_][i_58_ - 1] >> 2) + (is[i_59_][i_58_ + 1] >> 3) + (is[i_59_][i_58_] >> 1));
					anIntArrayArray165[i_59_][i_58_] = i_66_ - i_67_;
				}
			}
			for (int i_68_ = 0; i_68_ < anInt152; i_68_++) {
				anIntArray144[i_68_] = 0;
				anIntArray145[i_68_] = 0;
				anIntArray146[i_68_] = 0;
				anIntArray147[i_68_] = 0;
				anIntArray148[i_68_] = 0;
			}
			for (int i_69_ = -5; i_69_ < anInt151 + 5; i_69_++) {
				for (int i_70_ = 0; i_70_ < anInt152; i_70_++) {
					int i_71_ = i_69_ + 5;
					if (i_71_ >= 0 && i_71_ < anInt151) {
						int i_72_ = aByteArrayArrayArray159[i_50_][i_71_][i_70_] & 0xff;
						if (i_72_ > 0) {
							FloorDefinition class15 = FloorDefinition.aClass15Array314[i_72_ - 1];
							anIntArray144[i_70_] += class15.anInt323;
							anIntArray145[i_70_] += class15.anInt321;
							anIntArray146[i_70_] += class15.anInt322;
							anIntArray147[i_70_] += class15.anInt324;
							anIntArray148[i_70_]++;
						}
					}
					int i_73_ = i_69_ - 5;
					if (i_73_ >= 0 && i_73_ < anInt151) {
						int i_74_ = aByteArrayArrayArray159[i_50_][i_73_][i_70_] & 0xff;
						if (i_74_ > 0) {
							FloorDefinition class15 = FloorDefinition.aClass15Array314[i_74_ - 1];
							anIntArray144[i_70_] -= class15.anInt323;
							anIntArray145[i_70_] -= class15.anInt321;
							anIntArray146[i_70_] -= class15.anInt322;
							anIntArray147[i_70_] -= class15.anInt324;
							anIntArray148[i_70_]--;
						}
					}
				}
				if (i_69_ >= 1 && i_69_ < anInt151 - 1) {
					int i_75_ = 0;
					int i_76_ = 0;
					int i_77_ = 0;
					int i_78_ = 0;
					int i_79_ = 0;
					for (int i_80_ = -5; i_80_ < anInt152 + 5; i_80_++) {
						int i_81_ = i_80_ + 5;
						if (i_81_ >= 0 && i_81_ < anInt152) {
							i_75_ += anIntArray144[i_81_];
							i_76_ += anIntArray145[i_81_];
							i_77_ += anIntArray146[i_81_];
							i_78_ += anIntArray147[i_81_];
							i_79_ += anIntArray148[i_81_];
						}
						int i_82_ = i_80_ - 5;
						if (i_82_ >= 0 && i_82_ < anInt152) {
							i_75_ -= anIntArray144[i_82_];
							i_76_ -= anIntArray145[i_82_];
							i_77_ -= anIntArray146[i_82_];
							i_78_ -= anIntArray147[i_82_];
							i_79_ -= anIntArray148[i_82_];
						}
						if (i_80_ >= 1
								&& i_80_ < anInt152 - 1
								&& (!Region.aBoolean169 || (aByteArrayArrayArray138[0][i_69_][i_80_] & 0x2) != 0 || ((aByteArrayArrayArray138[i_50_][i_69_][i_80_] & 0x10) == 0 && (method164(
										i_80_, i_50_, i_69_, (byte) 0) == Region.anInt162)))) {
							if (i_50_ < Region.anInt150) {
								Region.anInt150 = i_50_;
							}
							int i_83_ = (aByteArrayArrayArray159[i_50_][i_69_][i_80_] & 0xff);
							int i_84_ = (aByteArrayArrayArray155[i_50_][i_69_][i_80_] & 0xff);
							if (i_83_ > 0 || i_84_ > 0) {
								int i_85_ = anIntArrayArrayArray149[i_50_][i_69_][i_80_];
								int i_86_ = (anIntArrayArrayArray149[i_50_][i_69_ + 1][i_80_]);
								int i_87_ = (anIntArrayArrayArray149[i_50_][i_69_ + 1][i_80_ + 1]);
								int i_88_ = (anIntArrayArrayArray149[i_50_][i_69_][i_80_ + 1]);
								int i_89_ = anIntArrayArray165[i_69_][i_80_];
								int i_90_ = anIntArrayArray165[i_69_ + 1][i_80_];
								int i_91_ = anIntArrayArray165[i_69_ + 1][i_80_ + 1];
								int i_92_ = anIntArrayArray165[i_69_][i_80_ + 1];
								int i_93_ = -1;
								int i_94_ = -1;
								if (i_83_ > 0) {
									int i_95_ = i_75_ * 256 / i_78_;
									int i_96_ = i_76_ / i_79_;
									int i_97_ = i_77_ / i_79_;
									i_93_ = method177(i_95_, i_96_, i_97_);
									i_95_ = i_95_ + Region.anInt141 & 0xff;
									i_97_ += Region.anInt163;
									if (i_97_ < 0) {
										i_97_ = 0;
									} else if (i_97_ > 255) {
										i_97_ = 255;
									}
									i_94_ = method177(i_95_, i_96_, i_97_);
								}
								if (i_50_ > 0) {
									boolean bool = true;
									if (i_83_ == 0 && (aByteArrayArrayArray153[i_50_][i_69_][i_80_]) != 0) {
										bool = false;
									}
									if (i_84_ > 0 && !(FloorDefinition.aClass15Array314[i_84_ - 1].aBoolean319)) {
										bool = false;
									}
									if (bool && i_85_ == i_86_ && i_85_ == i_87_ && i_85_ == i_88_) {
										anIntArrayArrayArray168[i_50_][i_69_][i_80_] |= 0x924;
									}
								}
								int i_98_ = 0;
								if (i_93_ != -1) {
									i_98_ = (Rasterizer3D.anIntArray1548[Region.method171(i_94_, 96)]);
								}
								if (i_84_ == 0) {
									class22.method246(i_50_, i_69_, i_80_, 0, 0, -1, i_85_, i_86_, i_87_, i_88_,
											Region.method171(i_93_, i_89_), Region.method171(i_93_, i_90_),
											Region.method171(i_93_, i_91_), Region.method171(i_93_, i_92_), 0, 0, 0, 0,
											i_98_, 0);
								} else {
									int i_99_ = ((aByteArrayArrayArray153[i_50_][i_69_][i_80_]) + 1);
									byte i_100_ = (aByteArrayArrayArray142[i_50_][i_69_][i_80_]);
									FloorDefinition class15 = FloorDefinition.aClass15Array314[i_84_ - 1];
									int i_101_ = class15.anInt317;
									int i_102_;
									int i_103_;
									if (i_101_ >= 0) {
										i_103_ = Rasterizer3D.method498(i_101_, 0);
										i_102_ = -1;
									} else if (class15.anInt316 == 16711935) {
										i_102_ = -2;
										i_101_ = -1;
										i_103_ = (Rasterizer3D.anIntArray1548[method182(class15.anInt325, 96)]);
									} else {
										i_102_ = method177(class15.anInt320, class15.anInt321, class15.anInt322);
										i_103_ = (Rasterizer3D.anIntArray1548[method182(class15.anInt325, 96)]);
									}
									class22.method246(i_50_, i_69_, i_80_, i_99_, i_100_, i_101_, i_85_, i_86_, i_87_,
											i_88_, Region.method171(i_93_, i_89_), Region.method171(i_93_, i_90_),
											Region.method171(i_93_, i_91_), Region.method171(i_93_, i_92_),
											method182(i_102_, i_89_), method182(i_102_, i_90_),
											method182(i_102_, i_91_), method182(i_102_, i_92_), i_98_, i_103_);
								}
							}
						}
					}
				}
			}
			for (int i_104_ = 1; i_104_ < anInt152 - 1; i_104_++) {
				for (int i_105_ = 1; i_105_ < anInt151 - 1; i_105_++) {
					class22.method245(i_50_, i_105_, i_104_, method164(i_104_, i_50_, i_105_, (byte) 0));
				}
			}
		}
		class22.method272((byte) 2, -10, -50, -50);
		for (int i_106_ = 0; i_106_ < anInt151; i_106_++) {
			for (int i_107_ = 0; i_107_ < anInt152; i_107_++) {
				if ((aByteArrayArrayArray138[1][i_106_][i_107_] & 0x2) == 2) {
					class22.method243(true, i_106_, i_107_);
				}
			}
		}
		if (i <= 0) {
			anInt160 = 313;
		}
		int i_108_ = 1;
		int i_109_ = 2;
		int i_110_ = 4;
		for (int i_111_ = 0; i_111_ < 4; i_111_++) {
			if (i_111_ > 0) {
				i_108_ <<= 3;
				i_109_ <<= 3;
				i_110_ <<= 3;
			}
			for (int i_112_ = 0; i_112_ <= i_111_; i_112_++) {
				for (int i_113_ = 0; i_113_ <= anInt152; i_113_++) {
					for (int i_114_ = 0; i_114_ <= anInt151; i_114_++) {
						if ((anIntArrayArrayArray168[i_112_][i_114_][i_113_] & i_108_) != 0) {
							int i_115_ = i_113_;
							int i_116_ = i_113_;
							int i_117_ = i_112_;
							int i_118_ = i_112_;
							for (/**/; i_115_ > 0; i_115_--) {
								if (((anIntArrayArrayArray168[i_112_][i_114_][i_115_ - 1]) & i_108_) == 0) {
									break;
								}
							}
							for (/**/; i_116_ < anInt152; i_116_++) {
								if (((anIntArrayArrayArray168[i_112_][i_114_][i_116_ + 1]) & i_108_) == 0) {
									break;
								}
							}
							while_0_: for (/**/; i_117_ > 0; i_117_--) {
								for (int i_119_ = i_115_; i_119_ <= i_116_; i_119_++) {
									if (((anIntArrayArrayArray168[i_117_ - 1][i_114_][i_119_]) & i_108_) == 0) {
										break while_0_;
									}
								}
							}
							while_1_: for (/**/; i_118_ < i_111_; i_118_++) {
								for (int i_120_ = i_115_; i_120_ <= i_116_; i_120_++) {
									if (((anIntArrayArrayArray168[i_118_ + 1][i_114_][i_120_]) & i_108_) == 0) {
										break while_1_;
									}
								}
							}
							int i_121_ = (i_118_ + 1 - i_117_) * (i_116_ - i_115_ + 1);
							if (i_121_ >= 8) {
								int i_122_ = 240;
								int i_123_ = ((anIntArrayArrayArray149[i_118_][i_114_][i_115_]) - i_122_);
								int i_124_ = (anIntArrayArrayArray149[i_117_][i_114_][i_115_]);
								Scene.method244(-8967, i_114_ * 128, i_124_, i_114_ * 128, i_116_ * 128 + 128, i_111_,
										i_115_ * 128, i_123_, 1);
								for (int i_125_ = i_117_; i_125_ <= i_118_; i_125_++) {
									for (int i_126_ = i_115_; i_126_ <= i_116_; i_126_++) {
										anIntArrayArrayArray168[i_125_][i_114_][i_126_] &= i_108_ ^ 0xffffffff;
									}
								}
							}
						}
						if ((anIntArrayArrayArray168[i_112_][i_114_][i_113_] & i_109_) != 0) {
							int i_127_ = i_114_;
							int i_128_ = i_114_;
							int i_129_ = i_112_;
							int i_130_ = i_112_;
							for (/**/; i_127_ > 0; i_127_--) {
								if (((anIntArrayArrayArray168[i_112_][i_127_ - 1][i_113_]) & i_109_) == 0) {
									break;
								}
							}
							for (/**/; i_128_ < anInt151; i_128_++) {
								if (((anIntArrayArrayArray168[i_112_][i_128_ + 1][i_113_]) & i_109_) == 0) {
									break;
								}
							}
							while_2_: for (/**/; i_129_ > 0; i_129_--) {
								for (int i_131_ = i_127_; i_131_ <= i_128_; i_131_++) {
									if (((anIntArrayArrayArray168[i_129_ - 1][i_131_][i_113_]) & i_109_) == 0) {
										break while_2_;
									}
								}
							}
							while_3_: for (/**/; i_130_ < i_111_; i_130_++) {
								for (int i_132_ = i_127_; i_132_ <= i_128_; i_132_++) {
									if (((anIntArrayArrayArray168[i_130_ + 1][i_132_][i_113_]) & i_109_) == 0) {
										break while_3_;
									}
								}
							}
							int i_133_ = (i_130_ + 1 - i_129_) * (i_128_ - i_127_ + 1);
							if (i_133_ >= 8) {
								int i_134_ = 240;
								int i_135_ = ((anIntArrayArrayArray149[i_130_][i_127_][i_113_]) - i_134_);
								int i_136_ = (anIntArrayArrayArray149[i_129_][i_127_][i_113_]);
								Scene.method244(-8967, i_127_ * 128, i_136_, i_128_ * 128 + 128, i_113_ * 128, i_111_,
										i_113_ * 128, i_135_, 2);
								for (int i_137_ = i_129_; i_137_ <= i_130_; i_137_++) {
									for (int i_138_ = i_127_; i_138_ <= i_128_; i_138_++) {
										anIntArrayArrayArray168[i_137_][i_138_][i_113_] &= i_109_ ^ 0xffffffff;
									}
								}
							}
						}
						if ((anIntArrayArrayArray168[i_112_][i_114_][i_113_] & i_110_) != 0) {
							int i_139_ = i_114_;
							int i_140_ = i_114_;
							int i_141_ = i_113_;
							int i_142_ = i_113_;
							for (/**/; i_141_ > 0; i_141_--) {
								if (((anIntArrayArrayArray168[i_112_][i_114_][i_141_ - 1]) & i_110_) == 0) {
									break;
								}
							}
							for (/**/; i_142_ < anInt152; i_142_++) {
								if (((anIntArrayArrayArray168[i_112_][i_114_][i_142_ + 1]) & i_110_) == 0) {
									break;
								}
							}
							while_4_: for (/**/; i_139_ > 0; i_139_--) {
								for (int i_143_ = i_141_; i_143_ <= i_142_; i_143_++) {
									if (((anIntArrayArrayArray168[i_112_][i_139_ - 1][i_143_]) & i_110_) == 0) {
										break while_4_;
									}
								}
							}
							while_5_: for (/**/; i_140_ < anInt151; i_140_++) {
								for (int i_144_ = i_141_; i_144_ <= i_142_; i_144_++) {
									if (((anIntArrayArrayArray168[i_112_][i_140_ + 1][i_144_]) & i_110_) == 0) {
										break while_5_;
									}
								}
							}
							if ((i_140_ - i_139_ + 1) * (i_142_ - i_141_ + 1) >= 4) {
								int i_145_ = (anIntArrayArrayArray149[i_112_][i_139_][i_141_]);
								Scene.method244(-8967, i_139_ * 128, i_145_, i_140_ * 128 + 128, i_142_ * 128 + 128,
										i_111_, i_141_ * 128, i_145_, 4);
								for (int i_146_ = i_139_; i_146_ <= i_140_; i_146_++) {
									for (int i_147_ = i_141_; i_147_ <= i_142_; i_147_++) {
										anIntArrayArrayArray168[i_112_][i_146_][i_147_] &= i_110_ ^ 0xffffffff;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public void method168(int i, int i_148_, boolean bool, byte[] is, int i_149_, int i_150_, int i_151_,
			CollisionMap[] class46s, int i_152_, int i_153_) {
		if (bool) {
			anInt166 = 476;
		}
		for (int i_154_ = 0; i_154_ < 8; i_154_++) {
			for (int i_155_ = 0; i_155_ < 8; i_155_++) {
				if (i_151_ + i_154_ > 0 && i_151_ + i_154_ < 103 && i_152_ + i_155_ > 0 && i_152_ + i_155_ < 103) {
					class46s[i_149_].anIntArrayArray757[i_151_ + i_154_][(i_152_ + i_155_)] &= ~0x1000000;
				}
			}
		}
		Buffer class50_sub1_sub2 = new Buffer(true, is);
		for (int i_156_ = 0; i_156_ < 4; i_156_++) {
			for (int i_157_ = 0; i_157_ < 64; i_157_++) {
				for (int i_158_ = 0; i_158_ < 64; i_158_++) {
					if (i_156_ == i_150_ && i_157_ >= i_153_ && i_157_ < i_153_ + 8 && i_158_ >= i_148_
							&& i_158_ < i_148_ + 8) {
						method183(0, (byte) -61, 0, class50_sub1_sub2, i,
								i_151_ + TileUtilities.method348((byte) 7, i, i_157_ & 0x7, i_158_ & 0x7), i_149_,
								i_152_ + TileUtilities.method349(i_158_ & 0x7, i_157_ & 0x7, i, (byte) 5));
					} else {
						method183(0, (byte) -61, 0, class50_sub1_sub2, 0, -1, 0, -1);
					}
				}
			}
		}
	}

	public static void method169(OnDemandRequester class32_sub1, Buffer class50_sub1_sub2, byte i) {
		if (i == -3) {
			int i_159_ = -1;
			for (;;) {
				int i_160_ = class50_sub1_sub2.method535();
				if (i_160_ == 0) {
					break;
				}
				i_159_ += i_160_;
				GameObjectDefinition class47 = GameObjectDefinition.method423(i_159_);
				class47.method425(class32_sub1, -747);
				for (;;) {
					int i_161_ = class50_sub1_sub2.method535();
					if (i_161_ == 0) {
						break;
					}
					class50_sub1_sub2.method521();
				}
			}
		}
	}

	public static boolean method170(int i, byte i_162_, int i_163_) {
		GameObjectDefinition class47 = GameObjectDefinition.method423(i_163_);
		if (i_162_ != Region.aByte154) {
			for (int i_164_ = 1; i_164_ > 0; i_164_++) {
				/* empty */
			}
		}
		if (i == 11) {
			i = 10;
		}
		if (i >= 5 && i <= 8) {
			i = 4;
		}
		return class47.method432(26261, i);
	}

	public static int method171(int i, int i_165_) {
		if (i == -1) {
			return 12345678;
		}
		i_165_ = i_165_ * (i & 0x7f) / 128;
		if (i_165_ < 2) {
			i_165_ = 2;
		} else if (i_165_ > 126) {
			i_165_ = 126;
		}
		return (i & 0xff80) + i_165_;
	}

	public void method172(int i, CollisionMap[] class46s, Scene class22, boolean bool, byte[] is, int i_166_,
			int i_167_, int i_168_, int i_169_, int i_170_, int i_171_) {
		Buffer class50_sub1_sub2 = new Buffer(true, is);
		if (!bool) {
			int i_172_ = -1;
			for (;;) {
				int i_173_ = class50_sub1_sub2.method535();
				if (i_173_ == 0) {
					break;
				}
				i_172_ += i_173_;
				int i_174_ = 0;
				for (;;) {
					int i_175_ = class50_sub1_sub2.method535();
					if (i_175_ == 0) {
						break;
					}
					i_174_ += i_175_ - 1;
					int i_176_ = i_174_ & 0x3f;
					int i_177_ = i_174_ >> 6 & 0x3f;
					int i_178_ = i_174_ >> 12;
					int i_179_ = class50_sub1_sub2.method521();
					int i_180_ = i_179_ >> 2;
					int i_181_ = i_179_ & 0x3;
					if (i_178_ == i_171_ && i_177_ >= i_168_ && i_177_ < i_168_ + 8 && i_176_ >= i_170_
							&& i_176_ < i_170_ + 8) {
						GameObjectDefinition class47 = GameObjectDefinition.method423(i_172_);
						int i_182_ = (i_169_ + TileUtilities.method350(i_167_, class47.anInt775, i_181_, i_177_ & 0x7,
								(byte) -117, class47.anInt801, i_176_ & 0x7));
						int i_183_ = (i_166_ + TileUtilities.method351(class47.anInt801, i_167_, 671, i_177_ & 0x7,
								i_176_ & 0x7, class47.anInt775, i_181_));
						if (i_182_ > 0 && i_183_ > 0 && i_182_ < 103 && i_183_ < 103) {
							int i_184_ = i;
							if ((aByteArrayArrayArray138[1][i_182_][i_183_] & 0x2) == 2) {
								i_184_--;
							}
							CollisionMap class46 = null;
							if (i_184_ >= 0) {
								class46 = class46s[i_184_];
							}
							method173(class22, class46, i_183_, i, i_182_, aByte139, i_181_ + i_167_ & 0x3, i_180_,
									i_172_);
						}
					}
				}
			}
		}
	}

	public void method173(Scene class22, CollisionMap class46, int i, int i_185_, int i_186_, byte i_187_, int i_188_,
			int i_189_, int i_190_) {
		if (!Region.aBoolean169
				|| (aByteArrayArrayArray138[0][i_186_][i] & 0x2) != 0
				|| ((aByteArrayArrayArray138[i_185_][i_186_][i] & 0x10) == 0 && method164(i, i_185_, i_186_, (byte) 0) == Region.anInt162)) {
			if (i_185_ < Region.anInt150) {
				Region.anInt150 = i_185_;
			}
			int i_191_ = anIntArrayArrayArray149[i_185_][i_186_][i];
			int i_192_ = anIntArrayArrayArray149[i_185_][i_186_ + 1][i];
			int i_193_ = anIntArrayArrayArray149[i_185_][i_186_ + 1][i + 1];
			int i_194_ = anIntArrayArrayArray149[i_185_][i_186_][i + 1];
			int i_195_ = i_191_ + i_192_ + i_193_ + i_194_ >> 2;
			GameObjectDefinition class47 = GameObjectDefinition.method423(i_190_);
			int i_196_ = i_186_ + (i << 7) + (i_190_ << 14) + 1073741824;
			if (i_187_ != 0) {
				aBoolean157 = !aBoolean157;
			}
			if (!class47.aBoolean759) {
				i_196_ += -2147483648;
			}
			byte i_197_ = (byte) ((i_188_ << 6) + i_189_);
			if (i_189_ == 22) {
				if (!Region.aBoolean169 || class47.aBoolean759 || class47.aBoolean765) {
					Renderable class50_sub1_sub4;
					if (class47.anInt803 == -1 && class47.anIntArray805 == null) {
						class50_sub1_sub4 = class47.method431(22, i_188_, i_191_, i_192_, i_193_, i_194_, -1);
					} else {
						class50_sub1_sub4 = new GameObject(class47.anInt803, i_193_, i_194_, i_192_, 22, (byte) 3,
								i_190_, true, i_191_, i_188_);
					}
					class22.method247(i_186_, i, 669, i_197_, i_196_, i_195_, i_185_, class50_sub1_sub4);
					if (class47.aBoolean810 && class47.aBoolean759 && class46 != null) {
						class46.method414(8, i, i_186_);
					}
				}
			} else if (i_189_ == 10 || i_189_ == 11) {
				Renderable class50_sub1_sub4;
				if (class47.anInt803 == -1 && class47.anIntArray805 == null) {
					class50_sub1_sub4 = class47.method431(10, i_188_, i_191_, i_192_, i_193_, i_194_, -1);
				} else {
					class50_sub1_sub4 = new GameObject(class47.anInt803, i_193_, i_194_, i_192_, 10, (byte) 3, i_190_,
							true, i_191_, i_188_);
				}
				if (class50_sub1_sub4 != null) {
					int i_198_ = 0;
					if (i_189_ == 11) {
						i_198_ += 256;
					}
					int i_199_;
					int i_200_;
					if (i_188_ == 1 || i_188_ == 3) {
						i_199_ = class47.anInt775;
						i_200_ = class47.anInt801;
					} else {
						i_199_ = class47.anInt801;
						i_200_ = class47.anInt775;
					}
					if (class22.method251(i_185_, i_199_, i, class50_sub1_sub4, i_197_, i_198_, i_186_, -896, i_200_,
							i_195_, i_196_) && class47.aBoolean807) {
						Model class50_sub1_sub4_sub4;
						if (class50_sub1_sub4 instanceof Model) {
							class50_sub1_sub4_sub4 = (Model) class50_sub1_sub4;
						} else {
							class50_sub1_sub4_sub4 = class47.method431(10, i_188_, i_191_, i_192_, i_193_, i_194_, -1);
						}
						if (class50_sub1_sub4_sub4 != null) {
							for (int i_201_ = 0; i_201_ <= i_199_; i_201_++) {
								for (int i_202_ = 0; i_202_ <= i_200_; i_202_++) {
									int i_203_ = class50_sub1_sub4_sub4.anInt1671 / 4;
									if (i_203_ > 30) {
										i_203_ = 30;
									}
									if (i_203_ > (aByteArrayArrayArray164[i_185_][i_186_ + i_201_][i + i_202_])) {
										aByteArrayArrayArray164[i_185_][i_186_ + i_201_][i + i_202_] = (byte) i_203_;
									}
								}
							}
						}
					}
				}
				if (class47.aBoolean810 && class46 != null) {
					class46.method413(i, i_188_, class47.anInt775, class47.anInt801, class47.aBoolean809, i_186_,
							(byte) 52);
				}
			} else if (i_189_ >= 12) {
				Renderable class50_sub1_sub4;
				if (class47.anInt803 == -1 && class47.anIntArray805 == null) {
					class50_sub1_sub4 = class47.method431(i_189_, i_188_, i_191_, i_192_, i_193_, i_194_, -1);
				} else {
					class50_sub1_sub4 = new GameObject(class47.anInt803, i_193_, i_194_, i_192_, i_189_, (byte) 3,
							i_190_, true, i_191_, i_188_);
				}
				class22.method251(i_185_, 1, i, class50_sub1_sub4, i_197_, 0, i_186_, -896, 1, i_195_, i_196_);
				if (i_189_ >= 12 && i_189_ <= 17 && i_189_ != 13 && i_185_ > 0) {
					anIntArrayArrayArray168[i_185_][i_186_][i] |= 0x924;
				}
				if (class47.aBoolean810 && class46 != null) {
					class46.method413(i, i_188_, class47.anInt775, class47.anInt801, class47.aBoolean809, i_186_,
							(byte) 52);
				}
			} else if (i_189_ == 0) {
				Renderable class50_sub1_sub4;
				if (class47.anInt803 == -1 && class47.anIntArray805 == null) {
					class50_sub1_sub4 = class47.method431(0, i_188_, i_191_, i_192_, i_193_, i_194_, -1);
				} else {
					class50_sub1_sub4 = new GameObject(class47.anInt803, i_193_, i_194_, i_192_, 0, (byte) 3, i_190_,
							true, i_191_, i_188_);
				}
				class22.method249(i_195_, 49878, 0, Region.anIntArray158[i_188_], null, i_186_, i_196_, i_197_, i,
						class50_sub1_sub4, i_185_);
				if (i_188_ == 0) {
					if (class47.aBoolean807) {
						aByteArrayArrayArray164[i_185_][i_186_][i] = (byte) 50;
						aByteArrayArrayArray164[i_185_][i_186_][i + 1] = (byte) 50;
					}
					if (class47.aBoolean797) {
						anIntArrayArrayArray168[i_185_][i_186_][i] |= 0x249;
					}
				} else if (i_188_ == 1) {
					if (class47.aBoolean807) {
						aByteArrayArrayArray164[i_185_][i_186_][i + 1] = (byte) 50;
						aByteArrayArrayArray164[i_185_][i_186_ + 1][i + 1] = (byte) 50;
					}
					if (class47.aBoolean797) {
						anIntArrayArrayArray168[i_185_][i_186_][i + 1] |= 0x492;
					}
				} else if (i_188_ == 2) {
					if (class47.aBoolean807) {
						aByteArrayArrayArray164[i_185_][i_186_ + 1][i] = (byte) 50;
						aByteArrayArrayArray164[i_185_][i_186_ + 1][i + 1] = (byte) 50;
					}
					if (class47.aBoolean797) {
						anIntArrayArrayArray168[i_185_][i_186_ + 1][i] |= 0x249;
					}
				} else if (i_188_ == 3) {
					if (class47.aBoolean807) {
						aByteArrayArrayArray164[i_185_][i_186_][i] = (byte) 50;
						aByteArrayArrayArray164[i_185_][i_186_ + 1][i] = (byte) 50;
					}
					if (class47.aBoolean797) {
						anIntArrayArrayArray168[i_185_][i_186_][i] |= 0x492;
					}
				}
				if (class47.aBoolean810 && class46 != null) {
					class46.method412(i_188_, 37679, class47.aBoolean809, i_189_, i_186_, i);
				}
				if (class47.anInt802 != 16) {
					class22.method257(i, class47.anInt802, i_185_, i_186_, 0);
				}
			} else if (i_189_ == 1) {
				Renderable class50_sub1_sub4;
				if (class47.anInt803 == -1 && class47.anIntArray805 == null) {
					class50_sub1_sub4 = class47.method431(1, i_188_, i_191_, i_192_, i_193_, i_194_, -1);
				} else {
					class50_sub1_sub4 = new GameObject(class47.anInt803, i_193_, i_194_, i_192_, 1, (byte) 3, i_190_,
							true, i_191_, i_188_);
				}
				class22.method249(i_195_, 49878, 0, Region.anIntArray167[i_188_], null, i_186_, i_196_, i_197_, i,
						class50_sub1_sub4, i_185_);
				if (class47.aBoolean807) {
					if (i_188_ == 0) {
						aByteArrayArrayArray164[i_185_][i_186_][i + 1] = (byte) 50;
					} else if (i_188_ == 1) {
						aByteArrayArrayArray164[i_185_][i_186_ + 1][i + 1] = (byte) 50;
					} else if (i_188_ == 2) {
						aByteArrayArrayArray164[i_185_][i_186_ + 1][i] = (byte) 50;
					} else if (i_188_ == 3) {
						aByteArrayArrayArray164[i_185_][i_186_][i] = (byte) 50;
					}
				}
				if (class47.aBoolean810 && class46 != null) {
					class46.method412(i_188_, 37679, class47.aBoolean809, i_189_, i_186_, i);
				}
			} else if (i_189_ == 2) {
				int i_204_ = i_188_ + 1 & 0x3;
				Renderable class50_sub1_sub4;
				Renderable class50_sub1_sub4_205_;
				if (class47.anInt803 == -1 && class47.anIntArray805 == null) {
					class50_sub1_sub4 = class47.method431(2, 4 + i_188_, i_191_, i_192_, i_193_, i_194_, -1);
					class50_sub1_sub4_205_ = class47.method431(2, i_204_, i_191_, i_192_, i_193_, i_194_, -1);
				} else {
					class50_sub1_sub4 = new GameObject(class47.anInt803, i_193_, i_194_, i_192_, 2, (byte) 3, i_190_,
							true, i_191_, 4 + i_188_);
					class50_sub1_sub4_205_ = new GameObject(class47.anInt803, i_193_, i_194_, i_192_, 2, (byte) 3,
							i_190_, true, i_191_, i_204_);
				}
				class22.method249(i_195_, 49878, Region.anIntArray158[i_204_], Region.anIntArray158[i_188_],
						class50_sub1_sub4_205_, i_186_, i_196_, i_197_, i, class50_sub1_sub4, i_185_);
				if (class47.aBoolean797) {
					if (i_188_ == 0) {
						anIntArrayArrayArray168[i_185_][i_186_][i] |= 0x249;
						anIntArrayArrayArray168[i_185_][i_186_][i + 1] |= 0x492;
					} else if (i_188_ == 1) {
						anIntArrayArrayArray168[i_185_][i_186_][i + 1] |= 0x492;
						anIntArrayArrayArray168[i_185_][i_186_ + 1][i] |= 0x249;
					} else if (i_188_ == 2) {
						anIntArrayArrayArray168[i_185_][i_186_ + 1][i] |= 0x249;
						anIntArrayArrayArray168[i_185_][i_186_][i] |= 0x492;
					} else if (i_188_ == 3) {
						anIntArrayArrayArray168[i_185_][i_186_][i] |= 0x492;
						anIntArrayArrayArray168[i_185_][i_186_][i] |= 0x249;
					}
				}
				if (class47.aBoolean810 && class46 != null) {
					class46.method412(i_188_, 37679, class47.aBoolean809, i_189_, i_186_, i);
				}
				if (class47.anInt802 != 16) {
					class22.method257(i, class47.anInt802, i_185_, i_186_, 0);
				}
			} else if (i_189_ == 3) {
				Renderable class50_sub1_sub4;
				if (class47.anInt803 == -1 && class47.anIntArray805 == null) {
					class50_sub1_sub4 = class47.method431(3, i_188_, i_191_, i_192_, i_193_, i_194_, -1);
				} else {
					class50_sub1_sub4 = new GameObject(class47.anInt803, i_193_, i_194_, i_192_, 3, (byte) 3, i_190_,
							true, i_191_, i_188_);
				}
				class22.method249(i_195_, 49878, 0, Region.anIntArray167[i_188_], null, i_186_, i_196_, i_197_, i,
						class50_sub1_sub4, i_185_);
				if (class47.aBoolean807) {
					if (i_188_ == 0) {
						aByteArrayArrayArray164[i_185_][i_186_][i + 1] = (byte) 50;
					} else if (i_188_ == 1) {
						aByteArrayArrayArray164[i_185_][i_186_ + 1][i + 1] = (byte) 50;
					} else if (i_188_ == 2) {
						aByteArrayArrayArray164[i_185_][i_186_ + 1][i] = (byte) 50;
					} else if (i_188_ == 3) {
						aByteArrayArrayArray164[i_185_][i_186_][i] = (byte) 50;
					}
				}
				if (class47.aBoolean810 && class46 != null) {
					class46.method412(i_188_, 37679, class47.aBoolean809, i_189_, i_186_, i);
				}
			} else if (i_189_ == 9) {
				Renderable class50_sub1_sub4;
				if (class47.anInt803 == -1 && class47.anIntArray805 == null) {
					class50_sub1_sub4 = class47.method431(i_189_, i_188_, i_191_, i_192_, i_193_, i_194_, -1);
				} else {
					class50_sub1_sub4 = new GameObject(class47.anInt803, i_193_, i_194_, i_192_, i_189_, (byte) 3,
							i_190_, true, i_191_, i_188_);
				}
				class22.method251(i_185_, 1, i, class50_sub1_sub4, i_197_, 0, i_186_, -896, 1, i_195_, i_196_);
				if (class47.aBoolean810 && class46 != null) {
					class46.method413(i, i_188_, class47.anInt775, class47.anInt801, class47.aBoolean809, i_186_,
							(byte) 52);
				}
			} else {
				if (class47.aBoolean769) {
					if (i_188_ == 1) {
						int i_206_ = i_194_;
						i_194_ = i_193_;
						i_193_ = i_192_;
						i_192_ = i_191_;
						i_191_ = i_206_;
					} else if (i_188_ == 2) {
						int i_207_ = i_194_;
						i_194_ = i_192_;
						i_192_ = i_207_;
						i_207_ = i_193_;
						i_193_ = i_191_;
						i_191_ = i_207_;
					} else if (i_188_ == 3) {
						int i_208_ = i_194_;
						i_194_ = i_191_;
						i_191_ = i_192_;
						i_192_ = i_193_;
						i_193_ = i_208_;
					}
				}
				if (i_189_ == 4) {
					Renderable class50_sub1_sub4;
					if (class47.anInt803 == -1 && class47.anIntArray805 == null) {
						class50_sub1_sub4 = class47.method431(4, 0, i_191_, i_192_, i_193_, i_194_, -1);
					} else {
						class50_sub1_sub4 = new GameObject(class47.anInt803, i_193_, i_194_, i_192_, 4, (byte) 3,
								i_190_, true, i_191_, 0);
					}
					class22.method250(i_185_, Region.anIntArray158[i_188_], i_188_ * 512, i_196_, i_197_, i_186_, 0, i,
							0, i_195_, class50_sub1_sub4, -930);
				} else if (i_189_ == 5) {
					int i_209_ = 16;
					int i_210_ = class22.method267(i_185_, i_186_, i);
					if (i_210_ > 0) {
						i_209_ = GameObjectDefinition.method423(i_210_ >> 14 & 0x7fff).anInt802;
					}
					Renderable class50_sub1_sub4;
					if (class47.anInt803 == -1 && class47.anIntArray805 == null) {
						class50_sub1_sub4 = class47.method431(4, 0, i_191_, i_192_, i_193_, i_194_, -1);
					} else {
						class50_sub1_sub4 = new GameObject(class47.anInt803, i_193_, i_194_, i_192_, 4, (byte) 3,
								i_190_, true, i_191_, 0);
					}
					class22.method250(i_185_, Region.anIntArray158[i_188_], i_188_ * 512, i_196_, i_197_, i_186_,
							Region.anIntArray143[i_188_] * i_209_, i, Region.anIntArray161[i_188_] * i_209_, i_195_,
							class50_sub1_sub4, -930);
				} else if (i_189_ == 6) {
					Renderable class50_sub1_sub4;
					if (class47.anInt803 == -1 && class47.anIntArray805 == null) {
						class50_sub1_sub4 = class47.method431(4, 0, i_191_, i_192_, i_193_, i_194_, -1);
					} else {
						class50_sub1_sub4 = new GameObject(class47.anInt803, i_193_, i_194_, i_192_, 4, (byte) 3,
								i_190_, true, i_191_, 0);
					}
					class22.method250(i_185_, 256, i_188_, i_196_, i_197_, i_186_, 0, i, 0, i_195_, class50_sub1_sub4,
							-930);
				} else if (i_189_ == 7) {
					Renderable class50_sub1_sub4;
					if (class47.anInt803 == -1 && class47.anIntArray805 == null) {
						class50_sub1_sub4 = class47.method431(4, 0, i_191_, i_192_, i_193_, i_194_, -1);
					} else {
						class50_sub1_sub4 = new GameObject(class47.anInt803, i_193_, i_194_, i_192_, 4, (byte) 3,
								i_190_, true, i_191_, 0);
					}
					class22.method250(i_185_, 512, i_188_, i_196_, i_197_, i_186_, 0, i, 0, i_195_, class50_sub1_sub4,
							-930);
				} else if (i_189_ == 8) {
					Renderable class50_sub1_sub4;
					if (class47.anInt803 == -1 && class47.anIntArray805 == null) {
						class50_sub1_sub4 = class47.method431(4, 0, i_191_, i_192_, i_193_, i_194_, -1);
					} else {
						class50_sub1_sub4 = new GameObject(class47.anInt803, i_193_, i_194_, i_192_, 4, (byte) 3,
								i_190_, true, i_191_, 0);
					}
					class22.method250(i_185_, 768, i_188_, i_196_, i_197_, i_186_, 0, i, 0, i_195_, class50_sub1_sub4,
							-930);
				}
			}
		}
	}

	public void method174(int i, boolean bool, int i_211_, int i_212_, byte[] is, int i_213_, CollisionMap[] class46s) {
		if (bool) {
			anInt166 = -379;
		}
		for (int i_214_ = 0; i_214_ < 4; i_214_++) {
			for (int i_215_ = 0; i_215_ < 64; i_215_++) {
				for (int i_216_ = 0; i_216_ < 64; i_216_++) {
					if (i_212_ + i_215_ > 0 && i_212_ + i_215_ < 103 && i + i_216_ > 0 && i + i_216_ < 103) {
						class46s[i_214_].anIntArrayArray757[i_212_ + i_215_][i + i_216_] &= ~0x1000000;
					}
				}
			}
		}
		Buffer class50_sub1_sub2 = new Buffer(true, is);
		for (int i_217_ = 0; i_217_ < 4; i_217_++) {
			for (int i_218_ = 0; i_218_ < 64; i_218_++) {
				for (int i_219_ = 0; i_219_ < 64; i_219_++) {
					method183(i_213_, (byte) -61, i_211_, class50_sub1_sub2, 0, i_218_ + i_212_, i_217_, i_219_ + i);
				}
			}
		}
	}

	public Region(int[][][] is, int i, byte[][][] is_220_, int i_221_, int i_222_) {
		Region.anInt150 = 99;
		anInt151 = i_222_;
		anInt152 = i_221_;
		anIntArrayArrayArray149 = is;
		aByteArrayArrayArray138 = is_220_;
		aByteArrayArrayArray159 = new byte[4][anInt151][anInt152];
		aByteArrayArrayArray155 = new byte[4][anInt151][anInt152];
		aByteArrayArrayArray153 = new byte[4][anInt151][anInt152];
		aByteArrayArrayArray142 = new byte[4][anInt151][anInt152];
		anIntArrayArrayArray168 = new int[4][anInt151 + 1][anInt152 + 1];
		aByteArrayArrayArray164 = new byte[4][anInt151 + 1][anInt152 + 1];
		anIntArrayArray165 = new int[anInt151 + 1][anInt152 + 1];
		anIntArray144 = new int[anInt152];
		anIntArray145 = new int[anInt152];
		anIntArray146 = new int[anInt152];
		anIntArray147 = new int[anInt152];
		anIntArray148 = new int[anInt152];
		if (i != 14290) {
			throw new NullPointerException();
		}
	}

	public static int method175(int i, int i_223_) {
		int i_224_ = i + i_223_ * 57;
		i_224_ = i_224_ << 13 ^ i_224_;
		int i_225_ = i_224_ * (i_224_ * i_224_ * 15731 + 789221) + 1376312589 & 0x7fffffff;
		return i_225_ >> 19 & 0xff;
	}

	public static int method176(int i, int i_226_, int i_227_, int i_228_) {
		int i_229_ = (65536 - Rasterizer3D.anIntArray1537[i_227_ * 1024 / i_228_] >> 1);
		return (i * (65536 - i_229_) >> 16) + (i_226_ * i_229_ >> 16);
	}

	public int method177(int i, int i_230_, int i_231_) {
		if (i_231_ > 179) {
			i_230_ /= 2;
		}
		if (i_231_ > 192) {
			i_230_ /= 2;
		}
		if (i_231_ > 217) {
			i_230_ /= 2;
		}
		if (i_231_ > 243) {
			i_230_ /= 2;
		}
		int i_232_ = (i / 4 << 10) + (i_230_ / 32 << 7) + i_231_ / 2;
		return i_232_;
	}

	public static int method178(int i, int i_233_) {
		int i_234_ = (Region.method175(i - 1, i_233_ - 1) + Region.method175(i + 1, i_233_ - 1)
				+ Region.method175(i - 1, i_233_ + 1) + Region.method175(i + 1, i_233_ + 1));
		int i_235_ = (Region.method175(i - 1, i_233_) + Region.method175(i + 1, i_233_)
				+ Region.method175(i, i_233_ - 1) + Region.method175(i, i_233_ + 1));
		int i_236_ = Region.method175(i, i_233_);
		return i_234_ / 16 + i_235_ / 8 + i_236_ / 4;
	}

	public void method179(int i, CollisionMap[] class46s, int i_237_, int i_238_, Scene class22, byte[] is) {
		if (i_238_ < 0) {
			Buffer class50_sub1_sub2 = new Buffer(true, is);
			int i_239_ = -1;
			for (;;) {
				int i_240_ = class50_sub1_sub2.method535();
				if (i_240_ == 0) {
					break;
				}
				i_239_ += i_240_;
				int i_241_ = 0;
				for (;;) {
					int i_242_ = class50_sub1_sub2.method535();
					if (i_242_ == 0) {
						break;
					}
					i_241_ += i_242_ - 1;
					int i_243_ = i_241_ & 0x3f;
					int i_244_ = i_241_ >> 6 & 0x3f;
					int i_245_ = i_241_ >> 12;
					int i_246_ = class50_sub1_sub2.method521();
					int i_247_ = i_246_ >> 2;
					int i_248_ = i_246_ & 0x3;
					int i_249_ = i_244_ + i_237_;
					int i_250_ = i_243_ + i;
					if (i_249_ > 0 && i_250_ > 0 && i_249_ < 103 && i_250_ < 103) {
						int i_251_ = i_245_;
						if ((aByteArrayArrayArray138[1][i_249_][i_250_] & 0x2) == 2) {
							i_251_--;
						}
						CollisionMap class46 = null;
						if (i_251_ >= 0) {
							class46 = class46s[i_251_];
						}
						method173(class22, class46, i_250_, i_245_, i_249_, aByte139, i_248_, i_247_, i_239_);
					}
				}
			}
		}
	}

	public void method180(int i, int i_252_, int i_253_, int i_254_, int i_255_) {
		if (i_254_ < 0) {
			for (int i_256_ = i_252_; i_256_ <= i_252_ + i_253_; i_256_++) {
				for (int i_257_ = i; i_257_ <= i + i_255_; i_257_++) {
					if (i_257_ >= 0 && i_257_ < anInt151 && i_256_ >= 0 && i_256_ < anInt152) {
						aByteArrayArrayArray164[0][i_257_][i_256_] = (byte) 127;
						if (i_257_ == i && i_257_ > 0) {
							anIntArrayArrayArray149[0][i_257_][i_256_] = anIntArrayArrayArray149[0][i_257_ - 1][i_256_];
						}
						if (i_257_ == i + i_255_ && i_257_ < anInt151 - 1) {
							anIntArrayArrayArray149[0][i_257_][i_256_] = anIntArrayArrayArray149[0][i_257_ + 1][i_256_];
						}
						if (i_256_ == i_252_ && i_256_ > 0) {
							anIntArrayArrayArray149[0][i_257_][i_256_] = anIntArrayArrayArray149[0][i_257_][i_256_ - 1];
						}
						if (i_256_ == i_252_ + i_253_ && i_256_ < anInt152 - 1) {
							anIntArrayArrayArray149[0][i_257_][i_256_] = anIntArrayArrayArray149[0][i_257_][i_256_ + 1];
						}
					}
				}
			}
		}
	}

	public static boolean method181(int i, int i_258_, byte[] is, int i_259_) {
		boolean bool = true;
		Buffer class50_sub1_sub2 = new Buffer(true, is);
		if (i_259_ != 24515) {
			throw new NullPointerException();
		}
		int i_260_ = -1;
		for (;;) {
			int i_261_ = class50_sub1_sub2.method535();
			if (i_261_ == 0) {
				break;
			}
			i_260_ += i_261_;
			int i_262_ = 0;
			boolean bool_263_ = false;
			for (;;) {
				if (bool_263_) {
					int i_264_ = class50_sub1_sub2.method535();
					if (i_264_ == 0) {
						break;
					}
					class50_sub1_sub2.method521();
				} else {
					int i_265_ = class50_sub1_sub2.method535();
					if (i_265_ == 0) {
						break;
					}
					i_262_ += i_265_ - 1;
					int i_266_ = i_262_ & 0x3f;
					int i_267_ = i_262_ >> 6 & 0x3f;
					int i_268_ = class50_sub1_sub2.method521() >> 2;
					int i_269_ = i_267_ + i;
					int i_270_ = i_266_ + i_258_;
					if (i_269_ > 0 && i_270_ > 0 && i_269_ < 103 && i_270_ < 103) {
						GameObjectDefinition class47 = GameObjectDefinition.method423(i_260_);
						if (i_268_ != 22 || !Region.aBoolean169 || class47.aBoolean759 || class47.aBoolean765) {
							bool &= class47.method428(-321);
							bool_263_ = true;
						}
					}
				}
			}
		}
		return bool;
	}

	public int method182(int i, int i_271_) {
		if (i == -2) {
			return 12345678;
		}
		if (i == -1) {
			if (i_271_ < 0) {
				i_271_ = 0;
			} else if (i_271_ > 127) {
				i_271_ = 127;
			}
			i_271_ = 127 - i_271_;
			return i_271_;
		}
		i_271_ = i_271_ * (i & 0x7f) / 128;
		if (i_271_ < 2) {
			i_271_ = 2;
		} else if (i_271_ > 126) {
			i_271_ = 126;
		}
		return (i & 0xff80) + i_271_;
	}

	public void method183(int i, byte i_272_, int i_273_, Buffer class50_sub1_sub2, int i_274_, int i_275_, int i_276_,
			int i_277_) {
		if (i_272_ != -61) {
			aBoolean140 = !aBoolean140;
		}
		if (i_275_ >= 0 && i_275_ < 104 && i_277_ >= 0 && i_277_ < 104) {
			aByteArrayArrayArray138[i_276_][i_275_][i_277_] = (byte) 0;
			for (;;) {
				int i_278_ = class50_sub1_sub2.method521();
				if (i_278_ == 0) {
					if (i_276_ == 0) {
						anIntArrayArrayArray149[0][i_275_][i_277_] = -Region.method184(932731 + i_275_ + i, 556238
								+ i_277_ + i_273_) * 8;
					} else {
						anIntArrayArrayArray149[i_276_][i_275_][i_277_] = (anIntArrayArrayArray149[i_276_ - 1][i_275_][i_277_] - 240);
						break;
					}
					break;
				}
				if (i_278_ == 1) {
					int i_279_ = class50_sub1_sub2.method521();
					if (i_279_ == 1) {
						i_279_ = 0;
					}
					if (i_276_ == 0) {
						anIntArrayArrayArray149[0][i_275_][i_277_] = -i_279_ * 8;
					} else {
						anIntArrayArrayArray149[i_276_][i_275_][i_277_] = (anIntArrayArrayArray149[i_276_ - 1][i_275_][i_277_] - i_279_ * 8);
						break;
					}
					break;
				}
				if (i_278_ <= 49) {
					aByteArrayArrayArray155[i_276_][i_275_][i_277_] = class50_sub1_sub2.method522();
					aByteArrayArrayArray153[i_276_][i_275_][i_277_] = (byte) ((i_278_ - 2) / 4);
					aByteArrayArrayArray142[i_276_][i_275_][i_277_] = (byte) (i_278_ - 2 + i_274_ & 0x3);
				} else if (i_278_ <= 81) {
					aByteArrayArrayArray138[i_276_][i_275_][i_277_] = (byte) (i_278_ - 49);
				} else {
					aByteArrayArrayArray159[i_276_][i_275_][i_277_] = (byte) (i_278_ - 81);
				}
			}
		} else {
			for (;;) {
				int i_280_ = class50_sub1_sub2.method521();
				if (i_280_ == 0) {
					break;
				}
				if (i_280_ == 1) {
					class50_sub1_sub2.method521();
					break;
				}
				if (i_280_ <= 49) {
					class50_sub1_sub2.method521();
				}
			}
		}
	}

	public static int method184(int i, int i_281_) {
		int i_282_ = (Region.method163(i + 45365, i_281_ + 91923, 4) - 128
				+ (Region.method163(i + 10294, i_281_ + 37821, 2) - 128 >> 1) + (Region.method163(i, i_281_, 1) - 128 >> 2));
		i_282_ = (int) (i_282_ * 0.3) + 35;
		if (i_282_ < 10) {
			i_282_ = 10;
		} else if (i_282_ > 60) {
			i_282_ = 60;
		}
		return i_282_;
	}
}
