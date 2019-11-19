package moe.keshane.PikaImage.Controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import moe.keshane.PikaImage.Dao.Entity.User;
import moe.keshane.PikaImage.Exception.DataInputException;
import moe.keshane.PikaImage.Exception.DatabaseException;
import moe.keshane.PikaImage.Exception.UserNotFoundException;
import moe.keshane.PikaImage.Service.UserService;
import moe.keshane.PikaImage.Util.CookieUtils;
import moe.keshane.PikaImage.Util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public String register(String username, String password, String rePassword) {
        try {
            if (StringUtils.nullCheck(username,password,rePassword)) {
                throw new DataInputException("输入数据不能为空");
            }
            User register = userService.register(username, password, rePassword);
            return register.toString();
        } catch (DataInputException die) {
            throw new DataInputException(die.getMessage());
        } catch (DatabaseException de) {
            throw new DatabaseException("注册失败，用户名重复");
        }
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public String login(String username, String password, HttpServletResponse response) {
        try {
            if (StringUtils.nullCheck(username,password)) {
                throw new DataInputException("输入数据不能为空");
            }
            User user = userService.login(username, password);
            CookieUtils.setUserIdCookie(response, user.getUserId());
            return user.toString();
        } catch (DataInputException die) {
            throw new DataInputException(die.getMessage());
        } catch (NullPointerException e) {
            throw new DataInputException("输入数据不能为空");
        } catch (DatabaseException de) {
            throw new DatabaseException(de.getMessage());
        } catch (UserNotFoundException e) {
            throw e;
        }
    }

}
