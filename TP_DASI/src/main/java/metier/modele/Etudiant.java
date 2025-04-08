/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import javax.persistence.Entity;

/**
 *
 * @author amalle
 */
@Entity
public class Etudiant extends Intervenant {
    
    private String universite;
    private String specialte;

    public Etudiant() {
        super();
    }

    public Etudiant(String nom, String prenom, String numTel, Integer niveauMin, Integer niveauMax, String login, String motDePasse,
                    String universite, String specialte) {
        super(nom, prenom, numTel, niveauMin, niveauMax, login, motDePasse);
        this.universite = universite;
        this.specialte = specialte;
    }

    public String getUniversite() {
        return universite;
    }

    public void setUniversite(String universite) {
        this.universite = universite;
    }

    public String getSpecialte() {
        return specialte;
    }

    public void setSpecialte(String specialte) {
        this.specialte = specialte;
    }
}
