package fr.istic.web.rest;

import fr.istic.ProjetTaaGliApp;

import fr.istic.domain.Etudiant;
import fr.istic.repository.EtudiantRepository;

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
 * Test class for the EtudiantResource REST controller.
 *
 * @see EtudiantResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = ProjetTaaGliApp.class)

public class EtudiantResourceIntTest {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));
    private static final String DEFAULT_ETU_ID = "AAAAA";
    private static final String UPDATED_ETU_ID = "BBBBB";
    private static final String DEFAULT_ETU_NOM = "AAAAA";
    private static final String UPDATED_ETU_NOM = "BBBBB";
    private static final String DEFAULT_ETU_PRENOM = "AAAAA";
    private static final String UPDATED_ETU_PRENOM = "BBBBB";
    private static final String DEFAULT_ETU_RUE = "AAAAA";
    private static final String UPDATED_ETU_RUE = "BBBBB";
    private static final String DEFAULT_ETU_VILLE = "AAAAA";
    private static final String UPDATED_ETU_VILLE = "BBBBB";
    private static final String DEFAULT_ETU_CODE_DEP = "AAAAA";
    private static final String UPDATED_ETU_CODE_DEP = "BBBBB";
    private static final String DEFAULT_ETU_TEL_PERSO = "AAAAA";
    private static final String UPDATED_ETU_TEL_PERSO = "BBBBB";
    private static final String DEFAULT_ETU_TEL_MOB = "AAAAA";
    private static final String UPDATED_ETU_TEL_MOB = "BBBBB";
    private static final String DEFAULT_ETU_PREMIER_EMPLOI = "AAAAA";
    private static final String UPDATED_ETU_PREMIER_EMPLOI = "BBBBB";
    private static final String DEFAULT_ETU_DERNIER_EMPLOI = "AAAAA";
    private static final String UPDATED_ETU_DERNIER_EMPLOI = "BBBBB";

    private static final Boolean DEFAULT_ETU_RECH_EMP = false;
    private static final Boolean UPDATED_ETU_RECH_EMP = true;

    private static final ZonedDateTime DEFAULT_ETU_DATE_MAJ = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_ETU_DATE_MAJ = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_ETU_DATE_MAJ_STR = dateTimeFormatter.format(DEFAULT_ETU_DATE_MAJ);

    @Inject
    private EtudiantRepository etudiantRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEtudiantMockMvc;

    private Etudiant etudiant;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EtudiantResource etudiantResource = new EtudiantResource();
        ReflectionTestUtils.setField(etudiantResource, "etudiantRepository", etudiantRepository);
        this.restEtudiantMockMvc = MockMvcBuilders.standaloneSetup(etudiantResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Etudiant createEntity(EntityManager em) {
        Etudiant etudiant = new Etudiant()
                .etuId(DEFAULT_ETU_ID)
                .etuNom(DEFAULT_ETU_NOM)
                .etuPrenom(DEFAULT_ETU_PRENOM)
                .etuRue(DEFAULT_ETU_RUE)
                .etuVille(DEFAULT_ETU_VILLE)
                .etuCodeDep(DEFAULT_ETU_CODE_DEP)
                .etuTelPerso(DEFAULT_ETU_TEL_PERSO)
                .etuTelMob(DEFAULT_ETU_TEL_MOB)
                .etuPremierEmploi(DEFAULT_ETU_PREMIER_EMPLOI)
                .etuDernierEmploi(DEFAULT_ETU_DERNIER_EMPLOI)
                .etuRechEmp(DEFAULT_ETU_RECH_EMP)
                .etuDateMaj(DEFAULT_ETU_DATE_MAJ);
        return etudiant;
    }

    @Before
    public void initTest() {
        etudiant = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtudiant() throws Exception {
        int databaseSizeBeforeCreate = etudiantRepository.findAll().size();

        // Create the Etudiant

        restEtudiantMockMvc.perform(post("/api/etudiants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(etudiant)))
                .andExpect(status().isCreated());

        // Validate the Etudiant in the database
        List<Etudiant> etudiants = etudiantRepository.findAll();
        assertThat(etudiants).hasSize(databaseSizeBeforeCreate + 1);
        Etudiant testEtudiant = etudiants.get(etudiants.size() - 1);
        assertThat(testEtudiant.getEtuId()).isEqualTo(DEFAULT_ETU_ID);
        assertThat(testEtudiant.getEtuNom()).isEqualTo(DEFAULT_ETU_NOM);
        assertThat(testEtudiant.getEtuPrenom()).isEqualTo(DEFAULT_ETU_PRENOM);
        assertThat(testEtudiant.getEtuRue()).isEqualTo(DEFAULT_ETU_RUE);
        assertThat(testEtudiant.getEtuVille()).isEqualTo(DEFAULT_ETU_VILLE);
        assertThat(testEtudiant.getEtuCodeDep()).isEqualTo(DEFAULT_ETU_CODE_DEP);
        assertThat(testEtudiant.getEtuTelPerso()).isEqualTo(DEFAULT_ETU_TEL_PERSO);
        assertThat(testEtudiant.getEtuTelMob()).isEqualTo(DEFAULT_ETU_TEL_MOB);
        assertThat(testEtudiant.getEtuPremierEmploi()).isEqualTo(DEFAULT_ETU_PREMIER_EMPLOI);
        assertThat(testEtudiant.getEtuDernierEmploi()).isEqualTo(DEFAULT_ETU_DERNIER_EMPLOI);
        assertThat(testEtudiant.isEtuRechEmp()).isEqualTo(DEFAULT_ETU_RECH_EMP);
        assertThat(testEtudiant.getEtuDateMaj()).isEqualTo(DEFAULT_ETU_DATE_MAJ);
    }

    @Test
    @Transactional
    public void checkEtuIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = etudiantRepository.findAll().size();
        // set the field null
        etudiant.setEtuId(null);

        // Create the Etudiant, which fails.

        restEtudiantMockMvc.perform(post("/api/etudiants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(etudiant)))
                .andExpect(status().isBadRequest());

        List<Etudiant> etudiants = etudiantRepository.findAll();
        assertThat(etudiants).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEtuNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = etudiantRepository.findAll().size();
        // set the field null
        etudiant.setEtuNom(null);

        // Create the Etudiant, which fails.

        restEtudiantMockMvc.perform(post("/api/etudiants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(etudiant)))
                .andExpect(status().isBadRequest());

        List<Etudiant> etudiants = etudiantRepository.findAll();
        assertThat(etudiants).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEtuPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = etudiantRepository.findAll().size();
        // set the field null
        etudiant.setEtuPrenom(null);

        // Create the Etudiant, which fails.

        restEtudiantMockMvc.perform(post("/api/etudiants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(etudiant)))
                .andExpect(status().isBadRequest());

        List<Etudiant> etudiants = etudiantRepository.findAll();
        assertThat(etudiants).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEtuDateMajIsRequired() throws Exception {
        int databaseSizeBeforeTest = etudiantRepository.findAll().size();
        // set the field null
        etudiant.setEtuDateMaj(null);

        // Create the Etudiant, which fails.

        restEtudiantMockMvc.perform(post("/api/etudiants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(etudiant)))
                .andExpect(status().isBadRequest());

        List<Etudiant> etudiants = etudiantRepository.findAll();
        assertThat(etudiants).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEtudiants() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get all the etudiants
        restEtudiantMockMvc.perform(get("/api/etudiants?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(etudiant.getId().intValue())))
                .andExpect(jsonPath("$.[*].etuId").value(hasItem(DEFAULT_ETU_ID.toString())))
                .andExpect(jsonPath("$.[*].etuNom").value(hasItem(DEFAULT_ETU_NOM.toString())))
                .andExpect(jsonPath("$.[*].etuPrenom").value(hasItem(DEFAULT_ETU_PRENOM.toString())))
                .andExpect(jsonPath("$.[*].etuRue").value(hasItem(DEFAULT_ETU_RUE.toString())))
                .andExpect(jsonPath("$.[*].etuVille").value(hasItem(DEFAULT_ETU_VILLE.toString())))
                .andExpect(jsonPath("$.[*].etuCodeDep").value(hasItem(DEFAULT_ETU_CODE_DEP.toString())))
                .andExpect(jsonPath("$.[*].etuTelPerso").value(hasItem(DEFAULT_ETU_TEL_PERSO.toString())))
                .andExpect(jsonPath("$.[*].etuTelMob").value(hasItem(DEFAULT_ETU_TEL_MOB.toString())))
                .andExpect(jsonPath("$.[*].etuPremierEmploi").value(hasItem(DEFAULT_ETU_PREMIER_EMPLOI.toString())))
                .andExpect(jsonPath("$.[*].etuDernierEmploi").value(hasItem(DEFAULT_ETU_DERNIER_EMPLOI.toString())))
                .andExpect(jsonPath("$.[*].etuRechEmp").value(hasItem(DEFAULT_ETU_RECH_EMP.booleanValue())))
                .andExpect(jsonPath("$.[*].etuDateMaj").value(hasItem(DEFAULT_ETU_DATE_MAJ_STR)));
    }

    @Test
    @Transactional
    public void getEtudiant() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);

        // Get the etudiant
        restEtudiantMockMvc.perform(get("/api/etudiants/{id}", etudiant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(etudiant.getId().intValue()))
            .andExpect(jsonPath("$.etuId").value(DEFAULT_ETU_ID.toString()))
            .andExpect(jsonPath("$.etuNom").value(DEFAULT_ETU_NOM.toString()))
            .andExpect(jsonPath("$.etuPrenom").value(DEFAULT_ETU_PRENOM.toString()))
            .andExpect(jsonPath("$.etuRue").value(DEFAULT_ETU_RUE.toString()))
            .andExpect(jsonPath("$.etuVille").value(DEFAULT_ETU_VILLE.toString()))
            .andExpect(jsonPath("$.etuCodeDep").value(DEFAULT_ETU_CODE_DEP.toString()))
            .andExpect(jsonPath("$.etuTelPerso").value(DEFAULT_ETU_TEL_PERSO.toString()))
            .andExpect(jsonPath("$.etuTelMob").value(DEFAULT_ETU_TEL_MOB.toString()))
            .andExpect(jsonPath("$.etuPremierEmploi").value(DEFAULT_ETU_PREMIER_EMPLOI.toString()))
            .andExpect(jsonPath("$.etuDernierEmploi").value(DEFAULT_ETU_DERNIER_EMPLOI.toString()))
            .andExpect(jsonPath("$.etuRechEmp").value(DEFAULT_ETU_RECH_EMP.booleanValue()))
            .andExpect(jsonPath("$.etuDateMaj").value(DEFAULT_ETU_DATE_MAJ_STR));
    }

    @Test
    @Transactional
    public void getNonExistingEtudiant() throws Exception {
        // Get the etudiant
        restEtudiantMockMvc.perform(get("/api/etudiants/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtudiant() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);
        int databaseSizeBeforeUpdate = etudiantRepository.findAll().size();

        // Update the etudiant
        Etudiant updatedEtudiant = etudiantRepository.findOne(etudiant.getId());
        updatedEtudiant
                .etuId(UPDATED_ETU_ID)
                .etuNom(UPDATED_ETU_NOM)
                .etuPrenom(UPDATED_ETU_PRENOM)
                .etuRue(UPDATED_ETU_RUE)
                .etuVille(UPDATED_ETU_VILLE)
                .etuCodeDep(UPDATED_ETU_CODE_DEP)
                .etuTelPerso(UPDATED_ETU_TEL_PERSO)
                .etuTelMob(UPDATED_ETU_TEL_MOB)
                .etuPremierEmploi(UPDATED_ETU_PREMIER_EMPLOI)
                .etuDernierEmploi(UPDATED_ETU_DERNIER_EMPLOI)
                .etuRechEmp(UPDATED_ETU_RECH_EMP)
                .etuDateMaj(UPDATED_ETU_DATE_MAJ);

        restEtudiantMockMvc.perform(put("/api/etudiants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEtudiant)))
                .andExpect(status().isOk());

        // Validate the Etudiant in the database
        List<Etudiant> etudiants = etudiantRepository.findAll();
        assertThat(etudiants).hasSize(databaseSizeBeforeUpdate);
        Etudiant testEtudiant = etudiants.get(etudiants.size() - 1);
        assertThat(testEtudiant.getEtuId()).isEqualTo(UPDATED_ETU_ID);
        assertThat(testEtudiant.getEtuNom()).isEqualTo(UPDATED_ETU_NOM);
        assertThat(testEtudiant.getEtuPrenom()).isEqualTo(UPDATED_ETU_PRENOM);
        assertThat(testEtudiant.getEtuRue()).isEqualTo(UPDATED_ETU_RUE);
        assertThat(testEtudiant.getEtuVille()).isEqualTo(UPDATED_ETU_VILLE);
        assertThat(testEtudiant.getEtuCodeDep()).isEqualTo(UPDATED_ETU_CODE_DEP);
        assertThat(testEtudiant.getEtuTelPerso()).isEqualTo(UPDATED_ETU_TEL_PERSO);
        assertThat(testEtudiant.getEtuTelMob()).isEqualTo(UPDATED_ETU_TEL_MOB);
        assertThat(testEtudiant.getEtuPremierEmploi()).isEqualTo(UPDATED_ETU_PREMIER_EMPLOI);
        assertThat(testEtudiant.getEtuDernierEmploi()).isEqualTo(UPDATED_ETU_DERNIER_EMPLOI);
        assertThat(testEtudiant.isEtuRechEmp()).isEqualTo(UPDATED_ETU_RECH_EMP);
        assertThat(testEtudiant.getEtuDateMaj()).isEqualTo(UPDATED_ETU_DATE_MAJ);
    }

    @Test
    @Transactional
    public void deleteEtudiant() throws Exception {
        // Initialize the database
        etudiantRepository.saveAndFlush(etudiant);
        int databaseSizeBeforeDelete = etudiantRepository.findAll().size();

        // Get the etudiant
        restEtudiantMockMvc.perform(delete("/api/etudiants/{id}", etudiant.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Etudiant> etudiants = etudiantRepository.findAll();
        assertThat(etudiants).hasSize(databaseSizeBeforeDelete - 1);
    }
}
