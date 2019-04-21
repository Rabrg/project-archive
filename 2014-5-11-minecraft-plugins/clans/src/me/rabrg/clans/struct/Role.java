package me.rabrg.clans.struct;

import me.rabrg.clans.Conf;

public enum Role {
	ADMIN(2, "admin"), MODERATOR(1, "moderator"), NORMAL(0, "normal member");

	public final int value;
	public final String nicename;

	private Role(final int value, final String nicename) {
		this.value = value;
		this.nicename = nicename;
	}

	public boolean isAtLeast(final Role role) {
		return this.value >= role.value;
	}

	public boolean isAtMost(final Role role) {
		return this.value <= role.value;
	}

	@Override
	public String toString() {
		return this.nicename;
	}

	public String getPrefix() {
		if (this == Role.ADMIN) {
			return Conf.prefixAdmin;
		}

		if (this == Role.MODERATOR) {
			return Conf.prefixMod;
		}

		return "";
	}
}
