package me.rabrg.clans.zcore.util;

import java.io.File;
import java.lang.reflect.Type;
import java.util.logging.Level;

import me.rabrg.clans.zcore.MPlugin;

// TODO: Give better name and place to differenciate from the entity-orm-ish system in "me.rabrg.core.persist".

public class Persist {

	private final MPlugin p;

	public Persist(final MPlugin p) {
		this.p = p;
	}

	// ------------------------------------------------------------ //
	// GET NAME - What should we call this type of object?
	// ------------------------------------------------------------ //

	public static String getName(final Class<?> clazz) {
		return clazz.getSimpleName().toLowerCase();
	}

	public static String getName(final Object o) {
		return getName(o.getClass());
	}

	public static String getName(final Type type) {
		return getName(type.getClass());
	}

	// ------------------------------------------------------------ //
	// GET FILE - In which file would we like to store this object?
	// ------------------------------------------------------------ //

	public File getFile(final String name) {
		return new File(p.getDataFolder(), name + ".json");
	}

	public File getFile(final Class<?> clazz) {
		return getFile(getName(clazz));
	}

	public File getFile(final Object obj) {
		return getFile(getName(obj));
	}

	public File getFile(final Type type) {
		return getFile(getName(type));
	}

	// NICE WRAPPERS

	public <T> T loadOrSaveDefault(final T def, final Class<T> clazz) {
		return loadOrSaveDefault(def, clazz, getFile(clazz));
	}

	public <T> T loadOrSaveDefault(final T def, final Class<T> clazz, final String name) {
		return loadOrSaveDefault(def, clazz, getFile(name));
	}

	public <T> T loadOrSaveDefault(final T def, final Class<T> clazz, final File file) {
		if (!file.exists()) {
			p.log("Creating default: " + file);
			this.save(def, file);
			return def;
		}

		final T loaded = this.load(clazz, file);

		if (loaded == null) {
			p.log(Level.WARNING, "Using default as I failed to load: " + file);

			// backup bad file, so user can attempt to recover their changes
			// from it
			final File backup = new File(file.getPath() + "_bad");
			if (backup.exists()) {
				backup.delete();
			}
			p.log(Level.WARNING, "Backing up copy of bad file to: " + backup);
			file.renameTo(backup);

			return def;
		}

		return loaded;
	}

	// SAVE

	public boolean save(final Object instance) {
		return save(instance, getFile(instance));
	}

	public boolean save(final Object instance, final String name) {
		return save(instance, getFile(name));
	}

	public boolean save(final Object instance, final File file) {
		return DiscUtil.writeCatch(file, p.gson.toJson(instance));
	}

	// LOAD BY CLASS

	public <T> T load(final Class<T> clazz) {
		return load(clazz, getFile(clazz));
	}

	public <T> T load(final Class<T> clazz, final String name) {
		return load(clazz, getFile(name));
	}

	public <T> T load(final Class<T> clazz, final File file) {
		final String content = DiscUtil.readCatch(file);
		if (content == null) {
			return null;
		}

		try {
			final T instance = p.gson.fromJson(content, clazz);
			return instance;
		} catch (final Exception ex) { // output the error message rather than
										// full stack trace; error parsing the
										// file, most likely
			p.log(Level.WARNING, ex.getMessage());
		}

		return null;
	}

	// LOAD BY TYPE
	@SuppressWarnings("unchecked")
	public <T> T load(final Type typeOfT, final String name) {
		return (T) load(typeOfT, getFile(name));
	}

	@SuppressWarnings("unchecked")
	public <T> T load(final Type typeOfT, final File file) {
		final String content = DiscUtil.readCatch(file);
		if (content == null) {
			return null;
		}

		try {
			return (T) p.gson.fromJson(content, typeOfT);
		} catch (final Exception ex) { // output the error message rather than
										// full stack trace; error parsing the
										// file, most likely
			p.log(Level.WARNING, ex.getMessage());
		}

		return null;
	}
}
