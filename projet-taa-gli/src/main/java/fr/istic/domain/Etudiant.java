package fr.istic.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Etudiant.
 */
@Entity
@Table(name = "etudiant")
public class Etudiant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "etu_id", nullable = false)
    private String etuId;

    @NotNull
    @Column(name = "etu_nom", nullable = false)
    private String etuNom;

    @NotNull
    @Column(name = "etu_prenom", nullable = false)
    private String etuPrenom;

    @Column(name = "etu_rue")
    private String etuRue;

    @Column(name = "etu_ville")
    private String etuVille;

    @Column(name = "etu_code_dep")
    private String etuCodeDep;

    @Column(name = "etu_tel_perso")
    private String etuTelPerso;

    @Column(name = "etu_tel_mob")
    private String etuTelMob;

    @Column(name = "etu_premier_emploi")
    private String etuPremierEmploi;

    @Column(name = "etu_dernier_emploi")
    private String etuDernierEmploi;

    @Column(name = "etu_rech_emp")
    private Boolean etuRechEmp;

    @NotNull
    @Column(name = "etu_date_maj", nullable = false)
    private ZonedDateTime etuDateMaj;

    @OneToOne
    @JoinColumn(unique = true)
    private Partenaire stagiaire;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEtuId() {
        return etuId;
    }

    public Etudiant etuId(String etuId) {
        this.etuId = etuId;
        return this;
    }

    public void setEtuId(String etuId) {
        this.etuId = etuId;
    }

    public String getEtuNom() {
        return etuNom;
    }

    public Etudiant etuNom(String etuNom) {
        this.etuNom = etuNom;
        return this;
    }

    public void setEtuNom(String etuNom) {
        this.etuNom = etuNom;
    }

    public String getEtuPrenom() {
        return etuPrenom;
    }

    public Etudiant etuPrenom(String etuPrenom) {
        this.etuPrenom = etuPrenom;
        return this;
    }

    public void setEtuPrenom(String etuPrenom) {
        this.etuPrenom = etuPrenom;
    }

    public String getEtuRue() {
        return etuRue;
    }

    public Etudiant etuRue(String etuRue) {
        this.etuRue = etuRue;
        return this;
    }

    public void setEtuRue(String etuRue) {
        this.etuRue = etuRue;
    }

    public String getEtuVille() {
        return etuVille;
    }

    public Etudiant etuVille(String etuVille) {
        this.etuVille = etuVille;
        return this;
    }

    public void setEtuVille(String etuVille) {
        this.etuVille = etuVille;
    }

    public String getEtuCodeDep() {
        return etuCodeDep;
    }

    public Etudiant etuCodeDep(String etuCodeDep) {
        this.etuCodeDep = etuCodeDep;
        return this;
    }

    public void setEtuCodeDep(String etuCodeDep) {
        this.etuCodeDep = etuCodeDep;
    }

    public String getEtuTelPerso() {
        return etuTelPerso;
    }

    public Etudiant etuTelPerso(String etuTelPerso) {
        this.etuTelPerso = etuTelPerso;
        return this;
    }

    public void setEtuTelPerso(String etuTelPerso) {
        this.etuTelPerso = etuTelPerso;
    }

    public String getEtuTelMob() {
        return etuTelMob;
    }

    public Etudiant etuTelMob(String etuTelMob) {
        this.etuTelMob = etuTelMob;
        return this;
    }

    public void setEtuTelMob(String etuTelMob) {
        this.etuTelMob = etuTelMob;
    }

    public String getEtuPremierEmploi() {
        return etuPremierEmploi;
    }

    public Etudiant etuPremierEmploi(String etuPremierEmploi) {
        this.etuPremierEmploi = etuPremierEmploi;
        return this;
    }

    public void setEtuPremierEmploi(String etuPremierEmploi) {
        this.etuPremierEmploi = etuPremierEmploi;
    }

    public String getEtuDernierEmploi() {
        return etuDernierEmploi;
    }

    public Etudiant etuDernierEmploi(String etuDernierEmploi) {
        this.etuDernierEmploi = etuDernierEmploi;
        return this;
    }

    public void setEtuDernierEmploi(String etuDernierEmploi) {
        this.etuDernierEmploi = etuDernierEmploi;
    }

    public Boolean isEtuRechEmp() {
        return etuRechEmp;
    }

    public Etudiant etuRechEmp(Boolean etuRechEmp) {
        this.etuRechEmp = etuRechEmp;
        return this;
    }

    public void setEtuRechEmp(Boolean etuRechEmp) {
        this.etuRechEmp = etuRechEmp;
    }

    public ZonedDateTime getEtuDateMaj() {
        return etuDateMaj;
    }

    public Etudiant etuDateMaj(ZonedDateTime etuDateMaj) {
        this.etuDateMaj = etuDateMaj;
        return this;
    }

    public void setEtuDateMaj(ZonedDateTime etuDateMaj) {
        this.etuDateMaj = etuDateMaj;
    }

    public Partenaire getStagiaire() {
        return stagiaire;
    }

    public Etudiant stagiaire(Partenaire partenaire) {
        this.stagiaire = partenaire;
        return this;
    }

    public void setStagiaire(Partenaire partenaire) {
        this.stagiaire = partenaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Etudiant etudiant = (Etudiant) o;
        if(etudiant.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, etudiant.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Etudiant{" +
            "id=" + id +
            ", etuId='" + etuId + "'" +
            ", etuNom='" + etuNom + "'" +
            ", etuPrenom='" + etuPrenom + "'" +
            ", etuRue='" + etuRue + "'" +
            ", etuVille='" + etuVille + "'" +
            ", etuCodeDep='" + etuCodeDep + "'" +
            ", etuTelPerso='" + etuTelPerso + "'" +
            ", etuTelMob='" + etuTelMob + "'" +
            ", etuPremierEmploi='" + etuPremierEmploi + "'" +
            ", etuDernierEmploi='" + etuDernierEmploi + "'" +
            ", etuRechEmp='" + etuRechEmp + "'" +
            ", etuDateMaj='" + etuDateMaj + "'" +
            '}';
    }
}
