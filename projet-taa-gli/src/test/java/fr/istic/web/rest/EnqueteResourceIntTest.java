package fr.istic.web.rest;

import fr.istic.ProjetTaaGliApp;

import fr.istic.domain.Enquete;
import fr.istic.repository.EnqueteRepository;

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
 * Test class for the EnqueteResource REST controller.
 *
 * @see EnqueteResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = ProjetTaaGliApp.class)

public class EnqueteResourceIntTest {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final Long DEFAULT_ENQ_ID = 1L;
    private static final Long UPDATED_ENQ_ID = 2L;
    private static final String DEFAULT_MODE_ENQUETE = "AAAAA";
    private static final String UPDATED_MODE_ENQUETE = "BBBBB";

    private static final ZonedDateTime DEFAULT_DATE_ENQUETE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE_ENQUETE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_ENQUETE_STR = dateTimeFormatter.format(DEFAULT_DATE_ENQUETE);
    private static final String DEFAULT_ETU_NOM = "AAAAA";
    private static final String UPDATED_ETU_NOM = "BBBBB";
    private static final String DEFAULT_ETU_RUE = "AAAAA";
    private static final String UPDATED_ETU_RUE = "BBBBB";
    private static final String DEFAULT_ETU_VILLE = "AAAAA";
    private static final String UPDATED_ETU_VILLE = "BBBBB";
    private static final String DEFAULT_ETU_CODE_DEP = "AAAAA";
    private static final String UPDATED_ETU_CODE_DEP = "BBBBB";

    private static final ZonedDateTime DEFAULT_ENQ_DATE_DEBUT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_ENQ_DATE_DEBUT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_ENQ_DATE_DEBUT_STR = dateTimeFormatter.format(DEFAULT_ENQ_DATE_DEBUT);
    private static final String DEFAULT_ENQ_DUREE_RECHERCHE = "AAAAA";
    private static final String UPDATED_ENQ_DUREE_RECHERCHE = "BBBBB";

    private static final Long DEFAULT_ENQ_QUANTITE = 1L;
    private static final Long UPDATED_ENQ_QUANTITE = 2L;
    private static final String DEFAULT_ENQ_QUESTION = "AAAAA";
    private static final String UPDATED_ENQ_QUESTION = "BBBBB";
    private static final String DEFAULT_ENQ_REPONSE = "AAAAA";
    private static final String UPDATED_ENQ_REPONSE = "BBBBB";

    private static final Long DEFAULT_ENQ_SALAIRE = 1L;
    private static final Long UPDATED_ENQ_SALAIRE = 2L;
    private static final String DEFAULT_ENQ_SALAIRE_DEVISE = "AAAAA";
    private static final String UPDATED_ENQ_SALAIRE_DEVISE = "BBBBB";

    @Inject
    private EnqueteRepository enqueteRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEnqueteMockMvc;

