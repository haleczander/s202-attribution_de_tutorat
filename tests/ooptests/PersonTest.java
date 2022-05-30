package ooptests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import graphs.affectation.Tutor;

public class PersonTest {
    // Person : Student et Teacher
    // Student : Tutor et Tutored
    // Tutor : TutorDuplicate

    //Student
    


    public static void main(String[] args) {
        PersonTest test = new PersonTest();
        test.testTutors();
    }

    void testTutors(){
        //Tutor standard
        Tutor t1 = new Tutor("Jean", 15, 2, 0, 'A');
        //Mauvaise moyenne
        Tutor t2 = new Tutor("Jean2", -1, 2, 0, 'A');
        Tutor t3 = new Tutor("Jean3", 21, 2, 0, 'A');
        //Mauvais niveau
        Tutor t4 = new Tutor("Jean4", 15, 1, 0, 'A');
        Tutor t5 = new Tutor("Jean5", 15, 4, 0, 'A');
        //Absences négatives
        Tutor t6 = new Tutor("Jean6", 15, 2, -1, 'A');
        //Mauvaise motivation
        Tutor t7 = new Tutor("Jean7", 15, 2, 0, 'a');
        Tutor t8 = new Tutor("Jean8", 15, 2, 0, '0');
        Tutor t9 = new Tutor("Jean9", 15, 2, 0, 'D');
        //Nombre de tutorés
        Tutor t10 = new Tutor("Jean10", 15, 2, 0, 'A', 1);
        Tutor t11 = new Tutor("Jean11", 15, 2, 0, 'A', 2);
        Tutor t12 = new Tutor("Jean12", 15, 3, 0, 'A', 1);
        Tutor t13 = new Tutor("Jean12", 15, 3, 0, 'A', 2);
        Tutor t14 = new Tutor("Jean12", 15, 3, 0, 'A', 1);
        Tutor t15 = new Tutor("Jean15", 15, 3, 0, 'A', -1);
        Tutor t16 = new Tutor("Jean16", 15, 3, 0, 'A', 3);

        String[] names = new String[]{"Jean", "Jean2", "Jean3", "Jean4", "Jean5", "Jean6", "Jean7", "Jean8", "Jean9", "Jean10", "Jean11", "Jean12", "Jean12", "Jean14", "Jean15", "Jean16"};

        double[] averages = new double[] {15, (Double)null, (Double)null, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15};

        int[] levels = new int[] {2, 2, 2, (Integer)null, (Integer)null, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3};

        int[] absences = new int[] {0, 0, 0, 0, 0, (Integer)null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        char[] motivation = new char[] {'A', 'A', 'A', 'A', 'A', 'A', (Character)null, (Character)null, (Character)null, 'A', 'A', 'A', 'A', 'A', 'A', 'A'};

        int[] nbofTutored = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2};

        Tutor[] tutors = new Tutor[] {t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16};

        for (int i = 0; i < tutors.length; i++) {
            assertEquals(names[i], tutors[i].getName());
            assertEquals(averages[i], tutors[i].getAverage());
            assertEquals(levels[i], tutors[i].getLevel());
            assertEquals(absences[i], tutors[i].getAbsences());
            assertEquals(motivation[i], tutors[i].getMotivation());
            assertEquals(nbofTutored[i], tutors[i].getNbofTutored());
        }
    }
}
