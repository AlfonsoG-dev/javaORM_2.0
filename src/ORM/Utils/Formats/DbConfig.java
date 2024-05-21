package ORM.Utils.Formats;

public class DbConfig {
    public final static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private String host;
    private String user;
    private String password;
    private String dbName;

    public DbConfig(String host, String user, String password, String dbName) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.dbName = dbName;
    }
    public String getUser() {
        return user;
    }
    public String getPassword() {
        return password;
    }

    public String getURL() {
        String b = "jdbc:mysql://" + host + "/" + dbName;
        return b;
    }
}
