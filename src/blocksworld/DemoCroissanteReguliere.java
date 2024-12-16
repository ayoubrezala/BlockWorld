package blocksworld;

import java.util.*;
import modelling.*;
import cp.*;
import bwmodel.*;
import bwui.*;
import javax.swing.JFrame;

public class DemoCroissanteReguliere {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int nbrBlocks = 5; 
        int nbrPiles = 2;  
        BlocksWorld world = new BlocksWorld(nbrBlocks, nbrPiles);

        // on récupère les contraintes du monde
        BlocksWorldConstraints constraintsWorld = new BlocksWorldConstraints(nbrBlocks, nbrPiles);
        Set<Constraint> lesContraintes = constraintsWorld.getSetConstraint();

        // on ajoute les contraintes régulières
        BlockWorldRegular regularWorld = new BlockWorldRegular(nbrBlocks, nbrPiles);
        lesContraintes.addAll(regularWorld.getConstraintReguliere());

        // on ajoute les contraintes croissantes
        Croissante croissanteWorld = new Croissante(nbrBlocks, nbrPiles);
        lesContraintes.addAll(croissanteWorld.getConstraintCroissante());

        // on récupère les variables "on"
        List<Variable> vrbls = world.getVariableOn();

        // on génère un nombre aléatoire pour les heuristiques
        Random aleatoire = new Random();
        ValueHeuristic valeurHeuristique = new RandomValueHeuristic(aleatoire);

        VariableHeuristic heuristique1 = new NbConstraintsVariableHeuristic(lesContraintes, false);
        VariableHeuristic heuristique2 = new DomainSizeVariableHeuristic(false);

        // on crée les solveurs
        Solver solver1 = new BacktrackSolver(new HashSet<>(vrbls), lesContraintes);
        Solver solver2 = new MACSolver(new HashSet<>(vrbls), lesContraintes);
        Solver solver3 = new HeuristicMACSolver(new HashSet<>(vrbls), lesContraintes, heuristique1, valeurHeuristique);
        Solver solver4 = new HeuristicMACSolver(new HashSet<>(vrbls), lesContraintes, heuristique2, valeurHeuristique);

        System.out.println("Choisissez un solveur :");
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
        long start = System.currentTimeMillis();
        Map<Variable, Object> solution = solveurChoisi.solve();
        long end = System.currentTimeMillis();

        // on affiche les résultats
        afficherSolution("Solveur choisi", solution, end - start);

        // on montre la solution en graphique si elle existe
        if (solution != null) {
            BWIntegerGUI gui = new BWIntegerGUI(nbrBlocks);
            JFrame frame = new JFrame("Plan Visualization");
            BWState<Integer> state = DemoPlanning.makestate(nbrBlocks, world, solution);
            BWComponent<Integer> component = gui.getComponent(state);
            frame.add(component);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        } else {
            System.out.println("Aucune solution trouvée pour l'affichage graphique.");
        }
    }
    // on affiche les résultats et le temps de calcul
    private static void afficherSolution(String solverName, Map<Variable, Object> solution, long timeMillis) {
        System.out.println("Résultats pour " + solverName + " :");
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

