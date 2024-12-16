package modelling;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Implication implements Constraint {
    private final Variable v1;
    private final Set<Object> s1;
    private final Variable v2;
    private final Set<Object> s2;

    // Constructeur
    public Implication(Variable v1, Set<Object> s1, Variable v2, Set<Object> s2) {
        if (v1 == null || v2 == null) {
            throw new IllegalArgumentException("Les variables v1 et v2 ne peuvent pas être nulles.");
        }
        if (s1 == null || s2 == null) {
            throw new IllegalArgumentException("Les ensembles s1 et s2 ne peuvent pas être nulls.");
        }
        this.v1 = v1;
        this.s1 = s1;
        this.v2 = v2;
        this.s2 = s2;
    }

    @Override
    public Set<Variable> getScope() {
        Set<Variable> scope = new HashSet<>();
        scope.add(v1);
        scope.add(v2);
        return scope;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> ensemble) {
        // Vérifie que toutes les variables du scope ont une valeur
        for (Variable variable : getScope()) {
            if (!ensemble.containsKey(variable)) {
                throw new IllegalArgumentException(
                    "Toutes les variables du scope de la contrainte doivent avoir une valeur."
                );
            }
        }

        // Récupère les valeurs associées à v1 et v2
        Object valeurV1 = ensemble.get(v1);
        Object valeurV2 = ensemble.get(v2);

        // Logique de l'implication
        if (!s1.contains(valeurV1)) {
            // Si la valeur de v1 n'est pas dans s1, la contrainte est satisfaite
            return true;
        }
        // Sinon, la valeur de v2 doit être dans s2
        return s2.contains(valeurV2);
    }

    @Override
    public String toString() {
        return "Implication : " + "v1 = " + v1.getName() + ", v2 = " + v2.getName();
    }
}

