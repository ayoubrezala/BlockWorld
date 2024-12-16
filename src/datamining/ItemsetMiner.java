 package datamining;
import modelling.Variable;
import modelling.BooleanVariable;
import java.util.*;

public interface ItemsetMiner {
    public BooleanDatabase getDatabase(); //cette methode retourne une BooleanDatabase
    public Set<Itemset> extract(float min);//cette méthode retourne tous les itemsets avec une fréquence d'au moins la valeur donnée en argument
    
}


