package imdb.Utils;

import imdb.config.Configuration;
import imdb.entities.Film;
import imdb.exception.ExceptionTech;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Reader {

    private Configuration config;

    public Reader() {
        this.config = new Configuration();
    }

    public static List<Film> getFilms(String csvFile) {
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
        String[] morceaux = ligneCsv.split(";");

        // Initialisation des variables
        String titre = null;
        String anneeSortieStr = null;
        String langue = null;
        String resume = null;

        // Attribution des valeurs aux variables en fonction de l'index des colonnes
        if (morceaux.length > 2) {
            anneeSortieStr = morceaux[2];  // L'année de sortie est dans la troisième colonne
        }
        if (morceaux.length > 7) {
            langue = morceaux[7];  // La langue est dans la huitième colonne
        }
        if (morceaux.length > 8) {
            String fullResume = morceaux[8]; // Index 8 pour le résumé
            resume = fullResume.length() > 1000? fullResume.substring(0, 999) : fullResume;
        }
        if (morceaux.length > 1) {
            titre = morceaux[1];  // Le titre est dans la deuxième colonne
        }

        Film film = new Film();
        film.setTitre(titre);
        film.setLangue(langue);
        film.setResume(resume);

        return film;
    }
}
