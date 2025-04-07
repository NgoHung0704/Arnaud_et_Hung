/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Intervenant;

/**
 *
 * @author ahngo
 */
public class IntervenantDao {
    
    public void createIntervenant(Intervenant intervenant){
        JpaUtil.obtenirContextePersistance().persist(intervenant);
    }
    
    
    public List<Intervenant> trouverIntervenantsDispoSupNiveau(Integer niveau) {
        String s = "SELECT i FROM Intervenant i WHERE i.enCours IS NULL AND i.niveauMax <= :niveau AND i.niveauMin >= :niveau";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Intervenant.class);
        query.setParameter("niveau", niveau); 
        return query.getResultList();
    }
    
    public void updateIntervenant (Intervenant intervenant){
        JpaUtil.obtenirContextePersistance().merge(intervenant);
    }
}
