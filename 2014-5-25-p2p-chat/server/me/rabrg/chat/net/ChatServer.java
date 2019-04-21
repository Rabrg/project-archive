package me.rabrg.chat.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public final class ChatServer implements Runnable {

	private final InetSocketAddress inetSocketAddress;

	private final ServerSocketChannel serverSocketChannel;

	private final Selector selector;

	private volatile boolean running;

	protected ChatServer(final InetSocketAddress inetSocketAddress) throws IOException {
		this.inetSocketAddress = inetSocketAddress;
		
		selector = Selector.open();
		
		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.bind(inetSocketAddress);
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
	}

	public void start() {
		running = true;
		new Thread(this).start();
	}

	@Override
	public void run() {
		try {
			while(running) {
				selector.selectNow();
				for (final SelectionKey selectionKey : selector.selectedKeys()) {
					if (selectionKey.isValid()) {
						if (selectionKey.isAcceptable()) {
							final SocketChannel socketChannel = serverSocketChannel.accept();
							if (socketChannel != null) {
								socketChannel.configureBlocking(false);
								sendMessage(socketChannel.register(selector, SelectionKey.OP_READ), "Welcome to the server."); // TODO: message constant
							}
						}
						if (selectionKey.isReadable()) {
							final ByteBuffer messageByteBuffer = ByteBuffer.allocate(512);
							((SocketChannel) selectionKey.channel()).read(messageByteBuffer);
							final String message = new String(messageByteBuffer.array(), StandardCharsets.UTF_8);
							broadcastMessage(message);
							System.out.println(message);
						}
					}
				}
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public void broadcastMessage(final String message) throws IOException {
		final ByteBuffer messageByteBuffer = ByteBuffer.wrap(message.getBytes());
		System.out.println(selector.keys());
		for (final SelectionKey selectionKey : selector.keys()) {
			if (selectionKey.isValid() && !(selectionKey.channel() instanceof ServerSocketChannel)) {
				sendMessage(selectionKey, (ByteBuffer) messageByteBuffer.rewind());
			}
		}
	}

	public void sendMessage(final SelectionKey selectionKey, final String message) throws IOException {
		sendMessage(selectionKey, ByteBuffer.wrap(message.getBytes()));
	}

	public void sendMessage(final SelectionKey selectionKey, final ByteBuffer message) throws IOException {
		((SocketChannel) selectionKey.channel()).write(message);
	}

	public void stop() {
		running = false;
	}

	public InetSocketAddress getInetSocketAddress() {
		return inetSocketAddress;
	}
}
