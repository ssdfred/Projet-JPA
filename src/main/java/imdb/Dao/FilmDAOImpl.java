package imdb.Dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import imdb.entities.Film;

public class FilmDAOImpl implements FilmDao {
	protected EntityManagerFactory emf = Persistence.createEntityManagerFactory("IMDB");

	@Override
    public void save(Film film) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(film);
        em.getTransaction().commit();
        em.close();
    }


	@Override
	public Film findById(int id) {
		EntityManager em = emf.createEntityManager();
		Film film = em.find(Film.class, id);
		em.close();
		return film;
	}

	@Override
	public List<Film> findAll() {
		EntityManager em = emf.createEntityManager();
		List<Film> films = em.createQuery("SELECT f FROM Film f", Film.class).getResultList();
		em.close();
		return films;
	}

	@Override
	public List<Film> findByActeur(String nomActeur) {
		EntityManager em = emf.createEntityManager();
		List<Film> films = em.createQuery("SELECT f FROM Film f JOIN f.acteurs a WHERE a.nom = :nomActeur", Film.class)
				.setParameter("nomActeur", nomActeur).getResultList();
		em.close();
		return films;
	}

	@Override
	public List<Film> findAnneeSortieBeetween(int anneeDebut, int anneeFin) {
		EntityManager em = emf.createEntityManager();
		List<Film> films = em
				.createQuery("SELECT f FROM Film f WHERE YEAR(f.anneeSortie) BETWEEN :anneeDebut AND :anneeFin",
						Film.class)
				.setParameter("anneeDebut", anneeDebut).setParameter("anneeFin", anneeFin).getResultList();
		em.close();
		return films;
	}
	
	@Override
	public void deleteById(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Film film = em.find(Film.class, id);
        if (film != null) {
            em.remove(film);
        }
        em.getTransaction().commit();
        em.close();
    }


}
