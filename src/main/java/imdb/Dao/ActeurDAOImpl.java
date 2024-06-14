package imdb.Dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import imdb.entities.Acteur;

public class ActeurDAOImpl implements ActeurDao {

	 private EntityManagerFactory emf = Persistence.createEntityManagerFactory("IMDB");

	    @Override
	    public void save(Acteur acteur) {
	        EntityManager em = emf.createEntityManager();
	        em.getTransaction().begin();
	        em.persist(acteur);
	        em.getTransaction().commit();
	        em.close();
	    }

	    @Override
	    public Acteur findById(int id) {
	        EntityManager em = emf.createEntityManager();
	        Acteur acteur = em.find(Acteur.class, id);
	        em.close();
	        return acteur;
	    }

	    @Override
	    public List<Acteur> findAll() {
	        EntityManager em = emf.createEntityManager();
	        List<Acteur> acteurs = em.createQuery("SELECT a FROM Acteur a", Acteur.class).getResultList();
	        em.close();
	        return acteurs;
	    }

	    @Override
	    public List<Acteur> findByFilmTitre(String titreFilm) {
	        EntityManager em = emf.createEntityManager();
	        List<Acteur> acteurs = em.createQuery("SELECT a FROM Acteur a JOIN a.films f WHERE f.titre = :titreFilm", Acteur.class)
	                                 .setParameter("titreFilm", titreFilm)
	                                 .getResultList();
	        em.close();
	        return acteurs;
	    }

	    @Override
	    public void deleteById(int id) {
	        EntityManager em = emf.createEntityManager();
	        em.getTransaction().begin();
	        Acteur acteur = em.find(Acteur.class, id);
	        if (acteur != null) {
	            em.remove(acteur);
	        }
	        em.getTransaction().commit();
	        em.close();
	    }
}
