package datamining;

import java.util.*;
import modelling.*;


public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner {
    public BruteForceAssociationRuleMiner(BooleanDatabase base) {
        super(base);
    }

    // on a cette methode qui génère tous les ensembles de premisse candidats pour les regles d'association a partir d'un ensemble donné
        public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> items) {
        Set<Set<BooleanVariable>> candidats = new HashSet<>(); 
        List<BooleanVariable> itemList = new ArrayList<>(items);
        int n = itemList.size();

        // cette boucle nous générer tous les sous-ensembles possibles
        for (int i = 1; i < (int) Math.pow(2, n); i++) {
            Set<BooleanVariable> candidat = new HashSet<>();
            int indice = 0;
            int temp = i;
            while (temp > 0) {
                if (temp % 2 == 1) {
                    candidat.add(itemList.get(indice)); // on ajoute l'élément correspondant à l'indice au sous-ensemble candidat
                }
                temp /= 2;
                indice++;
            }

            // on vérifie est ce que le candidat n'est ni vide ni égal à l'ensemble initial 
            if (!candidat.isEmpty() && !candidat.equals(items)) {
                candidats.add(candidat);
            }
        }
        candidats.remove(items); 
        return candidats;
    }

    
    @Override
    public BooleanDatabase getDatabase() {
        return super.getDatabase(); // elle retourne la base de données de la classe parent
    }
    //dans cette méthode on utilise la méthode de force brute pour extraire les règles d'association
    @Override
    public Set<AssociationRule> extract(float frequence, float confiance) {
        Set<AssociationRule> reglesassociation = new HashSet<>();
        Apriori ap = new Apriori(getDatabase());
        Set<Itemset> frequentItemsets = ap.extract(frequence);
        for (Itemset itemset : frequentItemsets) {
            Set<BooleanVariable> items = itemset.getItems(); 
            Set<Set<BooleanVariable>> premises = allCandidatePremises(items);
            for (Set<BooleanVariable> premise : premises) {
                Set<BooleanVariable> conclusion = new HashSet<>(items);
                conclusion.removeAll(premise); 
                float premiseFrequence = frequency(premise, frequentItemsets); // elle calcule la fréquence de la prémisse
                float confianceValeur = itemset.getFrequency() / premiseFrequence; // elle calcule la valeur de confiance

                // on vérifie  est ce que la valeur de confiance est supérieure ou égale au seuil de confiance spécifié
                if (confianceValeur >= confiance) {
                    AssociationRule regle = new AssociationRule(premise, conclusion, itemset.getFrequency(), confianceValeur);
                    reglesassociation.add(regle);
                }
            }
        }

        return reglesassociation; // elle retourne l'ensemble de règles d'association extraites
    }

}
