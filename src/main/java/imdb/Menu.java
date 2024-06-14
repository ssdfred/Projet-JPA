package imdb;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.opencsv.exceptions.CsvValidationException;

import imdb.Dao.ActeurDAOImpl;
import imdb.Dao.ActeurDao;
import imdb.Dao.FilmDAOImpl;
import imdb.Dao.FilmDao;
import imdb.Utils.CsvParser;
import imdb.entities.Acteur;
import imdb.entities.Film;


public class Menu {
    private static final Scanner scanner = new Scanner(System.in);

    private FilmDao filmDAO;
    private ActeurDao acteurDAO;
    private CsvParser csvParser;

    public Menu() {
        filmDAO = new FilmDAOImpl();
        acteurDAO = new ActeurDao();
        csvParser = new CsvParser();
    }

    public static void main(String[] args) throws IOException, CsvValidationException {

        Menu menu = new Menu();
        menu.run();
    }

    public void run() throws IOException, CsvValidationException {
        csvParser.parseAndInsert();

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
        List<Acteur> acteurs = acteurDAO.findByFilmTitre(titreFilm);
        acteurs.forEach(acteur -> System.out.println(acteur.getNom() + " " + acteur.getPrenom()));
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
        String nomActeur1 = scanner.nextLine();
        System.out.println("Entrez le nom du second acteur :");
        String nomActeur2 = scanner.nextLine();
        List<Film> films1 = filmDAO.findByActeur(nomActeur1);
        List<Film> films2 = filmDAO.findByActeur(nomActeur2);
        films1.retainAll(films2);
        films1.forEach(film -> System.out.println(film.getTitre()));
    }

    private void afficherActeursCommunsFilms() {
        System.out.println("Entrez le titre du premier film :");
        String titreFilm1 = scanner.nextLine();
        System.out.println("Entrez le titre du second film :");
        String titreFilm2 = scanner.nextLine();
        List<Acteur> acteurs1 = acteurDAO.findByFilmTitre(titreFilm1);
        List<Acteur> acteurs2 = acteurDAO.findByFilmTitre(titreFilm2);
        acteurs1.retainAll(acteurs2);
        acteurs1.forEach(acteur -> System.out.println(acteur.getNom() + " " + acteur.getPrenom()));
    }

    private void afficherFilmsEntreAnneesAvecActeur() {
        System.out.println("Entrez l'année de début :");
        int anneeDebut = Integer.parseInt(scanner.nextLine());
        System.out.println("Entrez l'année de fin :");
        int anneeFin = Integer.parseInt(scanner.nextLine());
        System.out.println("Entrez le nom de l'acteur :");
        String nomActeur = scanner.nextLine();
        List<Film> films = filmDAO.findAnneeSortieBeetween(anneeDebut, anneeFin);
        films = films.stream().filter(film -> film.getActeurs().stream().anyMatch(acteur -> acteur.getNom().equals(nomActeur))).collect(Collectors.toList());
        films.forEach(film -> System.out.println(film.getTitre()));
    }
}