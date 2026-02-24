package orm.builders.querys;

import orm.utils.formats.ParamValue;
import orm.utils.formats.UsableMethods;
import orm.utils.query.QueryUtils;

// TODO: verify the changes of the string to StringBuilder
public class QueryBuilder {

    private static final String SELECT = "SELECT ";
    private static final String LIMIT = " LIMIT ";
    private static final String OFFSET = " OFFSET ";
    private static final String FROM = " FROM ";

    private String tbName;
    private QueryUtils queryUtils;

    public QueryBuilder(String tbName) {
        this.tbName = tbName;
        queryUtils = new QueryUtils();
    }

    public String getPreparedCount(ParamValue condition, String columns) {
        StringBuilder b = new StringBuilder(SELECT);
        b.append(queryUtils.getCountSelection(columns));
        b.append(queryUtils.getPreparedCondition(condition));
        int limit = condition.getLimit() > 0 ? condition.getLimit() : -1;
        int offset = condition.getOffset();
        if(limit != -1) {
            b.append(LIMIT);
            b.append(limit);
            b.append(OFFSET);
            b.append(offset);
        }
        return b.toString();
    }
    public String getPreparedSelectQuery(ParamValue condition) {
        StringBuilder b = new StringBuilder(SELECT);
        b.append("*");
        b.append(FROM);
        b.append(tbName);
        b.append(queryUtils.getPreparedCondition(condition));
        int limit = condition.getLimit() > 0 ? condition.getLimit() : -1;
        int offset = condition.getOffset();
        if(limit != -1) {
            b.append(LIMIT);
            b.append(limit);
            b.append(OFFSET);
            b.append(offset);
        }
        return b.toString();
    }
    public String getSelectInQuery(ParamValue condition, String columns) {
        if(columns.isEmpty()) {
            columns = "*";
        }
        StringBuilder b = new StringBuilder(SELECT);
        b.append(columns);
        b.append(FROM);
        b.append(tbName);
        b.append(queryUtils.getInCondition(condition));
        int limit = condition.getLimit() > 0 ? condition.getLimit() : -1;
        int offset = condition.getOffset();
        if(limit != -1) {
            b.append(LIMIT);
            b.append(limit);
            b.append(OFFSET);
            b.append(offset);
        }
        return b.toString();
    }
    public String getPreparedFindQuery(ParamValue condition, String columns) {
        if(columns.isEmpty()) {
            columns = "*";
        }
        StringBuilder b = new StringBuilder(SELECT);
        b.append(columns);
        b.append(FROM);
        b.append(tbName);
        b.append(queryUtils.getPreparedCondition(condition));
        int limit = condition.getLimit() > 0 ? condition.getLimit() : -1;
        int offset = condition.getOffset();
        if(limit != -1) {
            b.append(LIMIT);
            b.append(limit);
            b.append(OFFSET);
            b.append(offset);
        }
        return b.toString();
    }
    /**
     * select * from table where nombre like 'regex'
     */
    public String getSelectPattern(ParamValue condition, String columns) {
        if(columns.isEmpty()) {
            columns = "*";
        }
        StringBuilder b = new StringBuilder(SELECT);
        b.append(columns);
        b.append(FROM);
        b.append(tbName);
        b.append(queryUtils.getPatternCondition(condition));
        int limit = condition.getLimit() > 0 ? condition.getLimit() : -1;
        int offset = condition.getOffset();
        if(limit != -1) {
            b.append(LIMIT);
            b.append(limit);
            b.append(OFFSET);
            b.append(offset);
        }
        return b.toString();
    }
    public String getPreparedSelectMinMax(ParamValue params, ParamValue condition) {
        StringBuilder b = new StringBuilder(SELECT);
        b.append(queryUtils.getMinMaxSelection(params));
        b.append(queryUtils.getPreparedCondition(condition));
        int limit = condition.getLimit() > 0 ? condition.getLimit() : -1;
        int offset = condition.getOffset();
        if(limit != -1) {
            b.append(LIMIT);
            b.append(limit);
            b.append(OFFSET);
            b.append(offset);
        }
        return b.toString();
    }
    public String getPreparedInsertQuery(UsableMethods m) {
        String columns = queryUtils.getModelData(m)[1];
        String questionMark = queryUtils.replaceForQuestion(columns);
        return "INSERT INTO " + tbName + "(" + columns + ") VALUES(" + questionMark + ")";
    }
    /**
     * INSERT INTO tbName(column1, column2) values ('value1', 'value2');
     * INSERT INTO tbName values('value1', 'value2');
     */
    public String getInsertQuery(UsableMethods m) {
        String types = queryUtils.getModelData(m)[0];
        String columns = queryUtils.getModelData(m)[1];
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
        StringBuilder b = new StringBuilder(SELECT);
        String pColumns = queryUtils.getAlias(p, tbName);
        b.append(pColumns);
        b.append(", ");
        for(int i=0; i<ftb.length; ++i) {
            UsableMethods m = f[i];
            String fColumns = queryUtils.getAlias(m, ftb[i]) + ", ";
            b.append(fColumns);
        }
        b.append(FROM);
        b.append(tbName);
        for(int i=0; i<ftb.length; ++i) {
            ParamValue condition = conditions[i];
            String where = " INNER JOIN " + ftb[i] + queryUtils.getInnerJoinCondition(
                    tbName, ftb[i], condition
            );
            b.append(where);
        }
        return b.toString();
    }
    public String getPreparedUpdateQuery(UsableMethods m, ParamValue condition) {
        String whereClause = queryUtils.getPreparedCondition(condition);
        String setValues = queryUtils.getSetValues(m);
        return "UPDATE " + tbName + " SET " + setValues + whereClause;
    }

    public String getPreparedDeleteQuery(ParamValue condition) {
        String b = queryUtils.getPreparedCondition(condition);
        return "DELETE FROM " + tbName + b;
    }
}
