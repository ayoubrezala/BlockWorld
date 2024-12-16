package blocksworld;

import modelling.*;
import java.util.*;

public class BlockWorldRegular extends BlocksWorldConstraints {

    public BlockWorldRegular(int nbrBloc, int nbrPile) {
        super(nbrBloc, nbrPile);
    }
    // on genere les contraintes régulières
    public Set<Constraint> getConstraintReguliere() {
        Set<Constraint> lesContraintes = new HashSet<>();
        int nbrBloc = getNbrBlocks(); 
        int nbrPile = getNbrPiles(); 
        List<Variable> laListeDeON = getVariableOn();

        for (int n = 0; n < nbrBloc; n++) { // on parcourt chaque bloc
            for (int m = 0; m < nbrBloc; m++) { // on parcourt chaque bloc encore
                if (n != m) { // on vérifie que ce soit pas le même bloc
                    int dfrnc = (2 * m) - n;
                    Set<Object> values = new HashSet<>();

                    // on ajoute les piles (-1 à -nbrPile)
                    for (int k = -nbrPile; k < 0; k++) {
                        values.add(k);
                    }
                    values.add(dfrnc);
                    lesContraintes.add(new Implication(
                        laListeDeON.get(n), 
                        Set.of(m), 
                        laListeDeON.get(m), 
                        values
                    ));
                }
            }
        }
        return lesContraintes;
    }
    // on vérifie si les contraintes régulières sont valides
    public boolean reguliereValide(Map<Variable, Object> instanciation) {
        if (!this.constraintValide(instanciation)) {
            return false; // si les contraintes héritées sont pas valides
        }
        for (Constraint constraint : this.getConstraintReguliere()) {
            if (!constraint.isSatisfiedBy(instanciation)) {
                return false; // si une contrainte régulière échoue
            }
        }
        return true; 
    }
    // on combine les contraintes héritées et régulières
    @Override
    public Set<Constraint> getSetConstraint() {
        Set<Constraint> contraintes = super.getSetConstraint();
        contraintes.addAll(getConstraintReguliere());
        return contraintes;
    }
    public static void main(String[] args) {
        BlockWorldRegular world = new BlockWorldRegular(2, 1);
        System.out.println(world.getSetConstraint());
    }
}
