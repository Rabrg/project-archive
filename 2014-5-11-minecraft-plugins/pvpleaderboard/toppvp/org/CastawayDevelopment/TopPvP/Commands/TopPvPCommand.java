package org.CastawayDevelopment.TopPvP.Commands;

import org.bukkit.command.CommandSender;

/**
 *
 * @author DarkSeraphim
 */
public abstract class TopPvPCommand 
{

    private final boolean needsPlayer;
    
    protected TopPvPCommand(boolean needsPlayer)
    {
        this.needsPlayer = needsPlayer;
    }

    public boolean needsPlayer()
    {
        return this.needsPlayer;
    }
    
    public abstract void execute(CommandSender sender, String[] args, boolean isAlias);
}
