package imdb.Dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import imdb.entities.Acteur;
import imdb.entities.Film;

public class ActeurDAOImpl implements ActeurDao {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("IMDB");
	private EntityManager em;
	private static final Logger logger = LoggerFactory.getLogger(ActeurDAOImpl.class);
 


    @Override
    public Acteur findById(int id) {
        em = emf.createEntityManager();
        Acteur acteur = em.find(Acteur.class, id);
        em.close();
        return acteur;
    }

    @Override
    public List<Acteur> findAll() {
        em = emf.createEntityManager();
        List<Acteur> acteurs = em.createQuery("SELECT a FROM Acteur a", Acteur.class).getResultList();
        em.close();
        return acteurs;
    }



    @Override
    public List<Acteur> findByNom(String nom) {
        em = emf.createEntityManager();
        List<Acteur> acteurs = em.createQuery("SELECT a FROM Acteur a WHERE a.nom = :nom", Acteur.class)
                .setParameter("nom", nom)
                .getResultList();
        em.close();
        return acteurs;
    }

    @Override
    public void update(Acteur acteur) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(acteur);
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void closeEntityManager() {
        emf.close();
    }

    @Override
    public void delete(Acteur acteur) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.contains(acteur) ? acteur : em.merge(acteur));
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void save(List<Acteur> acteurs) {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            for (Acteur acteur : acteurs) {
                em.persist(acteur);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.error("Erreur lors de la sauvegarde des acteurs", e);
            throw new RuntimeException("Erreur lors de la sauvegarde des acteurs", e);
        } finally {
            em.close();
        }
    }


	@Override
	public Acteur findOrCreateActeur(String nomComplet) {
	    em = emf.createEntityManager();
	    Acteur acteur = null;
	    try {
	        em.getTransaction().begin();
	        
	        // Extraction du nom et du prénom à partir du nom complet
	        String[] nomEtPrenom = nomComplet.split(" ");
	        String nom = nomEtPrenom[0];
	        String prenom = nomEtPrenom.length > 1 ? nomEtPrenom[1] : "";
	        
	        try {
	            acteur = em.createQuery("FROM Acteur WHERE nom = :nom AND prenom = :prenom", Acteur.class)
	                    .setParameter("nom", nom)
	                    .setParameter("prenom", prenom)
	                    .getSingleResult();
	        } catch (NoResultException e) {
	            // Si aucun acteur trouvé, créer un nouvel acteur
	            acteur = new Acteur();
	            acteur.setNom(nom);
	            acteur.setPrenom(prenom);
	            em.persist(acteur);
	        }
	        
	        em.getTransaction().commit();
	    } catch (Exception e) {
	        if (em.getTransaction().isActive()) {
	            em.getTransaction().rollback();
	        }
	        throw new RuntimeException("Erreur lors de la recherche ou de la création de l'acteur", e);
	    } finally {
	        em.close();
	    }
	    return acteur;
	}

	public void close() {
        emf.close();
    }

    public Acteur find(String idImdb) {
    	 TypedQuery<Acteur> query = em.createQuery("SELECT a FROM Acteur a WHERE a.idImdb = :idImdb", Acteur.class);
         query.setParameter("idImdb", idImdb);
         List<Acteur> result = query.getResultList();
         return result.isEmpty() ? null : result.get(0);
     }
    public Acteur findByNomAndPrenom(String nom, String prenom) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Acteur> query = em.createQuery("SELECT a FROM Acteur a WHERE a.nom = :nom AND a.prenom = :prenom", Acteur.class);
            query.setParameter("nom", nom);
            query.setParameter("prenom", prenom);

            List<Acteur> resultList = query.getResultList();
            if (resultList.isEmpty()) {
                return null;
            } else {
                // Retourner le premier acteur trouvé
                return resultList.get(0);
            }
        } finally {
            em.close();
        }
    }
		
	}



