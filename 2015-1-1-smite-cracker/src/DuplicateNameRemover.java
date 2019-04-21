import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class DuplicateNameRemover {

	public static void main(String[] args) throws IOException {
		final List<String> usernamesUnchecked = Files.readAllLines(Paths.get("usernames2.txt"), Charset.forName("UTF-8"));
		final List<String> usernames = new ArrayList<String>();
		final Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		for (final String username : usernamesUnchecked) {
			if (!p.matcher(username).find() && !usernames.contains(username)) {
				usernames.add(username);
			}
		}
		Files.write(Paths.get("usernames.txt"), usernames, Charset.forName("UTF-8"), StandardOpenOption.CREATE);
	}

}
