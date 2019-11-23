package moe.keshane.PikaImage.Web.Controller;


import moe.keshane.PikaImage.Common.UserKey;
import moe.keshane.PikaImage.Dao.Entity.User;
import moe.keshane.PikaImage.Service.UserService;
import moe.keshane.PikaImage.Util.StringUtils;
import moe.keshane.PikaImage.Util.UserCookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    UserService userService;

    @RequestMapping(value = {"/", "/index"})
    public String index(HttpServletRequest request, ModelMap modelMap) {
        String uuid = UserCookieUtils.getRequestCookie(request, UserKey.UUID);
        if(StringUtils.isNull(uuid)){
            return "index";
        }
        User user = userService.getUserById(uuid);
        if(user!=null){
            modelMap.put(UserKey.USERNAME,user.getUsername());
        }
        return "index";
    }
}
