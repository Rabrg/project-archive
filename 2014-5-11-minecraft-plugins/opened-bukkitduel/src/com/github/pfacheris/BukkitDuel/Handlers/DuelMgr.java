package com.github.pfacheris.BukkitDuel.Handlers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import com.github.pfacheris.BukkitDuel.BukkitDuel;
import com.github.pfacheris.BukkitDuel.Objects.Duel;

public class DuelMgr {

	private final BukkitDuel plugin;
	private final List<Duel> duels;

	public DuelMgr(final BukkitDuel plugin) {
		this.plugin = plugin;
		duels = new ArrayList<Duel>();
		duels.clear();
	}

	public List<Duel> getActiveDuels() {
		final List<Duel> activeDuels = new ArrayList<Duel>();
		for (final Duel d : duels) {
			if (d.getIsActive()) {
				activeDuels.add(d);
			}
		}

		return activeDuels;
	}

	public List<Duel> getPendingDuels() {
		final List<Duel> pendingDuels = new ArrayList<Duel>();
		for (final Duel d : duels) {
			if (!d.getIsActive()) {
				pendingDuels.add(d);
			}
		}

		return pendingDuels;
	}

	public Duel getDuelByTwoParticipants(final Player initiator, final Player challengee) {
		if (duels.size() > 0) {
			for (final Duel d : duels) {
				if (d.getChallengee().equals(challengee) || d.getInitiator().equals(initiator)) {
					return d;
				}
				if (d.getChallengee().equals(initiator) || d.getInitiator().equals(challengee)) {
					return d;
				}
			}
		}
		return null;
	}

	public List<Duel> getDuelByParticipant(final Player participant) {
		final List<Duel> duelsWithParticipant = new ArrayList<Duel>();
		for (final Duel d : duels) {
			if (d.getChallengee().equals(participant) || d.getInitiator().equals(participant)) {
				duelsWithParticipant.add(d);
			}
		}

		return duelsWithParticipant;
	}

	public Duel getActiveDuelByPlayer(final Player participant) {
		for (final Duel d : duels) {
			if ((d.getChallengee().equals(participant) || d.getInitiator().equals(participant)) && d.getIsActive()) {
				return d;
			}
		}

		return null;
	}

	public boolean isPlayerInActiveDuel(final Player participant) {
		for (final Duel d : duels) {
			if ((d.getChallengee().equals(participant) || d.getInitiator().equals(participant)) && d.getIsActive()) {
				return true;
			}
		}

		return false;
	}

	public List<Duel> getDuelByInitiator(final Player participant) {
		final List<Duel> duelsWithParticipant = new ArrayList<Duel>();
		for (final Duel d : duels) {
			if (d.getInitiator().equals(participant)) {
				duelsWithParticipant.add(d);
			}
		}

		return duelsWithParticipant;
	}

	public List<Duel> getDuelByChallengee(final Player participant) {
		final List<Duel> duelsWithParticipant = new ArrayList<Duel>();
		for (final Duel d : duels) {
			if (d.getChallengee().equals(participant)) {
				duelsWithParticipant.add(d);
			}
		}

		return duelsWithParticipant;
	}

	public void save(final Duel newDuel) {
		duels.add(newDuel);
	}

	public void cancelDuel(final Duel cancelledDuel) {
		duels.remove(cancelledDuel);
	}

	public void endDuel(final Duel concludedDuel, final boolean initiatorWins) {
		duels.remove(concludedDuel);
		concludedDuel.endDuel(plugin, initiatorWins);
		if (getActiveDuels().size() < 1) {
			HandlerList.unregisterAll(plugin);
		}
	}
}
