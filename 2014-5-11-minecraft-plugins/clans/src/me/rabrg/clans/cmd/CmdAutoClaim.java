package me.rabrg.clans.cmd;

import me.rabrg.clans.Clan;
import me.rabrg.clans.struct.Permission;
import me.rabrg.clans.struct.Role;

public class CmdAutoClaim extends FCommand {
	public CmdAutoClaim() {
		super();
		this.aliases.add("autoclaim");

		// this.requiredArgs.add("");
		this.optionalArgs.put("clan", "your");

		this.permission = Permission.AUTOCLAIM.node;
		this.disableOnLock = true;

		senderMustBePlayer = true;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		final Clan forClan = this.argAsClan(0, myClan);
		if (forClan == null || forClan == fme.getAutoClaimFor()) {
			fme.setAutoClaimFor(null);
			msg("<i>Auto-claiming of land disabled.");
			return;
		}

		if (!fme.canClaimForClan(forClan)) {
			if (myClan == forClan) {
				msg("<b>You must be <h>%s<b> to claim land.", Role.MODERATOR.toString());
			} else {
				msg("<b>You can't claim land for <h>%s<b>.", forClan.describeTo(fme));
			}

			return;
		}

		fme.setAutoClaimFor(forClan);

		msg("<i>Now auto-claiming land for <h>%s<i>.", forClan.describeTo(fme));
		fme.attemptClaim(forClan, me.getLocation(), true);
	}

}