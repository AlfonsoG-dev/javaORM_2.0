import java.sql.Connection;

import ORM.Builders.Query.QueryBuilder;
import ORM.DbConnection.Connector;

import ORM.Utils.Formats.DbConfig;
import ORM.Utils.Formats.ParamValue;
import Samples.TestODM;

class javaORM {
    public static void main(String[] args) {
        TestODM[] tes = new TestODM[2];
        TestODM t = new TestODM("alfonso", "alf@gmail.com", "admin");
        TestODM t1 = new TestODM("sebastian", "sebas@gmail.com", "user");
        tes[0] = t;
        tes[1] = t1;
        String[] tables = {"account", "city"};
        ParamValue[] conditions = new ParamValue[2];
        String[] 
            columns1 = {"nombre", "edad"},
            values1 = {"nombre", "edad"},
            columns2 = {"nombre2", "edad2"},
            values2 = {"nombre2", "edad2"};
        ParamValue c1 = new ParamValue(columns1, values1, "and");
        ParamValue c2 = new ParamValue(columns2, values2, "or");
        conditions[0] = c1;
        conditions[1] = c2;
        QueryBuilder builder = new QueryBuilder("user");
        System.out.println(
                builder.getInnerJoinQuery(
                    t,
                    tes,
                    tables,
                    conditions
                )
        );
    }

    public static Connection initDB() {
        DbConfig c = new DbConfig("share_DB", "localhost", 3306, "test_user", "5x5W12");
        Connector cursor = new Connector(c);
        return cursor.mysqlConnection();
    }
}
