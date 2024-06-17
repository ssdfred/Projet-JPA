package imdb.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import imdb.entities.Film;

public class FilmDatabaseInserter {

    public static void main(String[] args) {
        String csvFile = "E:\\CDA Curs\\java\\Projet-JPA\\src\\main\\resources\\Csv\\films.csv"; // Path to the CSV file
        List<Film> films = null;
		try {
			films = Reader.getFilms(csvFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        insertFilmsIntoDatabase(films);
    }

    public static void insertFilmsIntoDatabase(List<Film> films) {
        String url = "jdbc:mysql://localhost:3306/imdb";
        String user = "root";
        String password = "root";

        String sql = "INSERT INTO films (titre, anneeSortie, langue, resume) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (Film film : films) {
                pstmt.setString(1, film.getTitre());
                pstmt.setDate(2, new java.sql.Date(film.getAnneeSortie().getTime()));
                pstmt.setString(3, film.getLangue());
                pstmt.setString(4, film.getResume());
                pstmt.addBatch();
            }
            
            pstmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    } 
}
