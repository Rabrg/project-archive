package net.thewavemc.onevsone.playerrestore;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class PlayerRestore {
	public ItemStack[] invcontent;
	public ItemStack[] armcontent;
	public Location loc;
	public Vector velocity;
	public float fallDistance;
	public double health;
	public int hunger;

	public PlayerRestore(final ItemStack[] invcontent, final ItemStack[] armcontent, final Location loc, final Vector velocity, final float fallDistance, final double health,
			final int hunger) {
		this.invcontent = invcontent;
		this.armcontent = armcontent;

		this.loc = loc;

		this.velocity = velocity;

		this.fallDistance = fallDistance;

		this.health = health;
		this.hunger = hunger;
	}
}
