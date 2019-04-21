package me.rabrg.skywars.utilities;

import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtil {

	public static Location getLocation(final World world, final String coordinates) {
		final String[] chunks = coordinates.split(" ");

		final double posX = Double.parseDouble(chunks[0]);
		final double posY = Double.parseDouble(chunks[1]);
		final double posZ = Double.parseDouble(chunks[2]);

		float yaw = 0.0F;
		float pitch = 0.0F;

		if (chunks.length == 5) {
			yaw = (Float.parseFloat(chunks[3]) + 180.0F + 360.0F) % 360.0F;
			pitch = Float.parseFloat(chunks[4]);
		}

		return chunks.length == 5 ? new Location(world, posX, posY, posZ, yaw, pitch) : new Location(world, posX, posY, posZ);
	}
}