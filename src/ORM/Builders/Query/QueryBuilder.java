package ORM.Builders.Query;

import ORM.Utils.Formats.ParamValue;
import ORM.Utils.Formats.UsableMethods;
import ORM.Utils.Query.QueryUtils;

public class QueryBuilder {
    private String tbName;
    private QueryUtils queryUtils;

    public QueryBuilder(String tbName) {
        this.tbName = tbName;
        queryUtils = new QueryUtils();
    }

    public String getPreparedCount(ParamValue condition, String columns) {
        String b = "SELECT ";
        b += queryUtils.getCountSelection(columns);
        b += queryUtils.getPreparedCondition(condition);
        return b;
    }
    public String getPreparedSelectQuery(ParamValue condition) {
        String b = "SELECT * FROM " + tbName;
        b += queryUtils.getPreparedCondition(condition);
        return b;
    }
    public String getSelectInQuery(ParamValue condition, String columns) {
        if(columns.isEmpty()) {
            columns = "*";
        }
        String b = "SELECT " + columns + " FROM " + tbName;
        b += queryUtils.getInCondition(condition);
        return b;
    }
    public String getPreparedFindQuery(ParamValue condition, String columns) {
        if(columns.isEmpty()) {
            columns = "*";
        }
        String b = "SELECT " + columns + " FROM " + tbName;
        b += queryUtils.getPreparedCondition(condition);
        return b;
    }
    /**
     * select * from table where nombre like 'regex'
     */
    public String getSelectPattern(ParamValue condition, String columns) {
        if(columns.isEmpty()) {
            columns = "*";
        }
        String b = "SELECT " + columns + " FROM " + tbName;
        b += queryUtils.getPatternCondition(condition);
        return b;
    }
    public String getPreparedSelectMinMax(ParamValue params, ParamValue condition) {
        String b = "SELECT ";
        b += queryUtils.getMinMaxSelection(params);
        b += queryUtils.getPreparedCondition(condition);
        return b;
    }
    public String getPreparedInsertQuery(UsableMethods m) {
        String
            columns = queryUtils.getModelData(m)[1],
            questionMark = queryUtils.replaceForQuestion(columns);
        return "INSERT INTO " + tbName + "(" + columns + ") VALUES(" + questionMark + ")";
    }
    /**
     * INSERT INTO tbName(column1, column2) values ('value1', 'value2');
     * INSERT INTO tbName values('value1', 'value2');
     */
    public String getInsertQuery(UsableMethods m) {
        String 
            types = queryUtils.getModelData(m)[0],
            columns = queryUtils.getModelData(m)[1];
        return "INSERT INTO " + tbName + "(" + columns + ") VALUES(" + types + ")";
    }
    /**
     * select column as table_column, column_1 as table_column_1 from account INNER JOIN user ON account.fk=user.pk; 
     * select column as table_column, column_1 as table_column_1 from account INNER JOIN user ON account.column=user.column; 
     * <br> pre: </br> the list parameters will have the same length
     * @param p: primary model.
     * @param f: list of foreign models.
     * @param ftb: list of foreign models tables.
     * @param conditions: list of conditions.
     */
    public String getInnerJoinQuery(UsableMethods p, UsableMethods[] f, String[] ftb, ParamValue[] conditions) {
        String
            b = "SELECT ",
            pColumns = queryUtils.getAlias(p, tbName);
        b += pColumns + ", ";
        for(int i=0; i<ftb.length; ++i) {
            UsableMethods m = f[i];
            String fColumns = queryUtils.getAlias(m, ftb[i]) + ", ";
            b += fColumns;
        }
        b += "FROM " + tbName;
        for(int i=0; i<ftb.length; ++i) {
            ParamValue condition = conditions[i];
            String where = " INNER JOIN " + ftb[i] + queryUtils.getInnerJoinCondition(
                    tbName, ftb[i], condition
            );
            b += where;
        }
        return b;
    }
    public String getPreparedUpdateQuery(UsableMethods m, ParamValue condition) {
        String
            whereClause = queryUtils.getNormalCondition(condition),
            setValues = queryUtils.getSetValues(m),
            b = "UPDATE " + tbName + " SET " + setValues + whereClause;
        return b;
    }

    public String getPreparedDeleteQuery(ParamValue condition) {
        String b = queryUtils.getPreparedCondition(condition);
        return "DELETE FROM " + tbName + b;
    }
}
