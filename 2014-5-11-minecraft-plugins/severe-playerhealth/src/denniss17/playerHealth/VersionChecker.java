package denniss17.playerHealth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * VersionChecker v 1.2 This generic class is used to check for updates of the
 * corresponding plugin. It is compatible with every plugin This class is now
 * using the ServerMods API
 * 
 * @link http://wiki.bukkit.org/ServerMods_API
 * @author Denniss17
 * @version 1.2.0
 */
public class VersionChecker implements Runnable {
	// The project's unique ID
	private final int projectID;

	// Keys for extracting file information from JSON response
	private static final String API_NAME_VALUE = "name";

	// Static information for querying the API
	private static final String API_QUERY = "/servermods/files?projectIds=";
	private static final String API_HOST = "https://api.curseforge.com";

	public String versionName = null;
	private String apiKey = null;
	private final JavaPlugin plugin;

	public VersionChecker(final JavaPlugin plugin, final int projectID) {
		this.plugin = plugin;
		this.projectID = projectID;
	}

	public VersionChecker(final JavaPlugin plugin, final int projectID, final String apiKey) {
		this.plugin = plugin;
		this.projectID = projectID;
		this.apiKey = apiKey;
	}

	/**
	 * Returns the latest version for this plugin, or null if unknown
	 * 
	 * @return the latest version or null
	 */
	public String getLatestVersionName() {
		return this.versionName;
	}

	/**
	 * Activate this versionChecker to check for updates with the given interval
	 * (in ticks)
	 * 
	 * @param interval
	 *            The interval in ticks
	 * */
	public void activate(final long interval) {
		plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, this, 0, interval);
	}

	/**
	 * Query for updates via the ServerMods API
	 */
	@Override
	public void run() {
		URL url = null;

		plugin.getLogger().info("Checking for updates...");

		try {
			// Create the URL to query using the project's ID
			url = new URL(API_HOST + API_QUERY + projectID);
		} catch (final MalformedURLException e) {
			// There was an error creating the URL
			plugin.getLogger().info("Failed: Unable to create URL");
			return;
		}

		try {
			// Open a connection and query the project
			final URLConnection conn = url.openConnection();

			if (apiKey != null) {
				// Add the API key to the request if present
				conn.addRequestProperty("X-API-Key", apiKey);
			}

			// Add the user-agent to identify the program
			conn.addRequestProperty("User-Agent", plugin.getName() + "/v" + plugin.getDescription().getVersion() + " (by denniss17)");

			// Read the response of the query
			// The response will be in a JSON format, so only reading one line
			// is necessary.
			final BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final String response = reader.readLine();

			// Parse the array of files from the query's response
			final JSONArray array = (JSONArray) JSONValue.parse(response);

			if (array.size() > 0) {
				// Get the newest file's details
				final JSONObject latest = (JSONObject) array.get(array.size() - 1);

				// Get the version's title
				versionName = (String) latest.get(API_NAME_VALUE);

				if (versionName.equals(plugin.getDescription().getVersion())) {
					plugin.getLogger().info("You have the latest version.");
				} else {
					plugin.getLogger().info("There is a new version (" + versionName + ") available. Download it here: " + plugin.getDescription().getWebsite());
				}
			}
		} catch (final IOException e) {
			plugin.getLogger().info("Failed: " + e.getMessage());
			return;
		}
	}

}
