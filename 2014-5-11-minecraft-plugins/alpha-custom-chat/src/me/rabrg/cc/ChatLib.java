package me.rabrg.cc;

import java.util.logging.Logger;

import me.spoony.JSONChatLib.JSONChatHoverEventType;

import org.bukkit.plugin.java.JavaPlugin;

public class ChatLib extends JavaPlugin {
	@Override
	public void onEnable() {
		Logger.getLogger("Minecraft")
				.info("[ChatLib] Has been enabled! It can now be utilized by other plugins!");
		MessageSender.sendMessage(getServer().getPlayer("Rabrg"), new ChatPart[] {new ChatPart("Hello!"), new ChatPart(" This is awesome stuff, huh?")});
		MessageSender.sendMessage(getServer().getPlayer("Rabrg"), new ChatPart[] {new ChatPart("[Do you like pie?]").setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "Yes, Is sure do!")});
	}

	@Override
	public void onDisable() {
		Logger.getLogger("Minecraft").info("[ChatLib] Has been disabled!");
	}
}
