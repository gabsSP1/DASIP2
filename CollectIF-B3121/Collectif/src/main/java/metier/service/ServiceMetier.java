package metier.service;

import dao.ActiviteDao;
import dao.AdherentDao;
import dao.DemandeDEvenementDao;
import dao.EquipeDao;
import dao.EvenementDao;
import dao.JpaUtil;
import dao.LieuDao;
import dao.TypeDeLieuDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import metier.modele.Activite;
import metier.modele.Adherent;
import metier.modele.DemandeDEvenement;
import metier.modele.Evenement;
import metier.modele.Equipe;
import metier.modele.Lieu;
import metier.modele.TypeDeLieu;


public class ServiceMetier {
    
    private static final String MOT_DE_PASSE_ADMINISTRATEUR = "root";
    
    /**
     * Permet d'inscrire un nouvel adhérent.
     * @param a : l'objet Adherent à enregistrer
     * @throws ServiceException En cas de problème la transaction est annulée et une exception levée.
     */
    public static void ajouterAdherent ( Adherent a ) throws ServiceException
    {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        a.setCoordonnees(ServiceTechnique.obtenirLatLongViaAdresse( a.getAdresse() ));

        try 
        {
            AdherentDao.create ( a );
            JpaUtil.validerTransaction ( );
        }
        catch ( Throwable e )
        {
            JpaUtil.annulerTransaction ( );
            throw new ServiceException ( "ERREUR : creation d'un adherant" );
        }
        finally
        {
            JpaUtil.fermerEntityManager ( );
        }
    }
    
    /**
     * Permet d'identifier un adhérent par son email et son mot de passe.
     * @param mail : l'adresse email entrée par l'adhérent souhaitant se connecter
     * @param mdp : le mot de passe entré par l'adhérent souhaitant se connecter
     * @return l'objet Adherent identifié si un tel Adherent est trouvé, et null sinon.
     * @throws ServiceException en cas de probleme au noveau de la couche persistance
     */
    public static Adherent identifierAdherentParEmailEtMotDePasse ( String mail, String mdp ) throws ServiceException
    {
        JpaUtil.creerEntityManager();
        Adherent adherent = null;
        
        try 
        {
            adherent = AdherentDao.findByEMailAndPassword ( mail, mdp );
        }
        catch ( Throwable e )
        {
            throw new ServiceException ( "ERREUR : identification d'un adherant" );
        }
        finally
        {
            JpaUtil.fermerEntityManager ( );
        }
        
        return adherent;
    }
    
    /**
     * Permet de récupérer un adhérent via son adresse email
     * @param mail : l'adresse email associee a l'adherent
     * @return l'objet Adherent associe a l'email si un tel Adherent est trouvé, et null sinon.
     * @throws ServiceException en cas de probleme au noveau de la couche persistance
     */
    public static Adherent recupererAdherentParEmail ( String mail ) throws ServiceException
    {
        JpaUtil.creerEntityManager();
        Adherent adherent = null;
        
        try 
        {
            adherent = AdherentDao.findByEMail ( mail );
        }
        catch ( Throwable e )
        {
            throw new ServiceException ( "ERREUR : récupération d'un adherant" );
        }
        finally
        {
            JpaUtil.fermerEntityManager ( );
        }
        
        return adherent;
    }
    
    /**
     * Permet de récupérer un adhérent via son identifiant unique
     * @param id : l'identifiant de l'adherent
     * @return l'objet Adherent associe a l'id si un tel Adherent est trouvé, et null sinon.
     * @throws ServiceException en cas de probleme au noveau de la couche persistance
     */
    public static Adherent recupererAdherentParId ( int id ) throws ServiceException
    {
        JpaUtil.creerEntityManager();
        Adherent adherent = null;
        
        try 
        {
            adherent = AdherentDao.findById ( id );
        }
        catch ( Throwable e )
        {
            throw new ServiceException ( "ERREUR : récupération d'un adherent" );
        }
        finally
        {
            JpaUtil.fermerEntityManager ( );
        }
        
        return adherent;
    }
    
	/**
     * Permet de récupérer une activité via son identifiant unique
     * @param id : l'identifiant de l'activite
     * @return l'objet Activite associe a l'id si une telle Activite est trouvée, et null sinon.
     * @throws ServiceException en cas de probleme au noveau de la couche persistance
     */
    public static Activite recupererActiviteParId ( int id ) throws ServiceException
    {
        JpaUtil.creerEntityManager();
        Activite activite = null;
        
        try 
        {
            activite = ActiviteDao.findById ( id );
        }
        catch ( Throwable e )
        {
            throw new ServiceException ( "ERREUR : recuperation d'une activite" );
        }
        finally
        {
            JpaUtil.fermerEntityManager ( );
        }
        
        return activite;
    }
        
