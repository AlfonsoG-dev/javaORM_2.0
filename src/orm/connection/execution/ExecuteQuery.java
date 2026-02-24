package orm.connection.execution;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import orm.builders.querys.QueryBuilder;

import orm.utils.formats.ParamValue;
import orm.utils.formats.UsableMethods;
import orm.utils.model.ModelUtils;

// TODO: verify the try-resource changes
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
            if(values[i].contains("'")) {
                values[i] = values[i].replace("'", "");
            }
            pstm.setString((i+1), values[i]);
        }
    }
    public ResultSet preparedSelectCountQuery(ParamValue condition, String columns) {
        String sql = builder.getPreparedCount(condition, columns);
        try (PreparedStatement pstm = cursor.prepareStatement(sql)) {
            setPrepareStatementData(condition.getValues(), pstm);
            return pstm.executeQuery();
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public ResultSet preparedSelectQuery(ParamValue c){
        String sql = builder.getPreparedSelectQuery(c);
        try(PreparedStatement pstm = cursor.prepareStatement(sql)) {
            setPrepareStatementData(c.getValues(), pstm);
            return pstm.executeQuery();
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public ResultSet selectInQuery(ParamValue condition, String columns){
        String sql = builder.getSelectInQuery(condition, columns);
        try(Statement stm = cursor.createStatement()) {
            return stm.executeQuery(sql);
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public ResultSet preparedFindQuery(ParamValue c, String columns) {
        String sql = builder.getPreparedFindQuery(c, columns);
        try(PreparedStatement pstm = cursor.prepareStatement(sql)) {
            setPrepareStatementData(c.getValues(), pstm);
            return pstm.executeQuery();
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public ResultSet selectPatternQuery(ParamValue condition, String columns) {
        String sql = builder.getSelectPattern(condition, columns);
        try(Statement stm = cursor.createStatement()) {
            return stm.executeQuery(sql);
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public ResultSet preparedSelectMinMaxQuery(ParamValue params, ParamValue condition) {
        String sql = builder.getPreparedSelectMinMax(params, condition);
        try(PreparedStatement pstm = cursor.prepareStatement(sql)) {
            setPrepareStatementData(condition.getValues(), pstm);
            return pstm.executeQuery();
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public int preparedInsertQuery(UsableMethods m){
        String modelValues = modelUtils.getTypes(m.getInstanceData(), true);
        String sql = builder.getPreparedInsertQuery(m);
        try(PreparedStatement pstm = cursor.prepareStatement(sql)) {
            setPrepareStatementData(modelValues.split(","), pstm);
            return pstm.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public int insertQuery(UsableMethods m){
        String sql = builder.getInsertQuery(m);
        try(Statement stm = cursor.createStatement()) {
            return stm.executeUpdate(sql);
        } catch(SQLException e) {
            e.printStackTrace();
            return -1;
        } 
    }
    public ResultSet innerJoinQuery(UsableMethods p, UsableMethods[] f, String[] ftb, ParamValue[] conditions) {
        String sql = builder.getInnerJoinQuery(p, f, ftb, conditions);
        try(Statement stm = cursor.createStatement()) {
            return stm.executeQuery(sql);
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public int preparedUpdateQuery(UsableMethods m, ParamValue c) {
        String types = modelUtils.getTypes(m.getInstanceData(), true);
        String sql = builder.getPreparedUpdateQuery(m, c);
        try(PreparedStatement pstm = cursor.prepareStatement(sql)) {
            String[] setValues = types.split(",");
            String[] conditionValues = c.getValues();
            // set the condition values
            List<String> val = new ArrayList<>();
            for(String s: setValues) {
                val.add(s.replace("'", ""));
            }
            val.addAll(Arrays.asList(conditionValues));
            // set the update values
            for(int i=0; i<val.size(); ++i) {
                pstm.setString((i+1), val.get(i));
            }
            return pstm.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int preparedDeleteQuery(ParamValue c) {
        String sql = builder.getPreparedDeleteQuery(c);
        try(PreparedStatement pstm = cursor.prepareCall(sql)) {
            setPrepareStatementData(c.getValues(), pstm);
            return pstm.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
