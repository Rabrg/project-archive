package me.rabrg.clans.cmd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import me.rabrg.clans.Clan;
import me.rabrg.clans.Conf;
import me.rabrg.clans.event.ClanRelationEvent;
import me.rabrg.clans.integration.SpoutFeatures;
import me.rabrg.clans.struct.Permission;
import me.rabrg.clans.struct.Relation;

public abstract class FRelationCommand extends FCommand {
	public Relation targetRelation;

	public FRelationCommand() {
		super();
		this.requiredArgs.add("clan tag");
		// this.optionalArgs.put("player name", "you");

		this.permission = Permission.RELATION.node;
		this.disableOnLock = true;

		senderMustBePlayer = true;
		senderMustBeMember = false;
		senderMustBeModerator = true;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		final Clan them = this.argAsClan(0);
		if (them == null) {
			return;
		}

		if (!them.isNormal()) {
			msg("<b>Nope! You can't.");
			return;
		}

		if (them == myClan) {
			msg("<b>Nope! You can't declare a relation to yourself :)");
			return;
		}

		if (myClan.getRelationWish(them) == targetRelation) {
			msg("<b>You already have that relation wish set with %s.", them.getTag());
			return;
		}

		// if economy is enabled, they're not on the bypass list, and this
		// command has a cost set, make 'em pay
		if (!payForCommand(targetRelation.getRelationCost(), "to change a relation wish", "for changing a relation wish")) {
			return;
		}

		// try to set the new relation
		final Relation oldRelation = myClan.getRelationTo(them, true);
		myClan.setRelationWish(them, targetRelation);
		final Relation currentRelation = myClan.getRelationTo(them, true);
		final ChatColor currentRelationColor = currentRelation.getColor();

		// if the relation change was successful
		if (targetRelation.value == currentRelation.value) {
			// trigger the clan relation event
			final ClanRelationEvent relationEvent = new ClanRelationEvent(myClan, them, oldRelation, currentRelation);
			Bukkit.getServer().getPluginManager().callEvent(relationEvent);

			them.msg("<i>Your clan is now " + currentRelationColor + targetRelation.toString() + "<i> to " + currentRelationColor + myClan.getTag());
			myClan.msg("<i>Your clan is now " + currentRelationColor + targetRelation.toString() + "<i> to " + currentRelationColor + them.getTag());
		}
		// inform the other clan of your request
		else {
			them.msg(currentRelationColor + myClan.getTag() + "<i> wishes to be your " + targetRelation.getColor() + targetRelation.toString());
			them.msg("<i>Type <c>/" + Conf.baseCommandAliases.get(0) + " " + targetRelation + " " + myClan.getTag() + "<i> to accept.");
			myClan.msg(currentRelationColor + them.getTag() + "<i> were informed that you wish to be " + targetRelation.getColor() + targetRelation);
		}

		if (!targetRelation.isNeutral() && them.isPeaceful()) {
			them.msg("<i>This will have no effect while your clan is peaceful.");
			myClan.msg("<i>This will have no effect while their clan is peaceful.");
		}

		if (!targetRelation.isNeutral() && myClan.isPeaceful()) {
			them.msg("<i>This will have no effect while their clan is peaceful.");
			myClan.msg("<i>This will have no effect while your clan is peaceful.");
		}

		SpoutFeatures.updateAppearances(myClan, them);
		SpoutFeatures.updateTerritoryDisplayLoc(null);
	}
}
