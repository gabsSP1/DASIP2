/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.util.Collection;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javax.persistence.Query;
import com.google.maps.*;
import com.google.maps.model.*;
import dao.ActiviteDao;
import dao.AdherentDao;
import dao.DemandeDEvenementDao;
import dao.EvenementDao;
import dao.JpaUtil;
import dao.LieuDao;
import dao.TypeDeLieuDao;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.modele.*;
import metier.service.ServiceMetier;
import metier.service.ServiceTechnique;
/**
 *
 * @author pfinnerty
 */
public class Main_1 {
    
    
    
    public static void main2 ( String [ ] args ) throws Throwable
    {
       /** //Test service fournis :
        Adherent adh;
        JpaUtil.creerEntityManager();
       /** JpaUtil.ouvrirTransaction();
        
        try {
            AdherentDao.create(adh);
        } catch (Throwable ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JpaUtil.validerTransaction();
        
        JpaUtil.ouvrirTransaction();
        adh = AdherentDao.findById(1);
        adh.setPrenom("Jesus");
        AdherentDao.update(adh);
        
        JpaUtil.validerTransaction();
        
        for ( Adherent a : AdherentDao.findAll() )
        {
            System.out.println(a);
        }
        
        JpaUtil.fermerEntityManager();
        
        */
        
        //Test Lieu
        /*JpaUtil.creerEntityManager();
        Lieu l;
        Lieu l = new Lieu("Halle Collette Besson", "Gymnase", "13 avenue des Arts, 69100 Villeurbanne");
        
        JpaUtil.ouvrirTransaction();
        
        try {
            LieuDao.create(l);
        } catch (Throwable ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JpaUtil.validerTransaction();
        
        JpaUtil.ouvrirTransaction();
        l = LieuDao.findById(101);
        l.setDescription("Gymnase polyvalent");
        LieuDao.update(l);
        
        JpaUtil.validerTransaction();
        
        for ( Lieu ll : LieuDao.findAll() )
        {
            System.out.println(ll);
        }
        
        JpaUtil.fermerEntityManager();*/
        
        //Test Activite
        /*JpaUtil.creerEntityManager();
        Activite a;
        /*a = new Activite("Babyfoot", Boolean.TRUE, 5);
        
        JpaUtil.ouvrirTransaction();
        
        try {
            ActiviteDao.create(a);
        } catch (Throwable ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JpaUtil.validerTransaction();*/
        
        /*JpaUtil.ouvrirTransaction();
        a = ActiviteDao.findById(151);
        a.setDenomination("Pieds de bébé"); // nom québéquois du babyfoot
        ActiviteDao.update(a);
        
        JpaUtil.validerTransaction();
        
        for ( Activite aa : ActiviteDao.findAll() )
        {
            System.out.println(aa);
        }
        
        JpaUtil.fermerEntityManager();*/
        
        //----- INSERTION DES TYPES DE LIEUX
        
        /*
        
        JpaUtil.creerEntityManager();
        
        //1 on va créer les types de lieu
        JpaUtil.ouvrirTransaction();
        
        try {
            TypeDeLieuDao.create(new TypeDeLieu ("Gymnase"));
            TypeDeLieuDao.create(new TypeDeLieu ("Local Associatif"));
            TypeDeLieuDao.create(new TypeDeLieu ("Stade"));
            
            JpaUtil.validerTransaction();
        } catch (Throwable ex) {
            JpaUtil.annulerTransaction();
        }
        
        
        JpaUtil.fermerEntityManager();
                
        */
        
        //----- TESTS DU DAO D'ADHERENT
        
        //System.out.println (ServiceMetier.identifierAdministrateurParMotDePasse( "root" ) );
        
        //*System.out.println ( a );
        
        //----- TEST CREATION DEMANDE EVENEMENT
        
        /*JpaUtil.creerEntityManager();
        
        Activite acti = ActiviteDao.findById(38);
        Adherent ad1 = AdherentDao.findById(39);
        Adherent ad2 = AdherentDao.findById(40);
        Adherent ad3 = AdherentDao.findById(41);
        Adherent ad4 = AdherentDao.findById(42);
        
        JpaUtil.fermerEntityManager();
        
        DemandeDEvenement demande1 = new DemandeDEvenement( ad1, acti, new Date ( ) );
        DemandeDEvenement demande2 = new DemandeDEvenement( ad2, acti, new Date ( ) );
        DemandeDEvenement demande3 = new DemandeDEvenement( ad3, acti, new Date ( ) );
        DemandeDEvenement demande4 = new DemandeDEvenement( ad4, acti, new Date ( ) );
        
        ServiceMetier.creerDemandeEvenement( demande1 );
        ServiceMetier.creerDemandeEvenement( demande2 );
        ServiceMetier.creerDemandeEvenement( demande3 );
        ServiceMetier.creerDemandeEvenement( demande4 );*/
        
        
        JpaUtil.creerEntityManager();
        Adherent ad1 = AdherentDao.findById(39);

        Evenement event = EvenementDao.findById(1057);
        Lieu lieu = LieuDao.findById(5);
        
        JpaUtil.fermerEntityManager();
        ServiceMetier.definirLieuPourEvenement(event, lieu);
        
        JpaUtil.creerEntityManager();
        System.out.println ( "Tous les lieux");
        for ( Evenement e : EvenementDao.findAllParAdherent(ad1) )
        {
            System.out.println ( e );
        }
        
        System.out.println ( "Lieu attribué ");
        for ( Evenement e : EvenementDao.findAllAvecSansLieu( true ) )
        {
            System.out.println ( e );
        }
        
        System.out.println ( "Lieu non-attribué");
        for ( Evenement e : EvenementDao.findAllAvecSansLieu( false ) )
        {
            
            System.out.println ( e );
        }
        JpaUtil.fermerEntityManager();
        
        /*
        LatLng coords = ServiceTechnique.obtenirLatLongViaAdresse("8 Rue Arago, Villeurbanne");
        System.out.println ( coords );
        */
        
        /*JpaUtil.creerEntityManager();
        JpaUtil.fermerEntityManager();*/
    }
}
