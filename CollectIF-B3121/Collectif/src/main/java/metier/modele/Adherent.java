package metier.modele;

import com.google.maps.model.LatLng;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

 
@Entity
public class Adherent implements Serializable, Comparable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "VERSION", nullable = false)
    @Version
    private int version;
    
    private String nom;
    private String prenom;
    
    @Column(unique=true)
    private String mail;
    private String mdp;
    private String adresse;
    private Double longitude;
    private Double latitude;

    public Adherent() {
    }

    public Adherent(String nom, String prenom, String adresse, String mail, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.adresse = adresse;
        this.mdp = motDePasse;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMail() {
        return mail;
    }

    public String getAdresse() {
        return adresse;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setCoordonnees(LatLng latLng) {
        this.longitude = latLng.lng;
        this.latitude = latLng.lat;
    }

    @Override
    public String toString() {
        return "Adherent{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", adresse=" + adresse + ", longitude=" + longitude + ", latitude=" + latitude + '}';
    }

    @Override
    public int compareTo(Object o) {
        Adherent a = (Adherent) o;
        if ( id < a.getId ( ) )
        {
            return -1;
        }
        else if ( id > a.getId ( ) )
        {
            return 1;
        }
        return 0;
    }
}