/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import dao.EleveDao;
import dao.EtablissementDao;
import dao.IntervenantDao;
import dao.InterventionDao;
import dao.JpaUtil;
import dao.MatiereDao;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import metier.modele.Autre;
import metier.modele.Eleve;
import metier.modele.Enseignant;
import metier.modele.Etablissement;
import metier.modele.Etudiant;
import metier.modele.Intervenant;
import metier.modele.Intervention;
import metier.modele.Matiere;
import util.Message;

public class Service {
    
    private final EleveDao eleveDao;
    private final EtablissementDao etablissementDao;
    private final InterventionDao interventionDao;
    private final MatiereDao matiereDao;
    private final IntervenantDao intervenantDao;

    public Service() {
        this.eleveDao = new EleveDao();
        this.etablissementDao = new EtablissementDao();
        this.interventionDao = new InterventionDao();
        this.matiereDao = new MatiereDao();
        this.intervenantDao = new IntervenantDao();
    }

    
    
    // Inscription d'un élève
    public Boolean inscireEleve(Eleve eleve, String codeEtablissement){
        Boolean inscriptionReussie = false;
        try  {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            // Récupération de l'etablissement et vérification
            Etablissement etablissement = etablissementDao.chercherEtablissement(codeEtablissement);           
            
            if(etablissement == null){
                
                Outils outils = new Outils();
                
                etablissement = outils.trouverEtablissement(codeEtablissement);
                
                if (etablissement != null){
                    // On ajoute l'élève à l'établissement
                    etablissement.addEleve(eleve);
                    // On persist l'établissement
                    etablissementDao.createEtablissement(etablissement);
                    eleve.setEtablissement(etablissement);
                    eleveDao.createEleve(eleve);
                }else{
                    inscriptionReussie = false;
                    throw new RuntimeException("Etablissement non trouvé !");
                }
            }else{
                // On associe l'élève à l'établissement
                eleve.setEtablissement(etablissement);
                etablissement.addEleve(eleve);
                // On met à jour l'établissement
                etablissementDao.updateEtablissement(etablissement);
                // On persist l'élève
                eleveDao.createEleve(eleve);
            }
            
            JpaUtil.validerTransaction();
            inscriptionReussie = true;
        }catch (Exception e){
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        }finally {
            JpaUtil.fermerContextePersistance();
        }
        Message.envoyerMailConfirmation(eleve.getMail(), eleve.getPrenom(), inscriptionReussie);
        return inscriptionReussie;
    }
    
    public List<Matiere> listerMatiere(){
        List<Matiere> listMatiere = new ArrayList<>();
        try {
            JpaUtil.creerContextePersistance();
            
            listMatiere = matiereDao.listerMatiere();
            
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JpaUtil.fermerContextePersistance();
        }
        return listMatiere;
    }
    
    public Intervention creerDemandeIntervention(Eleve eleve, String demande, Matiere matiere){
        Intervention intervention = null;
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            // Creation de l'intervention
            Date date = new Date(); 
            intervention = new Intervention(demande, date);
            
            // Associer l'intervention avec l'élève
            eleve.addIntervention(intervention);
            intervention.setEleve(eleve);
            
            // Associer la matière à l'intervention
            intervention.setMatiere(matiere);
            
            List<Intervenant> intervenants = intervenantDao.trouverIntervenantsSupNiveau(eleve.getNiveauEleve());
            
            if (intervenants.isEmpty()) throw new RuntimeException("Aucun intervenants disponible pour le niveau de l'élève !");
            
            Integer minIntervention = Integer.MAX_VALUE;
            Intervenant bonIntervant = null;
            for (Intervenant i : intervenants){ // Pour chaque intervenant
                if (i.getEnCours() == null){ // Si l'intervenant est disponible
                    Integer size = i.getHistoriqueInterventions().size(); // On cherche celui qui a le moins d'interventions
                    if (size < minIntervention) {
                        minIntervention = size;
                        bonIntervant = i;
                    }
                }
            }
            
            if(bonIntervant == null) throw new RuntimeException("Aucun intervenants disponible !");
            
            // Génération du lien
            String lienVisio = "https://servif.insa-lyon.fr/InteractIF/visio.html?eleve=" + eleve.getMail()
                    + "&intervenant=" + bonIntervant.getLogin();
            intervention.setLienVisio(lienVisio);
            
            
            
            // On associe l'intervenant à l'intervention
            intervention.setIntervenant(bonIntervant);
            bonIntervant.addInterventiontHistorique(intervention);
            bonIntervant.setEnCours(intervention);
            
            // Persist intervention et update eleve, intervenant
            interventionDao.createIntervention(intervention);
            eleveDao.updateEleve(eleve);
            intervenantDao.updateIntervenant(bonIntervant);
            
            JpaUtil.validerTransaction();
            Message.notificationSoutien(bonIntervant.getPrenom(), 
                bonIntervant.getNom(), 
                bonIntervant.getNumTel(), 
                matiere.getMatiere(), 
                eleve.getPrenom(), eleve.getNiveauEleve());
        } catch(Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return intervention;
    }
    
    public Matiere obtenirMatiereDepuisId(Long id){
        Matiere matiere = null;
        try{
            JpaUtil.creerContextePersistance();
            
            matiere = matiereDao.obtenirMatiereById(id);
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            JpaUtil.fermerContextePersistance();
        }
        return matiere;
    }
    
