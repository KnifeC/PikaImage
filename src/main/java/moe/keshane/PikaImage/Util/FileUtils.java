package moe.keshane.PikaImage.Util;

import lombok.extern.slf4j.Slf4j;
import moe.keshane.PikaImage.Common.FileKey;
import moe.keshane.PikaImage.Exception.DirectoryAleadyExistException;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class FileUtils {

    public static boolean movaDirectory(String source,String target){
        boolean b = copyDirectory(source,target);
        if(b){
            return deleteDirectory(source);
        }
        return false;
    }

    public static boolean copyDirectory(String source,String target){
        try {
            FileSystemUtils.copyRecursively(Paths.get(source),Paths.get(target));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteDirectory(String dirPath){
        log.info("获得删除路径: {} ",dirPath);
        boolean b = FileSystemUtils.deleteRecursively(Paths.get(dirPath).toFile());
        return b;
    }

    public static boolean isExist(String dirPath){
        if(!Files.exists(Paths.get(dirPath),new LinkOption[]{ LinkOption.NOFOLLOW_LINKS})){
            return false;
        }
        return true;
    }

    public static boolean createDirFromPath(String dirPath){
        if(!Files.exists(Paths.get(dirPath),new LinkOption[]{ LinkOption.NOFOLLOW_LINKS})){
            try {
                Path path = Files.createDirectories(Paths.get(dirPath));
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static String getPathByUri(String uri){
        return Paths.get(getJarPath().toString(), FileKey.FILEROOT,FileKey.IMAGESTORAGE,uri).toString();
    }

    public static String getPathByUserNameAndUri(String userName,String uri){
        String userRootPath = getUserRootPath(userName);
        Path path = Paths.get(userRootPath,uri);
        return path.toString();
    }

    public static String getUserRootPath(String userName){
        Path userRootPath = Paths.get(getJarPath().toString(), FileKey.FILEROOT,FileKey.IMAGESTORAGE,userName);
//        log.info(userRootPath.toString());
        if(!Files.exists(userRootPath,new LinkOption[]{ LinkOption.NOFOLLOW_LINKS})){
            try {
                Path path = Files.createDirectories(userRootPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return userRootPath.toString();
    }

    public static List<String> listDir(String targetPath){
        return Arrays.stream(new File(targetPath).listFiles())
                .filter(File::isDirectory)
                .map(f->f.getName())
                .map(f->String.valueOf(f))
                .collect(Collectors.toList());
    }

    public static List<String> listFile(String targetPath){
        return Arrays.stream(new File(targetPath).listFiles())
                .filter(File::isFile)
                .map(f->f.getName())
                .map(f->f.toString())
                .collect(Collectors.toList());
    }

    public static Path getJarPath() {
        ApplicationHome home = new ApplicationHome(FileUtils.class);
        File jarPath = home.getDir();
        return jarPath.toPath();
    }

}
