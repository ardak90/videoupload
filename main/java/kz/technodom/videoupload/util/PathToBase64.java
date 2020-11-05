package kz.technodom.videoupload.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * Utility to convert Video to Base64 and reverse.
 */

public class PathToBase64 {

    public static String convert(String path) throws IOException {
        File file = Paths.get(path).toFile();
        return Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(file.toPath()));
    }

    public static File revert(String base, String url, String uuid, String mimeType) throws IOException {
        byte[] data = Base64.getDecoder().decode(base);
        String newfile = url + "/" + uuid + "." + mimeType;
        try (OutputStream stream = new FileOutputStream(newfile)) {
            stream.write(data);
        }
        return new File(newfile);
    }

}
