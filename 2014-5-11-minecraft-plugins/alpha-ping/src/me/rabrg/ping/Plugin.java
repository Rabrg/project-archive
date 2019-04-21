package me.rabrg.ping;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.comphenix.protocol.AsynchronousManager;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

public final class Plugin extends JavaPlugin {

	private ProtocolManager protocolManager;

	@Override
	public void onEnable() {
		protocolManager = ProtocolLibrary.getProtocolManager();
	    
	}
}
