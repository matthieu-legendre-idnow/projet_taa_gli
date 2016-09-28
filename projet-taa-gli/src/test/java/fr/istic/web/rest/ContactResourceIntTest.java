package fr.istic.web.rest;

import fr.istic.ProjetTaaGliApp;

import fr.istic.domain.Contact;
import fr.istic.repository.ContactRepository;

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
 * Test class for the ContactResource REST controller.
 *
 * @see ContactResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = ProjetTaaGliApp.class)

public class ContactResourceIntTest {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final Long DEFAULT_CONTACT_ID = 1L;
    private static final Long UPDATED_CONTACT_ID = 2L;
    private static final String DEFAULT_CONTACT_NOM = "AAAAA";
    private static final String UPDATED_CONTACT_NOM = "BBBBB";
    private static final String DEFAULT_CONTACT_ROLE = "AAAAA";
    private static final String UPDATED_CONTACT_ROLE = "BBBBB";
    private static final String DEFAULT_CONTACT_SEXE = "AAAAA";
    private static final String UPDATED_CONTACT_SEXE = "BBBBB";
    private static final String DEFAULT_CONTACT_TEL = "AAAAA";
    private static final String UPDATED_CONTACT_TEL = "BBBBB";
    private static final String DEFAULT_CONTACT_MAIL = "AAAAA";
    private static final String UPDATED_CONTACT_MAIL = "BBBBB";

    private static final ZonedDateTime DEFAULT_CONTACT_DATE_MAJ = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CONTACT_DATE_MAJ = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CONTACT_DATE_MAJ_STR = dateTimeFormatter.format(DEFAULT_CONTACT_DATE_MAJ);

    @Inject
    private ContactRepository contactRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restContactMockMvc;

