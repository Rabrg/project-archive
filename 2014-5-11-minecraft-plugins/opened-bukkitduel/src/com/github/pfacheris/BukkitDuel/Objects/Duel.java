package com.github.pfacheris.BukkitDuel.Objects;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.github.pfacheris.BukkitDuel.BukkitDuel;
import com.github.pfacheris.BukkitDuel.Listeners.PlayerInDuelListener;

public class Duel {

	private Player initiator;
	private Player challengee;
	private Arena arena;
	public Stakes stakesInitiator;
	public Stakes stakesChallengee;
	private boolean isActive;
	private Location initiatorOriginalPosition;
	private Location challengeeOriginalPosition;

	public Duel(final Player initiator, final Player challengee, final Arena arena) {
		this.initiator = initiator;
		this.challengee = challengee;
		this.arena = arena;
		this.stakesInitiator = new Stakes(initiator);
		this.stakesChallengee = new Stakes(initiator);
		this.isActive = false;
	}

	public Player getInitiator() {
		return this.initiator;
	}

	public void setInitiator(final Player newDuelist) {
		this.initiator = newDuelist;
	}

	public Player getChallengee() {
		return this.challengee;
	}

	public void setChallengee(final Player newDuelist) {
		this.challengee = newDuelist;
	}

	public Arena getArena() {
		return arena;
	}

	public void setArena(final Arena newArena) {
		this.arena = newArena;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void startDuel(final BukkitDuel plugin) {
		this.isActive = true;
		this.initiator.setHealth(20);
		this.initiator.setFoodLevel(20);
		this.challengee.setHealth(20);
		this.challengee.setFoodLevel(20);
		this.arena.setInUse(true);
		final Listener listener = new PlayerInDuelListener(plugin, this);
		plugin.registerEvents(listener);

		this.initiatorOriginalPosition = initiator.getLocation();
		this.challengeeOriginalPosition = challengee.getLocation();

		this.initiator.teleport(arena.getSpawn1());
		this.challengee.teleport(arena.getSpawn2());

		this.initiator.sendMessage("Fight!");
		this.challengee.sendMessage("Fight!");
	}

	public void endDuel(final BukkitDuel plugin, final boolean initiatorWins) {
		this.isActive = false;
		this.arena.setInUse(false);
		this.initiator.teleport(initiatorOriginalPosition);
		this.challengee.teleport(challengeeOriginalPosition);

		this.initiator.setHealth(20);
		this.initiator.setFoodLevel(20);
		this.challengee.setHealth(20);
		this.challengee.setFoodLevel(20);

		if (initiatorWins) {
			initiator.sendMessage(ChatColor.GREEN + "You have won the duel! Winnings are listed below:");
			if (stakesChallengee.getWagerMoneyAmount() > 0 && BukkitDuel.economy.isEnabled()) {
				BukkitDuel.economy.bankDeposit(initiator.getName(), stakesChallengee.getWagerMoneyAmount());
				initiator.sendMessage("Money: " + stakesChallengee.getWagerMoneyAmount() + " Dollars");
			}
			if (stakesChallengee.getWagerExperienceAmount() > 0) {
				// adjust for levels rather than exp amount
				initiator.setExp(initiator.getExp() + stakesChallengee.getWagerExperienceAmount());
				initiator.sendMessage("Experience: " + stakesChallengee.getWagerExperienceAmount());
				if (challengee.isOnline()) {
					challengee.setExp(initiator.getExp() - stakesChallengee.getWagerExperienceAmount());
				}
			}
			if (stakesInitiator.getWagerItems().getSize() > 0) {
				for (final ItemStack items : stakesInitiator.getWagerItems().getContents()) {
					initiator.getInventory().addItem(items);
				}
			}
			if (stakesChallengee.getWagerItems().getSize() > 0) {
				for (final ItemStack items : stakesChallengee.getWagerItems().getContents()) {
					initiator.getInventory().addItem(items);
					initiator.sendMessage("Item: " + items.getType() + "x " + items.getAmount());
				}
			}
			if (stakesChallengee.getWagerPowerAmount() > 0) {
				// Do faction power handling here.
			}

			if (challengee.isOnline()) {
				challengee.sendMessage(ChatColor.GREEN + "You have lost the duel, tough luck!");
			}
		} else {
			challengee.sendMessage(ChatColor.GREEN + "You have won the duel! Winnings are listed below:");

			if (stakesInitiator.getWagerMoneyAmount() > 0 && BukkitDuel.economy.isEnabled()) {
				BukkitDuel.economy.bankDeposit(challengee.getName(), stakesInitiator.getWagerMoneyAmount());
				challengee.sendMessage("Money: " + stakesChallengee.getWagerMoneyAmount() + " Dollars");
			}
			if (stakesInitiator.getWagerExperienceAmount() > 0) {
				// adjust for levels rather than exp amount
				challengee.setExp(initiator.getExp() + stakesInitiator.getWagerExperienceAmount());
				challengee.sendMessage("Experience: " + stakesChallengee.getWagerExperienceAmount());
				if (initiator.isOnline()) {
					initiator.setExp(initiator.getExp() - stakesInitiator.getWagerExperienceAmount());
				}
			}
			if (stakesChallengee.getWagerItems().getSize() > 0) {
				for (final ItemStack items : stakesChallengee.getWagerItems().getContents()) {
					challengee.getInventory().addItem(items);
				}
			}
			if (stakesInitiator.getWagerItems().getSize() > 0) {
				for (final ItemStack items : stakesInitiator.getWagerItems()) {
					challengee.getInventory().addItem(items);
					challengee.sendMessage("Item: " + items.getType() + "x " + items.getAmount());
				}
			}
			if (stakesInitiator.getWagerPowerAmount() > 0) {
				// Do faction power handling here.
			}

			if (initiator.isOnline()) {
				initiator.sendMessage(ChatColor.GREEN + "You have lost the duel, tough luck!");
			}
		}
	}

}
