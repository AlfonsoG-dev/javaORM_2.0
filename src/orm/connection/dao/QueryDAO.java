package orm.connection.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import orm.connection.execution.ExecuteQuery;
import orm.utils.formats.ParamValue;
import orm.utils.formats.UsableMethods;

public class QueryDAO<T> {

    private static final String CONSOLE_FORMAT = "%s%n";

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
        try(ResultSet rst = execute.preparedSelectCountQuery(condition, columns)) {
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
        try(ResultSet rst = execute.selectInQuery(condition, columns)) {
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
        }
        return data;
    }

    public List<String> selectPattern(ParamValue condition, String columns) {
        List<String> data = new ArrayList<>();
        try(ResultSet rst = execute.selectPatternQuery(condition, columns)) {
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
        }
        return data;
    }

    public List<String> preparedSelectMinMax(ParamValue params, ParamValue condition) {
        List<String> data = new ArrayList<>();
        try(ResultSet rst = execute.preparedSelectMinMaxQuery(params, condition)) {
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
        }
        return data;
    }

    public List<T> preparedSelect(ParamValue c) {
        List<T> data = new ArrayList<>();
        try(PreparedStatement pstm = execute.preparedSelectQuery(c); ResultSet rst = pstm.executeQuery()) {
            data.addAll(buildObject.build(rst));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    public List<String> preparedFind(ParamValue condition, String columns) {
        List<String> data = new ArrayList<>();
        try(ResultSet rst = execute.preparedFindQuery(condition, columns)) {
            int length = rst.getMetaData().getColumnCount();
            while(rst.next()) {
                StringBuilder b = new StringBuilder();
                for(int i=1; i<=length; ++i) {
                    b.append(rst.getMetaData().getColumnName(i));
                    b.append(":");
                    b.append(rst.getString(i));
                    b.append("\n");
                }
                data.add(b.toString());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    public boolean preparedInsert(UsableMethods m) {
        boolean isAdded = false;
        int rst = execute.preparedInsertQuery(m);
        if(rst > 0) {
            isAdded = true;
        } else {
            System.console().printf(CONSOLE_FORMAT,
                    "[Error] something happen while trying to execute preparedInsertQuery");
        }
        return isAdded;
    }
    public boolean insert(UsableMethods m) {
        boolean isAdded = false;
        int rst = execute.insertQuery(m);
        if(rst > 0) {
            isAdded = true;
        } else {
            System.console().printf(CONSOLE_FORMAT,
                "[Error] something happen while trying to execute insertQuery"
            );
        }
        return isAdded;
    }
    public List<String> innerJoin(UsableMethods m, UsableMethods[] foreignModel, String[] foreignTables,
            ParamValue[] conditions) {
        List<String> data = new ArrayList<>();
        try(ResultSet rst = execute.innerJoinQuery(m, foreignModel, foreignTables, conditions)) {
            int length = rst.getMetaData().getColumnCount();
            while(rst.next()) {
                StringBuilder b = new StringBuilder();
                for(int i=1; i<=length; ++i) {
                    String column = rst.getMetaData().getColumnName(i);
                    b.append(column);
                    b.append(":");
                    b.append(rst.getString(i));
                    b.append("\n");
                }
                data.add(b.toString());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    public boolean preparedUpdate(UsableMethods m, ParamValue condition) {
        boolean isUpdated = false;
        int rst = execute.preparedUpdateQuery(m, condition);
        if(rst > 0) {
            isUpdated = true;
        } else {
            System.console().printf(CONSOLE_FORMAT,
                    "[Error] something happen while trying to execute preparedUpdate");
        }
        return isUpdated;
    }
    public boolean preparedDelete(ParamValue condition) {
        boolean isDeleted = false;
        int rst = execute.preparedDeleteQuery(condition);
        if(rst > 0) {
            isDeleted = true;
        } else {
            System.console().printf(CONSOLE_FORMAT,
                    "[Error] something happen while trying to execute preparedDelete");
        }
        return isDeleted;
    }
}
