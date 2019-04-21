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

import com.github.DarkSeraphim.sql.Database;
import com.github.DarkSeraphim.sql.MySQL;
import com.github.DarkSeraphim.sql.SQLite;
import com.github.DarkSeraphim.sql.TableBuilder;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import org.CastawayDevelopment.TopPvP.TopPvP;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DatabaseManager
{

    private TopPvP plugin;
    private Database db;
    
    private final String tableName = "toppvp";
    
    private PreparedStatement getplayer;
    private final Object getplayer_lock = new Object();
    private PreparedStatement createplayer;
    private final Object createplayer_lock = new Object();
    private PreparedStatement updateplayer;
    private final Object updateplayer_lock = new Object();
    private PreparedStatement deleteplayer;
    private final Object deleteplayer_lock = new Object();
    private PreparedStatement resetAll;
    private final Object resetAll_lock = new Object();
    private PreparedStatement fetchAllBounty;
    private final Object fetchAllBounty_lock = new Object();

    public Map<String, Object> getPlayerData(Player player)
    {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("id", null);
        data.put("kills", null);
        data.put("deaths", null);
        data.put("lastkillers", null);
        data.put("bounty", null);
        data.put("bountyissuer", null);
        
        if(!Database.synchronizedExecuteQuery(data, getplayer, getplayer_lock, player.getName()))
        {
            createPlayer(player);
            Database.synchronizedExecuteQuery(data, getplayer, getplayer_lock, player.getName());
        }

        if(data.get("bountyissuer") != null && ((String)data.get("bountyissuer")).isEmpty())
        {
            data.put("bountyissuer", null);
        }
        
        return data;
    }

    public void createPlayer(Player player)
    {  
        Database.synchronizedExecuteUpdate(createplayer, createplayer_lock, player.getName());
    }

    // Might convert to a own Player object for cleaner use
    public void updatePlayer(PlayerClass player)
    {
        final int kills = player.getKills().getValue();
        final int deaths = player.getDeaths().getValue();
        final LinkedHashSet<String> lastKills = player.getLastKills();
        StringBuilder listBuilder = new StringBuilder();
        Iterator<String> it = lastKills.iterator();
        String next;
        while(it.hasNext())
        {
            next = it.next();
            if(listBuilder.length() > 0 && it.hasNext()) 
                listBuilder.append(",");
            listBuilder.append(next);
        }
        final String lastKillers = listBuilder.toString();
        final int bounty = player.getBounty();
        final int id = player.getId();
        final String issuer = player.hasBountyIssued() ? player.getBountyIssuer() : "";
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                Database.synchronizedExecuteUpdate(updateplayer, updateplayer_lock, kills, deaths, lastKillers, bounty, issuer, id);
            }
        }.runTaskAsynchronously(plugin);
    }
    
    public void deletePlayer(int id)
    {
        Database.synchronizedExecuteUpdate(deleteplayer, deleteplayer_lock, id);
    }
    
    
    /**
     * Fetches all open bounties. Run this async as it uses SQL :P
     * @return a ordered @code{Map<String, Integer>}
     */
    public Map<String, Integer> getOpenBounties()
    {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        Map<String, Object> template = new HashMap<String, Object>();
        template.put("bounty", null);
        template.put("issuer", null);
        results.add(template);
        if(Database.synchronizedExecuteQuery(results, this.fetchAllBounty, this.fetchAllBounty_lock))
        {
            int bounty;
            String issuer;
            for(Map<String, Object> result : results)
            {
                issuer = (String) result.get("issuer");
                bounty = (Integer) result.get("bounty");
                map.put(issuer, bounty); 
            }
        }
        return map;
    }
    
    /**
     * Resets all players. Run this async as it uses SQL :P
     */
    public void resetAll()
    {
        if(this.plugin.economy.isEnabled())
        {
            Map<String, Integer> openBounties = getOpenBounties();
            for(Map.Entry<String, Integer> issue : openBounties.entrySet())
            {
                this.plugin.economy.depositPlayer(issue.getKey(), issue.getValue());
            }
        }
        Database.synchronizedExecuteUpdate(resetAll, resetAll_lock);
    }

    private void connect() throws SQLException
    {
        if (!this.db.isReady())
        {
            if (!this.db.connect())
            {
                throw new SQLException("Could not connect to the database!");
            }
        }

        boolean exists = this.db.checkTable("toppvp");
        if (!exists)
        {
            // Create it.
            TableBuilder toppvp = new TableBuilder("toppvp");
            toppvp.addColumn("id", "int(255)").addProperty("NOT NULL").addProperty("AUTO_INCREMENT");
            toppvp.addColumn("username", "varchar(16)").addProperty("NOT NULL").addProperty("UNIQUE");
            toppvp.addColumn("kills", "int(24)").addProperty("NOT NULL").addProperty("DEFAULT 0");
            toppvp.addColumn("deaths", "int(24)").addProperty("NOT NULL").addProperty("DEFAULT 0");
            toppvp.addColumn("lastkillers", "varchar(50)").addProperty("NOT NULL").addProperty("DEFAULT ''");
            toppvp.addColumn("bounty", "int(255)").addProperty("NOT NULL").addProperty("DEFAULT 0");
            toppvp.addColumn("bountyissuer", "varchar(16)").addProperty("NOT NULL").addProperty("DEFAULT ''");
            toppvp.setPrimaryKey("id");
            toppvp.createTable(this.db);
        }
        else
        {
            plugin.getLogger().info("Table exists.");
        }
    }
    
    private void prepare()
    {
        if(this.db.isReady())
        {
            this.createplayer = this.db.prepare("INSERT INTO toppvp(`username`) VALUES(?)");
            this.getplayer = this.db.prepare("SELECT * FROM toppvp WHERE `username` = ?");
            this.updateplayer = this.db.prepare("UPDATE toppvp SET kills = ?, deaths = ?, lastkillers = ?, bounty = ?, bountyissuer = ? WHERE id = ?");
            this.deleteplayer = this.db.prepare("DELETE FROM toppvp WHERE id = ?");
            this.fetchAllBounty = this.db.prepare("SELECT bounty, bountyissuer AS issuer FROM toppvp WHERE bountyissuer != '' ORDER BY `bounty` DESC");
            this.resetAll = this.db.prepare("UPDATE toppvp SET kills = 0, deaths = 0, lastkillers = '', bounty = 0, bountyissuer = '' WHERE 1");
        }
    }

    public DatabaseManager(TopPvP plugin)
    {

        this.plugin = plugin;
        if (plugin.getConfig().getBoolean("mysql.enabled", false))
        {
            String host = plugin.getConfig().getString("mysql.host", "localhost");
            int port = plugin.getConfig().getInt("mysql.port", 3306);
            String database = plugin.getConfig().getString("mysql.database", "minecraft");
            String user = plugin.getConfig().getString("mysql.username", "root");
            String pass = plugin.getConfig().getString("mysql.password", "");
            this.db = new MySQL(plugin.getLogger(), host, port, database, user, pass);
        }
        else
        {
            this.db = new SQLite(plugin.getLogger(), new File(plugin.getDataFolder(), "TopPvP.db"));
        }
        plugin.getLogger().info("Using " + (this.db instanceof MySQL ? "MySQL" : "SQLite"));
        TopPvP.log("Connecting to DB");
        try
        {
            this.connect();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(plugin);
            return;
        }
        prepare();
    }
}
