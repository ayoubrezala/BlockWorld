package blocksworld;

import java.util.Map;
import modelling.*;
import planning.*;
//cette heursistque est pour compter la distance totale que chaque bloc doit parcourir pour atteindre sa position cible
public class BlocksTotalDistanceHeuristic implements Heuristic {
    private Map<Variable, Object> but;
    public BlocksTotalDistanceHeuristic(Map<Variable, Object> but) {
        this.but = but;
    }
    @Override
    public float estimate(Map<Variable, Object> etat) {
        float dist = 0;
        for (Variable v : etat.keySet()) {
            Object posActuelle = etat.get(v);
            Object posCible = but.get(v);
            if (posActuelle != null && posCible != null && posActuelle instanceof Integer && posCible instanceof Integer) {
                dist += Math.abs((Integer) posCible - (Integer) posCible);
            }
        }
        return dist;
    }
}

