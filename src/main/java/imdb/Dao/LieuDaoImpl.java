package imdb.Dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import imdb.entities.Acteur;
import imdb.entities.Lieu;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class LieuDaoImpl implements LieuDao {
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("IMDB");
	private EntityManager em;

	private static final Logger logger = LoggerFactory.getLogger(ActeurDAOImpl.class);
	
    @Override
    public void save(Lieu lieu) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(lieu);
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
    public void save(List<Lieu> lieus) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            for (Lieu lieu : lieus) {
                if (lieu != null && lieu.getId() == null) {
                    em.persist(lieu);
                }
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
    public Lieu findById(int id) {
        return em.find(Lieu.class, id);
    }

    @Override
    public List<Lieu> findAll() {
        return em.createQuery("SELECT l FROM Lieu l", Lieu.class).getResultList();
    }

    @Override
    public void update(Lieu lieu) {
        em.getTransaction().begin();
        em.merge(lieu);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Lieu lieu) {
        em.getTransaction().begin();
        em.remove(lieu);
        em.getTransaction().commit();
    }
}
