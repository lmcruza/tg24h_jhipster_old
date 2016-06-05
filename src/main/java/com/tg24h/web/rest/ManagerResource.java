package com.tg24h.web.rest;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.tg24h.domain.User;
import com.tg24h.repository.UserRepository;
import com.tg24h.security.AuthoritiesConstants;
import com.tg24h.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Company.
 */
@RestController
@RequestMapping("/api")
public class ManagerResource {

    private final Logger log = LoggerFactory.getLogger(ManagerResource.class);
        
    @Inject
    private UserRepository userRepository;
    
    /**
     * GET  /managers : get all the users with authority Manager.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of managers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/managers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.SUPERVISOR)
    public ResponseEntity<List<User>> getAllManagers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Managers");
        Page<User> page = userRepository.findByAuthoritiesNameInOrderByLogin(new HashSet<String>(Arrays.asList(AuthoritiesConstants.MANAGER)), pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/managers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
