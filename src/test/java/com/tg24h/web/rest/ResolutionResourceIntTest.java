package com.tg24h.web.rest;

import com.tg24h.Tg24HApp;
import com.tg24h.domain.Resolution;
import com.tg24h.repository.ResolutionRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ResolutionResource REST controller.
 *
 * @see ResolutionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Tg24HApp.class)
@WebAppConfiguration
@IntegrationTest
public class ResolutionResourceIntTest {

    private static final String DEFAULT_PRODUCT_ID = "AAAAA";
    private static final String UPDATED_PRODUCT_ID = "BBBBB";
    private static final String DEFAULT_INSTANCE_ID = "AAAAA";
    private static final String UPDATED_INSTANCE_ID = "BBBBB";
    private static final String DEFAULT_MANAGER = "AAAAA";
    private static final String UPDATED_MANAGER = "BBBBB";
    private static final String DEFAULT_DATA = "AAAAA";
    private static final String UPDATED_DATA = "BBBBB";

    @Inject
    private ResolutionRepository resolutionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restResolutionMockMvc;

    private Resolution resolution;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ResolutionResource resolutionResource = new ResolutionResource();
        ReflectionTestUtils.setField(resolutionResource, "resolutionRepository", resolutionRepository);
        this.restResolutionMockMvc = MockMvcBuilders.standaloneSetup(resolutionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        resolutionRepository.deleteAll();
        resolution = new Resolution();
        resolution.setProductId(DEFAULT_PRODUCT_ID);
        resolution.setInstanceId(DEFAULT_INSTANCE_ID);
        resolution.setManager(DEFAULT_MANAGER);
        resolution.setData(DEFAULT_DATA);
    }

    @Test
    public void createResolution() throws Exception {
        int databaseSizeBeforeCreate = resolutionRepository.findAll().size();

        // Create the Resolution

        restResolutionMockMvc.perform(post("/api/resolutions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resolution)))
                .andExpect(status().isCreated());

        // Validate the Resolution in the database
        List<Resolution> resolutions = resolutionRepository.findAll();
        assertThat(resolutions).hasSize(databaseSizeBeforeCreate + 1);
        Resolution testResolution = resolutions.get(resolutions.size() - 1);
        assertThat(testResolution.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testResolution.getInstanceId()).isEqualTo(DEFAULT_INSTANCE_ID);
        assertThat(testResolution.getManager()).isEqualTo(DEFAULT_MANAGER);
        assertThat(testResolution.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    public void checkProductIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = resolutionRepository.findAll().size();
        // set the field null
        resolution.setProductId(null);

        // Create the Resolution, which fails.

        restResolutionMockMvc.perform(post("/api/resolutions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resolution)))
                .andExpect(status().isBadRequest());

        List<Resolution> resolutions = resolutionRepository.findAll();
        assertThat(resolutions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkInstanceIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = resolutionRepository.findAll().size();
        // set the field null
        resolution.setInstanceId(null);

        // Create the Resolution, which fails.

        restResolutionMockMvc.perform(post("/api/resolutions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resolution)))
                .andExpect(status().isBadRequest());

        List<Resolution> resolutions = resolutionRepository.findAll();
        assertThat(resolutions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkManagerIsRequired() throws Exception {
        int databaseSizeBeforeTest = resolutionRepository.findAll().size();
        // set the field null
        resolution.setManager(null);

        // Create the Resolution, which fails.

        restResolutionMockMvc.perform(post("/api/resolutions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resolution)))
                .andExpect(status().isBadRequest());

        List<Resolution> resolutions = resolutionRepository.findAll();
        assertThat(resolutions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = resolutionRepository.findAll().size();
        // set the field null
        resolution.setData(null);

        // Create the Resolution, which fails.

        restResolutionMockMvc.perform(post("/api/resolutions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(resolution)))
                .andExpect(status().isBadRequest());

        List<Resolution> resolutions = resolutionRepository.findAll();
        assertThat(resolutions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllResolutions() throws Exception {
        // Initialize the database
        resolutionRepository.save(resolution);

        // Get all the resolutions
        restResolutionMockMvc.perform(get("/api/resolutions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(resolution.getId())))
                .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.toString())))
                .andExpect(jsonPath("$.[*].instanceId").value(hasItem(DEFAULT_INSTANCE_ID.toString())))
                .andExpect(jsonPath("$.[*].manager").value(hasItem(DEFAULT_MANAGER.toString())))
                .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())));
    }

    @Test
    public void getResolution() throws Exception {
        // Initialize the database
        resolutionRepository.save(resolution);

        // Get the resolution
        restResolutionMockMvc.perform(get("/api/resolutions/{id}", resolution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(resolution.getId()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.toString()))
            .andExpect(jsonPath("$.instanceId").value(DEFAULT_INSTANCE_ID.toString()))
            .andExpect(jsonPath("$.manager").value(DEFAULT_MANAGER.toString()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()));
    }

    @Test
    public void getNonExistingResolution() throws Exception {
        // Get the resolution
        restResolutionMockMvc.perform(get("/api/resolutions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateResolution() throws Exception {
        // Initialize the database
        resolutionRepository.save(resolution);
        int databaseSizeBeforeUpdate = resolutionRepository.findAll().size();

        // Update the resolution
        Resolution updatedResolution = new Resolution();
        updatedResolution.setId(resolution.getId());
        updatedResolution.setProductId(UPDATED_PRODUCT_ID);
        updatedResolution.setInstanceId(UPDATED_INSTANCE_ID);
        updatedResolution.setManager(UPDATED_MANAGER);
        updatedResolution.setData(UPDATED_DATA);

        restResolutionMockMvc.perform(put("/api/resolutions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedResolution)))
                .andExpect(status().isOk());

        // Validate the Resolution in the database
        List<Resolution> resolutions = resolutionRepository.findAll();
        assertThat(resolutions).hasSize(databaseSizeBeforeUpdate);
        Resolution testResolution = resolutions.get(resolutions.size() - 1);
        assertThat(testResolution.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testResolution.getInstanceId()).isEqualTo(UPDATED_INSTANCE_ID);
        assertThat(testResolution.getManager()).isEqualTo(UPDATED_MANAGER);
        assertThat(testResolution.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    public void deleteResolution() throws Exception {
        // Initialize the database
        resolutionRepository.save(resolution);
        int databaseSizeBeforeDelete = resolutionRepository.findAll().size();

        // Get the resolution
        restResolutionMockMvc.perform(delete("/api/resolutions/{id}", resolution.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Resolution> resolutions = resolutionRepository.findAll();
        assertThat(resolutions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
