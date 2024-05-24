import java.sql.Connection;
import java.util.List;

import ORM.DbConnection.Connector;
import ORM.DbConnection.DAO.QueryDAO;
import ORM.Utils.Formats.DbConfig;
import ORM.Utils.Formats.ParamValue;
import Samples.Models.Foreign.UsersODM;


class javaORM {
    public static void main(String[] args) {
        Connection cursor = initDB();
        UsersODM m = new UsersODM();
        QueryDAO<UsersODM> dao = new QueryDAO<>(cursor, "test", m);
        String[]
            c = {"rol"},
            v = {"testing"};
        ParamValue condition = new ParamValue(c, v, "and");
        List<UsersODM> models = dao.preparedSelect(condition);
        if(models.size() > 0) {
            for(UsersODM model: models) {
                System.out.println(model.getInstanceData());
            }
        }
    }

    public static Connection initDB() {
        DbConfig c = new DbConfig("consulta", "localhost", 3306, "test_user", "5x5W12");
        Connector cursor = new Connector(c);
        return cursor.mysqlConnection();
    }
}
