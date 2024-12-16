package cp;

import java.util.*;
import modelling.*;

public class HeuristicMACSolver extends AbstractSolver {

    private VariableHeuristic variableHeuristic; 
    private ValueHeuristic valueHeuristic;

    public HeuristicMACSolver(Set<Variable> variables, Set<Constraint> contraites, VariableHeuristic variableHeuristic, ValueHeuristic valueHeuristic) {
        super(variables, contraites);
        this.variableHeuristic = variableHeuristic;
        this.valueHeuristic = valueHeuristic;
    }

    //on a utilse cette methode pour l'algorithme MAC
    public Map<Variable, Object> MAC(Map<Variable, Object> instantiationP, Set<Variable> ensembleVariables, Map<Variable, Set<Object>> ED) {
        ArcConsistency arcConsistency = new ArcConsistency(contraintes);

        //si l'ensemble des variables est vide on va retourner l'instanciation partielle
        if (ensembleVariables.isEmpty()) {
            return instantiationP;
        } else {
            if (!arcConsistency.ac1(ED)) {
                return null;
            }

            //on selectionne la meilleure variable et on prend en compte l'heuristique de variable.
            Variable xi = variableHeuristic.best(ensembleVariables, ED);
            ensembleVariables.remove(xi);
            
            
            for (Object vi : valueHeuristic.ordering(xi, ED.get(xi))) {
                Map<Variable, Object> N = new HashMap<>(instantiationP);
                N.put(xi, vi);

                
                if (isConsistent(N)) {
                    Map<Variable, Object> result = MAC(N, ensembleVariables, ED);
                    if (result != null) {
                        return result;
                    }
                }
            }
            ensembleVariables.add(xi);
            return null;
        }
    }

    @Override
    public Map<Variable, Object> solve() {
        Map<Variable, Set<Object>> ED = new HashMap<>();
        for (Variable variable : variables) {
            ED.put(variable, new HashSet<>(variable.getDomain()));
        }
        //on fait appel de la methode MAC pour appliquer l'algorithme MAC
        return MAC(new HashMap<>(), new HashSet<>(variables), ED);
    }

}
