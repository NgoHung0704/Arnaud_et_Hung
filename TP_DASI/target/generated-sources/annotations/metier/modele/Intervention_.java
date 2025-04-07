package metier.modele;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import metier.modele.Eleve;
import metier.modele.Intervenant;
import metier.modele.Matiere;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-04-07T22:39:50")
@StaticMetamodel(Intervention.class)
public class Intervention_ { 

    public static volatile SingularAttribute<Intervention, Date> date;
    public static volatile SingularAttribute<Intervention, Integer> evalEleve;
    public static volatile SingularAttribute<Intervention, String> lienVisio;
    public static volatile SingularAttribute<Intervention, String> messageEleve;
    public static volatile SingularAttribute<Intervention, Intervenant> intervenantEnCours;
    public static volatile SingularAttribute<Intervention, Integer> duree;
    public static volatile SingularAttribute<Intervention, Intervenant> intervenantHistorique;
    public static volatile SingularAttribute<Intervention, Long> id;
    public static volatile SingularAttribute<Intervention, String> bilanInterv;
    public static volatile SingularAttribute<Intervention, Eleve> eleve;
    public static volatile SingularAttribute<Intervention, Matiere> matiere;

}