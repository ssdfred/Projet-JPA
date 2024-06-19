package imdb.Dao;

import java.util.List;

import imdb.entities.Genre;


public interface GenreDao {

    void save(Genre genre);
    void save(List<Genre> genres);
    Genre findById(int id);
    List<Genre> findAll();
    void update(Genre genre);
    void delete(Genre genre);
}
