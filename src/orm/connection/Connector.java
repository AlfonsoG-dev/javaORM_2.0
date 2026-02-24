package orm.connection;

import java.sql.Connection;
import java.sql.DriverManager;

import orm.utils.formats.DbConfig;

public class Connector {
    private DbConfig config;
    public Connector(DbConfig config) {
        this.config = config;
    }
    public Connection mysqlConnection() {
        Connection c = null;
        try {
            Class.forName(config.DRIVER);
            c = DriverManager.getConnection(
                    config.getURL(),
                    config.getUser(),
                    config.getPassword()
            );
        } catch(Exception e) {
            e.printStackTrace();
        }
        return c;
    }
}
