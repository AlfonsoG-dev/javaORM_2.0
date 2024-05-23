package ORM.Utils.Model;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
public class ModelUtils {
    private String clean(String c, int spaces) {
        String b = "";
        if(c.length()-spaces > 0) {
            b = c.substring(0, c.length()-spaces);
        }
        return b;
    }
    public String getTypes(String model, boolean includeKeys) {
        String[] data = model.split("\n");
        String ts = "";
        if(!includeKeys) {
            for(int i=0; i<data.length; ++i) {
                String type = data[i].split(":", 2)[i].trim();
                if(!type.isEmpty()) {
                    ts += "'" + type + "',";
                }
            }
        } else {
            for(int i=0; i<data.length; ++i) {
                String type = data[i].split(":", 2)[1].trim();
                if(!type.isEmpty()) {
                    ts += "'" + type + "',";
                }
            }
        }
        return clean(ts, 1);
    }
    public String getColumns(String model, boolean includeKeys) {
        String[] data = model.split("\n");
        String 
            colName = "",
            modelCol = "";
        for(int i=0; i<data.length; ++i) {
            String column = data[i].split(":", 2)[0].trim();
            if(!column.isEmpty()) {
                colName += column + ",";
            }
        }
        String[] cols = colName.split(",");
        if(!includeKeys) {
            for(int i=1; i<cols.length; ++i) {
                modelCol += cols[i] + ",";
            }
        } else {
            for(int i=0; i<cols.length; ++i) {
                modelCol += cols[i] + ",";
            }
        }
        return clean(modelCol, 1);
    }
    public HashMap<String, List<String>> getKeys(String model) {
        HashMap<String, List<String>> keys = new HashMap<>();
        List<String>
            pks = new ArrayList<>(),
            fks = new ArrayList<>();
        String[] columns = getColumns(model, true).split(",");
        for(String c: columns) {
            if(c.contains("pk")) {
                pks.add(c);
            } 
            if(c.contains("fk")) {
                fks.add(c);
            }
        }
        if(pks.size() > 0) {
            keys.put("pk", pks);
        }
        if(fks.size() > 0) {
            keys.put("fk", fks);
        }
        return keys;
    }
}
