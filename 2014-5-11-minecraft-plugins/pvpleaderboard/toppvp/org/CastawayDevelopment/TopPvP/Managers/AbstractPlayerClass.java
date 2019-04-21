package org.CastawayDevelopment.TopPvP.Managers;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

/**
 * Do not use any of the Player methods when the PlayerQuitEvent has been processed
 * 
 * @author DarkSeraphim
 */
public abstract class AbstractPlayerClass
{
    
    private final Player player;
    
    private boolean valid = true;
    
    protected AbstractPlayerClass(Player player)
    {
        this.player = player;
    }
    
    protected void quit()
    {
        this.valid = false;
    }
    
    public boolean isValid()
    {
        return this.valid && this.player.isValid();
    }
    
    public String getName()
    {
        return this.player.getName();
    }
    
    public void sendMessage(String message)
    {
        this.player.sendMessage(message);
    }
    
    public Scoreboard getScoreboard()
    {
        return this.player.getScoreboard();
    }
    
    public void setScoreboard(Scoreboard scoreboard)    
    {
        this.player.setScoreboard(scoreboard);
    }
    
}
