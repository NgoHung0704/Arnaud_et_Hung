/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author ahngo
 */

@Entity
public class Etablissement {
    
     @Id
     @GeneratedValue(strategy=GenerationType.AUTO)
     private Long id;
     
     private String codeEtablissement;
     private String nomEtablissement;
     private String ips;
     private Double lat;
     private Double lng;
     
     @OneToMany(mappedBy="etablissement")
     private List<Eleve> eleves = new ArrayList<>();
     
    public Etablissement(String codeEtablissement, String nomEtablissement, String ips, Double lat, Double lng) {
        this.codeEtablissement = codeEtablissement;
        this.nomEtablissement = nomEtablissement;
        this.ips = ips;
        this.lat = lat;
        this.lng = lng;
    }

    public Etablissement() {
    }

    public Long getId() {
        return id;
    }

    public String getCodeEtablissement() {
        return codeEtablissement;
    }

    public String getNomEtablissement() {
        return nomEtablissement;
    }

    public String getIps() {
        return ips;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }
    
    public List<Eleve> getEleves() {
        return eleves;
    }
    
    public void setCodeEtablissement(String codeEtablissement) {
        this.codeEtablissement = codeEtablissement;
    }

    public void setNomEtablissement(String nomEtablissement) {
        this.nomEtablissement = nomEtablissement;
    }

    public void setIps(String ips) {
        this.ips = ips;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
    
    public void addEleve(Eleve eleve){
        this.eleves.add(eleve);
    }

    @Override
    public String toString() {
        return "Etablissement{" + "id=" + id + ", codeEtablissement=" + codeEtablissement + ", nomEtablissement=" + nomEtablissement + ", ips=" + ips + ", lat=" + lat + ", lng=" + lng + '}';
    }
    
    
}
