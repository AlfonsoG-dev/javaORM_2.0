package Samples.Models.Primary;

import java.sql.ResultSet;

import ORM.Utils.Formats.UsableMethods;
import ORM.Utils.Model.TableData;

public class TestModel implements UsableMethods {

    // members
    @TableData(constraint = "not null unique primary key auto_increment", type = "int")
    private int id_pk;
    @TableData(constraint = "not null", type = "varchar(100)")
    private String name;
    @TableData(constraint = "not null unique", type = "varchar(100)")
    private String email;
    @TableData(constraint = "not null", type = "int")
    private int users_id_fk;
    @TableData(constraint = "not null", type = "varchar(100)")
    private String rol;
    @TableData(constraint = "not null", type = "date")
    private String create_at;
    @TableData(constraint = "not null", type = "date")
    private String update_at;

    public TestModel() { }
    public TestModel(String name, String email, int users_id_fk, String rol) { }
    public TestModel(String name, String email, int users_id_fk, String rol, String create_at) { }
    public TestModel(String name, String email, int users_id_fk, String rol, String create_at, String update_at) { }
    @Override
    public <T> T build(ResultSet rst) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'build'");
    }
}
