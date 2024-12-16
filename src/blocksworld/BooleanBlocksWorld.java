package blocksworld;

import java.util.HashSet;
import java.util.Set;
import modelling.*;

public class BooleanBlocksWorld extends BlocksWorld {
    private Set<BooleanVariable> booleanVariables;
    public BooleanBlocksWorld(int nbrBlocks, int nbrPiles) {
        super(nbrBlocks, nbrPiles); 
        this.booleanVariables = new HashSet<>();

        // on génère les variables booléennes
        for (int i = 0; i < nbrBlocks; i++) {
            for (int j = 0; j < nbrBlocks; j++) {
                if (i != j) {
                    BooleanVariable onBBPrime = new BooleanVariable("on" + i + "," + j);
                    this.booleanVariables.add(onBBPrime);
                }
            }
            // on crée les variables "on-table(b, p)"
            for (int p = 0; p < nbrPiles; p++) {
                BooleanVariable onTableBP = new BooleanVariable("on-table" + i + "," + p);
                this.booleanVariables.add(onTableBP);
            }
        }
        // on ajoute les variables "free" et "fixed"
        this.booleanVariables.addAll(getVariableFree());
        this.booleanVariables.addAll(getVariableFixed());
    }
    public Set<BooleanVariable> getBooleanVariables() {
        return this.booleanVariables;
    }
    public int getNumBlocks() {
        return super.getNbrBlocks();
    }
    public int getNumPiles() {
        return super.getNbrPiles();
    }
}
