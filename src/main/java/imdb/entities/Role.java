package imdb.entities;

import jakarta.persistence.JoinColumn;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.mapping.Array;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String nom;

    @ManyToOne

    private Film film ;
    
    @ManyToOne
    private Acteur acteur;
    /** Constructeur
 	 * 
 	 */
 	public Role() {
 		super();
 	}
	/** Getter pour id
	 * @return the id 
	*/
	public int getId() {
		return id;
	}
	/** Setter pour id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/** Getter pour nom
	 * @return the nom 
	*/
	public String getNom() {
		return nom;
	}
	/** Setter pour nom
	 * @param nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	/** Getter pour acteur
	 * @return the acteur 
	*/
	
	@Override
	public String toString() {
		return "Role [id=" + id + ", nom=" + nom + ", film=" + film + ", acteur=" + acteur + "]";
	}
	/** Getter pour film
	 * @return the film 
	*/
	public Film getFilm() {
		return film;
	}
	/** Setter pour film
	 * @param film
	 */
	public void setFilm(Film film) {
		this.film = film;
	}
	/** Getter pour acteur
	 * @return the acteur 
	*/
	public Acteur getActeur() {
		return acteur;
	}
	/** Setter pour acteur
	 * @param acteur
	 */
	public void setActeur(Acteur acteur) {
		this.acteur = acteur;
	}



}