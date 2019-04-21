package me.rabrg.clans.cmd;

import org.bukkit.ChatColor;

import me.rabrg.clans.Conf;
import me.rabrg.clans.P;
import me.rabrg.clans.iface.EconomyParticipator;
import me.rabrg.clans.integration.Econ;
import me.rabrg.clans.struct.Permission;

public class CmdMoneyDeposit extends FCommand {

	public CmdMoneyDeposit() {
		super();
		this.aliases.add("d");
		this.aliases.add("deposit");

		this.requiredArgs.add("amount");
		this.optionalArgs.put("clan", "yours");

		this.permission = Permission.MONEY_DEPOSIT.node;
		this.setHelpShort("deposit money");

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
		final boolean success = Econ.transferMoney(fme, fme, clan, amount);

		if (success && Conf.logMoneyTransactions) {
			P.p.log(ChatColor.stripColor(P.p.txt.parse("%s deposited %s in the clan bank: %s", fme.getName(), Econ.moneyString(amount), clan.describeTo(null))));
		}
	}

}
