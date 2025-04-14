/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author ahngo
 */
@Entity
public class Intervention {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String messageEleve;
    
    @Temporal(TemporalType.DATE)
    private Date date;
    
    private String lienVisio;
    private Long duree; // En minutes
    private String bilanInterv;
    private Integer evalEleve;
    
    @ManyToOne
    private Matiere matiere;
    
    @ManyToOne
    private Eleve eleve;
    
    @OneToOne
    private Intervenant intervenant;

    public Intervention(){
        
    }
    
    public Intervention(String messageEleve, Date date) {
        this.messageEleve = messageEleve;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getMessageEleve() {
        return messageEleve;
    }

    public Date getDate() {
        return date;
    }

    public String getLienVisio() {
        return lienVisio;
    }

    public Long getDuree() {
        return duree;
    }

    public String getBilanInterv() {
        return bilanInterv;
    }

    public Integer getEvalEleve() {
        return evalEleve;
    }

    public Intervenant getIntervenant() {
        return intervenant;
    } 

    public Matiere getMatiere() {
        return matiere;
    }

    public Eleve getEleve() {
        return eleve;
    } 

    public void setMessageEleve(String messageEleve) {
        this.messageEleve = messageEleve;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLienVisio(String lienVisio) {
        this.lienVisio = lienVisio;
    }

    public void setDuree(Long duree) {
        this.duree = duree;
    }

    public void setBilanInterv(String bilanInterv) {
        this.bilanInterv = bilanInterv;
    }

    public void setEvalEleve(Integer evalEleve) {
        this.evalEleve = evalEleve;
    }
    
    public void setEleve(Eleve eleve){
        this.eleve = eleve;
    }
    
    public void setMatiere(Matiere matiere){
        this.matiere = matiere;
    }

    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }

    @Override
    public String toString() {
        return "Intervention{" + "id=" + id + ", messageEleve=" + messageEleve + ", date=" + date + ", lienVisio=" + lienVisio + ", duree=" + duree + ", bilanInterv=" + bilanInterv + ", evalEleve=" + evalEleve + ", matiere=" + matiere + ", eleve=" + eleve + ", intervenant=" + intervenant + '}';
    }
    
    
}
