package package;

public class PlayerStatModel extends DatabaseModel {

    public PlayerStatModel() {
        super("stats", "CREATE TABLE %table%(username VARCHAR(255), total_kills INT, total_deaths INT)", PlayerStat.class);
    }

    public void select(String username, SelectQuery selectQuery) {
        selectQuery("SELECT * FROM %table% WHERE username='" + username + "'", selectQuery);
    }

    public void insert(String username) {
        nonReturningQuery("INSERT INTO %table%(username, total_kills, total_deaths) VALUES('" + username + ", 0, 0')");
    }

    public void addKill(String username) {
        nonReturningQuery("UPDATE %table% SET total_kills = total_kills + 1 WHERE username='" + username + "'");
    }

    public void addDeath(String username) {
        nonReturningQuery("UPDATE %table% SET total_deaths = total_deaths + 1 WHERE username='" + username + "'");
    }

    public void delete(String username) {
        nonReturningQuery("DELETE FROM %table% WHERE username='" + username + "'");
    }

}