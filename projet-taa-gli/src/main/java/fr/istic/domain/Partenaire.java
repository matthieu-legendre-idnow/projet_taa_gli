package fr.istic.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Partenaire.
 */
@Entity
@Table(name = "partenaire")
public class Partenaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "p_id", nullable = false)
    private Long pId;

    @NotNull
    @Column(name = "p_num", nullable = false)
    private String pNum;

    @Column(name = "p_siret")
    private String pSiret;

    @Column(name = "p_service")
    private String pService;

    @NotNull
    @Column(name = "p_region", nullable = false)
    private String pRegion;

    @NotNull
    @Column(name = "p_code_activity", nullable = false)
    private String pCodeActivity;

    @Column(name = "p_rue")
    private String pRue;

    @Column(name = "p_cplt_rue")
    private String pCpltRue;

    @NotNull
    @Column(name = "p_code_dep", nullable = false)
    private String pCodeDep;

    @NotNull
    @Column(name = "p_ville", nullable = false)
    private String pVille;

    @Column(name = "p_tel_std")
    private String pTelStd;

    @Column(name = "p_url")
    private String pUrl;

    @Column(name = "p_commentaire")
    private String pCommentaire;

    @Column(name = "p_nom_signataire")
    private String pNomSignataire;

    @Column(name = "p_effectif")
    private String pEffectif;

    @NotNull
    @Column(name = "p_date_maj", nullable = false)
    private ZonedDateTime pDateMaj;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getpId() {
        return pId;
    }

    public Partenaire pId(Long pId) {
        this.pId = pId;
        return this;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getpNum() {
        return pNum;
    }

    public Partenaire pNum(String pNum) {
        this.pNum = pNum;
        return this;
    }

    public void setpNum(String pNum) {
        this.pNum = pNum;
    }

    public String getpSiret() {
        return pSiret;
    }

    public Partenaire pSiret(String pSiret) {
        this.pSiret = pSiret;
        return this;
    }

    public void setpSiret(String pSiret) {
        this.pSiret = pSiret;
    }

    public String getpService() {
        return pService;
    }

    public Partenaire pService(String pService) {
        this.pService = pService;
        return this;
    }

    public void setpService(String pService) {
        this.pService = pService;
    }

    public String getpRegion() {
        return pRegion;
    }

    public Partenaire pRegion(String pRegion) {
        this.pRegion = pRegion;
        return this;
    }

    public void setpRegion(String pRegion) {
        this.pRegion = pRegion;
    }

    public String getpCodeActivity() {
        return pCodeActivity;
    }

    public Partenaire pCodeActivity(String pCodeActivity) {
        this.pCodeActivity = pCodeActivity;
        return this;
    }

    public void setpCodeActivity(String pCodeActivity) {
        this.pCodeActivity = pCodeActivity;
    }

    public String getpRue() {
        return pRue;
    }

    public Partenaire pRue(String pRue) {
        this.pRue = pRue;
        return this;
    }

    public void setpRue(String pRue) {
        this.pRue = pRue;
    }

    public String getpCpltRue() {
        return pCpltRue;
    }

    public Partenaire pCpltRue(String pCpltRue) {
        this.pCpltRue = pCpltRue;
        return this;
    }

    public void setpCpltRue(String pCpltRue) {
        this.pCpltRue = pCpltRue;
    }

    public String getpCodeDep() {
        return pCodeDep;
    }

    public Partenaire pCodeDep(String pCodeDep) {
        this.pCodeDep = pCodeDep;
        return this;
    }

    public void setpCodeDep(String pCodeDep) {
        this.pCodeDep = pCodeDep;
    }

    public String getpVille() {
        return pVille;
    }

    public Partenaire pVille(String pVille) {
        this.pVille = pVille;
        return this;
    }

    public void setpVille(String pVille) {
        this.pVille = pVille;
    }

    public String getpTelStd() {
        return pTelStd;
    }

    public Partenaire pTelStd(String pTelStd) {
        this.pTelStd = pTelStd;
        return this;
    }

    public void setpTelStd(String pTelStd) {
        this.pTelStd = pTelStd;
    }

    public String getpUrl() {
        return pUrl;
    }

    public Partenaire pUrl(String pUrl) {
        this.pUrl = pUrl;
        return this;
    }

    public void setpUrl(String pUrl) {
        this.pUrl = pUrl;
    }

    public String getpCommentaire() {
        return pCommentaire;
    }

    public Partenaire pCommentaire(String pCommentaire) {
        this.pCommentaire = pCommentaire;
        return this;
    }

    public void setpCommentaire(String pCommentaire) {
        this.pCommentaire = pCommentaire;
    }

    public String getpNomSignataire() {
        return pNomSignataire;
    }

    public Partenaire pNomSignataire(String pNomSignataire) {
        this.pNomSignataire = pNomSignataire;
        return this;
    }

    public void setpNomSignataire(String pNomSignataire) {
        this.pNomSignataire = pNomSignataire;
    }

    public String getpEffectif() {
        return pEffectif;
    }

    public Partenaire pEffectif(String pEffectif) {
        this.pEffectif = pEffectif;
        return this;
    }

    public void setpEffectif(String pEffectif) {
        this.pEffectif = pEffectif;
    }

    public ZonedDateTime getpDateMaj() {
        return pDateMaj;
    }

    public Partenaire pDateMaj(ZonedDateTime pDateMaj) {
        this.pDateMaj = pDateMaj;
        return this;
    }

    public void setpDateMaj(ZonedDateTime pDateMaj) {
        this.pDateMaj = pDateMaj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Partenaire partenaire = (Partenaire) o;
        if(partenaire.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, partenaire.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Partenaire{" +
            "id=" + id +
            ", pId='" + pId + "'" +
            ", pNum='" + pNum + "'" +
            ", pSiret='" + pSiret + "'" +
            ", pService='" + pService + "'" +
            ", pRegion='" + pRegion + "'" +
            ", pCodeActivity='" + pCodeActivity + "'" +
            ", pRue='" + pRue + "'" +
            ", pCpltRue='" + pCpltRue + "'" +
            ", pCodeDep='" + pCodeDep + "'" +
            ", pVille='" + pVille + "'" +
            ", pTelStd='" + pTelStd + "'" +
            ", pUrl='" + pUrl + "'" +
            ", pCommentaire='" + pCommentaire + "'" +
            ", pNomSignataire='" + pNomSignataire + "'" +
            ", pEffectif='" + pEffectif + "'" +
            ", pDateMaj='" + pDateMaj + "'" +
            '}';
    }
}
