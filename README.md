# Another java ORM(Object Relational Mapping) application
- In this iteration i want to use java.reflect to get the instance data of a model.

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

    @Override
    public String getInstanceData() {
        throw new UnsupportedOperationException(
                "[ ERROR ]: this class is only for declaring database info"
        );
    }
}
```
- Its necessary to use the `UsableMethods` because it has the methods to initialize the database and table data.
- Also it can carry the instance data.

>_ For the `TestModel` declaration its only nedded the database data no the instance data.
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
    /**
    * this method is replacing the TestModel
    */
    @Override
    public String getInstanceData() {
        ModelMetadata metadata = new ModelMetadata(TestODM.class);
        return metadata.getInstanceData(this);
    }
}
```
