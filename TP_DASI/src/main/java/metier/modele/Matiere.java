/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author ahngo
 */
@Entity
public class Matiere {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(unique = true)
    private String matiere;

    public Matiere(){
        
    }
    
    public Matiere(String matiere) {
        this.matiere = matiere;
    }

    public Long getId() {
        return id;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    @Override
    public String toString() {
        return "Matiere{" + "id=" + id + ", matiere=" + matiere + '}';
    }
    
    
    
}
