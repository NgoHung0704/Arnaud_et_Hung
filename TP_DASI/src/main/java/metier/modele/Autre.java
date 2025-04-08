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
public class Autre extends Intervenant {

    private String activite;

    public Autre() {
        super();
    }

    public Autre(String nom, String prenom, String numTel, Integer niveauMin, Integer niveauMax, String login, String motDePasse,
                 String activite) {
        super(nom, prenom, numTel, niveauMin, niveauMax, login, motDePasse);
        this.activite = activite;
    }

    public String getActivite() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite = activite;
    }
}

