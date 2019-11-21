package moe.keshane.PikaImage.Web.Controller;

import lombok.extern.slf4j.Slf4j;
import moe.keshane.PikaImage.Common.FileKey;
import moe.keshane.PikaImage.Common.KeySet;
import moe.keshane.PikaImage.Util.FileUtils;
import moe.keshane.PikaImage.Util.SessionUtils;
import moe.keshane.PikaImage.Util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
public class DirectoryController {
    @RequestMapping("/list")
    public String listRoot(HttpServletRequest request, HttpSession session, ModelMap modelMap) {
        request.getRequestURI();
        String username = SessionUtils.getUserNameFromSession(session);
        String userRootPath = FileUtils.getUserRootPath(username);
        List<String> dirList = FileUtils.listDir(userRootPath);
        List<String> fileList = FileUtils.listFile(userRootPath);
        modelMap.put(KeySet.USERNAME, username);
        modelMap.put(FileKey.DIRECTORIES, dirList);
        modelMap.put(FileKey.FILES, fileList);
        return "list";
    }

    @RequestMapping("/list/**")
    public String list(HttpServletRequest request, HttpSession session, ModelMap modelMap) {
        String uri = request.getRequestURI();
        String uriP = uri.substring(uri.indexOf("list") + 4, uri.length());
        String username = SessionUtils.getUserNameFromSession(session);
        String uriPath = FileUtils.getPathByUserNameAndUri(username, uriP);
        if (!FileUtils.isExist(uriPath)) {
//            FileUtils.createDirFromPath(uriPath);
            modelMap.put(KeySet.USERNAME, username);
            modelMap.put(KeySet.MESSAGE_TYPE, KeySet.DANGER);
            modelMap.put(KeySet.MESSAGE, "目录不存在");
            return "list";
        }
        List<String> dirList = FileUtils.listDir(uriPath);
        List<String> fileList = FileUtils.listFile(uriPath);
        modelMap.put(KeySet.USERNAME, username);
        modelMap.put(FileKey.DIRECTORIES, dirList);
        modelMap.put(FileKey.FILES, fileList);
        return "list";
    }

    @RequestMapping(value = "/directory",method = RequestMethod.POST)
    @ResponseBody
    public String createDirectory(String directoryname, HttpServletRequest request, HttpSession session) {
        if(StringUtils.isHasAny(directoryname,".","/","|","\\",",","`","~","*","^","%","#")){
            return "创建目录失败，包含违规名";
        }
        String uri = request.getRequestURI();
        String uriP = uri.substring(uri.indexOf("list") + 4, uri.length());
        String username = SessionUtils.getUserNameFromSession(session);
        String uriPath = FileUtils.getPathByUserNameAndUri(username, uriP);
        boolean b = FileUtils.createDirFromPath(uriPath + "/"+directoryname);
        if(b){
            return "创建目录成功";
        }
        return "创建目录失败，该目录已经存在";
    }

}
