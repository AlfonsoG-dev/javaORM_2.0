package Samples.Query;

import java.sql.Connection;
import java.util.List;

import ORM.DbConnection.DAO.QueryDAO;
import ORM.Utils.Formats.ParamValue;
import Samples.Models.Foreign.UsersODM;

public class QuerySample {
    private Connection cursor;
    public QuerySample(Connection cursor) {
        this.cursor = cursor;
    }
    public void preparedSelectSample() {
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
}
