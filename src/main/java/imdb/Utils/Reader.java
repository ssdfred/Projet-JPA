package imdb.Utils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.hibernate.Hibernate;
import org.hibernate.grammars.hql.HqlParser.CurrentDateFunctionContext;

import imdb.Dao.ActeurDAOImpl;
import imdb.Dao.ActeurDao;
import imdb.Dao.FilmDAOImpl;
import imdb.Dao.FilmDao;
import imdb.Dao.LieuDaoImpl;
import imdb.Dao.RoleDao;
import imdb.Dao.RoleDaoImpl;
import imdb.entities.Acteur;
import imdb.entities.Film;
import imdb.entities.Lieu;
import imdb.entities.Realisateur;
import imdb.entities.Role;
import imdb.exception.ExceptionTech;
import jakarta.persistence.EntityManager;

public class Reader {

	private static ActeurDAOImpl acteurDao = new ActeurDAOImpl();

	public List<Film> getFilms(String csvFile) {
	    List<String> lignes = null;
	    try {
	        lignes = FileUtils.readLines(new File(csvFile), "UTF-8");
	    } catch (IOException e) {
	        throw new ExceptionTech("Fichier " + csvFile + " introuvable.", e);
	    }

	    // Supprimer l'en-tête
	    lignes.remove(0);

	    List<Film> films = new ArrayList<>();
	    lignes.forEach(ligne -> {
	        Film film = transformeLigneEnFilm(ligne);
	        if (film != null) {
	           // List<Acteur> acteurs = getActeursDuFilm(ligne); // Récupérer les acteurs du film

	            // Charger explicitement les films associés à chaque acteur
	         //   acteurs.forEach(acteur -> {
	          //      acteur.getFilms().size(); // Force l'initialisation de la collection
	         //   });

	         //   film.setActeurs(acteurs); // Associer les acteurs au film (inverse)

	            // Sauvegarder les acteurs du film dans la base de données
	          //  acteurDao.save(acteurs);
	            films.add(film);
	        }
	    });
	    return films;
	}


