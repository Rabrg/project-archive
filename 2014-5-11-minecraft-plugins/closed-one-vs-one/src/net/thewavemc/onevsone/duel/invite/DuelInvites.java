package net.thewavemc.onevsone.duel.invite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DuelInvites {
	private final List<Invite> list = new ArrayList();

	public void add(final Invite p) {
		this.list.add(p);
	}

	public void removeAll(final String s) {
		for (int i = this.list.size() - 1; i >= 0; i--) {
			if (this.list.get(i).getInviter().equals(s) || this.list.get(i).getInvited().equals(s)) {
				this.list.remove(i);
			}
		}
	}

	public boolean containsInviter(final String invited) {
		for (int i = this.list.size() - 1; i >= 0; i--) {
			if (this.list.get(i).getInviter().equals(invited)) {
				return true;
			}
		}
		return false;
	}

	public boolean containsInvited(final String inviter) {
		for (int i = this.list.size() - 1; i >= 0; i--) {
			if (this.list.get(i).getInvited().equals(inviter)) {
				return true;
			}
		}
		return false;
	}

	public String getInviter(final String invited) {
		for (int i = this.list.size() - 1; i >= 0; i--) {
			if (this.list.get(i).getInvited().equals(invited)) {
				return this.list.get(i).getInviter();
			}
		}
		return null;
	}

	public String getSpecificInviter(final String invited, final String inviter) {
		for (int i = this.list.size() - 1; i >= 0; i--) {
			if (this.list.get(i).getInvited().equals(invited) && this.list.get(i).getInviter().equals(inviter)) {
				return this.list.get(i).getInviter();
			}
		}
		return null;
	}

	public Iterator<Invite> iterator() {
		return this.list.iterator();
	}
}
