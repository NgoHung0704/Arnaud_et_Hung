/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.JpaUtil;
import java.util.List;
import metier.modele.Client;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author amalle
 */
public class ClientDao {
    
    public void create(Client client)
    {
        JpaUtil.obtenirContextePersistance().persist(client);
    }
    
    public void delete(Client client)
    {
        JpaUtil.obtenirContextePersistance().remove(client);
    }
    
    public Client update(Client client)
    {
        return JpaUtil.obtenirContextePersistance().merge(client);
    }
    
    public Client findById(Long id) 
    {
        return JpaUtil.obtenirContextePersistance().find(Client.class, id);
    }
    
    public List<Client> findAll()
    {
        String s = "select c from Client c order by c.nom asc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s,Client.class);
        return query.getResultList();
    }
}