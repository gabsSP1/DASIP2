package vue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.modele.Activite;
import metier.modele.Adherent;
import metier.modele.Lieu;
import metier.modele.TypeDeLieu;
import metier.modele.DemandeDEvenement;
import metier.modele.Evenement;
import metier.service.ServiceException;
import metier.service.ServiceMetier;
import java.util.Date;
import java.text.*;
import metier.modele.Equipe;
import metier.service.ServiceTechnique;

/**
 *
 * @author cdautreme
 */
public class Main {
    public static String lireChaine(String invite) {
        String chaineLue = null;
        System.out.print(invite);
        try {
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            chaineLue = br.readLine();
        } catch (IOException exception) {
            exception.printStackTrace(System.err);
        }
        return chaineLue;

    }

    public static Integer lireInteger(String invite) {
        Integer valeurLue = null;
        try {
            valeurLue = new Integer(lireChaine(invite));
        } catch (java.lang.NumberFormatException e) {
            System.out.println("erreur de saisie" );
            valeurLue = lireInteger(invite);
        }
        return valeurLue;
    }

    public static Integer lireInteger(String invite, List<Integer> valeursPossibles) {
        Integer valeurLue = null;
        try {
            valeurLue = new Integer(lireChaine(invite));
            if (!(valeursPossibles.contains(valeurLue))) {
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println("erreur de saisie" );
            valeurLue = lireInteger(invite, valeursPossibles);
        }
        return valeurLue;
    }

    private static void printMenu ( )
    {
        System.out.println ( "*** Bienvenue sur COLLECT'IF ***\n" );
        System.out.println ( "Choix disponibles :\n" );
        System.out.println ( " * 1 - Inscription d'un adherent" );
        System.out.println ( " * 2 - Connexion d'un adherent" );
        System.out.println ( " * 3 - Connexion du gestionnaire" );
        System.out.println ( " ____________________\n" );
        System.out.println ( " * 4 - Lister les activites" );
        System.out.println ( " * 5 - Lister les lieux" );
        System.out.println ( " * 6 - Lister les types de lieu" );
        System.out.println ( " ____________________\n" );
        System.out.println ( " * 7 - Lister les adherents" );
        System.out.println ( " * 8 - Lister toutes les demandes d'évènement" );
        System.out.println ( " ____________________\n" );
        System.out.println ( " * 9 - Faire une demande d'evenement" );
        System.out.println ( " ____________________\n" );
        System.out.println ( " * 10 - Consulter l'historique des demandes d'un adherent" );
        System.out.println ( " * 11 - Consulter les evenements d'un adherent" );
        System.out.println ( " ____________________\n" );
        System.out.println ( " * 12 - Afficher un evenement" );
        System.out.println ( " * 13 - Consulter les evenements (gestionnaire)" );
        System.out.println ( " * 14 - Affecter un lieu a un evenement (gestionnaire)" );
        System.out.println ( " ____________________\n" );
        System.out.println ( " * 0 - QUITTER" );
        System.out.println ( " ____________________\n\n" );
    }
    
    public static void main(String[] args) {        
        Integer choix = -1;
        Main.printMenu ( );
        
        choix = Main.lireInteger(" Votre choix ? " );
        
        while ( choix != 0 )
        {
            switch ( choix )
            {
                case 1 :
                    String nom = Main.lireChaine(" Nom : " );
                    String prenom = Main.lireChaine(" Prenom : " );
                    String adresse = Main.lireChaine(" Adresse : " );
                    String mail = Main.lireChaine(" Mail : " );
                    String motDePasse = Main.lireChaine(" Mot de passe : " );
                        
                    System.out.println ( "\n * Apputez sur n'importe quelle touche pour continuer..." );

                    try {
                        System.in.read();
                    } catch (IOException e) {
                        e.printStackTrace ( );
                    }
                
                    try {
                        ServiceMetier.ajouterAdherent( new Adherent ( nom, prenom, adresse, mail, motDePasse ) );
                        Adherent adhCree = ServiceMetier.recupererAdherentParEmail(mail);
                        System.out.println ( " * Adherent inscrit ! Id : " + adhCree.getId());
                        System.out.println ( ServiceTechnique.envoyerEMail ( mail, "Bienvenue chez Collect'IF", "Bonjour " + prenom + ",\n Nous vous confirmons votre adhesion a l'association Collecti'IF. Votre numero d'adherent est : " + adhCree.getId() + "." ) );
                    } catch (ServiceException ex) {
                        System.out.println ( " * ERREUR : La creation d'un adherant a echoue !" );
                        System.out.println ( ServiceTechnique.envoyerEMail ( mail, "Bienvenue chez Collect'IF", "Bonjour " + prenom + ",\n Vous adhesion a l'association Collect'IF a malencontreusement echoue. Merci de recommencer ulterieurement." ) );
                    }
                    
                    break;
                case 2 :
                    String email = Main.lireChaine(" Entrez votre email : " );
                    String mdp = Main.lireChaine(" Entrez votre mot de passe : " );
                    Adherent a;
            
                    try {
                        if ( ( a = ServiceMetier.identifierAdherentParEmailEtMotDePasse(email, mdp) ) == null )
                        {
                            System.out.println ( " * Erreur de connexion !" );
                        }
                        else
                        {
                            System.out.println ( " * Utilisateur connecte : " + a );
                        }
                    } catch (ServiceException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
            
                    break;
                case 3 :
                    String mdpGest = Main.lireChaine(" Entrez le mot de passe gestionnaire : " );
                    if ( ServiceMetier.identifierAdministrateurParMotDePasse(mdpGest) )
                    {
                        System.out.println ( " * Gestionnaire connecte !" );
                    }
                    else
                    {
                        System.out.println ( " * Erreur de connexion !" );
                    }
                    break;
                case 4 :
                    List<Activite> activites = null;
                    try {
                        activites = ServiceMetier.recupererActivites();
                    } catch (ServiceException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    for ( Activite ac : activites )
                        System.out.println(ac);
                    break;
                case 5 :
                    List<Lieu> lieux = null;
                    try {
                        lieux = ServiceMetier.recupererLieux();
                    } catch (ServiceException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    for ( Lieu l : lieux )
                        System.out.println(l);
                    break;
                case 6 :
                    List<TypeDeLieu> types = null;
                    try {
                        types = ServiceMetier.recupererTypesDeLieux();
                    } catch (ServiceException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    for ( TypeDeLieu t : types )
                        System.out.println(t);
                    break;
                case 7 :
                    List<Adherent> adherents = null;
                    try {
                        adherents = ServiceMetier.recupererAdherents();
                    } catch (ServiceException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    for ( Adherent ad : adherents )
                        System.out.println(ad);
                    break;
                case 8:
                    List<DemandeDEvenement> ddes = null;
                    try {
                        ddes = ServiceMetier.recupererDemandesDEvenements();
                    } catch (ServiceException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    for ( DemandeDEvenement dddde : ddes )
                        System.out.println(dddde);
                    break;
                case 9 :
                    int idAdherent = lireInteger ( " Id de l'adherent : " );
                    int idActi = lireInteger ( " Id de l'activite : " );
                    String dateString = lireChaine ( " Date de l'activite (JJ/MM/AAAA) : " );
                    
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy" );
                    Date date = null;
                    
                    try {
                        date = formatter.parse(dateString);
                    } catch (ParseException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    try {
                        Adherent aa = ServiceMetier.recupererAdherentParId(idAdherent);
                        Activite acti = ServiceMetier.recupererActiviteParId(idActi);
                        
                        System.out.println ( "\n * Apputez sur n'importe quelle touche pour continuer..." );

                        try {
                            System.in.read();
                        } catch (IOException e) {
                            e.printStackTrace ( );
                        }
                        
                        ServiceMetier.creerDemandeEvenement ( new DemandeDEvenement ( aa, acti, date ) );
                        
                        System.out.println ( " * Demande enregistree !" );
                        
                    } catch (ServiceException ex) {
                        System.out.println ( " * ERREUR : la demande n'a pas pu etre enregistree" );
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case 10 :
                    int idAdede = lireInteger ( " Id de l'adherent : " );
                    
                    Adherent adhereeeent = null;
                    List<DemandeDEvenement> dddes = null;
                    try {
                        adhereeeent = ServiceMetier.recupererAdherentParId(idAdede);
                        dddes = ServiceMetier.recupererDemandesPourAdherent ( adhereeeent );
                    } catch (ServiceException ex) {
                        System.out.println ( " * ERREUR : impossible de recuperer la liste des demandes de cet adherent" );
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    for ( DemandeDEvenement d : dddes )
                        System.out.println(d);
                    
                    break;
                case 11 :
                    int idAde = lireInteger ( " Id de l'adherent : " );
                    
                    Adherent adhereeent = null;
                    List<Evenement> evs = null;
                    try {
                        adhereeent = ServiceMetier.recupererAdherentParId(idAde);
                        evs = ServiceMetier.recupererEvenementsPourAdherent ( adhereeent );
                    } catch (ServiceException ex) {
                        System.out.println ( " * ERREUR : impossible de recuperer la liste des evenements de cet adherent" );
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    for ( Evenement ev : evs )
                        System.out.println(ev);
                    
                    break;
                case 12 :
                    int idEvent = lireInteger ( " Id de l'evenement : " );
                    
                    Evenement unEvenement = null;
                    try {
                        unEvenement = ServiceMetier.recupererEvenementParId(idEvent);
                    
                        System.out.println ( "Evenement " + unEvenement.getId() + " - " + unEvenement.getActivite().getDenomination() + " le " + unEvenement.getDateEvenement() );
                        System.out.println ( "Participants : " + ( unEvenement.getActivite().isParEquipe() ? "(par equipe)" : "" ) );

                        if ( unEvenement.getActivite().isParEquipe() )
                        {
                            for ( Equipe eq : unEvenement.getEquipes() )
                            {
                                for ( Adherent ad_eq : eq.getAdherents() )
                                {
                                    System.out.println ( " - " + ad_eq.getPrenom() + " " + ad_eq.getNom() + " (num " + ad_eq.getId() + ")" );
                                }
                                System.out.println ( "" );
                            }
                        }
                    } catch (ServiceException ex) {
                        System.out.println ( " * ERREUR : impossible de recuperer cet evenement" );
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    break;
                case 13 :
                    List<Evenement> events = null;
                    try {
                        events = ServiceMetier.recupererEvenements();
                    } catch (ServiceException ex) {
                        System.out.println ( " * ERREUR : impossible de recuperer la liste des evenements" );
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    for ( Evenement ev : events )
                        System.out.println(ev);
                    
                    break;
                case 14 :
                    int idDeLEvent = lireInteger ( " Id de l'evenement : " );
                    int idLieu = lireInteger ( " Id du lieu : " );
                    
                    try {
                        Evenement evenement = ServiceMetier.recupererEvenementParId ( idDeLEvent );
                        Lieu lieeeu = ServiceMetier.recupererLieuParId ( idLieu );
                        
                        System.out.println ( "\n * Apputez sur n'importe quelle touche pour continuer..." );

                        try {
                            System.in.read();
                        } catch (IOException e) {
                            e.printStackTrace ( );
                        }
                        
                        ServiceMetier.definirLieuPourEvenement(evenement, lieeeu);
                        
                        System.out.println ( " * Le lieu a bien ete modifie !" );
                        System.out.println ( "\n" );
						
                        // simulation de l'envoi d'un email à un des adherents par affichage à l'écran
                        Adherent adhTmp = evenement.getEquipes().get(0).getAdherents().get(0);
                        String msgTmp = "Bonjour " + adhTmp.getPrenom() + ",\n\n\tComme vous l'aviez souhaité, Collect'IF organise un evenement de " + evenement.getActivite().getDenomination() + " le " + evenement.getDateEvenement() + ". Vous trouverez ci-dessous les details de cet evenement.\n\n\tAssociativement votre,\n\n\t\tLe responsable de l'Association.\n\nEvenement : " + evenement.getActivite().getDenomination() + "\nDate : " + evenement.getDateEvenement() + "\nLieu : " + evenement.getLieuEvenement().getDenomination() + ", " + evenement.getLieuEvenement().getAdresse() + " (à " + new DecimalFormat("#.##").format(ServiceTechnique.obtenirDistanceEntreAdherentEtLieu(adhTmp, lieeeu)) + " km de chez vous)\n\nVous jouerez ";
                        
                        if ( evenement.getActivite().isParEquipe() )
                        {
                            msgTmp += "avec :\n";
                            for ( int i = 1 ; i < evenement.getEquipes().get(0).getAdherents().size() ; i++ )
                            {
                                msgTmp += "\t" + evenement.getEquipes().get(0).getAdherents().get(i).getPrenom() + " " + evenement.getEquipes().get(0).getAdherents().get(i).getNom() + "\n";
                            }
                            msgTmp += "Contre :\n";
                            for ( int i = 0 ; i < evenement.getEquipes().get(1).getAdherents().size() ; i++ )
                            {
                                msgTmp += "\t" + evenement.getEquipes().get(1).getAdherents().get(i).getPrenom() + " " + evenement.getEquipes().get(1).getAdherents().get(i).getNom() + "\n";
                            }
                        }
                        else
                        {
                            msgTmp += "avec :\n";
                            for ( int i = 0 ; i < evenement.getEquipes().get(0).getAdherents().size() ; i++ )
                            {
                                msgTmp += "\t" + evenement.getEquipes().get(0).getAdherents().get(i).getPrenom() + " " + evenement.getEquipes().get(0).getAdherents().get(i).getNom() + "\n";
                            }
                        }
                        
                        System.out.println ( ServiceTechnique.envoyerEMail ( adhTmp.getMail(), "Nouvel Evenement Collect'IF", msgTmp ) );
    
                    } catch (ServiceException ex) {
                        System.out.println ( " * ERREUR : impossible de modifier le lieu de l'evenement" );
                    } catch (Exception e) {
                        System.out.println ( " * ERREUR : impossible de modifier le lieu de l'evenement" );
                    }
                    break;
                default :
                    System.out.println ( " * ERREUR : choix incorrect " );
                    break;
            }
            
            System.out.println ( "\n * Apputez sur n'importe quelle touche pour continuer..." );
            
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace ( );
            }
            
            Main.printMenu ( );
        
            choix = Main.lireInteger(" Votre choix ? " );
        }
        
    }
}
