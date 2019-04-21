package me.rabrg.clans;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.com.google.gson.GsonBuilder;
import org.bukkit.craftbukkit.libs.com.google.gson.reflect.TypeToken;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.rabrg.clans.cmd.CmdAutoHelp;
import me.rabrg.clans.cmd.FCmdRoot;
import me.rabrg.clans.integration.Econ;
import me.rabrg.clans.integration.EssentialsFeatures;
import me.rabrg.clans.integration.LWCFeatures;
import me.rabrg.clans.integration.SpoutFeatures;
import me.rabrg.clans.integration.Worldguard;
import me.rabrg.clans.integration.capi.CapiFeatures;
import me.rabrg.clans.listeners.ClansBlockListener;
import me.rabrg.clans.listeners.ClansChatListener;
import me.rabrg.clans.listeners.ClansEntityListener;
import me.rabrg.clans.listeners.ClansExploitListener;
import me.rabrg.clans.listeners.ClansPlayerListener;
import me.rabrg.clans.listeners.ClansServerListener;
import me.rabrg.clans.struct.ChatMode;
import me.rabrg.clans.util.AutoLeaveTask;
import me.rabrg.clans.util.LazyLocation;
import me.rabrg.clans.util.MapFLocToStringSetTypeAdapter;
import me.rabrg.clans.util.MyLocationTypeAdapter;
import me.rabrg.clans.zcore.MPlugin;
import me.rabrg.clans.zcore.util.TextUtil;

public class P extends MPlugin {
	// Our single plugin instance
	public static P p;

	// Listeners
	public final ClansPlayerListener playerListener;
	public final ClansChatListener chatListener;
	public final ClansEntityListener entityListener;
	public final ClansExploitListener exploitListener;
	public final ClansBlockListener blockListener;
	public final ClansServerListener serverListener;

	// Persistance related
	private boolean locked = false;

	public boolean getLocked() {
		return this.locked;
	}

	public void setLocked(final boolean val) {
		this.locked = val;
		this.setAutoSave(val);
	}

	private Integer AutoLeaveTask = null;

	// Commands
	public FCmdRoot cmdBase;
	public CmdAutoHelp cmdAutoHelp;

	public P() {
		p = this;
		this.playerListener = new ClansPlayerListener(this);
		this.chatListener = new ClansChatListener(this);
		this.entityListener = new ClansEntityListener(this);
		this.exploitListener = new ClansExploitListener();
		this.blockListener = new ClansBlockListener(this);
		this.serverListener = new ClansServerListener(this);
	}

	@Override
	public void onEnable() {
		// bit of (apparently absolutely necessary) idiot-proofing for CB
		// version support due to changed GSON lib package name
		try {
			Class.forName("org.bukkit.craftbukkit.libs.com.google.gson.reflect.TypeToken");
		} catch (final ClassNotFoundException ex) {
			this.log(Level.SEVERE, "GSON lib not found. Your CraftBukkit build is too old (< 1.3.2) or otherwise not compatible.");
			this.suicide();
			return;
		}

		if (!preEnable()) {
			return;
		}
		this.loadSuccessful = false;

		// Load Conf from disk
		Conf.load();
		CPlayers.i.loadFromDisc();
		Clans.i.loadFromDisc();
		Board.load();

		// Add Base Commands
		this.cmdBase = new FCmdRoot();
		this.cmdAutoHelp = new CmdAutoHelp();
		this.getBaseCommands().add(cmdBase);

		EssentialsFeatures.setup();
		SpoutFeatures.setup();
		Econ.setup();
		CapiFeatures.setup();
		LWCFeatures.setup();

		if (Conf.worldGuardChecking || Conf.worldGuardBuildPriority) {
			Worldguard.init(this);
		}

		// start up task which runs the autoLeaveAfterDaysOfInactivity routine
		startAutoLeaveTask(false);

		// Register Event Handlers
		getServer().getPluginManager().registerEvents(playerListener, this);
		getServer().getPluginManager().registerEvents(chatListener, this);
		getServer().getPluginManager().registerEvents(entityListener, this);
		getServer().getPluginManager().registerEvents(exploitListener, this);
		getServer().getPluginManager().registerEvents(blockListener, this);
		getServer().getPluginManager().registerEvents(serverListener, this);

		// since some other plugins execute commands directly through this
		// command interface, provide it
		this.getCommand(this.refCommand).setExecutor(this);

		postEnable();
		this.loadSuccessful = true;
	}

