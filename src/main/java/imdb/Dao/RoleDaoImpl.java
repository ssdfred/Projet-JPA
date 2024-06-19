package imdb.Dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import imdb.entities.Acteur;
import imdb.entities.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class RoleDaoImpl implements RoleDao {
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("IMDB");
	private EntityManager em;
	private static final Logger logger = LoggerFactory.getLogger(RoleDaoImpl.class);
	@Override
	public void save(List<Role> roles) {
		em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            for (Role role: roles) {
                em.persist(role);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.error("Erreur lors de la sauvegarde des acteurs", e);
            throw new RuntimeException("Erreur lors de la sauvegarde des acteurs", e);
        } finally {
            em.close();
        }
    }


	@Override
	public Role findById(int id) {
	    try (EntityManager em = emf.createEntityManager()) {
	        return em.find(Role.class, id);
	    } catch (Exception e) {
	        throw new RuntimeException("Erreur lors de la recherche du rôle par ID : " + id, e);
	    }
	}
	@Override
	public List<Role> findAll() {
		  	em = emf.createEntityManager();
	        TypedQuery<Role> query = em.createQuery("SELECT r FROM Role r", Role.class);
	        List<Role> roles = query.getResultList();
	        em.close();
	        return roles;
	    }

	@Override
	public void update(Role role) {
		 em = emf.createEntityManager();
	        em.getTransaction().begin();
	        try {
	            em.persist(role);
	            em.getTransaction().commit();
	        } catch (Exception e) {
	            if (em.getTransaction().isActive()) {
	                em.getTransaction().rollback();
	            }
	            throw new RuntimeException("Erreur lors de la mise à jour du rôle", e);
	        } finally {
	            em.close();
	        }
	    }

	@Override
	public void delete(Role role) {
		 em = emf.createEntityManager();
	        em.getTransaction().begin();
	        try {
	            em.remove(em.contains(role) ? role : em.merge(role));
	            em.getTransaction().commit();
	        } catch (Exception e) {
	            if (em.getTransaction().isActive()) {
	                em.getTransaction().rollback();
	            }
	            throw new RuntimeException("Erreur lors de la suppression du rôle", e);
	        } finally {
	            em.close();
	        }
	    }
	public void closeEntityManager() {
        emf.close();
    }
}