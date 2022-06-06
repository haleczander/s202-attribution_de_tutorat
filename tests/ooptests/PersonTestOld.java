// package ooptests;
// import static org.junit.jupiter.api.Assertions.assertEquals;

// import org.junit.jupiter.api.Test;

// import oop.Student;
// import oop.Tutor;
// import oop.TutorDuplicate;

// public class PersonTestOld {
//     // Person : Student et Teacher
//     // Student : Tutor et Tutored
//     // Tutor : TutorDuplicate

//     //Student
//     //Tutor standard
//     Tutor t1 = new Tutor("Jean", 15, 2, 0, 'A');
//     //Mauvaise moyenne
//     Tutor t2 = new Tutor("Jean2", -1, 2, 0, 'A');
//     Tutor t3 = new Tutor("Jean3", 21, 2, 0, 'A');
//     //Mauvais niveau
//     Tutor t4 = new Tutor("Jean4", 15, 1, 0, 'A');
//     Tutor t5 = new Tutor("Jean5", 15, 4, 0, 'A');
//     //Absences négatives
//     Tutor t6 = new Tutor("Jean6", 15, 2, -1, 'A');
//     //Mauvaise motivation
//     Tutor t7 = new Tutor("Jean7", 15, 2, 0, 'a');
//     Tutor t8 = new Tutor("Jean8", 15, 2, 0, '0');
//     Tutor t9 = new Tutor("Jean9", 15, 2, 0, 'D');
//     //Nombre de tutorés
//     Tutor t10 = new Tutor("Jean10", 15, 2, 0, 'A', 1);
//     Tutor t11 = new Tutor("Jean11", 15, 2, 0, 'A', 2);
//     Tutor t12 = new Tutor("Jean12", 15, 3, 0, 'A', 1);
//     Tutor t13 = new Tutor("Jean13", 15, 3, 0, 'A', 2);
//     Tutor t14 = new Tutor("Jean14", 15, 3, 0, 'A', 1);
//     Tutor t15 = new Tutor("Jean15", 15, 3, 0, 'A', -1);
//     Tutor t16 = new Tutor("Jean16", 15, 3, 0, 'A', 3);

//     Tutor[] tutors = new Tutor[] {t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16};


//     public static void main(String[] args) {
//         PersonTestOld test = new PersonTestOld();
//         test.testTutors();

//     }



//     @Test
//     void testTutors(){
//         getNameTest();
//         getAverageTest();
//         getLevelTest();
//         getAbsencesTest();
//         getMotivationTest();
//         getNbofTutoredTest();      
//         getWeightTest();
//         duplicateTest();

//     }

//     @Test
//     void duplicateTest(){
//         for (int i = 0; i < tutors.length; i++) {
//             assertEquals(new TutorDuplicate(tutors[i]), tutors[i].duplicate());
//         }
//     }

//     @Test
//     void getWeightTest(){
//         double[] weights = new double[] {};

//         for (int i = 0; i < tutors.length; i++) {
//             assertEquals(weights[i], tutors[i].getWeight());
//         }
//     }
    
//     @Test
//     void getNameTest(){
//         String[] names = new String[]{"Jean", "Jean2", "Jean3", "Jean4", "Jean5", "Jean6", "Jean7", "Jean8", "Jean9", "Jean10", "Jean11", "Jean12", "Jean13", "Jean14", "Jean15", "Jean16"};

//         for (int i = 0; i < tutors.length; i++) {
//             assertEquals(names[i], tutors[i].getName());
//         }
//     }

//     @Test
//     void getAverageTest(){
//         double[] averages = new double[] {15, Student.getDefaultGrade(), Student.getDefaultGrade(), 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15};

//         for (int i = 0; i < tutors.length; i++) {
//             assertEquals(averages[i], tutors[i].getAverage());
//         }
//     }

//     @Test
//     void getLevelTest(){
//         int[] levels = new int[] {2, 2, 2, Tutor.getDefaultLevel(), Tutor.getDefaultLevel(), 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3};

//         for (int i = 0; i < tutors.length; i++) {
//             assertEquals(levels[i], tutors[i].getLevel());
//         }
//     }

//     @Test
//     void getAbsencesTest(){
//         int[] absences = new int[] {0, 0, 0, 0, 0, Student.getDefaultAbsences(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

//         for (int i = 0; i < tutors.length; i++) {
//             assertEquals(absences[i], tutors[i].getAbsences());
//         }
//     }    

//     @Test
//     void getMotivationTest(){
//         char[] motivation = new char[] {'A', 'A', 'A', 'A', 'A', 'A', Student.getDefaultMotivation(), Student.getDefaultMotivation(), Student.getDefaultMotivation(), 'A', 'A', 'A', 'A', 'A', 'A', 'A'};

//         for (int i = 0; i < tutors.length; i++) {
//             assertEquals(motivation[i], tutors[i].getMotivation());
//         }
//     }

//     @Test
//     void getNbofTutoredTest(){
//         int[] nbofTutored = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 2, 2};

//         for (int i = 0; i < tutors.length; i++) {
//             // System.out.println(tutors[i].getName());
//             assertEquals(nbofTutored[i], tutors[i].getNbofTutored());
//         }
//     }
// }
