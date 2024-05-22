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

    @Override
    public String getInstanceData() {
        throw new UnsupportedOperationException(
                "[ ERROR ]: this class is only for declaring database info"
        );
    }
}
```
- Its necessary to use the `UsableMethods`
