package Samples;

import ORM.Utils.Formats.UsableMethods;
import ORM.Utils.Model.ModelMetadata;

public class TestODM implements UsableMethods {
    public String name;
    public String email;
    public String rol;

    
    public TestODM(String name, String email, String rol) {
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

    @Override
    public String getInstanceData() {
        ModelMetadata metadata = new ModelMetadata(TestODM.class);
        return metadata.getInstanceData(this);
    }
}
