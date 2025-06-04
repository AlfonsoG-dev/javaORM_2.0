# Another java ORM(Object Relational Mapping) application

- In this iteration i want to use java.reflect to get the instance data of a model.

> For this purpose is necessary to use 2 classes, one if used as a table model, the other one is used to perform CRUD operations and it needs to have in its name the ODM sentence at the end.

>- Also the model can be use for SELECT operation and the ODM class is used for INSERT, UPDATE, DELETE operations.

> See [First version](https://github.com/AlfonsoG-dev/javaMysqlORM) for a more simple approach.

# References
- [java.lang.reflect](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/reflect/package-summary.html)
- [java mysql orm 1.0](https://github.com/AlfonsoG-dev/javaMysqlORM)
- [mysql tutorial](https://www.w3schools.com/mysql/)

# Dependencies
- [java build tool](https://github.com/AlfonsoG-dev/javaBuild)
- [mysql JDBC_8.3.0](https://dev.mysql.com/downloads/connector/j/5.1.html)
- [java_jdk_17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

# Features 

- [x] Use prepared statements.
- [x] Use Annotation base model for migrations. 
- [x] Dynamic class info loading.
- [x] Query generation.

# Additional information.

- The following is a database model.

```java
public class TestModel implements UsableMethods {
    @TableData(constraint = "not null unique primary key auto_increment", type = "int")
    private int id_pk;
    @TableData(constraint = "not null", type = "text")
    private String description;
    @TableData(constraint = "not null unique", type = "varchar(100)")
    private String userName;
    public TestModel() { }

    public int getId_pk() {
        return id_pk;
    }
    public String getDescription() {
        return description;
    }
    public String getUserName() {
        return userName;
    }
}
```
- Its necessary to use the `UsableMethods` because it has the methods to initialize the database and table data.
- And also it carries the instance data.

> For the `TestODM` declaration its only needed the database data no the instance data.
>- In order to get the instance data you need to create another class call `TestODM` that will supply the functionality.

- The following is an instance class or model.

```java
public class TestODM extends TestModel {
    public String description;
    public Stirng userName;

    public TestODM(String description, String userName) {
        super(description, userName);
        this.description = description;
        this.userName = userName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String val) {
        description = val;
    }
    public String getUserName() {
        return userName;
    }
    public void setRol(String val) {
        userName = val;
    }
}
```

- The `ODM` class must declare a *set* methods, this methods will be used to build the model using `java.reflect`.
- This class must declare an empty constructor for the same purpose.
_ Also in this class all *private* attributes will be ignored for the build process, only public members are allowed.

> All of the SELECT type DAO operations returns a list of the generic class because the *UsableMethods* interface use *java.lang.reflect* to build the class instance from the *ResultSet* of the statement execution.

```java
List<T> data = new ArrayList();
T m = null;
while(rst.next()) {
    m = (T) this.getClass().getConstructor().newInstance();
    for(int i=1; i<=length; ++i) {
        String 
            columnName = rst.getMetaData().getColumnName(i),
            value = rst.getString(i);
        /**
         * the instance have an empty constructor, the *buildTest* method invokes all methods that start with 'set' to add data to the instance, the name of the method mus be equal to the rst table column with 'set' at the start. 
         * all set methods receive a value as parameter, the *value form rst is the data from the table row. 
        */
        buildTest(columnName, value, m);
    }
    data.add(m);
}
```

# Samples
- [migrations](./src/Samples/Migration/MigrationSample.java)
- [query](./src/Samples/Query/QuerySample.java)

> models:

- [primary_model](./src/Samples/Models/Primary/TestModel.java)
- [foreign_model](./src/Samples/Models/Foreign/UsersModel.java)

> model instances:

- [ODM models_P](./src/Samples/Models/Primary/TestODM.java)
- [ODM models_F](./src/Samples/Models/Foreign/UsersODM.java)

# How to.

- [password manager](https://github.com/AlfonsoG-dev/gestorPassword)

> the password manager project uses this *ORM* to perform CRUD operations with a MYSQL database.

# Disclaimer

- This project is for educational purposes.
- Security issues are not taken into account.
- It's not intended to create an functional ORM.
