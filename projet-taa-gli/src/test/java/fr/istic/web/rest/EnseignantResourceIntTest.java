package fr.istic.web.rest;

import fr.istic.ProjetTaaGliApp;

import fr.istic.domain.Enseignant;
import fr.istic.repository.EnseignantRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EnseignantResource REST controller.
 *
 * @see EnseignantResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = ProjetTaaGliApp.class)

public class EnseignantResourceIntTest {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final Long DEFAULT_ENSEIGNANT_ID = 1L;
    private static final Long UPDATED_ENSEIGNANT_ID = 2L;
    private static final String DEFAULT_ENSEIGNANT_SESAME = "AAAAA";
    private static final String UPDATED_ENSEIGNANT_SESAME = "BBBBB";
    private static final String DEFAULT_ENSEIGNANT_SEXE = "AAAAA";
    private static final String UPDATED_ENSEIGNANT_SEXE = "BBBBB";
    private static final String DEFAULT_ENSEIGNANT_NOM = "AAAAA";
    private static final String UPDATED_ENSEIGNANT_NOM = "BBBBB";
    private static final String DEFAULT_ENSEIGNANT_PRENOM = "AAAAA";
    private static final String UPDATED_ENSEIGNANT_PRENOM = "BBBBB";
    private static final String DEFAULT_ENSEIGNANT_ADRESSE = "AAAAA";
    private static final String UPDATED_ENSEIGNANT_ADRESSE = "BBBBB";
    private static final String DEFAULT_ENSEIGNANT_TEL_PRO = "AAAAA";
    private static final String UPDATED_ENSEIGNANT_TEL_PRO = "BBBBB";

    private static final Boolean DEFAULT_ENSEIGNANT_ACTIF = false;
    private static final Boolean UPDATED_ENSEIGNANT_ACTIF = true;

    private static final ZonedDateTime DEFAULT_ENSEIGNANT_MAJ = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_ENSEIGNANT_MAJ = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_ENSEIGNANT_MAJ_STR = dateTimeFormatter.format(DEFAULT_ENSEIGNANT_MAJ);

    @Inject
    private EnseignantRepository enseignantRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEnseignantMockMvc;

