package ORM.Builders.Query;

import ORM.Utils.Formats.ParamValue;
import ORM.Utils.Formats.UsableMethods;

import ORM.Utils.Query.QueryUtils;

public class QueryBuilder {
    private String tbName;
    private QueryUtils utils;

    public QueryBuilder(String tbName) {
        this.tbName = tbName;
        utils = new QueryUtils();
    }

    public String getPreparedSelectQuery(ParamValue condition) {
        String b = "SELECT * FROM " + tbName;
        b += utils.getPreparedCondition(condition);
        return b;
    }
    public String getPreparedFindQuery(ParamValue condition, String columns) {
        String b = "SELECT " + columns + " FROM " + tbName;
        b += utils.getPreparedCondition(condition);
        return b;
    }
    /**
     * INSERT INTO tbName(column1, column2) values ('value1', 'value2');
     * INSERT INTO tbName values('value1', 'value2');
     */
    public String getInsertQuery(UsableMethods m) {
        String 
            types = utils.getModelData(m)[0],
            columns = utils.getModelData(m)[1];
        return "INSERT INTO " + tbName + "(" + columns + ") VALUES(" + types + ")";
    }
    public String getPreparedInsertQuery(UsableMethods m) {
        String
            columns = utils.getModelData(m)[1],
            questionMark = utils.replaceForQuestion(columns);
        return "INSERT INTO " + tbName + "(" + columns + ") VALUES(" + questionMark + ")";
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
        for(int i=0; i<ftb.length; ++i) {
            ParamValue condition = conditions[i];
            String whereClause = utils.getInnerJoinCondition(tbName, ftb[i], condition);
            System.out.println(whereClause);
        }
        return "";
    }
    public String getPreparedUpdateQuery(UsableMethods m, ParamValue condition) {
        String
            whereClause = utils.getNormalCondition(condition),
            setValues = utils.getSetValues(m),
            b = "UPDATE " + tbName + " SET " + setValues + whereClause;
        return b;
    }

    public String getPreparedDeleteQuery(ParamValue condition) {
        String b = utils.getPreparedCondition(condition);
        return "DELETE FROM " + tbName + b;
    }
}
