package com.lol768.battlekits.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * SLAPI = Saving/Loading API API for Saving and Loading Objects.
 * 
 * @author Tomsik68
 */
public class SLAPI {
	public static void save(final Object obj, final String path) throws Exception {
		final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
		oos.writeObject(obj);
		oos.flush();
		oos.close();
	}

	public static Object load(final String path) throws Exception {
		final ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
		final Object result = ois.readObject();
		ois.close();
		return result;
	}
}