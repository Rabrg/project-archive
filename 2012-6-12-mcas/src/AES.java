import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Uses AES to encrypt and decrypt a message.
 * 
 * @author Ryan Greene
 */
public class AES {

	public static byte[] sdmg = null;
	public static byte[] iqweht = null;

	/**
	 * The key generator instance.
	 */
	private static KeyGenerator keyGenerator;

	/**
	 * The secret key spec instance.
	 */
	private static SecretKeySpec secretkeySpec;

	/**
	 * The cipher instance.
	 */
	private static Cipher cipher;

	/**
	 * The secret key instance.
	 */
	private static SecretKey secretKey;

	/**
	 * The key size.
	 */
	private static final int KEY_SIZE = 128;

	/**
	 * Decrypts the message.
	 * 
	 * @param encrypted
	 *            The encrypted message.
	 * @return The decrypted message.
	 */
	public static String decrypt(byte[] encrypted) {
		try {
			cipher.init(Cipher.DECRYPT_MODE, secretkeySpec);
			return new String(cipher.doFinal(encrypted));
		} catch (Exception ex) {
			System.err.println(ex);
		}
		return null;
	}

	/**
	 * Encrypts the message.
	 * 
	 * @return The encrypted message.
	 */
	public static byte[] encrypt(String decrypted) {
		try {
			cipher.init(Cipher.ENCRYPT_MODE, secretkeySpec);
			return cipher.doFinal(decrypted.getBytes());
		} catch (Exception ex) {
			System.err.println(ex);
		}
		return null;
	}

	/**
	 * Runs the program.
	 * 
	 * @param args
	 *            The running arguments
	 */
	public static void test() {
		AES aes = new AES();
		AES.sdmg = encrypt("123edoi@gmail.com");
		AES.iqweht = encrypt("penislol");
	}

	/**
	 * The constructor.
	 */
	public AES() {
		try {
			cipher = Cipher.getInstance("AES");
			keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(KEY_SIZE);
			secretKey = keyGenerator.generateKey();
			byte[] raw = secretKey.getEncoded();
			secretkeySpec = new SecretKeySpec(raw, "AES");
		} catch (NoSuchAlgorithmException ex) {
			System.err.println(ex);
		} catch (NoSuchPaddingException ex) {
			System.err.println(ex);
		}
	}
}