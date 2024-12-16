package blocksworld;

import java.util.Map;
import modelling.*;
import planning.*;
public class BlocksHeuristic implements Heuristic {
//cette heuristique est pour calculer le nombre de blocs qui sont dans une mauvaise position
    protected Map<Variable, Object> but;//l'état ou on veut arriver
    public BlocksHeuristic(Map<Variable, Object> but) {
        this.but = but;
    }
    @Override
    public float estimate(Map<Variable, Object> etat) {
        int nbrMauvaisesPosition = 0;
        for (Variable v : etat.keySet()) {
            Object vlrActuelle = etat.get(v);
            Object vlrBut = but.get(v);

            // etat=/=but => nbrMauvaisesPosition+1
            if (vlrActuelle != null && vlrBut != null && !vlrActuelle.equals(vlrBut)) {
                nbrMauvaisesPosition++;
            }
        }
        return nbrMauvaisesPosition;
    }
}

