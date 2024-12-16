package planning;

import modelling.Variable;
import java.util.*;

public class Demo {
    public static void main(String[] args) {
        // On défine les variables et leurs domaines
        Variable varX = new Variable("X", new HashSet<>(Arrays.asList(0, 1, 2, 3, 4)));
        Variable varY = new Variable("Y", new HashSet<>(Arrays.asList(0, 1, 2, 3, 4)));

        // On défine l'état initial
        Map<Variable, Object> initialState = new HashMap<>();
        initialState.put(varX, 0);  // X = 0 au départ
        initialState.put(varY, 0);  // Y = 0 au départ

        //On défine les actions en définissant la précondition et les effets
        Set<Action> actions = new HashSet<>();

        // La pemiére action: Si X = 0, alors X devient 1
        Map<Variable, Object> precond1 = new HashMap<>();
        precond1.put(varX, 0);
        Map<Variable, Object> effet1 = new HashMap<>();
        effet1.put(varX, 1);
        actions.add(new BasicAction(precond1, effet1, 1));

        // La deuxiéme action: Si X = 1, alors X devient 2
        Map<Variable, Object> precond2 = new HashMap<>();
        precond2.put(varX, 1);
        Map<Variable, Object> effet2 = new HashMap<>();
        effet2.put(varX, 2);
        actions.add(new BasicAction(precond2, effet2, 1));

        // La troisiéme action: Si Y = 0, alors Y devient 1
        Map<Variable, Object> precond3 = new HashMap<>();
        precond3.put(varY, 0);
        Map<Variable, Object> effet3 = new HashMap<>();
        effet3.put(varY, 1);
        actions.add(new BasicAction(precond3, effet3, 3));

        // La quatriéme action: Si Y = 1, alors Y devient 2
        Map<Variable, Object> precond4 = new HashMap<>();
        precond4.put(varY, 1);
        Map<Variable, Object> effet4 = new HashMap<>();
        effet4.put(varY, 2);
        actions.add(new BasicAction(precond4, effet4, 2));

        //On défine le but
        Map<Variable, Object> goalState = new HashMap<>();
        goalState.put(varX, 2);  // Le but est d'atteindre X = 2
        goalState.put(varY, 2);  // Le but est aussi d'atteindre Y = 2
        Goal goal = new BasicGoal(goalState);

        //On crée les planificateurs
        
         DFSPlanner dfs=  new DFSPlanner(initialState, actions, goal);
         BFSPlanner  bfs = new BFSPlanner(initialState, actions, goal);
         DijkstraPlanner djk =new DijkstraPlanner(initialState, actions, goal);
         AStarPlanner astar = new AStarPlanner(initialState, actions, goal, new SimpleHeuristic());
        

        //On active le comptage des noeuds
        
	    // l'algorithme DFS
            dfs.activateNodeCount(true);
            List<Action> plan = dfs.plan();
            if (plan != null) {
            System.out.println("Plan DFS : " + plan);  // si le plan est trouvé ,on l'affiche
        } 
        else {
            System.out.println("Aucun plan trouvé avec DFS."); //sinon on affiche qu'il n y a aucun message
        }
            //On affiche le nombre de noeuds éxplorés
            System.out.println("Nombre de nœuds explorés  dfs: " + dfs.getNodesExplored());
            System.out.println("-------------------------------------------------------------");
            // l'algorithme BFS
            bfs.activateNodeCount(true);
            List<Action> plan2 = bfs.plan();
            if (plan2 != null) {
            System.out.println("Plan BFS : " + plan2);// si le plan est trouvé ,on l'affiche
        } 
        else {
            System.out.println("Aucun plan trouvé avec BFS.");  //sinon on affiche qu'il n y a aucun message
        }
            System.out.println("Nombre de nœuds explorés bfs  : " + bfs.getNodesExplored());
	    System.out.println("-------------------------------------------------------------");
            
	    // l'algorithme dijkstra
            djk.activateNodeCount(true);  
            List<Action> plan3 = djk.plan();
            if (plan3 != null) {
            System.out.println("Plan dijkstra : " + plan3);// si le plan est trouvé ,on l'affiche
        } 
        else {
            System.out.println("Aucun plan trouvé AVEC dijkstra.");  //sinon on affiche qu'il n y a aucun message
        }
            System.out.println("Nombre de nœuds explorés dijkstra  : " + djk.getNodesExplored());
            System.out.println("-------------------------------------------------------------");
          // l'algorithme A*
        astar.activateNodeCount(true);
        List<Action> planAStar = astar.plan();
        if (planAStar != null) {
            System.out.println("Plan A* : " + planAStar);// si le plan est trouvé ,on l'affiche
        } 
        else {
            System.out.println("Aucun plan trouvé avec A*.");//sinon on affiche qu'il n y a aucun message
        }
        System.out.println("Nombre de nœuds explorés A* : " + astar.getNodesExplored());

        
	System.out.println("=============== Fin des tests ===============");



        
    }
        static class SimpleHeuristic implements Heuristic {
        @Override
        public float estimate(Map<Variable, Object> état) {
      
            return 2;
        }
    }
}
