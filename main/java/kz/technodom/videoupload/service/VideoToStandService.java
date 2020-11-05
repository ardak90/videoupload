package kz.technodom.videoupload.service;

import kz.technodom.videoupload.domain.VideoToStand;
import kz.technodom.videoupload.repository.VideoToStandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link VideoToStand}.
 */
@Service
@Transactional
public class VideoToStandService {

    private final Logger log = LoggerFactory.getLogger(VideoToStandService.class);

    private final VideoToStandRepository videoToStandRepository;

    public VideoToStandService(VideoToStandRepository videoToStandRepository) {
        this.videoToStandRepository = videoToStandRepository;
    }

    /**
     * Save a videoToStand.
     *
     * @param videoToStand the entity to save.
     * @return the persisted entity.
     */
    public VideoToStand save(VideoToStand videoToStand) {
        log.debug("Request to save VideoToStand : {}", videoToStand);
        return videoToStandRepository.save(videoToStand);
    }

    /**
     * Get all the videoToStands.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<VideoToStand> findAll() {
        log.debug("Request to get all VideoToStands");
        return videoToStandRepository.findAll();
    }


    /**
     * Get one videoToStand by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VideoToStand> findOne(Long id) {
        log.debug("Request to get VideoToStand : {}", id);
        return videoToStandRepository.findById(id);
    }

    /**
     * Delete the videoToStand by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete VideoToStand : {}", id);
        videoToStandRepository.deleteById(id);
    }
}
