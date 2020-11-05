package kz.technodom.videoupload.util;

public class FileUploadUtil {

    public static boolean isValidImageFile(String contentType) {
        if (!(contentType.equals("image/pjpeg") || (contentType.equals("image/jpg") ||contentType.equals("image/jpeg") || contentType.equals("image/png")
            || contentType.equals("image/gif") || contentType.equals("image/bmp")
            || contentType.equals("image/x-png") || contentType.equals("image/x-icon")))) {
            return false;
        }
        return true;
    }

    public static boolean isFormatAvi(String contentType){
        if(contentType.equals("video/avi")||
            contentType.equals("video/msvideo")||
            contentType.equals("video/x-msvideo")||
            contentType.equals("image/avi")||
            contentType.equals("video/xmpg2")||
            contentType.equals("application/x-troff-msvideo")){
            return true;
        }
        return false;
    }

    public static boolean isVideoFormat(String contentType){
        if( contentType.equals("")){

        }
        return false;
    }

}
