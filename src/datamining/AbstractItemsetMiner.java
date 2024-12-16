package datamining;
import modelling.Variable;
import modelling.BooleanVariable;
import java.util.*;

public abstract class AbstractItemsetMiner implements ItemsetMiner{
	private BooleanDatabase database;
	// un attribut statique comparator permettant de comparer les items 
	public static final Comparator<BooleanVariable> COMPARATOR =(var1, var2) -> var1.getName().compareTo(var2.getName());
	
	public AbstractItemsetMiner(BooleanDatabase database){
		this.database=database;		
	}

	@Override
	public BooleanDatabase getDatabase() {
        return this.database;
    }
    	// methode pour calculer la frequence d'un ensemble de BooleanVariables
	public float frequency(Set<BooleanVariable> itemset) {
		List<Set<BooleanVariable>> transactions = database.getTransactions();
		int numTransactions = transactions.size();
		
		if (numTransactions == 0) {
		    return 0.0f; 
		}

		int count = 0;
		for (Set<BooleanVariable> transaction : transactions) {
		    if (transaction.containsAll(itemset)) {
		        count++;
		    }
		}

		return (float) count / numTransactions;
	 }
}

	
	
