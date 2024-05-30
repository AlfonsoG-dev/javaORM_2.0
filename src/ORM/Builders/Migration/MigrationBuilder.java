package ORM.Builders.Migration;

import java.sql.Connection;

import java.util.List;

import ORM.Utils.Formats.ParamValue;
import ORM.Utils.Formats.UsableMethods;
import ORM.Utils.Model.ModelUtils;
import ORM.Utils.Query.MigrationUtils;
import ORM.Utils.Query.QueryUtils;

public class MigrationBuilder {
    private String tableName;
    private ModelUtils modelUtils;
    private MigrationUtils migrationUtils;
    private QueryUtils queryUtils;
    public MigrationBuilder(String tableName, Connection cursor) {
        this.tableName = tableName;
        modelUtils = new ModelUtils();
        migrationUtils = new MigrationUtils(cursor);
        queryUtils = new QueryUtils();
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
            b += queryUtils.clean(c, 2);
        } else {
            b+= columns;
        }
        return getAlterTableQuery(b);
    }
    public String getRenameColumnQuery(String model) {
        String
            toRename = migrationUtils.getCompareColumnNames(tableName, model).get("rename"),
            b = "";
        if(toRename != null) {
            String[] data = toRename.split(",");
            for(String d: data) {
                String[] value = d.split(":");
                b += "RENAME COLUMN " + value[1] + " TO " + value[0] + ", ";
            }
        }
        b = queryUtils.clean(b, 2);
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
                    addType = "";
                int index = modelUtils.getColumnIndex(primaryM, d, includeKeys);
                addType = index < modelTypes.length ? modelTypes[index].replace("'", "") : null;
                afterColumn = (index-1) < modelColumn.length ? modelColumn[index-1] : null;
                if(addType.contains(".")) {
                    addType = addType.split("\\.")[0];
                }
                b += d + " " + addType + " AFTER " + afterColumn + ", ";
            }
            if(includeKeys) {
                b += getAddKeyConstraintQuery(toAdd, foreignM, foreignT);
            }
        }
        b = queryUtils.clean(b, 2);
        return getAlterTableQuery(b);
    }
    public String getRemoveColumnQuery(String model) {
        String
            toRemove = migrationUtils.getCompareColumnNames(tableName, model).get("remove"),
            b = "DROP COLUMN ";
        if(toRemove != null) {
            String[] columns = toRemove.split(",");
            for(String c: columns) {
                if(c.contains("pk") || c.contains("fk")) {
                    b += getRemoveKeyConstraintQuery(c);
                } else {
                    b += c + ", ";
                }
            }
        }
        b = queryUtils.clean(b, 2);
        return getAlterTableQuery(b);
    }
    public String getModifyTypeQuery(String model) {
        String 
            toModify = migrationUtils.getCompareTypes(tableName, model).get("modify"),
            b = "MODIFY COLUMN ";
        if(toModify != null) {
            String[]
                modelColumns = modelUtils.getColumns(model, true).split(","),
                types = toModify.split(",");
            for(String t: types) {
                String type = t.split(":")[0];
                int index = Integer.parseInt(t.split(":")[1]);
                b += modelColumns[index]  + " " + type + ", ";
            }
        }
        b = queryUtils.clean(b, 2);
        return getAlterTableQuery(b);
    }
    private String getAddKeyConstraintQuery(String addColumns, String[] foreignM, String[] foreignT) {
        String b = "";
        List<String> pk = modelUtils.getKeys(addColumns).get("pk");
        if(pk != null) {
            b += "ADD CONSTRAINT " + pk.get(0) + " PRIMARY KEY(" + pk.get(0) + "), ";
        }
        List<String> fks = modelUtils.getKeys(addColumns).get("fk");
        for(int i=0; i<fks.size(); ++i) {
            if(fks != null && fks.size() == foreignM.length) {
                String
                    table = foreignT[i],
                    model = foreignM[i],
                    foreignPk = modelUtils.getKeys(model).get("pk").get(0);
                if(fks.get(i).contains(table)) {
                    String primaryFK = fks.get(i);
                    b += "ADD CONSTRAINT " + primaryFK + " FOREIGN KEY(" + primaryFK + ") REFERENCES " +
                        table + "(" + foreignPk + ") ON DELETE CASCADE ON UPDATE CASCADE, ";
                }
            }
        }
        return b;
    }
    /**
     * ALTER TABLE tableName ADD CONSTRAINT contraint_name value().
     * <br> pre: </br> i: indicates a numbre value, s: indicates a string value.
     * @param params: columns: check_nombre, values: nombre in (''), values: edad > 18
     */
    public String getAddChekConstraintQuery(ParamValue params) {
        String
            t = "",
            b = "ADD CONSTRAINT ";
        if(!params.getType().isEmpty()) {
            t = params.getType();
        }
        String[]
            c = params.getColumns(),
            v = params.getValues();
        for(int i=0; i<c.length; ++i) {
            b += "chk_" + c[i] + " CHECK (";
            String l = "";
            if(t.equals("s")) {
                b += c[i] + " IN ('" + v[i] + "', ";
            } else if(t.equals("i")) {
                l += c[i] + " " + v[i] + " AND ";
            }
            b += queryUtils.clean(l, 5);
        }
        b = queryUtils.clean(b, 2) + ")";
        return getAlterTableQuery(b);
    }
    public String getDeleteCheckConstraintQuery(String name) {
        String b = "";
        if(name.contains(",")) {
            String[] names = name.split(",");
            for(String n: names) {
                b += "DROP CHECK " + n + ", ";
            }
            b = queryUtils.clean(b, 2);
        } else {
            b += "DROP CHECK " + name;
        }
        return getAlterTableQuery(b);
    }
    public String getAddDefaultConstraintQuery(ParamValue params) {
        String b = "ALTER ";
        String[]
             c = params.getColumns(),
             v = params.getValues();
        for(int i=0; i<c.length; ++i) {
            b += c[i] + " SET DEFAULT '" + v[i] + "', ";
        }
        b = queryUtils.clean(b, 2);
        return getAlterTableQuery(b);
    }
    public String getDeleteDefaultConstraintQuery(String column) {
        String b = "ALTER ";
        if(column.contains(",")) {
            String[] columns = column.split(",");
            for(String c: columns) {
                b += c.trim() + " DROP DEFAULT, "; 
            }
            b = queryUtils.clean(b, 2);
        } else {
                b += column + " DROP DEFAULT"; 
        }
        return getAlterTableQuery(b);
    }
    private String getRemoveKeyConstraintQuery(String column) {
        String b = "DROP ";
        if(column.contains("pk")) {
            b += "PRIMARY KEY, ";
        } else if(column.contains("fk")) {
            b += "FOREIGN KEY " + column + ", ";
        }
        return b;
    }
}
