package metier.modele;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;


@Entity
public class Evenement implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "VERSION", nullable = false)
    @Version
    private int version;
    
    @OneToOne
    private Activite activite;
    
    @Temporal(TemporalType.DATE)
    private Date dateEvenement;
    
    @OneToMany
    private List < Equipe > equipes;
    
    @OneToOne
    private Lieu lieuEvenement;

    public Evenement() {
    }

    public Evenement(Activite activite, Date dateEvenement, List < Equipe> equipes, Lieu lieuEvenement) {
        this.activite = activite;
        this.dateEvenement = dateEvenement;
        this.equipes = equipes;
        this.lieuEvenement = lieuEvenement;
    }

    public Long getId() {
        return id;
    }

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    public Date getDateEvenement() {
        return dateEvenement;
    }

    public void setDateEvenement(Date dateEvenement) {
        this.dateEvenement = dateEvenement;
    }

    public List < Equipe> getEquipes() {
        return equipes;
    }

    public void setEquipes( List < Equipe> equipes) {
        this.equipes = equipes;
    }

    public Lieu getLieuEvenement() {
        return lieuEvenement;
    }

    public void setLieuEvenement(Lieu lieuEvenement) {
        this.lieuEvenement = lieuEvenement;
    }
    
    @Override
    public String toString ( )
    {
        String s = "Evenement : id (" + id + ") " + this.activite + " Date :" + dateEvenement + " ";
        for ( Equipe equ : this.equipes )
        {
            s += "\n Equipe : " + equ;
        }
        return s;
    }
}
