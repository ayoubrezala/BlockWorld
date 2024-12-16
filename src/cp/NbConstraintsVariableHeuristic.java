package cp;
import modelling.Variable;
import modelling.Constraint;
import java.util.*;

public class NbConstraintsVariableHeuristic implements VariableHeuristic {
    private Set<Constraint> contraintes;
    private boolean preferePlusDeContraintes;

    public NbConstraintsVariableHeuristic(Set<Constraint> contraintes, boolean preferePlusDeContraintes) {
        this.contraintes = contraintes;
        this.preferePlusDeContraintes = preferePlusDeContraintes;
    }

    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domaines) {
        Variable meilleureVariable = null;
        int meilleurCompte;
        if (preferePlusDeContraintes) {
            meilleurCompte = Integer.MIN_VALUE;
        } else {
            meilleurCompte = Integer.MAX_VALUE;
        }


        // on calcule le nombre de contraintes associées à chaque variable
        for (Variable var : variables) {
            int count = countConstraints(var);
            // on va vérifier est ce que le nombre de contraintes actuel est meilleur que le meilleur compte ,et on va prendre en compte la préférence
            if (preferePlusDeContraintes && count > meilleurCompte) {
                meilleureVariable = var;
                meilleurCompte = count;
            } else if (!preferePlusDeContraintes && count < meilleurCompte) {
                meilleureVariable = var;
                meilleurCompte = count;
            }
        }
        return meilleureVariable;
    }

    //on compte le nombre de contraintes associé a une variable
    private int countConstraints(Variable var) {
        int count = 0;
        for (Constraint contrainte : contraintes) {
            if (contrainte.getScope().contains(var)) {
                count++;
            }
        }
        return count;
    }
}
