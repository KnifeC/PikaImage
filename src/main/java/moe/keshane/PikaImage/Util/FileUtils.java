package moe.keshane.PikaImage.Util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.system.ApplicationHome;

import java.io.File;

@Slf4j
public class FileUtils {

    private String userPath;
    public FileUtils(String userUuid){
        this.userPath = userUuid;
    }

    public static String getJarPath(){
        ApplicationHome home = new ApplicationHome(FileUtils.class);
        File jarF = home.getSource();
        log.info(jarF.getParentFile().toString());
        return jarF.getParentFile().toString();
    }

}
