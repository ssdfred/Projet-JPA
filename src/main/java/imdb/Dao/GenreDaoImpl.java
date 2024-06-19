package imdb.Dao;

import java.util.List;

import imdb.entities.Genre;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class GenreDaoImpl implements GenreDao {
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("IMDB");
	private EntityManager em;
	@Override
	public void save(Genre genre) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(genre);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

	@Override
	public void save(List<Genre> genres) {
		em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            for (Genre genre : genres) {
                
                    em.persist(genre);
               
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

	@Override
	public Genre findById(int id) {
		return em.find(Genre.class, id);
	}

	@Override
	public List<Genre> findAll() {
		return em.createQuery("SELECT l FROM Lieu l", Genre.class).getResultList();
	}

	@Override
	public void update(Genre genre) {
		em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(genre);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

	@Override
	public void delete(Genre genre) {
	       em.getTransaction().begin();
	        em.remove(genre);
	        em.getTransaction().commit();
	    }

}
