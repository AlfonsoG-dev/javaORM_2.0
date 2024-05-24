package Samples.Models.Foreign;

import java.sql.ResultSet;

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

    @SuppressWarnings("unchecked")
    @Override
    public UsersODM build(ResultSet rst) {
        UsersODM m = null;
        try {
            while(rst.next()) {
                m = new UsersODM(rst.getString(2), rst.getString(3));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return m;
    }
}
