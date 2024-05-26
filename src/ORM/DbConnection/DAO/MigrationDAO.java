package ORM.DbConnection.DAO;

import java.sql.Connection;
import java.sql.Statement;

import ORM.DbConnection.Execution.ExecuteMigration;
import ORM.Utils.Formats.UsableMethods;

public class MigrationDAO {
    private ExecuteMigration execute;
    public MigrationDAO(Connection cursor, String tableName) {
        execute = new ExecuteMigration(cursor, tableName);
    }
    public boolean createDatabase(String database) {
        boolean isCreated = false;
        Statement stm = null;
        int rst = 0;
        try {
            rst = execute.createDatabaseQuery(database, stm);
            if(rst > 0) {
                isCreated = true;
            } else {
                System.err.println(
                        "[ INFO ]: something happen while trying to execute create database query"
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(stm != null) {
                try {
                    stm.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                stm = null;
            }
        }
        return isCreated;
    }
    public boolean createTable(UsableMethods m, String type) {
        boolean isCreated = false;
        Statement stm = null;
        int rst = 0;
        try {
            rst = execute.createTableQuery(m, type, stm);
            if(rst > 0) {
                isCreated = true;
            } else {
                System.err.println(
                        "[ INFO ]: something happen while trying to execute create table query"
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(stm != null) {
                try {
                    stm.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                stm = null;
            }
        }
        return isCreated;
    }
    public boolean createIndex(boolean unique, String columns) {
        boolean isCreated = false;
        Statement stm = null;
        int rst = 0;
        try {
            rst = execute.createIndexQuery(unique, columns, stm);
            if(rst > 0) {
                isCreated = true;
            } else {
                System.err.println(
                        "[ INFO ]: something happen while trying to execute create index query"
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(stm != null) {
                try {
                    stm.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                stm = null;
            }
        }
        return isCreated;
    }
    public boolean removeIndex(String column) {
        boolean isRemoved = false;
        Statement stm = null;
        int rst = 0;
        try {
            rst = execute.removeIndexQuery(column, stm);
            if(rst > 0) {
                isRemoved = true;
            } else {
                System.err.println(
                        "[ INFO ]: something happen while trying to execute remove index query"
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(stm != null) {
                try {
                    stm.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                stm = null;
            }
        }
        return isRemoved;
    }
    public boolean renameColumn(String model) {
        boolean isRenamed = false;
        Statement stm = null;
        int rst = 0;
        try {
            rst = execute.renameColumnQuery(model, stm);
            if(rst > 0) {
                isRenamed = true;
            } else {
                System.err.println(
                        "[ INFO ]: something happen while trying to execute rename column query"
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(stm != null) {
                try {
                    stm.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                stm = null;
            }
        }
        return isRenamed;
    }
    public boolean addColumn(String primaryModel, String[] foreigModels, String[] foreignTables, 
            boolean includeKeys) {
        boolean isAdded = false;
        Statement stm = null;
        int rst = 0;
        try {
            rst = execute.addColumnQuery(primaryModel, foreigModels, foreignTables, includeKeys, stm);
            if(rst > 0) {
                isAdded = true;
            } else {
                System.err.println(
                        "[ INFO ]: something happen while trying to execute add column query"
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(stm != null) {
                try {
                    stm.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                stm = null;
            }
        }
        return isAdded;
    }
    public boolean removeColumn(String model) {
        boolean isRemoved = false;
        Statement stm = null;
        int rst = 0;
        try {
            rst = execute.removeColumnQuery(model, stm);
            if(rst > 0) {
                isRemoved = true;
            } else {
                System.err.println(
                        "[ INFO ]: something happen while trying to execute remove column query"
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(stm != null) {
                try {
                    stm.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                stm = null;
            }
        }
        return isRemoved;
    }

    public boolean modifyType(String model) {
        boolean isRemoved = false;
        Statement stm = null;
        int rst = 0;
        try {
            rst = execute.modifyTypeQuery(model, stm);
            if(rst > 0) {
                isRemoved = true;
            } else {
                System.err.println(
                        "[ INFO ]: something happen while trying to execute modify type query"
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(stm != null) {
                try {
                    stm.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                stm = null;
            }
        }
        return isRemoved;
    }
}