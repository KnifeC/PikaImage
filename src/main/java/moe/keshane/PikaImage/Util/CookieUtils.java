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

    public static boolean setUserIdCookie(HttpServletResponse response,String userId){
        Cookie cookie = setDefaultCookie(KeySet.UUID, userId);
        cookie.setMaxAge(7*24*60*60);
        cookie.setPath("/");
        return addCookieResponse(response,cookie);
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

    public static boolean deleteUserIdcookie(HttpServletResponse response){
        boolean b = deleteCookie(response, KeySet.UUID, "/");
        return b;
    }

}
