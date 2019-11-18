package moe.keshane.PikaImage.Config;

import moe.keshane.PikaImage.Common.KeySet;
import moe.keshane.PikaImage.Dao.Entity.User;
import moe.keshane.PikaImage.Service.UserService;
import moe.keshane.PikaImage.Util.CookieUtils;
import moe.keshane.PikaImage.Util.SessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Key;

public class LoginIntercepter implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = CookieUtils.getRequestCookie(request, KeySet.UUID);
        if(userId==null||userId.equals("")) {
            request.setAttribute(KeySet.MESSAGE,"该操作需要登陆");
            response.sendRedirect("/login");
            return false;
        }
        User user = userService.getUserById(userId);
        if(user == null){
            request.setAttribute(KeySet.MESSAGE,"用户名或密码错误");
            response.sendRedirect("/login");
            return false;
        }
        HttpSession session = request.getSession();
        SessionUtils.setSessionData(session,KeySet.USERID,user.getUserId());
        SessionUtils.setSessionData(session,KeySet.USERNAME,user.getUsername());
        SessionUtils.setSessionData(session,KeySet.USERTYPE,user.getType());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
