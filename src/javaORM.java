import java.sql.Connection;

import ORM.Builders.Migration.MigrationBuilder;
import ORM.DbConnection.Connector;

import ORM.Utils.Formats.DbConfig;

import Samples.Models.Primary.TestModel;
import Samples.Models.Foreign.*;

class javaORM {
    public static void main(String[] args) {
        Connection cursor = initDB();
        MigrationBuilder builder = new MigrationBuilder("users", cursor);
        TestModel model = new TestModel();
        UsersModel u = new UsersModel();
        String[]
            models = {u.initModel()},
            tables = {"users"};
        String b = builder.getAddColumnQuery(
            model.initModel(), models, tables, true
        );
        System.out.println(b);
    }

    public static Connection initDB() {
        DbConfig c = new DbConfig("consulta", "localhost", 3306, "test_user", "5x5W12");
        Connector cursor = new Connector(c);
        return cursor.mysqlConnection();
    }
}
