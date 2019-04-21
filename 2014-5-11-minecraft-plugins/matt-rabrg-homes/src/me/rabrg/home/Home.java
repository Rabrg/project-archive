package me.rabrg.home;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.avaje.ebean.validation.Length;
import com.avaje.ebean.validation.NotEmpty;
import com.avaje.ebean.validation.NotNull;

@Entity
@Table(name = "hb_home")
public class Home {
	@Id
	private int id;
	@NotNull
	private String playerName;
	@Length(max = 30)
	@NotEmpty
	private String name;
	@NotNull
	private double x;
	@NotNull
	private double y;
	@NotNull
	private double z;
	@NotNull
	private float pitch;
	@NotNull
	private float yaw;
	@NotEmpty
	private String worldName;

	public void setId(final int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(final String playerName) {
		this.playerName = playerName;
	}

	public Player getPlayer() {
		return Bukkit.getServer().getPlayer(playerName);
	}

	public void setPlayer(final Player player) {
		playerName = player.getName();
	}

	public String getWorldName() {
		return worldName;
	}

	public void setWorldName(final String worldName) {
		this.worldName = worldName;
	}

	public double getX() {
		return x;
	}

	public void setX(final double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(final double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(final double z) {
		this.z = z;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(final float pitch) {
		this.pitch = pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(final float yaw) {
		this.yaw = yaw;
	}

	public void setLocation(final Location location) {
		worldName = location.getWorld().getName();
		x = location.getX();
		y = location.getY();
		z = location.getZ();
		pitch = location.getPitch();
		yaw = location.getYaw();
	}

	public Location getLocation() {
		final World world = Bukkit.getServer().getWorld(worldName);
		return new Location(world, x, y, z, yaw, pitch);
	}
}
