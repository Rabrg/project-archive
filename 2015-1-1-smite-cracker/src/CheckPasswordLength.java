import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public final class CheckPasswordLength {

	public static void main(String[] args) throws IOException {
		final List<String> passwords = Files.readAllLines(Paths.get("passwords.txt"));
		for (final String password : passwords) {
			if (password.length() >= 7) {
				System.out.println(password);
			}
		}
	}

}
