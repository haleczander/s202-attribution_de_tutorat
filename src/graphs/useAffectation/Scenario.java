package graphs.useaffectation;

import java.util.ArrayList;
import java.util.List;

import graphs.affectation.Assignment;
import oop.Resource;
import oop.Teacher;
import oop.Tutor;
import oop.Tutored;

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
         * Départ avec deux listes d'étudiants,
         * chaque étudiant a un nom, une moyenne, un niveau, un nombre d'absences et une
         * motivation.
         * Les tuteurs de 3e année disposent en plus d'un nombre max de tutorés
         * Le nombre d'absences des tutorés est comptabilisé
         * 
         * Description des ensembles:
         * T = {5 Tuteurs} : certains peuvent prendre en charge plusieurs étudiants
         * U = {7 Tutorés}
         */

        /** 
         * Instanciation des listes
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

        // Professeur en charge de la matière.
        Teacher teacher = new Teacher("Baste", Resource.R104);

        /**
         * Ajout de tous les tuteurs et tutorés dans leur liste respective
         * Instanciation de l'assignation
         */
        tutored.addAll(List.of(u1, u2, u3, u4, u5, u6, u7));
        tutors.addAll(List.of(t1, t2, t3, t4, t5));
        System.out.println("Initialisation des listes :\nTutorés:\t "+tutored+"\nTuteurs:\t "+tutors);
        System.out.println("\nDes différences d'arrondis à 0.001 près avec le rapport peuvent être constatées.\n");
        Assignment cas1 = new Assignment(tutored, tutors);
        cas1.setTeacher(teacher);

        /**
         * Cas 1.A : Un seul tutoré par tuteur
         * 
         * Entrée :             7 tutorés, 5 tuteurs
         * Affectation :        5 tutorés, 5 tuteurs
         * Liste d'attente :    2 tutorés
         */
        cas1.setPolyTutor(false);        
        cas1.printScenario("1.A", "Aucune option n'est active");

        /**
         * Cas 1.B : Les tuteurs peuvent prendre en charge deux tutorés
         *
         * Rq : seul Olivier accepte de prendre en charge plusieurs tutorés
         * Entrée :             7 tutorés, 5 tuteurs
         * Affectation :        6 tutorés, 6 tuteurs
         * Liste d'attente :    1 tutoré
         */
        cas1.setPolyTutor(true);        
        cas1.printScenario("1.B", "Les tuteurs peuvent encadrer deux tutorés");
        cas1.setPolyTutor(false);

        /**
         * Cas 2.A : Forcage d'une affectation
         * 
         * Rq : Claude & Jaqueline, l'arête correspondante a un poids de -1000
         * Entrée :             7 tutorés, 5 tuteurs
         * Affectation :        5 tutorés, 5 tuteurs
         * Liste d'attente :    2 tutorés
         */
        cas1.addForcedAssignments(u1, t2);
        cas1.printScenario("2.A", "L'enseignant veut associer Claude à Jaqueline");
        cas1.removeForcedAssignment(u1, t2);

        /**
         * Cas 2.B : Incompatibilité entre deux étudiants
         * 
         * Rq : Claude & Édouard, l'arête correspondante a un poids de +1000
         * Entrée :             7 tutorés, 5 tuteurs
         * Affectation :        5 tutorés, 5 tuteurs
         * Liste d'attente :    2 tutorés
         */
        cas1.addForbiddenAssignments(u1, t4);        
        cas1.printScenario("2.B", "Claude et Édouard refusent d'être associés");
        cas1.removeForbiddenAssignment(u1, t4);


        /**
         * Cas 3.A : La force de la moyenne est doublée
         * 
         * Rq : alpha passe à 2
         * Entrée :             7 tutorés, 5 tuteurs
         * Affectation :        5 tutorés, 5 tuteurs
         * Liste d'attente :    2 tutorés
         */
        teacher.setAverageWeighting(2);        
        cas1.printScenario("3.A", "Force de la moyenne doublée");
        teacher.setAverageWeighting(1);

        /**
         * Cas 3.B : La force du niveau est doublée
         * 
         * Rq : beta passe à 2
         * Entrée :             7 tutorés, 5 tuteurs
         * Affectation :        5 tutorés, 5 tuteurs
         * Liste d'attente :    2 tutorés
         */
        teacher.setLevelWeighting(2);        
        cas1.printScenario("3.B", "Force du niveau doublée");
        teacher.setLevelWeighting(1);

        /**
         * Cas 3.A : La force des absences est doublée
         * 
         * Rq : gamma passe à 2
         * Entrée :             7 tutorés, 5 tuteurs
         * Affectation :        5 tutorés, 5 tuteurs
         * Liste d'attente :    2 tutorés
         */
        teacher.setAbsenceWeighting(2);
        cas1.printScenario("3.C", "Force des absences doublée");
        teacher.setAbsenceWeighting(1);

        /**
         * Cas 4.A : Un tuteur est exclu
         * 
         * Rq : Vincent
         * Entrée :             7 tutorés, 4 tuteurs
         * Affectation :        4 tutorés, 4 tuteurs
         * Liste d'attente :    3 tutorés
         */
        cas1.removeStudent(t1);        
        cas1.printScenario("4.A", "Exclusion d'un tuteur");
        cas1.addStudent(t1);

        /**
         * Cas 4.B : Un tutoré est exclu
         * 
         * Rq : Alexandria
         * Entrée :             6 tutorés, 5 tuteurs
         * Affectation :        5 tutorés, 5 tuteurs
         * Liste d'attente :    1 tutoré
         */
        cas1.removeStudent(u6);
        cas1.printScenario("4.B", "Exclusion d'un tutoré");

        System.out.println("Fin du scénario.");

    }

}
