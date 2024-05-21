package ORM.Utils.Query;

import ORM.Utils.Formats.ParamValue;
import ORM.Utils.Formats.UsableModel;
import ORM.Utils.Model.ModelUtils;

public class QueryUtils {
    private ModelUtils modelUtils;
    public QueryUtils() {
        modelUtils = new ModelUtils();
    }
    public String cleanByType(String type, String query) {
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
        return b;
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
        return b;
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
    public String[] getModelData(UsableModel m) {
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

    public String getSetValues(UsableModel m) {
        String b = "";
        String[]
            c = getModelData(m)[1].split(","),
            v = getModelData(m)[0].split(",");
        for(int i=0; i<c.length; ++i) {
            b += c[i] + "=" + v[i] + ", ";
        }
        return clean(b, 2);
    }
}
