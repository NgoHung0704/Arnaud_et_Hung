/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.TypedQuery;
import metier.modele.Etablissement;

/**
 *
 * @author ahngo
 */
public class EtablissementDao {
    
    public Etablissement chercherEtablissement(String code) {
        String s = "SELECT e FROM Etablissement e WHERE e.codeEtablissement = :code";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Etablissement.class);
        query.setParameter("code", code);
        List<Etablissement> listResultat = query.getResultList();
        
        Etablissement resultat;
        if (listResultat.isEmpty()){
            resultat = null;
        }else {
            resultat = listResultat.get(0);
        }
        return resultat;
    }
    
    public void createEtablissement(Etablissement etablissement){
        JpaUtil.obtenirContextePersistance().persist(etablissement);
    }
    
    public void updateEtablissement (Etablissement etablissement){
        JpaUtil.obtenirContextePersistance().merge(etablissement);
    }
}
