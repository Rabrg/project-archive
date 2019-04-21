package com.gmail.woodyc40.arenapvp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ArenaPVP extends JavaPlugin{

    @Override
    public void onEnable(){
        if(!getDataFolder().exists())
            getDataFolder().mkdir();

        if(getConfig() == null)
            saveDefaultConfig();

        new ArenaManager(this);
        ArenaManager.getManager().loadGames();

        getServer().getPluginManager().registerEvents(new GameListener(this), this);
    }

    @Override
    public void onDisable(){
        saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]){
        if(!(sender instanceof Player)){
            sender.sendMessage("Whoa there buddy, only players may execute this!");
            return true;
        }

        Player p = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("create")){
            ArenaManager.getManager().createArena(p.getLocation());
            p.sendMessage("Created arena at " + p.getLocation().toString());

            return true;
        }
        if(cmd.getName().equalsIgnoreCase("join")){
            if(args.length != 1){
                p.sendMessage("Insuffcient arguments!");
                return true;
            }
            int num = 0;
            try{
                num = Integer.parseInt(args[0]);
            }catch(NumberFormatException e){
                p.sendMessage("Invalid arena ID");
            }
            ArenaManager.getManager().addPlayer(p, num);

            return true;
        }
        if(cmd.getName().equalsIgnoreCase("leave")){
            ArenaManager.getManager().removePlayer(p);
            p.sendMessage("You have left the arena!");

            return true;
        }
        if(cmd.getName().equalsIgnoreCase("remove")){
            if(args.length != 1){
                p.sendMessage("Insuffcient arguments!");
                return true;
            }
            int num = 0;
            try{
                num = Integer.parseInt(args[0]);
            }catch(NumberFormatException e){
                p.sendMessage("Invalid arena ID");
            }
            ArenaManager.getManager().removeArena(num);

            return true;
        }

        return false;
    }

}
