/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
    
}
