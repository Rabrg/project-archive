package net.erbros.lottery;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import net.erbros.lottery.register.payment.Method;
import net.erbros.lottery.register.payment.Methods;


public class Lottery extends JavaPlugin
{

	public Method Method = null;
	public Methods Methods = null;
	public boolean timerStarted = false;
	private Server server = null;
	private LotteryConfig lConfig;
	private LotteryGame lGame;

	@Override
	public void onDisable()
	{
		// Disable all running timers.
		Bukkit.getServer().getScheduler().cancelTasks(this);

		lConfig.debugMsg("[Lottery]: has been disabled (including timers).");
	}

	@Override
	public void onEnable()
	{

		FileConfiguration config;
		lConfig = new LotteryConfig(this);
		lGame = new LotteryGame(this);
		// Lets find some configs
		config = getConfig();
		config.options().copyDefaults(true);
		saveConfig();
		lConfig.loadConfig();

		final PluginManager pm = getServer().getPluginManager();

		server = getServer();

		pm.registerEvents(new PluginListener(this), this);
		if (lConfig.useWelcomeMessage())
		{
			pm.registerEvents(new PlayerJoinListener(this), this);
		}

		getCommand("lottery").setExecutor(new MainCommandExecutor(this));

		// Is the date we are going to draw the lottery set? If not, we should
		// do it.
		if (getNextexec() == 0)
		{
			// Set first time to be config hours later? Millisecs, * 1000.
			setNextexec(System.currentTimeMillis() + extendTime());
		}

		// Start the timer for the first time.
		startTimerSchedule(false);

	}

	public Server getBukkitServer()
	{
		return server;
	}

	public LotteryConfig getLotteryConfig()
	{
		return lConfig;
	}

	public LotteryGame getLotteryGame()
	{
		return lGame;
	}

	protected long getNextexec()
	{
		return lConfig.getNextexec();
	}

	protected void setNextexec(final long aNextexec)
	{
		lConfig.setNextexec(aNextexec);
	}

	public boolean isLotteryDue()
	{
		return getNextexec() > 0 && System.currentTimeMillis() + 1000 >= getNextexec();
	}

	public void startTimerSchedule(final boolean drawAtOnce)
	{
		long extendtime;
		// Cancel any existing timers.
		if (timerStarted)
		{
			// Let's try and stop any running threads.
			try
			{
				Bukkit.getServer().getScheduler().cancelTasks((Plugin)this);
			}
			catch (ClassCastException exception)
			{
			}

			extendtime = extendTime();
		}
		else
		{
			// Get time until lottery drawing.
			extendtime = getNextexec() - System.currentTimeMillis();
		}
		// What if the admin changed the config to a shorter time? lets check,
		// and if
		// that is the case, lets use the new time.
		if (System.currentTimeMillis() + extendTime() < getNextexec())
		{
			setNextexec(System.currentTimeMillis() + extendTime());
		}

		// If the time is passed (perhaps the server was offline?), draw lottery
		// at once.
		if (extendtime <= 0)
		{
			extendtime = 1000;
			lConfig.debugMsg("Seems we need to make a draw at once!");
		}

		// Is the drawAtOnce boolean set to true? In that case, do drawing in a
		// few secs.
		if (drawAtOnce)
		{
			extendtime = 100;
			setNextexec(System.currentTimeMillis() + 100);
			lConfig.debugMsg("DRAW NOW");
		}

		// Delay in server ticks. 20 ticks = 1 second.
		extendtime = extendtime / 1000 * 20;
		runDrawTimer(extendtime);

		// Timer is now started, let it know.
		timerStarted = true;
	}

	public void lotteryDraw()
	{
		lConfig.debugMsg("Doing a lottery draw");

		if (getNextexec() > 0 && System.currentTimeMillis() + 1000 >= getNextexec())
		{
			// Get the winner, if any. And remove file so we are ready for
			// new round.
			lConfig.debugMsg("Getting winner.");
			if (!lGame.getWinner())
			{
				lConfig.debugMsg("Failed getting winner");
			}
			setNextexec(System.currentTimeMillis() + extendTime());
		}
		// Call a new timer.
		startTimerSchedule(false);
	}

	public void extendLotteryDraw()
	{
		// Cancel timer.
		try
		{
			Bukkit.getServer().getScheduler().cancelTasks((Plugin)this);
		}
		catch (ClassCastException exception)
		{
		}

		long extendtime;

		// How much time left? Below 0?
		if (getNextexec() < System.currentTimeMillis())
		{
			extendtime = 3000;
		}
		else
		{
			extendtime = getNextexec() - System.currentTimeMillis();
		}
		// Delay in server ticks. 20 ticks = 1 second.
		extendtime = extendtime / 1000 * 20;
		runDrawTimer(extendtime);
	}

	private void runDrawTimer(final long extendtime)
	{
		// Is this very long until? On servers with lag and long between
		// restarts there might be a very long time between when server
		// should have drawn winner and when it will draw. Perhaps help the
		// server a bit by only scheduling for half the length at a time?
		// But only if its more than 5 seconds left.
		if (extendtime < 5 * 20)
		{
			server.getScheduler().runTaskLaterAsynchronously(this, new LotteryDraw(this, true), extendtime);
			lConfig.debugMsg("LotteryDraw() " + extendtime + 100);
		}
		else
		{
			final long newtime = extendtime / 10;
			server.getScheduler().runTaskLaterAsynchronously(this, new LotteryDraw(this, false), newtime);
			lConfig.debugMsg("extendLotteryDraw() " + newtime);
		}
	}

	public long extendTime()
	{
		final double exacttime = lConfig.getHours() * 60 * 60 * 1000;
		final long extendTime = (long)exacttime;
		lConfig.debugMsg("extendTime: " + extendTime);
		return extendTime;
	}
}