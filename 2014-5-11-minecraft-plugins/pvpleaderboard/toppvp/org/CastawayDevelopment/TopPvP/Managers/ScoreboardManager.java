/*
 * This file is part of TopPvP, PvP leader stats plugin!.
 * TopPvP is licensed under GNU General Public License v3.
 * Copyright (C) 2013 The Castaway Development Team
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.CastawayDevelopment.TopPvP.Managers;

import org.CastawayDevelopment.TopPvP.TopPvP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardManager
{

    private TopPvP plugin;
    //private ScoreboardAPI api;
    //private Scoreboard kills;
    //private Scoreboard deaths;

    public ScoreboardManager(TopPvP plugin)
    {
        this.plugin = plugin;
    }
    
    public void onPlayerJoin(final PlayerClass player)
    {
        Scoreboard sb = player.getScoreboard();
        if(sb == Bukkit.getScoreboardManager().getMainScoreboard())
        {
            sb = Bukkit.getScoreboardManager().getNewScoreboard();
        }
        
        final Scoreboard assign = sb;
        
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                while(!player.isValid())
                {
                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch(InterruptedException ex)
                    {
                        // Swallow the exception
                    }
                }
                
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        player.setScoreboard(assign);
                        ScoreboardManager.this.registerPlayer(player);
                    }
                }.runTask(ScoreboardManager.this.plugin);
            }
        }.runTaskAsynchronously(this.plugin);
    }
    
    // Do not call this manually please, let the onPlayerJoin(Player) do it for you :3
    private void registerPlayer(PlayerClass player)
    {
        Scoreboard sb = player.getScoreboard();
        Objective stats = sb.registerNewObjective("toppvp_stats", "dummy");
        stats.setDisplayName(ChatColor.GOLD+""+ChatColor.BOLD+""+ChatColor.ITALIC+"Stats");
        Score kills = stats.getScore(getScore("Kills"));
        Score deaths = stats.getScore(getScore("Deaths"));
        kills.setScore(player.getKills().getValue());
        deaths.setScore(player.getDeaths().getValue());
        
        // This is basically the update method (iirc)
        stats.setDisplaySlot(DisplaySlot.SIDEBAR);
    }
    
    public void updatePlayer(PlayerClass player)
    {
        Scoreboard sb = player.getScoreboard();
        Objective stats = sb.getObjective("toppvp_stats");
        Score kills = stats.getScore(getScore("Kills"));
        Score deaths = stats.getScore(getScore("Deaths"));
        kills.setScore(player.getKills().getValue());
        deaths.setScore(player.getDeaths().getValue());
    }
    
    public static OfflinePlayer getScore(String name)
    {
        return Bukkit.getOfflinePlayer(name);
    }
}
