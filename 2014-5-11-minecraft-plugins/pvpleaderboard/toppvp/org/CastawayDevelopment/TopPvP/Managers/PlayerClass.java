package org.CastawayDevelopment.TopPvP.Managers;

import java.util.LinkedHashSet;
import java.util.Map;
import org.CastawayDevelopment.TopPvP.TopPvP;
import org.bukkit.entity.Player;

/**
 *
 * @author DarkSeraphim
 */
public class PlayerClass extends AbstractPlayerClass
{
    
    public class Incrementable
    {
        private int value;
        
        Incrementable()
        {
            this(0);
        }
        
        Incrementable(int def)
        {
            this.value = def;
        }
        
        public int getValue()
        {
            return this.value;
        }
        
        public void increment()
        {
            this.value++;
        }
        
        public void setValue(int i)
        {
            this.value = i;
        }
    }
    
    private final int id;
    
    private Incrementable kills;
    
    private Incrementable deaths;
    
    private final LinkedHashSet<String> lastKills = new LinkedHashSet<String>();
    
    private int bounty = 0;
    
    private String bountyIssuer;
    
    protected PlayerClass(Player player, TopPvP plugin)
    {
        super(player);
        Map<String, Object> data = plugin.getDatabaseManager().getPlayerData(player);
        this.id = (Integer) data.get("id");
        this.kills = new Incrementable((Integer) data.get("kills"));
        this.deaths = new Incrementable((Integer) data.get("deaths"));
        this.bounty = (Integer) data.get("bounty");
        this.bountyIssuer = (String) data.get("bountyissuer");
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public void sendMessage(String message, Object...params)
    {
        super.sendMessage(String.format(message, params));
    }
    
    public Incrementable getKills()
    {
        return this.kills;
    }
    
    public Incrementable getDeaths()
    {
        return this.deaths;
    }
    
    public float getKillDeathRatio()
    {
        if(this.deaths.getValue() == 0) return Float.POSITIVE_INFINITY;
        return (float)this.kills.getValue()/(float)this.deaths.getValue();
    }
    
    public String getLastKillerName()
    {
        int size = getLastKills().size();
        if(size < 1) return "";
        return getLastKills().toArray(new String[size])[--size];
    }
    
    public LinkedHashSet<String> getLastKills()
    {
        return this.lastKills;
    }
    
    public void update(TopPvP plugin)
    {
        update(false, plugin);
    }
    
    public void update(boolean onlyScoreboard, TopPvP plugin)
    {
        if(!onlyScoreboard)
            plugin.getDatabaseManager().updatePlayer(this);
        plugin.getScoreboardManager().updatePlayer(this);
    }
    
    public boolean setBounty(int b)
    {
        if(b <= bounty)
        {
            return false;
        }
        this.bounty = b;
        return true;
    }
    
    public int getBounty()
    {
        return this.bounty;
    }
    
    public void setBountyIssuer(String name)
    {
        this.bountyIssuer = name;
    }
    
    public boolean hasBountyIssued()
    {
        return this.bountyIssuer != null;
    }
    
    public String getBountyIssuer()
    {
        return this.bountyIssuer;
    }
    
    public void resetBounty()
    {
        this.bounty = 0;
        this.bountyIssuer = null;
    }
}
