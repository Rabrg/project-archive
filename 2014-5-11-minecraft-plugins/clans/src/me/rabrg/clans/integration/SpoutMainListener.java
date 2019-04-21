package me.rabrg.clans.integration;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.spout.SpoutCraftEnableEvent;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.player.SpoutPlayer;

import me.rabrg.clans.Board;
import me.rabrg.clans.CLocation;
import me.rabrg.clans.CPlayer;
import me.rabrg.clans.CPlayers;
import me.rabrg.clans.Clan;
import me.rabrg.clans.Conf;
import me.rabrg.clans.P;

public class SpoutMainListener implements Listener {
	@EventHandler(priority = EventPriority.NORMAL)
	public void onSpoutCraftEnable(final SpoutCraftEnableEvent event) {
		final CPlayer me = CPlayers.i.get(event.getPlayer());

		SpoutFeatures.updateAppearances(me.getPlayer());
		updateTerritoryDisplay(me, true);
	}

	// -----------------------------------------------------------------------------------------//
	// Everything below this is handled in here to prevent errors on servers not
	// running Spout
	// -----------------------------------------------------------------------------------------//

	private transient static Map<String, GenericLabel> territoryLabels = new HashMap<String, GenericLabel>();
	private transient static Map<String, NoticeLabel> territoryChangeLabels = new HashMap<String, NoticeLabel>();
	private transient static Map<String, GenericLabel> ownerLabels = new HashMap<String, GenericLabel>();
	private final static int SCREEN_WIDTH = 427;

	// private final static int SCREEN_HEIGHT = 240;

	public boolean updateTerritoryDisplay(final CPlayer player, final boolean notify) {
		final Player p = player.getPlayer();
		if (p == null) {
			return false;
		}

		final SpoutPlayer sPlayer = SpoutManager.getPlayer(p);
		if (!sPlayer.isSpoutCraftEnabled() || (Conf.spoutTerritoryDisplaySize <= 0 && !Conf.spoutTerritoryNoticeShow)) {
			return false;
		}

		doLabels(player, sPlayer, notify);

		return true;
	}

	public void updateOwnerList(final CPlayer player) {
		final SpoutPlayer sPlayer = SpoutManager.getPlayer(player.getPlayer());
		if (!sPlayer.isSpoutCraftEnabled() || (Conf.spoutTerritoryDisplaySize <= 0 && !Conf.spoutTerritoryNoticeShow)) {
			return;
		}

		final CLocation here = player.getLastStoodAt();
		final Clan clanHere = Board.getClanAt(here);

		doOwnerList(player, sPlayer, here, clanHere);

		return;
	}

	public void removeTerritoryLabels(final String playerName) {
		territoryLabels.remove(playerName);
		territoryChangeLabels.remove(playerName);
		ownerLabels.remove(playerName);
	}

