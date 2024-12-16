package modelling;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DifferenceConstraint implements Constraint {
    private Variable v1;
    private Variable v2;

    public DifferenceConstraint(Variable v1, Variable v2) {
        this.v1=v1;
        this.v2=v2;
    }

    @Override
    public Set<Variable> getScope() {
        Set<Variable> scope = new HashSet<>();
        scope.add(v1);
        scope.add(v2);
        return scope;
    }
    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> ensemble) {
        for (Variable variable : getScope()) {
            if (!ensemble.containsKey(variable)) {
                throw new IllegalArgumentException("il faut que toutes les variables du scope de la contraite auront une valeur");
            }
        }

        Object a = ensemble.get(v1);
        Object b = ensemble.get(v2);
        return !a.equals(b);
    }

    @Override
    public String toString() {
        return "DifferenceConstraint :"+"v1=" + v1.getName() +", v2=" + v2.getName();
    }
}



