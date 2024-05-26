package Samples.Query;

import java.sql.Connection;
import java.util.List;

import ORM.DbConnection.DAO.QueryDAO;
import ORM.Utils.Formats.ParamValue;
import ORM.Utils.Formats.UsableMethods;
import Samples.Models.Foreign.UsersODM;
import Samples.Models.Primary.TestODM;

public class QuerySample {
    private Connection cursor;
    public QuerySample(Connection cursor) {
        this.cursor = cursor;
    }
    public void prepareCountSample() {
        UsersODM m = new UsersODM();
        QueryDAO<UsersODM> dao = new QueryDAO<>(cursor, "users", m);
        String[]
            c = {"rol"},
            v = {"admin"};
        ParamValue condition = new ParamValue(c, v, "or");
        List<String> data = dao.prepareCount(condition, "description, rol");
        for(String d: data) {
            System.out.println(d);
        }
    }
    public void selectInSample() {
        UsersODM m = new UsersODM();
        QueryDAO<UsersODM> dao = new QueryDAO<>(cursor, "users", m);
        String[]
            c = {"rol"},
            v = {"test", "user", "admin"};
        ParamValue condition = new ParamValue(c, v, "or");
        List<String> data = dao.selectIn(condition, "description, rol");
        for(String d: data) {
            System.out.println(d);
        }
    }
    public void selectPatternSample() {
        UsersODM m = new UsersODM();
        QueryDAO<UsersODM> dao = new QueryDAO<>(cursor, "users", m);
        String[]
            c = {"rol"},
            v = {"test", "user", "admin"};
        ParamValue condition = new ParamValue(c, v, "or");
        List<String> data = dao.selectPattern(condition, "description, rol");
        for(String d: data) {
            System.out.println(d);
        }
    }
    public void preparedSelectMinMaxSample() {
        UsersODM m = new UsersODM();
        QueryDAO<UsersODM> dao = new QueryDAO<>(cursor, "test", m);
        String[]
            c = {"min", "max"},
            v = {"rol", "description"};
        ParamValue params = new ParamValue(c, v, "");
        String[]
            c1 = {"rol"},
            v1 = {"admin"};
        ParamValue condition = new ParamValue(c1, v1, "and");
        List<String> data = dao.preparedSelectMinMax(params, condition);
        for(String d: data) {
            System.out.println(d);
        }
    }
    public void preparedSelectSample() {
        UsersODM m = new UsersODM();
        QueryDAO<UsersODM> dao = new QueryDAO<>(cursor, "users", m);
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
    public void preparedFindSample() {
        TestODM m = new TestODM();
        QueryDAO<TestODM> dao = new QueryDAO<>(cursor, "test", m);
        String columns = "";
        ParamValue condition = null;
        List<String> data = dao.preparedFind(condition, columns);
        if(data.size() > 0) {
            for(String d: data) {
                System.out.println(d);
            }
        }
    }
    public void preparedInsertSample() {
        UsersODM m = new UsersODM("my description of this user", "testing");
        QueryDAO<UsersODM> dao = new QueryDAO<>(cursor, "users", m);
        boolean isAdded = dao.preparedInsert(m);
        if(isAdded) {
            System.out.println("[ INFO ]: data has been inserted");
        }
    }
    public void insertSample() {
        TestODM m = new TestODM("model name", "model email", 2, "model rol");
        QueryDAO<TestODM> dao = new QueryDAO<>(cursor, "test", m);
        boolean isAdded = dao.insert(m);
        if(isAdded) {
            System.out.println("[ INFO ]: data has been inserted");
        }
    }
    /**
     * remember that the foreign model declare the pk of the relation and the model that declares the fk is the
     * primary model.
     * {@link TestODM} declares the fk.
     * {@link UsersODM} declares the pk that is the fk of the previous class.
     */
    public void innerJoinSample() {
        // set model with the relation
        TestODM m = new TestODM("model name", "model email", 1, "model rol");
        // set foreign model
        UsersODM foreign = new UsersODM("foreign model description", "foreign model");
        UsableMethods[] foreignModels = new UsableMethods[1];
        foreignModels[0] = foreign;
        // set foreign tables
        String[] foreignTables = {"users"};
        // set conditions 
        ParamValue[] conditions = new ParamValue[1];
        String[]
            c = {"users_id_fk"},
            v = {"id_pk"};
        ParamValue condition = new ParamValue(c, v, "and");
        conditions[0] = condition;

        QueryDAO<TestODM> dao = new QueryDAO<>(cursor, "test", foreign);
        List<String> data = dao.innerJoin(m, foreignModels, foreignTables, conditions);
        if(data.size() > 0) {
            for(String d: data) {
                System.out.println(d);
            }
        }
    }
    public void preparedUpdateSample() {
        TestODM m = new TestODM("model name", "model email", 1, "model rol");
        String[] 
            c = {"name", "rol"},
            v = {"model name", "model rol"};
        ParamValue condition = new ParamValue(c, v, "and");
        QueryDAO<TestODM> dao = new QueryDAO<>(cursor, "test", m);
        boolean isUpdated = dao.preparedUpdate(m, condition);
        if(isUpdated) {
            System.out.println("[ INFO ]: record has been updated");
        }
    }
    public void preparedDeleteSample() {
        String[]
            c = {"name", "email"},
            v = {"model name", "model email"};
        ParamValue condition = new ParamValue(c, v, "and");
        /**
         * just for building
         */
        TestODM m = new TestODM();
        QueryDAO<TestODM> dao = new QueryDAO<>(cursor, "test", m);
        boolean isDeleted = dao.preparedDelete(condition);
        if(isDeleted) {
            System.out.println("[ INFO ]: record has been deleted");
        }
    }
}
