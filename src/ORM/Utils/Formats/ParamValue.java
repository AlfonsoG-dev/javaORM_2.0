package ORM.Utils.Formats;

public class ParamValue {
    private String[] columns;
    private String[] values;
    private String type;

    public ParamValue(String column, String value, String type) {
        columns = new String[1]; 
        columns[0] = column;

        values = new String[1];
        columns[0] = value;
        this.type = type;
    }

    public ParamValue(String[] columns, String[] values, String type) {
        this.columns = columns;
        this.values = values;
        this.type = type;
    }

    public String[] getColumns() {
        return columns;
    }

    public String[] getValues() {
        return values;
    }
    public String getType() {
        return type.toUpperCase();
    }
}
