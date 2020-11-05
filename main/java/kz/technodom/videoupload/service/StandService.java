package kz.technodom.videoupload.service;

import kz.technodom.videoupload.domain.Stand;
import kz.technodom.videoupload.repository.StandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Stand}.
 */
@Service
@Transactional
public class StandService {

    private final Logger log = LoggerFactory.getLogger(StandService.class);

    private final StandRepository standRepository;

    public StandService(StandRepository standRepository) {
        this.standRepository = standRepository;
    }

    /**
     * Save a stand.
     *
     * @param stand the entity to save.
     * @return the persisted entity.
     */
    public Stand save(Stand stand) {
        log.debug("Request to save Stand : {}", stand);
        return standRepository.save(stand);
    }

    /**
     * Get all the stands.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Stand> findAll() {
        log.debug("Request to get all Stands");
        return standRepository.findAll();
    }


    /**
     * Get one stand by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Stand> findOne(Long id) {
        log.debug("Request to get Stand : {}", id);
        return standRepository.findById(id);
    }

    /**
     * Delete the stand by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Stand : {}", id);
        standRepository.deleteById(id);
    }
}
