package imdb.Dao;

import imdb.entities.Film;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class FilmDAOImpl implements FilmDao {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("IMDB");
	private EntityManager em;

    @Override
    public void save(List<Film> films) {
    	em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            for (Film film : films) {
                em.persist(film);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public Film findById(int id) {
    	em = emf.createEntityManager();
        try {
            return em.find(Film.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Film> findByActeur(String nom) {
    	em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT f FROM Film f JOIN f.roles r JOIN r.acteur a WHERE a.nom = :nom", Film.class)
                    .setParameter("nom", nom).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<String> findActeursByFilm(String titre) {
    	em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT a.nom FROM Film f JOIN f.roles r JOIN r.acteur a WHERE f.titre = :titre", String.class)
                    .setParameter("titre", titre).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Film> findFilmsEntreAnnees(int anneeDebut, int anneeFin) {
    	em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT f FROM Film f WHERE f.anneeSortie BETWEEN :anneeDebut AND :anneeFin", Film.class)
                    .setParameter("anneeDebut", anneeDebut).setParameter("anneeFin", anneeFin).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Film> findFilmsCommunsActeurs(String acteur1, String acteur2) {
    	em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT DISTINCT f FROM Film f JOIN f.roles r1 JOIN r1.acteur a1 JOIN f.roles r2 JOIN r2.acteur a2 WHERE a1.nom = :acteur1 AND a2.nom = :acteur2",
                    Film.class).setParameter("acteur1", acteur1).setParameter("acteur2", acteur2).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<String> findActeursCommunsFilms(String film1, String film2) {
    	em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT DISTINCT a.nom FROM Film f1 JOIN f1.roles r1 JOIN r1.acteur a JOIN Film f2 JOIN f2.roles r2 JOIN r2.acteur a WHERE f1.titre = :film1 AND f2.titre = :film2",
                    String.class).setParameter("film1", film1).setParameter("film2", film2).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Film> findFilmsEntreAnneesAvecActeur(int anneeDebut, int anneeFin, String nomActeur) {
    	em = emf.createEntityManager();
        try {
            return em.createQuery(
                    "SELECT DISTINCT f FROM Film f JOIN f.roles r JOIN r.acteur a WHERE f.anneeSortie BETWEEN :anneeDebut AND :anneeFin AND a.nom = :nomActeur",
                    Film.class).setParameter("anneeDebut", anneeDebut).setParameter("anneeFin", anneeFin)
                    .setParameter("nomActeur", nomActeur).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Film film) {
    	em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(film);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Film film) {
    	em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.contains(film) ? film : em.merge(film));
            em.getTransaction().commit();
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
