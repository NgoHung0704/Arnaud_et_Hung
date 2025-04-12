/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import com.google.maps.model.LatLng;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.modele.Etablissement;
import util.EducNetApi;
import util.GeoNetApi;

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
            
            // API GeoNet
            
            LatLng latLng = GeoNetApi.getLatLng(reponse.get(1) + "," + reponse.get(4));
            resultat.setLat(latLng.lat);
            resultat.setLng(latLng.lng);
            
        } catch (IOException ex) {
            Logger.getLogger(Outils.class.getName()).log(Level.SEVERE, null, ex);
            resultat = null;
        }
        
        return resultat;
    }
}
