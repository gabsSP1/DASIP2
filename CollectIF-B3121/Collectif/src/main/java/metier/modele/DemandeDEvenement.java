package metier.modele;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;


@Table(uniqueConstraints=@UniqueConstraint(columnNames={"activite_id", "adherent_id", "dateEvenement"}))
@Entity
public class DemandeDEvenement implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column(name = "VERSION", nullable = false)
    @Version
    private int version;
    
    @ManyToOne
    Activite activite;
    
    @ManyToOne
    Adherent adherent;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    Date dateEvenement;
    
    boolean validee; // indique si la demande a été validée, i.e qu'un évènement a bien été créé
    
    public DemandeDEvenement () {
    }
    
    public DemandeDEvenement (Adherent unA, Activite uneActivite, Date uneDate)
    {
        dateEvenement = uneDate;
        activite = uneActivite;
        adherent = unA;
    }

    public Integer getId() {
        return id;
    }

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public void setAdherent(Adherent adherent) {
        this.adherent = adherent;
    }

    public Date getDateEvenement() {
        return dateEvenement;
    }

    public void setDateEvenement(Date dateEvenement) {
        this.dateEvenement = dateEvenement;
    }

    public boolean isValidee() {
        return validee;
    }

    public void setValidee(boolean validee) {
        this.validee = validee;
    }

    @Override
    public String toString() {
        return "DemandeDEvenement{" + "id=" + id + ", activite=" + activite + ", adherent=" + adherent + ", date=" + dateEvenement + '}';
    }
}
