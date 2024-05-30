package ORM.DbConnection.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import ORM.DbConnection.Execution.ExecuteMigration;
import ORM.Utils.Formats.ParamValue;
import ORM.Utils.Formats.UsableMethods;

public class MigrationDAO {
    private ExecuteMigration execute;
    public MigrationDAO(Connection cursor, String tableName) {
        execute = new ExecuteMigration(cursor, tableName);
    }
    public boolean createDatabase(String database) {
        boolean isCreated = false;
        Statement stm = null;
        ResultSet rst = null;
        try {
            rst = execute.createDatabaseQuery(database, stm).getGeneratedKeys();
            if(rst.getMetaData().getColumnCount() > 0) {
                isCreated = true;
            } else {
                System.err.println(
                        "[ INFO ]: something happen while trying to execute create database query"
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(rst != null) {
                try {
                    rst.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                rst = null;
            }
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
        ResultSet rst = null;
        try {
            rst = execute.createTableQuery(m, type, stm).getGeneratedKeys();
            if(rst.getMetaData().getColumnCount() > 0) {
                isCreated = true;
            } else {
                System.err.println(
                        "[ INFO ]: something happen while trying to execute create table query"
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(rst != null) {
                try {
                    rst.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                rst = null;
            }
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
        ResultSet rst = null;
        try {
            rst = execute.createIndexQuery(unique, columns, stm).getGeneratedKeys();
            if(rst.getMetaData().getColumnCount() > 0) {
                isCreated = true;
            } else {
                System.err.println(
                        "[ INFO ]: something happen while trying to execute create index query"
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(rst != null) {
                try {
                    rst.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                rst = null;
            }
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
        ResultSet rst = null;
        try {
            rst = execute.removeIndexQuery(column, stm).getGeneratedKeys();
            if(rst.getMetaData().getColumnCount() > 0) {
                isRemoved = true;
            } else {
                System.err.println(
                        "[ INFO ]: something happen while trying to execute remove index query"
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(rst != null) {
                try {
                    rst.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                rst = null;
            }
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
    public boolean renameColumn(UsableMethods model) {
        boolean isRenamed = false;
        Statement stm = null;
        ResultSet rst = null;
        try {
            rst = execute.renameColumnQuery(model, stm).getGeneratedKeys();
            if(rst.getMetaData().getColumnCount() > 0) {
                isRenamed = true;
            } else {
                System.err.println(
                        "[ INFO ]: something happen while trying to execute rename column query"
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(rst != null) {
                try {
                    rst.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                rst = null;
            }
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
        ResultSet rst = null;
        try {
            rst = execute.addColumnQuery(
                    primaryModel, foreigModels, foreignTables, includeKeys, stm
            ).getGeneratedKeys();
            if(rst.getMetaData().getColumnCount() > 0) {
                isAdded = true;
            } else {
                System.err.println(
                        "[ INFO ]: something happen while trying to execute add column query"
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(rst != null) {
                try {
                    rst.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                rst = null;
            }
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
    public boolean removeColumn(UsableMethods model) {
        boolean isRemoved = false;
        Statement stm = null;
        ResultSet rst = null;
        try {
            rst = execute.removeColumnQuery(model, stm).getGeneratedKeys();
            if(rst.getMetaData().getColumnCount() > 0) {
                isRemoved = true;
            } else {
                System.err.println(
                        "[ INFO ]: something happen while trying to execute remove column query"
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(rst != null) {
                try {
                    rst.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                rst = null;
            }
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

    public boolean modifyType(UsableMethods model) {
        boolean isRemoved = false;
        Statement stm = null;
        ResultSet rst = null;
        try {
            rst = execute.modifyTypeQuery(model, stm).getGeneratedKeys();
            if(rst.getMetaData().getColumnCount() > 0) {
                isRemoved = true;
            } else {
                System.err.println(
                        "[ INFO ]: something happen while trying to execute modify type query"
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(rst != null) {
                try {
                    rst.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                rst = null;
            }
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
    public boolean addCheckConstraint(ParamValue params) {
        boolean isRemoved = false;
        Statement stm = null;
        ResultSet rst = null;
        try {
            rst = execute.addCheckConstraintQuery(params, stm).getGeneratedKeys();
            if(rst.getMetaData().getColumnCount() > 0) {
                isRemoved = true;
            } else {
                System.err.println(
                        "[ INFO ]: something happen while trying to execute add check constraint query"
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(rst != null) {
                try {
                    rst.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                rst = null;
            }
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
    public boolean addDefaultConstraint(ParamValue params) {
        boolean isRemoved = false;
        Statement stm = null;
        ResultSet rst = null;
        try {
            rst = execute.addDefaultConstraintQuery(params, stm).getGeneratedKeys();
            if(rst.getMetaData().getColumnCount() > 0) {
                isRemoved = true;
            } else {
                System.err.println(
                        "[ INFO ]: something happen while trying to execute add default constraint query"
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(rst != null) {
                try {
                    rst.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                rst = null;
            }
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
    public boolean removeCheckConstraint(String name) {
        boolean isRemoved = false;
        Statement stm = null;
        ResultSet rst = null;
        try {
            rst = execute.removeCheckConstraintQuery(name, stm).getGeneratedKeys();
            if(rst.getMetaData().getColumnCount() > 0) {
                isRemoved = true;
            } else {
                System.err.println(
                        "[ INFO ]: something happen while trying to execute remove check constraint query"
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(rst != null) {
                try {
                    rst.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                rst = null;
            }
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
    
    public boolean removeDefaultConstraint(String name) {
        boolean isRemoved = false;
        Statement stm = null;
        ResultSet rst = null;
        try {
            rst = execute.removeDefaultConstraintQuery(name, stm).getGeneratedKeys();
            if(rst.getMetaData().getColumnCount() > 0) {
                isRemoved = true;
            } else {
                System.err.println(
                        "[ INFO ]: something happen while trying to execute remove default constraint query"
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(rst != null) {
                try {
                    rst.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                rst = null;
            }
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
