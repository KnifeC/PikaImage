package moe.keshane.PikaImage.Config;

import moe.keshane.PikaImage.Common.KeySet;
import moe.keshane.PikaImage.Dao.Entity.User;
import moe.keshane.PikaImage.Service.UserService;
import moe.keshane.PikaImage.Util.SessionUtils;
import moe.keshane.PikaImage.Util.StringUtils;
import moe.keshane.PikaImage.Util.UserCookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginIntercepter implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uuid = UserCookieUtils.getRequestCookie(request, KeySet.UUID);
        if(StringUtils.isNull(uuid)){
            request.setAttribute(KeySet.MESSAGE,"该操作需要登陆");
            request.setAttribute(KeySet.MESSAGE_TYPE,KeySet.WARNING);
            response.sendRedirect("/login");
            return false;
        }
        User user = userService.getUserById(uuid);
        if(user == null){
            request.setAttribute(KeySet.MESSAGE,"该操作需要登陆");
            request.setAttribute(KeySet.MESSAGE_TYPE,KeySet.WARNING);
            response.sendRedirect("/login");
            return false;
        }
        request.setAttribute(KeySet.USERNAME,user.getUsername());
        SessionUtils.setSessionData(request.getSession(),KeySet.USERNAME,user.getUsername());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
