package kz.technodom.videoupload.web.rest;

import kz.technodom.videoupload.domain.VideoFile;
import kz.technodom.videoupload.service.StandService;
import kz.technodom.videoupload.service.VideoFileService;
import kz.technodom.videoupload.util.FileUploadUtil;
import kz.technodom.videoupload.web.rest.dto.VideoFileDTO;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import kz.technodom.videoupload.web.rest.exceptions.DateNotProvided;
import kz.technodom.videoupload.web.rest.exceptions.VideoFileFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link kz.technodom.videoupload.domain.VideoFile}.
 */
@RestController
@RequestMapping("/api")
public class VideoFileResource {

    private final Logger log = LoggerFactory.getLogger(VideoFileResource.class);

    private static final String ENTITY_NAME = "videoFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VideoFileService videoFileService;

    private final StandService standService;

    public VideoFileResource(VideoFileService videoFileService, StandService standService) {
        this.videoFileService = videoFileService;
        this.standService = standService;
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<VideoFileDTO> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("name") String name, @RequestParam("ids") String ids) {
        log.info("Uploaded file format: "+file.getContentType());
        if(file!=null && FileUploadUtil.isFormatAvi(file.getContentType().toLowerCase())) {
            if (ids != null && !ids.trim().isEmpty()) {
                String[] idsArray = ids.split(",");
                return ResponseEntity.status(HttpStatus.OK).body(videoFileService.write(idsArray, name, file, startDate, endDate));
            } else {
                throw new DateNotProvided("Date not provided");
            }
        } else {
            throw new VideoFileFormatException("Video not atteched or its not in MP4 format");
        }
    }

    @PostMapping("/uploadFiles")
    public ResponseEntity<List<VideoFileDTO>> uploadFiles(@RequestParam("files") MultipartFile[] files, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("name") String name, @RequestParam("ids") String ids) {
        List<VideoFileDTO> listDtos = new ArrayList<>();
        log.info("Uploaded files: ", files.length);
        int i = 1;
        for(MultipartFile file:files){
            log.info("Uploaded file format: "+file.getContentType());
            if(file!=null && FileUploadUtil.isFormatAvi(file.getContentType().toLowerCase())) {
                if (ids != null && !ids.trim().isEmpty()) {
                    String[] idsArray = ids.split(",");
                    listDtos.add(videoFileService.write(idsArray, name+" "+(i++), file, startDate, endDate));
                } else {
                    throw new DateNotProvided("Date not provided");
                }
            } else {
                throw new VideoFileFormatException("Video not atteched or its not in MP4 format");
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(listDtos);
    }

    @PutMapping("/uploadFile")
    public ResponseEntity<VideoFileDTO> updateFile(@RequestParam("id") Long id ,@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("name") String name, @RequestParam("ids") String ids) throws Exception {
        if (ids!=null&&!ids.trim().isEmpty()){
            String[] idsArray = ids.split(",");
            return ResponseEntity.status(HttpStatus.OK).body(videoFileService.update(id,idsArray, name, startDate, endDate));
        } else {
            throw new DateNotProvided("Date not provided");
        }
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = videoFileService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
    }

    @GetMapping("/videos/play/{name}/stream")
    public ResponseEntity<ResourceRegion> getVideo(@PathVariable String name, @RequestHeader HttpHeaders headers) throws IOException {
        Resource resource = videoFileService.loadFileAsResource(name);
        ResourceRegion region = resourceRegion(resource, headers);
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
            .contentType(MediaTypeFactory
                .getMediaType(resource)
                .orElse(MediaType.APPLICATION_OCTET_STREAM))
            .body(region);
    }

    private ResourceRegion resourceRegion(Resource video, HttpHeaders headers) throws IOException {
        Long contentLength = video.contentLength();
        Optional<HttpRange> range = headers.getRange().stream().findFirst();
        if(range.isPresent()){
            Long start = range.get().getRangeStart(contentLength);
            Long end = range.get().getRangeEnd(contentLength);
            Long rangeLength = Math.min(1*1024*1024,end-start+1);
            return new ResourceRegion(video, start, rangeLength);
        }else{
            Long rangeLength = Math.min(1*1024*1024, contentLength);
            return new ResourceRegion(video, 0, rangeLength);
        }
    }


    /**
     * {@code GET  /video-files} : get all the videoFiles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of videoFiles in body.
     */
    @GetMapping("/video-files")
    public List<VideoFile> getAllVideoFiles() {
        log.debug("REST request to get all VideoFiles");
        return videoFileService.findAll();
    }

    /**
     * {@code GET  /video-files/:id} : get the "id" videoFile.
     *
     * @param id the id of the videoFile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the videoFile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/video-files/{id}")
    public ResponseEntity<VideoFile> getVideoFile(@PathVariable Long id) {
        log.debug("REST request to get VideoFile : {}", id);
        Optional<VideoFile> videoFile = videoFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(videoFile);
    }

    /**
     * {@code DELETE  /video-files/:id} : delete the "id" videoFile.
     *
     * @param id the id of the videoFile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/video-files/{id}")
    public ResponseEntity<Void> deleteVideoFile(@PathVariable Long id) {
        log.debug("REST request to delete VideoFile : {}", id);
        videoFileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
