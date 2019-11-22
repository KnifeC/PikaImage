package moe.keshane.PikaImage.Web.Controller;

import lombok.extern.slf4j.Slf4j;
import moe.keshane.PikaImage.Common.FileKey;
import moe.keshane.PikaImage.Common.KeySet;
import moe.keshane.PikaImage.Exception.PageNotFoundException;
import moe.keshane.PikaImage.Util.FileUtils;
import moe.keshane.PikaImage.Util.MessageUtils;
import moe.keshane.PikaImage.Util.SessionUtils;
import moe.keshane.PikaImage.Util.StringUtils;
import moe.keshane.PikaImage.Web.Result.FileNamePath;
import moe.keshane.PikaImage.Web.Result.Message;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class DirectoryController {
    @RequestMapping("/list")
    public String listRoot(HttpServletRequest request, HttpSession session, ModelMap modelMap) {
        String uri = null;
        try {
            uri = URLDecoder.decode(request.getRequestURI(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String username = SessionUtils.getUserNameFromSession(session);
        String userRootPath = FileUtils.getUserRootPath(username);
        List<String> dirList = FileUtils.listDir(userRootPath);
        List<String> fileList = FileUtils.listFile(userRootPath);
        List<FileNamePath> fileNamePaths = new ArrayList<>();
        for(String file : fileList){
            String imageUrl = "/image/"+username+"/"+file;
//            log.info("filename : {} , URL : {}",file,imageUrl);
            fileNamePaths.add(new FileNamePath(file,imageUrl));
        }
        modelMap.put(KeySet.USERNAME, username);
        modelMap.put(FileKey.NOWPATH, uri);
        modelMap.put(FileKey.DIRECTORIES, dirList);
        modelMap.put(FileKey.FILES, fileNamePaths);
        return "list";
    }

    @RequestMapping("/list/**")
    public String list(HttpServletRequest request, HttpSession session, ModelMap modelMap) {
        String uri = null;
        try {
            uri = URLDecoder.decode(request.getRequestURI(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String uriP = uri.substring(uri.indexOf("list") + 4, uri.length());
        String username = SessionUtils.getUserNameFromSession(session);
        String uriPath = FileUtils.getPathByUserNameAndUri(username, uriP);
        if (!FileUtils.isExist(uriPath)) {
            MessageUtils.sendDangerMessage(modelMap, "目录不存在");
            throw new PageNotFoundException("目录不存在");
        }
        List<String> dirList = FileUtils.listDir(uriPath);
        List<String> fileList = FileUtils.listFile(uriPath);
        List<FileNamePath> fileNamePaths = new ArrayList<>();
        for(String file : fileList){
            String imageUrl = "/image/"+username+"/"+uriP+"/"+file;
//            log.info("filename : {} , URL : {}",file,imageUrl);
            fileNamePaths.add(new FileNamePath(file,imageUrl));
        }
        modelMap.put(KeySet.USERNAME, username);
        modelMap.put(FileKey.NOWPATH, uri);
        modelMap.put(FileKey.DIRECTORIES, dirList);
        modelMap.put(FileKey.FILES, fileNamePaths);
        return "list";
    }

    //create file or directory
    @RequestMapping(value = "/directory", method = RequestMethod.POST)
    public String createDirectory(String sourceDir, String targetDirName, HttpSession session, RedirectAttributes redirectAttributes) {
        if (StringUtils.isNull(sourceDir, targetDirName)) {
            redirectAttributes.addFlashAttribute("message", new Message("输入数据不能为空", KeySet.WARNING));
            return "redirect:list";
        }
        if (StringUtils.isHasAny(targetDirName, ".", "/", "|", "\\", ",", "`", "~", "*", "^", "%", "#")) {
            redirectAttributes.addFlashAttribute("message", new Message("创建失败，包含违规名", KeySet.WARNING));
            return "redirect:" + sourceDir;
        }
//        log.info("数据校验完成");
        String uriP = sourceDir.substring(sourceDir.indexOf("list") + 4, sourceDir.length());
        String username = SessionUtils.getUserNameFromSession(session);
        String uriPath = FileUtils.getPathByUserNameAndUri(username, uriP);
        boolean b = FileUtils.createDirFromPath(Paths.get(uriPath, targetDirName).toString());
        if (b) {
//            log.info("创建成功: {}", uriPath);
            redirectAttributes.addFlashAttribute("message", new Message("创建成功", KeySet.SUCCESS));
            return "redirect:" + sourceDir;
        }
        redirectAttributes.addFlashAttribute("message", new Message("创建失败，目录已存在", KeySet.WARNING));
//        log.info("重定向DIR：{}", sourceDir);
        return "redirect:" + sourceDir;
    }

    //delete file or directory
    @RequestMapping(value = "/deletedirectory", method = RequestMethod.POST)
    public String deleteDirectory(String sourceDir, String targetDirName, HttpSession session, RedirectAttributes redirectAttributes) {
        if (StringUtils.isHasAny(targetDirName, "/", "|", "\\", ",", "`", "~", "*", "^", "%", "#")) {
            redirectAttributes.addFlashAttribute("message", new Message("删除失败", KeySet.WARNING));
            return "redirect:" + sourceDir;
        }
        String uriP = sourceDir.substring(sourceDir.indexOf("list") + 4, sourceDir.length());
        String username = SessionUtils.getUserNameFromSession(session);
        String uriPath = FileUtils.getPathByUserNameAndUri(username, uriP);
//        log.info("获得删除目录：{}", uriPath);
        boolean b = FileUtils.deleteDirectory(Paths.get(uriPath, targetDirName).toString());
        if (b) {
//            log.info("删除成功信息！！！");
            redirectAttributes.addFlashAttribute("message", new Message("删除成功", KeySet.SUCCESS));
//            log.info("重定向DIR：{}", sourceDir);
            return "redirect:" + sourceDir;
        }
        redirectAttributes.addFlashAttribute("message", new Message("删除失败", KeySet.WARNING));
        return "redirect:" + sourceDir;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadImage(String sourceDir, MultipartFile[] imageFile, HttpSession session,RedirectAttributes redirectAttributes) {
        String uriP = sourceDir.substring(sourceDir.indexOf("list") + 4, sourceDir.length());
        String username = SessionUtils.getUserNameFromSession(session);
        String uriPath = FileUtils.getPathByUserNameAndUri(username, uriP);
        boolean b = FileUtils.saveAllFile(uriPath, imageFile);
        if(b){
            redirectAttributes.addFlashAttribute("message", new Message("上传成功", KeySet.SUCCESS));
            return "redirect:" + sourceDir;
        }
        redirectAttributes.addFlashAttribute("message", new Message("上传失败", KeySet.DANGER));
        return "redirect:" + sourceDir;
    }

}
