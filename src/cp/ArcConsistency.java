package cp;
import modelling.Variable;
import modelling.Constraint;
import java.util.*;

public class ArcConsistency {

    private Set<Constraint> contraintes;

    public ArcConsistency(Set<Constraint> contraintes) {
        for (Constraint contrainte : contraintes) {
            if (contrainte.getScope().size() > 2) {
                throw new IllegalArgumentException("Il faut que les contraites soient unaires ou binaires");
            }
        }
        this.contraintes = contraintes;
    }

    public boolean enforceNodeConsistency(Map<Variable, Set<Object>> domaines) {
    boolean consistent = true;
    for (Variable var : domaines.keySet()) {
        Set<Object> domain = new HashSet<>(domaines.get(var));
        for (Object value : domain) {
            boolean satisfied = true;
            
            for (Constraint contrainte : contraintes) {
                // on vérifie si la contrante est unaire et est ce qu'elle concerne la variable actuelle
                if (contrainte.getScope().size() == 1 && contrainte.getScope().contains(var)) {
                    
                    Map<Variable, Object> assignment = Collections.singletonMap(var, value);
                    if (!contrainte.isSatisfiedBy(assignment)) {
                        satisfied = false;
                        break;
                    }
                }
            }
            // et si la valeur ne satisfait pas les contrainte unaires, on va la supprimer du domaine
            if (!satisfied) {
                domaines.get(var).remove(value);
            }
        }
        // si le domaine de la variable est vide, donc le problème est incohérent
        if (domaines.get(var).isEmpty()) {
            consistent = false;
        }
    }
    return consistent;
}


    public boolean revise(Variable v1 , Set<Object> domaine1 , Variable v2 , Set<Object> domaine2){
        
        boolean del = false; // c'est pour savoir si une valeur est supprimeée ou pas
        
       
        for (Object valeur1 : new HashSet<>(domaine1)) {
            
            boolean viable = false;
            
            for (Object valeur2 : new HashSet<>(domaine2)) {
                boolean toutSatisfait = true;
                //parcourt de chaque contrainte
                for (Constraint contrainte : this.contraintes) {
                    Set<Variable> scop = contrainte.getScope();
                    
                    
                    if (scop.size() == 2 && scop.contains(v1) && scop.contains(v2)) {
                    
                    //on fait l'instanciation avec les valeurs actuelles
                        Map<Variable,Object> n = new HashMap<>();
                        n.put(v1,valeur1);
                        n.put(v2,valeur2);
                        
                        //on verifie si l'instantiation ne satisfait pas la contrainte
                        if(!contrainte.isSatisfiedBy(n)){
                            toutSatisfait = false;
                            break;
                        }
                    }
                }
                                
                if (toutSatisfait) {
                    viable = true;
                    break;
                }
            }
                        
            if (!viable) {
               domaine1.remove(valeur1);
                del = true;  
            }
        }
        return del;
    }


public boolean ac1(Map<Variable, Set<Object>> domaines) {
    if (!enforceNodeConsistency(domaines)) {
        return false; 
    }

    boolean change;
    do {
        change = false;
        
        for (Variable xi : domaines.keySet()) {
            for (Variable xj : domaines.keySet()) {
                if (!xi.equals(xj)) {
                    
                    if (revise(xi, domaines.get(xi), xj, domaines.get(xj))) {
                        change = true;
                    }
                }
            }
        }
    } while (change); // Tant qu'il y a des changements, on continue

    // on fait la vérification de l'état des domaines pour chaque variable
    for (Variable x : domaines.keySet()) {
        if (domaines.get(x).isEmpty()) {
            return false; 
        }
    }
    return true; 
}



}

