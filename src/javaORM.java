import java.sql.Connection;

import ORM.DbConnection.Connector;
import ORM.DbConnection.DAO.QueryDAO;

import ORM.Utils.Formats.DbConfig;

import Samples.Models.Primary.UserModel;


public class javaORM {
    public static void main(String[] args) {
        Connection cursor = initDB();
        QueryDAO<UserModel> dao = new QueryDAO<>(cursor, "users", new UserModel());
        for(UserModel m: dao.readAll()) {
            System.out.println(m.getInstanceData());
        }
    }

    public static Connection initDB() {
        DbConfig c = new DbConfig("consulta", "localhost", 3306, "test_user", "5x5W12");
        Connector cursor = new Connector(c);
        return cursor.mysqlConnection();
    }
}
