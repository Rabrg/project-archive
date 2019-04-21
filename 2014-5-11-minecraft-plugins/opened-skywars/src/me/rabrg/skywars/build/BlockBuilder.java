package me.rabrg.skywars.build;

import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import me.rabrg.skywars.SkyWars;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;

public class BlockBuilder extends BukkitRunnable {

	private final EditSession editSession;
	private final List<BlockBuilderEntry> vectorList;
	private final List<BlockBuilderEntry> delayedList;
	private final int blocksPerTick;
	private final BuildFinishedHandler buildFinishedHandler;

	public BlockBuilder(final EditSession editSession, final List<BlockBuilderEntry> vectorList, final List<BlockBuilderEntry> delayedList, final int blocksPerTick,
			final BuildFinishedHandler buildFinishedHandler) {
		this.editSession = editSession;
		this.vectorList = vectorList;
		this.delayedList = delayedList;
		this.blocksPerTick = blocksPerTick;
		this.buildFinishedHandler = buildFinishedHandler;
	}

	public void start(final long delay, final long period) {
		runTaskTimer(SkyWars.get(), delay, period);
	}

	@Override
	public void run() {
		try {
			for (int iii = 0; iii < blocksPerTick; iii++) {
				if (!vectorList.isEmpty()) {
					place(vectorList.remove(0));

				} else if (!delayedList.isEmpty()) {
					place(delayedList.remove(0));

				} else {
					cancel();
					buildFinishedHandler.onBuildFinish();

					break;
				}
			}
		} catch (final MaxChangedBlocksException ex) {
			cancel();
			buildFinishedHandler.onBuildFinish();
		}
	}

	private void place(final BlockBuilderEntry entry) throws MaxChangedBlocksException {
		editSession.setBlock(entry.getLocation(), entry.getBlock());
	}

	public interface BuildFinishedHandler {

		void onBuildFinish();
	}
}
