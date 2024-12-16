//ta3na
package blocksworld;
import java.util.*;
import modelling.Variable;
import planning.*;
public class BlocksAction {

    private int nbrBlocks;
    private int nbrPiles;
    private Set<Action> lesActions;
    
    public BlocksAction(int nbrBlocks , int nbrPiles ,BlocksWorld world ){
        this.nbrBlocks = nbrBlocks;
        this.nbrPiles = nbrPiles;
        this.lesActions = new HashSet<>();

        for (Variable v1 : world.getVariableOn()) {
            for (Variable v2 : world.getVariables()) {
                for (Variable v3 : world.getVariables()) {    
                    // premiére action : on déplace un bloc b du dessus d’un bloc b′ vers le dessus d’un bloc b′′
                    if (v2.getName().startsWith("on") && v3.getName().startsWith("on") && !v1.equals(v2) && !v2.equals(v3) && !v3.equals(v1)) {
                        Map<Variable,Object> p = new HashMap<>();
                        //v1 est sur v2 (onb égal à b') est une précondition qu'on mets à p
                        p.put(v1,Integer.parseInt(v2.getName().substring(2)));
                        Map<Variable,Object> e = new HashMap<>();
                        e.put(v1, Integer.parseInt(v3.getName().substring(2))); // e pour effet et p pour precondition
                        for (Variable v : world.getVariableFixed()) {
                            if (v.getName().equals("fixed"+v1.getName().substring(2))) {
                                p.put(v, false);
                            }
                            if (v.getName().equals("fixed"+v3.getName().substring(2))) {
                               p.put(v, false);
                               e.put(v, true);
                            }
                            if (v.getName().equals("fixed"+v2.getName().substring(2))) {
                                
                                e.put(v, false);
                            }
                        }
                        //on crée de type BasicAction déja implémenté lors du tp planning
                        BasicAction premiereaction = new BasicAction(p, e, 1);
                        lesActions.add(premiereaction);
                    }               
                    //deuxiéme Action: déplacer un bloc b du dessus d’un bloc b′ vers une pile vide p
                    if (v2.getName().startsWith("on") && v3.getName().startsWith("free") && !v1.equals(v2) && !v2.equals(v3) && !v3.equals(v1)) {
                        //on initialise la map des precondition
                        Map<Variable,Object> p = new HashMap<>();
                        p.put(v1,Integer.parseInt(v2.getName().substring(2)));
                        p.put(v3, true);                    
                        Map<Variable,Object> e = new HashMap<>();
                        e.put(v1, -Integer.parseInt(v3.getName().substring(4)));
                        e.put(v3,false );
                        for (Variable v : world.getVariableFixed()) {
                            if (v.getName().equals("fixed"+v1.getName().substring(2))) {
                                p.put(v, false);
                            }
                            if (v.getName().equals("fixed"+v2.getName().substring(2))) {
                                e.put(v,false );
                            }
                        }                     
                        BasicAction deuxiemeaction = new BasicAction(p, e, 1);
                        lesActions.add(deuxiemeaction);                       
                    }
                    //troisiéme action: déplacer un bloc b du dessous d’une pile p vers le dessus d’un bloc b′
                    if (v2.getName().startsWith("on") && v3.getName().startsWith("free") && !v1.equals(v2) && !v2.equals(v3) && !v3.equals(v1)){   
                        Map<Variable,Object> p = new HashMap<>();
                        p.put(v1, -Integer.parseInt(v3.getName().substring(4)));
                        Map<Variable,Object> e = new HashMap<>();
                        e.put(v1,Integer.parseInt(v2.getName().substring(2)));
                        e.put(v3, true);
                        //on parcouris l'ensemble des variables fixed qu'on obtient à partir de la méthode getVariableFixed pour qu'recupere la variable fixed liee a notre bloc actuel
                        for (Variable v : world.getVariableFixed()) {
                            if (v.getName().equals("fixed"+v1.getName().substring(2))) {
                                p.put(v, false);
                            }
                            if (v.getName().equals("fixed"+v2.getName().substring(2))) {
                                p.put(v, false);
                                e.put(v, true);
                            }
                        }                    
                        BasicAction troisiemeaction = new BasicAction(p, e, 1);
                        lesActions.add(troisiemeaction);
                    }
                    //quatriéme action : on déplace un bloc b du dessous d’une pile p vers une pile vide p′
                    if (v2.getName().startsWith("free") && v3.getName().startsWith("free") && !v1.equals(v2) && !v2.equals(v3) && !v3.equals(v1)) {                      
                        Map<Variable,Object> p = new HashMap<>();
                        p.put(v1, -Integer.parseInt(v2.getName().substring(4)));
                        Map<Variable,Object> e = new HashMap<>();
                        for (Variable v : world.getVariableFixed()) {
                            if (v.getName().equals("fixed"+v1.getName().substring(2))) {
                                p.put(v, false);
                            }
                        }
                        p.put(v3,true );
                        e.put(v1, -Integer.parseInt(v3.getName().substring(4)));
                        e.put(v2, true);
                        e.put(v3, false);
                        BasicAction quatriemeaction = new BasicAction(p, e, 1);
                        lesActions.add(quatriemeaction);
                    }
                }
            }
        }
    }
    public Set<Action> getEnsembleDesActions() {
        return lesActions;
    }

    public int getNbrBlocks() {
        return nbrBlocks;
    }

    public int getNbrPiles() {
        return nbrPiles;
    }
}
