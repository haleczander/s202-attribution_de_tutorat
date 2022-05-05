package graphs.cas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import graphs.rapport.Assignment;
import graphs.rapport.Tutor;
import graphs.rapport.Tutored;

/**
 * Tests unitaire des différents scénarios imaginés.
 * 
 * @author Léopold V.
 * @see Scenario
 */
public class GlobalTest {
    public Tutored 
            n1,
            n2,
            n3,
            n4,
            n5,
            n6,
            n7;
    public Tutor 
            f1,
            f2,
            f3,
            f4,
            f5,
            f6,
            f7;
    public Assignment assignment1,
            assignment2;
            public List<Tutored> tutoredList;
    public List<Tutor> tutorList;
    public Map<Tutored, Tutor> affectationsForces;
    public Map<Tutored, Tutor> incompatibilites;

    @BeforeEach
    public void initialize() {
        n1 = new Tutored("Claude", 9.8, 0, 'A');
        n2 = new Tutored("Madeleine", 6.9, 8, 'A');
        n3 = new Tutored("Sabine", 12.7, 0, 'C');
        n4 = new Tutored("Hugues", 0.2, 2, 'B');
        n5 = new Tutored("Lucas", 17.3, 5, 'C');
        n6 = new Tutored("Alexandria", 12.5, 0, 'A');
        n7 = new Tutored("Anouk", 10.5, 1, 'B');

        f1 = new Tutor("Vincent", 9.3, 2, 0, 'A', 2);
        f2 = new Tutor("Jacqueline", 13.2, 2, 1, 'B', 1);
        f3 = new Tutor("Pénélope", 13.2, 2, 3, 'A', 2);
        f4 = new Tutor("Édouard", 13.9, 3, 0, 'C', 1);
        f5 = new Tutor("Olivier", 11.3, 3, 2, 'B', 2);
        f6 = new Tutor("Inès", 9.3, 3, 1, 'A', 2);
        f7 = new Tutor("Franck", 11.9, 3, 4, 'C', 1);

        tutoredList = new ArrayList<>();
        tutorList = new ArrayList<>();

        affectationsForces = new HashMap<>();

        incompatibilites = new HashMap<>();
    }

    @Test
    public void casAutantDeTuteursEtTutores() {
        tutoredList.addAll(List.of(n1, n2, n3, n4));
        tutorList.addAll(List.of(f1, f2, f3, f4));
        assignment1 = new Assignment(tutoredList, tutorList);

        System.out.println(assignment1.getTextAssignment());
    }

    @Test
    public void casPlusDeTutoresQueDeTuteurs() {
        tutoredList.addAll(List.of(n1, n2, n3, n4, n5, n6));
        tutorList.addAll(List.of(f2, f4, f5, f6));
        assignment1 = new Assignment(tutoredList, tutorList);

        System.out.println(assignment1.getTextAssignment());
    }

    @Test
    public void casAffectationManuelle() {
        tutoredList.addAll(List.of(n1, n2, n3, n4, n5));
        tutorList.addAll(List.of(f2, f3, f4, f6));
        affectationsForces.put(n5, f4);
        
        // faire un assertEquals ici pour montrer qu'ils étaient par forcément affecter
        // ensemble au début
        
        assignment1 = new Assignment(tutoredList, tutorList);
        assignment1.setForcedAssignments(affectationsForces);
        
        System.out.println(assignment1.getTextAssignment());
        
        // grâce à l'utilisation d'une Map, on peut forcer plus d'affectations
        
        affectationsForces.put(n4, f3);
        assignment1.setForcedAssignments(affectationsForces);

        System.out.println(assignment1.getTextAssignment());
    }

    @Test
    public void casIncompatibilite() {
        tutoredList.addAll(List.of(n1, n2, n3, n4, n5, n6));
        tutorList.addAll(List.of(f4, f5, f6, f7));
        incompatibilites.put(n3, f4);

        assignment1 = new Assignment(tutoredList, tutorList);
        System.out.println(assignment1.getTextAssignment());

        // assertEquals sur le fait que tel et tel sont affectés ensemble.

        assignment1.setNoAssignments(incompatibilites);
        System.out.println(assignment1.getTextAssignment());

        // assertEquals sur le fait que tel et tel ne sont plus ensemble Sadge.
        // je mets "tel" jusqu'à ce qu'on trouve une formule définitive.
    }

    @Test
    public void casListeAttente() {
        // fonctionnalités supplémentaires : 
        // - on peut choisir de faire ou pas un doublon des étudiants qui peuvent s'occuper de plusieurs étudiants.
        //   (valeur par défaut = true, comme vu précédemment).
        // - liste d'attente dans le cas où il y aurait trop d'étudiants d'un côté ou de l'autre.
        tutoredList.addAll(List.of(n1, n2, n3, n4));
        tutorList.addAll(List.of(f1, f2, f3, f4, f5, f6, f7));

        assignment1 = new Assignment(tutoredList, tutorList);
        // System.out.println(assignment1.getTextAssignment());
        // trop de tuteurs : liste d'attente

        tutoredList.addAll(List.of(n5, n6, n7));
        tutorList.clear();
        tutorList.addAll(List.of(f1, f5, f6, f7));

        assignment2 = new Assignment(tutoredList, tutorList);
        assignment2.setTutorsSplit(false);
        System.out.println(assignment2.getTextAssignment());
        // les tuteurs pourraient prendre en charge 7 étudiants mais doublons désactivés : certains tutorés en liste d'attente.
        
    }
}
