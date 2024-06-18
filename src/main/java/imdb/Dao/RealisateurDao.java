package imdb.Dao;

import java.util.List;


import imdb.entities.Realisateur;

public interface RealisateurDao {
	void save(List<Realisateur> realisateurs);
    Realisateur findById(int id);
    List<Realisateur> findAll();
    void update(Realisateur realisateur);
    void delete(Realisateur realisateur);
    void closeEntityManager();
	
}
