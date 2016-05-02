package metier.modele;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import metier.modele.TypeDeLieu;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-03-24T16:02:01")
@StaticMetamodel(Lieu.class)
public class Lieu_ { 

    public static volatile SingularAttribute<Lieu, Integer> id;
    public static volatile SingularAttribute<Lieu, String> adresse;
    public static volatile SingularAttribute<Lieu, String> description;
    public static volatile SingularAttribute<Lieu, Double> longitude;
    public static volatile SingularAttribute<Lieu, Double> latitude;
    public static volatile SingularAttribute<Lieu, String> denomination;
    public static volatile SingularAttribute<Lieu, Integer> version;
    public static volatile SingularAttribute<Lieu, TypeDeLieu> typeLieu;

}