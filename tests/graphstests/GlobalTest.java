package graphstests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.ulille.but.sae2_02.graphes.Arete;
import graphs.affectation.Assignment;
import graphs.affectation.Tools;
import graphs.useaffectation.Scenario;
import oop.Resource;
import oop.Student;
import oop.Teacher;
import oop.Tutor;
import oop.Tutored;

/**
 * Tests unitaire des différents scénarios imaginés.
 * 
 * @author Léopold V.
 * @see Scenario
 */
public class GlobalTest {
    private Tutored 
            u1,
            u2,
            u3,
            u4,
            u5,
            u6,
            u7;
    private Tutor 
            t1,
            t2,
            t3,
            t4,
            t5;
    private Teacher teacher;
    private Assignment assignment;
    private static final double DELTA = 0.002;

    @BeforeEach
    public void initialize() {
        u1 = new Tutored("Claude", 9.8, 0, 'A');
        u2 = new Tutored("Madeleine", 6.9, 8, 'A');
        u3 = new Tutored("Sabine", 12.7, 0, 'C');
        u4 = new Tutored("Hugues", 0.2, 2, 'B');
        u5 = new Tutored("Lucas", 17.3, 5, 'C');
        u6 = new Tutored("Alexandria", 12.5, 0, 'A');
        u7 = new Tutored("Anouk", 10.5, 1, 'B');

        t1 = new Tutor("Vincent", 9.3, 2, 0, 'A');
        t2 = new Tutor("Jacqueline", 13.2, 2, 1, 'B');
        t3 = new Tutor("Pénélope", 13.2, 2, 3, 'A');
        t4 = new Tutor("Édouard", 16.2, 3, 0, 'C', 1);
        t5 = new Tutor("Olivier", 11.3, 3, 2, 'B');

        teacher = new Teacher("name", Resource.R106);
        teacher.setAbsenceWeighting(1);
        teacher.setAverageWeighting(1);
        teacher.setLevelWeighting(1);

        List<Tutored> tutoredList = new ArrayList<>();
        tutoredList.addAll(List.of(u1, u2, u3, u4, u5, u6, u7));
        List<Tutor> tutorList = new ArrayList<>();
        tutorList.addAll(List.of(t1, t2, t3, t4, t5));
        assignment = new Assignment(tutoredList, tutorList);
        assignment.setTeacher(teacher);
    }

    @Test
    public void casDeBase() {
        // cas 1.A
        assignment.setPolyTutor(false);
        List<Arete<Student>> edges = assignment.getAssignment();
        List<Student> waitingList = assignment.getWaitingList();
         

        assertEquals(edges.size(), 5);
        assertEquals(waitingList.size(), 2);
        assertEquals(assignment.getCost(), 9.242, DELTA);
    }

    @Test
    public void casPolytutorat() {
        // cas 1.B
        assignment.setPolyTutor(true);
        List<Arete<Student>> edges = assignment.getAssignment();
        List<Student> waitingList = assignment.getWaitingList();
         

        assertEquals(edges.size(), 6);
        assertEquals(waitingList.size(), 1);
        assertEquals(assignment.getCost(), 11.941, DELTA);
        assertEquals(waitingList.get(0), u5);
        int olivierCount = 0;
        Pattern pattern = Pattern.compile("olivier", Pattern.CASE_INSENSITIVE);
        Matcher matcher;
        for (Arete<Student> edge : edges) {
            matcher = pattern.matcher(edge.getExtremite2().getName());
            if(matcher.find()) {
                olivierCount++;
            }
        }
        assertEquals(olivierCount, 2);
    }

    @Test
    public void casAffectationForcee() {
        // cas 2.A
        // rappel : on force l'affectation entre Claude & Jacqueline
        assignment.setPolyTutor(false);
        List<Arete<Student>> edges = assignment.getAssignment();
        
        Arete<Student> wantedAssignment = new Arete<>(u1, t2);
        for (Arete<Student> edge : edges) {
            assertFalse(Tools.edgeTextEquals(edge, wantedAssignment));
        }
        
        assignment.addForcedAssignments(u1, t2);
        edges = assignment.getAssignment();
        List<Student> waitingList = assignment.getWaitingList();
        boolean isEdgeInAssignment = false;
         

        for (Arete<Student> edge : edges) {
            if(Tools.edgeTextEquals(edge, wantedAssignment)) {
                isEdgeInAssignment = true;
            }
        }
        assertTrue(isEdgeInAssignment);
        assertEquals(edges.size(), 5);
        assertEquals(waitingList, List.of(u5, u2));
        assertEquals(assignment.getCost(), 7.415, DELTA);

        assignment.removeForcedAssignment(u1);
        edges = assignment.getAssignment();
        for (Arete<Student> edge : edges) {
            assertFalse(Tools.edgeTextEquals(edge, wantedAssignment));
        }

    }

