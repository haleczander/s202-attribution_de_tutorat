package ooptests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import oop.Person;
import oop.Teacher;
import oop.Tutor;
import oop.Tutored;

public class PersonTest {    
    static Person p1 = new Tutored("Antoine", 0, 'A');
    static Person p2 = new Tutored("Jean Carle", 1, 'B');
    static Person p3 = new Tutor("Delphine", 2,3 , 'C');
    static Person p4 = new Tutor("Patricia Everaere", 3, 0, 'A');
    static Person p5 = new Teacher("Corwyn Fèvre");
    static Person p6 = new Teacher("Iovka");
    static Person p7 = ((Tutor)p4).duplicate();
    
    static Person[] persons = new Person[] {p1, p2, p3, p4, p5, p6, p7};

    public static void main(String[] args) {
        forenameTest();
        surnameTest();
        nameTest();
        isStudentTest();
        toStringTest();
        shortToStringTest();
    }

    @Test static void forenameTest(){
        String[] forenames = new String[] {"Antoine", "Jean", "Delphine", "Patricia", "Corwyn", "Iovka", "Patricia"};

        for (int i = 0 ; i < PersonTest.persons.length ; i++) {
            assertEquals(forenames[i], PersonTest.persons[i].getForename());
        }
    }

    @Test static void surnameTest(){
        String[] surnames = new String[] {null, "Carle", null, "Everaere", "Fèvre", null, "Everaere(D)"};

        for (int i = 0 ; i < PersonTest.persons.length ; i++) {
            assertEquals(surnames[i], PersonTest.persons[i].getSurname());
        }
    }

    @Test static void nameTest(){
        String[] names = new String[] {"Antoine", "Jean Carle", "Delphine", "Patricia Everaere", "Corwyn Fèvre", "Iovka", "Patricia Everaere(D)"};

        for (int i = 0 ; i < PersonTest.persons.length ; i++) {
            assertEquals(names[i], PersonTest.persons[i].getName());
        }
    }

    @Test static void isStudentTest(){
        assertTrue(p1.isStudent());
        assertTrue(p2.isStudent());
        assertTrue(p3.isStudent());
        assertTrue(p4.isStudent());
        assertFalse(p5.isStudent());
        assertFalse(p6.isStudent());
        assertTrue(p7.isStudent());
    }

    @Test static void toStringTest(){
        assertEquals("Tutored [Antoine, level= 1, absences= 0, notes= {}, motivation= A]", p1.toString());
        assertEquals("Tutored [Jean Carle, level= 1, absences= 1, notes= {}, motivation= B]", p2.toString());
        assertEquals("Tutor [Delphine, level= 2, absences= 3, notes= {}, motivation= C, nbOfTutored= 1]", p3.toString());
        assertEquals("Tutor [Patricia Everaere, level= 3, absences= 0, notes= {}, motivation= A, nbOfTutored= 2]", p4.toString());
        assertEquals("Teacher [Corwyn Fèvre, matières= []]", p5.toString());
        assertEquals("Teacher [Iovka, matières= []]", p6.toString());
        assertEquals("TutorDuplicate [Patricia Everaere(D), level= 3, absences= 0, notes= {}, motivation= A, nbOfTutored= 1]", p7.toString());
    }

    @Test static void shortToStringTest(){
        Person.setShortName(true);
        
        assertEquals("Antoine", p1.toString());
        assertEquals("Jean Carle", p2.toString());
        assertEquals("Delphine", p3.toString());
        assertEquals("Patricia Everaere", p4.toString());
        assertEquals("Corwyn Fèvre", p5.toString());
        assertEquals("Iovka", p6.toString());
        assertEquals("Patricia Everaere(D)", p7.toString());
    }
}
