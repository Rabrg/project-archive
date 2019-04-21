package me.rabrg.league;

import me.rabrg.league.command.LeagueCommandExecutor;
import me.rabrg.league.command.LeaveCommandExecutor;
import me.rabrg.league.listener.CommandListener;
import me.rabrg.league.match.MatchArenaManager;
import me.rabrg.league.queue.Queue;
import me.rabrg.league.queue.QueueSelector;
import me.rabrg.league.rating.RatingComparator;
import me.rabrg.league.rating.Ratings;

import org.bukkit.plugin.java.JavaPlugin;

public final class League extends JavaPlugin {

	private Ratings ratings;

	private RatingComparator ratingComparator;

	private Queue queue;

	private QueueSelector queueSelector;

	private MatchArenaManager matchArenaManager;

	public void onEnable() {
		ratings = new Ratings(this);
		ratingComparator = new RatingComparator(this);
		queue = new Queue(this);
		queueSelector = new QueueSelector(this);
		queueSelector.runTaskTimer(this, 0, 1200);
		matchArenaManager = new MatchArenaManager(this);
		
		getCommand("leave").setExecutor(new LeaveCommandExecutor(this));
		getCommand("league").setExecutor(new LeagueCommandExecutor(this));
		
		getServer().getPluginManager().registerEvents(new CommandListener(this), this);
	}

	public Ratings getRatings() {
		return ratings;
	}

	public RatingComparator getRatingComparator() {
		return ratingComparator;
	}

	public Queue getQueue() {
		return queue;
	}

	public MatchArenaManager getMatchArenaManager() {
		return matchArenaManager;
	}
}
