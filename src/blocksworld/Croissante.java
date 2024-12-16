package blocksworld;

import modelling.*;
import java.util.*;

public class Croissante extends BlocksWorldConstraints {
    public Croissante(int nbrBloc, int nbrPile) {
        super(nbrBloc, nbrPile);
    }
    public Set<Constraint> getConstraintCroissante() {
        Set<Constraint> lesconstraintes = new HashSet<>();

        //on récupére les variables "on"
        List<Variable> laListeDeON = getVariableOn();

        for (int i = 0; i < laListeDeON.size(); i++) {
            Variable v1 = laListeDeON.get(i);
            Set<Object> domaine = new HashSet<>();
            for (Object vlr : v1.getDomain()) {
                if (vlr instanceof Integer && (int) vlr < i) { // La valeur doit être inférieure à l'indice
                    domaine.add(vlr);
                }
            }
            UnaryConstraint unaire = new UnaryConstraint(v1, domaine);
            lesconstraintes.add(unaire);
        }
        return lesconstraintes;
    }
    public boolean croissanteValide(Map<Variable, Object> instanciation) {
        for (Constraint constraint : this.getConstraintCroissante()) {
            if (!constraint.isSatisfiedBy(instanciation)) {
                return false;
            }
        }
        return true;
    }
    @Override
    public Set<Constraint> getSetConstraint() {
        Set<Constraint> contraintes = super.getSetConstraint();
        contraintes.addAll(getConstraintCroissante());
        return contraintes;
    }
}
