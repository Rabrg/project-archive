package me.rabrg.skywars.build;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;

public class BlockBuilderEntry {

	private final Vector location;
	private final BaseBlock block;

	public BlockBuilderEntry(final Vector location, final BaseBlock block) {
		this.location = location;
		this.block = block;
	}

	public Vector getLocation() {
		return location;
	}

	public BaseBlock getBlock() {
		return block;
	}
}
