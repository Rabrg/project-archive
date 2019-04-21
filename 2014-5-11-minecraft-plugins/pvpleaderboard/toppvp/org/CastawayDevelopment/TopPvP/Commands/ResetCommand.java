package org.CastawayDevelopment.TopPvP.Commands;

import org.CastawayDevelopment.TopPvP.Managers.PlayerClass;
import org.CastawayDevelopment.TopPvP.TopPvP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author DarkSeraphim
 */
public class ResetCommand extends TopPvPCommand
{

    private final TopPvP plugin;
    
    public ResetCommand(TopPvP plugin)
    {
        super(false);
        this.plugin = plugin;
    }
    
    @Override
    public void execute(CommandSender sender, String[] args, boolean isAlias)
    {
        if(args.length < 1)
        {
            sender.sendMessage(ChatColor.RED+"The correct usage is /toppvp reset <a[ll]|p[layer]> [playername]");
            return;
        }
        
        Reset set;
        
        try
        {
            if(args[0].isEmpty() || (set = Reset.valueOf(args[0].substring(0,1).toUpperCase())) == null)
            {
                throw new IllegalArgumentException();
            }
        }
        catch(IllegalArgumentException ex)
        {
            sender.sendMessage("Unknown target: use a[ll] or p[layer].");
            return;
        }
        
        if(set == Reset.P && args.length != 2)
        {
            sender.sendMessage(ChatColor.RED+"The correct usage is /toppvp reset <a[ll]|p[layer]> [playername]");
            return;
        }
        switch(set)
        {
            case P:
                Player other = Bukkit.getPlayerExact(args[1]);
                if(other == null)
                {
                    sender.sendMessage(ChatColor.RED+"That player is not online.");
                    return;
                }
                PlayerClass pcOther = this.plugin.getPlayerManager().getPlayer(other);
                pcOther.getKills().setValue(0);
                pcOther.getDeaths().setValue(0);
                pcOther.resetBounty();
                pcOther.getLastKills().clear();
                pcOther.update(this.plugin);
                break;
            case A:
                this.plugin.getDatabaseManager().resetAll();
                PlayerClass pc;
                for(Player player : Bukkit.getOnlinePlayers())
                {
                    pc = this.plugin.getPlayerManager().getPlayer(player);
                    pc.getKills().setValue(0);
                    pc.getDeaths().setValue(0);
                    pc.resetBounty();
                    pc.getLastKills().clear();
                    if(pc != null) pc.update(true, this.plugin);
                }
                break;
        }
        if(set == Reset.P)
            sender.sendMessage(ChatColor.GREEN+"Player updated!");
        else
            sender.sendMessage(ChatColor.GREEN+"All stats reset!");
    }
    
    private enum Reset
    {
        P,
        A
    }

}
