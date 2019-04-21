package me.rabrg.clans.cmd;

import me.rabrg.clans.Clan;
import me.rabrg.clans.integration.Econ;
import me.rabrg.clans.struct.Permission;

public class CmdMoneyBalance extends FCommand {
	public CmdMoneyBalance() {
		super();
		this.aliases.add("b");
		this.aliases.add("balance");

		// this.requiredArgs.add("");
		this.optionalArgs.put("clan", "yours");

		this.permission = Permission.MONEY_BALANCE.node;
		this.setHelpShort("show clan balance");

		senderMustBePlayer = false;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		Clan clan = myClan;
		if (this.argIsSet(0)) {
			clan = this.argAsClan(0);
		}

		if (clan == null) {
			return;
		}
		if (clan != myClan && !Permission.MONEY_BALANCE_ANY.has(sender, true)) {
			return;
		}

		Econ.sendBalanceInfo(fme, clan);
	}

}
