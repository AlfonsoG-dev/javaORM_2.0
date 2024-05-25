package Samples.Models.Primary;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestODM extends TestModel {
    public String name;
    public String email;
    public int users_id_fk;
    public String rol;
    public String create_at;
    public String update_at;

    public TestODM() { }
    
    public TestODM(String name, String email, int users_id_fk, String rol) {
        super(name, email, users_id_fk, rol);
        this.name = name;
        this.email = email;
        this.users_id_fk = users_id_fk;
        this.rol = rol;
    }
    public TestODM(String name, String email, int users_id_fk, String rol, String create_at) {
        super(name, email, users_id_fk, rol, create_at);
        this.name = name;
        this.email = email;
        this.users_id_fk = users_id_fk;
        this.rol = rol;
        this.create_at = create_at;
    }
    public TestODM(String name, String email, int users_id_fk, String rol, String create_at, String update_at) {
        super(name, email, users_id_fk, rol, create_at, update_at);
        this.name = name;
        this.email = email;
        this.users_id_fk = users_id_fk;
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
    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }
    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }
    public void createUpdate_at() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
        LocalDateTime miDate  = LocalDateTime.now();  
        create_at             = dtf.format(miDate).toString();
    }

    public void createCreate_at() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
        LocalDateTime miDate  = LocalDateTime.now();  
        create_at             = dtf.format(miDate).toString();
    }

}
