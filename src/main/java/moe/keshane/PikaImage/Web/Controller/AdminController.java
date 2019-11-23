package moe.keshane.PikaImage.Web.Controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import moe.keshane.PikaImage.Common.BaseUrlKey;
import moe.keshane.PikaImage.Common.MessageKey;
import moe.keshane.PikaImage.Common.UserKey;
import moe.keshane.PikaImage.Dao.Entity.User;
import moe.keshane.PikaImage.Exception.PageNotFoundException;
import moe.keshane.PikaImage.Service.SettingService;
import moe.keshane.PikaImage.Service.UserService;
import moe.keshane.PikaImage.Util.SessionUtils;
import moe.keshane.PikaImage.Util.UserCookieUtils;
import moe.keshane.PikaImage.Web.Result.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    SettingService settingService;

    @RequestMapping(value = "/baseurl", method = RequestMethod.POST)
    public String baseUrl(String baseUrl, HttpServletRequest request, RedirectAttributes redirectAttributes, HttpSession session) {
        String username = SessionUtils.getUserNameFromSession(session);
        String type = SessionUtils.getUserTypeFromSession(session);
        if (type.equals("admin")) {
            settingService.setBaseUrl(baseUrl);
            redirectAttributes.addFlashAttribute(MessageKey.MESSAGE,new Message("修改成功",MessageKey.SUCCESS));
            return "redirect:/admin";
        }
        throw new PageNotFoundException("Resource does not exist");
    }

    @RequestMapping("/admin")
    public String admin(ModelMap modelMap, HttpServletRequest request,HttpSession session) {
        String username = SessionUtils.getUserNameFromSession(session);
        String type = SessionUtils.getUserTypeFromSession(session);
        String baseUrl = settingService.getBaseUrl();
        if(baseUrl!=null){
            modelMap.put(BaseUrlKey.BASEURL,baseUrl);
        }else{
            modelMap.put(BaseUrlKey.BASEURL,"");
        }
        if (type.equals("admin")) {
            modelMap.put(UserKey.USERNAME,username);
            return "admin";
        }
        throw new PageNotFoundException("Resource does not exist");
    }
}
