package modelling;

import java.util.*;

public class Demo {
    public static void main(String[] args) {
    	//On crée des domaines
    	Set<Object> s1 = new HashSet<>(Arrays.asList(1, 2, 3)); 
        Set<Object> s2 = new HashSet<>(Arrays.asList(1, 2, 3, 4));
        Set<Object> s3 = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5)); 
        Set<Object> s4 = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6));
        Set<Object> s5 = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        //On crée des variables
        Variable v1 = new Variable("X1", new HashSet<>(s1));
        Variable v2 = new Variable("X2", new HashSet<>(s2));
        Variable v3 = new Variable("X3", new HashSet<>(s3));
        Variable v4 = new Variable("X4", new HashSet<>(s4));
        Variable v5 = new Variable("X5", new HashSet<>(s5));
        BooleanVariable bv1 = new BooleanVariable("B1");
        BooleanVariable bv2 = new BooleanVariable("B2");
        BooleanVariable bv3 = new BooleanVariable("B3");
        

	//On crée des instantaions
        Map<Variable, Object> instantiation0 = new HashMap<>();
        Map<Variable, Object> instantiation1 = new HashMap<>();
        instantiation1.put(v1, 1);
        instantiation1.put(v2, 2);
        instantiation1.put(v3, 3);
        instantiation1.put(v4, 17);
        instantiation1.put(v5, 3);
        instantiation1.put(bv1, false);
        instantiation1.put(bv2, true);
        instantiation1.put(bv3, false);
        
        System.out.println("=============== Test des DifferenceConstraint ===============");
        
        //DifferenceConstraint  marche (return true)
        System.out.println("Test 1: v1 =/= v2 (return true)");
        DifferenceConstraint diffConstraint1 = new DifferenceConstraint(v1, v2);
        System. out. println(diffConstraint1.isSatisfiedBy(instantiation1));
        
        //DifferenceConstraint  ne marche pas (return False)
        System.out.println("\nTest 2: v3 = v5 (return false)");
        DifferenceConstraint diffConstraint2 = new DifferenceConstraint(v3, v5);
        System. out. println(diffConstraint2.isSatisfiedBy(instantiation1));
        
        System. out. println("\n cas de instantiation vide \n");
        
        //DifferenceConstraint avec instantiation vide
        DifferenceConstraint diffConstraint3 = new DifferenceConstraint(v1, v2);
        try{  System. out. println(diffConstraint3.isSatisfiedBy(instantiation0));
           } catch (Exception e){
                System . out. println(e.getMessage()+"\n");
           }
           
        //DifferenceConstraint avec des variables boolean
        System. out. println("\n test avec des booleans \n");
        System.out.println("Test 4: bv1 =/= bv2 (return true)");
        DifferenceConstraint diffConstraintbol1 = new DifferenceConstraint(bv1, bv2); //DifferenceConstraint  marche (return true)
        System. out. println(diffConstraintbol1.isSatisfiedBy(instantiation1));
        System.out.println("Test 4b: bv1 = bv3 (return false)");
        DifferenceConstraint diffConstraintbol2 = new DifferenceConstraint(bv1, bv3); ////DifferenceConstraint  ne marche pas (return False)
        System. out. println(diffConstraintbol2.isSatisfiedBy(instantiation1));
        
        
        System . out. println("----------------------------------------------------------- \n");
        
        System.out.println("=============== Test des ImplicationConstraint ===============");
        //Implication  marche (return true)
        System.out.println("Test 1: (v1 -> s1) => (v2 > s2) (return true)");
        Implication implication1 = new Implication(v1, s1, v2, s2);
        System. out. println(implication1.isSatisfiedBy(instantiation1));
        
        System.out.println("\nTest 2: (v4 -/-> s4) → (v1 peut prendre n'importe quelle valeur) ( return true)");
        Implication implication2 = new Implication(v4, s4, v1, s1); // v4 n'est pas dans s4 =>  v1 peut prendre ses valeurs n'importe où soit dans son domaine soit dehors
        System. out. println(implication2.isSatisfiedBy(instantiation1));
        
        //Implication ne marche pas (return false)
        System.out.println("\nTest 3: (v1 -> s1) => (v4 -/-> s4) (return false)");
        Implication implication3 = new Implication(v1, s1, v4, s4);
        System. out. println(implication3.isSatisfiedBy(instantiation1));
        
        //Implication avec instantiation vide
         System. out. println("\n cas de instantiation vide \n");
        Implication implication4 = new Implication(v1, s1, v2, s2);
        try{  System. out. println(implication4.isSatisfiedBy(instantiation0));
           } catch (Exception e){
                System . out. println(e.getMessage()+"\n");
           }
        
        System . out. println("----------------------------------------------------------- \n");
        
        System.out.println("=============== Test des UnaryConstraint ===============");
        //Unaryconstraint marche (return true)
        System.out.println("Test 1: v1 -> s1 (return true)");
        UnaryConstraint unaryConstraint1 = new UnaryConstraint(v1, s1);
        System. out. println(unaryConstraint1.isSatisfiedBy(instantiation1));
        
        //Unaryconstraint ne marche pas
         System.out.println("\nTest 2: v4 -> s4 (return false)");
        UnaryConstraint unaryConstraint2 = new UnaryConstraint(v4, s4);
        System. out. println(unaryConstraint2.isSatisfiedBy(instantiation1)); 
        
        //Unaryconstraint avec instantiation vide
        System. out. println("\n cas de instantiation vide \n");
        UnaryConstraint unaryConstraint3 = new UnaryConstraint(v1, s1);
        try{  System. out. println(unaryConstraint3.isSatisfiedBy(instantiation0));
           } catch (Exception e){
                System . out. println(e.getMessage()+"\n");
           }
        System.out.println("=============== Fin des tests ===============");

   }
}
