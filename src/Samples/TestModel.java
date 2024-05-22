package Samples;

import ORM.Utils.Formats.UsableModel;
import ORM.Utils.Model.ModelMetadata;
import ORM.Utils.Model.TableData;

public class TestModel implements UsableModel {

    // members
    @TableData(constraint = "not null unique primary key auto_increment", type = "int")
    private int id_pk;
    @TableData(constraint = "not null", type = "varchar(100)")
    private String name;
    @TableData(constraint = "not null unique", type = "varchar(100)")
    private String email;
    @TableData(constraint = "not null", type = "varchar(100)")
    private String rol;

    // contructor
    public TestModel(int id_pk, String name, String email, String rol) {
        this.id_pk = id_pk;
        this.name = name;
        this.email = email;
        this.rol = rol;
    }

    public TestModel(String name, String email, String rol) {
        this.name = name;
        this.email = email;
        this.rol = rol;
    }

    // get
    public int getId_pk() {
        return id_pk;
    }
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRol() {
        return rol;
    }

    // set
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    // UsableModel methods
    @Override
    public String getInstanceData() {
        String b =""; 
        if(getId_pk() > 0) {
            b += "id_pk: " + getId_pk() + "\n";
        }
        if(!getName().isEmpty()) {
            b += "name: " + getName() + "\n";
        } 
        if(!getEmail().isEmpty()) {
            b += "email: " + getEmail() + "\n";
        }
        if(!getRol().isEmpty()) {
            b += "rol: " + getRol() + "\n";
        }
        return b;
    }
    public String instanceData(TestModel m) {
        ModelMetadata metadata = new ModelMetadata(TestModel.class);
        return metadata.getInstanceData(m);
    }
}
