package me.rabrg.chat;

import java.io.IOException;
import java.net.InetSocketAddress;

import me.rabrg.chat.net.ChatClient;

public final class ChatClientApplication {

	public static void main(final String[] args) {
		try {
			final ChatClient chatClient = new ChatClient(new InetSocketAddress("localhost", 43594));
			chatClient.start();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

}
