package com.gmail.woodyc40.arenapvp;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.List;

public class GameListener implements Listener{

    static List<String> players = new ArrayList<String>();
    static ArenaPVP plugin;

    public GameListener(ArenaPVP plugin){
        GameListener.plugin = plugin;
    }

    @EventHandler
    public void onDamange(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player && players.contains(((Player) e.getEntity()).getName())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        if(ArenaManager.getManager().isInGame(e.getEntity())){
            ArenaManager.getManager().removePlayer(e.getEntity());
        }
    }

    public static void add(Player p){
        final String name = p.getName();
        players.add(name);

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
            @Override
            public void run(){
                players.remove(name);
            }
        }, 100L);
    }
}
