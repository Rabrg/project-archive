package me.rabrg.clans.integration.capi;

import java.util.LinkedHashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.massivecraft.capi.Channel;
import com.massivecraft.capi.Channels;
import com.massivecraft.capi.events.CAPIListChannelsEvent;
import com.massivecraft.capi.events.CAPIMessageToChannelEvent;
import com.massivecraft.capi.events.CAPIMessageToPlayerEvent;
import com.massivecraft.capi.events.CAPISelectChannelEvent;
import me.rabrg.clans.CPlayer;
import me.rabrg.clans.CPlayers;
import me.rabrg.clans.Clan;
import me.rabrg.clans.P;
import me.rabrg.clans.struct.Relation;

public class PluginCapiListener implements Listener {
	P p;

	Set<String> myChannelIds = new LinkedHashSet<String>();

	public PluginCapiListener(final P p) {
		this.p = p;

		myChannelIds.add("clan");
		myChannelIds.add("allies");
	}

	private String replacePlayerTags(String format, final CPlayer me, final CPlayer you) {
		final String meClanTag = me.getChatTag(you);
		format = format.replace("{ME_FACTIONTAG}", meClanTag.length() == 0 ? "" : meClanTag);
		format = format.replace("{ME_FACTIONTAG_PADR}", meClanTag.length() == 0 ? "" : meClanTag + " ");
		format = format.replace("{ME_FACTIONTAG_PADL}", meClanTag.length() == 0 ? "" : " " + meClanTag);
		format = format.replace("{ME_FACTIONTAG_PADB}", meClanTag.length() == 0 ? "" : " " + meClanTag + " ");

		final String youClanTag = you.getChatTag(me);
		format = format.replace("{YOU_FACTIONTAG}", youClanTag.length() == 0 ? "" : youClanTag);
		format = format.replace("{YOU_FACTIONTAG_PADR}", youClanTag.length() == 0 ? "" : youClanTag + " ");
		format = format.replace("{YOU_FACTIONTAG_PADL}", youClanTag.length() == 0 ? "" : " " + youClanTag);
		format = format.replace("{YOU_FACTIONTAG_PADB}", youClanTag.length() == 0 ? "" : " " + youClanTag + " ");

		return format;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onListChannelsEvent(final CAPIListChannelsEvent event) {
		for (final Channel c : Channels.i.getAll()) {
			if (myChannelIds.contains(c.getId())) {
				event.getChannels().add(c);
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onMessageToChannel(final CAPIMessageToChannelEvent event) {
		if (event.isCancelled()) {
			return;
		}
		if (!myChannelIds.contains(event.getChannel().getId())) {
			return;
		}

		final Player me = event.getMe();
		final CPlayer fme = CPlayers.i.get(me);
		final Clan myClan = fme.getClan();

		if (event.getChannel().getId().equals("clan") && myClan.isNormal()) {
			event.getThem().addAll(myClan.getOnlinePlayers());

			// Send to any players who are spying chat... could probably be
			// implemented better than this
			for (final CPlayer fplayer : CPlayers.i.getOnline()) {
				if (fplayer.isSpyingChat() && fplayer.getClan() != myClan) {
					fplayer.sendMessage("[FCspy] " + myClan.getTag() + ": " + event.getMessage());
				}
			}
		} else if (event.getChannel().getId().equals("allies")) {
			for (final Player somePlayer : Bukkit.getServer().getOnlinePlayers()) {
				final CPlayer someCPlayer = CPlayers.i.get(somePlayer);
				if (someCPlayer.getRelationTo(fme).isAtLeast(Relation.ALLY)) {
					event.getThem().add(somePlayer);
				} else if (someCPlayer.isSpyingChat()) {
					someCPlayer.sendMessage("[ACspy]: " + event.getMessage());
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onMessageToPlayer(final CAPIMessageToPlayerEvent event) {
		if (event.isCancelled()) {
			return;
		}
		event.setFormat(this.replacePlayerTags(event.getFormat(), CPlayers.i.get(event.getMe()), CPlayers.i.get(event.getYou())));
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onSelectChannel(final CAPISelectChannelEvent event) {
		if (event.isCancelled()) {
			return;
		}
		final String channelId = event.getChannel().getId();
		if (!myChannelIds.contains(channelId)) {
			return;
		}

		final Player me = event.getMe();
		final CPlayer fme = CPlayers.i.get(me);

		if (!fme.hasClan()) {
			event.setFailMessage(p.txt.parse("<b>You must be member in a clan to use this channel."));
			event.setCancelled(true);
		}
	}
}
