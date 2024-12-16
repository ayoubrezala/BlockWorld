                                                                                                                                                                                                         package cp;
import modelling.Variable;
import modelling.Constraint;
import java.util.Map;
import java.util.Set;

public abstract class AbstractSolver implements Solver {
	protected Set<Variable> variables;
	protected Set<Constraint> contraintes;
	
	
	public AbstractSolver (Set<Variable> variables , Set<Constraint> contraintes){
		this.variables=variables;
		this.contraintes=contraintes;
	}
	
	
    	//la méthode retourne un boolean , vrai si l'affectation satisfait toutes les contraintes , faux sinon
    	public boolean isConsistent(Map<Variable,Object> affectation){
    		for (Constraint contrainte : contraintes) {
            		if(affectation.keySet().containsAll(contrainte.getScope())){
            			if (!contrainte.isSatisfiedBy(affectation)) {
                			return false; 
            			}
        		}
        	}
        	return true;
   	 }	
}
