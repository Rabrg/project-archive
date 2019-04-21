package me.rabrg.league.queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import me.rabrg.league.League;

import org.bukkit.entity.Player;

public final class Queue {

	private final League league;

	private final List<Player> queue;

	public Queue(final League league) {
		this.league = league;
		this.queue = new ArrayList<Player>();
	}

	public void queue(final Player player) {
		queue.add(player);
	}

	public void dequeue(final Player player) {
		queue.remove(player);
	}

	public void select() {
		Collections.sort(queue, league.getRatingComparator());
		Iterator<Player> iterator = queue.listIterator();
		while (iterator.hasNext()) {
			final Player first = iterator.next();
			iterator.remove();
			if (!iterator.hasNext()) {
				first.sendMessage("Sorry, we there weren't enough people to match you up.");
				break;
			}
			@SuppressWarnings("unused")
			final Player second = iterator.next();
			iterator.remove();
			
			// TODO: create duel
		}
	}
}
