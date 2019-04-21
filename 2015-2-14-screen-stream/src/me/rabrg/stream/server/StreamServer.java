package me.rabrg.stream.server;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

public final class StreamServer {

	private final ServerSocket serverSocket;

	public StreamServer() {
		try {
			serverSocket = new ServerSocket(25565);
			serverSocket.setReceiveBufferSize(204800);
		} catch (final IOException e) {
			throw new IllegalStateException("Exception thrown while initializing stream.", e);
		}
	}

	public static void main(final String[] args) {
		new StreamServer().start();
	}

	public void start() {
		for (;;) {
			try {
				final Socket socket = serverSocket.accept();
				socket.setReceiveBufferSize(204800);
				final DataInputStream input = new DataInputStream(socket.getInputStream());
				for (;;) {
					final int opcode = input.readByte();
					final int sector = input.readByte();
					final BufferedImage image = ImageIO.read(input);
					System.out.println(opcode + " " + sector + " " + image);
				}
			} catch (final IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
