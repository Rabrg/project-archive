package me.rabrg.stream.client;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.objectplanet.image.PngEncoder;

public final class StreamClient {

	// The best method for my 6 core processor by ~25%. 13 screen captures per second.
	private static final int SECTORS = 2;

	private final Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();

	private final StreamWorker[] streamWorkers = new StreamWorker[SECTORS];

	private final PngEncoder pngEncoder = new PngEncoder(PngEncoder.COLOR_GRAYSCALE, PngEncoder.BEST_SPEED);

	private final Socket socket;

	// Buffer sized optimized for grayscale at best speed
	private final DataOutputStream output;

	public StreamClient(final String address, final int port) {
		try {
			socket = new Socket(address, port);
			socket.setSendBufferSize(204800);
			output = new DataOutputStream(socket.getOutputStream());
		} catch (final IOException e) {
			throw new IllegalStateException("Exception occured while initializing Stream", e);
		}
	}

	public static void main(final String[] args) {
		new StreamClient("localhost", 25565).start();
	}

	public void start() {
		final int widthOffset = (int) (screenDimension.getWidth() / SECTORS);
		for (int sector = 0; sector < SECTORS; sector++) {
			streamWorkers[sector] = new StreamWorker(this, sector, new Rectangle(widthOffset * sector, 0, widthOffset, (int) screenDimension.getHeight()));
			new Thread(streamWorkers[sector]).start();
		}
	}

	public PngEncoder getPngEncoder() {
		return pngEncoder;
	}

	public DataOutputStream getOutput() {
		return output;
	}
}