	@Override
	public GsonBuilder getGsonBuilder() {
		final Type mapFLocToStringSetType = new TypeToken<Map<CLocation, Set<String>>>() {
		}.getType();

		return new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.VOLATILE)
				.registerTypeAdapter(LazyLocation.class, new MyLocationTypeAdapter())
				.registerTypeAdapter(mapFLocToStringSetType, new MapFLocToStringSetTypeAdapter());
	}

	@Override
	public void onDisable() {
		// only save data if plugin actually completely loaded successfully
		if (this.loadSuccessful) {
			Board.save();
			Conf.save();
		}
		EssentialsFeatures.unhookChat();
		if (AutoLeaveTask != null) {
			this.getServer().getScheduler().cancelTask(AutoLeaveTask);
			AutoLeaveTask = null;
		}
		super.onDisable();
	}

	public void startAutoLeaveTask(final boolean restartIfRunning) {
		if (AutoLeaveTask != null) {
			if (!restartIfRunning) {
				return;
			}
			this.getServer().getScheduler().cancelTask(AutoLeaveTask);
		}

		if (Conf.autoLeaveRoutineRunsEveryXMinutes > 0.0) {
			final long ticks = (long) (20 * 60 * Conf.autoLeaveRoutineRunsEveryXMinutes);
			AutoLeaveTask = getServer().getScheduler().scheduleSyncRepeatingTask(this, new AutoLeaveTask(), ticks, ticks);
		}
	}

	@Override
	public void postAutoSave() {
		Board.save();
		Conf.save();
	}

	@Override
	public boolean logPlayerCommands() {
		return Conf.logPlayerCommands;
	}

	@Override
	public boolean handleCommand(final CommandSender sender, final String commandString, final boolean testOnly) {
		if (sender instanceof Player && ClansPlayerListener.preventCommand(commandString, (Player) sender)) {
			return true;
		}

		return super.handleCommand(sender, commandString, testOnly);
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] split) {
		// if bare command at this point, it has already been handled by
		// MPlugin's command listeners
		if (split == null || split.length == 0) {
			return true;
		}

		// otherwise, needs to be handled; presumably another plugin directly
		// ran the command
		final String cmd = Conf.baseCommandAliases.isEmpty() ? "/f" : "/" + Conf.baseCommandAliases.get(0);
		return handleCommand(sender, cmd + " " + TextUtil.implode(Arrays.asList(split), " "), false);
	}

	// -------------------------------------------- //
	// Functions for other plugins to hook into
	// -------------------------------------------- //

	// This value will be updated whenever new hooks are added
	public int hookSupportVersion() {
		return 3;
	}

	// If another plugin is handling insertion of chat tags, this should be used
	// to notify Clans
	public void handleClanTagExternally(final boolean notByClans) {
		Conf.chatTagHandledByAnotherPlugin = notByClans;
	}

	// Simply put, should this chat event be left for Clans to handle? For now,
	// that means players with Clan Chat
	// enabled or use of the Clans f command without a slash; combination of
	// isPlayerClanChatting() and isClansCommand()

	public boolean shouldLetClansHandleThisChat(final AsyncPlayerChatEvent event) {
		if (event == null) {
			return false;
		}
		return (isPlayerClanChatting(event.getPlayer()) || isClansCommand(event.getMessage()));
	}

	// Does player have Clan Chat enabled? If so, chat plugins should preferably
	// not do channels,
	// local chat, or anything else which targets individual recipients, so Clan
	// Chat can be done
	public boolean isPlayerClanChatting(final Player player) {
		if (player == null) {
			return false;
		}
		final CPlayer me = CPlayers.i.get(player);

		if (me == null) {
			return false;
		}
		return me.getChatMode().isAtLeast(ChatMode.ALLIANCE);
	}

	// Is this chat message actually a Clans command, and thus should be left
	// alone by other plugins?

	// TODO: GET THIS BACK AND WORKING

	public boolean isClansCommand(final String check) {
		if (check == null || check.isEmpty()) {
			return false;
		}
		return this.handleCommand(null, check, true);
	}

	// Get a player's clan tag (clan name), mainly for usage by chat plugins for
	// local/channel chat
	public String getPlayerClanTag(final Player player) {
		return getPlayerClanTagRelation(player, null);
	}

	// Same as above, but with relation (enemy/neutral/ally) coloring
	// potentially added to the tag
	public String getPlayerClanTagRelation(final Player speaker, final Player listener) {
		String tag = "~";

		if (speaker == null) {
			return tag;
		}

		final CPlayer me = CPlayers.i.get(speaker);
		if (me == null) {
			return tag;
		}

		// if listener isn't set, or config option is disabled, give back
		// uncolored tag
		if (listener == null || !Conf.chatTagRelationColored) {
			tag = me.getChatTag().trim();
		} else {
			final CPlayer you = CPlayers.i.get(listener);
			if (you == null) {
				tag = me.getChatTag().trim();
			} else {
				tag = me.getChatTag(you).trim();
			}
		}
		if (tag.isEmpty()) {
			tag = "~";
		}

		return tag;
	}

	// Get a player's title within their clan, mainly for usage by chat plugins
	// for local/channel chat
	public String getPlayerTitle(final Player player) {
		if (player == null) {
			return "";
		}

		final CPlayer me = CPlayers.i.get(player);
		if (me == null) {
			return "";
		}

		return me.getTitle().trim();
	}

	// Get a list of all clan tags (names)
	public Set<String> getClanTags() {
		final Set<String> tags = new HashSet<String>();
		for (final Clan clan : Clans.i.get()) {
			tags.add(clan.getTag());
		}
		return tags;
	}

	// Get a list of all players in the specified clan
	public Set<String> getPlayersInClan(final String clanTag) {
		final Set<String> players = new HashSet<String>();
		final Clan clan = Clans.i.getByTag(clanTag);
		if (clan != null) {
			for (final CPlayer fplayer : clan.getCPlayers()) {
				players.add(fplayer.getName());
			}
		}
		return players;
	}

	// Get a list of all online players in the specified clan
	public Set<String> getOnlinePlayersInClan(final String clanTag) {
		final Set<String> players = new HashSet<String>();
		final Clan clan = Clans.i.getByTag(clanTag);
		if (clan != null) {
			for (final CPlayer fplayer : clan.getCPlayersWhereOnline(true)) {
				players.add(fplayer.getName());
			}
		}
		return players;
	}

	// check if player is allowed to build/destroy in a particular location
	public boolean isPlayerAllowedToBuildHere(final Player player, final Location location) {
		return ClansBlockListener.playerCanBuildDestroyBlock(player, location, "", true);
	}

	// check if player is allowed to interact with the specified block
	// (doors/chests/whatever)
	public boolean isPlayerAllowedToInteractWith(final Player player, final Block block) {
		return ClansPlayerListener.canPlayerUseBlock(player, block, true);
	}

	// check if player is allowed to use a specified item (flint&steel, buckets,
	// etc) in a particular location
	public boolean isPlayerAllowedToUseThisHere(final Player player, final Location location, final Material material) {
		return ClansPlayerListener.playerCanUseItemHere(player, location, material, true);
	}
}
