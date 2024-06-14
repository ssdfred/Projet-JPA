package imdb.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;

@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String titre;
    private Date anneeSortie;

    @ManyToMany
    @JoinTable(name = "film_acteurs",
               joinColumns = @JoinColumn(name = "id_film"),
               inverseJoinColumns = @JoinColumn(name = "id_acteur"))
    private List<Acteur> acteurs;

    @OneToMany(mappedBy="film")
    private Set<Role> roles = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "film_realisateurs",
               joinColumns = @JoinColumn(name = "id_film"),
               inverseJoinColumns = @JoinColumn(name = "id_realisateur"))
    private Set<Realisateur> realisateurs = new HashSet<>();
    
    @ManyToMany
    @JoinTable(name="FILM_GENRE", joinColumns = @JoinColumn(name = "id_film"), 
               inverseJoinColumns = @JoinColumn(name = "id_genre"))
    private Set<Genre> genres = new HashSet<>();
    
    @ManyToMany
    @JoinTable(name="FILM_PAYS", joinColumns = @JoinColumn(name = "id_film"), 
               inverseJoinColumns = @JoinColumn(name = "id_pays"))
    private Set<Pays> pays = new HashSet<>();

    // Constructeurs, getters et setters

    public Film() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Date getAnneeSortie() {
        return anneeSortie;
    }

    public void setAnneeSortie(Date anneeSortie) {
        this.anneeSortie = anneeSortie;
    }

    public List<Acteur> getActeurs() {
        return acteurs;
    }

    public void setActeurs(List<Acteur> acteurs) {
        this.acteurs = acteurs;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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

    @Override
    public String toString() {
        return "Film [id=" + id + ", titre=" + titre + ", anneeSortie=" + anneeSortie + ", acteurs=" + acteurs
                + ", roles=" + roles + ", realisateurs=" + realisateurs + ", genres=" + genres + ", pays=" + pays + "]";
    }

	public void save(Film film) {
		// TODO Auto-generated method stub
		
	}

	public void setLangue(String trim) {
		// TODO Auto-generated method stub
		
	}

	public void setResume(String trim) {
		// TODO Auto-generated method stub
		
	}


}
