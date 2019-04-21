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
public class SetCommand extends TopPvPCommand
{

    private final TopPvP plugin;
    
    public SetCommand(TopPvP plugin)
    {
        super(false);
        this.plugin = plugin;
    }
    
    @Override
    public void execute(CommandSender sender, String[] args, boolean isAlias)
    {
        if(args.length != 3)
        {
            sender.sendMessage(ChatColor.RED+"The correct usage is /toppvp set <k[ills]|d[eaths]> <player> <amount>");
            return;
        }
        
        Set set;
        
        try
        {
            if(args[0].isEmpty() || (set = Set.valueOf(args[0].substring(0,1).toUpperCase())) == null)
            {
                throw new IllegalArgumentException();
            }
        }
        catch(IllegalArgumentException ex)
        {
            sender.sendMessage("Unknown value: use k[ills] or d[deaths].");
            return;
        }
        
        Player other = Bukkit.getPlayerExact(args[1]);
        if(other == null)
        {
            sender.sendMessage(ChatColor.RED+"That player is not online.");
            return;
        }
        
        int val;
        try
        {
            val = Integer.parseInt(args[2]);
        }
        catch(NumberFormatException ex)
        {
            sender.sendMessage(ChatColor.RED+"Invalid amount.");
            return;
        }
        if(val < 0)
        {
            sender.sendMessage(ChatColor.RED+"Amount cannot be negative.");
            return;
        }
        
        PlayerClass pcOther = this.plugin.getPlayerManager().getPlayer(other);
        boolean update = true;
        switch(set)
        {
            case K:
                if(pcOther.getKills().getValue() == val)
                    update = false;
                pcOther.getKills().setValue(val);
                break;
            case D:
                if(pcOther.getKills().getValue() == val)
                    update = false;
                pcOther.getDeaths().setValue(val);
                break;
        }
        
        if(update)
        {
            pcOther.update(this.plugin);
        }
        sender.sendMessage(ChatColor.GREEN+"Player updated!");
    }
    
    private enum Set
    {
        K,
        D
    }

}
