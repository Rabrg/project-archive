package net.erbros.lottery;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.bukkit.Material;


public class Etc
{
	public static String formatCost(double cost, LotteryConfig lConfig)
	{
		if (lConfig.useiConomy())
		{
			return lConfig.formatCurrency((formatAmount(cost, lConfig.useiConomy())));
		}
		else
		{
			return String.valueOf(
					(int)formatAmount(cost, lConfig.useiConomy())).concat(
					" " + formatMaterialName(lConfig.getMaterial()));
		}
	}

	public static double formatAmount(double amount, final boolean usingiConomy)
	{

		if (usingiConomy)
		{
			return Math.floor(amount * 100) / 100;
		}
		else
		{
			return Math.floor(amount);
		}
	}

	public static String formatMaterialName(final int materialId)
	{
		String rawMaterialName = Material.getMaterial(materialId).toString();
		rawMaterialName = rawMaterialName.toLowerCase(Locale.ENGLISH);
		// Large first letter.
		final String firstLetterCapital = rawMaterialName.substring(0, 1).toUpperCase();
		rawMaterialName = firstLetterCapital + rawMaterialName.substring(1, rawMaterialName.length());
		return rawMaterialName.replace("_", " ");
	}



	public static String timeUntil(final long time, final boolean mini, LotteryConfig lConfig)
	{
		long timeLeft = time;
		// How many days left?
		String stringTimeLeft = "";

		if (timeLeft >= 60 * 60 * 24)
		{
			final int days = (int)Math.floor(timeLeft / (60 * 60 * 24));
			timeLeft -= 60 * 60 * 24 * days;
			if (mini)
			{
				stringTimeLeft += Integer.toString(days) + "d ";
			}
			else
			{
				stringTimeLeft += Integer.toString(days) + " " + lConfig.getPlural("day", days) + ", ";
			}
		}
		if (timeLeft >= 60 * 60)
		{
			final int hours = (int)Math.floor(timeLeft / (60 * 60));
			timeLeft -= 60 * 60 * hours;
			if (mini)
			{
				stringTimeLeft += Integer.toString(hours) + "h ";
			}
			else
			{
				stringTimeLeft += Integer.toString(hours) + " " + lConfig.getPlural("hour", hours) + ", ";
			}
		}
		if (timeLeft >= 60)
		{
			final int minutes = (int)Math.floor(timeLeft / (60));
			timeLeft -= 60 * minutes;
			if (mini)
			{
				stringTimeLeft += Integer.toString(minutes) + "m ";

			}
			else
			{
				stringTimeLeft += Integer.toString(minutes) + " " + lConfig.getPlural("minute", minutes) + ", ";
			}
		}
		else
		{
			// Lets remove the last comma, since it will look bad with 2 days, 3
			// hours, and 14 seconds.
			if (!stringTimeLeft.equalsIgnoreCase("") && !mini)
			{
				stringTimeLeft = stringTimeLeft.substring(
						0, stringTimeLeft.length() - 1);
			}
		}
		final int secs = (int)timeLeft;
		if (mini)
		{
			stringTimeLeft += secs + "s";
		}
		else
		{
			if (!stringTimeLeft.equalsIgnoreCase(""))
			{
				stringTimeLeft += "and ";
			}
			stringTimeLeft += Integer.toString(secs) + " " + lConfig.getPlural("second", secs);
		}

		return stringTimeLeft;
	}

	public static Map<String, Integer> realPlayersFromList(final List<String> ticketList)
	{
		final Map<String, Integer> playerList = new HashMap<String, Integer>();
		int value;
		for (String check : ticketList)
		{
			if (playerList.containsKey(check))
			{
				value = Integer.parseInt(playerList.get(check).toString()) + 1;
			}
			else
			{
				value = 1;
			}
			playerList.put(check, value);
		}
		return playerList;
	}

	public static int parseInt(final String arg)
	{
		int newInt = 0;
		try
		{
			newInt = Integer.parseInt(arg);
		}
		catch (NumberFormatException e)
		{
		}
		return newInt > 0 ? newInt : 0;
	}

	public static double parseDouble(final String arg)
	{
		double newDouble = 0;
		try
		{
			newDouble = Double.parseDouble(arg);
		}
		catch (NumberFormatException e)
		{
		}
		return newDouble > 0 ? newDouble : 0;
	}
}
