/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author ahngo
 */
@Entity
@Inheritance (strategy = InheritanceType.JOINED)
public class Intervenant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String nom;
    private String prenom;
    private String login;
    
    @Column(unique = true)
    private String numTel;
    
    private Integer niveauMin;
    private Integer niveauMax;
    private String motDePasse;
    
    @OneToOne(mappedBy="intervenant")
    private Intervention enCours;
    
    @OneToMany
    private List<Intervention> historiqueInterventions;
    
    public Intervenant(){
        
    }

    public Intervenant(String nom, String prenom, String numTel, Integer niveauMin, Integer niveauMax, String login, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.numTel = numTel;
        this.niveauMin = niveauMin;
        this.niveauMax = niveauMax;
        this.motDePasse = motDePasse;
        this.login = login;
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

    public String getLogin() {
        return login;
    }

    public String getNumTel() {
        return numTel;
    }

    public Integer getNiveauMin() {
        return niveauMin;
    }

    public Integer getNiveauMax() {
        return niveauMax;
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

    public void setLogin(String login) {
        this.login = login;
    }
    
    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public void setNiveauMin(Integer niveau) {
        this.niveauMin = niveau;
    }

    public void setNiveauMax(Integer niveauMax) {
        this.niveauMax = niveauMax;
    }
    
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }  

    public List<Intervention> getHistoriqueInterventions() {
        return historiqueInterventions;
    }

    public Intervention getEnCours() {
        return enCours;
    }

    public void setEnCours(Intervention enCours) {
        this.enCours = enCours;
    }
    
    
    public void addInterventiontHistorique(Intervention intervention){
        this.historiqueInterventions.add(intervention);
    }
}
