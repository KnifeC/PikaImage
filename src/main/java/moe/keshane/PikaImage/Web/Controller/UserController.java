package moe.keshane.PikaImage.Web.Controller;

import lombok.extern.slf4j.Slf4j;
import moe.keshane.PikaImage.Common.KeySet;
import moe.keshane.PikaImage.Dao.Entity.User;
import moe.keshane.PikaImage.Service.UserService;
import moe.keshane.PikaImage.Util.MessageUtils;
import moe.keshane.PikaImage.Util.StringUtils;
import moe.keshane.PikaImage.Util.UserCookieUtils;
import moe.keshane.PikaImage.Web.Result.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(String username, String password, String rePassword, ModelMap modelMap,RedirectAttributes redirectAttributes) {
        log.info("Register username : {} password : {} repassword : {}",username,password,rePassword);
        if (StringUtils.isNull(username, password, rePassword)) {
            MessageUtils.sendDangerMessage(modelMap,"输入数据不能为空");
            return "register";
        }
        if (!password.equals(rePassword)) {
            MessageUtils.sendDangerMessage(modelMap,"两次输入的密码不一致");
            return "register";
        }
        User register = userService.register(username, password, rePassword);
        redirectAttributes.addFlashAttribute(KeySet.MESSAGE,new Message("注册成功",KeySet.SUCCESS));
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String username, String password, HttpServletResponse response, ModelMap modelMap,RedirectAttributes redirectAttributes) {
        if (StringUtils.isNull(username, password)) {
            MessageUtils.sendDangerMessage(modelMap,"输入数据不能为空");
            return "login";
        }
        User user = userService.login(username, password);
        UserCookieUtils.setUserIdCookie(response, user.getUserId());
        modelMap.put(KeySet.USERNAME, user.getUsername());
        redirectAttributes.addFlashAttribute(KeySet.MESSAGE,new Message("登陆成功",KeySet.SUCCESS));
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

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap modelMap, HttpServletResponse response, HttpSession session) {
        UserCookieUtils.deleteUserIdcookie(response);
        session.invalidate();
        MessageUtils.sendSuccessMessage(modelMap,"登出成功");
        return "login";
    }
}
