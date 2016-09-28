package fr.istic.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.istic.domain.Partenaire;

import fr.istic.repository.PartenaireRepository;
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
 * REST controller for managing Partenaire.
 */
@RestController
@RequestMapping("/api")
public class PartenaireResource {

    private final Logger log = LoggerFactory.getLogger(PartenaireResource.class);
        
    @Inject
    private PartenaireRepository partenaireRepository;

    /**
     * POST  /partenaires : Create a new partenaire.
     *
     * @param partenaire the partenaire to create
     * @return the ResponseEntity with status 201 (Created) and with body the new partenaire, or with status 400 (Bad Request) if the partenaire has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/partenaires",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Partenaire> createPartenaire(@Valid @RequestBody Partenaire partenaire) throws URISyntaxException {
        log.debug("REST request to save Partenaire : {}", partenaire);
        if (partenaire.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("partenaire", "idexists", "A new partenaire cannot already have an ID")).body(null);
        }
        Partenaire result = partenaireRepository.save(partenaire);
        return ResponseEntity.created(new URI("/api/partenaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("partenaire", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /partenaires : Updates an existing partenaire.
     *
     * @param partenaire the partenaire to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated partenaire,
     * or with status 400 (Bad Request) if the partenaire is not valid,
     * or with status 500 (Internal Server Error) if the partenaire couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/partenaires",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Partenaire> updatePartenaire(@Valid @RequestBody Partenaire partenaire) throws URISyntaxException {
        log.debug("REST request to update Partenaire : {}", partenaire);
        if (partenaire.getId() == null) {
            return createPartenaire(partenaire);
        }
        Partenaire result = partenaireRepository.save(partenaire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("partenaire", partenaire.getId().toString()))
            .body(result);
    }

    /**
     * GET  /partenaires : get all the partenaires.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of partenaires in body
     */
    @RequestMapping(value = "/partenaires",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Partenaire> getAllPartenaires() {
        log.debug("REST request to get all Partenaires");
        List<Partenaire> partenaires = partenaireRepository.findAll();
        return partenaires;
    }

    /**
     * GET  /partenaires/:id : get the "id" partenaire.
     *
     * @param id the id of the partenaire to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the partenaire, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/partenaires/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Partenaire> getPartenaire(@PathVariable Long id) {
        log.debug("REST request to get Partenaire : {}", id);
        Partenaire partenaire = partenaireRepository.findOne(id);
        return Optional.ofNullable(partenaire)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /partenaires/:id : delete the "id" partenaire.
     *
     * @param id the id of the partenaire to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/partenaires/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePartenaire(@PathVariable Long id) {
        log.debug("REST request to delete Partenaire : {}", id);
        partenaireRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("partenaire", id.toString())).build();
    }

}
