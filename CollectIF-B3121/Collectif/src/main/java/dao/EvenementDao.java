package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Adherent;
import metier.modele.Evenement;

public class EvenementDao {
    
    public static void create(Evenement event) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(event);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public static Evenement update(Evenement event) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            event = em.merge(event);
        }
        catch(Exception e){
            throw e;
        }
        return event;
    }
    
    public static Evenement findById(long id) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Evenement event = null;
        try {
            event = em.find(Evenement.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return event;
    }
    
    public static List<Evenement> findAll() throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Evenement> events = null;
        try {
            Query q = em.createQuery("SELECT a FROM Evenement a");
            events = (List<Evenement>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        
        return events;
    }
	
    public static List<Evenement> findAllAvecSansLieu( boolean avecLieu ) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Evenement> events = null;
        try {
            Query q;
            if ( avecLieu )
            {
                q = em.createQuery("SELECT a FROM Evenement a WHERE a.lieuEvenement IS NOT NULL");
            }
            else
            {
                q = em.createQuery("SELECT a FROM Evenement a WHERE a.lieuEvenement IS NULL");
            }
            events = (List<Evenement>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        
        return events;
    }
    
    public static List<Evenement> findAllParAdherent ( Adherent a ) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Evenement> events = null;
        try {
            Query q = em.createQuery("SELECT e FROM Evenement e LEFT JOIN e.equipes AS equipes LEFT JOIN equipes.adherents AS adherents WHERE adherents = :adherent");
            q.setParameter("adherent", a);
            
            events = (List<Evenement>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        
        return events;
    }
}
