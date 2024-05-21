package ORM.Utils.Query;

public class QueryUtils {
    public String cleanByType(String type, String query) {
        String b = "";
        switch(type.toLowerCase()) {
            case "and":
                b = query.substring(0, query.length()-4);
                break;
            case "or":
                b = query.substring(0, query.length()-3);
                break;
            case "not":
                b = query.substring(0, query.length()-4);
                break;
        }
        return b;
    }
}
