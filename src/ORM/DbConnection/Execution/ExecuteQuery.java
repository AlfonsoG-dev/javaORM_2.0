package ORM.DbConnection.Execution;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import ORM.Builders.Query.QueryBuilder;

import ORM.Utils.Formats.ParamValue;
import ORM.Utils.Formats.UsableMethods;

import ORM.Utils.Model.ModelUtils;

public class ExecuteQuery {
    private Connection cursor;
    private QueryBuilder builder;
    private ModelUtils modelUtils;

    public ExecuteQuery(Connection cursor, String tableName) {
        this.cursor = cursor;
        builder = new QueryBuilder(tableName);
        modelUtils = new ModelUtils();
    }
    protected void setPrepareStatementData(String[] values, PreparedStatement pstm) throws SQLException {
        for(int i=0; i<values.length; ++i) {
            pstm.setString((i+1), values[i].trim());
        }
    }
    public ResultSet preparedSelectCountQuery(ParamValue condition, String columns, PreparedStatement pstm)
        throws SQLException {
        
        String sql = builder.getPreparedCount(condition, columns);
        pstm = cursor.prepareStatement(sql);
        setPrepareStatementData(condition.getValues(), pstm);
        return pstm.executeQuery();
    }
    public ResultSet preparedSelectQuery(ParamValue c, PreparedStatement pstm) throws SQLException {
        String sql = builder.getPreparedSelectQuery(c);
        pstm = cursor.prepareStatement(sql);
        setPrepareStatementData(c.getValues(), pstm);
        return pstm.executeQuery();
    }
    public ResultSet selectInQuery(ParamValue condition, String columns, Statement stm) throws SQLException {
        String sql = builder.getSelectInQuery(condition, columns);
        stm = cursor.createStatement();
        return stm.executeQuery(sql);
    }
    public ResultSet preparedFindQuery(ParamValue c, String columns, PreparedStatement pstm)
            throws SQLException {
        String sql = builder.getPreparedFindQuery(c, columns);
        pstm = cursor.prepareStatement(sql);
        setPrepareStatementData(c.getValues(), pstm);
        return pstm.executeQuery();
    }
    public ResultSet selectPatternQuery(ParamValue condition, String columns, Statement stm) 
        throws SQLException {
        String sql = builder.getSelectPattern(condition, columns);
        stm = cursor.createStatement();
        return stm.executeQuery(sql);
    }
    public ResultSet selectMinMaxQuery(ParamValue params, ParamValue condition, PreparedStatement pstm)
            throws SQLException {
        String sql = builder.getPreparedSelectMinMax(params, condition);
        pstm = cursor.prepareStatement(sql);
        setPrepareStatementData(condition.getValues(), pstm);
        return pstm.executeQuery();
    }
    public int preparedInsertQuery(UsableMethods m, PreparedStatement pstm) throws SQLException {
        String
            modelValues = modelUtils.getTypes(m.getInstanceData(), true),
            sql = builder.getPreparedInsertQuery(m);
        pstm = cursor.prepareStatement(sql);
        setPrepareStatementData(modelValues.split(","), pstm);
        return pstm.executeUpdate();
    }
    public int insertQuery(UsableMethods m, Statement stm) throws SQLException {
        String sql = builder.getInsertQuery(m);
        stm = cursor.createStatement();
        return stm.executeUpdate(sql);
    }
    public ResultSet innerJoinQuery(UsableMethods p, UsableMethods[] f, String[] ftb, ParamValue[] conditions, 
            Statement stm) throws SQLException {
        String sql = builder.getInnerJoinQuery(p, f, ftb, conditions);
        stm = cursor.createStatement();
        return stm.executeQuery(sql);
    }
    public int preparedUpdateQuery(UsableMethods m, ParamValue c, PreparedStatement pstm)
        throws SQLException {
        String sql = builder.getPreparedUpdateQuery(m, c);
        pstm = cursor.prepareStatement(sql);
        setPrepareStatementData(c.getValues(), pstm);
        return pstm.executeUpdate();
    }

    public int preparedDeleteQuery(ParamValue c, PreparedStatement pstm) throws SQLException {
        String sql = builder.getPreparedDeleteQuery(c);
        pstm = cursor.prepareCall(sql);
        setPrepareStatementData(c.getValues(), pstm);
        return pstm.executeUpdate();
    }
}
