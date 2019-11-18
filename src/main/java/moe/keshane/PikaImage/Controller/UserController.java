package moe.keshane.PikaImage.Controller;

import lombok.extern.slf4j.Slf4j;
import moe.keshane.PikaImage.Dao.Entity.User;
import moe.keshane.PikaImage.Form.UserForm;
import moe.keshane.PikaImage.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "register",method = RequestMethod.POST)
    @ResponseBody
    public String register(UserForm userForm){
        User register = userService.register(userForm.getUsername(), userForm.getPassword(), userForm.getRepassword());
        return register.toString();
    }

}
