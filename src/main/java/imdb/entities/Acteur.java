package imdb.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Acteur {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(length = 255)
	private String nom;
	private String prenom;
	private LocalDate dateNaissance;
	private float taille;
	private String url;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "lieuNaissance_id")
	private Lieu lieuNaissance;
    @OneToMany(mappedBy = "acteur", cascade = CascadeType.ALL)
    private List<Role> roles = new ArrayList<>();
	@ManyToMany(mappedBy = "acteurs", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<Film> films;


	// Constructeurs, getters et setters

	public Acteur() {
	}

	public Acteur(String nom, String prenom, LocalDate dateNaissance, float taille, String url, Lieu lieuNaissance) {
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.taille = taille;
		this.url = url;
		this.lieuNaissance = lieuNaissance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public Lieu getLieuNaissance() {
		return lieuNaissance;
	}

	/**
	 * Getter pour taille
	 * 
	 * @return the taille
	 */
	public float getTaille() {
		return taille;
	}

	/**
	 * Setter pour taille
	 * 
	 * @param taille
	 */
	public void setTaille(float taille) {
		this.taille = taille;
	}

	/**
	 * Getter pour url
	 * 
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Setter pour url
	 * 
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Acteur [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", dateNaissance=" + dateNaissance
				+ ", taille=" + taille + ", url=" + url + ", lieuNaissance=" + lieuNaissance + ", films=" + films + "]";
	}

	/**
	 * Setter pour lieuNaissance
	 * 
	 * @param lieuNaissance
	 */
	public void setLieuNaissance(Lieu lieuNaissance) {
		this.lieuNaissance = lieuNaissance;
	}

	/**
	 * Getter pour films
	 * 
	 * @return the films
	 */
	public List<Film> getFilms() {
		return films;
	}

	/**
	 * Setter pour films
	 * 
	 * @param films
	 */
	public void setFilms(List<Film> films) {
		this.films = films;
	}

	/** Getter pour roles
	 * @return the roles 
	*/
	public List<Role> getRoles() {
		return roles;
	}

	/** Setter pour roles
	 * @param roles
	 */
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
