package planning;
import modelling.Variable;
import java.util.*;

public class DijkstraPlanner implements Planner{
	private Map<Variable,Object> étatinitial;
	private Set<Action> actions;
	private Goal but;
	private boolean noeudsactives = false;
    	private int noeudsexplores = 0;
	
	public DijkstraPlanner(Map<Variable,Object> étatinitial,Set<Action> actions,Goal but){
		this.étatinitial=étatinitial;
		this.actions=actions;
		this.but=but;
	}
	
	
    @Override
public List<Action> plan() {
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Double> distance = new HashMap<>();
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();

        


        Set<Map<Variable, Object>> open = new HashSet<>();
        Set<Map<Variable, Object>> goals = new HashSet<>();
        Map<Variable, Object> copie = new HashMap<>(étatinitial);
        father.put(copie, null);
        distance.put(copie, 0.0);
        open.add(copie);
        while (!open.isEmpty()) {
            if (noeudsactives) {
                noeudsexplores++;
            }
            Map<Variable, Object> étatactuel = argmin(open, distance);
            open.remove(étatactuel);
            if (but.isSatisfiedBy(étatactuel)) {
                goals.add(étatactuel);
            }
            for (Action action : actions) {
                if (action.isApplicable(étatactuel)) {
                    Map<Variable, Object> next= action.successor(étatactuel);
                   if (!distance.containsKey(next)) {
                        distance.put(next, Double.POSITIVE_INFINITY);
                    }
                    if (distance.get(étatactuel) + action.getCost() < distance.get(next)) {
                        distance.put(next, distance.get(étatactuel) + action.getCost());
                        father.put(next, étatactuel);
                        plan.put(next, action);
                        open.add(next);
                    }
                }
            }
        }
        if (goals.isEmpty()) {
            return null;
        }
        return getDijkstraPlan(father, plan, goals, distance);
    }
   private Map<Variable, Object> argmin(Set<Map<Variable, Object>> open, Map<Map<Variable, Object>, Double> distance) {
    if (open.isEmpty()) {
        return null;
    }

    Map<Variable, Object> minétat = null;
    double minDistance = Double.POSITIVE_INFINITY;

    
    for (Map<Variable, Object> state : open) {
       
        double distanceétat = distance.get(state);

       
        if (distanceétat < minDistance) {
            minDistance = distanceétat;
            minétat = state;
        }
    }

   
    return minétat;


}
    private List<Action> getDijkstraPlan(Map<Map<Variable, Object>, Map<Variable, Object>> father, 
                                     Map<Map<Variable, Object>, Action> plan, 
                                     Set<Map<Variable, Object>> goals, 
                                     Map<Map<Variable, Object>, Double> distance) {
    
    Map<Variable, Object> but = argmin(goals, distance);

   
    if (but == null) {
        return null;
    }
    List<Action> actionList = new ArrayList<>();
    Map<Variable, Object> étatactuel = but;

    while (father.get(étatactuel) != null) {
        Action action = plan.get(étatactuel);
        actionList.add(action);
        étatactuel = father.get(étatactuel);
    }
    Collections.reverse(actionList);
    return actionList;
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