    /**
     * Permet d'identifier l'administrateur.
     * @param mdp : le mot de passe entré par l'utilisateur souhaitant se connecter en tant qu'administrateur
     * @return true si l'administrateur est identifié, et false sinon.
     */
    public static boolean identifierAdministrateurParMotDePasse ( String mdp )
    {
        return mdp.equals(MOT_DE_PASSE_ADMINISTRATEUR);
    }
    
    /**
     * Permet de créer une nouvelle demande
     * @param demande : la demande à enregistrer.
	 * @see creerEvenement, crée un évènement si le nombre de demandes realisées est suffisant
     * @throws ServiceException En cas de problème la transaction est annulée et une exception levée.
     */
    public static void creerDemandeEvenement ( DemandeDEvenement demande ) throws ServiceException
    {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();

        try 
        {
            DemandeDEvenementDao.create ( demande );
            JpaUtil.validerTransaction ( );
            
            Activite a = demande.getActivite();
			
			// si, après avoir créé la nouvelle demande, il y a assez de demandes pour l'activité et le jour spécifié,
			// un évènement est créé par la méthode creerEvenement ( );
            long nbDemandes = DemandeDEvenementDao.compterDemandesDEvenementPourDateEtActivite(demande);
            
            if ( nbDemandes == a.getNbParticipants() )
            {
                ServiceMetier.creerEvenement ( demande.getDateEvenement ( ), a );
            }
        }
        catch ( Throwable e )
        {
            JpaUtil.annulerTransaction ( );
            throw new ServiceException ( "ERREUR : creation d'une demande d'evènement" );
        }
        finally
        {
            JpaUtil.fermerEntityManager ( );
        }
    }

	/**
     * Permet de créer un évènement
     * @param dateEvenement : la date de l'évènement
     * @param a : l'activite associe à l'évènement
     * @throws ServiceException En cas de problème la transaction est annulée et une exception levée.
     */
    private static void creerEvenement ( Date dateEvenement, Activite a ) throws ServiceException
    {
        JpaUtil.ouvrirTransaction();
        
        try 
        {
            List<DemandeDEvenement> demandes  = DemandeDEvenementDao.findDemandesEnCoursPourActiviteEtDate ( dateEvenement, a );
            
            Evenement e = new Evenement ( a, dateEvenement, null, null );
            
            
            // on affecte les utilisateurs dans les équipes, selon l'ordre dans lequel ils apparaisseent dans la liste
			// des demandes
            
            ArrayList < Equipe > equipes = new ArrayList < > ( );
            int i = 0;
			
            ArrayList < Adherent > equ1 = new ArrayList < > ( );
            ArrayList < Adherent > equ2 = null;
            Equipe equipe2 = null;
			
			// si l'activité n'est pas par équipe, on considère que tous les utilisateurs sont dans la même équipe
            Equipe equipe1 = new Equipe ( equ1 );
            equipes.add( equipe1 );
			
			// si l'acitivité est par équipe, on crée une seconde équipe
            if ( a.isParEquipe() )
            {
                equ2 = new ArrayList < > ( );
                equipe2 = new Equipe ( equ2 );
                equipes.add( equipe2 );
            }
			
            for ( DemandeDEvenement d : demandes )
            {
				// on indique que le service a accédé aux demandes des utilisateurs (en maraquant chacune comme étant "validée")
				// afin de ne pas traiter deux fois la même demande
                d.setValidee(true);
                DemandeDEvenementDao.update(d);
                
                // affectation des équipes
                if ( a.isParEquipe() )
                {
                    if ( i < a.getNbParticipants() / 2 )
                    {
                        equ1.add(d.getAdherent());
                    }
                    else
                    {
                        equ2.add(d.getAdherent());
                    }
                    i++;
                }
                else
                {
                    equ1.add(d.getAdherent());
                }
            }
            
            e.setEquipes(equipes);
            EquipeDao.create(equipe1);
            if ( a.isParEquipe() )
            {
                EquipeDao.create(equipe2);
            }
            EvenementDao.create(e);
            JpaUtil.validerTransaction();
        }
        catch ( Throwable e )
        {
            e.printStackTrace();
            JpaUtil.annulerTransaction ( );
            throw new ServiceException ( "ERREUR : creation d'un d'évènement" );
        }
    }
	
	
    /**
     * Permet d'attribuer un lieu à un évènement
     * @param evenement : l'evenement concerne
     * @param l : le lieu que l'on souhaite attribuer
     * @throws ServiceException En cas de problème la transaction est annulée et une exception levée.
     * @throws ServiceException Si l'on essaie d'attribuer un lieu dont l'usage ne permet pas la pratique de l'activite,
	 * une exception est levée
     */
    public static void definirLieuPourEvenement ( Evenement evenement, Lieu l ) throws ServiceException
    {
        if ( evenement.getActivite().getTypeLieu().compareTo(l.getTypeLieu()) != 0 )
            throw new ServiceException ( "ERREUR : attribution d'un lieu - type de lieu incompatible !");
        
        evenement.setLieuEvenement(l);
        
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();

        try 
        {
            EvenementDao.update(evenement);
            JpaUtil.validerTransaction ( );
            
		}
        catch ( Throwable e )
        {
            JpaUtil.annulerTransaction ( );
            throw new ServiceException ( "ERREUR : attribution d'un lieu pour l'évènement " + evenement.getId() );
        }
        finally
        {
            JpaUtil.fermerEntityManager ( );
        }
    }
	
