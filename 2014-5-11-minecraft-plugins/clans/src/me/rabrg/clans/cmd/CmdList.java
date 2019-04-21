package me.rabrg.clans.cmd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import me.rabrg.clans.Clan;
import me.rabrg.clans.Clans;
import me.rabrg.clans.Conf;
import me.rabrg.clans.struct.Permission;

public class CmdList extends FCommand {

	public CmdList() {
		super();
		this.aliases.add("list");
		this.aliases.add("ls");

		// this.requiredArgs.add("");
		this.optionalArgs.put("page", "1");

		this.permission = Permission.LIST.node;
		this.disableOnLock = false;

		senderMustBePlayer = false;
		senderMustBeMember = false;
		senderMustBeModerator = false;
		senderMustBeAdmin = false;
	}

	@Override
	public void perform() {
		// if economy is enabled, they're not on the bypass list, and this
		// command has a cost set, make 'em pay
		if (!payForCommand(Conf.econCostList, "to list the clans", "for listing the clans")) {
			return;
		}

		final ArrayList<Clan> clanList = new ArrayList<Clan>(Clans.i.get());
		clanList.remove(Clans.i.getNone());
		clanList.remove(Clans.i.getSafeZone());
		clanList.remove(Clans.i.getWarZone());

		// Sort by total followers first
		Collections.sort(clanList, new Comparator<Clan>() {
			@Override
			public int compare(final Clan f1, final Clan f2) {
				final int f1Size = f1.getCPlayers().size();
				final int f2Size = f2.getCPlayers().size();
				if (f1Size < f2Size) {
					return 1;
				} else if (f1Size > f2Size) {
					return -1;
				}
				return 0;
			}
		});

		// Then sort by how many members are online now
		Collections.sort(clanList, new Comparator<Clan>() {
			@Override
			public int compare(final Clan f1, final Clan f2) {
				final int f1Size = f1.getCPlayersWhereOnline(true).size();
				final int f2Size = f2.getCPlayersWhereOnline(true).size();
				if (f1Size < f2Size) {
					return 1;
				} else if (f1Size > f2Size) {
					return -1;
				}
				return 0;
			}
		});

		final ArrayList<String> lines = new ArrayList<String>();

		/*
		 * // this code was really slow on large servers, getting full info for
		 * every clan and then only showing 9 of them; rewritten below
		 * lines.add(p.txt.parse("<i>Clanless<i> %d online",
		 * Clans.i.getNone().getCPlayersWhereOnline(true).size())); for (Clan
		 * clan : clanList) {
		 * lines.add(p.txt.parse("%s<i> %d/%d online, %d/%d/%d",
		 * clan.getTag(fme), clan.getCPlayersWhereOnline(true).size(),
		 * clan.getCPlayers().size(), clan.getLandRounded(),
		 * clan.getPowerRounded(), clan.getPowerMaxRounded()) ); }
		 * 
		 * sendMessage(p.txt.getPage(lines, this.argAsInt(0, 1), "Clan List"));
		 */

		clanList.add(0, Clans.i.getNone());

		final int pageheight = 9;
		int pagenumber = this.argAsInt(0, 1);
		final int pagecount = (clanList.size() / pageheight) + 1;
		if (pagenumber > pagecount) {
			pagenumber = pagecount;
		} else if (pagenumber < 1) {
			pagenumber = 1;
		}
		final int start = (pagenumber - 1) * pageheight;
		int end = start + pageheight;
		if (end > clanList.size()) {
			end = clanList.size();
		}

		lines.add(p.txt.titleize("Clan List " + pagenumber + "/" + pagecount));

		for (final Clan clan : clanList.subList(start, end)) {
			if (clan.isNone()) {
				lines.add(p.txt.parse("<i>Clanless<i> %d online", Clans.i.getNone().getCPlayersWhereOnline(true).size()));
				continue;
			}
			lines.add(p.txt.parse("%s<i> %d/%d online, %d/%d/%d", clan.getTag(fme), clan.getCPlayersWhereOnline(true).size(), clan.getCPlayers().size(),
					clan.getLandRounded(), clan.getPowerRounded(), clan.getPowerMaxRounded()));
		}

		sendMessage(lines);
	}
}
