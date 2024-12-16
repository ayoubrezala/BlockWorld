package cp;
import modelling.Variable;
import modelling.Constraint;
import java.util.*;

public class DomainSizeVariableHeuristic implements VariableHeuristic {
    private boolean prefereGrand;     
    public DomainSizeVariableHeuristic(boolean prefereGrand) {
        this.prefereGrand = prefereGrand;
    }

    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains) {
        Variable meilleureVariable = null;
        int meilleureTaille;
        if (prefereGrand) {
            meilleureTaille = Integer.MIN_VALUE;
        } else {
            meilleureTaille = Integer.MAX_VALUE;
        }

        // on parcour toutes les variables et leurs domaines
        for (Variable var : variables) {
            int tailleDomaine = domains.get(var).size();
            if (prefereGrand && tailleDomaine > meilleureTaille) {
                meilleureVariable = var;
                meilleureTaille = tailleDomaine;
            } else if (!prefereGrand && tailleDomaine < meilleureTaille) {
                meilleureVariable = var;
                meilleureTaille = tailleDomaine;
            }
        }
        return meilleureVariable;
    }
}