	/**
     * Permet de recuperer les demandes d'évènement pour un adhérent donné
     * @param a : l'adhérent concerné
	 * @returns la collection des demandes d'évènement de l'adhérent
     * @throws ServiceException en cas de probleme au noveau de la couche persistance
     */    
    public static List<DemandeDEvenement> recupererDemandesPourAdherent ( Adherent a ) throws ServiceException
    {
        JpaUtil.creerEntityManager();
        List<DemandeDEvenement> demandes = null;
        
        try 
        {
            demandes = DemandeDEvenementDao.findDemandesParAdherent ( a );
        }
        catch ( Throwable e )
        {
            throw new ServiceException ( "ERREUR : obtention des demandes pour l'adherent " + a.getId() );
        }
        finally
        {
            JpaUtil.fermerEntityManager ( );
        }
        
        return demandes;
    }
	
    /**
     * Permet de recuperer les évènements pour un adhérent donné
     * @param a : l'adhérent concerné
	 * @returns la collection des évènments de l'adhérent
     * @throws ServiceException en cas de probleme au noveau de la couche persistance
     */   
    public static List<Evenement> recupererEvenementsPourAdherent ( Adherent a ) throws ServiceException
    {
        JpaUtil.creerEntityManager();
        List<Evenement> evenements = null;
        
        try 
        {
            evenements = EvenementDao.findAllParAdherent(a);
        }
        catch ( Throwable e )
        {
            throw new ServiceException ( "ERREUR : obtention des evenements pour l'adherent " + a.getId() );
        }
        finally
        {
            JpaUtil.fermerEntityManager ( );
        }
        
        return evenements;
    }
	
    /**
     * Permet de récupérer un évènement via son identifiant unique
     * @param id : l'identifiant de l'évènement
     * @return l'objet Evenement associe a l'id si un tel Evenement est trouvé, et null sinon.
     * @throws ServiceException en cas de probleme au noveau de la couche persistance
     */ 
    public static Evenement recupererEvenementParId ( int idEvenement ) throws ServiceException
    {
        JpaUtil.creerEntityManager();
        Evenement evenement = null;
        
        try 
        {
            evenement = EvenementDao.findById(idEvenement);
        }
        catch ( Throwable e )
        {
            throw new ServiceException ( "ERREUR : recuperation de l'evenement " + idEvenement );
        }
        finally
        {
            JpaUtil.fermerEntityManager ( );
        }
        
        return evenement;
    }
    
    /**
     * Permet de récupérer les évèenements ayant (ou non) un lieu leur étant attribué
     * @param avecLieu : true si l'on souhaite récupérer les évènement avec un lieu défini, false sinon
     * @return la collection d'objets Evenement correspondants à la recherche
     * @throws ServiceException en cas de probleme au noveau de la couche persistance
     */ 
    public static List<Evenement> recupererEvenementsAvecOuSansLieu ( boolean avecLieu ) throws ServiceException
    {
        JpaUtil.creerEntityManager();
        List<Evenement> evenements = null;
        
        try 
        {
            evenements = EvenementDao.findAllAvecSansLieu(avecLieu);
        }
        catch ( Throwable e )
        {
            throw new ServiceException ( "ERREUR : obtention des evenements avec ou sans lieu" );
        }
        finally
        {
            JpaUtil.fermerEntityManager ( );
        }
        
        return evenements;
    }
    	
