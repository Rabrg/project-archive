package denniss17.playerHealth;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

public class TagAPIListener implements Listener {

	private final PlayerHealth plugin;

	public TagAPIListener(final PlayerHealth plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onNameTag(final PlayerReceiveNameTagEvent event) {
		// A new tag is send -> add it to list
		plugin.tags.get(event.getNamedPlayer()).add(event.getTag());
	}
}
