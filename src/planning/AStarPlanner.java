package planning;
import modelling.Variable;
import java.util.*;

public class AStarPlanner implements Planner{
	private Map<Variable,Object> étatinitial;
	private Set<Action> actions;
	private Goal but;
	private Heuristic heuristic;
	private boolean noeudsactives = false;
    	private int noeudsexplores = 0;
    
    
	public AStarPlanner(Map<Variable,Object> étatinitial,Set<Action> actions,Goal but,Heuristic heuristic){
		this.étatinitial=étatinitial;
		this.actions=actions;
		this.but=but;
		this.heuristic=heuristic;
	}
	
	
    @Override
 public List<Action> plan() {
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Double> distance = new HashMap<>();
        Map<Map<Variable, Object>, Double> value = new HashMap<>();
        Set<Map<Variable, Object>> open = new HashSet<>();

        
        Map<Variable, Object> copie = new HashMap<>(étatinitial);
        father.put(copie, null); 
        distance.put(copie, 0.0); 
        value.put(copie,(double) heuristic.estimate(copie)); 
        open.add(copie);

        
        while (!open.isEmpty()) {
            if (noeudsactives) {
                noeudsexplores++;
            }
            Map<Variable, Object> étatactuel = argmin(open, value);
            if (but.isSatisfiedBy(étatactuel)) {
                return reconstructPlan(étatactuel, father, plan);
            }
            open.remove(étatactuel);
            for (Action action : actions) {
                if (action.isApplicable(étatactuel)) {
                    Map<Variable, Object> next = action.successor(étatactuel);
                    if (!distance.containsKey(next)) {
                        distance.put(next, Double.POSITIVE_INFINITY);
                    }
                    if (distance.get(étatactuel) + action.getCost() < distance.get(next)) {
                        distance.put(next, distance.get(étatactuel) + action.getCost());
                        value.put(next, distance.get(étatactuel) + action.getCost() + heuristic.estimate(next));
                        father.put(next, étatactuel);
                        plan.put(next, action);
                        open.add(next);
                    }
                }
            }
        }
        return null;
    }
   private Map<Variable, Object> argmin(Set<Map<Variable, Object>> open, Map<Map<Variable, Object>, Double> value) {
    Map<Variable, Object> minétat = null;
    double minValue = Double.POSITIVE_INFINITY;

    for (Map<Variable, Object> state : open) {
        if (value.containsKey(state)) {
            double stateValue = value.get(state);
            if (stateValue < minValue) {
                minValue = stateValue;
                minétat = state;
            }
        }
    }
    return minétat;
}
    private List<Action> reconstructPlan(Map<Variable, Object> but, 
                                         Map<Map<Variable, Object>, Map<Variable, Object>> father, 
                                         Map<Map<Variable, Object>, Action> plan) {
        List<Action> liste = new ArrayList<>();
        Map<Variable, Object> étatactuel = but;
        while (father.get(étatactuel) != null) {
            Action action = plan.get(étatactuel);
            liste.add(action);
            étatactuel = father.get(étatactuel);
        }
        Collections.reverse(liste);
        return liste;
    }
    	public void activateNodeCount(boolean activate) {
        this.noeudsactives = activate;
    }
    public int getNodesExplored() {
        return noeudsexplores;
    }
    
	@Override
	public Map<Variable,Object> getInitialState(){
		return this.étatinitial;
	}
	@Override
	public Set<Action> getActions(){
		return this.actions;
        }
	
	@Override
	public Goal getGoal(){
		return this.but;
	}
}
