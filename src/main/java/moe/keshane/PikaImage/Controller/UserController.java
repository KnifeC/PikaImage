package moe.keshane.PikaImage.Controller;

import lombok.extern.slf4j.Slf4j;
import moe.keshane.PikaImage.Common.KeySet;
import moe.keshane.PikaImage.Dao.Entity.User;
import moe.keshane.PikaImage.Service.UserService;
import moe.keshane.PikaImage.Util.StringUtils;
import moe.keshane.PikaImage.Util.UserCookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(String username, String password, String rePassword, ModelMap modelMap) {
        log.info("Register username : {} password : {} repassword : {}",username,password,rePassword);
        if (StringUtils.isNull(username, password, rePassword)) {
            modelMap.put("message", "输入数据不能为空");
            modelMap.put("message_type", KeySet.DANGER);
            return "register";
        }
        if (!password.equals(rePassword)) {
            modelMap.put(KeySet.MESSAGE, "输入数据不能为空");
            modelMap.put(KeySet.MESSAGE_TYPE, KeySet.DANGER);
            return "register";
        }
        User register = userService.register(username, password, rePassword);
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String username, String password, HttpServletResponse response, ModelMap modelMap) {
        if (StringUtils.isNull(username, password)) {
            modelMap.put(KeySet.MESSAGE, "输入数据不能为空");
            modelMap.put(KeySet.MESSAGE_TYPE, KeySet.DANGER);
            return "login";
        }
        User user = userService.login(username, password);
        UserCookieUtils.setUserIdCookie(response, user.getUserId());
        modelMap.put(KeySet.USERNAME, user.getUsername());
        return "redirect:/";

    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginpage() {
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerpage() {
        return "register";
    }
}
