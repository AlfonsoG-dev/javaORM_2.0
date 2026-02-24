package orm.utils.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ModelMetadata {
    private static final String CONSOLE_FORMAT = "%s%n";

    private static final String[] CONSTRAINT_FIELD = {" and "};

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
        return myClass.getDeclaredFields();
    }
    /**
     * model columns are the attributes names
     * @return columns or attributes names
     */
    private String getColumns() {
        StringBuilder rest = new StringBuilder();
        Field[] myFields = getFields();
        if(myFields.length > 0) {
            for(Field f: myFields) {
                rest.append(f.getName());
                rest.append(", ");
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
        StringBuilder constraint = new StringBuilder();
        ArrayList<Annotation[]> misAnnotations = getAnnotations();
        for(Annotation[] m: misAnnotations) {
            constraint.append(m[0]);
            constraint.append(CONSTRAINT_FIELD[0]);
        }
        return constraint.substring(0, constraint.length()-5);
    }
    /**
     * constraint part of model annotations
     */
    private String getColumConstraint() {
        try {
            String[] myConstraint = getAnnotationConstraint().split(CONSTRAINT_FIELD[0]);
            StringBuilder data = new StringBuilder();
            for(String c: myConstraint) {
                String[] f = c.split(".constraint");
                for(String i: f) {
                    String[] ci = i.split(", ");
                    if(ci[0].startsWith("\"") || ci[0].endsWith("\"")) {
                        data.append(ci[0]);
                    }
                }
            }
            String restData  = data.toString().replace("=", ", ");
            return restData.substring(2, restData.length());
        } catch(Exception e) {
            System.console().printf(CONSOLE_FORMAT, e);
            return "";
        }
    }
    private void appendType(String[] constraintValues, StringBuilder data) {
        for(String t: constraintValues) {
            String[] mt = t.split("type=");
            if(mt.length == 2 && (mt[1].startsWith("\"") || mt[1].endsWith(")"))) {
                data.append(mt[1]);
            }
        }
    }
    /**
     * type part of model annotations
     */
    private String getColumnType() {
        try {
            String[] myConstraint = getAnnotationConstraint().split(CONSTRAINT_FIELD[0]);
            StringBuilder data = new StringBuilder();
            for(String c: myConstraint) {
                String[] f = c.split(".constraint");
                for(String i: f) {
                    appendType(i.split(", "), data);
                }
            }
            String restData  = data.toString().replace("\")", "\", ");
            String cleanData = "";
            if((restData.length()-2) > 0) {
                cleanData = restData.substring(0, restData.length()-2);
            }
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
            Class<?> returnType = t.getReturnType();
            if(returnType == String.class) {
                if(t.invoke(instance) != null) {
                    m = t;
                }
            } else if(returnType == int.class && ((int) t.invoke(instance) > 0)) {
                m = t;
            }
        } catch(Exception e) {
            System.console().printf(CONSOLE_FORMAT, e.getLocalizedMessage());
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
        StringBuilder b = new StringBuilder();
        Field[] fields = getFields();
        try {
            for(Field f: fields) {
                String fieldName = f.getName();
                String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Method m = validateValue(methodName, instance);
                if(validateName(methodName) && m != null) {
                    b.append(fieldName);
                    b.append(": ");
                    b.append(m.invoke(instance));
                    b.append("\n");
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return b.toString();
    }
    /**
     * model column: type format.
     */
    public String getProperties() {
        StringBuilder b = new StringBuilder();
        String[] columns = getColumns().split(", ");
        String[] types = getColumnType().split(", ");
        String[] constraint = getColumConstraint().split(", ");
        for(int i=0; i<columns.length; ++i) {
            if(constraint[i].equals("")) {
                b.append(columns[i]);
                b.append(": ");
                b.append(types[i]);
                b.append(" ");
                b.append(constraint[i]);
                b.append("\n");
            }
        }
        return b.toString().replace("\"", "");
    }
}
