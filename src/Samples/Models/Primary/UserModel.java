package Samples.Models.Primary;

import ORM.Utils.Formats.UsableMethods;
import ORM.Utils.Model.TableData;

public class UserModel implements UsableMethods {
    @TableData(constraint = "not null unique primary key auto_increment", type = "int")
    private int id_pk;
    @TableData(constraint = "not null unique", type = "varchar(100)")
    private String nombre;
    @TableData(constraint = "not null unique", type = "varchar(100)")
    private String email;
    @TableData(constraint = "not null", type = "varchar(100)")
    private String password;
    @TableData(constraint = "", type = "varchar(50)")
    private String rol;
    @TableData(constraint = "not null", type = "datetime")
    private String create_at;
    @TableData(constraint = "", type = "datetime")
    private String update_at;
    public UserModel() { }
    /**
     * @return the id_pk
     */
    public int getId_pk() {
        return id_pk;
    }
    /**
     * @param id_pk the id_pk to set
     */
    public void setId_pk(int id_pk) {
        this.id_pk = id_pk;
    }
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return the rol
     */
    public String getRol() {
        return rol;
    }
    /**
     * @param rol the rol to set
     */
    public void setRol(String rol) {
        this.rol = rol;
    }
    /**
     * @return the create_at
     */
    public String getCreate_at() {
        return create_at;
    }
    /**
     * @param create_at the create_at to set
     */
    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }
    /**
     * @return the update_at
     */
    public String getUpdate_at() {
        return update_at;
    }
    /**
     * @param update_at the update_at to set
     */
    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }
}

