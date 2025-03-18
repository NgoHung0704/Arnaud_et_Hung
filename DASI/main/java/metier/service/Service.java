/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import dao.ClientDao;
import dao.JpaUtil;
import java.util.List;
import metier.modele.Client;

/**
 *
 * @author amalle
 */
public class Service {
    
    
    public Boolean inscrireClient(Client client)
    {
        ClientDao clientDao = new ClientDao();
        
        Boolean res = true;
        
        try  {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            clientDao.create(client);
            JpaUtil.validerTransaction();
        }catch  (Exception ex){
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            res = false;
        }finally {
            JpaUtil.fermerContextePersistance();
        }
        return res;
    }
    
     public List<Client> listerClients(){
        ClientDao clientDao = new ClientDao();
        
        List<Client> liste;
        
        try  {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            liste = clientDao.findAll();
            JpaUtil.validerTransaction();
        }catch  (Exception ex){
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            liste = null;
        }finally {
            JpaUtil.fermerContextePersistance();
        }
         
        return liste;
     }
}
