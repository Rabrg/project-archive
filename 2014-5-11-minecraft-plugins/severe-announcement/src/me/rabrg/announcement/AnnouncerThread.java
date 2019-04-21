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

import java.util.Random;

/**
 * Thread which handles the announcing.
 *
 * @author MiHo
 */
class AnnouncerThread extends Thread {
    /**
     * Tool used for generating random numbers.
     */
    private final Random randomGenerator = new Random();

    /**
     * The plugin which holds this thread.
     */
    private final Plugin plugin;

    /**
     * The last announcement index. (Only for sequential announcing.)
     */
    private int lastAnnouncement = 0;

    /**
     * Allocates a new scheduled announcer thread.
     *
     * @param plugin the plugin which holds the thread.
     */
    public AnnouncerThread(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * The main method of the thread.
     */
    @Override
    public void run() {
        if (plugin.isAnnouncerEnabled()) {
            if (plugin.isRandom()) {
                lastAnnouncement = Math.abs(randomGenerator.nextInt() % plugin.numberOfAnnouncements());
            } else {
                if ((++lastAnnouncement) >= plugin.numberOfAnnouncements()) {
                    lastAnnouncement = 0;
                }
            }

            if (lastAnnouncement < plugin.numberOfAnnouncements()) {
                plugin.announce(lastAnnouncement + 1);
            }
        }
    }
}