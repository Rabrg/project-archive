package me.rabrg.clans.util;

import org.bukkit.ChatColor;

import me.rabrg.clans.CPlayer;
import me.rabrg.clans.Clan;
import me.rabrg.clans.Conf;
import me.rabrg.clans.iface.RelationParticipator;
import me.rabrg.clans.struct.Relation;
import me.rabrg.clans.zcore.util.TextUtil;

public class RelationUtil {
	public static String describeThatToMe(final RelationParticipator that, final RelationParticipator me, final boolean ucfirst) {
		String ret = "";

		final Clan thatClan = getClan(that);
		if (thatClan == null) {
			return "ERROR"; // ERROR
		}

		final Clan myClan = getClan(me);
		// if (myClan == null) return that.describeTo(null); // no relation, but
		// can show basic name or tag

		if (that instanceof Clan) {
			if (me instanceof CPlayer && myClan == thatClan) {
				ret = "your clan";
			} else {
				ret = thatClan.getTag();
			}
		} else if (that instanceof CPlayer) {
			final CPlayer fplayerthat = (CPlayer) that;
			if (that == me) {
				ret = "you";
			} else if (thatClan == myClan) {
				ret = fplayerthat.getNameAndTitle();
			} else {
				ret = fplayerthat.getNameAndTag();
			}
		}

		if (ucfirst) {
			ret = TextUtil.upperCaseFirst(ret);
		}

		return "" + getColorOfThatToMe(that, me) + ret;
	}

	public static String describeThatToMe(final RelationParticipator that, final RelationParticipator me) {
		return describeThatToMe(that, me, false);
	}

	public static Relation getRelationTo(final RelationParticipator me, final RelationParticipator that) {
		return getRelationTo(that, me, false);
	}

	public static Relation getRelationTo(final RelationParticipator me, final RelationParticipator that, final boolean ignorePeaceful) {
		final Clan fthat = getClan(that);
		if (fthat == null) {
			return Relation.NEUTRAL; // ERROR
		}

		final Clan fme = getClan(me);
		if (fme == null) {
			return Relation.NEUTRAL; // ERROR
		}

		if (!fthat.isNormal() || !fme.isNormal()) {
			return Relation.NEUTRAL;
		}

		if (fthat.equals(fme)) {
			return Relation.MEMBER;
		}

		if (!ignorePeaceful && (fme.isPeaceful() || fthat.isPeaceful())) {
			return Relation.NEUTRAL;
		}

		if (fme.getRelationWish(fthat).value >= fthat.getRelationWish(fme).value) {
			return fthat.getRelationWish(fme);
		}

		return fme.getRelationWish(fthat);
	}

	public static Clan getClan(final RelationParticipator rp) {
		if (rp instanceof Clan) {
			return (Clan) rp;
		}

		if (rp instanceof CPlayer) {
			return ((CPlayer) rp).getClan();
		}

		// ERROR
		return null;
	}

	public static ChatColor getColorOfThatToMe(final RelationParticipator that, final RelationParticipator me) {
		final Clan thatClan = getClan(that);
		if (thatClan != null) {
			if (thatClan.isPeaceful() && thatClan != getClan(me)) {
				return Conf.colorPeaceful;
			}

			if (thatClan.isSafeZone() && thatClan != getClan(me)) {
				return Conf.colorPeaceful;
			}

			if (thatClan.isWarZone() && thatClan != getClan(me)) {
				return Conf.colorWar;
			}
		}

		return getRelationTo(that, me).getColor();
	}
}
