package kz.technodom.videoupload.web.rest;

import kz.technodom.videoupload.domain.VideoToStand;
import kz.technodom.videoupload.service.VideoFileService;
import kz.technodom.videoupload.service.VideoToStandService;
import kz.technodom.videoupload.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link kz.technodom.videoupload.domain.VideoToStand}.
 */
@RestController
@RequestMapping("/api")
public class VideoToStandResource {

    private final Logger log = LoggerFactory.getLogger(VideoToStandResource.class);

    private static final String ENTITY_NAME = "videoToStand";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VideoToStandService videoToStandService;

    private final VideoFileService videoFileService;

    public VideoToStandResource(VideoToStandService videoToStandService, VideoFileService videoFileService) {
        this.videoToStandService = videoToStandService;
        this.videoFileService = videoFileService;
    }

    /**
     * {@code POST  /video-to-stands} : Create a new videoToStand.
     *
     * @param videoToStand the videoToStand to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new videoToStand, or with status {@code 400 (Bad Request)} if the videoToStand has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/video-to-stands")
    public ResponseEntity<VideoToStand> createVideoToStand(@RequestBody VideoToStand videoToStand) throws URISyntaxException {
        log.debug("REST request to save VideoToStand : {}", videoToStand);
        if (videoToStand.getId() != null) {
            throw new BadRequestAlertException("A new videoToStand cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VideoToStand result = videoToStandService.save(videoToStand);
        return ResponseEntity.created(new URI("/api/video-to-stands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /video-to-stands} : Updates an existing videoToStand.
     *
     * @param videoToStand the videoToStand to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated videoToStand,
     * or with status {@code 400 (Bad Request)} if the videoToStand is not valid,
     * or with status {@code 500 (Internal Server Error)} if the videoToStand couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/video-to-stands")
    public ResponseEntity<VideoToStand> updateVideoToStand(@RequestBody VideoToStand videoToStand) throws URISyntaxException {
        log.debug("REST request to update VideoToStand : {}", videoToStand);
        if (videoToStand.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VideoToStand result = videoToStandService.save(videoToStand);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, videoToStand.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /video-to-stands} : get all the videoToStands.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of videoToStands in body.
     */
    @GetMapping("/video-to-stands")
    public List<VideoToStand> getAllVideoToStands() {
        log.debug("REST request to get all VideoToStands");
        return videoToStandService.findAll();
    }

    /**
     * {@code GET  /video-to-stands/:id} : get the "id" videoToStand.
     *
     * @param id the id of the videoToStand to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the videoToStand, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/video-to-stands/{id}")
    public ResponseEntity<VideoToStand> getVideoToStand(@PathVariable Long id) {
        log.debug("REST request to get VideoToStand : {}", id);
        Optional<VideoToStand> videoToStand = videoToStandService.findOne(id);
        return ResponseUtil.wrapOrNotFound(videoToStand);
    }

    @GetMapping("/video-to-stands/forceupload/{id}")
    public ResponseEntity<VideoToStand> forceUploadVideoToStand(@PathVariable Long id) {
        log.debug("REST request to force upload video to stand : {}", id);
        Optional<VideoToStand> videoToStand = videoFileService.forceVideoToStandUpload(id);
        return ResponseUtil.wrapOrNotFound(videoToStand);
    }

    @GetMapping("/video-to-stands/forceremove/{id}")
    public ResponseEntity<VideoToStand> forceRemoveVideoFromStand(@PathVariable Long id) {
        log.debug("REST request to force upload video to stand : {}", id);
        Optional<VideoToStand> videoToStand = videoFileService.forceVideoToStandRemove(id);
        return ResponseUtil.wrapOrNotFound(videoToStand);
    }

    /**
     * {@code DELETE  /video-to-stands/:id} : delete the "id" videoToStand.
     *
     * @param id the id of the videoToStand to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/video-to-stands/{id}")
    public ResponseEntity<Void> deleteVideoToStand(@PathVariable Long id) {
        log.debug("REST request to delete VideoToStand : {}", id);
        videoToStandService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
