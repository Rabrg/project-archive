package net.erbros.lottery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;


public class LotteryConfig
{

	final private Lottery plugin;
	private FileConfiguration config;
	private double cost;
	private double hours;
	private long nextexec;
	private boolean useiConomy;
	private int material;
	private double extraInPot;
	private boolean broadcastBuying;
	private int broadcastBuyingTime;
	private boolean welcomeMessage;
	private double netPayout;
	private boolean clearExtraInPot;
	private int maxTicketsEachUser;
	private int ticketsAvailable;
	private double jackpot;
	private String lastwinner;
	private double lastwinneramount;
	private boolean buyingExtendDeadline;
	private int buyingExtendRemaining;
	private double buyingExtendBase;
	private double buyingExtendMultiplier;
	private String taxTarget;

	private HashMap<String, List<String>> messages;


	public LotteryConfig(final Lottery plugin)
	{
		this.plugin = plugin;
		this.config = plugin.getConfig();
	}



  public void loadConfig()
	{
		plugin.reloadConfig();
		config = plugin.getConfig();

		debugMsg("Loading Lottery configuration");

		hours = config.getDouble("config.hours", 24);

		useiConomy = config.getBoolean("config.useiConomy", true);
		material = config.getInt("config.material", 266);
		broadcastBuying = config.getBoolean("config.broadcastBuying", true);
		broadcastBuyingTime = config.getInt("config.broadcastBuyingTime", 120);
		welcomeMessage = config.getBoolean("config.welcomeMessage", true);
		extraInPot = config.getDouble("config.extraInPot", 0);
		clearExtraInPot = config.getBoolean("config.clearExtraInPot", true);
		netPayout = config.getDouble("config.netPayout", 100);
		maxTicketsEachUser = config.getInt("config.maxTicketsEachUser", 1);
		ticketsAvailable = config.getInt("config.numberOfTicketsAvailable", 0);
		jackpot = config.getDouble("config.jackpot", 0);
		nextexec = config.getLong("config.nextexec");
		cost = Etc.formatAmount(config.getDouble("config.cost", 5), useiConomy);
		lastwinner = config.getString("config.lastwinner", "");
		lastwinneramount = config.getDouble("config.lastwinneramount", 0);
		buyingExtendDeadline = config.getBoolean("config.buyingExtend.enabled", true);
		buyingExtendRemaining = config.getInt("config.buyingExtend.secondsRemaining", 30);
		buyingExtendBase = config.getDouble("config.buyingExtend.extendBase", 15);
		buyingExtendMultiplier = config.getDouble("config.buyingExtend.extendMultiplier", 1.5);
		taxTarget = config.getString("config.taxTarget", "");

		// Load messages?
		loadCustomMessages();
		// Then lets save this stuff :)
		plugin.saveConfig();
	}

	public void set(final String path, final Object value)
	{
		config.set(path, value);
		plugin.saveConfig();
	}

