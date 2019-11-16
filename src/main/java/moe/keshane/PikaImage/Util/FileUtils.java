package moe.keshane.PikaImage.Util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

@Slf4j
public class FileUtils {

    private String userPath;
    public FileUtils(String userUuid){
        this.userPath = userUuid;
    }

    public static String getJarPath() {
        ApplicationHome home = new ApplicationHome(FileUtils.class);
        File jarPath = home.getDir();
        log.info("Jar file root path : {}",jarPath.toString());
        return jarPath.toString();
    }

}
