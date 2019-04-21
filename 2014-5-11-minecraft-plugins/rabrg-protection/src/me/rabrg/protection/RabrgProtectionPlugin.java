package me.rabrg.protection;

import java.util.ArrayList;
import java.util.List;

import me.rabrg.protection.listener.EntityEventListener;
import me.rabrg.protection.listener.PlayerEventListener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public final class RabrgProtectionPlugin extends JavaPlugin {
	private Vector protectedRegionCenter;
	private Vector protectedRegionMinimum;
	private Vector protectedRegionMaximum;
	private final List<Player> protectedPlayers = new ArrayList<>();

	@Override
	public void onEnable() {
		saveDefaultConfig();

		protectedRegionCenter = getConfig().getVector("protected-region-center");
		protectedRegionMinimum = getConfig().getVector("protected-region-minimum");
		protectedRegionMaximum = getConfig().getVector("protected-region-maximum");

		getServer().getPluginManager().registerEvents(new PlayerEventListener(this), this);
		getServer().getPluginManager().registerEvents(new EntityEventListener(this), this);
		for (final Player player : getServer().getOnlinePlayers()) {
			if (isInsideProtection(player.getLocation().toVector())) {
				protectedPlayers.add(player);
			}
		}
	}

	@Override
	public void onDisable() {
		protectedPlayers.clear();
	}

	public void pushEntityOutsideProtectedSphere(final Entity entity) {
		final Vector vector = entity.getLocation().toVector().subtract(protectedRegionCenter).normalize();
		entity.setVelocity(vector.multiply(0.5D));
		entity.teleport(entity.getLocation().add(vector), TeleportCause.PLUGIN);
	}

	public boolean isInsideProtection(final Vector vector) {
		return vector.isInAABB(protectedRegionMinimum, protectedRegionMaximum);
	}

	public boolean isPlayerProtected(final Player player) {
		return protectedPlayers.contains(player);
	}

	public void addProtectedPlayer(final Player player) {
		protectedPlayers.add(player);
	}

	public void removeProtectedPlayer(final Player player) {
		protectedPlayers.remove(player);
	}
}
