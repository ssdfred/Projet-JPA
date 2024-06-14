package imdb.Dao;

import java.util.List;

import imdb.entities.Film;



public interface FilmDao {
	
    void save(Film film);
    Film findById(int id);
    List<Film> findAll();
    List<Film> findByActeur(String nomActeur);
    List<Film> findAnneeSortieBeetween(int anneeDebut, int anneeFin);
    void deleteById(int id);
}
