package datamining;
import modelling.Variable;
import modelling.BooleanVariable;
import java.util.*;

public class AssociationRule {
    private final Set<BooleanVariable> premise;    
    private final Set<BooleanVariable> conclusion;
    private final float frequence;                 
    private final float confiance;                

    public AssociationRule(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, float frequence, float confiance) {
        this.premise = premise;
        this.conclusion = conclusion;
        this.frequence = frequence;
        this.confiance = confiance;
    }

    public Set<BooleanVariable> getPremise() {
        return premise;
    }

    public Set<BooleanVariable> getConclusion() {
        return conclusion;
    }

    public float getFrequency() {
        return frequence;
    }
    public float getConfidence() {
        return confiance;
    }
    @Override
    public String toString() {
        
        return "["+premise.toString()+" , "+conclusion.toString()+" , "+ this.frequence+" , "+ this.confiance+"]";
    }
}
    

