package ORM.Utils.Formats;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ORM.Utils.Model.ModelMetadata;


@SuppressWarnings("unchecked")
public interface UsableMethods {
    private<T> T buildTest(String rstColumnName, String rstValue, T instance) {
        Class<?> c = instance.getClass();
        try {
            Method[] methods = c.getMethods();
            String compare = "set" + rstColumnName.substring(0, 1).toUpperCase().concat(rstColumnName.substring(1));
            for(Method m: methods) {
                if(m.getName().equals(compare)) {
                    for(Class<?> p: m.getParameterTypes()) {
                        if(p == String.class) {
                            m.invoke(instance, rstValue);
                        }
                        if(p == int.class) {
                            m.invoke(instance, Integer.parseInt(rstValue));
                        }
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return instance;
    }
    public default<T> List<T> build(ResultSet rst) {
        List<T> data = new ArrayList<>();
        T m = null;
        try {
            int length = rst.getMetaData().getColumnCount();
            while(rst.next()) {
                m = (T) this.getClass().getConstructor().newInstance();
                for(int i=1; i<=length; ++i) {
                    String 
                        columnName = rst.getMetaData().getColumnName(i),
                        value = rst.getString(i);
                    buildTest(columnName, value, m);
                }
                data.add(m);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    public default String getInstanceData() {
        ModelMetadata metadata = new ModelMetadata(this.getClass());
        return metadata.getInstanceData(this);
    }

    public default String initModel() {
        ModelMetadata metadata = new ModelMetadata(this.getClass());
        return metadata.getProperties();
    }
}
