package net.thewavemc.onevsone.duel.invite;

public class Invite {
	private final String inviter;
	private final String invited;

	public Invite(final String inviter, final String invited) {
		this.inviter = inviter;
		this.invited = invited;
	}

	public String getInviter() {
		return this.inviter;
	}

	public String getInvited() {
		return this.invited;
	}
}
