package fr.istic.web.rest;

import fr.istic.ProjetTaaGliApp;

import fr.istic.domain.Stage;
import fr.istic.repository.StageRepository;

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
 * Test class for the StageResource REST controller.
 *
 * @see StageResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = ProjetTaaGliApp.class)

public class StageResourceIntTest {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final ZonedDateTime DEFAULT_DATE_DEBUT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE_DEBUT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_DEBUT_STR = dateTimeFormatter.format(DEFAULT_DATE_DEBUT);

    private static final Long DEFAULT_P_IDENT = 1L;
    private static final Long UPDATED_P_IDENT = 2L;

    private static final Long DEFAULT_P_IDENT_CONVENTION = 1L;
    private static final Long UPDATED_P_IDENT_CONVENTION = 2L;

    private static final Long DEFAULT_ETU_IDENT = 1L;
    private static final Long UPDATED_ETU_IDENT = 2L;

    private static final Long DEFAULT_ENSEIGN_ID = 1L;
    private static final Long UPDATED_ENSEIGN_ID = 2L;

    private static final Long DEFAULT_ENC_ID = 1L;
    private static final Long UPDATED_ENC_ID = 2L;
    private static final String DEFAULT_NOM_ENC = "AAAAA";
    private static final String UPDATED_NOM_ENC = "BBBBB";
    private static final String DEFAULT_SUJET = "AAAAA";
    private static final String UPDATED_SUJET = "BBBBB";
    private static final String DEFAULT_LANG = "AAAAA";
    private static final String UPDATED_LANG = "BBBBB";
    private static final String DEFAULT_MOTSCLES = "AAAAA";
    private static final String UPDATED_MOTSCLES = "BBBBB";

    private static final Long DEFAULT_JOURS_TRAVAILLE = 1L;
    private static final Long UPDATED_JOURS_TRAVAILLE = 2L;

    private static final Long DEFAULT_SALAIRE = 1L;
    private static final Long UPDATED_SALAIRE = 2L;

    private static final ZonedDateTime DEFAULT_FIN_CONV = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_FIN_CONV = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_FIN_CONV_STR = dateTimeFormatter.format(DEFAULT_FIN_CONV);

    private static final ZonedDateTime DEFAULT_FIN_STAGE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_FIN_STAGE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_FIN_STAGE_STR = dateTimeFormatter.format(DEFAULT_FIN_STAGE);

    private static final ZonedDateTime DEFAULT_SOUTENANCE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_SOUTENANCE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_SOUTENANCE_STR = dateTimeFormatter.format(DEFAULT_SOUTENANCE);

    private static final ZonedDateTime DEFAULT_RAPPORT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_RAPPORT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_RAPPORT_STR = dateTimeFormatter.format(DEFAULT_RAPPORT);

    @Inject
    private StageRepository stageRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restStageMockMvc;

