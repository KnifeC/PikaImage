package moe.keshane.PikaImage.Util;

import lombok.extern.slf4j.Slf4j;
import moe.keshane.PikaImage.Common.KeySet;
import moe.keshane.PikaImage.Util.Base.BaseCookieUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class UserCookieUtils extends BaseCookieUtils {

    public static boolean setUserIdCookie(HttpServletResponse response, String userId){
        Cookie cookie = setDefaultCookie(KeySet.UUID, userId);
        cookie.setMaxAge(7*24*60*60);
        cookie.setPath("/");
        return addCookieResponse(response,cookie);
    }
    public static boolean deleteUserIdcookie(HttpServletResponse response){
        boolean b = deleteCookie(response, KeySet.UUID, "/");
        return b;
    }
}
