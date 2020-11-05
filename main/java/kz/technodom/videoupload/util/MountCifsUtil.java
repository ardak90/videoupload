package kz.technodom.videoupload.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MountCifsUtil {

    private static final Logger log = LoggerFactory.getLogger(MountCifsUtil.class);

    public static String runCommand(List<String> cmdList){
        StringBuilder builder = new StringBuilder();
        log.info("[SEND FILE TO STAND] command is: " + Arrays.toString(cmdList.toArray()));
        ProcessBuilder pb = new ProcessBuilder(cmdList);
        pb.redirectErrorStream(true);
        try {
            Process process = pb.start();
            process.waitFor();
            process.getInputStream();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    builder.append(line + System.getProperty("line.separator"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String output = builder.toString();
        log.info("[MOUNT RESPONSE]:", output);

        return output.replaceAll("\\s","Z");
    }

    public static String mount(String ip, String srcFolder, String rootPath, String rootFolder, String ADLogin, String ADPassword){
        List<String> cmdList = new ArrayList<>();
 //       cmdList.add("sudo");
        cmdList.add("mount");
        cmdList.add("-t");
        cmdList.add("cifs");
        cmdList.add("//"+ip+"/"+srcFolder+"/");
        cmdList.add(rootFolder);
        cmdList.add("-o");
        cmdList.add("username="+ADLogin+",password="+ADPassword+",vers=3.0");
        return runCommand(cmdList).trim();
    }

    public static void unmount(String rootPath, String rootFolder){
        List<String> cmdList = new ArrayList<>();
   //     cmdList.add("sudo");
        cmdList.add("umount");
        cmdList.add(rootFolder);
        runCommand(cmdList);
    }

    public static boolean copy(String source, String destination){
        Path sourceFile = Paths.get(source);
        Path targetFile = Paths.get(destination);
        try {

            Files.copy(sourceFile, targetFile,
                StandardCopyOption.REPLACE_EXISTING);
            log.info("[SEND FILE TO STAND]: File successfully sent to stand");
            return true;
        } catch (IOException ex) {
            log.error("[SEND FILE TO STAND]: I/O Error when copying file");
            return false;
        }
    }

    public static void removeFile(String rootPath, String rootFolder, String filename){
        List<String> cmdList = new ArrayList<>();
    //    cmdList.add("sudo");
        cmdList.add("rm");
        cmdList.add(rootFolder+"/"+filename);
        runCommand(cmdList);
    }
}
