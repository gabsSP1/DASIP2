package metier.modele;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import metier.modele.Activite;
import metier.modele.Adherent;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-03-24T16:02:01")
@StaticMetamodel(DemandeDEvenement.class)
public class DemandeDEvenement_ { 

    public static volatile SingularAttribute<DemandeDEvenement, Integer> id;
    public static volatile SingularAttribute<DemandeDEvenement, Activite> activite;
    public static volatile SingularAttribute<DemandeDEvenement, Date> dateEvenement;
    public static volatile SingularAttribute<DemandeDEvenement, Boolean> validee;
    public static volatile SingularAttribute<DemandeDEvenement, Adherent> adherent;
    public static volatile SingularAttribute<DemandeDEvenement, Integer> version;

}