package cp;
import modelling.Variable;
import modelling.Constraint;
import java.util.*;

public interface ValueHeuristic {
    List<Object> ordering(Variable var, Set<Object> domain);
}
