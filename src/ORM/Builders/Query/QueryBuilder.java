package ORM.Builders.Query;

import ORM.Utils.Formats.ParamValue;
import ORM.Utils.Query.QueryUtils;

public class QueryBuilder {
    private String tbName;
    private QueryUtils utils;

    public QueryBuilder(String tbName) {
        this.tbName = tbName;
        utils = new QueryUtils();
    }

    public String getPreparedSelect(ParamValue condition) {
        String
            t = "",
            b = "SELECT * FROM " + tbName;
        if(!condition.getType().isEmpty()) {
            t = condition.getType();
        }
        b += " WHERE ";
        String[] c = condition.getColumns();
        for(int i=0; i<c.length; ++i) {
            if(t.equals("NOT")) {
                b+=  " NOT " + c[i] + "=? AND ";
            } else {
                b+= c[i] + "=? " + t + " ";
            }
        }
        return utils.cleanByType(t, b);
    }
    public String getPreparedFind(ParamValue condition, String columns) {
        String
            t = "",
            b = "SELECT " + columns + " FROM " + tbName;
        if(!condition.getType().isEmpty()) {
            t = condition.getType();
        }
        b += " WHERE ";
        String[] c = condition.getColumns();
        for(int i=0; i<c.length; ++i) {
            if(t.equals("NOT")) {
                b += " NOT " + c[i] + "=? AND ";
            } else {
                b += c[i] + "=? " + t + " ";
            }
        }
        return utils.cleanByType(t, b);
    }
}
