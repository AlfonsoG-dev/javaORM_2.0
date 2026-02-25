package orm.utils.model;

import java.util.List;
import java.util.Map;
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
    private void appendColumnType(StringBuilder ts, String[] data) {
        for(int i=0; i<data.length; ++i) {
            String[] spaces = data[i].split(":", 2);
            if(spaces.length > 1) {
                String type = spaces[1].trim();
                if(!type.isEmpty()) {
                    ts.append("'");
                    ts.append(type);
                    ts.append("',");
                }
            }
        }
    }
    public String getTypes(String model, boolean includeKeys) {
        String[] data = model.split("\n");
        StringBuilder ts = new StringBuilder();
        if(!includeKeys) {
            for(int i=0; i<data.length; ++i) {
                String type = data[i].split(":", 2)[i].trim();
                if(!type.isEmpty()) {
                    ts.append("'");
                    ts.append(type);
                    ts.append("',");
                }
            }
        } else {
            appendColumnType(ts, data);
        }
        return clean(ts.toString(), 1);
    }
    public String getColumns(String model, boolean includeKeys) {
        String[] data = model.split("\n");
        StringBuilder colName = new StringBuilder();
        StringBuilder modelCol = new StringBuilder();
        for(int i=0; i<data.length; ++i) {
            String column = data[i].split(":", 2)[0].trim();
            if(!column.isEmpty()) {
                colName.append(column);
                colName.append(",");
            }
        }
        String[] cols = colName.toString().split(",");
        if(!includeKeys) {
            for(int i=1; i<cols.length; ++i) {
                modelCol.append(cols[i]);
                modelCol.append(",");
            }
        } else {
            for(int i=0; i<cols.length; ++i) {
                modelCol.append(cols[i]);
                modelCol.append(",");
            }
        }
        return clean(modelCol.toString(), 1);
    }
    public Map<String, List<String>> getKeys(String model) {
        Map<String, List<String>> keys = new HashMap<>();
        List<String> pks = new ArrayList<>();
        List<String> fks = new ArrayList<>();
        String[] columns = getColumns(model, true).split(",");
        for(String c: columns) {
            if(c.contains("pk")) {
                pks.add(c);
            } 
            if(c.contains("fk")) {
                fks.add(c);
            }
        }
        if(!pks.isEmpty()) {
            keys.put("pk", pks);
        }
        if(!fks.isEmpty()) {
            keys.put("fk", fks);
        }
        return keys;
    }
    public int getColumnIndex(String modelData, String columnName, boolean includeKeys) {
        int index = 0;
        String[] columns = getColumns(modelData, includeKeys).split(",");
        for(int i=0; i<columns.length; ++i) {
            if(columns[i].equals(columnName)) {
                index = i;
            }
        }
        return index;
    }
}
