package me.rabrg.stream.client;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.io.IOException;

//TODO: current and previous comparison for network compression
public final class StreamWorker implements Runnable {

	private final StreamClient stream;
	private final int sector;
	private final Rectangle rectangle;

	private final Robot robot;

	protected StreamWorker(final StreamClient stream, final int sector, final Rectangle rectangle) {
		this.stream = stream;
		this.sector = sector;
		this.rectangle = rectangle;
		
		try {
			robot = new Robot();
		} catch (final AWTException e) {
			throw new IllegalStateException("Robot could not be initialized.");
		}
	}

	@Override
	public void run() {
		for(;;) {
			try {
				stream.getOutput().writeByte(1);
				stream.getOutput().writeByte(sector);
				stream.getPngEncoder().encode(robot.createScreenCapture(rectangle), stream.getOutput());
				stream.getOutput().flush();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}
}
