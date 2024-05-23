package ORM.Builders.Migration;

import java.sql.Connection;

import ORM.Utils.Formats.UsableMethods;
import ORM.Utils.Model.ModelUtils;
import ORM.Utils.Query.MigrationUtils;

public class MigrationBuilder {
    private String tableName;
    private ModelUtils modelUtils;
    private MigrationUtils migrationUtils;
    public MigrationBuilder(String tableName, Connection cursor) {
        this.tableName = tableName;
        modelUtils = new ModelUtils();
        migrationUtils = new MigrationUtils(cursor);
    }
    public String getCreateDatabaseQuery(String database) {
        return "CREATE DATABASE IF NOT EXISTS " + database;
    }
    public String getCreateTableQuery(UsableMethods m, String type) {
        String 
            foreign = "",
            init = m.initModel();
        String[]
            c = modelUtils.getColumns(init, true).split(","),
            t = modelUtils.getTypes(init, true).split(",");
        for(String ts: t) {
            if(ts.contains(".")) {
                foreign = ts.replace(".", ",");
            }
        }
        String value = "(";
        for(int i=0; i<c.length; ++i) {
            t[i] = t[i].replace("'", "");
            if(t[i].contains(".")) {
                t[i] = foreign;
            }
            value += c[i] + " " + t[i] + ", ";
        }
        String
            v = value.substring(0, value.length()-2),
            sql = "";
        switch(type) {
            case "n":
                sql = "CREATE TABLE IF NOT EXISTS " + tableName + v + ")";
                break;
            case "t":
                sql = "CREATE TEMPORARY TABLE t_" + tableName + v + ")";
                break;
        }
        return sql;
    }
    public String getCreateIndexQuery() {
        String b = "";
        return b;
    }
    public String getRemoveIndexQuery() {
        String b = "";
        return b;
    }
    public String getAddColumnQuery() {
        String b = "";
        return b;
    }
    public String getRemoveColumnQuery() {
        String b = "";
        return b;
    }
    public String getModifyTypeQuery() {
        String b = "";
        return b;
    }
    public String getAddConstraintQuery() {
        String b = "";
        return b;
    }
    public String getRemoveConstraintQuery() {
        String b = "";
        return b;
    }
}
