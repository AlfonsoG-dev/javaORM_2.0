package orm.builders.migration;

import java.sql.Connection;

import java.util.List;

import orm.utils.formats.ParamValue;
import orm.utils.formats.UsableMethods;
import orm.utils.model.ModelUtils;
import orm.utils.query.MigrationUtils;
import orm.utils.query.QueryUtils;

// TODO: verify the StringBuilder changes that are attach to the b variable in all the code.
public class MigrationBuilder {
    private static final String ADD_CONSTRAINT = "ADD CONSTRAINT ";

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
        String foreign = "";
        String init = m.initModel();
        String[] c = modelUtils.getColumns(init, true).split(",");
        String[] t = modelUtils.getTypes(init, true).split(",");
        for(String ts: t) {
            if(ts.contains(".")) {
                foreign = ts.replace(".", ",");
            }
        }
        StringBuilder value = new StringBuilder("(");
        for(int i=0; i<c.length; ++i) {
            t[i] = t[i].replace("'", "");
            if(t[i].contains(".")) {
                t[i] = foreign.replace("'", "");
            }
            value.append(c[i]);
            value.append(" ");
            value.append(t[i].replace("'", ""));
            value.append(", ");
        }
        String v = value.toString().substring(0, value.length()-2);
        String sql = "";
        if (type.equals("n")) {
            sql = "CREATE TABLE IF NOT EXISTS " + tableName + v + ")";
        } else if(type.equals("t")) {
            sql = "CREATE TEMPORARY TABLE t_" + tableName + v + ")";
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
            StringBuilder c = new StringBuilder();
            for(String s: spaces) {
                c.append(s);
                c.append(", ");
            }
            b += queryUtils.clean(c.toString(), 2);
        } else {
            b+= columns;
        }
        return getAlterTableQuery(b);
    }
    public String getRenameColumnQuery(String model) {
        String toRename = migrationUtils.getCompareColumnNames(tableName, model).get("rename");
        StringBuilder b = new StringBuilder();
        if(toRename != null) {
            String[] data = toRename.split(",");
            for(String d: data) {
                String[] value = d.split(":");
                b.append("RENAME COLUMN ");
                b.append(value[1]);
                b.append(" TO ");
                b.append(value[0]);
                b.append(", ");
            }
        }
        b = new StringBuilder(queryUtils.clean(b.toString(), 2));
        return getAlterTableQuery(b.toString());
    }
    public String getAddColumnQuery(String primaryM, String[] foreignM, String[] foreignT, boolean includeKeys) {
        String toAdd = migrationUtils.getCompareColumnNames(tableName, primaryM).get("add");
        StringBuilder b = new StringBuilder("ADD COLUMN ");
        if(toAdd != null) {
            String[] data = toAdd.split(",");
            String[] modelTypes = modelUtils.getTypes(primaryM, includeKeys).split(",");
            String[] modelColumn = modelUtils.getColumns(primaryM, includeKeys).split(",");
            for(String d: data) {
                String afterColumn = "";
                String addType = "";
                int index = modelUtils.getColumnIndex(primaryM, d, includeKeys);
                addType = index < modelTypes.length ? modelTypes[index].replace("'", "") : null;
                afterColumn = (index-1) < modelColumn.length ? modelColumn[index-1] : null;
                if(addType != null && addType.contains(".")) {
                    addType = addType.split("\\.")[0];
                }
                b.append(d);
                b.append(" ");
                b.append(addType);
                b.append(" AFTER ");
                b.append(afterColumn);
                b.append(", ");
            }
            if(includeKeys) {
                b.append(getAddKeyConstraintQuery(toAdd, foreignM, foreignT));
            }
        }
        b = new StringBuilder(queryUtils.clean(b.toString(), 2));
        return getAlterTableQuery(b.toString());
    }
    public String getRemoveColumnQuery(String model) {
        String toRemove = migrationUtils.getCompareColumnNames(tableName, model).get("remove");
        StringBuilder b = new StringBuilder("DROP COLUMN ");
        if(toRemove != null) {
            String[] columns = toRemove.split(",");
            for(String c: columns) {
                if(c.contains("pk") || c.contains("fk")) {
                    b.append(getRemoveKeyConstraintQuery(c));
                } else {
                    b.append(c);
                    b.append(", ");
                }
            }
        }
        b = new StringBuilder(queryUtils.clean(b.toString(), 2));
        return getAlterTableQuery(b.toString());
    }
    public String getModifyTypeQuery(String model) {
        String toModify = migrationUtils.getCompareTypes(tableName, model).get("modify");
        StringBuilder b = new StringBuilder("MODIFY COLUMN ");
        if(toModify != null) {
            String[] modelColumns = modelUtils.getColumns(model, true).split(",");
            String[] types = toModify.split(",");
            for(String t: types) {
                String type = t.split(":")[0];
                int index = Integer.parseInt(t.split(":")[1]);
                b.append(modelColumns[index]);
                b.append(" ");
                b.append(type);
                b.append(", ");
            }
        }
        b = new StringBuilder(queryUtils.clean(b.toString(), 2));
        return getAlterTableQuery(b.toString());
    }
    private String getAddKeyConstraintQuery(String addColumns, String[] foreignM, String[] foreignT) {
        StringBuilder b = new StringBuilder();
        List<String> pk = modelUtils.getKeys(addColumns).get("pk");
        if(pk != null) {
            b.append(ADD_CONSTRAINT);
            b.append(pk.get(0));
            b.append(" PRIMARY KEY(");
            b.append(pk.get(0));
            b.append("), ");
        }
        List<String> fks = modelUtils.getKeys(addColumns).get("fk");
        if(foreignM != null && foreignT != null) {
            for(int i=0; i<fks.size(); ++i) {
                if(fks.size() == foreignM.length) {
                    String table = foreignT[i];
                    String model = foreignM[i];
                    String foreignPk = modelUtils.getKeys(model).get("pk").get(0);
                    if(fks.get(i).contains(table)) {
                        String primaryFK = fks.get(i);
                        b.append(ADD_CONSTRAINT);
                        b.append(primaryFK);
                        b.append(" FOREIGN KEY(");
                        b.append(primaryFK);
                        b.append(") REFERENCES ");
                        b.append(table);
                        b.append("(");
                        b.append(foreignPk);
                        b.append(") ON DELETE CASCADE ON UPDATE CASCADE, ");
                    }
                }
            }
        }
        return b.toString();
    }
    /**
     * ALTER TABLE tableName ADD CONSTRAINT contraint_name value().
     * <br> pre: </br> i: indicates a numbre value, s: indicates a string value.
     * @param params: columns: check_nombre, values: nombre in (''), values: edad > 18
     */
    public String getAddChekConstraintQuery(ParamValue params) {
        String t = "";
        StringBuilder b = new StringBuilder(ADD_CONSTRAINT);
        if(!params.getType().isEmpty()) {
            t = params.getType();
        }
        String[] c = params.getColumns();
        String[] v = params.getValues();
        for(int i=0; i<c.length; ++i) {
            b.append("chk_");
            b.append(c[i]);
            b.append(" CHECK (");
            String l = "";
            if(t.equals("s")) {
                b.append(c[i]);
                b.append(" IN ('");
                b.append(v[i]);
                b.append("', ");
            } else if(t.equals("i")) {
                l += c[i] + " " + v[i] + " AND ";
            }
            b.append(queryUtils.clean(l, 5));
        }
        b = new StringBuilder(queryUtils.clean(b.toString(), 2));
        b.append(")");
        return getAlterTableQuery(b.toString());
    }
    public String getDeleteCheckConstraintQuery(String name) {
        StringBuilder b = new StringBuilder();
        if(name.contains(",")) {
            String[] names = name.split(",");
            for(String n: names) {
                b.append("DROP CHECK ");
                b.append(n);
                b.append(", ");
            }
            b = new StringBuilder(queryUtils.clean(b.toString(), 2));
        } else {
            b.append("DROP CHECK ");
            b.append(name);
        }
        return getAlterTableQuery(b.toString());
    }
    public String getAddDefaultConstraintQuery(ParamValue params) {
        StringBuilder b = new StringBuilder( "ALTER ");
        String[] c = params.getColumns();
        String[] v = params.getValues();
        for(int i=0; i<c.length; ++i) {
            b.append(c[i]);
            b.append(" SET DEFAULT '");
            b.append(v[i]);
            b.append("', ");
        }
        b = new StringBuilder(queryUtils.clean(b.toString(), 2));
        return getAlterTableQuery(b.toString());
    }
    public String getDeleteDefaultConstraintQuery(String column) {
        StringBuilder b = new StringBuilder("ALTER ");
        if(column.contains(",")) {
            String[] columns = column.split(",");
            for(String c: columns) {
                b.append(c.trim());
                b.append(" DROP DEFAULT, "); 
            }
            b = new StringBuilder(queryUtils.clean(b.toString(), 2));
        } else {
                b.append(column);
                b.append(" DROP DEFAULT"); 
        }
        return getAlterTableQuery(b.toString());
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
