package fr.istic.web.rest;

import fr.istic.ProjetTaaGliApp;

import fr.istic.domain.Partenaire;
import fr.istic.repository.PartenaireRepository;

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
 * Test class for the PartenaireResource REST controller.
 *
 * @see PartenaireResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = ProjetTaaGliApp.class)

public class PartenaireResourceIntTest {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final Long DEFAULT_P_ID = 1L;
    private static final Long UPDATED_P_ID = 2L;
    private static final String DEFAULT_P_NUM = "AAAAA";
    private static final String UPDATED_P_NUM = "BBBBB";
    private static final String DEFAULT_P_SIRET = "AAAAA";
    private static final String UPDATED_P_SIRET = "BBBBB";
    private static final String DEFAULT_P_SERVICE = "AAAAA";
    private static final String UPDATED_P_SERVICE = "BBBBB";
    private static final String DEFAULT_P_REGION = "AAAAA";
    private static final String UPDATED_P_REGION = "BBBBB";
    private static final String DEFAULT_P_CODE_ACTIVITY = "AAAAA";
    private static final String UPDATED_P_CODE_ACTIVITY = "BBBBB";
    private static final String DEFAULT_P_RUE = "AAAAA";
    private static final String UPDATED_P_RUE = "BBBBB";
    private static final String DEFAULT_P_CPLT_RUE = "AAAAA";
    private static final String UPDATED_P_CPLT_RUE = "BBBBB";
    private static final String DEFAULT_P_CODE_DEP = "AAAAA";
    private static final String UPDATED_P_CODE_DEP = "BBBBB";
    private static final String DEFAULT_P_VILLE = "AAAAA";
    private static final String UPDATED_P_VILLE = "BBBBB";
    private static final String DEFAULT_P_TEL_STD = "AAAAA";
    private static final String UPDATED_P_TEL_STD = "BBBBB";
    private static final String DEFAULT_P_URL = "AAAAA";
    private static final String UPDATED_P_URL = "BBBBB";
    private static final String DEFAULT_P_COMMENTAIRE = "AAAAA";
    private static final String UPDATED_P_COMMENTAIRE = "BBBBB";
    private static final String DEFAULT_P_NOM_SIGNATAIRE = "AAAAA";
    private static final String UPDATED_P_NOM_SIGNATAIRE = "BBBBB";
    private static final String DEFAULT_P_EFFECTIF = "AAAAA";
    private static final String UPDATED_P_EFFECTIF = "BBBBB";

    private static final ZonedDateTime DEFAULT_P_DATE_MAJ = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_P_DATE_MAJ = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_P_DATE_MAJ_STR = dateTimeFormatter.format(DEFAULT_P_DATE_MAJ);

    @Inject
    private PartenaireRepository partenaireRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPartenaireMockMvc;

