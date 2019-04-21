package me.rabrg.duel.listener;

import me.rabrg.duel.Plugin;
import me.rabrg.duel.arena.ArenaManager;
import me.rabrg.duel.kit.Kit;
import me.rabrg.duel.kit.Kits;
import me.rabrg.duel.player.DuelPlayer;
import me.rabrg.duel.player.DuelPlayerManager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public final class ChallengeListener implements Listener {

	@SuppressWarnings("unused")
	private final Plugin plugin;

	public ChallengeListener(final Plugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerInteractEntityEvent(final PlayerInteractEntityEvent event) {
		System.out.println("test");
		// TODO: check if in arena
		if (event.getRightClicked() instanceof Player) {
			final DuelPlayer challenger = DuelPlayerManager.getPlayer(event.getPlayer());
			final DuelPlayer challenged = DuelPlayerManager.getPlayer((Player) event.getRightClicked());
			final Kit kit = Kits.getKits(challenger.getPlayer().getItemInHand().getType());
			
			if (kit !=null) {
				challenger.setChallenged(challenged);
				challenger.setChallengedWith(kit);
				challenger.getPlayer().sendMessage("[Duel] You have challenged player " + challenged.getPlayer().getName() + ".");
				
				challenged.setChallengedBy(challenger);
				challenged.setChallengedByWith(kit);
				challenged.getPlayer().sendMessage("[Duel] You have been challenged by player " + challenger.getPlayer().getName() + ".");
				
				if (challenger.equals(challenged.getChallenged()) && challenger.getChallengedWith().equals(challenged.getChallengedWith())) {
					ArenaManager.startArena(challenged, challenger);
				}
			}
		}
	}
}
