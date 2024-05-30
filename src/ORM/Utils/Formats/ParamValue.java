package ORM.Utils.Formats;

public class ParamValue {
    private String[] columns;
    private String[] values;
    private String type;
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
