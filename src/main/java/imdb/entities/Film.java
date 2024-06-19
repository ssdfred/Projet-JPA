package imdb.entities;

import java.time.Year;
import java.util.ArrayList;
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
import jakarta.persistence.OneToMany;

@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String idImdb;
    private String titre;
    private Year anneeSortie;
    private String langue;

    @Column(columnDefinition = "TEXT", length = 500)
    private String resume;

    @ManyToMany
    @JoinTable(name = "film_acteurs",
               joinColumns = @JoinColumn(name = "id_film"),
               inverseJoinColumns = @JoinColumn(name = "id_acteur"))
    private List<Acteur> acteurs;


    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Role> roles = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "film_realisateurs",
               joinColumns = @JoinColumn(name = "id_film"),
               inverseJoinColumns = @JoinColumn(name = "id_realisateur"))
    private Set<Realisateur> realisateurs = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "FILM_GENRE",
               joinColumns = @JoinColumn(name = "id_film"),
               inverseJoinColumns = @JoinColumn(name = "id_genre"))
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "FILM_PAYS",
               joinColumns = @JoinColumn(name = "id_film"),
               inverseJoinColumns = @JoinColumn(name = "id_pays"))
    private Set<Pays> pays = new HashSet<>();

    // Constructeurs
    public Film(String titre, Year anneeSortie, String langue, String resume) {
        this.titre = titre;
        this.anneeSortie = anneeSortie;
        this.langue = langue;
        this.resume = resume;
    }

 

    public Film() {
		// TODO Auto-generated constructor stub
	}

    public void addRole(Role role) {
        roles.add(role);
        
        this.titre=this.titre+role.getNom();
    }

    public void removeRole(Role role) {
        roles.remove(role);
        this.titre=this.titre+role.getNom();
    }



    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Year getAnneeSortie() {
        return anneeSortie;
    }

    public void setAnneeSortie(Year anneeSortie) {
        this.anneeSortie = anneeSortie;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public List<Acteur> getActeurs() {
        return acteurs;
    }

    public void setActeurs(List<Acteur> acteurs) {
        this.acteurs = acteurs;
    }

 

    public Set<Realisateur> getRealisateurs() {
        return realisateurs;
    }

    public void setRealisateurs(Set<Realisateur> realisateurs) {
        this.realisateurs = realisateurs;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Pays> getPays() {
        return pays;
    }

    public void setPays(Set<Pays> pays) {
        this.pays = pays;
    }



	/** Getter pour id
	 * @return the id 
	*/
	public Integer getId() {
		return id;
	}



	/** Setter pour id
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
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



	/** Getter pour idImdb
	 * @return the idImdb 
	*/
	public String getIdImdb() {
		return idImdb;
	}



	/** Setter pour idImdb
	 * @param idImdb
	 */
	public void setIdImdb(String idImdb) {
		this.idImdb = idImdb;
	}


}
