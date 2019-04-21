package com.runescape.media.renderable;

import com.runescape.Game;
import com.runescape.cache.config.VarBit;
import com.runescape.cache.def.GameObjectDefinition;
import com.runescape.cache.media.AnimationSequence;

public class GameObject extends Renderable {
	
	public boolean aBoolean1714;
	public int anInt1715;
	public int anInt1716;
	public int anInt1717;
	public int anInt1718;
	public int anInt1719;
	public int anInt1720;
	public int anInt1721;
	public int anInt1722;
	public static Game aClient1723;
	public AnimationSequence aClass14_1724;
	public int anInt1725;
	public int anInt1726;
	public int anIntArray1727[];
	public int anInt1728;
	public int anInt1729;
	public int anInt1730;
	
	public GameObjectDefinition method603(int i) {
		int j = -1;
		if (i != 0) {
			anInt1728 = 109;
		}
		if (anInt1725 != -1) {
			VarBit class49 = VarBit.aClass49Array824[anInt1725];
			int k = class49.anInt826;
			int l = class49.anInt827;
			int i1 = class49.anInt828;
			int j1 = Game.anIntArray1214[i1 - l];
			j = GameObject.aClient1723.anIntArray1039[k] >> l & j1;
		} else if (anInt1726 != -1) {
			j = GameObject.aClient1723.anIntArray1039[anInt1726];
		}
		if (j < 0 || j >= anIntArray1727.length || anIntArray1727[j] == -1) {
			return null;
		} else {
			return GameObjectDefinition.method423(anIntArray1727[j]);
		}
	}

	public GameObject(int i, int j, int k, int l, int i1, byte byte0, int j1, boolean flag, int k1, int l1) {
		aBoolean1714 = false;
		anInt1720 = j1;
		anInt1721 = i1;
		anInt1722 = l1;
		anInt1715 = k1;
		anInt1716 = l;
		anInt1717 = j;
		anInt1718 = k;
		if (i != -1) {
			aClass14_1724 = AnimationSequence.aClass14Array293[i];
			anInt1730 = 0;
			anInt1729 = Game.anInt1325 - 1;
			if (flag && aClass14_1724.anInt298 != -1) {
				anInt1730 = (int) (Math.random() * aClass14_1724.anInt294);
				anInt1729 -= (int) (Math.random() * aClass14_1724.method205(0, anInt1730));
			}
		}
		GameObjectDefinition class47 = GameObjectDefinition.method423(anInt1720);
		anInt1725 = class47.anInt778;
		anInt1726 = class47.anInt781;
		anIntArray1727 = class47.anIntArray805;
		if (byte0 != 3) {
			anInt1719 = -126;
		}
	}

	@Override
	public Model method561(byte byte0) {
		if (byte0 != 3) {
			aBoolean1714 = !aBoolean1714;
		}
		int i = -1;
		if (aClass14_1724 != null) {
			int j = Game.anInt1325 - anInt1729;
			if (j > 100 && aClass14_1724.anInt298 > 0) {
				j = 100;
			}
			while (j > aClass14_1724.method205(0, anInt1730)) {
				j -= aClass14_1724.method205(0, anInt1730);
				anInt1730++;
				if (anInt1730 < aClass14_1724.anInt294) {
					continue;
				}
				anInt1730 -= aClass14_1724.anInt298;
				if (anInt1730 >= 0 && anInt1730 < aClass14_1724.anInt294) {
					continue;
				}
				aClass14_1724 = null;
				break;
			}
			anInt1729 = Game.anInt1325 - j;
			if (aClass14_1724 != null) {
				i = aClass14_1724.anIntArray295[anInt1730];
			}
		}
		GameObjectDefinition class47;
		if (anIntArray1727 != null) {
			class47 = method603(0);
		} else {
			class47 = GameObjectDefinition.method423(anInt1720);
		}
		if (class47 == null) {
			return null;
		} else {
			Model class50_sub1_sub4_sub4 = class47.method431(anInt1721, anInt1722, anInt1715, anInt1716, anInt1717,
					anInt1718, i);
			return class50_sub1_sub4_sub4;
		}
	}
}
