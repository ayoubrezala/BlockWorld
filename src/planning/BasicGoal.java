package planning;
import modelling.Variable;
import java.util.HashMap;
import java.util.Map;

public class BasicGoal implements Goal{
	private Map<Variable,Object> but;
	
	
	public BasicGoal(Map<Variable,Object> but){
		this.but=but;
	}
	
	
	@Override
	public boolean isSatisfiedBy(Map<Variable,Object> état){
		for(Map.Entry<Variable,Object> entry:but.entrySet()){
			Variable v=entry.getKey();// On récupère la variable
			Object valeur=entry.getValue();// Récupère la variable
			Object reel=état.get(v);
			if(!état.containsKey(v)||!reel.equals(valeur)){
				return false;//le but n'et pas atteint
			}
		}
		return true;//les conditions sont satisfaites
	}
}
