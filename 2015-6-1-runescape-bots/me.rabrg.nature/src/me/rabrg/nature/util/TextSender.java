package me.rabrg.nature.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import me.rabrg.nature.RabrgNature;

public class TextSender {

	public static void main(final String[] args) throws Exception {
		RabrgNature.SENDER.send("5618561647@MyMetroPcs.com", "test message from rabrg");
		RabrgNature.SENDER.send("yabit@pcs.rogers.com", "test message from rabrg");
	}

	private final Properties mail;
	private final Session session;

	public TextSender(final String email, final String password) {
		this.mail = new Properties();
		this.mail.put("mail.smtp.auth", "true");
		this.mail.put("mail.smtp.starttls.enable", "true");
		this.mail.put("mail.smtp.host", "smtp.gmail.com");
		this.mail.put("mail.smtp.port", "587");

		this.session = Session.getInstance(mail, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(email, password);
			}
		});
	}

	public void send(String phoneNumber, String textMessage) throws Exception {
		Message message = new MimeMessage(session);
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(phoneNumber));
		message.setText(textMessage);

		Transport.send(message);
		Transport.send(message);
		Transport.send(message);
	}
}