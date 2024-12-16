package cp;
import modelling.Variable;
import modelling.Constraint;
import java.util.*;

public class BacktrackSolver extends AbstractSolver{
	
	public BacktrackSolver(Set<Variable> variables, Set<Constraint> contraintes) {
        super(variables, contraintes);
    }
    
    //cette methode auxiliaure implemente l'algorithme du backtracking
    public Map<Variable, Object> solve() {
        Map<Variable, Object> solutionPartielle = new HashMap<>();
        List<Variable> variableNonInstanciées = new ArrayList<>(variables);
        return backtrack(solutionPartielle, variableNonInstanciées);
    }
     private Map<Variable, Object> backtrack(Map<Variable, Object> solutionPartielle, List<Variable> variableNonInstanciées) {
     
     	//on vérifie si l'ensemble des variables est vide
        if (variableNonInstanciées.isEmpty()) {
            return solutionPartielle;
        }
        Variable variable = variableNonInstanciées.remove(0);
        for (Object value : variable.getDomain()) {
            solutionPartielle.put(variable, value);
            if (isConsistent(solutionPartielle)) {
                 Map<Variable, Object> result = backtrack(solutionPartielle, variableNonInstanciées);
                if (result != null) {
                    return result;  
                }
            }

            solutionPartielle.remove(variable);
        }

        variableNonInstanciées.add(0, variable);
        return null; 
    }
}
