package me.rabrg.clans.iface;

public interface EconomyParticipator extends RelationParticipator {
	public String getAccountId();

	public void msg(String str, Object... args);
}