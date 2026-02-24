package orm.connection.dao;

import java.io.Console;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import orm.connection.execution.ExecuteMigration;
import orm.utils.formats.ParamValue;
import orm.utils.formats.UsableMethods;

public class MigrationDAO {

    private static final Console console = System.console();
    private static final String CONSOLE_FORMAT = "[%s] %s%n";
    private static final String[] LOG_LEVEL = {
        "Info",
        "Error",
        "Warning"
    };

    private ExecuteMigration execute;

    public MigrationDAO(Connection cursor, String tableName) {
        execute = new ExecuteMigration(cursor, tableName);
    }
    public boolean createDatabase(String database) {
        boolean isCreated = false;
        try(Statement stm = execute.createDatabaseQuery(database); ResultSet rst = stm.getGeneratedKeys()) {
            if(rst.getMetaData().getColumnCount() > 0) {
                isCreated = true;
            } else {
                console.printf(CONSOLE_FORMAT,
                        LOG_LEVEL[1], "something happen while trying to execute create database query");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return isCreated;
    }
    public boolean createTable(UsableMethods m, String type) {
        boolean isCreated = false;
        try(Statement stm = execute.createTableQuery(m, type); ResultSet rst = stm.getGeneratedKeys()) {
            if(rst.getMetaData().getColumnCount() > 0) {
                isCreated = true;
            } else {
                console.printf(CONSOLE_FORMAT,
                        LOG_LEVEL[1], "something happen while trying to execute create table query");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return isCreated;
    }
    public boolean createIndex(boolean unique, String columns) {
        boolean isCreated = false;
        try(Statement stm = execute.createIndexQuery(unique, columns); ResultSet rst = stm.getGeneratedKeys()) {
            if(rst.getMetaData().getColumnCount() > 0) {
                isCreated = true;
            } else {
                console.printf(CONSOLE_FORMAT,
                        LOG_LEVEL[1], "something happen while trying to execute create index query");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return isCreated;
    }
    public boolean removeIndex(String column) {
        boolean isRemoved = false;
        try(Statement stm = execute.removeIndexQuery(column); ResultSet rst = stm.getGeneratedKeys()) {
            if(rst.getMetaData().getColumnCount() > 0) {
                isRemoved = true;
            } else {
                console.printf(CONSOLE_FORMAT, 
                        LOG_LEVEL[1], "something happen while trying to execute remove index query");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return isRemoved;
    }
    public boolean renameColumn(UsableMethods model) {
        boolean isRenamed = false;
        try(Statement stm = execute.renameColumnQuery(model); ResultSet rst = stm.getGeneratedKeys()) {
            if(rst.getMetaData().getColumnCount() > 0) {
                isRenamed = true;
            } else {
                console.printf(CONSOLE_FORMAT,
                        LOG_LEVEL[1], "something happen while trying to execute rename column query");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return isRenamed;
    }
    public boolean addColumn(String primaryModel, String[] foreigModels, String[] foreignTables, boolean includeKeys) {
        boolean isAdded = false;
        try(Statement stm = execute.addColumnQuery(primaryModel, foreigModels, foreignTables, includeKeys);
                ResultSet rst = stm.getGeneratedKeys()) {
            if(rst.getMetaData().getColumnCount() > 0) {
                isAdded = true;
            } else {
                console.printf(CONSOLE_FORMAT,
                        LOG_LEVEL[1], "something happen while trying to execute add column query");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return isAdded;
    }
    public boolean removeColumn(UsableMethods model) {
        boolean isRemoved = false;
        try(Statement stm = execute.removeColumnQuery(model); ResultSet rst = stm.getGeneratedKeys()) {
            if(rst.getMetaData().getColumnCount() > 0) {
                isRemoved = true;
            } else {
                console.printf(CONSOLE_FORMAT,
                        LOG_LEVEL[1], "something happen while trying to execute remove column query");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return isRemoved;
    }

    public boolean modifyType(UsableMethods model) {
        boolean isRemoved = false;
        try(Statement stm = execute.modifyTypeQuery(model); ResultSet rst = stm.getGeneratedKeys()) {
            if(rst.getMetaData().getColumnCount() > 0) {
                isRemoved = true;
            } else {
                console.printf(CONSOLE_FORMAT,
                        LOG_LEVEL[1], "something happen while trying to execute modify type query");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return isRemoved;
    }
    public boolean addCheckConstraint(ParamValue params) {
        boolean isRemoved = false;
        try(Statement stm = execute.addCheckConstraintQuery(params); ResultSet rst = stm.getGeneratedKeys()) {
            if(rst.getMetaData().getColumnCount() > 0) {
                isRemoved = true;
            } else {
                console.printf(CONSOLE_FORMAT,
                        LOG_LEVEL[1], "something happen while trying to execute add check constraint query");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return isRemoved;
    }
    public boolean addDefaultConstraint(ParamValue params) {
        boolean isRemoved = false;
        try(Statement stm = execute.addDefaultConstraintQuery(params); ResultSet rst = stm.getGeneratedKeys()) {
            if(rst.getMetaData().getColumnCount() > 0) {
                isRemoved = true;
            } else {
                console.printf(CONSOLE_FORMAT,
                        LOG_LEVEL[1], "something happen while trying to execute add default constraint query");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return isRemoved;
    }
    public boolean removeCheckConstraint(String name) {
        boolean isRemoved = false;
        try(Statement stm = execute.removeCheckConstraintQuery(name); ResultSet rst = stm.getGeneratedKeys()) {
            if(rst.getMetaData().getColumnCount() > 0) {
                isRemoved = true;
            } else {
                console.printf(CONSOLE_FORMAT,
                        LOG_LEVEL[1], "something happen while trying to execute remove check constraint query");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return isRemoved;
    }
    public boolean removeDefaultConstraint(String name) {
        boolean isRemoved = false;
        try(Statement stm = execute.removeDefaultConstraintQuery(name); ResultSet rst = stm.getGeneratedKeys()) {
            if(rst.getMetaData().getColumnCount() > 0) {
                isRemoved = true;
            } else {
                console.printf(CONSOLE_FORMAT,
                        LOG_LEVEL[1], "something happen while trying to execute remove default constraint query");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return isRemoved;
    }
}
