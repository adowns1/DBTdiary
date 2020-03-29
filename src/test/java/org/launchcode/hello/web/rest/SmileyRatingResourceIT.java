package org.launchcode.hello.web.rest;

import org.launchcode.hello.MyApp;
import org.launchcode.hello.domain.SmileyRating;
import org.launchcode.hello.repository.SmileyRatingRepository;
import org.launchcode.hello.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static org.launchcode.hello.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.launchcode.hello.domain.enumeration.Rating;
/**
 * Integration tests for the {@link SmileyRatingResource} REST controller.
 */
@SpringBootTest(classes = MyApp.class)
public class SmileyRatingResourceIT {

    private static final Rating DEFAULT_RATING = Rating.HAPPY;
    private static final Rating UPDATED_RATING = Rating.NEUTRAL;

    @Autowired
    private SmileyRatingRepository smileyRatingRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restSmileyRatingMockMvc;

    private SmileyRating smileyRating;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SmileyRatingResource smileyRatingResource = new SmileyRatingResource(smileyRatingRepository);
        this.restSmileyRatingMockMvc = MockMvcBuilders.standaloneSetup(smileyRatingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SmileyRating createEntity(EntityManager em) {
        SmileyRating smileyRating = new SmileyRating()
            .rating(DEFAULT_RATING);
        return smileyRating;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SmileyRating createUpdatedEntity(EntityManager em) {
        SmileyRating smileyRating = new SmileyRating()
            .rating(UPDATED_RATING);
        return smileyRating;
    }

    @BeforeEach
    public void initTest() {
        smileyRating = createEntity(em);
    }

    @Test
    @Transactional
    public void createSmileyRating() throws Exception {
        int databaseSizeBeforeCreate = smileyRatingRepository.findAll().size();

        // Create the SmileyRating
        restSmileyRatingMockMvc.perform(post("/api/smiley-ratings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(smileyRating)))
            .andExpect(status().isCreated());

        // Validate the SmileyRating in the database
        List<SmileyRating> smileyRatingList = smileyRatingRepository.findAll();
        assertThat(smileyRatingList).hasSize(databaseSizeBeforeCreate + 1);
        SmileyRating testSmileyRating = smileyRatingList.get(smileyRatingList.size() - 1);
        assertThat(testSmileyRating.getRating()).isEqualTo(DEFAULT_RATING);
    }

    @Test
    @Transactional
    public void createSmileyRatingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = smileyRatingRepository.findAll().size();

        // Create the SmileyRating with an existing ID
        smileyRating.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSmileyRatingMockMvc.perform(post("/api/smiley-ratings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(smileyRating)))
            .andExpect(status().isBadRequest());

        // Validate the SmileyRating in the database
        List<SmileyRating> smileyRatingList = smileyRatingRepository.findAll();
        assertThat(smileyRatingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSmileyRatings() throws Exception {
        // Initialize the database
        smileyRatingRepository.saveAndFlush(smileyRating);

        // Get all the smileyRatingList
        restSmileyRatingMockMvc.perform(get("/api/smiley-ratings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(smileyRating.getId().intValue())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING.toString())));
    }
    
    @Test
    @Transactional
    public void getSmileyRating() throws Exception {
        // Initialize the database
        smileyRatingRepository.saveAndFlush(smileyRating);

        // Get the smileyRating
        restSmileyRatingMockMvc.perform(get("/api/smiley-ratings/{id}", smileyRating.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(smileyRating.getId().intValue()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSmileyRating() throws Exception {
        // Get the smileyRating
        restSmileyRatingMockMvc.perform(get("/api/smiley-ratings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSmileyRating() throws Exception {
        // Initialize the database
        smileyRatingRepository.saveAndFlush(smileyRating);

        int databaseSizeBeforeUpdate = smileyRatingRepository.findAll().size();

        // Update the smileyRating
        SmileyRating updatedSmileyRating = smileyRatingRepository.findById(smileyRating.getId()).get();
        // Disconnect from session so that the updates on updatedSmileyRating are not directly saved in db
        em.detach(updatedSmileyRating);
        updatedSmileyRating
            .rating(UPDATED_RATING);

        restSmileyRatingMockMvc.perform(put("/api/smiley-ratings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSmileyRating)))
            .andExpect(status().isOk());

        // Validate the SmileyRating in the database
        List<SmileyRating> smileyRatingList = smileyRatingRepository.findAll();
        assertThat(smileyRatingList).hasSize(databaseSizeBeforeUpdate);
        SmileyRating testSmileyRating = smileyRatingList.get(smileyRatingList.size() - 1);
        assertThat(testSmileyRating.getRating()).isEqualTo(UPDATED_RATING);
    }

    @Test
    @Transactional
    public void updateNonExistingSmileyRating() throws Exception {
        int databaseSizeBeforeUpdate = smileyRatingRepository.findAll().size();

        // Create the SmileyRating

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSmileyRatingMockMvc.perform(put("/api/smiley-ratings")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(smileyRating)))
            .andExpect(status().isBadRequest());

        // Validate the SmileyRating in the database
        List<SmileyRating> smileyRatingList = smileyRatingRepository.findAll();
        assertThat(smileyRatingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSmileyRating() throws Exception {
        // Initialize the database
        smileyRatingRepository.saveAndFlush(smileyRating);

        int databaseSizeBeforeDelete = smileyRatingRepository.findAll().size();

        // Delete the smileyRating
        restSmileyRatingMockMvc.perform(delete("/api/smiley-ratings/{id}", smileyRating.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SmileyRating> smileyRatingList = smileyRatingRepository.findAll();
        assertThat(smileyRatingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
