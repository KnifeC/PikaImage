package moe.keshane.PikaImage.Controller;

import lombok.extern.slf4j.Slf4j;
import moe.keshane.PikaImage.Common.FileKey;
import moe.keshane.PikaImage.Common.KeySet;
import moe.keshane.PikaImage.Util.FileUtils;
import moe.keshane.PikaImage.Util.SessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
public class FileController {
    @RequestMapping("/list")
    public String listRoot(HttpServletRequest request,HttpSession session, ModelMap modelMap){
        request.getRequestURI();
        String username = SessionUtils.getUserNameFromSession(session);
        String userRootPath = FileUtils.getUserRootPath(username);
        List<String> dirList = FileUtils.listDir(userRootPath);
        List<String> fileList = FileUtils.listFile(userRootPath);
        modelMap.put(KeySet.USERNAME,username);
        modelMap.put(FileKey.DIRECTORIES,dirList);
        modelMap.put(FileKey.FILES,fileList);
        return "list";
    }

    @RequestMapping("/list/**")
    public String list(HttpServletRequest request,HttpSession session, ModelMap modelMap){
        String uri = request.getRequestURI();
        String uriP = uri.substring(uri.indexOf("list")+4,uri.length());
        String username = SessionUtils.getUserNameFromSession(session);
        String uriPath = FileUtils.getUriPath(username, uriP);
        if(!FileUtils.isExist(uriPath)){
//            FileUtils.createDirFromPath(uriPath);
            modelMap.put(KeySet.USERNAME,username);
            modelMap.put(KeySet.MESSAGE_TYPE,KeySet.DANGER);
            modelMap.put(KeySet.MESSAGE,"目录不存在");
            return "list";
        }
        List<String> dirList = FileUtils.listDir(uriPath);
        List<String> fileList = FileUtils.listFile(uriPath);
        modelMap.put(KeySet.USERNAME,username);
        modelMap.put(FileKey.DIRECTORIES,dirList);
        modelMap.put(FileKey.FILES,fileList);
        return "list";

    }

}
