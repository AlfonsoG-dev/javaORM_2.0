package orm.connection.dao;

import java.io.Console;
import java.sql.Connection;
import java.sql.ResultSet;

import orm.connection.execution.ExecuteMigration;
import orm.utils.formats.ParamValue;
import orm.utils.formats.UsableMethods;

// TODO: verify the changes of the ResultSet being in a try-resource enclosure and the Statement being in the execute class.
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
        try(ResultSet rst = execute.createDatabaseQuery(database).getGeneratedKeys()) {
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
        try(ResultSet rst = execute.createTableQuery(m, type).getGeneratedKeys()) {
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
        try(ResultSet rst = execute.createIndexQuery(unique, columns).getGeneratedKeys()) {
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
        try(ResultSet rst = execute.removeIndexQuery(column).getGeneratedKeys()) {
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
        try(ResultSet rst = execute.renameColumnQuery(model).getGeneratedKeys()) {
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
        try(ResultSet rst = execute.addColumnQuery(primaryModel, foreigModels, foreignTables, includeKeys).getGeneratedKeys()) {
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
        try(ResultSet rst = execute.removeColumnQuery(model).getGeneratedKeys()) {
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
        try(ResultSet rst = execute.modifyTypeQuery(model).getGeneratedKeys()) {
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
        try(ResultSet rst = execute.addCheckConstraintQuery(params).getGeneratedKeys()) {
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
        try(ResultSet rst = execute.addDefaultConstraintQuery(params).getGeneratedKeys()) {
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
        try(ResultSet rst = execute.removeCheckConstraintQuery(name).getGeneratedKeys()) {
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
        try(ResultSet rst = execute.removeDefaultConstraintQuery(name).getGeneratedKeys()) {
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
