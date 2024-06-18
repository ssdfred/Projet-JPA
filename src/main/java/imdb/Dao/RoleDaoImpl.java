package imdb.Dao;

import java.util.List;

import imdb.entities.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class RoleDaoImpl implements RoleDao {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("IMDB");

	@Override
	public void save(Role role) {
		 EntityManager em = emf.createEntityManager();
	        em.getTransaction().begin();
	        try {
	            em.persist(role);
	            em.getTransaction().commit();
	        } catch (Exception e) {
	            if (em.getTransaction().isActive()) {
	                em.getTransaction().rollback();
	            }
	            throw new RuntimeException("Erreur lors de la sauvegarde du rôle", e);
	        } finally {
	            em.close();
	        }
	    }

	@Override
	public Role findById(int id) {
		   EntityManager em = emf.createEntityManager();
	        Role role = em.find(Role.class, id);
	        em.close();
	        return role;
	    }

	@Override
	public List<Role> findAll() {
		 EntityManager em = emf.createEntityManager();
	        TypedQuery<Role> query = em.createQuery("SELECT r FROM Role r", Role.class);
	        List<Role> roles = query.getResultList();
	        em.close();
	        return roles;
	    }

	@Override
	public void update(Role role) {
		 EntityManager em = emf.createEntityManager();
	        em.getTransaction().begin();
	        try {
	            em.merge(role);
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
		 EntityManager em = emf.createEntityManager();
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