/*
 * Copyright (C) 2011-2012 Mi.Ho.
 * Recreated by Whisk (C) 2012 Whisk1 http://alyeskahosting.net
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

/**
 * Static container for the permissions.
 * @author MiHo
 */
public final class AnnouncerPermissions {
    /**
     * The permission needed for receiving announcements this plugin.
     */
    public static final String RECEIVER = "announcer.receiver";

    /**
     * The permission needed to add new announcements.
     */
    public static final String ADD = "announcer.add";

    /**
     * The permission needed to delete existing announcements.
     */
    public static final String DELETE = "announcer.delete";

    /**
     * The permission needed to broadcast existing announcements.
     */
    public static final String BROADCAST = "announcer.broadcast";

    /**
     * The permission needed for moderating this plugin.
     */
    public static final String MODERATOR = "announcer.moderate";

    /**
     * The permission needed for moderating this plugin.
     */
    public static final String ADMINISTRATOR = "announcer.admin";

}