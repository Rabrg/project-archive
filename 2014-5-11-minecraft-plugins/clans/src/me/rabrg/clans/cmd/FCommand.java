package me.rabrg.clans.cmd;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rabrg.clans.CPlayer;
import me.rabrg.clans.CPlayers;
import me.rabrg.clans.Clan;
import me.rabrg.clans.Clans;
import me.rabrg.clans.Conf;
import me.rabrg.clans.P;
import me.rabrg.clans.integration.Econ;
import me.rabrg.clans.struct.Role;
import me.rabrg.clans.zcore.MCommand;

public abstract class FCommand extends MCommand<P> {
	public boolean disableOnLock;

	public CPlayer fme;
	public Clan myClan;
	public boolean senderMustBeMember;
	public boolean senderMustBeModerator;
	public boolean senderMustBeAdmin;

	public boolean isMoneyCommand;

	public FCommand() {
		super(P.p);

		// Due to safety reasons it defaults to disable on lock.
		disableOnLock = true;

		// The money commands must be disabled if money should not be used.
		isMoneyCommand = false;

		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
	public void execute(final CommandSender sender, final List<String> args, final List<MCommand<?>> commandChain) {
		if (sender instanceof Player) {
			this.fme = CPlayers.i.get((Player) sender);
			this.myClan = this.fme.getClan();
		} else {
			this.fme = null;
			this.myClan = null;
		}
		super.execute(sender, args, commandChain);
	}

	@Override
	public boolean isEnabled() {
		if (p.getLocked() && this.disableOnLock) {
			msg("<b>Clans was locked by an admin. Please try again later.");
			return false;
		}

		if (this.isMoneyCommand && !Conf.econEnabled) {
			msg("<b>Clan economy features are disabled on this server.");
			return false;
		}

		if (this.isMoneyCommand && !Conf.bankEnabled) {
			msg("<b>The clan bank system is disabled on this server.");
			return false;
		}

		return true;
	}

	@Override
	public boolean validSenderType(final CommandSender sender, final boolean informSenderIfNot) {
		final boolean superValid = super.validSenderType(sender, informSenderIfNot);
		if (!superValid) {
			return false;
		}

		if (!(this.senderMustBeMember || this.senderMustBeModerator || this.senderMustBeAdmin)) {
			return true;
		}

		if (!(sender instanceof Player)) {
			return false;
		}

		final CPlayer fplayer = CPlayers.i.get((Player) sender);

		if (!fplayer.hasClan()) {
			sender.sendMessage(p.txt.parse("<b>You are not member of any clan."));
			return false;
		}

		if (this.senderMustBeModerator && !fplayer.getRole().isAtLeast(Role.MODERATOR)) {
			sender.sendMessage(p.txt.parse("<b>Only clan moderators can %s.", this.getHelpShort()));
			return false;
		}

		if (this.senderMustBeAdmin && !fplayer.getRole().isAtLeast(Role.ADMIN)) {
			sender.sendMessage(p.txt.parse("<b>Only clan admins can %s.", this.getHelpShort()));
			return false;
		}

		return true;
	}

	// -------------------------------------------- //
	// Assertions
	// -------------------------------------------- //

	public boolean assertHasClan() {
		if (me == null) {
			return true;
		}

		if (!fme.hasClan()) {
			sendMessage("You are not member of any clan.");
			return false;
		}
		return true;
	}

	public boolean assertMinRole(final Role role) {
		if (me == null) {
			return true;
		}

		if (fme.getRole().value < role.value) {
			msg("<b>You <h>must be " + role + "<b> to " + this.getHelpShort() + ".");
			return false;
		}
		return true;
	}

	// -------------------------------------------- //
	// Argument Readers
	// -------------------------------------------- //

	// FPLAYER ======================
	public CPlayer strAsCPlayer(final String name, final CPlayer def, final boolean msg) {
		CPlayer ret = def;

		if (name != null) {
			final CPlayer fplayer = CPlayers.i.get(name);
			if (fplayer != null) {
				ret = fplayer;
			}
		}

		if (msg && ret == null) {
			this.msg("<b>No player \"<p>%s<b>\" could be found.", name);
		}

		return ret;
	}

	public CPlayer argAsCPlayer(final int idx, final CPlayer def, final boolean msg) {
		return this.strAsCPlayer(this.argAsString(idx), def, msg);
	}

	public CPlayer argAsCPlayer(final int idx, final CPlayer def) {
		return this.argAsCPlayer(idx, def, true);
	}

	public CPlayer argAsCPlayer(final int idx) {
		return this.argAsCPlayer(idx, null);
	}

	// BEST FPLAYER MATCH ======================
	public CPlayer strAsBestCPlayerMatch(final String name, final CPlayer def, final boolean msg) {
		CPlayer ret = def;

		if (name != null) {
			final CPlayer fplayer = CPlayers.i.getBestIdMatch(name);
			if (fplayer != null) {
				ret = fplayer;
			}
		}

		if (msg && ret == null) {
			this.msg("<b>No player match found for \"<p>%s<b>\".", name);
		}

		return ret;
	}

	public CPlayer argAsBestCPlayerMatch(final int idx, final CPlayer def, final boolean msg) {
		return this.strAsBestCPlayerMatch(this.argAsString(idx), def, msg);
	}

	public CPlayer argAsBestCPlayerMatch(final int idx, final CPlayer def) {
		return this.argAsBestCPlayerMatch(idx, def, true);
	}

	public CPlayer argAsBestCPlayerMatch(final int idx) {
		return this.argAsBestCPlayerMatch(idx, null);
	}

	// FACTION ======================
	public Clan strAsClan(final String name, final Clan def, final boolean msg) {
		Clan ret = def;

		if (name != null) {
			Clan clan = null;

			// First we try an exact match
			if (clan == null) {
				clan = Clans.i.getByTag(name);
			}

			// Next we match clan tags
			if (clan == null) {
				clan = Clans.i.getBestTagMatch(name);
			}

			// Next we match player names
			if (clan == null) {
				final CPlayer fplayer = CPlayers.i.getBestIdMatch(name);
				if (fplayer != null) {
					clan = fplayer.getClan();
				}
			}

			if (clan != null) {
				ret = clan;
			}
		}

		if (msg && ret == null) {
			this.msg("<b>The clan or player \"<p>%s<b>\" could not be found.", name);
		}

		return ret;
	}

	public Clan argAsClan(final int idx, final Clan def, final boolean msg) {
		return this.strAsClan(this.argAsString(idx), def, msg);
	}

	public Clan argAsClan(final int idx, final Clan def) {
		return this.argAsClan(idx, def, true);
	}

	public Clan argAsClan(final int idx) {
		return this.argAsClan(idx, null);
	}

	// -------------------------------------------- //
	// Commonly used logic
	// -------------------------------------------- //

	public boolean canIAdministerYou(final CPlayer i, final CPlayer you) {
		if (!i.getClan().equals(you.getClan())) {
			i.sendMessage(p.txt.parse("%s <b>is not in the same clan as you.", you.describeTo(i, true)));
			return false;
		}

		if (i.getRole().value > you.getRole().value || i.getRole().equals(Role.ADMIN)) {
			return true;
		}

		if (you.getRole().equals(Role.ADMIN)) {
			i.sendMessage(p.txt.parse("<b>Only the clan admin can do that."));
		} else if (i.getRole().equals(Role.MODERATOR)) {
			if (i == you) {
				return true; // Moderators can control themselves
			} else {
				i.sendMessage(p.txt.parse("<b>Moderators can't control each other..."));
			}
		} else {
			i.sendMessage(p.txt.parse("<b>You must be a clan moderator to do that."));
		}

		return false;
	}

	// if economy is enabled and they're not on the bypass list, make 'em pay;
	// returns true unless person can't afford the cost
	public boolean payForCommand(final double cost, final String toDoThis, final String forDoingThis) {
		if (!Econ.shouldBeUsed() || this.fme == null || cost == 0.0 || fme.isAdminBypassing()) {
			return true;
		}

		if (Conf.bankEnabled && Conf.bankClanPaysCosts && fme.hasClan()) {
			return Econ.modifyMoney(myClan, -cost, toDoThis, forDoingThis);
		} else {
			return Econ.modifyMoney(fme, -cost, toDoThis, forDoingThis);
		}
	}

	// like above, but just make sure they can pay; returns true unless person
	// can't afford the cost
	public boolean canAffordCommand(final double cost, final String toDoThis) {
		if (!Econ.shouldBeUsed() || this.fme == null || cost == 0.0 || fme.isAdminBypassing()) {
			return true;
		}

		if (Conf.bankEnabled && Conf.bankClanPaysCosts && fme.hasClan()) {
			return Econ.hasAtLeast(myClan, cost, toDoThis);
		} else {
			return Econ.hasAtLeast(fme, cost, toDoThis);
		}
	}
}