    /**
     * Permet de récupérer tous les évènements sauvegardés en base
     * @return la collections des objets Evenement sauvegardés
     * @throws ServiceException en cas de probleme au noveau de la couche persistance
     */ 
    public static List<Evenement> recupererEvenements (  ) throws ServiceException
    {
        JpaUtil.creerEntityManager();
        List<Evenement> evenements = null;
        
        try 
        {
            evenements = EvenementDao.findAll();
        }
        catch ( Throwable e )
        {
            throw new ServiceException ( "ERREUR : obtention des evenements" );
        }
        finally
        {
            JpaUtil.fermerEntityManager ( );
        }
        
        return evenements;
    }
    
    /**
     * Permet de récupérer tous les évènements sauvegardés en base
     * @return la collections des objets Evenement sauvegardés
     * @throws ServiceException en cas de probleme au noveau de la couche persistance
     */ 
    public static List<DemandeDEvenement> recupererDemandesDEvenements (  ) throws ServiceException
    {
        JpaUtil.creerEntityManager();
        List<DemandeDEvenement> ddes = null;
        
        try 
        {
            ddes = DemandeDEvenementDao.findAll();
        }
        catch ( Throwable e )
        {
            throw new ServiceException ( "ERREUR : obtention des demandes d'évènement" );
        }
        finally
        {
            JpaUtil.fermerEntityManager ( );
        }
        
        return ddes;
    }
	
    /**
     * Permet de récupérer la liste des activités offertes par le service Collect'IF
     * @return la collection des objets Activites sauvegardés
     * @throws ServiceException en cas de probleme au noveau de la couche persistance
     */ 
    public static List<Activite> recupererActivites ( ) throws ServiceException
    {
        JpaUtil.creerEntityManager();
        List<Activite> activites = null;
        
        try 
        {
            activites = ActiviteDao.findAll();
        }
        catch ( Throwable e )
        {
            throw new ServiceException ( "ERREUR : recuperation de la liste des activites" );
        }
        finally
        {
            JpaUtil.fermerEntityManager ( );
        }
        
        return activites;
    }
	
    /**
     * Permet de récupérer la liste des lieux associés au service Collect'IF
     * @return la collection des objets Lieu sauvegardés
     * @throws ServiceException en cas de probleme au noveau de la couche persistance
     */ 
    public static List<Lieu> recupererLieux ( ) throws ServiceException
    {
        JpaUtil.creerEntityManager();
        List<Lieu> lieux = null;
        
        try 
        {
            lieux = LieuDao.findAll();
        }
        catch ( Throwable e )
        {
            throw new ServiceException ( "ERREUR : recuperation de la liste des lieux" );
        }
        finally
        {
            JpaUtil.fermerEntityManager ( );
        }
        
        return lieux;
    }
	
    /**
     * Permet de récupérer un lieu via son identifiant unique
     * @return l'objet Lieu associe a l'id si un tel Lieu est trouvé, et null sinon.
     * @throws ServiceException en cas de probleme au noveau de la couche persistance
     */ 
    public static Lieu recupererLieuParId ( int idLieu ) throws ServiceException
    {
        JpaUtil.creerEntityManager();
        Lieu lieu = null;
        
        try 
        {
            lieu = LieuDao.findById(idLieu);
        }
        catch ( Throwable e )
        {
            throw new ServiceException ( "ERREUR : recuperation du lieu " + idLieu );
        }
        finally
        {
            JpaUtil.fermerEntityManager ( );
        }
        
        return lieu;
    }
	
    /**
     * Permet de récupérer la liste des types de lieux associés au service Collect'IF
     * @return la collection des objets TypeDeLieu sauvegardés
     * @throws ServiceException en cas de probleme au noveau de la couche persistance
     */ 
    public static List<TypeDeLieu> recupererTypesDeLieux ( ) throws ServiceException
    {
        JpaUtil.creerEntityManager();
        List<TypeDeLieu> types = null;
        
        try 
        {
            types = TypeDeLieuDao.findAll();
        }
        catch ( Throwable e )
        {
            throw new ServiceException ( "ERREUR : recuperation de la liste des types de lieux" );
        }
        finally
        {
            JpaUtil.fermerEntityManager ( );
        }
        
        return types;
    }
  
    /**
     * Permet de récupérer la adherents inscrits au service Collect'IF
     * @return la collection des objets Adherent sauvegardés
     * @throws ServiceException en cas de probleme au noveau de la couche persistance
     */ 
    public static List<Adherent> recupererAdherents ( ) throws ServiceException
    {
        JpaUtil.creerEntityManager();
        List<Adherent> adherents = null;
        
        try 
        {
            adherents = AdherentDao.findAll();
        }
        catch ( Throwable e )
        {
            throw new ServiceException ( "ERREUR : recuperation de la liste des adherents" );
        }
        finally
        {
            JpaUtil.fermerEntityManager ( );
        }
        
        return adherents;
    }
}
