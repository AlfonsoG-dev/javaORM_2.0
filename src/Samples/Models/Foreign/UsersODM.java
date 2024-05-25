package Samples.Models.Foreign;

public class UsersODM extends UsersModel {
    public String description;
    public String rol;

    public UsersODM() { }
    public UsersODM(String description, String rol) {
        this.description = description;
        this.rol = rol;
    }

    public String getDescription() {
        return description;
    }
    public String getRol() {
        return rol;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
}
