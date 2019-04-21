package me.rabrg.capture;

import me.rabrg.capture.uploader.Uploader;

import com.objectplanet.image.PngEncoder;

public final class Capture {

	private final PngEncoder pngEncoder;
	private Uploader uploader;

	private Capture() {
		pngEncoder = new PngEncoder();
	}

	public PngEncoder getPngEncoder() {
		return pngEncoder;
	}

	public Uploader getUploader() {
		return uploader;
	}

	public void setUploader(Uploader uploader) {
		this.uploader = uploader;
	}

}
