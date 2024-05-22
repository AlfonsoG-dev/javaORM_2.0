package ORM.Utils.Model;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class ModelMetadata {
    /**
     * model or table name
     */
    private String modelName;
    /**
     * {@link java.lang.reflect.Constructor}
     * this one is created using model name as {@link String}
     */
    public ModelMetadata(String nModelName) {
        modelName = nModelName;
    }
    /**
     * {@link java.lang.reflect.Constructor}
     * this one is created using class reflect
     */
    public ModelMetadata(Class<?> model) {
        modelName = model.getName();
    }
    /**
     * list declare class fields.
     * @throws ClassNotFoundException: error while trying to get class for name
     * @return list of class fields.
     */
    private Field[] getFields() throws ClassNotFoundException {
        Class<?> myClass = Class.forName(modelName);
        Field[] myFields = myClass.getDeclaredFields();
        return myFields;
    }
    /**
     * model columns are the attributes names
     * @return columns or attributes names
     */
    private String getColumns() {
        StringBuffer rest = new StringBuffer();
        try {
            Field[] myFields = getFields();
            if(myFields.length > 0) {
                for(Field f: myFields) {
                    rest.append(f.getName() + ", ");
                }
            }
        } catch(ClassNotFoundException e) {
            System.err.println(e);
        }
        return rest.substring(0, rest.length()-2);
    }
    /**
     * list of class annotations.
     */
    private ArrayList<Annotation[]> getAnnotations() {
        ArrayList<Annotation[]> rest = new ArrayList<>();
        try {
            Field[] myFields = getFields();
            if(myFields.length > 0) {
                for(Field f: myFields) {
                    if(f.getAnnotations().length > 0) {
                        rest.add(f.getAnnotations());
                    }
                }
            }
        } catch(ClassNotFoundException e) {
            System.err.println(e);
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
            System.err.println(e);
            return "";
        }
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
