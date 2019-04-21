package me.rabrg.caps;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin implements Listener {

	private static final double PERCENTAGE = 75;

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onAsyncPlayerChatEvent(final AsyncPlayerChatEvent event) {
		double upperCaseCharacters = 0;
		double totalCharacters = 0;
		
		for (char c : event.getMessage().toCharArray()) {
			if (Character.isUpperCase(c)) {
				upperCaseCharacters++;
			}
			totalCharacters++;
		}
		
		System.out.println(upperCaseCharacters + " " + totalCharacters);
		
		double percentage = (upperCaseCharacters / totalCharacters) * 100;
		
		if (percentage >= PERCENTAGE) {
			getServer().dispatchCommand(getServer().getConsoleSender(), "tempmute " + event.getPlayer().getName() + " 5m");
			event.setCancelled(true);
			event.getPlayer().sendMessage("You have been temporarily muted for talking in too much caps.");
		}
	}
}
