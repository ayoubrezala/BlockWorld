package blocksworld;

import java.util.*;
import modelling.*;

public class BlocksWorldConstraints extends BlocksWorld {
    private Set<Constraint> lesContraintes;

    public BlocksWorldConstraints(int nbrBlocks, int nbrPiles) {
        super(nbrBlocks, nbrPiles);
        this.lesContraintes = new HashSet<>();
        generateConstraints(); 
    }
    private void generateConstraints() {
        List<Variable> listDeON = getVariableOn();
        List<BooleanVariable> fixedList = getVariableFixed();
        List<BooleanVariable> freeList = getVariableFree();

        // 1. on impose que onb ==/== onb′ pour chaque couple de blocs
        for (int i = 0; i < listDeON.size(); i++) {
            for (int j = i + 1; j < listDeON.size(); j++) {
                DifferenceConstraint diff = new DifferenceConstraint(listDeON.get(i), listDeON.get(j));
                lesContraintes.add(diff);
            }
        }
        // 2. onb = b′ => fixedb′ = true
        for (int i = 0; i < listDeON.size(); i++) {
            for (int j = 0; j < listDeON.size(); j++) {
                if (i != j) {
                    Implication imp = new Implication(
                        listDeON.get(i),
                        Set.of(j), // valeur pour b′
                        fixedList.get(j),
                        Set.of(true) // valeur pour fixedb′
                    );
                    lesContraintes.add(imp);
                }
            }
        }
        // 3.onb = −(p + 1) => freep = false
        for (int i = 0; i < listDeON.size(); i++) {
            for (int p = 0; p < freeList.size(); p++) {
                Implication imp = new Implication(
                    listDeON.get(i),
                    Set.of(-(p + 1)), // valeur pour la pile
                    freeList.get(p),
                    Set.of(false) // valeur pour freep
                );
                lesContraintes.add(imp);
            }
        }
    }
    public boolean constraintValide(Map<Variable, Object> config) {
        for (Constraint contrainte : lesContraintes) {
            if (!contrainte.isSatisfiedBy(config)) {
                return false;
            }
        }
        return true;
    }
    public Set<Constraint> getSetConstraint() {
        return lesContraintes;
    }
}

