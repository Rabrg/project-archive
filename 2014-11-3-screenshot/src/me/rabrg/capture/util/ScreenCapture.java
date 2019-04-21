package me.rabrg.capture.util;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

/**
 * A utility class which captures the screen into a BufferedImage.
 * @author Ryan Greene
 *
 */
public final class ScreenCapture {

	/**
	 * The robot instance for creating a screen capture.
	 */
	private final Robot robot;

	/**
	 * The rectangle instance representing the entire screen.
	 */
	private final Rectangle screenRect;

	/**
	 * Constructs a new ScreenCapture instance.
	 * @throws AWTException If the robot instance cannot be created.
	 */
	public ScreenCapture() throws AWTException {
		robot = new Robot();
		screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
	}

	/**
	 * Captures the screen in the specified rectangle.
	 * @param captureScreenRect The rectangle of the screen being captured.
	 * @return The captured screen.
	 */
	public BufferedImage capture(final Rectangle captureScreenRect) {
		return robot.createScreenCapture(captureScreenRect);
	}

	/**
	 * Captures the entire screen.
	 * @return The captured screen.
	 */
	public BufferedImage capture() {
		return capture(screenRect);
	}
}
