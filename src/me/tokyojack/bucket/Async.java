package package;

import java.sql.Connection;

public abstract class Async {

    public abstract void connected(Connection connection) throws Exception;

}
