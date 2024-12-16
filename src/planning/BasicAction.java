package planning;
import modelling.Variable;
import java.util.HashMap;
import java.util.Map;

public class BasicAction implements Action{
	private Map<Variable,Object> precondition;
	private Map<Variable,Object> effet;
	private int cout;
	
	
	public BasicAction(Map<Variable,Object> precondition,Map<Variable,Object> effet ,int cout){
		this.precondition=precondition;
		this.effet=effet;
		this.cout=cout;
	}
	
	
	@Override
	public boolean isApplicable(Map<Variable,Object> état){
		for(Map.Entry<Variable,Object> entry:precondition.entrySet()){
			Variable v=entry.getKey();// on récupére la variable de la précondition
			Object valeur=entry.getValue();//On récupère la valeur
			Object reel=état.get(v);
			if(!état.containsKey(v)||!reel.equals(valeur)){
				return false;
			}
		}
		return true;
	}
	@Override
	public Map<Variable,Object> successor(Map<Variable,Object> état){
		Map<Variable,Object> successeur=new HashMap<>(état);
		for (Map.Entry<Variable, Object> entry : effet.entrySet()) {
            		successeur.put(entry.getKey(), entry.getValue());
        	}
        	return successeur;//elle retourne le nouvel état
        }
	
	@Override
	public int getCost(){
		return cout;//elle retourne le cout
	}
	@Override
public String toString() {
    // on concacténe les préconditions
    String preconditionStr = "{";
  

    for (Map.Entry<Variable, Object> entry : precondition.entrySet()) {
       
        preconditionStr += entry.getKey().getName() + " = " + entry.getValue();
       
    }
    preconditionStr += "}";

    // on concacténe les effets
    String effectStr = "{";
    

    for (Map.Entry<Variable, Object> entry : effet.entrySet()) {
      
        effectStr += entry.getKey().getName() + " = " + entry.getValue();
        
    }
    effectStr += "}";

    //c'est le résultat final
    return "{" +
            "precondition=" + preconditionStr +
            ", effect=" + effectStr +
            ", cost=" + cout +
            '}';
}

}
