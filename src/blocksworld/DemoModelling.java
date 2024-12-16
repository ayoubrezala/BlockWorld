package blocksworld;

import java.util.*;
import modelling.*;

public class DemoModelling {
    public static void main(String[] args) {

        BlocksWorld world = new BlocksWorld(5, 2);

        // on crée les configurations
        Map<Variable, Object> world1 = createConfiguration(world, new int[]{-1, -2, 1, 0, 3}, new boolean[]{true, true, false, true, false}, new boolean[]{false, false});
        Map<Variable, Object> world2 = createConfiguration(world, new int[]{2, -2, 4, 1, -1}, new boolean[]{false, true, true, false, true}, new boolean[]{false, false});
        Map<Variable, Object> world3 = createConfiguration(world, new int[]{-1, 0, 1, -2, 3}, new boolean[]{true, true, false, true, false}, new boolean[]{false, false});
        Map<Variable, Object> world4 = createConfiguration(world, new int[]{-1, -2, 1, 4, 0}, new boolean[]{true, true, false, false, true}, new boolean[]{false, false});

        // on vérifie les contraintes régulières
        BlockWorldRegular reguliere = new BlockWorldRegular(5, 2);
        validateConstraints("régulières", reguliere.getSetConstraint(), world1, world2, world3, world4);

        // on vérifie les contraintes croissantes
        Croissante croissante = new Croissante(5, 2);
        validateConstraints("croissantes", croissante.getSetConstraint(), world1, world2, world3, world4);
    }
    // on crée une configuration
    private static Map<Variable, Object> createConfiguration(BlocksWorld world, int[] onValues, boolean[] fixedValues, boolean[] freeValues) {
        Map<Variable, Object> configuration = new HashMap<>();

        // on remplit les variables "on"
        List<Variable> onList = world.getVariableOn();
        for (int i = 0; i < onList.size(); i++) {
            configuration.put(onList.get(i), onValues[i]);
        }

        // on remplit les variables "fixed"
        List<BooleanVariable> fixedList = world.getVariableFixed();
        for (int i = 0; i < fixedList.size(); i++) {
            configuration.put(fixedList.get(i), fixedValues[i]);
        }

        // on remplit les variables "free"
        List<BooleanVariable> freeList = world.getVariableFree();
        for (int i = 0; i < freeList.size(); i++) {
            configuration.put(freeList.get(i), freeValues[i]);
        }

        return configuration;
    }

    // on vérifie les contraintes et on affiche le résultat
    private static void validateConstraints(String type, Set<Constraint> constraints, Map<Variable, Object>... worlds) {
        for (int i = 0; i < worlds.length; i++) {
            boolean isValid = true;
            for (Constraint constraint : constraints) {
                if (!constraint.isSatisfiedBy(worlds[i])) {
                    isValid = false;
                    break;
                }
            }
            afficherMessage("la configuration " + (i + 1), type, isValid);
        }
    }

    // on affiche les messages
    private static void afficherMessage(String configuration, String typeContrainte, boolean satisfait) {
        if (satisfait) {
            System.out.println(configuration + " satisfait toute les contraintes " + typeContrainte + ".");
        } else {
            System.out.println(configuration + " ne satisfait pas toute les contraintes " + typeContrainte + ".");
        }
    }
}

