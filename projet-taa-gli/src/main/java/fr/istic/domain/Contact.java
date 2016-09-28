package fr.istic.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Contact.
 */
@Entity
@Table(name = "contact")
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "contact_id", nullable = false)
    private Long contactId;

    @NotNull
    @Column(name = "contact_nom", nullable = false)
    private String contactNom;

    @NotNull
    @Column(name = "contact_role", nullable = false)
    private String contactRole;

    @Column(name = "contact_sexe")
    private String contactSexe;

    @Column(name = "contact_tel")
    private String contactTel;

    @Column(name = "contact_mail")
    private String contactMail;

    @Column(name = "contact_date_maj")
    private ZonedDateTime contactDateMaj;

    @OneToOne
    @JoinColumn(unique = true)
    private Partenaire contact;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContactId() {
        return contactId;
    }

    public Contact contactId(Long contactId) {
        this.contactId = contactId;
        return this;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getContactNom() {
        return contactNom;
    }

    public Contact contactNom(String contactNom) {
        this.contactNom = contactNom;
        return this;
    }

    public void setContactNom(String contactNom) {
        this.contactNom = contactNom;
    }

    public String getContactRole() {
        return contactRole;
    }

    public Contact contactRole(String contactRole) {
        this.contactRole = contactRole;
        return this;
    }

    public void setContactRole(String contactRole) {
        this.contactRole = contactRole;
    }

    public String getContactSexe() {
        return contactSexe;
    }

    public Contact contactSexe(String contactSexe) {
        this.contactSexe = contactSexe;
        return this;
    }

    public void setContactSexe(String contactSexe) {
        this.contactSexe = contactSexe;
    }

    public String getContactTel() {
        return contactTel;
    }

    public Contact contactTel(String contactTel) {
        this.contactTel = contactTel;
        return this;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getContactMail() {
        return contactMail;
    }

    public Contact contactMail(String contactMail) {
        this.contactMail = contactMail;
        return this;
    }

    public void setContactMail(String contactMail) {
        this.contactMail = contactMail;
    }

    public ZonedDateTime getContactDateMaj() {
        return contactDateMaj;
    }

    public Contact contactDateMaj(ZonedDateTime contactDateMaj) {
        this.contactDateMaj = contactDateMaj;
        return this;
    }

    public void setContactDateMaj(ZonedDateTime contactDateMaj) {
        this.contactDateMaj = contactDateMaj;
    }

    public Partenaire getContact() {
        return contact;
    }

    public Contact contact(Partenaire partenaire) {
        this.contact = partenaire;
        return this;
    }

    public void setContact(Partenaire partenaire) {
        this.contact = partenaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contact contact = (Contact) o;
        if(contact.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, contact.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Contact{" +
            "id=" + id +
            ", contactId='" + contactId + "'" +
            ", contactNom='" + contactNom + "'" +
            ", contactRole='" + contactRole + "'" +
            ", contactSexe='" + contactSexe + "'" +
            ", contactTel='" + contactTel + "'" +
            ", contactMail='" + contactMail + "'" +
            ", contactDateMaj='" + contactDateMaj + "'" +
            '}';
    }
}
