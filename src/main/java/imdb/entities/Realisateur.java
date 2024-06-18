package imdb.entities;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Realisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 255)
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lieuNaissance_id")
    private Lieu lieuNaissance;

    @ManyToMany
    @JoinTable(name="FILM_REAlISATEUR", joinColumns = @JoinColumn(name = "id_realisateur"), 
               inverseJoinColumns = @JoinColumn(name = "id_film"))
    private Set<Film> films = new HashSet<>();
	/** Constructeur
	 * 
	 */
	public Realisateur() {
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

	/** Getter pour prenom
	 * @return the prenom 
	*/
	public String getPrenom() {
		return prenom;
	}

	/** Setter pour prenom
	 * @param prenom
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/** Getter pour dateNaissance
	 * @return the dateNaissance 
	*/
	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	/** Setter pour dateNaissance
	 * @param dateNaissance
	 */
	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	/** Getter pour lieuNaissance
	 * @return the lieuNaissance 
	*/
	public Lieu getLieuNaissance() {
		return lieuNaissance;
	}

	/** Setter pour lieuNaissance
	 * @param lieuNaissance
	 */
	public void setLieuNaissance(Lieu lieuNaissance) {
		this.lieuNaissance = lieuNaissance;
	}

	@Override
	public String toString() {
		return "Realisateur [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", dateNaissance=" + dateNaissance
				+ ", lieuNaissance=" + lieuNaissance + "]";
	}

	/** Getter pour films
	 * @return the films 
	*/
	public Set<Film> getFilms() {
		return films;
	}

	/** Setter pour films
	 * @param films
	 */
	public void setFilms(Set<Film> films) {
		this.films = films;
	}


}
