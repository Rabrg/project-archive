package me.rabrg.clans.util;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

import org.bukkit.craftbukkit.libs.com.google.gson.JsonArray;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonDeserializationContext;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonDeserializer;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonElement;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonObject;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonParseException;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonPrimitive;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonSerializationContext;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonSerializer;

import me.rabrg.clans.CLocation;
import me.rabrg.clans.P;

public class MapFLocToStringSetTypeAdapter implements JsonDeserializer<Map<CLocation, Set<String>>>, JsonSerializer<Map<CLocation, Set<String>>> {

	@Override
	public Map<CLocation, Set<String>> deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
			throws JsonParseException {
		try {
			final JsonObject obj = json.getAsJsonObject();
			if (obj == null) {
				return null;
			}

			final Map<CLocation, Set<String>> locationMap = new ConcurrentHashMap<CLocation, Set<String>>();
			Set<String> nameSet;
			Iterator<JsonElement> iter;
			String worldName;
			String[] coords;
			int x, z;

			for (final Entry<String, JsonElement> entry : obj.entrySet()) {
				worldName = entry.getKey();
				for (final Entry<String, JsonElement> entry2 : entry.getValue().getAsJsonObject().entrySet()) {
					coords = entry2.getKey().trim().split("[,\\s]+");
					x = Integer.parseInt(coords[0]);
					z = Integer.parseInt(coords[1]);

					nameSet = new HashSet<String>();
					iter = entry2.getValue().getAsJsonArray().iterator();
					while (iter.hasNext()) {
						nameSet.add(iter.next().getAsString());
					}

					locationMap.put(new CLocation(worldName, x, z), nameSet);
				}
			}

			return locationMap;

		} catch (final Exception ex) {
			ex.printStackTrace();
			P.p.log(Level.WARNING, "Error encountered while deserializing a Map of CLocations to String Sets.");
			return null;
		}
	}

	@Override
	public JsonElement serialize(final Map<CLocation, Set<String>> src, final Type typeOfSrc, final JsonSerializationContext context) {
		final JsonObject obj = new JsonObject();

		try {
			if (src != null) {
				CLocation loc;
				String locWorld;
				Set<String> nameSet;
				Iterator<String> iter;
				JsonArray nameArray;
				JsonPrimitive nameElement;

				for (final Entry<CLocation, Set<String>> entry : src.entrySet()) {
					loc = entry.getKey();
					locWorld = loc.getWorldName();
					nameSet = entry.getValue();

					if (nameSet == null || nameSet.isEmpty()) {
						continue;
					}

					nameArray = new JsonArray();
					iter = nameSet.iterator();
					while (iter.hasNext()) {
						nameElement = new JsonPrimitive(iter.next());
						nameArray.add(nameElement);
					}

					if (!obj.has(locWorld)) {
						obj.add(locWorld, new JsonObject());
					}

					obj.get(locWorld).getAsJsonObject().add(loc.getCoordString(), nameArray);
				}
			}
			return obj;

		} catch (final Exception ex) {
			ex.printStackTrace();
			P.p.log(Level.WARNING, "Error encountered while serializing a Map of CLocations to String Sets.");
			return obj;
		}
	}
}
