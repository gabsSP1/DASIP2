package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.TypeDeLieu;

public class TypeDeLieuDao {
    
    public static void create(TypeDeLieu typeLieu) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(typeLieu);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public static TypeDeLieu update(TypeDeLieu typeLieu) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            typeLieu = em.merge(typeLieu);
        }
        catch(Exception e){
            throw e;
        }
        return typeLieu;
    }
    
    public static TypeDeLieu findById(int id) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        TypeDeLieu typeLieu = null;
        try {
            typeLieu = em.find(TypeDeLieu.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return typeLieu;
    }
    
    public static List<TypeDeLieu> findAll() throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<TypeDeLieu> typeLieux = null;
        try {
            Query q = em.createQuery("SELECT a FROM TypeDeLieu a");
            typeLieux = (List<TypeDeLieu>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        
        return typeLieux;
    }
}
