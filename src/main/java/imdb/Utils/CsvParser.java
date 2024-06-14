package imdb.Utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import imdb.Dao.ActeurDao;
import imdb.Dao.FilmDao;
import imdb.entities.Acteur;
import imdb.entities.Film;

import java.io.FileReader;
import java.io.IOException;

public class CsvParser {

    private final String CSV_FILE_PATH = "chemin_vers_votre_fichier.csv"; // Remplacez par le chemin de votre fichier CSV

    //private final FilmDao filmDAO;
    private final ActeurDao acteurDAO;

    public CsvParser() {
        //this.filmDAO = new FilmDao(); // Remplacez par votre implémentation FilmDao
        this.acteurDAO = new ActeurDao(); // Remplacez par votre implémentation ActeurDao
    }

    public void parseAndInsert() throws IOException, CsvValidationException {
        try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH))) {
            String[] line;
            // Skip header line
            reader.readNext();

            while ((line = reader.readNext()) != null) {
                if (line.length < 6) {
                    System.out.println("Ligne invalide : " + String.join(",", line));
                    continue;
                }

                String idImdb = line[0];
                String identite = line[1];
                String dateNaissance = line[2];
                String lieuNaissance = line[3];
                String url = line[5];

                // Enregistrement de l'acteur en base de données (exemple)
                Acteur acteur = new Acteur();

                acteur.setNom(identite);
                acteur.setUrl(url);
                acteurDAO.save(acteur); // Assurez-vous que votre ActeurDao a une méthode save

                // Vous pouvez également insérer les films associés ici
                // Exemple: création d'un film et insertion avec le FilmDao
                // Film film = new Film();
                // film.setTitre("Titre du film");
                // film.setAnneeSortie(année_de_sortie);
                // filmDAO.save(film); // Assurez-vous que votre FilmDao a une méthode save
            }
        }
    }
}
