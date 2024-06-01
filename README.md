# Another java ORM(Object Relational Mapping) application

- In this iteration i want to use java.reflect to get the instance data of a model.

>_ For this purpose is necessary to use 2 classes, one if used as a table model, the other one is used to perform CRUD operations and it needs to have in its name the ODM sentence at the end.

>>_ Also the model can be use for SELECT operation and the ODM class is used for INSERT, UPDATE, DELETE operations.

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

>_ The following is a database model.
```java
public class TestModel implements UsableMethods {
    @TableData(constraint = "not null unique primary key auto_increment", type = "int")
    private int id_pk;
    @TableData(constraint = "not null", type = "text")
    private String description;
    @TableData(constraint = "not null unique", type = "varchar(100)")
    private String userName;
    
    public TestModel(String description, String userName) {
        this.description = description;
        this.userName = userName;
    }
}
```
- Its necessary to use the `UsableMethods` because it has the methods to initialize the database and table data.
- And also it carries the instance data.

>_ For the `TestODM` declaration its only needed the database data no the instance data.
- In order to get the instance data you need to create another class call `TestODM` that will supply the functionality.

>_ The following is an instance class or model.

```java
public class TestODM extends TestModel {
    public String description;
    public Stirng userName;

    public TestODM(String description, String userName) {
        super(description, userName);
        this.description = description;
        this.userName = userName;
    }
    public void setDescription(String val) {
    }
    public void setRol(String val) {
    }
}
```

>- The `ODM` class must declare a *set* methods, this methods will be used to build the model using `java.reflect`.
>- This class must declare an empty constructor for the same purpose.


- This is the `UsableMethods` interface declaration
```java
public interface UsableMethods {

    /**
    * This methods use java.reflect to invoke all the methods that starts with getMethodName.
    * The model also will have to declare the attributes with public scope.
    * All the attributes that are declare with private scope will be ignored.
    * The ODM class will be use for this.
    */
    public default String getInstanceData() {
        ModelMetadata metadata = new ModelMetadata(this.getClass());
        return metadata.getInstanceData(this);
    }

    /**
    * this method use java.reflect to get the Annotation data of all the attributes.
    * The class that is use as model only declare private fields and the public field is a constructor used for the ODM class.
    * the model class will be used for this.
    */
    public default String initModel() {
        ModelMetadata metadata = new ModelMetadata(this.getClass());
        return metadata.getProperties();
    }
}
```

# Samples
- [migrations](./src/Samples/Migration/MigrationSample.java)
- [query](./src/Samples/Query/QuerySample.java)

>_ models:

- [primary_model](./src/Samples/Models/Primary/TestModel.java)
- [foreign_model](./src/Samples/Models/Foreign/UsersModel.java)

>_ model instances:

- [ODM models_P](./src/Samples/Models/Primary/TestODM.java)
- [ODM models_F](./src/Samples/Models/Foreign/UsersODM.java)

# How to.
- [password manager](https://github.com/AlfonsoG-dev/gestorPassword)

>_ the password manager project uses this *ORM* to perform CRUD operations with a MYSQL database.

# Disclaimer

- This project is for educational purposes.
- Security issues are not taken into account.
- It's not intended to create an functional ORM.
