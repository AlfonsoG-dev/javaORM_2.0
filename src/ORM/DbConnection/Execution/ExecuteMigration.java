package ORM.DbConnection.Execution;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import ORM.Builders.Migration.MigrationBuilder;
import ORM.Utils.Formats.ParamValue;
import ORM.Utils.Formats.UsableMethods;

public class ExecuteMigration {
    private MigrationBuilder builder;
    private Connection cursor;
    public ExecuteMigration(Connection cursor, String tableName) {
        this.cursor = cursor;
        builder = new MigrationBuilder(tableName, cursor);
    }

    public int createDatabaseQuery(String database, Statement stm) throws SQLException {
        String sql = builder.getCreateDatabaseQuery(database);
        stm = cursor.createStatement();
        return stm.executeUpdate(sql);
    }

    public int createTableQuery(UsableMethods m, String type, Statement stm) throws SQLException {
        String sql = builder.getCreateTableQuery(m, type);
        stm = cursor.createStatement();
        return stm.executeUpdate(sql);
    }

    public int createIndexQuery(boolean unique, String columns, Statement stm) throws SQLException {
        String sql = builder.getCreateIndexQuery(unique, columns);
        stm = cursor.createStatement();
        return stm.executeUpdate(sql);
    }

    public int removeIndexQuery(String column, Statement stm) throws SQLException {
        String sql = builder.getRemoveIndexQuery(column);
        stm = cursor.createStatement();
        return stm.executeUpdate(sql);
    }
    public int renameColumnQuery(UsableMethods model, Statement stm) throws SQLException {
        String sql = builder.getRenameColumnQuery(model.initModel());
        stm = cursor.createStatement();
        return stm.executeUpdate(sql);
    }
    public int addColumnQuery(String pm, String[] fm, String[] ft, boolean ik, Statement stm)
            throws SQLException {
        String sql = builder.getAddColumnQuery(pm, fm, ft, ik);
        stm = cursor.createStatement();
        return stm.executeUpdate(sql);
    }

    public int removeColumnQuery(UsableMethods model, Statement stm) throws SQLException {
        String sql = builder.getRemoveColumnQuery(model.initModel());
        stm = cursor.createStatement();
        return stm.executeUpdate(sql);
    }
    public int modifyTypeQuery(UsableMethods model, Statement stm) throws SQLException {
        String sql = builder.getModifyTypeQuery(model.initModel());
        stm = cursor.createStatement();
        return stm.executeUpdate(sql);
    }
    public int addCheckConstraintQuery(ParamValue params, Statement stm) throws SQLException {
        String sql = builder.getAddChekConstraintQuery(params);
        stm = cursor.createStatement();
        return stm.executeUpdate(sql);
    }
    public int addDefaultConstraintQuery(ParamValue params, Statement stm) throws SQLException {
        String sql = builder.getAddDefaultConstraintQuery(params);
        stm = cursor.createStatement();
        return stm.executeUpdate(sql);
    }
    public int removeCheckConstraintQuery(String name, Statement stm) throws SQLException {
        String sql =  builder.getDeleteCheckConstraintQuery(name);
        stm = cursor.createStatement();
        return stm.executeUpdate(sql);
    }
    public int removeDefaultConstraintQuery(String name, Statement stm) throws SQLException {
        String sql = builder.getDeleteDefaultConstraintQuery(name);
        stm = cursor.createStatement();
        return stm.executeUpdate(sql);
    }
}
