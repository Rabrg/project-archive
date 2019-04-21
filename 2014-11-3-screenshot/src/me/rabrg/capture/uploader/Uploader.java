package me.rabrg.capture.uploader;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * An interface which uploads images to a image hosting site.
 * @author Ryan Greene
 *
 */
public interface Uploader {

	/**
	 * Uploads the specified image to a image hosting site.
	 * @param image The image being uploaded.
	 * @return A string representing the location the image is hosted at. 
	 */
	public String upload(final BufferedImage image) throws IOException;
}
