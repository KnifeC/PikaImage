package moe.keshane.PikaImage.Controller;


import moe.keshane.PikaImage.Common.KeySet;
import moe.keshane.PikaImage.Dao.Entity.User;
import moe.keshane.PikaImage.Service.UserService;
import moe.keshane.PikaImage.Util.UserCookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    UserService userService;

    @RequestMapping(value = {"/", "/index"})
    public String index(HttpServletRequest request) {
        User user = UserCookieUtils.getUserFromCookie(request);
        if(user!=null){
            request.setAttribute(KeySet.USERNAME,user.getUsername());
        }
        return "index";
    }
}
