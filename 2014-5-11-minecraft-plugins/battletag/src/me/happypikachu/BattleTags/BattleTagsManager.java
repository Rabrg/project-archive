package me.happypikachu.BattleTags;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.kitteh.tag.PlayerReceiveNameTagEvent;

import com.massivecraft.factions.Conf;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;

public class BattleTagsManager implements Listener {
	private BattleTags plugin;
    public BattleTagsManager(BattleTags plugin) {
            this.plugin = plugin;
    }
    
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onNameTag(PlayerReceiveNameTagEvent event) {
		//Factions by Brettflan
		if (plugin.getServer().getPluginManager().isPluginEnabled("Factions")) {
			if (plugin.getConfig().getBoolean("Factions." + event.getNamedPlayer().getWorld().getName())) {
				Faction pFaction = FPlayers.i.get(event.getPlayer()).getFaction();
				Faction npFaction = FPlayers.i.get(event.getNamedPlayer()).getFaction();
				if (plugin.getServer().getPluginManager().getPlugin("Factions").getDescription().getVersion().startsWith("1.6")) {
					if (pFaction.getRelationTo(npFaction).isAlly()) {
						event.setTag(Conf.colorAlly + event.getNamedPlayer().getName());
					} else if (pFaction.getRelationTo(npFaction).isEnemy()) {
						event.setTag(Conf.colorEnemy + event.getNamedPlayer().getName());
					} else if (pFaction.getRelationTo(npFaction).isMember()) {
						event.setTag(Conf.colorMember + event.getNamedPlayer().getName());
					} else if (pFaction.getRelationTo(npFaction).isNeutral()) {
						event.setTag(Conf.colorNeutral + event.getNamedPlayer().getName());
					}
				} else {
					plugin.getLogger().warning("Hooked into unexpected Factions version (" + plugin.getServer().getPluginManager().getPlugin("Factions").getDescription().getVersion() + ")." +
							" Try a different version of Factions or BattleTags? Disable support in all worlds to remove this message.");
				}
			}
		}
	}
}