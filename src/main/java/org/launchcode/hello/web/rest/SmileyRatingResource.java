package org.launchcode.hello.web.rest;

import org.launchcode.hello.domain.SmileyRating;
import org.launchcode.hello.repository.SmileyRatingRepository;
import org.launchcode.hello.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.launchcode.hello.domain.SmileyRating}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SmileyRatingResource {

    private final Logger log = LoggerFactory.getLogger(SmileyRatingResource.class);

    private static final String ENTITY_NAME = "smileyRating";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SmileyRatingRepository smileyRatingRepository;

    public SmileyRatingResource(SmileyRatingRepository smileyRatingRepository) {
        this.smileyRatingRepository = smileyRatingRepository;
    }

    /**
     * {@code POST  /smiley-ratings} : Create a new smileyRating.
     *
     * @param smileyRating the smileyRating to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new smileyRating, or with status {@code 400 (Bad Request)} if the smileyRating has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/smiley-ratings")
    public ResponseEntity<SmileyRating> createSmileyRating(@RequestBody SmileyRating smileyRating) throws URISyntaxException {
        log.debug("REST request to save SmileyRating : {}", smileyRating);
        if (smileyRating.getId() != null) {
            throw new BadRequestAlertException("A new smileyRating cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SmileyRating result = smileyRatingRepository.save(smileyRating);
        return ResponseEntity.created(new URI("/api/smiley-ratings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /smiley-ratings} : Updates an existing smileyRating.
     *
     * @param smileyRating the smileyRating to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated smileyRating,
     * or with status {@code 400 (Bad Request)} if the smileyRating is not valid,
     * or with status {@code 500 (Internal Server Error)} if the smileyRating couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/smiley-ratings")
    public ResponseEntity<SmileyRating> updateSmileyRating(@RequestBody SmileyRating smileyRating) throws URISyntaxException {
        log.debug("REST request to update SmileyRating : {}", smileyRating);
        if (smileyRating.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SmileyRating result = smileyRatingRepository.save(smileyRating);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, smileyRating.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /smiley-ratings} : get all the smileyRatings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of smileyRatings in body.
     */
    @GetMapping("/smiley-ratings")
    public ResponseEntity<List<SmileyRating>> getAllSmileyRatings(Pageable pageable) {
        log.debug("REST request to get a page of SmileyRatings");
        Page<SmileyRating> page = smileyRatingRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /smiley-ratings/:id} : get the "id" smileyRating.
     *
     * @param id the id of the smileyRating to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the smileyRating, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/smiley-ratings/{id}")
    public ResponseEntity<SmileyRating> getSmileyRating(@PathVariable Long id) {
        log.debug("REST request to get SmileyRating : {}", id);
        Optional<SmileyRating> smileyRating = smileyRatingRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(smileyRating);
    }

    /**
     * {@code DELETE  /smiley-ratings/:id} : delete the "id" smileyRating.
     *
     * @param id the id of the smileyRating to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/smiley-ratings/{id}")
    public ResponseEntity<Void> deleteSmileyRating(@PathVariable Long id) {
        log.debug("REST request to delete SmileyRating : {}", id);
        smileyRatingRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
