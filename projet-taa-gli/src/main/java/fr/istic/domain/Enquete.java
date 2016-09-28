package fr.istic.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Enquete.
 */
@Entity
@Table(name = "enquete")
public class Enquete implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "mode_enquete")
    private String modeEnquete;

    @NotNull
    @Column(name = "date_enquete", nullable = false)
    private ZonedDateTime dateEnquete;

    @NotNull
    @Column(name = "etu_nom", nullable = false)
    private String etuNom;

    @Column(name = "etu_rue")
    private String etuRue;

    @Column(name = "etu_ville")
    private String etuVille;

    @Column(name = "etu_code_dep")
    private String etuCodeDep;

    @NotNull
    @Column(name = "enq_date_debut", nullable = false)
    private ZonedDateTime enqDateDebut;

    @Column(name = "enq_duree_recherche")
    private String enqDureeRecherche;

    @Column(name = "enq_quantite")
    private Long enqQuantite;

    @Column(name = "enq_question")
    private String enqQuestion;

    @Column(name = "enq_reponse")
    private String enqReponse;

    @Column(name = "enq_salaire")
    private Long enqSalaire;

    @Column(name = "enq_salaire_devise")
    private String enqSalaireDevise;

    @OneToOne
    @JoinColumn(unique = true)
    private Etudiant enquete;

    @OneToOne
    @JoinColumn(unique = true)
    private Stage stage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModeEnquete() {
        return modeEnquete;
    }

    public Enquete modeEnquete(String modeEnquete) {
        this.modeEnquete = modeEnquete;
        return this;
    }

    public void setModeEnquete(String modeEnquete) {
        this.modeEnquete = modeEnquete;
    }

    public ZonedDateTime getDateEnquete() {
        return dateEnquete;
    }

    public Enquete dateEnquete(ZonedDateTime dateEnquete) {
        this.dateEnquete = dateEnquete;
        return this;
    }

    public void setDateEnquete(ZonedDateTime dateEnquete) {
        this.dateEnquete = dateEnquete;
    }

    public String getEtuNom() {
        return etuNom;
    }

    public Enquete etuNom(String etuNom) {
        this.etuNom = etuNom;
        return this;
    }

    public void setEtuNom(String etuNom) {
        this.etuNom = etuNom;
    }

    public String getEtuRue() {
        return etuRue;
    }

    public Enquete etuRue(String etuRue) {
        this.etuRue = etuRue;
        return this;
    }

    public void setEtuRue(String etuRue) {
        this.etuRue = etuRue;
    }

    public String getEtuVille() {
        return etuVille;
    }

    public Enquete etuVille(String etuVille) {
        this.etuVille = etuVille;
        return this;
    }

    public void setEtuVille(String etuVille) {
        this.etuVille = etuVille;
    }

    public String getEtuCodeDep() {
        return etuCodeDep;
    }

    public Enquete etuCodeDep(String etuCodeDep) {
        this.etuCodeDep = etuCodeDep;
        return this;
    }

    public void setEtuCodeDep(String etuCodeDep) {
        this.etuCodeDep = etuCodeDep;
    }

    public ZonedDateTime getEnqDateDebut() {
        return enqDateDebut;
    }

    public Enquete enqDateDebut(ZonedDateTime enqDateDebut) {
        this.enqDateDebut = enqDateDebut;
        return this;
    }

    public void setEnqDateDebut(ZonedDateTime enqDateDebut) {
        this.enqDateDebut = enqDateDebut;
    }

    public String getEnqDureeRecherche() {
        return enqDureeRecherche;
    }

    public Enquete enqDureeRecherche(String enqDureeRecherche) {
        this.enqDureeRecherche = enqDureeRecherche;
        return this;
    }

    public void setEnqDureeRecherche(String enqDureeRecherche) {
        this.enqDureeRecherche = enqDureeRecherche;
    }

    public Long getEnqQuantite() {
        return enqQuantite;
    }

    public Enquete enqQuantite(Long enqQuantite) {
        this.enqQuantite = enqQuantite;
        return this;
    }

    public void setEnqQuantite(Long enqQuantite) {
        this.enqQuantite = enqQuantite;
    }

    public String getEnqQuestion() {
        return enqQuestion;
    }

    public Enquete enqQuestion(String enqQuestion) {
        this.enqQuestion = enqQuestion;
        return this;
    }

    public void setEnqQuestion(String enqQuestion) {
        this.enqQuestion = enqQuestion;
    }

    public String getEnqReponse() {
        return enqReponse;
    }

    public Enquete enqReponse(String enqReponse) {
        this.enqReponse = enqReponse;
        return this;
    }

    public void setEnqReponse(String enqReponse) {
        this.enqReponse = enqReponse;
    }

    public Long getEnqSalaire() {
        return enqSalaire;
    }

    public Enquete enqSalaire(Long enqSalaire) {
        this.enqSalaire = enqSalaire;
        return this;
    }

    public void setEnqSalaire(Long enqSalaire) {
        this.enqSalaire = enqSalaire;
    }

    public String getEnqSalaireDevise() {
        return enqSalaireDevise;
    }

    public Enquete enqSalaireDevise(String enqSalaireDevise) {
        this.enqSalaireDevise = enqSalaireDevise;
        return this;
    }

    public void setEnqSalaireDevise(String enqSalaireDevise) {
        this.enqSalaireDevise = enqSalaireDevise;
    }

    public Etudiant getEnquete() {
        return enquete;
    }

    public Enquete enquete(Etudiant etudiant) {
        this.enquete = etudiant;
        return this;
    }

    public void setEnquete(Etudiant etudiant) {
        this.enquete = etudiant;
    }

    public Stage getStage() {
        return stage;
    }

    public Enquete stage(Stage stage) {
        this.stage = stage;
        return this;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Enquete enquete = (Enquete) o;
        if(enquete.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, enquete.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Enquete{" +
            "id=" + id +
            ", modeEnquete='" + modeEnquete + "'" +
            ", dateEnquete='" + dateEnquete + "'" +
            ", etuNom='" + etuNom + "'" +
            ", etuRue='" + etuRue + "'" +
            ", etuVille='" + etuVille + "'" +
            ", etuCodeDep='" + etuCodeDep + "'" +
            ", enqDateDebut='" + enqDateDebut + "'" +
            ", enqDureeRecherche='" + enqDureeRecherche + "'" +
            ", enqQuantite='" + enqQuantite + "'" +
            ", enqQuestion='" + enqQuestion + "'" +
            ", enqReponse='" + enqReponse + "'" +
            ", enqSalaire='" + enqSalaire + "'" +
            ", enqSalaireDevise='" + enqSalaireDevise + "'" +
            '}';
    }
}
