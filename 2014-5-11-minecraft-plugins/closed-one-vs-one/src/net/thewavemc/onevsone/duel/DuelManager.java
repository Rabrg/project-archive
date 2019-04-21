package net.thewavemc.onevsone.duel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.thewavemc.onevsone.Configuration;
import net.thewavemc.onevsone.OneVsOne;
import net.thewavemc.onevsone.duel.duel.Duel;
import net.thewavemc.onevsone.duel.duel.DuelGames;
import net.thewavemc.onevsone.duel.invite.DuelInvites;
import net.thewavemc.onevsone.duel.invite.Invite;
import net.thewavemc.onevsone.playerrestore.PlayerRestore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DuelManager {
	OneVsOne plugin;
	private final List<String> ovo = new ArrayList();
	private final DuelInvites invites = new DuelInvites();
	private final DuelGames duels = new DuelGames();

	public DuelManager(final OneVsOne plugin) {
		this.plugin = plugin;
	}

	public void createDuel(final Player p1, final Player p2) {
		final Duel duel = new Duel(this.plugin, p1.getName(), p2.getName());

		this.invites.removeAll(p1.getName());
		this.invites.removeAll(p2.getName());

		p1.getInventory().setContents(Configuration.inventoryContents);
		p2.getInventory().setContents(Configuration.inventoryContents);

		p1.getInventory().setArmorContents(Configuration.armorContents);
		p2.getInventory().setArmorContents(Configuration.armorContents);

		p1.updateInventory();
		p2.updateInventory();

		p1.setHealth(20.0D);
		p2.setHealth(20.0D);

		this.duels.add(duel);
		duel.setTaskID(Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, duel, 0L, 10L));
		for (final String s : this.ovo) {
			final Player p = Bukkit.getPlayerExact(s);
			if (!p.getName().equals(p1.getName()) && !p.getName().equals(p2.getName())) {
				p1.hidePlayer(p);
				p2.hidePlayer(p);
			}
		}
	}

	public void inviteDuel(final Player p1, final Player p2) {
		if (p1.getName().equals(p2.getName())) {
			p1.sendMessage(this.plugin.TAG + ChatColor.RED + "You can't invite yourself!");
			return;
		}
		boolean alreadyInvited = true;

		final Iterator<Invite> it = this.invites.iterator();
		while (it.hasNext()) {
			final Invite i = it.next();
			final String inviter = i.getInviter();
			final String invited = i.getInvited();
			if (invited.equals(p1.getName()) && inviter.equals(p2.getName())) {
				acceptDuel(p1);
				return;
			}
			if (inviter.equals(p1.getName()) && invited.equals(p2.getName())) {
				alreadyInvited = false;
			}
		}
		if (alreadyInvited) {
			this.invites.add(new Invite(p1.getName(), p2.getName()));

			p1.sendMessage(this.plugin.TAG + ChatColor.GREEN + "Succesfully invited " + p2.getName());
			p2.sendMessage(this.plugin.TAG + ChatColor.GREEN + p1.getName() + " invited you, type \"/ovo accept\" to accept the invite!");
		} else {
			p1.sendMessage(this.plugin.TAG + ChatColor.RED + "You already invited " + p2.getName());
		}
	}

	public boolean acceptDuel(final Player p1) {
		if (this.invites.containsInvited(p1.getName())) {
			final Player c = Bukkit.getPlayerExact(this.invites.getInviter(p1.getName()));
			c.sendMessage(this.plugin.TAG + ChatColor.GREEN + p1.getName() + " accepted your invite!");
			createDuel(c, p1);
			return true;
		}
		return false;
	}

	public boolean acceptDuel(final Player p1, final String who) {
		if (this.invites.containsInvited(p1.getName())) {
			final Player c = Bukkit.getPlayerExact(this.invites.getSpecificInviter(p1.getName(), who));
			if (c != null) {
				c.sendMessage(this.plugin.TAG + ChatColor.GREEN + p1.getName() + " accepted your invite!");
				createDuel(c, p1);
			} else {
				p1.sendMessage(this.plugin.TAG + ChatColor.RED + who + " never invited you!");
			}
			return true;
		}
		return false;
	}

	public void endDuel(final Player p1) {
		final Player p2 = this.duels.getOpponent(p1.getName());
		this.plugin.clearPlayer(p2);
		p2.getInventory().setItem(4, Configuration.inviteItem);
		p2.setHealth(20.0D);

		p2.sendMessage(this.plugin.TAG + ChatColor.GREEN + "You won a duel against " + p1.getName() + "!");
		p1.sendMessage(this.plugin.TAG + ChatColor.RED + "You lost a duel against " + p2.getName() + "!");

		this.duels.remove(p1.getName());
		for (final String s : this.ovo) {
			if (!s.equals(p1.getName()) && !s.equals(p2.getName())) {
				final Player cP = Bukkit.getPlayerExact(s);
				p1.showPlayer(cP);
				p2.showPlayer(cP);
			}
		}
	}

	public void enter(final Player p1) {
		if (p1.getVehicle() == null) {
			p1.sendMessage(this.plugin.TAG + ChatColor.GREEN + "You entered One Vs One!");
			try {
				this.plugin.getPlayerRestoreManager().storePlayer(
						p1,
						new PlayerRestore(p1.getInventory().getContents().clone(), p1.getInventory().getArmorContents().clone(), p1.getLocation().clone(),
								p1.getVelocity().clone(), p1.getFallDistance(), p1.getHealth(), p1.getFoodLevel()));
			} catch (final Exception e) {
				e.printStackTrace();
			}
			this.plugin.clearPlayer(p1);
			p1.getInventory().setItem(4, Configuration.inviteItem);
			p1.teleport(Configuration.spawn);
			p1.setHealth(20.0D);
			p1.setFoodLevel(20);
			this.ovo.add(p1.getName());

			final Iterator<Duel> it = this.duels.iterator();
			while (it.hasNext()) {
				final Duel d = it.next();
				d.getPlayer1().hidePlayer(p1);
				d.getPlayer2().hidePlayer(p1);
			}
		} else {
			p1.sendMessage(this.plugin.TAG + ChatColor.RED + "You can't join One Vs One while riding a " + p1.getVehicle().getType().toString().toLowerCase());
		}
	}

	public void leave(final Player p1, final boolean restore) {
		if (this.ovo.contains(p1.getName())) {
			this.ovo.remove(p1.getName());
			if (this.duels.containsPlayer(p1.getName())) {
				endDuel(p1);
				return;
			}
			this.invites.removeAll(p1.getName());
			try {
				if (restore) {
					p1.sendMessage(this.plugin.TAG + ChatColor.GREEN + "You left One Vs One!");
					this.plugin.getPlayerRestoreManager().restorePlayer(p1);
				}
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
		if (restore) {
			for (final Player p : Bukkit.getOnlinePlayers()) {
				if (!p.getName().equals(p1.getName())) {
					p.showPlayer(p1);
					p1.showPlayer(p);
				}
			}
		}
	}

	public boolean isInDuel(final Player p1) {
		return this.duels.containsPlayer(p1.getName());
	}

	public boolean isOneVsOne(final Player p1) {
		return this.ovo.contains(p1.getName());
	}

	public Iterator<Invite> iterator() {
		return this.invites.iterator();
	}
}
