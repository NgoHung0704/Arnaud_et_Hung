/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.persistence.TypedQuery;
import metier.modele.Matiere;

/**
 *
 * @author ahngo
 */
public class MatiereDao {
    
    public Matiere obtenirMatiereFromNom(String matiere){
        String s = "SELECT m FROM Matiere m WHERE m.matiere = :matiere";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Matiere.class);
        query.setParameter("matiere", matiere); 
        return (Matiere) query.getSingleResult();
    }
    
    public void createMatiere(Matiere matiere){
        JpaUtil.obtenirContextePersistance().persist(matiere);
    }
}
