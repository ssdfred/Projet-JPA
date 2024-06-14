package imdb.entities;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Genre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(length = 255)
	private String nom;
	
	public Genre() {
		
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
	@Override
	public String toString() {
		return "Genre [id=" + id + ", nom=" + nom + "]";
	}
}
