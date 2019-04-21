package me.rabrg.clans.cmd;

import org.bukkit.ChatColor;

import me.rabrg.clans.Conf;
import me.rabrg.clans.P;
import me.rabrg.clans.iface.EconomyParticipator;
import me.rabrg.clans.integration.Econ;
import me.rabrg.clans.struct.Permission;

public class CmdMoneyTransferFp extends FCommand {
	public CmdMoneyTransferFp() {
		this.aliases.add("fp");

		this.requiredArgs.add("amount");
		this.requiredArgs.add("clan");
		this.requiredArgs.add("player");

		// this.optionalArgs.put("", "");

		this.permission = Permission.MONEY_F2P.node;
		this.setHelpShort("transfer f -> p");

		senderMustBePlayer = false;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		final double amount = this.argAsDouble(0, 0d);
		final EconomyParticipator from = this.argAsClan(1);
		if (from == null) {
			return;
		}
		final EconomyParticipator to = this.argAsBestCPlayerMatch(2);
		if (to == null) {
			return;
		}

		final boolean success = Econ.transferMoney(fme, from, to, amount);

		if (success && Conf.logMoneyTransactions) {
			P.p.log(ChatColor.stripColor(P.p.txt.parse("%s transferred %s from the clan \"%s\" to the player \"%s\"", fme.getName(), Econ.moneyString(amount),
					from.describeTo(null), to.describeTo(null))));
		}
	}
}
