package me.rabrg.clans.integration;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.player.SpoutPlayer;

import me.rabrg.clans.CLocation;
import me.rabrg.clans.CPlayer;
import me.rabrg.clans.CPlayers;
import me.rabrg.clans.Clan;
import me.rabrg.clans.Conf;
import me.rabrg.clans.P;
import me.rabrg.clans.struct.Relation;
import me.rabrg.clans.struct.Role;

public class SpoutFeatures {
	private transient static boolean spoutMe = false;
	private transient static SpoutMainListener mainListener;
	private transient static boolean listenersHooked;

	public static void setup() {
		final Plugin test = Bukkit.getServer().getPluginManager().getPlugin("Spout");
		if (test == null || !test.isEnabled()) {
			return;
		}

		setAvailable(true, test.getDescription().getFullName());
	}

	// set integration availability
	public static void setAvailable(final boolean enable, final String pluginName) {
		spoutMe = enable;
		if (!spoutMe) {
			return;
		}

		P.p.log("Found and will use features of " + pluginName);

		if (!listenersHooked) {
			listenersHooked = true;
			mainListener = new SpoutMainListener();
			Bukkit.getServer().getPluginManager().registerEvents(mainListener, P.p);
		}
	}

	// If we're successfully hooked into Spout
	public static boolean enabled() {
		return spoutMe;
	}

	// If Spout is available and the specified Player is running the Spoutcraft
	// client
	public static boolean availableFor(final Player player) {
		return spoutMe && SpoutManager.getPlayer(player).isSpoutCraftEnabled();
	}

	// update displayed current territory for all players inside a specified
	// chunk; if specified chunk is null, then simply update everyone online
	public static void updateTerritoryDisplayLoc(final CLocation fLoc) {
		if (!enabled()) {
			return;
		}

		final Set<CPlayer> players = CPlayers.i.getOnline();

		for (final CPlayer player : players) {
			if (fLoc == null) {
				mainListener.updateTerritoryDisplay(player, false);
			} else if (player.getLastStoodAt().equals(fLoc)) {
				mainListener.updateTerritoryDisplay(player, true);
			}
		}
	}

	// update displayed current territory for specified player; returns false if
	// unsuccessful
	public static boolean updateTerritoryDisplay(final CPlayer player) {
		if (!enabled()) {
			return false;
		}

		return mainListener.updateTerritoryDisplay(player, true);
	}

	// update owner list for all players inside a specified chunk; if specified
	// chunk is null, then simply update everyone online
	public static void updateOwnerListLoc(final CLocation fLoc) {
		if (!enabled()) {
			return;
		}

		final Set<CPlayer> players = CPlayers.i.getOnline();

		for (final CPlayer player : players) {
			if (fLoc == null || player.getLastStoodAt().equals(fLoc)) {
				mainListener.updateOwnerList(player);
			}
		}
	}

	// update owner list for specified player
	public static void updateOwnerList(final CPlayer player) {
		if (!enabled()) {
			return;
		}

		mainListener.updateOwnerList(player);
	}

	public static void playerDisconnect(final CPlayer player) {
		if (!enabled()) {
			return;
		}

		mainListener.removeTerritoryLabels(player.getName());
	}

	// update all appearances between every player
	public static void updateAppearances() {
		if (!enabled()) {
			return;
		}

		final Set<CPlayer> players = CPlayers.i.getOnline();

		for (final CPlayer playerA : players) {
			for (final CPlayer playerB : players) {
				updateSingle(playerB, playerA);
			}
		}
	}

	// update all appearances related to a specific player
	public static void updateAppearances(final Player player) {
		if (!enabled() || player == null) {
			return;
		}

		final Set<CPlayer> players = CPlayers.i.getOnline();
		final CPlayer playerA = CPlayers.i.get(player);

		for (final CPlayer playerB : players) {
			updateSingle(playerB, playerA);
			updateSingle(playerA, playerB);
		}
	}

	// as above method, but with a delay added; useful for after-login update
	// which doesn't always propagate if done immediately
	public static void updateAppearancesShortly(final Player player) {
		P.p.getServer().getScheduler().scheduleSyncDelayedTask(P.p, new Runnable() {
			@Override
			public void run() {
				updateAppearances(player);
			}
		}, 100);
	}

	// update all appearances related to a single clan
	public static void updateAppearances(final Clan clan) {
		if (!enabled() || clan == null) {
			return;
		}

		final Set<CPlayer> players = CPlayers.i.getOnline();
		Clan clanA;

		for (final CPlayer playerA : players) {
			clanA = playerA.getClan();

			for (final CPlayer playerB : players) {
				if (clanA != clan && playerB.getClan() != clan) {
					continue;
				}

				updateSingle(playerB, playerA);
			}
		}
	}

