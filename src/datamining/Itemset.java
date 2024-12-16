package datamining;
import modelling.Variable;
import modelling.BooleanVariable;
import java.util.*;



public class Itemset {
    private Set<BooleanVariable> items;
    private float frequence;
    
    public Itemset(Set<BooleanVariable> items, float frequence) {
        //la fréquence est entendue entre 0,0 pour un motif non supporté, et 1,0 pour un motif supporté par toutes les transactions)
        if (frequence < 0.0 || frequence > 1.0) {
            throw new IllegalArgumentException("La fréquence doit être comprise entre 0.0 et 1.0");
        }
        this.items = items;
        this.frequence = frequence;
    }
    
    public Set<BooleanVariable> getItems() {
        return items;
    }

    public float getFrequency() {
        return frequence;
    }
    @Override
    public String toString() {
        return this.items.toString();
    }
} 
