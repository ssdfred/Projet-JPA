package imdb.Utils;


import java.util.List;

import imdb.entities.Film;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class FilmDatabaseInserter {

    public void main(String[] args) {

        EntityManagerFactory emf= Persistence.createEntityManagerFactory("IMDB");
        EntityManager em = emf.createEntityManager();

        //Récupérer les films à partir du fichier CSV
        String csvFile = "E:\\CDA Curs\\java\\Projet-JPA\\src\\main\\resources\\Csv\\films.csv"; 
        List<Film> films = null;
       // try {
       //     films = Reader.getFilms(csvFile);
      //  } catch (Exception e) {
      //      e.printStackTrace();
      //  }

        //Insérer les films dans la base de données
        insertFilmsIntoDatabase(films, em);
    }

    public static void insertFilmsIntoDatabase(List<Film> films, EntityManager em) {
        em.getTransaction().begin();
        for (Film film : films) {
            em.persist(film);
        }
        em.getTransaction().commit();
    }
}

