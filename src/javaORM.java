import java.sql.Connection;

import ORM.Database.Connector;
import ORM.Utils.Formats.DbConfig;

class javaORM {
    public static void main(String[] args) {
        initDB();
    }

    private static Connection initDB() {
        DbConfig c = new DbConfig("consulta", "localhost", 3306, "test_user", "5x5W12");
        Connector cursor = new Connector(c);
        return cursor.mysqlConnection();
    }
}
