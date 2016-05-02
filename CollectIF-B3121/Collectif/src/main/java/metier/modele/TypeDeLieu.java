package metier.modele;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;


@Entity
public class TypeDeLieu implements Serializable, Comparable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column(name = "VERSION", nullable = false)
    @Version
    private int version;
    
    private String libelle;
    
    
    public TypeDeLieu ( ){
    }
    
    public TypeDeLieu ( String unLibelle )
    {
        libelle = unLibelle;
    }

    public Integer getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    
    @Override
    public String toString() {
        return "TypeDeLieu{" + "id=" + id + ", libelle=" + libelle + '}';
    }

    @Override
    public int compareTo(Object o) {
        TypeDeLieu t = (TypeDeLieu) o;
        if ( id < t.getId ( ) )
        {
            return -1;
        }
        else if ( id > t.getId ( ) )
        {
            return 1;
        }
        return 0;
    }
}
