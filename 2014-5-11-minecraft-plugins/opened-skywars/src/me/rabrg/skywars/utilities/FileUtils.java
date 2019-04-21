package me.rabrg.skywars.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;

import javax.annotation.Nonnull;

import org.bukkit.plugin.Plugin;

public class FileUtils {

	public static boolean deleteFolder(@Nonnull final File file) {
		if (file.exists()) {
			boolean result = true;

			if (file.isDirectory()) {
				final File[] contents = file.listFiles();

				if (contents != null) {
					for (final File f : contents) {
						result = result && deleteFolder(f);
					}
				}
			}

			return result && file.delete();
		}

		return false;
	}

	public static void saveResource(final Plugin plugin, String resourcePath, final File outFile, final boolean replace) {
		if (resourcePath == null || resourcePath.equals("")) {
			throw new IllegalArgumentException("ResourcePath cannot be null or empty");
		}

		resourcePath = resourcePath.replace('\\', '/');
		final InputStream in = plugin.getResource(resourcePath);
		if (in == null) {
			throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found.");
		}

		final int lastIndex = resourcePath.lastIndexOf('/');
		final File outDir = new File(plugin.getDataFolder(), resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));

		if (!outDir.exists() && !outDir.mkdirs()) {
			return;
		}

		try {
			if (!outFile.exists() || replace) {
				final OutputStream out = new FileOutputStream(outFile);
				final byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.close();
				in.close();
			} else {
				plugin.getLogger().log(Level.WARNING, "Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
			}

		} catch (final IOException ex) {
			plugin.getLogger().log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, ex);
		}
	}
}
