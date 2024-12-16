package datamining;
import modelling.Variable;
import modelling.BooleanVariable;
import java.util.*;


public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner {
    private final BooleanDatabase database;

    // constructeur
    protected AbstractAssociationRuleMiner(BooleanDatabase database) {
        this.database = database;
    }

    // accesseur pour la base de données
    @Override
    public BooleanDatabase getDatabase() {
        return database;
    }
    public static float frequency(Set<BooleanVariable> items, Set<Itemset> itemsets) {
        for (Itemset itemset : itemsets) {
            if (itemset.getItems().equals(items)) {
                return itemset.getFrequency(); // retourne la fréquence si l'ensemble d'items est trouvé
            }
        }
        throw new IllegalArgumentException("L'ensemble d'items n'apparaît pas dans l'ensemble des itemsets fréquents.");
    }
    public static float confidence(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, Set<Itemset> itemsets) {
        Set<BooleanVariable> rule = new HashSet<>(premise); // on crée un ensemble pour la règle
        rule.addAll(conclusion); //on ajoute la conclusion à la règle

        float frequencyPremise = frequency(premise, itemsets); 
        float frequencyRule = frequency(rule, itemsets); 

        if (frequencyPremise == 0) {
            throw new IllegalArgumentException("la fréquence de la prémisse est nulle, la confiance ne peut pas être calculée.");
        }

        return frequencyRule / frequencyPremise; // calcul de la confiance
    }
}

