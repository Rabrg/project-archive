package net.erbros.lottery;

import java.util.TimerTask;


class LotteryDraw extends TimerTask
{

	final private Lottery plugin;
	final private boolean draw;

	public LotteryDraw(final Lottery plugin, final boolean draw)
	{
		this.plugin = plugin;
		this.draw = draw;
	}

	public void run()
	{

		if (draw && plugin.isLotteryDue())
		{
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(
					plugin, new Runnable()
			{
				@Override
				public void run()
				{
					plugin.lotteryDraw();
				}
			});

		}
		else
		{
			plugin.extendLotteryDraw();
		}
	}
}