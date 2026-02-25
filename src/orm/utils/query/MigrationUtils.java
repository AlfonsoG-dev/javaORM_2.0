package orm.utils.query;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;


import orm.utils.model.ModelUtils;

import java.util.List;
import java.util.Map;
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
        } catch(Exception e) {
            e.printStackTrace();
        }
        return rst;
    }
    private void appendKeyConstraint(StringBuilder b, String[] keys, int k) {
        if(keys[k].contains("PRI")) {
            b.append(" UNIQUE PRIMARY KEY");
        }
        if(keys[k].contains("MUL")) {
            b.append(" FOREIGN KEY");
        }
        if(keys[k].contains("UNI")) {
            b.append(" UNIQUE");
        }
    }
    private void appendConstraints(List<String> rows, String[] types, String[] nulls, String[] keys, String[] extra) {
        for(int k=0; k<types.length; ++k) {
            StringBuilder b = new StringBuilder();
            if(types[k] != null) {
                b.append(types[k].toUpperCase());
            }
            if (nulls [k] != null && nulls [k].contains("NO")) {
                b.append(" NOT NULL");
            }
            if(keys[k] != null) {
                appendKeyConstraint(b, keys, k);
            }
            if(extra[k] != null) {
                b.append(" ");
                b.append(extra[k]);
            }
            rows.add(b.toString().trim());
        }
    }
    public Map<String, List<String>> getTableData(String table) {
        HashMap<String, List<String>> data = new HashMap<>();
        List<String> columns = new ArrayList<>();
        List<String> rows = new ArrayList<>();
        ResultSet rst = getShowCreateTable(table);
        try {
            while(rst.next()) {
                columns.addAll(Arrays.asList(rst.getString(1).split("\n")));
                String[] types = rst.getString(2).split("\n");
                String[] nulls = rst.getString(3).split("\n");
                String[] keys = rst.getString(4).split("\n");
                String[] extra = rst.getString(6).split("\n");
                appendConstraints(rows, types, nulls, keys, extra);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        data.put("columns", columns);
        data.put("rows", rows);
        return data;
    }
    private void appendAdditionalColumns(List<String> modelColumns, List<String> tableColumns, Map<String, String> data) {
        StringBuilder added = new StringBuilder();
        for(int i=0; i<modelColumns.size(); ++i) {
            if(!tableColumns.contains(modelColumns.get(i))) {
                added.append(modelColumns.get(i));
                added.append(",");
            }
        }
        data.put("add", added.substring(0, added.length()-1));
    }
    public Map<String, String> getCompareColumnNames(String table, String modelData) {
        HashMap<String, String> data = new HashMap<>();
        List<String> modelColumns = Arrays.asList(modelUtils.getColumns(modelData, true).split(","));
        List<String> tableColumns = getTableData(table).get("columns");
        if(tableColumns.size() == modelColumns.size()) {
            StringBuilder renamed = new StringBuilder();
            for(int i=0; i<tableColumns.size(); ++i) {
                if(!tableColumns.get(i).equals(modelColumns.get(i))) {
                    renamed.append(modelColumns.get(i));
                    renamed.append(":");
                    renamed.append(tableColumns.get(i));
                    renamed.append(",");
                }
            }
            data.put("rename", renamed.substring(0, renamed.length()-1));
        }
        if(modelColumns.size() > tableColumns.size()) {
            appendAdditionalColumns(modelColumns, tableColumns, data);
        }
        if(tableColumns.size() > modelColumns.size()) {
            StringBuilder deleted = new StringBuilder();
            for(int i=0; i<tableColumns.size(); ++i) {
                if(!modelColumns.contains(tableColumns.get(i))) {
                    deleted.append(tableColumns.get(i));
                    deleted.append(":");
                    deleted.append(i);
                    deleted.append(",");
                }
            }
            data.put("delete", deleted.substring(0, deleted.length()-1));
        }
        return data;
    }
    public Map<String, String> getCompareTypes(String table, String modelData) {
        HashMap<String, String> data = new HashMap<>();
        List<String> modelTypes = Arrays.asList(modelUtils.getTypes(modelData, true).split(","));
         List<String> tableTypes = getTableData(table).get("rows");
        if(modelTypes.size() == tableTypes.size()) {
            StringBuilder modify = new StringBuilder();
            for(int i=0; i<modelTypes.size(); ++i) {
                String cleanModelTypes = modelTypes.get(i).replace("'", "");
                if(!tableTypes.contains(cleanModelTypes)) {
                    modify.append(cleanModelTypes);
                    modify.append(":");
                    modify.append(i);
                    modify.append(",");
                }
            }
            data.put("modify", modify.substring(0, modify.length()-1));
        }
        return data;
    }
}
