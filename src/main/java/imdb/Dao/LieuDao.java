package imdb.Dao;

import java.util.List;

import imdb.entities.Lieu;

public interface LieuDao {
    void save(Lieu lieu);
    void save(List<Lieu> lieus);
    Lieu findById(int id);
    List<Lieu> findAll();
    void update(Lieu lieu);
    void delete(Lieu lieu);
}
