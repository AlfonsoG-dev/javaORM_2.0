package Samples;

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
    @TableData(constraint = "not null", type = "varchar(100)")
    private String rol;

    public TestModel(String name, String email, String rol) { }

    @Override
    public String getInstanceData() {
        throw new UnsupportedOperationException(
                "[ ERROR ]: this class is only for declaring database info"
        );
    }

}
