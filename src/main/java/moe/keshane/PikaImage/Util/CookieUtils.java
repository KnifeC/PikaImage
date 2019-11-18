package moe.keshane.PikaImage.Util;

import moe.keshane.PikaImage.Common.KeySet;
import moe.keshane.PikaImage.Dao.Entity.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
    public static Cookie setDefaultCookie(String cookieKey,String cookieValue){
        Cookie cookie=new Cookie(cookieKey,cookieValue);
        return cookie;
    }

    public static Cookie setUserIdCookie(String userId){
        Cookie cookie = setDefaultCookie(KeySet.UUID, userId);
        cookie.setMaxAge(7*24*60*60);
        cookie.setPath("/");
        return cookie;
    }

    public static void addCookieResponse(HttpServletResponse response,Cookie cookie){
        response.addCookie(cookie);
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
}
