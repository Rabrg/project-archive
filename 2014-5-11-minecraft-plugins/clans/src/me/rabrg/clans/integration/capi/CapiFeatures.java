package me.rabrg.clans.integration.capi;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import me.rabrg.clans.P;

public class CapiFeatures {
	public static void setup() {
		final Plugin plug = Bukkit.getServer().getPluginManager().getPlugin("capi");
		if (plug != null && plug.getClass().getName().equals("me.rabrg.capi.P")) {
			P.p.log("Integration with the CAPI plugin was successful");
			Bukkit.getPluginManager().registerEvents(new PluginCapiListener(P.p), P.p);
		}
	}
}
