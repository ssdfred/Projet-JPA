package imdb.Dao;

import java.util.List;

import imdb.entities.Lieu;
import imdb.entities.Realisateur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class RealisateurDaoImpl implements RealisateurDao {
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("IMDB");
    private EntityManager em;

    @Override
    public void save(List<Realisateur> realisateurs) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            for (Realisateur realisateur : realisateurs) {
                em.persist(realisateur);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public Realisateur findById(int id) {
        EntityManager em = emf.createEntityManager();
        Realisateur realisateur = em.find(Realisateur.class, id);
        em.close();
        return realisateur;
    }

    @Override
    public List<Realisateur> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Realisateur> realisateurs = em.createQuery("SELECT r FROM Realisateur r", Realisateur.class).getResultList();
        em.close();
        return realisateurs;
    }

    @Override
    public void update(Realisateur realisateur) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(realisateur);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Realisateur realisateur) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            realisateur = em.merge(realisateur);
            em.remove(realisateur);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void closeEntityManager() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}