package me.rabrg.clans.zcore.util;

import java.io.File;

import me.rabrg.clans.zcore.MPlugin;

public class LibLoader {
	MPlugin p;

	public LibLoader(final MPlugin p) {
		this.p = p;
		new File("./lib").mkdirs();
	}

	public boolean require(final String filename, final String url) {
		if (!include(filename, url)) {
			p.log("Failed to load the required library " + filename);
			p.suicide();
			return false;
		}
		return true;
	}

	public boolean include(final String filename, final String url) {
		final File file = getFile(filename);
		if (!file.exists()) {
			p.log("Downloading library " + filename);
			if (!DiscUtil.downloadUrl(url, file)) {
				p.log("Failed to download " + filename);
				return false;
			}
		}

		return ClassLoadHack.load(file);
	}

	private static File getFile(final String filename) {
		return new File("./lib/" + filename);
	}
}
