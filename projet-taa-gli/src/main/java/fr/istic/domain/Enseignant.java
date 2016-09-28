package fr.istic.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Enseignant.
 */
@Entity
@Table(name = "enseignant")
public class Enseignant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "enseignant_id", nullable = false)
    private Long enseignantId;

    @NotNull
    @Column(name = "enseignant_sesame", nullable = false)
    private String enseignantSesame;

    @Column(name = "enseignant_sexe")
    private String enseignantSexe;

    @NotNull
    @Column(name = "enseignant_nom", nullable = false)
    private String enseignantNom;

    @NotNull
    @Column(name = "enseignant_prenom", nullable = false)
    private String enseignantPrenom;

    @Column(name = "enseignant_adresse")
    private String enseignantAdresse;

    @Column(name = "enseignant_tel_pro")
    private String enseignantTelPro;

    @NotNull
    @Column(name = "enseignant_actif", nullable = false)
    private Boolean enseignantActif;

    @Column(name = "enseignant_maj")
    private ZonedDateTime enseignantMaj;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEnseignantId() {
        return enseignantId;
    }

    public Enseignant enseignantId(Long enseignantId) {
        this.enseignantId = enseignantId;
        return this;
    }

    public void setEnseignantId(Long enseignantId) {
        this.enseignantId = enseignantId;
    }

    public String getEnseignantSesame() {
        return enseignantSesame;
    }

    public Enseignant enseignantSesame(String enseignantSesame) {
        this.enseignantSesame = enseignantSesame;
        return this;
    }

    public void setEnseignantSesame(String enseignantSesame) {
        this.enseignantSesame = enseignantSesame;
    }

    public String getEnseignantSexe() {
        return enseignantSexe;
    }

    public Enseignant enseignantSexe(String enseignantSexe) {
        this.enseignantSexe = enseignantSexe;
        return this;
    }

    public void setEnseignantSexe(String enseignantSexe) {
        this.enseignantSexe = enseignantSexe;
    }

    public String getEnseignantNom() {
        return enseignantNom;
    }

    public Enseignant enseignantNom(String enseignantNom) {
        this.enseignantNom = enseignantNom;
        return this;
    }

    public void setEnseignantNom(String enseignantNom) {
        this.enseignantNom = enseignantNom;
    }

    public String getEnseignantPrenom() {
        return enseignantPrenom;
    }

    public Enseignant enseignantPrenom(String enseignantPrenom) {
        this.enseignantPrenom = enseignantPrenom;
        return this;
    }

    public void setEnseignantPrenom(String enseignantPrenom) {
        this.enseignantPrenom = enseignantPrenom;
    }

    public String getEnseignantAdresse() {
        return enseignantAdresse;
    }

    public Enseignant enseignantAdresse(String enseignantAdresse) {
        this.enseignantAdresse = enseignantAdresse;
        return this;
    }

    public void setEnseignantAdresse(String enseignantAdresse) {
        this.enseignantAdresse = enseignantAdresse;
    }

    public String getEnseignantTelPro() {
        return enseignantTelPro;
    }

    public Enseignant enseignantTelPro(String enseignantTelPro) {
        this.enseignantTelPro = enseignantTelPro;
        return this;
    }

    public void setEnseignantTelPro(String enseignantTelPro) {
        this.enseignantTelPro = enseignantTelPro;
    }

    public Boolean isEnseignantActif() {
        return enseignantActif;
    }

    public Enseignant enseignantActif(Boolean enseignantActif) {
        this.enseignantActif = enseignantActif;
        return this;
    }

    public void setEnseignantActif(Boolean enseignantActif) {
        this.enseignantActif = enseignantActif;
    }

    public ZonedDateTime getEnseignantMaj() {
        return enseignantMaj;
    }

    public Enseignant enseignantMaj(ZonedDateTime enseignantMaj) {
        this.enseignantMaj = enseignantMaj;
        return this;
    }

    public void setEnseignantMaj(ZonedDateTime enseignantMaj) {
        this.enseignantMaj = enseignantMaj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Enseignant enseignant = (Enseignant) o;
        if(enseignant.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, enseignant.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Enseignant{" +
            "id=" + id +
            ", enseignantId='" + enseignantId + "'" +
            ", enseignantSesame='" + enseignantSesame + "'" +
            ", enseignantSexe='" + enseignantSexe + "'" +
            ", enseignantNom='" + enseignantNom + "'" +
            ", enseignantPrenom='" + enseignantPrenom + "'" +
            ", enseignantAdresse='" + enseignantAdresse + "'" +
            ", enseignantTelPro='" + enseignantTelPro + "'" +
            ", enseignantActif='" + enseignantActif + "'" +
            ", enseignantMaj='" + enseignantMaj + "'" +
            '}';
    }
}
