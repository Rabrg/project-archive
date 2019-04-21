package me.rabrg.mercury;

import java.util.logging.Level;

import javax.mail.MessagingException;

import me.rabrg.mercury.command.EmailCommandExecutor;

import org.bukkit.plugin.java.JavaPlugin;

public final class Plugin extends JavaPlugin {

	private MessageTransporter messageTransporter;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		try {
			messageTransporter = new MessageTransporter(getConfig().getString("username"), getConfig().getString("password"));
		} catch (MessagingException e) {
			getLogger().log(Level.SEVERE, "Exception thrown while creating the message transporter", e);
			setEnabled(false);
			return;
		}
		
		getCommand("email").setExecutor(new EmailCommandExecutor(this));
	}

	public MessageTransporter getMessageTransporter() {
		return messageTransporter;
	}
}
