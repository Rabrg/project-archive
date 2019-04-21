package me.rabrg.cc;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.spoony.JSONChatLib.JSONChatColor;
import me.spoony.JSONChatLib.JSONChatExtra;
import me.spoony.JSONChatLib.JSONChatFormat;
import me.spoony.JSONChatLib.JSONChatMessage;

import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

public class MessageSender {
	public List<ChatPart> chatparts;

	public static void sendMessage(final Player receiver, final ChatPart[] parts) {
		final JSONChatMessage message = new JSONChatMessage("",
				JSONChatColor.WHITE, null);
		for (final ChatPart part : parts) {
			if (!part.getText().equalsIgnoreCase("") && part.getText() != null) {
				JSONChatExtra extra;
				if (part.getFormat() == null) {
					extra = new JSONChatExtra(part.getText(), part.getColor(),
							Arrays.asList(new JSONChatFormat[0]));
				} else {
					extra = new JSONChatExtra(part.getText(), part.getColor(),
							part.getFormat());
				}
				if (part.getClicktype() != null) {
					extra.setClickEvent(part.getClicktype(),
							part.getClickdata());
				}
				if (part.getHovertype() != null) {
					extra.setHoverEvent(part.getHovertype(),
							part.getHoverdata());
				}
				message.addExtra(extra);
			}
		}
		message.sendToPlayer(receiver);
	}

	public static void sendRawMessage(final Player receiver, final String json) {
		final ProtocolManager manager = ProtocolLibrary.getProtocolManager();
		final PacketContainer messagePacket = manager
				.createPacket(PacketType.Play.Server.CHAT);
		messagePacket.getChatComponents().write(0,
				WrappedChatComponent.fromJson(json));
		try {
			manager.sendServerPacket(receiver, messagePacket);
		} catch (final InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public MessageSender() {
		this.chatparts = new ArrayList<ChatPart>();
	}

	public void addChatPart(final ChatPart part) {
		if (part == null) {
			return;
		}
		this.chatparts.add(part);
	}

	public void sendMessage(final Player receiver) {
		final JSONChatMessage message = new JSONChatMessage("",
				JSONChatColor.WHITE, null);
		for (final ChatPart part : this.chatparts) {
			if (!part.getText().equalsIgnoreCase("") && part.getText() != null) {
				JSONChatExtra extra;
				if (part.getFormat() == null) {
					extra = new JSONChatExtra(part.getText(), part.getColor(),
							Arrays.asList(new JSONChatFormat[0]));
				} else {
					extra = new JSONChatExtra(part.getText(), part.getColor(),
							part.getFormat());
				}
				if (part.getClicktype() != null) {
					extra.setClickEvent(part.getClicktype(),
							part.getClickdata());
				}
				if (part.getHovertype() != null) {
					extra.setHoverEvent(part.getHovertype(),
							part.getHoverdata());
				}
				message.addExtra(extra);
			}
		}
		message.sendToPlayer(receiver);
	}
}
