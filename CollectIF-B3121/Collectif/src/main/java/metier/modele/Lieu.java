package metier.modele;

import com.google.maps.model.LatLng;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

@Entity
public class Lieu implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column(name = "VERSION", nullable = false)
    @Version
    private int version;
    
    private String denomination;
    private String description;
    private String adresse;
    private Double longitude;
    private Double latitude;
    
    @ManyToOne
    TypeDeLieu typeLieu;

    public Lieu() {
    }
     public Lieu(String denomination, String description, String adresse) {
        this.denomination = denomination;
        this.description = description;
        this.adresse = adresse;
    }


    public Integer getId() {
        return id;
    }

    public String getDenomination() {
        return denomination;
    }


    public String getDescription() {
        return description;
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
    
    public TypeDeLieu getTypeLieu () {
        return typeLieu;
    }
    
    public void setCoordonnees(LatLng latLng) {
        this.longitude = latLng.lng;
        this.latitude = latLng.lat;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setTypeLieu ( TypeDeLieu unTypeDeLieu )
    {
        this.typeLieu = unTypeDeLieu;
    }
    @Override
    public String toString() {
        return "Lieu{" + "id=" + id + ", denomination=" + denomination + ", description=" + description + ", adresse=" + adresse + ", longitude=" + longitude + ", latitude=" + latitude + '}';
    }
    
    
}
