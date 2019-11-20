package moe.keshane.PikaImage.Util;

import moe.keshane.PikaImage.Common.KeySet;
import moe.keshane.PikaImage.Dao.Entity.User;
import moe.keshane.PikaImage.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserCookieUtils extends BaseCookieUtils {
    @Autowired
    static UserService userService;

    public static User getUserFromCookie(HttpServletRequest request){
        String userId = BaseCookieUtils.getRequestCookie(request, KeySet.UUID);
        if (StringUtils.isNull(userId)) {
            return null;
        }
        User user = userService.getUserById(userId);
        if(user == null){
            return null;
        }
        return user;
    }

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
