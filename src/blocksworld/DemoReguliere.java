package blocksworld;

import java.util.*;
import modelling.*;
import cp.*;
import bwmodel.*;
import bwui.*;
import javax.swing.JFrame;

public class DemoReguliere {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int nbrBlocks = 5; 
        int nbrPiles = 2;  
        BlockWorldRegular regularWorld = new BlockWorldRegular(nbrBlocks, nbrPiles);

        // on récupère les contraintes et les variables
        Set<Constraint> lesContraintes = regularWorld.getSetConstraint();
        List<Variable> listeDeVrbls = regularWorld.getVariableOn(); 

        // on transforme la liste des variables en ensemble
        Set<Variable> vrbls = new HashSet<>(listeDeVrbls);

        // on génère un nombre aleatoire pour les heuristiques
        Random aleatoire = new Random();
        ValueHeuristic valeurheuristique = new RandomValueHeuristic(aleatoire);
        VariableHeuristic heuristique1 = new NbConstraintsVariableHeuristic(lesContraintes, false);
        VariableHeuristic heuristique2 = new DomainSizeVariableHeuristic(false);

        // on crée les solveurs
        Solver solver1 = new BacktrackSolver(vrbls, lesContraintes);
        Solver solver2 = new MACSolver(vrbls, lesContraintes);
        Solver solver3 = new HeuristicMACSolver(vrbls, lesContraintes, heuristique1, valeurheuristique);
        Solver solver4 = new HeuristicMACSolver(vrbls, lesContraintes, heuristique2, valeurheuristique);

        // on affiche le menu pour choisir un solveur
        System.out.println("On doit choisir un solveur :");
        System.out.println("1 - BackTrackSolver");
        System.out.println("2 - MACSolver");
        System.out.println("3 - HeuristicMACSolver avec NbConstraintsVariableHeuristic");
        System.out.println("4 - HeuristicMACSolver avec DomainSizeVariableHeuristic");

        int choix = scanner.nextInt();
        Solver solveurChoisi = null;
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
                System.out.println("Choix invalide. On utilise BackTrackSolver par défaut.");
                solveurChoisi = solver1;
                break;
        }
        // on résout avec le solveur choisi
        Map<Variable, Object> solution = resolveSolver("Solveur choisi", solveurChoisi);

        // on montre le résultat en graphique si une solution est trouvée
        if (solution != null) {
            BWIntegerGUI gui = new BWIntegerGUI(nbrBlocks);
            JFrame frame = new JFrame("Plan Visualization");
            BWState<Integer> state = DemoPlanning.makestate(nbrBlocks, regularWorld, solution);
            BWComponent<Integer> component = gui.getComponent(state);
            frame.add(component);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        } else {
            System.out.println("On trouve pas une solution pour l'affichage graphique.");
        }
    }
    // on résout un solveur et on affiche les résultats
    private static Map<Variable, Object> resolveSolver(String solverName, Solver solver) {
        long start = System.currentTimeMillis();
        Map<Variable, Object> solution = solver.solve();
        long end = System.currentTimeMillis();
        afficherSolution(solverName, solution, end - start);
        return solution;
    }
    // on affiche les résultats et le temps de calcul
    private static void afficherSolution(String solverName, Map<Variable, Object> solution, long timeMillis) {
        System.out.println("Resultats pour " + solverName + " :");
        if (solution != null) {
            for (Map.Entry<Variable, Object> entry : solution.entrySet()) {
                System.out.println(entry.getKey().getName() + " : " + entry.getValue());
            }
        } else {
            System.out.println("On trouve pas de solution.");
        }
        System.out.println("Temps de calcul : " + timeMillis + " millisecondes.\n");
    }
}

