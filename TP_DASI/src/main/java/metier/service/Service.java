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
import java.util.List;
import metier.modele.Eleve;
import metier.modele.Etablissement;
import metier.modele.Intervenant;
import metier.modele.Intervention;
import metier.modele.Matiere;

/**
 *
 * @author ahngo
 */
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
                
                //etablissement = outils.trouverEtablissement(codeEtablissement);
                // PROBLEME API EDUCNET SUPPRIMER LA LIGNE ENTRE --- ET DECOMMENTER CELLE D'EN HAUT
                // ----------
                etablissement = new Etablissement("UAJDIA", "Jean Jores", "JSP", 43f, 65f);
                // ----------
                
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
        
        return inscriptionReussie;
    }
   
    // Ce service représente une demande d'intervention de la part d'un élève, ce service
    // associe l'intervention à l'élève qui en fait la demande, à la matière demander et associe
    // aussi l'intervention à un intervenant (on associe directement un intervenant si il existe, pas
    // de liste d'attente et si aucun intervenant la demande est annulée)
    public Boolean creerDemandeSoutien(Eleve eleve, Intervention intervention, String matiereName){
        Boolean demandeReussie = false;
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            // Associer l'intervention avec l'élève
            eleve.addIntervention(intervention);
            intervention.setEleve(eleve);
            
            // Associer la matière à l'intervention
            Matiere matiere = matiereDao.obtenirMatiereFromNom(matiereName);
            intervention.setMatiere(matiere);
            
            List<Intervenant> intervenants = intervenantDao.trouverIntervenantsDispoSupNiveau(eleve.getNiveauEleve());
            
            if (intervenants.isEmpty()) throw new RuntimeException("Aucun intervenants disponible !");
            
            Integer minIntervention = Integer.MAX_VALUE;
            Intervenant bonIntervant = intervenants.get(0);
            for (Intervenant i : intervenants){
                Integer size = i.getHistoriqueInterventions().size();
                if (size < minIntervention) {
                    minIntervention = size;
                    bonIntervant = i;
                }
            }
            
            // On associe l'intervenant à l'intervention
            intervention.setIntervenant(bonIntervant);
            bonIntervant.addInterventiontHistorique(intervention);
            bonIntervant.setEnCours(intervention);
            
            // Persist intervention et update eleve, intervenant
            interventionDao.createIntervention(intervention);
            eleveDao.updateEleve(eleve);
            intervenantDao.updateIntervenant(bonIntervant);
            
            JpaUtil.validerTransaction();
            demandeReussie = true;
        } catch(Exception e) {
            JpaUtil.annulerTransaction();
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return demandeReussie;
    }
    
    // Initialise des Intervenants dans la base de donnée.
    public void initialiserIntervenant() {
        try{
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            Intervenant int1 = new Intervenant("ZOLA", "Emile", "06 46 69 28 09", 6, 0, "ezola", "germinal");
            Intervenant int2 = new Intervenant("HUGO", "Victor", "09 78 45 71 12", 2, 0, "vhugo", "demainDesLaube");
            Intervenant int3 = new Intervenant("MAUPASSANT", "Guy", "06 54 84 75 87", 6, 3, "gmaupassant", "leHorla");
            Intervenant int4 = new Intervenant("CAMUS", "Albert", "07 54 21 54 65", 1, 0, "acamus", "laChute");
            
            intervenantDao.createIntervenant(int1);
            intervenantDao.createIntervenant(int2);
            intervenantDao.createIntervenant(int3);
            intervenantDao.createIntervenant(int4);
            
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
