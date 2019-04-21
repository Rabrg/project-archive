package net.erbros.lottery;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import net.erbros.lottery.register.payment.Methods;


public class PluginListener implements Listener
{

	final private Lottery plugin;
	final private Methods Methods;

	public PluginListener(final Lottery plugin)
	{
		this.plugin = plugin;
		this.Methods = new Methods();
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPluginDisable(final PluginDisableEvent event)
	{
		if (this.Methods != null && Methods.hasMethod())
		{
			final boolean check = Methods.checkDisabled(event.getPlugin());

			if (check)
			{
				this.plugin.Method = null;
				System.out.println("[Lottery] Payment method was disabled. No longer accepting payments.");
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPluginEnable(final PluginEnableEvent event)
	{
		if (!Methods.hasMethod())
		{
			if (Methods.setMethod(Bukkit.getPluginManager()))
			{
				this.plugin.Method = Methods.getMethod();
				System.out.println(
						"[Lottery] Payment method found (" + this.plugin.Method.getName() + " version: " + this.plugin.Method.getVersion() + ")");
			}
		}
	}
}