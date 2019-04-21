package me.rabrg.clans.zcore.persist;

import me.rabrg.clans.zcore.MPlugin;

public class SaveTask implements Runnable {
	static private boolean running = false;

	MPlugin p;

	public SaveTask(final MPlugin p) {
		this.p = p;
	}

	@Override
	public void run() {
		if (!p.getAutoSave() || running) {
			return;
		}
		running = true;
		p.preAutoSave();
		EM.saveAllToDisc();
		p.postAutoSave();
		running = false;
	}
}
