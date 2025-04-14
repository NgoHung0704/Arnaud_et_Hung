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
import java.util.List;
import java.util.Map;
import metier.modele.Eleve;
import metier.modele.Intervenant;
import metier.modele.Intervention;
import metier.modele.Matiere;
import metier.service.Service;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) { 
        
        JpaUtil.creerFabriquePersistance();
        
        // On créer en dur les intervenants et les matières
        testerInitialiserIntervenant();
        testerInitialisationMatiere();
        
        // L'élève s'inscrit 
        testerIncrireEleve();
        
        // Un élève s'authentifie 
        Eleve eleve = testerAuthentificationEleve();
        
        // L'élève authentifié demande un soutien
        Intervention intervention = testerDemandeIntervention(eleve);
        
        // L'intervenant s'authentifie pour accéder à la demande de soutien
        Intervenant intervenant = testerAuthentificationIntervenant();
        
        // Récupérer l'intervention en cours (Intervention correspondant à celle attribuée à l'intervenant) :
        Intervention interventionIntervenant = intervenant.getEnCours();

        // L'intervention se finie l'élève peut renseigner l'évaluation
        testerEvaluerProgression(intervention);
        
        // L'intervenant rédige aussi le bilan
        testerRedigerBilan(interventionIntervenant);
        
        // On termine la visio
        testerTerminerVisio(intervention);
   
        
        // test des statistiques
        testerRepartitionGeo();
        testerStatsIps();
        testerStatsMoyenne();
        
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
        service.inscireEleve(e1, "0691664J");
    }
    
    public static Intervention testerDemandeIntervention(Eleve eleve) {
        Service service = new Service();
        
        
        System.out.println("Test de la demande de soutien !");
        
        List<Matiere> listMatiere = service.listerMatiere();
        Matiere matiere = service.obtenirMatiereDepuisId(listMatiere.get(0).getId());
        Intervention intervention = service.creerDemandeIntervention(eleve, "J'ai besoin d'aide svp", matiere);
        
        if(intervention != null){
            System.out.println("True -> Intervention crée avec succès : " + intervention);
        }else{
            System.out.println("False -> Echec de création de l'intervention");
        }
        return intervention;
    }
    
    public static Eleve testerAuthentificationEleve() {
        Service service = new Service();
        Eleve eleve = service.authentifierEleve("arnaud.malle@insa-lyon.fr", "monMotDePasse");
        
        if(eleve != null){
            System.out.println("True -> Eleve authentifié : " + eleve);
        }else{
            System.out.println("False -> Eleve non authentifié");
        }
        return eleve;
    }
    
    public static Intervenant testerAuthentificationIntervenant() {
        Service service = new Service();
        Intervenant intervenant = service.authentifierIntervenant("acamus", "laChute"); // Login & Mot de passe
        
        if(intervenant != null){
            System.out.println("True -> Intervenant authentifié : " + intervenant);
        }else{
            System.out.println("False -> Intervenant non authentifié");
        }
        return intervenant;
    }
    
    public static void testerEvaluerProgression(Intervention intervention) {
        Service service = new Service();
        
        System.out.println("Test de l'évaluation de la progression de l'élève !");
        // Evaluation de l'élève
        service.evaluerProgression(intervention, 0);
    }
    
    public static void testerRedigerBilan(Intervention intervention) {
        Service service = new Service();
        
        System.out.println("Test de la rédaction d'un bilan !");
        // Bilan de l'intervenant
        service.redigerBilan(intervention, "Bravo tu maitrise tout !");
    }
    
    public static void testerTerminerVisio(Intervention intervention) {
        Service service = new Service();
        
        System.out.println("Test de terminer la visio !");
        service.terminerVisio(intervention);
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
    
    public static void testerRepartitionGeo() {
        Service service = new Service();
        
        System.out.println("Obtention des répartitions géographiques");
        Map<String, List<Double>> repartition = service.obtenirRepartitionGeographique();
        System.out.println("Répartition géographique : " + repartition);
    }
    
    public static void testerStatsIps(){
        Service service = new Service();
        
        System.out.println("Obtention des statistiques IPS");
        Map<String, String> statsIps = service.obtenirStatsIps();
        System.out.println("Statistiques IPS : " + statsIps);
    }
    
    public static void testerStatsMoyenne() {
        Service service = new Service();
        
        System.out.println("Obtention de la moyenne des temps des interventions");
        Float moyenne = service.obtenirMoyenneTempsInterventions();
        System.out.println("Moyenne de temps des intervention : " + moyenne + " minutes.");
    }
    
}
