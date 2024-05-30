package Samples.Migration;

import java.sql.Connection;

import ORM.DbConnection.DAO.MigrationDAO;
import ORM.Utils.Formats.ParamValue;

import Samples.Models.Primary.TestModel;
import Samples.Models.Foreign.UsersModel;

public class MigrationSample {
    private MigrationDAO dao;
    public MigrationSample(Connection cursor) {
        dao = new MigrationDAO(cursor, "users");
    }
    public void createDatabaseSample() {
        boolean isCreated = dao.createDatabase("databaseName");
        if(isCreated) {
            System.out.println("database has been created");
        }
    }
    public void createTAbleSample() {
        UsersModel m = new UsersModel();
        boolean isCreated = dao.createTable(m, "n");
        if(isCreated) {
            System.out.println("table has been created");
        }
    }
    public void createIndexSample() {
        boolean isCreated = dao.createIndex(true, "nombre, email");
        if(isCreated) {
            System.out.println("index's has been created");
        }
    }
    public void removeIndexSample() {
        boolean isRemoved = dao.removeIndex("nombre");
        if(isRemoved) {
            System.out.println("index has been removed");
        }
    }
    public void renameColumnSample() {
        // modify 1 of the model columns, change its name and execute this code
        UsersModel m = new UsersModel();
        boolean isModified = dao.renameColumn(m);
        if(isModified) {
            System.out.println("the column's name has been modified");
        }
    }
    public void addColumnSample() {
        TestModel model = new TestModel();
        UsersModel u = new UsersModel();
        String[]
            models = {u.initModel()},
            tables = {"users"};
        boolean isAdded = dao.addColumn(model.getInstanceData(), models, tables, true);
        if(isAdded) {
            System.out.println("a column has been added");
        }
    }
    public void removeColumnSample() {
        // delete 1 field of the model, and execute this code
        UsersModel m = new UsersModel();
        boolean isRemoved = dao.removeColumn(m);
        if(isRemoved) {
            System.out.println("a column has been deleted");
        }
    }
    public void modifyTypeSample() {
        // modify 1 type of the annotation fields, and execute this code
        UsersModel m = new UsersModel();
        boolean isModified = dao.modifyType(m);
        if(isModified) {
            System.out.println("a type has been modified");
        }
    }
    public void addCheckConstraintSample() {
        String[]
            c = {"rol"},
            v = {"testing", "admin"};
        ParamValue params = new ParamValue(c, v, "s");
        boolean isCreated = dao.addCheckConstraint(params);
        if(isCreated) {
            System.out.println("check contraint has been created");
        }
    }
    public void addDefaultConstraintSample() {
        String[] 
            c = {"rol"},
            v = {"testing"};
        ParamValue params = new ParamValue(c, v, "");
        boolean isCreated = dao.addDefaultConstraint(params);
        if(isCreated) {
            System.out.println("default value contraint has been created");
        }
    }
    public void removeCheckConstraintSample() {
        boolean isRemoved = dao.removeCheckConstraint("chk_rol");
        if(isRemoved) {
            System.out.println("check constraint has been removed");
        }
    }
    public void removeDefaultConstraintSample() {
        boolean isRemoved = dao.removeDefaultConstraint("rol");
        if(isRemoved) {
            System.out.println("default constraint has been removed");
        }
    }
}
