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

    public Statement createDatabaseQuery(String database) {
        String sql = builder.getCreateDatabaseQuery(database);
        try (Statement stm = cursor.createStatement()) {
            stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            return stm;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Statement createTableQuery(UsableMethods m, String type) {
        String sql = builder.getCreateTableQuery(m, type);
        try (Statement stm = cursor.createStatement()) {
            stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            return stm; 
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Statement createIndexQuery(boolean unique, String columns) {
        String sql = builder.getCreateIndexQuery(unique, columns);
        try (Statement stm = cursor.createStatement()) {
            stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            return stm;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Statement removeIndexQuery(String column) {
        String sql = builder.getRemoveIndexQuery(column);
        try (Statement stm = cursor.createStatement()) {
            stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            return stm; 
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Statement renameColumnQuery(UsableMethods model) {
        String sql = builder.getRenameColumnQuery(model.initModel());
        try (Statement stm = cursor.createStatement()) {
            stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            return stm;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Statement addColumnQuery(String pm, String[] fm, String[] ft, boolean ik) {
        String sql = builder.getAddColumnQuery(pm, fm, ft, ik);
        try (Statement stm = cursor.createStatement()) {
            stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            return stm; 
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Statement removeColumnQuery(UsableMethods model) {
        String sql = builder.getRemoveColumnQuery(model.initModel());
        try (Statement stm = cursor.createStatement()) {
            stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            return stm; 
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Statement modifyTypeQuery(UsableMethods model) {
        String sql = builder.getModifyTypeQuery(model.initModel());
        try (Statement stm = cursor.createStatement()) {
            stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            return stm;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Statement addCheckConstraintQuery(ParamValue params) {
        String sql = builder.getAddChekConstraintQuery(params);
        try (Statement stm = cursor.createStatement()) {
            stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            return stm; 
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Statement addDefaultConstraintQuery(ParamValue params){
        String sql = builder.getAddDefaultConstraintQuery(params);
        try (Statement stm = cursor.createStatement()) {
            stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            return stm;
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
    public Statement removeCheckConstraintQuery(String name){
        String sql =  builder.getDeleteCheckConstraintQuery(name);
        try (Statement stm = cursor.createStatement()) {
            stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            return stm; 
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Statement removeDefaultConstraintQuery(String name){
        String sql = builder.getDeleteDefaultConstraintQuery(name);
        try (Statement stm = cursor.createStatement()) {
            stm.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            return stm; 
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
