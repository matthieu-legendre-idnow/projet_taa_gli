package fr.istic.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.istic.domain.Stage;

import fr.istic.repository.StageRepository;
import fr.istic.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Stage.
 */
@RestController
@RequestMapping("/api")
public class StageResource {

    private final Logger log = LoggerFactory.getLogger(StageResource.class);
        
    @Inject
    private StageRepository stageRepository;

    /**
     * POST  /stages : Create a new stage.
     *
     * @param stage the stage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stage, or with status 400 (Bad Request) if the stage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/stages",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Stage> createStage(@Valid @RequestBody Stage stage) throws URISyntaxException {
        log.debug("REST request to save Stage : {}", stage);
        if (stage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("stage", "idexists", "A new stage cannot already have an ID")).body(null);
        }
        Stage result = stageRepository.save(stage);
        return ResponseEntity.created(new URI("/api/stages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("stage", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stages : Updates an existing stage.
     *
     * @param stage the stage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stage,
     * or with status 400 (Bad Request) if the stage is not valid,
     * or with status 500 (Internal Server Error) if the stage couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/stages",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Stage> updateStage(@Valid @RequestBody Stage stage) throws URISyntaxException {
        log.debug("REST request to update Stage : {}", stage);
        if (stage.getId() == null) {
            return createStage(stage);
        }
        Stage result = stageRepository.save(stage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("stage", stage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stages : get all the stages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stages in body
     */
    @RequestMapping(value = "/stages",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Stage> getAllStages() {
        log.debug("REST request to get all Stages");
        List<Stage> stages = stageRepository.findAll();
        return stages;
    }

    /**
     * GET  /stages/:id : get the "id" stage.
     *
     * @param id the id of the stage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stage, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/stages/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Stage> getStage(@PathVariable Long id) {
        log.debug("REST request to get Stage : {}", id);
        Stage stage = stageRepository.findOne(id);
        return Optional.ofNullable(stage)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /stages/:id : delete the "id" stage.
     *
     * @param id the id of the stage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/stages/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStage(@PathVariable Long id) {
        log.debug("REST request to delete Stage : {}", id);
        stageRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("stage", id.toString())).build();
    }

}
