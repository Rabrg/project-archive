package me.rabrg.chat;

import java.io.IOException;

import me.rabrg.chat.net.ChatServer;
import me.rabrg.chat.net.ChatServerFactory;

public final class ChatServerApplication {

	public static void main(final String[] args) {
		try {
			final ChatServer chatServer = ChatServerFactory.createChatServer(43594);
			chatServer.start();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
