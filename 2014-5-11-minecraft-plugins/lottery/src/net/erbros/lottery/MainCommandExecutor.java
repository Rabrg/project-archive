package net.erbros.lottery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import net.erbros.lottery.register.payment.Methods;


public class MainCommandExecutor implements CommandExecutor
{

	final private Lottery plugin;
	final private LotteryConfig lConfig;
	final private LotteryGame lGame;

	public MainCommandExecutor(final Lottery plugin)
	{
		this.plugin = plugin;
		lConfig = plugin.getLotteryConfig();
		lGame = plugin.getLotteryGame();
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args)
	{

		// Lets check if we have found a plugin for money.
		if (lConfig.useiConomy() && !Methods.hasMethod())
		{
			lConfig.debugMsg("No money plugin found yet.");
			lGame.sendMessage(sender, "ErrorPlugin");
			return true;
		}

		// Can the player access the plugin?
		if (!sender.hasPermission("lottery.buy"))
		{
			lGame.sendMessage(sender, "ErrorAccess");
		}

		// If its just /lottery, and no args.
		if (args.length == 0)
		{
			commandNull(sender, args);
		}
		else if (args[0].equalsIgnoreCase("buy"))
		{
			commandBuy(sender, args);
		}
		else if (args[0].equalsIgnoreCase("claim"))
		{
			commandClaim(sender, args);
		}
		else if (args[0].equalsIgnoreCase("winners"))
		{
			commandWinners(sender, args);
		}
		else if (args[0].equalsIgnoreCase("messages"))
		{
			commandMessages(sender, args);
		}
		else if (args[0].equalsIgnoreCase("help"))
		{
			commandHelp(sender, args);
		}
		else if (args[0].equalsIgnoreCase("draw"))
		{
			if (sender.hasPermission("lottery.admin.draw"))
			{
				commandDraw(sender, args);
			}
			else
			{
				lGame.sendMessage(sender, "ErrorAccess");
			}
		}
		else if (args[0].equalsIgnoreCase("addtopot"))
		{
			if (sender.hasPermission("lottery.admin.addtopot"))
			{
				commandAddToPot(sender, args);
			}
			else
			{
				lGame.sendMessage(sender, "ErrorAccess");
			}
		}
		else if (args[0].equalsIgnoreCase("config"))
		{
			if (sender.hasPermission("lottery.admin.editconfig"))
			{
				commandConfig(sender, args);
			}
			else
			{
				lGame.sendMessage(sender, "ErrorAccess");
			}
		}
		else
		{
			lGame.sendMessage(sender, "ErrorCommand");
		}

		return true;
	}

	public void commandNull(final CommandSender sender, final String[] args)
	{
		// Is this a console? If so, just tell that lottery is running and time until next draw.
		if (!(sender instanceof Player))
		{
			sender.sendMessage("Hi Console - The Lottery plugin is running");
			lGame.sendMessage(sender, "DrawIn", lGame.timeUntil(false));
			return;
		}
		final Player player = (Player)sender;

		// Check if we got any money/items in the pot.
		final double amount = lGame.winningAmount();
		lConfig.debugMsg("pot current total: " + amount);
		// Send some messages:
		lGame.sendMessage(sender, "DrawIn", lGame.timeUntil(false));
		lGame.sendMessage(sender, "TicketCommand");
		lGame.sendMessage(sender, "PotAmount");
		if (lConfig.getMaxTicketsEachUser() > 1)
		{
			lGame.sendMessage(
					player, "YourTickets", lGame.playerInList(player), lConfig.getPlural("ticket", lGame.playerInList(player)));
		}
		// Number of tickets available?
		if (lConfig.getTicketsAvailable() > 0)
		{
			lGame.sendMessage(
					sender, "TicketRemaining", (lConfig.getTicketsAvailable() - lGame.ticketsSold()), lConfig.getPlural(
					"ticket", lConfig.getTicketsAvailable() - lGame.ticketsSold()));
		}
		lGame.sendMessage(sender, "CommandHelp");

		// Does lastwinner exist and != null? Show.
		// Show different things if we are using iConomy over
		// material.
		if (lConfig.getLastwinner() != null)
		{
			lGame.sendMessage(sender, "LastWinner", lConfig.getLastwinner(), Etc.formatCost(lConfig.getLastwinneramount(), lConfig));
		}

		// if not iConomy, make players check for claims.
		if (!lConfig.useiConomy())
		{
			lGame.sendMessage(sender, "CheckClaim");
		}
	}

