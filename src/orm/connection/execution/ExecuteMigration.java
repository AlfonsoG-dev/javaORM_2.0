package orm.connection.execution;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import orm.builders.migration.MigrationBuilder;
import orm.utils.formats.ParamValue;
import orm.utils.formats.UsableMethods;

public class ExecuteMigration {
    private MigrationBuilder builder;
    private Connection cursor;
    public ExecuteMigration(Connection cursor, String tableName) {
        this.cursor = cursor;
        builder = new MigrationBuilder(tableName, cursor);
    }

    public Statement createDatabaseQuery(String database) throws SQLException {
        String sql = builder.getCreateDatabaseQuery(database);
        Statement stm = cursor.createStatement();
        stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        return stm;
    }

    public Statement createTableQuery(UsableMethods m, String type) throws SQLException {
        String sql = builder.getCreateTableQuery(m, type);
        Statement stm = cursor.createStatement();
        stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        return stm; 
    }

    public Statement createIndexQuery(boolean unique, String columns) throws SQLException {
        String sql = builder.getCreateIndexQuery(unique, columns);
        Statement stm = cursor.createStatement();
        stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        return stm;
    }

    public Statement removeIndexQuery(String column) throws SQLException {
        String sql = builder.getRemoveIndexQuery(column);
        Statement stm = cursor.createStatement();
        stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        return stm; 
    }
    public Statement renameColumnQuery(UsableMethods model) throws SQLException {
        String sql = builder.getRenameColumnQuery(model.initModel());
        Statement stm = cursor.createStatement();
        stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        return stm;
    }
    public Statement addColumnQuery(String pm, String[] fm, String[] ft, boolean ik) throws SQLException {
        String sql = builder.getAddColumnQuery(pm, fm, ft, ik);
        Statement stm = cursor.createStatement();
        stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        return stm; 
    }

    public Statement removeColumnQuery(UsableMethods model) throws SQLException {
        String sql = builder.getRemoveColumnQuery(model.initModel());
        Statement stm = cursor.createStatement();
        stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        return stm; 
    }
    public Statement modifyTypeQuery(UsableMethods model) throws SQLException {
        String sql = builder.getModifyTypeQuery(model.initModel());
        Statement stm = cursor.createStatement();
        stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        return stm;
    }
    public Statement addCheckConstraintQuery(ParamValue params) throws SQLException {
        String sql = builder.getAddChekConstraintQuery(params);
        Statement stm = cursor.createStatement();
        stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        return stm; 
    }
    public Statement addDefaultConstraintQuery(ParamValue params) throws SQLException {
        String sql = builder.getAddDefaultConstraintQuery(params);
        Statement stm = cursor.createStatement();
        stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        return stm;
    }
    public Statement removeCheckConstraintQuery(String name) throws SQLException {
        String sql =  builder.getDeleteCheckConstraintQuery(name);
        Statement stm = cursor.createStatement();
        stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        return stm; 
    }
    public Statement removeDefaultConstraintQuery(String name) throws SQLException {
        String sql = builder.getDeleteDefaultConstraintQuery(name);
        Statement stm = cursor.createStatement();
        stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        return stm; 
    }
}