    private Partenaire partenaire;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PartenaireResource partenaireResource = new PartenaireResource();
        ReflectionTestUtils.setField(partenaireResource, "partenaireRepository", partenaireRepository);
        this.restPartenaireMockMvc = MockMvcBuilders.standaloneSetup(partenaireResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partenaire createEntity(EntityManager em) {
        Partenaire partenaire = new Partenaire()
                .pId(DEFAULT_P_ID)
                .pNum(DEFAULT_P_NUM)
                .pSiret(DEFAULT_P_SIRET)
                .pService(DEFAULT_P_SERVICE)
                .pRegion(DEFAULT_P_REGION)
                .pCodeActivity(DEFAULT_P_CODE_ACTIVITY)
                .pRue(DEFAULT_P_RUE)
                .pCpltRue(DEFAULT_P_CPLT_RUE)
                .pCodeDep(DEFAULT_P_CODE_DEP)
                .pVille(DEFAULT_P_VILLE)
                .pTelStd(DEFAULT_P_TEL_STD)
                .pUrl(DEFAULT_P_URL)
                .pCommentaire(DEFAULT_P_COMMENTAIRE)
                .pNomSignataire(DEFAULT_P_NOM_SIGNATAIRE)
                .pEffectif(DEFAULT_P_EFFECTIF)
                .pDateMaj(DEFAULT_P_DATE_MAJ);
        return partenaire;
    }

    @Before
    public void initTest() {
        partenaire = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartenaire() throws Exception {
        int databaseSizeBeforeCreate = partenaireRepository.findAll().size();

        // Create the Partenaire

        restPartenaireMockMvc.perform(post("/api/partenaires")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partenaire)))
                .andExpect(status().isCreated());

        // Validate the Partenaire in the database
        List<Partenaire> partenaires = partenaireRepository.findAll();
        assertThat(partenaires).hasSize(databaseSizeBeforeCreate + 1);
        Partenaire testPartenaire = partenaires.get(partenaires.size() - 1);
        assertThat(testPartenaire.getpId()).isEqualTo(DEFAULT_P_ID);
        assertThat(testPartenaire.getpNum()).isEqualTo(DEFAULT_P_NUM);
        assertThat(testPartenaire.getpSiret()).isEqualTo(DEFAULT_P_SIRET);
        assertThat(testPartenaire.getpService()).isEqualTo(DEFAULT_P_SERVICE);
        assertThat(testPartenaire.getpRegion()).isEqualTo(DEFAULT_P_REGION);
        assertThat(testPartenaire.getpCodeActivity()).isEqualTo(DEFAULT_P_CODE_ACTIVITY);
        assertThat(testPartenaire.getpRue()).isEqualTo(DEFAULT_P_RUE);
        assertThat(testPartenaire.getpCpltRue()).isEqualTo(DEFAULT_P_CPLT_RUE);
        assertThat(testPartenaire.getpCodeDep()).isEqualTo(DEFAULT_P_CODE_DEP);
        assertThat(testPartenaire.getpVille()).isEqualTo(DEFAULT_P_VILLE);
        assertThat(testPartenaire.getpTelStd()).isEqualTo(DEFAULT_P_TEL_STD);
        assertThat(testPartenaire.getpUrl()).isEqualTo(DEFAULT_P_URL);
        assertThat(testPartenaire.getpCommentaire()).isEqualTo(DEFAULT_P_COMMENTAIRE);
        assertThat(testPartenaire.getpNomSignataire()).isEqualTo(DEFAULT_P_NOM_SIGNATAIRE);
        assertThat(testPartenaire.getpEffectif()).isEqualTo(DEFAULT_P_EFFECTIF);
        assertThat(testPartenaire.getpDateMaj()).isEqualTo(DEFAULT_P_DATE_MAJ);
    }

    @Test
    @Transactional
    public void checkpIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = partenaireRepository.findAll().size();
        // set the field null
        partenaire.setpId(null);

        // Create the Partenaire, which fails.

