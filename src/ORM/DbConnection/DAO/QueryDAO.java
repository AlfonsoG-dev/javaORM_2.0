package ORM.DbConnection.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ORM.DbConnection.Execution.ExecuteQuery;
import ORM.Utils.Formats.ParamValue;
import ORM.Utils.Formats.UsableMethods;

public class QueryDAO<T> {
    private Connection cursor;
    private String tableName;
    private ExecuteQuery execute;
    private UsableMethods buildObject;
    public QueryDAO(Connection cursor, String tableName, UsableMethods build) {
        this.cursor = cursor;
        this.tableName = tableName;
        execute = new ExecuteQuery(cursor, tableName);
        this.buildObject = build;
    }
    public Connection getConnection() {
        return cursor;
    }
    public List<String> prepareCount(ParamValue condition, String columns) {
        List<String> data = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        try {
            rst = execute.preparedSelectCountQuery(condition, columns, pstm);
            int length = rst.getMetaData().getColumnCount();
            while(rst.next()) {
                String b = "";
                for(int i=1; i<=length; ++i) {
                    b = rst.getMetaData().getColumnName(i) + ":" + rst.getString(i) + "\n";
                }
                if(b.length()-1 > 0) {
                    b = b.substring(0, b.length()-1);
                }
                data.add(b);
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
            if(pstm != null) {
                try {
                    pstm.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                pstm = null;
            }
        }
        return data;
    }
    public List<T> readAll() {
        List<T> data = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        try {
            String sql = "SELECT * FROM " + tableName;
            pstm = cursor.prepareStatement(sql);
            rst = pstm.executeQuery();
            data.addAll(buildObject.build(rst));
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
            if(pstm != null) {
                try {
                    pstm.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                pstm = null;
            }
        }
        return data;
    }
    public List<String> selectIn(ParamValue condition, String columns) {
        List<String> data = new ArrayList<>();
        Statement stm = null;
        ResultSet rst = null;
        try {
            rst = execute.selectInQuery(condition, columns, stm);
            int length = rst.getMetaData().getColumnCount();
            while(rst.next()) {
                String b = "";
                for(int i=1; i<=length; ++i) {
                    b = rst.getMetaData().getColumnName(i) + ":" + rst.getString(i) + "\n";
                }
                if(b.length()-1 > 0) {
                    b = b.substring(0, b.length()-1);
                }
                data.add(b);
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
        return data;
    }

    public List<String> selectPattern(ParamValue condition, String columns) {
        List<String> data = new ArrayList<>();
        Statement stm = null;
        ResultSet rst = null;
        try {
            rst = execute.selectPatternQuery(condition, columns, stm);
            int length = rst.getMetaData().getColumnCount();
            while(rst.next()) {
                String b = "";
                for(int i=1; i<=length; ++i) {
                    b = rst.getMetaData().getColumnName(i) + ":" + rst.getString(i) + "\n";
                }
                if(b.length()-1 > 0) {
                    b = b.substring(0, b.length()-1);
                }
                data.add(b);
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
        return data;
    }

    public List<String> preparedSelectMinMax(ParamValue params, ParamValue condition) {
        List<String> data = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        try {
            rst = execute.preparedSelectMinMaxQuery(params, condition, pstm);
            int length = rst.getMetaData().getColumnCount();
            while(rst.next()) {
                String b = "";
                for(int i=1; i<=length; ++i) {
                    b = rst.getMetaData().getColumnName(i) + ":" + rst.getString(i) + "\n";
                }
                if(b.length()-1 > 0) {
                    b = b.substring(0, b.length()-1);
                }
                data.add(b);
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
            if(pstm != null) {
                try {
                    pstm.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                pstm = null;
            }
        }
        return data;
    }

    public List<T> preparedSelect(ParamValue c) {
        List<T> data = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        try {
            rst = execute.preparedSelectQuery(c, pstm);
            data.addAll(buildObject.build(rst));
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
            if(pstm != null) {
                try {
                    pstm.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                pstm = null;
            }
        }
        return data;
    }
    public List<String> preparedFind(ParamValue condition, String columns) {
        PreparedStatement pstm = null;
        ResultSet rst = null;
        List<String> data = new ArrayList<>();
        try {
            rst = execute.preparedFindQuery(condition, columns, pstm);
            int length = rst.getMetaData().getColumnCount();
            while(rst.next()) {
                String b = "";
                for(int i=1; i<=length; ++i) {
                    b += rst.getMetaData().getColumnName(i) + ":" + rst.getString(i) + "\n";
                }
                if(b.length()-1 > 0) {
                    b = b.substring(0, b.length()-1);
                }
                data.add(b);
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
            if(pstm != null) {
                try {
                    pstm.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                pstm = null;
            }
        }
        return data;
    }
    public boolean preparedInsert(UsableMethods m) {
        boolean isAdded = false;
        PreparedStatement pstm = null;
        int rst = 0;
        try {
            rst = execute.preparedInsertQuery(m, pstm);
            if(rst > 0) {
                isAdded = true;
            } else {
                System.err.println(
                        "[ INFO ]: something happen while trying to execute preparedInsertQuery"
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(pstm != null) {
                try {
                    pstm.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                pstm = null;
            }
        }
        return isAdded;
    }
    public boolean insert(UsableMethods m) {
        boolean isAdded = false;
        Statement stm = null;
        int rst = 0;
        try {
            rst = execute.insertQuery(m, stm);
            if(rst > 0) {
                isAdded = true;
            } else {
                System.err.println(
                    "[ INFO ]: something happen while trying to execute insertQuery"
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
    public List<String> innerJoin(UsableMethods m, UsableMethods[] foreignModel, String[] foreignTables,
            ParamValue[] conditions) {
        List<String> data = new ArrayList<>();
        Statement stm = null;
        ResultSet rst = null;
        try {
            rst = execute.innerJoinQuery(m, foreignModel, foreignTables, conditions, stm);
            int length = rst.getMetaData().getColumnCount();
            while(rst.next()) {
                String b = "";
                for(int i=1; i<=length; ++i) {
                    String column = rst.getMetaData().getColumnName(i);
                    b += column + ":" + rst.getString(i) + "\n";
                }
                if(b.length()-1 > 0) {
                    b = b.substring(0, b.length()-1);
                }
                data.add(b);
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
        return data;
    }
    public boolean preparedUpdate(UsableMethods m, ParamValue condition) {
        boolean isUpdated = false;
        PreparedStatement pstm = null;
        int rst = 0;
        try {
            rst = execute.preparedUpdateQuery(m, condition, pstm);
            if(rst > 0) {
                isUpdated = true;
            } else {
                System.err.println(
                        "[ INFO ]: something happen while trying to execute preparedUpdate"
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(pstm != null) {
                try {
                    pstm.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                pstm = null;
            }
        }
        return isUpdated;
    }
    public boolean preparedDelete(ParamValue condition) {
        boolean isDeleted = false;
        PreparedStatement pstm = null;
        int rst = 0;
        try {
            rst = execute.preparedDeleteQuery(condition, pstm);
            if(rst > 0) {
                isDeleted = true;
            } else {
                System.err.println(
                        "[ INFO ]: something happen while trying to execute preparedDelete"
                );
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(pstm != null) {
                try {
                    pstm.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                pstm = null;
            }
        }
        return isDeleted;
    }
}
