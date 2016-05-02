package dao;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Adherent;

public class AdherentDao {
    
    public static void create(Adherent adherent) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(adherent);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public static Adherent update(Adherent adherent) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            adherent = em.merge(adherent);
        }
        catch(Exception e){
            throw e;
        }
        return adherent;
    }
    
    public static Adherent findById(long id) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Adherent adherent = null;
        try {
            adherent = em.find(Adherent.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return adherent;
    }
    
    public static Adherent findByEMailAndPassword ( String mail, String mdp )
    {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Adherent adh = null;
        try {
            Query q = em.createQuery("SELECT a FROM Adherent a WHERE a.mail =:mail AND a.mdp =:mdp").setParameter("mail", mail).setParameter("mdp", mdp);
            adh = (Adherent) q.getSingleResult();
        } catch ( Throwable thr )
        {
            
        }
        return adh;
    }

    public static Adherent findByEMail(String mail) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Adherent adh = null;
        try {
            Query q = em.createQuery("SELECT a FROM Adherent a WHERE a.mail =:mail").setParameter("mail", mail);
            adh = (Adherent) q.getSingleResult();
        } catch ( Throwable thr )
        {
            
        }
        return adh;
    }
    
    public static List<Adherent> findAll() throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Adherent> adherents = null;
        try {
            Query q = em.createQuery("SELECT a FROM Adherent a");
            adherents = (List<Adherent>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        
        return adherents;
    }
}
