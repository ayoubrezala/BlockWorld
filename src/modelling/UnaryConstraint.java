package modelling;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UnaryConstraint implements Constraint {
    private Variable v;
    private Set<Object> s;


    public UnaryConstraint(Variable v,Set<Object> s) {
        this.v=v;
        this.s=s;
    }

    @Override
    public Set<Variable> getScope() {
        Set<Variable> scope = new HashSet<>();
        scope.add(v);
        return scope;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> ensemble) {
        for (Variable variable : getScope()) {
            if (!ensemble.containsKey(variable)) {
                throw new IllegalArgumentException("il faut que toutes les variables du scope de la contraite auront une valeur");
            }
        }

        Object a = ensemble.get(v);
        return s.contains(a);
    }

    @Override
    public String toString() {
        return "UnaryConstraint :"+"v=" + v.getName();
    }
}



