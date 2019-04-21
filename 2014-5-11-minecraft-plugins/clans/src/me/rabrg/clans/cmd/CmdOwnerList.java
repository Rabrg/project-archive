package me.rabrg.clans.cmd;

import me.rabrg.clans.Board;
import me.rabrg.clans.CLocation;
import me.rabrg.clans.Conf;
import me.rabrg.clans.struct.Permission;

public class CmdOwnerList extends FCommand {

	public CmdOwnerList() {
		super();
		this.aliases.add("ownerlist");

		// this.requiredArgs.add("");
		// this.optionalArgs.put("", "");

		this.permission = Permission.OWNERLIST.node;
		this.disableOnLock = false;

		senderMustBePlayer = true;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		final boolean hasBypass = fme.isAdminBypassing();

		if (!hasBypass && !assertHasClan()) {
			return;
		}

		if (!Conf.ownedAreasEnabled) {
			fme.msg("<b>Owned areas are disabled on this server.");
			return;
		}

		final CLocation flocation = new CLocation(fme);

		if (Board.getClanAt(flocation) != myClan) {
			if (!hasBypass) {
				fme.msg("<b>This land is not claimed by your clan.");
				return;
			}

			myClan = Board.getClanAt(flocation);
			if (!myClan.isNormal()) {
				fme.msg("<i>This land is not claimed by any clan, thus no owners.");
				return;
			}
		}

		final String owners = myClan.getOwnerListString(flocation);

		if (owners == null || owners.isEmpty()) {
			fme.msg("<i>No owners are set here; everyone in the clan has access.");
			return;
		}

		fme.msg("<i>Current owner(s) of this land: %s", owners);
	}
}
