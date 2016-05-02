package metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity
public class Equipe implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "VERSION", nullable = false)
    @Version
    private int version;
    
    @OneToMany
    private List < Adherent > adherents;

    public Equipe() {
    }

    public Equipe(ArrayList<Adherent> adherents) {
        this.adherents = adherents;
    }

    public Long getId() {
        return id;
    }

    public List<Adherent> getAdherents() {
        return adherents;
    }

    public void setAdherents(ArrayList<Adherent> adherents) {
        this.adherents = adherents;
    }
    
    @Override
    public String toString ( )
    {
        String s = " id : " + this.id + " {";
        for ( Adherent a : this.adherents )
        {
            s += a.getNom() + " | ";
        }
        s+= "}";
        return s;
    }
}
