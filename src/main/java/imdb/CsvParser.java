package imdb;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import imdb.entities.Acteur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.function.Consumer;

public class CsvParser {

    private EntityManagerFactory emf;

    public CsvParser() {
        emf = Persistence.createEntityManagerFactory("IMDB");
    }

    private void executeInTransaction(Consumer<EntityManager> action) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            action.accept(em); // Exécute l'action avec l'EntityManager
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // Rejette l'exception après le rollback
        } finally {
            em.close(); // Ferme l'EntityManager dans tous les cas
        }
    }

    public void parseActeurs(String filePath, int limit) throws IOException, CsvException {
        executeInTransaction(em -> {
            try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
                String[] line;
                boolean headerSkipped = false; // Pour sauter l'en-tête
                int count = 0;

                // Sauter la première ligne (en-tête)
                reader.readNext();

                while ((line = reader.readNext()) != null && count < limit) {
                    // Vérifie si c'est une ligne valide d'acteur
                    if (line.length < 5 || !line[0].startsWith("nm")) {
                        // Ignorer les lignes sans identité ou dont l'identité n'est pas conforme
                        System.err.println("Identité manquante ou invalide pour la ligne : " + Arrays.toString(line));
                        continue;
                    }

                    Acteur acteur = new Acteur();

                    // Extraction des informations d'acteur
                    acteur.setIdImdb(line[0].trim()); // ID IMDB
                    acteur.setIdentite(line[1].trim()); // IDENTITE

                    // Parsing de la date de naissance
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd yyyy");
                    try {
                        acteur.setDateNaissance(dateFormat.parse(line[2].trim()));
                    } catch (ParseException e) {
                        System.err.println("Erreur de parsing de la date de naissance : " + line[2].trim());
                        e.printStackTrace();
                        continue; // Sauter cette itération si la date de naissance est invalide
                    }

                    // Lieu de naissance
                    acteur.setLieuNaissance(line[3].trim());

                    // URL
                    acteur.setUrl(line[5].trim()); // Assurez-vous que le tableau line a suffisamment d'éléments

                    em.persist(acteur); // Persiste l'acteur
                    count++;
                }
            } catch (IOException | CsvException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        CsvParser parser = new CsvParser();
        try {
            parser.parseActeurs("src/main/resources/Csv/acteurs.csv", 10); // Limite à 10 lignes
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }
}
