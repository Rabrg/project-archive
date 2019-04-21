package me.rabrg.clans.cmd;

import me.rabrg.clans.Board;
import me.rabrg.clans.CLocation;
import me.rabrg.clans.CPlayer;
import me.rabrg.clans.Clan;
import me.rabrg.clans.Conf;
import me.rabrg.clans.integration.SpoutFeatures;
import me.rabrg.clans.struct.Permission;
import me.rabrg.clans.struct.Role;

public class CmdOwner extends FCommand {

	public CmdOwner() {
		super();
		this.aliases.add("owner");

		// this.requiredArgs.add("");
		this.optionalArgs.put("player name", "you");

		this.permission = Permission.OWNER.node;
		this.disableOnLock = true;

		senderMustBePlayer = true;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	// TODO: Fix colors!

	@Override
	public void perform() {
		final boolean hasBypass = fme.isAdminBypassing();

		if (!hasBypass && !assertHasClan()) {
			return;
		}

		if (!Conf.ownedAreasEnabled) {
			fme.msg("<b>Sorry, but owned areas are disabled on this server.");
			return;
		}

		if (!hasBypass && Conf.ownedAreasLimitPerClan > 0 && myClan.getCountOfClaimsWithOwners() >= Conf.ownedAreasLimitPerClan) {
			fme.msg("<b>Sorry, but you have reached the server's <h>limit of %d <b>owned areas per clan.", Conf.ownedAreasLimitPerClan);
			return;
		}

		if (!hasBypass && !assertMinRole(Conf.ownedAreasModeratorsCanSet ? Role.MODERATOR : Role.ADMIN)) {
			return;
		}

		final CLocation flocation = new CLocation(fme);

		final Clan clanHere = Board.getClanAt(flocation);
		if (clanHere != myClan) {
			if (!hasBypass) {
				fme.msg("<b>This land is not claimed by your clan, so you can't set ownership of it.");
				return;
			}

			if (!clanHere.isNormal()) {
				fme.msg("<b>This land is not claimed by a clan. Ownership is not possible.");
				return;
			}
		}

		final CPlayer target = this.argAsBestCPlayerMatch(0, fme);
		if (target == null) {
			return;
		}

		final String playerName = target.getName();

		if (target.getClan() != myClan) {
			fme.msg("%s<i> is not a member of this clan.", playerName);
			return;
		}

		// if no player name was passed, and this claim does already have owners
		// set, clear them
		if (args.isEmpty() && myClan.doesLocationHaveOwnersSet(flocation)) {
			myClan.clearClaimOwnership(flocation);
			SpoutFeatures.updateOwnerListLoc(flocation);
			fme.msg("<i>You have cleared ownership for this claimed area.");
			return;
		}

		if (myClan.isPlayerInOwnerList(playerName, flocation)) {
			myClan.removePlayerAsOwner(playerName, flocation);
			SpoutFeatures.updateOwnerListLoc(flocation);
			fme.msg("<i>You have removed ownership of this claimed land from %s<i>.", playerName);
			return;
		}

		// if economy is enabled, they're not on the bypass list, and this
		// command has a cost set, make 'em pay
		if (!payForCommand(Conf.econCostOwner, "to set ownership of claimed land", "for setting ownership of claimed land")) {
			return;
		}

		myClan.setPlayerAsOwner(playerName, flocation);
		SpoutFeatures.updateOwnerListLoc(flocation);

		fme.msg("<i>You have added %s<i> to the owner list for this claimed land.", playerName);
	}
}
