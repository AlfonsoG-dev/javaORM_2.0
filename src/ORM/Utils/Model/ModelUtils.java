package ORM.Utils.Model;

public class ModelUtils {
    private String clean(String c, int spaces) {
        String b = "";
        if(c.length()-spaces > 0) {
            b = c.substring(0, c.length()-spaces);
        }
        return b;
    }
    public String getTypes(String modelData, boolean includeKeys) {
        String[] data = modelData.split("\n");
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
    public String getColumns(String modelData, boolean includeKeys) {
        String[] data = modelData.split("\n");
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
}
