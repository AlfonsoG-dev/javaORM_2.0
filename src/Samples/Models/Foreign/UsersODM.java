package Samples.Models.Foreign;

import java.lang.reflect.Method;
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
    public void setDescription(String description) {
        this.description = description;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }

    @SuppressWarnings("unchecked")
    @Override
    public UsersODM build(ResultSet rst) {
        UsersODM m = new UsersODM();
        try {
            int length = rst.getMetaData().getColumnCount();
            while(rst.next()) {
                for(int i=1; i<=length; ++i) {
                    String 
                        columnName = rst.getMetaData().getColumnName(i),
                        value = rst.getString(i);
                    buildTest(columnName, value, m);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return m;
    }
    private void buildTest(String rstColumnName, String rstValue, UsersODM instance) {
        Class<?> c = UsersODM.class;
        try {
            Method[] methods = c.getMethods();
            String compare = "set" + rstColumnName.substring(0, 1).toUpperCase().concat(rstColumnName.substring(1));
            for(Method m: methods) {
                if(m.getName().equals(compare)) {
                    for(Class<?> p: m.getParameterTypes()) {
                        if(p == String.class) {
                            m.invoke(instance, rstValue);
                        }
                        if(p == int.class) {
                            m.invoke(instance, Integer.parseInt(rstValue));
                        }
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
