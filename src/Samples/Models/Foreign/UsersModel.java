package Samples.Models.Foreign;

import ORM.Utils.Formats.UsableMethods;
import ORM.Utils.Model.TableData;

public class UsersModel implements UsableMethods {
    @TableData(constraint = "not null unique primary key auto_increment", type = "int")
    private int id_pk;
    @TableData(constraint = "not null", type = "varchar(100)")
    private String description;
    @TableData(constraint = "", type = "varchar(50)")
    private String rol;
    public UsersModel() { }
}
