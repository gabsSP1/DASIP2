package vue;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.modele.Activite;
import metier.modele.Adherent;
import metier.modele.DemandeDEvenement;
import metier.modele.Evenement;
import metier.service.ServiceException;
import metier.service.ServiceMetier;
import static vue.Main.lireChaine;
import static vue.Main.lireInteger;

public class MainCreationDemandes {
    public static void main ( String [ ] args )
    {
        int idAdede = lireInteger ( " Id de l'adherent : " );
        
        while ( idAdede != -1 )
        {
            Adherent a = null;
            List<Evenement> evenements = null;

            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy" );
            Date date = null;

            try {
                date = formatter.parse("31/03/2016");
            } catch (ParseException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                a = ServiceMetier.recupererAdherentParId(idAdede);
                ServiceMetier.creerDemandeEvenement ( new DemandeDEvenement ( a,ServiceMetier.recupererActiviteParId(38), date ) );
                
                evenements = ServiceMetier.recupererEvenements();
                
                for ( Evenement ev : evenements )
                    System.out.println(ev);

            } catch (ServiceException ex) {
                System.out.println ( " * ERREUR : la demande n'a pas pu etre enregistree" );
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            idAdede = lireInteger ( " Id de l'adherent : " );
        }
    }
}