	private void doLabels(final CPlayer player, final SpoutPlayer sPlayer, final boolean notify) {
		final CLocation here = player.getLastStoodAt();
		final Clan clanHere = Board.getClanAt(here);
		final String tag = clanHere.getColorTo(player).toString() + clanHere.getTag();

		// ----------------------
		// Main territory display
		// ----------------------
		if (Conf.spoutTerritoryDisplayPosition > 0 && Conf.spoutTerritoryDisplaySize > 0) {
			GenericLabel label;
			if (territoryLabels.containsKey(player.getName())) {
				label = territoryLabels.get(player.getName());
			} else {
				label = new GenericLabel();
				label.setWidth(1).setHeight(1); // prevent Spout's questionable
												// new "no default size" warning
				label.setScale(Conf.spoutTerritoryDisplaySize);

				sPlayer.getMainScreen().attachWidget(P.p, label);
				territoryLabels.put(player.getName(), label);
			}

			String msg = tag;

			if (Conf.spoutTerritoryDisplayShowDescription && !clanHere.getDescription().isEmpty()) {
				msg += " - " + clanHere.getDescription();
			}

			label.setText(msg);
			alignLabel(label, msg);
			label.setDirty(true);
		}

		// -----------------------
		// Fading territory notice
		// -----------------------
		if (notify && Conf.spoutTerritoryNoticeShow && Conf.spoutTerritoryNoticeSize > 0) {
			NoticeLabel label;
			if (territoryChangeLabels.containsKey(player.getName())) {
				label = territoryChangeLabels.get(player.getName());
			} else {
				label = new NoticeLabel(Conf.spoutTerritoryNoticeLeaveAfterSeconds);
				label.setWidth(1).setHeight(1); // prevent Spout's questionable
												// new "no default size" warning
				label.setScale(Conf.spoutTerritoryNoticeSize);
				label.setY(Conf.spoutTerritoryNoticeTop);
				sPlayer.getMainScreen().attachWidget(P.p, label);
				territoryChangeLabels.put(player.getName(), label);
			}

			String msg = tag;

			if (Conf.spoutTerritoryNoticeShowDescription && !clanHere.getDescription().isEmpty()) {
				msg += " - " + clanHere.getDescription();
			}

			label.setText(msg);
			alignLabel(label, msg, 2);
			label.resetNotice();
			label.setDirty(true);
		}

		// and owner list, of course
		doOwnerList(player, sPlayer, here, clanHere);
	}

	private void doOwnerList(final CPlayer player, final SpoutPlayer sPlayer, final CLocation here, final Clan clanHere) {
		// ----------
		// Owner list
		// ----------
		if (Conf.spoutTerritoryDisplayPosition > 0 && Conf.spoutTerritoryDisplaySize > 0 && Conf.spoutTerritoryOwnersShow && Conf.ownedAreasEnabled) {
			GenericLabel label;
			if (ownerLabels.containsKey(player.getName())) {
				label = ownerLabels.get(player.getName());
			} else {
				label = new GenericLabel();
				label.setWidth(1).setHeight(1); // prevent Spout's questionable
												// new "no default size" warning
				label.setScale(Conf.spoutTerritoryDisplaySize);
				label.setY((int) (10 * Conf.spoutTerritoryDisplaySize));
				sPlayer.getMainScreen().attachWidget(P.p, label);
				ownerLabels.put(player.getName(), label);
			}

			String msg = "";

			if (player.getClan() == clanHere) {
				msg = clanHere.getOwnerListString(here);

				if (!msg.isEmpty()) {
					msg = Conf.ownedLandMessage + msg;
				}
			}

			label.setText(msg);
			alignLabel(label, msg);
			label.setDirty(true);
		}
	}

	// this is only necessary because Spout text size scaling is currently
	// bugged and breaks their built-in alignment methods
	public void alignLabel(final GenericLabel label, final String text) {
		alignLabel(label, text, Conf.spoutTerritoryDisplayPosition);
	}

	public void alignLabel(final GenericLabel label, final String text, final int alignment) {
		final int labelWidth = (int) (GenericLabel.getStringWidth(text) * Conf.spoutTerritoryDisplaySize);
		if (labelWidth > SCREEN_WIDTH) {
			label.setX(0);
			return;
		}

		switch (alignment) {
		case 1: // left aligned
			label.setX(0);
			break;
		case 2: // center aligned
			label.setX((SCREEN_WIDTH - labelWidth) / 2);
			break;
		default: // right aligned
			label.setX(SCREEN_WIDTH - labelWidth);
		}
	}

	private static class NoticeLabel extends GenericLabel {
		private final int initial;
		private int countdown; // current delay countdown

		public NoticeLabel(final float secondsOfLife) {
			initial = (int) (secondsOfLife * 20);
			resetNotice();
		}

		public final void resetNotice() {
			countdown = initial;
		}

		@Override
		public void onTick() {
			if (countdown <= 0) {
				return;
			}

			this.countdown -= 1;

			if (this.countdown <= 0) {
				this.setText("");
				this.setDirty(true);
			}
		}
	}
}