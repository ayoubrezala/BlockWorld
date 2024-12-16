package planning;
import modelling.Variable;
import java.util.*;

public class BFSPlanner implements Planner{
	private Map<Variable,Object> étatinitial;
	private Set<Action> actions;
	private Goal but;
	private boolean noeudsactives = false;
    	private int noeudsexplores = 0;
	
	public BFSPlanner(Map<Variable,Object> étatinitial,Set<Action> actions,Goal but){
		this.étatinitial=étatinitial;
		this.actions=actions;
		this.but=but;
	}
	
	
    @Override
public List<Action> plan() {
	
            noeudsexplores = 0;
    Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
    Map<Map<Variable, Object>, Action> plan = new HashMap<>();
    Set<Map<Variable, Object>> closed = new HashSet<>();
    Queue<Map<Variable, Object>> open = new LinkedList<>();

    
    Map<Variable, Object> initialState = new HashMap<>(étatinitial);
    closed.add(étatinitial);
    open.add(étatinitial);
    father.put(étatinitial, null);

    
    if (but.isSatisfiedBy(étatinitial)) {
        return new ArrayList<>();
    }
    while (!open.isEmpty()) {
    	if (noeudsactives) {
                noeudsexplores++;
            }
        Map<Variable, Object> instantiation = open.poll();
        closed.add(instantiation); 
        for (Action action : actions) {
            if (action.isApplicable(instantiation)) {
                Map<Variable, Object> next = action.successor(instantiation);
                if (!closed.contains(next) && !open.contains(next)) {
                    father.put(next, instantiation);
                    plan.put(next, action);
                    if (but.isSatisfiedBy(next)) {
                        return reconstructPlan(next, father, plan);
                    } else {
                        open.add(next);
                    }
                }
            }
        }
    }
    return null;
}

    
    private List<Action> reconstructPlan(Map<Variable, Object> goal, Map<Map<Variable, Object>, Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> plan) {
    List<Action> liste = new ArrayList<>();
    Map<Variable, Object> étatactuel = goal;

    
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
