import java.sql.Connection;

import ORM.Builders.Migration.MigrationBuilder;
import ORM.DbConnection.Connector;

import ORM.Utils.Formats.DbConfig;
import Samples.Models.TestModel;


class javaORM {
    public static void main(String[] args) {
        Connection cursor = initDB();
        MigrationBuilder builder = new MigrationBuilder("users", cursor);
        TestModel model = new TestModel();
        System.out.println(builder.getAddColumnQuery(model.initModel(), args, args, true));
    }

    public static Connection initDB() {
        DbConfig c = new DbConfig("consulta", "localhost", 3306, "test_user", "5x5W12");
        Connector cursor = new Connector(c);
        return cursor.mysqlConnection();
    }
}
