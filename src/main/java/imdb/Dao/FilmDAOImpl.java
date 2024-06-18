package imdb.Dao;

import imdb.entities.Acteur;
import imdb.entities.Film;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class FilmDAOImpl implements FilmDao {

    private EntityManagerFactory emf;
    private EntityManager em;

    public FilmDAOImpl() {
        emf = Persistence.createEntityManagerFactory("IMDB");
        em = emf.createEntityManager();
    }

    @Override
    public void save(List<Film> films) {
        em.getTransaction().begin();
        for (Film film : films) {
            em.persist(film);
        }
        em.getTransaction().commit();
    }
    @Override
    public Film findById(int id) {
        EntityManager em = emf.createEntityManager();
        Film film = em.find(Film.class, id);
        em.close();
        return film;
    }
    @Override
    public List<Film> findByActeur(String nom) {
        return em.createQuery("SELECT f FROM Film f JOIN f.acteurs a WHERE a.nom = :nom", Film.class)
                .setParameter("nom", nom)
                .getResultList();
    }

    @Override
    public List<String> findActeursByFilm(String titre) {
        return em.createQuery("SELECT a.nom FROM Film f JOIN f.acteurs a WHERE f.titre = :titre", String.class)
                .setParameter("titre", titre)
                .getResultList();
    }

    @Override
    public List<Film> findFilmsEntreAnnees(int anneeDebut, int anneeFin) {
        return em.createQuery("SELECT f FROM Film f WHERE f.anneeSortie BETWEEN :anneeDebut AND :anneeFin", Film.class)
                .setParameter("anneeDebut", anneeDebut)
                .setParameter("anneeFin", anneeFin)
                .getResultList();
    }

    @Override
    public List<Film> findFilmsCommunsActeurs(String acteur1, String acteur2) {
        return em.createQuery("SELECT DISTINCT f FROM Film f JOIN f.acteurs a1 JOIN f.acteurs a2 WHERE a1.nom = :acteur1 AND a2.nom = :acteur2", Film.class)
                .setParameter("acteur1", acteur1)
                .setParameter("acteur2", acteur2)
                .getResultList();
    }

    @Override
    public List<String> findActeursCommunsFilms(String film1, String film2) {
        return em.createQuery("SELECT DISTINCT a.nom FROM Film f1 JOIN f1.acteurs a JOIN Film f2 JOIN f2.acteurs a WHERE f1.titre = :film1 AND f2.titre = :film2", String.class)
                .setParameter("film1", film1)
                .setParameter("film2", film2)
                .getResultList();
    }

    @Override
    public List<Film> findFilmsEntreAnneesAvecActeur(int anneeDebut, int anneeFin, String nomActeur) {
        return em.createQuery("SELECT DISTINCT f FROM Film f JOIN f.acteurs a WHERE f.anneeSortie BETWEEN :anneeDebut AND :anneeFin AND a.nom = :nomActeur", Film.class)
                .setParameter("anneeDebut", anneeDebut)
                .setParameter("anneeFin", anneeFin)
                .setParameter("nomActeur", nomActeur)
                .getResultList();
    }
    @Override
    public void update(Film film) {
        em.getTransaction().begin();
        em.merge(film);
        em.getTransaction().commit();
    }

    @Override
    public void delete(Film film) {
        em.getTransaction().begin();
        em.remove(film);
        em.getTransaction().commit();
    }

    @Override
    public void closeEntityManager() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
