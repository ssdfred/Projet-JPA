package imdb.Dao;

import imdb.entities.Acteur;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class ActeurDao {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("IMDB");
    private final EntityManager em = emf.createEntityManager();

    public void save(Acteur acteur) {
        em.getTransaction().begin();
        em.persist(acteur);
        em.getTransaction().commit();
    }

    public Acteur findById(int id) {
        return em.find(Acteur.class, id);
    }

    public Acteur findByIdImdb(String idImdb) {
        return em.createQuery("SELECT a FROM Acteur a WHERE a.idImdb = :idImdb", Acteur.class)
                .setParameter("idImdb", idImdb)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public List<Acteur> findByNom(String nom) {
        return em.createQuery("SELECT a FROM Acteur a WHERE a.nom = :nom", Acteur.class)
                .setParameter("nom", nom)
                .getResultList();
    }

    public List<Acteur> findAll() {
        return em.createQuery("SELECT a FROM Acteur a", Acteur.class)
                .getResultList();
    }

    public void update(Acteur acteur) {
        em.getTransaction().begin();
        em.merge(acteur);
        em.getTransaction().commit();
    }

    public void delete(Acteur acteur) {
        em.getTransaction().begin();
        em.remove(acteur);
        em.getTransaction().commit();
    }

    // Ajoutez d'autres méthodes selon vos besoins (findByDateNaissance, findByLieuNaissance, etc.)

    public void closeEntityManager() {
        em.close();
        emf.close();
    }
}
