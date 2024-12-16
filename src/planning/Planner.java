package planning;
import modelling.Variable;
import java.util.Map;
import java.util.Set;
import java.util.List;
public interface Planner{
	List<Action> plan();
	Map<Variable,Object> getInitialState();
	Set<Action> getActions();
	Goal getGoal();
}
