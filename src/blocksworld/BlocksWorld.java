package blocksworld;

import java.util.*;
import modelling.Variable;
import modelling.BooleanVariable;

public class BlocksWorld {
    private int nbrBlocks;
    private int nbrPiles;
    private List<Variable> vrbls; 
    private List<Variable> variableOn; 
    private List<BooleanVariable> variableFixed; 
    private List<BooleanVariable> variableFree;

    public BlocksWorld(int nbrBlocks, int nbrPiles) {
        this.nbrBlocks = nbrBlocks;
        this.nbrPiles = nbrPiles;
        this.vrbls = new ArrayList<>();
        this.variableOn = new ArrayList<>();
        this.variableFixed = new ArrayList<>();
        this.variableFree = new ArrayList<>();

        // on cree les variables "on"
        for (int i = 0; i < nbrBlocks; i++) {
            Set<Object> domaineOnb = new TreeSet<>();

            // on met les piles avec des valeurs de -1 à -(nbrPiles)
            for (int j = -nbrPiles; j < 0; j++) {
                domaineOnb.add(j);
            }
            // on met les blocs sauf le bloc actuel
            for (int k = 0; k < nbrBlocks; k++) {
                if (k != i) {
                    domaineOnb.add(k);
                }
            }
            Variable onb = new Variable("on" + i, domaineOnb);
            this.vrbls.add(onb);
            this.variableOn.add(onb);
        }

        // on cree les variables "fixed"
        for (int i = 0; i < nbrBlocks; i++) {
            BooleanVariable fixedb = new BooleanVariable("fixed" + i);
            this.vrbls.add(fixedb);
            this.variableFixed.add(fixedb);
        }

        // on cree les variables "free"
        for (int i = 1; i <= nbrPiles; i++) {
            BooleanVariable freep = new BooleanVariable("free" + i);
            this.vrbls.add(freep);
            this.variableFree.add(freep);
        }
    }
    public List<Variable> getVariables() {
        return vrbls;
    }
    public int getNbrBlocks() {
        return nbrBlocks;
    }
    public int getNbrPiles() {
        return nbrPiles;
    }
    public List<BooleanVariable> getVariableFixed() {
        return variableFixed;
    }
    public List<Variable> getVariableOn() {
        return variableOn;
    }
    public List<BooleanVariable> getVariableFree() {
        return variableFree;
    }
}

