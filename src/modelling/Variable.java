package modelling;
import java.util.Objects;
import java.util.Set;
public class Variable{
	private String nom;
	private Set<Object> domaine;
	
	public Variable(String nom, Set<Object> domaine) {
		this.nom=nom;
		this.domaine=domaine;
		}
	//méthode accesseurs du nom et domaine
	public String  getName() {
		return nom;
		}
	public Set<Object> getDomain() {
		return domaine;
	}
	//Redéfinition des méthodes equals et hashCode
	@Override
    	public boolean equals(Object obj) {
        	if (this == obj) return true;
        	if (!(obj instanceof Variable)) return false;
        	Variable other = (Variable) obj;
        	return Objects.equals(this.nom, other.nom);
    	}
	@Override
	public int hashCode(){
		return Objects.hash(nom);
	}
		
}