        restPartenaireMockMvc.perform(post("/api/partenaires")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partenaire)))
                .andExpect(status().isBadRequest());

        List<Partenaire> partenaires = partenaireRepository.findAll();
        assertThat(partenaires).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkpNumIsRequired() throws Exception {
        int databaseSizeBeforeTest = partenaireRepository.findAll().size();
        // set the field null
        partenaire.setpNum(null);

        // Create the Partenaire, which fails.

        restPartenaireMockMvc.perform(post("/api/partenaires")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partenaire)))
                .andExpect(status().isBadRequest());

        List<Partenaire> partenaires = partenaireRepository.findAll();
        assertThat(partenaires).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkpRegionIsRequired() throws Exception {
        int databaseSizeBeforeTest = partenaireRepository.findAll().size();
        // set the field null
        partenaire.setpRegion(null);

        // Create the Partenaire, which fails.

        restPartenaireMockMvc.perform(post("/api/partenaires")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partenaire)))
                .andExpect(status().isBadRequest());

        List<Partenaire> partenaires = partenaireRepository.findAll();
        assertThat(partenaires).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkpCodeActivityIsRequired() throws Exception {
        int databaseSizeBeforeTest = partenaireRepository.findAll().size();
        // set the field null
        partenaire.setpCodeActivity(null);

        // Create the Partenaire, which fails.

        restPartenaireMockMvc.perform(post("/api/partenaires")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partenaire)))
                .andExpect(status().isBadRequest());

        List<Partenaire> partenaires = partenaireRepository.findAll();
        assertThat(partenaires).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkpCodeDepIsRequired() throws Exception {
        int databaseSizeBeforeTest = partenaireRepository.findAll().size();
        // set the field null
        partenaire.setpCodeDep(null);

        // Create the Partenaire, which fails.

        restPartenaireMockMvc.perform(post("/api/partenaires")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partenaire)))
                .andExpect(status().isBadRequest());

        List<Partenaire> partenaires = partenaireRepository.findAll();
        assertThat(partenaires).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkpVilleIsRequired() throws Exception {
        int databaseSizeBeforeTest = partenaireRepository.findAll().size();
        // set the field null
        partenaire.setpVille(null);

        // Create the Partenaire, which fails.

        restPartenaireMockMvc.perform(post("/api/partenaires")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partenaire)))
                .andExpect(status().isBadRequest());

        List<Partenaire> partenaires = partenaireRepository.findAll();
        assertThat(partenaires).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkpDateMajIsRequired() throws Exception {
        int databaseSizeBeforeTest = partenaireRepository.findAll().size();
        // set the field null
        partenaire.setpDateMaj(null);

        // Create the Partenaire, which fails.

        restPartenaireMockMvc.perform(post("/api/partenaires")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partenaire)))
                .andExpect(status().isBadRequest());

        List<Partenaire> partenaires = partenaireRepository.findAll();
        assertThat(partenaires).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPartenaires() throws Exception {
        // Initialize the database
        partenaireRepository.saveAndFlush(partenaire);

        // Get all the partenaires
        restPartenaireMockMvc.perform(get("/api/partenaires?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(partenaire.getId().intValue())))
                .andExpect(jsonPath("$.[*].pId").value(hasItem(DEFAULT_P_ID.intValue())))
                .andExpect(jsonPath("$.[*].pNum").value(hasItem(DEFAULT_P_NUM.toString())))
                .andExpect(jsonPath("$.[*].pSiret").value(hasItem(DEFAULT_P_SIRET.toString())))
                .andExpect(jsonPath("$.[*].pService").value(hasItem(DEFAULT_P_SERVICE.toString())))
                .andExpect(jsonPath("$.[*].pRegion").value(hasItem(DEFAULT_P_REGION.toString())))
                .andExpect(jsonPath("$.[*].pCodeActivity").value(hasItem(DEFAULT_P_CODE_ACTIVITY.toString())))
                .andExpect(jsonPath("$.[*].pRue").value(hasItem(DEFAULT_P_RUE.toString())))
                .andExpect(jsonPath("$.[*].pCpltRue").value(hasItem(DEFAULT_P_CPLT_RUE.toString())))
                .andExpect(jsonPath("$.[*].pCodeDep").value(hasItem(DEFAULT_P_CODE_DEP.toString())))
                .andExpect(jsonPath("$.[*].pVille").value(hasItem(DEFAULT_P_VILLE.toString())))
                .andExpect(jsonPath("$.[*].pTelStd").value(hasItem(DEFAULT_P_TEL_STD.toString())))
                .andExpect(jsonPath("$.[*].pUrl").value(hasItem(DEFAULT_P_URL.toString())))
                .andExpect(jsonPath("$.[*].pCommentaire").value(hasItem(DEFAULT_P_COMMENTAIRE.toString())))
                .andExpect(jsonPath("$.[*].pNomSignataire").value(hasItem(DEFAULT_P_NOM_SIGNATAIRE.toString())))
                .andExpect(jsonPath("$.[*].pEffectif").value(hasItem(DEFAULT_P_EFFECTIF.toString())))
                .andExpect(jsonPath("$.[*].pDateMaj").value(hasItem(DEFAULT_P_DATE_MAJ_STR)));
    }

    @Test
    @Transactional
    public void getPartenaire() throws Exception {
        // Initialize the database
        partenaireRepository.saveAndFlush(partenaire);

        // Get the partenaire
        restPartenaireMockMvc.perform(get("/api/partenaires/{id}", partenaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(partenaire.getId().intValue()))
            .andExpect(jsonPath("$.pId").value(DEFAULT_P_ID.intValue()))
            .andExpect(jsonPath("$.pNum").value(DEFAULT_P_NUM.toString()))
            .andExpect(jsonPath("$.pSiret").value(DEFAULT_P_SIRET.toString()))
            .andExpect(jsonPath("$.pService").value(DEFAULT_P_SERVICE.toString()))
            .andExpect(jsonPath("$.pRegion").value(DEFAULT_P_REGION.toString()))
            .andExpect(jsonPath("$.pCodeActivity").value(DEFAULT_P_CODE_ACTIVITY.toString()))
            .andExpect(jsonPath("$.pRue").value(DEFAULT_P_RUE.toString()))
            .andExpect(jsonPath("$.pCpltRue").value(DEFAULT_P_CPLT_RUE.toString()))
            .andExpect(jsonPath("$.pCodeDep").value(DEFAULT_P_CODE_DEP.toString()))
            .andExpect(jsonPath("$.pVille").value(DEFAULT_P_VILLE.toString()))
            .andExpect(jsonPath("$.pTelStd").value(DEFAULT_P_TEL_STD.toString()))
            .andExpect(jsonPath("$.pUrl").value(DEFAULT_P_URL.toString()))
            .andExpect(jsonPath("$.pCommentaire").value(DEFAULT_P_COMMENTAIRE.toString()))
            .andExpect(jsonPath("$.pNomSignataire").value(DEFAULT_P_NOM_SIGNATAIRE.toString()))
            .andExpect(jsonPath("$.pEffectif").value(DEFAULT_P_EFFECTIF.toString()))
            .andExpect(jsonPath("$.pDateMaj").value(DEFAULT_P_DATE_MAJ_STR));
    }

    @Test
    @Transactional
    public void getNonExistingPartenaire() throws Exception {
        // Get the partenaire
        restPartenaireMockMvc.perform(get("/api/partenaires/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartenaire() throws Exception {
        // Initialize the database
        partenaireRepository.saveAndFlush(partenaire);
        int databaseSizeBeforeUpdate = partenaireRepository.findAll().size();

        // Update the partenaire
        Partenaire updatedPartenaire = partenaireRepository.findOne(partenaire.getId());
        updatedPartenaire
                .pId(UPDATED_P_ID)
                .pNum(UPDATED_P_NUM)
                .pSiret(UPDATED_P_SIRET)
                .pService(UPDATED_P_SERVICE)
                .pRegion(UPDATED_P_REGION)
                .pCodeActivity(UPDATED_P_CODE_ACTIVITY)
                .pRue(UPDATED_P_RUE)
                .pCpltRue(UPDATED_P_CPLT_RUE)
                .pCodeDep(UPDATED_P_CODE_DEP)
                .pVille(UPDATED_P_VILLE)
                .pTelStd(UPDATED_P_TEL_STD)
                .pUrl(UPDATED_P_URL)
                .pCommentaire(UPDATED_P_COMMENTAIRE)
                .pNomSignataire(UPDATED_P_NOM_SIGNATAIRE)
                .pEffectif(UPDATED_P_EFFECTIF)
                .pDateMaj(UPDATED_P_DATE_MAJ);

        restPartenaireMockMvc.perform(put("/api/partenaires")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPartenaire)))
                .andExpect(status().isOk());

        // Validate the Partenaire in the database
        List<Partenaire> partenaires = partenaireRepository.findAll();
        assertThat(partenaires).hasSize(databaseSizeBeforeUpdate);
        Partenaire testPartenaire = partenaires.get(partenaires.size() - 1);
        assertThat(testPartenaire.getpId()).isEqualTo(UPDATED_P_ID);
        assertThat(testPartenaire.getpNum()).isEqualTo(UPDATED_P_NUM);
        assertThat(testPartenaire.getpSiret()).isEqualTo(UPDATED_P_SIRET);
        assertThat(testPartenaire.getpService()).isEqualTo(UPDATED_P_SERVICE);
        assertThat(testPartenaire.getpRegion()).isEqualTo(UPDATED_P_REGION);
        assertThat(testPartenaire.getpCodeActivity()).isEqualTo(UPDATED_P_CODE_ACTIVITY);
        assertThat(testPartenaire.getpRue()).isEqualTo(UPDATED_P_RUE);
        assertThat(testPartenaire.getpCpltRue()).isEqualTo(UPDATED_P_CPLT_RUE);
        assertThat(testPartenaire.getpCodeDep()).isEqualTo(UPDATED_P_CODE_DEP);
        assertThat(testPartenaire.getpVille()).isEqualTo(UPDATED_P_VILLE);
        assertThat(testPartenaire.getpTelStd()).isEqualTo(UPDATED_P_TEL_STD);
        assertThat(testPartenaire.getpUrl()).isEqualTo(UPDATED_P_URL);
        assertThat(testPartenaire.getpCommentaire()).isEqualTo(UPDATED_P_COMMENTAIRE);
        assertThat(testPartenaire.getpNomSignataire()).isEqualTo(UPDATED_P_NOM_SIGNATAIRE);
        assertThat(testPartenaire.getpEffectif()).isEqualTo(UPDATED_P_EFFECTIF);
        assertThat(testPartenaire.getpDateMaj()).isEqualTo(UPDATED_P_DATE_MAJ);
    }

    @Test
    @Transactional
    public void deletePartenaire() throws Exception {
        // Initialize the database
        partenaireRepository.saveAndFlush(partenaire);
        int databaseSizeBeforeDelete = partenaireRepository.findAll().size();

        // Get the partenaire
        restPartenaireMockMvc.perform(delete("/api/partenaires/{id}", partenaire.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Partenaire> partenaires = partenaireRepository.findAll();
        assertThat(partenaires).hasSize(databaseSizeBeforeDelete - 1);
    }
}
