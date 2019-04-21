package me.rabrg.clans.listeners;

import java.util.UnknownFormatConversionException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import me.rabrg.clans.CPlayer;
import me.rabrg.clans.CPlayers;
import me.rabrg.clans.Clan;
import me.rabrg.clans.Conf;
import me.rabrg.clans.P;
import me.rabrg.clans.struct.ChatMode;
import me.rabrg.clans.struct.Relation;

public class ClansChatListener implements Listener {
	public P p;

	public ClansChatListener(final P p) {
		this.p = p;
	}

	// this is for handling slashless command usage and clan/alliance chat, set
	// at lowest priority so Clans gets to them first
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerEarlyChat(final PlayerChatEvent event) {
		if (event.isCancelled()) {
			return;
		}

		final Player talkingPlayer = event.getPlayer();
		final String msg = event.getMessage();
		final CPlayer me = CPlayers.i.get(talkingPlayer);
		final ChatMode chat = me.getChatMode();

		// slashless clans commands need to be handled here if the user isn't in
		// public chat mode
		if (chat != ChatMode.PUBLIC && p.handleCommand(talkingPlayer, msg, false, true)) {
			if (Conf.logPlayerCommands) {
				Bukkit.getLogger().log(Level.INFO, "[PLAYER_COMMAND] " + talkingPlayer.getName() + ": " + msg);
			}
			event.setCancelled(true);
			return;
		}

		// Is it a clan chat message?
		if (chat == ChatMode.FACTION) {
			final Clan myClan = me.getClan();

			final String message = String.format(Conf.clanChatFormat, me.describeTo(myClan), msg);
			myClan.sendMessage(message);

			Bukkit.getLogger().log(Level.INFO, ChatColor.stripColor("ClanChat " + myClan.getTag() + ": " + message));

			// Send to any players who are spying chat
			for (final CPlayer fplayer : CPlayers.i.getOnline()) {
				if (fplayer.isSpyingChat() && fplayer.getClan() != myClan) {
					fplayer.sendMessage("[FCspy] " + myClan.getTag() + ": " + message);
				}
			}

			event.setCancelled(true);
			return;
		} else if (chat == ChatMode.ALLIANCE) {
			final Clan myClan = me.getClan();

			final String message = String.format(Conf.allianceChatFormat, ChatColor.stripColor(me.getNameAndTag()), msg);

			// Send message to our own clan
			myClan.sendMessage(message);

			// Send to all our allies
			for (final CPlayer fplayer : CPlayers.i.getOnline()) {
				if (myClan.getRelationTo(fplayer) == Relation.ALLY) {
					fplayer.sendMessage(message);
				} else if (fplayer.isSpyingChat()) {
					fplayer.sendMessage("[ACspy]: " + message);
				}
			}

			Bukkit.getLogger().log(Level.INFO, ChatColor.stripColor("AllianceChat: " + message));

			event.setCancelled(true);
			return;
		}
	}

	// this is for handling insertion of the player's clan tag, set at highest
	// priority to give other plugins a chance to modify chat first
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(final PlayerChatEvent event) {
		if (event.isCancelled()) {
			return;
		}

		// Are we to insert the Clan tag into the format?
		// If we are not to insert it - we are done.
		if (!Conf.chatTagEnabled || Conf.chatTagHandledByAnotherPlugin) {
			return;
		}

		final Player talkingPlayer = event.getPlayer();
		final String msg = event.getMessage();
		String eventFormat = event.getFormat();
		final CPlayer me = CPlayers.i.get(talkingPlayer);
		int InsertIndex = 0;

		if (!Conf.chatTagReplaceString.isEmpty() && eventFormat.contains(Conf.chatTagReplaceString)) {
			// we're using the "replace" method of inserting the clan tags
			// if they stuck "[FACTION_TITLE]" in there, go ahead and do it too
			if (eventFormat.contains("[FACTION_TITLE]")) {
				eventFormat = eventFormat.replace("[FACTION_TITLE]", me.getTitle());
			}
			InsertIndex = eventFormat.indexOf(Conf.chatTagReplaceString);
			eventFormat = eventFormat.replace(Conf.chatTagReplaceString, "");
			Conf.chatTagPadAfter = false;
			Conf.chatTagPadBefore = false;
		} else if (!Conf.chatTagInsertAfterString.isEmpty() && eventFormat.contains(Conf.chatTagInsertAfterString)) {
			// we're using the "insert after string" method
			InsertIndex = eventFormat.indexOf(Conf.chatTagInsertAfterString) + Conf.chatTagInsertAfterString.length();
		} else if (!Conf.chatTagInsertBeforeString.isEmpty() && eventFormat.contains(Conf.chatTagInsertBeforeString)) {
			// we're using the "insert before string" method
			InsertIndex = eventFormat.indexOf(Conf.chatTagInsertBeforeString);
		} else {
			// we'll fall back to using the index place method
			InsertIndex = Conf.chatTagInsertIndex;
			if (InsertIndex > eventFormat.length()) {
				return;
			}
		}

		final String formatStart = eventFormat.substring(0, InsertIndex) + ((Conf.chatTagPadBefore && !me.getChatTag().isEmpty()) ? " " : "");
		final String formatEnd = ((Conf.chatTagPadAfter && !me.getChatTag().isEmpty()) ? " " : "") + eventFormat.substring(InsertIndex);

		final String nonColoredMsgFormat = formatStart + me.getChatTag().trim() + formatEnd;

		// Relation Colored?
		if (Conf.chatTagRelationColored) {
			// We must choke the standard message and send out individual
			// messages to all players
			// Why? Because the relations will differ.
			event.setCancelled(true);

			for (final Player listeningPlayer : event.getRecipients()) {
				final CPlayer you = CPlayers.i.get(listeningPlayer);
				final String yourFormat = formatStart + me.getChatTag(you).trim() + formatEnd;
				try {
					listeningPlayer.sendMessage(String.format(yourFormat, talkingPlayer.getDisplayName(), msg));
				} catch (final UnknownFormatConversionException ex) {
					Conf.chatTagInsertIndex = 0;
					P.p.log(Level.SEVERE, "Critical error in chat message formatting!");
					P.p.log(Level.SEVERE, "NOTE: This has been automatically fixed right now by setting chatTagInsertIndex to 0.");
					P.p.log(Level.SEVERE,
							"For a more proper fix, please read this regarding chat configuration: http://massivecraft.com/plugins/clans/config#Chat_configuration");
					return;
				}
			}

			// Write to the log... We will write the non colored message.
			final String nonColoredMsg = ChatColor.stripColor(String.format(nonColoredMsgFormat, talkingPlayer.getDisplayName(), msg));
			Bukkit.getLogger().log(Level.INFO, nonColoredMsg);
		} else {
			// No relation color.
			event.setFormat(nonColoredMsgFormat);
		}
	}
}