	// update all appearances between two clans
	public static void updateAppearances(final Clan clanA, final Clan clanB) {
		if (!enabled() || clanA == null || clanB == null) {
			return;
		}

		for (final CPlayer playerA : clanA.getCPlayersWhereOnline(true)) {
			for (final CPlayer playerB : clanB.getCPlayersWhereOnline(true)) {
				updateSingle(playerB, playerA);
				updateSingle(playerA, playerB);
			}
		}
	}

	// update a single appearance; internal use only by above public methods
	private static void updateSingle(final CPlayer viewer, final CPlayer viewed) {
		if (viewer == null || viewed == null) {
			return;
		}

		final Clan viewedClan = viewed.getClan();
		if (viewedClan == null) {
			return;
		}

		// these still end up returning null on occasion at this point, mucking
		// up the SpoutManager.getPlayer() method
		if (viewer.getPlayer() == null || viewed.getPlayer() == null) {
			return;
		}

		final SpoutPlayer pViewer = SpoutManager.getPlayer(viewer.getPlayer());
		final SpoutPlayer pViewed = SpoutManager.getPlayer(viewed.getPlayer());
		if (pViewed == null || pViewer == null) {
			return;
		}

		final String viewedTitle = viewed.getTitle();
		final Role viewedRole = viewed.getRole();

		if ((Conf.spoutClanTagsOverNames || Conf.spoutClanTitlesOverNames) && viewer != viewed) {
			if (viewedClan.isNormal()) {
				String addTag = "";
				if (Conf.spoutClanTagsOverNames) {
					addTag += viewedClan.getTag(viewed.getColorTo(viewer).toString() + "[") + "]";
				}

				final String rolePrefix = viewedRole.getPrefix();
				if (Conf.spoutClanTitlesOverNames && (!viewedTitle.isEmpty() || !rolePrefix.isEmpty())) {
					addTag += (addTag.isEmpty() ? "" : " ") + viewedRole.getPrefix() + viewedTitle;
				}

				pViewed.setTitleFor(pViewer, addTag + "\n" + pViewed.getDisplayName());
			} else {
				pViewed.setTitleFor(pViewer, pViewed.getDisplayName());
			}
		}

		if ((Conf.spoutClanAdminCapes && viewedRole.equals(Role.ADMIN)) || (Conf.spoutClanModeratorCapes && viewedRole.equals(Role.MODERATOR))) {
			final Relation relation = viewer.getRelationTo(viewed);
			String cape = "";
			if (!viewedClan.isNormal()) {
				// yeah, no cape if no clan
			} else if (viewedClan.isPeaceful()) {
				cape = Conf.capePeaceful;
			} else if (relation.isNeutral()) {
				cape = Conf.capeNeutral;
			} else if (relation.isMember()) {
				cape = Conf.capeMember;
			} else if (relation.isEnemy()) {
				cape = Conf.capeEnemy;
			} else if (relation.isAlly()) {
				cape = Conf.capeAlly;
			}

			if (cape.isEmpty()) {
				pViewed.resetCapeFor(pViewer);
			} else {
				pViewed.setCapeFor(pViewer, cape);
			}
		} else if (Conf.spoutClanAdminCapes || Conf.spoutClanModeratorCapes) {
			pViewed.resetCapeFor(pViewer);
		}
	}

	// method to convert a Bukkit ChatColor to a Spout Color
	protected static Color getSpoutColor(final ChatColor inColor, final int alpha) {
		if (inColor == null) {
			return SpoutFixedColor(191, 191, 191, alpha);
		}

		switch (inColor.getChar()) {
		case 0x1:
			return SpoutFixedColor(0, 0, 191, alpha);
		case 0x2:
			return SpoutFixedColor(0, 191, 0, alpha);
		case 0x3:
			return SpoutFixedColor(0, 191, 191, alpha);
		case 0x4:
			return SpoutFixedColor(191, 0, 0, alpha);
		case 0x5:
			return SpoutFixedColor(191, 0, 191, alpha);
		case 0x6:
			return SpoutFixedColor(191, 191, 0, alpha);
		case 0x7:
			return SpoutFixedColor(191, 191, 191, alpha);
		case 0x8:
			return SpoutFixedColor(64, 64, 64, alpha);
		case 0x9:
			return SpoutFixedColor(64, 64, 255, alpha);
		case 0xA:
			return SpoutFixedColor(64, 255, 64, alpha);
		case 0xB:
			return SpoutFixedColor(64, 255, 255, alpha);
		case 0xC:
			return SpoutFixedColor(255, 64, 64, alpha);
		case 0xD:
			return SpoutFixedColor(255, 64, 255, alpha);
		case 0xE:
			return SpoutFixedColor(255, 255, 64, alpha);
		case 0xF:
			return SpoutFixedColor(255, 255, 255, alpha);
		default:
			return SpoutFixedColor(0, 0, 0, alpha);
		}
	}

	private static Color SpoutFixedColor(final int r, final int g, final int b, final int a) {
		return new Color(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f);
	}
}
