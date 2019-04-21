package me.rabrg.kitpvp.util;

import org.bukkit.Location;

public final class Cuboid {

	private final Location cornerOne;

	private final Location cornerTwo;

	public Cuboid(final Location cornerOne, final Location cornerTwo) {
		this.cornerOne = cornerOne;
		this.cornerTwo = cornerTwo;
	}

	public boolean isInside(final Location location) {
        boolean x = location.getX() > Math.min(cornerOne.getX(), cornerTwo.getX()) && location.getX() < Math.max(cornerOne.getX(), cornerTwo.getX());
        boolean y = location.getY() > Math.min(cornerOne.getY(), cornerTwo.getY()) && location.getY() < Math.max(cornerOne.getY(), cornerTwo.getY());
        boolean z = location.getZ() > Math.min(cornerOne.getZ(), cornerTwo.getZ()) && location.getZ() < Math.max(cornerOne.getZ(), cornerTwo.getZ());
        return x && y && z;
    }
}
