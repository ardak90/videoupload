package kz.technodom.videoupload.web.rest.dto;

import kz.technodom.videoupload.domain.VideoFile;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VideoFileDTO {

    public Long id;
    public String uuid;
    public Long size;
    public String mimeType;
    public String downloadUrl;

    public static VideoFileDTO fromVideoFile(VideoFile videoFile){
        VideoFileDTO videoFileDTO = new VideoFileDTO();
        videoFileDTO.id = videoFile.getId();
        videoFileDTO.mimeType = videoFile.getMimeType();
        videoFileDTO.size = videoFile.getSize();
        videoFileDTO.uuid = videoFile.getUuid();
        return videoFileDTO;
    }

    public static LocalDateTime stringToLocalDateTime(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(date, formatter);
    }
}
