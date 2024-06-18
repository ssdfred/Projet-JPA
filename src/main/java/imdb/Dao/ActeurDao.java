package imdb.Dao;

import imdb.entities.Acteur;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public interface ActeurDao {



	void save(List<Acteur> acteurs);
	public Acteur findOrCreateActeur(String nomComplet);
    public Acteur findById(int id);

    public List<Acteur> findByNom(String nom);

    public List<Acteur> findAll();

    public void update(Acteur acteur) ;

    public void delete(Acteur acteur);

  

    public void closeEntityManager();
	Acteur findOrCreateActeur(String nom, String prenom);
}
