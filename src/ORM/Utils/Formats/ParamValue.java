package ORM.Utils.Formats;

public class ParamValue {
    private String[] columns;
    private String[] values;
    private String type;
    private int limit;
    /**
     * has the same value as limit
     */
    private int offset;
    public ParamValue(String[] columns, String[] values, String type) {
        this.columns = columns;
        this.values = values;
        this.type = type;
    }
    public ParamValue(String[] columns, String[] values, String type, int limit) {
        this.columns = columns;
        this.values = values;
        this.type = type;
        this.limit = limit;
        this.offset = limit;
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
    public int getLimit() {
        return limit;
    }
    public int getOffset() {
        return offset;
    }
}
