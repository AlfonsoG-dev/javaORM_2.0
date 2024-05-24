# Another java ORM(Object Relational Mapping) application

- In this iteration i want to use java.reflect to get the instance data of a model.

# Features 

- [x] Use prepared statements.
- [x] Use Annotation base model for migrations. 
- [x] Dinamyc class info loading.
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

>_ For the `TestODM` declaration its only nedded the database data no the instance data.
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
}
```

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
- [query]()
- [primary_model](./src/Samples/Models/primary/TestModel.java)
- [foreign_model](./src/Samples/Models/Foreign/UsersModel.java)

# Disclaimer

- This project is for educational purposes.
- Security issues are not taken into account.
- It's not intended to create an functional ORM.
