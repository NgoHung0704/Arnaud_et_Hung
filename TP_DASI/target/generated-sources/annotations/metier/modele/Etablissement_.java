package metier.modele;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import metier.modele.Eleve;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2025-04-09T20:15:47")
@StaticMetamodel(Etablissement.class)
public class Etablissement_ { 

    public static volatile SingularAttribute<Etablissement, String> nomEtablissement;
    public static volatile SingularAttribute<Etablissement, Float> lng;
    public static volatile SingularAttribute<Etablissement, String> codeEtablissement;
    public static volatile SingularAttribute<Etablissement, Long> id;
    public static volatile SingularAttribute<Etablissement, String> ips;
    public static volatile ListAttribute<Etablissement, Eleve> eleves;
    public static volatile SingularAttribute<Etablissement, Float> lat;

}