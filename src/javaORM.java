import java.sql.Connection;

import ORM.Builders.Query.QueryBuilder;
import ORM.Database.Connector;
import ORM.Utils.Formats.DbConfig;
import ORM.Utils.Formats.ParamValue;

class javaORM {
    public static void main(String[] args) {
        QueryBuilder b = new QueryBuilder("user");
        String[] 
            c = {"nombre", "rol"},
            v = {"alfonso", "admin"};
        ParamValue value = new ParamValue(c, v, "or");
        String a = b.getPreparedSelect(value);
        System.out.println(a);
    }

    public static Connection initDB() {
        DbConfig c = new DbConfig("consulta", "localhost", 3306, "test_user", "5x5W12");
        Connector cursor = new Connector(c);
        return cursor.mysqlConnection();
    }
}
