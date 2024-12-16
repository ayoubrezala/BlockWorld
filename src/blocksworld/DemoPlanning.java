package blocksworld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JFrame;
import modelling.*;
import planning.*;
import bwmodel.*;
import bwui.*;

public class DemoPlanning {
    // on créer un état du monde pour l'affichage graphique
    public static BWState<Integer> makestate(int n ,BlocksWorld world ,Map<Variable , Object> univers){      
        // on prépaire un état pour un monde avec n blocs
        BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(n);
        for (int block = 0; block < n; block++) {
            Variable onb = null;
            // on cherche la variable "onb" associé a ce bloc
            for (Variable v : world.getVariableOn()) {
                if (Integer.parseInt(v.getName().substring(2)) == block) {
                    onb = v;
                    break;
                }
            }
            if (onb != null) {
                int sous = (int) univers.get(onb);
                if (sous >= 0) {
                    builder.setOn(block, sous);
                }
            }
        }
        // on construit l’état du monde
        BWState<Integer> etat = builder.getState();
        return etat;
    } 
    public static void main(String[] args) {

        int nbrBlocks = 5;
        int nbrPiles = 3;
        
        BlocksWorld world  = new BlocksWorld(nbrBlocks, nbrPiles);
        BlocksAction ac = new BlocksAction(nbrBlocks,nbrPiles,world);
        Set<Action> acts = new HashSet<>();
        acts = ac.getEnsembleDesActions();
        
        Map<Variable, Object> objectif , univers = new HashMap<>();
        objectif = new HashMap<>();
        for (Variable v : world.getVariables()) {
            if (v.getName().startsWith("on")) {
                if (v.getName().equals("on0")) {
                    univers.put(v, -1);
                } else if (v.getName().equals("on1")) {
                    univers.put(v, 2);
                } else if (v.getName().equals("on2")) {
                    univers.put(v, -2);
                } else if (v.getName().equals("on3")) {
                    univers.put(v, 0);
                } else {
                    univers.put(v, 3);
                }
            } else if (v.getName().startsWith("fixed")) {
            
                if (v.getName().equals("fixed1")) {
                    univers.put(v, false);
                } else if (v.getName().equals("fixed4")) {
                    univers.put(v, false);
                } else {
                    univers.put(v, true);
                }
            } else if (v.getName().startsWith("free")) {
                // on donne des valeurs aux variables "free"
                if (v.getName().equals("free3")) {
                    univers.put(v, true);
                } else {
                    univers.put(v, false);
                }
            }
        }
        for (Variable v : world.getVariables()) {
            if (v.getName().startsWith("on")) {
                if (v.getName().equals("on0")) {
                    objectif.put(v, 1);
                } else if (v.getName().equals("on1")) {
                    objectif.put(v, 3);
                } else if (v.getName().equals("on2")) {
                    objectif.put(v, -1);
                } else if (v.getName().equals("on3")) {
                    objectif.put(v, -2);
                } else {
                    objectif.put(v, 2);
                }
            } else if (v.getName().startsWith("fixed")) {
                if (v.getName().equals("fixed0")) {
                    objectif.put(v, false);
                } else if (v.getName().equals("fixed4")) {
                    objectif.put(v, false);
                } else {
                    objectif.put(v, true);
                }
            } else if (v.getName().startsWith("free")) {
                if (v.getName().equals("free3")) {
                    objectif.put(v, true);
                } else {
                    objectif.put(v, false);
                }
            }
        }
        
        Goal goal = new BasicGoal(objectif);
        
         // on résout le probleme avec Dijkstra
        long startDijkstra = System.currentTimeMillis();
        DijkstraPlanner plannerDeDijkstra = new DijkstraPlanner(univers,acts, goal);
        plannerDeDijkstra.activateNodeCount(true);
        List<Action> PlanDedijkstra = plannerDeDijkstra.plan();
        long endDijkstra = System.currentTimeMillis();

        List<Action> resultDijkstra = new ArrayList<>();
        resultDijkstra.addAll(PlanDedijkstra);
        for (Action action : resultDijkstra) {
            System.out.println(action);
        }
        System.out.println("nombre de neuds explorés Dijkstra : "+plannerDeDijkstra.getNodesExplored());
        System.out.println("temps Dijkstra : " + (endDijkstra - startDijkstra) + " ms");

        // on résoud avec BFS
        long startBFS = System.currentTimeMillis();
        BFSPlanner plannerDeBFS = new BFSPlanner(univers,acts, goal);
        plannerDeBFS.activateNodeCount(true);
        List<Action> planDeBFSPlanner = plannerDeBFS.plan();
        long endBFS = System.currentTimeMillis();

        List<Action> resultBfs = new ArrayList<>();
        resultBfs.addAll(planDeBFSPlanner);
        for (Action action : resultBfs) {
            System.out.println(action);
        }
        System.out.println("nombre de neuds explorés BFS : "+plannerDeBFS.getNodesExplored());
        System.out.println("temps BFS : " + (endBFS - startBFS) + " ms");
        
        // on resolu le probleme avec DFS
        long startDFS = System.currentTimeMillis();
        DFSPlanner plannerDeDFS = new DFSPlanner(univers,acts, goal);
        plannerDeDFS.activateNodeCount(true);
        List<Action> planDeDFSPlanner = plannerDeDFS.plan();
        long endDFS = System.currentTimeMillis();

        List<Action> resultDfs = new ArrayList<>();
        resultDfs.addAll(planDeDFSPlanner);
        for (Action action : resultDfs) {
            System.out.println(action);
        }
        System.out.println("nombre de neuds explorés DFS : "+plannerDeDFS.getNodesExplored());
        System.out.println("temps DFS : " + (endDFS - startDFS) + " ms");
        
        // on résout avec A*
        BlocksHeuristic blocksHeuristic = new BlocksHeuristic(objectif);
        long startAStar = System.currentTimeMillis();
        AStarPlanner plannerDeAStar = new AStarPlanner(univers, acts, goal,blocksHeuristic);
        plannerDeAStar.activateNodeCount(true);
        List<Action> planDeAStarPlanner = plannerDeAStar.plan();
        long endAStar = System.currentTimeMillis();

        List<Action> resultAStar = new ArrayList<>();
        resultAStar.addAll(planDeAStarPlanner);
        for (Action action : resultAStar) {
            System.out.println(action);
        }
        System.out.println("nombre de neuds explorés A* : "+plannerDeAStar.getNodesExplored());
        System.out.println("temps A* : " + (endAStar - startAStar) + " ms");
        
        // on montre le plan avec une interface
        BWIntegerGUI gui = new BWIntegerGUI(nbrBlocks);
        JFrame frame = new JFrame("Plan Visualization");
        BWState<Integer> etat = DemoPlanning.makestate(nbrBlocks, world, univers);
        BWComponent<Integer> component = gui.getComponent(etat);
        frame.add(component);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        for (Action a : resultBfs) {
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            univers = a.successor(univers);
            component.setState(DemoPlanning.makestate(5, world, univers));
        }
    }
}

