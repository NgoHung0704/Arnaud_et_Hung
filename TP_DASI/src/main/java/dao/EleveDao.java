/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.persistence.TypedQuery;
import metier.modele.Eleve;

/**
 *
 * @author ahngo
 */
public class EleveDao {
    
    public void createEleve(Eleve eleve){
        JpaUtil.obtenirContextePersistance().persist(eleve);
    }
    
    public void updateEleve (Eleve eleve){
        JpaUtil.obtenirContextePersistance().merge(eleve);
    }
    
    public Eleve findByMailAndMdp(String mail, String mdp){
        String s = "SELECT e FROM Eleve e WHERE e.mail = :mail AND e.motDePasse = :mdp";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Eleve.class);
        query.setParameter("mail", mail);
        query.setParameter("mdp", mdp);
        return (Eleve) query.getSingleResult();
    }
}
