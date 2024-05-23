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
    protected String getAlterTableQuery(String operations) {
        return "ALTER TABLE " + tableName + " " + operations;
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
    public String getCreateIndexQuery(boolean unique, String columns) {
        String b = "CREATE ";
        if(unique) {
            b += "UNIQUE ";
        }
        if(!columns.isEmpty()) {
            b += "INDEX(" + columns + ")";
        }
        return b;
    }
    public String getRemoveIndexQuery(String columns) {
        String b = "DROP INDEX ";
        if(columns.contains(",")) {
            String[] spaces = columns.trim().split(",");
            String c = "";
            for(String s: spaces) {
                c += s + ", ";
            }
            b += c.substring(0, c.length()-2);
        } else {
            b+= columns;
        }
        return getAlterTableQuery(b);
    }
    public String getAddColumnQuery(String primaryM, String[] foreignM, String[] foreignT, boolean includeKeys) {
        String
            toAdd = migrationUtils.getCompareColumnNames(tableName, primaryM).get("add"),
            b = "ADD COLUMN ";
        if(toAdd != null) {
            String[]
                data = toAdd.split(","),
                modelTypes = modelUtils.getTypes(primaryM, includeKeys).split(","),
                modelColumn = modelUtils.getColumns(primaryM, includeKeys).split(",");
            for(String d: data) {
                String
                    afterColumn = "",
                    addType = "",
                    addColumn = d.split(":")[0];
                int index = modelUtils.getColumnIndex(primaryM, addColumn, includeKeys);
                addType = index < modelTypes.length ? modelTypes[index].replace("'", "") : null;
                afterColumn = (index-1) < modelColumn.length ? modelColumn[index-1] : null;
                if(addType != null && afterColumn != null) {
                    if(addType.contains(".")) {
                        addType = addType.split("\\.")[0];
                    }
                    b+= addColumn + " " + addType + " AFTER " + afterColumn + ", ";
                }
                if(includeKeys) {
                    // TODO: create pk | fk constraint
                }
            }
        }
        String clean = "";
        if(!b.isEmpty()) {
            if(includeKeys) {
                clean = b.substring(0, b.length()-2);
            } else {
                clean = b;
            }
        }
        return getAlterTableQuery(clean);
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
