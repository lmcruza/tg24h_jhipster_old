package com.tg24h.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tg24h.domain.Resolution;
import com.tg24h.repository.ResolutionRepository;
import com.tg24h.web.rest.util.HeaderUtil;
import com.tg24h.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * REST controller for managing Resolution.
 */
@RestController
@RequestMapping("/api")
public class ResolutionResource {

    private final Logger log = LoggerFactory.getLogger(ResolutionResource.class);
        
    @Inject
    private ResolutionRepository resolutionRepository;
    
    /**
     * POST  /resolutions : Create a new resolution.
     *
     * @param resolution the resolution to create
     * @return the ResponseEntity with status 201 (Created) and with body the new resolution, or with status 400 (Bad Request) if the resolution has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/resolutions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Resolution> createResolution(@Valid @RequestBody Resolution resolution) throws URISyntaxException {
        log.debug("REST request to save Resolution : {}", resolution);
        if (resolution.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("resolution", "idexists", "A new resolution cannot already have an ID")).body(null);
        }
        Resolution result = resolutionRepository.save(resolution);
        return ResponseEntity.created(new URI("/api/resolutions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("resolution", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /resolutions : Updates an existing resolution.
     *
     * @param resolution the resolution to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated resolution,
     * or with status 400 (Bad Request) if the resolution is not valid,
     * or with status 500 (Internal Server Error) if the resolution couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/resolutions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Resolution> updateResolution(@Valid @RequestBody Resolution resolution) throws URISyntaxException {
        log.debug("REST request to update Resolution : {}", resolution);
        if (resolution.getId() == null) {
            return createResolution(resolution);
        }
        Resolution result = resolutionRepository.save(resolution);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("resolution", resolution.getId().toString()))
            .body(result);
    }

    /**
     * GET  /resolutions : get all the resolutions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of resolutions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/resolutions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Resolution>> getAllResolutions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Resolutions");
        Page<Resolution> page = resolutionRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/resolutions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /resolutions/:id : get the "id" resolution.
     *
     * @param id the id of the resolution to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the resolution, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/resolutions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Resolution> getResolution(@PathVariable String id) {
        log.debug("REST request to get Resolution : {}", id);
        Resolution resolution = resolutionRepository.findOne(id);
        return Optional.ofNullable(resolution)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /resolutions/:id : delete the "id" resolution.
     *
     * @param id the id of the resolution to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/resolutions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteResolution(@PathVariable String id) {
        log.debug("REST request to delete Resolution : {}", id);
        resolutionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("resolution", id.toString())).build();
    }

}
