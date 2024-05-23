package ORM.Builders.Migration;

import ORM.Utils.Model.ModelUtils;

public class MigrationBuilder {
    private String tableName;
    private ModelUtils modelUtils;
    public MigrationBuilder(String tableName) {
        this.tableName = tableName;
        modelUtils = new ModelUtils();
    }

    public String getCreateTableQuery() {
        String b = "";
        return b;
    }
    public String getCreateIndexQuery() {
        String b = "";
        return b;
    }
    public String getRemoveIndexQuery() {
        String b = "";
        return b;
    }
    public String getAddColumnQuery() {
        String b = "";
        return b;
    }
    public String getRemoveColumnQuery() {
        String b = "";
        return b;
    }
    public String getModifyTypeQuery() {
        String b = "";
        return b;
    }
    public String getAddConstraintQuery() {
        String b = "";
        return b;
    }
    public String getRemoveConstraintQuery() {
        String b = "";
        return b;
    }
}
