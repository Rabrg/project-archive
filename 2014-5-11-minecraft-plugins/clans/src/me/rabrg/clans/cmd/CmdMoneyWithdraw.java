package me.rabrg.clans.cmd;

import org.bukkit.ChatColor;

import me.rabrg.clans.Conf;
import me.rabrg.clans.P;
import me.rabrg.clans.iface.EconomyParticipator;
import me.rabrg.clans.integration.Econ;
import me.rabrg.clans.struct.Permission;

public class CmdMoneyWithdraw extends FCommand {
	public CmdMoneyWithdraw() {
		this.aliases.add("w");
		this.aliases.add("withdraw");

		this.requiredArgs.add("amount");
		this.optionalArgs.put("clan", "yours");

		this.permission = Permission.MONEY_WITHDRAW.node;
		this.setHelpShort("withdraw money");

		senderMustBePlayer = true;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		final double amount = this.argAsDouble(0, 0d);
		final EconomyParticipator clan = this.argAsClan(1, myClan);
		if (clan == null) {
			return;
		}
		final boolean success = Econ.transferMoney(fme, clan, fme, amount);

		if (success && Conf.logMoneyTransactions) {
			P.p.log(ChatColor.stripColor(P.p.txt.parse("%s withdrew %s from the clan bank: %s", fme.getName(), Econ.moneyString(amount), clan.describeTo(null))));
		}
	}
}