	public void commandMessages(final CommandSender sender, final String[] args)
	{
		if (!(sender instanceof Player)) {
			lGame.sendMessage(sender, "ErrorConsole3");
			return;
		}
		Player player = (Player)sender;

		if (player.hasMetadata("LotteryOptOut") && player.getMetadata("LotteryOptOut").get(0).asBoolean()) {
			player.setMetadata("LotteryOptOut", new FixedMetadataValue(plugin, false));
			lGame.sendMessage(sender, "MessagesEnabled");
		}
		else {
			player.setMetadata("LotteryOptOut", new FixedMetadataValue(plugin, true));
			lGame.sendMessage(sender, "MessagesDisabled");
		}
	}

	public void commandHelp(final CommandSender sender, final String[] args)
	{
		lGame.sendMessage(sender, "Help");
		// Are we dealing with admins?
		if (sender.hasPermission("lottery.admin.draw") || sender.hasPermission("lottery.admin.addtopot") || sender.hasPermission("lottery.admin.editconfig"))
		{
			lGame.sendMessage(sender, "HelpAdmin");
		}
	}

	public void commandBuy(final CommandSender sender, final String[] args)
	{
		// Is this a console? If so, just tell that lottery is running and time until next draw.
		if (!(sender instanceof Player))
		{
			lGame.sendMessage(sender, "ErrorConsole");
			return;
		}
		final Player player = (Player)sender;

		int buyTickets = 1;
		if (args.length > 1)
		{
			// How many tickets do the player want to buy?
			buyTickets = Etc.parseInt(args[1]);

			if (buyTickets < 1)
			{
				buyTickets = 1;
			}
		}

		final int allowedTickets = lConfig.getMaxTicketsEachUser() - lGame.playerInList(player);

		if (buyTickets > allowedTickets && allowedTickets > 0)
		{
			buyTickets = allowedTickets;
		}

		// Have the admin entered a max number of tickets in the lottery?
		if (lConfig.getTicketsAvailable() > 0)
		{
			// If so, can this user buy the selected amount?
			if (lGame.ticketsSold() + buyTickets > lConfig.getTicketsAvailable())
			{
				if (lGame.ticketsSold() >= lConfig.getTicketsAvailable())
				{
					lGame.sendMessage(sender, "ErrorNoAvailable");
					return;
				}
				else
				{
					buyTickets = lConfig.getTicketsAvailable() - lGame.ticketsSold();
				}
			}
		}

		if (lConfig.getMaxTicketsEachUser() > 0 && lGame.playerInList(
				player) + buyTickets > lConfig.getMaxTicketsEachUser())
		{
			lGame.sendMessage(sender, "ErrorAtMax", lConfig.getMaxTicketsEachUser(), lConfig.getPlural("ticket", lConfig.getMaxTicketsEachUser()));
			return;
		}

		if (lGame.addPlayer(player, lConfig.getMaxTicketsEachUser(), buyTickets))
		{
			// You got your ticket.
			lGame.sendMessage(
					sender, "BoughtTicket", buyTickets, lConfig.getPlural("ticket", buyTickets), Etc.formatCost(lConfig.getCost() * buyTickets, lConfig));

			// Can a user buy more than one ticket? How many
			// tickets have he bought now?
			if (lConfig.getMaxTicketsEachUser() > 1)
			{
				lGame.sendMessage(
						sender, "BoughtTickets", lGame.playerInList(player), lConfig.getPlural("ticket", lGame.playerInList(player)));
			}
			if (lConfig.isBuyingExtendDeadline() && lGame.timeUntil() < lConfig.getBuyingExtendRemaining())
			{
				final long timeBonus = (long)(lConfig.getBuyingExtendBase() + (lConfig.getBuyingExtendMultiplier() * Math.sqrt(
						buyTickets)));
				lConfig.setNextexec(lConfig.getNextexec() + (timeBonus * 1000));
			}
			if (lConfig.useBroadcastBuying())
			{
				if (lGame.timeUntil() < lConfig.getBroadcastBuyingTime())
				{
					lGame.broadcastMessage(
							"BoughtAnnounceDraw", player.getDisplayName(), buyTickets, lConfig.getPlural("ticket", buyTickets), lGame.timeUntil(true));
				}
				else
				{
					lGame.broadcastMessage(
							"BoughtAnnounce", player.getDisplayName(), buyTickets, lConfig.getPlural("ticket", buyTickets));
				}
			}

		}
		else
		{
			// Something went wrong.
			lGame.sendMessage(sender,"ErrorNotAfford");
		}

	}

