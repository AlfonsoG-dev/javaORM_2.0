package ORM.DbConnection.Execution;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import ORM.Builders.Migration.MigrationBuilder;
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
    public int renameColumnQuery(String model, Statement stm) throws SQLException {
        String sql = builder.getRenameColumnQuery(model);
        stm = cursor.createStatement();
        return stm.executeUpdate(sql);
    }
    public int addColumnQuery(String pm, String[] fm, String[] ft, boolean ik, Statement stm)
            throws SQLException {
        String sql = builder.getAddColumnQuery(pm, fm, ft, ik);
        stm = cursor.createStatement();
        return stm.executeUpdate(sql);
    }

    public int removeColumnQuery(String model, Statement stm) throws SQLException {
        String sql = builder.getRemoveColumnQuery(model);
        stm = cursor.createStatement();
        return stm.executeUpdate(sql);
    }
    public int modifyTypeQuery(String model, Statement stm) throws SQLException {
        String sql = builder.getModifyTypeQuery(model);
        stm = cursor.createStatement();
        return stm.executeUpdate(sql);
    }
}
