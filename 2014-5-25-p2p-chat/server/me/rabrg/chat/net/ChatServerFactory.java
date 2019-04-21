package me.rabrg.chat.net;

import java.io.IOException;
import java.net.InetSocketAddress;

public final class ChatServerFactory {

	public static ChatServer createChatServer(final InetSocketAddress inetSocketAddress) throws IOException {
		return new ChatServer(inetSocketAddress);
	}

	public static ChatServer createChatServer(final int port) throws IOException {
		return new ChatServer(new InetSocketAddress(port));
	}

	public static ChatServer createChatServer(final String address, final int port) throws IOException {
		return new ChatServer(new InetSocketAddress(address, port));
	}
}