	public void loadCustomMessages()
	{
		messages = new HashMap<String, List<String>>();
		messages.put("prefix", formatCustomMessage("message.prefix", "&6[LOTTERY]&r"));

		messages.put("Welcome", formatCustomMessage("message.Welcome", "%prefix% &fDraw in: &c%drawLong%"));
		messages.put("ErrorPlugin", formatCustomMessage("message.ErrorPlugin", "%prefix% Sorry, we haven\'t found a money plugin yet.."));
		messages.put("ErrorAccess", formatCustomMessage("message.ErrorAccess", "%prefix% You don\'t have access to that command."));
		messages.put("ErrorCommand", formatCustomMessage("message.ErrorCommand", "%prefix% Hey, I don\'t recognize that command!"));
		messages.put("ErrorConsole", formatCustomMessage("message.ErrorConsole", "%prefix% You\'re the console, I can\'t sell you tickets."));
		messages.put("ErrorConsole2", formatCustomMessage("message.ErrorConsole2", "%prefix% You\'re the console, you don\'t have an inventory."));
		messages.put("ErrorConsole3", formatCustomMessage("message.ErrorConsole3", "%prefix% You\'re the console, you can\'t change that."));
		messages.put("ErrorNoAvailable", formatCustomMessage("message.ErrorNoAvailable", "%prefix% There are no more tickets available"));
		messages.put("ErrorAtMax", formatCustomMessage("message.ErrorAtMax", "%prefix% You already have the maximum of %0% %1% already."));
		messages.put("ErrorNotAfford", formatCustomMessage("message.ErrorNotAfford", "%prefix% You can\'t afford a ticket"));
		messages.put("ErrorNumber", formatCustomMessage("message.ErrorNumber", "%prefix% Provide a number greater than zero (decimals accepted)"));
		messages.put("ErrorClaim", formatCustomMessage("message.ErrorClaim", "%prefix% You did not have anything unclaimed."));

		messages.put("TicketCommand", formatCustomMessage("message.TicketCommand", "%prefix% Buy a ticket for &c%cost% &rwith &c/lottery buy"));
		messages.put("PotAmount", formatCustomMessage("message.PotAmount", "%prefix% There is currently &a%pot% &rin the pot."));
		messages.put("YourTickets", formatCustomMessage("message.YourTickets", "%prefix% You have &c%0% &r%1%"));
		messages.put("TicketRemaining", formatCustomMessage("message.TimeRemaining", "%prefix% There is &c%0% &r%1% left."));
		messages.put("CommandHelp", formatCustomMessage("message.CommandHelp", "%prefix% &c/lottery help&r for other commands"));
		messages.put("LastWinner", formatCustomMessage("message.LastWinner", "%prefix% Last winner: %0% %1%"));
		messages.put("CheckClaim", formatCustomMessage("message.CheckClaim", "%prefix% Check if you have won with &c/lottery claim"));
		messages.put("BoughtTicket", formatCustomMessage("message.BoughtTicket", "%prefix% You got &c%0% &r%1% for &c%2%"));
		messages.put("BoughtTickets", formatCustomMessage("message.BoughtTickets", "%prefix% You now have &c%0% &r%1%"));
		messages.put("BoughtAnnounceDraw", formatCustomMessage("message.BoughtAnnounceDraw", "%prefix% &r%0% &rjust bought %1% %2%! Draw in %3%"));
		messages.put("BoughtAnnounce", formatCustomMessage("message.BoughtAnnounce", "%prefix% &r%0% &rjust bought %1% %2%!"));
		messages.put("DrawIn", formatCustomMessage("message.DrawIn", "%prefix% Draw in: &c%0%"));
		messages.put("DrawNow", formatCustomMessage("message.DrawNow", "%prefix% Lottery will be drawn at once."));
		messages.put("DrawSoon", formatCustomMessage("message.DrawSoon", "Soon"));
		messages.put("DrawSoonLong", formatCustomMessage("message.DrawSoonLong", "Draw will occur soon!"));
		messages.put("PlayerClaim", formatCustomMessage("message.PlayerClaim", "%prefix You just claimed %0%"));
		messages.put("MessagesEnabled", formatCustomMessage("message.MessagesEnabled", "%prefix% You will now receive Lottery broadcast messages."));
		messages.put("MessagesDisabled", formatCustomMessage("message.MessagesDisabled", "%prefix% You will no longer receive Lottery broadcast messages."));

		messages.put("NoWinnerTickets", formatCustomMessage("message.NoWinnerTickets", "%prefix% No tickets sold this round. That\'s a shame."));
		messages.put(
				"NoWinnerRollover", formatCustomMessage("message.NoWinnerRollover", "%prefix% No winner, we have a rollover! &a%0% &rwent to jackpot!"));
		messages.put("WinnerCongrat", formatCustomMessage("message.WinnerCongrat", "%prefix% Congratulations go to %0% &rfor winning &c%1%&r with &c%2%&r %3%."));
		messages.put("WinnerCongratClaim", formatCustomMessage("message.WinnerCongratClaim", "%prefix% Use &c/lottery claim &rto claim the winnings."));
		messages.put("WinnerSummary", formatCustomMessage("message.WinnerSummary", "%prefix% There was a total of %0% %1% buying %2% %3%"));

		messages.put("AddToPot", formatCustomMessage("message.AddToPot", "%prefix% Added &a%0% &rto pot. Extra total is &a%1%"));
		messages.put("ConfigCost", formatCustomMessage("message.ConfigCost", "%prefix% Cost changed to &c%0%"));
		messages.put("ConfigHours", formatCustomMessage("message.ConfigHours", "%prefix% Hours changed to &c%0%"));
		messages.put("ConfigMax", formatCustomMessage("message.ConfigMax", "%prefix% Max amount of tickets changed to &c%0%"));
		messages.put("ConfigReload", formatCustomMessage("message.ConfigReload", "%prefix% Config reloaded"));

		messages.put(
				"Help", formatCustomMessage(
				"message.Help",
				"%prefix% Help commands%newline%%prefix% &c/lottery&r : Basic lottery info.%newline%%prefix% &c/lottery buy <n>&r : Buy ticket(s)." +
				"%newline%%prefix% &c/lottery claim&r : Claim outstanding wins.%newline%%prefix% &c/lottery winners&r : Check last winners."));
		messages.put(
				"HelpAdmin", formatCustomMessage(
				"message.HelpAdmin",
				"%prefix% &1/lottery draw&r : Draw lottery.%newline%%prefix% &1/lottery addtopot&r : Add number to pot.%newline%%prefix% &1/lottery " +
				"config&r : Edit the config."));
		messages.put("HelpPot", formatCustomMessage("message.HelpPot", "%prefix% /lottery addtopot <number>"));
		messages.put(
				"HelpConfig", formatCustomMessage(
				"message.HelpConfig",
				"%prefix% Edit config commands%newline%%prefix% &c/lottery config cost <i>%newline%%prefix% &c/lottery config hours <i>%newline%%prefix% " +
				"&c/lottery config maxTicketsEachUser <i>%newline%%prefix% &c/lottery config reload"));
    messages.put("ticket", formatCustomMessage("message.Ticket", "ticket"));
    messages.put("tickets", formatCustomMessage("message.Tickets", "tickets"));
    messages.put("player", formatCustomMessage("message.player", "player"));
    messages.put("players", formatCustomMessage("message.players", "players"));
    messages.put("day", formatCustomMessage("message.day", "day"));
    messages.put("days", formatCustomMessage("message.days", "days"));
    messages.put("hour", formatCustomMessage("message.hour", "hour"));
    messages.put("hours", formatCustomMessage("message.hours", "hours"));
    messages.put("minute", formatCustomMessage("message.minute", "minute"));
    messages.put("minutes", formatCustomMessage("message.minutes", "minutes"));
    messages.put("second", formatCustomMessage("message.second", "second"));
    messages.put("seconds", formatCustomMessage("message.seconds", "seconds"));
	}

