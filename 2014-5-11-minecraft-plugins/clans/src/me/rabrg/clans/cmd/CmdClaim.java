package me.rabrg.clans.cmd;

import me.rabrg.clans.CLocation;
import me.rabrg.clans.Clan;
import me.rabrg.clans.Conf;
import me.rabrg.clans.struct.Permission;
import me.rabrg.clans.util.SpiralTask;

public class CmdClaim extends FCommand {

	public CmdClaim() {
		super();
		this.aliases.add("claim");

		// this.requiredArgs.add("");
		this.optionalArgs.put("clan", "your");
		this.optionalArgs.put("radius", "1");

		this.permission = Permission.CLAIM.node;
		this.disableOnLock = true;

		senderMustBePlayer = true;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		// Read and validate input
		final Clan forClan = this.argAsClan(0, myClan);
		final int radius = this.argAsInt(1, 1);

		if (radius < 1) {
			msg("<b>If you specify a radius, it must be at least 1.");
			return;
		}

		if (radius < 2) {
			// single chunk
			fme.attemptClaim(forClan, me.getLocation(), true);
		} else {
			// radius claim
			if (!Permission.CLAIM_RADIUS.has(sender, false)) {
				msg("<b>You do not have permission to claim in a radius.");
				return;
			}

			new SpiralTask(new CLocation(me), radius) {
				private int failCount = 0;
				private final int limit = Conf.radiusClaimFailureLimit - 1;

				@Override
				public boolean work() {
					final boolean success = fme.attemptClaim(forClan, this.currentLocation(), true);
					if (success) {
						failCount = 0;
					} else if (!success && failCount++ >= limit) {
						this.stop();
						return false;
					}

					return true;
				}
			};
		}
	}

}
