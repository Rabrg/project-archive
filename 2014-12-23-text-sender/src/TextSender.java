import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class TextSender {
	
	private final Properties mail;
	private final Session session;
	
	public TextSender(final String email, final String password) {
		this.mail = new Properties();
		this.mail.put("mail.smtp.auth", "true");
		this.mail.put("mail.smtp.starttls.enable", "true");
		this.mail.put("mail.smtp.host", "smtp.gmail.com");
		this.mail.put("mail.smtp.port", "587");
		this.session = Session.getInstance(mail, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(email, password);
			}
		});
	}
	
	public void send(String phoneNumber, String textMessage, final Carrier carrier) throws Exception {
		final Message message = new MimeMessage(session);
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(phoneNumber + carrier.getCarrier()));
		message.setText(textMessage);
		Transport.send(message);
	}

	public enum Carrier {
		ATT("@txt.att.net"), SPRINT("@messaging.sprintpcs.com"), TMOBILE("@tmomail.net"),VERIZON("@vtext.com"), VIRGIN("@vmobl.com");
		
		private final String carrier;
		
		private Carrier(final String carrier) {
			this.carrier = carrier;
		}
		
		public String getCarrier() {
			return carrier;
		}
		
	}
}