package blocksworld;

import java.util.*;
import modelling.*;
import cp.*;
import bwmodel.*;
import bwui.*;
import javax.swing.JFrame;

public class DemoCroissante {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int nbrBlocks = 5; 
        int nbrPiles = 2;  
        Croissante croissanteWorld = new Croissante(nbrBlocks, nbrPiles);

        // on récupère les contraintes et les variables
        Set<Constraint> lesContraintes = croissanteWorld.getSetConstraint();
        List<Variable> listeDeVrbls = croissanteWorld.getVariableOn(); 

        // on transforme la liste des variables en ensemble
        Set<Variable> vrbls = new HashSet<>(listeDeVrbls);
        // on fait un nombre aleatoire pour les heuristiques
        Random aleatoire = new Random();
        ValueHeuristic valeurHeuristique = new RandomValueHeuristic(aleatoire);

        // on fait des heuristiques pour les variables
        VariableHeuristic heuristique1 = new NbConstraintsVariableHeuristic(lesContraintes, false);
        VariableHeuristic heuristique2 = new DomainSizeVariableHeuristic(false);
        
        // on cree des solveurs
        Solver solver1 = new BacktrackSolver(new HashSet<>(vrbls), lesContraintes);
        Solver solver2 = new MACSolver(new HashSet<>(vrbls), lesContraintes);
        Solver solver3 = new HeuristicMACSolver(new HashSet<>(vrbls), lesContraintes, heuristique1, valeurHeuristique);
        Solver solver4 = new HeuristicMACSolver(new HashSet<>(vrbls), lesContraintes, heuristique2, valeurHeuristique);

        System.out.println("On doit choisir un solveur :");
        System.out.println("1 - BackTrackSolver");
        System.out.println("2 - MACSolver");
        System.out.println("3 - HeuristicMACSolver avec NbConstraintsVariableHeuristic");
        System.out.println("4 - HeuristicMACSolver avec DomainSizeVariableHeuristic");

        int choix = scanner.nextInt();
        Solver solveurChoisi = null;

        // on fait un switch pour voir quel solveur utiliser
        switch (choix) {
            case 1:
                solveurChoisi = solver1;
                break;
            case 2:
                solveurChoisi = solver2;
                break;
            case 3:
                solveurChoisi = solver3;
                break;
            case 4:
                solveurChoisi = solver4;
                break;
            default:
                System.out.println("Mauvais choix. On va utiliser BackTrackSolver par defaut.");
                solveurChoisi = solver1;
                break;
        }
        // on utilise le solveur choisi pour resoudre
        long start = System.currentTimeMillis();
        Map<Variable, Object> solution = solveurChoisi.solve();
        long end = System.currentTimeMillis();

        // on affiche les resultats
        afficherSolution(solveurChoisi.getClass().getSimpleName(), solution, end - start);

        // on montre la solution en graphique si c'est possible
        if (solution != null) {
            BWIntegerGUI gui = new BWIntegerGUI(nbrBlocks);
            JFrame frame = new JFrame("Plan Visualization");
            BWState<Integer> state = DemoPlanning.makestate(nbrBlocks, croissanteWorld, solution);
            BWComponent<Integer> component = gui.getComponent(state);
            frame.add(component);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        } else {
            System.out.println("On a pas trouver une solution pour afficher.");
        }
    }
    // on utilise cette methode pour afficher les solutions et les temps de calcul
    private static void afficherSolution(String solverName, Map<Variable, Object> solution, long timeMillis) {
        System.out.println("Resultats pour " + solverName + " :");
        if (solution != null) {
            for (Map.Entry<Variable, Object> entry : solution.entrySet()) {
                System.out.println(entry.getKey().getName() + " : " + entry.getValue());
            }
        } else {
            System.out.println("On a trouver aucune solution.");
        }
        System.out.println("Temps de calcul : " + timeMillis + " millisecondes.\n");
    }
}

