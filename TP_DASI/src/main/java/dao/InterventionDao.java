/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
    
}
