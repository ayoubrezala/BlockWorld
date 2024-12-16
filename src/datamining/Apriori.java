package datamining;

import java.util.*;
import modelling.*;

public class Apriori extends AbstractItemsetMiner {



    public Apriori(BooleanDatabase base) {
        super(base);
    }

    //cette méthode retourne les singletons fréquents avec une fréquence minimale donné
    public Set<Itemset> frequentSingletons(float minFrequence) {
        Set<Itemset> frequentSingletons = new HashSet<>();

       
        for (BooleanVariable variable : getDatabase().getItems()) {
            Set<BooleanVariable> item = new HashSet<>();
            item.add(variable);
            //on calcule la fréquence de cet ensemble
            float itemsetFrequence = frequency(item);
            if (itemsetFrequence >= minFrequence) {
            //vérifie si la fréquence calculée est supérieure ou égale à la fréquence donnée
                frequentSingletons.add(new Itemset(item, itemsetFrequence));
            }

        }
        return frequentSingletons;
    }

    // methode statique pour combiner deux ensembles de BooleanVariable
    public static SortedSet<BooleanVariable> combine(SortedSet<BooleanVariable> ensemble1,
            SortedSet<BooleanVariable> ensemble2) {
        // condition a verifier avant de combiner les deux listes
        //les deux ensembles aient la même taille k
        //les deux ensembles aient les mêmes k − 1 premiers items
        //les deux ensembles aient des differents dernier element
        if (!ensemble1.isEmpty() && !ensemble2.isEmpty() &&
                ensemble1.size() == ensemble2.size() &&
                ensemble1.headSet(ensemble1.last()).equals(ensemble2.headSet(ensemble2.last())) &&
                !ensemble1.last().equals(ensemble2.last())) {

            SortedSet<BooleanVariable> Setcombine = new TreeSet<>(ensemble1);
            Setcombine.add(ensemble2.last());
            return Setcombine;
        } else {

            return null;
        }
    }

    // cette méthode statique vérifie que tous les sous-ensembles d'un ensemble sont fréquents
    public static boolean allSubsetsFrequent(Set<BooleanVariable> items,Collection<SortedSet<BooleanVariable>> frequentSets) {
        
        for (BooleanVariable item : items) {

            //on crée un sous-ensemble en retirant un élément à la fois
            Set<BooleanVariable> subset = new HashSet<>(items);
            subset.remove(item);

            SortedSet<BooleanVariable> sortedSubset = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
            sortedSubset.addAll(subset);

            boolean found = false;
            for (SortedSet<BooleanVariable> frequentSet : frequentSets) {
                //cette condition verifie si le sous-ensemble trié est égal à un ensemble fréquent
                if (sortedSubset.equals(frequentSet)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Set<Itemset> extract(float minFrequence) {
        Set<Itemset> frequentItemsets = new HashSet<>();

        // 1:on génére des singletons
        Set<Itemset> frequentSingletons = frequentSingletons(minFrequence);
        List<SortedSet<BooleanVariable>> ensembleItemsetFrequent = new ArrayList<>();
        
        Set<Itemset> candidateItemsets = new HashSet<>();
        candidateItemsets.addAll(frequentSingletons);

        frequentItemsets.addAll(frequentSingletons);

        for (Itemset itemset : candidateItemsets) {
            SortedSet<BooleanVariable> e = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
            e.addAll(itemset.getItems());
            ensembleItemsetFrequent.add(e);
        }

        List<SortedSet<BooleanVariable>> temp = new ArrayList<>(ensembleItemsetFrequent);

        while (!candidateItemsets.isEmpty()) {
            Set<Itemset> newCandidateItemsets = new HashSet<>();

            //2:on génére des ensembles de taille k+1 avec la méthode combine
            for (Itemset itemset1 : candidateItemsets) {

                for (SortedSet<BooleanVariable> itemset2 : temp) {
                    SortedSet<BooleanVariable> s1 = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
                    s1.addAll(itemset1.getItems());
                    SortedSet<BooleanVariable> s2 = new TreeSet<>(AbstractItemsetMiner.COMPARATOR);
                    s2.addAll(itemset2);
                    
                    SortedSet<BooleanVariable> Setcombine = combine(s1, s2);

                    if (Setcombine != null) {
                        
                        //on vérifie si tous les sous-ensembles de l'ensemble candidat sont fréquents
                        if (allSubsetsFrequent(Setcombine, ensembleItemsetFrequent)) {
                            
                            float itemsetFrequence = frequency(Setcombine);
                            
                            //cette condition vérifie si la fréquence de l'ensemble candidat dépasse le seuil minimum et qu'il n'est pas encore dans la liste des ensembles fréquents
                            if (itemsetFrequence >= minFrequence && !ensembleItemsetFrequent.contains(Setcombine)) {

                                Itemset newItemset = new Itemset(Setcombine, itemsetFrequence);
                                frequentItemsets.add(newItemset);
                                newCandidateItemsets.add(newItemset);
                                ensembleItemsetFrequent.add(Setcombine);
                            }
                        }
                    }
                }
            }

            candidateItemsets = newCandidateItemsets;
            //on ajoute les nouveaux éléments d'ensembleItemsetFrequent à la liste temporaire
            temp.addAll(ensembleItemsetFrequent);
        }
        //l'ensemble final des itemset fréquents
        return frequentItemsets;
    }

}
