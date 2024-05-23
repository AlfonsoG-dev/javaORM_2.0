package Samples;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestODM extends TestModel {
    public String name;
    public String email;
    public String rol;
    public String create_at;
    public String update_at;

    public TestODM() { }
    
    public TestODM(String name, String email, String rol) {
        super(name, email, rol);
        this.name = name;
        this.email = email;
        this.rol = rol;
    }
    public TestODM(String name, String email, String rol, String create_at) {
        super(name, email, rol, create_at);
        this.name = name;
        this.email = email;
        this.rol = rol;
        this.create_at = create_at;
    }
    public TestODM(String name, String email, String rol, String create_at, String update_at) {
        super(name, email, rol, create_at, update_at);
        this.name = name;
        this.email = email;
        this.rol = rol;
        this.create_at = create_at;
        this.update_at = update_at;
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
    public String getCreate_at() {
        String date = null;
        if(create_at != null) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
            LocalDateTime miDate  = LocalDateTime.parse(create_at, dtf);  
            date                  = dtf.format(miDate).toString();
        }
        return date;    
    }

    public String getUpdate_at() {
        String date = null;
        if(update_at != null) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
            LocalDateTime miDate  = LocalDateTime.parse(update_at, dtf);  
            date                  = dtf.format(miDate).toString();
        }
        return date;    
    }
    public void setUpdate_at() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
        LocalDateTime miDate  = LocalDateTime.now();  
        create_at             = dtf.format(miDate).toString();
    }

    public void setCreate_at() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
        LocalDateTime miDate  = LocalDateTime.now();  
        create_at             = dtf.format(miDate).toString();
    }

}
