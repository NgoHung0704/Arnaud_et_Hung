/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.PrintStream;

/**
 *
 * @author amalle
 */
public class Message {

    public Message() {
    }
    
    private static final PrintStream OUT = System.out;
    
    public static void envoyerMailConfirmation(String destinataire, String nom, Boolean reussite){
        OUT.println("Expéditeur : contact@instruct.if");
        OUT.println("Pour : " + destinataire);
        
        if(reussite){
            OUT.println("Sujet : Bienvenue sur le réseau INSTRUCT'IF");
            OUT.println("Bonjour " + nom + ", nous te confirmons ton inscription sur le réseau INSTRUCT'IF. Si tu as besoin d'un soutien pour tes"
                    + " leçons ou tes devoirs, rends-toi sur notre site pour une mise en relation avec un intervenant.");
        }else{
            OUT.println("Sujet : Echec de l'inscription sur le réseau INSTRUCT'IF");
            OUT.println("Bonjour " + nom + ", ton inscription sur le réseau INSTRUCT'IF a malencontreusement échoué... Merci de recommencer ultérieurement.");            
        }
    }
    
    public static void notificationSoutien(String prenomInterv, String nomInterv, String numTel, String matiere, String prenomDemandeur, Integer niveau){
        String classe = niveau + "ème";
        switch (niveau) {
            case 0:
                classe = "terminale";
                break;
            case 1:
                classe = "1ère";
                break;
            case 2:
                classe = "2nd";
                break;
            default:
                break;
        }
        
        OUT.println("Pour : " + prenomInterv + " " + nomInterv + ", Tel : " + numTel);
        OUT.println("Message : Bonjour " + prenomInterv + ". Merci de prendre en charge la demande de soutien en << " + matiere + " >> demandée par "
        + prenomDemandeur + " en classe de " + classe + ".");
        
        
        
    }
}