    @Test
    public void casIncompatibilite() {
        // cas 2.B
        // rappel : on veut empêcher une affectation entre 
        assignment.setPolyTutor(false);
        List<Arete<Student>> edges = assignment.getAssignment();

        Arete<Student> unwantedAssignment = new Arete<>(u1, t4);
        boolean isEdgeInAssignment = false;
        for (Arete<Student> edge : edges) {
            if(Tools.edgeTextEquals(edge, unwantedAssignment)) {
                isEdgeInAssignment = true;
            }
        }
        assertFalse(isEdgeInAssignment);

        assignment.addForbiddenAssignments(u1, t4);
        edges = assignment.getAssignment();
        List<Student> waitingList = assignment.getWaitingList();
         

        for (Arete<Student> edge : edges) {
            assertFalse(Tools.edgeTextEquals(edge, unwantedAssignment));
        }
        assertEquals(edges.size(), 5);
        assertEquals(waitingList, List.of(u5, u2));
        assertEquals(assignment.getCost(), 9.242, DELTA);

        assignment.removeForbiddenAssignment(u1);
        edges = assignment.getAssignment();
        isEdgeInAssignment = false;
        for (Arete<Student> edge : edges) {
            if(Tools.edgeTextEquals(edge, unwantedAssignment)) {
                isEdgeInAssignment = true;
            }
        }
        assertFalse(isEdgeInAssignment);
    }

    @Test
    public void casPonderationMoyenne() {
        // cas 3.A
        assignment.setPolyTutor(false);
        teacher.setAverageWeighting(2);
        List<Arete<Student>> edges = assignment.getAssignment();
        List<Student> waitingList = assignment.getWaitingList();
 

        // assertNotEquals = poids sans pondération de la moyenne
        assertEquals(u1.getWeight(), 1.131, DELTA);
        assertNotEquals(u2.getWeight(), 1.055, DELTA);
        assertEquals(u4.getWeight(), 0.497, DELTA);
        assertNotEquals(u6.getWeight(), 0.811, DELTA);
        assertEquals(u7.getWeight(), 1.441, DELTA);

        assertEquals(t1.getWeight(), 1.467, DELTA);
        assertEquals(t2.getWeight(), 1.456, DELTA);
        assertNotEquals(t3.getWeight(), 1.141, DELTA);

        assertEquals(edges.size(), 5);
        assertEquals(waitingList, List.of(u5, u3));
        assertEquals(assignment.getCost(), 12.819, DELTA);
    }

    @Test
    public void casPonderationNiveau() {
        // cas 3.B
        assignment.setPolyTutor(false);
        teacher.setLevelWeighting(2);
        List<Arete<Student>> edges = assignment.getAssignment();
        List<Student> waitingList = assignment.getWaitingList();
         

        // assertNotEquals = poids avec pondération de la moyenne uniquement
        assertNotEquals(t1.getWeight(), 1.467, DELTA);
        assertEquals(t2.getWeight(), 1.637, DELTA);
        assertNotEquals(t3.getWeight(), 1.429, DELTA);
        assertEquals(t4.getWeight(), 1.266, DELTA);
        assertEquals(t5.getWeight(), 1.428, DELTA);

        assertEquals(edges.size(), 5);
        assertEquals(waitingList, List.of(u5, u2));
        assertEquals(assignment.getCost(), 11.342, DELTA);
    }

    @Test
    public void casPonderationAbsences() {
        // cas 3.C
        assignment.setPolyTutor(false);
        teacher.setAbsenceWeighting(2);
        List<Arete<Student>> edges = assignment.getAssignment();
        List<Student> waitingList = assignment.getWaitingList();
         

        // assertNotEquals = poids sans aucune pondération
        assertNotEquals(u2.getWeight(), 1.055, DELTA);
        assertEquals(u3.getWeight(), 1.306, DELTA);
        assertEquals(u4.getWeight(), 0.965, DELTA);
        assertNotEquals(u5.getWeight(), 1.696, DELTA);
        assertEquals(u6.getWeight(), 1.059, DELTA);

        assertEquals(t1.getWeight(), 1.262, DELTA);
        assertNotEquals(t3.getWeight(), 1.141, DELTA);
        assertEquals(t4.getWeight(), 1.147, DELTA);

        assertEquals(edges.size(), 5);
        assertEquals(waitingList, List.of(u5, u2));
        assertEquals(assignment.getCost(), 12.471, DELTA);
    }

    @Test
    public void casExclusionTuteur() {
        // cas 4.A
        assignment.setPolyTutor(false);
        assignment.removeStudent(t1);
        List<Arete<Student>> edges = assignment.getAssignment();
        List<Student> waitingList = assignment.getWaitingList();
         


        for (Arete<Student> edge : edges) {
            assertNotEquals(edge.getExtremite2(), t1);
        }
        assertEquals(edges.size(), 4);
        assertEquals(waitingList.size(), 3);
        assertEquals(waitingList, List.of(u5, u2, u3));
        assertEquals(assignment.getCost(), 7.178, DELTA);
    }

    @Test
    public void casExclusionTutore() {
        // cas 4.B
        assignment.setPolyTutor(false);
        assignment.removeStudent(u6);
        List<Arete<Student>> edges = assignment.getAssignment();
        List<Student> waitingList = assignment.getWaitingList();
         

        for (Arete<Student> edge : edges) {
            assertNotEquals(edge.getExtremite1(), u6);
        }
        assertEquals(edges.size(), 5);
        assertEquals(waitingList.size(), 1);
        assertEquals(waitingList, List.of(u5));
        assertEquals(assignment.getCost(), 9.457, DELTA);
    }
}
