package planning;
import modelling.Variable;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Set;
import java.util.List;

public class DFSPlanner implements Planner{
	private Map<Variable,Object> étatinitial;
	private Set<Action> actions;
	private Goal but;
	private boolean noeudsactives = false;
    	private int noeudsexplores = 0;
	
	public DFSPlanner(Map<Variable,Object> étatinitial,Set<Action> actions,Goal but){
		this.étatinitial=étatinitial;
		this.actions=actions;
		this.but=but;
	}
	
	@Override
    public List<Action> plan() {
    	if (noeudsactives) {
            noeudsexplores = 0;
        }
        return dfs(étatinitial, new ArrayList<Action>(), new HashSet<Map<Variable,Object>>());
    }

    
    private List<Action> dfs(Map<Variable, Object> étatactuel, List<Action> planactuel, Set<Map<Variable, Object>> étatvisités) {
    	if (noeudsactives) {
            noeudsexplores++;
        }
        if (but.isSatisfiedBy(étatactuel)) {
            return planactuel; 
        }
        if (étatvisités.contains(étatactuel)) {
            return null;
        }
        étatvisités.add(étatactuel); 

        
        for (Action action : actions) {
            if (action.isApplicable(étatactuel)) {
                Map<Variable, Object> newetat = action.successor(étatactuel);
                List<Action> newplan = new ArrayList<>(planactuel);
                newplan.add(action);
                List<Action> resultat = dfs(newetat, newplan, étatvisités);
                if (resultat != null) {
                    return resultat;
                }
            }
        }
        return null;
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
