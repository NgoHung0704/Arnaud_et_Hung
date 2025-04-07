/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.modele.Etablissement;
import util.EducNetApi;

/**
 *
 * @author ahngo
 */
public class Outils {
    
    public Etablissement trouverEtablissement(String uai){
        
        EducNetApi api = new EducNetApi();
        Etablissement resultat = new Etablissement();
        try {
            List<String> reponse = api.getInformationEtablissement(uai); // attention réponse peut etre null
            
            resultat.setCodeEtablissement(reponse.get(0));
            resultat.setIps(reponse.get(8));
            resultat.setNomEtablissement(reponse.get(1));
            // Ajouter pour avoir lattitude et longitude grace à l'API GeoNet
        } catch (IOException ex) {
            Logger.getLogger(Outils.class.getName()).log(Level.SEVERE, null, ex);
            resultat = null;
        }
        
        return resultat;
    }
}
