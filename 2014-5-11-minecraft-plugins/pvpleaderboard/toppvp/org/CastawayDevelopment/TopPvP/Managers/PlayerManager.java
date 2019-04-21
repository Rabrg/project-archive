package org.CastawayDevelopment.TopPvP.Managers;

import java.util.HashMap;
import java.util.Map;
import org.CastawayDevelopment.TopPvP.TopPvP;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author DarkSeraphim
 */
public class PlayerManager
{
    private final TopPvP plugin;
    
    Map<String, PlayerClass> players = new HashMap<String, PlayerClass>();
    
    public PlayerManager(TopPvP plugin)
    {
        this.plugin = plugin;
    }
    
    public void onPlayerJoin(final Player player)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                final PlayerClass pc = new PlayerClass(player, plugin);
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        PlayerManager.this.players.put(pc.getName(), pc);
                        plugin.getScoreboardManager().onPlayerJoin(pc);
                    }
                }.runTask(plugin);
            }
        }.runTaskAsynchronously(plugin);
    }
    
    public PlayerClass getPlayer(Player player)
    {
        return this.getPlayer(player.getName());
    }
    
    public PlayerClass getPlayer(String name)
    {
        return this.players.get(name);
    }
    
    public void onPlayerQuit(Player player)
    {
        PlayerClass pc = this.players.remove(player.getName());
        if(pc != null) pc.quit();
    }

}