    private Enseignant enseignant;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EnseignantResource enseignantResource = new EnseignantResource();
        ReflectionTestUtils.setField(enseignantResource, "enseignantRepository", enseignantRepository);
        this.restEnseignantMockMvc = MockMvcBuilders.standaloneSetup(enseignantResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enseignant createEntity(EntityManager em) {
        Enseignant enseignant = new Enseignant()
                .enseignantId(DEFAULT_ENSEIGNANT_ID)
                .enseignantSesame(DEFAULT_ENSEIGNANT_SESAME)
                .enseignantSexe(DEFAULT_ENSEIGNANT_SEXE)
                .enseignantNom(DEFAULT_ENSEIGNANT_NOM)
                .enseignantPrenom(DEFAULT_ENSEIGNANT_PRENOM)
                .enseignantAdresse(DEFAULT_ENSEIGNANT_ADRESSE)
                .enseignantTelPro(DEFAULT_ENSEIGNANT_TEL_PRO)
                .enseignantActif(DEFAULT_ENSEIGNANT_ACTIF)
                .enseignantMaj(DEFAULT_ENSEIGNANT_MAJ);
        return enseignant;
    }

    @Before
    public void initTest() {
        enseignant = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnseignant() throws Exception {
        int databaseSizeBeforeCreate = enseignantRepository.findAll().size();

        // Create the Enseignant

        restEnseignantMockMvc.perform(post("/api/enseignants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(enseignant)))
                .andExpect(status().isCreated());

        // Validate the Enseignant in the database
        List<Enseignant> enseignants = enseignantRepository.findAll();
        assertThat(enseignants).hasSize(databaseSizeBeforeCreate + 1);
        Enseignant testEnseignant = enseignants.get(enseignants.size() - 1);
        assertThat(testEnseignant.getEnseignantId()).isEqualTo(DEFAULT_ENSEIGNANT_ID);
        assertThat(testEnseignant.getEnseignantSesame()).isEqualTo(DEFAULT_ENSEIGNANT_SESAME);
        assertThat(testEnseignant.getEnseignantSexe()).isEqualTo(DEFAULT_ENSEIGNANT_SEXE);
        assertThat(testEnseignant.getEnseignantNom()).isEqualTo(DEFAULT_ENSEIGNANT_NOM);
        assertThat(testEnseignant.getEnseignantPrenom()).isEqualTo(DEFAULT_ENSEIGNANT_PRENOM);
        assertThat(testEnseignant.getEnseignantAdresse()).isEqualTo(DEFAULT_ENSEIGNANT_ADRESSE);
        assertThat(testEnseignant.getEnseignantTelPro()).isEqualTo(DEFAULT_ENSEIGNANT_TEL_PRO);
        assertThat(testEnseignant.isEnseignantActif()).isEqualTo(DEFAULT_ENSEIGNANT_ACTIF);
        assertThat(testEnseignant.getEnseignantMaj()).isEqualTo(DEFAULT_ENSEIGNANT_MAJ);
    }

    @Test
    @Transactional
    public void checkEnseignantIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = enseignantRepository.findAll().size();
        // set the field null
        enseignant.setEnseignantId(null);

        // Create the Enseignant, which fails.

        restEnseignantMockMvc.perform(post("/api/enseignants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(enseignant)))
                .andExpect(status().isBadRequest());

        List<Enseignant> enseignants = enseignantRepository.findAll();
        assertThat(enseignants).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnseignantSesameIsRequired() throws Exception {
        int databaseSizeBeforeTest = enseignantRepository.findAll().size();
        // set the field null
        enseignant.setEnseignantSesame(null);

        // Create the Enseignant, which fails.

        restEnseignantMockMvc.perform(post("/api/enseignants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(enseignant)))
                .andExpect(status().isBadRequest());

        List<Enseignant> enseignants = enseignantRepository.findAll();
        assertThat(enseignants).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnseignantNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = enseignantRepository.findAll().size();
        // set the field null
        enseignant.setEnseignantNom(null);

        // Create the Enseignant, which fails.

        restEnseignantMockMvc.perform(post("/api/enseignants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(enseignant)))
                .andExpect(status().isBadRequest());

        List<Enseignant> enseignants = enseignantRepository.findAll();
        assertThat(enseignants).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnseignantPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = enseignantRepository.findAll().size();
        // set the field null
        enseignant.setEnseignantPrenom(null);

        // Create the Enseignant, which fails.

        restEnseignantMockMvc.perform(post("/api/enseignants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(enseignant)))
                .andExpect(status().isBadRequest());

        List<Enseignant> enseignants = enseignantRepository.findAll();
        assertThat(enseignants).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnseignantActifIsRequired() throws Exception {
        int databaseSizeBeforeTest = enseignantRepository.findAll().size();
        // set the field null
        enseignant.setEnseignantActif(null);

        // Create the Enseignant, which fails.

        restEnseignantMockMvc.perform(post("/api/enseignants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(enseignant)))
                .andExpect(status().isBadRequest());

        List<Enseignant> enseignants = enseignantRepository.findAll();
        assertThat(enseignants).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnseignants() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get all the enseignants
        restEnseignantMockMvc.perform(get("/api/enseignants?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(enseignant.getId().intValue())))
                .andExpect(jsonPath("$.[*].enseignantId").value(hasItem(DEFAULT_ENSEIGNANT_ID.intValue())))
                .andExpect(jsonPath("$.[*].enseignantSesame").value(hasItem(DEFAULT_ENSEIGNANT_SESAME.toString())))
                .andExpect(jsonPath("$.[*].enseignantSexe").value(hasItem(DEFAULT_ENSEIGNANT_SEXE.toString())))
                .andExpect(jsonPath("$.[*].enseignantNom").value(hasItem(DEFAULT_ENSEIGNANT_NOM.toString())))
                .andExpect(jsonPath("$.[*].enseignantPrenom").value(hasItem(DEFAULT_ENSEIGNANT_PRENOM.toString())))
                .andExpect(jsonPath("$.[*].enseignantAdresse").value(hasItem(DEFAULT_ENSEIGNANT_ADRESSE.toString())))
                .andExpect(jsonPath("$.[*].enseignantTelPro").value(hasItem(DEFAULT_ENSEIGNANT_TEL_PRO.toString())))
                .andExpect(jsonPath("$.[*].enseignantActif").value(hasItem(DEFAULT_ENSEIGNANT_ACTIF.booleanValue())))
                .andExpect(jsonPath("$.[*].enseignantMaj").value(hasItem(DEFAULT_ENSEIGNANT_MAJ_STR)));
    }

    @Test
    @Transactional
    public void getEnseignant() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);

        // Get the enseignant
        restEnseignantMockMvc.perform(get("/api/enseignants/{id}", enseignant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(enseignant.getId().intValue()))
            .andExpect(jsonPath("$.enseignantId").value(DEFAULT_ENSEIGNANT_ID.intValue()))
            .andExpect(jsonPath("$.enseignantSesame").value(DEFAULT_ENSEIGNANT_SESAME.toString()))
            .andExpect(jsonPath("$.enseignantSexe").value(DEFAULT_ENSEIGNANT_SEXE.toString()))
            .andExpect(jsonPath("$.enseignantNom").value(DEFAULT_ENSEIGNANT_NOM.toString()))
            .andExpect(jsonPath("$.enseignantPrenom").value(DEFAULT_ENSEIGNANT_PRENOM.toString()))
            .andExpect(jsonPath("$.enseignantAdresse").value(DEFAULT_ENSEIGNANT_ADRESSE.toString()))
            .andExpect(jsonPath("$.enseignantTelPro").value(DEFAULT_ENSEIGNANT_TEL_PRO.toString()))
            .andExpect(jsonPath("$.enseignantActif").value(DEFAULT_ENSEIGNANT_ACTIF.booleanValue()))
            .andExpect(jsonPath("$.enseignantMaj").value(DEFAULT_ENSEIGNANT_MAJ_STR));
    }

    @Test
    @Transactional
    public void getNonExistingEnseignant() throws Exception {
        // Get the enseignant
        restEnseignantMockMvc.perform(get("/api/enseignants/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnseignant() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);
        int databaseSizeBeforeUpdate = enseignantRepository.findAll().size();

        // Update the enseignant
        Enseignant updatedEnseignant = enseignantRepository.findOne(enseignant.getId());
        updatedEnseignant
                .enseignantId(UPDATED_ENSEIGNANT_ID)
                .enseignantSesame(UPDATED_ENSEIGNANT_SESAME)
                .enseignantSexe(UPDATED_ENSEIGNANT_SEXE)
                .enseignantNom(UPDATED_ENSEIGNANT_NOM)
                .enseignantPrenom(UPDATED_ENSEIGNANT_PRENOM)
                .enseignantAdresse(UPDATED_ENSEIGNANT_ADRESSE)
                .enseignantTelPro(UPDATED_ENSEIGNANT_TEL_PRO)
                .enseignantActif(UPDATED_ENSEIGNANT_ACTIF)
                .enseignantMaj(UPDATED_ENSEIGNANT_MAJ);

        restEnseignantMockMvc.perform(put("/api/enseignants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEnseignant)))
                .andExpect(status().isOk());

        // Validate the Enseignant in the database
        List<Enseignant> enseignants = enseignantRepository.findAll();
        assertThat(enseignants).hasSize(databaseSizeBeforeUpdate);
        Enseignant testEnseignant = enseignants.get(enseignants.size() - 1);
        assertThat(testEnseignant.getEnseignantId()).isEqualTo(UPDATED_ENSEIGNANT_ID);
        assertThat(testEnseignant.getEnseignantSesame()).isEqualTo(UPDATED_ENSEIGNANT_SESAME);
        assertThat(testEnseignant.getEnseignantSexe()).isEqualTo(UPDATED_ENSEIGNANT_SEXE);
        assertThat(testEnseignant.getEnseignantNom()).isEqualTo(UPDATED_ENSEIGNANT_NOM);
        assertThat(testEnseignant.getEnseignantPrenom()).isEqualTo(UPDATED_ENSEIGNANT_PRENOM);
        assertThat(testEnseignant.getEnseignantAdresse()).isEqualTo(UPDATED_ENSEIGNANT_ADRESSE);
        assertThat(testEnseignant.getEnseignantTelPro()).isEqualTo(UPDATED_ENSEIGNANT_TEL_PRO);
        assertThat(testEnseignant.isEnseignantActif()).isEqualTo(UPDATED_ENSEIGNANT_ACTIF);
        assertThat(testEnseignant.getEnseignantMaj()).isEqualTo(UPDATED_ENSEIGNANT_MAJ);
    }

    @Test
    @Transactional
    public void deleteEnseignant() throws Exception {
        // Initialize the database
        enseignantRepository.saveAndFlush(enseignant);
        int databaseSizeBeforeDelete = enseignantRepository.findAll().size();

        // Get the enseignant
        restEnseignantMockMvc.perform(delete("/api/enseignants/{id}", enseignant.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Enseignant> enseignants = enseignantRepository.findAll();
        assertThat(enseignants).hasSize(databaseSizeBeforeDelete - 1);
    }
}
