import java.sql.Connection;

import ORM.Builders.Query.QueryBuilder;
import ORM.DbConnection.Connector;

import ORM.Utils.Formats.DbConfig;

import Samples.TestODM;

class javaORM {
    public static void main(String[] args) {
        TestODM t = new TestODM("alfonso", "alf@gmail.com", "admin");
        QueryBuilder builder = new QueryBuilder("user");
        System.out.println(builder.getPreparedInsertQuery(t));
    }

    public static Connection initDB() {
        DbConfig c = new DbConfig("share_DB", "localhost", 3306, "test_user", "5x5W12");
        Connector cursor = new Connector(c);
        return cursor.mysqlConnection();
    }
}
