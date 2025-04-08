/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package console;

import dao.JpaUtil;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import metier.modele.Eleve;
import metier.modele.Intervention;
import metier.service.Service;

/**
 *
 * @author ahngo
 */

// Pour la suite :
// TESTER LA DEMANDE DE SOUTIEN !! OK
// On doit ensuite gérer les liens entre Intervenant Autre, Enseignant et Etudiant OK
// - Gérer la génération du lien 
// - Gérer la confirmation d'une séance de soutien avec duree bilan et evalEleve tout ca
// - Service authentification




public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        JpaUtil.creerFabriquePersistance();
        //testerIncrireEleve();
        testerInitialiserIntervenant();
        testerInitialisationMatiere();
        testerDemandeSoutien();
        JpaUtil.fermerFabriquePersistance();
        
        
    }
    
    public static void testerIncrireEleve() {
        Service service = new Service();
        
        System.out.println("Inscription d'un éleve 1");
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try {
            date = sdf.parse("26/03/2004");
        }catch (ParseException e){
            e.printStackTrace();
        }
        
        Eleve e1 = new Eleve("BARRAN", "William", date, 1, "william.barran@insa-lyon.fr", "monMotDePasse");
        Boolean resultat = service.inscireEleve(e1, "0601297J");
        System.out.println(resultat + " -> Inscription de l'élève e1 : " + e1);
    }
    
    public static void testerDemandeSoutien() {
        Service service = new Service();
        
        System.out.println("Test de la demande de soutien !");
        // Inscription de l'élève
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try {
            date = sdf.parse("26/03/2004");
        }catch (ParseException e){
            e.printStackTrace();
        }
        Eleve e = new Eleve("MALLE", "Arnaud", date, 3, "arnaud.malle@insa-lyon.fr", "monMotDePass");
        service.inscireEleve(e, "0601297J");
        
        //Création de l'intervention
        Intervention intervention = new Intervention("J'ai besoin d'aide en mathematique svp", date);
        
        Boolean resultat = service.creerDemandeSoutien(e, intervention, "Mathematique");
        System.out.println(resultat + " -> Demande de résultat crée pour l'éleve " + e );
    }
    
    public static void testerInitialiserIntervenant() {
        Service service = new Service();
        
        System.out.println("Initialisation des intervenants");
        service.initialiserIntervenant();
    }
    
    public static void testerInitialisationMatiere() {
        Service service = new Service();
        
        System.out.println("Initialisation des matières");
        service.initialiserMatiere();
    }
    
}
