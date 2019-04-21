package org.waterpicker.serverlistv2;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin
  extends JavaPlugin
{
  private List<String> message;
  private Config config;
  
  public void onDisable() {}
  
  public void onEnable()
  {
    this.config = new Config(this);
    this.message = this.config.getConfig().getStringList("Messages");
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if ((sender instanceof Player))
    {
      Player player = (Player)sender;
      if ((cmd.getName().equalsIgnoreCase("list")) || (cmd.getName().equalsIgnoreCase("who")) || (cmd.getName().equalsIgnoreCase("online"))) {
        for (String i : this.message) {
          player.sendMessage(ChatColor.translateAlternateColorCodes('&', parse(i)));
        }
      }
      if ((cmd.getName().equalsIgnoreCase("serverlist")) && 
        (args[0].equalsIgnoreCase("reload")))
      {
        this.config.reloadConfig();
        this.message = this.config.getConfig().getStringList("Messages");
      }
      return true;
    }
    return false;
  }
  
  public String parse(String string)
  {
    String send = string;
    
    String temp = send.replace("<playeron>", "" + Bukkit.getOnlinePlayers().length);
    if (temp != null) {
      send = temp;
    }
    temp = send.replace("<playermax>", "" + Bukkit.getMaxPlayers());
    if (temp != null) {
      send = temp;
    }
    temp = send.replace("<staffon>", "" + getStaff());
    if (temp != null) {
      send = temp;
    }
    temp = send.replace("<stafflist>", "" + getStaffOnline());
    if (temp != null) {
      send = temp;
    }
    return send;
  }
  
  public int getStaff()
  {
    Player[] player = Bukkit.getOnlinePlayers();
    int online = 0;
    for (Player p : player) {
      if (p.hasPermission("severelist.staff")) {
        online++;
      }
    }
    return online;
  }
  
  public String getStaffOnline()
  {
    boolean first = true;
    String temp = "";
    Player[] player = Bukkit.getOnlinePlayers();
    for (Player p : player) {
      if (p.hasPermission("severelist.staff")) {
        if (first)
        {
          temp = temp + p.getPlayerListName();
          first = false;
        }
        else
        {
          temp = temp + ChatColor.RED + p.getPlayerListName();
        }
        temp += ", ";
      }
    }
    temp = temp.substring(0, temp.length() - 2);
    return temp;
  }
}
