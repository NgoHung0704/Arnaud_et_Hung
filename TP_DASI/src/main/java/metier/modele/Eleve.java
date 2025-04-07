/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ahngo
 */
@Entity
public class Eleve {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String nom;
    private String prenom;
    
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    
    private Integer niveauEleve;
    
    @Column(unique = true)
    private String mail;
    
    private String motDePasse;
    
    @ManyToOne
    private Etablissement etablissement;
    
    @OneToMany(mappedBy="eleve")
    private List<Intervention> interventions = new ArrayList<>();

    public Eleve() {
    }
    
    // private Etablissement etablissement;
    // private List<Intervention> interventions;

    // Constructeur
    public Eleve(String nom, String prenom, Date dateNaissance, Integer niveauEleve, String mail, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.niveauEleve = niveauEleve;
        this.mail = mail;
        this.motDePasse = motDePasse;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public Integer getNiveauEleve() {
        return niveauEleve;
    }

    public String getMail() {
        return mail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setNiveauEleve(Integer niveauEleve) {
        this.niveauEleve = niveauEleve;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
    
    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }
    
    public void addIntervention (Intervention intervention){
        this.interventions.add(intervention);
    }

    @Override
    public String toString() {
        return "Eleve{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", dateNaissance=" + dateNaissance + ", niveauEleve=" + niveauEleve + ", mail=" + mail + ", motDePasse=" + motDePasse + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.id);
        hash = 47 * hash + Objects.hashCode(this.nom);
        hash = 47 * hash + Objects.hashCode(this.prenom);
        hash = 47 * hash + Objects.hashCode(this.dateNaissance);
        hash = 47 * hash + Objects.hashCode(this.niveauEleve);
        hash = 47 * hash + Objects.hashCode(this.mail);
        hash = 47 * hash + Objects.hashCode(this.motDePasse);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Eleve other = (Eleve) obj;
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.prenom, other.prenom)) {
            return false;
        }
        if (!Objects.equals(this.niveauEleve, other.niveauEleve)) {
            return false;
        }
        if (!Objects.equals(this.mail, other.mail)) {
            return false;
        }
        if (!Objects.equals(this.motDePasse, other.motDePasse)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
}
