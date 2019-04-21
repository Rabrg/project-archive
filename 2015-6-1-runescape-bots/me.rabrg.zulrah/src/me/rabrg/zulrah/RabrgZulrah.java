package me.rabrg.zulrah;

import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.widgets.message.Message;

@ScriptManifest(author = "Rabrg", category = Category.MONEYMAKING, name = "Zulrah", version = 0.1)
public final class RabrgZulrah extends AbstractScript {

	private static final boolean LOG = true;

	private Tile tile;
	private int id;

	@Override
	public void onStart() {
		
	}

	@Override
	public int onLoop() {
		if (LOG) {
			final NPC zulrah = getNpcs().closest("Zulrah");
			if (zulrah != null) {
				tile = zulrah.getTile();
				id = zulrah.getID();
			}
		}
		return 0;
	}

	@Override
	public void onMessage(final Message message) {
		if (message.getUsername().equals(getLocalPlayer().getName())) {
			log("zulrah(" + id + "): " + tile.getTile() + ", player: " + getLocalPlayer().getTile()); 
		}
	}
}
