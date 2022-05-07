package graphs.cas;

import java.util.ArrayList;
import java.util.List;

import graphs.rapport.Assignment;
import graphs.rapport.Tutor;
import graphs.rapport.Tutored;

/**
 * Scénario pour représenter un cas concret d'utilisation de l'affection.
 * 
 * @author Alexandre H.
 */
public final class Scenario {

    private Scenario() {
        throw new UnsupportedOperationException("Utility class and cannot be instantiated");
    }

    public static void main(String[] args) {
        /**
         * Départ avec deux listes d'étudiant,
         * chaque étudiant a un nom, une moyenne, un niveau, un nombre d'absences et une
         * motivation.
         * Les tuteurs disposent en plus d'un nombre max de tutorés
         * Le nombre d'absences des tutorés est comptabilisé
         * Description des ensembles:
         * T = {5 Tuteurs} : certains peuvent prendre en charge plusieurs étudiants
         * U = {7 Tutorés}
         */

        /** 
         * Initialisation des listes
         */
        List<Tutor> tutors = new ArrayList<>();
        List<Tutored> tutored = new ArrayList<>();

        /** 
         * Création des étudiants.
         * Les étudiants sont issus des données pour tester
         */

        // Tuteurs : forment l'ensemble T.
        Tutor t1 = new Tutor("Vincent", 9.3, 2, 0, 'A');
        Tutor t2 = new Tutor("Jacqueline", 13.2, 2, 1, 'B');
        Tutor t3 = new Tutor("Pénélope", 13.2, 2, 3, 'A');
        Tutor t4 = new Tutor("Édouard", 16.2, 3, 0, 'C', 1);
        Tutor t5 = new Tutor("Olivier", 11.3, 3, 2, 'B');

        // Tutorés : forment l'ensemble U
        Tutored u1 = new Tutored("Claude", 9.8, 0, 'A');
        Tutored u2 = new Tutored("Madeleine", 6.9, 8, 'A');
        Tutored u3 = new Tutored("Sabine", 12.7, 0, 'C');
        Tutored u4 = new Tutored("Hugues", 0.2, 2, 'B');
        Tutored u5 = new Tutored("Lucas", 17.3, 5, 'C');
        Tutored u6 = new Tutored("Alexandria", 12.5, 0, 'A');
        Tutored u7 = new Tutored("Anouk", 10.5, 1, 'B');

        /**
         * Cas 1 : pas de doublons chez les tuteurs
         *         il n'y a aucun 
         * Entrée - 7 tutorés : 5 tuteurs
         * Affectation - 5 tutorés : 5 tuteurs - liste d'attente = 2 tutorés
         */
        tutored.addAll(List.of(u1, u2, u3, u4, u5, u6, u7));
        // commentaire parce que c'est bugué
        tutors.addAll(List.of(t1, t2, t3, t4, t5));
        Assignment cas1 = new Assignment(tutored, tutors);
        cas1.setPolyTutor(false);

        System.out.println(cas1.getTextAssignment());
        System.out.println(cas1.getTextCost() + "\n");

        /**
         * Cas 2 : les tuteurs peuvent prendre en charge plusieurs tutorés
         *         Olivier est dédoublé.
         * Entrée - 7 tutorés : 5 tuteurs
         * Affectation - 6 tutorés : 6 tuteurs - liste d'attente = 1 tutoré
         */
        cas1.setPolyTutor(true);
        System.out.println(cas1.getTextAssignment());
        System.out.println(cas1.getTextCost() + '\n');

        /**
         * Cas 3 : affectation manuelle
         * Après le calcul des poids des arêtes,
         * mise-à-jour d'une valeur d'arête à (-1) afin de forcer cette affectation
         * Nous voulons qu'Edouard (t4) et Lucas (u5) soient affectés ensemble
         */
        cas1.addForcedAssignments(u1, t2);
        System.out.println(cas1.getTextAssignment() + '\n');

        /**
         * Cas 4 : incompatibilité entre deux étudiants
         * Comme ci-dessus mais la valeur de l'arête est artificiellement haute (5)
         * Jacqueline (t2) et Alexandria (u6) ont des animosités
         */
        cas1.addForbiddenAssignments(u6, t3);
        System.out.println(cas1.getTextAssignment());

    }
}
