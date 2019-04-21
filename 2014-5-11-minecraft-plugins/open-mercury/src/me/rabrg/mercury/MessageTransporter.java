package me.rabrg.mercury;

import java.security.Security;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

/**
 * A class which transports messages.
 * @author Ryan Greene
 * 
 */
@SuppressWarnings("restriction")
public class MessageTransporter {

	/**
	 * The SSL socket factory used in the properties.
	 */
	private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

	static {
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	}

	/**
	 * The username of the account used to transport messages.
	 */
	private final String username;

	/**
	 * The session used to create and transport messages.
	 */
	private final Session session;

	/**
	 * The transport used to transport mesages.
	 */
	private final SMTPTransport transport;

	/**
	 * Constructs a new message transporter with the specified username and password.
	 * @param username The username of the account used to transport messages.
	 * @param password The password of the account used to transport messages.
	 * @throws MessagingException If an exception was thrown.
	 */
	public MessageTransporter(final String username, final String password) throws MessagingException {
		this.username = username;
		
		final Properties properties = System.getProperties();
		properties.setProperty("mail.smtps.host", "smtp.gmail.com");
		properties.setProperty("mail.smtp.socketFactory.class", MessageTransporter.SSL_FACTORY);
		properties.setProperty("mail.smtp.socketFactory.fallback", "false");
		properties.setProperty("mail.smtp.port", "465");
		properties.setProperty("mail.smtp.socketFactory.port", "465");
		properties.setProperty("mail.smtps.auth", "true");
		properties.put("mail.smtps.quitwait", "false"); // wait
		
		session = Session.getInstance(properties, null);
		
		transport = (SMTPTransport) session.getTransport("smtps");
		transport.connect("smtp.gmail.com", username, password);
	}

	/**
	 * Sends a message to the specified recipient with the specified subject and test.
	 * @param recipient The recipient receiving the message.
	 * @param subject The subject of the message.
	 * @param text The text of the message.
	 * @return Whether or not the message was sent successfully.
	 */
	public boolean send(final String recipient, final String subject, final String text) {
		final MimeMessage message = new MimeMessage(session);
		
		try {
			message.setFrom(new InternetAddress(username + "@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, false));
			message.setSubject(subject);
			message.setText(text, "utf-8");
			message.setSentDate(new Date());
			
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			return true;
		} catch (final MessagingException e) {
			e.printStackTrace();
		}
		return false;
	}
}