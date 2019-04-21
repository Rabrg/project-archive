/*
 * Copyright (C) 2011 Vex Software LLC
 * This file is part of Votifier.
 * 
 * Votifier is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Votifier is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Votifier.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.vexsoftware.votifier.model.listeners;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.vexsoftware.votifier.Votifier;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VoteListener;

public class CounterVoteListener implements VoteListener {

	public CounterVoteListener() {
		Votifier.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(Votifier.getInstance(), new Reminder(), 3000, 3000);
	}
	
	public void voteMade(Vote vote) {
		final String player = vote.getUsername().toLowerCase();
		if (Votifier.getInstance().incrementPlayerVoteCounter(player) >= 3) {
			Votifier.getInstance().resetPlayerVoteTimer(player);
			Votifier.getInstance().resetPlayerVoteCounter(player);
		}
	}

	class Reminder extends BukkitRunnable {

		@Override
		public void run() {
			for (Player player : Votifier.getInstance().getServer().getOnlinePlayers()) {
				if(System.currentTimeMillis() - Votifier.getInstance().getPlayerVoteTimer(player.getName().toLowerCase()) > 86400000) {
					player.sendMessage("[VoteReminder] You haven't voted on all of the websites today, please type /vote to do so!");
				}
			}
		}
		
	}
}
