package me.rabrg.ares.player;

import me.rabrg.ares.kit.Kit;
import me.rabrg.ares.kit.KitEffect;

import org.bukkit.entity.Player;

public final class AresPlayer {

	private final Player player;

	private Kit kit;

	public AresPlayer(final Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public Kit getKit() {
		return kit;
	}

	public void setKit(Kit kit) {
		this.kit = kit;
	}

	public boolean hasKitEffect(final KitEffect kitEffect) {
		for (KitEffect kitEffect_ : kit.getKitEffects()) {
			if (kitEffect_ == kitEffect) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return player.getName();
	}
}