    public List<Role> getRoles(String csvFile) {
        List<String> lignes = null;
        List<Role> roles = new ArrayList<>();

        try {
            lignes = FileUtils.readLines(new File(csvFile), "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException("Fichier " + csvFile + " introuvable.", e);
        }

        // Supprimer l'en-tête
        if (lignes != null && !lignes.isEmpty()) {
            lignes.remove(0);
        }

        FilmDao filmDAO = new FilmDAOImpl();
        ActeurDao acteurDAO = new ActeurDAOImpl();
        RoleDao roleDAO = new RoleDaoImpl();

        for (String ligne : lignes) {
            try {
                Role role = transformRole(ligne, acteurDAO, filmDAO);
                if (role != null) {
                    roles.add(role);
                    roleDAO.save((List<Role>) role);
                }
            } catch (Exception e) {
                System.err.println("Erreur lors du traitement de la ligne : " + ligne);
                e.printStackTrace();
            }
        }

        filmDAO.closeEntityManager();
        acteurDAO.closeEntityManager();
        roleDAO.closeEntityManager();

        return roles;
    }

    public static Role transformRole(String csvData, ActeurDao acteurDAO, FilmDao filmDAO) {
    	 String[] values = csvData.split(";");

	        // Création du rôle
	        Role role = new Role();
    	    try {
    	        if (values.length < 3) {
    	            throw new IllegalArgumentException("La ligne CSV ne contient pas suffisamment de colonnes : " + csvData);
    	        }

    	        String filmId = values[1].trim();
    	        //String acteurIdImdb = values[0].trim(); // Utilisation de idImdb ici
    	        String personnage = values[2].trim();
    	        String[] nomEtPrenom = personnage.split(" ");
    	        String nom = nomEtPrenom[0].trim();
    	        String prenom = nomEtPrenom.length > 1 ? nomEtPrenom[1].trim() : "";
    	        // Recherche de l'acteur par nom et prénom
    	        Acteur acteur = acteurDao.findByNomAndPrenom(nom, prenom);
    	        if (acteur == null) {
    	            throw new RuntimeException("Acteur non trouvé en base de données pour : " + nom + " " + prenom);
    	        }
    	        role.setActeur(acteur);

    	        // Recherche du film par son ID
    	        Film film = filmDAO.find(filmId);

    	        if (film == null) {
    	            throw new RuntimeException("Film non trouvé en base de données pour l'ID : " + filmId);
    	        }


    	        role.setActeur(acteur);
    	        role.setNom(personnage);

    	        return role;

    	    } catch (Exception e) {
    	        throw new RuntimeException("Erreur lors de la transformation de la ligne CSV : " + csvData, e);
    	    }

    }
	public static Film transformeLigneEnFilm(String ligneCsv) {
		String[] morceaux = ligneCsv.split(";", -1);

		// Initialisation des variables
		String idImdb = null;
		String titre = null;
		String anneeSortieStr = null;
		String langue = null;
		String resume = null;

		// Attribution des valeurs aux variables en fonction de l'index des colonnes
		idImdb = morceaux[0];
		if (morceaux.length > 2) {
			anneeSortieStr = morceaux[2]; // L'année de sortie est dans la troisième colonne
			// System.out.println(anneeSortieStr);
		}
		if (morceaux.length > 7) {
			langue = morceaux[7]; // La langue est dans la huitième colonne
		}
		if (morceaux.length > 8) {
			resume = morceaux[8];
			// System.out.println("RESUME : " + resume);
		}
		if (morceaux.length > 1) {
			titre = morceaux[1]; // Le titre est dans la deuxième colonne
		}

		Film film = new Film();
		if (anneeSortieStr.contains("-") || anneeSortieStr.contains("–")) {
			anneeSortieStr = anneeSortieStr.replace("–", "-");
			String[] annees = anneeSortieStr.split("-");
			int anneeMoyenne = (Integer.parseInt(annees[0]) + Integer.parseInt(annees[1])) / 2;
			film.setAnneeSortie(Year.of(anneeMoyenne));
		} else {
			film.setAnneeSortie(Year.of(Integer.parseInt(anneeSortieStr)));
		}
		film.setTitre(titre);
		film.setLangue(langue);
		film.setResume(resume);
		film.setIdImdb(idImdb);

		return film;
	}

	public List<Acteur> getActeursDuFilm(String csvFile) {
	    List<Acteur> acteurs = new ArrayList<>();
	    
	    try {
	        List<String> lignes = FileUtils.readLines(new File(csvFile), "UTF-8");
	        // Ignorer la première ligne si elle contient un en-tête
	        if (!lignes.isEmpty()) {
	            lignes.remove(0); // Supprimer l'en-tête si nécessaire
	        }

	        for (String ligne : lignes) {
	            try {
	                Acteur acteur = transformeLigneEnActeur(ligne);
	                acteurs.add(acteur);
	            } catch (Exception e) {
	                System.err.println("Erreur lors du traitement de la ligne : " + ligne);
	                e.printStackTrace();
	            }
	        }
	    } catch (IOException e) {
	        throw new ExceptionTech("Fichier " + csvFile + " introuvable.", e);
	    }
		return acteurs;
	    	}

	public static Acteur transformeLigneEnActeur(String ligneCsv) {
		String[] values = ligneCsv.split(";");
		String idImdb = values[0];
		String nomComplet = values[1];
		String[] nomEtPrenom = nomComplet.split(" ");
		String prenom = nomEtPrenom[0];
		String nom = nomEtPrenom.length > 1 ? nomEtPrenom[1] : ""; // Si un nom est présent

		// Vérification de la longueur de values
		if (values.length < 6) {
			System.out.println("La ligne CSV ne contient pas suffisamment de colonnes: " + ligneCsv);
			return null;
		}

		// Ajoutez le traitement de la date de naissance ici
        String dateNaissanceStr = values[2].trim(); // La date de naissance est à l'index 2
        LocalDate dateNaissance = null;

        // Logique pour parser la date de naissance
        if (!dateNaissanceStr.isEmpty()) {
            dateNaissance = parseDate(dateNaissanceStr);
        }
		// Impression de vérification
		//System.out.println("Date parsed: " + dateNaissance); // Vérification de la date

		String lieuNaissance = values[3];
		String[] villeRegionPay = lieuNaissance.split(",");
		String ville = villeRegionPay[0];
		String region = villeRegionPay.length > 1 ? villeRegionPay[1] : "";
		String pays = villeRegionPay.length > 2 ? villeRegionPay[2] : "";

		float taille = 0;
		if (!values[4].isEmpty()) {
			Float tailleTemp = Convertisseur.toFloatSafely(values[4].replace(" m", "").replace(",", "."));
			if (tailleTemp != null) {
				taille = tailleTemp;
			}
		}
		String url = values[5];

		Lieu lieu = new Lieu();
		lieu.setVille(ville);
		lieu.setRegion(region);
		lieu.setPays(pays);

		Acteur acteur = new Acteur();
		acteur.setIdImdb(idImdb);
		acteur.setNom(nom);
		acteur.setPrenom(prenom);
		acteur.setDateNaissance(dateNaissance);
		acteur.setLieuNaissance(lieu);
		acteur.setTaille(taille);
		acteur.setUrl(url);

		// Impression de vérification finale
		//System.out.println("Acteur créé : " + acteur);
		return acteur;
	}


	   // Méthode pour parser la date en utilisant les formats disponibles
	   private static LocalDate parseDate(String dateStr) {
	        try {
	            // Essayez d'abord de parser avec le format complet "MMMM d yyyy"
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d yyyy", Locale.ENGLISH);
	            return LocalDate.parse(dateStr, formatter);
	        } catch (DateTimeParseException e) {
	            try {
	                // Si ça échoue, essayez de parser avec seulement le mois et le jour "MMMM d"
	                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d", Locale.ENGLISH);
	                return parseMonthAndDay(dateStr, formatter);
	            } catch (DateTimeParseException ex) {
	                try {
	                    // Si ça échoue encore, essayez de parser avec seulement l'année "yyyy"
	                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
	                    LocalDate parsedDate = LocalDate.parse(dateStr, formatter);
	                    // Si seule l'année est disponible, retournez le 1er janvier de cette année
	                    return LocalDate.of(parsedDate.getYear(), Month.JANUARY, 1);
	                } catch (DateTimeParseException ey) {
	                    // Gérer les erreurs de parsing de la date
	                    System.out.println("Erreur lors du parsing de la date: " + dateStr);
	                    ey.printStackTrace();
	                    return null; // Retourne null ou une valeur par défaut selon le besoin
	                }
	            }
	        }
	    }

	    private static LocalDate parseMonthAndDay(String dateStr, DateTimeFormatter formatter) {
	        try {
	            LocalDate currentDate = LocalDate.now();
	            LocalDate parsedDate = LocalDate.parse(dateStr + " " + currentDate.getYear(), formatter);
	            return parsedDate;
	        } catch (DateTimeParseException ex) {
	            // Si le parsing échoue, retourner une date par défaut
	            return null; // Ou bien retourner une autre valeur par défaut selon le besoin
	        }
	    }
	
	public List<Realisateur> getRealisateurs(String csvFile) {
	    List<Realisateur> realisateurs = new ArrayList<>();
	    try {
	        List<String> lignes = FileUtils.readLines(new File(csvFile), "UTF-8");
	        // Ignorer la première ligne si elle contient un en-tête
	        if (!lignes.isEmpty()) {
	            lignes.remove(0); // Supprimer l'en-tête si nécessaire
	        }

	        for (String ligne : lignes) {
	            try {
	                Realisateur realisateur = transformeLigneEnRealisateur(ligne);
	                realisateurs.add(realisateur);
	            } catch (Exception e) {
	                System.err.println("Erreur lors du traitement de la ligne : " + ligne);
	                e.printStackTrace();
	            }
	        }
	    } catch (IOException e) {
	        throw new ExceptionTech("Fichier " + csvFile + " introuvable.", e);
	    }
	    return realisateurs;
	}

	private Realisateur transformeLigneEnRealisateur(String ligneCsv) {
	    String[] values = ligneCsv.split(";");
	    
	    if (values.length < 2) {
	        throw new IllegalArgumentException("Ligne CSV incorrecte : " + ligneCsv);
	    }
	    String nomComplet = values[1].trim(); // Le nom et le prénom sont à l'index 1
	    String[] nomEtPrenom = nomComplet.split(" ");
	    String nom = nomEtPrenom[0];
	    String prenom = nomEtPrenom.length > 1 ? nomEtPrenom[1] : ""; // Si un prénom est présent

	    String dateNaissanceStr = values[2].trim(); // La date de naissance est à l'index 2
        LocalDate dateNaissance = null;

        // Logique pour parser la date de naissance
        if (!dateNaissanceStr.isEmpty()) {
            dateNaissance = parseDate(dateNaissanceStr);
        }

	    // Création de l'objet Realisateur avec les données récupérées
	    Realisateur realisateur = new Realisateur();
	    realisateur.setNom(nom);
	    realisateur.setPrenom(prenom);
	    realisateur.setDateNaissance(dateNaissance);
	    // Ajoutez les autres attributs ici

	    return realisateur;
	}

}
