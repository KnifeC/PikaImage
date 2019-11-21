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
    public static boolean isHasAny(String source,String... checkArgs){
        for(String s : checkArgs){
            if(source.indexOf(s)!=-1){
                return true;
            }
        }
        return false;
    }
}
