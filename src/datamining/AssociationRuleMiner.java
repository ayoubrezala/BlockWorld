package datamining;
import modelling.Variable;
import modelling.BooleanVariable;
import java.util.*;

public interface AssociationRuleMiner {
    
    BooleanDatabase getDatabase();// methode retournant une instance de BooleanDatabase
    Set<AssociationRule> extract(float frequence, float confiance);
}