  public String getPlural(String word, int amount){
    String tlkey = "";

    if (word.equalsIgnoreCase("ticket"))
    {
      tlkey = amount == 1 ? "ticket" : "tickets";
    }
    if (word.equalsIgnoreCase("player"))
    {
      tlkey = amount == 1 ? "player" : "players";
    }
    if (word.equalsIgnoreCase("day"))
    {
      tlkey = amount == 1 ? "day" : "days";
    }
    if (word.equalsIgnoreCase("hour"))
    {
      tlkey = amount == 1 ? "hour" : "hours";
    }
    if (word.equalsIgnoreCase("minute"))
    {
      tlkey = amount == 1 ? "minute" : "minutes";
    }
    if (word.equalsIgnoreCase("second"))
    {
      tlkey = amount == 1 ? "second" : "seconds";
    }

    String pluralTl = "";
    try {
      for (String message : getMessage(tlkey)){
        pluralTl = message;
      }
      return pluralTl;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return pluralTl;
  }

	public List<String> getMessage(final String topic) throws Exception
	{
		if (!messages.containsKey(topic))
		{
			throw new Exception("Invalid Translation key");
		}
		return Collections.unmodifiableList(messages.get(topic));
	}

	public List<String> formatCustomMessage(final String node, final String def)
	{
		final List<String> fList = new ArrayList<String>();
		// Lets find a msg.
		final String msg = config.getString(node, def);
		config.set(node, msg);

		// Lets put this in a arrayList in case we want more than one line.
		Collections.addAll(fList, msg.split("%newline%"));
		return fList;
	}

	// Enable some debugging?
	public void debugMsg(final String msg)
	{
		if (config.getBoolean("config.debug") && msg != null)
		{
			plugin.getLogger().log(Level.INFO, msg);
		}
	}

	public double getCost()
	{
		return cost;
	}

	public void setCost(final double cost)
	{
		this.cost = cost;
		set("config.cost", cost);
	}

	public double getHours()
	{
		return hours;
	}

	public void setHours(final double hours)
	{
		this.hours = hours;
		set("config.hours", hours);
	}

	public long getNextexec()
	{
		return nextexec;
	}

	public void setNextexec(final long nextexec)
	{
		this.nextexec = nextexec;
		set("config.nextexec", nextexec);
	}

	public boolean useiConomy()
	{
		return useiConomy;
	}

	public int getMaterial()
	{
		return material;
	}

	public double getExtraInPot()
	{
		return extraInPot;
	}

	public void setExtraInPot(final double extraInPot)
	{
		this.extraInPot = extraInPot;
		set("config.extraInPot", extraInPot);
	}

	public void addExtraInPot(final double extra)
	{
		extraInPot += extra;
		setExtraInPot(extraInPot);
	}

	public boolean useBroadcastBuying()
	{
		return broadcastBuying;
	}

	public int getBroadcastBuyingTime()
	{
		return broadcastBuyingTime;
	}

	public boolean useWelcomeMessage()
	{
		return welcomeMessage;
	}

	public double getNetPayout()
	{
		return netPayout;
	}

	public void setNetPayout(final double netPayout)
	{
		this.netPayout = netPayout;
		set("config.netPayout", netPayout);
	}

	public boolean clearExtraInPot()
	{
		return clearExtraInPot;
	}

	public int getMaxTicketsEachUser()
	{
		return maxTicketsEachUser;
	}

	public void setMaxTicketsEachUser(final int maxTicketsEachUser)
	{
		this.maxTicketsEachUser = maxTicketsEachUser;
		set("config.maxTicketsEachUser", maxTicketsEachUser);
	}

	public int getTicketsAvailable()
	{
		return ticketsAvailable;
	}

	public double getJackpot()
	{
		return jackpot;
	}

	public void setJackpot(final double jackpot)
	{
		this.jackpot = jackpot;
		set("config.jackpot", jackpot);
	}

	public String getLastwinner()
	{
		return lastwinner;
	}

	public void setLastwinner(final String lastwinner)
	{
		this.lastwinner = lastwinner;
		set("config.lastwinner", lastwinner);
	}

	public double getLastwinneramount()
	{
		return lastwinneramount;
	}

	public void setLastwinneramount(final double lastwinneramount)
	{
		this.lastwinneramount = lastwinneramount;
		set("config.lastwinneramount", lastwinneramount);
	}

	public boolean isBuyingExtendDeadline()
	{
		return buyingExtendDeadline;
	}

	public int getBuyingExtendRemaining()
	{
		return buyingExtendRemaining;
	}

	public double getBuyingExtendBase()
	{
		return buyingExtendBase;
	}

	public double getBuyingExtendMultiplier()
	{
		return buyingExtendMultiplier;
	}

	public String getTaxTarget()
	{
		return taxTarget;
	}

	public String formatCurrency(double amount)
	{
		return plugin.Method.format(amount);
	}
}
