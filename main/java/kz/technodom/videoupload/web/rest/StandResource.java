package kz.technodom.videoupload.web.rest;

import kz.technodom.videoupload.domain.Stand;
import kz.technodom.videoupload.service.StandService;
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
 * REST controller for managing {@link kz.technodom.videoupload.domain.Stand}.
 */
@RestController
@RequestMapping("/api")
public class StandResource {

    private final Logger log = LoggerFactory.getLogger(StandResource.class);

    private static final String ENTITY_NAME = "stand";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StandService standService;

    public StandResource(StandService standService) {
        this.standService = standService;
    }

    /**
     * {@code POST  /stands} : Create a new stand.
     *
     * @param stand the stand to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new stand, or with status {@code 400 (Bad Request)} if the stand has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/stands")
    public ResponseEntity<Stand> createStand(@RequestBody Stand stand) throws URISyntaxException {
        log.debug("REST request to save Stand : {}", stand);
        if (stand.getId() != null) {
            throw new BadRequestAlertException("A new stand cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Stand result = standService.save(stand);
        return ResponseEntity.created(new URI("/api/stands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /stands} : Updates an existing stand.
     *
     * @param stand the stand to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated stand,
     * or with status {@code 400 (Bad Request)} if the stand is not valid,
     * or with status {@code 500 (Internal Server Error)} if the stand couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/stands")
    public ResponseEntity<Stand> updateStand(@RequestBody Stand stand) throws URISyntaxException {
        log.debug("REST request to update Stand : {}", stand);
        if (stand.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Stand result = standService.save(stand);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, stand.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /stands} : get all the stands.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of stands in body.
     */
    @GetMapping("/stands")
    public List<Stand> getAllStands() {
        log.debug("REST request to get all Stands");
        return standService.findAll();
    }

    /**
     * {@code GET  /stands/:id} : get the "id" stand.
     *
     * @param id the id of the stand to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the stand, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/stands/{id}")
    public ResponseEntity<Stand> getStand(@PathVariable Long id) {
        log.debug("REST request to get Stand : {}", id);
        Optional<Stand> stand = standService.findOne(id);
        return ResponseUtil.wrapOrNotFound(stand);
    }

    /**
     * {@code DELETE  /stands/:id} : delete the "id" stand.
     *
     * @param id the id of the stand to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/stands/{id}")
    public ResponseEntity<Void> deleteStand(@PathVariable Long id) {
        log.debug("REST request to delete Stand : {}", id);
        standService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
