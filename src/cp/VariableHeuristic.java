package cp;
import modelling.Variable;
import modelling.Constraint;
import java.util.*;

public interface VariableHeuristic {
    
    Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domaines);
}
