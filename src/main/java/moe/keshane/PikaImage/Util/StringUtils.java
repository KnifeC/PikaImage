package moe.keshane.PikaImage.Util;

public class StringUtils {
    public static boolean isNull(String... strings){
        for(String s:strings){
            if(s==null||s.equals("")){
                return true;
            }
        }
        return false;
    }
}
