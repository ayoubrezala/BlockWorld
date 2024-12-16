package planning;
import modelling.Variable;
import java.util.Map;

public interface Action{
	boolean isApplicable(Map<Variable,Object> état);
	Map<Variable,Object> successor(Map<Variable,Object> état);
	int getCost();
}
