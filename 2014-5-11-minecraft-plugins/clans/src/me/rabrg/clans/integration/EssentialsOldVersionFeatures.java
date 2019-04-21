package me.rabrg.clans.integration;

import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.earth2me.essentials.chat.EssentialsChat;
import com.earth2me.essentials.chat.IEssentialsChatListener;
import me.rabrg.clans.Conf;
import me.rabrg.clans.P;

/*
 * This Essentials integration handler is for older 2.x.x versions of Essentials which have "IEssentialsChatListener"
 */

public class EssentialsOldVersionFeatures {
	private static EssentialsChat essChat;

	public static void integrateChat(final EssentialsChat instance) {
		essChat = instance;
		try {
			essChat.addEssentialsChatListener("Clans", new IEssentialsChatListener() {
				@Override
				public boolean shouldHandleThisChat(final AsyncPlayerChatEvent event) {
					return P.p.shouldLetClansHandleThisChat(event);
				}

				@Override
				public String modifyMessage(final AsyncPlayerChatEvent event, final Player target, final String message) {
					return message.replace(Conf.chatTagReplaceString, P.p.getPlayerClanTagRelation(event.getPlayer(), target)).replace("[FACTION_TITLE]",
							P.p.getPlayerTitle(event.getPlayer()));
				}
			});
			P.p.log("Found and will integrate chat with " + essChat.getDescription().getFullName());

			// As of Essentials 2.8+, curly braces are not accepted and are
			// instead replaced with square braces, so... deal with it
			if (essChat.getDescription().getVersion().startsWith("2.8.") && Conf.chatTagReplaceString.contains("{")) {
				Conf.chatTagReplaceString = Conf.chatTagReplaceString.replace("{", "[").replace("}", "]");
				P.p.log("NOTE: as of Essentials 2.8+, we've had to switch the default chat replacement tag from \"{FACTION}\" to \"[FACTION]\". This has automatically been updated for you.");
			}
		} catch (final NoSuchMethodError ex) {
			essChat = null;
		}
	}

	public static void unhookChat() {
		if (essChat != null) {
			essChat.removeEssentialsChatListener("Clans");
		}
	}
}
