package imdb.Utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import imdb.entities.Acteur;
import imdb.entities.Film;
import imdb.entities.Realisateur;
import imdb.exception.ExceptionTech;

public class Reader {


	    public static  List<Film> getFilms(String csvFile) {
	        List<String> lignes = null;
	        try {
	            lignes = FileUtils.readLines(new File(csvFile), "UTF-8");
	        } catch (IOException e) {
	            throw new ExceptionTech("Fichier " + csvFile + " introuvable.", e);
	        }

	        // Remove header
	        lignes.remove(0);

	        // Limiter le nombre de lignes traitées à 10
	        List<String> limitedLines = lignes.subList(0, Math.min(10, lignes.size()));

	        ArrayList<Film> films = new ArrayList<>();
	        limitedLines.forEach(ligne -> {
	            films.add(transformeLigneEnFilm(ligne));
	        });

	        return films;
	    }

	    public static Film transformeLigneEnFilm(String ligneCsv) {
	        String[] morceaux = ligneCsv.split(";", -1);

	        // Initialisation des variables
	        String titre = null;
	        String anneeSortieStr = null;
	        String langue = null;
	        String resume = null;

	        // Attribution des valeurs aux variables en fonction de l'index des colonnes
	        if (morceaux.length > 2) {
	            anneeSortieStr = morceaux[2];  // L'année de sortie est dans la troisième colonne
	            System.out.println(anneeSortieStr);
	        }
	        if (morceaux.length > 7) {
	            langue = morceaux[7];  // La langue est dans la huitième colonne
	        }
	        if (morceaux.length > 8) {
	            resume = morceaux[8];
	            System.out.println("RESUME : " + resume);
	        }
	        if (morceaux.length > 1) {
	            titre = morceaux[1];  // Le titre est dans la deuxième colonne
	        }

	        Film film = new Film();
	        film.setAnneeSortie(Year.of(Integer.parseInt(anneeSortieStr)));
	        film.setTitre(titre);
	        film.setLangue(langue);
	        film.setResume(resume);

	        return film;
	    }
	

/*	public List<Acteur> getActeurs(String filePath) throws IOException, CsvValidationException {
        List<Acteur> acteurs = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                Acteur acteur = new Acteur();
                acteur.setNom(values[0]);
                acteur.setPrenom(values[1]);
                acteur.setDateNaissance(LocalDate.parse(values[2]));
                // Ajouter l'acteur à la liste
                acteurs.add(acteur);
            }
        } catch (IOException | CsvValidationException e) {
            throw new ExceptionTech("Erreur lors de la lecture du fichier CSV des acteurs", e);
        }

        return acteurs;
    }
    

   
    
    public List<Realisateur> getRealisateurs(String filePath) throws IOException, CsvValidationException {
        List<Realisateur> realisateurs = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                Realisateur realisateur = new Realisateur();
                realisateur.setNom(values[0]);
                realisateur.setPrenom(values[1]);
                realisateur.setDateNaissance(LocalDate.parse(values[2]));
                // Ajouter le réalisateur à la liste
                realisateurs.add(realisateur);
            }
        } catch (IOException | CsvValidationException e) {
            throw new ExceptionTech("Erreur lors de la lecture du fichier CSV des réalisateurs", e);
        }

        return realisateurs;
    }*/
}