    public Eleve authentifierEleve(String mail, String mdp) {
        Eleve eleve = null;
        try{
            JpaUtil.creerContextePersistance();
            eleve = eleveDao.findByMailAndMdp(mail, mdp);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            JpaUtil.fermerContextePersistance();
        }
        return eleve;
    }
    
    public Boolean evaluerProgression(Intervention intervention, Integer evalElev){
        Boolean reussite = false;
        try{
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            intervention.setEvalEleve(evalElev);
            interventionDao.updateIntervention(intervention);
            
            JpaUtil.validerTransaction();
            reussite = true;
        }catch (Exception e){
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        }finally{
            JpaUtil.fermerContextePersistance();
        }
        return reussite;
    }
    
    public Boolean redigerBilan(Intervention intervention, String bilan){
        Boolean reussite = false;
        try{
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            intervention.setBilanInterv(bilan);
            interventionDao.updateIntervention(intervention);
            
            JpaUtil.validerTransaction();
            reussite = true;
        }catch (Exception e){
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        }finally{
            JpaUtil.fermerContextePersistance();
        }
        return reussite;
    }
    
    public Boolean terminerVisio(Intervention intervention){
        Boolean reussite = false;
        try{
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            // On calcul la durée
            Date maintenant = new Date(); // Date actuelle
            long diffEnMillisecondes = maintenant.getTime() - intervention.getDate().getTime();
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diffEnMillisecondes);
            
            intervention.setDuree(minutes);
            interventionDao.updateIntervention(intervention);
            
            // On libère l'intervenant
            Intervenant intervenant = intervention.getIntervenant();
            intervenant.setEnCours(null);
            intervenantDao.updateIntervenant(intervenant);
            
            JpaUtil.validerTransaction();
            reussite = true;
        }catch (Exception e){
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        }finally{
            JpaUtil.fermerContextePersistance();
        }
        return reussite;
    }
    
    public Intervenant authentifierIntervenant(String login, String mdp){
        Intervenant intervenant = null;
        try{
            JpaUtil.creerContextePersistance();
            intervenant = intervenantDao.findByLoginAndMdp(login, mdp);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            JpaUtil.fermerContextePersistance();
        }
        return intervenant;
    }
    
    public Map<String, List<Double>> obtenirRepartitionGeographique() {
        Map<String, List<Double>> coordonneesMap = new HashMap<>();
        try{
            JpaUtil.creerContextePersistance();
        
            List<Etablissement> etablissements = etablissementDao.findAll();
            for (Etablissement e : etablissements) {
                List<Double> coords = new ArrayList<>();
                coords.add(e.getLat());
                coords.add(e.getLng());
                coordonneesMap.put(e.getCodeEtablissement(), coords);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            JpaUtil.fermerContextePersistance();
        }
        return coordonneesMap;
    }
    
    public Map<String, String> obtenirStatsIps() {
        Map<String, String> statsIps = new HashMap<>();
        try{
            JpaUtil.creerContextePersistance();
        
            List<Etablissement> etablissements = etablissementDao.findAll();
            for (Etablissement e : etablissements) {
                String ips = e.getIps();
                statsIps.put(e.getCodeEtablissement(), ips);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            JpaUtil.fermerContextePersistance();
        }
        return statsIps;
    }
    
    public Float obtenirMoyenneTempsInterventions(){
        Float moyenne = 0f;
        try{
            JpaUtil.creerContextePersistance();
            Integer n = 0;
            List<Intervention> interventions = interventionDao.findAll();
            for (Intervention e : interventions) {
                n++;
                moyenne += e.getDuree() / n;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            JpaUtil.fermerContextePersistance();
        }
        return moyenne;
    }
    
    public void initialiserIntervenant() {
        try{
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            Etudiant etu1 = new Etudiant("ZOLA", "Emile", "06 46 69 28 09", 6, 0, "ezola", "germinal", "Lyon 1", "Physique");
            Enseignant ens1 = new Enseignant("HUGO", "Victor", "09 78 45 71 12", 2, 0, "vhugo", "demainDesLaube", "Lycee");
            Autre autre1 = new Autre("MAUPASSANT", "Guy", "06 54 84 75 87", 6, 3, "gmaupassant", "leHorla", "Ecrivain");
            Enseignant ens2 = new Enseignant("CAMUS", "Albert", "07 54 21 54 65", 1, 0, "acamus", "laChute", "College");
            
            intervenantDao.createIntervenant(etu1);
            intervenantDao.createIntervenant(ens1);
            intervenantDao.createIntervenant(autre1);
            intervenantDao.createIntervenant(ens2);
            
            JpaUtil.validerTransaction();
        }catch (Exception e){
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        }finally{
            JpaUtil.fermerContextePersistance();
        }
    } 
    
    public void initialiserMatiere(){
        try{
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            Matiere matiere1 = new Matiere("Mathematique");
            Matiere matiere2 = new Matiere("Physique");
            Matiere matiere3 = new Matiere("Informatique");
            Matiere matiere4 = new Matiere("Francais");
            
            matiereDao.createMatiere(matiere1);
            matiereDao.createMatiere(matiere2);
            matiereDao.createMatiere(matiere3);
            matiereDao.createMatiere(matiere4);
            
            JpaUtil.validerTransaction();
        }catch (Exception e){
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        }finally{
            JpaUtil.fermerContextePersistance();
        }
    }
    
}
