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

    public Statement createDatabaseQuery(String database, Statement stm) throws SQLException {
        String sql = builder.getCreateDatabaseQuery(database);
        stm = cursor.createStatement();
        stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        return stm;
    }

    public Statement createTableQuery(UsableMethods m, String type, Statement stm) throws SQLException {
        String sql = builder.getCreateTableQuery(m, type);
        stm = cursor.createStatement();
        stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        return stm; 
    }

    public Statement createIndexQuery(boolean unique, String columns, Statement stm) throws SQLException {
        String sql = builder.getCreateIndexQuery(unique, columns);
        stm = cursor.createStatement();
        stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        return stm;
    }

    public Statement removeIndexQuery(String column, Statement stm) throws SQLException {
        String sql = builder.getRemoveIndexQuery(column);
        stm = cursor.createStatement();
        stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        return stm; 
    }
    public Statement renameColumnQuery(UsableMethods model, Statement stm) throws SQLException {
        String sql = builder.getRenameColumnQuery(model.initModel());
        stm = cursor.createStatement();
        stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        return stm;
    }
    public Statement addColumnQuery(String pm, String[] fm, String[] ft, boolean ik, Statement stm)
            throws SQLException {
        String sql = builder.getAddColumnQuery(pm, fm, ft, ik);
        stm = cursor.createStatement();
        stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        return stm; 
    }

    public Statement removeColumnQuery(UsableMethods model, Statement stm) throws SQLException {
        String sql = builder.getRemoveColumnQuery(model.initModel());
        stm = cursor.createStatement();
        stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        return stm; 
    }
    public Statement modifyTypeQuery(UsableMethods model, Statement stm) throws SQLException {
        String sql = builder.getModifyTypeQuery(model.initModel());
        stm = cursor.createStatement();
        stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        return stm;
    }
    public Statement addCheckConstraintQuery(ParamValue params, Statement stm) throws SQLException {
        String sql = builder.getAddChekConstraintQuery(params);
        stm = cursor.createStatement();
        stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        return stm; 
    }
    public Statement addDefaultConstraintQuery(ParamValue params, Statement stm) throws SQLException {
        String sql = builder.getAddDefaultConstraintQuery(params);
        stm = cursor.createStatement();
        stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        return stm;
    }
    public Statement removeCheckConstraintQuery(String name, Statement stm) throws SQLException {
        String sql =  builder.getDeleteCheckConstraintQuery(name);
        stm = cursor.createStatement();
        stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        return stm; 
    }
    public Statement removeDefaultConstraintQuery(String name, Statement stm) throws SQLException {
        String sql = builder.getDeleteDefaultConstraintQuery(name);
        stm = cursor.createStatement();
        stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        return stm; 
    }
}
