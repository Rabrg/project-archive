package me.rabrg.capture.uploader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A uploader which uploads to the Imgur image hosting site.
 * @author Ryan Greene
 *
 */
public final class ImgurUploader implements Uploader {

	@Override
	public String upload(final BufferedImage image) throws IOException {
		final HttpURLConnection connection = (HttpURLConnection) new URL("https://api.imgur.com/3/image.json").openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setRequestProperty("Authorization", "Client-ID " + "");
		return null;
	}

}
