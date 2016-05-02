package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Activite;

public class ActiviteDao {
    
    public static void create(Activite activite) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(activite);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public static Activite update(Activite activite) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            activite = em.merge(activite);
        }
        catch(Exception e){
            throw e;
        }
        return activite;
    }
    
    public static Activite findById(int id) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Activite activite = null;
        try {
            activite = em.find(Activite.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return activite;
    }
    
    public static List<Activite> findAll() throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Activite> activites = null;
        try {
            Query q = em.createQuery("SELECT a FROM Activite a");
            activites = (List<Activite>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        
        return activites;
    }
}
