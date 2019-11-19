package moe.keshane.PikaImage.Util;

public class StringUtils {
    public static boolean nullCheck(String... strings){
        for(String s:strings){
            if(s==null||s.equals("")){
                return false;
            }
        }
        return true;
    }
}
