package net.severepvp.loot;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SeverePvPLootRunnable extends BukkitRunnable {

	private final SeverePvPLoot plugin;
	private final Player dead;
	private final Player killer;

	public SeverePvPLootRunnable(final SeverePvPLoot plugin, final Player dead, final Player killer) {
		this.plugin = plugin;
		this.dead = dead;
		this.killer = killer;
	}

	@Override
	public void run() {
		killer.sendMessage("§4[§aSevereLoot§4] §bYour loot from §c" + dead.getName() + " §c§l§nCAN§b now be picked up by others!");
		plugin.allowPickup(killer);
	}

}
