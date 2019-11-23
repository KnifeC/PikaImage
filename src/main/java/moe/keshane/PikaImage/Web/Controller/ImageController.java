package moe.keshane.PikaImage.Web.Controller;

import lombok.extern.slf4j.Slf4j;
import moe.keshane.PikaImage.Exception.PageNotFoundException;
import moe.keshane.PikaImage.Util.FileUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;

@Slf4j
@Controller
@ResponseBody
public class ImageController {
    @RequestMapping(value = "/image/**")
    public ResponseEntity<FileSystemResource> image(HttpServletRequest request, HttpServletResponse response) {
        String uri = null;
        try {
            uri = URLDecoder.decode(request.getRequestURI(), "UTF-8");
            uri = uri.substring(uri.indexOf("image") + 5, uri.length());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        log.info("URI:{}", uri);
        String imagePath = FileUtils.getPathByUri(uri);
        log.info("FilePath:{}", imagePath);
        if (FileUtils.isExist(imagePath)) {
            log.info("文件存在1");
            FileSystemResource resource = new FileSystemResource(imagePath);
            String contentType = null;
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            log.info("ContentType:{}", contentType);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        }
        throw new PageNotFoundException("Resource does not exist");
    }
}
