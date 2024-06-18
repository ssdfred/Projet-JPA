package imdb;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import com.opencsv.exceptions.CsvValidationException;
import com.opencsv.exceptions.CsvException;

import imdb.Dao.FilmDAOImpl;
import imdb.Dao.FilmDao;
import imdb.Utils.Reader;
import imdb.entities.Acteur;
import imdb.entities.Film;
import imdb.entities.Realisateur;
import imdb.exception.ExceptionTech;

public class Menu {
	private static final Logger LOGGER = Logger.getLogger(Menu.class.getName());
	private static final Scanner scanner = new Scanner(System.in);

	private FilmDao filmDAO;
	private Reader reader;

	public Menu() {
		filmDAO = new FilmDAOImpl();
		reader = new Reader();
	}

	public static void main(String[] args) {
        Menu menu = new Menu();
        try {
            menu.run();
        } catch (IOException | CsvValidationException e) {
            System.err.println("Erreur lors de l'exécution du menu : " + e.getMessage());
            e.printStackTrace();
            if (e.getCause() != null) {
                System.err.println("Cause : " + e.getCause().getMessage());
                e.getCause().printStackTrace();
            }
        }
    }
	public void run() throws IOException, CsvValidationException {

		// Charger les films depuis le fichier CSV en utilisant Reader
        try {
            List<Film> films = reader.getFilms("src/main/resources/Csv/films.csv");
         //   List<Acteur> acteurs = reader.getActeurs("src/main/resources/Csv/acteurs.csv");
           // List<Realisateur> realisateurs = reader.getRealisateurs("src/main/resources/Csv/realisateurs.csv");

    /*        for (Film film : films) {
                for (Acteur acteur : acteurs) {
                    if (!film.getActeurs().contains(acteur)) {
                        film.getActeurs().add(acteur);
                    }
                }
                for (Realisateur realisateur : realisateurs) {
                    if (film.getRealisateurs().contains(realisateur)) {
                        film.getRealisateurs().add(realisateur);
                    }
                }
            }
*/
            // Sauvegarder les entités en utilisant DAO de films
            filmDAO.save(films);
        } catch (ExceptionTech e) {
            LOGGER.severe("Erreur technique : " + e.getMessage());
            throw e;
        }
	        while (true) {
	            afficherMenu();
	            int choix = Integer.parseInt(scanner.nextLine());

	            switch (choix) {
	                case 1:
	                    afficherFilmographieActeur();
	                    break;
	                case 2:
	                    afficherCastingFilm();
	                    break;
	                case 3:
	                    afficherFilmsEntreAnnees();
	                    break;
	                case 4:
	                    afficherFilmsCommunsActeurs();
	                    break;
	                case 5:
	                    afficherActeursCommunsFilms();
	                    break;
	                case 6:
	                    afficherFilmsEntreAnneesAvecActeur();
	                    break;
	                case 7:
	                    System.out.println("Fin de l'application.");
	                    filmDAO.closeEntityManager();
	                    return;
	                default:
	                    System.out.println("Choix invalide. Veuillez réessayer.");
	            }
	        }
	    }

	    private void afficherMenu() {
	        System.out.println("1. Affichage de la filmographie d’un acteur donné");
	        System.out.println("2. Affichage du casting d’un film donné");
	        System.out.println("3. Affichage des films sortis entre 2 années données");
	        System.out.println("4. Affichage des films communs à 2 acteurs/actrices donnés");
	        System.out.println("5. Affichage des acteurs communs à 2 films donnés");
	        System.out.println("6. Affichage des films sortis entre 2 années données et qui ont un acteur/actrice donné au casting");
	        System.out.println("7. Fin de l’application");
	    }

	    private void afficherFilmographieActeur() {
	        System.out.println("Entrez le nom de l'acteur :");
	        String nomActeur = scanner.nextLine();
	        List<Film> films = filmDAO.findByActeur(nomActeur);
	        films.forEach(film -> System.out.println(film.getTitre()));
	    }

	    private void afficherCastingFilm() {
	        System.out.println("Entrez le titre du film :");
	        String titreFilm = scanner.nextLine();
	        List<String> acteurs = filmDAO.findActeursByFilm(titreFilm);
	        acteurs.forEach(acteur -> System.out.println(acteur));
	    }

	    private void afficherFilmsEntreAnnees() {
	        System.out.print("Entrez l'année de début : ");
	        int anneeDebut = Integer.parseInt(scanner.nextLine());
	        System.out.print("Entrez l'année de fin : ");
	        int anneeFin = Integer.parseInt(scanner.nextLine());
	        List<Film> films = filmDAO.findFilmsEntreAnnees(anneeDebut, anneeFin);
	        films.forEach(film -> System.out.println(film.getTitre()));
	    }

	    private void afficherFilmsCommunsActeurs() {
	        System.out.println("Entrez le nom du premier acteur :");
	        String acteur1 = scanner.nextLine();
	        System.out.println("Entrez le nom du second acteur :");
	        String acteur2 = scanner.nextLine();
	        List<Film> films = filmDAO.findFilmsCommunsActeurs(acteur1, acteur2);
	        films.forEach(film -> System.out.println(film.getTitre()));
	    }

	    private void afficherActeursCommunsFilms() {
	        System.out.println("Entrez le titre du premier film :");
	        String film1 = scanner.nextLine();
	        System.out.println("Entrez le titre du second film :");
	        String film2 = scanner.nextLine();
	        List<String> acteurs = filmDAO.findActeursCommunsFilms(film1, film2);
	        acteurs.forEach(acteur -> System.out.println(acteur));
	    }

	    private void afficherFilmsEntreAnneesAvecActeur() {
	        System.out.print("Entrez l'année de début : ");
	        int anneeDebut = Integer.parseInt(scanner.nextLine());
	        System.out.print("Entrez l'année de fin : ");
	        int anneeFin = Integer.parseInt(scanner.nextLine());
	        System.out.print("Entrez le nom de l'acteur/actrice : ");
	        String nomActeur = scanner.nextLine();
	        List<Film> films = filmDAO.findFilmsEntreAnneesAvecActeur(anneeDebut, anneeFin, nomActeur);
	        films.forEach(film -> System.out.println(film.getTitre()));
	    }
}
