package ORM.Builders.Query;

import ORM.Utils.Formats.ParamValue;
import ORM.Utils.Formats.UsableModel;
import ORM.Utils.Query.QueryUtils;

public class QueryBuilder {
    private String tbName;
    private QueryUtils utils;

    public QueryBuilder(String tbName) {
        this.tbName = tbName;
        utils = new QueryUtils();
    }

    public String getPreparedSelectQuery(ParamValue condition) {
        String
            t = "",
            b = "SELECT * FROM " + tbName;
        if(!condition.getType().isEmpty()) {
            t = condition.getType();
        }
        b += utils.getPreparedCondition(condition);
        return utils.cleanByType(t, b);
    }
    public String getPreparedFindQuery(ParamValue condition, String columns) {
        String
            t = "",
            b = "SELECT " + columns + " FROM " + tbName;
        if(!condition.getType().isEmpty()) {
            t = condition.getType();
        }
        b += utils.getPreparedCondition(condition);
        
        return utils.cleanByType(t, b);
    }
    /**
     * INTSERT INTO tbName(column1, column2) values ('value1', 'value2');
     * INSERT INTO tbName values('value1', 'value2');
     */
    public String getInserQuery(UsableModel m) {
        String 
            types = utils.getModelData(m)[0],
            colums = utils.getModelData(m)[1];
        return "INSERT INTO " + tbName + "(" + colums + ") VALUES(" + types + ")";
    }
    public String getPreparedInserQuery(UsableModel m) {
        String
            columns = utils.getModelData(m)[1],
            questionMark = utils.replaceForQuestion(columns);
        return "INSERT INTO " + tbName + "(" + columns + ") VALUES(" + questionMark + ")";
    }
}