	public void commandClaim(final CommandSender sender, final String[] args)
	{
		// Is this a console? If so, just tell that lottery is running and time until next draw.
		if (!(sender instanceof Player))
		{
			lGame.sendMessage(sender,"ErrorConsole2");
			return;
		}

		lGame.removeFromClaimList((Player)sender);
	}

	public void commandDraw(final CommandSender sender, final String[] args)
	{
		// Start a timer that ends in 3 secs.
		lGame.sendMessage(sender,"DrawNow");
		plugin.startTimerSchedule(true);
	}

	public void commandWinners(final CommandSender sender, final String[] args)
	{
		// Get the winners.
		final ArrayList<String> winnerArray = new ArrayList<String>();
		try
		{
			final BufferedReader in = new BufferedReader(
					new FileReader(plugin.getDataFolder() + File.separator + "lotteryWinners.txt"));
			String str;
			while ((str = in.readLine()) != null)
			{
				winnerArray.add(str);
			}
			in.close();
		}
		catch (IOException e)
		{
		}
		String[] split;
		String winListPrice;
		for (int i = 0; i < winnerArray.size(); i++)
		{
			split = winnerArray.get(i).split(":");
			if (split[2].equalsIgnoreCase("0"))
			{
				winListPrice = plugin.Method.format(Double.parseDouble(split[1]));
			}
			else
			{
				winListPrice = split[1] + " " + Etc.formatMaterialName(
						Integer.parseInt(split[2]));
			}
			sender.sendMessage((i + 1) + ". " + split[0] + " " + winListPrice);
		}
	}

	public void commandAddToPot(final CommandSender sender, final String[] args)
	{
		if (args.length < 2)
		{
			lGame.sendMessage(sender,"HelpPot");
			return;
		}

		final double addToPot = Etc.parseDouble(args[1]);

		if (addToPot == 0)
		{
			lGame.sendMessage(sender,"ErrorNumber");
			return;
		}
		lConfig.addExtraInPot(addToPot);
		lGame.sendMessage(sender,"AddToPot", addToPot, lConfig.getExtraInPot());
	}

	public void commandConfig(final CommandSender sender, final String[] args)
	{
		if (args.length == 1)
		{
			lGame.sendMessage(sender,"HelpConfig");
			return;
		}
		else if (args.length > 2)
		{
			if (args[1].equalsIgnoreCase("cost"))
			{
				final double newCoin = Etc.parseDouble(args[2]);
				if (newCoin <= 0)
				{
					lGame.sendMessage(sender,"ErrorNumber");
				}
				else
				{
					lGame.sendMessage(sender,"ConfigCost", newCoin);
					lConfig.setCost(newCoin);
				}
			}
			else if (args[1].equalsIgnoreCase("hours"))
			{
				final double newHours = Etc.parseDouble(args[2]);
				if (newHours <= 0)
				{
					lGame.sendMessage(sender,"ErrorNumber");
				}
				else
				{
					lGame.sendMessage(sender,"ConfigHours", newHours);
					lConfig.setHours(newHours);
				}

			}
			else if (args[1].equalsIgnoreCase("maxTicketsEachUser") || args[1].equalsIgnoreCase("max"))
			{
				final int newMaxTicketsEachUser = Etc.parseInt(args[2]);
				lGame.sendMessage(sender,"ConfigMax", newMaxTicketsEachUser);
				lConfig.setMaxTicketsEachUser(newMaxTicketsEachUser);
			}
		}
		// Lets just reload the config.
		lConfig.loadConfig();
		lGame.sendMessage(sender,"ConfigReload");
	}
}
