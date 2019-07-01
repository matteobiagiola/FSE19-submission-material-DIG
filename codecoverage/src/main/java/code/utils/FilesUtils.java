package code.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FilesUtils {

    public static void move(File src, File target){
        Path targetPath = target.toPath();
        Path srcPath = src.toPath();
        try {
            Files.move(srcPath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
