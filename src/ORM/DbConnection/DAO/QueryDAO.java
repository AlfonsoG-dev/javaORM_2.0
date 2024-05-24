package ORM.DbConnection.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ORM.DbConnection.Execution.ExecuteQuery;
import ORM.Utils.Formats.ParamValue;
import ORM.Utils.Formats.UsableMethods;

public class QueryDAO<T> {
    private ExecuteQuery execute;
    private UsableMethods buildObject;
    public QueryDAO(Connection cursor, String tableName, UsableMethods build) {
        execute = new ExecuteQuery(cursor, tableName);
        this.buildObject = build;
    }
    public List<T> preparedSelect(ParamValue c) {
        List<T> data = new ArrayList<>();
        PreparedStatement pstm = null;
        ResultSet rst = null;
        try {
            rst = execute.preparedSelectQuery(c, pstm);
            data.add(buildObject.build(rst));
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
}
