package cp;

import java.util.*;
import modelling.*;


public class MACSolver extends AbstractSolver {
    public MACSolver(Set<Variable> variables , Set<Constraint> contraites){
        super(variables,contraites);
    }

	//on a utilse cette methode auxilliare pour appliquer l'algorithme MAC
    public Map<Variable, Object> MAC(Map<Variable, Object> instantiationP, Set<Variable> ensembleVariables, Map<Variable, Set<Object>> ED) {
    
    	// on crée une instance de la class ArcConsistency
        ArcConsistency arcConsistency = new ArcConsistency(contraintes);

	//si l'ensemble des variables est vide on va retourner l'instanciation partielle
        if (ensembleVariables.isEmpty()) {
            return instantiationP;
        } 
       
        else {
            if (!arcConsistency.ac1(ED)) {
                return null;//retourne null si au moins un domaine est vide
            }
		
	   
            Variable xi = ensembleVariables.iterator().next();
            ensembleVariables.remove(xi);
            for (Object vi : ED.get(xi)) {
                Map<Variable, Object> N = new HashMap<>(instantiationP);
                N.put(xi, vi);
                if (isConsistent(N)) {
                    Map<Variable, Object> result = MAC(N, ensembleVariables, ED);
                    //on va retourne le resultat s'il nest pas null
                    if (result != null) {
                        return result;
                    }
                }
            }
            ensembleVariables.add(xi);
            return null;
        }
    }
    
    //ceete methode est la méthode principale qui utilise la methode MAC qui nous permet de résoudre
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
