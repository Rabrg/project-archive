package me.rabrg.duel.kit;

import java.util.HashMap;
import java.util.Map;

import me.rabrg.duel.kit.impl.MeleeKit;

import org.bukkit.Material;

public enum Kits {

	STANDARD_KIT(Material.DIAMOND_SWORD, new MeleeKit());

	private static final Map<Material, Kit> kits = new HashMap<Material, Kit>();

	static {
		for (Kits kit : Kits.values()) {
			kits.put(kit.material, kit.kit);
		}
	}

	public static Kit getKits(final Material material) {
		return kits.get(material);
	}

	private final Material material;

	private final Kit kit;

	private Kits(final Material material, final Kit kit) {
		this.material = material;
		this.kit = kit;
	}

	public Material getMaterial() {
		return material;
	}

	public Kit getKit() {
		return kit;
	}
}
