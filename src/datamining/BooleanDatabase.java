package datamining;
import modelling.Variable;
import modelling.BooleanVariable;
import java.util.*;



public class BooleanDatabase {
    private Set<BooleanVariable> items;
    private List<Set<BooleanVariable>> transactions; // liste d'ensemble de booleanVariables

    
    public BooleanDatabase(Set<BooleanVariable> items) {
        this.items = items;
        this.transactions = new ArrayList<>();
    }

    //méthode destinée à insérer une transaction dans la collection actuelle de transactions
    public void add(Set<BooleanVariable> transaction) {
        this.transactions.add(transaction);
    }
    
    public Set<BooleanVariable> getItems() {
        return items;
    }

    public List<Set<BooleanVariable>> getTransactions() {
        return transactions;
    }
} 
