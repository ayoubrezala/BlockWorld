package modelling;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BooleanVariable extends Variable {

    public BooleanVariable(String name) {
        super(name, createBooleanDomain());
    }
    
    private static Set<Object> createBooleanDomain() {
        Set<Object> domain = new HashSet<>();
        domain.add(true);
        domain.add(false);
        return domain;
    }
    //Redéfinition des méthodes equals et hashCode
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Variable)) return false;
        Variable other = (Variable) obj;
        return Objects.equals(this.getName(), other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}

