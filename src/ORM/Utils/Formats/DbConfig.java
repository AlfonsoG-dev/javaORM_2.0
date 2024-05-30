package ORM.Utils.Formats;

public class DbConfig {
    public final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private String host;
    private String user;
    private int port;
    private String password;
    private String dbName;

    public DbConfig(String dbName, String host, int port, String user, String password) {
        this.host = host;
        this.user = user;
        this.port = port;
        this.password = password;
        this.dbName = dbName;
    }
    public String getHost() {
        return host;
    }
    public String getUser() {
        return user;
    }
    public int getPort() {
        return port;
    }
    public String getPassword() {
        return password;
    }
    public String getDbName() {
        return dbName;
    }
    public String getURL() {
        return "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.dbName;
    }
}