    private Stage stage;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StageResource stageResource = new StageResource();
        ReflectionTestUtils.setField(stageResource, "stageRepository", stageRepository);
        this.restStageMockMvc = MockMvcBuilders.standaloneSetup(stageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stage createEntity(EntityManager em) {
        Stage stage = new Stage()
                .dateDebut(DEFAULT_DATE_DEBUT)
                .pIdent(DEFAULT_P_IDENT)
                .pIdentConvention(DEFAULT_P_IDENT_CONVENTION)
                .etuIdent(DEFAULT_ETU_IDENT)
                .enseignId(DEFAULT_ENSEIGN_ID)
                .encId(DEFAULT_ENC_ID)
                .nomEnc(DEFAULT_NOM_ENC)
                .sujet(DEFAULT_SUJET)
                .lang(DEFAULT_LANG)
                .motscles(DEFAULT_MOTSCLES)
                .joursTravaille(DEFAULT_JOURS_TRAVAILLE)
                .salaire(DEFAULT_SALAIRE)
                .finConv(DEFAULT_FIN_CONV)
                .finStage(DEFAULT_FIN_STAGE)
                .soutenance(DEFAULT_SOUTENANCE)
                .rapport(DEFAULT_RAPPORT);
        return stage;
    }

    @Before
    public void initTest() {
        stage = createEntity(em);
    }

    @Test
    @Transactional
    public void createStage() throws Exception {
        int databaseSizeBeforeCreate = stageRepository.findAll().size();

        // Create the Stage

        restStageMockMvc.perform(post("/api/stages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stage)))
                .andExpect(status().isCreated());

        // Validate the Stage in the database
        List<Stage> stages = stageRepository.findAll();
        assertThat(stages).hasSize(databaseSizeBeforeCreate + 1);
        Stage testStage = stages.get(stages.size() - 1);
        assertThat(testStage.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testStage.getpIdent()).isEqualTo(DEFAULT_P_IDENT);
        assertThat(testStage.getpIdentConvention()).isEqualTo(DEFAULT_P_IDENT_CONVENTION);
        assertThat(testStage.getEtuIdent()).isEqualTo(DEFAULT_ETU_IDENT);
        assertThat(testStage.getEnseignId()).isEqualTo(DEFAULT_ENSEIGN_ID);
        assertThat(testStage.getEncId()).isEqualTo(DEFAULT_ENC_ID);
        assertThat(testStage.getNomEnc()).isEqualTo(DEFAULT_NOM_ENC);
        assertThat(testStage.getSujet()).isEqualTo(DEFAULT_SUJET);
        assertThat(testStage.getLang()).isEqualTo(DEFAULT_LANG);
        assertThat(testStage.getMotscles()).isEqualTo(DEFAULT_MOTSCLES);
        assertThat(testStage.getJoursTravaille()).isEqualTo(DEFAULT_JOURS_TRAVAILLE);
        assertThat(testStage.getSalaire()).isEqualTo(DEFAULT_SALAIRE);
        assertThat(testStage.getFinConv()).isEqualTo(DEFAULT_FIN_CONV);
        assertThat(testStage.getFinStage()).isEqualTo(DEFAULT_FIN_STAGE);
        assertThat(testStage.getSoutenance()).isEqualTo(DEFAULT_SOUTENANCE);
        assertThat(testStage.getRapport()).isEqualTo(DEFAULT_RAPPORT);
    }

    @Test
    @Transactional
    public void checkDateDebutIsRequired() throws Exception {
        int databaseSizeBeforeTest = stageRepository.findAll().size();
        // set the field null
        stage.setDateDebut(null);

        // Create the Stage, which fails.

        restStageMockMvc.perform(post("/api/stages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stage)))
                .andExpect(status().isBadRequest());

        List<Stage> stages = stageRepository.findAll();
        assertThat(stages).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkpIdentIsRequired() throws Exception {
        int databaseSizeBeforeTest = stageRepository.findAll().size();
        // set the field null
        stage.setpIdent(null);

        // Create the Stage, which fails.

        restStageMockMvc.perform(post("/api/stages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(stage)))
                .andExpect(status().isBadRequest());

        List<Stage> stages = stageRepository.findAll();
        assertThat(stages).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStages() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);

        // Get all the stages
        restStageMockMvc.perform(get("/api/stages?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(stage.getId().intValue())))
                .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT_STR)))
                .andExpect(jsonPath("$.[*].pIdent").value(hasItem(DEFAULT_P_IDENT.intValue())))
                .andExpect(jsonPath("$.[*].pIdentConvention").value(hasItem(DEFAULT_P_IDENT_CONVENTION.intValue())))
                .andExpect(jsonPath("$.[*].etuIdent").value(hasItem(DEFAULT_ETU_IDENT.intValue())))
                .andExpect(jsonPath("$.[*].enseignId").value(hasItem(DEFAULT_ENSEIGN_ID.intValue())))
                .andExpect(jsonPath("$.[*].encId").value(hasItem(DEFAULT_ENC_ID.intValue())))
                .andExpect(jsonPath("$.[*].nomEnc").value(hasItem(DEFAULT_NOM_ENC.toString())))
                .andExpect(jsonPath("$.[*].sujet").value(hasItem(DEFAULT_SUJET.toString())))
                .andExpect(jsonPath("$.[*].lang").value(hasItem(DEFAULT_LANG.toString())))
                .andExpect(jsonPath("$.[*].motscles").value(hasItem(DEFAULT_MOTSCLES.toString())))
                .andExpect(jsonPath("$.[*].joursTravaille").value(hasItem(DEFAULT_JOURS_TRAVAILLE.intValue())))
                .andExpect(jsonPath("$.[*].salaire").value(hasItem(DEFAULT_SALAIRE.intValue())))
                .andExpect(jsonPath("$.[*].finConv").value(hasItem(DEFAULT_FIN_CONV_STR)))
                .andExpect(jsonPath("$.[*].finStage").value(hasItem(DEFAULT_FIN_STAGE_STR)))
                .andExpect(jsonPath("$.[*].soutenance").value(hasItem(DEFAULT_SOUTENANCE_STR)))
                .andExpect(jsonPath("$.[*].rapport").value(hasItem(DEFAULT_RAPPORT_STR)));
    }

    @Test
    @Transactional
    public void getStage() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);

        // Get the stage
        restStageMockMvc.perform(get("/api/stages/{id}", stage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stage.getId().intValue()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT_STR))
            .andExpect(jsonPath("$.pIdent").value(DEFAULT_P_IDENT.intValue()))
            .andExpect(jsonPath("$.pIdentConvention").value(DEFAULT_P_IDENT_CONVENTION.intValue()))
            .andExpect(jsonPath("$.etuIdent").value(DEFAULT_ETU_IDENT.intValue()))
            .andExpect(jsonPath("$.enseignId").value(DEFAULT_ENSEIGN_ID.intValue()))
            .andExpect(jsonPath("$.encId").value(DEFAULT_ENC_ID.intValue()))
            .andExpect(jsonPath("$.nomEnc").value(DEFAULT_NOM_ENC.toString()))
            .andExpect(jsonPath("$.sujet").value(DEFAULT_SUJET.toString()))
            .andExpect(jsonPath("$.lang").value(DEFAULT_LANG.toString()))
            .andExpect(jsonPath("$.motscles").value(DEFAULT_MOTSCLES.toString()))
            .andExpect(jsonPath("$.joursTravaille").value(DEFAULT_JOURS_TRAVAILLE.intValue()))
            .andExpect(jsonPath("$.salaire").value(DEFAULT_SALAIRE.intValue()))
            .andExpect(jsonPath("$.finConv").value(DEFAULT_FIN_CONV_STR))
            .andExpect(jsonPath("$.finStage").value(DEFAULT_FIN_STAGE_STR))
            .andExpect(jsonPath("$.soutenance").value(DEFAULT_SOUTENANCE_STR))
            .andExpect(jsonPath("$.rapport").value(DEFAULT_RAPPORT_STR));
    }

    @Test
    @Transactional
    public void getNonExistingStage() throws Exception {
        // Get the stage
        restStageMockMvc.perform(get("/api/stages/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStage() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);
        int databaseSizeBeforeUpdate = stageRepository.findAll().size();

        // Update the stage
        Stage updatedStage = stageRepository.findOne(stage.getId());
        updatedStage
                .dateDebut(UPDATED_DATE_DEBUT)
                .pIdent(UPDATED_P_IDENT)
                .pIdentConvention(UPDATED_P_IDENT_CONVENTION)
                .etuIdent(UPDATED_ETU_IDENT)
                .enseignId(UPDATED_ENSEIGN_ID)
                .encId(UPDATED_ENC_ID)
                .nomEnc(UPDATED_NOM_ENC)
                .sujet(UPDATED_SUJET)
                .lang(UPDATED_LANG)
                .motscles(UPDATED_MOTSCLES)
                .joursTravaille(UPDATED_JOURS_TRAVAILLE)
                .salaire(UPDATED_SALAIRE)
                .finConv(UPDATED_FIN_CONV)
                .finStage(UPDATED_FIN_STAGE)
                .soutenance(UPDATED_SOUTENANCE)
                .rapport(UPDATED_RAPPORT);

        restStageMockMvc.perform(put("/api/stages")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedStage)))
                .andExpect(status().isOk());

        // Validate the Stage in the database
        List<Stage> stages = stageRepository.findAll();
        assertThat(stages).hasSize(databaseSizeBeforeUpdate);
        Stage testStage = stages.get(stages.size() - 1);
        assertThat(testStage.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testStage.getpIdent()).isEqualTo(UPDATED_P_IDENT);
        assertThat(testStage.getpIdentConvention()).isEqualTo(UPDATED_P_IDENT_CONVENTION);
        assertThat(testStage.getEtuIdent()).isEqualTo(UPDATED_ETU_IDENT);
        assertThat(testStage.getEnseignId()).isEqualTo(UPDATED_ENSEIGN_ID);
        assertThat(testStage.getEncId()).isEqualTo(UPDATED_ENC_ID);
        assertThat(testStage.getNomEnc()).isEqualTo(UPDATED_NOM_ENC);
        assertThat(testStage.getSujet()).isEqualTo(UPDATED_SUJET);
        assertThat(testStage.getLang()).isEqualTo(UPDATED_LANG);
        assertThat(testStage.getMotscles()).isEqualTo(UPDATED_MOTSCLES);
        assertThat(testStage.getJoursTravaille()).isEqualTo(UPDATED_JOURS_TRAVAILLE);
        assertThat(testStage.getSalaire()).isEqualTo(UPDATED_SALAIRE);
        assertThat(testStage.getFinConv()).isEqualTo(UPDATED_FIN_CONV);
        assertThat(testStage.getFinStage()).isEqualTo(UPDATED_FIN_STAGE);
        assertThat(testStage.getSoutenance()).isEqualTo(UPDATED_SOUTENANCE);
        assertThat(testStage.getRapport()).isEqualTo(UPDATED_RAPPORT);
    }

    @Test
    @Transactional
    public void deleteStage() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);
        int databaseSizeBeforeDelete = stageRepository.findAll().size();

        // Get the stage
        restStageMockMvc.perform(delete("/api/stages/{id}", stage.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Stage> stages = stageRepository.findAll();
        assertThat(stages).hasSize(databaseSizeBeforeDelete - 1);
    }
}
