package imdb.Dao;

import imdb.entities.Film;

import java.util.List;

public interface FilmDao {
    void saveAll(List<Film> films);
    List<Film> findByActeur(String nomActeur);
    List<String> findActeursByFilm(String titreFilm);
    List<Film> findAnneeSortieBeetween(int anneeDebut, int anneeFin);
    List<Film> findFilmsCommunsActeurs(String acteur1, String acteur2);
    List<String> findActeursCommunsFilms(String film1, String film2);
    List<Film> findFilmsEntreAnneesAvecActeur(int anneeDebut, int anneeFin, String nomActeur);
}
