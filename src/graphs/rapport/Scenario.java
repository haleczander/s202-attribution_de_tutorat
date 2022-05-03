package graphs.rapport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scenario {
    public static void main(String[] args) {
        /** Départ avec deux listes d'étudiant,
         * chaque étudiant a un nom, une moyenne, un niveau, un nombre d'absences et une motivation.
         * Les tuteurs disposent en plus d'un nombre max de tutorés
         * Le nombre d'absences des tutorés est comptabilisé
         * Description des ensembles:
         * T = {4 Tuteurs}      : certains peuvent prendre en charge plusieurs étudiants
         * U = {7 Tutorés}    
         */

        /** Initialisation des listes */
        ArrayList<Tutor> tutors = new ArrayList<>();
        ArrayList<Tutored> tutored = new ArrayList<>();

        /** Création des étudiants */
        /** Tuteurs */
        Tutor t1 = new Tutor("Vincent",        9.3,    2,  2,  0,  'A');
        Tutor t2 = new Tutor("Jacqueline",     13.2,   2,  1,  1,  'B');
        Tutor t3 = new Tutor("Pénélope",       13.2 ,  2,  2,  3,  'A');
        Tutor t4 = new Tutor("Édouard",        16.2,   3,  1,  0,  'C');
        /** Ensemble T */
        tutors.addAll(List.of(t1, t2, t3, t4));


        /**  Tutorés */
        Tutored u1 = new Tutored("Claude",     9.8,    0,  'A');
        Tutored u2 = new Tutored("Madeleine",  6.9,    8,  'A');
        Tutored u3 = new Tutored("Sabine",     12.7,   0,  'C');
        Tutored u4 = new Tutored("Hugues",     0.2,    2,  'B');
        Tutored u5 = new Tutored("Lucas",      17.3,   5,  'C');
        Tutored u6 = new Tutored("Alexandria", 12.5,   0,  'A');
        Tutored u7 = new Tutored("Anouk",      10.5,   1,  'B');
        /**Ensemble U */
        tutored.addAll(List.of(u1, u2, u3, u4, u5, u6, u7));


        /** Cas 1 : pas de doublons chez les tuteurs
         * Affectation 4 : 4
         */
        Assignment cas1 = new Assignment(tutored, tutors);
        cas1.setTutorsSplit(false);
        System.out.println(cas1.getTextAssignment());



        
         /** Cas 2 : les tuteurs peuvent prendre en charge plusieurs tutorés
          * Vincent et Pénéloppe sont dédoublés
          * Affectation 6  : 6
         */
        cas1.setTutorsSplit(true);
        System.out.println(cas1.getTextAssignment());


        /** Cas 3 : affectation manuelle
         * Après le calcul des poids des arêtes,
         * mise-à-jour d'une valeur d'arête à (-1) afin de forcer cette affectation
         * Nous voulons qu'Edouard (t4) et Lucas (u5) soient affectés ensemble
         */        
        Map<Tutored,Tutor> aretesO = new HashMap<>(); // HashMap car plusieurs affectations simultanées peuvent être réalisés
        aretesO.put(u5,t4);
        cas1.setForcedAssignments(aretesO);
        System.out.println(cas1.getTextAssignment());


        /** Cas 4 : incompatibilité entre deux étudiants
         * Comme ci-dessus mais la valeur de l'arête est artificiellement haute (5)
         * Vincent (t1) et Alexandria (u6) ont des animosités
         */
        Map<Tutored,Tutor> aretesN = new HashMap<>();
        aretesN.put(u6,t1);
        cas1.setNoAssignments(aretesN);
        System.out.println(cas1.getTextAssignment());

        
    }
}
