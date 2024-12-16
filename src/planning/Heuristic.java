package planning;
import modelling.Variable;
import java.util.Map;
import java.util.Set;
import java.util.List;
public interface Heuristic{
	float estimate(Map<Variable, Object> état);
}
