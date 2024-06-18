package imdb.Dao;

import imdb.entities.Film;

import java.util.List;

public interface FilmDao {
    void save(List<Film> films);
    public Film findById(int id);
    List<Film> findByActeur(String nom);
    List<String> findActeursByFilm(String titre);
    List<Film> findFilmsEntreAnnees(int anneeDebut, int anneeFin);
    List<Film> findFilmsCommunsActeurs(String acteur1, String acteur2);
    List<String> findActeursCommunsFilms(String film1, String film2);
    List<Film> findFilmsEntreAnneesAvecActeur(int anneeDebut, int anneeFin, String nomActeur);
    void update(Film film);
    void delete(Film film);
    void closeEntityManager();
}
