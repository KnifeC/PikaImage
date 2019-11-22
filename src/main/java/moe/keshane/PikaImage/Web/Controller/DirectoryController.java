package moe.keshane.PikaImage.Web.Controller;

import lombok.extern.slf4j.Slf4j;
import moe.keshane.PikaImage.Common.FileKey;
import moe.keshane.PikaImage.Common.KeySet;
import moe.keshane.PikaImage.Exception.PageNotFoundException;
import moe.keshane.PikaImage.Util.FileUtils;
import moe.keshane.PikaImage.Util.SessionUtils;
import moe.keshane.PikaImage.Util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Controller
public class DirectoryController {
    @RequestMapping("/list")
    public String listRoot(HttpServletRequest request, HttpSession session, ModelMap modelMap) {
        String uri = null;
        try {
            uri = URLDecoder.decode(request.getRequestURI(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String username = SessionUtils.getUserNameFromSession(session);
        String userRootPath = FileUtils.getUserRootPath(username);
        List<String> dirList = FileUtils.listDir(userRootPath);
        List<String> fileList = FileUtils.listFile(userRootPath);
        modelMap.put(FileKey.NOWPATH,uri);
        modelMap.put(KeySet.USERNAME, username);
        modelMap.put(FileKey.DIRECTORIES, dirList);
        modelMap.put(FileKey.FILES, fileList);
        return "list";
    }

    @RequestMapping("/list/**")
    public String list(HttpServletRequest request, HttpSession session, ModelMap modelMap, @RequestParam(value = "message")String message) {
        String uri = null;
        try {
            uri = URLDecoder.decode(request.getRequestURI(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String uriP = uri.substring(uri.indexOf("list") + 4, uri.length());
//        log.info("访问的路径去除list为: {}",uriP);
        String username = SessionUtils.getUserNameFromSession(session);
        String uriPath = FileUtils.getPathByUserNameAndUri(username, uriP);
//        log.info("组装好之后的文件路径为: {}",uriPath);
        if (!FileUtils.isExist(uriPath)) {
//            FileUtils.createDirFromPath(uriPath);
            modelMap.put(KeySet.USERNAME, username);
            modelMap.put(KeySet.MESSAGE_TYPE, KeySet.DANGER);
            modelMap.put(KeySet.MESSAGE, "目录不存在");
            throw new PageNotFoundException("目录不存在");
        }
        List<String> dirList = FileUtils.listDir(uriPath);
        List<String> fileList = FileUtils.listFile(uriPath);
        modelMap.put(KeySet.USERNAME, username);
        modelMap.put(FileKey.NOWPATH,uri);
        modelMap.put(FileKey.DIRECTORIES, dirList);
        modelMap.put(FileKey.FILES, fileList);
        return "list";
    }

    @RequestMapping(value = "/directory",method = RequestMethod.POST)
    public String createDirectory(String sourceDir, String targetDirName, HttpSession session, RedirectAttributes redirectAttributes) {
        if(StringUtils.isHasAny(targetDirName,".","/","|","\\",",","`","~","*","^","%","#")){
            return "创建目录失败，包含违规名";
        }
        String uriP = sourceDir.substring(sourceDir.indexOf("list") + 4, sourceDir.length());
        String username = SessionUtils.getUserNameFromSession(session);
        String uriPath = FileUtils.getPathByUserNameAndUri(username, uriP);
        boolean b = FileUtils.createDirFromPath(Paths.get(uriPath,targetDirName).toString());
        if(b){
            return "redirect:"+sourceDir;
        }
        return "创建目录失败，该目录已经存在";
    }

    @RequestMapping(value = "/deletedirectory",method = RequestMethod.POST)
    @ResponseBody
    public String deleteDirectory(String sourceDir,String targetDirName, HttpSession session, RedirectAttributes redirectAttributes) {
        if(StringUtils.isHasAny(targetDirName,".","/","|","\\",",","`","~","*","^","%","#")){
            return "创建目录失败，包含违规名";
        }
        String uriP = sourceDir.substring(sourceDir.indexOf("list") + 4, sourceDir.length());
        String username = SessionUtils.getUserNameFromSession(session);
        String uriPath = FileUtils.getPathByUserNameAndUri(username, uriP);
        boolean b = FileUtils.deleteDirectory(Paths.get(uriPath,targetDirName).toString());
        if(b){
            return "删除目录成功";
        }
        return "删除目录失败";
    }

}
