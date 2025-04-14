/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Intervention;


/**
 *
 * @author ahngo
 */
public class InterventionDao {
    
    public void createIntervention(Intervention intervention){
        JpaUtil.obtenirContextePersistance().persist(intervention);
    }
    
    public void updateIntervention (Intervention intervention){
        JpaUtil.obtenirContextePersistance().merge(intervention);
    }
    
    public List<Intervention> findAll() {
        String s = "SELECT i FROM Intervention i";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Intervention.class); 
        return query.getResultList(); 
    }
    
}