    private Enquete enquete;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EnqueteResource enqueteResource = new EnqueteResource();
        ReflectionTestUtils.setField(enqueteResource, "enqueteRepository", enqueteRepository);
        this.restEnqueteMockMvc = MockMvcBuilders.standaloneSetup(enqueteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enquete createEntity(EntityManager em) {
        Enquete enquete = new Enquete()
                .modeEnquete(DEFAULT_MODE_ENQUETE)
                .dateEnquete(DEFAULT_DATE_ENQUETE)
                .etuNom(DEFAULT_ETU_NOM)
                .etuRue(DEFAULT_ETU_RUE)
                .etuVille(DEFAULT_ETU_VILLE)
                .etuCodeDep(DEFAULT_ETU_CODE_DEP)
                .enqDateDebut(DEFAULT_ENQ_DATE_DEBUT)
                .enqDureeRecherche(DEFAULT_ENQ_DUREE_RECHERCHE)
                .enqQuantite(DEFAULT_ENQ_QUANTITE)
                .enqQuestion(DEFAULT_ENQ_QUESTION)
                .enqReponse(DEFAULT_ENQ_REPONSE)
                .enqSalaire(DEFAULT_ENQ_SALAIRE)
                .enqSalaireDevise(DEFAULT_ENQ_SALAIRE_DEVISE);
        return enquete;
    }

    @Before
    public void initTest() {
        enquete = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnquete() throws Exception {
        int databaseSizeBeforeCreate = enqueteRepository.findAll().size();

        // Create the Enquete

        restEnqueteMockMvc.perform(post("/api/enquetes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(enquete)))
                .andExpect(status().isCreated());

        // Validate the Enquete in the database
        List<Enquete> enquetes = enqueteRepository.findAll();
        assertThat(enquetes).hasSize(databaseSizeBeforeCreate + 1);
        Enquete testEnquete = enquetes.get(enquetes.size() - 1);
        assertThat(testEnquete.getModeEnquete()).isEqualTo(DEFAULT_MODE_ENQUETE);
        assertThat(testEnquete.getDateEnquete()).isEqualTo(DEFAULT_DATE_ENQUETE);
        assertThat(testEnquete.getEtuNom()).isEqualTo(DEFAULT_ETU_NOM);
        assertThat(testEnquete.getEtuRue()).isEqualTo(DEFAULT_ETU_RUE);
        assertThat(testEnquete.getEtuVille()).isEqualTo(DEFAULT_ETU_VILLE);
        assertThat(testEnquete.getEtuCodeDep()).isEqualTo(DEFAULT_ETU_CODE_DEP);
        assertThat(testEnquete.getEnqDateDebut()).isEqualTo(DEFAULT_ENQ_DATE_DEBUT);
        assertThat(testEnquete.getEnqDureeRecherche()).isEqualTo(DEFAULT_ENQ_DUREE_RECHERCHE);
        assertThat(testEnquete.getEnqQuantite()).isEqualTo(DEFAULT_ENQ_QUANTITE);
        assertThat(testEnquete.getEnqQuestion()).isEqualTo(DEFAULT_ENQ_QUESTION);
        assertThat(testEnquete.getEnqReponse()).isEqualTo(DEFAULT_ENQ_REPONSE);
        assertThat(testEnquete.getEnqSalaire()).isEqualTo(DEFAULT_ENQ_SALAIRE);
        assertThat(testEnquete.getEnqSalaireDevise()).isEqualTo(DEFAULT_ENQ_SALAIRE_DEVISE);
    }

    @Test
    @Transactional
    public void checkEnqIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = enqueteRepository.findAll().size();
        // set the field null

        // Create the Enquete, which fails.

        restEnqueteMockMvc.perform(post("/api/enquetes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(enquete)))
                .andExpect(status().isBadRequest());

        List<Enquete> enquetes = enqueteRepository.findAll();
        assertThat(enquetes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateEnqueteIsRequired() throws Exception {
        int databaseSizeBeforeTest = enqueteRepository.findAll().size();
        // set the field null
        enquete.setDateEnquete(null);

        // Create the Enquete, which fails.

        restEnqueteMockMvc.perform(post("/api/enquetes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(enquete)))
                .andExpect(status().isBadRequest());

        List<Enquete> enquetes = enqueteRepository.findAll();
        assertThat(enquetes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEtuNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = enqueteRepository.findAll().size();
        // set the field null
        enquete.setEtuNom(null);

        // Create the Enquete, which fails.

        restEnqueteMockMvc.perform(post("/api/enquetes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(enquete)))
                .andExpect(status().isBadRequest());

        List<Enquete> enquetes = enqueteRepository.findAll();
        assertThat(enquetes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnqDateDebutIsRequired() throws Exception {
        int databaseSizeBeforeTest = enqueteRepository.findAll().size();
        // set the field null
        enquete.setEnqDateDebut(null);

        // Create the Enquete, which fails.

        restEnqueteMockMvc.perform(post("/api/enquetes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(enquete)))
                .andExpect(status().isBadRequest());

        List<Enquete> enquetes = enqueteRepository.findAll();
        assertThat(enquetes).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnquetes() throws Exception {
        // Initialize the database
        enqueteRepository.saveAndFlush(enquete);

        // Get all the enquetes
        restEnqueteMockMvc.perform(get("/api/enquetes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(enquete.getId().intValue())))
                .andExpect(jsonPath("$.[*].enqId").value(hasItem(DEFAULT_ENQ_ID.intValue())))
                .andExpect(jsonPath("$.[*].modeEnquete").value(hasItem(DEFAULT_MODE_ENQUETE.toString())))
                .andExpect(jsonPath("$.[*].dateEnquete").value(hasItem(DEFAULT_DATE_ENQUETE_STR)))
                .andExpect(jsonPath("$.[*].etuNom").value(hasItem(DEFAULT_ETU_NOM.toString())))
                .andExpect(jsonPath("$.[*].etuRue").value(hasItem(DEFAULT_ETU_RUE.toString())))
                .andExpect(jsonPath("$.[*].etuVille").value(hasItem(DEFAULT_ETU_VILLE.toString())))
                .andExpect(jsonPath("$.[*].etuCodeDep").value(hasItem(DEFAULT_ETU_CODE_DEP.toString())))
                .andExpect(jsonPath("$.[*].enqDateDebut").value(hasItem(DEFAULT_ENQ_DATE_DEBUT_STR)))
                .andExpect(jsonPath("$.[*].enqDureeRecherche").value(hasItem(DEFAULT_ENQ_DUREE_RECHERCHE.toString())))
                .andExpect(jsonPath("$.[*].enqQuantite").value(hasItem(DEFAULT_ENQ_QUANTITE.intValue())))
                .andExpect(jsonPath("$.[*].enqQuestion").value(hasItem(DEFAULT_ENQ_QUESTION.toString())))
                .andExpect(jsonPath("$.[*].enqReponse").value(hasItem(DEFAULT_ENQ_REPONSE.toString())))
                .andExpect(jsonPath("$.[*].enqSalaire").value(hasItem(DEFAULT_ENQ_SALAIRE.intValue())))
                .andExpect(jsonPath("$.[*].enqSalaireDevise").value(hasItem(DEFAULT_ENQ_SALAIRE_DEVISE.toString())));
    }

    @Test
    @Transactional
    public void getEnquete() throws Exception {
        // Initialize the database
        enqueteRepository.saveAndFlush(enquete);

        // Get the enquete
        restEnqueteMockMvc.perform(get("/api/enquetes/{id}", enquete.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(enquete.getId().intValue()))
            .andExpect(jsonPath("$.enqId").value(DEFAULT_ENQ_ID.intValue()))
            .andExpect(jsonPath("$.modeEnquete").value(DEFAULT_MODE_ENQUETE.toString()))
            .andExpect(jsonPath("$.dateEnquete").value(DEFAULT_DATE_ENQUETE_STR))
            .andExpect(jsonPath("$.etuNom").value(DEFAULT_ETU_NOM.toString()))
            .andExpect(jsonPath("$.etuRue").value(DEFAULT_ETU_RUE.toString()))
            .andExpect(jsonPath("$.etuVille").value(DEFAULT_ETU_VILLE.toString()))
            .andExpect(jsonPath("$.etuCodeDep").value(DEFAULT_ETU_CODE_DEP.toString()))
            .andExpect(jsonPath("$.enqDateDebut").value(DEFAULT_ENQ_DATE_DEBUT_STR))
            .andExpect(jsonPath("$.enqDureeRecherche").value(DEFAULT_ENQ_DUREE_RECHERCHE.toString()))
            .andExpect(jsonPath("$.enqQuantite").value(DEFAULT_ENQ_QUANTITE.intValue()))
            .andExpect(jsonPath("$.enqQuestion").value(DEFAULT_ENQ_QUESTION.toString()))
            .andExpect(jsonPath("$.enqReponse").value(DEFAULT_ENQ_REPONSE.toString()))
            .andExpect(jsonPath("$.enqSalaire").value(DEFAULT_ENQ_SALAIRE.intValue()))
            .andExpect(jsonPath("$.enqSalaireDevise").value(DEFAULT_ENQ_SALAIRE_DEVISE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEnquete() throws Exception {
        // Get the enquete
        restEnqueteMockMvc.perform(get("/api/enquetes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnquete() throws Exception {
        // Initialize the database
        enqueteRepository.saveAndFlush(enquete);
        int databaseSizeBeforeUpdate = enqueteRepository.findAll().size();

        // Update the enquete
        Enquete updatedEnquete = enqueteRepository.findOne(enquete.getId());
        updatedEnquete
                .modeEnquete(UPDATED_MODE_ENQUETE)
                .dateEnquete(UPDATED_DATE_ENQUETE)
                .etuNom(UPDATED_ETU_NOM)
                .etuRue(UPDATED_ETU_RUE)
                .etuVille(UPDATED_ETU_VILLE)
                .etuCodeDep(UPDATED_ETU_CODE_DEP)
                .enqDateDebut(UPDATED_ENQ_DATE_DEBUT)
                .enqDureeRecherche(UPDATED_ENQ_DUREE_RECHERCHE)
                .enqQuantite(UPDATED_ENQ_QUANTITE)
                .enqQuestion(UPDATED_ENQ_QUESTION)
                .enqReponse(UPDATED_ENQ_REPONSE)
                .enqSalaire(UPDATED_ENQ_SALAIRE)
                .enqSalaireDevise(UPDATED_ENQ_SALAIRE_DEVISE);

        restEnqueteMockMvc.perform(put("/api/enquetes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEnquete)))
                .andExpect(status().isOk());

        // Validate the Enquete in the database
        List<Enquete> enquetes = enqueteRepository.findAll();
        assertThat(enquetes).hasSize(databaseSizeBeforeUpdate);
        Enquete testEnquete = enquetes.get(enquetes.size() - 1);
        assertThat(testEnquete.getModeEnquete()).isEqualTo(UPDATED_MODE_ENQUETE);
        assertThat(testEnquete.getDateEnquete()).isEqualTo(UPDATED_DATE_ENQUETE);
        assertThat(testEnquete.getEtuNom()).isEqualTo(UPDATED_ETU_NOM);
        assertThat(testEnquete.getEtuRue()).isEqualTo(UPDATED_ETU_RUE);
        assertThat(testEnquete.getEtuVille()).isEqualTo(UPDATED_ETU_VILLE);
        assertThat(testEnquete.getEtuCodeDep()).isEqualTo(UPDATED_ETU_CODE_DEP);
        assertThat(testEnquete.getEnqDateDebut()).isEqualTo(UPDATED_ENQ_DATE_DEBUT);
        assertThat(testEnquete.getEnqDureeRecherche()).isEqualTo(UPDATED_ENQ_DUREE_RECHERCHE);
        assertThat(testEnquete.getEnqQuantite()).isEqualTo(UPDATED_ENQ_QUANTITE);
        assertThat(testEnquete.getEnqQuestion()).isEqualTo(UPDATED_ENQ_QUESTION);
        assertThat(testEnquete.getEnqReponse()).isEqualTo(UPDATED_ENQ_REPONSE);
        assertThat(testEnquete.getEnqSalaire()).isEqualTo(UPDATED_ENQ_SALAIRE);
        assertThat(testEnquete.getEnqSalaireDevise()).isEqualTo(UPDATED_ENQ_SALAIRE_DEVISE);
    }

    @Test
    @Transactional
    public void deleteEnquete() throws Exception {
        // Initialize the database
        enqueteRepository.saveAndFlush(enquete);
        int databaseSizeBeforeDelete = enqueteRepository.findAll().size();

        // Get the enquete
        restEnqueteMockMvc.perform(delete("/api/enquetes/{id}", enquete.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Enquete> enquetes = enqueteRepository.findAll();
        assertThat(enquetes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
