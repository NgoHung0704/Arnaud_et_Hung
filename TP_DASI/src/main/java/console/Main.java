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
import util.Message;

/**
 *
 * @author ahngo
 */

// Reste à faire :
// Les statistiques 




public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        JpaUtil.creerFabriquePersistance();
        
        
        testerInitialiserIntervenant();
        testerInitialisationMatiere();
        //testerDemandeSoutien();
        
        //testerIncrireEleve();
        
       testerEvaluerProgressionEtBilan();
        
        //testerAuthentification();
        
        
        JpaUtil.fermerFabriquePersistance();
        
        
    }
    
    public static void testerIncrireEleve() {
        Service service = new Service();
        
        System.out.println("Test d'inscription d'un élève !");
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try {
            date = sdf.parse("26/03/2004");
        }catch (ParseException e){
            e.printStackTrace();
        }
        
        Eleve e1 = new Eleve("MALLE", "Arnaud", date, 1, "arnaud.malle@insa-lyon.fr", "monMotDePasse");
        Boolean resultat = service.inscireEleve(e1, "0691664J");
        Message.envoyerMailConfirmation(e1.getMail(), e1.getPrenom(), resultat);
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
        // Message.notificationSoutien(prenomInterv, nomInterv, numTel, matiere, prenomDemandeur, Integer.SIZE);
        
        
    }
    
    public static void testerAuthentification() {
        Service service = new Service();
        testerIncrireEleve();
        Eleve eleve = service.authentifierEleve("arnaud.malle@insa-lyon.fr", "monMotDePasse");
        
        if(eleve != null){
            System.out.println("True -> Eleve authentifié : " + eleve);
        }else{
            System.out.println("False -> Eleve non authentifié");
        }
    }
    
    public static void testerEvaluerProgressionEtBilan() {
        Service service = new Service();
        
        System.out.println("Test de l'évaluation de la progression de l'élève !");
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
        // Message.notificationSoutien(prenomInterv, nomInterv, numTel, matiere, prenomDemandeur, Integer.SIZE);
        
        // Evaluation de l'élève
        service.evaluerProgression(intervention, 0);
        
        // Bilan de l'intervenant
        service.redigerBilan(intervention, "Bravo tu maitrise tout !");
        
        // Renseigner la durée de l'intervention (minutes)
        service.renseignerDureeIntervention(intervention, 15);
        
        intervention = new Intervention("J'ai besoin d'aide en mathematique svp", date);
        resultat = service.creerDemandeSoutien(e, intervention, "Mathematique");
        System.out.println(resultat);
        
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
