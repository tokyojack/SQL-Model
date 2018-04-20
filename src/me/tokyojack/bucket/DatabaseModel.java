package package;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.scheduler.BukkitRunnable;

import lombok.Getter;

@Getter
public class DatabaseModel {

	private String tableQuery;
	private String tableName;

	private Class<?> resaultClass;

	public DatabaseModel(String tableName, String tableQuery, Class<?> resaultClass) {
		this.tableName = tableName;
		this.tableQuery = replaceTableNamePlaceholder(tableQuery).replace("CREATE TABLE", "CREATE TABLE IF NOT EXISTS");
		this.resaultClass = resaultClass;
	}

	protected void selectQuery(String query, SelectQuery selectQuery) {
		runAsync(new Async() {
			@Override
			public void connected(Connection connection) {
				try {
					PreparedStatement statement = connection.prepareStatement(replaceTableNamePlaceholder(query));
					ResultSet rs = statement.executeQuery();

					Constructor<?> constructor = resaultClass.getConstructor(ResultSet.class);

					while (rs.next()) {
						selectQuery.resault(constructor.newInstance(rs));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void nonReturningQuery(String query) {
		nonReturningQuery(query, null);
	}

	private void nonReturningQuery(String query, Query nonReturningQuery) {
		runAsync(new Async() {
			@Override
			public void connected(Connection connection) throws SQLException {
				PreparedStatement statement = connection.prepareStatement(replaceTableNamePlaceholder(query));
				statement.execute();

				if(nonReturningQuery != null)
					nonReturningQuery.complete();
			}
		});
	}

	public void createTable() {
		runAsync(new Async() {
			@Override
			public void connected(Connection connection) throws SQLException {
				PreparedStatement statement = connection.prepareStatement(tableQuery);
				statement.execute();
			}
		});
	}

	private void runAsync(Async async) {
		BukkitRunnable r = new BukkitRunnable() {
			@Override
			public void run() {
				try (Connection connection = DATABASE_CONNECTION) {
					async.connected(connection);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		r.runTaskAsynchronously(JAVA_PLUGIN);
	}

	private String replaceTableNamePlaceholder(String string) {
		return string.replace("%table%", this.tableName);
	}

}
