package imdb;


import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import imdb.entities.Acteur;

public class TestEntityManagerFactory {
    public static void main(String[] args) {
		String csvFilePath = "/Projet-JPA/src/main/resources/Csv";

		try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextRecord;
            while ((nextRecord = reader.readNext()) != null) {
                String nom = nextRecord[1];
                String dateNaissance = nextRecord[2];
                String lieuNaissance = nextRecord[3];
                String url = nextRecord[4];

                // Créez une requête SQL pour insérer les données dans votre table Acteur
                String sql = "INSERT INTO acteur (nom, date_naissance, lieu_naissance, url) VALUES (?, ?, ?, ?)";
                // Exécutez la requête avec les valeurs extraites
                // Exemple fictif : executeSql(sql, nom, dateNaissance, lieuNaissance, url);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }
}