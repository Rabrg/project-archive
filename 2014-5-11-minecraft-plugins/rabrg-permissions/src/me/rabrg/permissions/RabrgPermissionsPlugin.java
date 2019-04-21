package me.rabrg.permissions;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;

public final class RabrgPermissionsPlugin extends JavaPlugin {

	private Map<UUID, PermissionAttachment> permissions;

	@Override
	public void onEnable() {
		permissions = new HashMap<UUID, PermissionAttachment>();
	}

	public void registerPlayer(final Player player) {
		final PermissionAttachment attachment = player.addAttachment(this);
		permissions.put(player.getUniqueId(), attachment);
	}
}
