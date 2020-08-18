package util;

public class Regex {

    public boolean isText(String s){
        return s.matches("^[a-zA-Z\\u00C0-\\u00FF]*$");
    }

    public boolean isDouble(String s){
        return s.matches("\\d+\\.\\d{0,2}") || s.matches("\\.\\d{0,2}") || s.matches("[0-9]+");
    }

    public boolean isInt(String s){
        return s.matches("\\d+");
    }
}
