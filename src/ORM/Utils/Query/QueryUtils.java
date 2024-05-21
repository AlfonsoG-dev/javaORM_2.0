package ORM.Utils.Query;

import ORM.Utils.Formats.ParamValue;
import ORM.Utils.Formats.UsableModel;

public class QueryUtils {
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
    public String[] getModelData(UsableModel m) {
        String[] d = new String[2];
        // String properties = m.getInstanceData();
        return d;
    }
}
