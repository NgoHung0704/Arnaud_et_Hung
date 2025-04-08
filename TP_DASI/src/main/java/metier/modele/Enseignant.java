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
public class Enseignant extends Intervenant {

    private String typeEtab;

    public Enseignant() {
        super();
    }

    public Enseignant(String nom, String prenom, String numTel, Integer niveauMin, Integer niveauMax, String login, String motDePasse,
                      String typeEtab) {
        super(nom, prenom, numTel, niveauMin, niveauMax, login, motDePasse);
        this.typeEtab = typeEtab;
    }

    public String getTypeEtab() {
        return typeEtab;
    }

    public void setTypeEtab(String typeEtab) {
        this.typeEtab = typeEtab;
    }
}
