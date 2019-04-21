package me.rabrg.chat.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public final class ChatClient implements Runnable {

	private final InetSocketAddress inetSocketAddress;

	private final Socket socket;

	private final InputStream inputStream;

	private final OutputStream outputStream;

	private volatile boolean running;

	public ChatClient(final InetSocketAddress inetSocketAddress) throws IOException {
		this.inetSocketAddress = inetSocketAddress;
		
		socket = new Socket();
		socket.connect(inetSocketAddress);
		
		inputStream = socket.getInputStream();
		outputStream = socket.getOutputStream();
	}

	public void start() {
		running = true;
		new Thread(this).start();
	}
	@Override
	public void run() {
		try {
			while(running) {
				if (inputStream.available() > 0) {
					final byte[] messageByteArray = new byte[512];
					inputStream.read(messageByteArray);
					final String message = new String(messageByteArray, StandardCharsets.UTF_8);
					System.out.println(message);
					
					outputStream.write((message + "1").getBytes());
					System.out.println((message + "1"));
				}
			}
			socket.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		running = false;
	}

	public InetSocketAddress getInetSocketAddress() {
		return inetSocketAddress;
	}
}
