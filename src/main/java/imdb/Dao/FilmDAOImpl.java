package imdb.Dao;

import imdb.entities.Film;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class FilmDAOImpl implements FilmDao {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("IMDB");
    private EntityManager em = emf.createEntityManager();

    @Override
    public void saveAll(List<Film> films) {
        em.getTransaction().begin();
        for (Film film : films) {
            em.persist(film);
        }
        em.getTransaction().commit();
    }

    @Override
    public List<Film> findByActeur(String nomActeur) {
        String jpql = "SELECT f FROM Film f JOIN f.acteurs a WHERE a.nom = :nomActeur";
        TypedQuery<Film> query = em.createQuery(jpql, Film.class);
        query.setParameter("nomActeur", nomActeur);
        return query.getResultList();
    }

    @Override
    public List<String> findActeursByFilm(String titreFilm) {
        String jpql = "SELECT a.nom FROM Acteur a JOIN a.films f WHERE f.titre = :titreFilm";
        TypedQuery<String> query = em.createQuery(jpql, String.class);
        query.setParameter("titreFilm", titreFilm);
        return query.getResultList();
    }

    @Override
    public List<Film> findAnneeSortieBeetween(int anneeDebut, int anneeFin) {
        String jpql = "SELECT f FROM Film f WHERE YEAR(f.anneeSortie) BETWEEN :anneeDebut AND :anneeFin";
        TypedQuery<Film> query = em.createQuery(jpql, Film.class);
        query.setParameter("anneeDebut", anneeDebut);
        query.setParameter("anneeFin", anneeFin);
        return query.getResultList();
    }

    @Override
    public List<Film> findFilmsCommunsActeurs(String acteur1, String acteur2) {
        String jpql = "SELECT DISTINCT f FROM Film f JOIN f.acteurs a1 JOIN f.acteurs a2 WHERE a1.nom = :acteur1 AND a2.nom = :acteur2";
        TypedQuery<Film> query = em.createQuery(jpql, Film.class);
        query.setParameter("acteur1", acteur1);
        query.setParameter("acteur2", acteur2);
        return query.getResultList();
    }

    @Override
    public List<String> findActeursCommunsFilms(String film1, String film2) {
        String jpql = "SELECT DISTINCT a.nom FROM Acteur a JOIN a.films f1 JOIN a.films f2 WHERE f1.titre = :film1 AND f2.titre = :film2";
        TypedQuery<String> query = em.createQuery(jpql, String.class);
        query.setParameter("film1", film1);
        query.setParameter("film2", film2);
        return query.getResultList();
    }

    @Override
    public List<Film> findFilmsEntreAnneesAvecActeur(int anneeDebut, int anneeFin, String nomActeur) {
        String jpql = "SELECT DISTINCT f FROM Film f JOIN f.acteurs a WHERE YEAR(f.anneeSortie) BETWEEN :anneeDebut AND :anneeFin AND a.nom = :nomActeur";
        TypedQuery<Film> query = em.createQuery(jpql, Film.class);
        query.setParameter("anneeDebut", anneeDebut);
        query.setParameter("anneeFin", anneeFin);
        query.setParameter("nomActeur", nomActeur);
        return query.getResultList();
    }
}
