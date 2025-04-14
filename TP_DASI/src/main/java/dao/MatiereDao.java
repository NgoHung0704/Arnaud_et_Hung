/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Matiere;

/**
 *
 * @author ahngo
 */
public class MatiereDao {
    
    public Matiere obtenirMatiereById(Long id){
        String s = "SELECT m FROM Matiere m WHERE m.id = :id";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Matiere.class);
        query.setParameter("id", id); 
        return (Matiere) query.getSingleResult();
    }
    
    public List<Matiere> listerMatiere(){
        String s = "SELECT m FROM Matiere m";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Matiere.class); 
        return query.getResultList();
    }
    
    public void createMatiere(Matiere matiere){
        JpaUtil.obtenirContextePersistance().persist(matiere);
    }
}
