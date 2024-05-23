package ORM.Utils.Query;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;


import ORM.Utils.Model.ModelUtils;

import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;

public class MigrationUtils {
    private Connection cursor;
    private ModelUtils modelUtils;
    public MigrationUtils(Connection cursor) {
        this.cursor = cursor;
        modelUtils = new ModelUtils();
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
        return data;
    }
    public HashMap<String, String> getCompareColumnNames(String table, String modelData) {
        HashMap<String, String> data = new HashMap<>();
        List<String>
            modelColumns = Arrays.asList(modelUtils.getColumns(modelData, true).split(",")),
            tableColumns = getTableData(table).get("columns");
        if(tableColumns.size() == modelColumns.size()) {
            String renamed = "";
            for(int i=0; i<tableColumns.size(); ++i) {
                if(!tableColumns.get(i).equals(modelColumns.get(i))) {
                    renamed += modelColumns.get(i) + ":" + tableColumns.get(i) + ",";
                }
            }
            data.put(
                    "rename", renamed.substring(0, renamed.length()-1)
            );
        }
        if(modelColumns.size() > tableColumns.size()) {
            String added = "";
            for(int i=0; i<modelColumns.size(); ++i) {
                if(!tableColumns.contains(modelColumns.get(i))) {
                    added += modelColumns.get(i) + ",";
                }
            }
            data.put(
                    "add", added.substring(0, added.length()-1)
            );
        }
        if(tableColumns.size() > modelColumns.size()) {
            String deleted = "";
            for(int i=0; i<tableColumns.size(); ++i) {
                if(!modelColumns.contains(tableColumns.get(i))) {
                    deleted += tableColumns.get(i) + ":" + i + ",";
                }
            }
            data.put(
                    "delete", deleted.substring(0, deleted.length()-1)
            );
        }
        return data;
    }
    public HashMap<String, String> getCompareTypes(String table, String modelData) {
        HashMap<String, String> data = new HashMap<>();
        List<String>
            modelTypes = Arrays.asList(modelUtils.getTypes(modelData, true).split(",")),
            tableTypes = getTableData(table).get("rows");
        if(modelTypes.size() == tableTypes.size()) {
            String modify = "";
            for(int i=0; i<modelTypes.size(); ++i) {
                String cleanModelTypes = modelTypes.get(i).replace("'", "");
                if(!tableTypes.contains(cleanModelTypes)) {
                    modify += cleanModelTypes + ":" + i + ",";
                }
            }
            data.put(
                    "modify", modify.substring(0, modify.length()-1)
            );
        }
        return data;
    }
}
