package me.rabrg.skywars.database;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;

import me.rabrg.skywars.SkyWars;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;

public class Database {

	private final String connectionUri;
	private final String username;
	private final String password;
	private Connection connection;

	public Database(final ConfigurationSection config) throws ClassNotFoundException, SQLException {
		final String hostname = config.getString("hostname", "localhost");
		final int port = config.getInt("port", 3306);
		final String database = config.getString("database", "");

		connectionUri = String.format("jdbc:mysql://%s:%d/%s", hostname, port, database);
		username = config.getString("username", "");
		password = config.getString("password", "");

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect();

		} catch (final SQLException sqlException) {
			close();
			throw sqlException;
		}
	}

	private void connect() throws SQLException {
		if (connection != null) {
			try {
				connection.createStatement().execute("SELECT 1;");

			} catch (final SQLException sqlException) {
				if (sqlException.getSQLState().equals("08S01")) {
					try {
						connection.close();

					} catch (final SQLException ignored) {
					}
				}
			}
		}

		if (connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(connectionUri, username, password);
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public void close() {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}

		} catch (final SQLException ignored) {

		}

		connection = null;
	}

	public boolean checkConnection() {
		try {
			connect();

		} catch (final SQLException sqlException) {
			close();
			sqlException.printStackTrace();

			return false;
		}

		return true;
	}

	public void createTables() throws IOException, SQLException {
		final URL resource = Resources.getResource(SkyWars.class, "/tables.sql");
		final String[] databaseStructure = Resources.toString(resource, Charsets.UTF_8).split(";");

		if (databaseStructure.length == 0) {
			return;
		}

		Statement statement = null;

		try {
			connection.setAutoCommit(false);
			statement = connection.createStatement();

			for (String query : databaseStructure) {
				query = query.trim();

				if (query.isEmpty()) {
					continue;
				}

				statement.execute(query);
			}

			connection.commit();

		} finally {
			connection.setAutoCommit(true);

			if (statement != null && !statement.isClosed()) {
				statement.close();
			}
		}
	}

	public boolean doesPlayerExist(final String player) {
		if (!checkConnection()) {
			return false;
		}

		int count = 0;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			final StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("SELECT Count(`player_id`) ");
			queryBuilder.append("FROM `skywars_player` ");
			queryBuilder.append("WHERE `player_name` = ? ");
			queryBuilder.append("LIMIT 1;");

			preparedStatement = connection.prepareStatement(queryBuilder.toString());
			preparedStatement.setString(1, player);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				count = resultSet.getInt(1);
			}

		} catch (final SQLException sqlException) {
			sqlException.printStackTrace();

		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (final SQLException ignored) {
				}
			}

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (final SQLException ignored) {
				}
			}
		}

		return count > 0;
	}

	public void createNewPlayer(final String player) {
		if (!checkConnection()) {
			return;
		}

		PreparedStatement preparedStatement = null;

		try {
			final StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("INSERT INTO `skywars_player` ");
			queryBuilder.append("(`player_id`, `player_name`, `first_seen`, `last_seen`) ");
			queryBuilder.append("VALUES ");
			queryBuilder.append("(NULL, ?, NOW(), NOW());");

			preparedStatement = connection.prepareStatement(queryBuilder.toString());
			preparedStatement.setString(1, player);
			preparedStatement.executeUpdate();

		} catch (final SQLException sqlException) {
			sqlException.printStackTrace();

		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (final SQLException ignored) {
				}
			}
		}
	}

	public Map<String, Integer> getTopScore(final int limit) {
		final Map<String, Integer> topScore = Maps.newLinkedHashMap();

		if (!checkConnection()) {
			return topScore;
		}

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			final StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("SELECT ");
			queryBuilder.append("`player_name`, `score` ");
			queryBuilder.append("FROM ");
			queryBuilder.append("`skywars_player` ");
			queryBuilder.append("ORDER BY `score` DESC LIMIT ?;");

			preparedStatement = connection.prepareStatement(queryBuilder.toString());
			preparedStatement.setInt(1, limit);
			resultSet = preparedStatement.executeQuery();

			while (resultSet != null && resultSet.next()) {
				topScore.put(resultSet.getString("player_name"), resultSet.getInt("score"));
			}

		} catch (final SQLException sqlException) {
			sqlException.printStackTrace();

		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (final SQLException ignored) {
				}
			}

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (final SQLException ignored) {
				}
			}
		}

		return topScore;
	}
}
