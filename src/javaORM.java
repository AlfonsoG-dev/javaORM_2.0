import java.sql.Connection;

import ORM.DbConnection.Connector;

import ORM.Utils.Formats.DbConfig;



public class javaORM {
    public static void main(String[] args) {
    }

    public static Connection initDB() {
        DbConfig c = new DbConfig("consulta", "localhost", 3306, "test_user", "5x5W12");
        Connector cursor = new Connector(c);
        return cursor.mysqlConnection();
    }
}
