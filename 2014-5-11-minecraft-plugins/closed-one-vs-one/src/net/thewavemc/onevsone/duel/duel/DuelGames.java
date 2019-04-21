package net.thewavemc.onevsone.duel.duel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.entity.Player;

public class DuelGames {
	List<Duel> list = new ArrayList();

	public void add(final Duel d) {
		this.list.add(d);
	}

	public void remove(final String name) {
		for (int i = 0; i < this.list.size(); i++) {
			if (this.list.get(i).getPlayer1().getName().equals(name) || this.list.get(i).getPlayer2().getName().equals(name)) {
				this.list.remove(i);
				return;
			}
		}
	}

	public boolean containsPlayer(final String name) {
		for (int i = 0; i < this.list.size(); i++) {
			if (this.list.get(i).getPlayer1().getName().equals(name) || this.list.get(i).getPlayer2().getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	public Player getOpponent(final String name) {
		for (int i = 0; i < this.list.size(); i++) {
			if (this.list.get(i).getPlayer1().getName().equals(name)) {
				return this.list.get(i).getPlayer2();
			}
			if (this.list.get(i).getPlayer2().getName().equals(name)) {
				return this.list.get(i).getPlayer1();
			}
		}
		return null;
	}

	public Iterator<Duel> iterator() {
		return this.list.iterator();
	}
}