    private Contact contact;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContactResource contactResource = new ContactResource();
        ReflectionTestUtils.setField(contactResource, "contactRepository", contactRepository);
        this.restContactMockMvc = MockMvcBuilders.standaloneSetup(contactResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contact createEntity(EntityManager em) {
        Contact contact = new Contact()
                .contactId(DEFAULT_CONTACT_ID)
                .contactNom(DEFAULT_CONTACT_NOM)
                .contactRole(DEFAULT_CONTACT_ROLE)
                .contactSexe(DEFAULT_CONTACT_SEXE)
                .contactTel(DEFAULT_CONTACT_TEL)
                .contactMail(DEFAULT_CONTACT_MAIL)
                .contactDateMaj(DEFAULT_CONTACT_DATE_MAJ);
        return contact;
    }

    @Before
    public void initTest() {
        contact = createEntity(em);
    }

    @Test
    @Transactional
    public void createContact() throws Exception {
        int databaseSizeBeforeCreate = contactRepository.findAll().size();

        // Create the Contact

        restContactMockMvc.perform(post("/api/contacts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contact)))
                .andExpect(status().isCreated());

        // Validate the Contact in the database
        List<Contact> contacts = contactRepository.findAll();
        assertThat(contacts).hasSize(databaseSizeBeforeCreate + 1);
        Contact testContact = contacts.get(contacts.size() - 1);
        assertThat(testContact.getContactId()).isEqualTo(DEFAULT_CONTACT_ID);
        assertThat(testContact.getContactNom()).isEqualTo(DEFAULT_CONTACT_NOM);
        assertThat(testContact.getContactRole()).isEqualTo(DEFAULT_CONTACT_ROLE);
        assertThat(testContact.getContactSexe()).isEqualTo(DEFAULT_CONTACT_SEXE);
        assertThat(testContact.getContactTel()).isEqualTo(DEFAULT_CONTACT_TEL);
        assertThat(testContact.getContactMail()).isEqualTo(DEFAULT_CONTACT_MAIL);
        assertThat(testContact.getContactDateMaj()).isEqualTo(DEFAULT_CONTACT_DATE_MAJ);
    }

    @Test
    @Transactional
    public void checkContactIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactRepository.findAll().size();
        // set the field null
        contact.setContactId(null);

        // Create the Contact, which fails.

        restContactMockMvc.perform(post("/api/contacts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contact)))
                .andExpect(status().isBadRequest());

        List<Contact> contacts = contactRepository.findAll();
        assertThat(contacts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactRepository.findAll().size();
        // set the field null
        contact.setContactNom(null);

        // Create the Contact, which fails.

        restContactMockMvc.perform(post("/api/contacts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contact)))
                .andExpect(status().isBadRequest());

        List<Contact> contacts = contactRepository.findAll();
        assertThat(contacts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactRoleIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactRepository.findAll().size();
        // set the field null
        contact.setContactRole(null);

        // Create the Contact, which fails.

        restContactMockMvc.perform(post("/api/contacts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contact)))
                .andExpect(status().isBadRequest());

        List<Contact> contacts = contactRepository.findAll();
        assertThat(contacts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContacts() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contacts
        restContactMockMvc.perform(get("/api/contacts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(contact.getId().intValue())))
                .andExpect(jsonPath("$.[*].contactId").value(hasItem(DEFAULT_CONTACT_ID.intValue())))
                .andExpect(jsonPath("$.[*].contactNom").value(hasItem(DEFAULT_CONTACT_NOM.toString())))
                .andExpect(jsonPath("$.[*].contactRole").value(hasItem(DEFAULT_CONTACT_ROLE.toString())))
                .andExpect(jsonPath("$.[*].contactSexe").value(hasItem(DEFAULT_CONTACT_SEXE.toString())))
                .andExpect(jsonPath("$.[*].contactTel").value(hasItem(DEFAULT_CONTACT_TEL.toString())))
                .andExpect(jsonPath("$.[*].contactMail").value(hasItem(DEFAULT_CONTACT_MAIL.toString())))
                .andExpect(jsonPath("$.[*].contactDateMaj").value(hasItem(DEFAULT_CONTACT_DATE_MAJ_STR)));
    }

    @Test
    @Transactional
    public void getContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get the contact
        restContactMockMvc.perform(get("/api/contacts/{id}", contact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contact.getId().intValue()))
            .andExpect(jsonPath("$.contactId").value(DEFAULT_CONTACT_ID.intValue()))
            .andExpect(jsonPath("$.contactNom").value(DEFAULT_CONTACT_NOM.toString()))
            .andExpect(jsonPath("$.contactRole").value(DEFAULT_CONTACT_ROLE.toString()))
            .andExpect(jsonPath("$.contactSexe").value(DEFAULT_CONTACT_SEXE.toString()))
            .andExpect(jsonPath("$.contactTel").value(DEFAULT_CONTACT_TEL.toString()))
            .andExpect(jsonPath("$.contactMail").value(DEFAULT_CONTACT_MAIL.toString()))
            .andExpect(jsonPath("$.contactDateMaj").value(DEFAULT_CONTACT_DATE_MAJ_STR));
    }

    @Test
    @Transactional
    public void getNonExistingContact() throws Exception {
        // Get the contact
        restContactMockMvc.perform(get("/api/contacts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);
        int databaseSizeBeforeUpdate = contactRepository.findAll().size();

        // Update the contact
        Contact updatedContact = contactRepository.findOne(contact.getId());
        updatedContact
                .contactId(UPDATED_CONTACT_ID)
                .contactNom(UPDATED_CONTACT_NOM)
                .contactRole(UPDATED_CONTACT_ROLE)
                .contactSexe(UPDATED_CONTACT_SEXE)
                .contactTel(UPDATED_CONTACT_TEL)
                .contactMail(UPDATED_CONTACT_MAIL)
                .contactDateMaj(UPDATED_CONTACT_DATE_MAJ);

        restContactMockMvc.perform(put("/api/contacts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedContact)))
                .andExpect(status().isOk());

        // Validate the Contact in the database
        List<Contact> contacts = contactRepository.findAll();
        assertThat(contacts).hasSize(databaseSizeBeforeUpdate);
        Contact testContact = contacts.get(contacts.size() - 1);
        assertThat(testContact.getContactId()).isEqualTo(UPDATED_CONTACT_ID);
        assertThat(testContact.getContactNom()).isEqualTo(UPDATED_CONTACT_NOM);
        assertThat(testContact.getContactRole()).isEqualTo(UPDATED_CONTACT_ROLE);
        assertThat(testContact.getContactSexe()).isEqualTo(UPDATED_CONTACT_SEXE);
        assertThat(testContact.getContactTel()).isEqualTo(UPDATED_CONTACT_TEL);
        assertThat(testContact.getContactMail()).isEqualTo(UPDATED_CONTACT_MAIL);
        assertThat(testContact.getContactDateMaj()).isEqualTo(UPDATED_CONTACT_DATE_MAJ);
    }

    @Test
    @Transactional
    public void deleteContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);
        int databaseSizeBeforeDelete = contactRepository.findAll().size();

        // Get the contact
        restContactMockMvc.perform(delete("/api/contacts/{id}", contact.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Contact> contacts = contactRepository.findAll();
        assertThat(contacts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
