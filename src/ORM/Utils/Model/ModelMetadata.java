package ORM.Utils.Model;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ModelMetadata {
    /**
     * model or table name
     */
    private Class<?> myClass;
    /**
     * {@link java.lang.reflect.Constructor}
     * this one is created using model name as {@link String}
     */
    public ModelMetadata(String nModelName) {
        try {
            myClass = Class.forName(nModelName);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * {@link java.lang.reflect.Constructor}
     * this one is created using class reflect
     */
    public ModelMetadata(Class<?> model) {
        myClass = model;
    }
    /**
     * list declare class fields.
     * @throws ClassNotFoundException: error while trying to get class for name
     * @return list of class fields.
     */
    private Field[] getFields() {
        Field[] myFields = myClass.getDeclaredFields();
        return myFields;
    }
    /**
     * model columns are the attributes names
     * @return columns or attributes names
     */
    private String getColumns() {
        StringBuffer rest = new StringBuffer();
        Field[] myFields = getFields();
        if(myFields.length > 0) {
            for(Field f: myFields) {
                rest.append(f.getName() + ", ");
            }
        }
        return rest.substring(0, rest.length()-2);
    }
    /**
     * list of class annotations.
     */
    private ArrayList<Annotation[]> getAnnotations() {
        ArrayList<Annotation[]> rest = new ArrayList<>();
        Field[] myFields = getFields();
        if(myFields.length > 0) {
            for(Field f: myFields) {
                if(f.getAnnotations().length > 0) {
                    rest.add(f.getAnnotations());
                }
            }
        }
        return rest;
    }
    private String getAnnotationConstraint() {
        StringBuffer constraint = new StringBuffer();
        ArrayList<Annotation[]> misAnnotations = getAnnotations();
        for(Annotation[] m: misAnnotations) {
            constraint.append(m[0] + " and ");
        }
        return constraint.substring(0, constraint.length()-5);
    }
    /**
     * constraint part of model annotations
     */
    private String getColumConstraint() {
        try {
            String[] myConstraint = getAnnotationConstraint().split(" and ");
            StringBuffer data = new StringBuffer();
            for(String c: myConstraint) {
                String[] f = c.split(".miConstraint");
                for(String i: f) {
                    String[] ci = i.split(", ");
                    if(ci[0].startsWith("\"") || ci[0].endsWith("\"")) {
                        data.append(ci[0]);
                    }
                }
            }
            String 
                restData  = data.toString().replace("=", ", "),
                cleanData = restData.substring(2, restData.length());
            return cleanData;
        } catch(Exception e) {
            System.err.println(e);
            return "";
        }
    }
    /**
     * type part of model annotations
     */
    private String getColumnType() {
        try {
            String[] myConstraint = getAnnotationConstraint().split(" and ");
            StringBuffer data = new StringBuffer();
            for(String c: myConstraint) {
                String[] f = c.split(".miConstraint");
                for(String i: f) {
                    String[] ci = i.split(", ");
                    for(String t: ci) {
                        String[] mt = t.split("miType=");
                        if(mt.length == 2) {
                            if(mt[1].startsWith("\"") || mt[1].endsWith(")")) {
                                data.append(mt[1]);
                            }
                        }
                    }
                }
            }
            String 
                restData  = data.toString().replace("\")", "\", "),
                cleanData = restData.substring(0, restData.length()-2);
            return cleanData;
        } catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    private<T> Method validateValue(String methodName, T instance) {
        Method m = null;
        try {
            Method t = myClass.getMethod(methodName);
            if(t.invoke(instance) != null) {
                m = t;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return m;
    }
    private boolean validateName(String methodName) {
        boolean isValid = false;
        Method[] methods = myClass.getMethods();
        for(Method m: methods) {
            if(m.getName().equals(methodName)) {
                isValid = true;
            }
        }
        return isValid;
    }
    public<T> String getInstanceData(T instance) {
        String b = "";
        Field[] fields = getFields();
        try {
            for(Field f: fields) {
                String fieldName = f.getName();
                String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Method m = validateValue(methodName, instance);
                if(validateName(methodName) && m != null) {
                    b += fieldName + ": " + m.invoke(instance) + "\n";
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return b;
    }
    /**
     * model column: type format.
     */
    public String getProperties() {
        StringBuffer build = new StringBuffer();
        String[] 
            columns    = getColumns().split(", "),
            types      = getColumnType().split(", "),
            constraint = getColumConstraint().split(", ");
        for(int i=0; i<columns.length; ++i) {
            if(constraint[i] != "") {
                build.append(
                        columns[i] + ": " + types[i] + " " + constraint[i] + "\n"
                );
            }
        }
        return build.toString().replace("\"", "");
    }
}
