package dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import metier.modele.Activite;
import metier.modele.Adherent;
import metier.modele.DemandeDEvenement;

public class DemandeDEvenementDao
{
    public static void create(DemandeDEvenement demande) throws Throwable 
    {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(demande);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public static DemandeDEvenement update(DemandeDEvenement demande) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            demande = em.merge(demande);
        }
        catch(Exception e){
            throw e;
        }
        return demande;
    }
    
    public static DemandeDEvenement findById(int id) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        DemandeDEvenement demande = null;
        try {
            demande = em.find(DemandeDEvenement.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return demande;
    }
    
    public static long compterDemandesDEvenementPourDateEtActivite ( DemandeDEvenement demande )
    {
        EntityManager em = JpaUtil.obtenirEntityManager ( );
        long nbDemandes = 0;
        
        try
        {
            TypedQuery < Long > query;
            query = em.createQuery ( "SELECT COUNT ( d ) FROM DemandeDEvenement d WHERE d.dateEvenement = :date AND d.activite = :activite AND d.validee = FALSE", Long.class );
            query.setParameter ( "date", demande.getDateEvenement ( ) );
            query.setParameter ( "activite", demande.getActivite ( ) );
            nbDemandes = query.getSingleResult ( );
        }
        catch ( Exception e )
        {
            throw e;
        }
        
        return nbDemandes;
    }
    
    public static List<DemandeDEvenement> findAll() throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<DemandeDEvenement> demandes = null;
        try {
            Query q = em.createQuery("SELECT a FROM DemandeDEvenement a");
            demandes = (List<DemandeDEvenement>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        
        return demandes;
    }

    public static List<DemandeDEvenement> findDemandesEnCoursPourActiviteEtDate(Date dateEvenement, Activite activite) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<DemandeDEvenement> demandes = null;
        try {
            Query q = em.createQuery("SELECT d FROM DemandeDEvenement d WHERE d.dateEvenement = :date AND d.activite = :activite AND d.validee = FALSE");
            q.setParameter ( "date", dateEvenement );
            q.setParameter ( "activite", activite );
            q.setMaxResults(activite.getNbParticipants ( ) );
            
            demandes = (List<DemandeDEvenement>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        
        return demandes;
    }
    
    public static List<DemandeDEvenement> findDemandesParAdherent(Adherent a) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<DemandeDEvenement> demandes = null;
        try {
            Query q = em.createQuery("SELECT d FROM DemandeDEvenement d WHERE d.adherent = :adherent");
            q.setParameter ( "adherent", a );
            
            demandes = (List<DemandeDEvenement>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        
        return demandes;
    }
}
