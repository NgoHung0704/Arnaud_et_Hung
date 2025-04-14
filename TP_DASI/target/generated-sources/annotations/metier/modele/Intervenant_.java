package metier.modele;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import metier.modele.Intervention;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-04-14T19:42:41")
@StaticMetamodel(Intervenant.class)
public class Intervenant_ { 

    public static volatile SingularAttribute<Intervenant, String> motDePasse;
    public static volatile ListAttribute<Intervenant, Intervention> historiqueInterventions;
    public static volatile SingularAttribute<Intervenant, Long> id;
    public static volatile SingularAttribute<Intervenant, String> login;
    public static volatile SingularAttribute<Intervenant, String> nom;
    public static volatile SingularAttribute<Intervenant, String> prenom;
    public static volatile SingularAttribute<Intervenant, String> numTel;
    public static volatile SingularAttribute<Intervenant, Integer> niveauMin;
    public static volatile SingularAttribute<Intervenant, Integer> niveauMax;
    public static volatile SingularAttribute<Intervenant, Intervention> enCours;

}