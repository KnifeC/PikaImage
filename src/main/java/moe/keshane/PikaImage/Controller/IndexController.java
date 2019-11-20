package moe.keshane.PikaImage.Controller;


import com.sun.media.sound.ModelPatch;
import moe.keshane.PikaImage.Common.KeySet;
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
        String uuid = UserCookieUtils.getRequestCookie(request, KeySet.UUID);
        if(StringUtils.isNull(uuid)){
            return "index";
        }
        User user = userService.getUserById(uuid);
        if(user!=null){
            modelMap.put(KeySet.USERNAME,user.getUsername());
        }
        return "index";
    }
}
