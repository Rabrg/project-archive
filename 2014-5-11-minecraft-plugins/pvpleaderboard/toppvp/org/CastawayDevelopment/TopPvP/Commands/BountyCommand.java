package org.CastawayDevelopment.TopPvP.Commands;

import net.milkbowl.vault.economy.EconomyResponse;
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
public class BountyCommand extends TopPvPCommand
{
    private final TopPvP plugin;
    
    public BountyCommand(TopPvP plugin)
    {
        super(true);
        this.plugin = plugin;
    }
    
    @Override
    public void execute(CommandSender sender, String[] args, boolean isAlias)
    {
        if(!this.plugin.economy.isEnabled())
        {
            sender.sendMessage(ChatColor.RED+"The economy is disabled");
            return;
        }
        Player player = (Player) sender;
        if(args.length != 2)
        {
            player.sendMessage(ChatColor.RED+"The correct usage is /<command> <player> <bounty>".replace("<command>", isAlias ? "bounty" : "toppvp bounty"));
            return;
        }
        Player other = Bukkit.getPlayerExact(args[0]);
        if(other == null)
        {
            player.sendMessage(ChatColor.RED+"That player is not online.");
            return;
        }
        int bounty = Integer.MIN_VALUE;
        try
        {
            bounty = Integer.parseInt(args[1]);
        }
        catch(NumberFormatException ex)
        {
            player.sendMessage(ChatColor.RED+"Invalid bounty value.");
            return;
        }
        
        if(bounty < 0)
        {
            player.sendMessage(ChatColor.RED+"The bounty must be a positive amount.");
            return;
        }
        
        PlayerClass pcOther = this.plugin.getPlayerManager().getPlayer(other);
        if(bounty <= pcOther.getBounty())
        {
            player.sendMessage(ChatColor.RED+String.format("The bounty must be higher than the previous bounty: %d", pcOther.getBounty()));
            return;
        }
        
        if(!this.plugin.economy.has(player.getName(), bounty))
        {
            player.sendMessage(ChatColor.RED+"You do not have sufficient funds to do that.");
            return;
        }
        
        EconomyResponse er = this.plugin.economy.withdrawPlayer(player.getName(), bounty);
        if(er.transactionSuccess())
        {
            if(pcOther.hasBountyIssued())
            {
                this.plugin.economy.depositPlayer(pcOther.getBountyIssuer(), pcOther.getBounty());
                Player lastIssuer = Bukkit.getPlayerExact(pcOther.getBountyIssuer());
                if(lastIssuer != null)
                    lastIssuer.sendMessage(ChatColor.GOLD+String.format("%s offered a higher bounty for %s: %d", player.getName(), pcOther.getName(), bounty));
            }
            pcOther.setBounty(bounty);
            pcOther.setBountyIssuer(player.getName());
            pcOther.update(this.plugin);
            player.sendMessage(ChatColor.GREEN+String.format("Bounty placed on %s's head", pcOther.getName()));
        }
    }

}
