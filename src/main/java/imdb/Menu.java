package imdb;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.opencsv.exceptions.CsvValidationException;

import imdb.Dao.FilmDAOImpl;
import imdb.Dao.FilmDao;
import imdb.Utils.Reader;
import imdb.entities.Film;
import imdb.exception.ExceptionTech;

public class Menu {
    private static final Scanner scanner = new Scanner(System.in);

    private FilmDao filmDAO;
    private Reader reader;

    public Menu() {
        filmDAO = new FilmDAOImpl();
        reader = new Reader();
    }

    public static void main(String[] args) throws IOException, CsvValidationException {
    	
        Menu menu = new Menu();
        menu.run();
    }

    public void run() throws IOException, CsvValidationException {
    	
        List<Film> films = new ArrayList<>();
		try {
			films = Reader.getFilms("E:\\CDA Curs\\java\\Projet-JPA\\src\\main\\resources\\Csv\\films.csv");
		} catch (ExceptionTech e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        filmDAO.saveAll(films);
       
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
                    System.exit(0);
                default:
                    System.out.println("Choix non valide.");
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
        System.out.println("Entrez l'année de début :");
        int anneeDebut = Integer.parseInt(scanner.nextLine());
        System.out.println("Entrez l'année de fin :");
        int anneeFin = Integer.parseInt(scanner.nextLine());
        List<Film> films = filmDAO.findAnneeSortieBeetween(anneeDebut, anneeFin);
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
        System.out.println("Entrez l'année de début :");
        int anneeDebut = Integer.parseInt(scanner.nextLine());
        System.out.println("Entrez l'année de fin :");
        int anneeFin = Integer.parseInt(scanner.nextLine());
        System.out.println("Entrez le nom de l'acteur :");
        String nomActeur = scanner.nextLine();
        List<Film> films = filmDAO.findFilmsEntreAnneesAvecActeur(anneeDebut, anneeFin, nomActeur);
        films.forEach(film -> System.out.println(film.getTitre()));
    }
}
