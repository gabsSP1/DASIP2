package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Equipe;


public class EquipeDao {
    
    public static void create(Equipe adherent) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(adherent);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public static Equipe update(Equipe adherent) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            adherent = em.merge(adherent);
        }
        catch(Exception e){
            throw e;
        }
        return adherent;
    }
    
    public static Equipe findById(long id) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Equipe adherent = null;
        try {
            adherent = em.find(Equipe.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return adherent;
    }
    
    public static List<Equipe> findAll() throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Equipe> adherents = null;
        try {
            Query q = em.createQuery("SELECT a FROM Equipe a");
            adherents = (List<Equipe>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        
        return adherents;
    }
}
