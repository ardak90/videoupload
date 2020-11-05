package kz.technodom.videoupload.service;

import kz.technodom.videoupload.config.ApplicationProperties;
import kz.technodom.videoupload.domain.Stand;
import kz.technodom.videoupload.domain.User;
import kz.technodom.videoupload.domain.VideoFile;
import kz.technodom.videoupload.domain.VideoToStand;
import kz.technodom.videoupload.repository.UserRepository;
import kz.technodom.videoupload.repository.VideoFileRepository;
import kz.technodom.videoupload.repository.VideoToStandRepository;
import kz.technodom.videoupload.security.SecurityUtils;
import kz.technodom.videoupload.util.MountCifsUtil;
import kz.technodom.videoupload.web.rest.dto.VideoFileDTO;
import kz.technodom.videoupload.web.rest.exceptions.DateNotProvided;
import kz.technodom.videoupload.web.rest.exceptions.FileStorageException;
import kz.technodom.videoupload.web.rest.exceptions.MyFileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * Service Implementation for managing {@link VideoFile}.
 */
@Service
@Transactional
public class VideoFileService {

    private final Logger log = LoggerFactory.getLogger(VideoFileService.class);

    private final VideoFileRepository videoFileRepository;

    private final StandService standService;

    private final VideoToStandRepository videoToStandRepository;

    private final UserRepository userRepository;

    private Path fileStorageLocation;

    @Autowired
    ApplicationProperties applicationProperties;

    private String getLocalPath() {
        return applicationProperties.getVideosdir().getFilepath();
    }
    private String getLocalTempFolder(){return applicationProperties.getVideosdir().getTemporaryMountFolder();}
    private String getRemoteFolder(){return applicationProperties.getRemotedir().getFilepath();}
    private String getActiveDirectoryLogin(){return applicationProperties.getActivedirectory().getLogin();}
    private String getActiveDirectoryPassword(){return applicationProperties.getActivedirectory().getPassword();}

    public VideoFileService(VideoFileRepository videoFileRepository, StandService standService, VideoToStandRepository videoToStandRepository, UserRepository userRepository) {
        this.videoFileRepository = videoFileRepository;
        this.standService = standService;
        this.videoToStandRepository = videoToStandRepository;
        this.userRepository = userRepository;
    }

    /**
     * Save a videoFile.
     *
     * @param videoFile the entity to save.
     * @return the persisted entity.
     */
    public VideoFile save(VideoFile videoFile) {
        log.debug("Request to save VideoFile : {}", videoFile);
        return videoFileRepository.save(videoFile);
    }

