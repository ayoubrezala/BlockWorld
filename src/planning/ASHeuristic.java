package planning;

import java.util.Map;

import modelling.Variable;
//class ASHeuristic qui implemente l'interface Heuristic
public class ASHeuristic implements Heuristic {
    
    protected Map<Variable, Object> goal; //notre but

    //constructeur
    public ASHeuristic(Map<Variable, Object> goal){
        this.goal = goal;
    }

    //methode retournant notre heuristic(elle retourne 1 si on arrive a l'etat final ou 0 sinon)
    @Override
    public float estimate(Map<Variable, Object> etat) {
       return this.goal.equals(etat) ? 0 : 1;
        
    }
    
}