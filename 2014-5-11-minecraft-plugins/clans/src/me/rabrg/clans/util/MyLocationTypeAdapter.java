package me.rabrg.clans.util;

import java.lang.reflect.Type;
import java.util.logging.Level;

import org.bukkit.craftbukkit.libs.com.google.gson.JsonDeserializationContext;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonDeserializer;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonElement;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonObject;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonParseException;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonSerializationContext;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonSerializer;

import me.rabrg.clans.P;

public class MyLocationTypeAdapter implements JsonDeserializer<LazyLocation>, JsonSerializer<LazyLocation> {
	private static final String WORLD = "world";
	private static final String X = "x";
	private static final String Y = "y";
	private static final String Z = "z";
	private static final String YAW = "yaw";
	private static final String PITCH = "pitch";

	@Override
	public LazyLocation deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
		try {
			final JsonObject obj = json.getAsJsonObject();

			final String worldName = obj.get(WORLD).getAsString();
			final double x = obj.get(X).getAsDouble();
			final double y = obj.get(Y).getAsDouble();
			final double z = obj.get(Z).getAsDouble();
			final float yaw = obj.get(YAW).getAsFloat();
			final float pitch = obj.get(PITCH).getAsFloat();

			return new LazyLocation(worldName, x, y, z, yaw, pitch);

		} catch (final Exception ex) {
			ex.printStackTrace();
			P.p.log(Level.WARNING, "Error encountered while deserializing a LazyLocation.");
			return null;
		}
	}

	@Override
	public JsonElement serialize(final LazyLocation src, final Type typeOfSrc, final JsonSerializationContext context) {
		final JsonObject obj = new JsonObject();

		try {
			obj.addProperty(WORLD, src.getWorldName());
			obj.addProperty(X, src.getX());
			obj.addProperty(Y, src.getY());
			obj.addProperty(Z, src.getZ());
			obj.addProperty(YAW, src.getYaw());
			obj.addProperty(PITCH, src.getPitch());

			return obj;
		} catch (final Exception ex) {
			ex.printStackTrace();
			P.p.log(Level.WARNING, "Error encountered while serializing a LazyLocation.");
			return obj;
		}
	}
}