    /**
     * Get all the videoFiles.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<VideoFile> findAll() {
        log.debug("Request to get all VideoFiles");
        return videoFileRepository.findAll();
    }


    /**
     * Get one videoFile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VideoFile> findOne(Long id) {
        log.debug("Request to get VideoFile : {}", id);
        return videoFileRepository.findById(id);
    }

    /**
     * Delete the videoFile by id.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void delete(Long id) {
        log.debug("Request to delete VideoFile : {}", id);
        Optional<VideoFile> videoFile = videoFileRepository.findById(id);
        try {
            if (videoFile.isPresent()) {
                videoFile.get().getVideosToStand().stream().forEach(videoToStand -> {
                    removeSingleVideoFileFromStand(videoToStand);
                    videoToStand.getStand().removeVideoToStandSet(videoToStand);
                    videoFileRepository.delete(videoToStand.getVideoFile());
                });
                deleteVideo(videoFile.get().getUuid());
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
    }

    private void deleteVideo(String uuid) throws IOException {
        Files.deleteIfExists(Paths.get(getLocalPath() + "/" + uuid));
    }

    private VideoFileDTO writeFile(MultipartFile file, Set<VideoToStand> videoToStands, String name){
        String filename = storeFile(file);
        VideoFile videoFile = new VideoFile();
        videoFile.setUuid(filename);
        videoFile.setSize(file.getSize());
        videoFile.setName(name);
        videoToStands.stream().forEach(videoToStand -> videoToStand.setVideoFile(videoFile));
        videoFile.setVideosToStand(videoToStands);
        return VideoFileDTO.fromVideoFile(save(videoFile));
    }

    public VideoFileDTO write(String[] idsArray, String name, MultipartFile file, String startDate, String endDate){
        Set<VideoToStand> stands = getStandsByArrayIds(idsArray, startDate, endDate);
        VideoFileDTO videoFileDTO = writeFile(file, stands, name);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/downloadFile/")
            .path(videoFileDTO.uuid)
            .toUriString();
        videoFileDTO.downloadUrl = fileDownloadUri;
        return videoFileDTO;
    }

    public VideoFileDTO update(Long id, String[] idsArray, String name, String startDate, String endDate) throws Exception {
        Optional<VideoFile> videoFile = videoFileRepository.findById(id);
        if(videoFile.isPresent()){
            VideoFile vf = videoFile.get();
            if(vf.getVideosToStand().stream().anyMatch(videoToStand -> videoToStand.isUploaded())){
             throw new Exception("Cannot update video file with uploaded video");
            } else {
                vf.getVideosToStand().stream().forEach(videoToStand -> {
                    videoToStand.getStand().removeVideoToStandSet(videoToStand);
                    videoToStandRepository.deleteById(videoToStand.getId());
                });
                Set<VideoToStand> stands = getStandsByArrayIds(idsArray, startDate, endDate);
                vf.setName(name);
                stands.stream().forEach(videoToStand -> videoToStand.setVideoFile(vf));
                vf.setVideosToStand(stands);
                VideoFileDTO videoFileDTO = VideoFileDTO.fromVideoFile(save(vf));
                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/downloadFile/")
                    .path(videoFileDTO.uuid)
                    .toUriString();
                videoFileDTO.downloadUrl = fileDownloadUri;
                return videoFileDTO;
            }
        }
        return null;
    }

    private Set<VideoToStand> getStandsByArrayIds(String[] idsArray, String startDate, String endDate){
        Set<VideoToStand> stands = new HashSet<>();
        for(int i = 0; i<idsArray.length; i++) {
            Optional<Stand> stand = standService.findOne(Long.parseLong(idsArray[i]));
            if (stand.isPresent()) {
                VideoToStand videoToStand = new VideoToStand();
                videoToStand.setStand(stand.get());
                videoToStand.setUploaded(false);
                videoToStand.setFailedToUpload(false);
                videoToStand.setFailedToDelete(false);
                try{
                    videoToStand.setStartDate(VideoFileDTO.stringToLocalDateTime(startDate));
                    videoToStand.setEndDate(VideoFileDTO.stringToLocalDateTime(endDate));
                } catch (Exception e){
                    throw new DateNotProvided("Date not provided or its wrong");
                }
                stands.add(videoToStand);
            }
        }
        return stands;
    }

    private String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = StringUtils.getFilenameExtension(fileName);
        UUID uuid = UUID.randomUUID();
        String newFileName = uuid+"."+extension;
        try {
            if(newFileName.contains("..")) {
                throw new FileStorageException("[FILE SAVE] Sorry! Filename contains invalid path sequence " + newFileName);
            }
            fileStorageLocation = Paths.get(getLocalPath()).toAbsolutePath().normalize();
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = fileStorageLocation.resolve(newFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return newFileName;
        } catch (IOException ex) {
            throw new FileStorageException("[FILE SAVE] Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            fileStorageLocation = Paths.get(getLocalPath()).toAbsolutePath().normalize();
            Path filePath = fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            uploadVideoFilesToStand();
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    private void uploadVideoFilesToStand(){
        Optional<List<VideoToStand>> videoToStands = videoToStandRepository.findAllByStartDateIsLessThanAndEndDateGreaterThan(LocalDateTime.now(), LocalDateTime.now());

        if(videoToStands.isPresent())
        {
            log.info("[CRONJOB] Active videos count: ", videoToStands.get().size());
            videoToStands
                .get()
                .stream()
                .filter(videoToStand -> videoToStand.getStand().getIpAddress()!=null
                                      &&!videoToStand.getStand().getIpAddress().trim().isEmpty()
                                      &&videoToStand.getStand().getUpdateStartTime().isBefore(LocalTime.now())
                                      &&videoToStand.getStand().getUpdateEndTime().isAfter(LocalTime.now()))
                .forEach(videoToStand ->
                        uploadSingleVideoFileToStand(videoToStand));
        }
    }



    private VideoToStand uploadSingleVideoFileToStand(VideoToStand videoToStand){
        if(!videoToStand.isUploaded()){
            if(uploadVideoFileToStand(videoToStand)){
                videoToStand.setUploaded(true);
                videoToStand.setFailedToUpload(false);
                return videoToStandRepository.save(videoToStand);
            } else {
                videoToStand.setFailedToUpload(true);
                return videoToStandRepository.save(videoToStand);
            }
        }
        return null;
    }

    private void removeVideoFilesFromStand(){
        Optional<List<VideoToStand>> videoToStands = videoToStandRepository.findAllByEndDateIsLessThan(LocalDateTime.now());
        if(videoToStands.isPresent()){
            videoToStands
                .get()
                .stream()
                .filter(videoToStand -> videoToStand.getStand().getIpAddress()!=null
                                        &&!videoToStand.getStand().getIpAddress().trim().isEmpty()
                                        &&videoToStand.getStand().getUpdateStartTime().isBefore(LocalTime.now())
                                        &&videoToStand.getStand().getUpdateEndTime().isAfter(LocalTime.now()))
                .forEach(videoToStand ->
                    removeSingleVideoFileFromStand(videoToStand));
        }
    }

    private VideoToStand removeSingleVideoFileFromStand(VideoToStand videoToStand){
        if(videoToStand.isUploaded()){
            if(deleteVideoFileFromStand(videoToStand)){
                videoToStand.setUploaded(false);
                videoToStand.setFailedToDelete(false);
                return videoToStandRepository.save(videoToStand);
            } else {
                videoToStand.setFailedToDelete(true);
                return videoToStandRepository.save(videoToStand);
            }
        }
        return null;
    }

    public boolean uploadVideoFileToStand(VideoToStand videoToStand){
        try{
            tryMount(videoToStand);
            MountCifsUtil.copy(getLocalPath() + "/" + videoToStand.getVideoFile().getUuid(), getLocalTempFolder() + "/" + videoToStand.getVideoFile().getUuid());
            MountCifsUtil.unmount(getLocalPath(), getLocalTempFolder());
            return true;
        }
        catch (Exception e){
            log.info("[UPLOAD RESPONSE]", e);
            MountCifsUtil.unmount(getLocalPath(), getLocalTempFolder());
            return false;
        }
    }

    public boolean deleteVideoFileFromStand(VideoToStand videoToStand){
        try{
            tryMount(videoToStand);
            MountCifsUtil.removeFile(getLocalPath(), getLocalTempFolder(), videoToStand.getVideoFile().getUuid());
            MountCifsUtil.unmount(getLocalPath(), getLocalTempFolder());
            return true;
        }
        catch (Exception e){
            log.info("[DELETE EXCEPTION]", e);
            MountCifsUtil.unmount(getLocalPath(), getLocalTempFolder());
            return false;
        }
    }

    public String tryMount(VideoToStand videoToStand){
        return MountCifsUtil.mount(videoToStand.getStand().getIpAddress(), getRemoteFolder(), getLocalPath(), getLocalTempFolder(), getActiveDirectoryLogin(), getActiveDirectoryPassword());
    }

    public Optional<VideoToStand> forceVideoToStandUpload(Long id){
        Optional<VideoToStand> videoToStand = videoToStandRepository.findById(id);
        if(videoToStand.isPresent()){
            return Optional.ofNullable(uploadSingleVideoFileToStand(videoToStand.get()));
        }
        return null;
    }

    public Optional<VideoToStand> forceVideoToStandRemove(Long id){
        Optional<VideoToStand> videoToStand = videoToStandRepository.findById(id);
        if(videoToStand.isPresent()){
            return Optional.ofNullable(removeSingleVideoFileFromStand(videoToStand.get()));
        }
        return null;
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void updateStands() {
        log.info("[CRONJOB] Time: " + LocalDateTime.now().toString());
        log.info("[CRONJOB] Updating videos to stands");
        uploadVideoFilesToStand();
        log.info("[CRONJOB] Removing outdated videos from stands");
        removeVideoFilesFromStand();
    }
}
