package cp;
import modelling.Variable;
import modelling.Constraint;
import java.util.*;

public class RandomValueHeuristic implements ValueHeuristic {
    private Random random;

    public RandomValueHeuristic(Random random) {
        this.random = random;
    }
    
    //on va ordonner les valeurs de façon aléatoire
    @Override
    public List<Object> ordering(Variable var, Set<Object> domaine) {
        List<Object> values = new ArrayList<>(domaine);
        Collections.shuffle(values, random);//on mélange aléatoirement la liste
        return values;//on va  retourner la liste des valeurs mélangées(aprés le shuffle)
    }
}
