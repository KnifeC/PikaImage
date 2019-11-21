package moe.keshane.PikaImage.Util.Base;

import moe.keshane.PikaImage.Common.KeySet;
import moe.keshane.PikaImage.Dao.Entity.User;
import moe.keshane.PikaImage.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseCookieUtils {

    public static Cookie setDefaultCookie(String cookieKey,String cookieValue){
        Cookie cookie=new Cookie(cookieKey,cookieValue);
        return cookie;
    }

    public static boolean addCookieResponse(HttpServletResponse response,Cookie cookie){
        response.addCookie(cookie);
        return true;
    }

    public static String getRequestCookie(HttpServletRequest request,String cookieKey){
        Cookie[] cookies =  request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(cookieKey)){
                    return cookie.getValue();
                }
            }
        }
        return  null;
    }

    public static boolean deleteCookie(HttpServletResponse response,String cookieKey,String path){
        Cookie cookie = new Cookie(cookieKey, null);
        cookie.setMaxAge(0);
        cookie.setPath(path);
        response.addCookie(cookie);
        return true;
    }

}
