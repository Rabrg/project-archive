/*
 * Copyright (C) 2011-2012 Mi.Ho.
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details. You should have received a copy of the GNU General Public
 * License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */

package me.rabrg.announcement;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;



/**
 * Scheduled AnnouncerPlugin for Bukkit.
 *
 * @author MiHo
 */
public class Plugin extends JavaPlugin {

    /**
     * Messages to be announced.
     */
    protected List<String> announcementMessages;

    /**
     * The tag used for the broadcast.
     */
    protected String announcementPrefix;

    /**
     * Period used for announcing.
     */
    protected long announcementInterval;

    /**
     * Flag if the plugin is enabled.
     */
    protected boolean enabled;

    /**
     * Flag if the plugin should output the announcements randomly.
     */
    protected boolean random;
    
    /**
     * Flag if the plugin should output the onPlayerLogin announcement.
     */
    protected boolean motdEnabled;
    
    /**
     * The onPlayerLogin announcement.
     */
    protected String motd;
    
    /**
     * Thread used to announcing.
     */
    private AnnouncerThread announcerThread;

    /**
     * The logger used to output logging information.
     */
    private Logger logger;
    
    /**
     * The listener used to register events.
     */
    private AnnouncerEventListener listener;
    /**
     * Allocates a new AnnouncerPlugin plugin. Any initialisation code is here. NOTE: Event registration should be done
     * in onEnable not here as all events are unregistered when a plugin is disabled
     */
    public Plugin() {
        super();

        announcerThread = new AnnouncerThread(this);
    }

    /**
     * Called when enabling the plugin.
     */
    public void onEnable() {

        logger = getServer().getLogger();

        // Create default config if not exist yet.
        if (!new File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
        }

        // Load configuration.
        reloadConfiguration();

        // Register the schedule.
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler
            .scheduleSyncRepeatingTask(this, announcerThread, announcementInterval * 20, announcementInterval * 20);
        
        // Register the event listener.
		listener = new AnnouncerEventListener(this);
		PluginManager pm = Bukkit.getServer().getPluginManager();
		pm.registerEvents(this.listener, this);

        // Register command executor.
        AnnouncerCommandExecutor announcerCommandExecutor = new AnnouncerCommandExecutor(this);
        getCommand("announce").setExecutor(announcerCommandExecutor);
        getCommand("announcer").setExecutor(announcerCommandExecutor);
        getCommand("an").setExecutor(announcerCommandExecutor);

        // Logging.
        logger.info(String.format("%s is enabled!", getDescription().getFullName()));
        
        try 
        {
        	MetricsLite metricsLite = new MetricsLite(this);
        	metricsLite.start();
        }
        catch(IOException e)
        {
        	logger.info(String.format("%s was unable to send stats to mcstats.org", getDescription().getFullName()));
        }
    }

    /**
     * Called when disabling the plugin.
     */
    public void onDisable() {
        // Logging.
        logger.info(String.format("%s is disabled!", getDescription().getFullName()));
    }

    /**
     * Broadcasts an announcement.
     */
    public void announce() {
        announcerThread.run();
    }

    /**
     * Broadcasts an announcement.
     *
     * @param index 1 based index. (Like in the list output.)
     */
    public void announce(int index) {
        announce(announcementMessages.get(index - 1));
    }

    /**
     * Broadcasts an announcement.
     *
     * @param line the messages to promote.
     */
    public void announce(String line) {
        String[] messages = line.split("&n");
        for (String message : messages) {
            if (message.startsWith("/")) {
                // Execute the command, cause it's a command:
                getServer().dispatchCommand(getServer().getConsoleSender(), message.substring(1));
            } else if (getServer().getOnlinePlayers().length > 0) {
                // Broadcast the message only when somebody is online:
                String messageToSend = ChatColorHelper.replaceColorCodes(String.format("%s%s", announcementPrefix, message));
                getServer().broadcast(messageToSend, AnnouncerPermissions.RECEIVER);
            }
        }
    }

