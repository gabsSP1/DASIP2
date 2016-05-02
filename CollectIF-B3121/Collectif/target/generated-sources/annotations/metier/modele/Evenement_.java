package metier.modele;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import metier.modele.Activite;
import metier.modele.Equipe;
import metier.modele.Lieu;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-03-24T16:02:01")
@StaticMetamodel(Evenement.class)
public class Evenement_ { 

    public static volatile SingularAttribute<Evenement, Long> id;
    public static volatile ListAttribute<Evenement, Equipe> equipes;
    public static volatile SingularAttribute<Evenement, Activite> activite;
    public static volatile SingularAttribute<Evenement, Date> dateEvenement;
    public static volatile SingularAttribute<Evenement, Lieu> lieuEvenement;
    public static volatile SingularAttribute<Evenement, Integer> version;

}