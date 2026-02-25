package orm.utils.query;

import orm.utils.formats.ParamValue;
import orm.utils.formats.UsableMethods;
import orm.utils.model.ModelUtils;

public class QueryUtils {

    private static final String CLAUSE = " WHERE ";
    private static final String NOT_CONDITION = " NOT ";


    private ModelUtils modelUtils;
    public QueryUtils() {
        modelUtils = new ModelUtils();
    }
    private String cleanByType(String type, String query) {
        String b = "";
        switch(type.toLowerCase()) {
            case "and":
                b = query.substring(0, query.length()-4);
                break;
            case "or":
                b = query.substring(0, query.length()-3);
                break;
            case "not":
                b = query.substring(0, query.length()-4);
                break;
            default:
                break;
        }
        return b;
    }
    public String clean(String query, int spaces) {
        String b = "";
        if(query.length()-spaces > 0) {
            b = query.substring(0, query.length()-spaces);
        }
        return b;
    }
    /**
     * @param condition of {ParamValue}: if empty values returns empty string
     */
    public String getPreparedCondition(ParamValue condition) {
        String t = "";
        StringBuilder b = new StringBuilder(CLAUSE);
        if(!condition.getType().isEmpty()) {
            t = condition.getType();
        }
        if(condition.getColumns().length == 0) {
            return "";
        }
        String[] columns = condition.getColumns();
        for(String c: columns) {
            if(t.equals("NOT")) {
                b.append(NOT_CONDITION);
                b.append(c);
                b.append("=? AND ");
            } else {
                b.append(c);
                b.append("=? ");
                b.append(t);
                b.append(" ");
            }
        }
        return cleanByType(t, b.toString());
    }
    public String getNormalCondition(ParamValue condition) {
        String t = "";
        StringBuilder b = new StringBuilder(CLAUSE);
        if(!condition.getType().isEmpty()) {
            t = condition.getType();
        }
        String[] c = condition.getColumns();
        String[] v = condition.getValues();
        for(int i=0; i<c.length; ++i) {
            if(t.equals("NOT")) {
                b.append(NOT_CONDITION);
                b.append(c[i]);
                b.append("='");
                b.append(v[i]);
                b.append("' AND ");
            } else {
                b.append(c[i]);
                b.append("='");
                b.append(v[i]);
                b.append("' ");
                b.append(t);
                b.append(" ");
            }
        }
        return cleanByType(t, b.toString());
    }
    public String getInCondition(ParamValue condition) {
        String t = "";
        StringBuilder cValues = new StringBuilder();
        StringBuilder b = new StringBuilder(CLAUSE);
        if(!condition.getType().isEmpty()) {
            t = condition.getType();
        }
        if(condition.getColumns().length == 0) {
            return "";
        }
        String[] c = condition.getColumns();
        String[] v = condition.getValues();
        for(int i=0; i<v.length; ++i) {
            cValues.append("'");
            cValues.append(v[i]);
            cValues.append("', ");
        }
        if(cValues.length()-2 > 0) {
            cValues = new StringBuilder(cValues.substring(0, cValues.length()-2));
        }
        for(int i=0; i<c.length; ++i) {
            if(t.equals("NOT")) {
                b.append(NOT_CONDITION);
                b.append(c[i]);
                b.append(" IN ");
                b.append("(");
                b.append(cValues);
                b.append(") AND ");
            } else {
                b.append(c[i]);
                b.append(" IN ");
                b.append("(");
                b.append(cValues);
                b.append(") ");
                b.append(t);
                b.append(" ");
            }
        }
        return cleanByType(t, b.toString());
    }
    public String getPatternCondition(ParamValue condition) {
        String t = "";
        StringBuilder b = new StringBuilder(CLAUSE);
        if(!condition.getType().isEmpty()) {
            t = condition.getType();
        }
        if(condition.getColumns().length == 0) {
            return "";
        }
        String[] c = condition.getColumns();
        String[] v = condition.getValues();
        for(int i=0; i<c.length; ++i) {
            if(t.equals("NOT")) {
                b.append(NOT_CONDITION);
                b.append(c[i]);
                b.append(" LIKE '");
                b.append(v[i]);
                b.append("' AND ");
            } else {
                b.append(c[i]);
                b.append(" LIKE '");
                b.append(v[i]);
                b.append("'");
                b.append(t);
                b.append(" ");
            }
        }
        return cleanByType(t, b.toString());
    }
    /**
     * ON pTableName.fk = fTableName.user.pk
     */
    public String getInnerJoinCondition(String pTableName, String fTableName, ParamValue condition) {
        String t = "";
        StringBuilder b = new StringBuilder();
        if(!condition.getType().isEmpty()) {
            t = condition.getType();
        }
        String[] columns = condition.getColumns();
        String[] values = condition.getValues();
        b.append(" ON");
        for(int i=0; i<columns.length; ++i) {
            b.append(" ");
            b.append(pTableName);
            b.append(".");
            b.append(columns[i]);
            b.append(" = ");
            b.append(fTableName);
            b.append(".");
            b.append(values[i]);
            b.append(" ");
            b.append(t); 
        }
        return cleanByType(t, b.toString());
    }
    public String replaceForQuestion(String columns) {
        StringBuilder b = new StringBuilder();
        String[] separate = columns.split(",");
        int i = 0;
        while(i < separate.length) {
            b.append("?,");
            ++i;
        }
        return clean(b.toString(), 1);
    }
    public String getMinMaxSelection(ParamValue params) {
        StringBuilder b = new StringBuilder();
        String[] c = params.getColumns();
        String[] v = params.getValues();
        if(c.length == 0) {
            return "";
        }
        for(int i=0; i<c.length; ++i) {
            if(c[i].equals("min")) {
                b.append("MIN(");
                b.append(v[i]);
                b.append(") AS min_");
                b.append(v[i]);
                b.append(", ");
            } else if(c[i].equals("max")) {
               b.append("MAX(");
               b.append(v[i]);
               b.append(") AS max_");
               b.append(v[i]);
               b.append(", ");
            }
        }
        return clean(b.toString(), 2);
    }
    /**
     * @param columns givin the column to count, default COUNT(*) as count_all
     */
    public String getCountSelection(String columns) {
        StringBuilder b = new StringBuilder();
        if(columns.isEmpty()) {
            return "COUNT(*) AS count_all";
        }
        String[] c = columns.split(",");
        for(int i=0; i<c.length; ++i) {
            b.append("COUNT(");
            b.append(c[i]);
            b.append(") AS count_");
            b.append(c[i]);
            b.append(", ");
        }
        return clean(b.toString(), 2);
    }
    public String[] getModelData(UsableMethods m) {
        String instanceData = m.getInstanceData();
        String[] returns = new String[2];
        String[] types = modelUtils.getTypes(instanceData, true).split(",");
        String[] columns = modelUtils.getColumns(instanceData, true).split(",");
        StringBuilder t = new StringBuilder();
        StringBuilder c = new StringBuilder();
        for(int i=0; i<types.length; ++i) {
            String d = types[i];
            if(!d.equals("''")) {
                t.append(d);
                t.append(",");
            }
        }
        for(int i=0; i<columns.length; ++i) {
            c.append(columns[i]);
            c.append(",");
        }
        returns[0] = clean(t.toString(), 1);
        returns[1] = clean(c.toString(), 1);
        return returns;
    }

    public String getSetValues(UsableMethods m) {
        StringBuilder b = new StringBuilder();
        String[] c = getModelData(m)[1].split(",");
        for(int i=0; i<c.length; ++i) {
            b.append(c[i]);
            b.append("=?, ");
        }
        return clean(b.toString(), 2);
    }
    public String getAlias(UsableMethods m, String tableName) {
        StringBuilder b = new StringBuilder();
        String[] columns = getModelData(m)[1].split(",");
        for(String c: columns) {
            b.append(tableName);
            b.append(".");
            b.append(c);
            b.append(" AS ");
            b.append(tableName);
            b.append("_");
            b.append(c);
            b.append(", ");
        }
        return clean(b.toString(), 2);
    }
}
