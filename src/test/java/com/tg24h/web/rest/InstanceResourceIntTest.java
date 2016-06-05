package com.tg24h.web.rest;

import com.tg24h.Tg24HApp;
import com.tg24h.domain.Instance;
import com.tg24h.repository.InstanceRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tg24h.domain.enumeration.InstanceStatus;

/**
 * Test class for the InstanceResource REST controller.
 *
 * @see InstanceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Tg24HApp.class)
@WebAppConfiguration
@IntegrationTest
public class InstanceResourceIntTest {

    private static final String DEFAULT_PRODUCT_ID = "AAAAA";
    private static final String UPDATED_PRODUCT_ID = "BBBBB";
    private static final String DEFAULT_MANAGER = "AAAAA";
    private static final String UPDATED_MANAGER = "BBBBB";
    private static final String DEFAULT_USER = "AAAAA";
    private static final String UPDATED_USER = "BBBBB";

    private static final InstanceStatus DEFAULT_STATUS = InstanceStatus.DRAFT;
    private static final InstanceStatus UPDATED_STATUS = InstanceStatus.NEW;

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_DATA = "AAAAA";
    private static final String UPDATED_DATA = "BBBBB";

    @Inject
    private InstanceRepository instanceRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInstanceMockMvc;

    private Instance instance;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InstanceResource instanceResource = new InstanceResource();
        ReflectionTestUtils.setField(instanceResource, "instanceRepository", instanceRepository);
        this.restInstanceMockMvc = MockMvcBuilders.standaloneSetup(instanceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        instanceRepository.deleteAll();
        instance = new Instance();
        instance.setProductId(DEFAULT_PRODUCT_ID);
        instance.setManager(DEFAULT_MANAGER);
        instance.setUser(DEFAULT_USER);
        instance.setStatus(DEFAULT_STATUS);
        instance.setCreateDate(DEFAULT_CREATE_DATE);
        instance.setUpdateDate(DEFAULT_UPDATE_DATE);
        instance.setData(DEFAULT_DATA);
    }

    @Test
    public void createInstance() throws Exception {
        int databaseSizeBeforeCreate = instanceRepository.findAll().size();

        // Create the Instance

        restInstanceMockMvc.perform(post("/api/instances")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instance)))
                .andExpect(status().isCreated());

        // Validate the Instance in the database
        List<Instance> instances = instanceRepository.findAll();
        assertThat(instances).hasSize(databaseSizeBeforeCreate + 1);
        Instance testInstance = instances.get(instances.size() - 1);
        assertThat(testInstance.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testInstance.getManager()).isEqualTo(DEFAULT_MANAGER);
        assertThat(testInstance.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testInstance.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testInstance.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testInstance.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testInstance.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    public void checkProductIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = instanceRepository.findAll().size();
        // set the field null
        instance.setProductId(null);

        // Create the Instance, which fails.

        restInstanceMockMvc.perform(post("/api/instances")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instance)))
                .andExpect(status().isBadRequest());

        List<Instance> instances = instanceRepository.findAll();
        assertThat(instances).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkManagerIsRequired() throws Exception {
        int databaseSizeBeforeTest = instanceRepository.findAll().size();
        // set the field null
        instance.setManager(null);

        // Create the Instance, which fails.

        restInstanceMockMvc.perform(post("/api/instances")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instance)))
                .andExpect(status().isBadRequest());

        List<Instance> instances = instanceRepository.findAll();
        assertThat(instances).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = instanceRepository.findAll().size();
        // set the field null
        instance.setUser(null);

        // Create the Instance, which fails.

        restInstanceMockMvc.perform(post("/api/instances")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instance)))
                .andExpect(status().isBadRequest());

        List<Instance> instances = instanceRepository.findAll();
        assertThat(instances).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = instanceRepository.findAll().size();
        // set the field null
        instance.setStatus(null);

        // Create the Instance, which fails.

        restInstanceMockMvc.perform(post("/api/instances")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instance)))
                .andExpect(status().isBadRequest());

        List<Instance> instances = instanceRepository.findAll();
        assertThat(instances).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = instanceRepository.findAll().size();
        // set the field null
        instance.setData(null);

        // Create the Instance, which fails.

        restInstanceMockMvc.perform(post("/api/instances")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(instance)))
                .andExpect(status().isBadRequest());

        List<Instance> instances = instanceRepository.findAll();
        assertThat(instances).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllInstances() throws Exception {
        // Initialize the database
        instanceRepository.save(instance);

        // Get all the instances
        restInstanceMockMvc.perform(get("/api/instances?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(instance.getId())))
                .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.toString())))
                .andExpect(jsonPath("$.[*].manager").value(hasItem(DEFAULT_MANAGER.toString())))
                .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
                .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())));
    }

    @Test
    public void getInstance() throws Exception {
        // Initialize the database
        instanceRepository.save(instance);

        // Get the instance
        restInstanceMockMvc.perform(get("/api/instances/{id}", instance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(instance.getId()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.toString()))
            .andExpect(jsonPath("$.manager").value(DEFAULT_MANAGER.toString()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()));
    }

    @Test
    public void getNonExistingInstance() throws Exception {
        // Get the instance
        restInstanceMockMvc.perform(get("/api/instances/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateInstance() throws Exception {
        // Initialize the database
        instanceRepository.save(instance);
        int databaseSizeBeforeUpdate = instanceRepository.findAll().size();

        // Update the instance
        Instance updatedInstance = new Instance();
        updatedInstance.setId(instance.getId());
        updatedInstance.setProductId(UPDATED_PRODUCT_ID);
        updatedInstance.setManager(UPDATED_MANAGER);
        updatedInstance.setUser(UPDATED_USER);
        updatedInstance.setStatus(UPDATED_STATUS);
        updatedInstance.setCreateDate(UPDATED_CREATE_DATE);
        updatedInstance.setUpdateDate(UPDATED_UPDATE_DATE);
        updatedInstance.setData(UPDATED_DATA);

        restInstanceMockMvc.perform(put("/api/instances")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedInstance)))
                .andExpect(status().isOk());

        // Validate the Instance in the database
        List<Instance> instances = instanceRepository.findAll();
        assertThat(instances).hasSize(databaseSizeBeforeUpdate);
        Instance testInstance = instances.get(instances.size() - 1);
        assertThat(testInstance.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testInstance.getManager()).isEqualTo(UPDATED_MANAGER);
        assertThat(testInstance.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testInstance.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testInstance.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testInstance.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testInstance.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    public void deleteInstance() throws Exception {
        // Initialize the database
        instanceRepository.save(instance);
        int databaseSizeBeforeDelete = instanceRepository.findAll().size();

        // Get the instance
        restInstanceMockMvc.perform(delete("/api/instances/{id}", instance.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Instance> instances = instanceRepository.findAll();
        assertThat(instances).hasSize(databaseSizeBeforeDelete - 1);
    }
}
