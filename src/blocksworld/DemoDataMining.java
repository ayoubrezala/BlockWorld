package blocksworld;

import java.util.List;
import java.util.Random;
import java.util.Set;
import bwgeneratordemo.Demo;
import modelling.BooleanVariable;
import datamining.*;

public class DemoDataMining {

    public static void main(String[] args) {
        
        int nbrInstances = 10000; // on met le nombre d'instances
        float frequenceMinimum = 2.0f / 3.0f; // on fixe une fréquence minimale
        float confidence = 95.0f / 100.0f; // on fixe une confiance minimale

        BooleanBlocksWorld booleanBlocksWorld = new BooleanBlocksWorld(2, 1);
        
        BooleanDatabase booleanDatabase = new BooleanDatabase(booleanBlocksWorld.getBooleanVariables());
        
        // on affiche les variables booléennes
        System.out.println("Variables booléennes dans le monde :\n");
        for (BooleanVariable var : booleanBlocksWorld.getBooleanVariables()) {
            System.out.println(var);
        }
        // on génère des instances
        for (int i = 0; i < nbrInstances; i++) {
            Random aleatoire = new Random();

            // on génère un état aléatoire sous forme de liste
            List<List<Integer>> etat = Demo.getState(aleatoire);

            // on calcule le nombre total de blocs
            int nbrBloc = etat.stream().mapToInt(List::size).sum();

            // on crée une instance de BooleanBlocksWorld pour cet état
            BooleanBlocksWorld instanciation = new BooleanBlocksWorld(nbrBloc, etat.size());

            // on ajoute les variables de l'instance à la base de données
            booleanDatabase.add(instanciation.getBooleanVariables());
        }
        // on crée un objet Apriori pour extraire les motifs fréquents
        Apriori apriori = new Apriori(booleanDatabase);
        Set<Itemset> listFrequents = apriori.extract(frequenceMinimum);

        // on affiche les motifs fréquents
        System.out.println("\nMotifs fréquents (Apriori) :\n");
        for (Itemset itemset : listFrequents) {
            System.out.println(itemset);
        }
        // on crée un objet BruteForceAssociationRuleMiner pour extraire les règles d'association
        BruteForceAssociationRuleMiner brute = new BruteForceAssociationRuleMiner(booleanDatabase);
        Set<AssociationRule> listDeAssociationRule = brute.extract(frequenceMinimum, confidence);

        // on affiche les règles d'association
        System.out.println("\nRègles d'association :\n");
        for (AssociationRule regle : listDeAssociationRule) {
            System.out.println(regle);
        }
    }
}

