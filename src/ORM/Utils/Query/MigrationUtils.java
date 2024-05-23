package ORM.Utils.Query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class MigrationUtils {
    private Connection cursor;
    public MigrationUtils(Connection cursor) {
        this.cursor = cursor;
    }
    protected ResultSet getShowCreateTable(String table) {
        Statement stm = null;
        ResultSet rst = null;
        try {
            stm = cursor.createStatement();
            String query = "SHOW COLUMNS FROM " + table;
            rst = stm.executeQuery(query);
            /*
            int length = rst.getMetaData().getColumnCount();
            while(rst.next()) {
                for(int i=1; i<length; ++i) {
                    if(i>1 && rst.getString(i) != null) {
                        System.out.println(rst.getString(i).isEmpty());
                    }
                    if(i==5) {
                        continue;
                    }
                }
            }
            */
        } catch(Exception e) {
            e.printStackTrace();
        }
        return rst;
    }
    public HashMap<String, List<String>> getTableData(String table) {
        HashMap<String, List<String>> data = new HashMap<>();
        List<String> 
            columns = new ArrayList<>(),
            rows = new ArrayList<>();
        ResultSet rst = getShowCreateTable(table);
        try {
            while(rst.next()) {
                String[] cols = rst.getString(1).split("\n");
                for(String c: cols) {
                    columns.add(c);
                }
                String[] 
                    types = rst.getString(2).split("\n"),
                     nulls = rst.getString(3).split("\n"),
                     keys = rst.getString(4).split("\n"),
                     extra = rst.getString(6).split("\n");
                for(int k=0; k<types.length; ++k) {
                    String b = "";
                    if(types[k] != null) {
                        b += types[k].toUpperCase();
                    }
                    if (nulls [k] != null && nulls [k].contains("NO")) {
                        b +=  " NOT NULL";
                    }
                    if(keys[k] != null) {
                        if(keys[k].contains("PRI")) {
                            b += " UNIQUE PRIMARY KEY";
                        }
                        if(keys[k].contains("MUL")) {
                            b += " FOREIGN KEY";
                        }
                        if(keys[k].contains("UNI")) {
                            b += " UNIQUE";
                        }
                    }
                    if(extra[k] != null) {
                        b += " " + extra[k];
                    }
                    rows.add(b.trim());
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        data.put("columns", columns);
        data.put("rows", rows);
        System.out.println(data.get("rows"));
        return data;
    }
    public void getColumns() {
    }
    /**
     * rows are the types or values
     */
    public void getRows() {
    }
    public void getConstraint() {
    }
    public void getCompareColumnNames() {
    }
    public void getCompareTypes() {
    }
    public void getCompareConstraint() {
    }
}
