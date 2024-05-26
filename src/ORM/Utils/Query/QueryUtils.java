package ORM.Utils.Query;

import ORM.Utils.Formats.ParamValue;
import ORM.Utils.Formats.UsableMethods;
import ORM.Utils.Model.ModelUtils;

public class QueryUtils {
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
    public String getPreparedCondition(ParamValue condition) {
        String
            t = "",
            b = " WHERE ";
        if(!condition.getType().isEmpty()) {
            t = condition.getType();
        }
        String[] columns = condition.getColumns();
        for(String c: columns) {
            if(t.equals("NOT")) {
                b += " NOT " + c + "=? AND ";
            } else {
                b += c + "=? " + t + " ";
            }
        }
        return cleanByType(t, b);
    }
    public String getNormalCondition(ParamValue condition) {
        String 
            t = "",
            b = " WHERE ";
        if(!condition.getType().isEmpty()) {
            t = condition.getType();
        }
        String[] 
            c = condition.getColumns(),
            v = condition.getValues();
        for(int i=0; i<c.length; ++i) {
            if(t.equals("NOT")) {
                b += " NOT " + c[i] + "='" + v[i] + "' AND ";
            } else {
                b += c[i] + "='" + v[i] + "' " + t + " ";
            }
        }
        return cleanByType(t, b);
    }
    public String getInCondition(ParamValue condition) {
        String
            t = "",
            cValues = "",
            b = " WHERE ";
        if(!condition.getType().isEmpty()) {
            t = condition.getType();
        }
        String[]
            c = condition.getColumns(),
            v = condition.getValues();
        for(int i=0; i<v.length; ++i) {
            cValues += "'" + v[i] + "', ";
        }
        if(cValues.length()-2 > 0) {
            cValues = cValues.substring(0, cValues.length()-2);
        }
        for(int i=0; i<c.length; ++i) {
            if(t.equals("NOT")) {
                b += " NOT " + c[i] + " IN " + "(" + cValues + ") AND ";
            } else {
                b += c[i] + " IN " + "(" + cValues + ") " + t + " ";
            }
        }
        return cleanByType(t, b);
    }
    public String getPatternCondition(ParamValue condition) {
        String 
            t = "",
            b = " WHERE ";
        if(!condition.getType().isEmpty()) {
            t = condition.getType();
        }
        String[]
            c = condition.getColumns(),
            v = condition.getValues();
        for(int i=0; i<c.length; ++i) {
            if(t.equals("NOT")) {
                b += " NOT " + c[i] + " LIKE '" + v[i] + "' AND ";
            } else {
                b += c[i] + " LIKE '" + v[i] + "'" + t + " ";
            }
        }
        return cleanByType(t, b);
    }
    /**
     * ON pTableName.fk = fTableName.user.pk
     */
    public String getInnerJoinCondition(String pTableName, String fTableName, ParamValue condition) {
        String 
            t = "",
            b = "";
        if(!condition.getType().isEmpty()) {
            t = condition.getType();
        }
        String[] 
            columns = condition.getColumns(),
            values = condition.getValues();
        b += " ON";
        for(int i=0; i<columns.length; ++i) {
            b += " " + pTableName + "." + columns[i] + " = " + fTableName + "." + values[i] + " " + t; 
        }
        return cleanByType(t, b);
    }
    public String replaceForQuestion(String columns) {
        String b = "";
        String[] separate = columns.split(",");
        int i = 0;
        while(i < separate.length) {
            b += "?,";
            ++i;
        }
        return clean(b, 1);
    }
    public String getMinMaxSelection(ParamValue params) {
        String b = "";
        String[] 
            c = params.getColumns(),
            v = params.getValues();
        for(int i=0; i<c.length; ++i) {
            if(c[i].equals("min")) {
                b += "MIN(" + v[i] +") AS min_" + v[i] + ", ";
            } else if(c[i].equals("max")) {
                b += "MAX(" + v[i] +") AS max_" + v[i] + ", ";
            }
        }
        return clean(b, 2);
    }
    public String getCountSelection(String columns) {
        String b = "";
        String[] c = columns.split(",");
        for(int i=0; i<c.length; ++i) {
            b += "COUNT(" + c[i] +") AS count_" + c[i] + ", ";
        }
        return clean(b, 2);
    }
    public String[] getModelData(UsableMethods m) {
        String[]
            returns = new String[2],
            types = modelUtils.getTypes(m.getInstanceData(), true).split(","),
            columns = modelUtils.getColumns(m.getInstanceData(), true).split(",");
        String 
            t = "",
            c = "";
        for(int i=0; i<types.length; ++i) {
            String d = types[i];
            if(!d.equals("''")) {
                t += d + ",";
            }
        }
        for(int i=0; i<columns.length; ++i) {
            c += columns[i] + ",";
        }
        returns[0] = clean(t, 1);
        returns[1] = clean(c, 1);
        return returns;
    }

    public String getSetValues(UsableMethods m) {
        String b = "";
        String[]
            c = getModelData(m)[1].split(","),
            v = getModelData(m)[0].split(",");
        for(int i=0; i<c.length; ++i) {
            b += c[i] + "=" + v[i] + ", ";
        }
        return clean(b, 2);
    }
    public String getAlias(UsableMethods m, String tableName) {
        String b = "";
        String[] columns = getModelData(m)[1].split(",");
        for(String c: columns) {
            b += tableName + "." + c + " AS " + tableName + "_" + c + ", ";
        }
        return clean(b, 2);
    }
}
