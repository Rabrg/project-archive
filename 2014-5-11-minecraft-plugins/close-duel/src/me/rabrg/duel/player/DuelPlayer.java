package me.rabrg.duel.player;

import me.rabrg.duel.kit.Kit;

import org.bukkit.entity.Player;

public class DuelPlayer {

	private final Player player;

	private int rating;

	private DuelPlayer challenged;

	private Kit challengedWith;

	private DuelPlayer challengedBy;

	private Kit challengedByWith;

	private DuelPlayer dueling;

	public DuelPlayer(final Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public DuelPlayer getChallenged() {
		return challenged;
	}

	public void setChallenged(DuelPlayer challenged) {
		this.challenged = challenged;
	}

	public Kit getChallengedWith() {
		return challengedWith;
	}

	public void setChallengedWith(Kit challengedWith) {
		this.challengedWith = challengedWith;
	}

	public DuelPlayer getChallengedBy() {
		return challengedBy;
	}

	public void setChallengedBy(DuelPlayer challengedBy) {
		this.challengedBy = challengedBy;
	}

	public Kit getChallengedByWith() {
		return challengedByWith;
	}

	public void setChallengedByWith(Kit challengedByWith) {
		this.challengedByWith = challengedByWith;
	}

	public DuelPlayer getDueling() {
		return dueling;
	}

	public void setDueling(DuelPlayer dueling) {
		this.dueling = dueling;
	}
}
