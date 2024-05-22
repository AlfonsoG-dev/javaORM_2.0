package Samples;

public class TestODM extends TestModel {
    public String name;
    public String email;
    public String rol;
    
    public TestODM(String name, String email, String rol) {
        super(name, email, rol);
        this.name = name;
        this.email = email;
        this.rol = rol;
    }

    // get
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRol() {
        return rol;
    }
}