    /**
     * Saves the announcements.
     */
    public void saveConfiguration() {
        getConfig().set("announcement.messages", announcementMessages);
        getConfig().set("announcement.interval", announcementInterval);
        getConfig().set("announcement.prefix", announcementPrefix);
        getConfig().set("announcement.enabled", enabled);
        getConfig().set("announcement.random", random);
        getConfig().set("announcement.motdEnabled", motdEnabled);
        getConfig().set("announcement.motd", motd);
        saveConfig();
    }

    /**
     * Reloads the configuration.
     */
    public void reloadConfiguration() {
        reloadConfig();
        announcementPrefix = getConfig().getString("announcement.prefix", "&c[Announcement] ");
        announcementMessages = getConfig().getStringList("announcement.messages");
        announcementInterval = getConfig().getInt("announcement.interval", 1000);
        enabled = getConfig().getBoolean("announcement.enabled", true);
        random = getConfig().getBoolean("announcement.random", false);
        motdEnabled = getConfig().getBoolean("announcement.motdEnabled", false);
        motd = getConfig().getString("announcement.motd", "");
    }

    /**
     * @return prefix used for all announcements.
     */
    public String getAnnouncementPrefix() {
        return announcementPrefix;
    }

    /**
     * Sets the prefix used for all announcements.
     *
     * @param announcementPrefix the prefix to use for all announcements.
     */
    public void setAnnouncementPrefix(String announcementPrefix) {
        this.announcementPrefix = announcementPrefix;
        saveConfiguration();
    }

    /**
     * @return the announcement period.
     */
    public long getAnnouncementInterval() {
        return announcementInterval;
    }

    /**
     * Sets the announcement period.
     *
     * @param announcementInterval the period to set.
     */
    public void setAnnouncementInterval(long announcementInterval) {
        this.announcementInterval = announcementInterval;
        saveConfiguration();

        // Register the schedule
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.cancelTasks(this);
        scheduler
            .scheduleSyncRepeatingTask(this, announcerThread, announcementInterval * 20, announcementInterval * 20);
    }

    /**
     * Adds a new announcement.
     *
     * @param message the message to announce.
     */
    public void addAnnouncement(String message) {
        announcementMessages.add(message);
        saveConfiguration();
    }

    /**
     * Returns the Announcement with the passed index.
     *
     * @param index 1 based index, like in /announce list.
     * @return the announcement string.
     */
    public String getAnnouncement(int index) {
        return announcementMessages.get(index - 1);
    }

    /**
     * @return the number of announcements.
     */
    public int numberOfAnnouncements() {
        return announcementMessages.size();
    }

    /**
     * Removes all announcements.
     */
    public void removeAnnouncements() {
        announcementMessages.clear();
        saveConfiguration();
    }

    /**
     * Removes the announcement with the passed index.
     *
     * @param index the index which selects the announcement to remove.
     */
    public void removeAnnouncement(int index) {
        announcementMessages.remove(index - 1);
        saveConfiguration();
    }



    public boolean isAnnouncerEnabled() {
        return enabled;
    }

    public void setAnnouncerEnabled(boolean enabled) {
        this.enabled = enabled;
        saveConfiguration();
    }

    public boolean isRandom() {
        return random;
    }

    public void setRandom(boolean random) {
        this.random = random;
        saveConfiguration();
    }
    
    public void sayMessage(String messageToSay) {
        String messageToSend = ChatColorHelper.replaceColorCodes(String.format("%s%s", announcementPrefix, messageToSay));
        getServer().broadcast(messageToSend, AnnouncerPermissions.RECEIVER);
    }
    
    public void sendMotd(Player player)
    {
    	String messageToSend = ChatColorHelper.replaceColorCodes(String.format("%s%s", announcementPrefix, motd));
    	player.sendMessage(messageToSend);
    }

    public void setMotdEnabled(boolean enabled) {
        this.motdEnabled = enabled;
        saveConfiguration();
    }
    
    public void setMotd(String motd) {
        this.motd = motd;
        saveConfiguration();
    }
    
    public String getMotd() {
    	return motd;
    }
    
    public boolean isMotdEnabled()
    {
    	return motdEnabled;
    }
}
